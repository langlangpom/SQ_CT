package com.evian.sqct.service;

import com.evian.sqct.bean.appletLiveStreaming.request.*;
import com.evian.sqct.bean.appletLiveStreaming.response.*;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName:BaseAppletLiveStreamingManager
 * Package:com.evian.sqct.service
 * Description:小程序直播
 *
 * @Date:2020/6/19 11:45
 * @Author:XHX
 */
@Service
public class BaseAppletLiveStreamingManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BaseEnterpriseManager enterpriseManager;

    /**
     * 获取直播商品
     * @param eid
     * @return
     */
    public GetapprovedRpeDTO getapproved(GetapprovedReqDTO dto){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(dto.getEid());
        Integer pageIndex = dto.getPageIndex();
        Integer pageSize = dto.getPageSize();
        int offset = (pageIndex-1)* pageSize;
        int limit = pageSize;
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/getapproved?access_token={0}&offset={1}&limit={2}&status={3}";
        url = MessageFormat.format(url,access_token,offset,limit,dto.getStatus());

        logger.info(url);
        String result = HttpClientUtil.doGet(url);
        WXGetapprovedRpeDTO wxGetapprovedRpeDTO = JacksonUtils.json2pojo(result, WXGetapprovedRpeDTO.class);
        if(wxGetapprovedRpeDTO.getErrcode()!=0){
//            throw new ResultException(wxGetapprovedRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGetapprovedRpeDTO.getErrcode(),wxGetapprovedRpeDTO.getErrmsg());
        }
        return BeanConvertUtils.copyProperties(wxGetapprovedRpeDTO, GetapprovedRpeDTO.class);

    }

    /**
     * 获取该企业微信永久素材
     * @param eid
     * @return
     */
    public BatchgetMaterialRpeDTO batchgetMaterial(BatchgetMaterialReqDTO dto){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectEnterpriseGZHAppidByEid(dto.getEid());
        Integer pageIndex = dto.getPageIndex();
        Integer pageSize = dto.getPageSize();
        int offset = (pageIndex-1)* pageSize;
        int count = pageSize;
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token={0}";
        url = MessageFormat.format(url,access_token);
        JSONObject json = new JSONObject();
        json.put("type",dto.getType());
        json.put("offset",offset);
        json.put("count",count);
        logger.info(url);
        String result = HttpClientUtil.doPostJson(url,json.toString());
        WXBatchgetMaterialRpeDTO wxBatchgetMaterialRpeDTO = JacksonUtils.json2pojo(result, WXBatchgetMaterialRpeDTO.class);
        if(wxBatchgetMaterialRpeDTO!=null&&wxBatchgetMaterialRpeDTO.getErrcode()!=null&&wxBatchgetMaterialRpeDTO.getErrcode()!=0){
            logger.error(result);
//            throw new ResultException(wxBatchgetMaterialRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxBatchgetMaterialRpeDTO.getErrcode(),wxBatchgetMaterialRpeDTO.getErrmsg());
        }

        return BeanConvertUtils.copyProperties(wxBatchgetMaterialRpeDTO, BatchgetMaterialRpeDTO.class);
    }

    /**
     * 商品添加并提审
     * @param eid
     * @return
     */
    public GoodsAddRpeDTO goodsAdd(GoodsAddReqDTO dto){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(dto.getEid());
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/add?access_token={0}";
        url = MessageFormat.format(url,access_token);
        Map<String, GoodsAddGoodsInfoReqDTO> map = new HashMap<>();
        GoodsAddGoodsInfoReqDTO goodsInfo = dto.getGoodsInfo();
        String infoUrl = goodsInfo.getUrl();
        String[] split = infoUrl.split("\\?");
        if(split.length>1){
            // 该参数的值需要进行 encode 处理再填入  https://developers.weixin.qq.com/miniprogram/dev/framework/liveplayer/commodity-api.html
            /*try {
                String param = split[1];
                param = URLEncoder.encode(param,"UTF-8");
                goodsInfo.setUrl(split[0]+"?"+param);
            } catch (UnsupportedEncodingException e) {
                throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
            }*/
            String param = split[1];
            goodsInfo.setUrl(split[0]+"?"+param);
        }
        map.put("goodsInfo", goodsInfo);
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGoodsAddRpeDTO wxGoodsAddRpeDTO = JacksonUtils.json2pojo(result, WXGoodsAddRpeDTO.class);
        if(wxGoodsAddRpeDTO!=null&&wxGoodsAddRpeDTO.getErrcode()!=null&&wxGoodsAddRpeDTO.getErrcode()!=0){
            logger.error(result);
            if(wxGoodsAddRpeDTO.getErrcode()==300018){
                // 没有按规定上传图片大小
                throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_IMAGE_SIZE.getCode()
                        ,ResultCode.CODE_ERROR_IMAGE_SIZE.getMessage()+"，规定图片长和宽为300px * 300px");
            }

//            throw new ResultException(wxGoodsAddRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGoodsAddRpeDTO.getErrcode(),wxGoodsAddRpeDTO.getErrmsg());

        }

        return BeanConvertUtils.copyProperties(wxGoodsAddRpeDTO, GoodsAddRpeDTO.class);
    }

    /**
     * 添加临时素材，完成后调用goodsAdd(商品添加并提审)
     * @param dto
     * @param goodsInfoImg
     * @return
     * @throws Exception
     */
    public GoodsAddRpeDTO goodsAddFile(GoodsAddGoodsInfoFileReqDTO dto,Integer eid,String status,Integer goodsId, MultipartFile... goodsInfoImg) throws Exception {
        if("add".equals(status)||(goodsInfoImg.length>0|| !StringUtils.isBlank(dto.getCoverImgUrl()))){
            EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
            String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
            String url = "https://api.weixin.qq.com/cgi-bin/media/upload?type=image&access_token="+access_token;
            String result = null;
            String fileName = dto.getName()+".png";
            if(goodsInfoImg.length>0){
                MultipartFile multipartFile = goodsInfoImg[0];
                result = HttpClientUtil.UploadFile(url, multipartFile.getInputStream(),fileName);
            }else{
                result = HttpClientUtil.UploadFile(url, HttpClientUtil.getImageStream(dto.getCoverImgUrl()),fileName);
            }
            UploadRpeDTO upload = JacksonUtils.json2pojo(result, UploadRpeDTO.class);
            if(upload!=null&&upload.getErrcode()!=null&&upload.getErrcode()!=0){
                logger.error(result);
//                throw new ResultException(upload.getErrmsg());
                throw BaseRuntimeException.jointCodeAndMessage(upload.getErrcode(),upload.getErrmsg());
            }

            GoodsAddGoodsInfoReqDTO goodsAddGoodsInfoReqDTO = BeanConvertUtils.copyProperties(dto, GoodsAddGoodsInfoReqDTO.class);
            goodsAddGoodsInfoReqDTO.setCoverImgUrl(upload.getMedia_id());
            GoodsAddReqDTO goodsAddReqDTO = new GoodsAddReqDTO(eid,goodsAddGoodsInfoReqDTO);
            if("add".equals(status)){
                // 商品添加并提审
                return goodsAdd(goodsAddReqDTO);
            }else{
                // 更新商品
                return goodsUpdate(goodsAddReqDTO,goodsId);
            }
        // 更新商品
        }else{
            GoodsAddGoodsInfoReqDTO goodsAddGoodsInfoReqDTO = BeanConvertUtils.copyProperties(dto, GoodsAddGoodsInfoReqDTO.class);
            GoodsAddReqDTO goodsAddReqDTO = new GoodsAddReqDTO(eid,goodsAddGoodsInfoReqDTO);
            return goodsUpdate(goodsAddReqDTO,goodsId);
        }
    }

    /**
     * 上传临时素材
     * @param eid
     * @param img
     * @return
     * @throws Exception
     */
    public UploadRpeDTO uploadingTempMaterial(Integer eid,MultipartFile img) throws Exception {
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?type=image&access_token="+access_token;
        String guid = UUID.randomUUID().toString();
        String fileName = MD5Util.md5(guid).toUpperCase();
        fileName = fileName+".png";
        logger.info("[url:{}] [fileName:{}]",url,fileName);
        String result = HttpClientUtil.UploadFile(url, img.getInputStream(),fileName);
        return JacksonUtils.json2pojo(result, UploadRpeDTO.class);
    }

    /**
     * 撤销审核
     * @param dto
     * @param goodsInfoImg
     * @return
     * @throws Exception
     */
    public void goodsResetaudit(Integer eid,Integer auditId,Integer goodsId){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/resetaudit?access_token="+access_token;
        Map<String, Integer> map = new HashMap<>(2);
        map.put("auditId",auditId);
        map.put("goodsId",goodsId);
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGoodsAddRpeDTO wxGoodsAddRpeDTO = JacksonUtils.json2pojo(result, WXGoodsAddRpeDTO.class);
        if(wxGoodsAddRpeDTO!=null&&wxGoodsAddRpeDTO.getErrcode()!=null&&wxGoodsAddRpeDTO.getErrcode()!=0){
            logger.error(result);
//            throw new ResultException(wxGoodsAddRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGoodsAddRpeDTO.getErrcode(),wxGoodsAddRpeDTO.getErrmsg());
        }
    }

    /**
     * 重新提交审核
     * @param dto
     * @param goodsInfoImg
     * @return
     * @throws Exception
     */
    public Map<String,Object> goodsAudit(Integer eid,Integer goodsId){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/audit?access_token="+access_token;
        Map<String, Integer> map = new HashMap<>(1);
        map.put("goodsId",goodsId);
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        Map<String,Object> resultMap = JacksonUtils.json2map(result);
        if(resultMap!=null&&resultMap.get("errcode")!=null&&(Integer)resultMap.get("errcode")!=0){
            logger.error(result);
//            throw new ResultException((String)resultMap.get("errmsg"));
            throw BaseRuntimeException.jointCodeAndMessage((Integer) resultMap.get("errcode"),(String)resultMap.get("errmsg"));
        }
        return resultMap;
    }

    /**
     * 删除商品
     * @param dto
     * @param goodsInfoImg
     * @return
     * @throws Exception
     */
    public void goodsDelete(Integer eid,Integer goodsId){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/delete?access_token="+access_token;
        Map<String, Integer> map = new HashMap<>(1);
        map.put("goodsId",goodsId);
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGoodsAddRpeDTO wxGoodsAddRpeDTO = JacksonUtils.json2pojo(result, WXGoodsAddRpeDTO.class);
        if(wxGoodsAddRpeDTO!=null&&wxGoodsAddRpeDTO.getErrcode()!=null&&wxGoodsAddRpeDTO.getErrcode()!=0){
            logger.error(result);
//            throw new ResultException(wxGoodsAddRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGoodsAddRpeDTO.getErrcode(),wxGoodsAddRpeDTO.getErrmsg());
        }
    }

    /**
     * 更新商品
     * @param dto
     * @param goodsInfoImg
     * @return
     * @throws Exception
     */
    public GoodsAddRpeDTO goodsUpdate(GoodsAddReqDTO dto,Integer goodsId){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(dto.getEid());
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/goods/update?access_token="+access_token;
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> goodsUpdate = new HashMap<>();
        GoodsAddGoodsInfoReqDTO goodsInfo = dto.getGoodsInfo();
        String infoUrl = goodsInfo.getUrl();
        if(!StringUtils.isBlank(infoUrl)){
            String[] split = infoUrl.split("\\?");
            if(split.length>1){
                // 该参数的值需要进行 encode 处理再填入  https://developers.weixin.qq.com/miniprogram/dev/framework/liveplayer/commodity-api.html
                /*try {
                    String param = split[1];
                    param = URLEncoder.encode(param,"UTF-8");
                    goodsUpdate.put("url",split[0]+"?"+param);
                } catch (UnsupportedEncodingException e) {
                    throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
                }*/
                String param = split[1];
//                param = URLEncoder.encode(param,"UTF-8");
                goodsUpdate.put("url",split[0]+"?"+param);
            }

        }
        String coverImgUrl = goodsInfo.getCoverImgUrl();
        if(!StringUtils.isBlank(coverImgUrl)){
            goodsUpdate.put("coverImgUrl",coverImgUrl);
        }
        String name = goodsInfo.getName();
        if(!StringUtils.isBlank(name)){
            goodsUpdate.put("name",name);
        }
        String price = goodsInfo.getPrice();
        if(!StringUtils.isBlank(price)){
            goodsUpdate.put("price",price);
        }
        String price2 = goodsInfo.getPrice2();
        if(price2!=null){
            goodsUpdate.put("price2",price2);
        }
        Integer priceType = goodsInfo.getPriceType();
        if(priceType!=null){
            goodsUpdate.put("priceType",priceType);
        }
        goodsUpdate.put("goodsId",goodsId);
        map.put("goodsInfo", goodsUpdate);
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGoodsAddRpeDTO wxGoodsAddRpeDTO = JacksonUtils.json2pojo(result, WXGoodsAddRpeDTO.class);
        if(wxGoodsAddRpeDTO!=null&&wxGoodsAddRpeDTO.getErrcode()!=null&&wxGoodsAddRpeDTO.getErrcode()!=0){
            logger.error(result);
//            throw new ResultException(wxGoodsAddRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGoodsAddRpeDTO.getErrcode(),wxGoodsAddRpeDTO.getErrmsg());
        }

        return BeanConvertUtils.copyProperties(wxGoodsAddRpeDTO, GoodsAddRpeDTO.class);
    }

    public GetliveinfoRpeDTO getliveinfo(GetliveinfoReqDTO dto){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(dto.getEid());
        Integer pageIndex = dto.getPageIndex();
        Integer pageSize = dto.getPageSize();
        int start = (pageIndex-1)* pageSize;
        int limit = pageSize;
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxa/business/getliveinfo?access_token="+access_token;
        Map<String,Object> map = new HashMap<>(2);
        map.put("start",start);
        map.put("limit",limit);
        String json = JacksonUtils.obj2json(map);
        logger.info(url);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGetliveinfoRpeDTO wxGetliveinfoRpeDTO = JacksonUtils.json2pojo(result, WXGetliveinfoRpeDTO.class);
        if(wxGetliveinfoRpeDTO.getErrcode()!=0){
//            throw new ResultException(wxGetliveinfoRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGetliveinfoRpeDTO.getErrcode(),wxGetliveinfoRpeDTO.getErrmsg());
        }
        return BeanConvertUtils.copyProperties(wxGetliveinfoRpeDTO, GetliveinfoRpeDTO.class);

    }

    public Map<String,Object> createLiveinfo(WXCreateLiveinfoReqDTO dto,Integer eid){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(eid);
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/room/create?access_token="+access_token;
        String json = JacksonUtils.obj2json(dto);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        Map<String,Object> resultMap = JacksonUtils.json2map(result);
        if(resultMap!=null&&resultMap.get("errcode")!=null&&(Integer)resultMap.get("errcode")!=0){
            logger.error(result);
//            throw new ResultException((String)resultMap.get("errmsg"));
            throw BaseRuntimeException.jointCodeAndMessage((Integer) resultMap.get("errcode"),(String)resultMap.get("errmsg"));
        }
        return resultMap;
    }

    /**
     * 直播间导入已入库商品
     * @param dto
     */
    public void liveinfoAddgoods(LiveinfoAddgoodsReqDTO dto){
        EEnterpriseWechatliteapp applet = enterpriseManager.selectGroupBookingApplet(dto.getEid());
        String access_token = WxTokenAndJsticketCache.getAccess_token(applet.getAppId());
        String url = "https://api.weixin.qq.com/wxaapi/broadcast/room/addgoods?access_token="+access_token;
        Map<String,Object> map = new HashMap<>(2);
        map.put("ids",dto.getIds());
        map.put("roomId",dto.getRoomId());
        String json = JacksonUtils.obj2json(map);
        logger.info(json);
        String result = HttpClientUtil.doPostJson(url,json);
        WXGoodsAddRpeDTO wxGoodsAddRpeDTO = JacksonUtils.json2pojo(result, WXGoodsAddRpeDTO.class);
        if(wxGoodsAddRpeDTO!=null&&wxGoodsAddRpeDTO.getErrcode()!=null&&wxGoodsAddRpeDTO.getErrcode()!=0){
            logger.error(result);
//            throw new ResultException(wxGoodsAddRpeDTO.getErrmsg());
            throw BaseRuntimeException.jointCodeAndMessage(wxGoodsAddRpeDTO.getErrcode(),wxGoodsAddRpeDTO.getErrmsg());
        }
    }

}
