package com.evian.sqct.bean.vendor;

import java.util.Date;

/**
 * @date   2018年11月15日 下午3:28:32
 * @author XHX
 * @Description 广告价格
 */
public class AdLedPrice {

	private Integer priceId;
	
	private Integer minCharQuantity;
	private Integer maxCharQuantity;
	private Double price;
	private String creater;
	private Date createTime;
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public Integer getMinCharQuantity() {
		return minCharQuantity;
	}
	public void setMinCharQuantity(Integer minCharQuantity) {
		this.minCharQuantity = minCharQuantity;
	}
	public Integer getMaxCharQuantity() {
		return maxCharQuantity;
	}
	public void setMaxCharQuantity(Integer maxCharQuantity) {
		this.maxCharQuantity = maxCharQuantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "AdLedPrice [priceId=" + priceId + ", minCharQuantity=" + minCharQuantity + ", maxCharQuantity="
				+ maxCharQuantity + ", price=" + price + ", creater=" + creater + ", createTime=" + createTime + "]";
	}
	
}
