package com.evian.sqct.bean.user;

import com.evian.sqct.bean.baseBean.PagingPojo;

import java.io.Serializable;

/**
 * ClassName:VendorReplenishmentPartSelectDTO
 * Package:com.evian.sqct.bean.user
 * Description:查询售货机管理者
 *
 * @Date:2020/6/5 13:58
 * @Author:XHX
 */
public class VendorReplenishmentPartSelectDTO extends PagingPojo implements Serializable {

    private static final long serialVersionUID = -1402144218135470931L;
    private Integer eid;
    private Integer shopId;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "VendorReplenishmentPartSelectDTO [" +
                "eid=" + eid +
                ", shopId=" + shopId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }
}
