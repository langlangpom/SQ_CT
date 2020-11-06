package com.evian.sqct.service;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.bean.util.JPushConfig;
import com.evian.sqct.bean.util.JPushShangHuModel;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.util.HttpClientUtilOkHttp;
import com.evian.sqct.util.JacksonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 水趣商户极光推送
 */
@Service
public class JpushShangHuService extends BaseManager{
    private static final Logger logger = LoggerFactory
            .getLogger(JpushShangHuService.class);


    public static boolean iosState = UrlManage.getInteceptorSwitch()==null?false:UrlManage.getInteceptorSwitch();// false:开发环境 true:生成环境

    /**
     * 推送通知
     * type:10000订单 type:10001订单变更(其它售货机区别表名)
     */
    public static Map<String, Object> pushMsg(JPushShangHuModel model) {

        String appKey = JPushConfig.getAppkey();

        String masterSecret = JPushConfig.getMastersecret();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("xid", model.xid);
        resultMap.put("type", model.type);
        resultMap.put("tag", "失败");
        logger.info("[appkey:{}] [appkey.length():{}] [masterSecret:{}] [appkey.length():{}]",new Object[]{appKey,appKey.length(),masterSecret,masterSecret.length()});
        if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(masterSecret)) {
            logger.error("[JpushShangHuService 未配置AppKey,masterSecret xid:{} did:{} title:{} registerId:{}]", new Object[]{
                    model.xid, model.type, model.title, model.registerId});
        }

        //判断是否是百度的userId,百度推送的忽略
        if (!StringUtils.isEmpty(model.registerId)) {
            if (StringUtils.isNumeric(model.registerId)) {
                return resultMap;
            }
        }

        /*try {
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient(masterSecret, appKey,
                    null, clientConfig);
            PushPayload payload = buildPushObject_alertWithTitle(model);
            if (payload != null) {
                PushResult result = jpushClient.sendPush(payload);
                if (result != null && result.msg_id > 0) {
                    resultMap.put("tag", "成功");
                }
                logger.info("[JpushShangHuService PushResult:{}] [xid:{} did:{} title:{} registerId:{}]", new Object[]{
                        result, model.xid, model.type, model.title, model.registerId});
            } else {
                logger.error("[JpushShangHuService PushPayload:{} title:{} registerId:{}]", new Object[]{payload, model.title, model.registerId});
            }
        } catch (Exception ex) {
            logger.error("[exception]", ex);
        }*/

        // 2020-08-13 切换至统一发送兼容多方平台推送接口
        String pushJson =JacksonUtils.obj2json(model);
        String result = pushMsg(pushJson);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if("E00000".equals(code)){
            resultMap.put("tag", "成功");
        }
        return resultMap;
    }

    private static PushPayload buildPushObject_alertWithTitle(JPushShangHuModel model) {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("xid", String.valueOf(model.xid));
        extras.put("type", String.valueOf(model.type));
        extras.put("title", model.title);
        extras.put("sendTime", model.sendTime);
        extras.put("voiceContent", model.voiceContent);

        Builder build = PushPayload.newBuilder();
        if (!StringUtils.isEmpty(model.registerId)) {
            List<String> registerIds = Arrays
                    .asList(new String[]{model.registerId});
            build.setAudience(Audience.registrationId(registerIds));
        } else {
            build.setAudience(Audience.all());
        }

        if (model.platform == 1) {
            // 安卓平台
            return build.setPlatform(Platform.android())
                    .setNotification(Notification.android(model.message, model.title, extras))
                    .build();
        } else if (model.platform == 2) {
            // 苹果平台
            return build
                    .setPlatform(Platform.ios())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(model.message)
                                    .setSound("default")
                                    .setBadge(1)
                                    .setContentAvailable(true)
                                    .addExtras(extras)
                                    .build())
                            .build())
                    .setMessage(Message.content(model.message))
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(iosState)
                            .build())
                    .build();
        } else {
            // 安卓平台、 苹果平台
            return build
                    .setPlatform(Platform.android_ios())
                    .setNotification(
                            Notification
                                    .newBuilder()
                                    .addPlatformNotification(
                                            AndroidNotification
                                                    .newBuilder()
                                                    .setTitle(model.title)
                                                    .setAlert(model.message)
                                                    .addExtras(extras)
                                                    .build())
                                    .addPlatformNotification(
                                            IosNotification.newBuilder()
                                                    .setBadge(1)
                                                    .setSound("default")
                                                    .setAlert(model.message)
                                                    .addExtras(extras)
                                                    .setContentAvailable(true)
                                                    .build()).build())
                    .setMessage(Message.content(model.message))
                    .setOptions(
                            Options.newBuilder().setApnsProduction(iosState)//设置环境
                                    .build()).build();
        }
    }


    /** 水趣推送消息给水趣商户APP  Json */
    private static String pushMsg(String pushJson) {
        List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("pushJson", pushJson));
        String webContent = HttpClientUtilOkHttp.postEvianApi("http://10.16.101.7:18085/Push_Web/evian/push/pushMsg.action", params);
//        webContent = stringCodeExchangeIntCode(webContent);
        return webContent;
    }

}
