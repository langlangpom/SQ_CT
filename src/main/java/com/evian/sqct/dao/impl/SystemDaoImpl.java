package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.sys.DaoConfig;
import com.evian.sqct.bean.sys.SpSprocColumns90;
import com.evian.sqct.bean.sys.SysParamModel;
import com.evian.sqct.dao.ICacheDao;
import com.evian.sqct.dao.ISystemDao;
import com.evian.sqct.util.ResultSetToBeanHelper;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SystemDaoImpl
 * Package:com.evian.sqct.impl
 * Description:系统存储过程
 *
 * @Date:2020/6/2 17:49
 * @Author:XHX
 */
@Repository("systemDao")
public class SystemDaoImpl implements ISystemDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("vendorTemplate")
    private JdbcTemplate vendorDataSource;

    @Autowired
    private DaoConfig daoConfig;

    @Autowired
    private ICacheDao cacheDao;

    private final String HEARDER = "storageProcess:";

    private final String VENDORHEARDER = "vendorStorageProcess:";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<SpSprocColumns90> sp_sproc_columns_90(String storedProcedureName) {
        return executeSelect(0,storedProcedureName,jdbcTemplate);
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<SpSprocColumns90> sp_sproc_columns_90Vendor(String storedProcedureName) {
        return executeSelect(1,storedProcedureName,vendorDataSource);
    }

    /**
     * 删除缓存的存储过程数据
     *
     * @param storedProcedureName 存储过程名
     */
    @Override
    public void removeCacheSp_sproc_columns_90(String storedProcedureName) {
        cacheDao.removeValue(appendHeader(storedProcedureName));
    }

    /**
     * 删除缓存的存储过程数据
     *
     * @param storedProcedureName 存储过程名
     */
    @Override
    public void removeCacheSp_sproc_columns_90Vendor(String storedProcedureName) {
        cacheDao.removeValue(appendVerdorHeader(storedProcedureName));
    }

    /**
     * 执行存储过程查询
     * @param state
     * @param storedProcedureName
     * @param jdbcTemplate
     * @return
     */
    private List<SpSprocColumns90> executeSelect(int state, String storedProcedureName, JdbcTemplate jdbcTemplate){
        List<SpSprocColumns90> result = null;
        // 先获取缓存的数据
        result = cacheGetDate(state, storedProcedureName);

        if(result==null){
            result = (List<SpSprocColumns90>)jdbcTemplate.execute(
                    "{call sp_sproc_columns_90(?,?,?,?,?,?)}",
                    (CallableStatementCallback) cs -> {
                        return sp_sproc_columns_90DBOper(cs,storedProcedureName);
                    });

            // 存入缓存
            cacheSetDate(result, storedProcedureName);
        }
        return result;
    }

    /**
     * 获取缓存数据
     * @param state
     * @param storedProcedureName
     * @return
     */
    private List<SpSprocColumns90> cacheGetDate(int state,String storedProcedureName){
        List<SpSprocColumns90> result = null;
        if(daoConfig.getStoredProcedureCacheSwitch()){
            if(DaoConfig.REDIS.equals(daoConfig.getStoredProcedureCacheState())){
                result = redisCacheGetDate(state, storedProcedureName);
            }else if(DaoConfig.MAP.equals(daoConfig.getStoredProcedureCacheState())){
                result = mapCacheGetDate(state, storedProcedureName);
            }
        }
        return result;
    }

    /**
     * 获取redis 缓存
     * @param state
     * @param storedProcedureName
     * @return
     */
    private List<SpSprocColumns90> redisCacheGetDate(int state,String storedProcedureName){
        List<SpSprocColumns90> result = null;
        try {
            if(state==0){
                result = (List<SpSprocColumns90>) cacheDao.get(appendHeader(storedProcedureName));

            }else{
                result = (List<SpSprocColumns90>) cacheDao.get(appendVerdorHeader(storedProcedureName));
            }
        } catch (RedisConnectionFailureException e) {
            daoConfig.setSpcSwitch(false);
            logger.error("连接redis异常,切断存储过程入参缓存存储:{}",e);
        }
        return result;
    }

    /**
     * 获取map 缓存
     * @param state
     * @param storedProcedureName
     * @return
     */
    private List<SpSprocColumns90> mapCacheGetDate(int state,String storedProcedureName){
        daoConfig.setSpcSwitch(false);
        return null;
    }

    /**
     * 存入缓存
     * @param result
     * @param storedProcedureName
     */
    private void cacheSetDate(List<SpSprocColumns90> result,String storedProcedureName) {
        // 存入缓存
        if(daoConfig.getStoredProcedureCacheSwitch()){
            if(DaoConfig.REDIS.equals(daoConfig.getStoredProcedureCacheState())){
                redisCacheSetDate(result, storedProcedureName);
            }else if(DaoConfig.MAP.equals(daoConfig.getStoredProcedureCacheState())){
                mapCacheSetDate(result, storedProcedureName);
            }
        }
    }

    /**
     * 存入redis缓存
     * @param result
     * @param storedProcedureName
     */
    private void redisCacheSetDate(List<SpSprocColumns90> result,String storedProcedureName){
        // 没查询到就缓存 300秒=五分钟
        if(result.size()==0){
            cacheDao.setExpireSeconds(appendHeader(storedProcedureName),result,300);
        }else{
            cacheDao.setExpireDays(appendHeader(storedProcedureName),result,1);
        }
    }

    /**
     * 存入map缓存
     * @param result
     * @param storedProcedureName
     */
    private void mapCacheSetDate(List<SpSprocColumns90> result,String storedProcedureName){

    }




    private Object sp_sproc_columns_90DBOper(CallableStatement cs,String storedProcedureName) throws SQLException {
        cs.setString("procedure_name", storedProcedureName);
        cs.setString("procedure_owner", null);
        cs.setObject("procedure_qualifier", null);
        cs.setString("column_name", null);
        cs.setInt("ODBCVer", 2);
        cs.setBoolean("fUsePattern", true);
        cs.execute();
        ResultSet rs = cs.executeQuery();
        try {
            return ResultSetToBeanHelper.resultSetToList(rs, SpSprocColumns90.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    private String appendHeader(String key){
        StringBuilder identStr = new StringBuilder(HEARDER);
        return identStr.append(key).toString();
    }

    private String appendVerdorHeader(String key){
        StringBuilder identStr = new StringBuilder(VENDORHEARDER);
        return identStr.append(key).toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map<String, Object>> getShopCartGoodsOptimizeRedis(final int clientId, final int eid, final List<GoodsShopCar> goodsShopCars) {
        List<Map<String, Object>> results = (List<Map<String, Object>>) jdbcTemplate.execute(
                (CallableStatementCreator) con -> {
                    SQLServerCallableStatement cs = con.prepareCall("{call Proc_DisPark_Get_ShopCat_Product_Combo_Redis_Back(?,?,?)}")
                            .unwrap(com.microsoft.sqlserver.jdbc.SQLServerCallableStatement.class);
                    // 使用SQLServerDataTable对象来传递表参数
                    SQLServerDataTable sourceTable = new SQLServerDataTable();
                    // 在表对象中添加字段的信息
                    sourceTable.addColumnMetadata("id", Types.BIGINT);
                    sourceTable.addColumnMetadata("eid", Types.INTEGER);
                    sourceTable.addColumnMetadata("shopId", Types.INTEGER);
                    sourceTable.addColumnMetadata("clientId", Types.INTEGER);
                    sourceTable.addColumnMetadata("pid", Types.INTEGER);
                    sourceTable.addColumnMetadata("number", Types.INTEGER);
                    sourceTable.addColumnMetadata("settleStyle", Types.NVARCHAR);
                    sourceTable.addColumnMetadata("dateCreated", Types.BIGINT);
                    sourceTable.addColumnMetadata("dateUpdated", Types.BIGINT);
                    sourceTable.addColumnMetadata("fpid", Types.INTEGER);
                    sourceTable.addColumnMetadata("activityId", Types.INTEGER);

                    for (GoodsShopCar v : goodsShopCars) {
                        sourceTable.addRow(
                                v.getId(), v.getEid(),
                                v.getShopId(), v.getClientId(),
                                v.getPid(), v.getNumber(),
                                v.getSettleStyle(), v.getDateCreated(),
                                v.getDateUpdated(), v.getFpid(),
                                v.getActivityId());
                    }
                    cs.setStructured(1, "e_shopcar_data", sourceTable);
                    cs.setObject("eid", eid);
                    cs.setObject("clientId", clientId);
                    return cs;
                },
                (CallableStatementCallback) cs -> {
                    List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
                    try {
                        ResultSet rs = cs.executeQuery();
                        //购物车商品集合
                        while (rs.next()) {
                            Map<String, Object> productMap = new HashMap<String, Object>();
                            productMap.put("eid", rs.getInt("eid"));
                            productMap.put("eName", rs.getString("eName"));
                            productMap.put("logoUrl", rs.getString("logoUrl"));

                            productMap.put("shopId", rs.getInt("shopId"));
                            productMap.put("shopNo", rs.getString("shopNo"));
                            productMap.put("shopName", rs.getString("shopName"));
//                            productMap.put("pictureUrl", Constants.replaceNull(rs.getString("pictureUrl"), String.class));
                            int sendTime = rs.getInt("sendOnTime");
                            productMap.put("sendOnTime", sendTime == 0 ? 60 : sendTime);
                            productMap.put("chooseTimeItem", rs.getBoolean("chooseTimeItem"));

                            productMap.put("minSendPrice", rs.getBigDecimal("minSendPrice").doubleValue());
                            productMap.put("freight", rs.getBigDecimal("freight").doubleValue());
                            productMap.put("grade", rs.getInt("grade"));
                            productMap.put("location", rs.getString("location"));
                            productMap.put("startTime", rs.getString("startTime"));
                            productMap.put("endTime", rs.getString("endTime"));
                            productMap.put("ifGroupBuy", rs.getBoolean("ifGroupBuy"));

                            productMap.put("pid", rs.getInt("pid"));
                            productMap.put("pcode", rs.getString("pcode"));
                            productMap.put("pname", rs.getString("pname"));
                            productMap.put("unit", rs.getString("unit"));
                            productMap.put("price", rs.getBigDecimal("price").doubleValue());
                            productMap.put("vipPrice", rs.getBigDecimal("vipPrice").doubleValue());
//                            productMap.put("productPic", Constants.replaceNull(rs.getString("productPic"), String.class));
                            productMap.put("repertoryNum", rs.getInt("repertoryNum"));
                            productMap.put("salesNum", rs.getInt("salesNum"));
                            productMap.put("ifCombo", rs.getBoolean("ifCombo"));
                            productMap.put("ifTicket", rs.getBoolean("ifTicket"));
                            productMap.put("ifHot", rs.getBoolean("ifHot"));
                            productMap.put("number", rs.getInt("number"));
                            productMap.put("settleStyle", rs.getString("settleStyle"));
                            productMap.put("sendLocation", rs.getString("sendLocation"));
                            productMap.put("hashTicket", rs.getBoolean("hashTicket"));
                            productMap.put("linepay", rs.getBoolean("linepay"));
//                            productMap.put("smallPictureUrl", Constants.replaceNull(rs.getString("smallPictureUrl"), String.class));
                            productMap.put("stepNum", rs.getInt("stepNum"));
                            productMap.put("maxTransactionNum", rs.getInt("maxTransactionNum"));
//                            productMap.put("property", Constants.replaceNull(rs.getString("property"), String.class));
                            productMap.put("surplusNum", rs.getInt("surplusNum"));
                            productMap.put("ifpledge", rs.getBoolean("ifpledge"));

                            productMap.put("cityId", rs.getInt("cityId"));
                            productMap.put("dateCreated", rs.getLong("dateCreated"));
                            productMap.put("fpid", rs.getInt("fpid"));
                            productMap.put("maxnum", rs.getInt("maxnum"));
                            productMap.put("id", rs.getInt("id"));
                            productMap.put("activityId", rs.getInt("activityId"));
                            productMap.put("saleaRamrk", rs.getString("saleaRamrk"));
                            productMap.put("popUpOnLine", rs.getBoolean("popUpOnLine"));
                            productMap.put("isDouble", rs.getBoolean("isDouble"));
                            productMap.put("ticketIsPledge", rs.getBoolean("ticketIsPledge"));
//                            productMap.put("synopsis", Constants.replaceNull(rs.getString("synopsis"), String.class));
                            productList.add(productMap);
                        }
                        rs.close();

                        //统计电子票余量
                        if (productList.size() > 0 && cs.getMoreResults()) {
                            ResultSet rs1 = cs.getResultSet();
                            //电子票余量字典
                            Map<Integer, Integer> ticketNumDic = new HashMap<Integer, Integer>();
                            while (rs1.next()) {
                                int pid = rs1.getInt("pid");
                                int surplusNum = rs1.getInt("surplusNum");
                                if (ticketNumDic.containsKey(pid)) {
                                    ticketNumDic.put(pid, ticketNumDic.get(pid) + surplusNum);
                                } else {
                                    ticketNumDic.put(pid, surplusNum);
                                }
                            }
                            rs1.close();

                            //统计共享票余量
                            if (cs.getMoreResults()) {
                                ResultSet rs2 = cs.getResultSet();
                                while (rs2.next()) {
                                    int surplusNum = rs2.getInt("surplusNum");
                                    int pid = rs2.getInt("pid");
                                    if (ticketNumDic.containsKey(pid)) {
                                        ticketNumDic.put(pid, ticketNumDic.get(pid) + surplusNum);
                                    } else {
                                        ticketNumDic.put(pid, surplusNum);
                                    }
                                }
                                rs2.close();
                            }

                            //匹配电子票数量
                            for (Map<String, Object> map : productList) {
                                int pid = (Integer) map.get("pid");
                                if (ticketNumDic.containsKey(pid)) {
                                    map.put("surplusNum", ticketNumDic.get(pid));
                                }
                            }
                        }

                        //匹配买赠活动说明
                        if (productList.size() > 0 && cs.getMoreResults()) {
                            ResultSet rs3 = cs.getResultSet();
                            while (rs3.next()) {
                                int pid = rs3.getInt("pid");
                                int cityId = rs3.getInt("cityId");
                                String saleaRamrk = rs3.getString("remark");
                                for (Map<String, Object> map : productList) {
                                    if (pid == (Integer) map.get("pid") && cityId == (Integer) map.get("cityId") && "现金".equals(map.get("settleStyle").toString())) {
                                        map.put("saleaRamrk", saleaRamrk);
                                    }
                                }
                            }
                            rs3.close();
                        }
                        return productList;
                    } catch (Exception e) {
                        logger.error("ex:{}", new Object[]{e});
                        return productList;
                    }
                });
        return results;
    }

    @Override
    public List<SysParamModel> selectESystemConfig(String sysParam) {
        String sql = "select sysId, sysParam, sysParamName, sysValue, sysDescription, sysSort, sysGroup, enabled from e_sys_config where sysParam=?";
        return jdbcTemplate.query(sql,new Object[]{sysParam},new BeanPropertyRowMapper<>(SysParamModel.class));
    }

    @Override
    public List<SysParamModel> selectESystemConfig() {
        String sql = "select sysId, sysParam, sysParamName, sysValue, sysDescription, sysSort, sysGroup, enabled from e_sys_config";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(SysParamModel.class));
    }
}
