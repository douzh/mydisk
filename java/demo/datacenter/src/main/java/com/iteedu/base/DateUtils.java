package com.iteedu.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author Administrator
 *
 */
public class DateUtils {
	
	private static final String FORMAT_YMD="yyyyMMdd";
	
	public static String formatYmd(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YMD);
		return sdf.format(d);
	}
	
	public static Date parseYmd(String d){
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_YMD);
		try {
			return sdf.parse(d);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getYesterdayYmd(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return formatYmd(cal.getTime());
	}
	
	public static String getTodayYmd(){
		return formatYmd(new Date());
	}
	
	public static long getLongTime(String d){
		return parseYmd(d).getTime();
	}
}
