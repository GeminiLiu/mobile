package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.constants.Constants;


public class ProcessStateModel {//	属性设置区域--Begin--
	private  String ProcessStateID;
	private  String Submitter;
	private  long CreateDate;
	private  String AssignedTo;
	private  String LastModifiedBy;
	private  long ModifiedDate;
	private  int  Status;
	private  String ShortDescription;
	private  String ProcessStateName;
	private  String ProcessStateDesc;
//	1 ProcessStateID 本记录的唯一标识，创建是自动形成，无业务含义
	public String GetProcessStateID()
	{
	   return ProcessStateID;
	}
	public void   SetProcessStateID(String p_ProcessStateID)
	{
	   ProcessStateID=p_ProcessStateID;
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
	public void   SetCreateDate(long p_CreateDate)
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
	public int  GetStatus()
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
//	650000001 ProcessStateName 流程状态名称

	public String GetProcessStateName()
	{
	   return ProcessStateName;
	}
	public void   SetProcessStateName(String p_ProcessStateName)
	{
	   ProcessStateName=p_ProcessStateName;
	}
//	650000002 ProcessStateDesc 流程状态的描述
	public String GetProcessStateDesc()
	{
	   return ProcessStateDesc;
	}
	public void   SetProcessStateDesc(String p_ProcessStateDesc)
	{
	   ProcessStateDesc=p_ProcessStateDesc;
	}
//	属性设置区域--End--
	
	private String ModifyUrl="";
	public String getModifyUrl() {
		if(ModifyUrl==null)
			ModifyUrl="";
		if(ModifyUrl.equals(""))
		{
			String strModifyUrl = Constants.REMEDY_QUERY_URL;
			strModifyUrl = Constants.REMEDY_QUERY_URL.replaceAll("<REMEDY_FROM>",Constants.TblProcessState);
			strModifyUrl=strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
			strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.GetProcessStateID());
			setModifyUrl(strModifyUrl);
		}
		return ModifyUrl;
	}	
	
	public void setModifyUrl(String baseModifyUrl) {
		ModifyUrl = baseModifyUrl;
	}		

}
