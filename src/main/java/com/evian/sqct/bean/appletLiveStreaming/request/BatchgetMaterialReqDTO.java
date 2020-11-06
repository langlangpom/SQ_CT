package com.evian.sqct.bean.appletLiveStreaming.request;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:BatchgetMaterialReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 17:53
 * @Author:XHX
 */
public class BatchgetMaterialReqDTO extends PagingPojo {
    @NotNull
    private Integer eid;
    /** 图片（image）、视频（video）、语音 （voice）、图文（news） */
    @NotNull
    private String type;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BatchgetMaterialReqDTO [" +
                "eid=" + eid +
                ", type=" + type +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ']';
    }
}
