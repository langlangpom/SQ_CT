package com.evian.sqct.bean.order;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderModel {

	private Long row;
	private Integer orderId;
	private String orderGroup;
	private String orderNo;
	private Integer eid;
	private Integer shopId;
	private Integer clientId;
	private Integer did;
	private String sendAddress;
	private String phone;
	private String contacts;
	private Timestamp dateCreated;
	private Timestamp appointmentTime;
	private String sendRemark;
	private String account;
	private String sdkType;
	private String mobileType;
	private String mobileIMEl;
	private String appVer;
	private String orderRemark;
	private Integer ticketCount;
	private BigDecimal cashTotal;
	private BigDecimal linePayTotal;
	private Boolean linePaySuceed;
	private Short payMode;
	private Integer status;
	private Boolean ifEvaluate;
	private Boolean ifMutual;
	private Timestamp mutualTime;
	private String mutualId;
	private Timestamp deliverTime;
	private String deliverMan;
	private Timestamp completeTime;
	private Integer privilegeId;
	private BigDecimal discountMoney;
	private String discountDescribe;
	private Boolean has_invoice;
	private String shopName;
	private String eName;
	private BigDecimal receivableTotal;
	private Timestamp cancelTime;
	private String cancelReason;
	private Boolean in_come;
	private Timestamp comeDate;
	private Boolean has_return;
	private BigDecimal paymentMoney;
	private Integer confirm_take;
	private Integer sourceGroup;
	private String manager_account;
	private String send_account;
	private String manager_remark;
	private String send_remark;
	private String send_name;
	private BigDecimal balanceTotal;
	private BigDecimal integralTotal;

	public BigDecimal getIntegralTotal() {
		return integralTotal;
	}
	public void setIntegralTotal(BigDecimal integralTotal) {
		this.integralTotal = integralTotal;
	}
	public BigDecimal getBalanceTotal() {
		return balanceTotal;
	}
	public void setBalanceTotal(BigDecimal balanceTotal) {
		this.balanceTotal = balanceTotal;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public Integer getSourceGroup() {
		return sourceGroup;
	}
	public void setSourceGroup(Integer sourceGroup) {
		this.sourceGroup = sourceGroup;
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderGroup() {
		return orderGroup;
	}
	public void setOrderGroup(String orderGroup) {
		this.orderGroup = orderGroup;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
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
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Timestamp appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getSendRemark() {
		return sendRemark;
	}
	public void setSendRemark(String sendRemark) {
		this.sendRemark = sendRemark;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSdkType() {
		return sdkType;
	}
	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	public String getMobileIMEl() {
		return mobileIMEl;
	}
	public void setMobileIMEl(String mobileIMEl) {
		this.mobileIMEl = mobileIMEl;
	}
	public String getAppVer() {
		return appVer;
	}
	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public Integer getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}
	public BigDecimal getCashTotal() {
		return cashTotal;
	}
	public void setCashTotal(BigDecimal cashTotal) {
		this.cashTotal = cashTotal;
	}
	public BigDecimal getLinePayTotal() {
		return linePayTotal;
	}
	public void setLinePayTotal(BigDecimal linePayTotal) {
		this.linePayTotal = linePayTotal;
	}
	public Boolean getLinePaySuceed() {
		return linePaySuceed;
	}
	public void setLinePaySuceed(Boolean linePaySuceed) {
		this.linePaySuceed = linePaySuceed;
	}
	public Short getPayMode() {
		return payMode;
	}
	public void setPayMode(Short payMode) {
		this.payMode = payMode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getIfEvaluate() {
		return ifEvaluate;
	}
	public void setIfEvaluate(Boolean ifEvaluate) {
		this.ifEvaluate = ifEvaluate;
	}
	public Boolean getIfMutual() {
		return ifMutual;
	}
	public void setIfMutual(Boolean ifMutual) {
		this.ifMutual = ifMutual;
	}
	public Timestamp getMutualTime() {
		return mutualTime;
	}
	public void setMutualTime(Timestamp mutualTime) {
		this.mutualTime = mutualTime;
	}
	public String getMutualId() {
		return mutualId;
	}
	public void setMutualId(String mutualId) {
		this.mutualId = mutualId;
	}
	public Timestamp getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Timestamp deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getDeliverMan() {
		return deliverMan;
	}
	public void setDeliverMan(String deliverMan) {
		this.deliverMan = deliverMan;
	}
	public Timestamp getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Timestamp completeTime) {
		this.completeTime = completeTime;
	}
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}
	public String getDiscountDescribe() {
		return discountDescribe;
	}
	public void setDiscountDescribe(String discountDescribe) {
		this.discountDescribe = discountDescribe;
	}
	public Boolean getHas_invoice() {
		return has_invoice;
	}
	public void setHas_invoice(Boolean has_invoice) {
		this.has_invoice = has_invoice;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public BigDecimal getReceivableTotal() {
		return receivableTotal;
	}
	public void setReceivableTotal(BigDecimal receivableTotal) {
		this.receivableTotal = receivableTotal;
	}
	public Timestamp getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Timestamp cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Boolean getIn_come() {
		return in_come;
	}
	public void setIn_come(Boolean in_come) {
		this.in_come = in_come;
	}
	public Timestamp getComeDate() {
		return comeDate;
	}
	public void setComeDate(Timestamp comeDate) {
		this.comeDate = comeDate;
	}
	public Boolean getHas_return() {
		return has_return;
	}
	public void setHas_return(Boolean has_return) {
		this.has_return = has_return;
	}
	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}
	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	public Integer getConfirm_take() {
		return confirm_take;
	}
	public void setConfirm_take(Integer confirm_take) {
		this.confirm_take = confirm_take;
	}

	public String getManager_account() {
		return manager_account;
	}

	public void setManager_account(String manager_account) {
		this.manager_account = manager_account;
	}

	public String getSend_account() {
		return send_account;
	}

	public void setSend_account(String send_account) {
		this.send_account = send_account;
	}

	public String getManager_remark() {
		return manager_remark;
	}

	public void setManager_remark(String manager_remark) {
		this.manager_remark = manager_remark;
	}

	public String getSend_remark() {
		return send_remark;
	}

	public void setSend_remark(String send_remark) {
		this.send_remark = send_remark;
	}
	@Override
	public String toString() {
		return "OrderModel{" +
				"row=" + row +
				", orderId=" + orderId +
				", orderGroup='" + orderGroup + '\'' +
				", orderNo='" + orderNo + '\'' +
				", eid=" + eid +
				", shopId=" + shopId +
				", clientId=" + clientId +
				", did=" + did +
				", sendAddress='" + sendAddress + '\'' +
				", phone='" + phone + '\'' +
				", contacts='" + contacts + '\'' +
				", dateCreated=" + dateCreated +
				", appointmentTime=" + appointmentTime +
				", sendRemark='" + sendRemark + '\'' +
				", account='" + account + '\'' +
				", sdkType='" + sdkType + '\'' +
				", mobileType='" + mobileType + '\'' +
				", mobileIMEl='" + mobileIMEl + '\'' +
				", appVer='" + appVer + '\'' +
				", orderRemark='" + orderRemark + '\'' +
				", ticketCount=" + ticketCount +
				", cashTotal=" + cashTotal +
				", linePayTotal=" + linePayTotal +
				", linePaySuceed=" + linePaySuceed +
				", payMode=" + payMode +
				", status=" + status +
				", ifEvaluate=" + ifEvaluate +
				", ifMutual=" + ifMutual +
				", mutualTime=" + mutualTime +
				", mutualId='" + mutualId + '\'' +
				", deliverTime=" + deliverTime +
				", deliverMan='" + deliverMan + '\'' +
				", completeTime=" + completeTime +
				", privilegeId=" + privilegeId +
				", discountMoney=" + discountMoney +
				", discountDescribe='" + discountDescribe + '\'' +
				", has_invoice=" + has_invoice +
				", shopName='" + shopName + '\'' +
				", eName='" + eName + '\'' +
				", receivableTotal=" + receivableTotal +
				", cancelTime=" + cancelTime +
				", cancelReason='" + cancelReason + '\'' +
				", in_come=" + in_come +
				", comeDate=" + comeDate +
				", has_return=" + has_return +
				", paymentMoney=" + paymentMoney +
				", confirm_take=" + confirm_take +
				", sourceGroup=" + sourceGroup +
				", manager_account='" + manager_account + '\'' +
				", send_account='" + send_account + '\'' +
				", manager_remark='" + manager_remark + '\'' +
				", send_remark='" + send_remark + '\'' +
				", balanceTotal='" + balanceTotal + '\'' +
				", integralTotal='" + integralTotal + '\'' +
				'}';
	}
}
