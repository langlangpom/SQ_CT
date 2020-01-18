package com.evian.sqct.bean.user;

import java.sql.Timestamp;

public class UserModel {
	Integer userId;
	String loginName;
	String passWord;
	String description;
	String nickName;
	String photo;
	String email;
	String phone;
	Timestamp createTime;
	Timestamp updateTime;
	String createUser;
	String updateUser;
	Timestamp lastLoginTime;
	String lastLoginIP;
	Integer loginCount;
	Short enabled;
	Boolean superAdmin;
	Integer eid;
	Integer shopId;
	String shopName;
	String eName;
	Boolean registerUser;
	Boolean isFinance;
	Integer roleId;
	Long row;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	
	public Short getEnabled() {
		return enabled;
	}
	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
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
	
	public Boolean getRegisterUser() {
		return registerUser;
	}
	public void setRegisterUser(Boolean registerUser) {
		this.registerUser = registerUser;
	}
	
	public Boolean getIsFinance() {
		return isFinance;
	}
	public void setIsFinance(Boolean isFinance) {
		this.isFinance = isFinance;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", loginName=" + loginName
				+ ", passWord=" + passWord + ", description=" + description
				+ ", nickName=" + nickName + ", photo=" + photo + ", email="
				+ email + ", phone=" + phone + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", createUser=" + createUser
				+ ", updateUser=" + updateUser + ", lastLoginTime="
				+ lastLoginTime + ", lastLoginIP=" + lastLoginIP
				+ ", loginCount=" + loginCount + ", enabled=" + enabled
				+ ", superAdmin=" + superAdmin + ", eid=" + eid + ", shopId="
				+ shopId + ", shopName=" + shopName + ", eName=" + eName
				+ ", registerUser=" + registerUser + ", isFinance=" + isFinance
				+ ", roleId=" + roleId + ", row=" + row + "]";
	}
	
	
	
	
	
	

	
}
