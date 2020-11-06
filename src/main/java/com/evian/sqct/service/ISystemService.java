package com.evian.sqct.service;

/**
 * ClassName:ISystemService
 * Package:com.evian.sqct.service
 * Description:请为该功能做描述
 *
 * @Date:2020/6/9 11:17
 * @Author:XHX
 */
public interface ISystemService {

    boolean updateStoredProcedureCacheSwitch();

    boolean updateStoredProcedureCacheState(String state);

    Object test(String t);
}
