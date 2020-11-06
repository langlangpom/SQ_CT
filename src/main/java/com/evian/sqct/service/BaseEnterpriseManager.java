package com.evian.sqct.service;

import com.evian.sqct.bean.enterprise.input.ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO;
import com.evian.sqct.bean.enterprise.input.ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO;
import com.evian.sqct.bean.sys.EEnterpriseWechatliteapp;
import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ClassName:BaseEnterpriselManager
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2019/10/15 11:58
 * @Author:XHX
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BaseEnterpriseManager extends BaseManager{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("enterpriseDao")
    private IEnterpriseDao enterpriselDao;

    public String Proc_Backstage_appMerchant_account_enterprise_menu_apply(Integer eid,Integer menuId,String applicant,String shuiQuServer){
        return enterpriselDao.Proc_Backstage_appMerchant_account_enterprise_menu_apply(eid, menuId, applicant, shuiQuServer);
    }

    public EEnterpriseWechatliteapp selectEnterpriseEidByAppId(String appId){
        EEnterpriseWechatliteapp eEnterpriseWechatliteapp = enterpriselDao.selectEnterpriseEidByAppId(appId);
        return eEnterpriseWechatliteapp;
    }


    public EEnterpriseWechatliteapp selectEnterpriseGZHAppidByEid(Integer eid){
        try {
            EEnterpriseWechatliteapp eEnterpriseWechatliteapp = enterpriselDao.selectEnterpriseGZHAppidByEid(eid);
            return eEnterpriseWechatliteapp;
        } catch (DataAccessException e) {
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_ENTERPRISE_PAY);
        }
    }

    public EEnterpriseWechatliteapp selectEnterpriseGZHAppletAppidByEid(Integer eid,Integer appType){
        List<EEnterpriseWechatliteapp> eEnterpriseWechatliteapps = enterpriselDao.selectEnterpriseGZHAppletAppidByEid(eid,appType);
        if(eEnterpriseWechatliteapps.size()==0){
            // 未查询到相关数据
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_SDDV3_NO_DATA);
        }
        EEnterpriseWechatliteapp eEnterpriseWechatliteapp = eEnterpriseWechatliteapps.get(0);
        return eEnterpriseWechatliteapp;
    }

    /**
     * 查询拼团小程序
     * @param eid
     * @return
     */
    public EEnterpriseWechatliteapp selectGroupBookingApplet(Integer eid){
        return this.selectEnterpriseGZHAppletAppidByEid(eid,4);
    }

    public Map<String,Object> Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO dto){
        return enterpriselDao.Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(dto);
    }

    public String Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO dto){
        return enterpriselDao.Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat(dto);
    }

    public List<Map<String,Object>> selectEEarningsSubtractWanlaiWithdrawLogByWangLaiId(Integer wangLaiId){
        return enterpriselDao.selectEEarningsSubtractWanlaiWithdrawLogByWangLaiId(wangLaiId);
    }

    /**
     * 企业是否开启自动提现不用审核
     * @param eid
     * @param ids
     * @return
     */
    public Map<String,Object> voluntarilyWithdrawDepositNotAudit(Integer eid){
        return enterpriselDao.Proc_Backstage_enterprise_config_select(eid,"46");
    }

    /**
     * 企业是否开启水叮咚提现审核
     * @param eid
     * @param ids
     * @return
     */
    public Map<String,Object> switchSDDWithdrawDepositAudit(Integer eid){
        return enterpriselDao.Proc_Backstage_enterprise_config_select(eid,"59");
    }
}
