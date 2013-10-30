package cn.com.ultrapower.eoms.user.userinterface.bean;

public class AgentPram
{
	//用户名
	private String userName;
	//代办人名
	private String commissionName;
	//资源名
	private String sourceName;
	//资源ID
	private String sourceID;
	//资源模块名
	private String moduleName;
	
	public String getModuleName()
	{
		return moduleName;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	public String getCommissionName()
	{
		return commissionName;
	}
	public void setCommissionName(String commissionName)
	{
		this.commissionName = commissionName;
	}

	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getSourceID()
	{
		return sourceID;
	}
	public void setSourceID(String sourceID)
	{
		this.sourceID = sourceID;
	}
	public String getSourceName()
	{
		return sourceName;
	}
	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}
	
	
}
