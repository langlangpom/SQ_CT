package com.evian.sqct.bean.vendor.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageSingleProductSelectReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:Proc_Backstage_single_product_select
 *
 * @Date:2020/9/16 13:50
 * @Author:XHX
 */
public class ProcBackstageSingleProductSelectReqDTO extends PagingPojo {
    private Integer eid;
    private Integer productId;
    private Boolean hitTheShelf;
    private Boolean del;
    @NotNull
    private Integer accountId;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getHitTheShelf() {
        return hitTheShelf;
    }

    public void setHitTheShelf(Boolean hitTheShelf) {
        this.hitTheShelf = hitTheShelf;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "ProcBackstageSingleProductSelectReqDTO [" +
                "eid=" + eid +
                ", productId=" + productId +
                ", hitTheShelf=" + hitTheShelf +
                ", del=" + del +
                ", accountId=" + accountId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
