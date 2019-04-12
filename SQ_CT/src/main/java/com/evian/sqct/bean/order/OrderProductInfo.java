package com.evian.sqct.bean.order;

import java.math.BigDecimal;

public class OrderProductInfo {

	private Integer id;
	private Integer orderId;
	private Integer pid;
	private Integer number;
	private BigDecimal price;
	private BigDecimal total;
	private String settlementType;
	private Integer integral;
	private String genre;
	private Boolean ifEvaluate;
	private String voucherCode;
	private BigDecimal voucherMoney;
	private String pName;
	private String productPic;
	private String unit;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
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
	public BigDecimal getVoucherMoney() {
		return voucherMoney;
	}
	public void setVoucherMoney(BigDecimal voucherMoney) {
		this.voucherMoney = voucherMoney;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "OrderProductInfo [id=" + id + ", orderId=" + orderId + ", pid="
				+ pid + ", number=" + number + ", price=" + price + ", total="
				+ total + ", settlementType=" + settlementType + ", integral="
				+ integral + ", genre=" + genre + ", ifEvaluate=" + ifEvaluate
				+ ", voucherCode=" + voucherCode + ", voucherMoney="
				+ voucherMoney + ", pName=" + pName + ", productPic="
				+ productPic + ", unit=" + unit + "]";
	}
}
