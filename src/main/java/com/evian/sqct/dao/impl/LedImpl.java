package com.evian.sqct.dao.impl;

import com.evian.sqct.dao.ILedDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:LedImpl
 * Package:com.evian.sqct.impl
 * Description:led广告
 *
 * @Date:2019/12/11 9:50
 * @Author:XHX
 */
@Repository("ledDao")
public class LedImpl implements ILedDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("vendorTemplate")
    private JdbcTemplate vendorDataSource;

    @Override
    public Map<String, Object> selectLedOrderByOrderId(Integer orderId) {
        String sql = "select orderId, eid, orderNo, mainboardId, mainboardNo, openId, dataSourse, adContent, days, adSpeed, createTime, auditStatus, auditRemark, auditTime, auditor from vendor_ad_led_order_new where orderId = ?";

        Map<String, Object> result = null;
        try {
            result = vendorDataSource.queryForMap(sql, orderId);
        } catch (DataAccessException e) {

        }
        return result;
    }

    @Override
    public Integer updateLedOrderStatus(Integer orderId, Integer auditStatus, String auditRemark, String auditor) {
        String sql = "update vendor_ad_led_order_new set auditStatus=?,auditRemark=?,auditTime=GETDATE(),auditor=? where orderId=?";
        int update = vendorDataSource.update(sql, new Object[]{auditStatus, auditRemark, auditor, orderId});
        return update;
    }

    @Override
    public List<Map<String, Object>> selectLedOrderByEid(Integer eid) {
        String sql = "select orderId, eid, orderNo, mainboardId, mainboardNo, openId, dataSourse, adContent, days, adSpeed, createTime, auditStatus, auditRemark, auditTime, auditor from vendor_ad_led_order_new where eid = ?";
        List<Map<String, Object>> maps = vendorDataSource.queryForList(sql, eid);
        return maps;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_vendor_ad_led_order_new_select(final String beginTime, final String endTime
            ,final Integer orderId, final String orderNo, final Integer eid, final String mainboardNo, final Integer auditStatus, final Integer dataSourse
            , final String shopName, final Boolean isTest, final String nickName, final String openId, final Integer PageIndex
            , final Integer PageSize, final Boolean IsSelectAll) {
        Map<String, Object> result = (Map<String, Object>)vendorDataSource.execute(
                "{call Proc_Backstage_vendor_ad_led_order_new_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("beginTime", beginTime);
                    cs.setObject("endTime", endTime);
                    cs.setObject("orderId", orderId);
                    cs.setObject("orderNo", orderNo);
                    cs.setObject("eid", eid);
                    cs.setObject("mainboardNo", mainboardNo);
                    cs.setObject("auditStatus", auditStatus);
                    cs.setObject("dataSourse", dataSourse);
                    cs.setObject("shopName", shopName);
                    cs.setObject("isTest", isTest);
                    cs.setObject("nickName", nickName);
                    cs.setObject("openId", openId);
                    if(IsSelectAll!=null&&IsSelectAll) {
                        cs.setObject("IsSelectAll", IsSelectAll);
                        cs.setObject("PageIndex", null);
                        cs.setObject("PageSize", null);
                    }else {
                        cs.setObject("IsSelectAll", false);
                        if(PageIndex!=null&&PageSize!=null) {
                            cs.setObject("PageIndex", PageIndex);
                            cs.setObject("PageSize", PageSize);
                        }else {
                            cs.setObject("PageIndex", 1);
                            cs.setObject("PageSize", 20);
                        }
                    }
                    cs.registerOutParameter("Count", Types.INTEGER);// 注册输出参数的类型
                    cs.execute();
                    ResultSet rs = cs.executeQuery();
                    Map<String, Object> map = new HashMap<String, Object>();
                    try {
                        List<Map<String,Object>> resul =  ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        map.put("orders", resul);
                        map.put("Count", cs.getInt("Count"));
                        return map;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }finally{
                        if(rs!=null){
                            rs.close();
                        }
                    }
                });
        return result;
    }
}
