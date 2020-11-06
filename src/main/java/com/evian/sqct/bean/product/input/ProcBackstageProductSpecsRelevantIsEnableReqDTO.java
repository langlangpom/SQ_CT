package com.evian.sqct.bean.product.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageProductSpecsRelevantIsEnableReqDTO
 * Package:com.evian.sqct.bean.product.input
 * Description:请为该功能做描述
 *
 * @Date:2020/11/4 16:32
 * @Author:XHX
 */
public class ProcBackstageProductSpecsRelevantIsEnableReqDTO {
    @NotNull
    private Integer relevantId;
    @NotNull
    private Boolean isEnable;

    public Integer getRelevantId() {
        return relevantId;
    }

    public void setRelevantId(Integer relevantId) {
        this.relevantId = relevantId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean enable) {
        isEnable = enable;
    }

    @Override
    public String toString() {
        return "ProcBackstageProductSpecsRelevantIsEnableReqDTO [" +
                "relevantId=" + relevantId +
                ", isEnable=" + isEnable +
                ']';
    }
}
