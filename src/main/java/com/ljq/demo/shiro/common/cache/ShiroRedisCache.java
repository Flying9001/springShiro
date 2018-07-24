package com.ljq.demo.shiro.common.cache;

import com.ljq.demo.shiro.common.log.Log;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: shiro redis 缓存
 * @Author: junqiang.lu
 * @Date: 2018/7/23
 */
public class ShiroRedisCache<K,V> implements Cache<K,V> {

    private RedisTemplate<K, V> redisTemplate;// 通过构造方法注入该对象
    private long expireTime = 300;// 缓存的超时时间，单位为s
    private String cacheKey = "shiro-redis:";

    public ShiroRedisCache(){
    }

    public ShiroRedisCache(String cacheKey, RedisTemplate redisTemplate){
        this.cacheKey = cacheKey;
        this.redisTemplate = redisTemplate;
    }
    public ShiroRedisCache(long expireTime, String cacheKey, RedisTemplate redisTemplate){
        this.expireTime = expireTime;
        this.cacheKey = cacheKey;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过key来获取对应的缓存对象
     * 通过源码我们可以发现，shiro需要的key的类型为Object，V的类型为AuthorizationInfo对象
     */
    @Override
    public V get(K key) throws CacheException {
        Log.debug("key: " + key);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 将权限信息加入缓存中
     */
    @Override
    public V put(K key, V value) throws CacheException {
        Log.debug("key: " + key + " , value: " + value);
        redisTemplate.opsForValue().set(key, value, this.expireTime, TimeUnit.SECONDS);
        return value;
    }

    /**
     * 将权限信息从缓存中删除
     */
    @Override
    public V remove(K key) throws CacheException {
        Log.debug(key.toString());
        V v = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().getOperations().delete(key);
        return v;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }



}
