package com.evian.sqct.bean.shop.writerDTO;

/**
 * ClassName:ShowShopInfoDTO
 * Package:com.evian.sqct.bean.shop.writerDTO
 * Description:请为该功能做描述
 *
 * @Date:2020/7/9 11:29
 * @Author:XHX
 */
public class ShowShopInfoDTO {
    private Integer shopId;
    private String shopName;
    private Integer eid;
    private String location;
    private String address;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ShowShopInfoDTO [" +
                "shopId=" + shopId +
                ", shopName=" + shopName +
                ", eid=" + eid +
                ", location=" + location +
                ", address=" + address +
                ']';
    }
}
