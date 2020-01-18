package com.evian.sqct.bean.vendor;

/**
 * @date   2018年5月8日 下午3:28:34
 * @author XHX
 * @Description 调用的接口域名管理
 */
public class UrlManage {

	// 货柜开锁检测域名
	private static String ContainerMarketDetectionUnLockUrl;
	
	// 货柜下单任务调度域名
	private static String OrdersTaskUrl;
	
	// 微信Token和tiket域名
	private static String WxTokenAndTiket;
	
	// 水趣api域名
	private static String shuiqooApiUrl;
	
	// 后台域名
	private static String adminWebUrl;
	
	// 水趣商户域名
	private static String shuiqooMchantUrl;

	// 水趣公众号域名
	private static String shuiqooGZHUrl;
	
	public static String getShuiqooMchantUrl() {
		return shuiqooMchantUrl;
	}

	public static void setShuiqooMchantUrl(String shuiqooMchantUrl) {
		UrlManage.shuiqooMchantUrl = shuiqooMchantUrl;
	}

	public static String getAdminWebUrl() {
		return adminWebUrl;
	}

	public static void setAdminWebUrl(String adminWebUrl) {
		UrlManage.adminWebUrl = adminWebUrl;
	}

	// 原本是售货机前端测试开关  这里当做是否开启轮循开关
	private static Boolean inteceptorSwitch;

	public static String getShuiqooApiUrl() {
		return shuiqooApiUrl;
	}

	public static void setShuiqooApiUrl(String shuiqooApiUrl) {
		UrlManage.shuiqooApiUrl = shuiqooApiUrl;
	}

	public static String getContainerMarketDetectionUnLockUrl() {
		return ContainerMarketDetectionUnLockUrl;
	}

	public static void setContainerMarketDetectionUnLockUrl(String containerMarketDetectionUnLockUrl) {
		ContainerMarketDetectionUnLockUrl = containerMarketDetectionUnLockUrl;
	}

	public static String getOrdersTaskUrl() {
		return OrdersTaskUrl;
	}

	public static void setOrdersTaskUrl(String ordersTaskUrl) {
		OrdersTaskUrl = ordersTaskUrl;
	}

	public static String getWxTokenAndTiket() {
		return WxTokenAndTiket;
	}

	public static void setWxTokenAndTiket(String wxTokenAndTiket) {
		WxTokenAndTiket = wxTokenAndTiket;
	}

	public static Boolean getInteceptorSwitch() {
		return inteceptorSwitch;
	}

	public static void setInteceptorSwitch(Boolean inteceptorSwitch) {
		UrlManage.inteceptorSwitch = inteceptorSwitch;
	}

	public static String getShuiqooGZHUrl() {
		return shuiqooGZHUrl;
	}

	public static void setShuiqooGZHUrl(String shuiqooGZHUrl) {
		UrlManage.shuiqooGZHUrl = shuiqooGZHUrl;
	}
}
