package com.evian.sqct.impl;

import com.evian.sqct.dao.ILedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
