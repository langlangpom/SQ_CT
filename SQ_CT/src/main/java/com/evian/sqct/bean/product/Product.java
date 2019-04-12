package com.evian.sqct.bean.product;

public class Product {

	private Integer pid;
	private String pname;
	private String pcode;
	private String property;
	private Boolean ifHot;
	private Integer status;
	private Boolean enabled;
	private Integer eid;
	private Integer cid;
	private Integer shopId;
	private String createUser;
	private String eName;
	private Boolean isRelevance;
	private Boolean ifTicket;
	private Boolean hashTicket;
	private Integer sortTyoe;
	private String evianPName;
	private Boolean isSettingTyop1;
	private Boolean isSettingTyop2;
	private Integer pageIndex;
	private Integer pageSize;
	private Boolean isSelectAll;
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
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Boolean getIfHot() {
		return ifHot;
	}
	public void setIfHot(Boolean ifHot) {
		this.ifHot = ifHot;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public Boolean getIsRelevance() {
		return isRelevance;
	}
	public void setIsRelevance(Boolean isRelevance) {
		this.isRelevance = isRelevance;
	}
	public Boolean getIfTicket() {
		return ifTicket;
	}
	public void setIfTicket(Boolean ifTicket) {
		this.ifTicket = ifTicket;
	}
	public Boolean getHashTicket() {
		return hashTicket;
	}
	public void setHashTicket(Boolean hashTicket) {
		this.hashTicket = hashTicket;
	}
	public Integer getSortTyoe() {
		return sortTyoe;
	}
	public void setSortTyoe(Integer sortTyoe) {
		this.sortTyoe = sortTyoe;
	}
	public String getEvianPName() {
		return evianPName;
	}
	public void setEvianPName(String evianPName) {
		this.evianPName = evianPName;
	}
	public Boolean getIsSettingTyop1() {
		return isSettingTyop1;
	}
	public void setIsSettingTyop1(Boolean isSettingTyop1) {
		this.isSettingTyop1 = isSettingTyop1;
	}
	public Boolean getIsSettingTyop2() {
		return isSettingTyop2;
	}
	public void setIsSettingTyop2(Boolean isSettingTyop2) {
		this.isSettingTyop2 = isSettingTyop2;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getIsSelectAll() {
		return isSelectAll;
	}
	public void setIsSelectAll(Boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", pcode=" + pcode
				+ ", property=" + property + ", ifHot=" + ifHot + ", status="
				+ status + ", enabled=" + enabled + ", eid=" + eid + ", cid="
				+ cid + ", shopId=" + shopId + ", createUser=" + createUser
				+ ", eName=" + eName + ", isRelevance=" + isRelevance
				+ ", ifTicket=" + ifTicket + ", hashTicket=" + hashTicket
				+ ", sortTyoe=" + sortTyoe + ", evianPName=" + evianPName
				+ ", isSettingTyop1=" + isSettingTyop1 + ", isSettingTyop2="
				+ isSettingTyop2 + ", pageIndex=" + pageIndex + ", pageSize="
				+ pageSize + ", isSelectAll=" + isSelectAll + "]";
	}
}
