package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * ClassName:EPaySubAccount
 * Package:com.evian.sqct.bean.vendor
 * Description:e_pay_sub_account
 *
 * @Date:2020/9/22 14:57
 * @Author:XHX
 */
public class EPaySubAccount {

    private Integer subAccountId;
    @NotNull
    private Integer eid;
    @NotNull
    private String businessCode;
    @NotNull
    private String contactName;
    @NotNull
    private String identityCode;
    @NotNull
    private String mobilePhone;
    @NotNull
    private String contactEmail;
    @NotNull
    private String subjectType;
    @NotNull
    private String idDocType;
    @NotNull
    private Boolean owner;
    @NotNull
    private String merchantShortname;
    @NotNull
    private String servicePhone;
    @NotNull
    private String salesScenesType;
    @NotNull
    private String settlementId;
    @NotNull
    private String settlementIdName;
    @NotNull
    private String qualificationType;
    private Date createTime;
    @NotNull
    private String creator;
    private Integer operatStatus;
    private String applyment_id;

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
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

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getIdDocType() {
        return idDocType;
    }

    public void setIdDocType(String idDocType) {
        this.idDocType = idDocType;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public String getMerchantShortname() {
        return merchantShortname;
    }

    public void setMerchantShortname(String merchantShortname) {
        this.merchantShortname = merchantShortname;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getSalesScenesType() {
        return salesScenesType;
    }

    public void setSalesScenesType(String salesScenesType) {
        this.salesScenesType = salesScenesType;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String getSettlementIdName() {
        return settlementIdName;
    }

    public void setSettlementIdName(String settlementIdName) {
        this.settlementIdName = settlementIdName;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }

    public Integer getOperatStatus() {
        return operatStatus;
    }

    public void setOperatStatus(Integer operatStatus) {
        this.operatStatus = operatStatus;
    }

    public String getApplyment_id() {
        return applyment_id;
    }

    public void setApplyment_id(String applyment_id) {
        this.applyment_id = applyment_id;
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

    @Override
    public String toString() {
        return "EPaySubAccount [" +
                "subAccountId=" + subAccountId +
                ", eid=" + eid +
                ", businessCode=" + businessCode +
                ", contactName=" + contactName +
                ", identityCode=" + identityCode +
                ", mobilePhone=" + mobilePhone +
                ", contactEmail=" + contactEmail +
                ", subjectType=" + subjectType +
                ", idDocType=" + idDocType +
                ", owner=" + owner +
                ", merchantShortname=" + merchantShortname +
                ", servicePhone=" + servicePhone +
                ", salesScenesType=" + salesScenesType +
                ", settlementId=" + settlementId +
                ", settlementIdName=" + settlementIdName +
                ", qualificationType=" + qualificationType +
                ", operatStatus=" + operatStatus +
                ", applyment_id=" + applyment_id +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ']';
    }
}
