package com.evian.sqct.bean.thirdParty.input;

/**
 * ClassName:ProcBackstageRecruitOrderRelationOperatReqDTO
 * Package:com.evian.sqct.bean.thirdParty.input
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 16:30
 * @Author:XHX
 */
public class ProcBackstageRecruitOrderRelationOperatReqDTO {
    private Integer XID;
    private Integer eid;
    private String order_guid;
    private String Account;
    private Integer clientId;
    private String CreateUser;

    public ProcBackstageRecruitOrderRelationOperatReqDTO() {
    }

    public ProcBackstageRecruitOrderRelationOperatReqDTO(Integer XID, Integer eid, String order_guid, String account, Integer clientId, String createUser) {
        this.XID = XID;
        this.eid = eid;
        this.order_guid = order_guid;
        Account = account;
        this.clientId = clientId;
        CreateUser = createUser;
    }

    public Integer getXID() {
        return XID;
    }

    public void setXID(Integer XID) {
        this.XID = XID;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getOrder_guid() {
        return order_guid;
    }

    public void setOrder_guid(String order_guid) {
        this.order_guid = order_guid;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    @Override
    public String toString() {
        return "ProcBackstageRecruitOrderRelationOperatReqDTO [" +
                "XID=" + XID +
                ", eid=" + eid +
                ", order_guid=" + order_guid +
                ", Account=" + Account +
                ", clientId=" + clientId +
                ", CreateUser=" + CreateUser +
                ']';
    }
}
