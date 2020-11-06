package com.evian.sqct.bean.order;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:ProcAppOrderSendSaveLogisticsDTO
 * Package:com.evian.sqct.bean.order
 * Description:PROC_APP_orderSend_saveLogistics
 *
 * @Date:2020/5/19 13:33
 * @Author:XHX
 */

public class ProcAppOrderSendSaveLogisticsDTO implements Serializable {


    private static final long serialVersionUID = -7247005352832227921L;

    @NotNull
    private Integer orderId;
    @NotBlank
    private String logMsg;
    /** 来源 (1:前端用户  2:中台  3.水趣商户) */
    @NotNull
    private Integer dataReson = 3;
    @NotNull
    private String creater;
    private String orderNo;
    private Integer send_accountId;
    private Integer msgType;
    private String title = "配送通知";
    private String message = "订单状态变更:";

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public Integer getDataReson() {
        return dataReson;
    }

    public void setDataReson(Integer dataReson) {
        this.dataReson = dataReson;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getSend_accountId() {
        return send_accountId;
    }

    public void setSend_accountId(Integer send_accountId) {
        this.send_accountId = send_accountId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String combinationText() {
        return message+orderNo;
    }

    @Override
    public String toString() {
        return "ProcAppOrderSendSaveLogisticsDTO [" +
                "orderId=" + orderId +
                "title=" + title +
                ", logMsg=" + logMsg +
                ", dataReson=" + dataReson +
                ", creater=" + creater +
                ", orderNo=" + orderNo +
                ", send_accountId=" + send_accountId +
                ", msgType=" + msgType +
                ']';
    }
}
