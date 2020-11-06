package com.evian.sqct.util;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallBackPar {

	public static Map<String,Object> getParMap(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("message", "成功");
		map.put("data", "");
		return map;
	}

	public static String getParString(){
		JSONObject map = new JSONObject();
		map.put("code", 1);
		map.put("message", "成功");
		map.put("data", "");
		return map.toString();
	}

	public static JSONObject getParJSON(){
		JSONObject map = new JSONObject();
		map.put("code", 1);
		map.put("message", "成功");
		map.put("data", "");
		return map;
	}
}
