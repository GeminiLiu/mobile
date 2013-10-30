package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.GroupAndUserInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceManagerParm;
import cn.com.ultrapower.eoms.user.comm.function.Function;

/**
 * <p>Description:获得该用户审批人</p>
 * @author wangwenzhuo
 * @creattime 2007-1-16
 */
public class JDBCGetAuditUser {
	
	static final Logger logger 	= (Logger) Logger.getLogger(JDBCGetAuditUser.class);
	
	GetFormTableName getFormTableName		= new GetFormTableName();
	String sourcemanager					= getFormTableName.GetFormName("RemedyTsourceManager");
	String grouptable						= getFormTableName.GetFormName("RemedyTgroup");
	String peopletable						= getFormTableName.GetFormName("RemedyTpeople");
	String sourceconfig						= getFormTableName.GetFormName("sourceconfig");
	String sysskill							= getFormTableName.GetFormName("RemedyTrole");
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");

	/**
	 * <p>Description:获得审批人</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-16
	 * @param sourceManagerParm
	 * @return List
	 */
	public List getUserOfAudit(SourceManagerParm sourceManagerParm)
	{
		//全局变量sql
		String sql	= "";
		//作为返回参数的List变量
		List list	= new ArrayList();
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		//程序员条件,默认情况下为0
		String gotoFlag		= sourceManagerParm.getGotoflag();
		
		if(gotoFlag.equals("0"))
		{
			sql = sqlFlagEqualZero(sourceManagerParm);
			System.out.println(sql);
		}
		else
		{
			sql = "";
		}
		
		try
		{
			if(sql==null||sql.equals(""))
			{
				logger.info("[543]RoleGrandInterface.getUserOfAudit() SourceManagerParm数据不全");
				return null;
			}
			else
			{
				stm	= dataBase.GetStatement();
				//获得数据库查询结果集
				rs	= dataBase.executeResultSet(stm,sql);
				
				String userIntId	= "";
				String userFullName	= "";
				
				while(rs.next())
				{
					userIntId		= rs.getString("C630000029");
					userFullName	= rs.getString("C630000003");
					
					GroupAndUserInfo beanInfo = new GroupAndUserInfo();
					beanInfo.setUserIntId(Integer.parseInt(userIntId));
					beanInfo.setUserFullname(userFullName);
					
					list.add(beanInfo);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("[543]RoleGrandInterface.getUserOfAudit() 获得该用户的审批人失败"+e.getMessage());
			return null;
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return list;
	}
	
	/**
	 * <p>Description:当gotoflag为0时的sql条件</p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-16
	 * @param sourceManagerParm
	 * @return String
	 */
	public String sqlFlagEqualZero(SourceManagerParm sourceManagerParm)
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			String sourcemanager_userid		= sourceManagerParm.getSourcemanager_userid();
			String sourcemanager_type		= sourceManagerParm.getSourcemanager_type();
			String sourcemanager_sourceid	= sourceManagerParm.getSourcemanager_sourceid();
			//grandAction数值9代表授权权限
			String grandAction				= sourceManagerParm.getGrandaction();
			if(sourcemanager_userid==null||sourcemanager_userid.equals(""))
			{
				return null;
			}
			else if(sourcemanager_type==null||sourcemanager_type.equals(""))
			{
				return null;
			}
			else if(sourcemanager_sourceid==null||sourcemanager_sourceid.equals(""))
			{
				return null;
			}
			else if(grandAction==null||grandAction.equals(""))
			{
				return null;
			}
			
			sql.append("select distinct b.C630000029,b.C630000003 from "+sourcemanager+" a,"+peopletable+" b,"+sysskill+" c where a.C650000001 ='"+sourcemanager_sourceid+"'");
			sql.append(" and a.C650000004 = '"+sourcemanager_userid+"' and a.C650000005 = '"+sourcemanager_type+"'");
			sql.append(" and a.C650000007 = c.C610000007 and c.C610000008 = '"+sourcemanager_sourceid+"'");
			sql.append(" and b.C1 = a.C650000007");
			
			String[] temp_grandAction		= grandAction.split(",");
			//若只有一个值
			if(temp_grandAction.length==1)
			{
				sql.append(" and c.C610000010 = '"+grandAction+"'");
			}
			else
			{
				boolean firsttime = false;
				for(int i = 0;i<temp_grandAction.length;i++)
				{
					if(!firsttime)
					{
						sql.append(" and (c.C610000010 = '"+temp_grandAction[i]+"'");
						firsttime = true;
					}
					else
					{
						sql.append(" or c.C610000010 = '"+temp_grandAction[i]+"'");
					}
				}
				sql.append(")");
			}
			//增加审批人的联合查询
			//RemedyTrolesusergrouprel
			//RemedyTrolesskillmanage
			//RemedyTgroupuser
			sql.append(" union ");
			sql.append("select distinct b.C630000029,b.C630000003 from "+sourcemanager+" a,"+peopletable+" b,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTgroupuser+" RemedyTgroupuser where a.C650000001 ='"+sourcemanager_sourceid+"'");
			sql.append(" and a.C650000004 = '"+sourcemanager_userid+"' and a.C650000005 = '"+sourcemanager_type+"'");
			sql.append(" and RemedyTrolesskillmanage.C660000007 = '"+sourcemanager_sourceid+"'");
			sql.append(" and RemedyTrolesskillmanage.C660000006 = RemedyTrolesusergrouprel.c660000028 and RemedyTrolesusergrouprel.C660000027=RemedyTgroupuser.C620000027 and RemedyTgroupuser.C620000028=a.C650000007");
			sql.append(" and b.C1 = a.C650000007");
			if(temp_grandAction.length==1)
			{
				sql.append(" and RemedyTrolesskillmanage.C660000009 = '"+grandAction+"'");
			}
			else
			{
				boolean firsttime = false;
				for(int i = 0;i<temp_grandAction.length;i++)
				{
					if(!firsttime)
					{
						sql.append(" and (RemedyTrolesskillmanage.C660000009 = '"+temp_grandAction[i]+"'");
						firsttime = true;
					}
					else
					{
						sql.append(" or RemedyTrolesskillmanage.C660000009 = '"+temp_grandAction[i]+"'");
					}
				}
				sql.append(")");
			}
			
			return sql.toString();
		}
		catch(Exception e)
		{
			logger.error("[544]RoleGrandInterface.sqlFlagEqualZero() 拼获得该用户的审批人sql失败"+e.getMessage());
			return null;
		}
	}
	
}
