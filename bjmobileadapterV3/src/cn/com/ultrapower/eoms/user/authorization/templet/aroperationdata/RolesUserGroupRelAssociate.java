package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesUserGroupRelAssociate
{
	static final Logger logger = (Logger) Logger.getLogger(RolesUserGroupRelAssociate.class);
	
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
			logger.info("748 RolesUserGroupRelAssociate 类中setObject(String ID, String value, String flag)" +
						"方法出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
    public static ArrayList associateCondition(RolesUserGroupRelBean rolesUserGroupRelInfo)
    {
    	String RoleRel_UserID 	= "";
    	String RoleRel_GroupID 	= "";
    	String RoleRel_RoleID 	= "";
    	String RoleRel_OrderBy 	= "";
    	String RoleRel_Desc 	= "";
    	String RoleRel_memo 	= "";
    	String RoleRel_memo1 	= "";
    	
    	if(rolesUserGroupRelInfo!=null)
    	{
    		RoleRel_UserID 		= rolesUserGroupRelInfo.getRoleRel_UserID();
    		RoleRel_GroupID		= rolesUserGroupRelInfo.getRoleRel_GroupID();
    		RoleRel_RoleID		= rolesUserGroupRelInfo.getRoleRel_RoleID();
    		RoleRel_OrderBy		= rolesUserGroupRelInfo.getRoleRel_OrderBy();
    		RoleRel_Desc		= rolesUserGroupRelInfo.getRoleRel_Desc();
    		RoleRel_memo		= rolesUserGroupRelInfo.getRoleRel_memo();
    		RoleRel_memo1		= rolesUserGroupRelInfo.getRoleRel_memo1();
    	}
    	
    	ArInfo rolesusergroupreluserid 		= null;
    	ArInfo rolesusergrouprelgroupid 	= null;
    	ArInfo rolesusergrouprelroleid 		= null;
    	ArInfo rolesusergrouprelorderby 	= null;
    	ArInfo rolesusergroupreldesc 		= null;
    	ArInfo rolesusergrouprelmemo 		= null;
    	ArInfo rolesusergrouprelmemo1 		= null;
    	
    	try
    	{
    		rolesusergroupreluserid      	= setObject("660000026", RoleRel_UserID, "1");
    		rolesusergrouprelgroupid      	= setObject("660000027", RoleRel_GroupID, "1");
    		rolesusergrouprelroleid  		= setObject("660000028", RoleRel_RoleID, "1");
    		rolesusergrouprelorderby       	= setObject("660000030", RoleRel_OrderBy, "1");
    		rolesusergroupreldesc       	= setObject("660000031", RoleRel_Desc, "1");
    		rolesusergrouprelmemo       	= setObject("660000032", RoleRel_memo, "1");
    		rolesusergrouprelmemo1       	= setObject("660000033", RoleRel_memo1, "1");
    	
    	}
    	catch(Exception e)
    	{
    		logger.info("749 RolesUserGroupRelAssociate 类中 associateCondition(RolesUserGroupRelBean rolesUserGroupRelInfo) " +
    					"调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        
        returnList.add(rolesusergroupreluserid);
        returnList.add(rolesusergrouprelgroupid);
        returnList.add(rolesusergrouprelroleid);
        returnList.add(rolesusergrouprelorderby);
        returnList.add(rolesusergroupreldesc);
        returnList.add(rolesusergrouprelmemo);
        returnList.add(rolesusergrouprelmemo1);
        
    	return returnList;
    }	

}
