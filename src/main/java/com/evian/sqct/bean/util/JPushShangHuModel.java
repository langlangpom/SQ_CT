package com.evian.sqct.bean.util;

import java.io.Serializable;

/**
 * 水趣商户推送实体
 */
public class JPushShangHuModel implements Serializable {
    private static final long serialVersionUID = 3884959112854924649L;
    public Integer xid;
    public String title;
    public String message;
    public Integer type;   //来区分表名称
    public String sendTime;
    public Integer platform;  //0:所以平台  1:安卓 2:苹果
    public String registerId; //极光客户端ID
    public String jpushTag; //推送的Tag
    public String voiceContent;  //语音播报内容

    public JPushShangHuModel(Integer xid, String title, String message, Integer type, String sendTime, Integer platform, String registerId, String jpushTag, String voiceContent) {
        this.xid = xid;
        this.title = title;
        this.message = message;
        this.type = type;
        this.sendTime = sendTime;
        this.platform = platform;
        this.registerId = registerId;
        this.jpushTag = jpushTag;
        this.voiceContent = voiceContent;
    }
}
