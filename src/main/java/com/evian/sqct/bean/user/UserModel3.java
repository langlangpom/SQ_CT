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
	private String track_userId;		// 用于鹰眼的唯一id
	private Integer shopId;				// 店铺id
	private String shopName;			// 店铺名称
	private String pictureUrl;			// 店铺图片
	private Integer clientId;			// 客户id	
	private String identityCode;		// 客户idCode	
	private String appId;				// 小程序或者公众号appId
	private String openId;				// 用户openid
	private Integer roleId;				// 权限id
	private String roleName;			// 权限昵称
	private String sign;				// 返回sign=superAdmin或者shopAdmin时，无需判断权限
	private Boolean openShuiqooBusApp; 	// 是否开通水趣商户
	private String nickname;			// 微信昵称
	private Boolean isReceiveOrder;		// 是否接单员

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public String getTrack_userId() {
		return track_userId;
	}
	public void setTrack_userId(String track_userId) {
		this.track_userId = track_userId;
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
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Boolean getOpenShuiqooBusApp() {
		return openShuiqooBusApp;
	}
	public void setOpenShuiqooBusApp(Boolean openShuiqooBusApp) {
		this.openShuiqooBusApp = openShuiqooBusApp;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Boolean getIsReceiveOrder() {
		return isReceiveOrder;
	}
	public void setIsReceiveOrder(Boolean isReceiveOrder) {
		this.isReceiveOrder = isReceiveOrder;
	}

	@Override
	public String toString() {
		return "UserModel3 [accountId=" + accountId + ", account=" + account + ", userAuthorization="
				+ userAuthorization + ", isEnable=" + isEnable + ", remark=" + remark + ", eName=" + eName + ", eid="
				+ eid + ", headImg=" + headImg + ", fullname=" + fullname + ", SQ_IDCard=" + SQ_IDCard
				+ ", healthCertificateImg=" + healthCertificateImg + ", isAdmin=" + isAdmin + ", track_userId="
				+ track_userId + ", shopId=" + shopId + ", shopName=" + shopName + ", pictureUrl=" + pictureUrl
				+ ", clientId=" + clientId + ", identityCode=" + identityCode + ", appId=" + appId + ", openId="
				+ openId + ", openShuiqooBusApp=" + openShuiqooBusApp + ", nickname=" + nickname + ", isReceiveOrder=" + isReceiveOrder + "]";
	}
	
	
	

}
