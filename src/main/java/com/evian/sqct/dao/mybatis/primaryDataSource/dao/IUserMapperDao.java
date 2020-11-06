package com.evian.sqct.dao.mybatis.primaryDataSource.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * ClassName:IUserMapperDao
 * Package:com.evian.sqct.dao.mybatis.primaryDataSource.dao
 * Description:请为该功能做描述
 *
 * @Date:2020/10/26 15:04
 * @Author:XHX
 */
@Repository
public interface IUserMapperDao {
    Map<String,Object> selectNicknameByAccount(String account);
}
