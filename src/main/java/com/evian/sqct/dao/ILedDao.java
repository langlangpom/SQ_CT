package com.evian.sqct.dao;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ILedDao
 * Package:com.evian.sqct.dao
 * Description:广告Dao
 *
 * @Date:2019/12/11 9:49
 * @Author:XHX
 */
public interface ILedDao {

    Map<String,Object> selectLedOrderByOrderId(Integer orderId);

    Integer updateLedOrderStatus(Integer orderId,Integer auditStatus,String auditRemark,String auditor);

    List<Map<String,Object>> selectLedOrderByEid(Integer eid);

    Map<String, Object> Proc_Backstage_vendor_ad_led_order_new_select(String beginTime,String endTime,Integer orderId,String orderNo,Integer eid,String mainboardNo,Integer auditStatus,Integer dataSourse,String shopName,Boolean isTest,String nickName,String openId,Integer PageIndex,Integer  PageSize,Boolean IsSelectAll);
}
