package com.evian.sqct.config;

import com.evian.sqct.bean.util.JPushConfig;
import com.evian.sqct.bean.vendor.PayParam;
import com.evian.sqct.bean.vendor.UrlManage;
import com.evian.sqct.dao.IOrderDao;
import com.evian.sqct.util.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class StartupRunnerServlet implements CommandLineRunner {

	@Autowired  
    private Environment env;
	
	@Autowired
	@Qualifier("orderDao")
	private IOrderDao orderDao;
	
	@Override
	public void run(String... args) throws Exception {
		// 七牛配置初始化
		QiniuConfig.accessKey=env.getProperty("qiniu.accessKey");
		QiniuConfig.bucket=env.getProperty("qiniu.bucket");
		QiniuConfig.namespace=env.getProperty("qiniu.namespace");
		QiniuConfig.secretKey=env.getProperty("qiniu.secretKey");
		
		// 售货机调用接口域名
		UrlManage.setContainerMarketDetectionUnLockUrl(env.getProperty("hg.ks"));
		UrlManage.setShuiqooMchantUrl(env.getProperty("shuiqoo.mchant.url"));
		UrlManage.setOrdersTaskUrl(env.getProperty("hg.rwdd"));
		UrlManage.setShuiqooApiUrl(env.getProperty("shuiqoo.api.url"));
		PayParam.setWeChatAppPayAppId(env.getProperty("app.weixin.pay.appid"));
		PayParam.setWeChatAppPayMchId(env.getProperty("app.weixin.pay.mchid"));
		PayParam.setWeChatAppPayKey(env.getProperty("app.weixin.pay.key"));
		if("true".equals(env.getProperty("tiaozhuan.InteceptorSwitch"))) {
			UrlManage.setInteceptorSwitch(true);
		}else {
			UrlManage.setInteceptorSwitch(false);
		}
		
		Map<String, Object> selectSysConfig = orderDao.selectSysConfig();
		String paramAdminWebSit = (String) selectSysConfig.get("后台域名");
		String apiPath = "https://" + paramAdminWebSit;
		UrlManage.setAdminWebUrl(apiPath);

		JPushConfig.setAppkey(env.getProperty("jpush.appkey"));
		JPushConfig.setMastersecret(env.getProperty("jpush.mastersecret"));

	}
}
