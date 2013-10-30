package cn.com.ultrapower.eoms.user.userinterface.bean;

public class CommissionInfo
{
	//用户ID
	private String userID;
	//用户登陆名
	private String userLoginName;
	//用户全名
	private String userFullName;
	//资源ID
	private String sourceID;
	//资源英文名
	private String sourceName;
	//资源中文名
	private String sourceCnName;
	//资源url
	private String sourceUrl;
	//资源模块名
	private String sourceModule;
	
	public String getSourceCnName()
	{
		return sourceCnName;
	}
	public void setSourceCnName(String sourceCnName)
	{
		this.sourceCnName = sourceCnName;
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
	public String getUserFullName()
	{
		return userFullName;
	}
	public void setUserFullName(String userFullName)
	{
		this.userFullName = userFullName;
	}
	public String getUserID()
	{
		return userID;
	}
	public void setUserID(String userID)
	{
		this.userID = userID;
	}
	public String getUserLoginName()
	{
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName)
	{
		this.userLoginName = userLoginName;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourceModule() {
		return sourceModule;
	}
	public void setSourceModule(String sourceModule) {
		this.sourceModule = sourceModule;
	}

}
