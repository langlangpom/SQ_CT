package com.evian.sqct.dao;

import com.evian.sqct.bean.activity.EappMerchantAhareCodeActivityItem;
import com.evian.sqct.bean.activity.EappMerchantShareCodeActivity;

import java.util.List;
import java.util.Map;

/**
 * ClassName:IActivityDao
 * Package:com.evian.sqct.dao
 * Description:活动Dao
 *
 * @Date:2019/10/10 15:50
 * @Author:XHX
 */
public interface IActivityDao {

    /**
     * 查询活动
     * @param eid
     * @param activityType
     * @return
     */
    List<EappMerchantShareCodeActivity> selectEappMerchantShareCodeActivityByEidAndActivityType(Integer eid,Integer activityType);
    List<EappMerchantShareCodeActivity> Proc_Backstage_appMerchant_share_code_activity_select_forApp(Integer eid,Integer activityType,Integer accountId);

    /**
     * 根据活动id查询优惠券
     * @param ActivityId
     * @return
     */
    List<EappMerchantAhareCodeActivityItem> selectEappMerchantAhareCodeActivityItemByActivityId(Integer activityId);

    List<Map<String,Object>> Proc_Backstage_appMerchant_share_code_activity_item_select(Integer eid,Integer activityId);

    Map<String,Object> Proc_Backstage_appMerchant_share_code_activity_present_record_present(Integer eid,Integer activityId,Integer accountId,Integer codeTypeId,String cellphone);

    Map<String,Object> Proc_Backstage_appMerchant_share_code_activity_present_record_select(String beginTime,String endTime,Integer activityId,Integer accountId,Integer eid,Integer codeTypeId,String code,Boolean isComplete,Integer clientId,String cellphone,String staffAccount,Boolean isSendCellphone,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);



}
