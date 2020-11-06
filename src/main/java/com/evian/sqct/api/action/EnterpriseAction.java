package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.bean.enterprise.input.ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO;
import com.evian.sqct.exception.ResultException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.response.ResultVO;
import com.evian.sqct.service.BaseEnterpriseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:EnterpriseAction
 * Package:com.evian.sqct.api.action
 * Description:企业Action
 *
 * @Date:2019/10/15 12:04
 * @Author:XHX
 */
@RestController
@RequestMapping("/evian/sqct/enterprise")
public class EnterpriseAction extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseEnterpriseManager baseEnterpriseManager;

    /**
     * 149.企业APP商户端功能申请
     * @param clientId			用户身份ID
     * @return
     */
    @RequestMapping("enterpriseMenuApply.action")
    public ResultVO enterpriseMenuApply(Integer eid, Integer menuId, String applicant, String shuiQuServer) {
        if(eid==null||menuId==null||applicant==null||shuiQuServer==null) {
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }

        String cuidan = baseEnterpriseManager.Proc_Backstage_appMerchant_account_enterprise_menu_apply(eid, menuId, applicant, shuiQuServer);
        if(!"1".equals(cuidan)){
            throw new ResultException(cuidan);
        }

        return new ResultVO();
    }

    /**
     * 214.水叮咚提现管理
     * @param dto
     * @return
     */
    @RequestMapping("SDDWithdrawDeposit.action")
    public Map<String,Object> SDDWithdrawDeposit(ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO dto){
        return baseEnterpriseManager.Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select(dto);
    }


    /**
     * 215.提现汇总日志
     * @param dto
     * @return
     */
    @RequestMapping("SDDWithdrawDepositLog.action")
    public ResultVO SDDWithdrawDepositLog(Integer wangLaiId){
        if(wangLaiId==null){
            return new ResultVO(ResultCode.CODE_ERROR_PARAM);
        }
        List<Map<String, Object>> maps = baseEnterpriseManager.selectEEarningsSubtractWanlaiWithdrawLogByWangLaiId(wangLaiId);
        Map<String,Object> result = new HashMap<>(1);
        result.put("logs",maps);
        return new ResultVO(result);
    }
}
