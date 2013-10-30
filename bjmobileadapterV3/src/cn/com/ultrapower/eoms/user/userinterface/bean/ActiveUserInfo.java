package cn.com.ultrapower.eoms.user.userinterface.bean;

public class ActiveUserInfo
{
	//用户登陆名
	private String userLoginName;
	//用户中文名
	private String userName;
	//用户部门名
	private String DPName;
	//用户公司名
	private String CPName;
	//用户连结时间
	private String connectionTime;
	//用户最后登陆时间
	private String lastAccessTime;
	
	public String getConnectionTime()
	{
		return connectionTime;
	}
	public void setConnectionTime(String connectionTime)
	{
		this.connectionTime = connectionTime;
	}
	public String getCPName()
	{
		return CPName;
	}
	public void setCPName(String name)
	{
		CPName = name;
	}
	public String getDPName()
	{
		return DPName;
	}
	public void setDPName(String name)
	{
		DPName = name;
	}
	public String getLastAccessTime()
	{
		return lastAccessTime;
	}
	public void setLastAccessTime(String lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}
	public String getUserLoginName()
	{
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName)
	{
		this.userLoginName = userLoginName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
}
