package com.evian.sqct.bean.pay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:PayOnDeliveryByWeCathReqDTO
 * Package:com.evian.sqct.bean.pay
 * Description:请为该功能做描述
 *
 * @Date:2020/6/10 14:12
 * @Author:XHX
 */
public class PayOnDeliveryByWeCathReqDTO implements Serializable {
    private static final long serialVersionUID = 2600170140662111685L;

    @NotNull
    private String appId;
    @NotNull
    private String ip;

    private String orderNo;
    @NotNull
    private Double total;
    @NotNull
    private Integer orderId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PayOnDeliveryByWeCathReqDTO [" +
                "appId=" + appId +
                ", ip=" + ip +
                ", orderNo=" + orderNo +
                ", total=" + total +
                ", orderId=" + orderId +
                ']';
    }
}
