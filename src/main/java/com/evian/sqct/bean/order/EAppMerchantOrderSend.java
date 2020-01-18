package com.evian.sqct.bean.order;

import java.io.Serializable;
import java.util.Date;

/**
 * @date   2019年6月9日 下午2:37:19
 * @author XHX
 * @Description 订单配送表
 */
public class EAppMerchantOrderSend implements Serializable{
	private static final long serialVersionUID = -9074988611921078906L;
	
	private Integer xid;
	private Integer manager_accountId;
	private Integer send_accountId;
	private Integer eid;
	private Integer shopId;
	private Integer orderId;
	private Date createTime;
	private String creater;
	private Integer send_status;
	private String remark;
	public Integer getXid() {
		return xid;
	}
	public void setXid(Integer xid) {
		this.xid = xid;
	}
	public Integer getManager_accountId() {
		return manager_accountId;
	}
	public void setManager_accountId(Integer manager_accountId) {
		this.manager_accountId = manager_accountId;
	}
	public Integer getSend_accountId() {
		return send_accountId;
	}
	public void setSend_accountId(Integer send_accountId) {
		this.send_accountId = send_accountId;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	public Integer getSend_status() {
		return send_status;
	}
	public void setSend_status(Integer send_status) {
		this.send_status = send_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "EAppMerchantOrderSend [xid=" + xid + ", manager_accountId=" + manager_accountId + ", send_accountId="
				+ send_accountId + ", eid=" + eid + ", shopId=" + shopId + ", orderId=" + orderId + ", createTime="
				+ createTime + ", creater=" + creater + ", send_status=" + send_status + ", remark=" + remark + "]";
	}

}
