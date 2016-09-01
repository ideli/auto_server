package cn.mwee.auto.auth.util;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by mengfanyuan on 2016/8/30.
 */
@Setter
public class RedisClient {
    private final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    private RedisTemplate redisTemplate;

    private RetryTemplate retryTemplate;

    private long defaultExpiredSeconds = 1800000;

    private String lockPrefix = "lock:";


    public void set(String key, Object value) {
        retryTemplate.execute(
                (RetryContext context) -> {
                    redisTemplate.opsForValue().set(key, value);
                    return StringUtils.EMPTY;
                },
                context -> {
                    logger.error("REDIS ERROR: set =>  key:" + key
                                    + ",value:" + value + " , retrying at " + context.getRetryCount() + " times",
                            context.getLastThrowable());
                    redisTemplate.opsForValue().set(key, value);
                    return StringUtils.EMPTY;
                }
        );
    }

    public void set(String key, Object value, long expiredSeconds) {
        retryTemplate.execute(
                (RetryContext context) -> {
                    redisTemplate.opsForValue().set(key, value,
                            expiredSeconds <= 0 ? defaultExpiredSeconds : expiredSeconds, TimeUnit.SECONDS);
                    return StringUtils.EMPTY;
                },
                context -> {
                    logger.error("REDIS ERROR: set =>  key:" + key
                                    + ",value:" + value + " , expiredSeconds:" + expiredSeconds + ", retrying at "
                                    + context.getRetryCount() + " times",
                            context.getLastThrowable());
                    redisTemplate.opsForValue().set(key, value,
                            expiredSeconds <= 0 ? defaultExpiredSeconds : expiredSeconds, TimeUnit.SECONDS);
                    return StringUtils.EMPTY;
                }
        );
    }


    public Object get(String key) {
        Object result = retryTemplate.execute(
                (RetryContext context) -> {
                    return redisTemplate.opsForValue().get(key);
                },
                context -> {
                    logger.error("REDIS ERROR: get =>  key:" + key
                            + ", retrying at " + context.getRetryCount() + " times", context.getLastThrowable());
                    return redisTemplate.opsForValue().get(key);
                }
        );
        return result;
    }

    public Object get(String key, long expiredSeconds) {
        Object result = retryTemplate.execute(
                (RetryContext context) -> {
                    redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
                    return redisTemplate.opsForValue().get(key);
                },
                context -> {
                    logger.error("REDIS ERROR: get =>  key:" + key
                            + ", retrying at " + context.getRetryCount() + " times", context.getLastThrowable());
                    redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
                    return redisTemplate.opsForValue().get(key);
                }
        );
        return result;
    }


    public void delete(String key) {
        retryTemplate.execute(
                (RetryContext context) -> {
                    redisTemplate.delete(key);
                    return StringUtils.EMPTY;
                },
                context -> {
                    logger.error("REDIS ERROR: delete =>  key:" + key
                                    + " , retrying at " + context.getRetryCount() + " times",
                            context.getLastThrowable());
                    redisTemplate.delete(key);
                    return StringUtils.EMPTY;
                }
        );
    }


    public byte hAdd(String key, String hashKey, Object value) {
        byte result = retryTemplate.execute(
                (RetryContext context) -> {
                    byte t = redisTemplate.opsForHash().hasKey(key, hashKey) ? (byte) 0 : (byte) 1;
                    redisTemplate.opsForHash().put(key, hashKey, value);
                    return t;
                },
                context -> {
                    logger.error("REDIS ERROR: hAdd =>  key:" + key
                            + ",hashKey:" + hashKey + " , value:" + value + ", retrying at " + context.getRetryCount()
                            + " times", context.getLastThrowable());
                    byte t = redisTemplate.opsForHash().hasKey(key, hashKey) ? (byte) 0 : (byte) 1;
                    redisTemplate.opsForHash().put(key, hashKey, value);
                    return t;
                }
        );
        return result;
    }

    public Object hGet(String key, String hashKey) {
        Object result = retryTemplate.execute(
                (RetryContext context) -> {
                    return redisTemplate.opsForHash().get(key, hashKey);
                },
                context -> {
                    logger.error("REDIS ERROR: hGet =>  key:" + key
                            + ", retrying at " + context.getRetryCount() + " times", context.getLastThrowable());
                    return redisTemplate.opsForHash().get(key, hashKey);
                }
        );
        return result;
    }

    public void hDel(String key, String hashKey) {
        retryTemplate.execute(
                (RetryContext context) -> {
                    redisTemplate.opsForHash().delete(key, hashKey);
                    return StringUtils.EMPTY;
                },
                context -> {
                    logger.error("REDIS ERROR: delete =>  key:" + key
                                    + " , retrying at " + context.getRetryCount() + " times",
                            context.getLastThrowable());
                    redisTemplate.opsForHash().delete(key, hashKey);
                    return StringUtils.EMPTY;
                }
        );
    }


}
