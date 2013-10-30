package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.GroupIsSnatchBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class GroupIsSnatchAssociate
{
	static final Logger logger = (Logger) Logger.getLogger(GroupIsSnatchAssociate.class);
	
	/**
	 * 根据参数，将其组合成AR可以识别的格式。
	 * 日期 2007-1-22
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
			logger.info(" GroupIsSnatchAssociate 类中setObject(String ID, String value, String flag) 方法出现异常！"+e.getMessage());
			return null;
		}
	}

	/**
	 * 将Bean信息内的信息分离出来后组合放到ArrayList中。
	 * 日期 2007-1-22
	 * @author wangyanguang
	 */
    public static ArrayList associateCondition(GroupIsSnatchBean groupIsSnatchInfo)
    {
    	String basecategoryname 	= "";
    	String basecategoryschama 	= "";
    	String group 				= "";
    	String groupid 				= "";
    	String groupissnatch 		= "";
    	
    	if(groupIsSnatchInfo!=null)
    	{
    		basecategoryname 	= groupIsSnatchInfo.getBasecategoryname();
    		basecategoryschama 	= groupIsSnatchInfo.getBasecategoryschama();
    		group 				= groupIsSnatchInfo.getGroup();
    		groupid 			= groupIsSnatchInfo.getGroupid();
    		groupissnatch		= groupIsSnatchInfo.getGroupissnatch();
    	}
    	
        ArInfo categoryNameIDInfo   = null;
        ArInfo schamaInfo 			= null;
        ArInfo groupInfo    		= null;
        ArInfo groupIDInfo   		= null;
        ArInfo groupissnatchInfo    = null;
        
    	try
    	{
    		categoryNameIDInfo      = setObject("650000001", basecategoryname, "1");
    		schamaInfo 				= setObject("650000002", basecategoryschama, "1");
    		groupInfo       		= setObject("650000003", group, "1");
    		groupIDInfo      		= setObject("650000004", groupid, "1");
    		groupissnatchInfo       = setObject("650000005", groupissnatch, "1");
    	}
    	catch(Exception e)
    	{
    		logger.info(" GroupIsSnatchAssociate 类中 associateConditionassociateCondition(GroupIsSnatchBean groupIsSnatchInfo)" +
    				" 调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        
        returnList.add(categoryNameIDInfo);
        returnList.add(schamaInfo);
        returnList.add(groupInfo);
        returnList.add(groupIDInfo);
        returnList.add(groupissnatchInfo);
        
        return returnList;
    }
}
