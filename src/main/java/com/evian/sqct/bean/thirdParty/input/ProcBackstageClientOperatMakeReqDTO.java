package com.evian.sqct.bean.thirdParty.input;

/**
 * ClassName:ProcBackstageClientOperatMakeReqDTO
 * Package:com.evian.sqct.bean.thirdParty.input
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 15:46
 * @Author:XHX
 */
public class ProcBackstageClientOperatMakeReqDTO {

    private String Account;

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public ProcBackstageClientOperatMakeReqDTO() {

    }

    public ProcBackstageClientOperatMakeReqDTO(String account) {
        Account = account;
    }

    @Override
    public String toString() {
        return "ProcBackstageClientOperatMakeReqDTO [" +
                "Account=" + Account +
                ']';
    }
}
