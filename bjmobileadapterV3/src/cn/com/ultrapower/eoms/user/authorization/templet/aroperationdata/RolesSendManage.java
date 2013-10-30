package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSendManageBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesSendManage
{
	static final Logger logger = (Logger) Logger.getLogger(RolesSendManage.class);
	
	ArEdit ae = null;

	GetFormTableName gftn 	= new GetFormTableName();
	
	String tableName 		= "";
	String user 			= "";
	String password 		= "";
	String server 			= "";
	int    port 			= 0;

	/**
	 * 初始化：根据用户名，密码连结AR。
	 * @author wangyanguang
	 */
	public RolesSendManage()
	{
		try
		{
			tableName 		= gftn.GetFormName("rolessendmanage");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("721 RolesSendManage 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("722  RolesSendManage 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	
	/**
	 * 根据参数ID删除一条记录
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesSendManageDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("723 RolesSendManage 类中 rolesSendManageDelete(String recordID)方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据RolesSendManageBean向数据库中插入一条记录。
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesSendManageInsert(RolesSendManageBean rolesSendManageInfo)
	{
		ArrayList rolesSkillManageValue 	= new ArrayList();
		try
		{
			rolesSkillManageValue = RolesSendManageAssociate.associateCondition(rolesSendManageInfo);
		}
		catch(Exception e)
		{
			logger.info("724 RolesSendManage 类中 rolesskillManageInsert(RolesSendManageBean rolesSendManageInfo)" +
						"方法调用RolesSendManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesSkillManageValue);
		}
		catch(Exception e)
		{
			logger.info("725 RolesSendManage 类中 rolesskillManageInsert(RolesSendManageBean rolesSendManageInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据ID和RolesSendManageBean修改一条记录
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesSendManageModify(String recordID, RolesSendManageBean rolesSendManageInfo) 
	{
		ArrayList rolesSkillManageValue 	= new ArrayList();
		try
		{
			rolesSkillManageValue = RolesSendManageAssociate.associateCondition(rolesSendManageInfo);
		}
		catch(Exception e)
		{
			logger.info("726 RolesSendManage 类中  rolesSendManageModify(String recordID, RolesSendManageBean rolesSendManageInfo)" +
						"方法调用RolesSendManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesSkillManageValue);
		}
		catch(Exception e)
		{
			logger.info("727 RolesSendManage 类中  rolesSendManageModify(String recordID, RolesSendManageBean rolesSendManageInfo) " +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
	
}
