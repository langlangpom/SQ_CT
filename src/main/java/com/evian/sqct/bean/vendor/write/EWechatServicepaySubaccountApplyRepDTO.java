package com.evian.sqct.bean.vendor.write;

import java.sql.Date;

/**
 * ClassName:EWechatServicepaySubaccountApplyRepDTO
 * Package:com.evian.sqct.bean.vendor.write
 * Description:请为该功能做描述
 *
 * @Date:2020/9/24 14:13
 * @Author:XHX
 */
public class EWechatServicepaySubaccountApplyRepDTO {

    private Integer accountId;
    private Integer eid;
    private String businessCode;
    private String contactName;
    private String mobilePhone;
    private Integer operatStatus;
    private String jsonContent;
    private Date createTime;
    private String creator;
    private String applyment_id;
    private String applyment_state;
    private String sub_mchid;
    private String sign_url;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

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

    public Integer getOperatStatus() {
        return operatStatus;
    }

    public void setOperatStatus(Integer operatStatus) {
        this.operatStatus = operatStatus;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getApplyment_id() {
        return applyment_id;
    }

    public void setApplyment_id(String applyment_id) {
        this.applyment_id = applyment_id;
    }

    public String getApplyment_state() {
        return applyment_state;
    }

    public void setApplyment_state(String applyment_state) {
        this.applyment_state = applyment_state;
    }

    public String getSub_mchid() {
        return sub_mchid;
    }

    public void setSub_mchid(String sub_mchid) {
        this.sub_mchid = sub_mchid;
    }

    public String getSign_url() {
        return sign_url;
    }

    public void setSign_url(String sign_url) {
        this.sign_url = sign_url;
    }

    @Override
    public String toString() {
        return "EWechatServicepaySubaccountApplyRepDTO [" +
                "accountId=" + accountId +
                ", eid=" + eid +
                ", businessCode=" + businessCode +
                ", contactName=" + contactName +
                ", mobilePhone=" + mobilePhone +
                ", operatStatus=" + operatStatus +
                ", jsonContent=" + jsonContent +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", applyment_id=" + applyment_id +
                ", applyment_state=" + applyment_state +
                ", sub_mchid=" + sub_mchid +
                ", sign_url=" + sign_url +
                ']';
    }
}
