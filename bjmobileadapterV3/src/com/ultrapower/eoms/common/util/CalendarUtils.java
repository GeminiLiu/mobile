package com.ultrapower.eoms.common.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;

public class CalendarUtils {
	/**
	 * Calendar类转换为字符串
	 * @param calDate
	 * @return 字符
	 */
	@SuppressWarnings("static-access")
	public static String Cal2String(GregorianCalendar calDate) {
		String strDate = "";
		strDate = String.valueOf(calDate.get(calDate.YEAR));
		strDate = strDate + "-"
				+ String.valueOf(calDate.get(calDate.MONTH) + 1);
		strDate = strDate + "-" + String.valueOf(calDate.get(calDate.DATE));
		strDate = strDate + " "
				+ String.valueOf(calDate.get(calDate.HOUR_OF_DAY));
		strDate = strDate + ":" + String.valueOf(calDate.get(calDate.MINUTE));
		strDate = strDate + ":" + String.valueOf(calDate.get(calDate.SECOND));
		return strDate;
	}


	public static String getYYMMDD() {
		return getYYMMDD(getCurrentDateTime());
	}

	
	public static String getYYMMDD(String DateStr) {
		String YY, MM, DD;
		String ReturnDateStr;

		int s = DateStr.indexOf(" ");
		ReturnDateStr = DateStr.substring(0, s);

		String[] ss = ReturnDateStr.split("-"); 
		YY = ss[0].toString();
		YY = YY.substring(2, 4);
		MM = ss[1].toString();
		if (Integer.valueOf(MM).intValue() < 10) {
			MM = "0" + Integer.valueOf(MM).intValue();
		} 
		
		DD = ss[2].toString();
		if (Integer.valueOf(DD).intValue() < 10) {
			DD = "0" + Integer.valueOf(DD).intValue();
		} 		
		ReturnDateStr = YY + MM + DD;
		return ReturnDateStr;
	}

	/**
	 * 获得当前给定日期格式的字符串
	 * @param _dtFormat 日期格式
	 * @return 给定日期格式的字符串
	 */
	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentdatetime;
	}
	
	/**
	 * 获得给定日期的并且是给定日期格式的字符串
	 * @param strDate
	 * @param _dtFormat
	 * @return
	 */
	public static String getCurrentDateTime(String strDate, String _dtFormat) {
		String strDateTime;
		Date tDate = null;
		if (null == strDate) {
			return getCurrentDateTime();
		}
		SimpleDateFormat smpDateFormat = new SimpleDateFormat(_dtFormat);
		ParsePosition pos = new ParsePosition(0);
		tDate = smpDateFormat.parse(strDate, pos);
		strDateTime = smpDateFormat.format(tDate);
		return strDateTime;
	}
	
	public static String getDateTime(Date date, String _dtFormat) {
		String strDateTime;
		if (null == date) {
			return null;
		}
		SimpleDateFormat smpDateFormat = new SimpleDateFormat(_dtFormat);
		strDateTime = smpDateFormat.format(date);
		return strDateTime;
	}

	/**
	 * 获得当前日期时间格式的字符串
	 * @param 
	 * @return 当前日期时间格式的字符串
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	}
	public static String getCurrentDate() {
		return getCurrentDateTime("yyyy-MM-dd");
	}
	
	
	/**
	 * 比较时间
	 * @param startTime
	 * @param endTime
	 * @return 返回true and false
	 */
	public static boolean  timeCompare(String startTime,String endTime){
		boolean t = false;
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		try { 
		Date start = smdf.parse(startTime); 
		Date end   = smdf.parse(endTime); 
		long time  = (end.getTime() - start.getTime()); 
		if(time>0)
			t = true;
		} catch (Exception e) { 
		e.printStackTrace(); 
		}
		return t;
	}
	
	/**
	 * 比较两个日期大小，指定日期格式
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static boolean  timeCompare(String startTime,String endTime,String format){
		boolean t = false;
		SimpleDateFormat smdf = new SimpleDateFormat(format); 
		try { 
		Date start = smdf.parse(startTime); 
		Date end = smdf.parse(endTime); 
		long time = (end.getTime() - start.getTime()); 
		if(time>0)
			t = true;
		} catch (Exception e) { 
		e.printStackTrace(); 
		}
		return t;
	}
	
	/**
	 * 比较日期相差的天数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long daysBetween(String startTime,String endTime){
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		long distanceDay = 0;
		try {
			Date startDate = smdf.parse(startTime);
			Date endDate = smdf.parse(endTime);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			distanceDay = endCalendar.get(Calendar.DAY_OF_YEAR)
					- startCalendar.get(Calendar.DAY_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
        return distanceDay;
	}
	
	/**
	 * 比较相差的小时
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long timeBetween(String startTime,String endTime){
		SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		long distanceTime = 0;
		try {
			Date startDate = smdf.parse(startTime);
			Date endDate = smdf.parse(endTime);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			distanceTime = endCalendar.get(Calendar.HOUR)
					- startCalendar.get(Calendar.HOUR);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
        return distanceTime;
	}

	/** 
	* 获取指定日期的年份 
	* @param p_date util.Date日期 
	* @return int   年份 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getYearOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.YEAR );
	}
	  
	/** 
	* 获取指定日期的月份 
	* @param p_date util.Date日期 
	* @return int   月份 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getMonthOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.MONTH ) + 1;
	}
	
	
	/** 
	* 获取指定日期的月份 
	* @param 日期时间的秒
	* @return int   月份 
	* @author 梁阳
	* @Date:   2008-9-9
	*/
	public static int getMonthOfDate(Long dateTime) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTimeInMillis(dateTime*1000);
	   return c.get( java.util.Calendar.MONTH ) + 1;
	}

	/** 
	* 获取指定日期的日份 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getDayOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.DAY_OF_MONTH );
	}
	
	/** 
	* 获取指定日期的日份 
	* @param 日期时间的秒 
	* @return int   日份 
	* @author 梁阳
	* @Date:   2008-9-9
	*/
	public static int getDayOfDate(Long dateTime) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTimeInMillis(dateTime*1000);
	   return c.get( java.util.Calendar.DAY_OF_MONTH );
	}

	/** 
	* 获取指定日期的小时 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getHourOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.HOUR_OF_DAY );
	}
	  
	/** 
	* 获取指定日期的分钟 
	* @param p_date util.Date日期 
	* @return int   分钟 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getMinuteOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.MINUTE );
	}
	  
	/** 
	* 获取指定日期的秒钟 
	* @param p_date util.Date日期 
	* @return int   秒钟 
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static int getSecondOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.SECOND );
	}
	  
	/** 
	* 获取指定日期的毫秒   
	* @param p_date util.Date日期 
	* @return long   毫秒   
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static long getMillisOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.getTimeInMillis();
	}
	/** 
	* 获取指定日期的毫秒   
	* @param p_date util.Date日期 
	* @return long   毫秒   
	* @author wu wenlong
	* @Date:   2006-10-31
	*/
	public static long getDayOfWeek( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get(java.util.Calendar.DAY_OF_WEEK);
	}
	
	public static long getDayOfWeek(Long dateTime) {
		   java.util.Calendar c = java.util.Calendar.getInstance();
		   c.setTimeInMillis( dateTime *1000 );
		   return c.get(java.util.Calendar.DAY_OF_WEEK);
		}
	
	/**
	 * 把日期时间转换成秒
	 * @param datetime 日期时间格式的字符串
	 * @return 秒
	 */
	
	public static String dateToUinx_datetime(String datetime)
	{
		long longdate=0;
		String Strdate="";
		try
		{
			if(!String.valueOf(datetime).equals("")&&!String.valueOf(datetime).equals("null"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date DateTime=formatter.parse(datetime);
				longdate=DateTime.getTime();
				longdate=longdate/1000;
				Strdate=String.valueOf(longdate);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return Strdate;
	}
	
	/**
	 * 把日期转换成秒
	 * @param date 给定日期格式字符串
	 * @return
	 */
	public static String dateToUinx_date(String date)
	{
		long longdate=0;
		String Strdate="";
		try
		{
			if(!String.valueOf(date).equals("")&&!String.valueOf(date).equals("null"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date DateTime=formatter.parse(date);
				longdate=DateTime.getTime();
				longdate=longdate/1000;
				Strdate=String.valueOf(longdate);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return Strdate;
	}
	
	/**
	 * 把秒转换成日期
	 * @param sdate 秒的字符串
	 * @return
	 */
	public static String unixto_date(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
				sdate						= sdate+"000";
				long longdate				= Long.parseLong(sdate);
				java.util.Date utilDate 	= new java.util.Date(longdate);
				SimpleDateFormat formatter 	= new SimpleDateFormat("yyyy-MM-dd");
				redate						= formatter.format(utilDate);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return redate;
	}
	
	/**
	 * 把秒转换成日期时间
	 * @param sdate 秒的字符串
	 * @return
	 */
	public static String unixto_datetime(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
				sdate						= sdate+"000";
				long longdate=Long.parseLong(sdate);
				java.util.Date utilDate = new java.util.Date(longdate);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				redate=formatter.format(utilDate);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return redate;
	}
	
	/**
	 * 获得日期对象
	 * @param sdate 毫秒
	 * @return
	 */
	public static Date getUnix_date(String sdate)
	{
		java.util.Date utilDate	= null;
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
				long longdate=Long.parseLong(sdate);
				utilDate = new java.util.Date(longdate);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return null;
		}
		return utilDate;
	}

	/**
	 * 获得日期对象，日期格式
	 * @param date 日期格式
	 * @return
	 */
	public static Date getDate_date(String date)
	{
		java.util.Date DateTime	= null;
		try
		{
			if(!String.valueOf(date).equals("")&&!String.valueOf(date).equals("null"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				DateTime=formatter.parse(date);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return null;
		}
		return DateTime;
	}
	
	/**
	 * 获得日期对象，日期时间格式
	 * @param date 日期时间格式
	 * @return
	 */
	public static Date getDate_dateTime(String date)
	{
		java.util.Date DateTime	= null;
		try
		{
			if(!String.valueOf(date).equals("")&&!String.valueOf(date).equals("null"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateTime=formatter.parse(date);
			}
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return null;
		}
		return DateTime;
	}
	
	/**
	 * 获取当前的日期时间
	 * @return 返回当前日期时间格式字符串
	 */
	public static String now()
	{
		String redate="";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			redate=formatter.format(utilDate);
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return redate;
	}
	/**
	 * 获取当前的日期
	 * @return 获得当前日期格式的字符串
	 */
	public static String nowDate()
	{
		String redate="";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			redate=formatter.format(utilDate);
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return redate;
	}
	
	/**
	 * 获取当前的日期时间(秒表示)
	 * @return
	 */
	public static String nowsencond()
	{
		long longdate   = 0;
		String resecond = "";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String redate=formatter.format(utilDate);
			utilDate	= formatter.parse(redate);
			longdate=utilDate.getTime();
			longdate=longdate/1000;
			resecond=String.valueOf(longdate);
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return resecond;
	}
	
	/**
	 * 获取当前的日期时间(秒表示)
	 * @return
	 */
	public static Long nowLongsencond()
	{
		long longdate   = 0;
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String redate=formatter.format(utilDate);
			utilDate	= formatter.parse(redate);
			longdate=utilDate.getTime();
			longdate=longdate/1000;
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return null;
		}
		return longdate;
	}
	
	/**
	 * 获取当前的日期(秒表示)
	 * @return
	 */
	public static String nowdatesencond()
	{
		long longdate   = 0;
		String resecond = "";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String redate=formatter.format(utilDate);
			utilDate	= formatter.parse(redate);
			longdate=utilDate.getTime();
			longdate=longdate/1000;
			resecond=String.valueOf(longdate);
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return "";
		}
		return resecond;
	}
	
	/**
	 * 获取当前的日期(秒表示)
	 * @return
	 */
	public static Long nowLongdatesencond()
	{
		long longdate   = 0;
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String redate=formatter.format(utilDate);
			utilDate	= formatter.parse(redate);
			longdate=utilDate.getTime();
			longdate=longdate/1000;
		}
		catch(Exception exp)
		{
			//exp.printStackTrace();
			return null;
		}
		return longdate;
	}
	
	/**
	 * 转换指定编码的字符串
	 * @param str
	 * @return 返回指定编码字符串
	 */
    public static String toChinese(String str,String code) 
    {
        if (str == null || str.length() < 1) {
            str = "";
        } else {
            try {
            	str = (new String(str.getBytes(code)));
            } catch (Exception e) {
            	e.printStackTrace();
                return str;
            }
        }
        return str;
    }
	public static void main(String[] args)
	{
		//System.out.println(getDayOfDate(new Long(1221148800)));
		System.out.println(unixto_datetime("1221148800"));
	}
}
