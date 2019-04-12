package com.evian.sqct.bean.shop;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FindShopModel {

	private Long row;
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
	private BigDecimal minSendPrice;
	private BigDecimal freight;
	private Timestamp dateCreated;
	private String createUser;
	private Boolean ifLine;
	private String districtName;
	private String cityName;
	private Boolean cityIfLine;
	private Integer provinceId;
	private String eName;
	private String sendLocation;
	private Integer shareClientId;
	private Integer shareId;
	private String shareAccount;
	private Boolean isSettingTimeframe;
	private Boolean isSettingWechatliteapp;
	private Integer keeperId;
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
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
	public BigDecimal getMinSendPrice() {
		return minSendPrice;
	}
	public void setMinSendPrice(BigDecimal minSendPrice) {
		this.minSendPrice = minSendPrice;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
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
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Boolean getCityIfLine() {
		return cityIfLine;
	}
	public void setCityIfLine(Boolean cityIfLine) {
		this.cityIfLine = cityIfLine;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getSendLocation() {
		return sendLocation;
	}
	public void setSendLocation(String sendLocation) {
		this.sendLocation = sendLocation;
	}
	public Integer getShareClientId() {
		return shareClientId;
	}
	public void setShareClientId(Integer shareClientId) {
		this.shareClientId = shareClientId;
	}
	public Integer getShareId() {
		return shareId;
	}
	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}
	public String getShareAccount() {
		return shareAccount;
	}
	public void setShareAccount(String shareAccount) {
		this.shareAccount = shareAccount;
	}
	public Boolean getIsSettingTimeframe() {
		return isSettingTimeframe;
	}
	public void setIsSettingTimeframe(Boolean isSettingTimeframe) {
		this.isSettingTimeframe = isSettingTimeframe;
	}
	public Boolean getIsSettingWechatliteapp() {
		return isSettingWechatliteapp;
	}
	public void setIsSettingWechatliteapp(Boolean isSettingWechatliteapp) {
		this.isSettingWechatliteapp = isSettingWechatliteapp;
	}
	public Integer getKeeperId() {
		return keeperId;
	}
	public void setKeeperId(Integer keeperId) {
		this.keeperId = keeperId;
	}
	@Override
	public String toString() {
		return "FindShopModel [row=" + row + ", shopId=" + shopId + ", eid="
				+ eid + ", shopNo=" + shopNo + ", shopName=" + shopName
				+ ", address=" + address + ", tel=" + tel + ", linkman="
				+ linkman + ", cityId=" + cityId + ", districtId=" + districtId
				+ ", location=" + location + ", description=" + description
				+ ", pictureUrl=" + pictureUrl + ", shopType=" + shopType
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", scopeDescription=" + scopeDescription + ", sendOnTime="
				+ sendOnTime + ", minSendPrice=" + minSendPrice + ", freight="
				+ freight + ", dateCreated=" + dateCreated + ", createUser="
				+ createUser + ", ifLine=" + ifLine + ", districtName="
				+ districtName + ", cityName=" + cityName + ", cityIfLine="
				+ cityIfLine + ", provinceId=" + provinceId + ", eName="
				+ eName + ", sendLocation=" + sendLocation + ", shareClientId="
				+ shareClientId + ", shareId=" + shareId + ", shareAccount="
				+ shareAccount + ", isSettingTimeframe=" + isSettingTimeframe
				+ ", isSettingWechatliteapp=" + isSettingWechatliteapp
				+ ", keeperId=" + keeperId + "]";
	}
	
}
