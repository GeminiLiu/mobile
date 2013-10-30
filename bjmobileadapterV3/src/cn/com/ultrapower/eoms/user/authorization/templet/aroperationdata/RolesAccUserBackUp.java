package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesAccUserBackUpBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesAccUserBackUp {

static final Logger logger = (Logger) Logger.getLogger(RolesAccUserBackUp.class);
	
	ArEdit ae 				= null;
	GetFormTableName gftn 	= new GetFormTableName();
	String tableName 		= "";
	String user 			= "";
	String password 		= "";
	String server 			= "";
	int    port 			= 0;
	
	/**
	 * 初始化：根据用户名，密码连结AR。
	 * @author lihongbo
	 */
	public RolesAccUserBackUp()
	{
		try
		{
			tableName 		= gftn.GetFormName("rolesaccuserbackup");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("741 RolesAccUserBackUp 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("742  RolesAccUserBackUp 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	/**
	 * 根据ID删除一条记录
	 * 日期 2010-11-17
	 * @author lihongbo
	 */
	public boolean rolesAccUserBackUpDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("743 RolesAccUserBackUp 类中 rolesAccUserBackUpDelete(String recordID)" +
					"方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据RolesAccUserBackUpBean向数据库中插入一条记录。
	 * 日期 2010-11-17
	 * @author lihongbo
	 */
	public boolean rolesAccUserBackUpInsert(RolesAccUserBackUpBean rolesaccuserbackupInfo)
	{
		ArrayList rolesAccUserBackUpValue 	= new ArrayList();
		try
		{
			rolesAccUserBackUpValue = RolesAccUserBackUpAssociate.associateCondition(rolesaccuserbackupInfo);
		}
		catch(Exception e)
		{
			logger.info("744 RolesAccUserBackUp 类中 rolesAccUserBackUpInsert(RolesAccUserBackUpBean rolesaccuserbackupInfo)" +
						"方法调用RolesUserGroupRelAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesAccUserBackUpValue);
		}
		catch(Exception e)
		{
			logger.info("745 RolesAccUserBackUp 类中 rolesAccUserBackUpInsert(RolesAccUserBackUpBean rolesaccuserbackupInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}
	/**
	 * 根据ID和RolesAccUserBackUpBean修改一条记录
	 * 日期 2010-11-17
	 * @author lihongbo
	 */
	public boolean rolesAccUserBackUpModify(String recordID, RolesAccUserBackUpBean rolesaccuserbackupInfo) 
	{
		ArrayList rolesAccUserBackUpValue 	= new ArrayList();
		try
		{
			rolesAccUserBackUpValue = RolesAccUserBackUpAssociate.associateCondition(rolesaccuserbackupInfo);
		}
		catch(Exception e)
		{
			logger.info("746 RolesAccUserBackUp 类中 rolesAccUserBackUpModify(String recordID, RolesAccUserBackUpBean rolesaccuserbackupInfo)" +
						"方法调用RolesManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesAccUserBackUpValue);
		}
		catch(Exception e)
		{
			logger.info("747 RolesAccUserBackUp 类中  rolesAccUserBackUpModify(String recordID, RolesAccUserBackUpBean rolesaccuserbackupInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
}
