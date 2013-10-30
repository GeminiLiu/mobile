package cn.com.ultrapower.ultrawf.models.design;

public class UserModel
{
	private String userID;
	private String loginName;
	private String userName;
	private String groups;
	public String getUserID()
	{
		return userID;
	}
	public void setUserID(String userID)
	{
		this.userID = userID;
	}
	public String getLoginName()
	{
		return loginName;
	}
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getGroups()
	{
		return groups;
	}
	public void setGroups(String groups)
	{
		this.groups = groups;
	}
	
}
