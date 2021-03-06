package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;

/**
 * ClassName:VendorAppMerchantAccountProduct
 * Package:com.evian.sqct.bean.vendor
 * Description:vendor_appMerchant_account_product
 * 售货机对应账号常用商品
 *
 * @Date:2020/11/3 14:20
 * @Author:XHX
 */
public class VendorAppMerchantAccountProduct {
    @NotNull
    private Integer accountId;
    @NotNull
    private Integer pid;
    private Integer sortId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "VendorAppMerchantAccountProduct [" +
                "accountId=" + accountId +
                ", pid=" + pid +
                ", sortId=" + sortId +
                ']';
    }
}
