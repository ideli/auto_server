package cn.mwee.auto.auth.util;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SqlUtils {
    public static String wrapLike(String param) {
        StringBuilder sb = new StringBuilder();
        return sb.append("%").append(param).append("%").toString();
    }
}
