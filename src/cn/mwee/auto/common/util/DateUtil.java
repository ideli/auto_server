package cn.mwee.auto.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.DateFormatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 */
public class DateUtil {


    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);


    public static final long MILLISECONDS_SECOND = 1000;
    public static final long MILLISECONDS_MINUTE = 60000;
    public static final long MILLISECONDS_HOUR = 3600000;
    public static final long MILLISECONDS_DAY = 86400000;
    public static final long MILLISECONDS_WEEK = 604800000;
    public static final long SECONDS_MONTH = 2764800;

    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy_MM = "yyyy-MM";
    public final static String HH_mm_ss = "HH:mm:ss";
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public final static String yyyyMMdd = "yyyyMMdd";
    public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    private DateUtil() {
        //工具类无需对象实例化
    }

    static final Calendar calendar = Calendar.getInstance();

    /**
     * 获取字符串年份
     *
     * @param time
     * @return
     */
    public static String getYearString(String time) {
        return time.substring(0, 4);
    }

    /**
     * 以yyyy-MM-dd HH:mm:ss格式显示当前时间
     *
     * @return
     * @throws ParseException
     */
    public static Date getNowDate() throws ParseException {
        return getString2Date(getNowDate2String());
    }

    /**
     * 得到当前日期的字符串
     *
     * @return
     */
    public static String getNowDate2String() {
        return DateFormatUtils.format(new Date(), yyyy_MM_dd_HH_mm_ss);
    }

    public static String getNowDate2String(String format) {
        return DateFormatUtils.format(new Date(), format);
    }

    public static String getTimestamp2String(Timestamp timestamp) {
        return DateFormatUtils.format(timestamp, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * Date To String转化
     *
     * @param date
     * @return
     */
    public static String getDate2String(Date date) {
        return getDate2String(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static String getDate2String(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

    /**
     * ]
     * String To Date转化
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getString2Date(String date) throws ParseException {
        return getString2Date(date, yyyy_MM_dd_HH_mm_ss);
    }

    public static Date getString2Date(String date, String format) throws ParseException {
        String[] date_format = {format};
        return DateUtils.parseDate(date, date_format);
    }

    /**
     * 等效于php中time()的返回值
     *
     * @return 返回当前时间的总秒数(从1970-01-01 00:00:00算起)
     */
    public static long getPhpTime() {
        return new Date().getTime() / MILLISECONDS_SECOND;
    }

    public static long getPhpTime(Date d) {
        return d.getTime() / MILLISECONDS_SECOND;
    }

    /**
     * 获取今天起始时间字符串
     *
     * @return
     */
    public static String getTodayBegin2String() {
        return DateFormatUtils.format(getTodayBegin(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取今天结束时间字符串
     *
     * @return
     */
    public static String getTodayEnd2String() {
        return DateFormatUtils.format(getTodayEnd(), yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 获取今天的开始时刻
     *
     * @return
     */
    public static Date getTodayBegin() {
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        return calendar.getTime();
    }

    /**
     * 获取今天的结束时刻
     *
     * @return
     */
    public static Date getTodayEnd() {
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    public static Date addMonths(Date src, int addMonths) {
        calendar.setTime(src);
        calendar.add(Calendar.MONTH, addMonths);
        return calendar.getTime();

    }

    public static Date addMonths(int addMonths) {
        return addMonths(new Date(), addMonths);

    }

    public static Date addDays(Date src, int addDays) {
        calendar.setTime(src);
        calendar.add(Calendar.DATE, addDays);
        return calendar.getTime();

    }

    public static Date addDays(int addDays) {
        return addDays(new Date(), addDays);

    }

    public static Date addHours(Date src, int addHours) {
        calendar.setTime(src);
        calendar.add(Calendar.HOUR, addHours);
        return calendar.getTime();

    }

    public static Date addHours(int addHours) {
        return addHours(new Date(), addHours);
    }

    public static Date addMinutes(Date src, int addMinutes) {
        calendar.setTime(src);
        calendar.add(Calendar.MINUTE, addMinutes);
        return calendar.getTime();
    }

    public static Date addMinutes(int addMinutes) {
        return addMinutes(new Date(), addMinutes);
    }

    public static Date addSeconds(Date src, int addSeconds) {
        calendar.setTime(src);
        calendar.add(Calendar.SECOND, addSeconds);
        return calendar.getTime();

    }

    public static Date addSeconds(int addSeconds) {
        return addSeconds(new Date(), addSeconds);

    }

    /**
     * 返回指定日期是星期几(注:星期一为第1天,星期天为第7天)
     *
     * @param src
     * @return
     */
    public static int getDayOfWeek(Date src) {
        calendar.setTime(src);
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (index == 0) {
            index = 7;
        }
        return index;
    }

    /**
     * 返回今天是星期几（）
     *
     * @return
     */
    public static int getDayOfWeek() {
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 格式化日期输出
     * @param src
     * @param formatPattern
     * @return
     */
    public static String formatDate(Date src, String formatPattern) {
        if (src == null) {
            return "";
        }
        DateFormat fmt = new SimpleDateFormat(formatPattern);
        return fmt.format(src);
    }

    public static String formatDate(Date src) {
        return formatDate(src, yyyy_MM_dd_HH_mm_ss);
    }

    public static String formatDate() {
        return formatDate(new Date(), yyyy_MM_dd);
    }

    public static String formatDate(String formatPattern) {
        return formatDate(new Date(), formatPattern);
    }

    public static Date getDate(int year, int month, int day) {
        calendar.clear();
        calendar.set(year, month - 1, day);
        return calendar.getTime();

    }

    public static Date parseDate(String dateString, String formatPattern) throws Exception {
        return parseDate(dateString, formatPattern, null, false);
    }

    public static Date parseDate(String dateString) {
        try {
            return parseDate(dateString, yyyy_MM_dd_HH_mm_ss);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDate(String dateString, boolean throwExceptionWhenParseFail) throws Exception {
        return parseDate(dateString, yyyy_MM_dd_HH_mm_ss, null, throwExceptionWhenParseFail);
    }

    public static Date parseDate(String dateString, String formatPattern, Date defaultValue, boolean throwExceptionWhenParseFail) throws Exception {
        try {
            if (StringUtils.isEmpty(dateString) || StringUtils.isEmpty(formatPattern)) {
                return null;
            }
            DateFormatter dateFormatter = new DateFormatter();
            dateFormatter.setPattern(formatPattern);
            return dateFormatter.parse(dateString, Locale.CHINA);
        } catch (Exception e) {
            logger.error("DateUtil.parseDate出错:", e);
            logger.error("dateString:" + dateString + ",formatPattern:" + formatPattern);
            if (throwExceptionWhenParseFail) {
                throw e;
            }
        }
        return defaultValue;
    }

    /**
     * 得到指定日期是几号
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到今天是几号
     *
     * @return
     */
    public static int getDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到指定日期的月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }


    public static Date getMinDate(Date date1, Date date2) {
        return date1.getTime() < date2.getTime() ? date1 : date2;
    }

    public static long parseSecond(String date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = yyyy_MM_dd_HH_mm_ss;
        }
        Date date1 = null;
        try {
            date1 = parseDate(date, format);
        } catch (Exception e) {
            logger.error("DateUtil.parseDate出错:", e);
            logger.error("dateString:" + date + ",formatPattern:" + format);
            return getPhpTime();
        }
        if (date1 == null) {
            return getPhpTime();
        }
        return date1.getTime() / MILLISECONDS_SECOND;
    }

    public static Date getDate(String formatPattern) {

        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        try {
            return parseDate(format.format(new Date()), formatPattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(int min) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,min);
        return c.getTime();
    }
}
