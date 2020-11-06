package com.evian.sqct.bean.vendor.write;

import java.io.Serializable;

/**
 * ClassName:EWechatServicepaySubaccountApplyBankRepDTO
 * Package:com.evian.sqct.bean.vendor.write
 * Description:请为该功能做描述
 *
 * @Date:2020/9/24 16:19
 * @Author:XHX
 */
public class EWechatServicepaySubaccountApplyProvinceRepDTO implements Serializable {

    private static final long serialVersionUID = 819312885117595066L;

    private Integer zipcode;
    private String country;
    private String province;
    private String city;
    private String district;

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "EWechatServicepaySubaccountApplyBankRepDTO [" +
                "zipcode=" + zipcode +
                ", country=" + country +
                ", province=" + province +
                ", city=" + city +
                ", district=" + district +
                ']';
    }
}
