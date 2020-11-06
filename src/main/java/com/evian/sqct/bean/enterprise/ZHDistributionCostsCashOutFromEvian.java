package com.evian.sqct.bean.enterprise;

import java.io.Serializable;

/**
 * ClassName:ZHDistributionCostsCashOutFromEvian
 * Package:com.evian.sqct.bean.enterprise
 * Description:ZH_DistributionCosts_CashOut_fromEvian
 *
 * @Date:2020/7/24 15:38
 * @Author:XHX
 */
public class ZHDistributionCostsCashOutFromEvian implements Serializable {
    private static final long serialVersionUID = -674585640653739504L;
    private Integer CashOutID;
    private Integer eid;
    private Integer clientId;
    private Double nCashOut;
    private Boolean ifPayment;

    public Integer getCashOutID() {
        return CashOutID;
    }

    public void setCashOutID(Integer cashOutID) {
        CashOutID = cashOutID;
    }

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

    public Double getnCashOut() {
        return nCashOut;
    }

    public void setnCashOut(Double nCashOut) {
        this.nCashOut = nCashOut;
    }

    public Boolean getIfPayment() {
        return ifPayment;
    }

    public void setIfPayment(Boolean ifPayment) {
        this.ifPayment = ifPayment;
    }

    @Override
    public String toString() {
        return "ZHDistributionCostsCashOutFromEvian [" +
                "CashOutID=" + CashOutID +
                ", eid=" + eid +
                ", clientId=" + clientId +
                ", nCashOut=" + nCashOut +
                ", ifPayment=" + ifPayment +
                ']';
    }
}
