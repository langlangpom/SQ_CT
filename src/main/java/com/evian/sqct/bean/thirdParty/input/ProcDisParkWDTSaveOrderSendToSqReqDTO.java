package com.evian.sqct.bean.thirdParty.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcDisParkWDTSaveOrderSendToSqReqDTO
 * Package:com.evian.sqct.bean.thirdParty.input
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 16:39
 * @Author:XHX
 */
public class ProcDisParkWDTSaveOrderSendToSqReqDTO {
    @NotNull
    private Integer eid;
    @NotNull
    private Integer sq_platform_id;
    @NotNull
    private String order_GUID;
    @NotNull
    private String UserName;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getSq_platform_id() {
        return sq_platform_id;
    }

    public void setSq_platform_id(Integer sq_platform_id) {
        this.sq_platform_id = sq_platform_id;
    }

    public String getOrder_GUID() {
        return order_GUID;
    }

    public void setOrder_GUID(String order_GUID) {
        this.order_GUID = order_GUID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "ProcDisParkWDTSaveOrderSendToSqReqDTO [" +
                "eid=" + eid +
                ", sq_platform_id=" + sq_platform_id +
                ", order_GUID=" + order_GUID +
                ", UserName=" + UserName +
                ']';
    }
}
