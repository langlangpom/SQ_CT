package com.evian.sqct.inteceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @date   2018年9月29日 下午2:56:08
 * @author XHX
 * @Description 拦截器管理
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private DateCheckInteceptors dateCheckInteceptors;
	@Autowired
	private LoggerParamsInteceptor loggerParamsInteceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loggerParamsInteceptor).addPathPatterns("/**");
		registry.addInterceptor(dateCheckInteceptors).addPathPatterns("/evian/sqct/**");
		
	}
}
