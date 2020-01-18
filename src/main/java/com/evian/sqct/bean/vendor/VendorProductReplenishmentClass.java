package com.evian.sqct.bean.vendor;

import java.util.Date;

/**
 * @date   2019年3月3日 下午5:24:46
 * @author XHX
 * @Description 补货分类表
 */
public class VendorProductReplenishmentClass {

	private Integer replenishmentClassId;	// 补货类型id
	private Integer eid;					// 企业id
	private String className;				// 分类名称
	private Integer sortId;					// 序号
	private String remark;					// 备注
	private Date createTime;				// 创建时间
	private String creater;					// 创建人
	public Integer getReplenishmentClassId() {
		return replenishmentClassId;
	}
	public void setReplenishmentClassId(Integer replenishmentClassId) {
		this.replenishmentClassId = replenishmentClassId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Override
	public String toString() {
		return "VendorProductReplenishmentClass [replenishmentClassId=" + replenishmentClassId + ", eid=" + eid
				+ ", className=" + className + ", sortId=" + sortId + ", remark=" + remark + ", createTime="
				+ createTime + ", creater=" + creater + "]";
	}
	
	
}
