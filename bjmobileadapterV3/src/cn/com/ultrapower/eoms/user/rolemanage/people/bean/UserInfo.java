package cn.com.ultrapower.eoms.user.rolemanage.people.bean;

/**
 * <p>Description:将Remedy用户信息封装在javabean中<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class UserInfo {
	
	private String user_LoginName;
	private String user_FullName;
	private String user_Password;
	private String user_LicenceType;
	private String user_FullTextLicenceType;
	private String user_Status;
	private String user_Creator;
	
	public String getUser_Creator() 
	{
		return user_Creator;
	}
	
	public void setUser_Creator(String user_Creator) 
	{
		this.user_Creator = user_Creator;
	}
	
	public String getUser_FullName() 
	{
		return user_FullName;
	}
	
	public void setUser_FullName(String user_FullName) 
	{
		this.user_FullName = user_FullName;
	}
	
	public String getUser_FullTextLicenceType()
	{
		return user_FullTextLicenceType;
	}
	
	public void setUser_FullTextLicenceType(String user_FullTextLicenceType) 
	{
		this.user_FullTextLicenceType = user_FullTextLicenceType;
	}
	
	public String getUser_LicenceType() 
	{
		return user_LicenceType;
	}
	
	public void setUser_LicenceType(String user_LicenceType) 
	{
		this.user_LicenceType = user_LicenceType;
	}
	
	public String getUser_LoginName() 
	{
		return user_LoginName;
	}
	
	public void setUser_LoginName(String user_LoginName) 
	{
		this.user_LoginName = user_LoginName;
	}
	
	public String getUser_Status()
	{
		return user_Status;
	}
	
	public void setUser_Status(String user_Status)
	{
		this.user_Status = user_Status;
	}

	public String getUser_Password()
	{
		return user_Password;
	}

	public void setUser_Password(String user_Password)
	{
		this.user_Password = user_Password;
	}

}
