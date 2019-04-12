package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

/**
 * @date   2018年11月6日 上午10:10:15
 * @author XHX
 * @Description 社区
 */
public class GisCommunity {

	private Integer communityId;
	
	private Integer districtId;
	
	private String communityName;
	
	private String location;
	
	private Integer eid;
	
	private Integer accountId;
	
	private String account;
	
	private String remark;
	
	private Integer vendorCount;
	
	private Boolean isAudit;
	
	private String cityName;
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Boolean getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Integer getVendorCount() {
		return vendorCount;
	}

	public void setVendorCount(Integer vendorCount) {
		this.vendorCount = vendorCount;
	}

	private Timestamp createTime;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "GisCommunity [communityId=" + communityId + ", districtId=" + districtId + ", communityName="
				+ communityName + ", location=" + location + ", eid=" + eid + ", accountId=" + accountId + ", account="
				+ account + ", remark=" + remark + ", vendorCount=" + vendorCount + ", isAudit=" + isAudit
				+ ", cityName=" + cityName + ", createTime=" + createTime + "]";
	}

	
}
