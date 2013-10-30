package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;


/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class ReGroupAssociate {

	static final Logger logger = (Logger) Logger.getLogger(ReGroupAssociate.class);
	
    /**
     * <p>Description:对Remedy系统组信息把字段信息封装到一个bean对象内<p>
     * @author wangwenzhuo
     * @creattime 2006-11-6
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
    		logger.error("[408]ReGroupAssociate.setObject() 对Remedy系统组信息把字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:对Remedy系统组信息添加时把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-11-6
	 * @param groupInfo
	 * @return ArrayList
	 */
    public static ArrayList associateInsert(GroupInfo groupInfo)
    {		
    	String temp_Group_Name			= groupInfo.getGroupName();
    	String temp_Group_Id			= String.valueOf(groupInfo.getGroupIntId());
		String temp_Group_LongGroupName	= groupInfo.getGroupFullname();
			
		ArInfo Group_Name			= setObject("105",temp_Group_Name,"1");
		ArInfo Group_Id				= setObject("106",temp_Group_Id,"1");			
		ArInfo Group_LongGroupName	= setObject("8",temp_Group_LongGroupName,"1");
		ArInfo Group_Type			= setObject("107","0","1");
		ArInfo Group_Status			= setObject("7","0","1");
		ArInfo Group_Category		= setObject("120","0","1");
		
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(Group_Name);
			backlist.add(Group_Id);
			backlist.add(Group_LongGroupName);
			backlist.add(Group_Type);
			backlist.add(Group_Status);
			backlist.add(Group_Category);
			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[409]GroupAssociate.associateInsert() 对Remedy系统组信息添加时把字段信息转换成ArrayList对象失败"+e.getMessage());
    		return null;
		}
	}

    
    /**
     * <p>Description:对Remedy系统组信息修改把字段信息转换成ArrayList对象<p>
     * @author wangwenzhuo
     * @creattime 2006-11-6
     * @param groupInfo
     * @return ArrayList
     */
    public static ArrayList associateModify(GroupInfo groupInfo)
    {
		
    	String temp_Group_Name			= groupInfo.getGroupName();
		String temp_Group_LongGroupName	= groupInfo.getGroupFullname();
			
		ArInfo Group_Name			= setObject("105",temp_Group_Name,"1");		
		ArInfo Group_LongGroupName	= setObject("8",temp_Group_LongGroupName,"1");
		
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(Group_Name);
			backlist.add(Group_LongGroupName);
			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[410]GroupAssociate.associateInsert() 对Remedy系统组信息修改把字段信息转换成ArrayList对象失败"+e.getMessage());
    		return null;
		}
	}
    
}
