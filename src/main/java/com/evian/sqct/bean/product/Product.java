package com.evian.sqct.bean.product;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

public class Product extends PagingPojo {
	@NotNull
	private Integer eid;
	private Integer pid;
	private String pname;
	private String pcode;
	private String property;
	private Boolean ifHot;
	private Integer status;
	private Boolean enabled;
	private Integer cid;
	private Integer shopId;
	private String createUser;
	private String eName;
	private Boolean isRelevance;
	private Boolean ifTicket;
	private Boolean hashTicket;
	private Integer sortTyoe;
	private String evianPName;
	private Integer isSettingTyop1;
	private Boolean isSettingTyop2;
	private Boolean isSettingEarning;
	private Boolean ifpledge;
	private Boolean isAutoAudit;
	private Boolean isPromotion;
	private Boolean linePay;
	private Boolean recProduct;
	private Integer freightType;
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
	public Boolean getIsSettingTyop2() {
		return isSettingTyop2;
	}
	public void setIsSettingTyop2(Boolean isSettingTyop2) {
		this.isSettingTyop2 = isSettingTyop2;
	}
	public Integer getIsSettingTyop1() {
		return isSettingTyop1;
	}
	public void setIsSettingTyop1(Integer isSettingTyop1) {
		this.isSettingTyop1 = isSettingTyop1;
	}
	public Boolean getIsSettingEarning() {
		return isSettingEarning;
	}
	public void setIsSettingEarning(Boolean isSettingEarning) {
		this.isSettingEarning = isSettingEarning;
	}
	public Boolean getIfpledge() {
		return ifpledge;
	}
	public void setIfpledge(Boolean ifpledge) {
		this.ifpledge = ifpledge;
	}
	public Boolean getIsAutoAudit() {
		return isAutoAudit;
	}
	public void setIsAutoAudit(Boolean isAutoAudit) {
		this.isAutoAudit = isAutoAudit;
	}
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(Boolean promotion) {
		isPromotion = promotion;
	}
	public Boolean getLinePay() {
		return linePay;
	}
	public void setLinePay(Boolean linePay) {
		this.linePay = linePay;
	}
	public Boolean getRecProduct() {
		return recProduct;
	}
	public void setRecProduct(Boolean recProduct) {
		this.recProduct = recProduct;
	}
	public Integer getFreightType() {
		return freightType;
	}
	public void setFreightType(Integer freightType) {
		this.freightType = freightType;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", pcode=" + pcode + ", property=" + property + ", ifHot="
				+ ifHot + ", status=" + status + ", enabled=" + enabled + ", eid=" + eid + ", cid=" + cid + ", shopId="
				+ shopId + ", createUser=" + createUser + ", eName=" + eName + ", isRelevance=" + isRelevance
				+ ", ifTicket=" + ifTicket + ", hashTicket=" + hashTicket + ", sortTyoe=" + sortTyoe + ", evianPName="
				+ evianPName + ", isSettingTyop1=" + isSettingTyop1 + ", isSettingTyop2=" + isSettingTyop2
				+ ", isSettingEarning=" + isSettingEarning + ", ifpledge=" + ifpledge + ", isAutoAudit=" + isAutoAudit
				+ ", isPromotion=" + isPromotion+ ", linePay=" + linePay + ", recProduct=" + recProduct+ ", freightType=" + freightType
				+ ", PageIndex=" + PageIndex + ", PageSize=" + PageSize + ", IsSelectAll=" + IsSelectAll + "]";
	}
}
