package com.evian.sqct.util;

import com.evian.sqct.bean.sys.AccessTokenAndJsTicket;
import com.evian.sqct.bean.vendor.UrlManage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @date   2018年8月2日 下午4:13:20
 * @author XHX
 * @Description 微信tonken和js ticket缓存类
 */
public class WxTokenAndJsticketCache {

	private static final Logger logger = LoggerFactory.getLogger(WxTokenAndJsticketCache.class);
	
	public static Map<String, AccessTokenAndJsTicket> accessTokenAndJsTicketMap;
	
	public static String getAccess_token(String appId) {
		
		if(accessTokenAndJsTicketMap==null) {
			accessTokenAndJsTicketMap = new  HashMap<String,AccessTokenAndJsTicket>();
		}
		
//		long now = StrUtils.getWebsiteDateLong();
		long now = System.currentTimeMillis();
		
		Long newDateLong = now; // 当前时间
		AccessTokenAndJsTicket entity = null;
		if(accessTokenAndJsTicketMap.get(appId)==null) {
			entity = new AccessTokenAndJsTicket();
			entity.setAppId(appId);
			accessTokenAndJsTicketMap.put(appId, entity);
		}
		
		entity = accessTokenAndJsTicketMap.get(appId);
		
		
		if(entity.getTokenTimeout()==null||newDateLong>=entity.getTokenTimeout()) {
			String url=UrlManage.getAdminWebUrl() + "/WeiXin/ShowWxToken?appId="+entity.getAppId();
			String webContent = HttpClientUtil.post(url, "为了不返回411错误，这里不能为空");
			
			// 两小时后过期 稍微提前200秒
			Long timeOut = now+7000*1000;
			
			logger.info(entity.getAppId() + " 获取微信token, URL: "+ url + "   webContent: " + webContent+"  过期时间: "+timeOut);
			if (!StringUtils.isEmpty(webContent) && webContent.replace("|@|", "。").split("。").length == 2) {
				entity.setToken(webContent.replace("|@|", "。").split("。")[0]);
				entity.setTokenTimeout(timeOut);
			}else {
				logger.error("获取token失败:{}",webContent);
			}
			accessTokenAndJsTicketMap.put(appId, entity);
		}
		
		return entity.getToken();
	}
	public static String getJsapi_ticket(String appId) {
		if(accessTokenAndJsTicketMap==null) {
			getAccess_token(appId);
		}
		
		AccessTokenAndJsTicket entity = null;
		
		if(accessTokenAndJsTicketMap.get(appId)==null) {
			getAccess_token(appId);
		}
		
		entity = accessTokenAndJsTicketMap.get(appId);
		
//		long now = StrUtils.getWebsiteDateLong();// 当前时间
		long now = System.currentTimeMillis();
		
		Long newDateLong = now; // 当前时间
		
		if(entity.getTokenTimeout()==null||newDateLong>=entity.getTokenTimeout()) {
			getAccess_token(appId);
			entity = accessTokenAndJsTicketMap.get(appId);
		}
			
		if(entity.getJsTicketTimeout()==null||newDateLong>=entity.getJsTicketTimeout()) {
			String url=UrlManage.getAdminWebUrl() + "/WeiXin/ShowWxJsapiTicket?appId="+entity.getAppId();
			String webContent = HttpClientUtil.post(url, "为了不返回411错误，这里不能为空");
			
			// 两小时后过期 稍微提前200秒
			Long timeOut = now+7000*1000;
			logger.info(entity.getAppId() + " 获取微信ticket, URL: "+ url + "   webContent: " + webContent+"  过期时间: "+timeOut);
			if (!StringUtils.isEmpty(webContent) && webContent.replace("|@|", "。").split("。").length == 2) {
				entity.setJsTicket(webContent.replace("|@|", "。").split("。")[0]);
				entity.setJsTicketTimeout(timeOut);
			}else {
				logger.error("获取ticket失败:{}",webContent);
			}
			accessTokenAndJsTicketMap.put(appId, entity);
		}
		
		return entity.getJsTicket();
	}
	
	
	static String component_access_token;
	static Timestamp component_access_tokenTimeOut;
	static Boolean isChangecomponent_access_token;

	public static String getComponent_access_token() {
		
		if (component_access_token == null || component_access_tokenTimeOut == null || isChangecomponent_access_token == null ||
				StringUtils.isEmpty(component_access_token) || isChangecomponent_access_token || new Timestamp(System.currentTimeMillis()).after(component_access_tokenTimeOut)) {
			String url=UrlManage.getAdminWebUrl()+"/ComponentLiteapp/ShowComponentAccessToken";;
			String webContent = HttpClientUtil.post(url, "为了不返回411错误，这里不能为空");
			
			logger.info("获取第三方平台Component_access_token, URL: "+ url + "   webContent: " + webContent);
			if (!StringUtils.isEmpty(webContent) && webContent.replace("|@|", "。").split("。").length == 2) {
				component_access_token = webContent.replace("|@|", "。").split("。")[0];
				component_access_tokenTimeOut = Timestamp.valueOf(webContent.replace("|@|", "。").split("。")[1].replace("/", "-"));
				isChangecomponent_access_token = false;
			}
		}
		//System.out.println("jsapi_ticket:"+jsapi_ticket);
		return component_access_token;
	}

	public static Boolean setIsChangeComponent_access_token() {
		return isChangecomponent_access_token = true;
	}
}
