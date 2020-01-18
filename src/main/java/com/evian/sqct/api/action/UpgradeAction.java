package com.evian.sqct.api.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evian.sqct.bean.app.APPUpgrader;
import com.evian.sqct.service.BaseVendorManager;
import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;

@RestController
@RequestMapping("/evian/sqct/upgrade")
public class UpgradeAction{
	private static final Logger logger = LoggerFactory
			.getLogger(UpgradeAction.class);
	
	@Autowired
	BaseVendorManager vendorManager;
	
	/*
	@Value("${vendor.versionInt}")
	private String versionInt;
	
	@Value("${vendor.versionName}")
	private String versionName;
	
	@Value("${vendor.downloadUrl}")
	private String downloadUrl;
	
	@Value("${vendor.describe}")
	private String describe;*/
	
	/*
	 * 获取升级的信息
	 */
//	@RequestMapping(value = "/getUpgradeDescribe")
	/*public Map<String, Object> getUpgradeDescribe(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("水趣商户" + versionName+ "\n");
			String strArr[] = describe.split("\\$");
			for (String s : strArr) {
				sb.append(" " + s + "\n");
			}
			sb.append("感谢大家的支持与反馈");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionInt", versionInt);
			map.put("versionName", versionName);
			map.put("downloadUrl", downloadUrl);
			map.put("describe", sb);
			parMap.put("data", map);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
		
	}*/
	

	/*
	 * 获取升级的信息
	 */
	@RequestMapping(value = "/getUpgradeDescribe")
	public Map<String, Object> getUpgradeDescribe_v2(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> parMap = CallBackPar.getParMap();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("水趣商户" + APPUpgrader.getVersionName()+ "\n");
			String strArr[] = APPUpgrader.getDescribe().split("\\$");
			for (String s : strArr) {
				sb.append(" " + s + "\n");
			}
			sb.append("感谢大家的支持与反馈");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("versionInt", APPUpgrader.getVersionInt());
			map.put("versionName", APPUpgrader.getVersionName());
			map.put("downloadUrl", APPUpgrader.getDownloadUrl());
			map.put("describe", sb);
			parMap.put("data", map);
		} catch (Exception e) {
			logger.error("[project:{}] [exception:{}]", new Object[] {
					WebConfig.projectName, e });
			int code = Constants.CODE_ERROR_SYSTEM;
			String message = Constants.getCodeValue(code);
			parMap.put("code", code);
			parMap.put("message", message);
		}
		return parMap;
		
	}
}
