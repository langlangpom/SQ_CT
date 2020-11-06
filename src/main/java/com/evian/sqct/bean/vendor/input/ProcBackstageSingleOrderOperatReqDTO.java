package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageSingleOrderOperatReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/9/14 14:39
 * @Author:XHX
 */
public class ProcBackstageSingleOrderOperatReqDTO {

    @NotNull
    private Integer eid;
    @NotNull
    private Integer clientId;
    @NotNull
    private Integer productId;
    @NotNull
    private Integer productQty;
    @NotNull
    private Double orderMoney;
    @NotNull
    private Integer did;
    @NotNull
    private String sendAddress;
    @NotNull
    private String phone;
    @NotNull
    private String contacts;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductQty() {
        return productQty;
    }

    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ProcBackstageSingleOrderOperatReqDTO [" +
                "eid=" + eid +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", productQty=" + productQty +
                ", orderMoney=" + orderMoney +
                ", did=" + did +
                ", sendAddress=" + sendAddress +
                ", phone=" + phone +
                ", contacts=" + contacts +
                ']';
    }
}
