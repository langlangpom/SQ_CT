package com.evian.sqct.bean.vendor;

import java.sql.Date;

/**
 * ClassName:VendorOrder
 * Package:com.evian.sqct.bean.vendor
 * Description:vendor_order
 *
 * @Date:2020/7/16 11:16
 * @Author:XHX
 */
public class VendorOrder {
    private Integer orderId;
    private Integer eid;
    private Date createTime;
    private String productName;
    private String productCode;
    private String mainboardNo;
    private Integer doorIndex;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMainboardNo() {
        return mainboardNo;
    }

    public void setMainboardNo(String mainboardNo) {
        this.mainboardNo = mainboardNo;
    }

    public Integer getDoorIndex() {
        return doorIndex;
    }

    public void setDoorIndex(Integer doorIndex) {
        this.doorIndex = doorIndex;
    }

    @Override
    public String toString() {
        return "VendorOrder [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", createTime=" + createTime +
                ", productName=" + productName +
                ", productCode=" + productCode +
                ", mainboardNo=" + mainboardNo +
                ", doorIndex=" + doorIndex +
                ']';
    }
}
