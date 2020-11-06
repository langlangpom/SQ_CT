package com.evian.sqct.bean.vendor.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageSingleOrderSelectReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/9/15 9:17
 * @Author:XHX
 */
public class ProcBackstageSingleOrderSelectReqDTO extends PagingPojo {

    private Integer orderId;
    private Integer orderStatus;
    private String orderNo;
    private String account;
    private Integer clientId;
    private String openId;
    private String mainboardId;
    private Integer accountId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(String mainboardId) {
        this.mainboardId = mainboardId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "ProcBackstageSingleOrderSelectReqDTO [" +
                "orderId=" + orderId +
                ", orderStatus=" + orderStatus +
                ", mainboardId=" + mainboardId +
                ", orderNo=" + orderNo +
                ", account=" + account +
                ", accountId=" + accountId +
                ", clientId=" + clientId +
                ", openId=" + openId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
