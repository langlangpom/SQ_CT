package com.evian.sqct.bean.appletLiveStreaming.request;

import javax.validation.constraints.NotNull;

/**
 * ClassName:CreateLiveinfoReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:32
 * @Author:XHX
 */
public class CreateLiveinfoReqDTO {
    @NotNull
    private Integer eid;
    @NotNull
    private String name;
    @NotNull
    private Long startTime;
    @NotNull
    private Long endTime;
    @NotNull
    private String anchorName;
    @NotNull
    private String anchorWechat;
    @NotNull
    private Integer type;
    @NotNull
    private Integer screenType;
    @NotNull
    private Integer closeLike;
    @NotNull
    private Integer closeGoods;
    @NotNull
    private Integer closeComment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getAnchorWechat() {
        return anchorWechat;
    }

    public void setAnchorWechat(String anchorWechat) {
        this.anchorWechat = anchorWechat;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getScreenType() {
        return screenType;
    }

    public void setScreenType(Integer screenType) {
        this.screenType = screenType;
    }

    public Integer getCloseLike() {
        return closeLike;
    }

    public void setCloseLike(Integer closeLike) {
        this.closeLike = closeLike;
    }

    public Integer getCloseGoods() {
        return closeGoods;
    }

    public void setCloseGoods(Integer closeGoods) {
        this.closeGoods = closeGoods;
    }

    public Integer getCloseComment() {
        return closeComment;
    }

    public void setCloseComment(Integer closeComment) {
        this.closeComment = closeComment;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "CreateLiveinfoReqDTO [" +
                "eid=" + eid +
                ", name=" + name +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", anchorName=" + anchorName +
                ", anchorWechat=" + anchorWechat +
                ", type=" + type +
                ", screenType=" + screenType +
                ", closeLike=" + closeLike +
                ", closeGoods=" + closeGoods +
                ", closeComment=" + closeComment +
                ']';
    }
}
