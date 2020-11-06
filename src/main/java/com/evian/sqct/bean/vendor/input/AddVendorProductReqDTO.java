package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:AddVendorProductReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/10/30 14:56
 * @Author:XHX
 */
public class AddVendorProductReqDTO {
    @NotNull
    private Integer eid;
    /** 商品名称 */
    @NotNull
    private String productName;
    /** 商品价格 */
    @NotNull
    private Double price;
    /** 原价 2020-03-24 王总通过黄亮东口头需求 */
    @NotNull
    private Double originalPrice;
    /** 商品图片 */
    @NotNull
    private String picture;
    /** 商品图文URL */
    private String imageText="";
    /** 是否上架 */
    @NotNull
    private Boolean isLine;
    /** 录入人 */
    @NotNull
    private String createUser;
    /** 简介 */
    private String synopsis;
    /** 是否红包商品 */
    private Boolean isCash = false;
    /** 是否限制（每天买一次） */
    private Boolean isLimit = false;
    private String shortName;
    /** 是否奖品（抽奖活动实物奖品发放到自动售货机时候，补货时默认选择该项，企业只能有一个奖品商品，价格为0.01元，每天只能购买一次，在实物奖品审核时候设置一次） */
    private Boolean isPrize = false;
    /** 排序，由小到大 （2019-08-22 刘淑立口头需求） */
    private Integer sortId = 0;
    /** 黄亮东口头需求，辨别特约商户添加的商品，让特约商户想增加自己商品时只用添加这个表，不用添加水趣商品表 */
    @NotNull
    private Integer accountId;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public Boolean getIsLine() {
        return isLine;
    }

    public void setIsLine(Boolean isLine) {
        this.isLine = isLine;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Boolean getIsCash() {
        return isCash;
    }

    public void setIsCash(Boolean cash) {
        isCash = cash;
    }

    public Boolean getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Boolean limit) {
        isLimit = limit;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(Boolean prize) {
        isPrize = prize;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AddVendorProductReqDTO [" +
                "eid=" + eid +
                ", productName=" + productName +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", picture=" + picture +
                ", imageText=" + imageText +
                ", isLine=" + isLine +
                ", createUser=" + createUser +
                ", synopsis=" + synopsis +
                ", isCash=" + isCash +
                ", isLimit=" + isLimit +
                ", shortName=" + shortName +
                ", isPrize=" + isPrize +
                ", sortId=" + sortId +
                ", accountId=" + accountId +
                ']';
    }
}
