package com.evian.sqct.bean.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:EappMerchantAhareCodeActivityItem
 * Package:com.evian.sqct.bean.activity
 * Description:请为该功能做描述
 *
 * @Date:2019/10/10 17:26
 * @Author:XHX
 */
public class EappMerchantAhareCodeActivityItem implements Serializable{

    private static final long serialVersionUID = 1926542878667691288L;

    private Integer itemId;
    private Integer activityId;
    private Integer codeTypeId;
    private Integer validateDay;    // 有效天数
    private Date beginDate;
    private Date endDate;
    private Date createTime;
    private String creator;
    private Integer cityId;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(Integer codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public Integer getValidateDay() {
        return validateDay;
    }

    public void setValidateDay(Integer validateDay) {
        this.validateDay = validateDay;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "EappMerchantAhareCodeActivityItem{" +
                "itemId=" + itemId +
                ", activityId=" + activityId +
                ", codeTypeId=" + codeTypeId +
                ", validateDay=" + validateDay +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
