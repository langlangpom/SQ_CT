package com.evian.sqct.bean.user;

import java.io.Serializable;

public class WxUserAddressModel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6869685877371204822L;
	Integer did;
	String location;
	String streetName;
	String streetDescribe;
	String doorplate;
	String phone;
	String contacts;
	Boolean ifDefault;
	Integer citycode;
	Integer cityId;
	String cityName;
	Integer districtId;// v3区域ID
	String tag;
	Integer status;
	Boolean hashSubmit;
	String clientId;
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetDescribe() {
		return streetDescribe;
	}
	public void setStreetDescribe(String streetDescribe) {
		this.streetDescribe = streetDescribe;
	}
	public String getDoorplate() {
		return doorplate;
	}
	public void setDoorplate(String doorplate) {
		this.doorplate = doorplate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Boolean getIfDefault() {
		return ifDefault;
	}
	public void setIfDefault(Boolean ifDefault) {
		this.ifDefault = ifDefault;
	}
	public Integer getCitycode() {
		return citycode;
	}
	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getHashSubmit() {
		return hashSubmit;
	}
	public void setHashSubmit(Boolean hashSubmit) {
		this.hashSubmit = hashSubmit;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}


	//v2版本接口
	String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
