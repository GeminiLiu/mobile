package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.WorkflowRoleUserBackUpBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class WorkflowRoleUserBackUp {

static final Logger logger = (Logger) Logger.getLogger(WorkflowRoleUserBackUp.class);
	
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
	public WorkflowRoleUserBackUp()
	{
		try
		{
			tableName 		= gftn.GetFormName("workflowroleuserbackup");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("741 WorkflowRoleUserBackUp 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("742  WorkflowRoleUserBackUp 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	/**
	 * 根据ID删除一条记录
	 * 日期 2011-02-14
	 * @author lihongbo
	 */
	public boolean workflowRoleUserBackUpDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("743 WorkflowRoleUserBackUp 类中 workflowRoleUserBackUpDelete(String recordID)" +
					"方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据WorkflowRoleUserBackUpBean向数据库中插入一条记录。
	 * 日期 2011-02-14
	 * @author lihongbo
	 */
	public boolean workflowRoleUserBackUpInsert(WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)
	{
		ArrayList workflowRoleUserBackUpValue 	= new ArrayList();
		try
		{
			workflowRoleUserBackUpValue = WorkflowRoleUserBackUpAssociate.associateCondition(workflowroleuserbackupInfo);
		}
		catch(Exception e)
		{
			logger.info("744 WorkflowRoleUserBackUp 类中 workflowRoleUserBackUpInsert(WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)" +
						"方法调用WorkflowRoleUserBackUpAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, workflowRoleUserBackUpValue);
		}
		catch(Exception e)
		{
			logger.info("745 WorkflowRoleUserBackUp 类中 workflowRoleUserBackUpInsert(WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}
	/**
	 * 根据ID和WorkflowRoleUserBackUpBean修改一条记录
	 * 日期 2011-02-14
	 * @author lihongbo
	 */
	public boolean workflowRoleUserBackUpModify(String recordID, WorkflowRoleUserBackUpBean workflowroleuserbackupInfo) 
	{
		ArrayList workflowRoleUserBackUpValue 	= new ArrayList();
		try
		{
			workflowRoleUserBackUpValue = WorkflowRoleUserBackUpAssociate.associateCondition(workflowroleuserbackupInfo);
		}
		catch(Exception e)
		{
			logger.info("746 WorkflowRoleUserBackUp 类中 workflowRoleUserBackUpModify(String recordID, WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)" +
						"方法调用WorkflowRoleUserBackUpAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, workflowRoleUserBackUpValue);
		}
		catch(Exception e)
		{
			logger.info("747 WorkflowRoleUserBackUp 类中  workflowRoleUserBackUpModify(String recordID, WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
}
