package com.evian.sqct.bean.order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:TuiyaPayDTO
 * Package:com.evian.sqct.bean.order
 * Description:请为该功能做描述
 *
 * @Date:2020/6/4 16:44
 * @Author:XHX
 */
public class TuiyaPayDTO implements Serializable {
    private static final long serialVersionUID = 3569985453416977990L;

    @NotNull
    private Integer tuiyaOrderId;
    @NotNull
    private Double total;
    @NotNull
    private Integer state;
    @NotNull
    private Integer eid;
    @NotNull
    private String transferOperator;
    private String transferRemark = "";
    @NotNull
    private String ip;



    public Integer getTuiyaOrderId() {
        return tuiyaOrderId;
    }

    public void setTuiyaOrderId(Integer tuiyaOrderId) {
        this.tuiyaOrderId = tuiyaOrderId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "TuiyaPayDTO [" +
                "tuiyaOrderId=" + tuiyaOrderId +
                ", total=" + total +
                ", state=" + state +
                ", eid=" + eid +
                ", transferOperator=" + transferOperator +
                ", transferRemark=" + transferRemark +
                ", ip=" + ip +
                ']';
    }
}
