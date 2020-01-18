package com.evian.sqct.bean.vendor;

import java.util.Date;

/**
 * @date   2019年1月23日 上午9:29:57
 * @author XHX
 * @Description vendor_container_template
 */
public class VendorContainerTemplate {

	private Integer containerId;
	private String containerName;
	private Integer doorNum;
	private String remark;
	private Date createTime;
	private String creater;
	private Integer storageQuantity;
	private String containerPic;
	private Integer qrcodeDoorIndex;
	public Integer getContainerId() {
		return containerId;
	}
	public void setContainerId(Integer containerId) {
		this.containerId = containerId;
	}
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public Integer getDoorNum() {
		return doorNum;
	}
	public void setDoorNum(Integer doorNum) {
		this.doorNum = doorNum;
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
	public Integer getStorageQuantity() {
		return storageQuantity;
	}
	public void setStorageQuantity(Integer storageQuantity) {
		this.storageQuantity = storageQuantity;
	}
	public String getContainerPic() {
		return containerPic;
	}
	public void setContainerPic(String containerPic) {
		this.containerPic = containerPic;
	}
	public Integer getQrcodeDoorIndex() {
		return qrcodeDoorIndex;
	}
	public void setQrcodeDoorIndex(Integer qrcodeDoorIndex) {
		this.qrcodeDoorIndex = qrcodeDoorIndex;
	}
	@Override
	public String toString() {
		return "VendorContainerTemplate [containerId=" + containerId + ", containerName=" + containerName + ", doorNum="
				+ doorNum + ", remark=" + remark + ", createTime=" + createTime + ", creater=" + creater
				+ ", storageQuantity=" + storageQuantity + ", containerPic=" + containerPic + ", qrcodeDoorIndex="
				+ qrcodeDoorIndex + "]";
	}

}
