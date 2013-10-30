package cn.com.ultrapower.eoms.user.authorization.usercommision.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.UserCommisionInfo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @creattime 2007-3-1
 */
public class UserCommision {

	static final Logger logger = (Logger) Logger.getLogger(UserCommision.class);
	
	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	//动态读取
	//String TBLName			   = tablename.GetFormName("UltraProcess:SysSkillConfer");
	//固定
	String skillConfertable		= "UltraProcess:SysSkillConfer";
	
	/**
	 * <p>Description:根据个人授权信息的bean执行添加操作<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param userCommisionInfo
	 * @return boolean
	 */
	public boolean insertUserCommision(UserCommisionInfo userCommisionInfo)
	{
		try
		{
			ArrayList userCommisionValue	= UserCommisionAssociate.associateInsert(userCommisionInfo);
			ArEdit ar						= new ArEdit(user, password, driverurl, serverport);
			return ar.ArInster(skillConfertable,userCommisionValue);
		}
		catch(Exception e)
		{
			logger.error("[549]UserCommision.insertUserCommision() 根据个人授权信息的bean执行添加操作失败"+e.getMessage());
			return false;
		}		
	}
	
	/**
	 * <p>Description:根据个人授权信息的bean执行修改操作<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-1
	 * @param userCommisionInfo
	 * @param skillconfer_ID
	 * @return boolean
	 */
	public boolean modifyUserCommision(UserCommisionInfo userCommisionInfo,String skillconfer_ID)
	{
		try
		{			
			ArrayList userCommisionValue	= UserCommisionAssociate.associateInsert(userCommisionInfo);
			ArEdit ar 				  		= new ArEdit(user,password,driverurl,serverport);
			return ar.ArModify(skillConfertable,skillconfer_ID,userCommisionValue);
		}
		catch(Exception e)
		{
			logger.error("[550]UserCommision.modifyUserCommision() 根据个人授权信息的bean执行修改操作失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据C1删除个人授权信息<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-3
	 * @param skillconfer_ID
	 * @return boolean
	 */
	public boolean deleteUserCommision(String skillconfer_ID)
	{
		try
		{
			ArEdit ar = new ArEdit(user, password, driverurl, serverport);
	    	return ar.ArDelete(skillConfertable, skillconfer_ID);
		}
		catch(Exception e)
		{
			logger.error("[551]UserCommision.deleteUserCommision() 根据C1删除个人授权信息失败"+e.getMessage());
			return false;
		}
	}
	
}
