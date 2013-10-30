package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.TreeSQL;
import cn.com.ultrapower.eoms.user.authorization.usercommision.hibernate.dbmanage.UserCommisionFind;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class GroupIsSnatchSQL
{
	static final Logger logger = (Logger) Logger.getLogger(GroupIsSnatchSQL.class);
	private String sourcemanager		= "";
	private String systemmanage			= "";
	private String sourceconfig			= "";
	private String dutyorgnazition		= "";
	private String orgnazitionarranger	= "";
	private String usertablename		= "";
	private String grouptablename		= "";
	private String groupusertablename	= "";
	private String sysskill				= "";
	private String uid					= "";
	private String skillaction			= "";
	private String roleskillmanagetable	= "";
	private String usergrouprel			= "";
	private String rolemanagetable		= "";
	/**
	 * 从配制文件中读取表的配制信息
	 */
	public GroupIsSnatchSQL()
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
			roleskillmanagetable = getTableProperty.GetFormName("RemedyTrolesskillmanage");
			usergrouprel		= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			rolemanagetable		= getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	
	//查询出DEMO用户的资源信息。
	public String getDemoParentSourceInfo()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sourceconfigtable.source_id,sourceconfigtable.source_name,sourceconfigtable.source_cnname,sourceconfigtable.source_orderby from "+ sourceconfig +" sourceconfigtable");
		sql.append(" where sourceconfigtable.source_parentid='0'");
		sql.append(" and sourceconfigtable.source_type like '%1;%'");
		
		sql.append(" order by sourceconfigtable.source_orderby");
		System.out.println(sql.toString());
		StringBuffer sbf 	= new StringBuffer();
		
		
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 		= rs.getString("source_id");
	 	    	String sourcename     	= rs.getString("source_name");
	 	 	  	String sourcecnname     = rs.getString("source_cnname");
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcecnname+"\",\"groupissnatchsrctree.jsp?sourceid="+sourceid+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+sourcecnname+";"+sourcename+"\"));");
			}
			sbf.append("}catch(e){}");
			sbf.append("</script>");
			sbf.append("<script>document.write(tree);</script>");
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
		return sbf.toString();
	}
	
	//根据资源父ID查询下一级节点(Demo)
	public String getDemoSourceInfo(String parentsourceid)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sourceconfigtable.source_id,sourceconfigtable.source_name,sourceconfigtable.source_cnname,sourceconfigtable.source_orderby from "+ sourceconfig +" sourceconfigtable");
		sql.append(" where sourceconfigtable.source_parentid="+parentsourceid);
		sql.append(" and sourceconfigtable.source_type like '%1;%'");
		
		sql.append(" order by sourceconfigtable.source_orderby");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 		= rs.getString("source_id");
	 	    	String sourcename     	= rs.getString("source_name");
	 	 	  	String sourcecnname     = rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcecnname+"\" src=\"groupissnatchsrctree.jsp?sourceid="+sourceid+"\"  schkbox=\""+sourcecnname+";"+sourcename+"\" />");
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
		return sbf.toString();
	}
	//查询出非DEMO用户的资源信息。
	//关联查询技能表与资源表逻辑：select * from 资源表 and 技能表 where 资源表的ID=技能表中的资源字段ID and 技能表中的权限=1000
	public String getParentSourceInfo(String userid)
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		String fordersource = "";
		sql.append(" select distinct source_id,source_parentid,source_name,source_cnname,source_orderby");
		sql.append(" from (select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_name,sourcetable.source_cnname,sourcetable.source_orderby");
		sql.append(" from sourceconfig sourcetable,");
		sql.append(sysskill+" skilltable"); 
		sql.append(" where sourcetable.source_id = skilltable.C610000008 ");               
		sql.append(" and skilltable.C610000010 = '"+skillaction+"'");               
		sql.append(" and skilltable.c610000007 = '"+userid+"'"); 
		sql.append(" and sourcetable.source_type like '%1;%'");
		sql.append(" union ");
		sql.append(" select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_name,sourcetable.source_cnname,sourcetable.source_orderby");
		sql.append(" from sourceconfig sourcetable,");        
		sql.append(roleskillmanagetable+" roleskillmanage,");              
		sql.append(usergrouprel +"  rolegroupuserrel,"); 
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(groupusertablename+" groupuser");          
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");                        
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");         
		sql.append(" and roleskillmanage.c660000009 = '"+skillaction+"'");           
		sql.append(" and groupuser.c620000028 = '"+userid+"'"); 
		sql.append(" and sourcetable.source_type like '%1;%'");
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");	
		sql.append(" )  uu");  
		sql.append(" where 1 = 1 and not exists(");           
		sql.append(" select sourcetable.source_id");   
		sql.append(" from sourceconfig sourcetable, "); 
		sql.append(  sysskill+" skilltable");      
		sql.append(" where sourcetable.source_id = skilltable.C610000008 ");               
		sql.append(" and skilltable.C610000010 = '"+skillaction+"'");               
		sql.append(" and skilltable.c610000007 = '"+userid+"'");                        
		sql.append(" and sourcetable.source_type like '%1;%'");
		sql.append(" and sourcetable.source_id = uu.source_parentid)");
		sql.append(" and not exists( ");
		sql.append(" select sourcetable.source_id ");
		sql.append(" from sourceconfig sourcetable,");        
		sql.append(  roleskillmanagetable+" roleskillmanage,");              
		sql.append(  usergrouprel +"  rolegroupuserrel,"); 
		sql.append(  rolemanagetable + " rolemanagetable,");
		sql.append(  groupusertablename+" groupuser");
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");                        
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");         
		sql.append(" and roleskillmanage.c660000009 = '"+skillaction+"'");           
		sql.append(" and groupuser.c620000028 = '"+userid+"'"); 
		sql.append(" and sourcetable.source_type like '%1;%'");
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		sql.append(" and sourcetable.source_id = uu.source_parentid)");              
		sql.append(" order by source_orderby,source_parentid,source_id"); 
		
		System.out.println("组是否抢单顶级资源菜单SQL："+sql);
		StringBuffer sbf 	= new StringBuffer();
		
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 		= rs.getString("source_id");
	 	    	String sourcename     	= rs.getString("source_name");
	 	 	  	String sourcecnname     = rs.getString("source_cnname");
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcecnname+"\",\"groupissnatchsrctree.jsp?sourceid="+sourceid+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+sourcecnname+";"+sourcename+"\"));");
			}
			sbf.append("}catch(e){}");
			sbf.append("</script>");
			sbf.append("<script>document.write(tree);</script>");
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
		return sbf.toString();
	}

	//根据资源父ID查询下一级节点(非Demo)
	public String getSourceInfo(String parentsourceid,String userid)
	{
		StringBuffer sql = new StringBuffer();
		
		sql.append("select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_name,sourcetable.source_cnname,sourcetable.source_orderby from "+ sourceconfig +" sourcetable,"+ sysskill +" skilltable");
		sql.append(" where sourcetable.source_id = skilltable.C610000008 and skilltable.C610000010='"+skillaction+"' and skilltable.c610000007="+userid );
		sql.append(" and sourcetable.source_parentid='"+parentsourceid+"'");
		sql.append(" and sourcetable.source_type like '%1;%'");
		sql.append(" and skilltable.c610000018='0' ");
		sql.append(" union ");
		sql.append(" select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_name, sourcetable.source_cnname,sourcetable.source_orderby");
		sql.append("  from sourceconfig sourcetable,");   
		sql.append(roleskillmanagetable+" roleskillmanage,");              
		sql.append(usergrouprel +"  rolegroupuserrel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(groupusertablename+" groupuser"); 
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");
		sql.append("  and roleskillmanage.c660000009 = '"+skillaction+"'");    
		sql.append(" and groupuser.c620000028 = '"+userid+"'");     
		sql.append(" and sourcetable.source_parentid ='"+parentsourceid+"'");  
		
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		
		//sql.append(" and groupuser.c620000027=rolegroupuserrel.c660000027");      
		sql.append(" and rolegroupuserrel.c660000028=roleskillmanage.c660000006");
		sql.append(" and sourcetable.source_type like '%1;%'");
		      
		sql.append(" order by source_orderby,source_id,source_parentid");
		
		System.out.println("组是否抢单下一级资源菜单SQL："+sql);
		
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 		= rs.getString("source_id");
	 	    	String sourcename     	= rs.getString("source_name");
	 	 	  	String sourcecnname     = rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcecnname+"\" src=\"groupissnatchsrctree.jsp?sourceid="+sourceid+"\"   schkbox=\""+sourcecnname+";"+sourcename+"\"/>");
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
		return sbf.toString();
	}

	
	/**
	 * 根据用户ID查询用户所管理的组信息。(非Demo用户)(顶级组节点)
	 * 日期 2006-12-31
	 * @author wangyanguang void
	 */
	public String getParentGroupInfo(String userid)
	{
		int  userintid		= 0;
		userintid = Integer.parseInt(userid);
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from ");
		sql.append(grouptablename+" grouptable,"+sourcemanager+" sourcemanagetable,");
		sql.append(sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
		sql.append(" and (grouptable.C1 = sourcemanagetable.C650000003) ");
		sql.append(" and sourcemanagetable.C650000007 = '"+userid+"' and sourcemanagetable.C650000005 = '3'");
		sql.append(" and sourcemanagetable.C650000001 = sourceconfigtable.source_id " );
		sql.append(" and sourceconfigtable.source_name = '"+systemmanage+"' and not exists");
		sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+userid+"' and grouptable.C630000020 = c.C650000003 and c.C650000005 = '3')");
		
		sql.append(" order by grouptable.C630000022");
		StringBuffer sbf 	= new StringBuffer();
		
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
				
//				if(grouptype.equals("2"))
//				{
//					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
//				}
//				else if(grouptype.equals("0"))
//				{
//					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
//				}
//				else if(grouptype.equals("1"))
//				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"groupissnatchgtree.jsp?gid="+groupID+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+";"+groupID+"\"));");
//				}
			}
			sbf.append("}catch(e){}");
			sbf.append("</script>");
			sbf.append("<script>document.write(tree);</script>");
			
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
		return sbf.toString();
	}
	
	/**
	 * 根据组ID查询出当前组的子组信息。（关联资源管理表与资源表）(非Demo用户)
	 * 日期 2007-1-4
	 * @author wangyanguang
	 */
	public String getGroupInfo(String groupid,String userid)
	{
		StringBuffer sql 	= new StringBuffer();
		int  userintid		= 0;
		userintid 			= Integer.parseInt(userid);
		
		sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable,"+sourcemanager+" sourcetable,"+sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
		sql.append(" and (grouptable.C630000026 = sourcetable.C650000003 or grouptable.C1 = sourcetable.C650000003) and sourcetable.C650000007 = '"+userid+"' and sourcetable.C650000005 = '3' and grouptable.C630000020='"+groupid+"'");
		sql.append(" and sourcetable.C650000001 = sourceconfigtable.source_id and sourceconfigtable.source_name = '"+systemmanage+"'" );
		
		sql.append(" order by grouptable.C630000022");
		
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
				
//				if(grouptype.equals("2"))
//				{
//	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
//				}
//				else if(grouptype.equals("0"))
//				{
//					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
//				}
//				else if(grouptype.equals("1"))
//				{
					sbf.append("<tree text=\""+groupName+"\" src=\"groupissnatchgtree.jsp?gid="+groupID+"\"  schkbox=\""+groupName+";"+groupID+"\"/>");
//				}
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
		return sbf.toString();
	}
	
	
	/**
	 * 如果用户是Demo 显示所有组信息。
	 * 日期 2006-12-30
	 * @author wangyanguang
	 */
	public String getDemoParentInfo()
	{
		StringBuffer sql 	= new StringBuffer();
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ='0' and grouptable.C630000025='0'");
		
		sql.append(" order by grouptable.C630000022");
		StringBuffer sbf 	= new StringBuffer();
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
//				if(grouptype.equals("2"))
//				{
//					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
//				}
//				else if(grouptype.equals("0"))
//				{
//					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
//				}
//				else if(grouptype.equals("1"))
//				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"groupissnatchgtree.jsp?gid="+groupID+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+";"+groupID+"\"));");
//				}
			}
			sbf.append("}catch(e){}");
			sbf.append("</script>");
			sbf.append("<script>document.write(tree);</script>");
			
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
		return sbf.toString();
	}
	
	public String getDemoGroupInfo(String groupid)
	{
		StringBuffer sql 	= new StringBuffer();
		
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ="+groupid+" and grouptable.C630000025='0'");
		sql.append(" order by grouptable.C630000022");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
//				if(grouptype.equals("2"))
//				{
//	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
//				}
//				else if(grouptype.equals("0"))
//				{
//					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
//				}
//				else if(grouptype.equals("1"))
//				{
					sbf.append("<tree text=\""+groupName+"\" src=\"groupissnatchgtree.jsp?gid="+groupID+"\"  schkbox=\""+groupName+";"+groupID+"\"/>");
//				}
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
		return sbf.toString();
	}
	
	public static void main(String[] args)
	{
		GroupIsSnatchSQL treesql = new GroupIsSnatchSQL();
		//System.out.println(treesql.getSourceInfo("92","000000000000047"));
		System.out.println(treesql.getParentSourceInfo("000000000000047"));
		
	}
}
