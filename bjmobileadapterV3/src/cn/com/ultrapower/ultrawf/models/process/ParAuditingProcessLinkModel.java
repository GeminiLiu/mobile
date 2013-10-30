package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParAuditingProcessLinkModel {
	
//	属性设置区域--Begin--
	private  String LinkID="";
	private  String LinkBaseID="";
	private  String LinkBaseSchema="";
	private  String StartPhase="";
	private  String EndPhase="";
	private  String Desc="";
	//private  int Flag00IsAvail=0;
	//private  int Flag21Required=0;
	//private  int AuditingWayIsActive=0;
	private  String AuditingWayNo="";
	
	private String BaseID="";
	private String BaseSchema="";	
	//private  String LinkType="";
	//是否已存入历史记录表
	private int IsArchive=999;
	
	public int getIsArchive() {
		return IsArchive;
	}
	public void setIsArchive(int isArchive) {
		IsArchive = isArchive;
	}	
//	本记录的唯一标识，创建是自动形成，无业务含义
	public String getLinkID()
	{
	   return LinkID;
	}
	public void   setLinkID(String p_LinkID)
	{
	   LinkID=p_LinkID;
	}
//	指向主工单记录的指针
	public String getLinkBaseID()
	{
	   return LinkBaseID;
	}
	public void   setLinkBaseID(String p_LinkBaseID)
	{
	   LinkBaseID=p_LinkBaseID;
	}
//	指向主工单记录的指针
	public String getLinkBaseSchema()
	{
	   return LinkBaseSchema;
	}
	public void   setLinkBaseSchema(String p_LinkBaseSchema)
	{
	   LinkBaseSchema=p_LinkBaseSchema;
	}
//	环节表中的环节号。箭头的起点。

	public String getStartPhase()
	{
	   return StartPhase;
	}
	public void   setStartPhase(String p_StartPhase)
	{
	   StartPhase=p_StartPhase;
	}
//	环节表中的环节号。箭头的终点。

	public String getEndPhase()
	{
	   return EndPhase;
	}
	public void   setEndPhase(String p_EndPhase)
	{
	   EndPhase=p_EndPhase;
	}
//	用户选择流向时界面显示的提示。

	public String getDesc()
	{
	   return Desc;
	}
	public void   setDesc(String p_Desc)
	{
	   Desc=p_Desc;
	}
//	0停用（流程已经走过）、1启用（流程已经走过）、2等待（流程未经过走过）

	/*public int getFlag00IsAvail()
	{
	   return Flag00IsAvail;
	}
	public void   setFlag00IsAvail(int p_Flag00IsAvail)
	{
	   Flag00IsAvail=p_Flag00IsAvail;
	}
//	是否必选：1必选，0可选。必选的完成后下一级才能启动

	public int getFlag21Required()
	{
	   return Flag21Required;
	}
	public void   setFlag21Required(int p_Flag21Required)
	{
	   Flag21Required=p_Flag21Required;
	}
//	该审批环是否关闭，2未启动，1活动，0关闭。默认为：2
	public int getAuditingWayIsActive()
	{
	   return AuditingWayIsActive;
	}
	public void   setAuditingWayIsActive(int p_AuditingWayIsActive)
	{
	   AuditingWayIsActive=p_AuditingWayIsActive;
	}
	*/
//	该审批环的号。

	public String getAuditingWayNo()
	{
	   return AuditingWayNo;
	}
	public void   setAuditingWayNo(String p_AuditingWayNo)
	{
	   AuditingWayNo=p_AuditingWayNo;
	}
//	默认为“Auditing”

/*	public String getLinkType()
	{
	   return LinkType;
	}
	public void   setLinkType(String p_LinkType)
	{
	   LinkType=p_LinkType;
	}*/
//	属性设置区域--End--
	public String GetWhereSql()
	{
		StringBuffer sqlString=new StringBuffer();
		
		//1	LinkID			本记录的唯一标识，创建是自动形成，无业务含义
		//if(!LinkID.equals(""))
		//	sqlString.append(" and C1='"+LinkID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C1",LinkID));
		//700020501	LinkBaseID		指向主工单记录的指针
		//if(!LinkBaseID.equals(""))
		//	sqlString.append(" and C700020501='"+LinkBaseID+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020501",LinkBaseID));		
		//700020502	LinkBaseSchema		指向主工单记录的指针
		//if(!LinkBaseSchema.equals(""))
		//	sqlString.append(" and C700020502='"+LinkBaseSchema+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020502",LinkBaseSchema));
		//700020503	StartPhase		环节表中的环节号。箭头的起点。
		//if(!StartPhase.equals(""))
		//	sqlString.append(" and C700020503='"+StartPhase+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020503",StartPhase));
		//700020504	EndPhase		环节表中的环节号。箭头的终点。
		//if(!EndPhase.equals(""))
		//	sqlString.append(" and C700020504='"+EndPhase+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020504",EndPhase));
		//700020505	Desc			用户选择流向时界面显示的提示。
		//if(!Desc.equals(""))
		//	sqlString.append(" and C700020505='"+Desc+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020505",Desc));
		//700020506	Flag00IsAvail		0停用（流程已经走过）、1启用（流程已经走过）、2等待（流程未经过走过）
		//700020507	Flag21Required		是否必选：1必选，0可选。必选的完成后下一级才能启动
		//700020508	AuditingWayIsActive	该审批环是否关闭，2未启动，1活动，0关闭。默认为：2
		
		//700020509	AuditingWayNo		该审批环的号。
		//if(!AuditingWayNo.equals(""))
		//	sqlString.append(" and C700020509='"+AuditingWayNo+"'");
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020509",AuditingWayNo));
		//700020044	LinkType		默认为“Auditing”
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020501",BaseID));
		sqlString.append(UnionConditionForSql.getStringFiledSql("","C700020502",BaseSchema));
		
		
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

}
