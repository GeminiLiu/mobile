package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.WorkflowRoleUserBackUpBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class WorkflowRoleUserBackUpAssociate {
 
static final Logger logger = (Logger) Logger.getLogger(WorkflowRoleUserBackUpAssociate.class);
	
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
			logger.info("748 WorkflowRoleUserBackUpAssociate 类中setObject(String ID, String value, String flag)" +
						"方法出现异常！"+e.getMessage());
			return null;
		}
	}
	

	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2011-02-14
	 * @author lihongbo
	 */
    public static ArrayList associateCondition(WorkflowRoleUserBackUpBean workflowroleuserbackupInfo)
    {
    	
    	 String UserName = "";
    	 String FullName = "";
    	 String ChildRoleID = "";
    	 String ChildRoleName = "";
    	 String RoleID = "";
    	 String RoleName = "";
    	 String BaseID = "";
    	 String BaseSchema = "";
    	 String BaseName = "";
    	 String UserCorp = "";
    	 String UserCorpID = "";
    	 String UserDep = "";
    	 String UserDepID = "";
    	 String UserDN = "";
    	 String UserDNID = "";
    	 String UserDesc = "";
    	 String UserCorpInitID = "";
    	 String UserDepInitID = "";
    	 long acc_createtime = 0;
    	 String acc_operator = "";
    	 String acc_userfullname = "";
    	 String acc_createdate = "";
    	 String acc_operatefullname = "";
    	
    	if(workflowroleuserbackupInfo!=null)
    	{
    		UserName 		    = workflowroleuserbackupInfo.getUserName();
    		FullName    		= workflowroleuserbackupInfo.getFullName();
    		ChildRoleID		    = workflowroleuserbackupInfo.getChildRoleID();
    		ChildRoleName		= workflowroleuserbackupInfo.getChildRoleName();
    		RoleID     		    = workflowroleuserbackupInfo.getRoleID();
    		RoleName    		= workflowroleuserbackupInfo.getRoleName();
    		BaseID     		    = workflowroleuserbackupInfo.getBaseID();
    		BaseSchema          = workflowroleuserbackupInfo.getBaseSchema();
    		BaseName            = workflowroleuserbackupInfo.getBaseName();
    		UserCorp            = workflowroleuserbackupInfo.getUserCorp();
    		UserCorpID          = workflowroleuserbackupInfo.getUserCorpID();
    		UserDep             = workflowroleuserbackupInfo.getUserDep();
    		UserDepID           = workflowroleuserbackupInfo.getUserDepID();
    		UserDN              = workflowroleuserbackupInfo.getUserDN();
    		UserDNID            = workflowroleuserbackupInfo.getUserDNID();
    		UserDesc            = workflowroleuserbackupInfo.getUserDesc();
    		UserCorpInitID      = workflowroleuserbackupInfo.getUserCorpInitID();
    		UserDepInitID       = workflowroleuserbackupInfo.getUserDepInitID();
    		acc_createtime      = workflowroleuserbackupInfo.getAcc_createtime();
    		acc_operator        = workflowroleuserbackupInfo.getAcc_operator();
    		acc_userfullname    = workflowroleuserbackupInfo.getAcc_userfullname();
    		acc_createdate      = workflowroleuserbackupInfo.getAcc_createdate();
    		acc_operatefullname = workflowroleuserbackupInfo.getAcc_operatefullname();
    	}
    	
    	ArInfo workflowroleuserbackupUserName		        = null;
    	ArInfo workflowroleuserbackupFullName		        = null;
    	ArInfo workflowroleuserbackupChildRoleID		    = null;
    	ArInfo workflowroleuserbackupChildRoleName		    = null;
    	ArInfo workflowroleuserbackupRoleID		            = null;
    	ArInfo workflowroleuserbackupRoleName		        = null;
    	ArInfo workflowroleuserbackupBaseID		            = null;
    	ArInfo workflowroleuserbackupBaseSchema		        = null;
    	ArInfo workflowroleuserbackupBaseName		        = null;
    	ArInfo workflowroleuserbackupUserCorp		        = null;
    	ArInfo workflowroleuserbackupUserCorpID		        = null;
    	ArInfo workflowroleuserbackupUserDep		        = null;
    	ArInfo workflowroleuserbackupUserDepID		        = null;
    	ArInfo workflowroleuserbackupUserDN		            = null;
    	ArInfo workflowroleuserbackupUserDNID		        = null;
    	ArInfo workflowroleuserbackupUserDesc		        = null;
    	ArInfo workflowroleuserbackupUserCorpInitID		    = null;
    	ArInfo workflowroleuserbackupUserDepInitID		    = null;
    	ArInfo workflowroleuserbackupcreatetime		        = null;
    	ArInfo workflowroleuserbackupoperator		        = null;
    	ArInfo workflowroleuserbackupuserfullname		    = null;
    	ArInfo workflowroleuserbackupcreatedate		        = null;
    	ArInfo workflowroleuserbackupoperatefullname		= null;
    	
    	
    	try
    	{
    		workflowroleuserbackupUserName      	= setObject("660000001", UserName, "1");
    		workflowroleuserbackupFullName      	= setObject("660000002", FullName, "1");
    		workflowroleuserbackupChildRoleID  		= setObject("660000003", ChildRoleID, "1");
    		workflowroleuserbackupChildRoleName     = setObject("660000004", ChildRoleName, "1");
    		workflowroleuserbackupRoleID            = setObject("660000005", RoleID, "1");
    		workflowroleuserbackupRoleName          = setObject("660000006", RoleName, "1");
    		workflowroleuserbackupBaseID            = setObject("660000007", BaseID, "1");
    		workflowroleuserbackupBaseSchema        = setObject("660000008", BaseSchema, "1");
    		workflowroleuserbackupBaseName          = setObject("660000009", BaseName, "1");
    		workflowroleuserbackupUserCorp          = setObject("660000010", UserCorp, "1");
    		workflowroleuserbackupUserCorpID        = setObject("660000011", UserCorpID, "1");
    		workflowroleuserbackupUserDep           = setObject("660000012", UserDep, "1");
    		workflowroleuserbackupUserDepID         = setObject("660000013", UserDepID, "1");
    		workflowroleuserbackupUserDN            = setObject("660000014", UserDN, "1");
    		workflowroleuserbackupUserDNID          = setObject("660000015", UserDNID, "1");
    		workflowroleuserbackupUserDesc          = setObject("660000016", UserDesc, "1");
    		workflowroleuserbackupUserCorpInitID    = setObject("660000017", UserCorpInitID, "1");
    		workflowroleuserbackupUserDepInitID     = setObject("660000018", UserDepInitID, "1");
    		workflowroleuserbackupcreatetime        = setObject("660000019", (new Long(acc_createtime).toString()), "1");
    		workflowroleuserbackupoperator          = setObject("660000020", acc_operator, "1");
    		workflowroleuserbackupuserfullname      = setObject("660000021", acc_userfullname, "1");
    		workflowroleuserbackupcreatedate        = setObject("660000022", acc_createdate, "1");
    		workflowroleuserbackupoperatefullname   = setObject("660000023", acc_operatefullname, "1");
    	
    	}
    	catch(Exception e)
    	{
    		logger.info("749 WorkflowRoleUserBackUpAssociate 类中 associateCondition(WorkflowRoleUserBackUpBean workflowroleuserbackupInfo) " +
    					"调用 setObject时出现异常！"+e.getMessage());
    	}
    	
         ArrayList returnList = new ArrayList();
        
         returnList.add(workflowroleuserbackupUserName);
         returnList.add(workflowroleuserbackupFullName);
	   	 returnList.add(workflowroleuserbackupChildRoleID);		    
		 returnList.add(workflowroleuserbackupChildRoleName);		    
		 returnList.add(workflowroleuserbackupRoleID);		            
		 returnList.add(workflowroleuserbackupRoleName);		        
		 returnList.add(workflowroleuserbackupBaseID);		            
		 returnList.add(workflowroleuserbackupBaseSchema);		        
		 returnList.add(workflowroleuserbackupBaseName);		        
		 returnList.add(workflowroleuserbackupUserCorp);		        
		 returnList.add(workflowroleuserbackupUserCorpID);		        
		 returnList.add(workflowroleuserbackupUserDep);		        
		 returnList.add(workflowroleuserbackupUserDepID);		        
		 returnList.add(workflowroleuserbackupUserDN);		            
		 returnList.add(workflowroleuserbackupUserDNID);		        
		 returnList.add(workflowroleuserbackupUserDesc);		        
		 returnList.add(workflowroleuserbackupUserCorpInitID);		    
		 returnList.add(workflowroleuserbackupUserDepInitID);		    
		 returnList.add(workflowroleuserbackupcreatetime);		        
		 returnList.add(workflowroleuserbackupoperator);		        
		 returnList.add(workflowroleuserbackupuserfullname);		    
		 returnList.add(workflowroleuserbackupcreatedate);		        
		 returnList.add(workflowroleuserbackupoperatefullname);
        
    	return returnList;
    }	

}
