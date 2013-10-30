package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParConfigUserCloseBaseGroupModel {

//	属性设置区域--Begin--
	private  String ConfigID;
	private  String BaseCategoryName;
	private  String BaseCategorySchama;
	private  String Assginee;
	private  String AssgineeID;
	private  String CloseBaseGroup;
	private  String CloseBaseGroupID;
//	1 ConfigID 
	public String getConfigID()
	{
	   return ConfigID;
	}
	public void   setConfigID(String p_ConfigID)
	{
	   ConfigID=p_ConfigID;
	}
//	650000001 BaseCategoryName 工单类别名称
	public String getBaseCategoryName()
	{
	   return BaseCategoryName;
	}
	public void   setBaseCategoryName(String p_BaseCategoryName)
	{
	   BaseCategoryName=p_BaseCategoryName;
	}
//	650000002 BaseCategorySchama 工单Form名
	public String getBaseCategorySchama()
	{
	   return BaseCategorySchama;
	}
	public void   setBaseCategorySchama(String p_BaseCategorySchama)
	{
	   BaseCategorySchama=p_BaseCategorySchama;
	}
//	650000003 Assginee 人名，本记录的主人，派单的对象
	public String getAssginee()
	{
	   return Assginee;
	}
	public void   setAssginee(String p_Assginee)
	{
	   Assginee=p_Assginee;
	}
//	650000004 AssgineeID 人登陆名
	public String getAssgineeID()
	{
	   return AssgineeID;
	}
	public void   setAssgineeID(String p_AssgineeID)
	{
	   AssgineeID=p_AssgineeID;
	}
//	650000005 CloseBaseGroup 归档的组
	public String getCloseBaseGroup()
	{
	   return CloseBaseGroup;
	}
	public void   setCloseBaseGroup(String p_CloseBaseGroup)
	{
	   CloseBaseGroup=p_CloseBaseGroup;
	}
//	650000006 CloseBaseGroupID 归档的组ID
	public String getCloseBaseGroupID()
	{
	   return CloseBaseGroupID;
	}
	public void   setCloseBaseGroupID(String p_CloseBaseGroupID)
	{
	   CloseBaseGroupID=p_CloseBaseGroupID;
	}
//	属性设置区域--End--

	/**
	 * 查询扩展得sql语句
	 * @return
	 */
	private String ExtendSql;	
	public String getExtendSql() {
		if(ExtendSql==null)
			ExtendSql="";
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}		
	
	public String getWhereSql(String p_TblAliasName)
	{	

		StringBuffer sqlString = new StringBuffer();
		//	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",getConfigID()));
		//650000001 BaseCategoryName 工单类别名称
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000001",getBaseCategoryName()));
		//650000002 BaseCategorySchama 工单Form名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000002",getBaseCategorySchama()));
		//650000003 Assginee 人名，本记录的主人，派单的对象
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000003",getAssginee()));
		//650000004 AssgineeID 人登陆名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000004",getAssgineeID()));
		//650000005 CloseBaseGroup 归档的组
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000005",getCloseBaseGroup()));
		//650000006 CloseBaseGroupID 归档的组ID
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000006",getCloseBaseGroupID()));
		if(!this.getExtendSql().trim().equals(""))
			sqlString.append(getExtendSql());		
		return sqlString.toString();
	}

}
