package com.evian.sqct.bean.user;

import java.util.Date;

/**
 * @date   2019年6月4日 上午10:41:00
 * @author XHX
 * @Description 用户权限
 */
public class EAppMerchantAccountShop {

	private Integer accountId;
	private Integer eid;
	private Integer shopId;
	private Date createTime;
	private String createUser;
	private Boolean shopManager;
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Boolean getShopManager() {
		return shopManager;
	}
	public void setShopManager(Boolean shopManager) {
		this.shopManager = shopManager;
	}
	@Override
	public String toString() {
		return "EAppMerchantAccountShop [accountId=" + accountId + ", eid=" + eid + ", shopId=" + shopId
				+ ", createTime=" + createTime + ", createUser=" + createUser + ", shopManager=" + shopManager + "]";
	}
	
}
