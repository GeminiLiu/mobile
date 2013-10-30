package cn.com.ultrapower.ultrawf.share;

public class Sessions {
	
	/** 	描述：用户名全名
	 * 
	 */
	private String Session_UserFullName 		= null;

	/** 	描述：用户名登陆名

	 * 
	 */
	private String Session_UserLoginName		= null;
	
	/** 	描述：用户名的组列表
	 * 
	 */
	private String Session_GroupList 			= null;
	
	/** 	描述：用户名的角色列表
	 * 
	 */
	private String Session_RoleList 			= null;
	
	
	/** 	描述：用户的License类型
	 * 
	 */
	private long 	Session_LicenseType			;	 

	private String User_CPID;
	private String User_DPID;
	private String User_CP;
	private String User_DP;
	
	public String getUser_CPID()
	{
		return User_CPID;
	}

	public String getUser_CP() {
		return User_CP;
	}

	public void setUser_CP(String user_CP) {
		User_CP = user_CP;
	}

	public String getUser_DP() {
		return User_DP;
	}

	public void setUser_DP(String user_DP) {
		User_DP = user_DP;
	}

	public void setUser_CPID(String user_CPID)
	{
		User_CPID = user_CPID;
	}

	public String getUser_DPID()
	{
		return User_DPID;
	}

	public void setUser_DPID(String user_DPID)
	{
		User_DPID = user_DPID;
	}

	public String getSession_GroupList() {
		return Session_GroupList;
	}

	public void setSession_GroupList(String session_GroupList) {
		Session_GroupList = session_GroupList;
	}

	public String getSession_UserFullName() {
		return Session_UserFullName;
	}

	public void setSession_UserFullName(String session_UserFullName) {
		Session_UserFullName = session_UserFullName;
	}

	public String getSession_UserLoginName() {
		return Session_UserLoginName;
	}

	public void setSession_UserLoginName(String session_UserLoginName) {
		Session_UserLoginName = session_UserLoginName;
	}

	public long getSession_LicenseType() {
		return Session_LicenseType;
	}

	public void setSession_LicenseType(long session_LicenseType) {
		Session_LicenseType = session_LicenseType;
	}

	public String getSession_RoleList()
	{
		return Session_RoleList;
	}

	public void setSession_RoleList(String session_RoleList)
	{
		Session_RoleList = session_RoleList;
	}

	
}

