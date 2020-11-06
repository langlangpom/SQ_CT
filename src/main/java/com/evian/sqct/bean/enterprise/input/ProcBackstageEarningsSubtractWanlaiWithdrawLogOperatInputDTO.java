package com.evian.sqct.bean.enterprise.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO
 * Package:com.evian.sqct.bean.enterprise.input
 * Description:Proc_Backstage_earnings_subtract_wanlai_withdraw_log_operat
 *
 * @Date:2020/7/24 11:02
 * @Author:XHX
 */
public class ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO {
    @NotNull
    private Integer wangLaiId;
    @NotNull
    private String appId;
    @NotNull
    private String openId;
    @NotNull
    private String paymentNo;
    @NotNull
    private Double payMoney;
    @NotNull
    private String result;
    @NotNull
    private String remark;
    @NotNull
    private String createUser;
    @NotNull
    private Integer logType;

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
        return "ProcBackstageEarningsSubtractWanlaiWithdrawLogOperatInputDTO [" +
                "wangLaiId=" + wangLaiId +
                ", appId=" + appId +
                ", openId=" + openId +
                ", paymentNo=" + paymentNo +
                ", payMoney=" + payMoney +
                ", result=" + result +
                ", remark=" + remark +
                ", createUser=" + createUser +
                ", logType=" + logType +
                ']';
    }
}
