package com.evian.sqct.bean.user;

public class UserModel2 {

	private Integer id;
	private Integer eid;
	private String name;
	private String phone;
	private String userAuthorization;
	private String picture;
	private String eName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserAuthorization() {
		return userAuthorization;
	}

	public void setUserAuthorization(String userAuthorization) {
		this.userAuthorization = userAuthorization;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	@Override
	public String toString() {
		return "UserModel2 [id=" + id + ", eid=" + eid + ", name=" + name
				+ ", phone=" + phone + ", userAuthorization="
				+ userAuthorization + ", picture=" + picture + ",eName="
				+ eName + "]";
	}
}
