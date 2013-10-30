package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class RolesUserGroupRel
{
	static final Logger logger = (Logger) Logger.getLogger(RolesUserGroupRel.class);
	
	ArEdit ae 				= null;
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
	public RolesUserGroupRel()
	{
		try
		{
			tableName 		= gftn.GetFormName("rolesusergrouprel");
			user 			= gftn.GetFormName("user");
			password 		= gftn.GetFormName("password");
			server 			= gftn.GetFormName("driverurl");
			port 			= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("741 RolesUserGroupRel 类中 构造方法 调用 GetFormTableName 时出现异常！"+e.getMessage());
		}
		try
		{
			ae = new ArEdit(user, password, server, port);
		}
		catch(Exception e)
		{
			logger.info("742  RolesUserGroupRel 类中根据参数用户名，密码连结AR时出现异常！"+e.getMessage());
		}
	}
	
	/**
	 * 根据ID删除一条记录
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public boolean rolesUserGroupRelDelete(String recordID) 
	{
		try
		{
			return ae.ArDelete(tableName, recordID);
		}
		catch(Exception e)
		{
			logger.info("743 RolesUserGroupRel 类中 rolesUserGroupRelDelete(String recordID)" +
					"方法调用AR进行删除时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据RolesUserGroupRelBean向数据库中插入一条记录。
	 * 日期 2006-1-8
	 * @author wangyanguang
	 */
	public boolean rolesUserGroupRelInsert(RolesUserGroupRelBean rolesusergrouprelInfo)
	{
		ArrayList rolesUserGroupRelValue 	= new ArrayList();
		try
		{
			rolesUserGroupRelValue = RolesUserGroupRelAssociate.associateCondition(rolesusergrouprelInfo);
		}
		catch(Exception e)
		{
			logger.info("744 RolesUserGroupRel 类中 rolesUserGroupRelInsert(RolesUserGroupRelBean rolesusergrouprelInfo)" +
						"方法调用RolesUserGroupRelAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArInster(tableName, rolesUserGroupRelValue);
		}
		catch(Exception e)
		{
			logger.info("745 RolesUserGroupRel 类中rolesUserGroupRelInsert(RolesUserGroupRelBean rolesusergrouprelInfo)" +
						"方法调用AR进行插入时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据ID和RolesUserGroupRelBean修改一条记录
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public boolean rolesUserGroupRelModify(String recordID, RolesUserGroupRelBean rolesusergrouprelInfo) 
	{
		ArrayList rolesUserGroupRelValue 	= new ArrayList();
		try
		{
			rolesUserGroupRelValue = RolesUserGroupRelAssociate.associateCondition(rolesusergrouprelInfo);
		}
		catch(Exception e)
		{
			logger.info("746 RolesUserGroupRel 类中 rolesUserGroupRelModify(String recordID, RolesUserGroupRelBean rolesusergrouprelInfo)" +
						"方法调用RolesManageAssociate时出现异常！"+e.getMessage());
		}
		try
		{
			return ae.ArModify(tableName, recordID, rolesUserGroupRelValue);
		}
		catch(Exception e)
		{
			logger.info("747 RolesUserGroupRel 类中  rolesUserGroupRelModify(String recordID, RolesUserGroupRelBean rolesusergrouprelInfo)" +
						"方法调用AR进行修改时出现异常！"+e.getMessage());
			return false;
		}
	}
	
}
