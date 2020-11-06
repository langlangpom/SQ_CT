package com.evian.sqct.bean.appletLiveStreaming.response;

/**
 * ClassName:WXGetapprovedGoodsRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 14:22
 * @Author:XHX
 */
public class WXGetapprovedGoodsRpeDTO {
    private Integer goodsId;
    private String coverImgUrl;
    private String name;
    private Double price;
    private String url;
    private Integer priceType;
    private Double price2;
    private Integer thirdPartyTag;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Double getPrice2() {
        return price2;
    }

    public void setPrice2(Double price2) {
        this.price2 = price2;
    }

    public Integer getThirdPartyTag() {
        return thirdPartyTag;
    }

    public void setThirdPartyTag(Integer thirdPartyTag) {
        this.thirdPartyTag = thirdPartyTag;
    }

    @Override
    public String toString() {
        return "WXGetapprovedGoodsRpeDTO [" +
                "goodsId=" + goodsId +
                ", coverImgUrl=" + coverImgUrl +
                ", name=" + name +
                ", price=" + price +
                ", url=" + url +
                ", priceType=" + priceType +
                ", price2=" + price2 +
                ", thirdPartyTag=" + thirdPartyTag +
                ']';
    }
}
