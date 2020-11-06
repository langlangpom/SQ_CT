package com.evian.sqct.bean.vendor.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcXhxVendorStatusImageRecordSelect
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/7/13 11:32
 * @Author:XHX
 */
public class ProcXhxVendorStatusImageRecordSelect extends PagingPojo {
    private Integer mainboardId;
    private String mainboardNo;

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

    @Override
    public String toString() {
        return "ProcXhxVendorStatusImageRecordSelect [" +
                "mainboardId=" + mainboardId +
                ", mainboardNo=" + mainboardNo +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
