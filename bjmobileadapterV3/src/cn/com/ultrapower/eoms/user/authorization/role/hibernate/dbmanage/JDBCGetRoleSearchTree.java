package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

public class JDBCGetRoleSearchTree {

	static final Logger logger 		= (Logger) Logger.getLogger(JDBCGetRoleSearchTree.class);
	
	public String nodefind(String tuser)
	{
		//从配置文件中取表名
		GetFormTableName getTableProperty	= new GetFormTableName();
		IDataBase dataBase					= null;
		Statement stm						= null;
		ResultSet rs						= null;
		String tablename					= "";
		String sourcemanager				= "";
		String systemmanage					= "";
		String sourceconfig					= "";
		try
		{
			tablename						= getTableProperty.GetFormName("RemedyTgroup");
			sourcemanager					= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage					= getTableProperty.GetFormName("systemmanage");
			sourceconfig					= getTableProperty.GetFormName("sourceconfig");
		}
		catch(Exception e)
		{
			logger.info("256 JDBCGetRoleSearchTree 类中 nodefind(String tuser) 方法调用GetTableProperty时出现异常！"+e.getMessage());
		}
		
		//公共的js树
		StringBuffer treeinfo				= new StringBuffer();
		//当前用户不为Demo时的又一个js树
		StringBuffer treeinfo1				= new StringBuffer();
		//公共的查询条件
		StringBuffer sql					= new StringBuffer();
		//当前用户不为Demo时的又一个查询条件
		StringBuffer sql1					= new StringBuffer();
		StringBuffer sql2 					= new StringBuffer();
		try
		{
			//当前用户为Demo时
			if(tuser.equals("Demo"))
			{
				sql.append("select C1,C630000018,C630000020 from "+tablename +" where C630000025='0' order by C630000020");
				//"no"状态不进行sql1的子条件查询
				sql1.append("no");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId						= getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		sql2.append("select a.C1 from "+tablename+" a,"+sourcemanager+" b");
				sql2.append(" where a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3' and not exists");
				sql2.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003 and c.C650000005 = '3')");
				sql2.append(" order by a.C630000020");
		  		
				String strNotIn = getNotInStr(sql2.toString());
		  		
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.C1,a.C630000018,a.C630000020 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0'");
				sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3'");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
				sql.append(" and a.C1 not in ("+strNotIn+")");
				
				sql1.append("select a.C1,a.C630000018,a.C630000019,a.C630000037 from "+tablename+" a,"+sourcemanager+" b");
				sql1.append(" where a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3' and not exists");
				sql1.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003 and c.C650000005 = '3')");
			}
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stm		= dataBase.GetStatement();
			rs		= dataBase.executeResultSet(stm,sql.toString());
			
			String groupParentID = "";
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	String imgflag		 = "no";
 	    	System.out.println("sql::"+sql);
 	    	//当sql1为"no"状态时即当前用户不为Demo时
			if(!sql1.toString().equals("no"))
			{
				ResultSet rs1 = dataBase.executeResultSet(stm,sql1.toString());		
				while(rs1.next())
				{
		 	    	groupName     	= rs1.getString("C630000018");
		 	    	groupID       	= rs1.getString("C1");
		 	    	
		 	    	//调用js脚本生成树,将所有js生成树节点的父组ID置为0
		 	    	if(imgflag.equals("no"))
		 	    	{
		 	    		//treeinfo1.append("d.add("+groupID+",0,\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");
		 	    		treeinfo1.append("d.add("+groupID+",0,\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");
		 	    		imgflag		= "yes";
		 	    	}
		 	    	else
		 	    	{
		 	    		//treeinfo1.append("d.add("+groupID+",0,\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
		 	    		treeinfo1.append("d.add("+groupID+",0,\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
		 	    	}
				}
				//关闭rs1链接
				rs1.close();
			}
			
			while(rs.next())
			{
	 	    	groupParentID 	= rs.getString("C630000020");
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	//调用js脚本生成树
	 	    	if(groupParentID.equals("0")&&imgflag.equals("no"))
	 	    	{
		 	    	//treeinfo.append("d.add("+groupID+","+groupParentID+",\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");	
		 	    	treeinfo.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");
		 	    	imgflag		= "yes";
	 	    	}
	 	    	else
	 	    	{
	 	    		//treeinfo.append("d.add("+groupID+","+groupParentID+",\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
	 	    		treeinfo.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
	 	    	}
			}
			//关闭连接，释放资源
			rs.close();
			stm.close();
			dataBase.closeConn();
			
			return treeinfo.toString()+treeinfo1.toString();
		}
		catch(Exception e)
		{
			logger.error("257 JDBCGetRoleSearchTree 类中 nodefind(String tuser) 方法调用执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
			
	public String userInfo(String user)
	{
		IDataBase dataBase				= null;
		Statement stm					= null;
		ResultSet rs 					= null;
		StringBuffer treeinfo 			= new StringBuffer();
		String usertablename 			= "";
		String grouptablename 			= "";
		String groupusertablename 		= "";
		String tuserId					= "";
		String tablename				= "";
		String sourcemanager			= "";
		String systemmanage				= "";
		String sourceconfig				= "";
		GetUserInfoList getUserInfoList	= new GetUserInfoList();
		SysPeoplepo peoplepo 			= getUserInfoList.getUserInfoName(user);
		if(peoplepo!=null)
		{
			tuserId		= peoplepo.getC1();
		}
		GetFormTableName getTableProperty = new GetFormTableName();
		try
		{
			usertablename 			= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename 			= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename 		= getTableProperty.GetFormName("RemedyTgroupuser");
			tablename				= getTableProperty.GetFormName("RemedyTgroup");
			sourcemanager			= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage			= getTableProperty.GetFormName("systemmanage");
			sourceconfig			= getTableProperty.GetFormName("sourceconfig");
		}
		catch(Exception e)
		{
			logger.info("258 JDBCGetRoleSearchTree 类中 userInfo(String user) 方法调用GetTableProperty时出现异常！"+e.getMessage());
		}
		
		try
		{
			if (!String.valueOf(usertablename).equals("")
					&& !String.valueOf(usertablename).equals("null")
					&& !String.valueOf(grouptablename).equals("")
					&& !String.valueOf(grouptablename).equals("null")
					&& !String.valueOf(groupusertablename).equals("")
					&& !String.valueOf(groupusertablename).equals("null")) {
				String selecttable = groupusertablename + " tgroupuser,"
						+ grouptablename + " tgroup," + usertablename + " tuser";
				String sql = "select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid from "
						+ selecttable
						+ " where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0' ";

				if (String.valueOf(user).equals("Demo"))
				{
					sql = sql + " and exists (select C630000020 from "+grouptablename +" where C630000025='0')";
				}
				else
				{
					sql = sql + " and exists(select a.C1,a.C630000018,a.C630000020 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0' and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3' and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"')";
				}
				// 实例化一个类型为接口IDataBase类型的工厂类
				dataBase = DataBaseFactory
						.createDataBaseClassFromProp();
				// 获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs 		= dataBase.executeResultSet(stm,sql);
				String userid 		= "";
				String groupid 		= "";
				String userfullname = "";
				int i = 0;
				while (rs.next()) 
				{
					userid = rs.getString("userid");
					groupid = rs.getString("groupid");
					userfullname = rs.getString("userfullname");
					
					if(i==0)
					{
						//treeinfo =treeinfo+ "d.add("+userid+","+groupid+",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'1');";
						treeinfo.append("d.add("+userid+","+groupid+",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'1');");
					}else
					{
						//treeinfo =treeinfo+ "d.add("+userid+","+groupid+",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'0');";
						treeinfo.append("d.add("+userid+","+groupid+",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main',\"\",\"\",\"\",'0');");
					}
					i++;
				}
//				System.out.println(treeinfo.toString());
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
		catch (Exception e) 
		{
			logger.info("259 JDBCGetRoleSearchTree 类中 userInfo(String user) 方法执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	
	}

	/**
	 * <p>Description:根据条件拼得以","分隔的not in结果集<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-7
	 * @param sql			要求查得的rs结果集只有C1字段
	 * @return String
	 */
	public String getNotInStr(String sql)
	{
		//拼字符串的变量
		StringBuffer str	= new StringBuffer();
		IDataBase dataBase 	= null;
		Statement stm 		= null;
		ResultSet rs 		= null;
		try
		{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			//获得数据库查询结果集
			stm		= dataBase.GetStatement();
			rs		= dataBase.executeResultSet(stm,sql);
			while(rs.next())
			{
				String C1	= rs.getString("C1");
				if(!str.toString().equals(""))
				{
					str.append(",");
				}
				str.append("'"+C1+"'");
			}
			//关闭数据库连接
			rs.close();
			stm.close();
			dataBase.closeConn();
			
			return str.toString();
		}
		catch(Exception e)
		{
			logger.error("260  JDBCGetRoleSearchTree 类中 getNotInStr(String sql) 方法执行查询时出现异常！"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
}
