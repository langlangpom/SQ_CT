package com.evian.sqct.bean.enterprise.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO
 * Package:com.evian.sqct.bean.enterprise.input
 * Description:Proc_Backstage_ZH_DistributionCosts_CashOut_fromEvian_select
 *
 * @Date:2020/7/23 15:58
 * @Author:XHX
 */
public class ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO extends PagingPojo {

    private Integer eid;
    private Integer iStatus;
    private String sAccount;
    private Boolean ifPayment;
    private Boolean ifAudit;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getiStatus() {
        return iStatus;
    }

    public void setiStatus(Integer iStatus) {
        this.iStatus = iStatus;
    }

    public String getsAccount() {
        return sAccount;
    }

    public void setsAccount(String sAccount) {
        this.sAccount = sAccount;
    }

    public Boolean getIfPayment() {
        return ifPayment;
    }

    public void setIfPayment(Boolean ifPayment) {
        this.ifPayment = ifPayment;
    }

    public Boolean getIfAudit() {
        return ifAudit;
    }

    public void setIfAudit(Boolean ifAudit) {
        this.ifAudit = ifAudit;
    }

    @Override
    public String toString() {
        return "ProcBackstageZHDistributionCostsCashOutfromEvianSelectInputDTO [" +
                "eid=" + eid +
                ", iStatus=" + iStatus +
                ", sAccount=" + sAccount +
                ", ifPayment=" + ifPayment +
                ", ifAudit=" + ifAudit +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
