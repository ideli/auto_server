package cn.mwee.auto.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ListUtil {

    private static Logger logger = LoggerFactory.getLogger(ListUtil.class);

    private ListUtil() {
        //工具类无需对象实例化
    }

    public static <T> List<T> createList(T t) {
        List<T> temp = new ArrayList<>();
        temp.add(t);
        return temp;
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isEmpty(Set<?> set) {
        return set == null || set.size() <= 0;
    }

    /**
     * 二分法查询
     *
     * @param A          待查找的List(必须已经按关键属性排好序)
     * @param x          待查找的元素
     * @param comparator 比较器
     * @param <T>
     * @return
     */
    public static <T> List<T> binarySearch(List<T> A, T x, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<T>();
        int low = 0, high = A.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int temp = comparator.compare(x, A.get(mid));
            if (temp == 0) {
                if (mid > 0) {
                    if (comparator.compare(x, A.get(mid - 1)) == 0) {
                        for (int i = mid - 1; i >= 0; i--) {
                            if (comparator.compare(A.get(i), x) == 0) {
                                result.add(A.get(i));
                            } else break;
                        }
                    }
                }
                result.add(A.get(mid));
                if (mid < high) {
                    if (comparator.compare(x, A.get(mid + 1)) == 0) {
                        for (int i = mid + 1; i <= high; i++) {
                            if (comparator.compare(x, A.get(i)) == 0) {
                                result.add(A.get(i));
                            } else break;
                        }
                    }
                }
                return result;

            } else if (temp < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    /**
     * 将list中的元素拼接成字符串
     *
     * @param list
     * @param joinStr 各元素之间的连接字符
     * @return
     */
    public static String getString(List<?> list, String joinStr) {
        if (list == null) {
            return "";
        }
        if (StringUtils.isEmpty(joinStr)) {
            joinStr = " ";
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o.toString() + joinStr);
        }

        String s = sb.toString();
        if (s.endsWith(joinStr)) {
            s = s.substring(0, s.length() - joinStr.length());

        }
        return s;
    }

    /**
     * 将字符串转换成List
     *
     * @param src   目标字符串，比如"A,B,C,D"
     * @param split 分割字符串，比如","
     * @return
     */
    public static List<String> getList(String src, String split) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        String[] t = src.split(split);
        List<String> list = new ArrayList<String>();
        for (String s : t) {
            list.add(s);
        }
        return list;
    }

}
