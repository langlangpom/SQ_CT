package com.evian.sqct.bean.appletLiveStreaming.request;

import javax.validation.constraints.NotNull;

/**
 * ClassName:GoodsAddGoodsInfoFileReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 11:22
 * @Author:XHX
 */
public class GoodsAddGoodsInfoFileReqDTO{


    private String coverImgUrl;
    @NotNull
    private String name;
    @NotNull
    private Integer priceType;
    @NotNull
    private String price;

    private String price2 = "";
    @NotNull
    private String url;

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GoodsAddGoodsInfoFileReqDTO [" +
                ", coverImgUrl=" + coverImgUrl +
                ", name=" + name +
                ", priceType=" + priceType +
                ", price=" + price +
                ", price2=" + price2 +
                ", url=" + url +
                ']';
    }
}
