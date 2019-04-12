package com.evian.sqct.bean.user;

import java.sql.Timestamp;

/**
 * @date   2018年11月22日 下午1:01:01
 * @author XHX
 * @Description 用户表
 */
public class Eclient {
	private int clientId;  //客户ID
    private String account; //账号
    private String passWord; //密码
    private String email; //邮箱
    private String boundPhone; //绑定手机
    private String nickName;//昵称
    private String sex; //性别(先生/女生)
    private String birthday; //生日(格式:0526)
    private String photo; //头像
    private short status; //状态
    private String sdkType; //平台(ANDROID,IOS)
    private String mobileIMEL; //IMEL
    private String mobileType; //手机厂商具体型号
    private String sdkVer; //手机系统版本
    private int loginNumber; //登录次数
    private String identityCode; //唯一身份验证码
    private Timestamp dateLastLogin;//最后登录时间
    private Timestamp dateCreated; //创建时间
    private String weixinId; //微信ID
    private String location;  //注册坐标
    private String appVer; //APP,wx端版本号
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBoundPhone() {
		return boundPhone;
	}
	public void setBoundPhone(String boundPhone) {
		this.boundPhone = boundPhone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getSdkType() {
		return sdkType;
	}
	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}
	public String getMobileIMEL() {
		return mobileIMEL;
	}
	public void setMobileIMEL(String mobileIMEL) {
		this.mobileIMEL = mobileIMEL;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	public String getSdkVer() {
		return sdkVer;
	}
	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	public int getLoginNumber() {
		return loginNumber;
	}
	public void setLoginNumber(int loginNumber) {
		this.loginNumber = loginNumber;
	}
	public String getIdentityCode() {
		return identityCode;
	}
	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	public Timestamp getDateLastLogin() {
		return dateLastLogin;
	}
	public void setDateLastLogin(Timestamp dateLastLogin) {
		this.dateLastLogin = dateLastLogin;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAppVer() {
		return appVer;
	}
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	@Override
	public String toString() {
		return "Eclient [clientId=" + clientId + ", account=" + account + ", passWord=" + passWord + ", email=" + email
				+ ", boundPhone=" + boundPhone + ", nickName=" + nickName + ", sex=" + sex + ", birthday=" + birthday
				+ ", photo=" + photo + ", status=" + status + ", sdkType=" + sdkType + ", mobileIMEL=" + mobileIMEL
				+ ", mobileType=" + mobileType + ", sdkVer=" + sdkVer + ", loginNumber=" + loginNumber
				+ ", identityCode=" + identityCode + ", dateLastLogin=" + dateLastLogin + ", dateCreated=" + dateCreated
				+ ", weixinId=" + weixinId + ", location=" + location + ", appVer=" + appVer + "]";
	}
}
