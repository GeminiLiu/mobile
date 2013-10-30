package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesModuleRelBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesModuleRel
{
	static final Logger logger = (Logger) Logger
			.getLogger(RolesModuleRel.class);

	ArEdit ae = null;

	GetFormTableName gftn = new GetFormTableName();

	String tableName 	= "";
	String user 		= "";
	String password 	= "";
	String server 		= "";
	int    port 		= 0;

	/**
	 * 初始化：根据用户名，密码连结AR。
	 * 
	 * @author wangyanguang
	 */
	public RolesModuleRel()
	{

		try
		{
			tableName 	= gftn.GetFormName("rolesmodulerel");
			user 		= gftn.GetFormName("user");
			password 	= gftn.GetFormName("password");
			server 		= gftn.GetFormName("driverurl");
			port 		= Integer.parseInt(gftn.GetFormName("serverport"));
		} catch (Exception e)
		{
			logger.info("731 RolesManage 类中 构造方法 调用 GetFormTableName 时出现异常！"
					+ e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		} catch (Exception e)
		{
			logger.info("732  RolesManage 类中根据参数用户名，密码连结AR时出现异常！"
					+ e.getMessage());
		}
	}
	
	/**
	 * 根据参数记录ID删除一条记录。
	 * 日期 2006-12-28
	 * @author wangyanguang 
	 */
	public boolean rolesModuleRelDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("733 RolesModuleRel 类中 rolesModuleRelDelete(String recordID) 方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR向数据库中插入一条记录。
	 * 日期 2006-12-28
	 * @author wangyanguang
	 */
	public boolean rolesModuleRelInsert(RolesModuleRelBean rolesModuleRelInfo)
	{
		ArrayList rolesManageValue 	= new ArrayList();
		try
		{
			rolesManageValue = RolesModuleRelAssociate.associateCondition(rolesModuleRelInfo);
		}
		catch(Exception e)
		{
			logger.info("734 RolesModuleRel 类中 rolesModuleRelInsert(RolesModuleRelBean rolesModuleRelInfo)" +
						"方法调用RolesModuleRelAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesManageValue);
		}
		catch(Exception e)
		{
			logger.info("735 RolesModuleRel 类中rolesModuleRelInsert(RolesModuleRelBean rolesModuleRelInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}

	/**
	 * 根据参数调用AR修改一条记录。
	 * 日期 2006-12-28
	 * @author wangyanguang
	 */
	public boolean rolesModuleRelModify(String recordID, RolesModuleRelBean rolesModuleRelInfo) 
	{
		ArrayList rolesManageValue 	= new ArrayList();
		try
		{
			rolesManageValue = RolesModuleRelAssociate.associateCondition(rolesModuleRelInfo);
		}
		catch(Exception e)
		{
			logger.info("736 RolesModuleRel 类中  rolesModuleRelModify(String recordID, RolesModuleRelBean rolesModuleRelInfo)" +
						"方法调用RolesModuleRelAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesManageValue);
		}
		catch(Exception e)
		{
			logger.info("737 RolesModuleRel 类中  rolesModuleRelModify(String recordID, RolesModuleRelBean rolesModuleRelInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
}
