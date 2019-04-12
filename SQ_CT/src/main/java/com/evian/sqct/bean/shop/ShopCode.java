package com.evian.sqct.bean.shop;

import java.sql.Timestamp;

public class ShopCode {

	private Long row;
	private Long shopCode;
	private Integer shopId;
	private Integer eid;
	private String picUrl;
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
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Long getShopCode() {
		return shopCode;
	}
	public void setShopCode(Long shopCode) {
		this.shopCode = shopCode;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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
	@Override
	public String toString() {
		return "ShopCode [row=" + row + ", shopCode=" + shopCode + ", shopId="
				+ shopId + ", eid=" + eid + ", picUrl=" + picUrl
				+ ", manageCreateTime=" + manageCreateTime + ", isEnable="
				+ isEnable + ", tuikeClientId=" + tuikeClientId
				+ ", createTime=" + createTime + ", createUser=" + createUser
				+ ", eName=" + eName + ", shopName=" + shopName
				+ ", managerFullName=" + managerFullName + ", managerAccount="
				+ managerAccount + ", tuikeFullName=" + tuikeFullName
				+ ", tuikeAccount=" + tuikeAccount + "]";
	}
}
