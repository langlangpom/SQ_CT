package com.evian.sqct.bean.enterprise;

import java.io.Serializable;
import java.sql.Date;

/**
 * ClassName:EEarningsSubtractWanlaiWithdrawLog
 * Package:com.evian.sqct.bean.enterprise.writer
 * Description:e_earnings_subtract_wanlai_withdraw_log
 *
 * @Date:2020/7/24 14:53
 * @Author:XHX
 */
public class EEarningsSubtractWanlaiWithdrawLog implements Serializable {
    private static final long serialVersionUID = -4510881728589896171L;
    private Integer id;
    private Integer wangLaiId;
    private String appId;
    private String openId;
    private String paymentNo;
    private Double payMoney;
    private String result;
    private String remark;
    private Date createTime;
    private String createUser;
    private Integer logType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWangLaiId() {
        return wangLaiId;
    }

    public void setWangLaiId(Integer wangLaiId) {
        this.wangLaiId = wangLaiId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    @Override
    public String toString() {
        return "EEarningsSubtractWanlaiWithdrawLog [" +
                "id=" + id +
                ", wangLaiId=" + wangLaiId +
                ", appId=" + appId +
                ", openId=" + openId +
                ", paymentNo=" + paymentNo +
                ", payMoney=" + payMoney +
                ", result=" + result +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", logType=" + logType +
                ']';
    }
}
