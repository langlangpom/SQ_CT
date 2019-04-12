package com.evian.sqct.inteceptors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.EvianSignature;
import com.evian.sqct.util.RequestUtils;
import com.evian.sqct.util.WebConfig;

/**
 * @date   2018年9月29日 下午2:56:56
 * @author XHX
 * @Description 参数校验拦截器
 */
public class DateCheckInteceptors implements HandlerInterceptor{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!validateRequest_getReplenishmentList(RequestUtils.genMapByRequestParas(request.getParameterMap()))) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", Constants.CODE_ERROR_PARAM);
			map.put("message", Constants.getCodeValue(Constants.CODE_ERROR_PARAM));
			JSONObject result = new JSONObject(map );
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(result.toJSONString());
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	/**
	 * 参数验证
	 */
	private boolean validateRequest_getReplenishmentList(
			Map<String, String> params) throws IOException {
		// 签名开关 
		if(!WebConfig.signSwitch){
			logger.info("签名开关已关");
			return true;
		}

		if (!params.containsKey("sign")
				|| StringUtils.isEmpty(params.get("sign"))) {
			return false;
		}
		
		// 验证签名
		String sign = EvianSignature.getSignature(params, new String(
				EvianSignature.signKey));
		if (!params.get("sign").equals(sign)) {
			logger.info("[用户签名:{}] [正确签名:{}]",new Object[] {params.get("sign"),sign});
			
			return false;
		}
		return true;
	}

}
