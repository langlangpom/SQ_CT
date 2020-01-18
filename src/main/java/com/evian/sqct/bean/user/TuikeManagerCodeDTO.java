package com.evian.sqct.bean.user;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.evian.sqct.bean.vendor.UrlManage;

/**
 * @date   2019年8月16日 上午11:16:14
 * @author XHX
 * @Description 推客经理分配的店铺推客码
 */
public class TuikeManagerCodeDTO {

	private Long shopCode;
	private Integer eid;
	private Integer shopId;
	private String picUrl;
	private Integer managerClientId;
	private Timestamp manageCreateTime;
	private Boolean isEnable;
	private Integer tuikeClientId;
	private Timestamp createTime;
	private String createUser;
	private String eName;
	private String shopName;
	private String managerFullName;
	private String managerAccount;
	private String tuikeFullName;
	private String tuikeAccount;
	private Timestamp enableTime;
	private Boolean isShopMain;
	private String wechatQrcodePicUrl;
	
	public Long getShopCode() {
		return shopCode;
	}

	public void setShopCode(Long shopCode) {
		this.shopCode = shopCode;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		if(!StringUtils.isEmpty(picUrl)) {
			picUrl = UrlManage.getAdminWebUrl()+picUrl;
		}
		this.picUrl = picUrl;
	}

	public Integer getManagerClientId() {
		return managerClientId;
	}

	public void setManagerClientId(Integer managerClientId) {
		this.managerClientId = managerClientId;
	}

	public Timestamp getManageCreateTime() {
		return manageCreateTime;
	}

	public void setManageCreateTime(Timestamp manageCreateTime) {
		this.manageCreateTime = manageCreateTime;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getTuikeClientId() {
		return tuikeClientId;
	}

	public void setTuikeClientId(Integer tuikeClientId) {
		this.tuikeClientId = tuikeClientId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getManagerFullName() {
		return managerFullName;
	}

	public void setManagerFullName(String managerFullName) {
		this.managerFullName = managerFullName;
	}

	public String getManagerAccount() {
		return managerAccount;
	}

	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}

	public String getTuikeFullName() {
		return tuikeFullName;
	}

	public void setTuikeFullName(String tuikeFullName) {
		this.tuikeFullName = tuikeFullName;
	}

	public String getTuikeAccount() {
		return tuikeAccount;
	}

	public void setTuikeAccount(String tuikeAccount) {
		this.tuikeAccount = tuikeAccount;
	}

	public Timestamp getEnableTime() {
		return enableTime;
	}

	public void setEnableTime(Timestamp enableTime) {
		this.enableTime = enableTime;
	}

	public Boolean getIsShopMain() {
		return isShopMain;
	}

	public void setIsShopMain(Boolean isShopMain) {
		this.isShopMain = isShopMain;
	}

	public String getWechatQrcodePicUrl() {
		return wechatQrcodePicUrl;
	}

	public void setWechatQrcodePicUrl(String wechatQrcodePicUrl) {
		if(!StringUtils.isEmpty(wechatQrcodePicUrl)) {
			wechatQrcodePicUrl = UrlManage.getAdminWebUrl()+wechatQrcodePicUrl;
		}
		this.wechatQrcodePicUrl = wechatQrcodePicUrl;
	}

	@Override
	public String toString() {
		return "TuikeManagerCodeDTO [shopCode=" + shopCode + ", eid=" + eid + ", shopId=" + shopId + ", picUrl="
				+ picUrl + ", managerClientId=" + managerClientId + ", manageCreateTime=" + manageCreateTime
				+ ", isEnable=" + isEnable + ", tuikeClientId=" + tuikeClientId + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", eName=" + eName + ", shopName=" + shopName + ", managerFullName="
				+ managerFullName + ", managerAccount=" + managerAccount + ", tuikeFullName=" + tuikeFullName
				+ ", tuikeAccount=" + tuikeAccount + ", enableTime=" + enableTime + ", isShopMain=" + isShopMain
				+ ", wechatQrcodePicUrl=" + wechatQrcodePicUrl + "]";
	}
	
}
