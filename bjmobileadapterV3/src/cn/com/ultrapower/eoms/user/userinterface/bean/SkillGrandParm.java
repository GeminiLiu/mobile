package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SkillGrandParm {
	//用户id
	private String userid			= null;
	//用户登陆名
	private String userLoginName;
	
	//资源id
	private String sourceid			= null;

	//被代办人id
	private String commissionuserid	= null;
	
	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCommissionuserid() {
		return commissionuserid;
	}

	public void setCommissionuserid(String commissionuserid) {
		this.commissionuserid = commissionuserid;
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