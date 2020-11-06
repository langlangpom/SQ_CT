package com.evian.sqct.bean.pay;

import javax.validation.constraints.NotNull;

/**
 * ClassName:SddPayInputDTO
 * Package:com.evian.sqct.bean.pay
 * Description:水叮咚提现DTO
 *
 * @Date:2020/7/24 15:32
 * @Author:XHX
 */
public class SddPayInputDTO {
    @NotNull
    private Integer CashOutID;
    @NotNull
    private Double payMoney;
    @NotNull
    private String createrUser;
    @NotNull
    private String ip;
    /** 0=发起提现，1=线下自己付钱 -2=拒绝 */
    @NotNull
    private Integer state;
    /** 说明或原因 */
    @NotNull
    private String remark;

    public Integer getCashOutID() {
        return CashOutID;
    }

    public void setCashOutID(Integer cashOutID) {
        CashOutID = cashOutID;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getCreaterUser() {
        return createrUser;
    }

    public void setCreaterUser(String createrUser) {
        this.createrUser = createrUser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SddPayInputDTO [" +
                "CashOutID=" + CashOutID +
                ", payMoney=" + payMoney +
                ", createrUser=" + createrUser +
                ", ip=" + ip +
                ", state=" + state +
                ", remark=" + remark +
                ']';
    }
}
