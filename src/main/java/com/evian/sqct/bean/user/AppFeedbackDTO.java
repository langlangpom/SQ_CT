package com.evian.sqct.bean.user;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:AppFeedbackRequestDTO
 * Package:com.evian.sqct.bean.order
 * Description:反馈DTO
 *
 * @Date:2020/5/25 9:48
 * @Author:XHX
 */
public class AppFeedbackDTO implements Serializable {


    private static final long serialVersionUID = -1160007343813457010L;

    private Integer feedbackId;
    @NotNull
    private Integer eid;
    @NotNull
    private Integer accountId;
    @NotNull
    private String feedbackTitle;
    @NotNull
    private String feedbackContent;
    private String pictures;
    private Date createTime;
    /* 回复内容 */
    private String writeBackContent;
    private Date writeBackTime;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWriteBackContent() {
        return writeBackContent;
    }

    public void setWriteBackContent(String writeBackContent) {
        this.writeBackContent = writeBackContent;
    }

    public Date getWriteBackTime() {
        return writeBackTime;
    }

    public void setWriteBackTime(Date writeBackTime) {
        this.writeBackTime = writeBackTime;
    }

    @Override
    public String toString() {
        return "AppFeedbackDTO [" +
                "feedbackId=" + feedbackId +
                ", eid=" + eid +
                ", accountId=" + accountId +
                ", feedbackTitle=" + feedbackTitle +
                ", feedbackContent=" + feedbackContent +
                ", pictures=" + pictures +
                ", createTime=" + createTime +
                ", writeBackContent=" + writeBackContent +
                ", writeBackTime=" + writeBackTime +
                ']';
    }
}
