package com.evian.sqct.service;

import com.evian.sqct.bean.activity.EappMerchantAhareCodeActivityItem;
import com.evian.sqct.bean.activity.EappMerchantShareCodeActivity;
import com.evian.sqct.dao.IActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseActivityManager
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2019/10/10 15:59
 * @Author:XHX
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BaseActivityManager extends BaseManager{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("activityDao")
    private IActivityDao activityDao;

    public Map<String,Object> selectEappMerchantShareCodeActivityByEidAndActivityType(Integer eid,Integer accountId){
        Map<String ,Object> map = new HashMap<>();
        List<EappMerchantShareCodeActivity> couponShareList = activityDao.Proc_Backstage_appMerchant_share_code_activity_select_forApp(eid, 1,accountId);
        List<EappMerchantShareCodeActivity> couponSendList = activityDao.Proc_Backstage_appMerchant_share_code_activity_select_forApp(eid, 2,accountId);
        map.put("couponShareList",couponShareList);
        map.put("couponSendList",couponSendList);
        return map;
    }

    public List<EappMerchantAhareCodeActivityItem> selectEappMerchantAhareCodeActivityItemByActivityId(Integer activityId){
        return activityDao.selectEappMerchantAhareCodeActivityItemByActivityId(activityId);
    }

    public List<Map<String,Object>> Proc_Backstage_appMerchant_share_code_activity_item_select(Integer eid,Integer activityId){
        return activityDao.Proc_Backstage_appMerchant_share_code_activity_item_select(eid,activityId);
    }

    public Map<String,Object> Proc_Backstage_appMerchant_share_code_activity_present_record_present(Integer eid,Integer activityId,Integer accountId,Integer codeTypeId,String cellphone){
        return activityDao.Proc_Backstage_appMerchant_share_code_activity_present_record_present(eid, activityId, accountId, codeTypeId, cellphone);
    }

    public Map<String,Object> Proc_Backstage_appMerchant_share_code_activity_present_record_select(String beginTime,String endTime,Integer activityId,Integer accountId,Integer eid,Integer codeTypeId,String code,Boolean isComplete,Integer clientId,String cellphone,String staffAccount,Boolean isSendCellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll){
        return activityDao.Proc_Backstage_appMerchant_share_code_activity_present_record_select(beginTime, endTime, activityId, accountId, eid, codeTypeId, code, isComplete, clientId, cellphone, staffAccount,isSendCellphone, PageIndex, PageSize, IsSelectAll);
    }

}
