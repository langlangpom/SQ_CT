package com.evian.sqct.bean.shop;

public class Shop {

	private Integer shopId;
	private Integer eid;
	private String shopNo; 
	private String shopName;
	private String address;
	private String tel; 
	private String linkman;
	private Integer cityId;
	private Integer districtId;
	private String location;
	private String description;
	private String pictureUrl;
	private String shopType;
	private String startTime;
	private String endTime;
	private String scopeDescription;
	private Integer sendOnTime;
	private Double minSendPrice;
	private Double freight;
	private String createUser;
	private Boolean ifLine;
	private Integer staffId;
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getScopeDescription() {
		return scopeDescription;
	}
	public void setScopeDescription(String scopeDescription) {
		this.scopeDescription = scopeDescription;
	}
	public Integer getSendOnTime() {
		return sendOnTime;
	}
	public void setSendOnTime(Integer sendOnTime) {
		this.sendOnTime = sendOnTime;
	}
	public Double getMinSendPrice() {
		return minSendPrice;
	}
	public void setMinSendPrice(Double minSendPrice) {
		this.minSendPrice = minSendPrice;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Boolean getIfLine() {
		return ifLine;
	}
	public void setIfLine(Boolean ifLine) {
		this.ifLine = ifLine;
	}
	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", eid=" + eid + ", shopNo=" + shopNo
				+ ", shopName=" + shopName + ", address=" + address + ", tel="
				+ tel + ", linkman=" + linkman + ", cityId=" + cityId
				+ ", districtId=" + districtId + ", location=" + location
				+ ", description=" + description + ", pictureUrl=" + pictureUrl
				+ ", shopType=" + shopType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", scopeDescription="
				+ scopeDescription + ", sendOnTime=" + sendOnTime
				+ ", minSendPrice=" + minSendPrice + ", freight=" + freight
				+ ", createUser=" + createUser + ", ifLine=" + ifLine
				+ ", staffId=" + staffId + "]";
	}
	
}
