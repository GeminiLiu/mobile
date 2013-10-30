package cn.com.ultrapower.eoms.user.smsexport;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateOp
{

	/**
	 * 日期 2007-6-9
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		//DateOp.getLastTwoDay();
		DateOp.getLastMonth();
	}
	public static long getLastTwoDay()
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		Calendar  cal  =  Calendar.getInstance();
		cal.roll(java.util.Calendar.DAY_OF_YEAR,-14);
		System.out.println("前两周时间："+cal.getTimeInMillis());
		return cal.getTimeInMillis();
	}
	
	
	public static long getLastMonth()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar  cal       = Calendar.getInstance();
		cal.roll(java.util.Calendar.MONTH,-1);
		System.out.println("前一个月时间："+cal.getTimeInMillis());
		return cal.getTimeInMillis();
	}
}
