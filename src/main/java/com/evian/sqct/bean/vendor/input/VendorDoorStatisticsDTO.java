package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:VendorDoorStatisticsDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:vendorDoorStatistics
 *
 * @Date:2020/8/20 11:03
 * @Author:XHX
 */
public class VendorDoorStatisticsDTO {
    @NotNull
    private Integer eid;
    private Integer accountId;
    private Integer communityId;
    private Integer shopId;
    private Integer mainboardType;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getMainboardType() {
        return mainboardType;
    }

    public void setMainboardType(Integer mainboardType) {
        this.mainboardType = mainboardType;
    }

    @Override
    public String toString() {
        return "VendorDoorStatisticsDTO [" +
                "eid=" + eid +
                ", accountId=" + accountId +
                ", communityId=" + communityId +
                ", shopId=" + shopId +
                ", mainboardType=" + mainboardType +
                ']';
    }
}
