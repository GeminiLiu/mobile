package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage.UserCommisionFind;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class SyncSQL
{
	static final Logger logger = (Logger) Logger.getLogger(SyncSQL.class);

	String sourcemanager			= "";
	String systemmanage				= "";
	String sourceconfig				= "";
	String dutyorgnazition			= "";
	String orgnazitionarranger		= "";
	String usertablename			= "";
	String grouptablename			= "";
	String groupusertablename		= "";
	String sysskill					= "";
	String uid						= "";
	String skillaction				= "";
	String usergrouprel				= "";
	String rolemanagetable			= ""; 
	
	/**
	 * 取得配制信息，读取配制文件
	 */
	public SyncSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager		= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage		= getTableProperty.GetFormName("systemmanage");
			sourceconfig		= getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition		= getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger	= getTableProperty.GetFormName("orgnazitionarranger");
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill			= getTableProperty.GetFormName("RemedyTrole");
			skillaction			= getTableProperty.GetFormName("managergrandaction");
			usergrouprel		= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			rolemanagetable		= getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	
	/**
	 * 根据组ID查询组成员信息。(用户ID)
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getUserID(String groupid)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select groupuser.c620000028 from "+groupusertablename+" groupuser");
		sql.append(" where groupuser.c620000027='"+groupid+"'");
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String userid     	= rs.getString("C620000028");
	 	    	returnList.add(userid);
			}
		
			rs.close();
			stm.close();
			dataBase.closeConn();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
		
	}
	
	
	
	/**
	 * 根据用户ID、组ID查询用户、组与权限关联表，查询出所有满足条件的角色ID。
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param groupid		组ID
	 */
	public List getRoleid(String groupid)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select distinct usergrouppo.c660000028 from "+usergrouprel+" usergrouppo where ");
		sql.append(" (usergrouppo.c660000027="+groupid+")");
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String roleid     	= Function.nullString(rs.getString("c660000028"));
	 	    	if(!roleid.equals(""))
	 	    	{
	 	    		returnList.add(roleid);
	 	    	}
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
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	
	public static void main(String args[])
	{
		SyncSQL syncsql = new SyncSQL();
		List list = syncsql.getRoleid("000000000600003");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			String roleid = (String)it.next();
			System.out.println(roleid);
		}
		System.out.println("OK!");
	}
}
