package com.evian.sqct.bean.user;

public class UserModel3 {
	private Integer accountId;
	private String account; 			// 商户端用户账号
	private String userAuthorization;	// 权限类型：000；1为是，0为否；第一位app商户店铺管理员， 第二位表示是否设备管理员，第三位表示是否自动售货机补货员
	private Boolean isEnable;			// 0禁用，1启用
	private String remark;				// 备注
	private String eName;				// 企业名称
	private Integer eid;				// 企业id
//	private Boolean isAudit;			// 是否审核
	private String headImg;				// 头像
	private String fullname;			// 名称
	private String SQ_IDCard;		// 身份证号
	private String healthCertificateImg;// 健康证号
	private Boolean isAdmin;			// 是否超级管理员
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getSQ_IDCard() {
		return SQ_IDCard;
	}
	public void setSQ_IDCard(String sQ_IDCard) {
		SQ_IDCard = sQ_IDCard;
	}
	public String getHealthCertificateImg() {
		return healthCertificateImg;
	}
	public void setHealthCertificateImg(String healthCertificateImg) {
		this.healthCertificateImg = healthCertificateImg;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserAuthorization() {
		return userAuthorization;
	}
	public void setUserAuthorization(String userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	@Override
	public String toString() {
		return "UserModel3 [accountId=" + accountId + ", account=" + account + ", userAuthorization="
				+ userAuthorization + ", isEnable=" + isEnable + ", remark=" + remark + ", eName=" + eName + ", eid="
				+ eid + ", headImg=" + headImg + ", fullname=" + fullname + ", SQ_IDCard=" + SQ_IDCard
				+ ", healthCertificateImg=" + healthCertificateImg + ", isAdmin=" + isAdmin + "]";
	}
	
	

}
