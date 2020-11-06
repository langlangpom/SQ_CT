package com.evian.sqct.dao;

import com.evian.sqct.bean.sys.SpSprocColumns90;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.dao.impl.GoodsShopCar;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ISysTemDao
 * Package:com.evian.sqct.dao
 * Description:系统存储过程dao
 *
 * @Date:2020/6/2 17:14
 * @Author:XHX
 */
public interface ISystemDao {

    List<SpSprocColumns90> sp_sproc_columns_90(String storedProcedureName);

    List<SpSprocColumns90> sp_sproc_columns_90Vendor(String storedProcedureName);

    /**
     * 删除缓存的存储过程数据
     * @param storedProcedureName 存储过程名
     */
    void removeCacheSp_sproc_columns_90(String storedProcedureName);

    /**
     * 删除缓存的存储过程数据
     * @param storedProcedureName 存储过程名
     */
    void removeCacheSp_sproc_columns_90Vendor(String storedProcedureName);

    List<Map<String, Object>> getShopCartGoodsOptimizeRedis(final int clientId, final int eid, final List<GoodsShopCar> goodsShopCars);

    List<SysParamModel> selectESystemConfig(String sysParam);

    List<SysParamModel> selectESystemConfig();
}
