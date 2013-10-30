package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceInfoPram
{
	//用户登陆名
	private String userLoginName = "";
	//资源父ID
	private String sourceParentid = "";
	//权限值，可以传多个值，当传多个值时，以";"号相隔。
	private String grandValue = "";
	//模块名称：资源英文名。
	private String moudleName = "";
	
	public String getMoudleName()
	{
		return moudleName;
	}

	public void setMoudleName(String moudleName)
	{
		this.moudleName = moudleName;
	}

	public String getGrandValue()
	{
		return grandValue;
	}

	public void setGrandValue(String grandValue)
	{
		this.grandValue = grandValue;
	}

	public String getSourceParentid()
	{
		return sourceParentid;
	}

	public void setSourceParentid(String sourceParentid)
	{
		this.sourceParentid = sourceParentid;
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
