package com.evian.sqct.bean.thirdParty.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageRecruitGoodSelectDTO
 * Package:com.evian.sqct.bean.thirdParty
 * Description:Proc_Backstage_recruit_good_select
 *
 * @Date:2020/10/26 10:56
 * @Author:XHX
 */
public class ProcBackstageRecruitGoodSelectReqDTO extends PagingPojo {
    private Integer id;
    private Integer platform_id;
    private String platform_good_name;
    private String erp_good_name;
    @NotNull
    private Integer eid;

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

    public String getPlatform_good_name() {
        return platform_good_name;
    }

    public void setPlatform_good_name(String platform_good_name) {
        this.platform_good_name = platform_good_name;
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

    @Override
    public String toString() {
        return "ProcBackstageRecruitGoodSelectReqDTO [" +
                "id=" + id +
                ", platform_id=" + platform_id +
                ", platform_good_name=" + platform_good_name +
                ", erp_good_name=" + erp_good_name +
                ", eid=" + eid +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ']';
    }
}
