package com.evian.sqct.impl;

import com.evian.sqct.dao.IEnterpriseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public String Proc_Backstage_appMerchant_account_enterprise_menu_apply(final Integer eid, final Integer menuId, final String applicant, final String shuiQuServer) {
        String result = (String)jdbcTemplate.execute(
                "{call Proc_Backstage_appMerchant_account_enterprise_menu_apply(?,?,?,?,?)}",
                new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs)
                            throws SQLException {
                        cs.setObject("eid", eid);
                        cs.setObject("menuId", menuId);
                        cs.setObject("applicant", applicant);
                        cs.setObject("shuiQuServer", shuiQuServer);
                        cs.registerOutParameter("tag", Types.NVARCHAR);// 注册输出参数的类型
                        cs.execute();
                        return cs.getString("tag");
                    }
                });
        return result;
    }
}
