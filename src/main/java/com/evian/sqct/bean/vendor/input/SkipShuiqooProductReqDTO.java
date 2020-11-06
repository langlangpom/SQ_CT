package com.evian.sqct.bean.vendor.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:SkipShuiqooProductReqDTO
 * Package:com.evian.sqct.bean.vendor.input
 * Description:请为该功能做描述
 *
 * @Date:2020/9/30 16:40
 * @Author:XHX
 */
public class SkipShuiqooProductReqDTO extends PagingPojo {
    @NotNull
    private Integer eid;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "SkipShuiqooProductReqDTO [" +
                "eid=" + eid +
                ']';
    }
}
