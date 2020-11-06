package com.evian.sqct.bean.vendor;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ReplenishmentRecordSelectReqDTO
 * Package:com.evian.sqct.bean.vendor
 * Description:replenishmentRecordSelect api
 *
 * @Date:2020/6/11 16:39
 * @Author:XHX
 */
public class ReplenishmentRecordSelectReqDTO extends PagingPojo  {
    private Integer eid;
    private String account;
    private String productCode;
    private String productName;
    private Boolean isTest;
    private String containerCode;
    private Integer doorIndex;
    private Integer operatType;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }

    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    public Integer getDoorIndex() {
        return doorIndex;
    }

    public void setDoorIndex(Integer doorIndex) {
        this.doorIndex = doorIndex;
    }

    public Integer getOperatType() {
        return operatType;
    }

    public void setOperatType(Integer operatType) {
        this.operatType = operatType;
    }

    @Override
    public String toString() {
        return "ReplenishmentRecordSelectReqDTO [" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", eid=" + eid +
                ", account=" + account +
                ", productCode=" + productCode +
                ", productName=" + productName +
                ", isTest=" + isTest +
                ", containerCode=" + containerCode +
                ", doorIndex=" + doorIndex +
                ", operatType=" + operatType +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }



}
