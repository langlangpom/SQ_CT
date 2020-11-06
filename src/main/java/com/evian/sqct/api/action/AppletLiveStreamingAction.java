package com.evian.sqct.api.action;

import com.evian.sqct.annotation.DataNotLogCheck;
import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.bean.appletLiveStreaming.request.*;
import com.evian.sqct.bean.appletLiveStreaming.response.*;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseAppletLiveStreamingManager;
import com.evian.sqct.util.BeanConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * ClassName:AppletLiveStreamingAction
 * Package:com.evian.sqct.api.action
 * Description:小程序直播action
 *
 * @Date:2020/6/19 12:05
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/appletLiveStreaming")
public class AppletLiveStreamingAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseAppletLiveStreamingManager appletLiveStreamingManager;

    /**
     * 207获取该企业直播可用商品
     * @param eid
     * @return
     */
    @RequestMapping("getapproved.action")
    @TokenNotVerify
    @DataNotLogCheck
    public GetapprovedRpeDTO getapproved(@Valid GetapprovedReqDTO dto){
        return appletLiveStreamingManager.getapproved(dto);
    }


    /**
     * 获取该企业微信永久素材
     * @param eid
     * @return
     */
    @RequestMapping("batchgetMaterial.action")
    @TokenNotVerify
    @DataNotLogCheck
    public BatchgetMaterialRpeDTO batchgetMaterial(@Valid BatchgetMaterialReqDTO dto){
        return appletLiveStreamingManager.batchgetMaterial(dto);
    }

    /**
     * 208直播间商品操作(添加并提审、撤回审核、重新提审、删除、更新商品)
     * @param dto
     * @param feedbackImg
     * @return
     */
    @PostMapping("goodsOper.action")
    @TokenNotVerify
    @DataNotLogCheck
    public ResultVO goodsOper(GoodsAddGoodsInfoFileReqDTO dto,Integer eid,String status,Integer auditId,Integer goodsId, @RequestParam("goodsInfoImg") MultipartFile... goodsInfoImg) throws Exception {
        if(eid==null||status==null){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        if("add".equals(status)){
            if(goodsInfoImg==null||(goodsInfoImg.length==0&& StringUtils.isBlank(dto.getCoverImgUrl()))){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            if(dto.getName()==null||dto.getPriceType()==null||dto.getPrice()==null||dto.getUrl()==null||eid==null){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            GoodsAddRpeDTO goodsAddRpeDTO = appletLiveStreamingManager.goodsAddFile(dto,eid,status,goodsId, goodsInfoImg);
            return new ResultVO(goodsAddRpeDTO);
        }else if("resetaudit".equals(status)){
            if(eid==null||auditId==null||goodsId==null){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            appletLiveStreamingManager.goodsResetaudit(eid, auditId, goodsId);
        }else if("audit".equals(status)){
            if(eid==null||goodsId==null){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            Map<String, Object> result = appletLiveStreamingManager.goodsAudit(eid, goodsId);
            return new ResultVO(result);
        }else if("delete".equals(status)){
            if(eid==null||goodsId==null){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            appletLiveStreamingManager.goodsDelete(eid, goodsId);
        }else if("update".equals(status)){
            if(eid==null||goodsId==null||(StringUtils.isBlank(dto.getUrl())&&StringUtils.isBlank(dto.getPrice())
            &&dto.getPriceType()==null&&StringUtils.isBlank(dto.getName())&&dto.getPrice2()==null
                    &&StringUtils.isBlank(dto.getCoverImgUrl())&&goodsInfoImg.length==0)){
                return new ResultVO(ResultCode.CODE_ERROR_PARAM);
            }
            appletLiveStreamingManager.goodsAddFile(dto,eid,status,goodsId, goodsInfoImg);
        }else{
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        return new ResultVO();
    }

    /**
     * 209.创建直播间
     * @param dto
     * @return
     */
    @PostMapping("createLiveinfo.action")
    @TokenNotVerify
    @DataNotLogCheck
    public ResultVO createLiveinfo(@Valid CreateLiveinfoReqDTO dto
            ,@RequestParam("coverImg") MultipartFile coverImg
            ,@RequestParam("shareImg") MultipartFile shareImg) throws Exception {

        UploadRpeDTO coverDTO = appletLiveStreamingManager.uploadingTempMaterial(dto.getEid(), coverImg);
        if(coverImg==null||shareImg==null){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }else if(coverImg.getSize() >2048*1024|| shareImg.getSize() >1024*1024){
            logger.error("[error:{}] [coverImgSize:{}] [shareImgSize:{}]"
                    ,ResultCode.CODE_ERROR_IMAGE_SIZE.getMessage(),coverImg.getSize(),shareImg.getSize());
            // 没有按规定上传图片大小
            return new ResultVO(ResultCode.CODE_ERROR_IMAGE_SIZE);
        }
        UploadRpeDTO shareImgDTO = appletLiveStreamingManager.uploadingTempMaterial(dto.getEid(), shareImg);
        WXCreateLiveinfoReqDTO wxDTO = BeanConvertUtils.copyProperties(dto, WXCreateLiveinfoReqDTO.class);
        wxDTO.setCoverImg(coverDTO.getMedia_id());
        wxDTO.setShareImg(shareImgDTO.getMedia_id());
        Map<String, Object> liveinfo = appletLiveStreamingManager.createLiveinfo(wxDTO,dto.getEid());
        return new ResultVO(liveinfo);
    }

    /**
     * 210.获取直播间列表
     * @param dto
     * @return
     */
    @PostMapping("getliveinfo.action")
    @TokenNotVerify
    @DataNotLogCheck
    public GetliveinfoRpeDTO getliveinfo(@Valid GetliveinfoReqDTO dto){
        return appletLiveStreamingManager.getliveinfo(dto);
    }

    /**
     * 211.直播间导入已入库商品
     * @param dto
     * @return
     * https://api.weixin.qq.com/wxaapi/broadcast/room/addgoods?access_token=
     */
    @PostMapping("liveinfoAddgoods.action")
    @TokenNotVerify
    @DataNotLogCheck
    public ResultVO liveinfoAddgoods(@Valid LiveinfoAddgoodsReqDTO dto){
        appletLiveStreamingManager.liveinfoAddgoods(dto);
        return new ResultVO();
    }


    @RequestMapping("test001")
    @TokenNotVerify
    @DataNotLogCheck
    @Valid
    public ResultVO test001( @NotNull() String yy){
        return new ResultVO();
    }

}
