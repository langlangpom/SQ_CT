package com.evian.sqct.bean.vendor.write;

/**
 * ClassName:SkipShuiqooProductRepDTO
 * Package:com.evian.sqct.bean.vendor.write
 * Description:跳转水趣商品实体
 *
 * @Date:2020/9/30 14:23
 * @Author:XHX
 */
public class SkipShuiqooProductRepDTO {
    private Integer id;
    private Integer pid;
    private Integer eid;
    private String pname;
    private String unit;
    private Double price;
    private Double vipPrice;
    private String pictureUrl;
    private String describe;
    private String salesNum;
    private Boolean ifTicket;
    private Boolean ifCombo;
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(String salesNum) {
        this.salesNum = salesNum;
    }

    public Boolean getIfTicket() {
        return ifTicket;
    }

    public void setIfTicket(Boolean ifTicket) {
        this.ifTicket = ifTicket;
    }

    public Boolean getIfCombo() {
        return ifCombo;
    }

    public void setIfCombo(Boolean ifCombo) {
        this.ifCombo = ifCombo;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "SkipShuiqooProductRepDTO [" +
                "id=" + id +
                ", pid=" + pid +
                ", eid=" + eid +
                ", pname=" + pname +
                ", unit=" + unit +
                ", price=" + price +
                ", vipPrice=" + vipPrice +
                ", pictureUrl=" + pictureUrl +
                ", describe=" + describe +
                ", salesNum=" + salesNum +
                ", ifTicket=" + ifTicket +
                ", ifCombo=" + ifCombo +
                ", sort=" + sort +
                ']';
    }
}
