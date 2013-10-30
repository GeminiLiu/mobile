package cn.com.ultrapower.eoms.user.authorization.role.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RoleBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RoleAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(RoleAssociate.class);
	
	/**
	 * 根据参数，将参数组合成一个ArInfo Bean类对象。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
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
			logger.info("207 RoleAssociate 类中 setObject方法组合成一个Bean信息时出现异常!");
			return null;
		}
	}
    
    /**
     *  根据参数Role 信息将Bean信息组合成AR能够识别格式以便做增、删、改、查操作。
     *  2006-10-16
     * 
     * @author wangyanguang/����� 
     * @param roleInfo
     * @return ArrayList
     */
    public static ArrayList associateCondition(RoleBean roleInfo) 
    {
    	
    	 String Skill_Type					= "";
         String Skill_GroupID   			= "";
         String Skill_UserID  				= "";
         String Skill_Module   				= "";
         String Skill_CategoryQueryID   	= "";
         String Skill_Action          		= "";
         String Skill_CommissionGID     	= "";
         String Skill_CommissionUID    		= "";
         String Skill_CommissionCloseTime   = "";
         String Skill_Status                = "";
         String Skill_WorkFlowType          = "";
    	try
    	{
	    	Skill_Type					= roleInfo.getSkill_Type();
	        Skill_GroupID   			= roleInfo.getSkill_GroupID();
	        Skill_UserID  				= roleInfo.getSkill_UserID();
	        Skill_Module   				= roleInfo.getSkill_Module();
	        Skill_CategoryQueryID   	= roleInfo.getSkill_CategoryQueryID();
	        Skill_Action          		= roleInfo.getSkill_Action();
	        Skill_CommissionGID     	= roleInfo.getSkill_CommissionGID();
	        Skill_CommissionUID    		= roleInfo.getSkill_CommissionUID();
	        Skill_CommissionCloseTime   = String.valueOf(roleInfo.getSkill_CommissionCloseTime());
	        Skill_Status                = roleInfo.getSkill_Status();
	        Skill_WorkFlowType          = roleInfo.getSkill_WorkFlowType();
    	}
    	catch(Exception e)
    	{
    		logger.info("208 RoleAssociate 类中 associateCondition方法从参数中取值时出现异常！");
    	}
    	
    	ArInfo typeInfo 				=  null ;
    	ArInfo tmpGrandSetInfo			=  null ;
    	ArInfo roleIDInfo  				=  null ;
    	ArInfo moduleList				=  null ;
    	ArInfo categoryQueryIDInfo		=  null ;
    	ArInfo actionInfo 				=  null ;
    	ArInfo defClose_GIDInfo 		=  null ;
    	ArInfo commissionIDInfo 		=  null ;
    	ArInfo commissionCloseTimeInfo 	=  null ;
    	ArInfo statusInfo  				=  null ;
    	ArInfo workFlowTypeInfo 		=  null ;
    	try
    	{
	    	typeInfo 					=  setObject("610000006", Skill_Type, "1");
	    	tmpGrandSetInfo				=  setObject("610000011", Skill_GroupID, "1");
	    	roleIDInfo  				=  setObject("610000007", Skill_UserID, "1");
	    	moduleList					=  setObject("610000008", Skill_Module, "1");
	    	categoryQueryIDInfo			=  setObject("610000009", Skill_CategoryQueryID, "1");
	    	actionInfo 					=  setObject("610000010", Skill_Action, "1");
	    	defClose_GIDInfo 			=  setObject("610000012", Skill_CommissionGID, "1");
	    	commissionIDInfo 			=  setObject("610000014", Skill_CommissionUID, "1");
	    	commissionCloseTimeInfo 	=  setObject("610000015", Skill_CommissionCloseTime, "2");
	    	statusInfo  				=  setObject("610000018", Skill_Status, "1");
	    	workFlowTypeInfo 			=  setObject("610000019", Skill_WorkFlowType, "1");
    	}
    	catch(Exception e)
    	{
    		logger.info("209 RoleAssociate 类中 associateCondition 方法调用setObject方法时出现异常！");
    	}
    	

        ArrayList returnList = new ArrayList();
        
        returnList.add(typeInfo);
        returnList.add(roleIDInfo);
        returnList.add(moduleList);
        returnList.add(categoryQueryIDInfo);
        returnList.add(actionInfo);
        returnList.add(tmpGrandSetInfo);
        returnList.add(defClose_GIDInfo);
        returnList.add(commissionIDInfo);
        returnList.add(commissionCloseTimeInfo);
        returnList.add(statusInfo);
        returnList.add(workFlowTypeInfo);
        
        return returnList;
    }

    
}
