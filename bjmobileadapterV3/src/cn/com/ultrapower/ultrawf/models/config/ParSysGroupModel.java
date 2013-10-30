package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParSysGroupModel {

//	属性设置区域--Begin--
	private  String Group_ID="";
	private  String Group_Name="";
	private  String Group_FullName="";
	private  String Group_ParentID="";
	private  String Group_Type="";
	private  String Group_CreateUser="";
	private  String Group_Phone="";
	private  String Group_Fax="";
	private  String Group_Status="";
	private  String Group_CompanyID="";
	private  String Group_CompanyType="";
	private  String Group_Desc="";
	private  String Group_IntID="";
	private  String Group_DNID="";
//	1 Group_ID 
	public String getGroup_ID()
	{
	   return Group_ID;
	}
	public void   setGroup_ID(String p_Group_ID)
	{
	   Group_ID=p_Group_ID;
	}
//	630000018 Group_Name 
	public String getGroup_Name()
	{
	   return Group_Name;
	}
	public void   setGroup_Name(String p_Group_Name)
	{
	   Group_Name=p_Group_Name;
	}
//	630000019 Group_FullName 
	public String getGroup_FullName()
	{
	   return Group_FullName;
	}
	public void   setGroup_FullName(String p_Group_FullName)
	{
	   Group_FullName=p_Group_FullName;
	}
//	630000020 Group_ParentID 
	public String getGroup_ParentID()
	{
	   return Group_ParentID;
	}
	public void   setGroup_ParentID(String p_Group_ParentID)
	{
	   Group_ParentID=p_Group_ParentID;
	}
//	630000021 Group_Type 
	public String getGroup_Type()
	{
	   return Group_Type;
	}
	public void   setGroup_Type(String p_Group_Type)
	{
	   Group_Type=p_Group_Type;
	}
//	630000022 Group_CreateUser 
	public String getGroup_CreateUser()
	{
	   return Group_CreateUser;
	}
	public void   setGroup_CreateUser(String p_Group_CreateUser)
	{
	   Group_CreateUser=p_Group_CreateUser;
	}
//	630000023 Group_Phone 
	public String getGroup_Phone()
	{
	   return Group_Phone;
	}
	public void   setGroup_Phone(String p_Group_Phone)
	{
	   Group_Phone=p_Group_Phone;
	}
//	630000024 Group_Fax 
	public String getGroup_Fax()
	{
	   return Group_Fax;
	}
	public void   setGroup_Fax(String p_Group_Fax)
	{
	   Group_Fax=p_Group_Fax;
	}
//	630000025 Group_Status 
	public String getGroup_Status()
	{
	   return Group_Status;
	}
	public void   setGroup_Status(String p_Group_Status)
	{
	   Group_Status=p_Group_Status;
	}
//	630000026 Group_CompanyID 
	public String getGroup_CompanyID()
	{
	   return Group_CompanyID;
	}
	public void   setGroup_CompanyID(String p_Group_CompanyID)
	{
	   Group_CompanyID=p_Group_CompanyID;
	}
//	630000027 Group_CompanyType 
	public String getGroup_CompanyType()
	{
	   return Group_CompanyType;
	}
	public void   setGroup_CompanyType(String p_Group_CompanyType)
	{
	   Group_CompanyType=p_Group_CompanyType;
	}
//	630000028 Group_Desc 
	public String getGroup_Desc()
	{
	   return Group_Desc;
	}
	public void   setGroup_Desc(String p_Group_Desc)
	{
	   Group_Desc=p_Group_Desc;
	}
//	630000030 Group_IntID 
	public String getGroup_IntID()
	{
	   return Group_IntID;
	}
	public void   setGroup_IntID(String p_Group_IntID)
	{
	   Group_IntID=p_Group_IntID;
	}
//	630000037 Group_DNID 
	public String getGroup_DNID()
	{
	   return Group_DNID;
	}
	public void   setGroup_DNID(String p_Group_DNID)
	{
	   Group_DNID=p_Group_DNID;
	}
//	属性设置区域--End--


	
	public String GetWhereSql()
	{
		StringBuffer stringBuffer=new StringBuffer();
		//1 Group_ID
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C1",getGroup_ID()));
		//630000018	Group_Name
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000018",getGroup_Name()));
		//630000019	Group_FullName
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000019",getGroup_FullName()));
		//630000020	Group_ParentID
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000020",getGroup_ParentID()));
		//630000021	Group_Type
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000021",getGroup_Type()));
		//630000022	Group_CreateUser
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000022",getGroup_CreateUser()));
		//630000023	Group_Phone
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000023",getGroup_Phone()));
		//630000024	Group_Fax
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000024",getGroup_Fax()));
		//630000025	Group_Status
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000025",getGroup_Status()));
		//630000026	Group_CompanyID
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000026",getGroup_CompanyID()));
		//630000027	Group_CompanyType
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000027",getGroup_CompanyType()));
		//630000028	Group_Desc
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000028",getGroup_Desc()));
		//630000030	Group_IntID
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000030",getGroup_IntID()));
		//630000037	Group_DNID
		stringBuffer.append(UnionConditionForSql.getStringFiledSql("","C630000037",getGroup_DNID()));
		
		return stringBuffer.toString();
		
	}	

}
