package cn.com.ultrapower.eoms.user.log.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.log.bean.LogInfo;
import cn.com.ultrapower.eoms.user.log.hibernate.dbmanage.LogFind;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-27
 */
public class ArLog {
	
	static final Logger logger = (Logger) Logger.getLogger(ArLog.class);

	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName			   = tablename.GetFormName("log");
	
	/**
	 * <p>Description:进行用户日志信息数据添加<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param logInfo
	 * @return boolean
	 */
	public boolean insertLog(LogInfo logInfo) 
	{
		try
		{
			ArrayList logInfoValue	= LogAssociate.associateInsert(logInfo);
			ArEdit ar				= new ArEdit(user, password, driverurl, serverport);
			return ar.ArInster(TBLName, logInfoValue);
		}
		catch(Exception e)
		{
			logger.error("[450]ArLog.insertLog() 进行用户日志信息数据添加失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:进行日志的记录<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-27
	 * @param ArgLoginName
	 * @param ArgModuleName
	 * @param Arginfo
	 * @return boolean
	 */
	public static boolean logWrite(String ArgLoginName, String ArgModuleName,String Arginfo)
	{
		LogFind method			= new LogFind();
//		String moduleId			= method.findModuleId(ArgModuleName);
		String userName			= method.findUserFullName(ArgLoginName);
		String UserLogDateTime	= method.findCurrentTime();
		
		LogInfo logInfo = new LogInfo();
		logInfo.setUserLogLoginName(ArgLoginName);
		logInfo.setUserLogUserLog(Arginfo);
		logInfo.setUserLogModuleId(ArgModuleName);
		logInfo.setUserLogUserLogDateTime(UserLogDateTime);
		logInfo.setUserLogUserName(userName);
		
		try
		{
			ArLog log = new ArLog();
			return log.insertLog(logInfo);
		}
		catch(Exception e)
		{
			logger.error("[451]ArLog.logWrite() 日志记录失败"+e.getMessage());
			return false;
		}
		
	}
	
}