package com.evian.sqct.inteceptors;

import com.evian.sqct.util.CallBackPar;
import com.evian.sqct.util.Constants;
import com.evian.sqct.util.DES.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @date   2019年9月3日 上午9:33:00
 * @author XHX
 * @Description controller层异常捕捉打日志并返回系统异常
 */
@RestControllerAdvice
public class ControllerAdviceException {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(Exception.class)
	public Map<String, Object> runtimeException(Exception e){
		Map<String, Object> parMap = CallBackPar.getParMap();
		logger.error("[project:{}] [exception:{}]", new Object[] {
				WebConfig.projectName, e });
		int code = Constants.CODE_ERROR_SYSTEM;
		String message = Constants.getCodeValue(code);
		parMap.put("code", code);
		parMap.put("message", message);
		return parMap;
	}
}
