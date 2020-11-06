/**
 * 
 */
package com.evian.sqct.bean.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @FileName: WebConfig.java
 * @Package com.evian.mobile.util
 * @Description: Web配置参数
 * @author EVIAN(PA)
 * @date 2016年3月28日 下午1:16:23
 * @version V3.0
 */
@Component
public class WebConfig implements Serializable {

	private static final long serialVersionUID = -7168427688266035950L;

	@Value("${api.signSwitch}")
	private String signSwitch;// 签名开关

	private Boolean sSwitch; // String 转 Boolean 赋值给它担心会每次调用都执行一次  Boolean.parseBoolean

	@Value("${api.tokenSwitch}")
	private String tokenSwitch;// token开关

	private Boolean tSwitch; // String 转 Boolean 赋值给它担心会每次调用都执行一次  Boolean.parseBoolean

	public boolean getSignSwitch() {
		if(this.sSwitch==null){
			this.sSwitch = Boolean.parseBoolean(signSwitch);
		}
		return sSwitch;
	}

	public void setSignSwitch(String signSwitch) {
		this.signSwitch = signSwitch;
	}

	public boolean getTokenSwitch() {
		if(this.sSwitch==null){
			this.sSwitch = Boolean.parseBoolean(tokenSwitch);
		}
		return this.sSwitch;
	}

	public void setTokenSwitch(String tokenSwitch) {
		this.tokenSwitch = tokenSwitch;
	}

	@Override
	public String toString() {
		return "WebConfig [" +
				"signSwitch=" + signSwitch +
				", tokenSwitch=" + tokenSwitch +
				']';
	}
}
