package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;


/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class UserAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(UserAssociate.class);
	
    /**
     * <p>Description:把字段信息封装到一个bean对象内<p>
     * @author wangwenzhuo
	 * @CreatTime 2006-11-6
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
    		logger.error("[448]UserAssociate.setObject() 字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:Remedy系统用户信息添加时把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-11-6
	 * @param peopleInfo
	 * @return backlist
	 */
    public static ArrayList associateInsert(PeopleInfo peopleInfo)
    {
			
		String temp_User_LoginName		= peopleInfo.getUserLoginname();
		String temp_User_FullName		= peopleInfo.getUserFullname();
		String temp_User_LicenseType	= peopleInfo.getUserLicensetype();
		String temp_User_Password		= peopleInfo.getUserPassword();
		
					
		ArInfo User_LoginName				= setObject("101",temp_User_LoginName,"1");
		ArInfo User_FullName				= setObject("8",temp_User_FullName,"1");
		ArInfo User_Password				= setObject("102",temp_User_Password,"1");
		ArInfo User_LicenseType				= setObject("109",temp_User_LicenseType,"1");
		ArInfo User_FullTextLicenceType		= setObject("110","0","1");
		ArInfo User_Status					= setObject("7","0","1");
		
									
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(User_LoginName);
			backlist.add(User_FullName);
			backlist.add(User_Password);
			backlist.add(User_LicenseType);
			backlist.add(User_FullTextLicenceType);
			backlist.add(User_Status);
			
			String temp_User_GroupList = peopleInfo.getUserBelongGroupId();
			if(temp_User_GroupList==null||temp_User_GroupList.equals(""))
			{
				ArInfo User_GroupList = setObject("104","","1");
				backlist.add(User_GroupList);
				return backlist;
			}
			else
			{
				ArInfo User_GroupList = setObject("104",temp_User_GroupList,"1");
				backlist.add(User_GroupList);
				return backlist;
			}
		}
		catch(Exception e)
		{
			logger.error("[449]UserAssociate.associateInsert() Remedy系统用户信息添加时字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}
    
    /**
     * <p>Description:Remedy系统用户信息修改时字段信息转换ArrayList对象<p>
     * @author wangwenzhuo
	 * @CreatTime 2006-11-6
	 * @param peopleInfo
	 * @return backlist
     */
    public static ArrayList associateModify(PeopleInfo peopleInfo)
    {
		
		String temp_User_FullName		= peopleInfo.getUserFullname();
		String temp_User_LicenseType	= peopleInfo.getUserLicensetype();
		String temp_User_Password		= peopleInfo.getUserPassword();
		

		ArInfo User_FullName	= setObject("8",temp_User_FullName,"1");
		ArInfo User_Password	= setObject("102",temp_User_Password,"1");
		ArInfo User_LicenseType	= setObject("109",temp_User_LicenseType,"1");
		
		
											
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(User_FullName);
			backlist.add(User_LicenseType);
			backlist.add(User_Password);
			
			String temp_User_GroupList = peopleInfo.getUserBelongGroupId();
			if(temp_User_GroupList==null||temp_User_GroupList.equals(""))
			{
				ArInfo User_GroupList = setObject("104","","1");
				backlist.add(User_GroupList);
				return backlist;
			}
			else
			{
				ArInfo User_GroupList = setObject("104",temp_User_GroupList,"1");
				backlist.add(User_GroupList);
				return backlist;
			}
		}
		catch(Exception e)
		{
			logger.error("[453]UserAssociate.associateModify() Remedy系统用户信息修改时字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}

}
