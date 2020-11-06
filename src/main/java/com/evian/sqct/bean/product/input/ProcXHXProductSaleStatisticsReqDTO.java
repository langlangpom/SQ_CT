package com.evian.sqct.bean.product.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcXHXProductSaleStatisticsReqDTO
 * Package:com.evian.sqct.bean.product.input
 * Description:Proc_XHX_product_sale_statistics
 *
 * @Date:2020/11/5 14:55
 * @Author:XHX
 */
public class ProcXHXProductSaleStatisticsReqDTO extends PagingPojo {
    @NotNull
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ProcXHXProductSaleStatisticsReqDTO [" +
                "shopId=" + shopId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
