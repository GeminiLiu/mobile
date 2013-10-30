package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesAccUserBackUpBean;
import cn.com.ultrapower.eoms.user.authorization.bean.RolesUserGroupRelBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesAccUserBackUpAssociate {
 
static final Logger logger = (Logger) Logger.getLogger(RolesAccUserBackUpAssociate.class);
	
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
			logger.info("748 RolesAccUserBackUpAssociate 类中setObject(String ID, String value, String flag)" +
						"方法出现异常！"+e.getMessage());
			return null;
		}
	}
	

	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2010-11-17
	 * @author lihongbo
	 */
    public static ArrayList associateCondition(RolesAccUserBackUpBean rolesaccuserbackupInfo)
    {
    	
    	String acc_roleuser = "";
    	String acc_group_id = "";
    	String acc_roleid = "";
    	long acc_orderby = 0;
    	String acc_desc = "";
    	String acc_backup1 = "";
    	String acc_backup2 = "";
    	long acc_createtime = 0;
    	String acc_operator = "";
    	String acc_userfullname = "";
    	String acc_rolename = "";
    	String acc_createdate = "";
    	String acc_operatefullname = "";
    	
    	if(rolesaccuserbackupInfo!=null)
    	{
    		acc_roleuser 		= rolesaccuserbackupInfo.getAcc_roleuser();
    		acc_group_id		= rolesaccuserbackupInfo.getAcc_group_id();
    		acc_roleid		    = rolesaccuserbackupInfo.getAcc_roleid();
    		acc_orderby		    = rolesaccuserbackupInfo.getAcc_orderby();
    		acc_desc     		= rolesaccuserbackupInfo.getAcc_desc();
    		acc_backup1  		= rolesaccuserbackupInfo.getAcc_backup1();
    		acc_backup2 		= rolesaccuserbackupInfo.getAcc_backup2();
    		acc_createtime      = rolesaccuserbackupInfo.getAcc_createtime();
    		acc_operator        = rolesaccuserbackupInfo.getAcc_operator();
    		acc_userfullname    = rolesaccuserbackupInfo.getAcc_userfullname();
    		acc_rolename        = rolesaccuserbackupInfo.getAcc_rolename();
    		acc_createdate      = rolesaccuserbackupInfo.getAcc_createdate();
    		acc_operatefullname = rolesaccuserbackupInfo.getAcc_operatefullname();
    	}
    	
    	ArInfo rolesaccuserbackuproleuser 		    = null;
    	ArInfo rolesaccuserbackupgroup_id 	        = null;
    	ArInfo rolesaccuserbackuproleid 		    = null;
    	ArInfo rolesaccuserbackuporderby 	        = null;
    	ArInfo rolesaccuserbackupdesc 		        = null;
    	ArInfo rolesaccuserbackupbackup1 		    = null;
    	ArInfo rolesaccuserbackupbackup2 		    = null;
    	ArInfo rolesaccuserbackucreatetime 		    = null;
    	ArInfo rolesaccuserbackupoperator		    = null;
    	ArInfo rolesaccuserbackupuserfullname		= null;
    	ArInfo rolesaccuserbackuprolename		    = null;
    	ArInfo rolesaccuserbackupcreatedate		    = null;
    	ArInfo rolesaccuserbackupoperatefullname	= null;
    	
    	
    	try
    	{
    		rolesaccuserbackuproleuser      	= setObject("670000001", acc_roleuser, "1");
    		rolesaccuserbackupgroup_id      	= setObject("670000002", acc_group_id, "1");
    		rolesaccuserbackuproleid  		    = setObject("670000003", acc_roleid, "1");
    		rolesaccuserbackuporderby       	= setObject("670000004", (new Long(acc_orderby).toString()), "1");
    		rolesaccuserbackupdesc       	    = setObject("670000005", acc_desc, "1");
    		rolesaccuserbackupbackup1       	= setObject("670000006", acc_backup1, "1");
    		rolesaccuserbackupbackup2       	= setObject("670000007", acc_backup2, "1");
    		rolesaccuserbackucreatetime       	= setObject("670000008", (new Long(acc_createtime).toString()), "1");
    		rolesaccuserbackupoperator       	= setObject("670000009", acc_operator, "1");
    		rolesaccuserbackupuserfullname      = setObject("670000010", acc_userfullname, "1");
    		rolesaccuserbackuprolename          = setObject("670000011", acc_rolename, "1");
    		rolesaccuserbackupcreatedate        = setObject("670000012", acc_createdate, "1");
    		rolesaccuserbackupoperatefullname   = setObject("670000013", acc_operatefullname, "1");
    	
    	}
    	catch(Exception e)
    	{
    		logger.info("749 RolesAccUserBackUpAssociate 类中 associateCondition(RolesAccUserBackUpBean rolesaccuserbackupInfo) " +
    					"调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        
        returnList.add(rolesaccuserbackuproleuser);
        returnList.add(rolesaccuserbackupgroup_id);
        returnList.add(rolesaccuserbackuproleid);
        returnList.add(rolesaccuserbackuporderby);
        returnList.add(rolesaccuserbackupdesc);
        returnList.add(rolesaccuserbackupbackup1);
        returnList.add(rolesaccuserbackupbackup2);
        returnList.add(rolesaccuserbackucreatetime);
        returnList.add(rolesaccuserbackupoperator);
        returnList.add(rolesaccuserbackupuserfullname);
        returnList.add(rolesaccuserbackuprolename);
        returnList.add(rolesaccuserbackupcreatedate);
        returnList.add(rolesaccuserbackupoperatefullname);
        
    	return returnList;
    }	

}
