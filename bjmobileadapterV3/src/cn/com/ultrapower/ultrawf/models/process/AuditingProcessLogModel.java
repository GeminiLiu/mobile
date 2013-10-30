package cn.com.ultrapower.ultrawf.models.process;

public class AuditingProcessLogModel {
	
//	属性设置区域--Begin--
	private  String ProcessLogID;
	private  String ProcessID;
	private  String Act;
	private  String logUser;
	private  String logUserID;
	private  long StDate;
	private  String Result;
	
	private  String ProcessLogBaseID;
	private  String ProcessLogBaseSchema;	
	
public String getProcessLogBaseID() {
		return ProcessLogBaseID;
	}
	public void setProcessLogBaseID(String processLogBaseID) {
		ProcessLogBaseID = processLogBaseID;
	}
	public String getProcessLogBaseSchema() {
		return ProcessLogBaseSchema;
	}
	public void setProcessLogBaseSchema(String processLogBaseSchema) {
		ProcessLogBaseSchema = processLogBaseSchema;
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
	public long getStDate()
	{
	   return StDate;
	}
	public void   setStDate(long p_StDate)
	{
	   StDate=p_StDate;
	}
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
		

}
