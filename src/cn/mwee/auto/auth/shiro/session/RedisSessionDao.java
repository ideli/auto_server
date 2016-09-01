package cn.mwee.auto.auth.shiro.session;

import cn.mwee.auto.auth.util.RedisClient;
import lombok.Setter;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Administrator on 2016/8/29.
 */
@Setter
public class RedisSessionDao extends AbstractSessionDAO {

    private static final String SESSION_KEY="mwAuto:shiroSession:";

    private RedisClient redisClient;

    private long defaultSessionTimeOut;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
//        redisClient.hAdd(SESSION_KEY,sessionId.toString(),session);
        redisClient.set(SESSION_KEY+sessionId.toString(),session,defaultSessionTimeOut);
        return sessionId;

    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session)redisClient.get(SESSION_KEY+sessionId.toString(),defaultSessionTimeOut);
//        return (Session)redisClient.hGet(SESSION_KEY,sessionId.toString());
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        redisClient.set(SESSION_KEY+session.getId().toString(),session,defaultSessionTimeOut);
//        redisClient.hAdd(SESSION_KEY,session.getId().toString(),session);
    }

    @Override
    public void delete(Session session) {
        redisClient.hDel(SESSION_KEY,session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return Collections.emptySet();
    }
}
