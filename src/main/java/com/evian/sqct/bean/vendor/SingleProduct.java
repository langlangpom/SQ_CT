package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * ClassName:SingleProduct
 * Package:com.evian.sqct.bean.vendor
 * Description:表 single_product
 *
 * @Date:2020/9/7 11:15
 * @Author:XHX
 */
public class SingleProduct {

    private Integer productId;
    @NotNull
    private Integer eid;
    @NotNull
    private String productName;

    private String productPic;

    private Date createTime;
    @NotNull
    private Double price;

    private String describe = "";

    private Boolean hitTheShelf;
    @NotNull
    private Integer accountId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getHitTheShelf() {
        return hitTheShelf;
    }

    public void setHitTheShelf(Boolean hitTheShelf) {
        this.hitTheShelf = hitTheShelf;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "SingleProduct [" +
                "productId=" + productId +
                ", eid=" + eid +
                ", productName=" + productName +
                ", productPic=" + productPic +
                ", createTime=" + createTime +
                ", price=" + price +
                ", describe=" + describe +
                ", hitTheShelf=" + hitTheShelf +
                ", accountId=" + accountId +
                ']';
    }
}
