package cn.com.ultrapower.eoms.user.config.sourcemanager.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

public class LoadSourceUserTree {

	static final Logger logger 			= (Logger) Logger.getLogger(LoadSourceUserTree.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//组信息表
	private String grouptablename		= getFormTableName.GetFormName("RemedyTgroup");
	//资源管理者表
	private String sourcemanager		= getFormTableName.GetFormName("RemedyTsourceManager");
	//管理者类型为资源管理者
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	//资源配置表
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	//用户信息表
	private String usertablename		= getFormTableName.GetFormName("RemedyTpeople");
	//组成员信息表
	private String groupusertablename	= getFormTableName.GetFormName("RemedyTgroupuser");
	//组信息表
	private String rgroup				= getFormTableName.GetFormName("RemedyGroup");
	//用户信息表
	private String ruser				= getFormTableName.GetFormName("RemedyUser");
	//流程子角色表
	private String rworkflowchildrole	= getFormTableName.GetFormName("RemedyTworkFlowChildRole");

	/**
	 * <p>Description:根据管理者登陆名显示其所管理公司下所有节点生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-5
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	public String getGroupTree(String strwhere,String tuser,String id,String type)
	{
		//公共js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020='"+id+"'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000020");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId						= getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		//拼not in子查询的sql，要求其查询结果集为单个字段"a.C1"
		  		StringBuffer sql2 = new StringBuffer();
				sql2.append("select distinct a.C1 from "+grouptablename+" a,"+sourcemanager+" b,"+usertablename+" e,"+sourceconfig+" f");
				sql2.append(" where e.C1 = '"+tuserId+"' and (a.C1 = b.C650000003 or a.C1 = e.C630000013) and b.C650000007 = '"+tuserId+"' and not exists");
				sql2.append(" (select distinct c.C650000003 from "+sourcemanager+" c,"+usertablename+" d where c.C650000007 = '"+tuserId+"' and d.C1 = '"+tuserId+"' and (a.C630000020 = c.C650000003 and c.C650000003 = d.C630000013))");
				sql2.append(" and f.source_name = 'systemmanage' and f.source_id = b.C650000001");
				JDBCSourceUserTree sourceUserTree = new JDBCSourceUserTree();
				//查得not in结果集
				String strNotIn = sourceUserTree.getNotInStr(sql2.toString());
		  		
				//根据配置文件中的表名和传入的参数确定sql语句
		  		sql.append("select a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c");
				sql.append(" where a.C630000025='0' and a.C630000020='"+id+"'");
				sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3'");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
				//使用not in结果集作为where条件
				sql.append(" and a.C1 not in ("+strNotIn+")");
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000020");
			}
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
 	    	
 	    	if(type.equals("8"))
 	    	{
 	    		while(rs.next())
 				{
 		 	    	groupName     	= rs.getString("C630000018");
 		 	    	groupID       	= rs.getString("C1");

 		 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";8\" funpram=\""+groupName+":null:"+groupID+"\"/>");
 				}
 	    	}
 	    	else if(type.equals("10"))
 	    	{
 	    		while(rs.next())
 				{
 		 	    	groupName     	= rs.getString("C630000018");
 		 	    	groupID       	= rs.getString("C1");

 		 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";10\" funpram=\""+groupName+":null:"+groupID+"\"/>");
 				}
 	    	}
			
 	    	
		}
		catch(Exception e)
		{
			logger.error("[485]JDBCSourceUserTree.getGroupTree() 根据管理者登陆名显示其所管理公司下所有节点生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:当是主班人情况下角色类型生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-8
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	public String getDutyGroupTree(String tuser,String id)
	{
		//公共js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000021 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
				sql.append(" order by a.C630000020");
			}
			else
			{
				//根据配置文件中的表名和传入的参数确定sql语句
		  		sql.append("select a.C1,a.C630000018,a.C630000021,a.C630000020 from "+grouptablename+" a");
				sql.append(" where a.C630000025='0' and a.C630000020 = '"+id+"'");
				sql.append(" and a.C630000021 = '4'");
				sql.append(" order by a.C630000020");
			}
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
 	    	String groupType		= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupType		= rs.getString("C630000021");

 	    		if(groupType.equals("4"))
 	    		{
 	    			treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";4\" funpram=\""+groupName+":null:"+groupID+"\"/>");
 	    		}
 	    		else
 	    		{
 	    			treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";4\"/>");
 	    		}	
			}
		}
		catch(Exception e)
		{
			logger.error("[486]JDBCSourceUserTree.getDutyGroupTree() 当是主班人情况下角色类型生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:根据传入的管理者登陆名,显示该用户所管理公司以及他所选公司下的所有组信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-7
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	public String getUserOfGroupTree(String strwhere,String tuser,String id)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{	
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000020");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList	= new GetUserInfoList();
		  		String tuserId					= getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		//拼not in子查询的sql，要求其查询结果集为单个字段"a.C1"
		  		StringBuffer sql2 = new StringBuffer();
				sql2.append("select distinct a.C1 from "+grouptablename+" a,"+sourcemanager+" b,"+usertablename+" e,"+sourceconfig+" f");
				sql2.append(" where e.C1 = '"+tuserId+"' and (a.C1 = b.C650000003 or a.C1 = e.C630000013) and b.C650000007 = '"+tuserId+"' and not exists");
				sql2.append(" (select distinct c.C650000003 from "+sourcemanager+" c,"+usertablename+" d where c.C650000007 = '"+tuserId+"' and d.C1 = '"+tuserId+"' and (a.C630000020 = c.C650000003 and c.C650000003 = d.C630000013))");
				sql2.append(" and f.source_name = '"+systemmanage+"' and f.source_id = b.C650000001");
				
				JDBCSourceUserTree sourceUserTree = new JDBCSourceUserTree();
				//查得not in结果集
				String strNotIn = sourceUserTree.getNotInStr(sql2.toString());

				//根据配置文件中的表名和传入的参数确定sql语句
		  		sql.append("select distinct a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c,"+usertablename+" d");
				sql.append(" where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
				sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003 or a.C1 = d.C630000013 or a.C630000026 = d.C630000013)");
				sql.append(" and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3'");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
				sql.append(" and d.C1 = '"+tuserId+"'");
				//使用not in结果集作为where条件
				sql.append(" and a.C1 not in ("+strNotIn+")");
				
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000020");
			}
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";5\" funpram=\""+groupName+":null:"+groupID+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[487]JDBCSourceUserTree.getUserOfGroupTree() 根据传入的管理者登陆名,显示该用户所管理公司以及他所选公司下的所有组信息失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:生成组信息生成树协助用户信息生成树,点击节点没有返回事件<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-7
	 * @param strwhere
	 * @param tuser
	 * @param type		6为资源模块组信息生成树,11为虚拟组成员模块生成树
	 * @return String
	 */
	public String getGroupForUserTree(String strwhere,String tuser,String id,String type)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a where a.C630000025='0' and a.C630000020='"+id+"'");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId						= getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		//拼not in子查询的sql，要求其查询结果集为单个字段"a.C1"
		  		StringBuffer sql2 = new StringBuffer();
				sql2.append("select distinct a.C1 from "+grouptablename+" a,"+sourcemanager+" b,"+usertablename+" e,"+sourceconfig+" f");
				sql2.append(" where e.C1 = '"+tuserId+"' and (a.C1 = b.C650000003 or a.C1 = e.C630000013) and b.C650000007 = '"+tuserId+"' and not exists");
				sql2.append(" (select distinct c.C650000003 from "+sourcemanager+" c,"+usertablename+" d where c.C650000007 = '"+tuserId+"' and d.C1 = '"+tuserId+"' and (a.C630000020 = c.C650000003 and c.C650000003 = d.C630000013))");
				sql2.append(" and f.source_name = 'systemmanage' and f.source_id = b.C650000001");
				JDBCSourceUserTree sourceUserTree = new JDBCSourceUserTree();
				//查得not in结果集
				String strNotIn = sourceUserTree.getNotInStr(sql2.toString());
		  		
				//根据配置文件中的表名和传入的参数确定sql语句
		  		sql.append("select a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c");
				sql.append(" where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
				sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+tuserId+"' and b.C650000005 = '3'");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
				//使用not in结果集作为where条件
				sql.append(" and a.C1 not in ("+strNotIn+")");
			}
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			if(type.equals("11"))
			{
				sql.append(" and (a.C630000021 = '2' or a.C630000021 = '3')");
			}
			sql.append(" order by a.C630000020");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
 	    	
 	    	//资源模块组信息生成树
 	    	if(type.equals("6"))
 	    	{
	 	    	while(rs.next())
				{
	 	    		
		 	    	groupName     	= rs.getString("C630000018");
		 	    	groupID       	= rs.getString("C1");
		 	    	
		 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";6\"/>");
				}
 	    	}
 	    	//虚拟组成员
 	    	else
 	    	{
 	    		while(rs.next())
				{
	 	    		
		 	    	groupName     	= rs.getString("C630000018");
		 	    	groupID       	= rs.getString("C1");
		 	    	
		 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";11\"/>");
				}
 	    	}
		}
		catch(Exception e)
		{
			logger.error("[490]LoadSourceUserTree.getGroupForUserTree() 生成组信息生成树协助用户信息生成树,点击节点没有返回事件失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:根据传入的管理者登陆名,显示该用户所管理公司以及他所选公司下的所有组信息顶层<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-4
	 * @param strwhere
	 * @param group_id
	 * @return String
	 */
	public String getGroupTreeByParam(String strwhere,String group_id)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{	
			//根据配置文件中的表名和传入的参数确定sql语句
			sql.append("select a.C1,a.C630000018 from "+grouptablename+" a");
			sql.append(" where a.C630000020 = '"+group_id+"'");
			sql.append(" and a.C630000025='0'");
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by a.C630000020");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");

	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";7\" funpram=\""+groupName+":null:"+groupID+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("[488]LoadSourceUserTree.getGroupTreeByParam() 显示根据角色信息生成树的返回值,即该用户所选组的公司下的所有节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:生成用户信息树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-5
	 * @param strwhere
	 * @param group_id
	 * @param type			radio为单选,check为复选
	 * @return String
	 */
	public String getUserTree(String strwhere,String group_id,String type)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//如果为单选
			if(type.equals("radio"))
			{
				sql.append("select a.C1 userId, b.C620000027 groupId, a.C630000003 userLoginName");
				sql.append(" from "+usertablename+" a,"+groupusertablename+" b");
				sql.append(" where a.C630000012 = '0'");
				sql.append(" and b.C620000027 = '"+group_id+"' and b.C620000028 = a.C1");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
			}
			//如果为复选
			else
			{
				GetGroupInfoList getGroupInfoList	= new GetGroupInfoList();
				String groupType					= getGroupInfoList.getGroupInfo(group_id).getC630000021();
				//如果当前组类型为公司
				if(groupType.equals("2"))
				{
					sql.append("select a.C1 userId, a.C630000003 userLoginName");
					sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
					sql.append(" and a.C630000013 = '"+group_id+"' and a.C630000015 is null");
					if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
					{
						sql.append(" and "+strwhere);
					}
				}
				//如果当前组类型为部门
				else if(groupType.equals("3"))
				{
					sql.append("select a.C1 userId, a.C630000003 userLoginName");
					sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
					sql.append(" and a.C630000015 = '"+group_id+"'");
					if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
					{
						sql.append(" and "+strwhere);
					}
				}	
			}

			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			String userId			= "";
			String groupId			= "";
			String userLoginName	= "";
			//资源模块人员单选
			if(type.equals("radio"))
			{
				while(rs.next())
				{
					userId			= rs.getString("userId");
					groupId			= rs.getString("groupId");
					userLoginName	= rs.getString("userLoginName");
					//没有下层节点
					treeinfo.append("<tree text=\""+userLoginName+"\" funpram=\""+userLoginName+":"+userId+":"+groupId+"\"/>");
				}
			}
			//资源模块人员复选
			else
			{
				while(rs.next())
				{
					userId			= rs.getString("userId");
					userLoginName	= rs.getString("userLoginName");
					//没有下层节点
					treeinfo.append("<tree text=\""+userLoginName+"\"   schkbox=\""+userId+"\"/>");
				}
			}
		}
		catch(Exception e)
		{
			logger.error("[491]LoadSourceUserTree.getUserTree() 生成用户信息树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:根据传入卓越工单角色组id,显示卓越工单角色组列表或卓越工单角色组组成员<p>
	 * @author fangqun
	 * @creattime 2009-5-14
	 * @param id
	 * @return String
	 */
	public String getUserAndGroupTree_WF(String id, String flag)
	{
		//公共的js生成树
		StringBuffer treeinfo	= new StringBuffer();
		//公共的查询条件
		StringBuffer sql		= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase		= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm			= null;
		ResultSet rs			= null;
		
		try
		{	
			if(id != null && "0".equals(id)){
				sql.append("select distinct a.c700024007,a.c700024008 from "+rworkflowchildrole+" a ");
			}else if(id.indexOf("WF:") != -1 && id.indexOf(",") == -1){
				sql.append("select distinct a.c700024003,a.c700024004 from "+rworkflowchildrole+" a where a.c700024007='"+id+"'");
			}else if(id.indexOf(",") != -1){
				String[] temp = id.split(",");
				sql.append("select distinct a.c700024001,a.c700024002 from "+rworkflowchildrole+" a, "+rgroup+
						   " b where a.c700024007='"+temp[0]+"' and a.c700024003='"+temp[1]+"' and a.c700024001=b.c106");
			}else if(id != null){
				sql.append("select b.c1,b.c630000003 from "+ruser+" a, "+usertablename+" b where b.c630000001=a.c101 and a.c640000000 like '%"+id+"%'");
			}			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	if(id != null && "0".equals(id)){
 	    		while(rs.next())
 				{	 	    	
 		 	    	groupID       	= rs.getString(1);
 		 	    	groupName     	= rs.getString(2);	 
 		 	    	
 		 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";"+flag+"\"/>");
 				}
 	    		
 	    	}else if(id.indexOf("WF:") != -1 && id.indexOf(",") == -1){
 	    		while(rs.next())
 				{	 	    	
 		 	    	groupID       	= rs.getString(1);
 		 	    	groupName     	= rs.getString(2);	
 		 	    	
 	 	    		treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+id+","+groupID+";"+flag+"\"/>");
 				}
 	    		
			}else if(id.indexOf(",") != -1){
				while(rs.next())
				{	 	    	
		 	    	groupID       	= rs.getString(1);
		 	    	groupName     	= rs.getString(2);	 	
		 	    	
		 	    	if("15".equals(flag)){
		 	    		treeinfo.append("<tree text=\""+groupName+"\" schkbox=\""+groupName+":null:"+groupID+"\"/>");
		 	    	}else{
		 	    		treeinfo.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";"+flag+"\"/>");
		 	    	}
					
				}
				
			}else if(id != null){
				while(rs.next())
				{	 	    	
		 	    	groupID       	= rs.getString(1);
		 	    	groupName     	= rs.getString(2);
		 	    	
	 	    		treeinfo.append("<tree text=\""+groupName+"\" schkbox=\""+groupName+":"+groupID+":"+id+"\" />");
				}
				 	    	
 	    	}
 	    	
		}
		catch(Exception e)
		{
			logger.error("卓越工单角色组树生成失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
}
