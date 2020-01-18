package com.evian.sqct.bean.user;

/**
 * @date   2019年6月21日 下午4:37:57
 * @author XHX
 * @Description 该函数的功能描述
 */
public class VendorShopAdministratorDTO {

	private Integer accountId;			// 职员id
	private String name;				// 职员姓名
	private String account;				// 电话
	private String picture;				// 职员头像
	private String userAuthorization;	// 权限类型：000；1为是，0为否； 第一位表示是否登录，第二位app商户店铺管理员，第三位表示是否自动售货机补货员
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getUserAuthorization() {
		return userAuthorization;
	}
	public void setUserAuthorization(String userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
	@Override
	public String toString() {
		return "VendorShopAdministratorDTO [accountId=" + accountId + ", name=" + name + ", account=" + account
				+ ", picture=" + picture + ", userAuthorization=" + userAuthorization + "]";
	}
}
