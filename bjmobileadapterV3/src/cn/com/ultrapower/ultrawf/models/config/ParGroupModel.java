package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParGroupModel {
	
	private  String RequestID="";
	private  String LongGroupName="";
	private  String GroupName="";
	private  String GroupID="";
	private  int GroupType=999;

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
	public String GetGroupID()
	{
	   return GroupID;
	}
	public void   SetGroupID(String p_GroupID)
	{
	   GroupID=p_GroupID;
	}
//	107 GroupType 
	public int GetGroupType()
	{
	   return GroupType;
	}
	public void   SetGroupType(int p_GroupType)
	{
	   GroupType=p_GroupType;
	}

	
	public String GetWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		//1	RequestID
		if(!GetRequestID().equals(""))
			stringBuffer.append(" and C1='"+GetRequestID()+"'");
		//8 LongGroupName 
		if(!GetLongGroupName().equals(""))
			stringBuffer.append(" and C8='"+GetLongGroupName()+"'");		
		//105 GroupName 
		if(!GetGroupName().equals(""))
			stringBuffer.append(" and C105='"+GetGroupName()+"'");		
		//106 GroupID 
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C106",GetGroupID()));
		//if(GetGroupID()!=999)
		//	stringBuffer.append(" and C106='"+String.valueOf(GetGroupID())+"'");	
		//107 GroupType 
		stringBuffer.append(UnionConditionForSql.getIntFiedSql("","C107",GetGroupType()));
		//if(GetGroupType()!=999)
		//	stringBuffer.append(" and C107='"+String.valueOf(GetGroupType())+"'");	

		return stringBuffer.toString();
		
	}	

}
