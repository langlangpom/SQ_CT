package com.evian.sqct.bean.vendor;

public class VendorShopContainer {

	private String mainboardNo;
	
	private String mainboardNoMD5;
	
	private String housesName;
	
	private String address;
	
	private String location;
	
	private String containerCode;
	
	private Boolean isBind;

	
	
	public String getMainboardNo() {
		return mainboardNo;
	}



	public void setMainboardNo(String mainboardNo) {
		this.mainboardNo = mainboardNo;
	}



	public String getMainboardNoMD5() {
		return mainboardNoMD5;
	}



	public void setMainboardNoMD5(String mainboardNoMD5) {
		this.mainboardNoMD5 = mainboardNoMD5;
	}



	public String getHousesName() {
		return housesName;
	}



	public void setHousesName(String housesName) {
		this.housesName = housesName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public String getContainerCode() {
		return containerCode;
	}



	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}



	public Boolean getIsBind() {
		return isBind;
	}



	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}



	@Override
	public String toString() {
		return "VendorShopContainer [mainboardNo=" + mainboardNo
				+ ", mainboardNoMD5=" + mainboardNoMD5 + ", housesName="
				+ housesName + ", address=" + address + ", location="
				+ location + ", containerCode=" + containerCode + ", isBind="
				+ isBind + "]";
	}
}
