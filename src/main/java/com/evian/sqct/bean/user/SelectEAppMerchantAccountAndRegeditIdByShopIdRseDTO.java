package com.evian.sqct.bean.user;

import java.io.Serializable;

/**
 * ClassName:SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO
 * Package:com.evian.sqct.bean.user
 * Description:selectEAppMerchantAccountAndRegeditIdByShopId
 *
 * @Date:2020/6/16 11:15
 * @Author:XHX
 */
public class SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO implements Serializable {
    private static final long serialVersionUID = 1061744759813297139L;

    private Integer accountId;
    private Integer eid;
    private Integer shopId;
    private Boolean shopManager;
    private Boolean isReceiveOrder;
    private String regeditId;
    private Integer sourceId;
    private Integer platformId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Boolean getShopManager() {
        return shopManager;
    }

    public void setShopManager(Boolean shopManager) {
        this.shopManager = shopManager;
    }

    public Boolean getReceiveOrder() {
        return isReceiveOrder;
    }

    public void setReceiveOrder(Boolean receiveOrder) {
        isReceiveOrder = receiveOrder;
    }

    public String getRegeditId() {
        return regeditId;
    }

    public void setRegeditId(String regeditId) {
        this.regeditId = regeditId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @Override
    public String toString() {
        return "SelectEAppMerchantAccountAndRegeditIdByShopIdRseDTO [" +
                "accountId=" + accountId +
                ", eid=" + eid +
                ", shopId=" + shopId +
                ", shopManager=" + shopManager +
                ", isReceiveOrder=" + isReceiveOrder +
                ", regeditId=" + regeditId +
                ", sourceId=" + sourceId +
                ", platformId=" + platformId +
                ']';
    }
}
