package com.evian.sqct.bean.order;

import java.io.Serializable;
import java.util.Date;

/**
 * @date   2019年4月2日 上午11:29:10
 * @author XHX
 * @Description e_order_logistics 订单物流
 */
public class EOrderLogistics implements Serializable{

	private static final long serialVersionUID = 4258180674429445732L;
	
	@Override
	public String toString() {
		return "EOrderLogistics [logisticsId=" + logisticsId + ", logisticsType=" + logisticsType
				+ ", logisticsDescribe=" + logisticsDescribe + ", contactsId=" + contactsId + ", contacts=" + contacts
				+ ", phone=" + phone + ", photo=" + photo + ", dateCreated=" + dateCreated + ", dataReson=" + dataReson
				+ ", WEBID=" + WEBID + "]";
	}
	private Integer logisticsId;		// 物流ID
	private Integer logisticsType;		// 物流信息类别(1:订货单据;  2:售后服务;  3:投诉单据)
	private String logisticsDescribe;	// 物流描述
	private Integer contactsId;			// 相关职员ID
	private String contacts;			// 相关人员
	private String phone;				// 联系电话
	private String photo;				// 配送员头像
	private Date dateCreated;			// 录入日期
	private Integer dataReson;			// 数据来源 0:业务系统  1:水叮咚
	private Integer WEBID;				// ERP端用户ID
	public Integer getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}
	public Integer getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(Integer logisticsType) {
		this.logisticsType = logisticsType;
	}
	public String getLogisticsDescribe() {
		return logisticsDescribe;
	}
	public void setLogisticsDescribe(String logisticsDescribe) {
		this.logisticsDescribe = logisticsDescribe;
	}
	public Integer getContactsId() {
		return contactsId;
	}
	public void setContactsId(Integer contactsId) {
		this.contactsId = contactsId;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getDataReson() {
		return dataReson;
	}
	public void setDataReson(Integer dataReson) {
		this.dataReson = dataReson;
	}
	public Integer getWEBID() {
		return WEBID;
	}
	public void setWEBID(Integer wEBID) {
		WEBID = wEBID;
	}
	
	
}
