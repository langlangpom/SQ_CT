package com.evian.sqct.bean.order;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName:PaossUPOrderSendDTO
 * Package:com.evian.sqct.bean.order
 * Description:请为该功能做描述
 *
 * @Date:2020/5/23 11:32
 * @Author:XHX
 */
public class PaossUPOrderSendDTO implements Serializable {


    private static final long serialVersionUID = 4846771637372615415L;
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer eid;
    @NotNull
    private Integer shopId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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
        return "PaossUPOrderSendDTO [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", shopId=" + shopId +
                ']';
    }
}
