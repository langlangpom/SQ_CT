package com.evian.sqct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
public class SqCtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqCtApplication.class, args);
	}
}
