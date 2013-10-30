package cn.com.ultrapower.eoms.user.userinterface;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.userinterface.bean.AgentPram;

public class AgentInterface
{
	static final Logger logger = (Logger) Logger.getLogger(AgentInterface.class);

	public List getCommissionInfo(AgentPram agentInfo)
	{
		List returnList = new ArrayList();
		
		String userName 		= Function.nullString(agentInfo.getUserName());
		String commissionName 	= Function.nullString(agentInfo.getCommissionName());
		String sourceName 		= Function.nullString(agentInfo.getSourceName());
		String sourceID			= Function.nullString(agentInfo.getSourceID());
		String moduleName 		= Function.nullString(agentInfo.getModuleName());
		
		if(!userName.equals("")&&!commissionName.equals("")&&(!sourceName.equals("")||!sourceID.equals("")))
		{
			//根据用户名，代办人名，资源名，查询出此用户和此用户的代办人的资源的下一级资源信息。
			returnList = getNextSourceInfo(agentInfo);
		}
		else if(!userName.equals("")&&!commissionName.equals("")&&sourceName.equals("")&&sourceID.equals(""))
		{
			if(moduleName.equals(""))
			{
				logger.error("资源模块名不能为空！");
				return null;
			}
			//根据用户名，代办人名，查询出代办人所能代办用户的所有资源信息。
			returnList = getAllSourceInfo(agentInfo);
		}
		else if(!userName.equals("")&&commissionName.equals("")&&sourceName.equals("")&&sourceID.equals(""))
		{
			if(moduleName.equals(""))
			{
				logger.error("资源模块名不能为空！");
				return null;
			}
			//根据用户名，查询出此用户的所有代办人信息。
			returnList = getAgentInfo(agentInfo);
		}
		else
		{
			logger.error("您提供的参数不正确，请检查参数！");
			return null;
		}
		return returnList;	
		
	}
	
	//根据用户名，代办人名，资源名，查询出此用户和此用户的代办人的资源的下一级资源信息。
	public List getNextSourceInfo(AgentPram agentInfo)
	{
		AgentInterfaceSQL agentsql = new AgentInterfaceSQL();
		List returnList = agentsql.getNextSourceInfoSQL(agentInfo);
		return returnList;
	}
	
	//根据用户名，代办人名，查询出代办人所能代办用户的所有资源信息。
	public List getAllSourceInfo(AgentPram agentInfo)
	{
		AgentInterfaceSQL agentsql = new AgentInterfaceSQL();
		List returnList = agentsql.getAllSourceInfoSQL(agentInfo);
		return returnList;
	}
	
	//根据用户名，查询出此用户的所有代办人信息。
	public List getAgentInfo(AgentPram agentInfo)
	{
		AgentInterfaceSQL agentsql = new AgentInterfaceSQL();
		List returnList = agentsql.getAgentInfoSQL(agentInfo);
		return returnList;
	}
	
	public static void main(String[] args)
	{
		AgentInterface agentsql = new AgentInterface();
		AgentPram agentpram = new AgentPram();
		agentpram.setUserName("Demo");
		agentpram.setCommissionName("zh4");
		agentpram.setSourceName("");
		agentpram.setModuleName("dutymanage");
		List list = agentsql.getCommissionInfo(agentpram);
		System.out.println("result:");
		System.out.println(list.size());
		//以上是查询当前人关于这个资源模块的所有代办人信息的。
//		
//		AgentPram agentpram2 = new AgentPram();
//		agentpram2.setUserName("wangyanguang");
//		agentpram2.setCommissionName("Demo");
//		agentpram2.setSourceName("");
//		agentpram2.setModuleName("systemmanage");
//		agentsql.getCommissionInfo(agentpram);
		//以上是查询当前人关于这个资源模块下代办Demo的所有资源住处的。
		
		
		
	}
	
}
