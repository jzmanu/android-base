package com.manu.baselibrary.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by jzman
 * Powered by 2018/5/22 0022 14:51
 */
public class DateUtil {
    //默认格式化日期时间模板
    public static final String DATETIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
    public static final String TIME_FORMAT_DEFAULT = "HH:mm:ss";
    public static final String TIME_FORMAT_ONLY_HOUR_AND_MINUTE = "HH:mm";

    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String getFormatDateTime(Date date) {
        Calendar currentCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT_NO_SECOND);
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(TIME_FORMAT_ONLY_HOUR_AND_MINUTE);
        if (dateCal.after(currentCal) || currentCal.get(Calendar.YEAR) > dateCal.get(Calendar.YEAR) || currentCal.get(Calendar.DAY_OF_YEAR) - 2 > dateCal.get(Calendar.DAY_OF_YEAR)) {
            return simpleDateFormat.format(date);
        } else if (currentCal.get(Calendar.DAY_OF_YEAR) - 2 == dateCal.get(Calendar.DAY_OF_YEAR)) {
            return "前天 " + simpleTimeFormat.format(date);
        } else if (currentCal.get(Calendar.DAY_OF_YEAR) - 1 == dateCal.get(Calendar.DAY_OF_YEAR)) {
            return "昨天 " + simpleTimeFormat.format(date);
        } else if (currentCal.getTimeInMillis() - dateCal.getTimeInMillis() > 1000 * 60 * 60 ) {
            return (currentCal.getTimeInMillis() - dateCal.getTimeInMillis()) / (1000 * 60 * 60 ) + "小时前";
        } else {
            long min = (currentCal.getTimeInMillis() - dateCal.getTimeInMillis()) / (1000 * 60 );
            if (min == 0) {
                return "刚刚";
            } else {
                return (currentCal.getTimeInMillis() - dateCal.getTimeInMillis()) / (1000 * 60 ) + "分钟前";
            }
        }
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }
}
