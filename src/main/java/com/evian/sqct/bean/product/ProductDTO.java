package com.evian.sqct.bean.product;

import java.math.BigDecimal;

/**
 * @date   2019年5月17日 下午4:24:48
 * @author XHX
 * @Description 企业上架的商品
 */
public class ProductDTO {

	private Integer pid;
	private String pname;
	private Integer eid;
	private BigDecimal price;
	private BigDecimal vipPrice;
	private String pictureUrl;
	private String pcode;
	private String unit;
	private Boolean ifHot;
	private Short status;
	private Boolean enabled;
	private Integer brandId;
	private String brandName;
	private String synopsis;
	
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Boolean getIfHot() {
		return ifHot;
	}
	public void setIfHot(Boolean ifHot) {
		this.ifHot = ifHot;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	@Override
	public String toString() {
		return "ProductDTO [pid=" + pid + ", pname=" + pname + ", eid=" + eid + ", price=" + price + ", vipPrice="
				+ vipPrice + ", pictureUrl=" + pictureUrl + ", pcode=" + pcode + ", unit=" + unit + ", ifHot=" + ifHot
				+ ", status=" + status + ", enabled=" + enabled + ", brandId=" + brandId + ", brandName=" + brandName
				+ ", synopsis=" + synopsis + "]";
	}
}
