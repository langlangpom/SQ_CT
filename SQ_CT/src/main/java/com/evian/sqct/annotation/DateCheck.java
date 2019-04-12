package com.evian.sqct.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date   2018年9月29日 下午2:46:42
 * @author XHX
 * @Description 视图层参数校验 是否有传此参数
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateCheck {
	
	boolean validate() default true;
	
	// 需要校验的参数
	String [] check() default {};
	
	// 可有可无的参数
	String [] unnecessary() default {};
}
