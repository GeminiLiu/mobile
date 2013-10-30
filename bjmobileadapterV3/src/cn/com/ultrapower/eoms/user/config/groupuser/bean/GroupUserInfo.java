package cn.com.ultrapower.eoms.user.config.groupuser.bean;

/**
 * <p>Description:封装用户组信息到JavaBean中<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-20
 */
public class GroupUserInfo {
	
	private String mgroupId;
	private String mgroupGroupId;
	private String mgroupUserId;
	
	public String getMgroupGroupId()
	{
		return mgroupGroupId;
	}
	
	public void setMgroupGroupId(String mgroupGroupId)
	{
		this.mgroupGroupId = mgroupGroupId;
	}
	
	public String getMgroupId() 
	{
		return mgroupId;
	}
	
	public void setMgroupId(String mgroupId)
	{
		this.mgroupId = mgroupId;
	}
	
	public String getMgroupUserId() 
	{
		return mgroupUserId;
	}
	
	public void setMgroupUserId(String mgroupUserId) 
	{
		this.mgroupUserId = mgroupUserId;
	}
	
}
