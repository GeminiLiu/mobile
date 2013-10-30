package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.SendScopeInterfaceSQL;

public class PortaletSubSQL
{

	static final Logger logger = (Logger) Logger.getLogger(PortaletSubSQL.class);
	private String sourcemanager			= "";
	private String systemmanage				= "";
	private String sourceconfig				= "";
	private String dutyorgnazition			= "";
	private String orgnazitionarranger		= "";
	private String usertablename			= "";
	private String grouptablename			= "";
	private String groupusertablename		= "";
	private String sysskill					= "";
	private String uid						= "";
	private String skillaction				= "";
	private String roleskillmanagetable		= "";
	private String usergrouprel				= "";
	private String rolemanagetable			= "";
	/**
	 * 从配制文件中读取表的配制信息
	 */
	public PortaletSubSQL()
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
			rolemanagetable		 = getTableProperty.GetFormName("RemedyTrolesmanage");
			
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}

	
	//查询出DEMO用户的资源信息。
	public String getDemoParentSourceInfo(String type)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sourceconfigtable.source_id,sourceconfigtable.source_parentid,sourceconfigtable.source_cnname,sourceconfigtable.source_orderby from "+ sourceconfig +" sourceconfigtable");
		sql.append(" where sourceconfigtable.source_parentid='0'");
		if(type.equals("1"))
		{
			sql.append(" and sourceconfigtable.source_type like '%1;%'");
		}
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
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"portaletchecksourcetree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+sourceid+"\"));");
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
	public String getDemoSourceInfo(String parentsourceid,String type)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sourceconfigtable.source_id,sourceconfigtable.source_parentid,sourceconfigtable.source_cnname,sourceconfigtable.source_orderby from "+ sourceconfig +" sourceconfigtable");
		sql.append(" where sourceconfigtable.source_parentid="+parentsourceid);
		if(type.equals("1"))
		{
			sql.append(" and sourceconfigtable.source_type like '%1;%'");
		}
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
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"portaletchecksourcetree.jsp?sourceid="+sourceid+";"+type+"\"  schkbox=\""+sourceid+"\" />");
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
	public String getParentSourceInfo(String userid,String type)
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		String fordersource = "";
		
		sql.append(" select distinct source_id,source_parentid,source_cnname,source_orderby");
		sql.append(" from (select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_cnname,sourcetable.source_orderby");
		sql.append(" from sourceconfig sourcetable,");
		sql.append(sysskill+" skilltable"); 
		sql.append(" where sourcetable.source_id = skilltable.C610000008 ");               
		sql.append(" and skilltable.C610000010 = '"+skillaction+"'");               
		sql.append(" and skilltable.c610000007 = '"+userid+"'"); 
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		sql.append(" union ");
		sql.append(" select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_cnname,sourcetable.source_orderby");
		sql.append(" from sourceconfig sourcetable,");        
		sql.append(roleskillmanagetable+" roleskillmanage,");              
		sql.append(usergrouprel +"  rolegroupuserrel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(groupusertablename+" groupuser");          
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");                        
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");         
		sql.append(" and roleskillmanage.c660000009 = '"+skillaction+"'");           
		sql.append(" and groupuser.c620000028 = '"+userid+"'"); 
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		sql.append(" )  uu");         
//		sql.append(" and groupuser.c620000027 = rolegroupuserrel.c660000027)  uu");         
		sql.append(" where 1 = 1 and not exists");           
		sql.append(" (select sourcetable.source_id");   
		sql.append(" from sourceconfig sourcetable, "); 
		sql.append(  sysskill+" skilltable");      
		sql.append(" where sourcetable.source_id = skilltable.C610000008 ");               
		sql.append(" and skilltable.C610000010 = '"+skillaction+"'");               
		sql.append(" and skilltable.c610000007 = '"+userid+"'");                        
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
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
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		sql.append(" and sourcetable.source_id = uu.source_parentid)");     
		sql.append(" order by source_orderby,source_parentid,source_id"); 
		
		System.out.println(sql);
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
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"portaletchecksourcetree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+sourceid+"\"));");
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
	public String getSourceInfo(String parentsourceid,String userid,String type)
	{
		StringBuffer sql = new StringBuffer();
//		sql.append("select a.source_id,a.source_parentid,a.source_cnname from "+ sourceconfig +" a,"+ sysskill +" b");
//		sql.append(" where a.source_id = b.C610000008 and b.C610000010='"+skillaction+"' and b.c610000007="+userid );
//		sql.append(" and a.source_parentid="+parentsourceid);
//		
//		if(type.equals("1"))
//		{
//			sql.append(" and a.source_type like '%1;%'");
//		}
//		sql.append(" and b.c610000018='0' ");
//		sql.append(" order by a.source_id,a.source_parentid");
		
		sql.append("select sourcetable.source_id,sourcetable.source_parentid,sourcetable.source_cnname,sourcetable.source_orderby from "+ sourceconfig +" sourcetable,"+ sysskill +" skilltable");
		sql.append(" where sourcetable.source_id = skilltable.C610000008 and skilltable.C610000010='"+skillaction+"' and skilltable.c610000007="+userid );
		sql.append(" and sourcetable.source_parentid='"+parentsourceid+"'");
		
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		sql.append(" and skilltable.c610000018='0' ");
		sql.append(" union ");
		sql.append(" select sourcetable.source_id,sourcetable.source_parentid, sourcetable.source_cnname,sourcetable.source_orderby");
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
//		sql.append(" and groupuser.c620000027=rolegroupuserrel.c660000027");      
		sql.append(" and rolegroupuserrel.c660000028=roleskillmanage.c660000006");
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		      
		sql.append(" order by source_orderby,source_id,source_parentid");
		
		
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String sourceid 			= rs.getString("source_id");
	 	    	String sourceparentid     	= rs.getString("source_parentid");
	 	 	  	String sourcename       	= rs.getString("source_cnname");
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"portaletchecksourcetree.jsp?sourceid="+sourceid+";"+type+"\"  schkbox=\""+sourceid+"\" />");
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
	
	
}
