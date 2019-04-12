package com.evian.sqct.bean.product;

import java.sql.Timestamp;

public class ProductClass {

	private Long row;
	private Integer cid;
	private String classNo;
	private String className;
	private String description;
	private Integer classSort;
	private String property;
	private Timestamp dateCreated;
	private String createUser;
	private Timestamp dateUpdated;
	private String updateUser;
	private Boolean enabled;
	private Integer eid;
	private Boolean sysClass;
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getClassSort() {
		return classSort;
	}
	public void setClassSort(Integer classSort) {
		this.classSort = classSort;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Boolean getSysClass() {
		return sysClass;
	}
	public void setSysClass(Boolean sysClass) {
		this.sysClass = sysClass;
	}
	@Override
	public String toString() {
		return "ProductClass [row=" + row + ", cid=" + cid + ", classNo="
				+ classNo + ", className=" + className + ", description="
				+ description + ", classSort=" + classSort + ", property="
				+ property + ", dateCreated=" + dateCreated + ", createUser="
				+ createUser + ", dateUpdated=" + dateUpdated + ", updateUser="
				+ updateUser + ", enabled=" + enabled + ", eid=" + eid
				+ ", sysClass=" + sysClass + "]";
	}
	
}
