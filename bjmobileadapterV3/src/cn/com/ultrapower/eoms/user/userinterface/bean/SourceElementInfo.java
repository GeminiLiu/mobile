package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceElementInfo
{
	//资源中文名
	private String sourcename = "";
	//资源ID。
	private String sourceid = "";
	//URL值。
	private String urlvalue = "";
	//模块ID。
	private String moduleid	;
	
	private String userid;
	
	private String userLoginName;
	
	public String getUserLoginName()
	{
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName)
	{
		this.userLoginName = userLoginName;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getUserid()
	{
		return userid;
	}

	public String getModuleid()
	{
		return moduleid;
	}

	public void setModuleid(String moduleid)
	{
		this.moduleid = moduleid;
	}

	public String getUrlvalue()
	{
		return urlvalue;
	}

	public void setUrlvalue(String urlvalue)
	{
		this.urlvalue = urlvalue;
	}

	public String getSourceid()
	{
		return sourceid;
	}

	public void setSourceid(String sourceid)
	{
		this.sourceid = sourceid;
	}

	public String getSourcename()
	{
		return sourcename;
	}

	public void setSourcename(String sourcename)
	{
		this.sourcename = sourcename;
	}

}
