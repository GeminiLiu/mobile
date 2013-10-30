package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.RolesManageBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class RolesManageAssociate 
{
	static final Logger logger = (Logger) Logger.getLogger(RolesManageAssociate.class);
	
	/**
	 * 日期 2006-12-26
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
			logger.info("708 RolesManageAssociate 类中setObject(String ID, String value, String flag) 方法出现异常！"+e.getMessage());
			return null;
		}
	}

	/**
	 * 将参数组合为AR能够识别的格式。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 * @param rolesManageInfo
	 * @return ArrayList
	 */
    public static ArrayList associateCondition(RolesManageBean rolesManageInfo)
    {
    	String Role_Name 	= "";
    	String Role_Orderby = "";
    	String Role_Desc 	= "";
    	String Role_memo 	= "";
    	String Role_memo1 	= "";
    	
    	if(rolesManageInfo!=null)
    	{
    		Role_Name 		= rolesManageInfo.getRole_Name();
    		Role_Orderby 	= rolesManageInfo.getRole_Orderby();
    		Role_Desc 		= rolesManageInfo.getRole_Desc();
    		Role_memo 		= rolesManageInfo.getRole_memo();
    		Role_memo1 		= rolesManageInfo.getRole_memo1();
    	}
    	
    	ArInfo rolesnameinfo 	= null;
    	ArInfo rolesorderby 	= null;
    	ArInfo rolesdesc 		= null;
    	ArInfo rolesmemo 		= null;
    	ArInfo rolesmemo1 		= null;
    	
    	try
    	{
    		rolesnameinfo       = setObject("660000001", Role_Name, "1");
    		rolesorderby 		= setObject("660000002", Role_Orderby, "1");
    		rolesdesc          	= setObject("660000003", Role_Desc, "1");
    		rolesmemo         	= setObject("660000004", Role_memo, "1");
    		rolesmemo1          = setObject("660000005", Role_memo1, "1");
    	}
    	catch(Exception e)
    	{
    		logger.info("709 RolesManageAssociate 类中 associateCondition(RolesManageBean rolesManageInfo) 调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        returnList.add(rolesnameinfo);
        returnList.add(rolesorderby);
        returnList.add(rolesdesc);
        returnList.add(rolesmemo);
        returnList.add(rolesmemo1);
        
    	return returnList;
    }
}
