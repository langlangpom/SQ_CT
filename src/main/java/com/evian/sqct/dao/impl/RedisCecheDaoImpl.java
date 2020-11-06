package com.evian.sqct.dao.impl;

import com.evian.sqct.dao.ICacheDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:RedisCecheDaoImpl
 * Package:com.fun.dao.impl
 * Description:redis缓存操作类
 *
 * @Date:2020/3/19 15:09
 * @Author:XHX
 */
@Repository
public class RedisCecheDaoImpl implements ICacheDao {

    private final String IDENTSTR = "shuiqooSH:";

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    private String appendHeader(String key){

        StringBuilder identStr = new StringBuilder(IDENTSTR);
        return identStr.append(key).toString();
    }

    @Override
    public void set(String key, Object value) {
        if(StringUtils.isBlank(key)){
            throw new RuntimeException("redis key为空");
        }
        redisTemplate.opsForValue().set(appendHeader(key),value);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit unit) {

        redisTemplate.opsForValue().set(appendHeader(key),value,expire,unit);
    }

    @Override
    public void setExpireSeconds(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(appendHeader(key),value,expire,TimeUnit.SECONDS);
    }

    @Override
    public void setExpireHours(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(appendHeader(key),value,expire,TimeUnit.HOURS);
    }

    @Override
    public void setExpireDays(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(appendHeader(key),value,expire,TimeUnit.DAYS);
    }

    @Override
    public void expire(String key, long expire, TimeUnit unit) {
        redisTemplate.expire(appendHeader(key),expire,unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(appendHeader(key));
    }

    @Override
    public void removeValue(String key) {
        redisTemplate.delete(appendHeader(key));
    }

    @Override
    public Long sadd(String key,Object... values) {
        if(values==null||values.length==0){
            return 0L;
        }
        return redisTemplate.opsForSet().add(appendHeader(key),values);
    }

    @Override
    public Long saddExpireSeconds(String key, long expire,Object... values) {
        if(values==null||values.length==0){
            return 0L;
        }
        Long add = redisTemplate.opsForSet().add(appendHeader(key),values, expire, TimeUnit.SECONDS);
        return add;
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(appendHeader(key));
    }

    @Override
    public Set<String> keys(String hearder) {
        if(StringUtils.isBlank(hearder)){
            return new HashSet<String>();
        }
        Set<String> keys = redisTemplate.keys(appendHeader(hearder) + "*");
        return keys;
    }

    /**
     * 在 list 指定位置 插入值
     * 覆盖原有的值
     *
     * @param key   list的key
     * @param index 指定位置
     * @param value
     * @return
     */
    @Override
    public void lset(String key, int index, Object value) {
        redisTemplate.opsForList().set(appendHeader(key), index, value);
    }

    /**
     * 在 list 指定位置 插入值
     * 覆盖原有的值
     *
     * @param key   list的key
     * @param index 指定位置
     * @param value
     * @return
     */
    @Override
    public void lsetStr(String key, int index, String value) {
        stringRedisTemplate.opsForList().set(appendHeader(key), index, value);
    }

    /**
     * List头部追加记录
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果list不存在，则创建list 并进行push 操作
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long lpush(String key, Object... value) {
        return redisTemplate.opsForList().leftPushIfPresent(appendHeader(key),value);
    }

    /**
     * 根据 list的 key
     * 返回 list的 长度
     *
     * @param key
     * @return
     */
    @Override
    public long llen(String key) {
        return redisTemplate.opsForList().size(appendHeader(key));
    }

    /**
     * 移除并返回列表 key 的 最后一个值
     * 当 key 不存在时，返回 nil
     *
     * @param key@return
     */
    @Override
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(appendHeader(key));
    }

    /**
     * 获取list中 指定位置的值
     * index从0开始
     * 如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil
     *
     * @param key
     * @param index
     * @return
     */
    @Override
    public Object lindex(String key, int index) {
        return null;
    }
}
