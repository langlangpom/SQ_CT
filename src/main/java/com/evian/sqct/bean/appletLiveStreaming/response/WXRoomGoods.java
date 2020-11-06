package com.evian.sqct.bean.appletLiveStreaming.response;

/**
 * ClassName:WXRoomGoods
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:21
 * @Author:XHX
 */
public class WXRoomGoods {
    private String cover_img;
    private String url;
    private Double price;
    private String name;

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WXRoomGoods [" +
                "cover_img=" + cover_img +
                ", url=" + url +
                ", price=" + price +
                ", name=" + name +
                ']';
    }
}
