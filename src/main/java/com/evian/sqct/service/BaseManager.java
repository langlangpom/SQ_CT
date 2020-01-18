package com.evian.sqct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.util.Constants;

/**
 * @date   2019年8月19日 下午2:01:05
 * @author XHX
 * @Description 公共Server
 */
public class BaseManager {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String stringCodeExchangeIntCode(String webContent) {
		try {
			JSONObject parseObject = JSONObject.parseObject(webContent);
			String code = parseObject.getString("code");
			if("E00000".equals(code)) {
				parseObject.put("code", 1);
			}else {
				parseObject.put("code", Constants.CUSTOM_ERROR);
			}
			webContent = parseObject.toJSONString();
		} catch (Exception e) {
			logger.error(webContent);
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			JSONObject parMap = new JSONObject();
			parMap.put("code", code);
			parMap.put("message", message);
			webContent = parMap.toJSONString();
		}
		return webContent;
	}

	protected String ERROR_SYSTEM(){
		JSONObject parseObject = new JSONObject();
		int code = Constants.CODE_ERROR_SYSTEM;
		String message = Constants.getCodeValue(code);
		parseObject.put("code", code);
		parseObject.put("message", message);
		return parseObject.toJSONString();
	}
}
