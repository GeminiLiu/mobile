package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class SendScopeTreeSQL
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
	public SendScopeTreeSQL()
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
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"));");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\""+groupName+":"+groupID+":"+groupParentID+"\"));");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("tree.add(new WebFXLoadTreeItem(\""+groupName+"\",\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\",\"\",\"\",\"\",\"\",\"\",\"\",\""+groupID+"\"));");
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
				if(grouptype.equals("2"))
				{
	 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\" />");
				}
				else if(grouptype.equals("0"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
				}
				else if(grouptype.equals("1"))
				{
					sbf.append("<tree text=\""+groupName+"\" src=\"sendscopeinfotree.jsp?gid="+groupID+";"+usertype+";"+showuser+";"+grouptype+"\"  schkbox=\""+groupID+"\"/>");
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
