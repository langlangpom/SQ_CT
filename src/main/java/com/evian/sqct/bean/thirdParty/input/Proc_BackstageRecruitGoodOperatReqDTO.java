package com.evian.sqct.bean.thirdParty.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:Proc_BackstageRecruitGoodOperatReqDTO
 * Package:com.evian.sqct.bean.thirdParty.input
 * Description:招募基础商品操作
 *
 * @Date:2020/10/26 11:22
 * @Author:XHX
 */
public class Proc_BackstageRecruitGoodOperatReqDTO {
    @NotNull
    private Integer id;
    @NotNull
    private Integer platform_id;
    @NotNull
    private String platform_goods_id;
    @NotNull
    private String platform_good_name;
    @NotNull
    private Integer erp_good_id;
    @NotNull
    private String erp_good_name;
    @NotNull
    private Integer eid;
    @NotNull
    private String createUser;
    private Integer shopId;
    private Integer ywMdId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    public String getPlatform_goods_id() {
        return platform_goods_id;
    }

    public void setPlatform_goods_id(String platform_goods_id) {
        this.platform_goods_id = platform_goods_id;
    }

    public String getPlatform_good_name() {
        return platform_good_name;
    }

    public void setPlatform_good_name(String platform_good_name) {
        this.platform_good_name = platform_good_name;
    }

    public Integer getErp_good_id() {
        return erp_good_id;
    }

    public void setErp_good_id(Integer erp_good_id) {
        this.erp_good_id = erp_good_id;
    }

    public String getErp_good_name() {
        return erp_good_name;
    }

    public void setErp_good_name(String erp_good_name) {
        this.erp_good_name = erp_good_name;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getYwMdId() {
        return ywMdId;
    }

    public void setYwMdId(Integer ywMdId) {
        this.ywMdId = ywMdId;
    }

    @Override
    public String toString() {
        return "Proc_BackstageRecruitGoodOperatReqDTO [" +
                "id=" + id +
                ", platform_id=" + platform_id +
                ", platform_goods_id=" + platform_goods_id +
                ", platform_good_name=" + platform_good_name +
                ", erp_good_id=" + erp_good_id +
                ", erp_good_name=" + erp_good_name +
                ", eid=" + eid +
                ", createUser=" + createUser +
                ", shopId=" + shopId +
                ", ywMdId=" + ywMdId +
                ']';
    }
}
