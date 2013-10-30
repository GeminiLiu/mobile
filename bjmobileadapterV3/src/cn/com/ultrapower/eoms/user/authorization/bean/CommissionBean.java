package cn.com.ultrapower.eoms.user.authorization.bean;

public class CommissionBean
{

	private String username 	= "";
	private String goalname    	= "";
	private String tmpcause		= "";
	private String tmpcancel	= "";
	private String begintime  	= "";
	private String closetime	= "";
	private String tmpstatus	= "";
	private String tmpsource	= "";
	
	public String getBegintime()
	{
		return begintime;
	}
	public void setBegintime(String begintime)
	{
		this.begintime = begintime;
	}
	public String getClosetime()
	{
		return closetime;
	}
	public void setClosetime(String closetime)
	{
		this.closetime = closetime;
	}
	public String getGoalname()
	{
		return goalname;
	}
	public void setGoalname(String goalname)
	{
		this.goalname = goalname;
	}
	public String getTmpcancel()
	{
		return tmpcancel;
	}
	public void setTmpcancel(String tmpcancel)
	{
		this.tmpcancel = tmpcancel;
	}
	public String getTmpcause()
	{
		return tmpcause;
	}
	public void setTmpcause(String tmpcause)
	{
		this.tmpcause = tmpcause;
	}
	public String getTmpsource()
	{
		return tmpsource;
	}
	public void setTmpsource(String tmpsource)
	{
		this.tmpsource = tmpsource;
	}
	public String getTmpstatus()
	{
		return tmpstatus;
	}
	public void setTmpstatus(String tmpstatus)
	{
		this.tmpstatus = tmpstatus;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}

}
