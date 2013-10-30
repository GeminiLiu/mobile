package cn.com.ultrapower.ultrawf.models.process;

public class AuditingProcessLinkModel {
	
//	属性设置区域--Begin--
	private  String LinkID;
	private  String LinkBaseID;
	private  String LinkBaseSchema;
	private  String StartPhase;
	private  String EndPhase;
	private  String Desc;
	private  int Flag00IsAvail;
	private  int Flag21Required;
	private  int AuditingWayIsActive;
	private  String AuditingWayNo;
	private  String LinkType;
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

	public int getFlag00IsAvail()
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

	public String getLinkType()
	{
	   return LinkType;
	}
	public void   setLinkType(String p_LinkType)
	{
	   LinkType=p_LinkType;
	}
//	属性设置区域--End--
	


}
