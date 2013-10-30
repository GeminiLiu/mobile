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

public class MuGroupUserTree
{
	static final Logger logger = (Logger) Logger.getLogger(TreeSQL.class);
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
	public MuGroupUserTree()
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
	
	/**
	 * 查询代办人信息（根据登陆用户的部门ID查询出此用户所在部门下的所有人与组）
	 * 日期 2006-12-30
	 * @author wangyanguang
	 */
	public String getCommissionInfo(String userid,String usertype,String grouptype,String showuser)
	{
		GetUserInfoList getuserinfo = new GetUserInfoList();
		SysPeoplepo peoplepo 		= getuserinfo.getUserInfoID(userid);
		//查询出部门ID
		String departmentid = peoplepo.getC630000015();
		StringBuffer sql 	= new StringBuffer();
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable");
		sql.append(" where grouptable.C1="+departmentid+" and grouptable.C630000025='0'");
		sql.append(" order by grouptable.C630000022 ");
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
				
				if(grouptype.equals("2"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
				}
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
	 * 根据用户ID查询用户所管理的组信息。(非Demo用户)(顶级组节点)
	 * 日期 2006-12-31
	 * @author wangyanguang void
	 */
	public String getParentGroupInfo(String userid,String usertype,String grouptype,String showuser)
	{
		int  userintid		= 0;
		userintid = Integer.parseInt(userid);
		StringBuffer sql = new StringBuffer();
//		
//		sql.append("select distinct a.C1,a.C630000018,a.C630000020 from "+grouptablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0'");
//		sql.append(" and (a.C630000026 = b.C650000003 or a.C1 = b.C650000003) and b.C650000007 = '"+userid+"' and b.C650000005 = '3'");
//		sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"' and not exists" );
//		sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+userid+"' and a.C630000020 = c.C650000003 and c.C650000005 = '3')");
//		sql.append(" order by a.C630000020");
		
		sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from ");
		sql.append(grouptablename+" grouptable,"+sourcemanager+" sourcemanagetable,");
		sql.append(sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
		sql.append(" and (grouptable.C1 = sourcemanagetable.C650000003) ");
		sql.append(" and sourcemanagetable.C650000007 = '"+userid+"' and sourcemanagetable.C650000005 = '3'");
		sql.append(" and sourcemanagetable.C650000001 = sourceconfigtable.source_id " );
		sql.append(" and sourceconfigtable.source_name = '"+systemmanage+"' and not exists");
		//sql.append(" (select c.C1 from "+grouptablename+" c ");
		//sql.append(" where  grouptable.C630000020 = c.C1 ");
		//sql.append("  and (c.C630000026 = sourcemanagetable.C650000003 or c.C1 = sourcemanagetable.C650000003) ) order by grouptable.C630000020");
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
				
				if(grouptype.equals("2"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
				}
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
	public String getGroupInfo(String groupid,String grouptype,String usertype,String showuser,String userid)
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
				
				if(grouptype.equals("2"))
				{
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  schkbox=\""+groupID+"\"/>");
				}
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
	 * 根据组ID查询出子组及组成员信息。
	 * 日期 2007-1-4
	 * @author wangyanguang
	 */
	public String getGroupAndUserInfo(String groupid,String type)
	{
		StringBuffer sql 	= new StringBuffer();
		String selecttable 	= groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
		sql.append("select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid,tuser.C630000017 from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0'");
		sql.append(" and tgroup.C1="+groupid+" and tgroup.C630000025='0'");
		sql.append(" order by tuser.C630000017");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm	= dataBase.GetStatement();
		ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("userlogname");
	 	    	String userName     	= rs.getString("userfullname");
	 	 	  	String userID       	= rs.getString("userid");
	 	 	  	String groupID			= rs.getString("groupid");
				if(type.equals("0"))
				{
	 	 	  		sbf.append("<tree text=\""+userName+"\"  funpram=\""+userName+":"+userID+":"+groupID+"\" elementtype=\"2\"/>");
				}
				else if(type.equals("1"))
				{
					sbf.append("<tree text=\""+userName+"\" schkbox=\""+userID+";"+groupID+"\" elementtype=\"2\"/>");
				}
				else if(type.equals("2"))
				{
					sbf.append("<tree text=\""+userName+"\" elementtype=\"2\"/>");
				}
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
	public String getDemoParentInfo(String usertype,String grouptype,String showuser)
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
				if(grouptype.equals("2"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
				}
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
	
	public String getDemoGroupInfo(String groupid,String grouptype,String usertype,String showuser,String existsuser)
	{
		StringBuffer sql 	= new StringBuffer();
		
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ="+groupid+"  and grouptable.C630000025='0'");
		sql.append(" order by grouptable.C630000022");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			if(String.valueOf(existsuser).equals("yes"))
			{
				sbf.append("<tree text=\"反 选\" elementtype=\"3\"/>");
			}
			
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
				if(grouptype.equals("2"))
				{
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" elementtype=\"1\"/>");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\" elementtype=\"1\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userwebnexttree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  schkbox=\""+groupID+"\" elementtype=\"1\"/>");
				}
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
		logger.info(sql);
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
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\""+sourcename+":"+sourceid+":"+sourceparentid+"\"));");
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
	
	//	当配置主班人时,查询出DEMO用户的资源信息。
	public String getDemoParentSourceInfo1(String type,String flag)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sourceconfigtable.source_id,sourceconfigtable.source_parentid,sourceconfigtable.source_cnname,sourceconfigtable.source_orderby from "+ sourceconfig +" sourceconfigtable");
		sql.append(" where sourceconfigtable.source_parentid='0'");
		//当资源管理者类型为管理员时,资源树只显示系统管理节点
		if(flag.equals("3"))
		{
			sql.append(" and sourceconfigtable.source_name = '"+systemmanage+"'");
		}
		
		if(type.equals("1"))
		{
			sql.append(" and sourceconfigtable.source_type like '%1;%'");
		}
		sql.append(" order by sourceconfigtable.source_orderby");
		logger.info(sql);
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
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\""+sourcename+":"+sourceid+":"+sourceparentid+"\"));");
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
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\"  funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\" />");
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
		sql.append(  roleskillmanagetable+" roleskillmanage,");              
		sql.append(  usergrouprel +"  rolegroupuserrel,");
		sql.append(  rolemanagetable + " rolemanagetable,");
		sql.append(  groupusertablename+" groupuser");          
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");                        
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		sql.append(" and groupuser.c620000028 = '"+userid+"'"); 
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");  
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		
		sql.append(" and roleskillmanage.c660000009 = '"+skillaction+"'");           
		sql.append(" )  uu");  
//		sql.append(" and groupuser.c620000027 = rolegroupuserrel.c660000027)  uu");         
		sql.append(" where 1 = 1 and not exists( ");           
//		sql.append(" (select  source_id,source_parentid,source_cnname,source_orderby "); 
		sql.append(" select sourcetable.source_id ");   
		sql.append(" from sourceconfig sourcetable, "); 
		sql.append(  sysskill+" skilltable");      
		sql.append(" where sourcetable.source_id = skilltable.C610000008 ");               
		sql.append(" and skilltable.C610000010 = '"+skillaction+"'");               
		sql.append(" and skilltable.c610000007 = '"+userid+"'");                        
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		sql.append(" and sourcetable.source_id = uu.source_parentid) ");
		sql.append(" and not exists( ");
		sql.append(" select sourcetable.source_id ");
		sql.append(" from sourceconfig sourcetable,");        
		sql.append(  roleskillmanagetable+" roleskillmanage,");              
		sql.append(  usergrouprel +"  rolegroupuserrel,");
		sql.append(  rolemanagetable + " rolemanagetable,");              
		sql.append(  groupusertablename+" groupuser");          
		sql.append(" where sourcetable.source_id = roleskillmanage.c660000007");                        
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028"); 
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
		
		sql.append(" and roleskillmanage.c660000009 = '"+skillaction+"'");           
		sql.append(" and groupuser.c620000028 = '"+userid+"'"); 
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		sql.append(" and sourcetable.source_id = uu.source_parentid)");
		sql.append(" order by source_orderby,source_parentid,source_id");             
		
		logger.info("非Demo用户的资源树SQL:"+sql);
		
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
	 	 	  sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\""+sourcename+":"+sourceid+":"+sourceparentid+"\"));");
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
	
	//当配置主班人时,查询出非DEMO用户的资源信息。
	//关联查询技能表与资源表逻辑：select * from 资源表 and 技能表 where 资源表的ID=技能表中的资源字段ID and 技能表中的权限=1000
	public String getParentSourceInfo1(String userid,String type,String flag)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select a.source_id,a.source_parentid,a.source_cnname,a.source_orderby from "+ sourceconfig +" a,"+ sysskill +" b");
		sql.append(" where a.source_id = b.C610000008 and b.C610000010='"+skillaction+"' and b.c610000007="+userid+" and not exists");
		sql.append(" (select c.C610000008 from "+sysskill +" c where c.C610000008=a.source_parentid and c.C610000010='"+skillaction+"' and c.c610000007="+userid+")");
		//当资源管理者类型为管理员时,资源树只显示系统管理节点
		if(flag.equals("3"))
		{
			sql.append(" and a.source_name = '"+systemmanage+"'");
		}
		if(type.equals("1"))
		{
			sql.append(" and a.source_type like '%1;%'");
		}
		sql.append(" and b.c610000018='0'");
		sql.append(" order by a.source_orderby,a.source_id,a.source_parentid");
		logger.info(sql);
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
	 	 	  sbf.append("tree.add(new WebFXLoadTreeItem(\""+sourcename+"\",\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\",\"\",\"\",\"\",\"\",\"\",\""+sourcename+":"+sourceid+":"+sourceparentid+"\"));");
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
		sql.append(" and sourcetable.source_parentid ='"+parentsourceid+"'");      
		sql.append(" and groupuser.c620000028 = '"+userid+"'");     
//		sql.append(" and groupuser.c620000027=rolegroupuserrel.c660000027");      
		sql.append(" and rolegroupuserrel.c660000028=roleskillmanage.c660000006");
		//新加的权限控制
		sql.append(" and ((rolegroupuserrel.c660000026 = groupuser.c620000028 and");		           
		sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027) or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");	
		
		if(type.equals("1"))
		{
			sql.append(" and sourcetable.source_type like '%1;%'");
		}
		      
		sql.append(" order by source_orderby,source_id,source_parentid");
		
		logger.info("资源下一级节点SQL："+sql.toString());
		
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
	 	 	  	sbf.append("<tree text=\""+sourcename+"\" src=\"sourceinfotree.jsp?sourceid="+sourceid+";"+type+"\"   funpram=\""+sourcename+":"+sourceid+":"+sourceparentid+"\"/>");
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

	public static void main(String args[])
	{
		TreeSQL treesql = new TreeSQL();
		System.out.println(treesql.getSourceInfo("92","000000000000047",""));
	}
}
