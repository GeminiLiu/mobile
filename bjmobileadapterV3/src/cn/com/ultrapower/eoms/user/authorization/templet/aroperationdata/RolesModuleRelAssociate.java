package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesModuleRelBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesModuleRelAssociate
{
	static final Logger logger = (Logger) Logger.getLogger(RolesModuleRelAssociate.class);
	/**
	 * 日期 2006-12-28
	 * @author wangyanguang
	 */
	public static ArInfo setObject(String ID, String value, String flag)
	{
		try 
		{
			ArInfo arpeopleInfo = new ArInfo();
			arpeopleInfo.setFieldID(ID);
			arpeopleInfo.setValue(value);
			arpeopleInfo.setFlag(flag);
			
			return arpeopleInfo;
		} 
		catch (Exception e) 
		{
			logger.info("738 RolesModuleRelAssociate 类中setObject(String ID, String value, String flag) 方法出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 * @param rolesManageInfo
	 */
    public static ArrayList associateCondition(RolesModuleRelBean rolesModuleRelInfo)
    {
    	
    	String RoleModule_RoleID 	= "";
    	String RoleModule_SkillID 	= "";
    	String RoleModule_Type 		= "";
    	String RoleModule_memo 		= "";
    	String RoleModule_memo1 	= "";
    	String RoleModule_memo2 	= "";
    	
    	if(rolesModuleRelInfo!=null)
    	{
        	RoleModule_RoleID 	= rolesModuleRelInfo.getRoleModule_RoleID();
        	RoleModule_SkillID 	= rolesModuleRelInfo.getRoleModule_SkillID();
        	RoleModule_Type 	= rolesModuleRelInfo.getRoleModule_Type();
        	RoleModule_memo 	= rolesModuleRelInfo.getRoleModule_memo();
        	RoleModule_memo1 	= rolesModuleRelInfo.getRoleModule_memo1();
        	RoleModule_memo2 	= rolesModuleRelInfo.getRoleModule_memo2();
    	}
    	
    	ArInfo rolemoduleid 		= null;
    	ArInfo rolemoduleskillid 	= null;
    	ArInfo rolemoduletype 		= null;
    	ArInfo rolemodulememo 		= null;
    	ArInfo rolemodulememo1 		= null;
    	ArInfo rolemodulememo2 		= null;
    	
    	try
    	{
    		rolemoduleid       		= setObject("660000020", RoleModule_RoleID, "1");
    		rolemoduleskillid 		= setObject("660000021", RoleModule_SkillID, "1");
    		rolemoduletype          = setObject("660000022", RoleModule_Type, "1");
    		rolemodulememo         	= setObject("660000023", RoleModule_memo, "1");
    		rolemodulememo1         = setObject("660000024", RoleModule_memo1, "1");
    		rolemodulememo2         = setObject("660000025", RoleModule_memo2, "1");
    	}
    	catch(Exception e)
    	{
    		logger.info("739 RolesModuleRelAssociate 类中 " +
    				"associateCondition(RolesModuleRelBean rolesModuleRelInfo) 调用 setObject时出现异常！"+e.getMessage());
    	}
    	
    	ArrayList returnList = new ArrayList();
    	
        returnList.add(rolemoduleid);
        returnList.add(rolemoduleskillid);
        returnList.add(rolemoduletype);
        returnList.add(rolemodulememo);
        returnList.add(rolemodulememo1);
        returnList.add(rolemodulememo2);
        
    	return returnList;
    }
	
}
