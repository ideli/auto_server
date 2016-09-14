package cn.mwee.auto.deploy.util.cache;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/13.
 */
public class JschChannelCache {
    private static Cache<Integer, Channel> cache = new Cache<>();

    public static Cache<Integer, Channel> getCache() {
        return cache;
    }

    public static Map<Integer, String> getAllData() {
        Map<Integer, Channel> allData = getCache().getAllData();
        Map<Integer, String> resultData = new HashMap<>();
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
