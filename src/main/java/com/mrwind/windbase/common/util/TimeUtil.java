package com.mrwind.windbase.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/5/31
 */

public class TimeUtil {

    /**
     * 2017-04-23T14:36:53+0800
     */
    public static final SimpleDateFormat UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * 2017-07-27T11:26:19.805+08:00
     */
    public static final SimpleDateFormat UTC_1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * 星期四
     */
    public static final SimpleDateFormat EEEE = new SimpleDateFormat("EEEE");

    /**
     * 14:20
     */
    public static final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");

    /**
     * 2017-05-10 15:33:00
     */
    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 2017-05-10
     */
    public static final SimpleDateFormat yyyyMMdd1 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 2017-05-10 15:33
     */
    public static final SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 2017/05/10 15:33
     */
    public static final SimpleDateFormat yyyyMMddHHmm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 05-10-2017 15:33:00
     */
    public static final SimpleDateFormat MMddyyyyHHmmss = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    /**
     * 2017/07/28
     */
    public static final SimpleDateFormat yyyyMMdd2 = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 05-10 15:33
     */
    public static final SimpleDateFormat MMddHHmm = new SimpleDateFormat("MM-dd HH:mm");

    /**
     * 05-10
     */
    public static final SimpleDateFormat MMdd = new SimpleDateFormat("MM-dd");

    /**
     * 05-10 15:33:00
     */
    public static final SimpleDateFormat MMddHHmmss = new SimpleDateFormat("MM-dd HH:mm:ss");

    /**
     * 2017-07-15+08:00
     */
    public static final SimpleDateFormat yyyyMMddZ = new SimpleDateFormat("yyyy-MM-ddZ");

    /**
     * 2017-07
     */
    public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");

    public static String formatDate(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    /**
     * 将日期从一个格式转化为另一个格式q
     *
     * @param sourceStr
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String formatDate(String sourceStr, SimpleDateFormat sourceFormat, SimpleDateFormat targetFormat) {
        String result = "";
        try {
            Date date = sourceFormat.parse(sourceStr);
            result = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date formatToDate(String sourceStr, SimpleDateFormat sourceFormat) {
        Date date;
        try {
            date = sourceFormat.parse(sourceStr);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    public static String formatDateUTC(String sourceStr, SimpleDateFormat targetFormat) {
        return formatDate(sourceStr, UTC, targetFormat);
    }

    /**
     * 将utc时间转换为当前时区的毫秒值
     *
     * @param utcTime
     * @return
     */
    public static long formatUTCTimeToMills(String utcTime) {
        Date date = formatUTCTimeToDate(utcTime);
        if (date != null) {
            return date.getTime();
        }
        return 0;
    }

    public static Date formatUTCTimeToDate(String utcTime) {
        if (TextUtils.isEmpty(utcTime)) {
            return null;
        }
        try {
            String timeZone;
            if (!utcTime.endsWith("Z")) {
                //末尾不包含Z,则不需要减去8小时
                utcTime += "Z";
                timeZone = "+0800";
            } else {
                timeZone = "+0000";
            }
            Date date = (UTC).parse(utcTime.replaceAll("Z$", timeZone));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSameDay(Date d1, Date d2) {
        boolean isSameDay = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR) == 0) {
            isSameDay = true;
        }
        return isSameDay;
    }

    /**
     * 将年月日转换为当前时区的毫秒值
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static long convertToMills(int year, int month, int day) {
        String utcTime = convertToUtcTime(year, month, day);
        return convertToMills(utcTime);
    }


    /**
     * 年月日转utc时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String convertToUtcTime(int year, int month, int day) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);
            return convertToUtcTime(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Date时间转utc时间
     *
     * @param date
     * @return
     */
    public static String convertToUtcTime(Date date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将utc时间转换为当前时区的毫秒值
     *
     * @param utcTime
     * @return
     */
    public static long convertToMills(String utcTime) {
        if (TextUtils.isEmpty(utcTime)) {
            return 0;
        }

        try {

            String timeZone;
            if (!utcTime.endsWith("Z")) {
                //末尾不包含Z,则不需要减去8小时
                utcTime += "Z";
                timeZone = "+0800";
            } else {
                timeZone = "+0000";
            }

            Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(utcTime.replaceAll("Z$", timeZone));
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 格式化分钟为小时
     *
     * @param minute
     * @return
     */
    public static String formatMinuteToHourUnit(int minute) {
        if (minute <= 59) {
            return minute + "分钟";
        } else {
            return minute / 60 + "小时" + minute % 60 + "分钟";
        }
    }

    /**
     * 转换UTC时间 成类似微信聊天会话列表时间
     *
     * @param utc
     * @return
     */
    public static String formatUTCTimeToCommon(String utc) {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Date date = formatUTCTimeToDate(utc);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.DAY_OF_MONTH);
        int dstYear = calendar.get(Calendar.YEAR);
        int dstDay = calendar.get(Calendar.DAY_OF_YEAR);

        if (currentYear - dstYear != 0) {
            return formatDateUTC(utc, yyyyMMdd2);
        } else {
            switch (currentDay - dstDay) {
                case 0:
                    return formatDateUTC(utc, HHmm);
                case 1:
                    return "昨天";
                case 2:
                    return "前天";
                case 3:
                case 4:
                case 5:
                case 6:
                    //return getWeekName(calendar.get(Calendar.DAY_OF_WEEK));
                default:
                    return formatDateUTC(utc, yyyyMMdd2);

            }
        }
    }

    public static String getWeekName(int column) {
        switch (column) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

    /**
     * 生成一个当天时间为 00:00 的 Calendar 对象（秒和毫秒均为0）
     *
     * @return
     */
    public static Calendar getPureCalendar() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    /**
     * 判断一串字符是否为期望格式的日期
     *
     * @param sourceStr    字符串
     * @param expectFormat 期望格式
     * @return true or false
     */
    public static boolean isFormat(String sourceStr, SimpleDateFormat expectFormat) {
        boolean isFormat = true;
        try {
            expectFormat.parse(sourceStr);
        } catch (Exception e) {
            isFormat = false;
        }
        return isFormat;
    }

    /**
     * 比较两个日期大小
     *
     * @param d1     日期1
     * @param d2     日期2
     * @param format 格式，两个日期的格式必须要一直
     * @return d1 > d1 返回大于 0，= 返回 0，< 返回小于0
     */
    public static long compareDate(String d1, String d2, SimpleDateFormat format) {
        Calendar c1 = getPureCalendar();
        c1.setTime(formatToDate(d1, format));
        Calendar c2 = getPureCalendar();
        c2.setTime(formatToDate(d2, format));
        return c1.getTimeInMillis() - c2.getTimeInMillis();
    }

    /**
     * 获取指定格式数值之前的时间
     *
     * @param field  字段,eg Calendar.MINUTE,Calendar.HOUR..
     * @param amount 数值
     * @return Date
     */
    public static Date getBefore(int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 获取指定格式数值之前的时间
     *
     * @param field  字段,eg Calendar.MINUTE,Calendar.HOUR..
     * @param amount 数值
     * @return Date
     */
    public static Date getBefore(int field, int amount, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return c.getTime();
    }

}
