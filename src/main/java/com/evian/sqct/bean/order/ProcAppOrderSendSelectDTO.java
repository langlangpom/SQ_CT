package com.evian.sqct.bean.order;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:ProcAppOrderSendSelect
 * Package:com.evian.sqct.bean.order
 * Description:PROC_APP_orderSend_SELECT 传参
 *
 * @Date:2020/5/15 9:51
 * @Author:XHX
 */
public class ProcAppOrderSendSelectDTO extends PagingPojo implements Serializable {

    private static final long serialVersionUID = -2623416477546693190L;

    @NotNull
    private Integer eid;
    @NotNull
    private Integer shopId;
    private Integer accountId;
    private Integer status;
    private Integer payMode;
    private String orderNo;
    private Boolean isTicket;
    /** 是否查询包含退款、退单的单据 */
    private Boolean ifIncludeChargeback;

    public ProcAppOrderSendSelectDTO() {

    }

    public ProcAppOrderSendSelectDTO(Integer eid, Integer shopId, Integer accountId, String beginTime, String endTime, Integer status, Integer payMode, String orderNo, Boolean isTicket, Integer iRows, Integer pageSize) {
        this.eid = eid;
        this.shopId = shopId;
        this.accountId = accountId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.status = status;
        this.payMode = payMode;
        this.orderNo = orderNo;
        this.isTicket = isTicket;
        this.iRows = iRows;
        this.PageSize = pageSize;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getIsTicket() {
        return isTicket;
    }

    public Boolean getIfIncludeChargeback() {
        return ifIncludeChargeback;
    }

    public void setIfIncludeChargeback(Boolean ifIncludeChargeback) {
        this.ifIncludeChargeback = ifIncludeChargeback;
    }

    public void setIsTicket(Boolean isTicket) {
        this.isTicket = isTicket;
    }

    @Override
    public String toString() {
        return "ProcAppOrderSendSelectDTO [" +
                "eid=" + eid +
                ", shopId=" + shopId +
                ", accountId=" + accountId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", payMode=" + payMode +
                ", orderNo=" + orderNo +
                ", isTicket=" + isTicket +
                ", ifIncludeChargeback=" + ifIncludeChargeback +
                ", PageSize=" + PageSize +
                ", iRows=" + iRows +
                ']';
    }
}
