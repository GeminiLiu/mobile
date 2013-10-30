package cn.com.ultrapower.ultrawf.models.process;

public class DealProcessLogModel
{
	private String processLogID;
	private String processLogBaseID;
	private String processLogBaseSchema;
	private String processID;
	private String act;
	private String logUser;
	private String logUserID;
	private long stDate;
	private int flagStart;
	private String result;
	public String getProcessLogID()
	{
		return processLogID;
	}
	public void setProcessLogID(String processLogID)
	{
		this.processLogID = processLogID;
	}
	public String getProcessLogBaseID()
	{
		return processLogBaseID;
	}
	public void setProcessLogBaseID(String processLogBaseID)
	{
		this.processLogBaseID = processLogBaseID;
	}
	public String getProcessLogBaseSchema()
	{
		return processLogBaseSchema;
	}
	public void setProcessLogBaseSchema(String processLogBaseSchema)
	{
		this.processLogBaseSchema = processLogBaseSchema;
	}
	public String getProcessID()
	{
		return processID;
	}
	public void setProcessID(String processID)
	{
		this.processID = processID;
	}
	public String getAct()
	{
		return act;
	}
	public void setAct(String act)
	{
		this.act = act;
	}
	public String getLogUser()
	{
		return logUser;
	}
	public void setLogUser(String logUser)
	{
		this.logUser = logUser;
	}
	public String getLogUserID()
	{
		return logUserID;
	}
	public void setLogUserID(String logUserID)
	{
		this.logUserID = logUserID;
	}
	public long getStDate()
	{
		return stDate;
	}
	public void setStDate(long stDate)
	{
		this.stDate = stDate;
	}
	public int getFlagStart()
	{
		return flagStart;
	}
	public void setFlagStart(int flagStart)
	{
		this.flagStart = flagStart;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	
	
}
