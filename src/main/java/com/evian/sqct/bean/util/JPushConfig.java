package com.evian.sqct.bean.util;

/**
 * ClassName:JPushConfig
 * Package:com.evian.sqct.bean.util
 * Description:请为该功能做描述
 *
 * @Date:2019/9/30 11:58
 * @Author:XHX
 */
public class JPushConfig {
    private static String appkey;
    private static String mastersecret;

    public static String getAppkey() {
        return appkey;
    }

    public static void setAppkey(String appkey) {
        JPushConfig.appkey = appkey;
    }

    public static String getMastersecret() {
        return mastersecret;
    }

    public static void setMastersecret(String mastersecret) {
        JPushConfig.mastersecret = mastersecret;
    }
}
