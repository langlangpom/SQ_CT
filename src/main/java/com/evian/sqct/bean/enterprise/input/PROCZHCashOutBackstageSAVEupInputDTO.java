package com.evian.sqct.bean.enterprise.input;

/**
 * ClassName:PROCZHCashOutBackstageSAVEupInputDTO
 * Package:com.evian.sqct.bean.enterprise.input
 * Description:PROC_ZH_CashOut_Backstage_SAVEup
 *
 * @Date:2020/7/24 16:13
 * @Author:XHX
 */
public class PROCZHCashOutBackstageSAVEupInputDTO {
    private Integer CashOutID;
    private Integer clientid;
    private Boolean ifPayment;
    private Integer iStatus;
    private String paymentNo;
    private Integer ipaymentPlatform;
    private String SH_UserAccount;
    private String SH_remark;

    public Integer getCashOutID() {
        return CashOutID;
    }

    public void setCashOutID(Integer cashOutID) {
        CashOutID = cashOutID;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Boolean getIfPayment() {
        return ifPayment;
    }

    public void setIfPayment(Boolean ifPayment) {
        this.ifPayment = ifPayment;
    }

    public Integer getiStatus() {
        return iStatus;
    }

    public void setiStatus(Integer iStatus) {
        this.iStatus = iStatus;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Integer getIpaymentPlatform() {
        return ipaymentPlatform;
    }

    public void setIpaymentPlatform(Integer ipaymentPlatform) {
        this.ipaymentPlatform = ipaymentPlatform;
    }

    public String getSH_UserAccount() {
        return SH_UserAccount;
    }

    public void setSH_UserAccount(String SH_UserAccount) {
        this.SH_UserAccount = SH_UserAccount;
    }

    public String getSH_remark() {
        return SH_remark;
    }

    public void setSH_remark(String SH_remark) {
        this.SH_remark = SH_remark;
    }

    @Override
    public String toString() {
        return "PROCZHCashOutBackstageSAVEupInputDTO [" +
                "CashOutID=" + CashOutID +
                ", clientid=" + clientid +
                ", ifPayment=" + ifPayment +
                ", iStatus=" + iStatus +
                ", paymentNo=" + paymentNo +
                ", ipaymentPlatform=" + ipaymentPlatform +
                ", SH_UserAccount=" + SH_UserAccount +
                ", SH_remark=" + SH_remark +
                ']';
    }
}
