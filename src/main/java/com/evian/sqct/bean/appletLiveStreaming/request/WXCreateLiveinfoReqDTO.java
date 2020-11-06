package com.evian.sqct.bean.appletLiveStreaming.request;

/**
 * ClassName:WXCreateLiveinfoReqDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.request
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:53
 * @Author:XHX
 */
public class WXCreateLiveinfoReqDTO {
    private String name;
    private Long startTime;
    private Long endTime;
    private String anchorName;
    private String anchorWechat;
    private Integer type;
    private Integer screenType;
    private Integer closeLike;
    private Integer closeGoods;
    private Integer closeComment;
    private String coverImg;
    private String shareImg;

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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    @Override
    public String toString() {
        return "WXCreateLiveinfoReqDTO [" +
                "name=" + name +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", anchorName=" + anchorName +
                ", anchorWechat=" + anchorWechat +
                ", type=" + type +
                ", screenType=" + screenType +
                ", closeLike=" + closeLike +
                ", closeGoods=" + closeGoods +
                ", closeComment=" + closeComment +
                ", coverImg=" + coverImg +
                ", shareImg=" + shareImg +
                ']';
    }
}
