package com.evian.sqct.bean.app;

import java.io.Serializable;

/**
 * @date   2019年4月23日 上午11:52:57
 * @author XHX
 * @Description app版本号
 */
public class APPUpgrader implements Serializable{

	private static final long serialVersionUID = -1840762838578110260L;
	
	/**
	 * 版本号
	 */
	private static String versionInt;
	/**
	 * 版本名称
	 */
	private static String versionName;
	/**
	 * 下载地址
	 */
	private static String downloadUrl;
	/**
	 * 升级说明
	 */
	private static String describe;
	public static String getVersionInt() {
		return versionInt;
	}
	public static void setVersionInt(String versionInt) {
		APPUpgrader.versionInt = versionInt;
	}
	public static String getVersionName() {
		return versionName;
	}
	public static void setVersionName(String versionName) {
		APPUpgrader.versionName = versionName;
	}
	public static String getDownloadUrl() {
		return downloadUrl;
	}
	public static void setDownloadUrl(String downloadUrl) {
		APPUpgrader.downloadUrl = downloadUrl;
	}
	public static String getDescribe() {
		return describe;
	}
	public static void setDescribe(String describe) {
		APPUpgrader.describe = describe;
	}
	@Override
	public String toString() {
		return "APPUpgrader [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
