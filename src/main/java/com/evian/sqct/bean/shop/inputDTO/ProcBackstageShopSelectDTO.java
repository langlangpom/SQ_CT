package com.evian.sqct.bean.shop.inputDTO;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageShopSelectDTO
 * Package:com.evian.sqct.bean.shop.inputDTO
 * Description:请为该功能做描述
 *
 * @Date:2020/7/9 11:14
 * @Author:XHX
 */
public class ProcBackstageShopSelectDTO extends PagingPojo {

    private Integer shopId;
    private String shopNo;
    private String shopName;
    private String shopType;
    private Boolean ifLine;
    private Integer cityId;
    private Integer districtId;
    private Integer eid;
    private String ename;
    private Boolean isRelevance;
    private Boolean isHaveKeeper;
    private String shopName1;
    private Boolean isVendor;
    private Boolean addressMatchRole;
    private Boolean isLocation;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Boolean getIfLine() {
        return ifLine;
    }

    public void setIfLine(Boolean ifLine) {
        this.ifLine = ifLine;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Boolean getIsRelevance() {
        return isRelevance;
    }

    public void setIsRelevance(Boolean isRelevance) {
        this.isRelevance = isRelevance;
    }

    public Boolean getIsHaveKeeper() {
        return isHaveKeeper;
    }

    public void setIsHaveKeeper(Boolean isHaveKeeper) {
        this.isHaveKeeper = isHaveKeeper;
    }

    public String getShopName1() {
        return shopName1;
    }

    public void setShopName1(String shopName1) {
        this.shopName1 = shopName1;
    }

    public Boolean getIsVendor() {
        return isVendor;
    }

    public void setIsVendor(Boolean isVendor) {
        this.isVendor = isVendor;
    }

    public Boolean getAddressMatchRole() {
        return addressMatchRole;
    }

    public void setAddressMatchRole(Boolean addressMatchRole) {
        this.addressMatchRole = addressMatchRole;
    }

    public Boolean getIsLocation() {
        return isLocation;
    }

    public void setIsLocation(Boolean isLocation) {
        this.isLocation = isLocation;
    }

    @Override
    public String toString() {
        return "ProcBackstageShopSelectDTO [" +
                "shopId=" + shopId +
                ", shopNo=" + shopNo +
                ", shopName=" + shopName +
                ", shopType=" + shopType +
                ", ifLine=" + ifLine +
                ", cityId=" + cityId +
                ", districtId=" + districtId +
                ", eid=" + eid +
                ", ename=" + ename +
                ", isRelevance=" + isRelevance +
                ", isHaveKeeper=" + isHaveKeeper +
                ", shopName1=" + shopName1 +
                ", isVendor=" + isVendor +
                ", addressMatchRole=" + addressMatchRole +
                ", isLocation=" + isLocation +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }
}
