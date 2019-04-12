package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

/**
 * @date   2018年11月6日 上午10:07:58
 * @author XHX
 * @Description 省份
 */
public class GisProvince {

	private Integer provinceId;
	
	private String provinceName;
	
	private Timestamp dateCreated;

	@Override
	public String toString() {
		return "GisProvince [provinceId=" + provinceId + ", provinceName=" + provinceName + ", dateCreated="
				+ dateCreated + "]";
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
}
