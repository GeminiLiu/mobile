package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class BaseStateModel {
	
	//	属性设置区域--Begin--
	private  String BaseStateID;
	private  String Submitter;
	private  long CreateDate;
	private  String AssignedTo;
	private  String LastModifiedBy;
	private  long ModifiedDate;
	private  int Status;
	private  String ShortDescription;
	private  String BaseStateName;
	private  String BaseStateDesc;
//	1 BaseStateID 
	public String GetBaseStateID()
	{
	   return BaseStateID;
	}
	public void   SetBaseStateID(String p_BaseStateID)
	{
	   BaseStateID=p_BaseStateID;
	}
//	2 Submitter 
	public String GetSubmitter()
	{
	   return Submitter;
	}
	public void   SetSubmitter(String p_Submitter)
	{
	   Submitter=p_Submitter;
	}
//	3 CreateDate 
	public long GetCreateDate()
	{
	   return CreateDate;
	}
	public void SetCreateDate(long p_CreateDate)
	{
	   CreateDate=p_CreateDate;
	}
//	4 AssignedTo 
	public String GetAssignedTo()
	{
	   return AssignedTo;
	}
	public void   SetAssignedTo(String p_AssignedTo)
	{
	   AssignedTo=p_AssignedTo;
	}
//	5 LastModifiedBy 
	public String GetLastModifiedBy()
	{
	   return LastModifiedBy;
	}
	public void   SetLastModifiedBy(String p_LastModifiedBy)
	{
	   LastModifiedBy=p_LastModifiedBy;
	}
//	6 ModifiedDate 
	public long GetModifiedDate()
	{
	   return ModifiedDate;
	}
	public void   SetModifiedDate(long p_ModifiedDate)
	{
	   ModifiedDate=p_ModifiedDate;
	}
//	7 Status 
	public int GetStatus()
	{
	   return Status;
	}
	public void   SetStatus(int p_Status)
	{
	   Status=p_Status;
	}
//	8 ShortDescription 
	public String GetShortDescription()
	{
	   return ShortDescription;
	}
	public void   SetShortDescription(String p_ShortDescription)
	{
	   ShortDescription=p_ShortDescription;
	}
//	650000001 BaseStateName 工单状态名称

	public String GetBaseStateName()
	{
	   return BaseStateName;
	}
	public void   SetBaseStateName(String p_BaseStateName)
	{
	   BaseStateName=p_BaseStateName;
	}
//	650000002 BaseStateDesc 工单状态的描述
	public String GetBaseStateDesc()
	{
	   return BaseStateDesc;
	}
	public void   SetBaseStateDesc(String p_BaseStateDesc)
	{
	   BaseStateDesc=p_BaseStateDesc;
	}

	private String ModifyUrl="";
	public String getModifyUrl() {
		if(ModifyUrl==null)
			ModifyUrl="";
		if(ModifyUrl.equals(""))
		{
			String strModifyUrl = Constants.REMEDY_QUERY_URL;
			strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",Constants.TblBaseState);
			strModifyUrl=strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.GetBaseStateID());
			setModifyUrl(strModifyUrl);
		}
		return ModifyUrl;
	}	
	
	public void setModifyUrl(String baseModifyUrl) {
		ModifyUrl = baseModifyUrl;
	}		
	
}
