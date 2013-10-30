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

public class UserPhoneCheckedTreeSQL
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
	public UserPhoneCheckedTreeSQL()
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
	 * 根据组ID查询出当前组的子组信息。（关联资源管理表与资源表）(非Demo用户)
	 * 日期 2010-5-20
	 * @author wangyun
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
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  schkbox=\""+groupID+"\"/>");
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
	 * 日期 2010-5-20
	 * @author wangyun
	 */
	public String getGroupAndUserInfo(String groupid,String type)
	{
		StringBuffer sql 	= new StringBuffer();
		String selecttable 	= groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
		sql.append("select tuser.C1 userid,tuser.C630000001 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid,tgroup.c630000018 groupname,tuser.C630000017, tuser.C630000008 mobile from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0'");
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
	 	 	  	String groupName		= rs.getString("groupname");
	 	 	  	String mobile			= rs.getString("mobile"); 		//手机号
	 	 	  	if(mobile == null || mobile.equals(""))
	 	 	  		mobile = "00000000000";
	 	 	  
				if(type.equals("0"))
				{
	 	 	  		sbf.append("<tree text=\""+userName+"\"  funpram=\""+userName+":"+userID+":"+groupID+"\"/>");
				}
				else if(type.equals("1"))
				{
					sbf.append("<tree text=\""+userName+"\" schkbox=\""+userName+":"+mobile+"\" elementtype=\"2\"/>");
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
	 * 日期 2010-5-20
	 * @author wangyun
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
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
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
	
	/**
	 * 如果用户是Demo 显示所有组信息。
	 * 日期 2010-5-20
	 * @author wangyun
	 */
	public String getDemoGroupInfo(String groupid,String grouptype,String usertype,String showuser)
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
			while(rs.next())
			{
				String groupParentID 	= rs.getString("C630000020");
	 	    	String groupName     	= rs.getString("C630000018");
	 	 	  	String groupID       	= rs.getString("C1");
				if(grouptype.equals("2"))
				{
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"userphonecheckedtreeuser.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  schkbox=\""+groupID+"\"/>");
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
	

}
