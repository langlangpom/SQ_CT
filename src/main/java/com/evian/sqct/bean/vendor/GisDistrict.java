package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

/**
 * @date   2018年11月6日 上午10:04:53
 * @author XHX
 * @Description 区域
 */
public class GisDistrict {

	private Integer districtId;
	private String districtName;
	private Integer cityId;
	private Timestamp dateCreated;
	private Integer status;
	private Timestamp dateUpdated;
	private String updateUser;
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	@Override
	public String toString() {
		return "GisDistrict [districtId=" + districtId + ", districtName=" + districtName + ", cityId=" + cityId
				+ ", dateCreated=" + dateCreated + ", status=" + status + ", dateUpdated=" + dateUpdated
				+ ", updateUser=" + updateUser + "]";
	}
	
}
