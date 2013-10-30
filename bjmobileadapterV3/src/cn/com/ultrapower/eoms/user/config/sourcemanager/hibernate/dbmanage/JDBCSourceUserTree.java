package cn.com.ultrapower.eoms.user.config.sourcemanager.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.comm.function.Function;

/**
 * <p>Description:根据管理者权限确定资源管理者模块生成树<p>
 * @author wangwenzhuo
 * @CreatTime 2006-12-5
 */
public class JDBCSourceUserTree {
	
	static final Logger logger 			= (Logger) Logger.getLogger(JDBCSourceUserTree.class);
	
	GetFormTableName getFormTableName	= new GetFormTableName();
	//组信息表
	private String grouptablename		= getFormTableName.GetFormName("RemedyTgroup");
	//资源管理者表
	private String sourcemanager		= getFormTableName.GetFormName("RemedyTsourceManager");
	//用户信息表
	private String usertablename		= getFormTableName.GetFormName("RemedyTpeople");
	//资源配置表
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	//管理者类型为资源管理者
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	
	private String getFirstGroupFloorTree(String strwhere,String tuser,String type)
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
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId						= getUserInfoList.getUserInfoName(tuser).getC1();

				//查出该情况时父组ID不在已有资源授权列表中的组ID
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" f");
				sql.append(" where a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and a.C630000025='0' and not exists");
				sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
				sql.append(" and f.source_name = '"+systemmanage+"' and f.source_id = b.C650000001");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
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
 		 	    	
 		 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";8\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":null:"+groupID+"\"));");
 				}
 	    	}
 	    	else if(type.equals("10"))
 	    	{
 	    		while(rs.next())
 				{
 		 	    	groupName     	= rs.getString("C630000018");
 		 	    	groupID       	= rs.getString("C1");
 		 	    	
 		 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";10\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":null:"+groupID+"\"));");
 				}
 	    	}
		}
		catch(Exception e)
		{
			logger.error("[531]JDBCSourceUserTree.getGroupTree() 根据管理者登陆名显示其所管理公司下所有节点生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	
	
	/**
	 * <p>Description:当是主班人情况下角色类型生成树顶层<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-4
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	private String getFirstDutyGroupFloorTree(String strwhere,String tuser)
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
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
			else
			{	
				//查出该情况时父组ID不在已有资源授权列表中的组ID
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+usertablename+" b");
				sql.append(" where b.C630000001 = '"+tuser+"' and b.C630000015 = a.C1");
				sql.append(" and a.C630000025='0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
			System.out.println(sql);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());

 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";4\"));");
			}
		}
		catch(Exception e)
		{
			logger.error("[532]JDBCSourceUserTree.getDutyGroupTree() 当是主班人情况下角色类型生成树顶层失败"+e.getMessage());
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
	 * @param tuser
	 * @return String
	 */
	private String getFirstUserOfGroupFloorTree(String strwhere,String tuser)
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
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList	= new GetUserInfoList();
		  		String tuserId					= getUserInfoList.getUserInfoName(tuser).getC1();
				
				//查出该情况时父组ID不在已有资源授权列表中的组ID
				sql.append("select distinct a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+usertablename+" e,"+sourceconfig+" f");
				sql.append(" where a.C630000025='0' and  e.C1 = '"+tuserId+"' and (a.C1 = b.C650000003 or a.C1 = e.C630000013) and b.C650000007 = '"+tuserId+"' and not exists");
				sql.append(" (select distinct c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
				sql.append(" and f.source_name = '"+systemmanage+"' and f.source_id = b.C650000001");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
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
	 	    	
	 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";5\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":null:"+groupID+"\"));");
			}
		}
		catch(Exception e)
		{
			logger.error("[533]JDBCSourceUserTree.getFirstUserOfGroupFloorTree() 根据传入的管理者登陆名,显示该用户所管理公司以及他所选公司下的所有组信息顶层失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	
	
	/**
	 * <p>Description:显示根据角色信息生成树的返回值,即该用户所选组的公司下的所有节点<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-7
	 * @param strwhere
	 * @param roleId		组或用户所属组的组ID
	 * @return String
	 */
	private String getFirstGroupTreeFloorByParam(String strwhere,String roleId)
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
			//确定传入参数的组类型
			GetGroupInfoList groupInfoList	= new GetGroupInfoList();
			String groupType				= groupInfoList.getGroupInfo(roleId).getC630000021();
			
			//若组类型为公司或权限组
			if(groupType.equals("2")||groupType.equals("1"))
			{
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.C1,a.C630000018 from "+grouptablename+" a");
				sql.append(" where a.C1 = '"+roleId+"'");
			}
			//若组类型为其他情况
			else
			{
				//根据配置文件中的表名和传入的参数确定sql语句
		  		sql.append("select a.C1,a.C630000018 from "+grouptablename+" a,"+grouptablename+" b");
				sql.append(" where a.C630000025 = '0' and b.C1 = '"+roleId+"'");
				sql.append(" and a.C1 = b.C630000026");
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

	 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";7\"));");
			}
		}
		catch(Exception e)
		{
			logger.error("[534]JDBCSourceUserTree.getGroupTreeByParam() 显示根据角色信息生成树的返回值,即该用户所选组的公司下的所有节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:协助主班人情况下管理者类型生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-8
	 * @param roleId
	 * @return String
	 */
	public String getDutyGroupTreeByParam(String roleId)
	{
		try
		{	
			GetGroupInfoList groupInfoList	= new GetGroupInfoList();
 	    	String groupName				= groupInfoList.getGroupInfo(roleId).getC630000018();
 	    	String groupID					= groupInfoList.getGroupInfo(roleId).getC1();
 	    	
 	    	StringBuffer treeinfo = new StringBuffer();
 	    	
 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";9\"));");
	 	    return treeinfo.toString();
		}
		catch(Exception e)
		{
			logger.error("[489]JDBCSourceUserTree.getDutyGroupTreeByParam() 协助主班人情况下管理者类型生成树失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:生成资源模块组信息生成树协助用户信息生成树顶层,点击节点没有返回事件<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-4
	 * @param strwhere
	 * @param tuser
	 * @param type			6为资源模块组信息生成树,11为虚拟组成员模块生成树
	 * @return String
	 */
	public String getFirstGroupForUserFloorTree(String strwhere,String tuser,String type)
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
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '0'");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId						= getUserInfoList.getUserInfoName(tuser).getC1();
				
				//查出该情况时父组ID不在已有资源授权列表中的组ID
				sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" f");
				sql.append(" where a.C630000025='0' and a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and not exists");
				sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
				sql.append(" and f.source_name = '"+systemmanage+"' and f.source_id = b.C650000001");
			}
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			if(type.equals("11"))
			{
				sql.append(" and (a.C630000021 = '2' or a.C630000021 = '3')");
			}
			sql.append(" order by a.C630000022");
			
			
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
		 	    	
		 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";6\"));");
				}
 	    	}
 	    	//虚拟组成员
 	    	else
 	    	{
 	    		while(rs.next())
				{
		 	    	groupName     	= rs.getString("C630000018");
		 	    	groupID       	= rs.getString("C1");
		 	    	
		 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";11\"));");
				}
 	    	}
		}
		catch(Exception e)
		{
			logger.error("[535]JDBCSourceUserTree.getFirstGroupForUserFloorTree() 生成组信息生成树协助用户信息生成树顶层,点击节点没有返回事件失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
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
		StringBuffer str	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		try
		{
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql);
			while(rs.next())
			{
				String C1 = rs.getString("C1");
				if(!str.toString().equals(""))
				{
					str.append(",");
				}
				str.append("'"+C1+"'");
			}
		}
		catch(Exception e)
		{
			logger.error("[492]JDBCSourceUserTree.getNotInStr() 根据条件拼得以,分隔的not in结果集失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return str.toString();
	}
		
	/**
	 * <p>Description:根据条件决定客户端返回js生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-12-5
	 * @param flag			资源管理者类型
	 * @param tuser			管理者登陆名
	 * @param buttonType	roler为角色信息生成树，manager为管理者信息生成树
	 * @return String
	 */
	public String showUserTree(String flag,String tuser,String buttonType,String roleId)
	{	
		StringBuffer strjs = new StringBuffer();
		strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		strjs.append("try{");
		
    	try
    	{
    		//主班人0
			if(flag.equals("0"))
			{
				//只显示值班组
				if(buttonType.equals("roler"))
				{
					strjs.append(getFirstDutyGroupFloorTree("",tuser));
				}
				//显示该值班组下的人
				else
				{
					strjs.append(getDutyGroupTreeByParam(roleId));
				}	
			}
			//管理者1
			else if(flag.equals("1"))
			{
				//显示该用户所管理的公司的组和人,选择相应组或人作为返回值
				if(buttonType.equals("roler"))
				{
					strjs.append(getFirstUserOfGroupFloorTree("",tuser));
				}
				//显示该用户所管理公司下的所有人,选择相应人作为返回值
				else
				{
					strjs.append(getFirstGroupForUserFloorTree("",tuser,"6"));
				}
			}
			//归档组2
			else if(flag.equals("2"))
			{
				//显示该用户所管理公司下的所有人,选择相应人作为返回值
				if(buttonType.equals("roler"))
				{
					strjs.append(getFirstGroupForUserFloorTree("",tuser,"6"));
				}
				//显示所选人所属组的公司下的所有组,选择组类型为非公司的所有节点作为返回值
				else
				{
					strjs.append(getFirstGroupTreeFloorByParam("",roleId));
				}
			}
			//管理员3
			else if(flag.equals("3"))
			{
				//只显示该用户所管理的公司
				if(buttonType.equals("roler"))
				{					
					strjs.append(getFirstGroupFloorTree("a.C630000021='2'",tuser,"8"));
				}
				//显示该用户所管理公司下的所有人,选择相应人作为返回值
				else
				{	
					strjs.append(getFirstGroupForUserFloorTree("",tuser,"6"));
				}
			}
			//接口组4
			else if(flag.equals("4"))
			{
				//只显示该用户所管理的公司下的组,选择相应组作为返回值
				if(buttonType.equals("roler"))
				{				
					strjs.append(getFirstGroupFloorTree("",tuser,"10"));
				}
				//显示该用户所选组的公司下的所有组,选择组类型为非公司的所有节点作为返回值
				else
				{	
					strjs.append(getFirstGroupTreeFloorByParam("",roleId));
				}
			}
			//接口人5
			else if(flag.equals("5"))
			{
				//只显示该用户所管理的公司下的组,选择相应组作为返回值
				if(buttonType.equals("roler"))
				{
					strjs.append(getFirstGroupFloorTree("",tuser,"10"));
				}
				//显示该用户所选组的公司下的所有人和组,选择相应人作为返回值
				else
				{
					strjs.append(getFirstGroupForUserFloorTree("",tuser,"6"));
				}
			}
			
			strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
        	return strjs.toString();
    	}
    	catch(Exception e)
    	{
    		logger.error("[493]CreatGroupTree.showUserTree() 根据条件决定客户端返回js生成树失败"+e.getMessage());
    		return null;
    	}	
	}

}
