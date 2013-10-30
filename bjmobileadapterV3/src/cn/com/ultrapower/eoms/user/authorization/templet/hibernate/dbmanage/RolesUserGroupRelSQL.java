package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class RolesUserGroupRelSQL
{


	String sourcemanager			= "";
	String systemmanage				= "";
	String sourceconfig				= "";
	String dutyorgnazition			= "";
	String orgnazitionarranger		= "";
	String usertablename			= "";
	String grouptablename			= "";
	String groupusertablename		= "";
	String sysskill					= "";
	String uid						= "";
	String skillaction				= "";
	/**
	 * 取得配制信息，读取配制文件
	 */
	public RolesUserGroupRelSQL()
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
			
		}
		catch(Exception e)
		{
			System.out.print("从配置表中读取数据表名时出现异常！");
		}
	}
	
	/**
	 * 根据用户ID查询用户所管理的组信息。(非Demo用户)（顶级组节点）
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
		
//		sql.append(" (select c.C1 from "+grouptablename+" c ");
//		sql.append(" where  grouptable.C630000020 = c.C1 ");
//		sql.append("  and (c.C630000026 = sourcemanagetable.C650000003 or c.C1 = sourcemanagetable.C650000003) ) order by grouptable.C630000020");
		
		sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+userid+"' and grouptable.C630000020 = c.C650000003 and c.C650000005 = '3')");
		
		sql.append(" order by grouptable.C630000022");
		
		System.out.println("用户组与角色关联中查询用户组的SQL:"+sql.toString());
		StringBuffer sbf 	= new StringBuffer();
		sbf.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
		sbf.append("try{");
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
	 	 	  	sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"rolesusergrouprelserinfotree.jsp.jsp?gid="+groupID+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":1:"+groupID+"\"));");
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
		userintid = Integer.parseInt(userid);
		sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable,"+sourcemanager+" sourcetable,"+sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
		sql.append(" and (grouptable.C630000026 = sourcetable.C650000003 or grouptable.C1 = sourcetable.C650000003) and sourcetable.C650000007 = '"+userid+"' and sourcetable.C650000005 = '3' and grouptable.C630000020='"+groupid+"'");
		sql.append(" and sourcetable.C650000001 = sourceconfigtable.source_id and sourceconfigtable.source_name = '"+systemmanage+"'" );
		
		sql.append(" order by grouptable.C630000022");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase = DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
	 	 	  sbf.append("<tree text=\""+groupName+"\" src=\"rolesusergrouprelserinfotree.jsp.jsp?gid="+groupID+"\"  funpram=\""+groupName+":1:"+groupID+"\"/>");
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
	 * 根据组ID查询出组成员信息。(用户信息)
	 * 日期 2007-1-4
	 * @author wangyanguang
	 */
	public String getGroupAndUserInfo(String groupid)
	{
		StringBuffer sql 	= new StringBuffer();
		String selecttable 	= groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
		sql.append("select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid,tuser.C630000017 from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0'");
		sql.append(" and tgroup.C1="+groupid+"");
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
	 	 	  	sbf.append("<tree text=\""+userName+"\"  funpram=\""+userName+":"+userID+":"+groupID+"\"/>");
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
	 * 如果用户是Demo 显示所有组信息。(顶级组节点)
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
	 	 	  sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"rolesusergrouprelserinfotree.jsp.jsp?gid="+groupID+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":1:"+groupID+"\"));");
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
	 * Demo用户子组信息
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public String getDemoGroupInfo(String groupid)
	{
		StringBuffer sql 	= new StringBuffer();
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ="+groupid+" and grouptable.C630000025='0'");
		sql.append(" order by grouptable.C630000022");
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
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
	 	 	  sbf.append("<tree text=\""+groupName+"\" src=\"rolesusergrouprelserinfotree.jsp.jsp?gid="+groupID+"\"  funpram=\""+groupName+":1:"+groupID+"\"/>");
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


}
