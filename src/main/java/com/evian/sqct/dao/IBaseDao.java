package com.evian.sqct.dao;

import java.util.Map;

/**
 * ClassName:IBaseDao
 * Package:com.evian.sqct.dao
 * Description:请为该功能做描述
 *
 * @Date:2020/6/5 17:31
 * @Author:XHX
 */
public interface IBaseDao<T> {

    Map<String,Object> agencyDB(String storedProcedureName, T t,Class... className);

    Map<String,Object> agencyDBVendor(String storedProcedureName, T t,Class... className);

    Map<String,Object> agencyDB(String storedProcedureName);
}
