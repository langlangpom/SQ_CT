package com.evian.sqct.bean.appletLiveStreaming.response;

import com.evian.sqct.bean.wechat.BaseWXResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ClassName:WXGetliveinfoRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:15
 * @Author:XHX
 */
public class WXGetliveinfoRpeDTO extends BaseWXResponseDTO {
    private List<WXRoomInfo> room_info;
    @JsonProperty("total")
    private Integer Count;

    public List<WXRoomInfo> getRoom_info() {
        return room_info;
    }

    public void setRoom_info(List<WXRoomInfo> room_info) {
        this.room_info = room_info;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    @Override
    public String toString() {
        return "WXGetliveinfoRpeDTO [" +
                "room_info=" + room_info +
                ", Count=" + Count +
                ", errcode=" + errcode +
                ", errmsg=" + errmsg +
                ']';
    }
}
