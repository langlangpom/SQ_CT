package com.evian.sqct.dao.impl;

import com.evian.sqct.bean.sys.DaoConfig;
import com.evian.sqct.bean.sys.SpSprocColumns90;
import com.evian.sqct.dao.IBaseDao;
import com.evian.sqct.dao.ISystemDao;
import com.evian.sqct.exception.BaseRuntimeException;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.util.DaoUtil;
import com.evian.sqct.util.ResultSetToBeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * ClassName:BaseDaoImpl
 * Package:com.evian.sqct.impl
 * Description:请为该功能做描述
 *
 * @Date:2020/6/5 17:31
 * @Author:XHX
 */
@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {

    @Autowired
    private ISystemDao systemDao;

    @Autowired
    private DaoConfig daoConfig;



    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("vendorTemplate")
    private JdbcTemplate vendorDataSource;



    @Override
    public Map<String,Object> agencyDB(String storedProcedureName, T t,Class... className) {

        return executeRetryMechanism(0,jdbcTemplate,storedProcedureName,t,className);

/*
        // 是否开启了缓存
        if(daoConfig.getStoredProcedureCacheSwitch()){
            // 启用刷新缓存重试机制
            try {
                return (Map<String,Object>)execute(0,jdbcTemplate,storedProcedureName,t,className);
            } catch (TransientDataAccessResourceException e) {
                String message = e.getMessage();
                // 存储过程被更改更新缓存
                if(message.contains("索引")){
                    systemDao.removeCacheSp_sproc_columns_90(storedProcedureName);
                    return (Map<String,Object>)execute(0,jdbcTemplate,storedProcedureName,t,className);
                }else{
                    throw e;
                }
            }
        }else {
            return (Map<String,Object>)execute(0,jdbcTemplate,storedProcedureName,t,className);
        }*/


    }

    @Override
    public Map<String,Object> agencyDBVendor(String storedProcedureName, T t,Class... className) {

        return executeRetryMechanism(1,vendorDataSource,storedProcedureName,t,className);

        /*// 是否开启了缓存
        if(daoConfig.getStoredProcedureCacheSwitch()){
            // 启用刷新缓存重试机制
            try {
                return (Map<String,Object>)execute(1,vendorDataSource,storedProcedureName,t,className);
            } catch (TransientDataAccessResourceException e) {
                String message = e.getMessage();
                // 存储过程被更改更新缓存
                if(message.contains("索引")){
                    systemDao.removeCacheSp_sproc_columns_90Vendor(storedProcedureName);
                    return (Map<String,Object>)execute(1,vendorDataSource,storedProcedureName,t,className);
                }else{
                    throw e;
                }
            }
        }else {
            return (Map<String,Object>)execute(1,vendorDataSource,storedProcedureName,t,className);
        }*/


    }



    /**
     * 存储过程执行 参数错误重试机制
     * @param state
     * @param jdbcTemplate
     * @param storedProcedureName
     * @param t
     * @param className
     * @return
     */
    private Map<String,Object> executeRetryMechanism(int state,JdbcTemplate jdbcTemplate, String storedProcedureName, T t,Class... className){

        BiFunction<Integer,JdbcTemplate,Map<String,Object>> function = (s, j) -> (Map<String,Object>)execute(s,j,storedProcedureName,t,className);

        // 是否开启了缓存
        if(daoConfig.getStoredProcedureCacheSwitch()){
            // 启用刷新缓存重试机制
            try {
                return function.apply(state,jdbcTemplate);
            } catch (TransientDataAccessResourceException | UncategorizedSQLException e ) {
                String message = e.getMessage();

                System.out.println("存储过程错误重试-------");
                // 存储过程被更改更新缓存
                if(message.contains("索引")||message.contains("参数")){
                    systemDao.removeCacheSp_sproc_columns_90(storedProcedureName);
                    return function.apply(state,jdbcTemplate);
                }else{
                    throw e;
                }
            }
        }else {
            return (Map<String,Object>)execute(state,jdbcTemplate,storedProcedureName,t,className);
        }
    }

    /**
     * 执行存储过程
     * @param state 0 主库 1 售货机库
     * @param jdbcTemplate 连接Template
     * @param storedProcedureName 存储过程名
     * @param t 传参实体类
     * @param className 输出实体类性
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object execute(int state,JdbcTemplate jdbcTemplate, String storedProcedureName, T t,Class... className){
        long l1 = System.currentTimeMillis();

        // 1.获取存储过程参数----
        List<SpSprocColumns90> spSprocColumns90s;
        if(state==0){
            spSprocColumns90s = systemDao.sp_sproc_columns_90(storedProcedureName);
        }else{
            spSprocColumns90s = systemDao.sp_sproc_columns_90Vendor(storedProcedureName);
        }

        if(spSprocColumns90s==null||spSprocColumns90s.size()==0){
            // 没有该存储过程
            throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_NOT_STORAGE_DATA);
        }

        long l0 = System.currentTimeMillis();
        System.out.println("l0 = "+(l0-l1));

        // 2.拼接需要执行的存储过程名----
        String sql = jointCallStoredProcedureName(spSprocColumns90s,storedProcedureName);
        long l2 = System.currentTimeMillis();

        // 3.执行存储过程----
        return jdbcTemplate.execute(sql
                ,(CallableStatementCallback) cs ->{
                    long l20 = System.currentTimeMillis();
                    System.out.println("l20 = "+(l20-l2));
                    // 获取实体类所有get方法包括父类的get方法
                    List<Method> methods = gainGetFuntion(t);
                    long l21 = System.currentTimeMillis();
                    System.out.println("l21 = "+(l21-l20));
                    List<String> COLUMN_TYPE2 = new ArrayList<>();

                    for (int i=0;i<spSprocColumns90s.size();i++){
                        SpSprocColumns90 s = spSprocColumns90s.get(i);
                        short COLUMN_TYPE = s.getCOLUMN_TYPE().shortValue();
                        // 正常入参
                        if(COLUMN_TYPE==1){
                            String column_name = s.getCOLUMN_NAME();
                            // 去除参数前的@
                            column_name = column_name.replace("@","");
                            // 去除参数的_
                            String column_name2 = column_name.replace("_","");
                            String getName = "get"+column_name2;
                            for (int j = 0; j < methods.size(); j++){
                                String name = methods.get(j).getName();
                                if(name.replace("_","").equalsIgnoreCase(getName)){
                                    try {
                                        Object value = methods.get(j).invoke(t);
                                        System.out.println(column_name+" = "+value);
                                        cs.setObject(column_name,value);
                                        break;
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(j==methods.size()-1){
                                    // 没有找到对应的参数 先判断它是否是必传参数 1是可为空
                                    if(s.getNULLABLE()==1){
                                        cs.setObject(column_name,null);
                                        System.out.println(column_name+" = null");
                                    }else{
                                        throw BaseRuntimeException.jointCodeAndMessage(ResultCode.CODE_ERROR_STORAGE_DATA_INCORRECT.getCode(),"错误:"+storedProcedureName+"被修改");
                                    }
                                }
                            }


                        // 输出参数
                        }else if(COLUMN_TYPE==2){
                            String column_name = s.getCOLUMN_NAME();
                            // 去除参数前的@
                            column_name = column_name.replace("@","");
                            cs.registerOutParameter(column_name,s.getDATA_TYPE());
                            System.out.println(column_name+" = "+s.getDATA_TYPE());
                            COLUMN_TYPE2.add(column_name);
                        }

                    }
                    long l3 = System.currentTimeMillis();
                    System.out.println("l2 = "+(l3-l2));
                    System.out.println("l22 = "+(l3-l21));

                    // 可用于执行任何SQL语句，返回一个boolean值，表明执行该SQL语句是否返回了ResultSet。
                    boolean execute = cs.execute();


                    Map<String,Object> result = new HashMap<>();

                    // 返回输出参数
                    for (int i=0;i<COLUMN_TYPE2.size();i++){
                        String column_name = COLUMN_TYPE2.get(i);
                        result.put(column_name,cs.getObject(column_name));
                    }
                    // execute为false 说明没有select语句返回值
                    if(!execute){
                        return result;
                    }

                    // 返回集合
                    ResultSet rs = null;
                    try {
                        rs = cs.executeQuery();

                        int rNum = 0;
                        int classLength = 0;
                        if(className!=null&&className.length>0){
                            classLength = className.length;
                        }
                        if(rs!=null){
                            List result0 ;
                            if(classLength>=rNum&&rNum!=0){
                                result0 = ResultSetToBeanHelper.resultSetToList(rs, className[rNum]);
                            }else{
                                result0 = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                            }
                            result.put(DaoUtil.RESULT +rNum,result0);
                            rNum++;
                            while (cs.getMoreResults()){
                                rs = cs.getResultSet();
                                List resultNum ;
                                if(classLength>=rNum&&rNum!=0){
                                    resultNum = ResultSetToBeanHelper.resultSetToList(rs, className[rNum]);
                                }else{
                                    resultNum = ResultSetToBeanHelper.resultSetToList(rs, Map.class);
                                }
                                result.put(DaoUtil.RESULT+rNum,resultNum);
                                rNum++;
                            }
                        }
                        long l4 = System.currentTimeMillis();
                        System.out.println("l3 = "+(l4-l3));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }finally{
                        if(rs!=null){
                            rs.close();
                        }
                    }
                    return result;
                });
    }


    @Override
    public Map<String,Object> agencyDB(String storedProcedureName) {

        return null;
    }


    /**
     * 拼接需要执行的存储过程名
     * @param state 0 主库 1 售货机库
     * @param storedProcedureName 存储过程名
     * @return
     */
    private String jointCallStoredProcedureName(List<SpSprocColumns90> spSprocColumns90s,String storedProcedureName){
        StringBuilder sql = new StringBuilder("{call ")
                .append(storedProcedureName)
                .append("(");

        for (int i=0;i<spSprocColumns90s.size();i++){
            SpSprocColumns90 s = spSprocColumns90s.get(i);
            short COLUMN_TYPE = s.getCOLUMN_TYPE().shortValue();
            if(COLUMN_TYPE==1||COLUMN_TYPE==2){
                sql.append("?,");
            }
        }
        sql.deleteCharAt(sql.length() - 1).append(")}");
        return sql.toString();
    }


    /**
     * 获取实体类所有get方法包括父类的get方法
     * @param t
     * @return
     */
    private List<Method> gainGetFuntion(T t){
        List<Method> ms = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz!=null){
            Method[] m = clazz.getDeclaredMethods();
            for (Method mm :m){
                String name = mm.getName();
                if(name.contains("get")){
                    ms.add(mm);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return ms;
    }
}
