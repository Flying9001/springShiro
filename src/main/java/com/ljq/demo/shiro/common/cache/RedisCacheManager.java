package com.ljq.demo.shiro.common.cache;

import com.ljq.demo.shiro.common.log.Log;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: redis 缓存管理
 * @Author: junqiang.lu
 * @Date: 2018/7/23
 */
@Component("redisCacheManager")
public class RedisCacheManager implements CacheManager {

    private String cacheKeyPrefix = "shiro-redis:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Log.debug("Shiro Redis key: " + s);
        return new ShiroRedisCache(300,cacheKeyPrefix + s,redisTemplate);
    }


}
