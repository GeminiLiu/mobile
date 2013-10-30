package cn.com.ultrapower.eoms.user.comm.function;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class Function 
{
	static final Logger logger = (Logger) Logger.getLogger(Function.class);
	/**
	 *
	 * author wuwenlong
	 * @param args
	 * @return void
	 * 公用方法
	 */
	public Function()
	{
		
	}
	/**
	 *
	 * author wuwenlong
	 * @param args
	 * @return void
	 * 把null字符串转换成空值
	 */
	public static String nullString(String obj) 
	{
		if (obj == null||String.valueOf(obj).equals("null")) 
		{
			return "";
		} 
		else
		{
			return obj;
		}
	}
	
	/**
	 * <p>Description:将null的Long型数据转换为Long型的0</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-17
	 * @param obj
	 * @return Long
	 */
	public static Long nullLong(Long obj) 
	{
		if (obj == null||obj.equals("null")) 
		{
			return new Long(0);
		} 
		else
		{
			return obj;
		}
	}
	
	public static String StrNull(String obj) 
	{	try{
			if (obj == null||String.valueOf(obj).equals("null")) 
			{
				return "";
			} 
			else
			{
				return obj;
			}
		}catch(Exception e)
		{
			return "";
		}
	}
	/**
	 *
	 * author wuwenlong
	 * @param args
	 * @return void
	 * 判断字符串是否是数字
	 */
	public static boolean isnum(String numvalue)
	{
		try
		{
			int i			= 0;
			int length		= 0;
			String num		= "0123456789";
			String tmpnum	= "";
			String flag		= "";
			if(numvalue!=null&&!String.valueOf(numvalue).equals(""))
			{
				length=numvalue.length();
				for(i=0;i<length;i++)
				{
					tmpnum		= "";
					tmpnum		= numvalue.substring(i,i+1);
					System.out.println(tmpnum);
					if(num.indexOf(tmpnum)<0)
					{
						flag="1";
						break;
					}
					if(num.indexOf(tmpnum)>=0)
					{
						flag="2";
						continue;
					}
				}
				if(flag.equals("1"))
				{
					return false;
				}
				else if(flag.equals("2"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{	
			return false;
		}
	}
	
	/**
	 * date  2006-10-31
	 * author shigang
	 * @param args
	 * @return long
	 * 传过来的String 数据转换是long并判断是否为空
	 */
	public static long nuLong(String nulong){
		long longvalue=0;
		try{
			longvalue = Long.parseLong(nulong);
			if (longvalue==0){
				longvalue=0;
			}
		}catch(Exception e){
			longvalue=0;
			return longvalue;
		}
		return longvalue;
	}
	
	public static int nuint(String strint){
		int reint=0;
		try{
			reint = Integer.parseInt(strint);
		}catch(Exception e){
			reint=0;
			return reint;
		}
		return reint;
	}
	public static Long javaLong(String nulong){
		Long longvalue=new Long(0);
		try{
			longvalue = new Long(Long.parseLong(nulong));
			if (longvalue==new Long(0)){
				longvalue=new Long(0);
			}
		}catch(Exception e){
			longvalue=new Long(0);
			return longvalue;
		}
		return longvalue;
	}
    public static String toChinese(String str) 
    {
        if (str == null || str.length() < 1) {
            str = "";
        } else {
            try {
            	str = (new String(str.getBytes("iso-8859-1")));
            } catch (Exception e) {
                System.err.print(e.getMessage());
                e.printStackTrace();
                return str;
            }
        }
        return str;
    }
    public static String toTrans(String comestr) {
        if (comestr == null || comestr.length() < 1) {
        	comestr = "";
        }
        else 
        {
            try 
            {
            	comestr = (new String(comestr.getBytes("UTF-8"), "UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                return comestr;
            }
        }
        return comestr;
    }
    public static String toChinese1(String getstr)
    {
    	String result = "";
    	byte temp [];
    	try
    	{
    		if(getstr!=null&&!String.valueOf(getstr).equals("")&&!String.valueOf(getstr).equals("null"))
    		{
	    		temp=getstr.getBytes("UTF-8");
	    		result = new String(temp);
    		}
    		else
    		{
    			result="";
    		}
    	}
		catch(UnsupportedEncodingException e)
		{
		    System.out.println (e.toString());
		}
		return result;
    }
    
    /**
     * <p>Description:根据传入的参数和字符("."等特殊字符除外)分割字符串用于将最末一个多余字符删掉<p>
     * @author wangwenzhuo
     * @creattime 2006-11-25
     * @param str
     * @return String
     */
    public static String splitString(String str,String flag)
    {
    	String finalStr		= "";
		String temp_str[]	= str.split(flag);
		for(int i = 0;i<temp_str.length;i++)
		{	
			if(!finalStr.equals(""))
			{
				finalStr = finalStr + flag;
			}
			finalStr = finalStr + temp_str[i];
		}
		return finalStr;
    }
    
    /**
     * <p>Description:根据传入的参数和字符("."等特殊字符除外)分割字符串(去掉字符串最末一位内容)<p>
     * @author wangwenzhuo
     * @creattime 2006-12-27
     * @param str
     * @param flag
     * @return String
     */
    public static String splitDeformityString(String str,String flag)
    {
    	String finalStr		= "";
		String temp_str[]	= str.split(flag);
		for(int i = 0;i<temp_str.length-1;i++)
		{	
			if(!finalStr.equals(""))
			{
				finalStr = finalStr + flag;
			}
			finalStr = finalStr + temp_str[i];
		}
		return finalStr + ";";
    }
    
    /**
     * <p>Description:将Long转换为long型数据<p>
     * @author wangwenzhuo
     * @creattime 2006-12-26
     * @param num
     * @return long
     */
    public static long tranTolong(Long num)
    {
    	long longNum = 0;
    	try
    	{
    		if(num==null)
        	{
        		longNum = 0;
        	}
        	else
        	{
        		longNum = num.longValue();
        	}
        	return longNum;
    	}
    	catch(Exception e)
    	{
    		logger.error("将Long转换为long型数据失败"+e.getMessage());
    		return 0;
    	}
    	
    }
    
	/**
	 * 功能：将用户ID或组ID前面的0去掉。
	 * 日期 2006-11-23
	 * 
	 * @author wangyanguang/王彦广 
	 * @param zeroid
	 * @return String
	 */
	public static String dropZero(String zeroid)
	{
		try{
			String returnStr= "";
			for (int i=0;i<zeroid.length();i++)
			{
				String str = zeroid.substring(i,i+1);
				if(str.equals("0"))
				{
					returnStr = zeroid.substring(i+1);
				}
				else
				{
					break;
				}
			}
			return returnStr;
		}catch(Exception e){
			logger.error("--输入的zeroid----"+zeroid);
			return "000";
		
		}
	}
	public static String toChineseiso(String str)
	{
        if (str == null || str.length() < 1) {
        	str = "";
        }
        else 
        {
            try 
            {
            	str = new String(str.getBytes("iso-8859-1"),"UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                return str;
            }
        }
        return str;
	}
	//限制字符串长度
	public static String StrLen(String str)
	{
		try{
			StringBuffer returnlenStr= new StringBuffer();
			if(str!=""||!str.equals(null))
			{
				if(str.length()>30){
					returnlenStr.append(str.substring(1,30));
				}else{
					returnlenStr.append(str);
				}
			}
			return returnlenStr.toString();
			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	public synchronized static String getNewID(String tablename,String fieldname)
	{
		String newid	= "1";
		Calendar CD 	= Calendar.getInstance();
		long datetimeid	= CD.getTimeInMillis()/1000;
		String sql		= "select * from newid where tablename='"+ tablename +"' and fieldname='"+ fieldname +"'";
		String updatesql= "update newid set newid=newid+1 where tablename='"+ tablename +"' and fieldname='"+ fieldname +"'";
		String insertsql= "insert into newid (tablename,fieldname,newid) values('"+ tablename +"','"+ fieldname +"','1')";
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stmt		= null;
		ResultSet rs		= null;
		try
		{
			
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stmt=dataBase.GetStatement();
			rs		= stmt.executeQuery(sql);
			if(rs.next())
			{
				newid=String.valueOf(Long.parseLong(rs.getString("newid"))+1);
				stmt.execute(updatesql);
				
			}
			else
			{
				stmt.execute(insertsql);
			}
			rs.close();
			stmt.close();
			rs=null;
			stmt=null;
			dataBase.closeConn();	
			return newid;
		}
		catch(Exception e)
		{
			
			logger.error("获得NewID错误"+e.getMessage());
			newid=String.valueOf(datetimeid);
		}
		finally
		{
			Function.closeDataBaseSource(rs,stmt,dataBase);
		}
		return newid;
	}
	public static String gettimeid()
	{
		String timeid="";
		try
		{
			Calendar CD 	= Calendar.getInstance();
			long datetimeid	= CD.getTimeInMillis();
			timeid=String.valueOf(datetimeid);
			return timeid;
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return "0";
		}
	}
	public static String getStrZero(Long zeroid)
	{
		String returnStr= "";
		StringBuffer strzero=new StringBuffer();
		strzero.append("0000000000000000");
		strzero.append(zeroid);
		
		returnStr=(String) strzero.subSequence(strzero.length()-15,strzero.length());
		
	
		return returnStr;
	}
	
	
	public static String getStrZeroConvert(String zeroid)
	{
		String returnStr= "";
		StringBuffer strzero=new StringBuffer();
		strzero.append("0000000000000000");
		strzero.append(zeroid);
		
		returnStr=(String) strzero.subSequence(strzero.length()-15,strzero.length());
		
	
		return returnStr;
	}
	//获得工程目录
	public static String getProjectPath()
	{
		try
		{
			String path = Function.class.getResource("").getPath();
        	path=path.substring(1,path.lastIndexOf("WEB-INF"));
        	String[] pathsp;
    		String newpath	= "";
    		pathsp			= path.split("/");
    		if(pathsp.length>0)
    		{
    			for(int i=0;i<pathsp.length;i++)
    			{
    				if((String.valueOf(newpath).equals("")||String.valueOf(newpath).equals("null")))
    				{
    					if(String.valueOf(pathsp[0]).indexOf(":")>0)
    					{
    						newpath		= pathsp[0]+File.separator;
    					}
    					else
    					{
    						newpath		= File.separator+pathsp[0]+File.separator;
    					}
    				}
    				else
    				{
    					newpath		= newpath+pathsp[i]+File.separator;
    				}
    			}
    		}
    		newpath=newpath.replaceAll("%20"," ");
    		//logger.info("获得工程路径成功");
    		return newpath;
		}
		catch(Exception e)
		{
			System.out.println("获得工程路径失败");
			logger.info("获得工程路径失败");
			e.getMessage();
			return "";
		}
	}
	//初始化参数
	public static void initPram()
	{
		try
		{
			//注释方法，防止多次调用
	    	/*String path			= Function.getProjectPath();
			if(path!=null&&!path.equals(""))
			{
				path	=path+"WEB-INF"+File.separator+"cfg"+File.separator+"BasicInfoConfig.xml";
				long lastmodify=GetConfigLastModify.getFileLastModify(path);
				System.out.println(lastmodify+"="+ConfigContents.lastmodify);
				if(!ConfigContents.cacheflag||lastmodify!=ConfigContents.lastmodify)
				{
					ConfigContents.initContents();
				}
			}*/
		}
		catch(Exception e)
		{
			e.getMessage();
		}
	}
	
	/**
	 * <p>Description:释放数据库资源</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-16
	 * @param rs
	 * @param stm
	 * @param dataBase
	 */
	public static void closeDataBaseSource(ResultSet rs,Statement stm,IDataBase dataBase)
	{
		try
		{
			if(rs!=null)
			rs.close();
		}
		catch(Exception e)
		{
			logger.error("数据库rs为null"+e.getMessage());
		}
		try
		{
			if(stm!=null)
			stm.close();
		}
		catch(Exception e)
		{
			logger.error("数据库Statement为null"+e.getMessage());
		}
		try
		{
			if(dataBase!=null)
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			logger.error("数据库连接失败"+e.getMessage());
		}
	}
	
	/**
	 * <p>Description:将0:00形式的时间转化为秒</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-17
	 * @param timeStr
	 * @return Long
	 */
	public static Long tranHourMinuteToSecond(String timeStr)
	{
		String[] temp_time		= timeStr.split(":");
		int hourToSecond		= Integer.parseInt(temp_time[0])*60*60;
		int minuteToSecond		= Integer.parseInt(temp_time[1])*60;
		Long final_time			= new Long(hourToSecond + minuteToSecond);
		return final_time;
	}
	
	/**
	 * <p>Description:将时间秒转化为0:00形式</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-17
	 * @param temp_timeOfSecond		传过来的秒数
	 * @parm flagValue				默认初始值
	 * @return String
	 */
	public static String tranSecondToHourMinute(String temp_timeOfSecond,String flagValue)
	{
		//最终返回时间全局变量
		String final_time = "";
		//将获得参数作相应处理
		String timeOfSecond = "";
		if(temp_timeOfSecond==null||temp_timeOfSecond.equals(""))
		{
			if(flagValue.equals("start"))
			{
				final_time = "0:00";
			}
			else if(flagValue.equals("end"))
			{
				final_time = "23:59";
			}
		}
		else
		{
			if(temp_timeOfSecond==null||temp_timeOfSecond.equals(""))
			{
				timeOfSecond = "0";
			}
			else
			{
				timeOfSecond = temp_timeOfSecond;
			}
			
			//分钟,保持其为两位
			String timeOfMinute	= "";
			int temp_minute		= Integer.parseInt(timeOfSecond)/60%60;
			if(temp_minute<10)
			{
				timeOfMinute	= "0"+temp_minute;
			}
			else
			{
				timeOfMinute	= String.valueOf(temp_minute);
			}
			//小时
			String timeOfHour	= String.valueOf(Integer.parseInt(timeOfSecond)/3600);
			final_time	= timeOfHour + ":" +timeOfMinute;
		}
		return final_time;
	}
	public static String getTime(String time){
		try{
			 String[]	 remind_point1        = time.split(":");
			 int  hours     = Integer.parseInt(remind_point1[0]);
			 int  minute    = Integer.parseInt(remind_point1[1]);
			 int  lasthours = hours*60*60;
			 int lastminute = minute*60;
			 int  sumtime   = lasthours+lastminute;
			 String times   = String.valueOf(sumtime);
			 return times;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static String getTimevalue(String time){
		
		
			int convertime    = Integer.parseInt(time);
			String hours      = new Integer(convertime/3600).toString();//求出小时
			String minutes    = new Integer((convertime%3600)/60).toString();//求出分钟
				   minutes	  = minutes+"0".substring(0,1);
		    String lasttime   = hours+":"+minutes;
		    
		    return lasttime;
		
	}
	public static String getTimevalueNull(String time){
		try{
			if (time!=null){
				int convertime    = Integer.parseInt(time);
				String hours      = new Integer(convertime/3600).toString();//求出小时
				String minutes    = new Integer((convertime%3600)/60).toString();//求出分钟
				if (minutes.length()==1){	  
					minutes	  = "0"+minutes.substring(0,1);
				}
				String lasttime   = hours+":"+minutes;
			    
			    return lasttime;
			}else{
				return "0:00";
			}
		}catch(Exception e){
			 return "0:00";
		}
	}
	public static int getpagesize(String sql)
	{
		int recordnum = 0;
		if(!String.valueOf(sql).equals("null")&&!String.valueOf(sql).equals(""))
		{
			IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			Statement stmt		= null;
			ResultSet rs		= null;
			try
			{
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				//获得数据库查询结果集
				stmt=dataBase.GetStatement();
				rs		= stmt.executeQuery(sql);
				if(rs.next())
				{
					recordnum=rs.getInt(1);
				}
				else
				{
					recordnum=0;
				}
				rs.close();
				stmt.close();
				rs=null;
				stmt=null;
				dataBase.closeConn();
				return recordnum;
			}
			catch(Exception e)
			{
				logger.error("获得记录数错误"+e.getMessage());
				return recordnum;
			}
			finally
			{
				Function.closeDataBaseSource(rs,stmt,dataBase);
			}
		}
		else
		{
			return recordnum;
		}
	}
	//把日期时间转换成毫秒
	public static String DateToUinx_datetime(String datetime)
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
	//把日期转换成毫秒
	public static String DateToUinx_date(String date)
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
//	把日期转换成秒
	public static String DateToSecond_date(String date)
	{
		long longdate=0;
		String Strdate="";
		try
		{
			if(!String.valueOf(date).equals("")&&!String.valueOf(date).equals("null"))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date DateTime=formatter.parse(date);
				longdate=DateTime.getTime()/1000;
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
	//把秒转换成日期
	public static String Secondto_date(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
				long longdate				= Long.parseLong(sdate);
				java.util.Date utilDate 	= new java.util.Date(longdate*1000);
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
//	把毫秒转换成日期
	public static String Unixto_date(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
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
	//把毫秒转换成日期时间
	public static String Unixto_datetime(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
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
//	把毫秒转换成日期时间
	public static String Second_datetime(String sdate)
	{
		String redate="";
		try
		{
			if(!String.valueOf(sdate).equals("")&&!String.valueOf(sdate).equals("null"))
			{
				long longdate=Long.parseLong(sdate);
				java.util.Date utilDate = new java.util.Date(longdate*1000);
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
	//获取当前的日期时间
	public static String Now()
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
	//获取当前的日期
	public static String NowDate()
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
	
	//添加了替换'&'为'&amp;'
	public static String getReplaceString(String pramstr)
	{
		
		String returnstr 	= "";
		String tmpstr 		= "";
		String str 			= Function.nullString(pramstr);
		String strarray[] 	= str.split("&amp;");
		
		for(int intvalue = 0;intvalue<strarray.length;intvalue++)
		{
			tmpstr = strarray[intvalue].replaceAll("&","&amp;");
			returnstr = returnstr + tmpstr;
			returnstr = returnstr + "&amp;";
		}
		returnstr = returnstr.substring(0,returnstr.length()-5);
		
		return returnstr;
	}
//	-----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------
	/** 
	* 获取指定日期的年份 
	* @param p_date util.Date日期 
	* @return int   年份 
	* @author zhuqx
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
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getMonthOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.MONTH ) + 1;
	}

	/** 
	* 获取指定日期的日份 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getDayOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.DAY_OF_MONTH );
	}

	/** 
	* 获取指定日期的小时 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author zhuqx
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
	* @author zhuqx
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
	* @author zhuqx
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
	* @author zhuqx
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
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static long getDayOfWeek( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get(java.util.Calendar.DAY_OF_WEEK);
	}
	
	public static boolean setPreparm(PreparedStatement prepareStatement,List parmlist)
	{
		try
		{
			if(parmlist!=null&&parmlist.size()>0)
			{
				Iterator iterator = parmlist.iterator();
				int i=0;
				while(iterator.hasNext())
				{
					i=i+1;
					prepareStatement.setString(i,String.valueOf(iterator.next()));
				}
			}
			else
			{
				logger.error("绑定变量传过来的参数数组是null");
				return false;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public static void main(String args[])
	{
		System.out.println(DateToUinx_date("2007-9-9"));
		Date dddd=Function.getUnix_date("1179368270000");
		System.out.println("年"+Function.getYearOfDate(dddd));
		System.out.println("月"+Function.getMonthOfDate(dddd));
		System.out.println("日"+Function.getDayOfDate(dddd));
		System.out.println("时"+Function.getHourOfDate(dddd));
		System.out.println("分"+Function.getMinuteOfDate(dddd));
		System.out.println("秒"+Function.getSecondOfDate(dddd));
		int count = 20000;
		int range = 60000;
		double sum = 0;
		Random rand = new Random(); 
		for (int i=0; i<count; i++) {
			System.out.println(rand.nextInt(range));
		}
	}
}