package com.evian.sqct.util;
import java.util.HashMap;
import java.util.Map;

public class CallBackPar {

	public static Map<String,Object> getParMap(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		map.put("message", "成功");
		return map;
	}
}
