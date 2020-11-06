package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:VendorStaffScanCodeLocationReqDTO
 * Package:com.evian.sqct.bean.vendor
 * Description:vendor_staff_scan_code_location
 *
 * @Date:2020/6/10 15:03
 * @Author:XHX
 */
public class VendorStaffScanCodeLocationReqDTO implements Serializable {
    private static final long serialVersionUID = -7893083953299387972L;
    @NotNull
    private Integer mainboardId;
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer communityId;
    @NotNull
    private Integer eid;
    @NotNull
    private String location;

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "VendorStaffScanCodeLocationReqDTO [" +
                "mainboardId=" + mainboardId +
                ", accountId=" + accountId +
                ", communityId=" + communityId +
                ", eid=" + eid +
                ", location=" + location +
                ']';
    }


}
