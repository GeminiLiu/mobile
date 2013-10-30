package cn.com.ultrapower.eoms.user.comm.function;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger ;
/**
 * 日期 2006-10-16
 * @author xuquanxing
 */
public class ConvertTimeToSecond {
    static final Logger logger = (Logger) Logger.getLogger(ConvertTimeToSecond.class);
	/**
	 * 日期 2006-10-16
	 * @author xuquanxing 
	 * @param time
	 * @return long
	 * 该方法将字符串类型的时间转换成long类型 字符串格式必须位yyyy-mm-dd hh:mm:ss
	 */
	public static long timeConvert(String time){
		long result=0;
		try{
			Timestamp start = Timestamp.valueOf(time); 
		    result          = start.getTime()/1000;//获得秒
		    }catch(Exception ex){
			logger.info("[10001]ConvertTimeToSecond⣺"+ex.getMessage());
		}
		return result;
	}
	
	public static String numberToLong(String strtime)
	{
		if(strtime==null||strtime.equals(""))
		{
			return null;
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss",Locale.SIMPLIFIED_CHINESE);
			String timestr = sdf.format(new Date(Long.parseLong(strtime+"000"))); 
			return timestr;
		}

		
	}
	public static void main(String[] main){
		
		ConvertTimeToSecond op= new ConvertTimeToSecond();
		
		//System.out.print(new Long(op.timeConvert("2006-10-25 10:18:48")).toString());
		System.out.println(op.numberToLong("1161771528000"));
	}
}

