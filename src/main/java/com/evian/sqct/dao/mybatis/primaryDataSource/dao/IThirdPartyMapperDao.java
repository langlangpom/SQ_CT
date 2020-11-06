package com.evian.sqct.dao.mybatis.primaryDataSource.dao;

import com.evian.sqct.bean.thirdParty.RecruitOrderPojoBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName:IThirdPartyMapperDao
 * Package:com.evian.sqct.dao.mybatis.primaryDataSource.dao
 * Description:第三方 mapper
 *
 * @Date:2020/10/26 11:39
 * @Author:XHX
 */
@Repository
public interface IThirdPartyMapperDao {

    int deleteRecruitGood(Integer id);

    List<RecruitOrderPojoBean> selectRecruitOrderByOrderGuid(String order_guid);
}
