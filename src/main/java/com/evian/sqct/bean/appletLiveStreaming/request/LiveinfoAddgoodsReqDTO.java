package com.evian.sqct.bean.appletLiveStreaming.request;

import com.alibaba.fastjson.JSONArray;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;

import javax.validation.constraints.NotNull;

/**
 * ClassName:LiveinfoAddgoodsReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 17:38
 * @Author:XHX
 */
public class LiveinfoAddgoodsReqDTO {
    @NotNull
    private Integer eid;
    @NotNull
    private String ids;
    @NotNull
    private Integer roomId;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public JSONArray getIds() {
        JSONArray objects = null;
        try {
            objects = JSONArray.parseArray(ids);
        } catch (Exception e) {
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_PARAM);
        }
        return objects;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "LiveinfoAddgoodsReqDTO [" +
                "eid=" + eid +
                ", ids=" + ids +
                ", roomId=" + roomId +
                ']';
    }
}
