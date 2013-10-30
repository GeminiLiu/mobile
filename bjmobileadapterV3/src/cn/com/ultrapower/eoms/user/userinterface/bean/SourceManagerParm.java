package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceManagerParm
{
	//资源id
	private String sourcemanager_sourceid	= "";
	//条件
	private String sourcemanager_query		= "";
	//组id
	private String sourcemanager_groupid	= "";
	//用户id
	private String sourcemanager_userid		= "";
	//类型
	private String sourcemanager_type		= "";
	//管理组id
	private String sourcemanager_sgroupid	= "";
	//管理人id
	private String sourcemanager_suserid	= "";
	//权限值用分号隔开
	private String grandaction				= "";
	//默认0，程序控制。
	private String gotoflag					= "0";


	public String getGotoflag()
	{
		return gotoflag;
	}

	public void setGotoflag(String gotoflag)
	{
		this.gotoflag = gotoflag;
	}

	public String getGrandaction()
	{
		return grandaction;
	}

	public void setGrandaction(String grandaction)
	{
		this.grandaction = grandaction;
	}

	public String getSourcemanager_groupid()
	{
		return sourcemanager_groupid;
	}

	public void setSourcemanager_groupid(String sourcemanager_groupid)
	{
		this.sourcemanager_groupid = sourcemanager_groupid;
	}

	public String getSourcemanager_query()
	{
		return sourcemanager_query;
	}

	public void setSourcemanager_query(String sourcemanager_query)
	{
		this.sourcemanager_query = sourcemanager_query;
	}

	public String getSourcemanager_sgroupid()
	{
		return sourcemanager_sgroupid;
	}

	public void setSourcemanager_sgroupid(String sourcemanager_sgroupid)
	{
		this.sourcemanager_sgroupid = sourcemanager_sgroupid;
	}

	public String getSourcemanager_sourceid()
	{
		return sourcemanager_sourceid;
	}

	public void setSourcemanager_sourceid(String sourcemanager_sourceid)
	{
		this.sourcemanager_sourceid = sourcemanager_sourceid;
	}

	public String getSourcemanager_suserid()
	{
		return sourcemanager_suserid;
	}

	public void setSourcemanager_suserid(String sourcemanager_suserid)
	{
		this.sourcemanager_suserid = sourcemanager_suserid;
	}

	public String getSourcemanager_type()
	{
		return sourcemanager_type;
	}

	public void setSourcemanager_type(String sourcemanager_type)
	{
		this.sourcemanager_type = sourcemanager_type;
	}

	public String getSourcemanager_userid()
	{
		return sourcemanager_userid;
	}

	public void setSourcemanager_userid(String sourcemanager_userid)
	{
		this.sourcemanager_userid = sourcemanager_userid;
	}
	
}
