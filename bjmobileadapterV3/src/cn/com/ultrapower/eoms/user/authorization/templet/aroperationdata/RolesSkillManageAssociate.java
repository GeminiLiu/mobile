package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSkillManageBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesSkillManageAssociate 
{
	static final Logger logger = (Logger) Logger.getLogger(RolesSkillManageAssociate.class);
	
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
			logger.info("718 RolesSkillManageAssociate 类中setObject(String ID, String value, String flag)" +
						"方法出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
    public static ArrayList associateCondition(RolesSkillManageBean rolesSkillManageInfo)
    {
    	String RoleSkill_Name 			= "";
    	String RoleSkill_SourceID 		= "";
    	String RoleSkill_Sourcequery 	= "";
    	String RoleSkill_Grand 			= "";
    	String RoleSkill_OrderBy 		= "";
    	String RoleSkill_Desc 			= "";
    	String RoleSkill_memo 			= "";
    	String RoleSkill_memo1 			= "";
    	
    	if(rolesSkillManageInfo!=null)
    	{
    		RoleSkill_Name 			= rolesSkillManageInfo.getRoleSkill_Name();
    		RoleSkill_SourceID 		= rolesSkillManageInfo.getRoleSkill_SourceID();
    		RoleSkill_Sourcequery 	= rolesSkillManageInfo.getRoleSkill_Sourcequery();
    		RoleSkill_Grand 		= rolesSkillManageInfo.getRoleSkill_Grand();
    		RoleSkill_OrderBy 		= rolesSkillManageInfo.getRoleSkill_OrderBy();
        	RoleSkill_Desc 			= rolesSkillManageInfo.getRoleSkill_Desc();
        	RoleSkill_memo 			= rolesSkillManageInfo.getRoleSkill_memo();
        	RoleSkill_memo1 		= rolesSkillManageInfo.getRoleSkill_memo1();
    	}
    	
    	ArInfo rolesskillnameinfo 	= null;
    	ArInfo rolesskillsourceid 	= null;
    	ArInfo rolesskillgrand 		= null;
    	ArInfo rolesskillorderby 	= null;
    	ArInfo rolesskilldesc 		= null;
    	ArInfo rolesskillmemo 		= null;
    	ArInfo rolesskillmemo1 		= null;
    	ArInfo rolesskillsourcequery = null;
    	
    	try
    	{
    		rolesskillnameinfo      = setObject("660000006", RoleSkill_Name, "1");
    		rolesskillsourceid      = setObject("660000007", RoleSkill_SourceID, "1");
    		rolesskillsourcequery  	= setObject("660000008", RoleSkill_Sourcequery, "1");
    		rolesskillgrand       	= setObject("660000009", RoleSkill_Grand, "1");
    		rolesskillorderby       = setObject("660000010", RoleSkill_OrderBy, "1");
    		rolesskilldesc       	= setObject("660000011", RoleSkill_Desc, "1");
    		rolesskillmemo       	= setObject("660000012", RoleSkill_memo, "1");
    		rolesskillmemo1       	= setObject("660000013", RoleSkill_memo1, "1");
    	
    	}
    	catch(Exception e)
    	{
    		logger.info("719 RolesSkillManageAssociate 类中 associateCondition(RolesSkillManageBean rolesskillManageInfo) " +
    					"调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        returnList.add(rolesskillnameinfo);
        returnList.add(rolesskillsourceid);
        returnList.add(rolesskillgrand);
        returnList.add(rolesskillorderby);
        returnList.add(rolesskilldesc);
        returnList.add(rolesskillmemo);
        returnList.add(rolesskillmemo1);
        returnList.add(rolesskillsourcequery);
        
    	return returnList;
    }

}
