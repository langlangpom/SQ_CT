package com.evian.sqct.bean.order;

import com.evian.sqct.bean.baseBean.PagingPojo;

import javax.validation.constraints.NotNull;

/**
 * ClassName:FindSendOrderLogisticsByAccountIdReqDTO
 * Package:com.evian.sqct.bean.order
 * Description:findSendOrderLogisticsByAccountId
 *
 * @Date:2020/6/16 10:28
 * @Author:XHX
 */
public class FindSendOrderLogisticsByAccountIdReqDTO extends PagingPojo {
    @NotNull
    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "FindSendOrderLogisticsByAccountIdReqDTO [" +
                "accountId=" + accountId +
                ", PageIndex=" + PageIndex +
                ", PageSize=" + PageSize +
                ", IsSelectAll=" + IsSelectAll +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ']';
    }
}
