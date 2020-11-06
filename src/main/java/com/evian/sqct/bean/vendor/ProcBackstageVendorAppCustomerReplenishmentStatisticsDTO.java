package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO
 * Package:com.evian.sqct.bean.vendor
 * Description:Proc_Backstage_vendor_AppCustomer_replenishment_statistics
 *
 * @Date:2020/6/8 15:07
 * @Author:XHX
 */
public class ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO {

    @NotNull
	private String beginTime;
    @NotNull
	private String endTime;
    @NotNull
	private Integer eid;
    @NotNull
	private String account;

    private String productName;

    private String productCode;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "ProcBackstageVendorAppCustomerReplenishmentStatisticsDTO [" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", eid=" + eid +
                ", account=" + account +
                ", productName=" + productName +
                ", productCode=" + productCode +
                ']';
    }
}
