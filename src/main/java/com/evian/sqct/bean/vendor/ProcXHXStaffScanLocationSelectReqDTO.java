package com.evian.sqct.bean.vendor;

import com.evian.sqct.bean.baseBean.PagingPojo;

import java.io.Serializable;

/**
 * ClassName:ProcXHXStaffScanLocationSelectReqDTO
 * Package:com.evian.sqct.bean.vendor
 * Description:Proc_XHX_staff_scan_location_select
 *
 * @Date:2020/6/10 16:07
 * @Author:XHX
 */
public class ProcXHXStaffScanLocationSelectReqDTO extends PagingPojo implements Serializable {

    private static final long serialVersionUID = 3082031476041651128L;
    private Integer accountId;
    private Integer eid;
    private String account;
    private Integer communityId;
    private String communityName;
    private Integer mainboardId;
    private String mainboardNo;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public String getMainboardNo() {
        return mainboardNo;
    }

    public void setMainboardNo(String mainboardNo) {
        this.mainboardNo = mainboardNo;
    }

    @Override
    public String toString() {
        return "ProcXHXStaffScanLocationSelectReqDTO [" +
                "accountId=" + accountId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", eid=" + eid +
                ", account=" + account +
                ", communityId=" + communityId +
                ", communityName=" + communityName +
                ", mainboardId=" + mainboardId +
                ", mainboardNo=" + mainboardNo +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }
}
