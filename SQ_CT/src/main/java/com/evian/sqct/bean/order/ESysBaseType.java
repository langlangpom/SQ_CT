package com.evian.sqct.bean.order;

import java.io.Serializable;
import java.util.Date;

/**
 * @date   2019年3月21日 下午2:28:05
 * @author XHX
 * @Description e_sys_base_type 取消订单原因
 */
public class ESysBaseType implements Serializable{

	private static final long serialVersionUID = -2228821479711616981L;

	private Integer typeId;
	private Integer eid;
	private String typeName;
	private String typeClass;
	private String typeRemark;
	private Date createTime;
	private String creator;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}
	public String getTypeRemark() {
		return typeRemark;
	}
	public void setTypeRemark(String typeRemark) {
		this.typeRemark = typeRemark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "ESysBaseType [typeId=" + typeId + ", eid=" + eid + ", typeName=" + typeName + ", typeClass=" + typeClass
				+ ", typeRemark=" + typeRemark + ", createTime=" + createTime + ", creator=" + creator + "]";
	}
}
