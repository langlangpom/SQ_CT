package com.evian.sqct.bean.vendor;

import java.util.Date;

/**
 * @date   2018年12月22日 下午2:39:29
 * @author XHX
 * @Description 售货机状态
 */
public class VendorMainboardContainer {

	private String mainboardNo;
	private Integer mainboardId;
	private String containerCode;
	private Integer containerId;
	private Integer eid;
	private Integer containerStatus;
	private String qrcodePath;
	private Integer shopId;
	private String shopContainerName;
	private String location;
	private String containerAddress;
	private String remark;
	private Date createTime;
	private String creater;
	private Integer communityId;
	
	public String getMainboardNo() {
		return mainboardNo;
	}
	public void setMainboardNo(String mainboardNo) {
		this.mainboardNo = mainboardNo;
	}
	public Integer getMainboardId() {
		return mainboardId;
	}
	public void setMainboardId(Integer mainboardId) {
		this.mainboardId = mainboardId;
	}
	public String getContainerCode() {
		return containerCode;
	}
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}
	public Integer getContainerId() {
		return containerId;
	}
	public void setContainerId(Integer containerId) {
		this.containerId = containerId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getContainerStatus() {
		return containerStatus;
	}
	public void setContainerStatus(Integer containerStatus) {
		this.containerStatus = containerStatus;
	}
	public String getQrcodePath() {
		return qrcodePath;
	}
	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopContainerName() {
		return shopContainerName;
	}
	public void setShopContainerName(String shopContainerName) {
		this.shopContainerName = shopContainerName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContainerAddress() {
		return containerAddress;
	}
	public void setContainerAddress(String containerAddress) {
		this.containerAddress = containerAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	@Override
	public String toString() {
		return "VendorMainboardContainer [mainboardNo=" + mainboardNo + ", mainboardId=" + mainboardId
				+ ", containerCode=" + containerCode + ", containerId=" + containerId + ", eid=" + eid
				+ ", containerStatus=" + containerStatus + ", qrcodePath=" + qrcodePath + ", shopId=" + shopId
				+ ", shopContainerName=" + shopContainerName + ", location=" + location + ", containerAddress="
				+ containerAddress + ", remark=" + remark + ", createTime=" + createTime + ", creater=" + creater
				+ ", communityId=" + communityId + "]";
	}

	
}
