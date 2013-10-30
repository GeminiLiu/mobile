package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupIsSnatchBean;
import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class GroupIsSnatch
{

	static final Logger logger = (Logger) Logger.getLogger(GroupIsSnatch.class);
	
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
	public GroupIsSnatch() 
	{
		try
		{
			tableName 		= gftn.GetFormName("groupissnatch");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	
	/**
	 * 根据记录ID删除一条记录。
	 * 日期 2007-1-22
	 * @author wangyanguang
	 */
	public boolean groupIsSnatchDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中 groupIsSnatchDelete(String recordID)方法调用AR进行删除时出现异常！"+
						e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据Bean信息向数据库插入一条记录。
	 * 日期 2007-1-22
	 * @author wangyanguang
	 */
	public boolean groupIsSnatchInsert(GroupIsSnatchBean groupIsSnatchInfo)
	{

		ArrayList sendScopeValue 	= new ArrayList();
		try
		{
			sendScopeValue 				= GroupIsSnatchAssociate.associateCondition(groupIsSnatchInfo);
		}
		catch(Exception e)
		{
			logger.info("265 GroupIsSnatch 类中 groupIsSnatchInsert(GroupIsSnatchBean groupIsSnatchInfo)" +
					" 方法调用SendScopeAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, sendScopeValue);
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中 groupIsSnatchInsert(GroupIsSnatchBean groupIsSnatchInfo)" +
					" 方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据ID与Bean信息修改一条记录。
	 * 日期 2007-1-22
	 * @author wangyanguang
	 */
	public boolean groupIsSnatchModify(String recordID, GroupIsSnatchBean groupIsSnatchInfo) 
	{
		ArrayList sendScopeValue 	= new ArrayList();
		try
		{
			sendScopeValue 				= GroupIsSnatchAssociate.associateCondition(groupIsSnatchInfo);
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中 groupIsSnatchModify(String recordID, GroupIsSnatchBean groupIsSnatchInfo) " +
					" 方法调用SendScopeAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, sendScopeValue);
		}
		catch(Exception e)
		{
			logger.info(" GroupIsSnatch 类中 groupIsSnatchModify(String recordID, GroupIsSnatchBean groupIsSnatchInfo) " +
					" 方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}

}
