package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

public class GetSourceTree {
	
	static final Logger logger = (Logger) Logger.getLogger(GetSourceTree.class);
	private String treeinfo = "";

	static Session session = null;
	private String strjstree 		= "";
	private String tmpstrjstree		= "";
	
	//从配置文件中取表名
	GetFormTableName getFormTableName	= new GetFormTableName();
	private String sourceconfig			= getFormTableName.GetFormName("sourceconfig");
	private String roleskill			= getFormTableName.GetFormName("RemedyTrole");
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");
	
	private String managergrandaction	= getFormTableName.GetFormName("managergrandaction");
	private String grandaction			= getFormTableName.GetFormName("grandaction");
	
	private String rolesSkillManage		= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String rolesUserGroupRel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String groupUser			= getFormTableName.GetFormName("RemedyTgroupuser");
	
	private String systemmanage			= getFormTableName.GetFormName("systemmanage");
	private String muluurl				= getFormTableName.GetFormName("muluurl");
	private String remedyserver			= getFormTableName.GetFormName("driverurl");
	
	private String rolemanagetable		= getFormTableName.GetFormName("RemedyTrolesmanage");
	
	public StringBuffer showTree()
	{
		
		StringBuffer strjs = new  StringBuffer();
		strjs.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
    	strjs.append("<script language='JavaScript'>");
    	strjs.append(intfind());
 	    strjs.append("</script>");
    	strjs.append("<script>document.write(d);</script>");
    	return strjs;
	}
	
	public StringBuffer showmoduleTree()
	{
		
		StringBuffer strjs = new  StringBuffer();
		strjs.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
    	strjs.append("<script language='JavaScript'>");
    	strjs.append(intmodulefind());
 	    strjs.append("</script>");
    	strjs.append("<script>document.write(d);</script>");
    	return strjs;
	}
	
	public String intfind()
	{
		tmpstrjstree="";
		
		try
		{
		     String query = "from Sourceconfig Order by source_parentid";
			 HibernateDAO  session = new HibernateDAO(); 
			 List l1  = session.queryObject(query);
			if(l1.size()>0)
			{
			    for(Iterator it=l1.iterator(); it.hasNext();)
		 	    {
			    	Sourceconfig sysgroup = (Sourceconfig)it.next();
		 	    	String groupParentID  = sysgroup.getSourceParentid().toString();
		 	    	String groupName      = sysgroup.getSourceCnname().trim();
		 	    	String groupID        = sysgroup.getSourceId().toString();

		 	    	tmpstrjstree="d.add("+groupID+","+groupParentID+",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");";
		 	    	System.out.println("-----------------"+tmpstrjstree);
		 	    	
		 	    	strjstree=strjstree+tmpstrjstree;
		 	    }
			}
			logger.info(strjstree);
			return strjstree.toString();
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace(System.err);
			return null;
		}		
	}
	
	public String intmodulefind()
	{
		tmpstrjstree="";
		
		try
		{
		     String query = "from Sourceconfig Order by source_parentid";
			 HibernateDAO  session = new HibernateDAO(); 
			 List l1  = session.queryObject(query);
			if(l1.size()>0)
			{
			    for(Iterator it=l1.iterator(); it.hasNext();)
		 	    {
			    	Sourceconfig sysgroup = (Sourceconfig)it.next();
		 	    	String groupParentID  = sysgroup.getSourceParentid().toString();
		 	    	String groupName      = sysgroup.getSourceCnname().trim();
		 	    	String groupID   	  = sysgroup.getSourceId().toString();
		 	    	String sourceenname   = sysgroup.getSourceName().toString().trim();

		 	    	tmpstrjstree="d.add("+groupID+","+groupParentID+",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");";
		 	    	System.out.println("-----------------"+tmpstrjstree);
		 	    	
		 	    	strjstree=strjstree+tmpstrjstree;
		 	    }
			}
			logger.info(strjstree);
			return strjstree.toString();
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace(System.err);
			return null;
		}		
	}
		
	/**
	 * <p>Description:递归实现资源类别属性值模块生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-1
	 * @return String
	 */
	public String findSourceAttValueTree(String strwhere,String id,String tuser)
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
				sql.append(" where b.C610000007 = '"+tuserId+"' and b.C610000010 = '"+managergrandaction+"' and a.source_id = b.C610000008");
				sql.append(" and b.C610000018 = '0' and a.source_parentid = '"+id+"'");
				sql.append(" union");
				sql.append(" select distinct a.source_parentid,a.source_id,a.source_cnname,a.source_orderby from "+sourceconfig +" a,"+rolesSkillManage+" b,"+rolesUserGroupRel+" c,"+groupUser+" d");
				sql.append(" where d.C620000028 = '"+tuserId+"' and d.C620000027 = c.C660000027 and c.C660000028 = b.C660000006 and a.source_parentid = b.C660000007");
				sql.append(" and b.C660000009 = '"+managergrandaction+"' and a.source_parentid = '"+id+"'");
	  		}
			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
			{
				sql.append(" and "+strwhere);
			}
			sql.append(" order by source_orderby,source_parentid,source_id");
			
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String groupName     = "";
 	    	String groupID       = "";
 	    	
			while(rs.next())
			{
	 	    	groupName      = rs.getString("source_cnname");
	 	    	groupID        = rs.getString("source_id");
	 	    	//xml生成树
	 	    	strjs.append("<tree text=\""+groupName+"\" src=\"grouploadtree.jsp?str="+groupID+";3\" action=\"sourceattvalueshow.jsp?sourceid="+groupID+"\" target=\"mainFrame\" />");
			}
		}
		catch(Exception e)
		{
			logger.error("[480]GetSourceTree.findSourceAttValueTree() 递归实现资源类别属性值模块生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
		return strjs.toString();
	}
	
	/**
	 * <p>Description:显示资源类别属性值模块生成树<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-1
	 * @return String
	 */
	public String showAttValueTree(String tuser)
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
		  		
		  		sql.append("select distinct source_id,source_parentid,source_cnname,source_orderby" +
				   " from syssourceview sourceview");
				sql.append(" where sourceview.userid='"+tuserId+"'");
				sql.append(" and not exists");
				sql.append(" (");
				sql.append(" select 1 from syssourceview sourceview1");
				sql.append(" where sourceview1.source_id=sourceview.source_parentid " +
						   "and sourceview1.userid='"+tuserId+"'");
				sql.append(")");
				
				sql.append(" order by source_orderby,source_parentid,source_id");
			}
			System.out.println("sql"+sql);
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
			
 	    	String groupName     = "";
 	    	String groupID       = "";

 	    	stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
 	    	
			while(rs.next())
			{
	 	    	groupName      = rs.getString("source_cnname");
	 	    	groupID        = rs.getString("source_id");
	 	    	//生成树顶层节点(加链接)
	 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"grouploadtree.jsp?str="+groupID+";3\",\"sourceattvalueshow.jsp?sourceid="+groupID+"\",\"\",\"\",\"\",\"mainFrame\"));");
			}
			
			strjs.append("}catch(e){}");
     	    strjs.append("</script>");
        	strjs.append("<script>document.write(tree);</script>");
		}
		catch(Exception e)
		{
			logger.error("[481]GetSourceTree.showAttValueTree() 显示资源类别属性值模块生成树失败"+e.getMessage());
		}
		finally
		{
			Function.closeDataBaseSource(rs, stm, dataBase);
		}
    	return strjs.toString();
	}
	
	/**
	 * 
	 * 日期 2006-11-28
	 * 
	 * @author wangyanguang/王彦广 
	 * @param strwhere
	 * @return StringBuffer
	 *
	 */
	public StringBuffer show_JDBCTree(String strwhere)
	{
		
		StringBuffer strjs = new  StringBuffer();
		strjs.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
    	strjs.append("<script language='JavaScript'>");
    	strjs.append(JDBC_SourceTree(strwhere));
 	    strjs.append("</script>");
    	strjs.append("<script>d.closeAll();document.write(d);</script>");
    	return strjs;
	}
	/**
	 * 
	 * 日期 2006-11-28
	 * 
	 * @author wangyanguang/王彦广 
	 * @param strwhere
	 * @return String
	 *
	 */
	public String JDBC_SourceTree(String strwhere)
	{
		treeinfo = "";
		//从配置文件中取表名
		GetFormTableName getFormTableName	= new GetFormTableName();
		String tablename					= getFormTableName.GetFormName("sourceconfig");
		IDataBase dataBase					= null;
		Statement stm						= null;
		ResultSet rs						= null;
		System.out.println(tablename);
		try
		{
			if(!String.valueOf(tablename).equals("")&&!String.valueOf(tablename).equals("null"))
			{
				//根据配置文件中的表名和传入的参数确定sql语句
				String sql="select source_id,source_parentid,source_cnname from "+tablename +" where 1=1";
				if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql=sql+" and "+strwhere +" order by source_parentid,source_id";
				}
				System.out.println(sql);
				
				//实例化一个类型为接口IDataBase类型的工厂类
				dataBase	= DataBaseFactory.createDataBaseClassFromProp();
				stm		= dataBase.GetStatement();
				//获得数据库查询结果集
				rs		= dataBase.executeResultSet(stm,sql);
				
				String groupParentID = "";
	 	    	String groupName     = "";
	 	    	String groupID       = "";
	 	    	int i = 0;
				while(rs.next())
				{
					groupParentID  = rs.getString("source_parentid");
		 	    	groupName      = rs.getString("source_cnname");
		 	    	groupID        = rs.getString("source_id");
		 	    	//调用js脚本生成树
		 	    	if(i==0)
		 	    	{
		 	    		treeinfo=treeinfo+"d.add("+groupID+","+groupParentID+",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"\",'1');";
		 	    	}else
		 	    	{
		 	    		treeinfo=treeinfo+"d.add("+groupID+","+groupParentID+",\"<font onclick=back_time('"+groupName+"','"+groupID+"','"+groupParentID+"');>"+groupName+"</font>\",\"\",\"\",\"main\",\"\",\"\",\"\",'0');";
		 	    	}
		 	    	i++;
				}
				//关闭连接，释放资源
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
			logger.error("[446] GetSourceTree.findSourceAttValueTree() 递归实现资源类别属性值模块生成树失败:"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
	}
	
	 /**
     * 日期 2007-1-8
     * @author xuquanxing 
     * @param username
     * @return List
     */
    public List getSourceConfig(String username)
    {
    	StringBuffer sql = new StringBuffer();
    	if(username.equals("Demo"))
    	{
    		sql.append(" select b.source_id,b.source_cnname from "+sourceconfig+" b where b.source_parentid='0'");
    	}else
    	{
    		GetUserInfoList getUserInfoList = new GetUserInfoList();
    		// 取得用户id
//    		String tuserId = getUserInfoList.getUserInfoName(username).getC1();
    		sql.append("select b.source_id , b.source_cnname from  "+sourceconfig+" b ");
        	sql.append(" where  ");
        	sql.append(" b.source_parentid='0'");
    	}
    	System.out.println("xuquanxing="+sql.toString());
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		List    list        = new ArrayList();
	    stm	= dataBase.GetStatement();
			//获得数据库查询结果集
		rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String  source_id = rs.getString("source_id");
				String source_cnname = rs.getString("source_cnname");
				String  reslut    = source_id+","+source_cnname;
				list.add(reslut);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			//关闭连接，释放资源
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return list;
    }
    public String getSysMenu(String tuser,String id,String strwhere)
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
	  			sql.append("select distinct sourceconfig1.source_cnname,sourceconfig1.source_id,sourceconfig1.source_url,sourceconfig1.source_orderby from sourceconfig sourceconfig1,sourceconfig sourceconfig2");
	  			sql.append(" where sourceconfig1.source_module=sourceconfig2.source_id and sourceconfig2.source_name='"+systemmanage+"'");
	  			sql.append(" and sourceconfig1.source_type like '%0;%'");
	  			sql.append(" and sourceconfig1.source_url is not null");
	  			sql.append(" and sourceconfig1.source_isleft='0'");
	  			if(String.valueOf(id).equals("-1"))
	  			{
	  				sql.append(" and sourceconfig1.source_parentid=sourceconfig2.source_id");
	  			}
	  			else
	  			{
	  				sql.append(" and sourceconfig1.source_parentid = '"+id+"'");
	  			}
	  			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
	  			sql.append(" order by sourceconfig1.source_orderby");
	  			//sql.append("select sourceconfig.source_id,sourceconfig.source_cnname from "+sourceconfig+" sourceconfig where sourceconfig.source_parentid = '"+id+"'");
			}
	  		else
	  		{
				//根据配置文件中的表名和传入的参数确定sql语句
	  			sql.append("select distinct sourceconfig1.source_cnname,sourceconfig1.source_id,sourceconfig1.source_url,sourceconfig1.source_orderby from sourceconfig sourceconfig1,sourceconfig sourceconfig2,"+roleskill+" sysskill");
	  			sql.append(" where sourceconfig1.source_id=sysskill.c610000008 and sourceconfig1.source_module=sourceconfig2.source_id");
	  			sql.append(" and sourceconfig2.source_name='"+systemmanage+"' and sysskill.C610000007 = '"+tuserId+"' ");
	  			sql.append(" and (sysskill.C610000010 = '"+managergrandaction+"' or sysskill.C610000010 = '"+grandaction+"') and sysskill.C610000018 = '0'");
	  			sql.append(" and sourceconfig1.source_type like '%0;%'");
	  			sql.append(" and sourceconfig1.source_url is not null");
	  			sql.append(" and sourceconfig1.source_isleft='0'");
	  			if(String.valueOf(id).equals("-1"))
	  			{
	  				sql.append(" and sourceconfig1.source_parentid=sourceconfig2.source_id");
	  			}
	  			else
	  			{
	  				sql.append(" and sourceconfig1.source_parentid = '"+id+"'");
	  			}
	  			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
	  			sql.append(" union ");
	  			sql.append("select distinct sourceconfig1.source_cnname,sourceconfig1.source_id,sourceconfig1.source_url,sourceconfig1.source_orderby from sourceconfig sourceconfig1,sourceconfig sourceconfig2,"+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser");
	  			sql.append(","+rolemanagetable + " rolemanagetable");
	  			sql.append(" where sourceconfig1.source_id=RemedyTrolesskillmanage.c660000007 and sourceconfig1.source_module=sourceconfig2.source_id");
	  			sql.append(" and sourceconfig2.source_name='"+systemmanage+"' and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 ");
	  			sql.append(" and (RemedyTrolesskillmanage.C660000009 = '"+managergrandaction+"' or RemedyTrolesskillmanage.C660000009 = '"+grandaction+"')");
	  			sql.append(" and RemedyTrolesusergrouprel.C660000027=RemedyTgroupuser.C620000027 and RemedyTgroupuser.C620000028='"+tuserId+"'");
	  			sql.append(" and (RemedyTrolesusergrouprel.C660000026 is null or RemedyTrolesusergrouprel.C660000026='"+tuserId+"')");
	  			sql.append(" and rolemanagetable.c1=RemedyTrolesusergrouprel.c660000028");	
	  			sql.append(" and sourceconfig1.source_type like '%0;%'");
	  			sql.append(" and sourceconfig1.source_url is not null");
	  			sql.append(" and sourceconfig1.source_isleft='0'");
	  			if(String.valueOf(id).equals("-1"))
	  			{
	  				sql.append(" and sourceconfig1.source_parentid=sourceconfig2.source_id");
	  			}
	  			else
	  			{
	  				sql.append(" and sourceconfig1.source_parentid = '"+id+"'");
	  			}
	  			if(!String.valueOf(strwhere).equals("")&&!String.valueOf(strwhere).equals("null"))
				{
					sql.append(" and "+strwhere);
				}
	  			sql.append(" order by source_orderby");
				//sql.append("select a.source_id,a.source_cnname from "+sourceconfig +" a,"+roleskill+" b");
				//sql.append(" where b.C610000007 = '"+tuserId+"' and b.C610000010 = '"+managergrandaction+"' and a.source_id = b.C610000008 and not exists");
				//sql.append(" (select c.C610000008 from "+roleskill+" c where c.C610000007 = '"+tuserId+"' and c.C610000008 = a.source_parentid)");
				//sql.append(" and b.C610000018 = '0' and a.source_parentid = '"+id+"'");
	  		}
			
			//sql.append(" order by sourceconfig1.source_parentid,sourceconfig1.source_id");
			System.out.println(sql);
			stm	= dataBase.GetStatement();
			//获得数据库查询结果集
			rs	= dataBase.executeResultSet(stm,sql.toString());
			
 	    	String source_cnname     	= "";
 	    	String source_id      		= "";
 	    	String sourceattvalue_value = "";
 	    	if(String.valueOf(id).equals("-1"))
  			{
 	    		while(rs.next())
 				{
 	    			source_cnname      			= rs.getString("source_cnname");
 		 	    	source_id        			= rs.getString("source_id");
 		 	    	sourceattvalue_value		= rs.getString("source_url");
 		 	    	sourceattvalue_value		= sourceattvalue_value.replaceAll("remedyserver",remedyserver);
 		 	    	if(String.valueOf(sourceattvalue_value).equals("#"))
 		 	    	{
 		 	    		sourceattvalue_value="";
 		 	    	}
 		 	    	//xml生成树
 		 	    	strjs.append("tree.add(new WebFXLoadTreeItem(\""+source_cnname+"\",\"sysmenuloadtree.jsp?id="+source_id+"\",\""+sourceattvalue_value+"\",\"\",\"\",\"\",\"dutyMenuFrame\"));");
 				}
  			}
  			else
  			{
  				while(rs.next())
  				{
  					source_cnname      			= rs.getString("source_cnname");
 		 	    	source_id        			= rs.getString("source_id");
 		 	    	sourceattvalue_value		= rs.getString("source_url");
 		 	    	sourceattvalue_value		= sourceattvalue_value.replaceAll("remedyserver",remedyserver);
 		 	    	if(String.valueOf(sourceattvalue_value).equals("#"))
 		 	    	{
 		 	    		sourceattvalue_value="";
 		 	    	}
  		 	    	//xml生成树
  		 	    	strjs.append("<tree text=\""+source_cnname+"\" src=\"sysmenuloadtree.jsp?id="+source_id+"\" action=\""+sourceattvalue_value+"\" target=\"dutyMenuFrame\" />");
  				}
  			}
			
		}
		catch(Exception e)
		{
			logger.error("[480]GetSourceTree.findSourceAttValueTree() 递归实现资源类别属性值模块生成树失败"+e.getMessage());
		}
		finally
		{
			//关闭连接，释放资源
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return strjs.toString();
    }
	public static void main(String[] args)
	{
		GetSourceTree aa = new GetSourceTree();
		aa.showAttValueTree("test");

	}
		
}