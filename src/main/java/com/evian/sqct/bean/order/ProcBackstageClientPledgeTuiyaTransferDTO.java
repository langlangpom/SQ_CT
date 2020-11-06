package com.evian.sqct.bean.order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:ProcBackstageClientPledgeTuiyaTransferDTO
 * Package:com.evian.sqct.bean.order
 * Description:Proc_Backstage_client_pledge_tuiya_transfer
 *
 * @Date:2020/6/3 17:19
 * @Author:XHX
 */
public class ProcBackstageClientPledgeTuiyaTransferDTO implements Serializable {
    private static final long serialVersionUID = -7413982443954641472L;
    @NotNull
    private Integer eid;
    @NotNull
    private Integer dxid;
    @NotNull
    private Integer xid;
    @NotNull
    private Integer transferStatus;
    @NotNull
    private String transferOperator;
    @NotNull
    private String transferRemark;
    @NotNull
    private String orderNo;
    private String paymentNo;
    private String payResult;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
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

    public String getTransferOperator() {
        return transferOperator;
    }

    public void setTransferOperator(String transferOperator) {
        this.transferOperator = transferOperator;
    }

    public String getTransferRemark() {
        return transferRemark;
    }

    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    @Override
    public String toString() {
        return "ProcBackstageClientPledgeTuiyaTransferDTO [" +
                "eid=" + eid +
                ", dxid=" + dxid +
                ", xid=" + xid +
                ", transferStatus=" + transferStatus +
                ", transferOperator=" + transferOperator +
                ", transferRemark=" + transferRemark +
                ", orderNo=" + orderNo +
                ", paymentNo=" + paymentNo +
                ", payResult=" + payResult +
                ']';
    }
}
