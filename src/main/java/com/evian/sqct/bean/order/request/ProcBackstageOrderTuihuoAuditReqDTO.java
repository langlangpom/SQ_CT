package com.evian.sqct.bean.order.request;

/**
 * ClassName:ProcBackstageOrderTuihuoAuditReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 17:12
 * @Author:XHX
 */
public class ProcBackstageOrderTuihuoAuditReqDTO {

    private Integer orderId;
    private Integer eid;
    private Integer ifaudit;
    private String auditType;
    private String auditRen;
    private Double th_money;
    private String auditBankAccount;
    private String auditremark;
    private String auditUser;
    private Boolean isCheckETicketCombo;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getIfaudit() {
        return ifaudit;
    }

    public void setIfaudit(Integer ifaudit) {
        this.ifaudit = ifaudit;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditRen() {
        return auditRen;
    }

    public void setAuditRen(String auditRen) {
        this.auditRen = auditRen;
    }

    public Double getTh_money() {
        return th_money;
    }

    public void setTh_money(Double th_money) {
        this.th_money = th_money;
    }

    public String getAuditBankAccount() {
        return auditBankAccount;
    }

    public void setAuditBankAccount(String auditBankAccount) {
        this.auditBankAccount = auditBankAccount;
    }

    public String getAuditremark() {
        return auditremark;
    }

    public void setAuditremark(String auditremark) {
        this.auditremark = auditremark;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Boolean getCheckETicketCombo() {
        return isCheckETicketCombo;
    }

    public void setCheckETicketCombo(Boolean checkETicketCombo) {
        isCheckETicketCombo = checkETicketCombo;
    }

    @Override
    public String toString() {
        return "ProcBackstageOrderTuihuoAuditReqDTO [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", ifaudit=" + ifaudit +
                ", auditType=" + auditType +
                ", auditRen=" + auditRen +
                ", th_money=" + th_money +
                ", auditBankAccount=" + auditBankAccount +
                ", auditremark=" + auditremark +
                ", auditUser=" + auditUser +
                ", isCheckETicketCombo=" + isCheckETicketCombo +
                ']';
    }
}
