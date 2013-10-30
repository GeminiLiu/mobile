package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.AgentPram;
import cn.com.ultrapower.eoms.user.userinterface.bean.CommissionInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceElementInfo;

public class AgentInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(AgentInterfaceSQL.class);
	private String usertablename			= "";
	private String groupusertablename		= "";
	private String sysskill					= "";
	private String roleskillmanagetable		= "";
	private String usergrouprel				= "";
	private String skillconfertable			= "";
	private String rolemanagetable			= "";
	public AgentInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			usertablename			= getTableProperty.GetFormName("RemedyTpeople");
			groupusertablename		= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill				= getTableProperty.GetFormName("RemedyTrole");
			roleskillmanagetable 	= getTableProperty.GetFormName("RemedyTrolesskillmanage");
			usergrouprel			= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			skillconfertable		= getTableProperty.GetFormName("RemedyTskillconfer");
			rolemanagetable		 	= getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}

	//根据用户名，查询出此用户的所有代办人信息。
	public List getAgentInfoSQL(AgentPram agentInfo)
	{
		List returnList 	= new ArrayList();
		String userName 	= Function.nullString(agentInfo.getUserName());
		String sourceName 	= Function.nullString(agentInfo.getModuleName());
		StringBuffer sql 	= new StringBuffer();
		
		sql.append(" select distinct usertable.c1,usertable.c630000001,usertable.c630000003 from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append("sourceconfig sourcetable1,");
		sql.append("sourceconfig sourcetable2,");
		sql.append(skillconfertable+" skillconfertable");
		sql.append(" where usertable2.c630000001='"+userName+"'");
		sql.append(" and usertable2.c1 = skillconfertable.c610000027");
		sql.append(" and usertable.c1 = skillconfertable.c610000025");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and usertable.c630000012='0'");
		//添加资源模块名
		sql.append(" and sourcetable1.source_id=skillconfertable.c610000031");
		sql.append(" and sourcetable1.source_module=sourcetable2.source_id");
		sql.append(" and sourcetable2.source_name='"+sourceName+"'");
		sql.append(" order by usertable.c1");
		System.out.println(sql.toString());
		logger.info("根据用户名，查询此用户的所有代办人信息的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String userid     		= Function.nullString(rs.getString("c1"));
	 	    	String userLoginName	= Function.nullString(rs.getString("c630000001"));
	 	    	String userFullName		= Function.nullString(rs.getString("c630000003"));
	 	    	logger.info("用户ID："+userid+",用户登陆名："+userLoginName+",用户全名:"+userFullName);
 	    	
	 	    	if(!userid.equals("")&&!userLoginName.equals("")&&!userFullName.equals(""))
	 	    	{
	 	    		CommissionInfo commissioninfo = new CommissionInfo();
	 	    		
	 	    		commissioninfo.setUserFullName(userFullName);
	 	    		commissioninfo.setUserID(userid);
	 	    		commissioninfo.setUserLoginName(userLoginName);
	 	    		
	 	    		returnList.add(commissioninfo);
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

	//根据用户名，代办人名，查询出代办人所能代办用户的所有资源信息。
	public List getAllSourceInfoSQL(AgentPram agentInfo)
	{
		List returnList 		= new ArrayList();
		String userName 		= Function.nullString(agentInfo.getUserName());
		String commissionName 	= Function.nullString(agentInfo.getCommissionName());
		String moduleName		= Function.nullString(agentInfo.getModuleName());
		StringBuffer sql 		= new StringBuffer();
		
		sql.append(" select distinct sourcetable.source_id,sourcetable.source_name,sourcetable.source_cnname ");
		sql.append(",sourcetable.source_module,sourcetable.source_url,usertable2.c630000029,usertable2.c630000001 ");
		sql.append(" from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(sysskill +" skilltable,");
		sql.append(" sourceconfig sourcetable, ");
		sql.append(" sourceconfig sourcetable2 ");
		
		sql.append(" where usertable.c630000001='"+userName+"'");
		sql.append(" and   usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000027");
		sql.append(" and usertable2.c1=skillconfertable.c610000025");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		//与资源模块名关联。
		sql.append(" and sourcetable.source_module=sourcetable2.source_id");
		sql.append(" and sourcetable2.source_name='"+moduleName+"'");
		
        sql.append(" and sourcetable.source_type like '%0;%'");
        sql.append(" and sourcetable.source_isleft = '0'");
	
		
		
		//与技能表关联。
		sql.append(" and skilltable.c610000007=skillconfertable.c610000025");
		sql.append(" and skilltable.c610000008=skillconfertable.c610000031");
		sql.append(" and skilltable.c610000010=skillconfertable.c610000034");
		sql.append(" union ");
		
		sql.append(" select distinct sourcetable.source_id,sourcetable.source_name,sourcetable.source_cnname");
		sql.append(",sourcetable.source_module,sourcetable.source_url,usertable2.c630000029,usertable2.c630000001");
		
		sql.append(" from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(groupusertablename+" groupusertable,");
		sql.append(roleskillmanagetable+" skillmanagetable,");
		sql.append(usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(" sourceconfig sourcetable, ");
		sql.append(" sourceconfig sourcetable2 ");
		
		sql.append(" where usertable.c630000001='"+userName+"'");
		sql.append(" and   usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000027");
		sql.append(" and usertable2.c1=skillconfertable.c610000025");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		
		//与资源模块名关联。
		sql.append(" and sourcetable.source_module=sourcetable2.source_id");
		sql.append(" and sourcetable2.source_name='"+moduleName+"'");
		
        sql.append(" and sourcetable.source_type like '%0;%'");
        sql.append(" and sourcetable.source_isleft = '0'");
		
		//与模版表关联。
		sql.append(" and skillmanagetable.c660000007=skillconfertable.c610000031");
		sql.append(" and skillmanagetable.c660000009=skillconfertable.c610000034");
		sql.append(" and skillmanagetable.c660000006=usergrouprel.c660000028");
		sql.append(" and (usergrouprel.c660000026 = groupusertable.c620000028 or");		       
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
	 	    	String sourceid     	= Function.nullString(rs.getString("source_id"));
	 	    	String sourcename		= Function.nullString(rs.getString("source_name"));
	 	    	String sourcecnname		= Function.nullString(rs.getString("source_cnname"));
	 	    	String sourcemodule     = Function.nullString(rs.getString("source_module"));
	 	    	String url 				= Function.nullString(rs.getString("source_url"));
	 	    	String userid			= Function.nullString(rs.getString("c630000029"));
	 	    	String userloginname    = Function.nullString(rs.getString("c630000001"));
	 	    	logger.info("资源ID："+sourceid+",资源英文名："+sourcename+",资源中文名:"+sourcecnname+",用户ID:"+userid+",用户登陆名:"+userloginname);
 	    	
	 	    	if(!sourceid.equals("")&&!sourcename.equals("")&&!sourcecnname.equals(""))
	 	    	{
	 	    		SourceElementInfo sei = new SourceElementInfo();
	 	    		sei.setModuleid(sourcemodule);
	 	    		sei.setSourceid(sourceid);
	 	    		sei.setSourcename(sourcecnname);
	 	    		sei.setUrlvalue(url);
	 	    		sei.setUserid(userid);
	 	    		sei.setUserLoginName(userloginname);
	 	    		returnList.add(sei);
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
	
	//根据用户名，代办人名，资源名，查询出此用户和此用户的代办人的资源的下一级资源信息。
	public List getNextSourceInfoSQL(AgentPram agentInfo)
	{
		List returnList 		= new ArrayList();
		String userName 		= Function.nullString(agentInfo.getUserName());
		String commissionName 	= Function.nullString(agentInfo.getCommissionName());
		String sourceID			= Function.nullString(agentInfo.getSourceID());
		String sourceName		= Function.nullString(agentInfo.getSourceName());
		StringBuffer sql 		= new StringBuffer();
		
		sql.append(" select distinct sourcetable.source_id,sourcetable.source_name,sourcetable.source_cnname,sourcetable.source_url,sourcetable.source_module,usertable2.c1 from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(sysskill +" skilltable,");
		sql.append(" sourceconfig sourcetable, ");
		sql.append(" sourceconfig sourcetable2 ");
		sql.append(" where usertable.c630000001='"+userName+"'");
		sql.append(" and   usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000027");
		sql.append(" and usertable2.c1=skillconfertable.c610000025");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		//与技能表关联。
		sql.append(" and skilltable.c610000007=skillconfertable.c610000025");
		sql.append(" and skilltable.c610000008=skillconfertable.c610000031");
		sql.append(" and skilltable.c610000010=skillconfertable.c610000034");
		//与资源表关联。
		if(!sourceID.equals(""))
		{
			sql.append(" and sourcetable2.source_id='"+sourceID+"'");
		}
		if(!sourceName.equals(""))
		{
			sql.append(" and sourcetable2.source_name='"+sourceName+"'");
		}
		sql.append(" and sourcetable.source_parentid=sourcetable2.source_id");
		
		
		sql.append(" union ");
		
		sql.append(" select distinct sourcetable.source_id,sourcetable.source_name,sourcetable.source_cnname,sourcetable.source_url,sourcetable.source_module,usertable2.c1 from ");
		sql.append(usertablename+" usertable, ");
		sql.append(usertablename+" usertable2, ");
		sql.append(skillconfertable+" skillconfertable,");
		sql.append(groupusertablename+" groupusertable,");
		sql.append(roleskillmanagetable+" skillmanagetable,");
		sql.append(usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(" sourceconfig sourcetable, ");
		sql.append(" sourceconfig sourcetable2 ");
		sql.append(" where usertable.c630000001='"+userName+"'");
		sql.append(" and   usertable2.c630000001='"+commissionName+"'");
		sql.append(" and usertable.c1=skillconfertable.c610000027");
		sql.append(" and usertable2.c1=skillconfertable.c610000025");
		sql.append(" and skillconfertable.c610000026='0'");
		sql.append(" and sourcetable.source_id=skillconfertable.c610000031 ");
		//与模版表关联
		sql.append(" and skillmanagetable.c660000007=skillconfertable.c610000031");
		sql.append(" and skillmanagetable.c660000009=skillconfertable.c610000034");
		sql.append(" and skillmanagetable.c660000006=usergrouprel.c660000028");
		sql.append(" and (usergrouprel.c660000026 = groupusertable.c620000028 or");		       
		sql.append(" (usergrouprel.c660000026 is null and usergrouprel.c660000027 = groupusertable.c620000027))");
		
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append(" and groupusertable.c620000028=usertable.c1");
		//与资源表关联。
		if(!sourceID.equals(""))
		{
			sql.append(" and sourcetable2.source_id='"+sourceID+"'");
		}
		if(!sourceName.equals(""))
		{
			sql.append(" and sourcetable2.source_name='"+sourceName+"'");
		}
		sql.append(" and sourcetable.source_parentid=sourcetable2.source_id");
		
		
		
		logger.info("根据用户名，查询此用户的所有代办人信息的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String sourceid     	= Function.nullString(rs.getString("source_id"));
	 	    	String sourcename		= Function.nullString(rs.getString("source_name"));
	 	    	String sourcecnname		= Function.nullString(rs.getString("source_cnname"));
	 	    	 	    	
	 	    	String sourceurl		= Function.nullString(rs.getString("source_url"));
	 	    	String sourcecmodule	= Function.nullString(rs.getString("source_module"));
	 	    	String userID		    = Function.nullString(rs.getString("c1"));
	 	    	
	 	    	System.out.println("资源ID："+sourceid+",资源英文名："+sourcename+",资源中文名:"+sourcecnname);
 	    	
	 	    	if(!sourceid.equals("")&&!sourcename.equals("")&&!sourcecnname.equals(""))
	 	    	{
	 	    		CommissionInfo commissioninfo = new CommissionInfo();
	 	    		
	 	    		commissioninfo.setSourceCnName(sourcecnname);
	 	    		commissioninfo.setSourceID(sourceid);
	 	    		commissioninfo.setSourceName(sourcename);
	 	    		
	 	    		commissioninfo.setSourceUrl(sourceurl);
	 	    		commissioninfo.setSourceModule(sourcecmodule);
	 	    		commissioninfo.setUserID(userID);
	 	    		
	 	    		returnList.add(commissioninfo);
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
	
	public static void main(String[] args)
	{
		AgentInterfaceSQL agentsql = new AgentInterfaceSQL();
		AgentPram agentpram = new AgentPram();
		agentpram.setUserName("Demo");
		agentpram.setCommissionName("");
		agentpram.setSourceName("");
		//agentsql.getAgentInfoSQL(agentpram);
		//agentsql.getAllSourceInfoSQL(agentpram);
		agentsql.getNextSourceInfoSQL(agentpram);
	}
	
}
