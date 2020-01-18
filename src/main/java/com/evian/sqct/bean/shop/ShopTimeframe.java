package com.evian.sqct.bean.shop;

public class ShopTimeframe {

	private Integer id;
	private Integer eid;
	private Integer shopId;
	private String beginHour;
	private String endHour;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getBeginHour() {
		return beginHour;
	}
	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	@Override
	public String toString() {
		return "ShopTimeframe [id=" + id + ", eid=" + eid + ", shopId="
				+ shopId + ", beginHour=" + beginHour + ", endHour=" + endHour
				+ "]";
	}
}
