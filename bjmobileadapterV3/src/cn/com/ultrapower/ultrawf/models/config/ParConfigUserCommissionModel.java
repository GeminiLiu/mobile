package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParConfigUserCommissionModel {

//	属性设置区域--Begin--
	private  String ConfigID;
	private  String BaseCategoryName;
	private  String BaseCategorySchama;
	private  String Assginee;
	private  String AssgineeID;
	private  String Commissioner;
	private  String CommissionerID;
	private  String CommissionBeginTime;
	private  String CommissionEndTime;
//	1 ConfigID 本记录的唯一标识，创建是自动形成，无业务含义
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
//	650000005 Commissioner Assginee的代办人名，本记录的主人的代办人
	public String getCommissioner()
	{
	   return Commissioner;
	}
	public void   setCommissioner(String p_Commissioner)
	{
	   Commissioner=p_Commissioner;
	}
//	650000006 CommissionerID Assginee的代办人登陆名
	public String getCommissionerID()
	{
	   return CommissionerID;
	}
	public void   setCommissionerID(String p_CommissionerID)
	{
	   CommissionerID=p_CommissionerID;
	}
//	650000007 CommissionBeginTime 代办开始时间
	public String getCommissionBeginTime()
	{
	   return CommissionBeginTime;
	}
	public void   setCommissionBeginTime(String p_CommissionBeginTime)
	{
	   CommissionBeginTime=p_CommissionBeginTime;
	}
//	650000008 CommissionEndTime 代办结束时间
	public String getCommissionEndTime()
	{
	   return CommissionEndTime;
	}
	public void   setCommissionEndTime(String p_CommissionEndTime)
	{
	   CommissionEndTime=p_CommissionEndTime;
	}
//	属性设置区域--End--

	
	public String getWhereSql(String p_TblAliasName)
	{	

		StringBuffer sqlString = new StringBuffer();
		//	1	BaseID 本记录的唯一标识，创建是自动形成，无业务含义	
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C1",getConfigID()));
//		650000001 BaseCategoryName 工单类别名称
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000001",getBaseCategoryName()));
//		650000002 BaseCategorySchama 工单Form名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000002",getBaseCategorySchama()));
//		650000003 Assginee 人名，本记录的主人，派单的对象
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000003",getAssginee()));
//		650000004 AssgineeID 人登陆名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000004",getAssgineeID()));
//		650000005 Commissioner  Assginee的代办人名，本记录的主人的代办人
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000005",getCommissioner()));
//		650000006 CommissionerID Assginee的代办人登陆名
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"C650000006",getCommissionerID()));
//		650000007 CommissionBeginTime 代办开始时间
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C650000007",Long.parseLong(getCommissionBeginTime())));
//		650000008 CommissionEndTime 代办结束时间
		sqlString.append(UnionConditionForSql.getLongTimeFiedSql(p_TblAliasName,"C650000008",Long.parseLong(getCommissionEndTime())));
		return sqlString.toString();
	}
	

}
