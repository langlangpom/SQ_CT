package com.evian.sqct.bean.appletLiveStreaming.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ClassName:WXBatchgetMaterialRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 18:00
 * @Author:XHX
 */
public class WXBatchgetMaterialRpeDTO {

    private Integer errcode;
    private String errmsg;
    @JsonProperty("total_count")
    private Integer Count;
    List<WXBatchgetMaterialItemRpeDTO> item;

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

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }


    public List<WXBatchgetMaterialItemRpeDTO> getItem() {
        return item;
    }

    public void setItem(List<WXBatchgetMaterialItemRpeDTO> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "WXBatchgetMaterialRpeDTO [" +
                "errcode=" + errcode +
                ", errmsg=" + errmsg +
                ", Count=" + Count +
                ", item=" + item +
                ']';
    }
}
