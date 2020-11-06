package com.evian.sqct.bean.user;

import java.util.Date;

/**
 * @date   2019年6月24日 下午5:01:58
 * @author XHX
 * @Description 售货机职员权限表
 * 2020-05-09 发现它弃用了  转e_appMerchant_account_enterprise_role 表  字段sign=deliveryStaff为配送员
 */
@Deprecated
public class EAppMerchantAccountRole {

	private Integer roleId;				// 权限id
	private Integer eid;				// 企业id
	private String roleName;			// 权限名称
	private String userAuthorization;	// 权限类型：0000；1为是，0为否； 第一位app商户店铺管理员，第二位设备管理员，第三位表示是否自动售货机补货员，第四位配送员
	private Date createTime;			
	private String creater;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserAuthorization() {
		return userAuthorization;
	}
	public void setUserAuthorization(String userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Override
	public String toString() {
		return "EAppMerchantAccountRole [roleId=" + roleId + ", eid=" + eid + ", roleName=" + roleName
				+ ", userAuthorization=" + userAuthorization + ", createTime=" + createTime + ", creater=" + creater
				+ "]";
	}
}
