package cn.com.ultrapower.eoms.user.kpi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.*;
public class DateOperation
{
	// 把日期时间转换成秒
	public static String DateToUinx_datetime(String datetime)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date DateTime = formatter.parse(datetime);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}

	// 把日期转换成秒
	public static String DateToUinx_date(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date DateTime = formatter.parse(date);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	/**
	 * 日期 2007-5-24
	 * @author xuquanxing 
	 * @param date
	 * @return String
	 * 将当前时期格式化成年,如2007
	 */
	public static String DateToUinx_year()
	{
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			Strdate                    = formatter.format(new Date());
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	/**
	 * 日期 2007-5-24
	 * @author xuquanxing 
	 * @return String
	 * 将当前时期格式化成月,如05
	 */
	public static String DateToUinx_month()
	{
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("MM");
			Strdate                    = formatter.format(new Date());
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}

	
	/**
	 * 日期 2007-5-24
	 * @author xuquanxing 
	 * @return String
	 * 将当前时期格式化成日,如11
	 */
	public static String DateToUinx_day()
	{
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd");
			Strdate                    = formatter.format(new Date());
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	
	public static String DateToUinx_hour()
	{
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("HH");
			Strdate                    = formatter.format(new Date());
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	public static String yearMonthToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
			Date DateTime = formatter.parse(date);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	/**
	 * 日期 2007-5-17
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 */
	public static String yearMonthDayToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date DateTime = formatter.parse(date);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @param date
	 * @return String
	 */
	public static String yearMonthDayHourConvertToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
			Date DateTime              = formatter.parse(date);
			longdate                   = DateTime.getTime();
			Strdate                    = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	public static String yearMonthConvertToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			Date DateTime = formatter.parse(date);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	
	/**
	 * 日期 2007-5-17
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 */
	public static String yearMonthDayConvertToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date DateTime = formatter.parse(date);
			longdate = DateTime.getTime();
			Strdate = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @param date
	 * @return String
	 */
	public static String yearMonthDayHourToMillsecond(String date)
	{
		long longdate = 0;
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
			Date DateTime              = formatter.parse(date);
			longdate                   = DateTime.getTime();
			Strdate                    = String.valueOf(longdate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @param date
	 * @return String
	 */
	public static String millsecondToYearMonthDay()
	{
		String Strdate = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE);
		    Strdate              = formatter.format(new Date());
		  //  System.out.println("nihao="+new Date().getDay());
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return Strdate;
	}
	// 把秒转换成日期
	public static String Unixto_date(String sdate)
	{
		String redate = "";
		try
		{
			long longdate = Long.parseLong(sdate);
			java.util.Date utilDate = new java.util.Date(longdate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			redate = formatter.format(utilDate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return redate;
	}

	// 把秒转换成日期时间
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 */
	public static String Unixto_datetime(String sdate)
	{
		String redate = "";
		try
		{
			long longdate = Long.parseLong(sdate);
			java.util.Date utilDate = new java.util.Date(longdate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			redate = formatter.format(utilDate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return redate;
	}

	// 获取当前的日期时间
	public static String Now()
	{
		String redate = "";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			redate = formatter.format(utilDate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return redate;
	}
	
	
	/**
	 * 日期 2007-2-9
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 * 将字符串转换成200701
	 */
	public static String  secondTo_Month(String sdate)
	{
		String redate = "";
		try
		{
			long longdate = Long.parseLong(sdate);
			java.util.Date utilDate = new java.util.Date(longdate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM",Locale.SIMPLIFIED_CHINESE);
			redate = formatter.format(utilDate);
		} catch (Exception exp)
		{
			 exp.printStackTrace();
		}
		return redate;
	}
	/**
	 * 日期 2007-2-9
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 * 将格式为字符串的时间转换成格式20070130
	 */
	public static String  secondTo_Day(String sdate)
	{
		String redate = "";
		try
		{
			long longdate = Long.parseLong(sdate);
			java.util.Date utilDate = new java.util.Date(longdate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.SIMPLIFIED_CHINESE);
			redate = formatter.format(utilDate);
			System.out.println(redate);
		} catch (Exception exp)
		{
			 exp.printStackTrace();
		}
		return redate;
	}
	/**
	 * 日期 2007-2-9
	 * @author xuquanxing 
	 * @param sdate
	 * @return String
	 */
	public static String  secondTo_Hour(String sdate)
	{
		String redate = "";
		try
		{
			long longdate = Long.parseLong(sdate);
			java.util.Date utilDate = new java.util.Date(longdate);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH",Locale.SIMPLIFIED_CHINESE);
			redate = formatter.format(utilDate);
			System.out.println(redate);
		} catch (Exception exp)
		{
			 exp.printStackTrace();
		}
		return redate;
	}
	/**
	 * 日期 2007-2-6
	 * @author xuquanxing 
	 * @param t
	 * @return String
	 * 将格式为200701的时间转换成格式2007-01
	 */
	public static String formatMonth(String t)
	{
		String backmonth= "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM",Locale.SIMPLIFIED_CHINESE);
			Date date2 = null;
			date2 = formatter.parse(t);// 将参数按照给定的格式解析参数
		//	redate = formatter.format(t);
			SimpleDateFormat formatte = new SimpleDateFormat("yyyy-MM",Locale.SIMPLIFIED_CHINESE);
		    backmonth = formatte.format(new Date(date2.getTime()));
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return backmonth;
	}
	/**
	 * 日期 2007-2-6
	 * @author xuquanxing 
	 * @param t
	 * @return String
	 * 将格式为20070130的时间转换成格式2007-01-30
	 */
	public static String formatDay(String t)
	{
		String backday = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",Locale.SIMPLIFIED_CHINESE);
			Date date2 = null;
			date2 = formatter.parse(t);// 将参数按照给定的格式解析参数
			SimpleDateFormat formatte = new SimpleDateFormat("yyyy-MM-dd",Locale.SIMPLIFIED_CHINESE);
			backday = formatte.format(new Date(date2.getTime()));
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return backday;
	}

	/**
	 * 日期 2007-2-6
	 * @author xuquanxing 
	 * @param t
	 * @return String
	 * 将格式为2007013010的时间转换成格式2007-01-30 10:00:00
	 */
	public static String formatHour(String t)
	{
		String backhour = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH",Locale.SIMPLIFIED_CHINESE);
			Date date2 = null;
			date2 = formatter.parse(t);// 将参数按照给定的格式解析参数
			SimpleDateFormat formatte = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.SIMPLIFIED_CHINESE);
		    backhour = formatte.format(new Date(date2.getTime()));
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return backhour;
	}

	// 获取当前的日期
	public static String NowDate()
	{
		String redate = "";
		try
		{
			java.util.Date utilDate = new java.util.Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			redate = formatter.format(utilDate);
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return redate;
	}

	/**
	 * 日期 2007-2-5
	 * 
	 * @author xuquanxing
	 * @param t
	 *            void
	 */
	public static long timeop(String t, String pattern)
	{
		// 传入的参数要与yyyyMMddHH的格式相同 "yyyyMMddHH"
		SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		Date date2 = null;
		try
		{
			date2 = simpledateformat.parse(t);// 将参数按照给定的格式解析参数
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		System.out.println(date2.getTime());
		return date2.getTime();
	}
	/**
	 * 日期 2007-5-14
	 * @author xuquanxing 
	 * @param year
	 * @return String
	 * example: 2007 convert to the format millsencod
	 */
	public static String yearToSecond(String year)
	{
		String redate    = "";
		Date utilDate    = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			utilDate                   = formatter.parse(year);
			redate                     = new Long(utilDate.getTime()).toString();
		} catch (Exception exp)
		{
			// exp.printStackTrace();
		}
		return redate;
	}
	
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @return String
	 * 返回明天的日期
	 */
	public static String getTommrow()
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar  cal  =  Calendar.getInstance();
		cal.roll(java.util.Calendar.DAY_OF_YEAR,1);
		System.out.println("明天："+df.format(cal.getTime()));
		return df.format(cal.getTime());
	}
	
	
	/**
	 * 日期 2007-5-21
	 * @author xuquanxing 
	 * @return String
	 * 返回指定跨度（天）的日期如：相对当前天的昨天，明天
	 */
	public static String getInternalDate(int internal)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar  cal  =  Calendar.getInstance();
		cal.roll(java.util.Calendar.DAY_OF_YEAR,internal);
		System.out.println("指定间隔天数的日期："+df.format(cal.getTime()));
		return df.format(cal.getTime());
	}
	/**
	 * 日期 2007-5-24
	 * @author xuquanxing 
	 * @return String
	 *取的当前时间的下一个小时
	 */
	public static String getNextHour()
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHH");
		Calendar  cal  =  Calendar.getInstance();
		cal.roll(java.util.Calendar.HOUR_OF_DAY,1);
		System.out.println("下一小时："+df.format(cal.getTime()));
		return df.format(cal.getTime());
	}
	
	public static String getLastTwoDay()
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar  cal  =  Calendar.getInstance();
		cal.roll(java.util.Calendar.DAY_OF_YEAR,-14);
		System.out.println("前两周时间："+cal.getTimeInMillis());
		return df.format(cal.getTime());
	}
	
	public static void main(String[] args)
	{
	System.out.println(DateOperation.yearMonthDayToMillsecond("20070908"));
	}
}