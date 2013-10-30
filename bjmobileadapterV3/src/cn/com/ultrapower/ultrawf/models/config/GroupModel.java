package cn.com.ultrapower.ultrawf.models.config;

public class GroupModel {
//	属性设置区域--Begin--
	private  String RequestID;
	private  String LongGroupName;
	private  String GroupName;
	private  long GroupID;
	private  long GroupType;

//	1 RequestID 
	public String GetRequestID()
	{
	   return RequestID;
	}
	public void   SetRequestID(String p_RequestID)
	{
	   RequestID=p_RequestID;
	}

//	8 LongGroupName 
	public String GetLongGroupName()
	{
	   return LongGroupName;
	}
	public void   SetLongGroupName(String p_LongGroupName)
	{
	   LongGroupName=p_LongGroupName;
	}
//	105 GroupName 
	public String GetGroupName()
	{
	   return GroupName;
	}
	public void   SetGroupName(String p_GroupName)
	{
	   GroupName=p_GroupName;
	}
//	106 GroupID 
	public long GetGroupID()
	{
	   return GroupID;
	}
	public void   SetGroupID(long p_GroupID)
	{
	   GroupID=p_GroupID;
	}
//	107 GroupType 
	public long GetGroupType()
	{
	   return GroupType;
	}
	public void   SetGroupType(long p_GroupType)
	{
	   GroupType=p_GroupType;
	}

//	属性设置区域--End--

	
	

}
