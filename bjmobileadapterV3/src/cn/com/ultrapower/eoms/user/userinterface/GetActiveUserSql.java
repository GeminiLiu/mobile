package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.ActiveUserInfo;

import com.remedy.arsys.api.UserInfo;

public class GetActiveUserSql
{
	static final Logger logger = (Logger) Logger.getLogger(GetActiveUserSql.class);
	private String usertablename			= "";
	private String grouptablename			= "";

	public GetActiveUserSql()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	public List getActiveUserInfo(HashMap hm,String active_users)
	{
		List list = new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		sql.append("select distinct usertable.c630000001,usertable.c630000003,usertable.c630000013,usertable.c630000015,grouptable1.c630000018 cpname,grouptable2.c630000018 dpname");
		sql.append(" from "+usertablename+" usertable,"+grouptablename+" grouptable1,"+grouptablename+" grouptable2");
		sql.append(" where usertable.c630000001 in ("+active_users+")");
		sql.append(" and usertable.c630000013=grouptable1.c1");
		sql.append(" and usertable.c630000013 = grouptable1.c1 and (usertable.c630000015 = grouptable2.c1 or (usertable.c630000015 is null and usertable.c630000013 = grouptable2.c1))");
		sql.append(" order by usertable.c630000013,usertable.c630000015,usertable.c630000001");
		logger.info("查询语句："+sql.toString());
		System.out.println("查询语句："+sql.toString());
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String dpid	= "";
				String connectionTime = "";
				String lastAccessTime = "";
				
				String userLoginName 	= rs.getString("c630000001");
				String userName	 		= rs.getString("c630000003");
				if(rs.getString("c630000015")== null)
				{
					dpid = "";
				}else
				{
					dpid = rs.getString("c630000015");
				}
				String cpName  			= rs.getString("cpname");
				String dpName  			= rs.getString("dpname");
				if(dpid.equals(""))
				{
					dpName = "";
				}
				UserInfo userInfo 		= (UserInfo)hm.get(userLoginName);
				System.out.println(userInfo);
				if(userInfo!=null)
				{
					connectionTime = Function.Unixto_datetime(String.valueOf(userInfo.getConnectionTime())+"000");
					lastAccessTime = Function.Unixto_datetime(String.valueOf(userInfo.getLastAccessTime())+"000");
				}
				ActiveUserInfo activeUserBean = new ActiveUserInfo();
				activeUserBean.setConnectionTime(connectionTime);
				activeUserBean.setCPName(cpName);
				activeUserBean.setDPName(dpName);
				activeUserBean.setLastAccessTime(lastAccessTime);
				activeUserBean.setUserLoginName(userLoginName);
				activeUserBean.setUserName(userName);
				System.out.println(userLoginName+","+userName+","+cpName+","+dpName);
	 	    	list.add(activeUserBean);
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		
		return list;
	}
	
	public static void main(String args[])
	{
		GetActiveUserSql getsql = new GetActiveUserSql();
		getsql.getActiveUserInfo(null,"'Demo','wangxuelei',''");
	}
	
}
