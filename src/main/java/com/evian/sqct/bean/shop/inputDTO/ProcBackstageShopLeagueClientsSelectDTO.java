package com.evian.sqct.bean.shop.inputDTO;

import com.evian.sqct.bean.baseBean.PagingPojo;

/**
 * ClassName:ProcBackstageShopLeagueClientsSelectDTO
 * Package:com.evian.sqct.bean.shop.inputDTO
 * Description:Proc_Backstage_shop_league_clients_select
 *
 * @Date:2020/6/29 9:14
 * @Author:XHX
 */
public class ProcBackstageShopLeagueClientsSelectDTO extends PagingPojo {

    private Integer shopId;
    private Integer eid;
    private String nickName;
    private Boolean ifTuiYa;
    private String cellphone;
    private Integer noOrderDays;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getIfTuiYa() {
        return ifTuiYa;
    }

    public void setIfTuiYa(Boolean ifTuiYa) {
        this.ifTuiYa = ifTuiYa;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Integer getNoOrderDays() {
        return noOrderDays;
    }

    public void setNoOrderDays(Integer noOrderDays) {
        this.noOrderDays = noOrderDays;
    }

    @Override
    public String toString() {
        return "ProcBackstageShopLeagueClientsSelectDTO [" +
                "shopId=" + shopId +
                ", eid=" + eid +
                ", nickName=" + nickName +
                ", ifTuiYa=" + ifTuiYa +
                ", cellphone=" + cellphone +
                ", noOrderDays=" + noOrderDays +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
