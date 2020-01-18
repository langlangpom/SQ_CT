package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

/**
 * @date   2018年11月6日 上午10:00:24
 * @author XHX
 * @Description 城市
 */
public class GisCity {

	private Integer cityId;
	private String cityName;
	private String zipCode;
	private Integer baiduCode;
	private String location;
	private Integer zoom;
	private Integer provinceId;
	private Timestamp dateCreated;
	private Boolean ifLine;
	private Timestamp dateLine;
	private Boolean ifHot;
	private Timestamp dateUpdated;
	private String updateUser;
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Integer getBaiduCode() {
		return baiduCode;
	}
	public void setBaiduCode(Integer baiduCode) {
		this.baiduCode = baiduCode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Boolean getIfLine() {
		return ifLine;
	}
	public void setIfLine(Boolean ifLine) {
		this.ifLine = ifLine;
	}
	public Timestamp getDateLine() {
		return dateLine;
	}
	public void setDateLine(Timestamp dateLine) {
		this.dateLine = dateLine;
	}
	public Boolean getIfHot() {
		return ifHot;
	}
	public void setIfHot(Boolean ifHot) {
		this.ifHot = ifHot;
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
		return "GisCity [cityId=" + cityId + ", cityName=" + cityName + ", zipCode=" + zipCode + ", baiduCode="
				+ baiduCode + ", location=" + location + ", zoom=" + zoom + ", provinceId=" + provinceId
				+ ", dateCreated=" + dateCreated + ", ifLine=" + ifLine + ", dateLine=" + dateLine + ", ifHot=" + ifHot
				+ ", dateUpdated=" + dateUpdated + ", updateUser=" + updateUser + "]";
	}
}
