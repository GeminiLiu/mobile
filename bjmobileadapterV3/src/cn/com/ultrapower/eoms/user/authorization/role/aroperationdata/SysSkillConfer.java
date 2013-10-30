package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.authorization.bean.SysSkillConferBean;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

public class SysSkillConfer
{

	static final Logger logger = (Logger) Logger.getLogger(SysSkillConfer.class);
    
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
	public SysSkillConfer()
	{
		try
		{
			GetFormTableName gftn 	= new GetFormTableName();
		    tableName 				= gftn.GetFormName("skillconfer");
		    user      				= gftn.GetFormName("user");
		    password				= gftn.GetFormName("password");
		    server					= gftn.GetFormName("driverurl");
		    port					= Integer.parseInt(gftn.GetFormName("serverport"));
		}
		catch(Exception e)
		{
			logger.info("760 SysSkillConfer 调用 GetFormTableName 初始化时出现异常！请检查配置是否正确。");
		}
		try
		{
			ae = new ArEdit(user,password, server, port);
		}
		catch(Exception e)
		{
			logger.info("761 SysSkillConfer 类中构造方法 AR连结出现异常！");
		}
	}
	
	/**
	 * 根据ID删除一条记录。
	 * 日期 2007-1-13
	 * 
	 * @author wangyanguang/王彦广 
	 * @param recordID       要删除记录的ID号
	 * @return boolean
	 */
    public boolean sysskillconferdelete(String recordID)
    {
    	try
    	{
    		return ae.ArDelete(tableName, recordID);
    	}
    	catch(Exception e)
    	{
    		logger.info("762 SysSkillConfer 类中 sysskillconferdelete 方法AR删除记录出异常！");
    		return false;
    	}
    }
    
    /**
     * 根据Bean信息向数据库中插入一条记录。
	 * 日期 2007-1-13
     * @author wangyanguang/王彦广 
     * @param skillConferBean			SysSkillConfer Bean信息。
     */
    public boolean skillconferInsert(SysSkillConferBean skillConferBean)
    {
        ArrayList skillconferValue = new ArrayList();
        try
        {
        	skillconferValue = SysSkillConferAssociate.associateCondition(skillConferBean);
        }
        catch(Exception e)
        {
        	logger.info("763 SysSkillConfer 类中 skillconferInsert 方法调 SysSkillConferAssociate 类时出现异常！");
        }
        try
        {
        	return ae.ArInster(tableName, skillconferValue);
        }
        catch(Exception e)
        {
        	logger.info("764  SysSkillConfer 类中 skillconferInsert 方法调用AR插入数据时出现异常！");
        	return false;
        }
    }
    
    /**
     * 根据记录ID与SysSkillConfer Bean信息修改一条记录。
	 * 日期 2007-1-13
     * 
     * @author wangyanguang/王彦广 
     * @param recordID				记录ID号
     * @param skillConferBean		SysSkillConfer Bean信息
     */
    public boolean skillconferModify(String recordID,SysSkillConferBean skillConferBean)
    {
        ArrayList skillconferValue = new ArrayList();
        try
        {
        	skillconferValue = SysSkillConferAssociate.associateCondition(skillConferBean);
        }
        catch(Exception e)
        {
        	logger.info("765 SysSkillConfer 类中 skillconferModify 方法调用 SysSkillConferAssociate 类时出现异常！");
        }
        try
        {
        	return ae.ArModify(tableName, recordID, skillconferValue);
        }
        catch(Exception e)
        {
        	logger.info("766 Role 类中roleModify方法调用AR修改记录时出现异常！");
        	return false;
        }
    }
	
	

}
