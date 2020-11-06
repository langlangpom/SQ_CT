package com.evian.sqct.bean.order.request;

import com.evian.sqct.bean.baseBean.PagingPojo;

import java.sql.Date;

/**
 * ClassName:ProcBackstageOrderTuihuoSelectReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 16:50
 * @Author:XHX
 */
public class ProcBackstageOrderTuihuoSelectReqDTO extends PagingPojo {
    private Integer orderId;
    private Integer eid;
    private String eName;
    private String orderNo;
    private String account;
    private String th_type;
    private String paymentPlatform;
    private Date auditBeginTime;
    private Date auditendTime;
    private Integer ifaudit;

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

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTh_type() {
        return th_type;
    }

    public void setTh_type(String th_type) {
        this.th_type = th_type;
    }

    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public Date getAuditBeginTime() {
        return auditBeginTime;
    }

    public void setAuditBeginTime(Date auditBeginTime) {
        this.auditBeginTime = auditBeginTime;
    }

    public Date getAuditendTime() {
        return auditendTime;
    }

    public void setAuditendTime(Date auditendTime) {
        this.auditendTime = auditendTime;
    }

    public Integer getIfaudit() {
        return ifaudit;
    }

    public void setIfaudit(Integer ifaudit) {
        this.ifaudit = ifaudit;
    }

    @Override
    public String toString() {
        return "ProcBackstageOrderTuihuoSelectReqDTO [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", eName=" + eName +
                ", orderNo=" + orderNo +
                ", account=" + account +
                ", th_type=" + th_type +
                ", paymentPlatform=" + paymentPlatform +
                ", auditBeginTime=" + auditBeginTime +
                ", auditendTime=" + auditendTime +
                ", ifaudit=" + ifaudit +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
