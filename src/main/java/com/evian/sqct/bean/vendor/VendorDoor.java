package com.evian.sqct.bean.vendor;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VendorDoor {

	private Integer mainboardId;	// 主板ID
	private Integer doorIndex;		// 柜门序号(1-28)
	private Integer productId;		// 销售商品ID
	private Timestamp createTime;	// 录入日期
	private String creater;			// 录入人
	private Integer eid;			// 企业ID
	private Integer doorStatus;		// 柜门状态 0:未激活；1空闲；2使用中；3报损；4开门(交易中)
	private Integer portNo;			// 对应主板端口
	private String alias;			// 别名（用作开门提示）
	private String productName;		// 商品名称
	private String picture;			// 商品图片
	private BigDecimal price;		// 商品价格
	private Boolean isLine;			// 是否上架
	private String imageText;		// 商品图文URL
	private String styleCode;		// 主题色
	private String openId;			// 购买用户限制，为空时任何人可以购买，否则只有该openId的用户才可以购买，支付完成或者补货时操作（比如奖品发放时候用到）
	private Integer integralQuantityForNoPay;

	public Integer getIntegralQuantityForNoPay() {
		return integralQuantityForNoPay;
	}

	public void setIntegralQuantityForNoPay(Integer integralQuantityForNoPay) {
		this.integralQuantityForNoPay = integralQuantityForNoPay;
	}

	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getMainboardId() {
		return mainboardId;
	}
	public void setMainboardId(Integer mainboardId) {
		this.mainboardId = mainboardId;
	}
	public Integer getDoorIndex() {
		return doorIndex;
	}
	public void setDoorIndex(Integer doorIndex) {
		this.doorIndex = doorIndex;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(Integer doorStatus) {
		this.doorStatus = doorStatus;
	}
	public Integer getPortNo() {
		return portNo;
	}
	public void setPortNo(Integer portNo) {
		this.portNo = portNo;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Boolean getIsLine() {
		return isLine;
	}
	public void setIsLine(Boolean isLine) {
		this.isLine = isLine;
	}
	public String getImageText() {
		return imageText;
	}
	public void setImageText(String imageText) {
		this.imageText = imageText;
	}
	public String getStyleCode() {
		return styleCode;
	}
	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}
	@Override
	public String toString() {
		return "VendorDoor [mainboardId=" + mainboardId + ", doorIndex=" + doorIndex + ", productId=" + productId
				+ ", createTime=" + createTime + ", creater=" + creater + ", eid=" + eid + ", doorStatus=" + doorStatus
				+ ", portNo=" + portNo + ", alias=" + alias + ", productName=" + productName + ", picture=" + picture
				+ ", price=" + price + ", isLine=" + isLine + ", imageText=" + imageText + ", styleCode=" + styleCode
				+ ", openId=" + openId + ", integralQuantityForNoPay=" + integralQuantityForNoPay + "]";
	}
}
