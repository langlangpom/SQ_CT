package com.evian.sqct.bean.appletLiveStreaming.request;

import javax.validation.constraints.NotNull;

/**
 * ClassName:GoodsAddReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 18:12
 * @Author:XHX
 */
public class GoodsAddReqDTO {
    @NotNull
    private Integer eid;
    @NotNull
    private GoodsAddGoodsInfoReqDTO goodsInfo;

    public GoodsAddReqDTO() {
    }

    public GoodsAddReqDTO(Integer eid, GoodsAddGoodsInfoReqDTO goodsInfo) {
        this.eid = eid;
        this.goodsInfo = goodsInfo;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public GoodsAddGoodsInfoReqDTO getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsAddGoodsInfoReqDTO goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String toString() {
        return "GoodsAddReqDTO [" +
                "eid=" + eid +
                ", goodsInfo=" + goodsInfo +
                ']';
    }
}
