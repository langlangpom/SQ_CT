package com.evian.sqct.bean.appletLiveStreaming.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ClassName:GetapprovedRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 14:09
 * @Author:XHX
 */
public class WXGetapprovedRpeDTO {
    @JsonProperty("total")
    private Integer Count;
    private Integer errcode;
    private String errmsg;
    private List<WXGetapprovedGoodsRpeDTO> goods;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<WXGetapprovedGoodsRpeDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<WXGetapprovedGoodsRpeDTO> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "WXGetapprovedRpeDTO [" +
                "Count=" + Count +
                ", errcode=" + errcode +
                ", errmsg=" + errmsg +
                ", goods=" + goods +
                ']';
    }
}
