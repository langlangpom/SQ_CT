package com.evian.sqct.bean.vendor.write;

/**
 * ClassName:EWechatServicepaySubaccountApplyBanknameReqDTO
 * Package:com.evian.sqct.bean.vendor.write
 * Description:e_wechat_servicepay_subaccount_apply_bankname
 *
 * @Date:2020/9/27 17:10
 * @Author:XHX
 */
public class EWechatServicepaySubaccountApplyBanknameReqDTO {
    private String cft_bank_code;
    private String bankid;
    private String bankname;

    public String getCft_bank_code() {
        return cft_bank_code;
    }

    public void setCft_bank_code(String cft_bank_code) {
        this.cft_bank_code = cft_bank_code;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    @Override
    public String toString() {
        return "EWechatServicepaySubaccountApplyBanknameReqDTO [" +
                "cft_bank_code=" + cft_bank_code +
                ", bankid=" + bankid +
                ", bankname=" + bankname +
                ']';
    }
}
