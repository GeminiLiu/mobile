package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParAuditingProcessLogModel {
	
//	属性设置区域--Begin--
	private  String ProcessLogID="";
	private  String ProcessID="";
	private  String Act="";
	private  String logUser="";
	private  String logUserID="";
	private  long StDate=0;
	private  long StDateBegin=0;
	private  long StDateEnd=0;
	private  String Result="";
	private String ExtendSql="";
	//是否已存入历史记录表
	private int IsArchive=999;
	
	private String BaseID="";
	private String BaseSchema="";
	
	public int getIsArchive() {
		return IsArchive;
	}
	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}	
//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getProcessLogID()
	{
	   return ProcessLogID;
	}
	public void   setProcessLogID(String p_ProcessLogID)
	{
	   ProcessLogID=p_ProcessLogID;
	}
//	指向主工单处理过程记录的指针
	public String getProcessID()
	{
	   return ProcessID;
	}
	public void   setProcessID(String p_ProcessID)
	{
	   ProcessID=p_ProcessID;
	}
//	记录的动作

	public String getAct()
	{
	   return Act;
	}
	public void   setAct(String p_Act)
	{
	   Act=p_Act;
	}
//	记录的用户名字

	public String getlogUser()
	{
	   return logUser;
	}
	public void   setlogUser(String p_logUser)
	{
	   logUser=p_logUser;
	}
//	记录的用户登陆名
	public String getlogUserID()
	{
	   return logUserID;
	}
	public void   setlogUserID(String p_logUserID)
	{
	   logUserID=p_logUserID;
	}
//	创建/生效时间，表示该记录创建/生效时间
/*	public Date getStDate()
	{
	   return StDate;
	}
	public void   setStDate(Date p_StDate)
	{
	   StDate=p_StDate;
	}*/
//	Dealer记录的结果
 
	public String getResult()
	{
	   return Result;
	}
	public void   setResult(String p_Result)
	{
	   Result=p_Result;
	}
//	属性设置区域--End--
	
	public String GetWhereSql()
	{
		StringBuffer sqlString=new StringBuffer();
		//1	ProcessLogID		本记录的唯一标识，创建是自动形成，无业务含义
		//if(!ProcessLogID.equals(""))
		//	sqlString.append(" and C1='"+ProcessLogID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C1",ProcessLogID));
		//700020401	ProcessID	指向主工单处理过程记录的指针
		//if(!ProcessID.equals(""))
		//	sqlString.append(" and C700020401='"+ProcessID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020401",this.getProcessID()));
		//700020402	Act		记录的动作
		//if(!Act.equals(""))
		//	sqlString.append(" and C700020402='"+Act+"'");		
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020401",Act));
		//700020403	logUser		记录的用户名字
		//if(!logUser.equals(""))
		//	sqlString.append(" and C700020403='"+logUser+"'");	
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020403",logUser));
		//700020404	logUserID	记录的用户登陆名
		//if(!logUserID.equals(""))
		//	sqlString.append(" and C700020404='"+logUserID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020404",logUserID));		
		//700020405	StDate		创建/生效时间，表示该记录创建/生效时间
		//700020406	Result		Dealer记录的结果
		//if(!Result.equals(""))
		//	sqlString.append(" and C700020406='"+Result+"'");		
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020406",Result));
		
		//700020405	StDate		创建/生效时间，表示该记录创建/生效时间
		if(this.getStDate()>0)
			sqlString.append(" and C700020405="+String.valueOf(getStDate())+"");
		if(this.getStDateBegin()>0)
			sqlString.append(" and C700020405>="+String.valueOf(getStDateBegin())+"");
		if(this.getStDateEnd()>0)
			sqlString.append(" and C700020405<="+String.valueOf(getStDateEnd())+"");		
		
		//700020407 ProcessLogBaseID 工单号
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020407",this.getBaseID()));
		//700020408 ProcessLogBaseSchema 工单类别
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020408",this.getBaseSchema() ));
		return sqlString.toString();
		
	}
	public String getBaseID() {
		return BaseID;
	}
	public void setBaseID(String baseID) {
		BaseID = baseID;
	}
	public String getBaseSchema() {
		return BaseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		BaseSchema = baseSchema;
	}
	public long getStDate() {
		return StDate;
	}
	public void setStDate(long stDate) {
		StDate = stDate;
	}
	public long getStDateBegin() {
		return StDateBegin;
	}
	public void setStDateBegin(long stDateBegin) {
		StDateBegin = stDateBegin;
	}
	public long getStDateEnd() {
		return StDateEnd;
	}
	public void setStDateEnd(long stDateEnd) {
		StDateEnd = stDateEnd;
	}
	public String getExtendSql() {
		return ExtendSql;
	}
	public void setExtendSql(String extendSql) {
		ExtendSql = extendSql;
	}

}
