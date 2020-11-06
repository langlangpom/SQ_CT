package com.evian.sqct.bean.appletLiveStreaming.response;

import java.util.List;

/**
 * ClassName:BatchgetMaterialRpeDTO
 * Package:com.evian.sqct.bean.appletLiveStreaming.response
 * Description:batchgetMaterialRpeDTO
 *
 * @Date:2020/6/19 18:03
 * @Author:XHX
 */
public class BatchgetMaterialRpeDTO {
    private Integer Count;
    List<WXBatchgetMaterialItemRpeDTO> item;

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
        return "BatchgetMaterialRpeDTO [" +
                "Count=" + Count +
                ", item=" + item +
                ']';
    }
}
