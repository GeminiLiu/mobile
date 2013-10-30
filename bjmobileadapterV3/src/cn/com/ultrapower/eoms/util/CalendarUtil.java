package cn.com.ultrapower.eoms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 本类用于获得时间相关的信息
 * 
 * @author lijupeng
 * 
 */
public class CalendarUtil {

	private static Logger log = Logger.getLogger(CalendarUtil.class);

	/**
	 * 用于获得默认格式的当前日期
	 * 
	 * @return String 当前日期
	 */
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd");
	}

	/**
	 * 用于获得默认格式的当前日期
	 * 
	 * @return String 当前日期
	 */
	public static String getCurrentDateTime() {
		return getCurrentDate("yyyy-MM-dd KK:mm:ss");
	}
	
	
	
	/**
	 * 用于获得默认格式的当前日期24小时制
	 * @author LiangYang
	 * @return String 当前日期
	 */
	public static String getCurrentDateTime24() {
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 用于获得指定格式的当前日期
	 * 
	 * @param format
	 *            日期格式
	 * @return String 当前日期
	 */
	public static String getCurrentDate(String format) {
		String currentDate = "";
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			SimpleDateFormat simpleDateFormat;
			Date date = calendar.getTime();
			simpleDateFormat = new SimpleDateFormat(format);
			currentDate = simpleDateFormat.format(date);

		} catch (Exception e) {
			currentDate = "";
		}
		return currentDate;

	}
	/**
	 * 用于获得指定格式的日期

	 * 
	 * @param format,date
	 *            日期格式
	 * @return String 日期
	 */
	public static String getFormatDate(String format,Date date) {
		String formatDate = "";
		try {			
			SimpleDateFormat simpleDateFormat;			
			simpleDateFormat = new SimpleDateFormat(format);
			formatDate = simpleDateFormat.format(date);

		} catch (Exception e) {
			formatDate = "";
		}
		return formatDate;

	}
	
	
	
	/**
	 * 用于返回当前日期的下一天的日期
	 * 
	 * @return String 当前日期的下一天的日期
	 */
	public static String getNextDay() {
		return getFutureDay(getCurrentDate(), 1);
	}

	/**
	 * 用于返回指定日期的下一天的日期
	 * 
	 * @param appDate
	 *            指定日期
	 * @return 指定日期的下一天的日期
	 */
	public static String getNextDay(String appDate) {
		return getFutureDay(appDate, "yyyy-MM-dd", 1);
	}

	/**
	 * 用于返回指定日期增加指定天数的日期
	 * 
	 * @param appDate
	 *            指定日期
	 * @param days
	 *            指定天数
	 * @return 指定日期增加指定天数的日期
	 * 
	 */
	public static String getFutureDay(String appDate, int days) {
		return getFutureDay(appDate, "yyyy-MM-dd", days);
	}

	/**
	 * 用于返回指定日期格式的日期增加指定天数的日期
	 * 
	 * @param appDate
	 *            指定日期
	 * @param format
	 *            指定日期格式
	 * @param days
	 *            指定天数
	 * @return 指定日期格式的日期增加指定天数的日期
	 */
	public static String getFutureDay(String appDate, String format, int days) {
		String future = "";
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date date = simpleDateFormat.parse(appDate);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			date = calendar.getTime();
			future = simpleDateFormat.format(date);
		} catch (Exception e) {

		}

		return future;
	}

	/**
	 * 用于获得当前时间的毫秒值
	 * 
	 * @return Long 当前时间的毫秒值
	 * 
	 */
	public static Long currentTimeMillis() {
		return new Long(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		System.out.println(getBetweenDays("2006-11-08", "2006-12-31"));
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param fistDate
	 * @param secondDate
	 * @return
	 */
	public static int getBetweenDays(String begin, String end) {
		if (begin == null || end == null) {
			return 0;
		}
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date sdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			long times = edate.getTime() - sdate.getTime();
			days = (int) (times / 86400000);// 24 * 60 * 60 * 1000 = 86400000
		} catch (ParseException pe) {
			log.warn("计算两个日期的时间发生异常，可能是日期的格式有错,请用yyyy-MM-dd格式");
			pe.printStackTrace();
		}
		return days;
	}

	/**
	 * 计算两个日期相差的秒数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getBetweenSeconds(String begin, String end) {
		if (begin == null || end == null) {
			return 0;
		}
		int seconds = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date sdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			long times = edate.getTime() - sdate.getTime();
			seconds = (int) (times / 1000);// 24 * 60 * 60 * 1000 = 86400000
		} catch (ParseException pe) {
			log.warn("计算两个日期的时间发生异常，可能是日期的格式有错,请用yyyy-MM-dd HH:mm:ss格式");
			pe.printStackTrace();
		}
		return seconds;
	}
	
	
	/**
	 * 计算两个日期相差的秒数24小时制
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getBetweenSeconds24(String begin, String end) {
		if (begin == null || end == null) {
			return 0;
		}
		int seconds = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date sdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			long times = edate.getTime() - sdate.getTime();
			seconds = (int) (times / 1000);// 24 * 60 * 60 * 1000 = 86400000
		} catch (ParseException pe) {
			log.warn("计算两个日期的时间发生异常，可能是日期的格式有错,请用yyyy-MM-dd HH:mm:ss格式");
			pe.printStackTrace();
		}
		return seconds;
	}

	/**
	 * 计算两个日期相差的秒数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getBetweenMilliSeconds(String begin, String end) {
		if (begin == null || end == null) {
			return 0;
		}
		int milliSeconds = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date sdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			long times = edate.getTime() - sdate.getTime();
			milliSeconds = (int) times;// 24 * 60 * 60 * 1000 = 86400000
		} catch (ParseException pe) {
			log.warn("计算两个日期的时间发生异常，可能是日期的格式有错,请用yyyy-MM-dd HH:mm:ss格式");
			pe.printStackTrace();
		}
		return milliSeconds;
	}
	
	/**
	 * 返回指定月的第一天是星期几,星期日到星期六为1-7,week of first day in month
	 * 
	 * @param calendar
	 * @return
	 */
	public static int getWFDM(Calendar calendar) {
		if (calendar == null) {
			return 0;
		}
		Calendar cal = (Calendar) calendar.clone();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 比较realTime是否比baseTime晚,如果是则返回true,如果否则返回false
	 * @param baseTime 基准时间
	 * @param realTime 比较时间
	 * @return
	 */
	public static boolean after(String baseTime,String realTime,String format){
		boolean after = false;
		try {
			Calendar base = GregorianCalendar.getInstance();
			base.setTime(toDate(baseTime,format));
			
			Calendar real = GregorianCalendar.getInstance();
			real.setTime(toDate(realTime,format));
			if(real.after(base)){
				after = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return after;
	}
	/**
	 * 比较realTime是否比baseTime晚,如果是则返回true,如果否则返回false
	 * @param baseTime 基准时间
	 * @param realTime 比较时间
	 * @return
	 */
	public static boolean after(Calendar baseTime,Calendar realTime){
		boolean after = false;
		try {
			if(realTime.after(baseTime)){
				after = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return after;
	}
	/**
	 * 比较realTime是否比baseTime晚,如果是则返回true,如果否则返回false
	 * @param baseTime 基准时间
	 * @param realTime 比较时间
	 * @return
	 */
	public static boolean after(Date baseTime,Date realTime){
		boolean after = false;
		try {
			if(realTime.after(baseTime)){
				after = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return after;
	}
	/**
	 * 是否是有效的时间
	 * @param time
	 * @param format
	 * @return
	 */
	public static boolean isValidTime(String time, String format){
		boolean valid = false;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			simpleDateFormat.parse(time);
			valid = true;
		} catch (Exception e) {
			
		}
		return valid;
	}
	/**
	 * 把字符串转换为日期
	 * @param value
	 * @param format
	 * @return
	 */
	public static Date toDate(String value, String format){
		Date date = new Date();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(value);
		} catch (Exception e) {
			
		}
		return date;
	}
	/**
	 * 把日期转换为字符串
	 * @param value
	 * @param format
	 * @return
	 */
	public static String toString(Date value, String format){
		String date = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.format(value);
		} catch (Exception e) {
			
		}
		return date;
	}
	
	public static Calendar toCalendar(String value, String format){
		Calendar calendar = GregorianCalendar.getInstance();
		try {
			Date date1 = toDate(value, format);
			//int a = date1.getHours();
			
			calendar.setTime(date1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return calendar;
	}
	
	/**
	 * 以指定的格式去格式化日期
	 * 
	 * @param value
	 * @param format
	 * @return
	 */
	public static String format(String value, String format) {
		if (value == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}
}
