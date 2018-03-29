package com.hnzy.per.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用日期获取
 * 
 * @author XuXueYang
 * 
 * @time 2016-10-15
 */
public class DateUtil {

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String HHMMSS = "HH:mm:ss";

	public static final String HHMM = "HH:mm";

	public static final char Y = 'Y';
	public static final char M = 'M';
	public static final char D = 'D';
	public static final char H = 'H';
	public static final char F = 'F';
	public static final char S = 'S';

	/**
	 * 取得指定年份，月份的最后一天,最容易理解 其实就是判断闰年问题:能被4整除，但不能被100整除，除非能被400整除
	 * */
	public static int getDaysOfMonth(int year, int month) {
		int days[] = { 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (2 == month && 0 == (year % 4)
				&& (0 != (year % 100) || 0 == (year % 400))) {
			// 判断闰年，当 month=2 时才判断，以减少计算量
			days[2] = 29;
		}
		return days[month];
	}

	/**
	 * 取得指定年份，月份的最后一天 用JAVA的类来实现，方法是取得下个月第一天，然后再取前一天，
	 */
	public static int getDaysInMonth(int year, int mon) {
		java.util.GregorianCalendar d1 = new java.util.GregorianCalendar(year,
				mon - 1, 1);
		java.util.GregorianCalendar d2 = (java.util.GregorianCalendar) d1
				.clone();
		d2.add(Calendar.MONTH, 1);
		return (int) ((d2.getTimeInMillis() - d1.getTimeInMillis()) / 3600 / 1000 / 24);
	}

	/**
	 * 获取当前时间
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getCurrentDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return format(currDate, strFormat);
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String str, String strFormat) { 
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前年,月,日,时,分,秒
	 * 
	 * @param strFormat
	 * @return
	 */
	public static Integer getDateStr(char str) {
		Calendar cal = Calendar.getInstance();
		Integer YMD = null;
		switch (str) {
		case Y:
			YMD = cal.get(Calendar.YEAR);// 获取年份
			break;
		case M:
			YMD = cal.get(Calendar.MONTH) + 1;// 获取月份
			break;
		case D:
			YMD = cal.get(Calendar.DAY_OF_MONTH);// 获取日
			break;
		case H:
			YMD = cal.get(Calendar.HOUR_OF_DAY);// 小时
			break;
		case F:
			YMD = cal.get(Calendar.MINUTE);// 分
			break;
		case S:
			YMD = cal.get(Calendar.SECOND);// 秒
			break;
		}
		return YMD;
	}

	/**
	 * 获取某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 设置日历中月份的第1天
		cal.set(Calendar.DATE, 1);
		Date currDate = cal.getTime();

		return format(currDate, DateUtil.YYYY_MM_DD);
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		Date currDate = cal.getTime();
		return format(currDate, DateUtil.YYYY_MM_DD);
	}

	/**
	 * 获取当前时间的前一天
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getYesteDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1); // 得到前一天
		Date currDate = cal.getTime();
		return format(currDate, strFormat);
	}

	/**
	 * 获取当前时间的前一个月
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getLastMonthDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1); // 得到前一个月
		Date currDate = cal.getTime();
		return format(currDate, strFormat);
	}

	public static String format(Date currDate, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		String time = format.format(currDate.getTime());
		return time;
	}
	public static String get2WeekAgoDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -14);
		Date currDate = cal.getTime();
		return format(currDate, strFormat);
	}

	
	
	public static void main(String[] args) {
		System.out.println(getFirstDayOfMonth(2015, 5));
		System.out.println(getLastDayOfMonth(2015, 5)); 
		// System.out.println(getDaysOfMonth(2017, 2));
		// System.out.println(getDateStr(DateUtil.Y)-1);
		// System.out.println(getDateStr(DateUtil.M));
		// System.out.println(getDateStr(DateUtil.D));
		String now=new StringBuffer().append(2015).append("-12-31").toString();
		System.out.println(now);
	}
}
