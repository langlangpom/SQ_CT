package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.enterprise.EEntererpriseConfig;
import com.evian.sqct.bean.enterprise.EWeixinSendMessageTemplateEnterprise;
import com.evian.sqct.bean.enterprise.ZHDistributionCostsCashOutFromEvian;
import com.evian.sqct.bean.enterprise.input.PROCZHCashOutBackstageSAVEupInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.dao.IBaseDao;
import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.util.DaoUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * ClassName:EnterpriseImpl
 * Package:com.evian.sqct.impl
 * Description:请为该功能做描述
 *
 * @Date:2019/10/15 11:54
 * @Author:XHX
 */
@Repository("enterpriseDao")
public class EnterpriseImpl implements IEnterpriseDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IBaseDao baseDao;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public String Proc_Backstage_appMerchant_account_enterprise_menu_apply(final Integer eid, final Integer menuId, final String applicant, final String shuiQuServer) {
        String result = (String)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_account_enterprise_menu_apply(?,?,?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("eid", eid);
                    cs.setObject("menuId", menuId);
                    cs.setObject("applicant", applicant);
                    cs.setObject("shuiQuServer", shuiQuServer);
                    cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
                    cs.execute();
                    return cs.getString("tag");
                });
        return result;
    }

    @Override
    public EEnterpriseWechatliteapp selectEnterpriseEidByAppId(String appId) {
        String sql = "select appId, eid, liteappType, authorizerRefreshToken,appType, createTime, liteappName, appSecret, mchId, partnerKey, isValid from e_enterprise_wechatliteapp where appId=?";
        try {
            EEnterpriseWechatliteapp query = jdbcTemplate.queryForObject(sql, new Object[]{appId}, new BeanPropertyRowMapper<>(EEnterpriseWechatliteapp.class));
            return query;
        } catch (DataAccessException e) {
            return null;
        }

    }

    /**
     * 企业是否开启自动提现不用审核
     * @param eid
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Map<String, Object> Proc_Backstage_enterprise_config_select(Integer eid,String ids) {
        Map<String,Object> result = (Map<String,Object>)jdbcTemplate.execute(
                "{call Proc_Backstage_enterprise_config_select(?,?,?)}",
                (CallableStatementCallback) cs -> {
                    cs.setObject("ids", ids);
                    cs.setObject("paramName", null);
                    cs.setObject("eid", eid);
                    cs.execute();
                    ResultSet rs = null;
                    try {

                        rs = cs.executeQuery();

                        List<Map<String,Object>> resul = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                        if(resul.size()>0){
                            return resul.get(0);
                        }
                        return null;
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

    /**
     * 查询企业配置参数
     * @param eid
     * @param paramId  对应 e_enterprise_config_model 表中 paramId字段
     * @return
     */
    @Override
    public EEntererpriseConfig selectEntererpriseConfig(Integer eid, Integer paramId) {
        try {
            String sql = "select paramId, eid, paramKey, paramValue, paramStatus, createTime, isTimeLimit, beginTime, endTime from e_enterprise_config where eid=? and paramId=?";
            return jdbcTemplate.queryForObject(sql,new Object[]{eid,paramId},new BeanPropertyRowMapper<>(EEntererpriseConfig.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据企业id查询企业公众号商户号
     *
     * @param eid
     */
    @Override
    public EEnterpriseWechatliteapp selectEnterpriseGZHAppidByEid(Integer eid) {
        String sql = "select appId, eid, liteappType, authorizerRefreshToken,appType, createTime, liteappName, appSecret, mchId, partnerKey, isValid from e_enterprise_wechatliteapp where eid=? and appType=1";
        EEnterpriseWechatliteapp query = jdbcTemplate.queryForObject(sql, new Object[]{eid}, new BeanPropertyRowMapper<>(EEnterpriseWechatliteapp.class));
        return query;
    }

    /**
     * 根据企业id查询企业 appType 1.公众号2.小程序4.拼团小程序
     *
     * @param eid
     * @param appType
     */
    @Override
    public List<EEnterpriseWechatliteapp> selectEnterpriseGZHAppletAppidByEid(Integer eid, Integer appType) {
        String sql = "select appId, eid, liteappType, authorizerRefreshToken,appType, createTime, liteappName, appSecret, mchId, partnerKey, isValid from e_enterprise_wechatliteapp where eid=? and appType=?";
        return jdbcTemplate.query(sql, new Object[]{eid,appType}, new BeanPropertyRowMapper<>(EEnterpriseWechatliteapp.class));
    }

    /**
     * 查询企业发送模板id
     *
     * @param eid
     * @param tid
     * @return
     */
    @Override
    public EWeixinSendMessageTemplateEnterprise selectEWeixinSendMessageTemplateEnterpriseByTid(Integer eid, Integer tid) {
        String sql = "select eid, tid, templateId, createTime from e_weixin_send_message_template_enterprise where eid=? and tid=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{eid,tid},new BeanPropertyRowMapper<>(EWeixinSendMessageTemplateEnterprise.class));
    }

    @Override
    public Map<String, Object> Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO dto) {
        Map result = baseDao.agencyDB("Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select", dto);
        return DaoUtil.resultRename(result,"managementList");
    }

    @Override
    public String Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO dto) {
        Map result = baseDao.agencyDB("Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat", dto);
        return DaoUtil.resultTAG(result);
    }

    @Override
    public List<Map<String, Object>> selectEEarningsSubtractWanlaiWithdrawLogByWangLaiId(Integer wangLaiId) {
        String sql = "select id, wangLaiId, appId, openId, paymentNo, payMoney, result, remark, createTime, createUser, logType from e_earnings_subtract_wanlai_withdraw_log where wangLaiId=? and logType=2 order by createTime desc ";
        return jdbcTemplate.queryForList(sql,wangLaiId);
    }

    @Override
    public ZHDistributionCostsCashOutFromEvian selectZHDistributionCostsCashOutFromEvianByCashOutID(Integer CashOutID) {
        String sql = "select a.CashOutID, a.evian_xid, a.eid, b.clientId, a.ShopID, a.sAccount, a.nCashOut, a.CashOutNO, a.createDate, a.PayDate, a.ifPayment, a.iStatus,a.paymentNo, a.ipaymentPlatform, a.Remark, a.SH_datetime, a.SH_UserAccount, a.SH_remark, a.APPUserName,a.nCashOutPayAmount, a.MD_ID, a.MD_Name, a.MD_ZhangHu, a.MD_ShouKuanZhangHao, a.ZY_ID, a.MD_ShenQingRen from ZH_DistributionCosts_CashOut_fromEvian a inner join e_client b on a.sAccount=b.account where a.CashOutID=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{CashOutID},new BeanPropertyRowMapper<>(ZHDistributionCostsCashOutFromEvian.class));
    }

    @Override
    public String PROC_ZH_CashOut_Backstage_SAVEup(PROCZHCashOutBackstageSAVEupInputDTO dto) {
        Map result = baseDao.agencyDB("PROC_ZH_CashOut_Backstage_SAVEup", dto);
        return DaoUtil.resultTAG(result);
    }
}
