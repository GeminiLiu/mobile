package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.JDBCSourceTree;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class SendScope {

	static final Logger logger = (Logger) Logger.getLogger(SendScope.class);
	
	ArEdit ae = null;

	GetFormTableName gftn 	= new GetFormTableName();
	
	String tableName 		= "";
	String user 			= "";
	String password 		= "";
	String server 			= "";
	int port 				= 0;

	/**
	 * 初始化：根据用户名，密码连结AR。
	 * @author wangyanguang
	 */
	public SendScope() 
	{
		try
		{
			tableName 		= gftn.GetFormName("sendscope");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("262 SendScope 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("263  SendScope 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}

	/**
	 * 根据记录ID调用AR删除一条记录。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param recordID
	 * @return boolean
	 */
	public boolean sendScopeDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("264 SendScope 类中 sendScopeDelete(String recordID)方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR向数据库中插入一条记录。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param sendSceopeInfo
	 * @return boolean
	 */
	public boolean sendScopeInsert(SendScopeBean sendSceopeInfo)
	{

		ArrayList sendScopeValue 	= new ArrayList();
		try
		{
			sendScopeValue 				= SendScopeAssociate.associateCondition(sendSceopeInfo);
		}
		catch(Exception e)
		{
			logger.info("265 SendScope 类中 sendScopeInsert(SendScopeBean sendSceopeInfo) 方法调用SendScopeAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, sendScopeValue);
		}
		catch(Exception e)
		{
			logger.info("266  SendScope 类中 sendScopeInsert(SendScopeBean sendSceopeInfo) 方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR修改一条记录。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param recordID
	 * @param sendScopeInfo
	 * @return boolean
	 */
	public boolean sendScopeModify(String recordID, SendScopeBean sendScopeInfo) 
	{
		ArrayList sendScopeValue 	= new ArrayList();
		try
		{
			sendScopeValue 				= SendScopeAssociate.associateCondition(sendScopeInfo);
		}
		catch(Exception e)
		{
			logger.info("267 SendScope 类中 sendScopeModify(String recordID, SendScopeBean sendScopeInfo) 方法调用SendScopeAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, sendScopeValue);
		}
		catch(Exception e)
		{
			logger.info("268 SendScope 类中 sendScopeModify(String recordID, SendScopeBean sendScopeInfo) 方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
}
