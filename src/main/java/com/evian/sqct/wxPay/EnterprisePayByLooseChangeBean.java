package com.evian.sqct.wxPay;

import java.io.Serializable;

/**
 * ClassName:EnterprisePayByLooseChangeBean
 * Package:com.evian.sqct.wxPay
 * Description:企业支付到零钱实体类
 *
 * @Date:2020/5/7 17:09
 * @Author:XHX
 */

public class EnterprisePayByLooseChangeBean implements Serializable {

    private static final long serialVersionUID = 4827078464133767235L;

    private String mch_appid;
    private String mchid;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String partner_trade_no;
    private String openid;
    private String check_name;
    private String re_user_name;
    private String amount;
    private String desc;
    private String spbill_create_ip;
    private String appKey;

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
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

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "EnterprisePayByLooseChangeBean [" +
                "mch_appid=" + mch_appid +
                ", mchid=" + mchid +
                ", device_info=" + device_info +
                ", nonce_str=" + nonce_str +
                ", sign=" + sign +
                ", partner_trade_no=" + partner_trade_no +
                ", openid=" + openid +
                ", check_name=" + check_name +
                ", re_user_name=" + re_user_name +
                ", amount=" + amount +
                ", desc=" + desc +
                ", spbill_create_ip=" + spbill_create_ip +
                ", appKey=" + appKey +
                ']';
    }
}
