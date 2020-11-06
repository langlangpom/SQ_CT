package com.evian.sqct.util;

import com.evian.sqct.annotation.DataNotLogCheck;
import com.evian.sqct.annotation.InParamNotLogCheck;
import com.evian.sqct.annotation.LogNotCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
    @Around(value="execution(* com.evian.sqct.service.Base*.*(..))||execution(* com.evian.sqct.service.I*.*(..))")
    public Object performance(ProceedingJoinPoint joinPoint) throws Throwable{
		long action = System.currentTimeMillis();

		// 日志打印方法的传参
		recordIntroductionParam(joinPoint);
		// 环绕前
        Object result=joinPoint.proceed();
        // 环绕后
        if(logAroundSwitch){
        	// 日志打印返回值
			recordReturnValues(joinPoint, action, result);
        }
        return result;
    }

	/**
	 * 日志打印返回值
	 * @param joinPoint
	 * @param action 方法开始执行的开始时间
	 * @param result 返回值
	 * @return
	 */
    private void recordReturnValues(ProceedingJoinPoint joinPoint,long action,Object result){
		try {
			Signature signature = joinPoint.getSignature();
			String methodName = signature.getName();
			Class<?> classTarget = joinPoint.getTarget().getClass();
			Class<?>[] par=((MethodSignature) signature).getParameterTypes();
			Method objMethod=classTarget.getMethod(methodName, par);
			LogNotCheck logNotcheck = objMethod.getAnnotation(LogNotCheck.class);
			// 不进行入参和返回值日志记录
			if(logNotcheck!=null&&logNotcheck.validate()){
				return ;
			}
			DataNotLogCheck dataNotLogCheck = objMethod.getAnnotation(DataNotLogCheck.class);
			// 不进行记录返回值
			if(dataNotLogCheck!=null&&dataNotLogCheck.validate()){
				return ;
			}

			long time = System.currentTimeMillis()-action;

			// 打印日志
			logger.info("接口返回值:[methodName:{}][time:{}][result:{}]",new Object[]{methodName,time,result});
		} catch (NoSuchMethodException e) {

		}
	}

	/**
	 * 日志打印方法的传参
	 * @param joinPoint
	 */
	private void recordIntroductionParam(ProceedingJoinPoint joinPoint){
		try {
			Signature signature = joinPoint.getSignature();
			String methodName = signature.getName();

			Class<?> classTarget = joinPoint.getTarget().getClass();
			Class<?>[] par=((MethodSignature) signature).getParameterTypes();
			Method objMethod=classTarget.getMethod(methodName, par);
			LogNotCheck logNotcheck = objMethod.getAnnotation(LogNotCheck.class);
			// 不进行入参和返回值日志记录
			if(logNotcheck!=null&&logNotcheck.validate()){
				return ;
			}
			InParamNotLogCheck InParamcheck = objMethod.getAnnotation(InParamNotLogCheck.class);
			// 不进行入参日志记录
			if(InParamcheck!=null&&InParamcheck.validate()){
				return ;
			}

			MethodSignature methodSignature = (MethodSignature) signature;
			// 参数名
			String[] parameterNames = methodSignature.getParameterNames();

			StringBuilder logStr = new StringBuilder("[aop记录 ent:");
			logStr.append(methodName);
			logStr.append("] ");

			if(parameterNames!=null){
				for (String param: parameterNames){
					logStr.append("[");
					logStr.append(param);
					logStr.append(":{}] ");
				}
			}

			// 参数值
			Object[] args = joinPoint.getArgs();

			// 记录Service 方法的传参
			logger.info(logStr.toString(),args);
		} catch (Exception e) {
			logger.error("{}",e);
		}
	}
}
