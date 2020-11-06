package com.evian.sqct.inteceptors;

import com.evian.sqct.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 参数日志拦截器
 * 记录每个接口获取的参数
 * 
 * @author XHX
 *
 */
@Component
public class LoggerParamsInteceptor implements HandlerInterceptor{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * controller 执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long start = System.currentTimeMillis();
		// 获取全部请求参数[accont,pass,sign]
		Map<String, String> params = RequestUtils.genMapByRequestParas(request.getParameterMap());
		HandlerMethod handlerMethod =(HandlerMethod) handler;
		logger.info("[project:{}] [method:{}] [ip:{}] [params:{}] [start:{}]",
				new Object[] { "SQ_CT", handlerMethod.getMethod().getName(),RequestUtils.getRemoteAddr(request), params, start });
		return true;
	}

	/**
     * 页面渲染之后调用，一般用于资源清理操作
     */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
     * controller 执行之后，且页面渲染之前调用
     */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	
}







