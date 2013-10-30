package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class RoleCheckedTreeSQL
{
	static final Logger logger = (Logger) Logger.getLogger(RoleCheckedTreeSQL.class);
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
	public RoleCheckedTreeSQL()
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
			sysskill			= getTableProperty.GetFormName("RemedyTsysskill");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	

	/**
	 * 根据用户ID查询用户所管理的组信息。(非Demo用户)
	 * 日期 2006-12-31
	 * @author wangyanguang void
	 */
	public String getParentGroupInfo(String userid,String usertype,String grouptype,String showuser)
	{
		int  userintid			= 0;
		//标识ResultSet结果集是否为值班管理员信息的，如果是：1 否：0
		String deparementtype 	= "0";
		userintid 				= Integer.parseInt(userid);
		StringBuffer sql 		= new StringBuffer();
		StringBuffer sql1 		= new StringBuffer();
		
		sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from ");
		sql.append(grouptablename+" grouptable,"+sourcemanager+" sourcemanagetable,");
		sql.append(sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
		sql.append(" and ( grouptable.C1 = sourcemanagetable.C650000003) ");
		sql.append(" and sourcemanagetable.C650000007 = '"+userid+"' and sourcemanagetable.C650000005 = '3'");
		sql.append(" and sourcemanagetable.C650000001 = sourceconfigtable.source_id " );
		sql.append(" and sourceconfigtable.source_name = '"+systemmanage+"' and not exists");
		//sql.append(" (select c.C1 from "+grouptablename+" c ");
		//sql.append(" where  grouptable.C630000020 = c.C1 ");
		//sql.append("  and (c.C630000026 = sourcemanagetable.C650000003 or c.C1 = sourcemanagetable.C650000003) ) order by grouptable.C630000020");
		
		sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+userid+"' and grouptable.C630000020 = c.C650000003 and c.C650000005 = '3')");
		sql.append(" order by grouptable.C630000022");
		logger.info("sql:"+sql.toString());
		
		
		//查询用户的部门及值班组SQL
		sql1.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022");
		sql1.append(" from "+grouptablename+" grouptable,"+dutyorgnazition+" dutyorgnazitiontable, ");
		sql1.append(orgnazitionarranger+" orgarrangertable,"+ usertablename+" usertable ");
		sql1.append(" where grouptable.C630000025 = '0'");
		sql1.append(" and (grouptable.C630000026 = dutyorgnazitiontable.company_id )");
		sql1.append(" and orgarrangertable.ARRANGER_ID='"+userintid+"' and ");
		sql1.append(" dutyorgnazitiontable.ORGANIZATION_ID=orgarrangertable.ORGANIZATION_ID");
		sql1.append(" and exists (select * from "+grouptablename+" t3 where t3.c630000037 like '%'||grouptable.c1||';%' and t3.c630000021='4')");
		sql1.append(" and grouptable.c1=usertable.c630000015  and usertable.c1="+userid+"");
		sql1.append(" order by grouptable.C630000022");
		System.out.println(sql1.toString());
		
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
			if(!rs.next())
			{	
				rs.close();
				logger.info("不是管理员，以下是查询值班管理员！");
				rs 				= dataBase.executeResultSet(stm,sql1.toString());
				showuser 		= "0";
				deparementtype 	= "1";
			}
			else
			{
				logger.info("是管理员！！！");
				rs.previous();
			}
			
		}
		catch(Exception e)
		{
			logger.error("查询ResultSet 时出现异常!");
		}
		
		try
		{
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
				
				if(grouptype.equals("2"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+deparementtype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+deparementtype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+deparementtype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
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
	public String getGroupInfo(String groupid,String grouptype,String usertype,String showuser,String userid,String departmenttype)
	{
		StringBuffer sql 	= new StringBuffer();
		StringBuffer sql1 	= new StringBuffer();
		if(departmenttype.equals("0"))
		{
			sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable,"+sourcemanager+" sourcetable,"+sourceconfig+" sourceconfigtable where grouptable.C630000025 = '0'");
			sql.append(" and (grouptable.C630000026 = sourcetable.C650000003 or grouptable.C1 = sourcetable.C650000003) and sourcetable.C650000007 = '"+userid+"' and sourcetable.C650000005 = '3' and grouptable.C630000020='"+groupid+"'");
			sql.append(" and sourcetable.C650000001 = sourceconfigtable.source_id and sourceconfigtable.source_name = '"+systemmanage+"'" );
		}
		else if(departmenttype.equals("1"))
		{
			showuser = "1";
			sql.append("select distinct grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 ");
			sql.append(" from "+grouptablename+" grouptable,"+dutyorgnazition+" dutyorgnazitiontable,");
			sql.append(" "+orgnazitionarranger+" orgarrangertable");
			sql.append(" where grouptable.C630000025 = '0' and ");
			sql.append(" grouptable.C630000026 = dutyorgnazitiontable.company_id");
			sql.append(" and orgarrangertable.ARRANGER_ID="+userid+"");
			sql.append(" and dutyorgnazitiontable.ORGANIZATION_ID=orgarrangertable.ORGANIZATION_ID");
			sql.append(" and exists( select * from "+grouptablename+" t3");
			sql.append(" where t3.c630000037 like '%'||grouptable.c1||';%' and t3.c630000021='4')");
			sql.append(" and grouptable.C630000020="+groupid+"");
		}

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
				
				if(grouptype.equals("2"))
				{
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+departmenttype+"\" />");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+departmenttype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"rolecheckedinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+";"+departmenttype+"\"  schkbox=\""+groupID+"\"/>");
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
		sql.append(" and tgroup.C1="+groupid+"");
		sql.append(" order by tuser.C630000017");
		StringBuffer sbf 	= new StringBuffer();
		IDataBase dataBase	= null;
		dataBase 			= DataBaseFactory.createDataBaseClassFromProp();
		//获得数据库查询结果集
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
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
	 	 	  		sbf.append("<tree text=\""+userName+"\"  funpram=\""+userName+":"+userID+":"+groupID+"\"/>");
				}
				else if(type.equals("1"))
				{
					sbf.append("<tree text=\""+userName+"\"   schkbox=\""+userID+";"+groupID+"\"/>");
				}
				else if(type.equals("2"))
				{
					sbf.append("<tree text=\""+userName+"\" />");
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
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ='0' and grouptable.C630000025='0' ");
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
	
	public String getDemoGroupInfo(String groupid,String grouptype,String usertype,String showuser)
	{
		StringBuffer sql 	= new StringBuffer();
		sql.append("select grouptable.C1,grouptable.C630000018,grouptable.C630000020,grouptable.C630000022 from "+grouptablename+" grouptable  where grouptable.C630000020 ="+groupid+" and grouptable.C630000025='0' ");
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
	
	/**
	 * 判断用户是否为值班管理员，是返回真，否返回假。
	 * 日期 2007-1-5
	 * @author wangyanguang
	 */
	 public boolean isDutyArranger(String userid)
	 {
		  boolean flag 		= false;
		  StringBuffer sql 	= new StringBuffer();
		  sql.append("select * from  duty_arranger  a  where a.arranger_id ="+userid+"");
		  IDataBase dataBase 	= null;
		  dataBase 				= DataBaseFactory.createDataBaseClassFromProp();
		  Statement stm 		= dataBase.GetStatement();
		  ResultSet rs 			= dataBase.executeResultSet(stm,sql.toString());
		  try
		  {
			   if(rs.next())
			   {
				  flag = true;
			   }
			   rs.close();
			   stm.close();
			   dataBase.closeConn();
		  }
		  catch(Exception e)
		  {
			   e.printStackTrace();
			   return false;
		  }
		  finally
		  {
			  Function.closeDataBaseSource(rs,stm,dataBase);
		  }
		  return flag;
	 }
 
	/**
	 * 判断用户是否为管理员，是返回真，否返回假。
	 * 日期 2007-1-5
	 * @author wangyanguang
	 */
	 public boolean isSourceManager(String userid)
	 {
		 boolean flag 		= false;
		 StringBuffer sql = new StringBuffer();
		 sql.append("select a.c650000003 from "+sourcemanager+" a,"+sourceconfig+" b");
		 sql.append(" where a.c650000007 = '"+userid+"' and a.c650000005 = '3'");
		 sql.append(" and a.c650000001 = b.source_id and b.source_name = '"+systemmanage+"'");
	 
		 IDataBase dataBase = null;
		 dataBase = DataBaseFactory.createDataBaseClassFromProp();
		 Statement stm = dataBase.GetStatement();
		 ResultSet rs = dataBase.executeResultSet(stm,sql.toString());
		 try
		 {
			 if(rs.next())
			 {
				 flag = true;
			 }
			 rs.close();
			 stm.close();
			 dataBase.closeConn();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return false;
		 }
		 finally
		 {
			 Function.closeDataBaseSource(rs,stm,dataBase);
		 }
	  return flag;
	 }
	 
	 
}
