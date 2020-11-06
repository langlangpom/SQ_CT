package com.evian.sqct.bean.order;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:ProcAppOrderSendSaveUPDTO
 * Package:com.evian.sqct.bean.order
 * Description:PROC_APP_orderSend_saveUP 传参
 *
 * @Date:2020/5/14 16:56
 * @Author:XHX
 */
public class ProcAppOrderSendSaveUPDTO implements Serializable {

    private static final long serialVersionUID = -2480966043848795581L;

    @NotNull
    private Integer sendAccountId;
    @NotNull
    private Integer managerAccountId;
    @NotNull
    private String orderSend;
    @NotNull
    private Integer sendStatus;
    private String remark;
    private String creater;
    @NotNull
    private String dataSource;

    public ProcAppOrderSendSaveUPDTO(){}

    public ProcAppOrderSendSaveUPDTO(Integer sendAccountId, Integer managerAccountId, String orderSend, Integer sendStatus, String remark, String creater, String dataSource) {
        this.sendAccountId = sendAccountId;
        this.managerAccountId = managerAccountId;
        this.orderSend = orderSend;
        this.sendStatus = sendStatus;
        this.remark = remark;
        this.creater = creater;
        this.dataSource = dataSource;
    }

    public Integer getSendAccountId() {
        return sendAccountId;
    }

    public void setSendAccountId(Integer sendAccountId) {
        this.sendAccountId = sendAccountId;
    }

    public Integer getManagerAccountId() {
        return managerAccountId;
    }

    public void setManagerAccountId(Integer managerAccountId) {
        this.managerAccountId = managerAccountId;
    }

    public JSONArray getOrderSend() {
        if(!StringUtils.isBlank(orderSend)){
            return JSONArray.parseArray(orderSend);
        }
        return null;
    }

    public void setOrderSend(String orderSend) {
        this.orderSend = orderSend;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        return "ProcAppOrderSendSaveUPDTO [" +
                "sendAccountId=" + sendAccountId +
                ", managerAccountId=" + managerAccountId +
                ", orderSend=" + orderSend +
                ", sendStatus=" + sendStatus +
                ", remark=" + remark +
                ", creater=" + creater +
                ", DataSource=" + dataSource +
                ']';
    }
}
