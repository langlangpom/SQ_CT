package com.evian.sqct.bean.appletLiveStreaming.response;

import java.util.List;

/**
 * ClassName:GetliveinfoRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:24
 * @Author:XHX
 */
public class GetliveinfoRpeDTO {

    private List<WXRoomInfo> room_info;

    public List<WXRoomInfo> getRoom_info() {
        return room_info;
    }

    public void setRoom_info(List<WXRoomInfo> room_info) {
        this.room_info = room_info;
    }

    @Override
    public String toString() {
        return "GetliveinfoRpeDTO [" +
                "room_info=" + room_info +
                ']';
    }
}
