package com.jpa.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtils {

	public static final long A_DAY = 86400000L;
	
	// TODO
	public static void main(String[] args){
		//long a = getDayPlus("2016-01-01", "2016-03-01");
		System.out.println(get(null,null));
	}

	/**
	 * 周几 [0-6]
	 * @return
	 */
	public static int getTodayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取当前的小时[0-23]
	 * @return
	 */
	public static int getHour(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取几号[1-31]
	 * @return
	 */
	public static int getDay(){
		return Calendar.getInstance().get(Calendar.DATE);
	}
	
	/**
	 * 获取月份[1-12]
	 * @return
	 */
	public static int getMonth(){
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取年份
	 * @return 2016
	 */
	public static int getYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * 格式时间
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String get(String pattern, Date date){
		if(null == pattern){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		if(null == date){
			date = new Date();
		}

		try {
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
	}
	
	/**
	 * 获取上个月的今天的日期
	 * @return yyyy-MM-dd
	 */
	public static Date getDayOfLastMonth(){
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取一个月中最大天数
	 * @param month
	 * @return 30 or 31
	 */
	public static int getMaxDayOfMonth(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 计算日期时间差，返回计算后的日期 
	 * @param day
	 * @return
	 */
	public static Date getDayPlus(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
	
	/**
	 * 计算两个时间的差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long getDayPlus(String begin, String end){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(begin);
			Date d2 = df.parse(end);
		    long diff = d2.getTime() - d1.getTime();
		    return diff / A_DAY;
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 获取现在的日期
	 * @return yyyy-MM-dd
	 */
	public static String getDate() {
		return DateUtils.get("yyyy-MM-dd", null);
	}

	/**
	 * 获取现在的日期字符串
	 * @return yyyyMMdd
	 */
	public static String getDateString() {
		return DateUtils.get("yyyyMMdd", null);
	}

	/**
	 * 获取现在的时间
	 * @return HH:mm:ss
	 */
	public static String getTime() {
		return DateUtils.get("HH:mm:ss", null);
	}
	
	/**
	 * 获取现在的时间字符串
	 * @return HHmmss
	 */
	public static String getTimeString() {
		return DateUtils.get("HHmmss", null);
	}

	/**
	 * 获取现在的日期时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return DateUtils.get("yyyy-MM-dd HH:mm:ss", null);
	}
	
	/**
	 * 获取现在的日期时间字符串
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateTimeString() {
		return DateUtils.get("yyyyMMddHHmmss", null);
	}
	
	/**
	 * 获取现在的日期时间字符串
	 * @return yyyy_MM_dd_HH_mm_ss
	 */
	public static String getDateTimeByUnderline () {
		return DateUtils.get("yyyy_MM_dd_HH_mm_ss", null);
	}
	
	/**
	 * 获取现在的日期时间字符串
	 * @return yyyy年MM月dd日 HH时mm分ss秒
	 */
	public static String getDateTimeByLocaleChina() {
		return DateUtils.get("yyyy\u5E74MM\u6708dd\u65E5 HH\u65F6mm\u5206ss\u79D2", null);
	}
	
	/**
	 * 获取现在的时间戳
	 * @return
	 */
	public static long getTimes() {
		return (new Date()).getTime();
	}
		
	/**
	 * 时间戳转换成字符串时间
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String milliseconds2DateString(long milliseconds){
		return DateUtils.get("yyyy-MM-dd HH:mm:ss", new Date(milliseconds));
	}
	
	/**
	 * 字符串转换成日期
	 * @param datetime
	 * @return
	 */
	public static Date str2Date(String datetime){
		if(null == datetime){
			return new Date();
		}
		
		try {
			String pattern = null;
			if(Pattern.compile("^(\\d{4}\\-\\d{2}\\-\\d{2})$").matcher(datetime).matches()){
				pattern = "yyyy-MM-dd";
			} else if(Pattern.compile("^(\\d{2}:\\d{2}:\\d{2})$").matcher(datetime).matches()){
				pattern = "yyyy-MM-ddHH:mm:ss";
				datetime = getDate() + datetime;
			} else {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	/**
	 * 获取当前时间组成的路径
	 * @return yyyy/MM/dd/ or yyyy\\MM\\dd\\
	 */
	public static String getPathOfDate() {
		return DateUtils.get("yyyy" + File.separator + "MM" + File.separator + "dd" + File.separator, null);
	}

	/**
	 * 获取昨天的日期
	 * @return yyyy-MM-dd
	 */
	public static Date getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取给定的日期计算出星期
	 * @param day
	 * @return
	 */
	public static String getDayOfWeek(int day){
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, day);
		return DateUtils.get("yyyy-MM-dd", currentDate.getTime());
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		int dayOfWeek = getTodayOfWeek();
		return dayOfWeek == 1 ? 0 : 1 - dayOfWeek;
	}
	
	/**
	 * 获取本周一的日期
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfWeek(){
		return getDayOfWeek(getMondayPlus());
	}

	/**
	 * 获取上周一的日期
	 * @return yyyy-MM-dd
	 */
	public static String getLastMondayOfWeek(){
		return getDayOfWeek(getMondayPlus() - 7);
	}
	
	/**
	 * 获取本周天的日期
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfWeek(){
		return getDayOfWeek(getMondayPlus() + 6);
	}

	/**
	 * 获取上周天的日期
	 * @return yyyy-MM-dd
	 */
	public static String getLastSundayOfWeek(){
		return getDayOfWeek(getMondayPlus() - 1);
	}
	
}
