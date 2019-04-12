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

	
}
