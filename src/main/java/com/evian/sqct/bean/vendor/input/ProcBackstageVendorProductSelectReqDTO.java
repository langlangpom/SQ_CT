package com.evian.sqct.bean.vendor.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageVendorProductSelectReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/11/3 14:49
 * @Author:XHX
 */
public class ProcBackstageVendorProductSelectReqDTO extends PagingPojo {

    private Integer id;
    private String productName;
    private String productCode;
    private Integer eid;
    private Boolean isLine = true;
    private Integer classId;
    /** 补货分类 */
    private Integer replenishmentClassId;
    /** 常用补货员id */
    private Integer frequentlyUseAccountId;
    private Integer hasUseAccountId;
    private Integer mainboardId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Boolean getIsLine() {
        return isLine;
    }

    public void setIsLine(Boolean isLine) {
        this.isLine = isLine;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getReplenishmentClassId() {
        return replenishmentClassId;
    }

    public void setReplenishmentClassId(Integer replenishmentClassId) {
        this.replenishmentClassId = replenishmentClassId;
    }

    public Integer getFrequentlyUseAccountId() {
        return frequentlyUseAccountId;
    }

    public void setFrequentlyUseAccountId(Integer frequentlyUseAccountId) {
        this.frequentlyUseAccountId = frequentlyUseAccountId;
    }

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public Integer getHasUseAccountId() {
        return hasUseAccountId;
    }

    public void setHasUseAccountId(Integer hasUseAccountId) {
        this.hasUseAccountId = hasUseAccountId;
    }

    @Override
    public String toString() {
        return "ProcBackstageVendorProductSelectReqDTO [" +
                "id=" + id +
                ", mainboardId=" + mainboardId +
                ", productName=" + productName +
                ", productCode=" + productCode +
                ", eid=" + eid +
                ", isLine=" + isLine +
                ", classId=" + classId +
                ", replenishmentClassId=" + replenishmentClassId +
                ", frequentlyUseAccountId=" + frequentlyUseAccountId +
                ", hasUseAccountId=" + hasUseAccountId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
