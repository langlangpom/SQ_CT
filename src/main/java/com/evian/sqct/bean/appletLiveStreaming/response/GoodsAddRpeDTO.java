package com.evian.sqct.bean.appletLiveStreaming.response;

/**
 * ClassName:GoodsAddRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 18:23
 * @Author:XHX
 */
public class GoodsAddRpeDTO {
    private Integer auditId;
    private Integer goodsId;

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "GoodsAddRpeDTO [" +
                "auditId=" + auditId +
                ", goodsId=" + goodsId +
                ']';
    }
}
