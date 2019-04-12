package com.evian.sqct.bean.order;

import java.util.Date;

/**
 * @date   2019年2月27日 上午11:18:54
 * @author XHX
 * @Description 订单商品详情
 */
public class OrderDetail {

	private Integer id;
	private Integer orderId;
	private Integer pid;
	private Integer number;
	private Double price;
	private Double total;
	private String settlementType;
	private Integer integral;
	private String genre;
	private Boolean ifEvaluate;
	private String voucherCode;
	private Double voucherMoney;
	private Integer activityId;
	private Date beginDate;
	private Date endDate;
	private Boolean hashTicket;
	private Integer typeId;
	private String typeName;
	private Integer comboPid;
	private Integer ifKouJian;
	private String pname;
	private String pictureUrl;
	private Double vipPrice;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Boolean getIfEvaluate() {
		return ifEvaluate;
	}
	public void setIfEvaluate(Boolean ifEvaluate) {
		this.ifEvaluate = ifEvaluate;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Double getVoucherMoney() {
		return voucherMoney;
	}
	public void setVoucherMoney(Double voucherMoney) {
		this.voucherMoney = voucherMoney;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getHashTicket() {
		return hashTicket;
	}
	public void setHashTicket(Boolean hashTicket) {
		this.hashTicket = hashTicket;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getComboPid() {
		return comboPid;
	}
	public void setComboPid(Integer comboPid) {
		this.comboPid = comboPid;
	}
	public Integer getIfKouJian() {
		return ifKouJian;
	}
	public void setIfKouJian(Integer ifKouJian) {
		this.ifKouJian = ifKouJian;
	}
	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", orderId=" + orderId + ", pid=" + pid + ", number=" + number + ", price="
				+ price + ", total=" + total + ", settlementType=" + settlementType + ", integral=" + integral
				+ ", genre=" + genre + ", ifEvaluate=" + ifEvaluate + ", voucherCode=" + voucherCode + ", voucherMoney="
				+ voucherMoney + ", activityId=" + activityId + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", hashTicket=" + hashTicket + ", typeId=" + typeId + ", typeName=" + typeName + ", comboPid="
				+ comboPid + ", ifKouJian=" + ifKouJian + ", pname=" + pname + ", pictureUrl=" + pictureUrl
				+ ", vipPrice=" + vipPrice + "]";
	}
}
