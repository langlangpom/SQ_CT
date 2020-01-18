package com.evian.sqct.service;

import com.evian.sqct.dao.IEnterpriseDao;
import com.evian.sqct.util.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        logger.info("[project:{}] [step:enter] [eid:{}] [menuId:{}] [applicant:{}] [shuiQuServer:{}]",
                new Object[] { WebConfig.projectName,eid,menuId,applicant,shuiQuServer});
        return enterpriselDao.Proc_Backstage_appMerchant_account_enterprise_menu_apply(eid, menuId, applicant, shuiQuServer);
    }
}
