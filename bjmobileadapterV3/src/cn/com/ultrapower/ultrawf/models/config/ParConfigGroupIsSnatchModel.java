package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParConfigGroupIsSnatchModel {

//	属性设置区域--Begin--
	private  String ConfigID="";
	private  String BaseCategoryName="";
	private  String BaseCategorySchama="";
	private  String Group="";
	private  String GroupID="";
	private  String GroupIsSnatch="";
//	1 ConfigID 
	public String getConfigID()
	{
	   return ConfigID;
	}
	public void   setConfigID(String p_ConfigID)
	{
	   ConfigID=p_ConfigID;
	}
//	650000001 BaseCategoryName 
	public String getBaseCategoryName()
	{
	   return BaseCategoryName;
	}
	public void   setBaseCategoryName(String p_BaseCategoryName)
	{
	   BaseCategoryName=p_BaseCategoryName;
	}
//	650000002 BaseCategorySchama 
	public String getBaseCategorySchama()
	{
	   return BaseCategorySchama;
	}
	public void   setBaseCategorySchama(String p_BaseCategorySchama)
	{
	   BaseCategorySchama=p_BaseCategorySchama;
	}
//	650000003 Group 
	public String getGroup()
	{
	   return Group;
	}
	public void   setGroup(String p_Group)
	{
	   Group=p_Group;
	}
//	650000004 GroupID 
	public String getGroupID()
	{
	   return GroupID;
	}
	public void   setGroupID(String p_GroupID)
	{
	   GroupID=p_GroupID;
	}
//	650000005 GroupIsSnatch 
	public String getGroupIsSnatch()
	{
	   return GroupIsSnatch;
	}
	public void   setGroupIsSnatch(String p_GroupIsSnatch)
	{
	   GroupIsSnatch=p_GroupIsSnatch;
	}
//	属性设置区域--End--
	
	public String getWhereSql(String p_TblAliasName)
	{	
		StringBuffer sqlString = new StringBuffer();
		//	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
		//C1 as ConfigID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",this.getConfigID()));
		//C650000001 as BaseCategoryName
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000001",this.getBaseCategoryName()));
		//C650000002 as BaseCategorySchama
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000002",this.getBaseCategorySchama()));
		//C650000003 as Group
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000003",this.getGroup()));
		//C650000004 as GroupID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000004",this.getGroupID()));
		//C650000005 as GroupIsSnatch 
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000005",this.getGroupIsSnatch()));
		return sqlString.toString();
	}	
	
}
