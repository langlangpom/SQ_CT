package com.evian.sqct.bean.appletLiveStreaming.response;

import java.util.List;

/**
 * ClassName:WXRoomInfo
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:17
 * @Author:XHX
 */
public class WXRoomInfo {
    private String name;
    private Integer roomid;
    private String cover_img;
    private String share_img;
    private Integer live_status;
    private Long start_time;
    private Long end_time;
    private String anchor_name;
    private Integer total;
    private List<WXRoomGoods> goods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public Integer getLive_status() {
        return live_status;
    }

    public void setLive_status(Integer live_status) {
        this.live_status = live_status;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getAnchor_name() {
        return anchor_name;
    }

    public void setAnchor_name(String anchor_name) {
        this.anchor_name = anchor_name;
    }

    public List<WXRoomGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<WXRoomGoods> goods) {
        this.goods = goods;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "WXRoomInfo [" +
                "name=" + name +
                ", roomid=" + roomid +
                ", cover_img=" + cover_img +
                ", share_img=" + share_img +
                ", live_status=" + live_status +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", anchor_name=" + anchor_name +
                ", total=" + total +
                ", goods=" + goods +
                ']';
    }
}
