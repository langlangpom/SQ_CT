package com.evian.sqct.service;

import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.dao.ILedDao;
import com.evian.sqct.dao.IUserDao;
import com.evian.sqct.dao.IVendorDao;
import com.evian.sqct.util.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        logger.info("[project:{}] [step:enter] [orderId:{}]",
                new Object[] { WebConfig.projectName,orderId});
        return ledDao.selectLedOrderByOrderId(orderId);
    }

    public Integer updateLedOrderStatus(Integer orderId,Integer auditStatus,String auditRemark,String auditor){
        logger.info("[project:{}] [step:enter] [orderId:{}] [auditStatus:{}] [auditRemark:{}] [auditor:{}]",
                new Object[] { WebConfig.projectName,orderId,auditStatus,auditRemark,auditor});
        return ledDao.updateLedOrderStatus(orderId, auditStatus, auditRemark, auditor);
    }

    public void ledAOrderuditJPush(Integer orderId){
        logger.info("[project:{}] [step:enter] [orderId:{}]",
                new Object[] { WebConfig.projectName,orderId});
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
        JPushShangHuModel model = new JPushShangHuModel(xid, title, message, type, sendTime, platform, registerId, jpushTag, voiceContent.toString());
        JpushShangHuService.pushMsg(model);

    }

    public List<Map<String,Object>> selectLedOrderByEid(Integer eid){
        logger.info("[project:{}] [step:enter] [eid:{}]",
                new Object[] { WebConfig.projectName,eid});
        return ledDao.selectLedOrderByEid(eid);
    }

}
