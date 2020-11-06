package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:EWechatServicepaySubaccountApplyInputDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:e_wechat_servicepay_subaccount_apply
 *
 * @Date:2020/9/24 11:45
 * @Author:XHX
 */
public class EWechatServicepaySubaccountApplyInputDTO {
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer eid;
    private String businessCode;
    @NotNull
    private String contactName;
    @NotNull
    private String mobilePhone;
    @NotNull
    private String jsonContent;

    private String creator ="";

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "EWechatServicepaySubaccountApplyInputDTO [" +
                "accountId=" + accountId +
                ", eid=" + eid +
                ", businessCode=" + businessCode +
                ", contactName=" + contactName +
                ", mobilePhone=" + mobilePhone +
                ", jsonContent=" + jsonContent +
                ", creator=" + creator +
                ']';
    }
}
