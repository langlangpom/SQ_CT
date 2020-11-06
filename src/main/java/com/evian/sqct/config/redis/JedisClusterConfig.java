package com.evian.sqct.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * redis集群必须redis 3.0以上版本的支持
 * redis集群
 */
@Configuration
@Component
public class JedisClusterConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterConfig.class);

    @Autowired
    private Environment env;

    /**
     * Spring Data Redis 1.7 支持redis集群
     * jedis集群配置
     *
     * @return
     */
    @Bean
    @Primary
    public RedisConnectionFactory connectionFactory() {
        // 单机node
        String standAloneNode = env.getProperty("spring.redis.host");
        String clusterNodes = env.getProperty("spring.redis.cluster.nodes");
        String sentinelNodes = env.getProperty("spring.redis.sentinel.nodes");
        String master = env.getProperty("spring.redis.sentinel.master");
        Integer maxIdle = Integer.parseInt(env.getProperty("spring.redis.pool.max-idle"));
        RedisConnectionFactory redisConnectionFactory;
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);

        //  单机
        if(!StringUtils.isEmpty(standAloneNode)){
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
            String port = env.getProperty("spring.redis.port");
            jedisConnectionFactory.setHostName(standAloneNode);
            jedisConnectionFactory.setPort(Integer.parseInt(port));
            redisConnectionFactory = jedisConnectionFactory;

        //  cluster集群
        }else if(!StringUtils.isEmpty(clusterNodes)){
            Set<String> nodes = StringUtils.commaDelimitedListToSet(clusterNodes);
            redisConnectionFactory = new JedisConnectionFactory(new RedisClusterConfiguration(nodes),jedisPoolConfig);

        //  sentine模式
        }else{
            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(master,StringUtils.commaDelimitedListToSet(sentinelNodes));
            redisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,jedisPoolConfig);
        }

        return redisConnectionFactory;
    }
}