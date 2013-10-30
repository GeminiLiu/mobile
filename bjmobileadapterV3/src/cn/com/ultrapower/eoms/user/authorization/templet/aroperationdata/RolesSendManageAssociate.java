package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesSendManageBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesSendManageAssociate
{
	static final Logger logger = (Logger) Logger.getLogger(RolesSendManageAssociate.class);
	
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
			logger.info("728 RolesSendManageAssociate 类中setObject(String ID, String value, String flag)" +
						"方法出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
    public static ArrayList associateCondition(RolesSendManageBean rolesSendManageInfo)
    {
    	String RoleSend_Name 	= "";
    	String RoleSend_SouceID = "";
    	String RoleSend_Group 	= "";
    	String RoleSend_OrderBy = "";
    	String RoleSend_Desc 	= "";
    	String RoleSend_memo 	= "";
    	String RoleSend_memo1 	= "";
    	
    	if(rolesSendManageInfo!=null)
    	{
    	 	RoleSend_Name 		= rolesSendManageInfo.getRoleSend_Name();
        	RoleSend_SouceID 	= rolesSendManageInfo.getRoleSend_SouceID();
        	RoleSend_Group 		= rolesSendManageInfo.getRoleSend_Group();
        	RoleSend_OrderBy 	= rolesSendManageInfo.getRoleSend_OrderBy();
        	RoleSend_Desc 		= rolesSendManageInfo.getRoleSend_Desc();
        	RoleSend_memo 		= rolesSendManageInfo.getRoleSend_memo();
        	RoleSend_memo1 		= rolesSendManageInfo.getRoleSend_memo1();
    	}
    	
    	ArInfo rolessendnameinfo 	= null;
    	ArInfo rolessendsourceid 	= null;
    	ArInfo rolessendgroup 		= null;
    	ArInfo rolessendorderby 	= null;
    	ArInfo rolessenddesc 		= null;
    	ArInfo rolessendmemo 		= null;
    	ArInfo rolessendmemo1 		= null;
    	
    	try
    	{
    		rolessendnameinfo      	= setObject("660000014", RoleSend_Name, "1");
    		rolessendsourceid      	= setObject("660000015", RoleSend_SouceID, "1");
    		rolessendgroup  		= setObject("660000016", RoleSend_Group, "1");
    		rolessendorderby       	= setObject("660000017", RoleSend_OrderBy, "1");
    		rolessenddesc       	= setObject("660000018", RoleSend_Desc, "1");
    		rolessendmemo       	= setObject("660000019", RoleSend_memo, "1");
    		rolessendmemo1       	= setObject("660000020", RoleSend_memo1, "1");
    	
    	}
    	catch(Exception e)
    	{
    		logger.info("729 RolesSendManageAssociate 类中 associateCondition(RolesSendManageBean rolessendManageInfo) " +
    					"调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        returnList.add(rolessendnameinfo);
        returnList.add(rolessendsourceid);
        returnList.add(rolessendgroup);
        returnList.add(rolessendorderby);
        returnList.add(rolessenddesc);
        returnList.add(rolessendmemo);
        returnList.add(rolessendmemo1);
        
    	return returnList;
    }
}
