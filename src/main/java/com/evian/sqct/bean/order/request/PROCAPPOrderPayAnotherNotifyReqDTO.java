package com.evian.sqct.bean.order.request;

/**
 * ClassName:PROCAPPOrderPayAnotherNotifyReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:PROC_APP_Order_PayAnother_Notify
 *
 * @Date:2020/6/17 14:26
 * @Author:XHX
 */
public class PROCAPPOrderPayAnotherNotifyReqDTO {
    private Integer orderId;
    /** 对账单号 */
    private String paymentNo;
    /** 标记(GUID,发起支付携带的扩展参数) */
    private String payTag;
    /** 微信|支付宝 */
    private String paymentPlatform;
    /** 发起支付金额 */
    private Double payMoney;
    /** 支付时间 (yyyy-MM-dd HH:mm:ss) */
    private String paymentTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPayTag() {
        return payTag;
    }

    public void setPayTag(String payTag) {
        this.payTag = payTag;
    }

    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Override
    public String toString() {
        return "PROCAPPOrderPayAnotherNotifyReqDTO [" +
                "orderId=" + orderId +
                ", paymentNo=" + paymentNo +
                ", payTag=" + payTag +
                ", paymentPlatform=" + paymentPlatform +
                ", payMoney=" + payMoney +
                ", paymentTime=" + paymentTime +
                ']';
    }
}
