package com.evian.sqct.bean.user;

import java.io.Serializable;
import java.sql.Date;

/**
 * ClassName:EEnterpriseMsgWeixin
 * Package:com.evian.sqct.bean.user
 * Description:e_enterprise_msg_weixin  企业模板信息接收人微信
 *
 * @Date:2020/7/15 10:01
 * @Author:XHX
 */
public class EEnterpriseMsgWeixin implements Serializable {
    private static final long serialVersionUID = 8167347480264573672L;
    private Integer id;
    private Integer eid;
    private String openId;
    private Integer forType;
    private Boolean enabled;
    private Boolean isValid;
    private Date createTime;
    private String validCode;
    private String appId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getForType() {
        return forType;
    }

    public void setForType(Integer forType) {
        this.forType = forType;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "EEnterpriseMsgWeixin [" +
                "id=" + id +
                ", eid=" + eid +
                ", openId=" + openId +
                ", forType=" + forType +
                ", enabled=" + enabled +
                ", isValid=" + isValid +
                ", createTime=" + createTime +
                ", validCode=" + validCode +
                ", appId=" + appId +
                ']';
    }
}
