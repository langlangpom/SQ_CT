package com.evian.sqct.bean.appletLiveStreaming.response;

/**
 * ClassName:WXGoodsAddRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 18:22
 * @Author:XHX
 */
public class WXGoodsAddRpeDTO {
    private Integer errcode;
    private Integer auditId;
    private Integer goodsId;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "WXGoodsAddRpeDTO [" +
                "errcode=" + errcode +
                ", auditId=" + auditId +
                ", goodsId=" + goodsId +
                ", errmsg=" + errmsg +
                ']';
    }
}
