package cn.mwee.auto.deploy.util.cache;

import com.jcraft.jsch.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Cache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<K, V>();
    private DelayQueue<DelayItem<K>> queue = new DelayQueue<DelayItem<K>>();

    public Cache() {
        Runnable daemonTask = new Runnable() {
            @Override
            public void run() {
                daemonCheck();
            }
        };
        Thread daemonThread = new Thread(daemonTask);
        daemonThread.setDaemon(true);
        daemonThread.setName("Mw_Cache Daemon");
        daemonThread.start();
    }

    private void daemonCheck() {
        logger.info("cache service started.");
        while (true) {
            try {
                DelayItem<K> delayItem = queue.take();
                if (delayItem != null) {
                    K key = delayItem.getItem();
                    cacheObjMap.remove(key);
                }

            } catch (InterruptedException e) {
                logger.error("cache service stopped Interrupted", e);
                break;
            }
        }
        logger.info("cache service stopped.");
    }

    public V put(K key, V value, long timeout, TimeUnit unit) {
        V oldValue = cacheObjMap.put(key, value);
        if (oldValue != null) {
            queue.remove(key);
        }
        queue.put(new DelayItem<>(key, timeout, unit));
        return oldValue;
    }

    public V remove(K key) {
        V oldValue = cacheObjMap.remove(key);
        if (oldValue != null) {
            queue.remove(key);
        }
        return oldValue;
    }

    public V get (K key) {
        return cacheObjMap.get(key);
    }

    public boolean containKey(K key) {
        return cacheObjMap.containsKey(key);
    }

    public void clear() {
        cacheObjMap.clear();
        queue.clear();
    }

    public void timeOutDestroy(K key, V value){
        if (value !=null && value instanceof Channel) {
            Channel channel = (Channel) value;
            try {
                channel.disconnect();
            } catch (Exception e) {
                logger.error("",e);
            }
            try {
                channel.getSession().disconnect();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }
}
