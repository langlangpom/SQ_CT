package com.evian.sqct.api.action;

import com.evian.sqct.api.BaseAction;
import com.evian.sqct.service.BaseEnterpriseManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ClassName:EnterpriseAction
 * Package:com.evian.sqct.api.action
 * Description:请为该功能做描述
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
    public Map<String, Object> enterpriseMenuApply(Integer eid, Integer menuId, String applicant,String shuiQuServer) {
        Map<String, Object> parMap = CallBackPar.getParMap();
        if(eid==null||menuId==null||applicant==null||shuiQuServer==null) {
            int code = Constants.CODE_ERROR_PARAM;
            String message = Constants.getCodeValue(code);
            parMap.put("code", code);
            parMap.put("message", message);
            return parMap;
        }

        String cuidan = baseEnterpriseManager.Proc_Backstage_appMerchant_account_enterprise_menu_apply(eid, menuId, applicant, shuiQuServer);
        if(!"1".equals(cuidan)){
            setCode(parMap, 150);
            setMessage(parMap, cuidan);
        }

        return parMap;
    }
}
