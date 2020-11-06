package com.evian.sqct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author XHX
 */
@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
/** 强制开启CGLIB aop才能获取到入参的参数名 bean是接口的话 JDK代理是获取不到接口的入参参数，只有CGLIB代理才能获取参数，所以要开启CGLIB */
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SqCtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqCtApplication.class, args);
	}
}
