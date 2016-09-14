package cn.mwee.auto.deploy.util.cache;

import com.jcraft.jsch.Channel;

/**
 * Created by Administrator on 2016/9/13.
 */
public class JschChannelCache {
    private static Cache<Integer,Channel> cache = new Cache<>();
    public static Cache<Integer,Channel> getCache() {
        return cache;
    }
}
