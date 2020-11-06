package com.evian.sqct.bean.appletLiveStreaming.request;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:GetapprovedReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:getapproved
 *
 * @Date:2020/6/19 13:46
 * @Author:XHX
 */
public class GetapprovedReqDTO extends PagingPojo {
    @NotNull
    private Integer eid;
    @NotNull
    private Integer status;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetapprovedReqDTO [" +
                "eid=" + eid +
                ", status=" + status +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ']';
    }
}
