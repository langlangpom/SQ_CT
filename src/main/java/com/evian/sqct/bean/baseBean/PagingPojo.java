package com.evian.sqct.bean.baseBean;

/**
 * ClassName:PagingPojo
 * Package:com.evian.sqct.bean.baseBean
 * Description:分页和时间父类
 *
 * @Date:2020/6/3 16:12
 * @Author:XHX
 */
public class PagingPojo {

    protected Integer PageIndex = 1;
    protected Integer PageSize = 20;
    protected Boolean IsSelectAll = false;
    protected String beginTime;
    protected String endTime;
    protected Integer iRows = 0;

    public Integer getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        PageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public Boolean getIsSelectAll() {
        return IsSelectAll;
    }

    public void setIsSelectAll(Boolean isSelectAll) {
        IsSelectAll = isSelectAll;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getiRows() {
        return iRows;
    }

    public void setiRows(Integer iRows) {
        this.iRows = iRows;
    }
}
