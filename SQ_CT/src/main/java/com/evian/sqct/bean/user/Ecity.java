package com.evian.sqct.bean.user;

import java.sql.Timestamp;

/**
 * @date   2018年11月22日 下午3:39:21
 * @author XHX
 * @Description 城市实体
 */
public class Ecity {

	private int cityId;  //城市ID
    private String cityName; //城市名称
    private String zipCode; //城市邮编
    private String location; //中心坐标
    private String provinceId; //所属省份ID
    private Timestamp dateCreated; //创建时间
    private Boolean ifLine; //是否上线
    private Timestamp dateLine; //上线时间
    private Boolean ifHot;//是否热门城市
    private int baiduCode;  //对应百度cityCode
    private int zoom;  //地图缩放等级
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
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
	public int getBaiduCode() {
		return baiduCode;
	}
	public void setBaiduCode(int baiduCode) {
		this.baiduCode = baiduCode;
	}
	public int getZoom() {
		return zoom;
	}
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}
	@Override
	public String toString() {
		return "Ecity [cityId=" + cityId + ", cityName=" + cityName + ", zipCode=" + zipCode + ", location=" + location
				+ ", provinceId=" + provinceId + ", ifLine=" + ifLine + ", ifHot=" + ifHot + ", baiduCode=" + baiduCode
				+ ", zoom=" + zoom + "]";
	}
}
