package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

public class GroupUserTree 
{
	static final Logger logger = (Logger) Logger.getLogger(GroupUserTree.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GroupUserTree groupUserTree=new GroupUserTree();
		groupUserTree.getgrouptree("");
		groupUserTree.getusertree("");
	}
	//获得组信息tree字符串
	public String getgrouptree(String strwhere)
	{
		String treeinfo						="";
		String tablename					="";
		IDataBase dataBase = null;
		Statement stm      = null;
		ResultSet rs	   = null;
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			tablename							= getTableProperty.GetFormName("RemedyTgroup");
		}
		catch(Exception e)
		{
			logger.error("读配置表出现异常！");
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
				logger.info("sql:"+sql);
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
				while(rs.next())
				{
		 	    	groupParentID 	= rs.getString("C630000020");
		 	    	groupName     	= rs.getString("C630000018");
		 	    	groupID       	= rs.getString("C1");
		 	    	groupDnId	 	= rs.getString("C630000037");
		 	    	groupFullName 	= rs.getString("C630000019");
		 	    	treeinfo=treeinfo+"d.add("+groupID+","+groupParentID+",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupFullName+"','"+groupDnId+"');>"+groupName+"</font>\",\"\",\"\",\"main\");\n";
				}
				//System.out.println(treeinfo);
				rs.close();
				stm.close();
				dataBase.closeConn();
				return treeinfo;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public String getusertree(String strwhere)
	{
		String treeinfo						= "";
		String usertablename				= "";
		String grouptablename				= "";
		String groupusertablename			= "";
		IDataBase dataBase = null;
		Statement stm	 = null;
		ResultSet rs	 = null;
		
		GetFormTableName getTableProperty	= new GetFormTableName();
		usertablename						= getTableProperty.GetFormName("RemedyTpeople");
		grouptablename						= getTableProperty.GetFormName("RemedyTgroup");
		groupusertablename					= getTableProperty.GetFormName("RemedyTgroupuser");
		try
		{
			if(!String.valueOf(usertablename).equals("")&&!String.valueOf(usertablename).equals("null")&&!String.valueOf(grouptablename).equals("")&&!String.valueOf(grouptablename).equals("null")&&!String.valueOf(groupusertablename).equals("")&&!String.valueOf(groupusertablename).equals("null"))
			{
				String selecttable=groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
				String sql="select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0'";
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql=sql+" and "+strwhere;
				}
				logger.info("sql:"+sql);
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				//获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs		= dataBase.executeResultSet(stm,sql);
				String userid		= "";
				String groupid		= "";
				String userfullname	= "";
				while(rs.next())
				{
					userid			= rs.getString("userid");
					groupid			= rs.getString("groupid");
					userfullname	= rs.getString("userfullname");
					treeinfo =treeinfo+ "d.add("+userid+","+groupid+",\"<font onclick=back_time('"+userfullname+"','"+userid+"','"+groupid+"');>"+userfullname+"</font>\",'','','main');\n";
				}
				//System.out.println(treeinfo);
				rs.close();
				stm.close();
				dataBase.closeConn();
				return treeinfo;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			e.getMessage();
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public String nodefind(String tuser)
	{
		//从配置文件中取表名
		GetFormTableName getTableProperty	= new GetFormTableName();
		String tablename					= getTableProperty.GetFormName("RemedyTgroup");
		String sourcemanager				= getTableProperty.GetFormName("RemedyTsourceManager");
		String systemmanage					= getTableProperty.GetFormName("systemmanage");
		String sourceconfig					= getTableProperty.GetFormName("sourceconfig");
		IDataBase dataBase = null;
		Statement stm	 = null;
		ResultSet rs	 = null;
		//公共的js树
		StringBuffer treeinfo	= new StringBuffer();
		//当前用户不为Demo时的又一个js树
		StringBuffer treeinfo1	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//当前用户不为Demo时的又一个查询条件
		StringBuffer sql1		= new StringBuffer();
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
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.C1,a.C630000018,a.C630000020 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0'");
				sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3'");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"' and a.c630000021=2 order by a.C630000020");
			
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
 	    	logger.info("sql::"+sql);
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
		 	    	//	treeinfo1.append("d.add("+groupID+",0,\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");
		 	    		
		 	    		treeinfo1.append("d.add("+groupID+",0,\"<font onclick=back_time('"+groupName+"','"+groupID+"','1');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");

		 	    		imgflag		= "yes";
		 	    	}
		 	    	else
		 	    	{
		 	    		
		 	    		//treeinfo1.append("d.add("+groupID+",0,\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
		 	    		//treeinfo1.append("d.add("+groupID+",0,\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
		 	    		treeinfo1.append("d.add("+groupID+",0,\"<font onclick=back_time('"+groupName+"','"+groupID+"','1');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");

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
		 	    	//treeinfo.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",1);\n");
		 	    	//treeinfo1.append("d.add("+groupID+",0,\"<font onclick=back_time('"+groupName+"','"+groupID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");
		 	    	
		 	    	treeinfo1.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"<font onclick=back_time('"+groupName+"','"+groupID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");

		 	    	imgflag		= "yes";
	 	    	}
	 	    	else
	 	    	{
	 	    		//treeinfo.append("d.add("+groupID+","+groupParentID+",\"<a href='FindPeopleDao?id="+groupID+"' target='mainFrame'>"+groupName+"</a>\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
	 	    		//treeinfo.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"\",0);\n");
	 	    		//treeinfo1.append("d.add("+groupID+",0,\"<font onclick=back_time('"+groupName+"','"+groupID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");
	 	    		treeinfo1.append("d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"<font onclick=back_time('"+groupName+"','"+groupID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");");

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
			//logger.error("[411]GroupFind.findModify() 组成员模块生成树失败"+e.getMessage());
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	public String userInfo(String user)
	{

		String treeinfo 				= "";
		String usertablename 			= "";
		String grouptablename 			= "";
		String groupusertablename 		= "";
		String tuserId					= "";
		IDataBase dataBase = null;
		Statement stm = null;
		ResultSet rs = null;
		GetUserInfoList getUserInfoList	= new GetUserInfoList();
		SysPeoplepo peoplepo 			= getUserInfoList.getUserInfoName(user);
		if(peoplepo!=null)
		{
			tuserId		= peoplepo.getC1();
		}
		GetFormTableName getTableProperty = new GetFormTableName();
		usertablename 			= getTableProperty.GetFormName("RemedyTpeople");
		grouptablename 			= getTableProperty.GetFormName("RemedyTgroup");
		groupusertablename 		= getTableProperty.GetFormName("RemedyTgroupuser");
		String tablename		= getTableProperty.GetFormName("RemedyTgroup");
		String sourcemanager	= getTableProperty.GetFormName("RemedyTsourceManager");
		String systemmanage		= getTableProperty.GetFormName("systemmanage");
		String sourceconfig		= getTableProperty.GetFormName("sourceconfig");
		try {
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
				}else
				{
					sql = sql + " and exists(select a.C1,a.C630000018,a.C630000020 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0' and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3' and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"')";
				}
				logger.info("sql:"+sql);
				// 实例化一个类型为接口IDataBase类型的工厂类
				dataBase = DataBaseFactory.createDataBaseClassFromProp();
				// 获得数据库查询结果集
				stm		= dataBase.GetStatement();
				rs 		= dataBase.executeResultSet(stm,
						sql);
				String userid = "";
				String groupid = "";
				String userfullname = "";
				int i = 0;
				while (rs.next()) 
				{
					userid = rs.getString("userid");
					groupid = rs.getString("groupid");
					userfullname = rs.getString("userfullname");
					
					if(i==0)
					{
						treeinfo = treeinfo + "d.add(" + userid + "," + groupid	+ ",\"" + userfullname
						+ "<font onclick=back_time('"+userfullname+"','"+groupid+"');>\",'','','main',\"\",\"\",\"\",'1');";

					}else
					{
						treeinfo = treeinfo + "d.add(" + userid + "," + groupid	+ ",\"" + userfullname
						+ "<font onclick=back_time('"+userfullname+"','"+groupid+"');>\",'','','main',\"\",\"\",\"\",'1');";

					}
					i++;
				}
				logger.info("treeinfo:"+treeinfo);
				rs.close();
				stm.close();
				dataBase.closeConn();
				return treeinfo;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.getMessage();
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
}
