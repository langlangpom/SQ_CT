package com.evian.sqct.bean.appletLiveStreaming.request;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:GetliveinfoReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:09
 * @Author:XHX
 */
public class GetliveinfoReqDTO extends PagingPojo {
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
        return "GetliveinfoReqDTO [" +
                "eid=" + eid +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ']';
    }
}
