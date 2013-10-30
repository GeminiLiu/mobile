package cn.com.ultrapower.eoms.user.log.bean;

/**
 * <p>Description:将用户日志信息封装在javabean中<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-27
 */
public class LogInfo {
	
	private String userLogId;
	private String userLogLoginName;
	private String userLogUserName;
	private String userLogModuleId;
	private String userLogUserLog;
	private String userLogUserLogDateTime;
	
	public String getUserLogId() 
	{
		return userLogId;
	}
	
	public void setUserLogId(String userLogId) 
	{
		this.userLogId = userLogId;
	}
	
	public String getUserLogLoginName() 
	{
		return userLogLoginName;
	}
	
	public void setUserLogLoginName(String userLogLoginName)
	{
		this.userLogLoginName = userLogLoginName;
	}
	
	public String getUserLogModuleId()
	{
		return userLogModuleId;
	}
	
	public void setUserLogModuleId(String userLogModuleId) 
	{
		this.userLogModuleId = userLogModuleId;
	}
	
	public String getUserLogUserLog() 
	{
		return userLogUserLog;
	}
	
	public void setUserLogUserLog(String userLogUserLog)
	{
		this.userLogUserLog = userLogUserLog;
	}
	
	public String getUserLogUserLogDateTime() 
	{
		return userLogUserLogDateTime;
	}
	
	public void setUserLogUserLogDateTime(String userLogUserLogDateTime) 
	{
		this.userLogUserLogDateTime = userLogUserLogDateTime;
	}
	
	public String getUserLogUserName() 
	{
		return userLogUserName;
	}
	
	public void setUserLogUserName(String userLogUserName) 
	{
		this.userLogUserName = userLogUserName;
	}
		
}
