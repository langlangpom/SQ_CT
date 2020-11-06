package com.evian.sqct.dao.mybatis.vendorDataSource.dao;

import com.evian.sqct.bean.vendor.SkipShuiqooProduct;
import com.evian.sqct.bean.vendor.write.SkipShuiqooProductRepDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName:SkipShuiqooProductDao
 * Package:com.evian.sqct.dao.mybatis.dao
 * Description:请为该功能做描述
 *
 * @Date:2020/9/30 10:27
 * @Author:XHX
 */
@Repository
public interface ISkipShuiqooProductDao {

    SkipShuiqooProduct select(Integer id);

    int insertSkipShuiqooProduct(SkipShuiqooProduct skipShuiqooProduct);

    List<SkipShuiqooProduct> selectSkipShuiqooProductByEid(Integer eid);

    int deleteSkipShuiqooProduct(Integer id);

    List<SkipShuiqooProductRepDTO> selectSkipShuiqooProductRepByEid(Integer eid);
}
