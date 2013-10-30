package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesManageBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesManage 
{
	static final Logger logger = (Logger) Logger.getLogger(RolesManage.class);
	
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
	public RolesManage()
	{
		try
		{
			tableName 		= gftn.GetFormName("rolesmanage");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("701 RolesManage 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("702  RolesManage 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	
	/**
	 * 根据参数记录ID删除一条记录。
	 * 日期 2006-12-25
	 * @author wangyanguang 
	 * @param recordID
	 * @return boolean
	 */
	public boolean rolesManageDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("703 RolesManage 类中 rolesManageDelete(String recordID)方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR向数据库中插入一条记录。
	 * 日期 2006-12-25
	 * @author wangyanguang
	 */
	public boolean rolesManageInsert(RolesManageBean rolesManageInfo)
	{
		ArrayList rolesManageValue 	= new ArrayList();
		try
		{
			rolesManageValue = RolesManageAssociate.associateCondition(rolesManageInfo);
		}
		catch(Exception e)
		{
			logger.info("704 RolesManage 类中 rolesManageInsert(RolesManageBean rolesManageInfo)" +
						"方法调用RolesManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesManageValue);
		}
		catch(Exception e)
		{
			logger.info("705 RolesManage 类中 rolesManageInsert(RolesManageBean rolesManageInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR修改一条记录。
	 * 日期 2006-12-25
	 * @author wangyanguang
	 */
	public boolean rolesManageModify(String recordID, RolesManageBean rolesManageInfo) 
	{
		ArrayList rolesManageValue 	= new ArrayList();
		try
		{
			rolesManageValue = RolesManageAssociate.associateCondition(rolesManageInfo);
		}
		catch(Exception e)
		{
			logger.info("706 RolesManage 类中  rolesManageModify(String recordID, RolesManageBean rolesManageInfo)" +
						"方法调用RolesManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesManageValue);
		}
		catch(Exception e)
		{
			logger.info("707 RolesManage 类中  rolesManageModify(String recordID, RolesManageBean rolesManageInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}

}
