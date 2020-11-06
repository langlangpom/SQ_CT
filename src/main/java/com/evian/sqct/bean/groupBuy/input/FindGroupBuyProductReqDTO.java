package com.evian.sqct.bean.groupBuy.input;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:FindGroupBuyProductReqDTO
 * Package:com.evian.sqct.bean.groupBuy
 * Description:请为该功能做描述
 *
 * @Date:2020/7/7 10:52
 * @Author:XHX
 */
public class FindGroupBuyProductReqDTO extends PagingPojo {

    private Integer xaId;
    @NotNull
    private Integer eid;
    private Integer cityId;
    private Integer groupBuyType;
    private Boolean isEnabled;
    private String eName;
    private String shopName;
    private Integer shopId;
    private String pname;
    private String pcode;
    private Integer cid;
    private String groupName;

    public Integer getXaId() {
        return xaId;
    }

    public void setXaId(Integer xaId) {
        this.xaId = xaId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getGroupBuyType() {
        return groupBuyType;
    }

    public void setGroupBuyType(Integer groupBuyType) {
        this.groupBuyType = groupBuyType;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "FindGroupBuyProductReqDTO [" +
                "xaId=" + xaId +
                ", eid=" + eid +
                ", cityId=" + cityId +
                ", groupBuyType=" + groupBuyType +
                ", isEnabled=" + isEnabled +
                ", eName=" + eName +
                ", shopName=" + shopName +
                ", shopId=" + shopId +
                ", pname=" + pname +
                ", pcode=" + pcode +
                ", cid=" + cid +
                ", groupName=" + groupName +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
