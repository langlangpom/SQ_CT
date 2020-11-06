package com.evian.sqct.bean.vendor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * ClassName:SkipShuiqooProduct
 * Package:com.evian.sqct.bean.vendor
 * Description:skip_shuiqoo_product
 *
 * @Date:2020/9/30 9:26
 * @Author:XHX
 */
public class SkipShuiqooProduct {
    private Integer id;
    @NotNull
    private Integer pid;
    @NotNull
    private Integer eid;
    private Integer sort;
    private Date createTime;
    @NotNull
    private String creater;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "SkipShuiqooProduct [" +
                "id=" + id +
                ", pid=" + pid +
                ", eid=" + eid +
                ", sort=" + sort +
                ", createTime=" + createTime +
                ", creater=" + creater +
                ']';
    }
}
