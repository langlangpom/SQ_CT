package com.evian.sqct.service;

import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.ILedDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseLedManager
 * Package:com.evian.sqct.service
 * Description:LEDService
 *
 * @Date:2019/12/11 9:55
 * @Author:XHX
 */
@Service
public class BaseLedManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("ledDao")
    private ILedDao ledDao;

    @Autowired
    @Qualifier("userDao")
    private IUserDao userDao;

    @Autowired
    @Qualifier("vendorDao")
    private IVendorDao vendorDao;

    public Map<String, Object> selectLedOrderByOrderId(Integer orderId) {
        return ledDao.selectLedOrderByOrderId(orderId);
    }

    public Integer updateLedOrderStatus(Integer orderId,Integer auditStatus,String auditRemark,String auditor){
        return ledDao.updateLedOrderStatus(orderId, auditStatus, auditRemark, auditor);
    }

    public void ledAOrderuditJPush(Integer orderId){
        List<Map<String, Object>> maps = vendorDao.selectVendorManagementByLEDOrderId(orderId);
        if(maps.size()>0){
            for (Map<String, Object> map:maps) {
                Integer accountId = (Integer) map.get("accountId");
                jpushPushMsg(accountId,orderId);
            }
        }else{
            logger.error("[广告订单审核管理员极光推送错误:没有管理员] [orderId:{}]",new Object[]{orderId});
        }
    }

    /**
     * 广告订单审核管理员极光推送
     * @param send_accountId
     * @param orderIds
     */
    public void jpushPushMsg(Integer accountId,Integer orderId){

        Map<String, Object> stringObjectMap = userDao.selectAppMerchantJpush(accountId);
        if(stringObjectMap==null||stringObjectMap.get("regeditId")==null){
            logger.error("[param:{jpushPushMsg}] [send_accountId:{}] [error:{accountId没有极光账号}]",accountId);
            return;
        }
        String regeditId = (String) stringObjectMap.get("regeditId");
        Integer sourceId = (Integer) stringObjectMap.get("sourceId");
        Integer platformId = (Integer) stringObjectMap.get("platformId");

        Integer xid =orderId;
        String title = "广告订单审核通知";
        String message = "广告有新单需要审核，点击查看";
        Integer type = 10002;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());

        String sendTime = dateString;
        Integer platform = sourceId;
        String registerId = regeditId;
        String jpushTag = "";
        StringBuffer voiceContent = new StringBuffer("");
        JPushShangHuModel model = new JPushShangHuModel(xid, title, message, type, sendTime, platform, registerId, jpushTag, voiceContent.toString(),platformId);
        JpushShangHuService.pushMsg(model);

    }

    public List<Map<String,Object>> selectLedOrderByEid(Integer eid){
        return ledDao.selectLedOrderByEid(eid);
    }

    public Map<String, Object> Proc_Backstage_vendor_ad_led_order_new_select(String beginTime,String endTime,Integer orderId,String orderNo,Integer eid,String mainboardNo,Integer auditStatus,Integer dataSourse,String shopName,Boolean isTest,String nickName,String openId,Integer PageIndex,Integer  PageSize,Boolean IsSelectAll){
        return ledDao.Proc_Backstage_vendor_ad_led_order_new_select(beginTime, endTime,orderId, orderNo, eid, mainboardNo, auditStatus, dataSourse, shopName, isTest, nickName, openId, PageIndex, PageSize, IsSelectAll);
    }

    public String downAdvertingToVendor(Integer mainboardId,String mainboardNo,String content,String dir,Integer speed,Integer time,Integer id){
        List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
        params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
        params.add(new BasicNameValuePair("content", content));
        params.add(new BasicNameValuePair("dir", dir));
        params.add(new BasicNameValuePair("speed", speed.toString()));
        params.add(new BasicNameValuePair("time", time.toString()));
        params.add(new BasicNameValuePair("id", id.toString()));
        String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/advertising/downAdvertingToVendor.action", params);
        logger.info("postEvianApi:{}",postEvianApi);
        return postEvianApi;
    }

    public String vendorOpenCloseAD(Integer mainboardId,String mainboardNo,Integer status){
        List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
        params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
        params.add(new BasicNameValuePair("status", status.toString()));
        String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/advertising/vendorOpenCloseAD.action", params);
        logger.info("postEvianApi:{}",postEvianApi);
        return postEvianApi;
    }

    public String updateAdvertingStatus(Integer mainboardId,String mainboardNo,Integer status,Integer id){
        List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("mainboardId", mainboardId.toString()));
        params.add(new BasicNameValuePair("mainboardNo", mainboardNo));
        params.add(new BasicNameValuePair("status", status.toString()));
        params.add(new BasicNameValuePair("id", id.toString()));
        String postEvianApi = HttpClientUtilOkHttp.postEvianApi(UrlManage.getContainerMarketDetectionUnLockUrl()+"evian/advertising/updateAdvertingStatus.action", params);
        logger.info("postEvianApi:{}",postEvianApi);
        return postEvianApi;
    }

}
