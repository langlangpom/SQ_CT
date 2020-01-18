package com.evian.sqct.bean.app;

import java.io.Serializable;

/**
 * @date   2019年4月23日 上午11:52:57
 * @author XHX
 * @Description app版本号的查询对象
 */
public class APPUpgraderSelectObject implements Serializable{

	
	private static final long serialVersionUID = -3175405885226561554L;
	
	/**
	 * 版本号
	 */
	private String versionInt;
	/**
	 * 版本名称
	 */
	private String versionName;
	/**
	 * 下载地址
	 */
	private String downloadUrl;
	/**
	 * 升级说明
	 */
	private String describe;
	@Override
	public String toString() {
		return "APPUpgraderSelectObject [versionInt=" + versionInt + ", versionName=" + versionName + ", downloadUrl="
				+ downloadUrl + ", describe=" + describe + "]";
	}
	public String getVersionInt() {
		return versionInt;
	}
	public void setVersionInt(String versionInt) {
		this.versionInt = versionInt;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
