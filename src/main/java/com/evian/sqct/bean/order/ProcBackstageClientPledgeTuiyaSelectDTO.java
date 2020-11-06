package com.evian.sqct.bean.order;

import com.evian.sqct.bean.baseBean.PagingPojo;

import java.io.Serializable;

/**
 * ClassName:ProcBackstageClientPledgeTuiyaSelectDTO
 * Package:com.evian.sqct.bean.order
 * Description:Proc_Backstage_client_pledge_tuiya_select
 *
 * @Date:2020/6/3 16:10
 * @Author:XHX
 */
public class ProcBackstageClientPledgeTuiyaSelectDTO extends PagingPojo implements Serializable {

    private static final long serialVersionUID = -5989798190283281619L;

    private Integer xid;
    private Integer eid;
    private String account;
    private Integer tyState;
    private Boolean isAxceedOneYear;
    private Integer auditType;
    private String auditBeginTime;
    private String auditEndTime;
    private Integer transferStatus;
    private Boolean isTransfer = true;

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getTyState() {
        return tyState;
    }

    public void setTyState(Integer tyState) {
        this.tyState = tyState;
    }

    public Boolean getIsAxceedOneYear() {
        return isAxceedOneYear;
    }

    public void setIsAxceedOneYear(Boolean isAxceedOneYear) {
        this.isAxceedOneYear = isAxceedOneYear;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public String getAuditBeginTime() {
        return auditBeginTime;
    }

    public void setAuditBeginTime(String auditBeginTime) {
        this.auditBeginTime = auditBeginTime;
    }

    public String getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(String auditEndTime) {
        this.auditEndTime = auditEndTime;
    }


    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Boolean getIsTransfer() {
        return isTransfer;
    }

    @Override
    public String toString() {
        return "ProcBackstageClientPledgeTuiyaSelectDTO [" +
                "xid=" + xid +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", eid=" + eid +
                ", account=" + account +
                ", tyState=" + tyState +
                ", isAxceedOneYear=" + isAxceedOneYear +
                ", auditType=" + auditType +
                ", auditBeginTime=" + auditBeginTime +
                ", auditEndTime=" + auditEndTime +
                ", transferStatus=" + transferStatus +
                ", isTransfer=" + isTransfer +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }
}
