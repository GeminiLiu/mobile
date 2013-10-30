package cn.com.ultrapower.eoms.user.authorization.bean;

public class UserSendScopeGroupInfo
{

	private String groupid 				= "";
	private String groupname 			= "";
	private String roleid 				= "";
	private String sourcename 			= "";
	private String sendtype 			= "";
	private String usersendscopedesc 	= "";
	private String namemag				= "";

	public String getSendtype()
	{
		return sendtype;
	}

	public void setSendtype(String sendtype)
	{
		this.sendtype = sendtype;
	}

	public String getSourcename()
	{
		return sourcename;
	}

	public void setSourcename(String sourcename)
	{
		this.sourcename = sourcename;
	}

	public String getGroupid()
	{
		return groupid;
	}

	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}

	public String getGroupname()
	{
		return groupname;
	}

	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}

	public String getRoleid()
	{
		return roleid;
	}

	public void setRoleid(String roleid)
	{
		this.roleid = roleid;
	}

	public String getUsersendscopedesc()
	{
		return usersendscopedesc;
	}

	public void setUsersendscopedesc(String usersendscopedesc)
	{
		this.usersendscopedesc = usersendscopedesc;
	}

	public String getNamemag()
	{
		return namemag;
	}

	public void setNamemag(String namemag)
	{
		this.namemag = namemag;
	}



}
