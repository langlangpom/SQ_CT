package com.evian.sqct.bean.order;

import java.io.Serializable;

public class Order implements Serializable{

	private static final long serialVersionUID = -7902477554540716091L;
	private Integer orderId;
	private Integer shopId;
	private String beginTime;
	private String endTime;
	private Integer eid;
	private String orderNo;
	private Integer status;
	private String sdkType;
	private Boolean ifMutual;
	private Boolean ifreply;
	private Boolean in_come;
	private String account;
	private String shopName;
	private Integer confirm_take;
	private String eName;
	private Integer PageIndex;
	private Integer PageSize;
	private Boolean IsSelectAll;
	private Integer payMode;		// 支付方式  1:货到付款 2:在线支付 3:电子水票
	private Boolean linePaySuceed;	// 是否支付
	private Integer has_return;
	private String sourceGroup;
	private String sendAddress;
	private String deliverMan;
	private Boolean isFreight;
	private String phone;
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSdkType() {
		return sdkType;
	}
	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}
	public Boolean getIfMutual() {
		return ifMutual;
	}
	public void setIfMutual(Boolean ifMutual) {
		this.ifMutual = ifMutual;
	}
	public Boolean getIfreply() {
		return ifreply;
	}
	public void setIfreply(Boolean ifreply) {
		this.ifreply = ifreply;
	}
	public Boolean getIn_come() {
		return in_come;
	}
	public void setIn_come(Boolean in_come) {
		this.in_come = in_come;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getConfirm_take() {
		return confirm_take;
	}
	public void setConfirm_take(Integer confirm_take) {
		this.confirm_take = confirm_take;
	}
	public Integer getPageIndex() {
		return PageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		PageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return PageSize;
	}
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	public Boolean getIsSelectAll() {
		return IsSelectAll;
	}
	public void setIsSelectAll(Boolean isSelectAll) {
		IsSelectAll = isSelectAll;
	}

	public Boolean getLinePaySuceed() {
		return linePaySuceed;
	}

	public void setLinePaySuceed(Boolean linePaySuceed) {
		this.linePaySuceed = linePaySuceed;
	}

	public Integer getHas_return() {
		return has_return;
	}

	public void setHas_return(Integer has_return) {
		this.has_return = has_return;
	}

	public String getSourceGroup() {
		return sourceGroup;
	}

	public void setSourceGroup(String sourceGroup) {
		this.sourceGroup = sourceGroup;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getDeliverMan() {
		return deliverMan;
	}

	public void setDeliverMan(String deliverMan) {
		this.deliverMan = deliverMan;
	}

	public Boolean getIsFreight() {
		return isFreight;
	}

	public void setIsFreight(Boolean isFreight) {
		this.isFreight = isFreight;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Order [" +
				"orderId=" + orderId +
				", shopId=" + shopId +
				", beginTime=" + beginTime +
				", endTime=" + endTime +
				", eid=" + eid +
				", orderNo=" + orderNo +
				", status=" + status +
				", sdkType=" + sdkType +
				", ifMutual=" + ifMutual +
				", ifreply=" + ifreply +
				", in_come=" + in_come +
				", account=" + account +
				", shopName=" + shopName +
				", confirm_take=" + confirm_take +
				", eName=" + eName +
				", PageIndex=" + PageIndex +
				", PageSize=" + PageSize +
				", IsSelectAll=" + IsSelectAll +
				", payMode=" + payMode +
				", linePaySuceed=" + linePaySuceed +
				", has_return=" + has_return +
				", sourceGroup=" + sourceGroup +
				", sendAddress=" + sendAddress +
				", deliverMan=" + deliverMan +
				", isFreight=" + isFreight +
				", phone=" + phone +
				']';
	}
}
