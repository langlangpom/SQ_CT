package com.evian.sqct.bean.order.request;

/**
 * ClassName:PROCAPPOrderStatusUPdateReqDTO
 * Package:com.evian.sqct.bean.order.request
 * Description:PROC_APP_order_statusUPdate
 *
 * @Date:2020/6/29 14:17
 * @Author:XHX
 */
public class PROCAPPOrderStatusUPdateReqDTO {
    private Integer orderId;
    private String sType;
    private Integer iStatus;
    private String Remark;
    private String UserName;

    public PROCAPPOrderStatusUPdateReqDTO() {
    }

    public PROCAPPOrderStatusUPdateReqDTO(Integer orderId, String sType, Integer iStatus, String remark, String userName) {
        this.orderId = orderId;
        this.sType = sType;
        this.iStatus = iStatus;
        Remark = remark;
        UserName = userName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public Integer getiStatus() {
        return iStatus;
    }

    public void setiStatus(Integer iStatus) {
        this.iStatus = iStatus;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "PROCAPPOrderStatusUPdateReqDTO [" +
                "orderId=" + orderId +
                ", sType=" + sType +
                ", iStatus=" + iStatus +
                ", Remark=" + Remark +
                ", UserName=" + UserName +
                ']';
    }
}
