package cn.com.ultrapower.eoms.user.authorization.bean;

public class SysSkillConferBean
{
	private String Skillconfer_Cause 		= "";
	private String Skillconfer_CancelCause 	= "";
	private long Skillconfer_StartTime 		= 0;
	private long Skillconfer_EndTime 		= 0;
	private String Skillconfer_SkillID 		= "";
	private String Skillconfer_UserID 		= "";
	private String Skillconfer_Status 		= "";
	private String Skillconfer_DealUserID 	= "";
	private String Skillconfer_memo 		= "";
	
	
	public String getSkillconfer_CancelCause()
	{
		return Skillconfer_CancelCause;
	}
	public void setSkillconfer_CancelCause(String skillconfer_CancelCause)
	{
		Skillconfer_CancelCause = skillconfer_CancelCause;
	}
	public String getSkillconfer_Cause()
	{
		return Skillconfer_Cause;
	}
	public void setSkillconfer_Cause(String skillconfer_Cause)
	{
		Skillconfer_Cause = skillconfer_Cause;
	}
	public String getSkillconfer_DealUserID()
	{
		return Skillconfer_DealUserID;
	}
	public void setSkillconfer_DealUserID(String skillconfer_DealUserID)
	{
		Skillconfer_DealUserID = skillconfer_DealUserID;
	}

	public String getSkillconfer_memo()
	{
		return Skillconfer_memo;
	}
	public void setSkillconfer_memo(String skillconfer_memo)
	{
		Skillconfer_memo = skillconfer_memo;
	}
	public String getSkillconfer_SkillID()
	{
		return Skillconfer_SkillID;
	}
	public void setSkillconfer_SkillID(String skillconfer_SkillID)
	{
		Skillconfer_SkillID = skillconfer_SkillID;
	}

	public long getSkillconfer_EndTime()
	{
		return Skillconfer_EndTime;
	}
	public void setSkillconfer_EndTime(long skillconfer_EndTime)
	{
		Skillconfer_EndTime = skillconfer_EndTime;
	}
	public long getSkillconfer_StartTime()
	{
		return Skillconfer_StartTime;
	}
	public void setSkillconfer_StartTime(long skillconfer_StartTime)
	{
		Skillconfer_StartTime = skillconfer_StartTime;
	}
	public String getSkillconfer_Status()
	{
		return Skillconfer_Status;
	}
	public void setSkillconfer_Status(String skillconfer_Status)
	{
		Skillconfer_Status = skillconfer_Status;
	}
	public String getSkillconfer_UserID()
	{
		return Skillconfer_UserID;
	}
	public void setSkillconfer_UserID(String skillconfer_UserID)
	{
		Skillconfer_UserID = skillconfer_UserID;
	}


}
