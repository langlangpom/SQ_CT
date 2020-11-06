package com.evian.sqct.bean.vendor;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:VendorProduct
 * Package:com.evian.sqct.bean.vendor
 * Description:vendor_product
 *
 * @Date:2020/10/30 14:13
 * @Author:XHX
 */
public class VendorProduct implements Serializable {

    private static final long serialVersionUID = -7248984658945090658L;
    private Integer id;
    /** 企业 */
    private Integer eid;
    /** 商品名称 */
    private String productName;
    /** 商品价格 */
    private Double price;
    /** 原价 2020-03-24 王总通过黄亮东口头需求 */
    private Double originalPrice;
    /** 商品图片 */
    private String picture;
    /** 商品图文URL */
    private String imageText;
    /** 是否上架 */
    private Boolean isLine;
    /** 录入时间 */
    private Date createTime;
    /** 录入人 */
    private String createUser;
    /** 商品编号（5位数，自动生成，可以修改） */
    private String productCode;
    /** 是否删除（由水趣商品管理控制该字段） */
    private Boolean isDel;
    /** 简介 */
    private String synopsis;
    /** 是否红包商品 */
    private Boolean isCash;
    /** 是否限制（每天买一次） */
    private Boolean isLimit;
    private String shortName;
    /** 是否奖品（抽奖活动实物奖品发放到自动售货机时候，补货时默认选择该项，企业只能有一个奖品商品，价格为0.01元，每天只能购买一次，在实物奖品审核时候设置一次） */
    private Boolean isPrize;
    /** 排序，由小到大 （2019-08-22 刘淑立口头需求） */
    private Integer sortId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setIsLine(Boolean line) {
        isLine = line;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean del) {
        isDel = del;
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

    @Override
    public String toString() {
        return "VendorProduct [" +
                "id=" + id +
                ", eid=" + eid +
                ", productName=" + productName +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", picture=" + picture +
                ", imageText=" + imageText +
                ", isLine=" + isLine +
                ", createTime=" + createTime +
                ", createUser=" + createUser +
                ", productCode=" + productCode +
                ", isDel=" + isDel +
                ", synopsis=" + synopsis +
                ", isCash=" + isCash +
                ", isLimit=" + isLimit +
                ", shortName=" + shortName +
                ", isPrize=" + isPrize +
                ", sortId=" + sortId +
                ']';
    }
}
