package cn.com.ultrapower.eoms.user.userinterface.bean;

public class ElementInfoBean
{
	//节点ID
	private String elementid 	= "";
	//节点名称
	private String elementname 	= "";
	//节点标识：0:组  1:用户
	private String elementflag 	= "";
	//记录的ID即C1
	private String recordid		= "";
	//手机号
	private String mobile		= "";
	//用户的ID
	private String userid		= "";
	//树形结构的DN，根节点到该叶子节点的绝对路径名称
	private String groupFullName = "";
	//该组节点下是否有组成员用户
	private String hasuser = "";
	
	public String getHasuser() {
		return hasuser;
	}

	public void setHasuser(String hasuser) {
		this.hasuser = hasuser;
	}
	
	public String getGroupFullName()
	{
		return groupFullName;
	}

	public void setGroupFullName(String groupFullName)
	{
		this.groupFullName = groupFullName;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getElementflag()
	{
		return elementflag;
	}

	public void setElementflag(String elementflag)
	{
		this.elementflag = elementflag;
	}

	public String getElementid()
	{
		return elementid;
	}

	public void setElementid(String elementid)
	{
		this.elementid = elementid;
	}

	public String getElementname()
	{
		return elementname;
	}

	public void setElementname(String elementname)
	{
		this.elementname = elementname;
	}

	public String getRecordid()
	{
		return recordid;
	}

	public void setRecordid(String recordid)
	{
		this.recordid = recordid;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

}
