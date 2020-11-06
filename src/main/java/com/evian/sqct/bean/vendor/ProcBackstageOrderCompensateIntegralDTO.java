package com.evian.sqct.bean.vendor;

import java.io.Serializable;

/**
 * ClassName:ProcBackstageOrderCompensateIntegralDTO
 * Package:com.evian.sqct.bean.vendor
 * Description:Proc_Backstage_order_compensateIntegral
 *
 * @Date:2020/6/8 17:46
 * @Author:XHX
 */
public class ProcBackstageOrderCompensateIntegralDTO implements Serializable {

    private static final long serialVersionUID = 5801008293304708759L;
    private Integer orderId;
    private Integer eid;
    private Integer accountId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "ProcBackstageOrderCompensateIntegralDTO [" +
                "orderId=" + orderId +
                ", eid=" + eid +
                ", accountId=" + accountId +
                ']';
    }
}
