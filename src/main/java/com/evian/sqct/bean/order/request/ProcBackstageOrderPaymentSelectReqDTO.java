package com.evian.sqct.bean.order.request;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageOrderPaymentSelectReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 17:53
 * @Author:XHX
 */
public class ProcBackstageOrderPaymentSelectReqDTO extends PagingPojo {
    private Integer orderId;
    private Integer billSource = 0;

    public Integer getBillSource() {
        return billSource;
    }

    public void setBillSource(Integer billSource) {
        this.billSource = billSource;
    }

    public ProcBackstageOrderPaymentSelectReqDTO(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ProcBackstageOrderPaymentSelectReqDTO [" +
                "orderId=" + orderId +
                "billSource=" + billSource +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
