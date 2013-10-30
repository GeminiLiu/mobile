package cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

public class CommissionSearchSQL
{
	static final Logger logger = (Logger) Logger.getLogger(CommissionSearchSQL.class);
	
	//从配置文件中取表名
	GetFormTableName getFormTableName	= new GetFormTableName();
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	private String roleskill			= getFormTableName.GetFormName("RemedyTrole");
	private String managergrandaction	= getFormTableName.GetFormName("managergrandaction");
	private String rolesSkillManage		= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String rolesUserGroupRel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	//资源权限动作值表
	private String grandactiontable		= getFormTableName.GetFormName("RemedyTgrandaction");
	//组成员表
	private String groupUser			= getFormTableName.GetFormName("RemedyTgroupuser");
	//用户信息表
	private String usertablename		= getFormTableName.GetFormName("RemedyTpeople");
	//组信息表
	private String grouptablename		= getFormTableName.GetFormName("RemedyTgroup");
	//
	private String skillconferstatus	= getFormTableName.GetFormName("skillconferstatus");
	
	private String skillconfer			= getFormTableName.GetFormName("skillconfer");
	
	private String rolemanagetable		= getFormTableName.GetFormName("RemedyTrolesmanage");
	
	/**
	 * <p>Description:个人授权模块显示资源树顶级节点<p>
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param strwhere
	 * @param id
	 * @param tuser
	 * @return String
	 */
	public String getSourceTree(String tuser)
	{	
		//公共的资源生成树
		StringBuffer strjs	= new StringBuffer();
		//公共的查询sql
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
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a where a.source_parentid = '0'");
			}
			else
			{
				//当前用户不为Demo时的C1
				GetUserInfoList getUserInfoList		= new GetUserInfoList();
		  		String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
		  		
		  		sql.append("select distinct source_id,source_parentid,source_cnname,source_orderby from");
				
		  		sql.append(" (select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008 and b.C610000018 = '0'");
				sql.append(" union");
				sql.append(" select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
				sql.append( rolemanagetable + " rolemanagetable");
				sql.append(" where d.C620000028 = '"+tuserId+"'  and c.C660000028 = b.C660000006");
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and b.C660000007 = a.source_id) source1");
				sql.append(" where 1=1 and not exists (");
				sql.append(" select distinct a.source_id from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008 and b.C610000018 = '0'");
				sql.append(" and a.source_id = source1.source_parentid)");
				sql.append(" and not exists(");
				sql.append(" select distinct a.source_id from "+sourceconfig+" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
				sql.append(  rolemanagetable + " rolemanagetable");
				sql.append(" where d.C620000028 = '"+tuserId+"' and c.C660000028 = b.C660000006");
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and b.C660000007 = a.source_id");
				sql.append(" and a.source_id = source1.source_parentid)");
			}
			sql.append(" order by source_orderby,source_parentid,source_id");
			System.out.println(sql.toString());
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
			
 	    	String sourceName     = "";
 	    	String sourceID       = "";

 	    	stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
 	    	
			while(rs.next())
			{
	 	    	sourceName      = rs.getString("source_cnname");
	 	    	sourceID        = rs.getString("source_id");
	 	    	//生成树顶层节点(加链接)
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+sourceName+"\",\"commissionloadtree.jsp?sourceid="+sourceID+"\",\"\",\"\",\"\",\"\",\"\",\""+sourceName+":"+sourceID+":"+sourceID+"\"));");
			}
			
			strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("CommissionSerachSQL.showAttValueTree() 个人授权模块显示资源树顶级节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return strjs.toString();
	}
	


	/**
	 * <p>Description:个人授权模块动态加载资源树子节点<p>
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param strwhere
	 * @param id
	 * @param tuser
	 * @return String
	 */
	public String loadSourceTree(String strwhere,String id,String tuser)
	{
		//公共的资源生成树
		StringBuffer strjs	= new StringBuffer();
		//公共的查询sql
		StringBuffer sql	= new StringBuffer();
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		
		try
		{
			//当前用户不为Demo时的C1
			GetUserInfoList getUserInfoList		= new GetUserInfoList();
	  		String tuserId = getUserInfoList.getUserInfoName(tuser).getC1();
	  		if(tuser.toLowerCase().equals("demo"))
			{
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig+" a where a.source_parentid = '"+id+"'");
			}
	  		else
	  		{
				//根据配置文件中的表名和传入的参数确定sql语句
				sql.append("select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+roleskill+" b");
				sql.append(" where b.C610000007 = '"+tuserId+"' and a.source_id = b.C610000008");
				sql.append(" and b.C610000018 = '0' and a.source_parentid = '"+id+"'");
				sql.append(" union");
				sql.append(" select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d,");
				sql.append( rolemanagetable + " rolemanagetable");
				sql.append(" where d.C620000028 = '"+tuserId+"'  and c.C660000028 = b.C660000006 and a.source_id = b.C660000007");
				sql.append(" and ((c.c660000026 = d.c620000028 and");		           
				sql.append(" c.c660000027 = d.c620000027) or");		       
				sql.append(" (c.c660000026 is null and c.c660000027 = d.c620000027))");
				sql.append(" and rolemanagetable.c1=c.c660000028");
				sql.append(" and a.source_parentid = '"+id+"'");
	  		}
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by source_orderby,source_parentid,source_id");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String sourceName     = "";
 	    	String sourceID       = "";
 	    	
			while(rs.next())
			{
				sourceName      = rs.getString("source_cnname");
				sourceID        = rs.getString("source_id");
	 	    	//xml生成树
	 	    	strjs.append("<tree text=\""+sourceName+"\" src=\"commissionloadtree.jsp?sourceid="+sourceID+"\"  funpram=\""+sourceName+":"+sourceID+":"+sourceID+"\"/>");
			}
			System.out.println("treesql:"+strjs.toString());
		}
		catch(Exception e)
		{
			logger.error("CommissionSerachSQL.loadSourceTree() 个人授权模块动态加载资源树子节点失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return strjs.toString();
	}
	
	/**
	 * <p>Description:个人授权管理模块根据用户登陆名查找该用户所处公司所有组信息顶层<p>
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param strwhere
	 * @param tuser
	 * @return String
	 */
	public String getFirstGroupFloorTree(String strwhere,String tuser)
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
				sql.append("select distinct a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a,"+usertablename+" b");
				sql.append(" where b.C630000001 = '"+tuser+"' and b.C630000013 = a.C1");
				sql.append(" and a.C630000025='0'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000022");
			}
				
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());

			treeinfo.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			treeinfo.append("try{");
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	treeinfo.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"commisionloadtree.jsp?groupid="+groupID+"\"));");
			}
 	    	treeinfo.append("}catch(e){}");
 	    	treeinfo.append("</script>");
 	    	treeinfo.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("CommissionSearchSQL.getFirstGroupFloorTree() 个人授权管理模块根据用户登陆名查找该用户所处公司所有组信息顶层失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:个人授权管理模块显示该用户所处公司所有组信息子节点<p>
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param strwhere
	 * @param id
	 * @return String
	 */
	public String loadGroupTree(String strwhere,String id)
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
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+grouptablename+" a where a.C630000025 = '0' and a.C630000020 = '"+id+"'");
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by a.C630000022");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName		= "";
 	    	String groupID			= "";
			
 	    	while(rs.next())
			{
	 	    	groupName     	= rs.getString("C630000018");
	 	    	groupID       	= rs.getString("C1");
	 	    	
	 	    	treeinfo.append("<tree text=\""+groupName+"\" src=\"commisionloadtree.jsp?groupid="+groupID+"\" />");
			}
		}
		catch(Exception e)
		{
			logger.error("CommissionSearchSQL.loadGroupTree() 个人授权管理模块显示该用户所处公司所有组信息子节点失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	/**
	 * <p>Description:个人授权模块生成用户信息树<p>
	 * 日期 2007-3-5
	 * @author wangyanguang
	 * @param strwhere
	 * @param group_id
	 * @return String
	 */
	public String getUserTree(String strwhere,String group_id)
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
			GetGroupInfoList getGroupInfoList	= new GetGroupInfoList();
			String groupType					= getGroupInfoList.getGroupInfo(group_id).getC630000021();
			//如果当前组类型为公司
			if(groupType.equals("2"))
			{
				sql.append("select a.C1 userId,a.C630000001 userLoginName,a.C630000003 userName,a.C630000017");
				sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
				sql.append(" and a.C630000013 = '"+group_id+"' and a.C630000015 is null");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000017");
			}
			//如果当前组类型为部门
			else if(groupType.equals("3"))
			{
				sql.append("select a.C1 userId,a.C630000001 userLoginName,a.C630000003 userName,a.C630000017");
				sql.append(" from "+usertablename+" a where a.C630000012 = '0'");
				sql.append(" and a.C630000015 = '"+group_id+"'");
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
				sql.append(" order by a.C630000017");
			}	

			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
			String userId			= "";
			String userLoginName	= "";
			String userName			= "";

			while(rs.next())
			{
				userId			= rs.getString("userId");
				userLoginName	= rs.getString("userLoginName");
				userName		= rs.getString("userName");
				//没有下层节点
				treeinfo.append("<tree text=\""+userName+"\"   funpram=\""+userName+":"+userId+":"+userId+"\"/>");
			}
		}
		catch(Exception e)
		{
			logger.error("CommissionSearchSQL.getUserTree() 个人授权模块生成用户信息树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return treeinfo.toString();
	}
	
	
	public static void main(String[] args)
	{
		CommissionSearchSQL commissionsql = new CommissionSearchSQL();
		System.out.println(commissionsql.loadSourceTree("","1","Demo"));
	}
	
	
}
