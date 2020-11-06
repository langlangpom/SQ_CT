package com.evian.sqct.bean.order.request;

/**
 * ClassName:PROCAPPOrderPayAnotherReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:PROC_APP_Order_PayAnother
 *
 * @Date:2020/6/17 13:58
 * @Author:XHX
 */
public class PROCAPPOrderPayAnotherReqDTO {
    private Integer orderId;
    /** 支付二维码 */
    private String payCode;
    /** 微信|支付宝 */
    private String paymentPlatform;
    /** 发起支付金额 */
    private Double payMoney;
    /** 标记(GUID,发起支付携带的扩展参数) */
    private String payTag;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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

    public String getPayTag() {
        return payTag;
    }

    public void setPayTag(String payTag) {
        this.payTag = payTag;
    }

    @Override
    public String toString() {
        return "PROCAPPOrderPayAnotherReqDTO [" +
                "orderId=" + orderId +
                ", payCode=" + payCode +
                ", paymentPlatform=" + paymentPlatform +
                ", payMoney=" + payMoney +
                ", payTag=" + payTag +
                ']';
    }
}
