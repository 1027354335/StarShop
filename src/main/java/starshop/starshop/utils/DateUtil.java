package starshop.starshop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DateUtil {
    public final static String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public final static String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_SECOND_SINGLE = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FULL_STRING = "yyyyMMddHHmmssSSS";
    public final static String DATE_FULL_14 = "yyyyMMddHHmmss";
    public final static String DATE_FULL_DAY_8 = "yyyyMMdd";
    private final static int[] DATE_UNIT_ARR = new int[]{Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR_OF_DAY,
            Calendar.DATE, Calendar.MONTH, Calendar.YEAR};
    public static Calendar calendar = null;
    public static DateFormat dateFormat = null;
    public static Date date = null;

    /**
     * 将日期转为 字符串
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将日期转换为 字符串(转换的时间按照当前登录用户的时区)
     * @param date
     * @param format
     * @param timeZone
     * @return
     */
    public static String dateToString(Date date, String format, String timeZone) {
        if (date == null) {
            return null;
        }
        //1、格式化日期
        return getTimeZoneSimpleDateFormat(format, timeZone).format(date);
    }

    /**
     * 获取当前登录用户的 日期格式化对象
     * @param timeZone
     * @param format
     * @return
     */
    private static SimpleDateFormat getTimeZoneSimpleDateFormat(String format, String timeZone) {
        //1、获取对应时区的格式化器
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat;
    }


    /**
     * 获取当天初始时间
     * @param date 时间
     * @return 初始时间 (yyyy-MM-dd 00:00:00)
     */
    public static Date getInitialTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String dateStr = dateFormat.format(date);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2Str(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 计算两个时间间隔多少秒
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long intervalTime(Date startDate, Date endDate) {
        long a = endDate.getTime();
        long b = startDate.getTime();
        Long c = ((a - b) / 1000);
        return c;
    }

    /**
     * 检测一个日期是否在 小时之内,支持跨天的小时
     * @param time
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean checkDateIn(Date time, Date startDate, Date endDate) {
        if (startDate == null || endDate == null || time == null) {
            return true;
        }
        return time.before(endDate) && time.after(startDate);
    }
    /**
     * 检测一个日期是否在 时分秒 之内,支持跨天的小时
     *
     * @param time
     * @param startHms
     * @param endHms
     * @return
     */

    /**
     * 功能描述：格式化日期
     * @param dateStr 字符型日期：YYYY/MM/DD 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 功能描述：格式化日期
     * @param dateStr 字符型日期
     * @param format  格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static Date strParseDateDay(String date) throws ParseException {
        //获取的值为"19570323"
        //1、定义转换格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        //2、调用formatter2.parse(),将"19570323"转化为date类型 输出为：Sat Mar 23 00:00:00 GMT+08:00 1957
        Date parseDate = formatter.parse(date);
        return parseDate;
    }

    public static Date strParseDate(String date, String dateFormat) {
        Date parseDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            parseDate = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseDate;
    }

    /**
     * 日期型字符串转换成标准日期格式字符串
     * @param date
     * @param srcDateFormat
     * @param destDateFormat
     * @return
     */
    public static String strParseToDateStr(String date, String srcDateFormat, String destDateFormat) {
        String resultStr = null;
        Date parseDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(srcDateFormat);
        try {
            parseDate = formatter.parse(date);
            resultStr = DateUtil.dateToString(parseDate, destDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static String yyyyMMddHHmmssToStandard(String dateStr) {
        Date parseDate = null;
        String standStrForDate = null;

        try {
            parseDate = DateUtil.strParseDate(dateStr, DateUtil.DATE_FULL_14);
            standStrForDate = DateUtil.dateToString(parseDate, DATE_FORMAT_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return standStrForDate;
    }

    public static String yyyyMMddToStandard(String dateStr) {
        Date parseDate = null;
        String standStrForDate = null;

        try {
            parseDate = DateUtil.strParseDate(dateStr, DateUtil.DATE_FULL_DAY_8);
            standStrForDate = DateUtil.dateToString(parseDate, DATE_FORMAT_SECOND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return standStrForDate;
    }

    public static List<String> getRecent7Days() {
        //获取近7天的日期 如 2021-05-16
        List<String> sevenDays = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = null;
        for (int i = 0; i < 7; i++) {
            c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i);
            sevenDays.add(simpleDateFormat.format(c.getTime()));
        }
        Collections.reverse(sevenDays);
        return sevenDays;
    }

    public static int daysBetween(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate d1 = LocalDate.parse(date1, formatter);
        LocalDate d2 = LocalDate.parse(date2, formatter);
        return Math.abs((int) (d2.toEpochDay() - d1.toEpochDay()));
    }
}
