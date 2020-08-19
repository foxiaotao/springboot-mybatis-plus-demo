package com.sdgtt.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by conglin.liu on 2017/8/3.
 */
@Slf4j
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public final static String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMdd_HHmm = "yyyyMMdd_HHmm";
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String YYYYMMDD_HHMMSS = "yyyyMMdd HHmmss";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public final static DateTimeFormatter dateTimeFormatter_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter dateTimeFormatter_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public final static String date_suffix = "235959";
    public final static long THOUSAND = 1000L;
    /**
     * 某天零时零分零秒到23：59：59 的秒数
     */
    public static final long SECOND_OF_DAY = 86399L;


    /**
     * 获得时间戳（10位，精确到秒）
     * @return
     */
    public static String getTenTimestamp() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime() / 1000);
    }


    /**
     * 字符串(秒) 转为 Timestamp(10位)
     * @param tsStr  2017-06-06 12:00:00  -> 1496721600
     * @return
     */
    public static Timestamp stringToTimestamp(String tsStr) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(tsStr);
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
        }
        return ts;
    }

    /**
     * 字符串(秒) 转为 Timestamp(10位)
     * @param tsStr  2017-06-06 12:00:00  -> 1496721600
     * @return
     */
    public static Date stringToDate(String tsStr) {
        Date date = new Date();
        try {
            date = new Date(Long.valueOf(tsStr));
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
        }
        return date;
    }

    /**
     * 数值 毫秒值 转时间
     * @param times 时间的毫秒值
     * @return
     */
    public static Date getByLongTimes(Long times) {
        Date date = null;
        try {
            date = new Date(times);
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
        }
        return date;
    }

    /**
     * 数值 秒值 转时间
     * @param times 时间的毫秒值
     * @return
     */
    public static Date getBy10LongTimes(Long times) {
        return new Date(times * THOUSAND);
    }
    /**
     * 数值 秒值 转时间
     * @param times 时间的毫秒值
     * @return
     */
    public static Date getBy10LongTimes(String times) {
        return new Date(Long.valueOf(times) * THOUSAND);
    }

    /**
     * 字符毫秒值 转时间
     * @param times 时间的毫秒值
     * @return
     */
    public static Date getByLongTimes(String times) {
        Date date = null;
        try {
            if (ValidationUtil.isInteger(times)) {
                date = new Date(Long.valueOf(times));
            }
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
        }
        return date;
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        try {
            return date == null ? "" : new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            log.error("[exception]date transfer error,date={},pattern={}",date,pattern);
            return "";
        }
    }

    /**
     *  Date 按格式化后转为Long13
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Long formatToLong(Date date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            SimpleDateFormat format =  new SimpleDateFormat(pattern);
            String dated = format.format(date);
            //转换为Date类
            return  format.parse(dated).getTime();
        } catch (Exception e) {
            log.error("[exception]date transfer error,date={},pattern={}",date,pattern);
            return null;
        }
    }

    /**
     * 获得当天最晚时间戳
     *
     * @param time
     * @return
     */
    public static Long getEndOfDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    /**
     *  获得当天最开始时间戳
     *
     * @param time
     * @return
     */
    public static Long getStartOfDay(Long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    /**
     * 将yyyy-MM-dd 转成 yyyyMMdd+时分秒
     * @param deadline
     * @return yyyyMMddHHmmss
     */
    public static String getDeadline(String deadline) {
        try {
            if (StringUtils.isNotBlank(deadline)) {
                String date = deadline.replaceAll("-", "").replaceAll("/", "").substring(0, 8);
                return date + date_suffix;
            } else {
                return LocalDateTime.now().format(dateTimeFormatter_yyyyMMdd) + date_suffix;
            }
        } catch (Exception e) {
            return LocalDateTime.now().format(dateTimeFormatter_yyyyMMdd) + date_suffix;
        }
    }

    /**
     * 将yyyy-MM-dd 转成 yyyyMMdd+时分秒
     * @param deadline
     * @return yyyyMMddHHmmss
     */
    public static String getDeadline(Date deadline) {
        try {
            if (deadline != null) {
                String date = format(deadline, YYYYMMDD);
                return date + date_suffix;
            } else {
                return LocalDateTime.now().format(dateTimeFormatter_yyyyMMdd) + date_suffix;
            }
        } catch (Exception e) {
            return LocalDateTime.now().format(dateTimeFormatter_yyyyMMdd) + date_suffix;
        }
    }

    /**
     * 时间字符串 转换成时间
     * @param dateStr 时间字符串
     * @param parsePatterns 时间字符串 格式
     * @return
     */
    public static Date dateStr2Date(String dateStr, String parsePatterns) {
        try {
            return DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            logger.error("时间转换出错,deadline={},parsePatterns={}", dateStr, parsePatterns, e);
            return new Date();
        }
    }

    /**
     * 获取当前时间
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 把时间字符串，源格式转换为 目标格式输出
     * @param dateStr 时间字符串
     * @param dateFormatSource : （source）格式
     * @param dateFormatTarget （target）格式
     * @return
     */
    public static String parse(String dateStr, String dateFormatSource, String dateFormatTarget) {
        String dateFormat;
        try {
            dateFormat = format(dateStr2Date(dateStr, dateFormatSource), dateFormatTarget);
            if (StringUtils.isEmpty(dateFormat)) {
                logger.error("时间为空,dateStr={},parsePatterns={}", dateStr, dateFormatSource + "2" + dateFormatTarget);
                dateFormat = format(new Date(), dateFormatTarget);
            }
        } catch (Exception e) {
            logger.error("时间转换出错,dateStr={},parsePatterns={}", dateStr, dateFormatSource + "2" + dateFormatTarget, e);
            dateFormat = format(new Date(), dateFormatTarget);
        }
        return dateFormat;
    }

    public static Date monthLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        return new Date(calendar.getTime().getTime());
    }

    public static Date dayLater() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 根据时间 获取10位 时间戳
     * @param updated
     * @return
     */
    public static Long get10LongTimeFromDate(Date updated) {
        if (updated == null) {
            return System.currentTimeMillis() / 1000;
        } else {
            return updated.getTime() / 1000;
        }
    }

    /**
     * 根据10位时间戳 获取13位时间戳
     * @param dateTime
     * @return
     */
    public static Long get13LongTimeFormLong(Long dateTime) {
        if (dateTime == null) {
            return System.currentTimeMillis();
        }
        return dateTime * 1000;
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return true 在区间，false:不在区间
     * @author suntao
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param fromDt  指定时间
     * @param termUnit 时间类别  天   月    年
     * @param count 时间
     * @param beforeOrAfter  beforeOrAfter  true 往后  false 往前
     * @return
     */
    public static Date getNextDate(Date fromDt,int termUnit,int count,Boolean beforeOrAfter) {
        try {
            if (Objects.isNull(fromDt)) {
                fromDt = new Date();
            }

            LocalDate localDate = fromDt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!beforeOrAfter) {
                count = -count;
            }

            if (termUnit == Calendar.YEAR) {

                return Date.from(localDate.plusYears(count).atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else if (termUnit == Calendar.MONTH) {
                return Date.from(localDate.plusMonths(count).atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else {
                return Date.from(localDate.plusDays(count).atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    /**
     * 格式化时间转为时间戳
     *
     * @param strTime 格式化时间
     * @param pattern 自定义格式化类型
     * @return 13位时间戳
     */
    public static Long timeOfStrToLong(String strTime, String pattern){
        Long retTime = null;
        try {
            DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime parse = LocalDateTime.parse(strTime, ftf);
            retTime = LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (Exception e) {
            log.error("e={}", ExceptionUtils.getStackTrace(e));
        }
        return retTime;
    }

    /**
     * 格式化时间转为时间戳
     * 格式为：yyyyMMddHHmmss
     *
     * @param strTime 格式化时间
     * @return 13位时间戳
     */
    public static Long timeOfStrToLong(String strTime){
        return timeOfStrToLong(strTime, YYYYMMDDHHMMSS);

    }

    /**
     * 获取当前时间字符串
     *
     * @param format
     * @return
     */
    public static String getCurrentTimeString(String format) {
        // 如果为空，取默认值，如下
        if (StringUtils.isEmpty(format)) {
            format = YYYYMMDDHHMMSS;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }


    public static void main(String[] args) {
        Long dateLong = 1576944000000L;
        System.out.println(DateUtil.getEndOfDay(dateLong));
        System.out.println(DateUtil.getStartOfDay(1576548826000L));

    }

}
