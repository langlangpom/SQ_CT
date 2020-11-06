package com.evian.sqct.bean.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 水趣商户推送实体
 */
public class JPushShangHuModel implements Serializable {
    private static final long serialVersionUID = 3884959112854924649L;
    public Integer xid;
    public String title;
    public String message;
    /** 来区分表名称 */
    public Integer type;
    public String sendTime;
    /** 0:所有平台  1:安卓 2:苹果 对应彭安接口的 sourceId */
    @JsonProperty("sourceId")
    public Integer platform;
    /** 极光客户端ID */
    public String registerId;
    /** 推送的Tag */
    public String jpushTag;
    /** 语音播报内容 */
    public String voiceContent;
    /** 单据关联账号 */
    public String account="";
    /** 单号 */
    public String orderNo="";
    /** 厂商推送平台ID(1:极光  2:华为  3.小米  4.OPPO  5.VIVO) */
    public Integer platformId;

    public JPushShangHuModel(Integer xid, String title, String message, Integer type, String sendTime, Integer platform
            , String registerId, String jpushTag, String voiceContent, String account, String orderNo, Integer platformId) {
        this.xid = xid;
        this.title = title;
        this.message = message;
        this.type = type;
        this.sendTime = sendTime;
        this.platform = platform;
        this.registerId = registerId;
        this.jpushTag = jpushTag;
        this.voiceContent = voiceContent;
        this.account = account;
        this.orderNo = orderNo;
        this.platformId = platformId;
    }

    public JPushShangHuModel(Integer xid, String title, String message, Integer type, String sendTime, Integer platform
            , String registerId, String jpushTag, String voiceContent, Integer platformId) {
        this.xid = xid;
        this.title = title;
        this.message = message;
        this.type = type;
        this.sendTime = sendTime;
        this.platform = platform;
        this.registerId = registerId;
        this.jpushTag = jpushTag;
        this.voiceContent = voiceContent;
        this.platformId = platformId;
    }

    @Override
    public String toString() {
        return "JPushShangHuModel [" +
                "xid=" + xid +
                ", title=" + title +
                ", message=" + message +
                ", type=" + type +
                ", sendTime=" + sendTime +
                ", platform=" + platform +
                ", registerId=" + registerId +
                ", jpushTag=" + jpushTag +
                ", voiceContent=" + voiceContent +
                ", account=" + account +
                ", orderNo=" + orderNo +
                ", platformId=" + platformId +
                ']';
    }
}
