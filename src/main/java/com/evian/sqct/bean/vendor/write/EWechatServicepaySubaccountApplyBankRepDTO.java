package com.evian.sqct.bean.vendor.write;

import java.io.Serializable;

/**
 * ClassName:EWechatServicepaySubaccountApplyBank
 * Package:com.evian.sqct.bean.vendor.write
 * Description:e_wechat_servicepay_subaccount_apply_bank
 *
 * @Date:2020/9/24 16:44
 * @Author:XHX
 */
public class EWechatServicepaySubaccountApplyBankRepDTO implements Serializable {

    private static final long serialVersionUID = 1275108312769816296L;

    private String CNAPSCode;
    private String bankName;

    public String getCNAPSCode() {
        return CNAPSCode;
    }

    public void setCNAPSCode(String CNAPSCode) {
        this.CNAPSCode = CNAPSCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "EWechatServicepaySubaccountApplyBank [" +
                "CNAPSCode=" + CNAPSCode +
                ", bankName=" + bankName +
                ']';
    }
}
