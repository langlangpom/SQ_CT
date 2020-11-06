package com.evian.sqct.dao;

import com.evian.sqct.bean.enterprise.EEntererpriseConfig;
import com.evian.sqct.bean.enterprise.EWeixinSendMessageTemplateEnterprise;
import com.evian.sqct.bean.enterprise.ZHDistributionCostsCashOutFromEvian;
import com.evian.sqct.bean.enterprise.input.PROCZHCashOutBackstageSAVEupInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;

import java.util.List;
import java.util.Map;

/**
 * ClassName:IEnterpriseDao
 * Package:com.evian.sqct.dao
 * Description:请为该功能做描述
 *
 * @Date:2019/10/15 11:51
 * @Author:XHX 企业dao
 */
public interface IEnterpriseDao {
    String Proc_Backstage_appMerchant_account_enterprise_menu_apply(Integer eid,Integer menuId,String applicant,String shuiQuServer);

    EEnterpriseWechatliteapp selectEnterpriseEidByAppId(String appId);

    /**
     *
     * 企业参数查询
     * @param eid
     * @return
     */
    Map<String,Object> Proc_Backstage_enterprise_config_select(Integer eid,String ids);

    /**
     * 查询企业配置参数
     * @param eid
     * @param paramId  对应 e_enterprise_config_model 表中 paramId字段
     * @return
     */
    EEntererpriseConfig selectEntererpriseConfig(Integer eid,Integer paramId);

    /** 根据企业id查询企业公众号商户号 */
    EEnterpriseWechatliteapp selectEnterpriseGZHAppidByEid(Integer eid);

    /** 根据企业id查询企业 appType 1.公众号2.小程序4.拼团小程序 */
    List<EEnterpriseWechatliteapp> selectEnterpriseGZHAppletAppidByEid(Integer eid, Integer appType);

    /**
     * 查询企业发送模板id
     * @param eid
     * @param tid
     * @return
     */
    EWeixinSendMessageTemplateEnterprise selectEWeixinSendMessageTemplateEnterpriseByTid(Integer eid,Integer tid);

    Map<String,Object> Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO dto);

    String Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO dto);

    List<Map<String,Object>> selectEEarningsSubtractWanlaiWithdrawLogByWangLaiId(Integer wangLaiId);

    ZHDistributionCostsCashOutFromEvian selectZHDistributionCostsCashOutFromEvianByCashOutID(Integer CashOutID);

    String PROC_ZH_CashOut_Backstage_SAVEup(PROCZHCashOutBackstageSAVEupInputDTO dto);


}
