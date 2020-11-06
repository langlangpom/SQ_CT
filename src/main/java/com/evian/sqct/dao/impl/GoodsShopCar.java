package com.evian.sqct.dao.impl;

import java.io.Serializable;

/**
 * 购物车实体类
 */
public class GoodsShopCar implements Serializable {
    private static final long serialVersionUID = -3150956674990988828L;
    //序号ID
    private long id;
    //企业ID
    private int eid;
    //水店ID
    private int shopId;
    //客户ID
    private int clientId;
    //商品ID
    private int pid;
    //购买数量
    private int number;
    //结算方式(现金，电子票，回票，押桶，赠品)
    private String settleStyle;
    //录入日期时间戳
    private long dateCreated;
    //修改日期时间戳
    private long dateUpdated;
    //父商品ID
    private int fpid;
    //活动ID 0:无活动 1:买赠
    private int activityId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSettleStyle() {
        return settleStyle;
    }

    public void setSettleStyle(String settleStyle) {
        this.settleStyle = settleStyle;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(long dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getFpid() {
        return fpid;
    }

    public void setFpid(int fpid) {
        this.fpid = fpid;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

}
