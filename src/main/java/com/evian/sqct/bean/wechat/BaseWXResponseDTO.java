package com.evian.sqct.bean.wechat;

import java.io.Serializable;

/**
 * ClassName:BaseWXResponseDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/22 16:16
 * @Author:XHX
 */
public class BaseWXResponseDTO implements Serializable {

    protected Integer errcode;
    protected String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
