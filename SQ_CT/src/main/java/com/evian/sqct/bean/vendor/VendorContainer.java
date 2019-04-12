package com.evian.sqct.bean.vendor;

import java.util.Date;
/**
 * 
 * @date   2019年1月19日 上午10:52:39
 * @author XHX
 * @Description 货柜类型表 vendor_container_template
 */
public class VendorContainer {
	private Integer containerId;			// 类型id
	private String containerName;			// 货柜名称
	private Integer doorNum;				// 柜门数量
	private String remark;					// 备注
	private Date createTime;				// 创建时间
	private String creater;					// 录入人
	private Integer storageQuantity;		// 库存
	private String containerPic;			// 货柜类型图片
	private Integer qrcodeDoorIndex;		// 二维码所在门编号(0为不占用柜门)
	private Integer eid;					// 企业id 关联查询才会有 表内没有这个字段
	private Integer shopId;					// 店铺id 关联查询才会有 表内没有这个字段
	public Integer getEid() {
		return eid;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
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
		return "VendorContainer [containerId=" + containerId + ", containerName=" + containerName + ", doorNum="
				+ doorNum + ", remark=" + remark + ", createTime=" + createTime + ", creater=" + creater
				+ ", storageQuantity=" + storageQuantity + ", containerPic=" + containerPic + ", qrcodeDoorIndex="
				+ qrcodeDoorIndex + ", eid=" + eid + ", shopId=" + shopId + "]";
	}
}
