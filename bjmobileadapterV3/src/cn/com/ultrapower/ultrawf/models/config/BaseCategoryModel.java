package cn.com.ultrapower.ultrawf.models.config;


import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class BaseCategoryModel {

	//对象属性
	private  String BaseCategoryModifyUrl="";
	private  String BaseCategoryCreateUrl="";
	private  String BaseCategoryID;
	private  String Submitter;
	private  long CreateDate;
	private  String AssignedTo;
	private  String LastModifiedBy;
	private  long ModifiedDate;
	private  int Status;
	private  String ShortDescription;
	private  String BaseCategoryIsFlowName;
	private  String BaseCategoryStateName;
	
	private  int BaseCategoryIsFlow;	//	650000004
	private  int BaseCategoryState;	//	650000005
	private  int BaseCategoryDayLastNo;	//	650000006
	private  int BaseCategoryClassCode;	//	650000010
	private  int BaseCategoryIsDefaultFix;	//	650000011
	
	private  String BaseCategoryName;	//	650000001
	private  String BaseCategorySchama;	//	650000002
	private  String BaseCategoryCode;	//	650000003
	private  String BaseCategoryDesc;	//	650000007
	private  String BaseCategoryBtnAllIDS;	//	650000008
	private  String BaseCategoryClassName;	//	650000009
	private  String BaseCategoryDefaultFixTplBase;	//	650000012
	private  String BaseCategoryPageIDS;	//	650000013
	private  String BaseCategoryBtnFreeIDS;	//	650000014
	private  String BaseCategoryBtnFixIDS;	//	650000015
	private  String BaseCategoryPageHIDS;	//	650000016
	
	public int getBaseCategoryClassCode()
	{
		return BaseCategoryClassCode;
	}
	public void setBaseCategoryClassCode(int baseCategoryClassCode)
	{
		BaseCategoryClassCode = baseCategoryClassCode;
	}
	public int getBaseCategoryIsDefaultFix()
	{
		return BaseCategoryIsDefaultFix;
	}
	public void setBaseCategoryIsDefaultFix(int baseCategoryIsDefaultFix)
	{
		BaseCategoryIsDefaultFix = baseCategoryIsDefaultFix;
	}
	public String getBaseCategoryBtnAllIDS()
	{
		return BaseCategoryBtnAllIDS;
	}
	public void setBaseCategoryBtnAllIDS(String baseCategoryBtnAllIDS)
	{
		BaseCategoryBtnAllIDS = baseCategoryBtnAllIDS;
	}
	public String getBaseCategoryClassName()
	{
		return BaseCategoryClassName;
	}
	public void setBaseCategoryClassName(String baseCategoryClassName)
	{
		BaseCategoryClassName = baseCategoryClassName;
	}
	public String getBaseCategoryDefaultFixTplBase()
	{
		return BaseCategoryDefaultFixTplBase;
	}
	public void setBaseCategoryDefaultFixTplBase(String baseCategoryDefaultFixTplBase)
	{
		BaseCategoryDefaultFixTplBase = baseCategoryDefaultFixTplBase;
	}
	public String getBaseCategoryPageIDS()
	{
		return BaseCategoryPageIDS;
	}
	public void setBaseCategoryPageIDS(String baseCategoryPageIDS)
	{
		BaseCategoryPageIDS = baseCategoryPageIDS;
	}
	public String getBaseCategoryBtnFreeIDS()
	{
		return BaseCategoryBtnFreeIDS;
	}
	public void setBaseCategoryBtnFreeIDS(String baseCategoryBtnFreeIDS)
	{
		BaseCategoryBtnFreeIDS = baseCategoryBtnFreeIDS;
	}
	public String getBaseCategoryBtnFixIDS()
	{
		return BaseCategoryBtnFixIDS;
	}
	public void setBaseCategoryBtnFixIDS(String baseCategoryBtnFixIDS)
	{
		BaseCategoryBtnFixIDS = baseCategoryBtnFixIDS;
	}
	public String getBaseCategoryPageHIDS()
	{
		return BaseCategoryPageHIDS;
	}
	public void setBaseCategoryPageHIDS(String baseCategoryPageHIDS)
	{
		BaseCategoryPageHIDS = baseCategoryPageHIDS;
	}
	//	1 BaseCategoryID 本记录的唯一标识，创建是自动形成，无业务含义
	public String GetBaseCategoryID()
	{
	   return BaseCategoryID;
	}
	public void   SetBaseCategoryID(String p_BaseCategoryID)
	{
	   BaseCategoryID=p_BaseCategoryID;
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
	
	
//	650000001 BaseCategoryName 工单类别名称
	public String GetBaseCategoryName()
	{
	   return BaseCategoryName;
	}
	public void   SetBaseCategoryName(String p_BaseCategoryName)
	{
	   BaseCategoryName=p_BaseCategoryName;
	}
//	650000002 BaseCategorySchama 工单Form名


	public String GetBaseCategorySchama()
	{
	   return BaseCategorySchama;
	}
	public void   SetBaseCategorySchama(String p_BaseCategorySchama)
	{
	   BaseCategorySchama=p_BaseCategorySchama;
	}
//	650000003 BaseCategoryCode 工单代码，根据用户需求制定工单的代码
	public String GetBaseCategoryCode()
	{
	   return BaseCategoryCode;
	}
	public void   SetBaseCategoryCode(String p_BaseCategoryCode)
	{
	   BaseCategoryCode=p_BaseCategoryCode;
	}
//	650000004 BaseCategoryIsFlow 是否固定流程状态（0）否，（1）是
	public int GetBaseCategoryIsFlow()
	{
	   return BaseCategoryIsFlow;
	}
	public void   SetBaseCategoryIsFlow(int p_BaseCategoryIsFlow)
	{
	   BaseCategoryIsFlow=p_BaseCategoryIsFlow;
	}
	
	public String GetBaseCategoryIsFlowName()
	{
		if(GetBaseCategoryIsFlow()==1)
			BaseCategoryIsFlowName="是";
		else if(GetBaseCategoryIsFlow()==0)
			BaseCategoryIsFlowName="否";
		else
			BaseCategoryIsFlowName="";
	   return BaseCategoryIsFlowName;
	}
	public void   SetBaseCategoryIsFlowName(String p_BaseCategoryIsFlowName)
	{
		BaseCategoryIsFlowName=p_BaseCategoryIsFlowName;
	}	
	
//	650000005 BaseCategoryState 状态（0）停用，（1）可用


	public int GetBaseCategoryState()
	{
	   return BaseCategoryState;
	}
	public void   SetBaseCategoryState(int p_BaseCategoryState)
	{
	   BaseCategoryState=p_BaseCategoryState;
	}
	
	public String GetBaseCategoryStateName()
	{
		if(GetBaseCategoryState()==1)
			BaseCategoryStateName="可用";
		else if(GetBaseCategoryState()==0)
			BaseCategoryStateName="停用";
		else
			BaseCategoryStateName="";
	   return BaseCategoryStateName;
	}
	public void   SetBaseCategoryStateName(String p_BaseCategoryStateName)
	{
		BaseCategoryStateName=p_BaseCategoryStateName;
	}	
	
	
//	650000006 BaseCategoryDayLastNo 工单起始流水号，不可让用户输入，是系统每天自动增加的流水号


	public int GetBaseCategoryDayLastNo()
	{
	   return BaseCategoryDayLastNo;
	}
	public void   SetBaseCategoryDayLastNo(int p_BaseCategoryDayLastNo)
	{
	   BaseCategoryDayLastNo=p_BaseCategoryDayLastNo;
	}
//	650000007 BaseCategoryDesc 描述
	public String GetBaseCategoryDesc()
	{
	   return BaseCategoryDesc;
	}
	public void   SetBaseCategoryDesc(String p_BaseCategoryDesc)
	{
	   BaseCategoryDesc=p_BaseCategoryDesc;
	}
//	属性设置区域--End--	
	public String getBaseCreateUrl() {
		if (this.GetBaseCategoryIsFlow()==1)
		{
			String strCreateUrl = Constants.REMEDY_QUERY_URL;
			strCreateUrl=strCreateUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
			strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROM>",Constants.TblBaseCategory);
			strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROMVVIEW>","NewFixFlow");
			strCreateUrl = strCreateUrl.replaceAll("<REMEDY_EID>",this.GetBaseCategoryID());
			setBaseCreateUrl(strCreateUrl);		
		}
		else
		{
			String strCreateUrl = Constants.REMEDY_CREATE_URL;
			strCreateUrl=strCreateUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
			strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROM>",this.GetBaseCategorySchama());
			strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROMVVIEW>","");
			setBaseCreateUrl(strCreateUrl);					
		}
		return BaseCategoryCreateUrl;
	}
	
	public void setBaseCreateUrl(String baseCreateUrl) {
		BaseCategoryCreateUrl = baseCreateUrl;
	}
	
	public String getBaseCategoryModifyUrl() {
		String strModifyUrl = Constants.REMEDY_QUERY_URL;
		strModifyUrl=strModifyUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROM>",Constants.TblBaseCategory);
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		strModifyUrl = strModifyUrl.replaceAll("<REMEDY_EID>",this.GetBaseCategoryID());
		setBaseCategoryModifyUrl(strModifyUrl);
		return BaseCategoryModifyUrl;
	}

	public void setBaseCategoryModifyUrl(String baseCategoryModifyUrl) {
		BaseCategoryModifyUrl = baseCategoryModifyUrl;
	}
	
	public String getBaseCategoryCreateUrl() {
		String strCreateUrl = Constants.REMEDY_CREATE_URL;
		strCreateUrl=strCreateUrl.replaceAll("<REMEDY_SERVER>",Constants.REMEDY_SERVERNAME);
		strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROM>",Constants.TblBaseCategory);
		strCreateUrl = strCreateUrl.replaceAll("<REMEDY_FROMVVIEW>","");
		setBaseCategoryCreateUrl(strCreateUrl);
		//System.out.println(BaseCategoryCreateUrl);
		return BaseCategoryCreateUrl;
	}

	public void setBaseCategoryCreateUrl(String baseCategoryCreateUrl) {
		BaseCategoryCreateUrl = baseCategoryCreateUrl;
	}
	public String getBaseCategoryID()
	{
		return BaseCategoryID;
	}
	public void setBaseCategoryID(String baseCategoryID)
	{
		BaseCategoryID = baseCategoryID;
	}
	public String getSubmitter()
	{
		return Submitter;
	}
	public void setSubmitter(String submitter)
	{
		Submitter = submitter;
	}
	public long getCreateDate()
	{
		return CreateDate;
	}
	public void setCreateDate(long createDate)
	{
		CreateDate = createDate;
	}
	public String getAssignedTo()
	{
		return AssignedTo;
	}
	public void setAssignedTo(String assignedTo)
	{
		AssignedTo = assignedTo;
	}
	public String getLastModifiedBy()
	{
		return LastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy)
	{
		LastModifiedBy = lastModifiedBy;
	}
	public long getModifiedDate()
	{
		return ModifiedDate;
	}
	public void setModifiedDate(long modifiedDate)
	{
		ModifiedDate = modifiedDate;
	}
	public int getStatus()
	{
		return Status;
	}
	public void setStatus(int status)
	{
		Status = status;
	}
	public String getShortDescription()
	{
		return ShortDescription;
	}
	public void setShortDescription(String shortDescription)
	{
		ShortDescription = shortDescription;
	}
	public String getBaseCategoryName()
	{
		return BaseCategoryName;
	}
	public void setBaseCategoryName(String baseCategoryName)
	{
		BaseCategoryName = baseCategoryName;
	}
	public String getBaseCategorySchama()
	{
		return BaseCategorySchama;
	}
	public void setBaseCategorySchama(String baseCategorySchama)
	{
		BaseCategorySchama = baseCategorySchama;
	}
	public String getBaseCategoryCode()
	{
		return BaseCategoryCode;
	}
	public void setBaseCategoryCode(String baseCategoryCode)
	{
		BaseCategoryCode = baseCategoryCode;
	}
	public int getBaseCategoryIsFlow()
	{
		return BaseCategoryIsFlow;
	}
	public void setBaseCategoryIsFlow(int baseCategoryIsFlow)
	{
		BaseCategoryIsFlow = baseCategoryIsFlow;
	}
	public String getBaseCategoryIsFlowName()
	{
		return BaseCategoryIsFlowName;
	}
	public void setBaseCategoryIsFlowName(String baseCategoryIsFlowName)
	{
		BaseCategoryIsFlowName = baseCategoryIsFlowName;
	}
	public int getBaseCategoryState()
	{
		return BaseCategoryState;
	}
	public void setBaseCategoryState(int baseCategoryState)
	{
		BaseCategoryState = baseCategoryState;
	}
	public String getBaseCategoryStateName()
	{
		return BaseCategoryStateName;
	}
	public void setBaseCategoryStateName(String baseCategoryStateName)
	{
		BaseCategoryStateName = baseCategoryStateName;
	}
	public int getBaseCategoryDayLastNo()
	{
		return BaseCategoryDayLastNo;
	}
	public void setBaseCategoryDayLastNo(int baseCategoryDayLastNo)
	{
		BaseCategoryDayLastNo = baseCategoryDayLastNo;
	}
	public String getBaseCategoryDesc()
	{
		return BaseCategoryDesc;
	}
	public void setBaseCategoryDesc(String baseCategoryDesc)
	{
		BaseCategoryDesc = baseCategoryDesc;
	}
}
