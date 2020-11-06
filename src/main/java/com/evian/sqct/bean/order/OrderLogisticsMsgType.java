package com.evian.sqct.bean.order;

/**
 * ClassName:OrderLogisticsMsgType
 * Package:com.evian.sqct.bean.order
 * Description:e_appMerchant_order_logistics  msgType
 *
 * @Date:2020/10/27 10:46
 * @Author:XHX
 */
public enum  OrderLogisticsMsgType {
    /** 用户取消 */
    USER_CANCEL(1),
    /** 中台取消 */
    M_CANCEL(2),
    /** APP取消 */
    APP_CANCEL(3),
    /** 申请退款 */
    APPLY_FOR_REFUND(4),
    /** 审核退货 */
    AUDIT_REFUND(5),
    /** 改派 */
    CHANGE_DISPATCHING(6),
    /** 退单 */
    CHARGEBACK(7),
    /** 拒绝退款申请 */
    REFUSE_APPLY_FOR_REFUND(8);
    private int type;

    OrderLogisticsMsgType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
