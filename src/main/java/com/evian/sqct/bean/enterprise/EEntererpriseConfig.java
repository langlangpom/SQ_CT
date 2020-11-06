package com.evian.sqct.bean.enterprise;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:EEntererpriseConfig
 * Package:com.evian.sqct.bean.enterprise
 * Description:企业配置表 e_enterprise_config
 *
 * @Date:2020/5/26 13:58
 * @Author:XHX
 */
public class EEntererpriseConfig implements Serializable {
    private static final long serialVersionUID = -7092263540651890084L;

    /** 对应 e_enterprise_config_model 表中 paramId字段 */
    private Integer paramId;
    private Integer eid;
    /** 参数值 */
    private String paramKey;
    /** 参数键 */
    private String paramValue;
    /** 状态:0该参数无需审核，1参数需要审核但未审核，2参数已经审核，不能进行修改操作 */
    private Integer paramStatus;
    private Date createTime;
    private Boolean isTimeLimit;
    private Date beginTime;
    private Date endTime;

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getParamStatus() {
        return paramStatus;
    }

    public void setParamStatus(Integer paramStatus) {
        this.paramStatus = paramStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getTimeLimit() {
        return isTimeLimit;
    }

    public void setTimeLimit(Boolean timeLimit) {
        isTimeLimit = timeLimit;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "EEntererpriseConfig [" +
                "paramId=" + paramId +
                ", eid=" + eid +
                ", paramKey=" + paramKey +
                ", paramValue=" + paramValue +
                ", paramStatus=" + paramStatus +
                ", createTime=" + createTime +
                ", isTimeLimit=" + isTimeLimit +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
