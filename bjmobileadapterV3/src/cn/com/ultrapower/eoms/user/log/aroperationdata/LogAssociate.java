package cn.com.ultrapower.eoms.user.log.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.log.bean.LogInfo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-27
 */
public class LogAssociate {

	static final Logger logger = (Logger) Logger.getLogger(LogAssociate.class);
	
	/**
	 * <p>Description:用户日志信息把字段信息封装到一个bean对象内<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
	 */
	public static ArInfo setObject(String ID,String value,String flag){
    	try
    	{
    		ArInfo arGroupInfo = new ArInfo();
    		arGroupInfo.setFieldID(ID);
    		arGroupInfo.setValue(value);
    		arGroupInfo.setFlag(flag);
    		return arGroupInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[452]LogAssociate.setObject() 用户日志信息把字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:用户日志信息把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param logInfo
	 * @return ArrayList
	 */
	public static ArrayList associateInsert(LogInfo logInfo){
		
		String temp_UserLog_LoginName		= logInfo.getUserLogLoginName();
		String temp_UserLog_UserName		= logInfo.getUserLogUserName();
		String temp_UserLog_ModuleID		= logInfo.getUserLogModuleId();
		String temp_UserLog_UserLog			= logInfo.getUserLogUserLog();
		String temp_UserLog_UserLogDateTime	= logInfo.getUserLogUserLogDateTime();
		
		ArInfo UserLog_LoginName		= setObject("630000031",temp_UserLog_LoginName,"1");
		ArInfo UserLog_UserName			= setObject("630000032",temp_UserLog_UserName,"1");
		ArInfo UserLog_ModuleID			= setObject("630000033",temp_UserLog_ModuleID,"1");
		ArInfo UserLog_UserLog			= setObject("630000034",temp_UserLog_UserLog,"1");
		ArInfo UserLog_UserLogDateTime	= setObject("630000035",temp_UserLog_UserLogDateTime,"1");
		
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(UserLog_LoginName);
			backlist.add(UserLog_UserName);
			backlist.add(UserLog_ModuleID);
			backlist.add(UserLog_UserLog);
			backlist.add(UserLog_UserLogDateTime);
		
			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[453]LogAssociate.associateInsert() 用户日志信息把字段信息转换成ArrayList对象失败"+e.getMessage());
    		return null;
		}
	}    		 
	
}
