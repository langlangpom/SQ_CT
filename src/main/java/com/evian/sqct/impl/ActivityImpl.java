package com.evian.sqct.impl;

import com.evian.sqct.bean.activity.EappMerchantAhareCodeActivityItem;
import com.evian.sqct.bean.activity.EappMerchantShareCodeActivity;
import com.evian.sqct.dao.IActivityDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ActivityImpl
 * Package:com.evian.sqct.impl
 * Description:请为该功能做描述
 *
 * @Date:2019/10/10 15:52
 * @Author:XHX
 */
@Repository("activityDao")
public class ActivityImpl implements IActivityDao{

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<EappMerchantShareCodeActivity> selectEappMerchantShareCodeActivityByEidAndActivityType(Integer eid,Integer activityType) {
        String sql = "select activityId, eid, activityName, activityPic, synopsis, beginDate, endDate, receiveType, maxShareNum, activityExplain, createTime, creator, isEnable, isDel, activityType from e_appMerchant_share_code_activity where eid=? and activityType=? and isEnable=1 and isDel=0";
        List<EappMerchantShareCodeActivity> query = jdbcTemplate.query(sql, new Object[]{eid,activityType}, new BeanPropertyRowMapper<>(EappMerchantShareCodeActivity.class));
        return query;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<EappMerchantShareCodeActivity> Proc_Backstage_appMerchant_share_code_activity_select_forApp(final Integer eid, final Integer activityType, final Integer accountId) {
        List<EappMerchantShareCodeActivity> result = (List<EappMerchantShareCodeActivity>)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_share_code_activity_select_forApp(?,?,?)}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs)
                            throws SQLException {
                        cs.setObject("eid", eid);
                        cs.setObject("activityType", activityType);
                        cs.setObject("accountId", accountId);
                        cs.execute();
                        ResultSet rs = cs.executeQuery();
                        try {
                            List<EappMerchantShareCodeActivity> result = ResultSetToBeanHelper.resultSetToList(rs, EappMerchantShareCodeActivity.class);
                            return result;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return null;
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }
                    }
                });
        return result;
    }

    @Override
    public List<EappMerchantAhareCodeActivityItem> selectEappMerchantAhareCodeActivityItemByActivityId(Integer activityId) {
        String sql = "select  itemId, activityId, codeTypeId, validateDay, beginDate, endDate, createTime, creator, cityId from e_appMerchant_share_code_activity_item where activityId=? ";
        List<EappMerchantAhareCodeActivityItem> query = jdbcTemplate.query(sql, new Object[]{activityId}, new BeanPropertyRowMapper<>(EappMerchantAhareCodeActivityItem.class));
        return query;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map<String, Object>> Proc_Backstage_appMerchant_share_code_activity_item_select(final Integer eid, final Integer activityId) {
        List<Map<String,Object>> result = (List<Map<String,Object>>)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_share_code_activity_item_select(?,?)}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs)
                            throws SQLException {
                        cs.setObject("eid", eid);
                        cs.setObject("activityId", activityId);
                        cs.execute();
                        ResultSet rs = cs.executeQuery();
                        try {
                            List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                            return result;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return null;
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }
                    }
                });
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_appMerchant_share_code_activity_present_record_present(final Integer eid, final Integer activityId, final Integer accountId, final Integer codeTypeId, final String cellphone) {
        Map<String,Object> result = (Map<String,Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_share_code_activity_present_record_present(?,?,?,?,?,?,?)}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs)
                            throws SQLException {
                        cs.setObject("eid", eid);
                        cs.setObject("activityId", activityId);
                        cs.setObject("accountId", accountId);
                        cs.setObject("codeTypeId", codeTypeId);
                        cs.setObject("cellphone", cellphone);
                        cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
                        cs.registerOutParameter("code", Types.NVARCHAR);// 注册输出参数的类型
                        cs.execute();
                        Map<String,Object> map = new HashMap<>();
                        map.put("tag",cs.getString("tag"));
                        map.put("code",cs.getString("code"));
                        return map;
                    }
                });
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_appMerchant_share_code_activity_present_record_select(final String beginTime, final String endTime, final Integer activityId
            , final Integer accountId, final Integer eid, final Integer codeTypeId, final String code, final Boolean isComplete, final Integer clientId, final String cellphone
            , final String staffAccount,final Boolean isSendCellphone, final Integer PageIndex, final Integer PageSize, final Boolean IsSelectAll) {
        Map<String, Object> result = (Map<String, Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_share_code_activity_present_record_select(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs)
                            throws SQLException {
                        cs.setObject("beginTime", beginTime);
                        cs.setObject("endTime", endTime);
                        cs.setObject("activityId", activityId);
                        cs.setObject("accountId", accountId);
                        cs.setObject("eid", eid);
                        cs.setObject("codeTypeId", codeTypeId);
                        cs.setObject("code", code);
                        cs.setObject("isComplete", isComplete);
                        cs.setObject("clientId", clientId);
                        cs.setObject("cellphone", cellphone);
                        cs.setObject("staffAccount", staffAccount);
                        cs.setObject("isSendCellphone", isSendCellphone);
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
                            List<Map<String,Object>> result = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                            map.put("recordList", result);
                            map.put("Count", cs.getInt("Count"));
                            return map;
                        } catch (Exception e) {
                            return null;
                        }finally{
                            if(rs!=null){
                                rs.close();
                            }
                        }
                    }
                });
        return result;
    }

}
