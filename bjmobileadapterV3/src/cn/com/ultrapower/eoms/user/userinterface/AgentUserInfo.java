package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.SysSkillpo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.eoms.user.userinterface.bean.CommissionInfo;

public class AgentUserInfo 
{
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
	private String skillconfertable			= "";
	private String rolemanagetable			= "";
	
	static final Logger logger = (Logger) Logger.getLogger(AgentUserInfo.class);

	public AgentUserInfo()
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
			skillconfertable	= getTableProperty.GetFormName("RemedyTskillconfer");
			rolemanagetable		= getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			System.out.print("从配置表中读取数据表名时出现异常！");
		}
	}
	
	//根据用户名，查出用户的所代理人的ID集合，然后再根据用户ID，取得用户名，返回用户名的集合。
	public List getUserName(String commissionName)
	{
		List returnList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct usertable.c630000001 from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(sysskill +" skilltable,");
		sql.append(" sourceconfig sourcetable ");
		sql.append(" where usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000025");
		sql.append(" and usertable2.c1=skillconfertable.c610000027");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		sql.append(" and usertable.c630000012='0'");
		//与技能表关联。
		sql.append(" and skilltable.c610000007=skillconfertable.c610000025");
		sql.append(" and skilltable.c610000008=skillconfertable.c610000031");
		sql.append(" and skilltable.c610000010=skillconfertable.c610000034");
		
		sql.append(" union ");
		
		sql.append(" select distinct usertable.c630000001 from ");
		
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(groupusertablename+" groupusertable,");
		sql.append(roleskillmanagetable+" skillmanagetable,");
		sql.append(usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(" sourceconfig sourcetable ");
		sql.append(" where usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000025");
		sql.append(" and usertable2.c1=skillconfertable.c610000027");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		sql.append(" and usertable.c630000012='0'");
		//与模版表关联。
		sql.append(" and skillmanagetable.c660000007=skillconfertable.c610000031");
		sql.append(" and skillmanagetable.c660000009=skillconfertable.c610000034");
		sql.append(" and skillmanagetable.c660000006=usergrouprel.c660000028");
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and");		           
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027) or");		       
		sql.append(" (usergrouprel.c660000026 is null and usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append(" and groupusertable.c620000028=usertable.c1");
		
		
		logger.info("根据用户名，查询此用户的所有代办人信息的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String userLoginName     	= Function.nullString(rs.getString("c630000001"));
	 	    	
	 	    	logger.info("用户登陆名："+userLoginName);
 	    	
	 	    	if(!userLoginName.equals(""))
	 	    	{
	 	    		returnList.add(userLoginName);
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
		return returnList;
	
	}

	public static void main(String args[])
	{
		AgentUserInfo agentinfo = new AgentUserInfo();
		List list = agentinfo.getUserName("Demo");
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			System.out.println((String)it.next());
		}
		
	}
}
