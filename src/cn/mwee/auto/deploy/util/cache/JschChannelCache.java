package cn.mwee.auto.deploy.util.cache;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13.
 */
public class JschChannelCache {
    private static Cache<String, Channel> cache = new Cache<>();

    public static Cache<String, Channel> getCache() {
        return cache;
    }

    public static Map<String, String> getAllData() {
        Map<String, Channel> allData = getCache().getAllData();
        Map<String, String> resultData = new HashMap<>();
        allData.forEach((key, channel) -> {
            try {
                resultData.put(key,channel.getSession().getHost());
            } catch (JSchException e) {
                e.printStackTrace();
            }
        });
        return resultData;
    }
}
