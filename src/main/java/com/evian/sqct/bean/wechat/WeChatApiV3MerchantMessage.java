package com.evian.sqct.bean.wechat;

/**
 * ClassName:ApiV3MerchantMessage
 * Package:com.evian.sqct.bean.wechat
 * Description:微信apiV3商户信息
 *
 * @Date:2020/9/25 11:28
 * @Author:XHX
 */
public class WeChatApiV3MerchantMessage {

    /** 商户号 */
    private String mchId;
    /** 商户证书序列号 */
    private String mchSerialNo;
    /** api密钥 */
    private String apiV3Key;
    /** 你的商户私钥 */
    private String privateKey;

    public WeChatApiV3MerchantMessage() {
    }

    public WeChatApiV3MerchantMessage(String mchId, String mchSerialNo, String apiV3Key, String privateKey) {
        this.mchId = mchId;
        this.mchSerialNo = mchSerialNo;
        this.apiV3Key = apiV3Key;
        this.privateKey = privateKey;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchSerialNo() {
        return mchSerialNo;
    }

    public void setMchSerialNo(String mchSerialNo) {
        this.mchSerialNo = mchSerialNo;
    }

    public String getApiV3Key() {
        return apiV3Key;
    }

    public void setApiV3Key(String apiV3Key) {
        this.apiV3Key = apiV3Key;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "WeChatApiV3MerchantMessage [" +
                "mchId=" + mchId +
                ", mchSerialNo=" + mchSerialNo +
                ", apiV3Key=" + apiV3Key +
                ", privateKey=" + privateKey +
                ']';
    }
}
