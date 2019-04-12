package com.evian.sqct.bean.product;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductModel {

	private Long row;
	private Integer pid;
	private String pname;
	private String pcode;
	private String unit;
	private String property;
	private BigDecimal price;
	private BigDecimal vipPrice;
	private Integer pictureId;
	private String describe;
	private Integer repertoryNum;
	private Integer salesNum;
	private Boolean ifHot;
	private Integer sort;
	private Integer grade;
	private Timestamp dateCreated;
	private String createUser;
	private Timestamp dateUpdated;
	private Short status;
	private Boolean enabled;
	private String weight;
	private String reason;
	private Integer brandId;
	private Integer eid;
	private String eName;
	private String brandName;
	private String pictureUrl;
	private Boolean ifTicket;
	private Integer sellTag;
	private String imageTextUrl;
	private Boolean hashTicket;
	private Boolean linePay;
	private Integer yw_spid;
	private Boolean ifpledge;
	private BigDecimal shareEarning;
	private BigDecimal procurementMoney;
	private BigDecimal baseSendMoney;
	private BigDecimal speedSendEarning;
	private BigDecimal evaluateGoodEarning;
	private BigDecimal evaluateBadEarning;
	private BigDecimal tuiGuangJingLiEarning;
	private BigDecimal ziLiaoWanZhengHaoPingEarning;
	private Boolean isSettingTyop1;
	private Boolean isSettingTyop2;
	private String evianPName;
	@Override
	public String toString() {
		return "ProductModel [row=" + row + ", pid=" + pid + ", pname=" + pname
				+ ", pcode=" + pcode + ", unit=" + unit + ", property="
				+ property + ", price=" + price + ", vipPrice=" + vipPrice
				+ ", pictureId=" + pictureId + ", describe=" + describe
				+ ", repertoryNum=" + repertoryNum + ", salesNum=" + salesNum
				+ ", ifHot=" + ifHot + ", sort=" + sort + ", grade=" + grade
				+ ", dateCreated=" + dateCreated + ", createUser=" + createUser
				+ ", dateUpdated=" + dateUpdated + ", status=" + status
				+ ", enabled=" + enabled + ", weight=" + weight + ", reason="
				+ reason + ", brandId=" + brandId + ", eid=" + eid + ", eName="
				+ eName + ", brandName=" + brandName + ", pictureUrl="
				+ pictureUrl + ", ifTicket=" + ifTicket + ", sellTag="
				+ sellTag + ", imageTextUrl=" + imageTextUrl + ", hashTicket="
				+ hashTicket + ", linePay=" + linePay + ", yw_spid=" + yw_spid
				+ ", ifpledge=" + ifpledge + ", shareEarning=" + shareEarning
				+ ", procurementMoney=" + procurementMoney + ", baseSendMoney="
				+ baseSendMoney + ", speedSendEarning=" + speedSendEarning
				+ ", evaluateGoodEarning=" + evaluateGoodEarning
				+ ", evaluateBadEarning=" + evaluateBadEarning
				+ ", tuiGuangJingLiEarning=" + tuiGuangJingLiEarning
				+ ", ziLiaoWanZhengHaoPingEarning="
				+ ziLiaoWanZhengHaoPingEarning + ", isSettingTyop1="
				+ isSettingTyop1 + ", isSettingTyop2=" + isSettingTyop2
				+ ", evianPName=" + evianPName + "]";
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getPictureId() {
		return pictureId;
	}
	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getRepertoryNum() {
		return repertoryNum;
	}
	public void setRepertoryNum(Integer repertoryNum) {
		this.repertoryNum = repertoryNum;
	}
	public Integer getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}
	public Boolean getIfHot() {
		return ifHot;
	}
	public void setIfHot(Boolean ifHot) {
		this.ifHot = ifHot;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Boolean getIfTicket() {
		return ifTicket;
	}
	public void setIfTicket(Boolean ifTicket) {
		this.ifTicket = ifTicket;
	}
	public Integer getSellTag() {
		return sellTag;
	}
	public void setSellTag(Integer sellTag) {
		this.sellTag = sellTag;
	}
	public String getImageTextUrl() {
		return imageTextUrl;
	}
	public void setImageTextUrl(String imageTextUrl) {
		this.imageTextUrl = imageTextUrl;
	}
	public Boolean getHashTicket() {
		return hashTicket;
	}
	public void setHashTicket(Boolean hashTicket) {
		this.hashTicket = hashTicket;
	}
	public Boolean getLinePay() {
		return linePay;
	}
	public void setLinePay(Boolean linePay) {
		this.linePay = linePay;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getYw_spid() {
		return yw_spid;
	}
	public void setYw_spid(Integer yw_spid) {
		this.yw_spid = yw_spid;
	}
	public Boolean getIfpledge() {
		return ifpledge;
	}
	public void setIfpledge(Boolean ifpledge) {
		this.ifpledge = ifpledge;
	}
	public BigDecimal getShareEarning() {
		return shareEarning;
	}
	public void setShareEarning(BigDecimal shareEarning) {
		this.shareEarning = shareEarning;
	}
	public BigDecimal getProcurementMoney() {
		return procurementMoney;
	}
	public void setProcurementMoney(BigDecimal procurementMoney) {
		this.procurementMoney = procurementMoney;
	}
	public BigDecimal getBaseSendMoney() {
		return baseSendMoney;
	}
	public void setBaseSendMoney(BigDecimal baseSendMoney) {
		this.baseSendMoney = baseSendMoney;
	}
	public BigDecimal getSpeedSendEarning() {
		return speedSendEarning;
	}
	public void setSpeedSendEarning(BigDecimal speedSendEarning) {
		this.speedSendEarning = speedSendEarning;
	}
	public BigDecimal getEvaluateGoodEarning() {
		return evaluateGoodEarning;
	}
	public void setEvaluateGoodEarning(BigDecimal evaluateGoodEarning) {
		this.evaluateGoodEarning = evaluateGoodEarning;
	}
	public BigDecimal getEvaluateBadEarning() {
		return evaluateBadEarning;
	}
	public void setEvaluateBadEarning(BigDecimal evaluateBadEarning) {
		this.evaluateBadEarning = evaluateBadEarning;
	}
	public BigDecimal getTuiGuangJingLiEarning() {
		return tuiGuangJingLiEarning;
	}
	public void setTuiGuangJingLiEarning(BigDecimal tuiGuangJingLiEarning) {
		this.tuiGuangJingLiEarning = tuiGuangJingLiEarning;
	}
	public BigDecimal getZiLiaoWanZhengHaoPingEarning() {
		return ziLiaoWanZhengHaoPingEarning;
	}
	public void setZiLiaoWanZhengHaoPingEarning(
			BigDecimal ziLiaoWanZhengHaoPingEarning) {
		this.ziLiaoWanZhengHaoPingEarning = ziLiaoWanZhengHaoPingEarning;
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
	public String getEvianPName() {
		return evianPName;
	}
	public void setEvianPName(String evianPName) {
		this.evianPName = evianPName;
	}
}
