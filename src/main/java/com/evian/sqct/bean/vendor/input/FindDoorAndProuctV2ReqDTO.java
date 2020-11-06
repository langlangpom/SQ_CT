package com.evian.sqct.bean.vendor.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:FindDoorAndProuctV2ReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/7/6 9:17
 * @Author:XHX
 */
public class FindDoorAndProuctV2ReqDTO{

    @NotNull
    private Integer eid;
    private Integer mainboardId;
    private Integer replenishmentClassId;
    private Integer PageIndex;
    private Integer PageSize;

    public Integer getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        PageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getMainboardId() {
        return mainboardId;
    }

    public void setMainboardId(Integer mainboardId) {
        this.mainboardId = mainboardId;
    }

    public Integer getReplenishmentClassId() {
        return replenishmentClassId;
    }

    public void setReplenishmentClassId(Integer replenishmentClassId) {
        this.replenishmentClassId = replenishmentClassId;
    }

    @Override
    public String toString() {
        return "FindDoorAndProuctV2ReqDTO [" +
                "eid=" + eid +
                ", mainboardId=" + mainboardId +
                ", replenishmentClassId=" + replenishmentClassId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ']';
    }
}
