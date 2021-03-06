package com.evian.sqct.api;

import com.evian.sqct.response.ResultCode;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @date   2018年9月29日 下午4:10:29
 * @author XHX
 * @Description 该函数的功能描述
 */
public class BaseAction {

	/**
	 * 获取客户端IP地址，支持proxy
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @return IP地址
	 */
	public String getIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip)) {
			String[] ips = StringUtils.split(ip, ',');
			if (ips != null) {
				for (String tmpip : ips) {
					if (StringUtils.isBlank(tmpip)) {
						continue;
					}
					tmpip = tmpip.trim();
					if (isIPAddr(tmpip) && !tmpip.startsWith("10.")
							&& !tmpip.startsWith("192.168.")
							&& !"127.0.0.1".equals(tmpip)) {
						return tmpip.trim();
					}
				}
			}
		}
		ip = req.getHeader("x-real-ip");
		if(isIPAddr(ip))
			return ip;
		ip = req.getRemoteAddr();
		if (ip.indexOf('.') == -1)
			ip = "127.0.0.1";
		return ip;
	}
	
	/**
	 * 判断字符串是否是一个IP地址
	 * 
	 * @param addr
	 *            字符串
	 * @return true:IP地址，false：非IP地址
	 */
	private boolean isIPAddr(String addr) {
		if (StringUtils.isEmpty(addr))
			return false;
		String[] ips = StringUtils.split(addr, '.');
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
					&& ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}
	
	
	protected void setCode(Map<String, Object> resultMap,Integer code) {
		resultMap.put("code", code);
	}
	
	protected void setMessage(Map<String, Object> resultMap,String message) {
		resultMap.put("message", message);
	}
	
	protected Map<String, Object> setData(Map<String, Object> resultMap,Object data) {
		resultMap.put("data", data);
		return resultMap;
	}

	/**
	 * 返回系统异常
	 * @param resultMap
	 */
	protected Map<String, Object> setERROR_SYSTEM(Map<String, Object> resultMap){
		int code = ResultCode.CODE_ERROR_SYSTEM.getCode();
		String message = ResultCode.CODE_ERROR_SYSTEM.getMessage();
		resultMap.put("code", code);
		resultMap.put("message", message);
		return resultMap;
	}
	/**
	 * 返回参数错误
	 * @param resultMap
	 */
	protected Map<String, Object> setERROR_PARAM(Map<String, Object> resultMap){
		int code = ResultCode.CODE_ERROR_SYSTEM.getCode();
		String message = ResultCode.CODE_ERROR_SYSTEM.getMessage();
		resultMap.put("code", code);
		resultMap.put("message", message);
		return resultMap;
	}
	/**
	 * 返回参数错误
	 * @param resultMap
	 */
	protected String setERROR_PARAM(String resultString){
		int code = ResultCode.CODE_ERROR_SYSTEM.getCode();
		String message = ResultCode.CODE_ERROR_SYSTEM.getMessage();
		JSONObject result = JSONObject.fromObject(resultString);
		result.put("code", code);
		result.put("message", message);
		return result.toString();
	}
	/**
	 * 返回150自定义错误
	 * @param resultMap
	 */
	protected Map<String, Object> setERROR_150(Map<String, Object> resultMap,String message){
		int code = ResultCode.CUSTOM_CODE;
		resultMap.put("code", code);
		resultMap.put("message", message);
		return resultMap;
	}
	/**
	 * 根据code返回错误
	 * @param resultMap
	 */
	protected Map<String, Object> setERROR_BY_CODE(Map<String, Object> resultMap,int code){
		String message = ResultCode.CODE_ERROR_SYSTEM.getMessage();
		resultMap.put("code", code);
		resultMap.put("message", message);
		return resultMap;
	}
}
