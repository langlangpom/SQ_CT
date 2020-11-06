package com.evian.sqct.bean.order;

import java.io.Serializable;

/**
 * ClassName:ClientPledgeTuiya
 * Package:com.evian.sqct.bean.order
 * Description:e_client_pledge_tuiya
 *
 * @Date:2020/6/4 16:38
 * @Author:XHX
 */
public class ClientPledgeTuiya implements Serializable {

    private static final long serialVersionUID = -6004022090448411457L;

    private Integer orderId;
    private String orderNo;
    private Integer dxid;
    private Integer xid;
    private Integer transferStatus;
    private Integer buyOrderId;
    private Double total;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getDxid() {
        return dxid;
    }

    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Integer getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(Integer buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ClientPledgeTuiya [" +
                "orderId=" + orderId +
                ", orderNo=" + orderNo +
                ", dxid=" + dxid +
                ", xid=" + xid +
                ", transferStatus=" + transferStatus +
                ", buyOrderId=" + buyOrderId +
                ", total=" + total +
                ']';
    }
}
