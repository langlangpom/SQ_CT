package com.evian.sqct.bean.activity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ClassName:EappMerchantShareCodeActivity
 * Package:com.evian.sqct.bean.activity
 * Description:表e_appMerchant_share_code_activity实体类
 *
 * @Date:2019/10/10 15:39
 * @Author:XHX
 */
public class EappMerchantShareCodeActivity implements Serializable{


    private static final long serialVersionUID = 7672734094226926052L;

    private Integer activityId;
    private Integer eid;
    private String activityName;    // 活动名称
    private String activityPic;
    private String synopsis;        // 活动说明
    private Timestamp beginDate;
    private Timestamp endDate;
    private Integer receiveType;    // 领券条件 1所有用户可用, 2限新注册用户可用
    private Integer maxShareNum;    // 每任务领券最多次数限制
    private String activityExplain; // 规则说明(图文)
    private Timestamp createTime;
    private String creator;         // 录入人
    private Boolean isEnable;       // 1启用，0停用
    private Boolean isDel;          // 1删除，0正常
    private Integer activityType;   // 1.优惠券分享, 2.优惠券赠送
    private Boolean participation;// 是否参与

    public Boolean getParticipation() {
        return participation;
    }

    public void setParticipation(Boolean participation) {
        this.participation = participation;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(String activityPic) {
        this.activityPic = activityPic;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public Integer getMaxShareNum() {
        return maxShareNum;
    }

    public void setMaxShareNum(Integer maxShareNum) {
        this.maxShareNum = maxShareNum;
    }

    public String getActivityExplain() {
        return activityExplain;
    }

    public void setActivityExplain(String activityExplain) {
        this.activityExplain = activityExplain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return "EappMerchantShareCodeActivity{" +
                "activityId=" + activityId +
                ", eid=" + eid +
                ", activityName='" + activityName + '\'' +
                ", activityPic='" + activityPic + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", receiveType=" + receiveType +
                ", maxShareNum=" + maxShareNum +
                ", activityExplain='" + activityExplain + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", isEnable=" + isEnable +
                ", isDel=" + isDel +
                ", activityType=" + activityType +
                ", participation=" + participation +
                '}';
    }
}
