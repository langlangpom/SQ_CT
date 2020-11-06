package com.evian.sqct.bean.enterprise;

import java.io.Serializable;
import java.sql.Date;

/**
 * ClassName:EWeixinSendMessageTemplateEnterprise
 * Package:com.evian.sqct.bean.enterprise
 * Description:e_weixin_send_message_template_enterprise 企业微信发送模板id
 *
 * @Date:2020/7/15 9:50
 * @Author:XHX
 */
public class EWeixinSendMessageTemplateEnterprise implements Serializable {
    private static final long serialVersionUID = -9057168106368383819L;
    private Integer eid;
    private Integer tid;
    private String templateId;
    private Date createTime;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EWeixinSendMessageTemplateEnterprise [" +
                "eid=" + eid +
                ", tid=" + tid +
                ", templateId=" + templateId +
                ", createTime=" + createTime +
                ']';
    }
}
