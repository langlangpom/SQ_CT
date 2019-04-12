package com.evian.sqct.bean.vendor;

import java.sql.Timestamp;

public class VendorMainboardInstruct {

	private Long instructId;
	private Integer bmId;
	private String mainboardNo;
	private Integer doorIndex;
	private String Instruct;
	private Integer InstructType;
	private Boolean InstructSuc;
	private Timestamp dateCreated;
	private Timestamp lockDate;
	public Long getInstructId() {
		return instructId;
	}
	public void setInstructId(Long instructId) {
		this.instructId = instructId;
	}
	public Integer getBmId() {
		return bmId;
	}
	public void setBmId(Integer bmId) {
		this.bmId = bmId;
	}
	public String getMainboardNo() {
		return mainboardNo;
	}
	public void setMainboardNo(String mainboardNo) {
		this.mainboardNo = mainboardNo;
	}
	public Integer getDoorIndex() {
		return doorIndex;
	}
	public void setDoorIndex(Integer doorIndex) {
		this.doorIndex = doorIndex;
	}
	public String getInstruct() {
		return Instruct;
	}
	public void setInstruct(String instruct) {
		Instruct = instruct;
	}
	public Integer getInstructType() {
		return InstructType;
	}
	public void setInstructType(Integer instructType) {
		InstructType = instructType;
	}
	public Boolean getInstructSuc() {
		return InstructSuc;
	}
	public void setInstructSuc(Boolean instructSuc) {
		InstructSuc = instructSuc;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getLockDate() {
		return lockDate;
	}
	public void setLockDate(Timestamp lockDate) {
		this.lockDate = lockDate;
	}
	@Override
	public String toString() {
		return "VendorMainboardInstruct [instructId=" + instructId + ", bmId="
				+ bmId + ", mainboardNo=" + mainboardNo + ", doorIndex="
				+ doorIndex + ", Instruct=" + Instruct + ", InstructType="
				+ InstructType + ", InstructSuc=" + InstructSuc
				+ ", dateCreated=" + dateCreated + ", lockDate=" + lockDate
				+ "]";
	}
	
	
}
