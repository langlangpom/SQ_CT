package com.evian.sqct.bean.appletLiveStreaming.response;

import java.util.List;

/**
 * ClassName:GetapprovedRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:请为该功能做描述
 *
 * @Date:2020/6/19 14:31
 * @Author:XHX
 */
public class GetapprovedRpeDTO {
    private Integer Count;
    private List<WXGetapprovedGoodsRpeDTO> goods;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public List<WXGetapprovedGoodsRpeDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<WXGetapprovedGoodsRpeDTO> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "GetapprovedRpeDTO [" +
                "Count=" + Count +
                ", goods=" + goods +
                ']';
    }
}
