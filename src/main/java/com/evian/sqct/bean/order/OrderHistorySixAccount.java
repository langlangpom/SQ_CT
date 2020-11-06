package com.evian.sqct.bean.order;

import java.io.Serializable;

/**
 * ClassName:OrderHistorySix
 * Package:com.evian.sqct.bean.order
 * Description:历史订单里的用户openid 和 appid
 *
 * @Date:2020/6/4 17:30
 * @Author:XHX
 */
public class OrderHistorySixAccount implements Serializable {
    private static final long serialVersionUID = -2149692059612486187L;
    private Integer orderId;
    private String appId;
    private String openId;
    private String mchId;					//	支付商户号
    private String partnerKey;				//	支付partnerKey

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "OrderHistorySixAccount [" +
                "orderId=" + orderId +
                ", appId=" + appId +
                ", openId=" + openId +
                ", mchId=" + mchId +
                ", partnerKey=" + partnerKey +
                ']';
    }
}
