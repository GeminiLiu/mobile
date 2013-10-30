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
import cn.com.ultrapower.eoms.user.userinterface.UserSourceGrandInterfaceSQL;

public class CommissionTreeSQL
{
	static final Logger logger = (Logger) Logger.getLogger(CommissionTreeSQL.class);
		String tablename				= "";
		String sourcemanager			= "";
		String systemmanage				= "";
		String sourceconfig				= "";
		String dutyorgnazition			= "";
		String orgnazitionarranger		= "";
		String usertablename			= "";
		String grouptablename			= "";
		String groupusertablename		= "";
		String uid						= "";
		public CommissionTreeSQL()
		{
			GetFormTableName getTableProperty	= new GetFormTableName();
			try
			{
				tablename			= getTableProperty.GetFormName("RemedyTgroup");
				sourcemanager		= getTableProperty.GetFormName("RemedyTsourceManager");
				systemmanage		= getTableProperty.GetFormName("systemmanage");
				sourceconfig		= getTableProperty.GetFormName("sourceconfig");
				dutyorgnazition		= getTableProperty.GetFormName("dutyorgnazition");
				orgnazitionarranger	= getTableProperty.GetFormName("orgnazitionarranger");
				usertablename		= getTableProperty.GetFormName("RemedyTpeople");
				grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
				groupusertablename	= getTableProperty.GetFormName("RemedyTgroupuser");
			}
			catch(Exception e)
			{
				logger.error("从配置表中读取数据表名时出现异常！");
			}
		}
		
		/**
		 * 查询代办人信息（根据登陆用户的部门ID查询出此用户所在部门下的所有人与组） 日期 2006-12-30
		 * 
		 * @author wangyanguang
		 */
		public String getCommissionInfo(String userid,String type)
		{
			GetUserInfoList getuserinfo = new GetUserInfoList();
			SysPeoplepo peoplepo = getuserinfo.getUserInfoID(userid);
			// 查询出部门ID
			String departmentid = peoplepo.getC630000015();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+tablename+" a");
			sql.append(" where a.C1="+departmentid+"");
			sql.append(" order by a.C630000022");
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("C630000020");
		 	    	String groupName     	= rs.getString("C630000018");
		 	 	  	String groupID       	= rs.getString("C1");
					
					if(type.equals("2"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\" />");
					}
					else if(type.equals("0"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  schkbox=\""+groupID+"\"/>");
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
		 * 根据用户ID查询用户所管理的组信息。(非Demo用户) 日期 2006-12-31
		 * 
		 * @author wangyanguang void
		 */
		public String getParentGroupInfo(String userid,String type)
		{
			StringBuffer sql = new StringBuffer();
			
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0'");
			sql.append(" and ( a.C1 = b.C650000003) and b.C650000007 = '"+userid+"' and b.C650000005 = '3'");
			sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"' and not exists" );
			sql.append(" (select c.C650000003 from "+sourcemanager+" c where c.C650000007 = '"+userid+"' and a.C630000020 = c.C650000003 and c.C650000005 = '3')");
			sql.append(" order by a.C630000022");
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("C630000020");
		 	    	String groupName     	= rs.getString("C630000018");
		 	 	  	String groupID       	= rs.getString("C1");
					
					if(type.equals("2"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\" />");
					}
					else if(type.equals("0"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  schkbox=\""+groupID+"\"/>");
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
		 * 根据组ID查询出当前组的子组信息。（关联资源管理表与资源表）(非Demo用户) 日期 2007-1-4
		 * 
		 * @author wangyanguang
		 */
		public String getGroupInfo(String groupid,String type)
		{
			StringBuffer sql 	= new StringBuffer();
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+tablename+" a,"+sourcemanager+" b,"+sourceconfig+" c where a.C630000025 = '0'");
			sql.append(" and (a.C1 = b.C650000003) and b.C650000007 = '"+uid+"' and b.C650000005 = '3' and a.C630000020='"+groupid+"'");
			sql.append(" and b.C650000001 = c.source_id and c.source_name = '"+systemmanage+"'" );
			sql.append(" order by a.C630000022");
// sql1.append("select a.C1,a.C630000018,a.C630000020 from "+tablename+"
// a,"+dutyorgnazition+" b,"+orgnazitionarranger+" c where a.C630000025 = '0'");
// sql1.append(" and (a.C630000026 = b.DEPARTMENT_ID or a.C1 = b.DEPARTMENT_ID)
// and c.ARRANGER_ID='"+id+"' and ");
// sql1.append(" b.ORGANIZATION_ID=c.ORGANIZATION_ID");
			
			// 值班管理员
// select
// a.C1,a.C630000018,a.C630000020,b.department_id,b.department_id,c.arranger_id
// from t72 a,Duty_Organization b,Organization_Arranger c
// where a.C630000025 = '0' and (a.C630000026 = b.DEPARTMENT_ID or a.C630000030
// = b.DEPARTMENT_ID)
// and c.ARRANGER_ID='user_int_id' and b.ORGANIZATION_ID=c.ORGANIZATION_ID
			
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("C630000020");
		 	    	String groupName     	= rs.getString("C630000018");
		 	 	  	String groupID       	= rs.getString("C1");
					
					if(type.equals("2"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\" />");
					}
					else if(type.equals("0"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  schkbox=\""+groupID+"\"/>");
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
		 * 根据组ID查询出子组及组成员信息。 日期 2007-1-4
		 * 
		 * @author wangyanguang
		 */
		public String getGroupAndUserInfo(String groupid,String type)
		{
			StringBuffer sql = new StringBuffer();
			String selecttable=groupusertablename+" tgroupuser,"+grouptablename+" tgroup,"+usertablename+" tuser";
			sql.append("select tuser.C1 userid,tuser.C630000001,tuser.C630000017 userlogname,tuser.C630000003 userfullname,tgroup.C1 groupid from "+ selecttable +" where 1=1 and tgroupuser.C620000027=tgroup.C1 and tgroupuser.C620000028=tuser.C1 and tuser.C630000012='0'");
			sql.append(" and tgroup.C1="+groupid+"");
			sql.append(" and tgroup.C630000025='0'");
			sql.append(" order by tuser.C630000017");
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("userfullname");
					
		 	    	String groupName     	= rs.getString("userlogname");
		 	 	  	String groupID       	= rs.getString("userid");
					// sbf.append("<tree text=\""+groupName+"\"
					// src=\"userwebnexttree.jsp\" schkbox=\""+groupID+"\"/>");
					if(type.equals("0"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp\"  schkbox=\""+groupID+"\"/>");
					}
					else if(type.equals("2"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp\"/>");
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
		 * 如果用户是Demo 显示所有组信息。 日期 2006-12-30
		 * 
		 * @author wangyanguang
		 */
		public String getDemoParentInfo(String type)
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+tablename+" a  where a.C630000020 ='0'");
			sql.append(" order by a.C630000022");
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("C630000020");
		 	    	String groupName     	= rs.getString("C630000018");
		 	 	  	String groupID       	= rs.getString("C1");
					if(type.equals("2"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\" />");
					}
					else if(type.equals("0"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  schkbox=\""+groupID+"\"/>");
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
		
		public String getDemoGroupInfo(String groupid,String type)
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select a.C1,a.C630000018,a.C630000020,a.C630000022 from "+tablename+" a  where a.C630000020 ="+groupid+"");
			sql.append(" order by a.C630000022");
			StringBuffer sbf = new StringBuffer();
			IDataBase dataBase	= null;
			dataBase = DataBaseFactory.createDataBaseClassFromProp();
			// 获得数据库查询结果集
			Statement stm	= dataBase.GetStatement();
			ResultSet rs	= dataBase.executeResultSet(stm,sql.toString());
			try
			{
				while(rs.next())
				{
					String groupParentID 	= rs.getString("C630000020");
		 	    	String groupName     	= rs.getString("C630000018");
		 	 	  	String groupID       	= rs.getString("C1");
					if(type.equals("2"))
					{
		 	 	  		sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\" />");
					}
					else if(type.equals("0"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  funpram=\""+groupName+":"+groupID+":"+groupParentID+"\"/>");
					}
					else if(type.equals("1"))
					{
						sbf.append("<tree text=\""+groupName+"\" src=\"rolecommissionnexttree.jsp?gid="+groupID+"\"  schkbox=\""+groupID+"\"/>");
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


