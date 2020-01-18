package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

/**
 * @date   2018年8月13日 上午10:01:42
 * @author XHX
 * @Description 主板表 VendorMainboard不用了
 */
public class VendorMainboard2 {
	private Integer mainboardId;			// 主板ID
	private String mainboardNo;				// 主板序号
	private String mainboardNoMD5;			// 主板序号
	private Integer portNumber;				// 主板端口数量
	private Timestamp createTime;			// 录入日期
	private String creater;					// 录入人
	private String containerCode;			// 货柜编码（目前没用，和主板编码设为一样即可）
	private Integer containerId;			// 货柜类型ID（表vendor_container_template主键id）
	private Integer eid;					// 企业
	private Integer containerStatus;		// 货柜状态：0未激活，后台可以撤回分配；1激活，空闲中；2运行中；3报损中；4补货中
	private Integer shopId;					// 水店Id，水店内的职员可以管理该主板对应的货柜
	private String qrcodePath;				// 生成二维码路径，用户购买商品扫描该二维码
	private String shopContainerName;		// 名称，由设备管理员补充（如梅花山庄风雪连天柜）
	private String location;				// 坐标
	private String containerAddress;		// 设备地址，由设备管理员补充
	private String remark;					// 设备备注，由设备管理员补充
	private Timestamp bindTime;				// 分配时间
	private String binder;					// 分配人
	private String eName;					// 企业名字
	private String shopName;				// 店铺名字
	private String containerPic;
	private Integer communityId;
	private String communityName;
	
	public String getContainerPic() {
		return containerPic;
	}
	public void setContainerPic(String containerPic) {
		this.containerPic = containerPic;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public Integer getMainboardId() {
		return mainboardId;
	}
	public void setMainboardId(Integer mainboardId) {
		this.mainboardId = mainboardId;
	}
	public String getMainboardNo() {
		return mainboardNo;
	}
	public void setMainboardNo(String mainboardNo) {
		this.mainboardNo = mainboardNo;
	}
	public String getMainboardNoMD5() {
		return mainboardNoMD5;
	}
	public void setMainboardNoMD5(String mainboardNoMD5) {
		this.mainboardNoMD5 = mainboardNoMD5;
	}
	public Integer getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getQrcodePath() {
		return qrcodePath;
	}
	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
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
	public Timestamp getBindTime() {
		return bindTime;
	}
	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}
	public String getBinder() {
		return binder;
	}
	public void setBinder(String binder) {
		this.binder = binder;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@Override
	public String toString() {
		return "VendorMainboard2 [mainboardId=" + mainboardId + ", mainboardNo=" + mainboardNo + ", mainboardNoMD5="
				+ mainboardNoMD5 + ", portNumber=" + portNumber + ", createTime=" + createTime + ", creater=" + creater
				+ ", containerCode=" + containerCode + ", containerId=" + containerId + ", eid=" + eid
				+ ", containerStatus=" + containerStatus + ", shopId=" + shopId + ", qrcodePath=" + qrcodePath
				+ ", shopContainerName=" + shopContainerName + ", location=" + location + ", containerAddress="
				+ containerAddress + ", remark=" + remark + ", bindTime=" + bindTime + ", binder=" + binder + ", eName="
				+ eName + ", shopName=" + shopName + ", containerPic=" + containerPic + ", communityId=" + communityId
				+ ", communityName=" + communityName + "]";
	}
	
}
