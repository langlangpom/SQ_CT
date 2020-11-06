package com.evian.sqct.api.action;

import com.evian.sqct.bean.app.APPUpgrader;
import com.evian.sqct.service.BaseVendorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/evian/sqct/upgrade")
public class UpgradeAction{
	private static final Logger logger = LoggerFactory
			.getLogger(UpgradeAction.class);
	
	@Autowired
	BaseVendorManager vendorManager;

	/*
	 * 获取升级的信息
	 */
	@RequestMapping(value = "/getUpgradeDescribe")
	public Map<String, Object> getUpgradeDescribe(String state) {
		StringBuilder sb = new StringBuilder();
		sb.append("水趣商户" + APPUpgrader.getVersionName()+ "\n");
		String strArr[] = APPUpgrader.getDescribe().split("\\$");
		for (String s : strArr) {
			sb.append(" " + s + "\n");
		}
		sb.append("感谢大家的支持与反馈");
		if("html".equals(state)){
			sb = new StringBuilder(APPUpgrader.getDescribe());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionInt", APPUpgrader.getVersionInt());
		map.put("versionName", APPUpgrader.getVersionName());
		map.put("downloadUrl", APPUpgrader.getDownloadUrl());
		map.put("describe", sb);
		return map;
		
	}
}
