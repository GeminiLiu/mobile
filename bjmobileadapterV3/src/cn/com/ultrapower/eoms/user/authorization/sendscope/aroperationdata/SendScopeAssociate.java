package cn.com.ultrapower.eoms.user.authorization.sendscope.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.bean.SendScopeBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

public class SendScopeAssociate 
{
	
	static final Logger logger = (Logger) Logger.getLogger(SendScopeAssociate.class);
	
	/**
	 * 根据参数，将其组合成AR可以识别的格式。
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
			logger.info("269 SendScopeAssociate 类中setObject(String ID, String value, String flag) 方法出现异常！"+e.getMessage());
			return null;
		}
	}
    public static ArrayList associateCondition(SendScopeBean sendScopeInfo)
    {

    	String tmp_userID 	= "";
    	String tmp_groupID 	= "";
        String tmp_mType 	= "";
        String tmp_roleID 	= "";
        String tmp_source	= "";
        String tmp_desc 	= "";
        String tmp_type		= "";
        String tmp_memo1	= "";
        String tmp_memo2	= "";
        String tmp_memo3	= "";
        
    	if(sendScopeInfo!=null)
    	{
    		tmp_userID 		= sendScopeInfo.getManageGroupUser_UserID();
	    	tmp_groupID 	= sendScopeInfo.getManageGroupUser_GroupID();
	        tmp_mType 		= sendScopeInfo.getManageGroupUser_MType();
	        tmp_roleID 		= sendScopeInfo.getManageGroupUser_RoleID();
	        tmp_source		= sendScopeInfo.getManagerGroupUser_Source();
	        tmp_desc 		= sendScopeInfo.getManageGroupUser_Desc();
	        tmp_type		= sendScopeInfo.getManageGroupUser_Type();
	        tmp_memo1		= sendScopeInfo.getManageGroupUser_Memo1();
	        tmp_memo2		= sendScopeInfo.getManageGroupUser_Memo2();
	        tmp_memo3		= sendScopeInfo.getManageGroupUser_Memo3();
    	}
    	
        ArInfo userIDInfo   = null;
        ArInfo groupIDInfo 	= null;
        ArInfo mTypeInfo    = null;
        ArInfo roleIDInfo   = null;
        ArInfo descInfo     = null;
        ArInfo sourceinfo   = null;
        ArInfo typeinfo		= null;
        ArInfo memo1info	= null;
        ArInfo memo2info	= null;
        ArInfo memo3info	= null;
        
    	try
    	{
	        //�û�ID 610000020
	        userIDInfo      = setObject("610000020", tmp_userID, "1");
	        //��ID 610000021
	        groupIDInfo 	= setObject("610000021", tmp_groupID, "1");
	        //����0��1��  610000022
	        mTypeInfo       = setObject("610000022", tmp_mType, "1");
	        //��ɫID 610000023
	        roleIDInfo      = setObject("610000023", tmp_roleID, "1");
	        //����   610000024
	        descInfo        = setObject("610000024", tmp_desc, "1");
	        //����   610000025
	        sourceinfo      = setObject("610000025", tmp_source, "1");
	        //����   610000026
	        typeinfo        = setObject("610000026", tmp_type, "1");
	        //����   610000027
	        memo1info       = setObject("610000027", tmp_memo1, "1");
	        //����   610000028
	        memo2info       = setObject("610000028", tmp_memo2, "1");
	        //����   610000029
	        memo3info       = setObject("610000029", tmp_memo3, "1");
	        
    	}
    	catch(Exception e)
    	{
    		logger.info("270 SendScopeAssociate 类中 associateCondition(SendScopeBean sendScopeInfo) 调用 setObject时出现异常！"+e.getMessage());
    	}
    	
        ArrayList returnList = new ArrayList();
        returnList.add(userIDInfo);
        returnList.add(groupIDInfo);
        returnList.add(mTypeInfo);
        returnList.add(roleIDInfo);
        returnList.add(descInfo);
        returnList.add(sourceinfo);
        returnList.add(typeinfo);
        returnList.add(memo1info);
        returnList.add(memo2info);
        returnList.add(memo3info);
        
        return returnList;
    }

    
}
