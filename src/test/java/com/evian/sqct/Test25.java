package com.evian.sqct;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Test24
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/11/4 10:50
 * @Author:XHX
 */
public class Test25 {

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
//        param.put("shopId","MAOTAI1001");
        String url = "https://reserve.moutai.com.cn/api/rsv-server/anon/manage/rimItem/helpBox?shopId=MAOTAI1001";
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpGet httpPost = new HttpGet(url);

            httpPost.setHeader("Cookie","lambo-sso-key_0_=083TzBll2SLFU542wanl2bYcJw2TzBlh#mQMtJNLoZfN7fZHbqLTIfs2bWuloHOjQOTQoXMsYoFk=");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1301.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
            httpPost.setHeader("token","");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("Referer","https://reserve.moutai.com.cn/mconsumer/?a=1&token=083TzBll2SLFU542wanl2bYcJw2TzBlh\n");

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("关闭response失败");
            }
        }

    }
}
