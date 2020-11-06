package com.evian.sqct.bean.product.input;

import javax.validation.constraints.NotNull;

/**
 * ClassName:ProcBackstageProductSpecsRelevantSelectReqDTO
 * Package:com.evian.sqct.bean.product.input
 * Description:请为该功能做描述
 *
 * @Date:2020/11/4 16:17
 * @Author:XHX
 */
public class ProcBackstageProductSpecsRelevantSelectReqDTO {
    @NotNull
    private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "ProcBackstageProductSpecsRelevantSelectReqDTO [" +
                "pid=" + pid +
                ']';
    }
}
