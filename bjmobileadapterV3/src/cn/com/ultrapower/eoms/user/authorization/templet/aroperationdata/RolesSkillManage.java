package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesManageBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesSkillManage 
{

	static final Logger logger = (Logger) Logger.getLogger(RolesSkillManage.class);
	
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
	public RolesSkillManage()
	{
		try
		{
			tableName 		= gftn.GetFormName("rolesskillmanage");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("711 RolesSkillManage 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("712  RolesSkillManage 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	
	/**
	 * 根据ID删除一条记录
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesskillManageDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("713 RolesSkillManage 类中 rolesskillManageDelete(String recordID)方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据RolesSkillManageBean向数据库中插入一条记录。
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesskillManageInsert(RolesSkillManageBean rolesSkillManageInfo)
	{
		ArrayList rolesSkillManageValue 	= new ArrayList();
		try
		{
			rolesSkillManageValue = RolesSkillManageAssociate.associateCondition(rolesSkillManageInfo);
		}
		catch(Exception e)
		{
			logger.info("714 RolesSkillManage 类中 rolesskillManageInsert(RolesSkillManageBean rolesSkillManageInfo)" +
						"方法调用RolesSkillManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesSkillManageValue);
		}
		catch(Exception e)
		{
			logger.info("715 RolesSkillManage 类中 rolesSkillManageInsert(RolesSkillManageBean rolesSkillManageInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据ID和RolesSkillManageBean修改一条记录
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public boolean rolesskillManageModify(String recordID, RolesSkillManageBean rolesSkillManageInfo) 
	{
		ArrayList rolesSkillManageValue 	= new ArrayList();
		try
		{
			rolesSkillManageValue = RolesSkillManageAssociate.associateCondition(rolesSkillManageInfo);
		}
		catch(Exception e)
		{
			logger.info("716 RolesSkillManage 类中  rolesskillManageModify(String recordID, RolesSkillManageBean rolesSkillManageInfo)" +
						"方法调用RolesManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesSkillManageValue);
		}
		catch(Exception e)
		{
			logger.info("717 RolesSkillManage 类中  rolesskillManageModify(String recordID, RolesSkillManageBean rolesSkillManageInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
	
}
