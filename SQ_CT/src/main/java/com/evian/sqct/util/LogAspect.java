package com.evian.sqct.util;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @date   2018年7月25日 上午10:12:38
 * @author XHX
 * @Description 打印WeiXinService输出的日志 springAOP 环绕增强
 */
@Component
@Aspect
public class LogAspect { 

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	public static boolean logAroundSwitch = true;
	
	/**
	 * 环绕增强
	 * @throws Throwable 
	 * 
	 */
    @Around(value="execution(* com.evian.sqct.service.*.*(..))")
    public Object performance(ProceedingJoinPoint joinPoint) throws Throwable{
    	long action = new Date().getTime();
    	// 环绕前
        Object result=joinPoint.proceed();
        // 环绕后
        if(logAroundSwitch){
        	String methodName = joinPoint.getSignature().getName();
        	long time = new Date().getTime()-action;
        	
        	// 打印日志
        	logger.info("接口返回值:[methodName:{}][result:{}] [time:{}]",new Object[]{methodName,result,time});
        }
        return result;
    }
}
