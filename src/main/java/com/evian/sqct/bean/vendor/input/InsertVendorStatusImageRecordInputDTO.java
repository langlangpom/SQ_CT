package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:InsertVendorStatusImageRecordInputDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/7/13 11:14
 * @Author:XHX
 */
public class InsertVendorStatusImageRecordInputDTO {

    @NotNull
    private Integer mainboardId;
    @NotNull
    private String mainboardNo;
    private String shopContainerName;
    private String title;
    private String description;
    private String recordImage;

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public String getMainboardNo() {
        return mainboardNo;
    }

    public void setMainboardNo(String mainboardNo) {
        this.mainboardNo = mainboardNo;
    }

    public String getShopContainerName() {
        return shopContainerName;
    }

    public void setShopContainerName(String shopContainerName) {
        this.shopContainerName = shopContainerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecordImage() {
        return recordImage;
    }

    public void setRecordImage(String recordImage) {
        this.recordImage = recordImage;
    }

    @Override
    public String toString() {
        return "InsertVendorStatusImageRecordInputDTO [" +
                "mainboardId=" + mainboardId +
                ", mainboardNo=" + mainboardNo +
                ", shopContainerName=" + shopContainerName +
                ", title=" + title +
                ", description=" + description +
                ", recordImage=" + recordImage +
                ']';
    }
}
