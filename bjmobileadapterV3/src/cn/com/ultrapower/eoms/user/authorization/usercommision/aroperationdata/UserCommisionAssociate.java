package cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.UserCommisionInfo;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @creattime 2007-3-1
 */
public class UserCommisionAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(UserCommisionAssociate.class);
	
    /**
     * <p>Description:个人授权信息模块把字段信息封装到一个bean对象内<p>
     * @author wangwenzhuo
	 * @CreatTime 2007-3-1
     * @param ID
     * @param value
     * @param flag
     * @return ArInfo
     */
    public static ArInfo setObject(String ID,String value,String flag)
    {
    	try
    	{
    		ArInfo arpeopleInfo = new ArInfo();
    		arpeopleInfo.setFieldID(ID);
    		arpeopleInfo.setValue(value);
    		arpeopleInfo.setFlag(flag);
    		return arpeopleInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[552]UserCommisionAssociate.setObject() 个人授权信息模块把字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:个人授权信息模块把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @CreatTime 2007-3-1
	 * @param userCommisionInfo
	 * @return backlist
	 */
    public static ArrayList associateInsert(UserCommisionInfo userCommisionInfo)
    {
			
		String temp_Skillconfer_Cause				= userCommisionInfo.getSkillconfer_Cause();
		String temp_Skillconfer_CancelCause			= userCommisionInfo.getSkillconfer_CancelCause();
		String temp_Skillconfer_StartTime			= String.valueOf(userCommisionInfo.getSkillconfer_StartTime());
		String temp_Skillconfer_EndTime				= String.valueOf(userCommisionInfo.getSkillconfer_EndTime());
		String temp_Skillconfer_SkillID				= userCommisionInfo.getSkillconfer_SkillID();
		String temp_Skillconfer_UserID				= userCommisionInfo.getSkillconfer_UserID();
		String temp_Skillconfer_Status				= userCommisionInfo.getSkillconfer_Status();
		String temp_Skillconfer_DealUserID			= userCommisionInfo.getSkillconfer_DealUserID();
		String temp_Skillconfer_memo				= userCommisionInfo.getSkillconfer_memo();
		String temp_Skillconfer_GroupID				= userCommisionInfo.getSkillconfer_GroupID();
		String temp_Skillconfer_SourceEnname		= userCommisionInfo.getSkillconfer_SourceEnname();
		String temp_Skillconfer_SourceID			= userCommisionInfo.getSkillconfer_SourceID();
		String temp_Skillconfer_LoginName			= userCommisionInfo.getSkillconfer_LoginName();
		String temp_Skillconfer_DeanlLoginName		= userCommisionInfo.getSkillconfer_DeanlLoginName();
		String temp_Skillconfer_GrandActionValue	= userCommisionInfo.getSkillconfer_GrandActionValue();
		String temp_Skillconfer_GrandActionName		= userCommisionInfo.getSkillconfer_GrandActionName();
			
		ArInfo Skillconfer_Cause			= setObject("610000020",temp_Skillconfer_Cause,"1");
		ArInfo Skillconfer_CancelCause		= setObject("610000021",temp_Skillconfer_CancelCause,"1");
		ArInfo Skillconfer_StartTime		= setObject("610000022",temp_Skillconfer_StartTime,"1");
		ArInfo Skillconfer_EndTime			= setObject("610000023",temp_Skillconfer_EndTime,"1");
		ArInfo Skillconfer_SkillID			= setObject("610000024",temp_Skillconfer_SkillID,"1");
		ArInfo Skillconfer_UserID			= setObject("610000025",temp_Skillconfer_UserID,"1");
		ArInfo Skillconfer_Status			= setObject("610000026",temp_Skillconfer_Status,"1");
		ArInfo Skillconfer_DealUserID		= setObject("610000027",temp_Skillconfer_DealUserID,"1");
		ArInfo Skillconfer_memo				= setObject("610000028",temp_Skillconfer_memo,"1");
		ArInfo Skillconfer_GroupID			= setObject("610000029",temp_Skillconfer_GroupID,"1");
		ArInfo Skillconfer_SourceEnname		= setObject("610000030",temp_Skillconfer_SourceEnname,"1");
		ArInfo Skillconfer_SourceID			= setObject("610000031",temp_Skillconfer_SourceID,"1");
		ArInfo Skillconfer_LoginName		= setObject("610000032",temp_Skillconfer_LoginName,"1");
		ArInfo Skillconfer_DeanlLoginName	= setObject("610000033",temp_Skillconfer_DeanlLoginName,"1");
		ArInfo Skillconfer_GrandActionValue	= setObject("610000034",temp_Skillconfer_GrandActionValue,"1");
		ArInfo Skillconfer_GrandActionName	= setObject("610000035",temp_Skillconfer_GrandActionName,"1");
							
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(Skillconfer_Cause);
			backlist.add(Skillconfer_CancelCause);
			backlist.add(Skillconfer_StartTime);
			backlist.add(Skillconfer_EndTime);
			backlist.add(Skillconfer_SkillID);
			backlist.add(Skillconfer_UserID);
			backlist.add(Skillconfer_Status);
			backlist.add(Skillconfer_DealUserID);
			backlist.add(Skillconfer_memo);
			backlist.add(Skillconfer_GroupID);
			backlist.add(Skillconfer_SourceEnname);
			backlist.add(Skillconfer_SourceID);
			backlist.add(Skillconfer_LoginName);
			backlist.add(Skillconfer_DeanlLoginName);
			backlist.add(Skillconfer_GrandActionValue);
			backlist.add(Skillconfer_GrandActionName);
			
			return backlist;		
		}
		catch(Exception e)
		{
			logger.error("[553]UserCommisionAssociate.associateInsert() 个人授权信息模块把字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}	

}
