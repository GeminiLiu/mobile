package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @creatTime 2006-10-13�
 */
public class PeopleAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(PeopleAssociate.class);
	
    /**
     * <p>Description:把字段信息封装到一个bean对象内<p>
     * @author wangwenzhuo
	 * @CreatTime 2006-10-14
     * @param ID
     * @param value
     * @param flag
     * @return ArInfo
     */
    public static ArInfo setObject(String ID,String value,String flag)
    {
    	try
    	{
    		ArInfo arpeopleInfo = new ArInfo();
    		arpeopleInfo.setFieldID(ID);
    		arpeopleInfo.setValue(value);
    		arpeopleInfo.setFlag(flag);
    		return arpeopleInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[415]PeopleAssociate.setObject() 字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-10-14
	 * @param peopleInfo
	 * @return backlist
	 */
    public static ArrayList associateInsert(PeopleInfo peopleInfo)
    {
			
		String temp_User_LoginName		= peopleInfo.getUserLoginname();
		String temp_User_PassWord		= peopleInfo.getUserPassword();
		String temp_User_FullName		= peopleInfo.getUserFullname();
		String temp_User_CreateUser		= peopleInfo.getUserCreateuser();
		String temp_User_Position		= peopleInfo.getUserPosition();
		String temp_User_IsManager		= peopleInfo.getUserIsmanager();
		String temp_User_Type			= peopleInfo.getUserType();
		String temp_User_Mobie			= peopleInfo.getUserMobie();
		String temp_User_Phone			= peopleInfo.getUserPhone();
		String temp_User_Fax			= peopleInfo.getUserFax();
		String temp_User_Mail			= peopleInfo.getUserMail();
		String temp_User_Status			= peopleInfo.getUserStatus();
		String temp_User_CPID			= peopleInfo.getUserCpid();
		String temp_User_CPType			= peopleInfo.getUserCptype();
		String temp_User_DPID			= peopleInfo.getUserDpid();
		String temp_User_LicenseType	= peopleInfo.getUserLicensetype();
		String temp_User_OrderBy		= peopleInfo.getUserOrderby();
		int temp_User_IntID				= peopleInfo.getUserIntId();
			
		ArInfo User_LoginName		= setObject("630000001",temp_User_LoginName,"1");
		ArInfo User_PassWord		= setObject("630000002",temp_User_PassWord,"1");
		ArInfo User_FullName		= setObject("630000003",temp_User_FullName,"1");
		ArInfo User_CreateUser		= setObject("630000004",temp_User_CreateUser,"1");
		ArInfo User_Position		= setObject("630000005",temp_User_Position,"1");
		ArInfo User_IsManager		= setObject("630000006",temp_User_IsManager,"1");
		ArInfo User_Type			= setObject("630000007",temp_User_Type,"1");
		ArInfo User_Mobie			= setObject("630000008",temp_User_Mobie,"1");
		ArInfo User_Phone			= setObject("630000009",temp_User_Phone,"1");
		ArInfo User_Fax				= setObject("630000010",temp_User_Fax,"1");
		ArInfo User_Mail			= setObject("630000011",temp_User_Mail,"1");
		ArInfo User_Status			= setObject("630000012",temp_User_Status,"1");
		ArInfo User_CPID			= setObject("630000013",temp_User_CPID,"1");
		ArInfo User_CPType			= setObject("630000014",temp_User_CPType,"1");
		ArInfo User_DPID			= setObject("630000015",temp_User_DPID,"1");
		ArInfo User_LicenseType		= setObject("630000016",temp_User_LicenseType,"1");
		ArInfo User_OrderBy			= setObject("630000017",temp_User_OrderBy,"1");
		ArInfo User_IntID			= setObject("630000029",String.valueOf(temp_User_IntID),"1");
							
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(User_LoginName);
			backlist.add(User_PassWord);
			backlist.add(User_FullName);
			backlist.add(User_CreateUser);
			backlist.add(User_Position);
			backlist.add(User_IsManager);
			backlist.add(User_Type);
			backlist.add(User_Mobie);
			backlist.add(User_Phone);
			backlist.add(User_Fax);
			backlist.add(User_Mail);
			backlist.add(User_Status);
			backlist.add(User_CPID);
			backlist.add(User_CPType);
			backlist.add(User_DPID);
			backlist.add(User_LicenseType);
			backlist.add(User_OrderBy);
			backlist.add(User_IntID);
			
			String temp_User_BelongGroupID	= peopleInfo.getUserBelongGroupId();
			if(temp_User_BelongGroupID==null||temp_User_BelongGroupID.equals(""))
			{
				ArInfo User_BelongGroupID	= setObject("630000036","","1");
				backlist.add(User_BelongGroupID);
				return backlist;
			}
			else
			{
				ArInfo User_BelongGroupID	= setObject("630000036",temp_User_BelongGroupID,"1");
				backlist.add(User_BelongGroupID);
				return backlist;	
			}		
		}
		catch(Exception e)
		{
			logger.error("[416]PeopleAssociate.associateInsert() 字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}
}