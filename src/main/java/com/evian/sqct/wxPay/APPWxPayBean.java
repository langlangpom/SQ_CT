package com.evian.sqct.wxPay;

import java.io.Serializable;

/**
 * ClassName:APPWxPay
 * Package:com.evian.sqct.wxPay
 * Description:app端微信支付实体类
 *
 * @Date:2019/12/11 15:14
 * @Author:XHX
 */
public class APPWxPayBean implements Serializable{

    private static final long serialVersionUID = 3683547188629222381L;

    /** 服务商的APPID*/
    private String appid;
    /** 商户号*/
    private String mch_id;
    /** 子商户应用ID*/
    private String sub_appid;
    /** 子商户号*/
    private String sub_mch_id;
    /** 随机字符串*/
    private String nonce_str;
    /** 签名*/
    private String sign;
    /** 商品描述*/
    private String body;
    /** 附加数据*/
    private String attach;
    /** 商户订单号*/
    private String out_trade_no;
    /** 总金额*/
    private String total_fee;
    /** 终端IP*/
    private String spbill_create_ip;
    /** 通知地址*/
    private String notify_url;
    /** 交易类型*/
    private String trade_type;
    /** 预支付交易会话标识*/
    private String prepay_id;
    /** 支付密钥*/
    private String appKey;
    /** 订单id*/
    private Integer orderId;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "APPWxPayBean [" +
                "appid=" + appid +
                ", mch_id=" + mch_id +
                ", sub_appid=" + sub_appid +
                ", sub_mch_id=" + sub_mch_id +
                ", nonce_str=" + nonce_str +
                ", sign=" + sign +
                ", body=" + body +
                ", attach=" + attach +
                ", out_trade_no=" + out_trade_no +
                ", total_fee=" + total_fee +
                ", spbill_create_ip=" + spbill_create_ip +
                ", notify_url=" + notify_url +
                ", trade_type=" + trade_type +
                ", prepay_id=" + prepay_id +
                ", appKey=" + appKey +
                ", orderId=" + orderId +
                ']';
    }
}
