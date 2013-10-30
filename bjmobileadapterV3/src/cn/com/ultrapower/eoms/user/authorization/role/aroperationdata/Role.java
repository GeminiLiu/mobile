package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetGroupTree;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class Role 
{
	
	static final Logger logger = (Logger) Logger.getLogger(Role.class);
    ArEdit ae 				= null;
    String tableName 		= "";
    String user      		= "";
    String password			= "";
    String server			= "";
    int port				= 0;
    
    /**
     * 构造函数完成初始化工作，取得用户名，密码，连结AR。
     * @author wangyanguang
     */
	public Role()
	{
		try
		{
			GetFormTableName gftn 	= new GetFormTableName();
		    tableName 				= gftn.GetFormName("role");
		    user      				= gftn.GetFormName("user");
		    password				= gftn.GetFormName("password");
		    server					= gftn.GetFormName("driverurl");
		    port					= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("200 Role 调用 GetFormTableName 初始化时出现异常！请检查form.properties是否正确。");
		}
		try
		{
			ae = new ArEdit(user,password, server, port);
		}
		catch(Exception e)
		{
			logger.info("201 Role类中构造方法 AR连结出现异常！");
		}
	}
	
	/**
	 * 根据ID删除一条记录。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param recordID       要删除记录的ID号
	 * @return boolean
	 */
    public boolean roleDelete(String recordID)
    {
    	try
    	{
    		return ae.ArDelete(tableName, recordID);
    	}
    	catch(Exception e)
    	{
    		logger.info("202 Role类中 roleDelete方法AR删除记录出异常！");
    		return false;
    	}
    }
    
    /**
     * 根据Bean信息向数据库中插入一条记录。
     * 日期 2006-12-11
     * 
     * @author wangyanguang/王彦广 
     * @param roleInfo			Role Bean信息。
     * @return boolean
     */
    public boolean roleInsert(RoleBean roleInfo)
    {
        ArrayList roleValue = new ArrayList();
        try
        {
        	roleValue = RoleAssociate.associateCondition(roleInfo);
        }
        catch(Exception e)
        {
        	logger.info("203 Role类中roleInsert方法调RoleAssociate类时出现异常！");
        }
        try
        {
        	return ae.ArInster(tableName, roleValue);
        }
        catch(Exception e)
        {
        	logger.info("204 Role类中roleInsert方法调用AR插入数据时出现异常！");
        	return false;
        }
    }
    
    /**
     * 根据记录ID与Role Bean信息修改一条记录。
     * 日期 2006-12-11
     * 
     * @author wangyanguang/王彦广 
     * @param recordID			记录ID号
     * @param roleInfo			Role Bean信息
     * @return boolean
     */
    public boolean roleModify(String recordID,RoleBean roleInfo)
    {
        ArrayList roleValue = new ArrayList();
        try
        {
        	roleValue = RoleAssociate.associateCondition(roleInfo);
        }
        catch(Exception e)
        {
        	logger.info("205 Role 类中roleModify方法调用RoleAssociate类时出现异常！");
        }
        try
        {
        	return ae.ArModify(tableName, recordID, roleValue);
        }
        catch(Exception e)
        {
        	logger.info("206 Role 类中roleModify方法调用AR修改记录时出现异常！");
        	return false;
        }
    }

}
