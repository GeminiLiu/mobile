package cn.com.ultrapower.eoms.user.userinterface.bean;

public class UserGroupPram
{
	//用户登陆名
	private String userLoginName;
	//用户的公司ID，当组织结构树只想显示本公司的组与人员时，传这个参数。
	//(0代表查全部组织结构树，1代表查当前登陆用户所在组织结构树)
	private String user_CPID;
	//组的父ID。
	private String groupParentid;
	
	public String getGroupParentid()
	{
		return groupParentid;
	}
	public void setGroupParentid(String groupParentid)
	{
		this.groupParentid = groupParentid;
	}
	public String getUser_CPID()
	{
		return user_CPID;
	}
	public void setUser_CPID(String user_CPID)
	{
		this.user_CPID = user_CPID;
	}
	public String getUserLoginName()
	{
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName)
	{
		this.userLoginName = userLoginName;
	}
}
