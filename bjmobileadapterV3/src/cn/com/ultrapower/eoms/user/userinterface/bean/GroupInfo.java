package cn.com.ultrapower.eoms.user.userinterface.bean;

/**
 * <p>Description:将组信息封装在javabean中<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-16
 */
public class GroupInfo {
	
    private String groupId;
    private int groupIntId;
    private String groupName;
    private String groupFullname;
    private String groupParentid;
    private String groupType;
    private String groupOrderBy;
    private String groupPhone;
    private String groupFax;
    private String groupStatus;
    private String groupCompanyid;
    private String groupCompanytype;
    private String groupDesc;
    private String groupDnId;
    
	public String getGroupDnId() 
	{
		return groupDnId;
	}

	public void setGroupDnId(String groupDnId) 
	{
		this.groupDnId = groupDnId;
	}

	public String getGroupCompanyid() 
	{
		return groupCompanyid;
	}
	
	public void setGroupCompanyid(String groupCompanyid) 
	{
		this.groupCompanyid = groupCompanyid;
	}
	
	public String getGroupCompanytype() 
	{
		return groupCompanytype;
	}
	
	public void setGroupCompanytype(String groupCompanytype)
	{
		this.groupCompanytype = groupCompanytype;
	}
	
	public String getGroupDesc() 
	{
		return groupDesc;
	}
	
	public void setGroupDesc(String groupDesc)
	{
		this.groupDesc = groupDesc;
	}
	
	public String getGroupFax()
	{
		return groupFax;
	}
	
	public void setGroupFax(String groupFax) 
	{
		this.groupFax = groupFax;
	}
	
	public String getGroupFullname() 
	{
		return groupFullname;
	}
	
	public void setGroupFullname(String groupFullname) 
	{
		this.groupFullname = groupFullname;
	}
	
	public String getGroupId() 
	{
		return groupId;
	}
	
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	
	public String getGroupName() 
	{
		return groupName;
	}
	
	public void setGroupName(String groupName) 
	{
		this.groupName = groupName;
	}
	
	public String getGroupParentid()
	{
		return groupParentid;
	}
	
	public void setGroupParentid(String groupParentid)
	{
		this.groupParentid = groupParentid;
	}
	
	public String getGroupPhone() 
	{
		return groupPhone;
	}
	
	public void setGroupPhone(String groupPhone) 
	{
		this.groupPhone = groupPhone;
	}
	
	public String getGroupStatus() 
	{
		return groupStatus;
	}
	
	public void setGroupStatus(String groupStatus) 
	{
		this.groupStatus = groupStatus;
	}
	
	public String getGroupType() 
	{
		return groupType;
	}
	
	public void setGroupType(String groupType) 
	{
		this.groupType = groupType;
	}

	public int getGroupIntId() 
	{
		return groupIntId;
	}

	public void setGroupIntId(int groupIntId) 
	{
		this.groupIntId = groupIntId;
	}

	public String getGroupOrderBy()
	{
		return groupOrderBy;
	}

	public void setGroupOrderBy(String groupOrderBy)
	{
		this.groupOrderBy = groupOrderBy;
	}

}
