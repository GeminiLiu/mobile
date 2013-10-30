package cn.com.ultrapower.eoms.user.userinterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.userinterface.bean.ActiveUserInfo;

import com.remedy.arsys.api.ARException;
import com.remedy.arsys.api.ARServerUser;
import com.remedy.arsys.api.NameID;
import com.remedy.arsys.api.Proxy;
import com.remedy.arsys.api.Timestamp;
import com.remedy.arsys.api.UserInfo;
import com.remedy.arsys.api.Util;

public class GetActiveUser
{
	static final Logger logger = (Logger) Logger.getLogger(GetActiveUser.class);
    String user      		= "";
    String password			= "";
    String server			= "";
    int port				= 0;
    
    String usertwo      	= "";
    String passwordtwo		= "";
    String servertwo		= "";
    int porttwo				= 0;
    
   
	public GetActiveUser()
	{
		try
		{
			GetFormTableName gftn 	= new GetFormTableName();
		    user      				= gftn.GetFormName("user");
		    password				= gftn.GetFormName("password");
		    server					= gftn.GetFormName("driverurl");
		    port					= Integer.parseInt(gftn.GetFormName("serverport"));
		    
		    usertwo      			= gftn.GetFormName("usertwo");
		    passwordtwo				= gftn.GetFormName("passwordtwo");
		    servertwo				= gftn.GetFormName("driverurltwo");
		    porttwo					= Integer.parseInt(gftn.GetFormName("serverporttwo"));
		}
		catch(Exception e)
		{
			logger.info("调用 GetFormTableName 初始化时出现异常！请检查配置文件是否正确。");
		}
	}
	/**
	 * 得到在线用户列表
	 * 日期 2007-4-23
	 * @author wangyanguang
	 * @return List
	 */
	public List getActiveUser()
	{
		String userStr		= "";
		String userStr1 	= "";
		String userStr2		= "";
		 HashMap hm 					= new HashMap();
		try
		{
			userStr1 = userStr1 + getServer1User(userStr1,hm);
			System.out.println("用户列表1："+userStr1);
			userStr2 = userStr2 + getServer2User(userStr2,hm);
			System.out.println("用户2列表:"+userStr2);
			if(!String.valueOf(userStr1).equals("")&&!String.valueOf(userStr1).equals("null")&&!String.valueOf(userStr2).equals("")&&!String.valueOf(userStr2).equals("null"))
			{
				userStr = userStr1 + "," + userStr2;
			}
			else if(!String.valueOf(userStr1).equals("")&&!String.valueOf(userStr1).equals("null")&&(String.valueOf(userStr2).equals("")||String.valueOf(userStr2).equals("null")))
			{
				userStr = userStr1;
			}
			else if ((String.valueOf(userStr1).equals("null")||String.valueOf(userStr1).equals(""))&&!String.valueOf(userStr2).equals("")&&!String.valueOf(userStr2).equals("null"))
			{
				userStr = userStr2;
			}
			else if((String.valueOf(userStr1).equals("null")||String.valueOf(userStr1).equals(""))&&(String.valueOf(userStr2).equals("")||String.valueOf(userStr2).equals("null")))
			{
				return null;
			}
			System.out.println("用户列表ALL："+userStr);
			GetActiveUserSql getusersql = new GetActiveUserSql();
			List list = getusersql.getActiveUserInfo(hm,userStr);
			return list;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 * 获得服务器1的用户列表
	 * 日期 2007-4-24
	 * @author wangyanguang
	 * @param userStr
	 * @param hm
	 * @return String
	 */
	public String getServer1User(String userStr,HashMap hm)
	{
		
		ARServerUser arserveruser 	= null;
		int currentdate	= new Long(new Long(Function.DateToUinx_date(Function.NowDate())).longValue()/1000).intValue();
		try
		{
			if(!String.valueOf(server).equals("")&&!String.valueOf(server).equals("null"))
			{
				String servername = server;
				arserveruser = new ARServerUser(user,password,null,server,port);
		        Util.ARSetServerPort(arserveruser, new NameID(servername), port, 0) ;
				arserveruser.login();
				//第一个参数是ar的server信息，第二参数是获得用户的类型。2当前登录用户
				Proxy proxy	= new Proxy();
				Timestamp timestamp = new Timestamp(currentdate);
				UserInfo[] getuser;
				getuser = proxy.ARGetListUser(arserveruser,2,timestamp);
				for(int i=0;i<getuser.length;i++)
				{
					UserInfo tmpuserInfo= getuser[i];
					String nameKey = tmpuserInfo.getUserName().toString();
					userStr = userStr+ "'"+nameKey+"'"+",";
					hm.put(nameKey,tmpuserInfo);
					System.out.println("登陆名"+tmpuserInfo.getUserName());
					System.out.println("连接时间"+Function.Unixto_datetime(String.valueOf(tmpuserInfo.getConnectionTime())+"000"));
					System.out.println("最后登陆时间"+Function.Unixto_datetime(String.valueOf(tmpuserInfo.getLastAccessTime())+"000"));
					System.out.println("连接时间"+tmpuserInfo.getConnectionTime());
					System.out.println("最后登陆时间"+tmpuserInfo.getLastAccessTime());
				}
				userStr 				= userStr+"''";
				System.out.print("服务一用户"+userStr);
			}
			return userStr;
		}
		catch(ARException e)
		{
			System.out.println(e.getMessage());
			return "";
		}
	}
	/**
	 * 获得服务器2的用户列表
	 * 日期 2007-4-24
	 * @author wangyanguang
	 * @param userStr
	 * @param hm
	 * @return String
	 */
	public String getServer2User(String userStr,HashMap hm)
	{
		ARServerUser arserveruser 	= null;
		int currentdate	= new Long(new Long(Function.DateToUinx_date(Function.NowDate())).longValue()/1000).intValue();
		try
		{
			if(!String.valueOf(servertwo).equals("")&&!String.valueOf(servertwo).equals("null"))
			{
				String servername = servertwo;
				arserveruser = new ARServerUser(usertwo,passwordtwo,null,servertwo,porttwo);
		        Util.ARSetServerPort(arserveruser, new NameID(servername), porttwo, 0) ;
				arserveruser.login();
				//第一个参数是ar的server信息，第二参数是获得用户的类型。2当前登录用户
				Proxy proxy	= new Proxy();
				Timestamp timestamp = new Timestamp(currentdate);
				UserInfo[] getuser;
				getuser = proxy.ARGetListUser(arserveruser,2,timestamp);
				for(int i=0;i<getuser.length;i++)
				{
					UserInfo tmpuserInfo= getuser[i];
					String nameKey  = String.valueOf(tmpuserInfo.getUserName());
					String flag		= String.valueOf(hm.get(nameKey));
					if(String.valueOf(flag).equals("")||String.valueOf(flag).equals("null"))
					{
						userStr = userStr+ "'"+nameKey+"'"+",";
						hm.put(nameKey,tmpuserInfo);
					}
					System.out.println("登陆名"+tmpuserInfo.getUserName());
					System.out.println("连接时间"+Function.Unixto_datetime(String.valueOf(tmpuserInfo.getConnectionTime())+"000"));
					System.out.println("最后登陆时间"+Function.Unixto_datetime(String.valueOf(tmpuserInfo.getLastAccessTime())+"000"));
					System.out.println("连接时间"+tmpuserInfo.getConnectionTime());
					System.out.println("最后登陆时间"+tmpuserInfo.getLastAccessTime());
				}
				userStr = userStr+"''";
				System.out.print("服务二用户"+userStr);
				
			}
			//GetActiveUserSql getusersql = new GetActiveUserSql();
			//List list = getusersql.getActiveUserInfo(hm,userStr);
			return userStr;
		}
		catch(ARException e)
		{
			System.out.println(e.getMessage());
			return "";
		}
	}
	public static void main(String args[])
	{
		GetActiveUser getuser = new GetActiveUser();
		List list = getuser.getActiveUser();
		for(Iterator it = list.iterator();it.hasNext();)
		{
			ActiveUserInfo activeuser = (ActiveUserInfo)it.next();
			System.out.println(activeuser.getConnectionTime());
			System.out.println(activeuser.getCPName());
			System.out.println(activeuser.getDPName());
			System.out.println(activeuser.getLastAccessTime());
			System.out.println(activeuser.getUserLoginName());
			System.out.println(activeuser.getUserName());
		}
	}
}
