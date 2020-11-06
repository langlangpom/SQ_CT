package com.evian.sqct.bean.order.request;

/**
 * ClassName:ProcBackstageOrderTuihuoAuditTZReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 17:35
 * @Author:XHX
 */
public class ProcBackstageOrderTuihuoAuditTZReqDTO {

    private Integer orderId;
    private Integer eid;
    private Integer ifaudit;
    private String auditremark;

    public ProcBackstageOrderTuihuoAuditTZReqDTO(Integer orderId, Integer eid, Integer ifaudit, String auditremark) {
        this.orderId = orderId;
        this.eid = eid;
        this.ifaudit = ifaudit;
        this.auditremark = auditremark;
    }

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

    public String getAuditremark() {
        return auditremark;
    }

    public void setAuditremark(String auditremark) {
        this.auditremark = auditremark;
    }

    @Override
    public String toString() {
        return "ProcBackstageOrderTuihuoAuditTZReqDTO [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", ifaudit=" + ifaudit +
                ", auditremark=" + auditremark +
                ']';
    }
}
