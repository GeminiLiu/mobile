package cn.com.ultrapower.ultrawf.share;

import java.text.SimpleDateFormat;
import java.util.*;

public class FormatTime
{
    private static Date time;
    private static SimpleDateFormat format;
    private static String strtime;
    
    /**
     * 将长整型的日期转化为字符型日期字符串
     * @param intDate
     * @return
     */
    public static String formatIntToDateString(long intDate)
    {	
    	if(intDate>0)
    	{
    		try{
		        long c_unix_time2 = intDate;
		        time = new Date();
		        time.setTime(c_unix_time2*1000);
		        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		        strtime = format.format(time);
    		}catch(Exception ex)
    		{
    			strtime="";
    			ex.printStackTrace();
    		}	        
    	}
    	else
    	{
    		strtime="";
    	}
        return strtime;
    }
    
    /**
     * 将长整型的日期转化为字符型日期字符串
     * @param intDate
     * @return
     */
    public static String formatIntToDateString(String _format,long intDate)
    {	
    	if(intDate>0)
    	{
    		try{
		        long c_unix_time2 = intDate;
		        time = new Date();
		        time.setTime(c_unix_time2*1000);
		        format = new SimpleDateFormat(_format, Locale.getDefault());
		        strtime = format.format(time);
    		}catch(Exception ex)
    		{
    			strtime="";
    			ex.printStackTrace();
    		}
    	}
    	else
    	{
    		strtime="";
    	}
        return strtime;
    }      
    
    
    /**
     * 将长整型转换为日期类型
     * @param intDate
     * @return
     */
    public static Date formatIntToDate(long intDate)
    {
        long c_unix_time2 = intDate;
        time = new Date();
        time.setTime(c_unix_time2*1000);    	
    	return time;
    }
    /**
     * 将字符串转换为日期类型
     * @param strDate
     * @return
     */
    public static Date FormatStringToDate(String strDate)
    {
    	if(strDate.trim().equals(""))
    		return null;
    	try{
    		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    		return format.parse(strDate);
    	}catch(Exception ex)
    	{
    		return null;
    	}
    	
    }
    /**
     * 将日期转换成长整型
     * @param p_date
     * @return
     */
    public static long FormatDateToInt(Date p_date)
    {
    	if (p_date != null)
    	{
    		return p_date.getTime()/1000;
    	}
    	return 0;
    }
    /**
     * 将字符串日期转换成长整类型日期
     * @param strDate
     * @return
     */
    public static long FormatDateStringToInt(String strDate)
    {
    	if(strDate.trim().equals(""))
    		return -1;
    	String strAry[]=strDate.split(":");
    	if(strAry.length>1)
    		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    	else
    		format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    	  try
    	  {
    	   time = format.parse(strDate+":00");
    	   return time.getTime()/1000;
    	  }
    	  catch (Exception e)
    	  {
    	   return -1;
    	  }
    }
    /**
     * 将字符串类型转换为长整型(按格式)
     * @param strDate
     * @param _format
     * @return
     */
    public static long FormatDateStringToInt(String strDate,String _format)
    {
    	format = new SimpleDateFormat(_format, Locale.getDefault());
    	  try
    	  {
    	   time = format.parse(strDate);
    	   return time.getTime()/1000;
    	  }
    	  catch (Exception e)
    	  {
    	   return -1;
    	  }
    }
    /**
     * 将日期类型转化为长日期字符串
     * @param p_date
     * @return
     */
    public static String FormatLongDateToString(Date p_date)
    {
    	strtime="";
    	
    	if(FormatDateToInt(p_date)>0)
    	{ 
    		
    		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
   			strtime = format.format(p_date);
    	}
    	return strtime;
    }
    /**
     * 将日期类型转化为短日期字符串
     * @param p_date
     * @return
     */
    public static String FormatShortDateToString(Date p_date)
    {
    	format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    	strtime = format.format(p_date);
    	return strtime;
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
	
	public static Date getCurrentDate() {
		Date date=null;
		Calendar calendar = GregorianCalendar.getInstance();
		date = calendar.getTime();
		return date;
	}	
	
	public static String FormatLongStringToDateString(String strLong)
	{
		if(strLong==null)
			return "";
		if(strLong.trim().equals("")||strLong.trim().equals("0"))
			return "";
		return formatIntToDateString(Long.parseLong(strLong));
		
	}
    
}
