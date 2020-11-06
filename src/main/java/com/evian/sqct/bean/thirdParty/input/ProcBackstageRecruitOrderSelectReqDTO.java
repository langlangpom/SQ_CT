package com.evian.sqct.bean.thirdParty.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageRecruitOrderSelectReqDTO
 * Package:com.evian.sqct.bean.thirdParty.input
 * Description:Proc_Backstage_recruit_order_select
 *
 * @Date:2020/10/26 11:58
 * @Author:XHX
 */
public class ProcBackstageRecruitOrderSelectReqDTO extends PagingPojo {
    @NotNull
    private Integer eid;
    private String eName;
    private Integer sq_platform_id;
    private Boolean isAccountRelevance;
    private Boolean isProductRelevance;
    private Boolean isOrderRelevanceEvian;
    private String goods_no;
    private String order_guid;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public Integer getSq_platform_id() {
        return sq_platform_id;
    }

    public void setSq_platform_id(Integer sq_platform_id) {
        this.sq_platform_id = sq_platform_id;
    }

    public Boolean getAccountRelevance() {
        return isAccountRelevance;
    }

    public void setAccountRelevance(Boolean accountRelevance) {
        isAccountRelevance = accountRelevance;
    }

    public Boolean getProductRelevance() {
        return isProductRelevance;
    }

    public void setProductRelevance(Boolean productRelevance) {
        isProductRelevance = productRelevance;
    }

    public Boolean getOrderRelevanceEvian() {
        return isOrderRelevanceEvian;
    }

    public void setOrderRelevanceEvian(Boolean orderRelevanceEvian) {
        isOrderRelevanceEvian = orderRelevanceEvian;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getOrder_guid() {
        return order_guid;
    }

    public void setOrder_guid(String order_guid) {
        this.order_guid = order_guid;
    }

    @Override
    public String toString() {
        return "ProcBackstageRecruitOrderSelectReqDTO [" +
                "eid=" + eid +
                ", eName=" + eName +
                ", sq_platform_id=" + sq_platform_id +
                ", isAccountRelevance=" + isAccountRelevance +
                ", isProductRelevance=" + isProductRelevance +
                ", isOrderRelevanceEvian=" + isOrderRelevanceEvian +
                ", goods_no=" + goods_no +
                ", order_guid=" + order_guid +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
