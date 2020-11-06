package com.evian.sqct.dao;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:ICacheDao
 * Package:com.fun.dao
 * Description:缓存工具
 *
 * @Date:2020/3/19 15:03
 * @Author:XHX
 */
public interface ICacheDao {
    void set(String key, Object value);
    void set(String key, Object value, long expire, TimeUnit unit);
    void setExpireSeconds(String key, Object value, long expire);
    void setExpireHours(String key, Object value, long expire);
    void setExpireDays(String key, Object value, long expire);
    void expire(String key, long expire, TimeUnit unit);
    Object get(String key);
    void removeValue(String key);

    Long sadd(String key, Object... values);

    Long saddExpireSeconds(String key, long expire, Object... values);

    boolean hasKey(String key);

    /**
     * 模糊匹配查询所有 前缀带有hearder 的key
     * @param hearder
     * @return
     */
    Set<String> keys(String hearder);

    /**
     * 在 list 指定位置 插入值
     * 覆盖原有的值
     *
     * @param redisPool
     * @param key list的key
     * @param index 指定位置
     * @param value
     * @return
     */
    void lset(String key, int index, Object value);

    /**
     * 在 list 指定位置 插入值
     * 覆盖原有的值
     *
     * @param redisPool
     * @param key list的key
     * @param index 指定位置
     * @param value
     * @return
     */
    void lsetStr(String key, int index, String value);

    /**
     * List头部追加记录
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果list不存在，则创建list 并进行push 操作
     *
     * @param redisPool
     * @param key
     * @param value
     * @return
     */
    long lpush(String key, Object... value);

    /**
     * 根据 list的 key
     * 返回 list的 长度
     *
     * @param redisPool
     * @param key
     * @return
     */
    long llen(String key);

    /**
     * 移除并返回列表 key 的 最后一个值
     * 当 key 不存在时，返回 nil
     *
     * @param redisPool
     * @return
     */
    Object rPop(String key);

    /**
     * 获取list中 指定位置的值
     * index从0开始
     * 如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil
     *
     * @param key
     * @param index
     * @return
     */
    Object lindex(String key,int index);
}
