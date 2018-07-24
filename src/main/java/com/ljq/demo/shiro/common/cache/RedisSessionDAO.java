package com.ljq.demo.shiro.common.cache;

import com.ljq.demo.shiro.common.log.Log;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 由 redis 接管 shiro 默认 session 缓存
 * @Author: junqiang.lu
 * @Date: 2018/7/24
 */
@Component("redisSessionDAO")
public class RedisSessionDAO extends AbstractSessionDAO {

    /**
     * key前缀
     */
    private static final String SHIRO_REDIS_SESSION_KEY_PREFIX = "shiro.redis.session_";

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisSessionDAO(){}

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        Log.debug("shiro redis session [create]. sessionId = " + sessionId);
        this.assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(generateKey(sessionId), session, session.getTimeout(), TimeUnit.MILLISECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionID) {
        Log.debug("shiro redis session [read]. sessionId = " + sessionID);
        return (Session) redisTemplate.opsForValue().get(generateKey(sessionID));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Log.debug("shiro redis session [update]. sessionId = " + session.getId());
        redisTemplate.opsForValue().set(generateKey(session.getId()), session, session.getTimeout(),
                TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        Log.debug("shiro redis session [delete]. sessionId = " + session.getId());
        redisTemplate.delete(generateKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Object> keySet = redisTemplate.keys(generateKey("*"));
        Set<Session> sessionSet = new HashSet<>();
        if (CollectionUtils.isEmpty(keySet)) {
            return Collections.emptySet();
        }
        for (Object key : keySet) {
            sessionSet.add((Session) redisTemplate.opsForValue().get(key));
        }
        Log.debug("shiro redis session [all]. size = " + sessionSet.size());
        return sessionSet;
    }

    /**
     * 重组key
     * 区别其他使用环境的key
     *
     * @param key
     * @return
     */
    private String generateKey(Object key) {
        return SHIRO_REDIS_SESSION_KEY_PREFIX + this.getClass().getName() +"_"+ key;
    }

}
