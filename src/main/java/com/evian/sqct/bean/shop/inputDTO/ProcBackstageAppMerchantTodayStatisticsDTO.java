package com.evian.sqct.bean.shop.inputDTO;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageAppMerchantTodayStatisticsDTO
 * Package:com.evian.sqct.bean.shop.inputDTO
 * Description:Proc_Backstage_appMerchant_Today_statistics
 *
 * @Date:2020/6/29 9:30
 * @Author:XHX
 */
public class ProcBackstageAppMerchantTodayStatisticsDTO  {
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer shopId;
    @NotNull
    private String  beginTime;
    @NotNull
    private String  endTime;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    @Override
    public String toString() {
        return "ProcBackstageAppMerchantTodayStatisticsDTO [" +
                "accountId=" + accountId +
                ", shopId=" + shopId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
