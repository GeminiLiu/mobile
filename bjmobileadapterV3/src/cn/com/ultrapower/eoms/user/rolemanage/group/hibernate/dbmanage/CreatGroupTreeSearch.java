package cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

/**
 * <p>Description:在服务端生成树<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-20
 */
public class CreatGroupTreeSearch {
	
	static final Logger logger = (Logger) Logger.getLogger(CreatGroupTreeSearch.class);
	
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
	
	//公共的js树
	private StringBuffer strjs			= new StringBuffer();
	
	GetUserInfoList getUserInfoList		= new GetUserInfoList();
	
	/**
	 * <p>Description:组成员信息模块将生成树在服务器端生成<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-20
	 * @return StringBuffer
	 */
	public StringBuffer showTree(String tuser)
	{
		StringBuffer sql	= new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select C1,C630000018,C630000022 from "+grouptablename +" where C630000025 = '0' and C630000020 = '0' order by C630000022");
				
			}
			else
			{
				//当前用户不为Demo时的C1
		  		String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		//如果是系统管理员
		  		if(getUserInfoList.isSystemManagerById(tuserId))
		  		{
		  			sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c");
					sql.append(" where a.C630000025='0' and a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and not exists");
					sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
					sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
		  		}
		  		//如果为值班管理员
		  		else
		  		{
		  			sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a,"+usertablename+" b");
		  			sql.append(" where a.C1 = b.C630000015 and b.C1 = '"+tuserId+"'");
		  			sql.append(" and a.C630000025='0'");
		  		}
		  		
		  		sql.append(" order by a.C630000022");
			}
			
			System.out.println("组成员树形菜单SQL："+sql.toString());
			
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");

			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			String groupName     = "";
		    String groupID       = "";
 	    	
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");

	 	    	//生成树顶层节点
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";2\",\"FindPeopleDao?id="+groupID+"\",\"\",\"\",\"\",\"mainFrame\"));");
			}
		
    		strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
        	
    	}
    	catch(Exception e)
    	{
    		logger.error("[414]CreatGroupTree.showTree() 组成员信息模块生成树失败"+e.getMessage());
    	}
    	finally
		{
			//关闭连接，释放资源
			try
			{
				if(rs!=null)
				rs.close();
			}
			catch(Exception e)
			{
				logger.error("[414]CreatGroupTree.showTree() 组成员信息模块生成树失败"+e.getMessage());
			}
			try
			{
				if(stm!=null)
				stm.close();
			}
			catch(Exception e)
			{
				logger.error("[414]CreatGroupTree.showTree() 组成员信息模块生成树失败"+e.getMessage());
			}
			dataBase.closeConn();
		}
    	return strjs;
	}
	
	/**
	 * <p>Description:单位ID模块将生成树在服务器端生成<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-24
	 * @return StringBuffer
	 */
	public StringBuffer cpshowTree(String tuser)
	{
	 	StringBuffer sql = new StringBuffer();
	 	//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		try
    	{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000021 = '2' and a.C630000020 = '0'");
				sql.append(" order by a.C630000022");
			}
			else
			{
				//当前用户不为Demo时的C1
				String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
		  		//拼not in子查询的sql,要求其查询结果集为单个字段"a.C1"
				sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c");
				sql.append(" where a.C630000025='0' and a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and not exists");
				sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
				sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
				sql.append(" order by a.C630000022");
			}
			
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");

			stm	= dataBase.GetStatement();
    		rs	= dataBase.executeResultSet(stm,sql.toString());
    		
    		String groupName     = "";
    	 	String groupID       = "";
    	 	String groupDnId	 = "";
    	 	String groupFullName = "";
    		
			while(rs.next())
			{
	 	    	groupName		= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupDnId	 	= rs.getString("C630000037");
	 	    	groupFullName 	= rs.getString("C630000019");
	 	    	//生成树顶层节点
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtreeSearch.jsp?str="+groupID+";0\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupFullName+":"+groupDnId+"\"));");
			}
		
    		strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
		}
    	catch(Exception e)
    	{
    		logger.error("[415]CreatGroupTree.cpshowTree() 单位ID模块生成树失败"+e.getMessage());
    	}
    	finally
		{
			//关闭连接，释放资源
			try
			{
				if(rs!=null)
				rs.close();
			}
			catch(Exception e)
			{
				logger.error("[415]CreatGroupTree.cpshowTree() 单位ID模块生成树失败"+e.getMessage());
			}
			try
			{
				if(stm!=null)
				stm.close();
			}
			catch(Exception e)
			{
				logger.error("[415]CreatGroupTree.cpshowTree() 单位ID模块生成树失败"+e.getMessage());
			}
			dataBase.closeConn();
		}
    	return strjs;
	}
	
	/**
	 * <p>Description:部门ID模块将生成树在服务器端生成<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-24
	 * @param id
	 * @return StringBuffer
	 */
	public StringBuffer dpshowTree(String id)
	{	
	 	//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
    	try
    	{
    		strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
    		strjs.append("try{");
    		
    		//根据配置文件中的表名和传入的参数确定sql语句
			String sql	= "select C1,C630000018,C630000022 from "+grouptablename +" where C630000025 = '0' and C630000021 = '3' and C630000020 = '"+id+"' order by C630000022";
			
			stm			= dataBase.GetStatement();
    		rs			= dataBase.executeResultSet(stm,sql.toString());

    		String groupName     = "";
        	String groupID       = "";
        	
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	//生成树顶层节点
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";1\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+"\"));");
			}
		
    		strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
    	}
    	catch(Exception e)
    	{
    		logger.error("[416]CreatGroupTree.dpshowTree() 部门ID模块生成树失败"+e.getMessage());
    	}
    	finally
		{
			//关闭连接，释放资源
			try
			{
				if(rs!=null)
				rs.close();
			}
			catch(Exception e)
			{
				logger.error("[416]CreatGroupTree.dpshowTree() 部门ID模块生成树失败"+e.getMessage());
			}
			try
			{
				if(stm!=null)
				stm.close();
			}
			catch(Exception e)
			{
				logger.error("[416]CreatGroupTree.dpshowTree() 部门ID模块生成树失败"+e.getMessage());
			}
			dataBase.closeConn();
		}
    	return strjs;
	}
	
	/**
	 * <p>Description:组模块父组ID根据组类型将生成树在服务器端生成<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-26
	 * @return StringBuffer
	 */
	public StringBuffer parentidshowTree(String groupType,String cpId,String tuser)
	{
		StringBuffer sql = new StringBuffer();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;

		try
		{
			//当前用户为Demo时
			if(tuser.toLowerCase().equals("demo"))
			{
				if(groupType.equals("2")||groupType.equals("6"))
				{
					sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000021 = '2' and a.C630000020 = '0'");
					sql.append(" order by a.C630000022");
				}
				else
				{
					sql.append("select C1,C630000018,C630000019,C630000037,C630000022 from "+grouptablename+" where C630000025 = '0' and C1 ='"+cpId+"'");
					sql.append(" order by C630000022");
				}
				System.out.println(sql.toString());
			}
			else
			{
				//当前用户不为Demo时的C1
				String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
				//如果是系统管理员
		  		if(getUserInfoList.isSystemManagerById(tuserId))
		  		{
					if(groupType.equals("2")||groupType.equals("6"))
					{
						sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c");
						sql.append(" where a.C630000025 = '0' and a.C630000020 = '0' and a.C1 = b.C650000003 and b.C650000007 = '"+tuserId+"' and not exists");
						sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+tuserId+"' and a.C630000020 = c.C650000003)");
						sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'");
						sql.append(" order by C630000022");
					}
					else
					{
						//根据配置文件中的表名和传入的参数确定sql语句
						sql.append("select C1,C630000018,C630000019,C630000037,C630000022 from "+grouptablename+" where C630000025 = '0' and  C1 ='"+cpId+"'");
						sql.append(" order by C630000022");
					}
		  		}
		  		//如果为值班管理员
		  		else
		  		{
		  			//根据配置文件中的表名和传入的参数确定sql语句
					sql.append("select a.C1,a.C630000018,a.C630000019,a.C630000037,a.C630000022 from "+grouptablename+" a,"+usertablename+" b");
					sql.append(" where a.C630000025 = '0' and a.C1 = b.C630000015 and b.C1 = '"+tuserId+"'");
					sql.append(" order by a.C630000022");
		  		}
			}
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	String groupDnId	 = "";
 	    	String groupFullName = "";
 	    	
			while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	groupDnId	 	= rs.getString("C630000037");
	 	    	groupFullName 	= rs.getString("C630000019");
	 	    	//生成树顶层节点
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"parentidloadtree.jsp?str="+groupID+";"+groupType+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupFullName+":"+groupDnId+"\"));");
			}
			
			strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("[427]GroupFind.flagfind() 协助根据groupType实现当组模块生成树失败"+e.getMessage());
		}
		finally
		{
			//关闭连接，释放资源
			try
			{
				if(rs!=null)
				rs.close();
			}
			catch(Exception e)
			{
				logger.error("[427]GroupFind.flagfind() 协助根据groupType实现当组模块生成树失败"+e.getMessage());
			}
			try
			{
				if(stm!=null)
				stm.close();
			}
			catch(Exception e)
			{
				logger.error("[417]CreatGroupTree.parentidshowTree() 组模块父组ID根据组类型生成树失败"+e.getMessage());
			}
			dataBase.closeConn();
		}
		return strjs;
	}
	
}
