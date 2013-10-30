package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.GroupUserTree;

public class JDBCGetRoleContactTree {

	static final Logger logger 		= (Logger) Logger.getLogger(JDBCGetRoleContactTree.class);
	
	/**
	 * 日期 2006-11-27
	 * 
	 * @author wangyanguang/王彦广 
	 * @param args void
	 *
	 */
	public static void main(String[] args) 
	{
		GroupUserTree groupUserTree=new GroupUserTree();
		groupUserTree.getgrouptree("");
		groupUserTree.getusertree("");
	}
	//获得组信息tree字符串
	public String getgrouptree(String strwhere)
	{
		IDataBase dataBase					= null;
		Statement stm 						= null;
		ResultSet rs 						= null;
		StringBuffer treeinfo				= new StringBuffer();
		String tablename					= "";
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			tablename							= getTableProperty.GetFormName("RemedyTgroup");
		}
		catch(Exception e)
		{
			logger.info("253 JDBCGetRoleContactTree 类中 getgrouptree(String strwhere) 方法调用GetTableProperty时出现异常！"+e.getMessage());
		}
		try
		{
			if(!String.valueOf(tablename).equals("")&&!String.valueOf(tablename).equals("null"))
			{
				String sql="select * from "+tablename +" where 1=1 and C630000025='0'";
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql=sql+" and "+strwhere +" order by C630000020";
				}
				System.out.println(sql);
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				//获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs		= dataBase.executeResultSet(stm,sql);
				String groupParentID = "";
	 	    	String groupName     = "";
	 	    	String groupID       = "";
	 	    	String groupDnId	 = "";
	 	    	String groupFullName = "";
				int i = 0;
	 	    	while(rs.next())
				{
		 	    	groupParentID 	= rs.getString("C630000020");
		 	    	groupName     	= rs.getString("C630000018");
		 	    	groupID       	= rs.getString("C1");
		 	    	groupDnId	 	= rs.getString("C630000037");
		 	    	groupFullName 	= rs.getString("C630000019");
		 	    	if(i==0)
		 	    	{
		 	    		//treeinfo=treeinfo+"d.add(\""+groupID+"\",\""+groupParentID+"\",\""+groupName+"\",\"\",\"\",\"\",\"\",\"\",\"\",'1');";
		 	    		treeinfo.append("d.add(\""+groupID+"\",\""+groupParentID+"\",\""+groupName+"\",\"\",\"\",\"\",\"\",\"\",\"\",'1');");
		 	    	}else
		 	    	{
		 	    		//treeinfo=treeinfo+"d.add(\""+groupID+"\",\""+groupParentID+"\",\""+groupName+"\",\"\",\"\",\"\",\"\",\"\",\"\",'0');";
		 	    		treeinfo.append("d.add(\""+groupID+"\",\""+groupParentID+"\",\""+groupName+"\",\"\",\"\",\"\",\"\",\"\",\"\",'0');");
		 	    	}
		 	    	i++;
				}
				System.out.println(treeinfo);
				rs.close();
				stm.close();
				dataBase.closeConn();
				return treeinfo.toString();
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.info("255 JDBCGetRoleContactTree 类中 getgrouptree(String strwhere) 执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public String getusertree(String strwhere)
	{
		IDataBase dataBase					= null;
		Statement stm						= null;
		ResultSet rs 						= null;
		StringBuffer treeinfo				= new StringBuffer();
		String usertablename				= "";
		String grouptablename				= "";
		String groupusertablename			= "";
		GetFormTableName getTableProperty	= new GetFormTableName();
		usertablename						= getTableProperty.GetFormName("RemedyTpeople");
		grouptablename						= getTableProperty.GetFormName("RemedyTgroup");
		groupusertablename					= getTableProperty.GetFormName("RemedyTgroupuser");
		try
		{
			if(!String.valueOf(usertablename).equals("")&&!String.valueOf(usertablename).equals("null")&&!String.valueOf(grouptablename).equals("")&&!String.valueOf(grouptablename).equals("null")&&!String.valueOf(groupusertablename).equals("")&&!String.valueOf(groupusertablename).equals("null"))
			{
				String selecttable=groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
				String sql="select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0' and tgroup.C630000025='0'";
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql=sql+" and "+strwhere;
				}
				System.out.println(sql);
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				//获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs		= dataBase.executeResultSet(stm,sql);
				String userid		= "";
				String groupid		= "";
				String userfullname	= "";
				int i = 0;
				while(rs.next())
				{
					userid			= rs.getString("userid");
					groupid			= rs.getString("groupid");
					userfullname	= rs.getString("userfullname");
					if(i==0)
					{
						//treeinfo =treeinfo+ "d.add(\""+userid+"\",\""+groupid+"\",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'1');";
						treeinfo.append("d.add(\""+userid+"\",\""+groupid+"\",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'1');");
					}else
					{
						//treeinfo =treeinfo+ "d.add(\""+userid+"\",\""+groupid+"\",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'0');";
						treeinfo.append("d.add(\""+userid+"\",\""+groupid+"\",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'0');");
						
					}
					i++;
				}
				System.out.println(treeinfo);
				rs.close();
				stm.close();
				dataBase.closeConn();
				return treeinfo.toString();
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.info("256 JDBCGetRoleContactTree 类中 getusertree(String strwhere) 执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}


}
