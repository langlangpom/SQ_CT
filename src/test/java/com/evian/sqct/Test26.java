package com.evian.sqct;

import com.evian.sqct.util.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * ClassName:Test26
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/11/6 17:15
 * @Author:XHX
 */
public class Test26 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String decode = URLDecoder.decode("https%3A%2F%2Freserve.moutai.com.cn%2Fapi%2Frsv-server%2Fanon%2Fwechat%2Fauth2%2Fwxca6a32cf7a967782%3Frdurl%3Dhttps%3A%2F%2Freserve.moutai.com.cn%2Fmconsumer%2F%3Fa%3D1%23%2Ftabs%2Fapply&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect", "UTF-8");
//        System.out.println(decode);
        String s = HttpClientUtil.doGet("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxca6a32cf7a967782&redirect_uri=https%3A%2F%2Freserve.moutai.com.cn%2Fapi%2Frsv-server%2Fanon%2Fwechat%2Fauth2%2Fwxca6a32cf7a967782%3Frdurl%3Dhttps%3A%2F%2Freserve.moutai.com.cn%2Fmconsumer%2F%3Fa%3D1%23%2Ftabs%2Fapply&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        System.out.println(s);
    }
}
