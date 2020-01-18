package com.evian.sqct.bean.user;

/**
 * @date   2019年5月31日 下午3:40:44
 * @author XHX
 * @Description 职员列表
 */
public class StaffDTO {

	private Integer id;					// e_appMerchant_account accountId做对应
	private Integer staffId;			// 职员id
	private String staffNo;				// 职员编号
	private String name;				// 职员姓名
	private String phone;				// 电话
	private String picture;				// 职员头像
	private String userAuthorization;	// 权限类型：0000；1为是，0为否； 第一位app商户店铺管理员，第二位设备管理员，第三位表示是否自动售货机补货员，第四位配送员
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "StaffDTO [id=" + id + ", staffId=" + staffId + ", staffNo=" + staffNo + ", name=" + name + ", phone="
				+ phone + ", picture=" + picture + ", userAuthorization=" + userAuthorization + "]";
	}
	
}
