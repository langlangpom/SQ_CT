package com.evian.sqct.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.util.QiniuConfig;
@Component
public class StartupRunnerServlet implements CommandLineRunner {

	@Autowired  
    private Environment env;
	
	@Override
	public void run(String... args) throws Exception {
		// 七牛配置初始化
		QiniuConfig.accessKey=env.getProperty("qiniu.accessKey");
		QiniuConfig.bucket=env.getProperty("qiniu.bucket");
		QiniuConfig.namespace=env.getProperty("qiniu.namespace");
		QiniuConfig.secretKey=env.getProperty("qiniu.secretKey");
		
		// 售货机调用接口域名
		UrlManage.setContainerMarketDetectionUnLockUrl(env.getProperty("hg.ks"));
		UrlManage.setOrdersTaskUrl(env.getProperty("hg.rwdd"));
		UrlManage.setShuiqooApiUrl(env.getProperty("shuiqoo.api.url"));
	}
}
