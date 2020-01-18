package com.evian.sqct.service;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * @date   2018年8月16日 上午10:28:41
 * @author XHX
 * @Description 该函数的功能描述
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class BaseJPushManager {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	private String appKey = JPushConfig.getAppKey();
//	
//	private String masterSecret = JPushConfig.getMasterSecret();
	@Value("jpush.appkey")
	private String appKey;
	
	@Value("jpush.mastersecret")
	private String masterSecret;
	
	public void jpushALL(Map<String, String> parm){
		
		JPushClient client = new JPushClient(masterSecret, appKey);
		
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())// 推送平台     
				.setAudience(Audience.registrationId("18171adc035986d1","18171adc035986d197e"))// 推送目标     所有用户
				
				// 通知
				.setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder()
						.setAlert(parm.get("msg"))
						.setBadge(+1)
						.setSound("happy")//这里是设置提示音(更多可以去官网看看)
						.addExtras(parm)
						.build())
						.build())
				// 可选参数
				.setOptions(Options.newBuilder().setApnsProduction(false).build())
				
				// 自定义消息
				.setMessage(Message.newBuilder().setMsgContent(parm.get("msg")).addExtras(parm).build())
				.build();
		
		try {
			PushResult pu = client.sendPush(payload);
			System.out.println(1111+""+pu);
			
			logger.info("[getOriginalContent:{}] [error:{}] [msg_id:{}] [sendno:{}] [statusCode:{}] [getRateLimitQuota:{}] [getRateLimitRemaining:{}] [getRateLimitReset:{}] [getResponseCode:{}]",
					new Object[]{pu.getOriginalContent(),pu.error,pu.msg_id,pu.sendno,pu.statusCode,pu.getRateLimitQuota(),pu.getRateLimitRemaining(),pu.getRateLimitReset(),pu.getResponseCode()});
		} catch (APIConnectionException e) {
			logger.error(e.getMessage());
		} catch (APIRequestException e) {
			logger.error(e.getMessage());
		}finally{
			// 如果不关闭 进程不会退出。
			if(client!=null){
				client.close();
			}
		}
				
	}
}
