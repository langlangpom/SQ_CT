package com.evian.sqct.bean.vendor;

/**
 * ClassName:PayParam
 * Package:com.evian.sqct.bean.vendor
 * Description:支付参数
 *
 * @Date:2019/12/16 15:40
 * @Author:XHX
 */
public class PayParam {
    /**
     * 微信app支付appId
     */
    private static String WeChatAppPayAppId;

    /**
     * 微信app支付mch_id
     */
    private static String WeChatAppPayMchId;

    /**
     * 微信app支付key
     */
    private static String WeChatAppPayKey;

    public static String getWeChatAppPayAppId() {
        return WeChatAppPayAppId;
    }

    public static void setWeChatAppPayAppId(String weChatAppPayAppId) {
        WeChatAppPayAppId = weChatAppPayAppId;
    }

    public static String getWeChatAppPayMchId() {
        return WeChatAppPayMchId;
    }

    public static void setWeChatAppPayMchId(String weChatAppPayMchId) {
        WeChatAppPayMchId = weChatAppPayMchId;
    }

    public static String getWeChatAppPayKey() {
        return WeChatAppPayKey;
    }

    public static void setWeChatAppPayKey(String weChatAppPayKey) {
        WeChatAppPayKey = weChatAppPayKey;
    }
}
