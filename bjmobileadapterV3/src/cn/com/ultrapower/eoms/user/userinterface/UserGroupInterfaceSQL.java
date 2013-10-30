package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;


public class UserGroupInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(UserGroupInterfaceSQL.class);
	
	private String usertable;
	private String grouptable;
	private String groupusertable;
	
	public UserGroupInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertable		= getTableProperty.GetFormName("RemedyTpeople");
			grouptable		= getTableProperty.GetFormName("RemedyTgroup");
			groupusertable  = getTableProperty.GetFormName("RemedyTgroupuser");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	
	/**
	 * 获得组顶级节点List的SQL.
	 * 日期 2007-4-16
	 * @author wangyanguang
	 * @param userCPID       用户单位ID
	 * @return List
	 */
	public List getRootGroupListSQL(String userLoginName)
	{
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		if(!userLoginName.equals(""))
		{	
			sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000022");
			sql.append(" from "+ grouptable+" grouptable");		
			sql.append(","+ usertable + " usertable");
			sql.append(" where usertable.c630000013=grouptable.c1");
			sql.append(" and usertable.c630000001='"+userLoginName+"'");
		}else
		{
			sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000022");
			sql.append(" from "+ grouptable+" grouptable");
			sql.append(" where grouptable.c630000020='0'");
		}
		
		sql.append(" order by grouptable.c630000022");
		
		System.out.println("UserGroupInterface取得顶级公司节点的SQL："+sql.toString());
		logger.info("UserGroupInterface取得顶级公司节点的SQL："+sql.toString());
	
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String groupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	
	 	    	logger.info("组INT_ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		//elementinfo.setRecordid(recordid);
	 	    		returnList.add(elementinfo);
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
	
	/**
	 * 获得子组节点List的SQL.
	 * 日期 2007-4-16
	 * @author wangyanguang
	 * @param groupParentID
	 * @return List
	 */
	public List getGroupListSQL(String groupParentID)
	{

		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000022");
		sql.append(" from "+ grouptable+" grouptable,"+grouptable+" grouptable2");
		sql.append(" where grouptable.c630000020=grouptable2.c1");
		sql.append(" and grouptable2.c630000030='"+groupParentID+"'");
		
		sql.append(" order by grouptable.c630000022");
		
		System.out.println("UserGroupInterface取得子公司节点的SQL："+sql.toString());
		logger.info("UserGroupInterface取得子公司节点的SQL："+sql.toString());
	
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String groupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	
	 	    	logger.info("组INT_ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		//elementinfo.setRecordid(recordid);
	 	    		returnList.add(elementinfo);
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
	
	/**
	 * 获得用户节点List的SQL.
	 * 日期 2007-4-16
	 * @author wangyanguang
	 * @param groupID
	 * @return List
	 */
	public List getUserListSQL(String groupID)
	{
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		sql.append("select distinct usertable.c1,usertable.c630000001,usertable.c630000003,usertable.c630000017");
		sql.append(" from "+groupusertable+" groupusertable,"+ usertable + " usertable, "+grouptable+" grouptable");
		sql.append(" where groupusertable.c620000027 =grouptable.c1");
		sql.append(" and   grouptable.c630000030='"+groupID+"'");
		sql.append(" and   usertable.c630000012='0'");
		sql.append(" and   groupusertable.c620000028 = usertable.c1");
		
		sql.append(" order by c630000017");
		
		System.out.println("UserGroupInterface取得用户节点的SQL："+sql.toString());
		logger.info("UserGroupInterface取得用户节点的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String id			= Function.nullString(rs.getString("c1"));
	 	    	String userid     	= Function.nullString(rs.getString("c630000001"));
	 	    	String username1	= Function.nullString(rs.getString("c630000003"));
	 	    	logger.info("用户ID："+userid+",用户名称："+username1);
	 	    	
	 	    	if(!userid.equals("")&&!username1.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("1");
	 	    		elementinfo.setElementid(userid);
	 	    		elementinfo.setElementname(username1);
	 	    		elementinfo.setUserid(id);
	 	    		returnList.add(elementinfo);
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
}
