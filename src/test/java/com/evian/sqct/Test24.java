package com.evian.sqct;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:Test24
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/11/4 10:50
 * @Author:XHX
 */
public class Test24 {

    public static void main(String[] args) {
        Map<String, String> param = new HashMap<>();
        param.put("custId","******");
        String url = "https://reserve.moutai.com.cn/api/rsv-server/anon/consumer/getShops";
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeader("Cookie","lambo-sso-key_0_=0436RMFa1MMfVz0pKKFa1paXa616RMFI#mQMtJNLoZfN7fZHbqLTIfs2bWuloHOjQOTQoXMsYoFk=");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1301.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
            httpPost.setHeader("token","");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("Referer","https://reserve.moutai.com.cn/mconsumer/?a=1&token=0436RMFa1MMfVz0pKKFa1paXa616RMFI\n");
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);

                // 打印请求参数信息
                /*HttpEntity entity2 = httpPost.getEntity();
                String string = EntityUtils.toString(entity2,"utf-8");
                logger.info("--------------entity2 = "+entity2);*/
            }
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
