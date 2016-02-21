package com.iteedu.stock.spider.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtils
 *
 */
public class DateUtils {

    private static final String FORMAT_DATE_EN = "yyyy-MM-dd";
    private static final String FORMAT_DATE_EN2 = "yyyyMMdd";
    
    /**
     * 格式化日期 (yyyy-MM-dd)
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static String formatDateEN(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EN);
        return dateFormat.format(date);
    }
    /**
     * 格式化日期 (yyyyMMdd)
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static String formatDateEN2(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EN2);
        return dateFormat.format(date);
    }
    /**
     * 解析日期 (yyyy-MM-dd)
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static Date parseDateEN(String date) {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EN);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    /**
     * 解析日期 (yyyy-MM-dd)
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static Date parseDateEN2(String date) {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_EN2);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
