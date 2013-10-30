package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;

/**
 * <p>Description:封装ArrayList对象,封装传给ArEdit的参数<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-18
 */
public class GroupAssociate {
	
	static final Logger logger = (Logger) Logger.getLogger(GroupAssociate.class);
	
	/**
	 * <p>Description:组信息模块把字段信息封装到一个bean对象内<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-18
	 * @param ID
	 * @param value
	 * @param flag
	 * @return ArInfo
	 */
	public static ArInfo setObject(String ID,String value,String flag)
	{
    	try
    	{
    		ArInfo arGroupInfo = new ArInfo();
    		arGroupInfo.setFieldID(ID);
    		arGroupInfo.setValue(value);
    		arGroupInfo.setFlag(flag);
    		return arGroupInfo;
    	}
    	catch(Exception e)
    	{
    		logger.error("[404]GroupAssociate.setObject() 组信息模块把字段信息封装到一个bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	/**
	 * <p>Description:组信息模块把字段信息转换成ArrayList对象<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-18
	 * @param groupInfo
	 * @return ArrayList
	 */
	public static ArrayList associateInsert(GroupInfo groupInfo)
	{	
		String temp_Group_Name			= groupInfo.getGroupName();
		String temp_Group_FullName		= groupInfo.getGroupFullname();
		String temp_Group_ParentID		= groupInfo.getGroupParentid();
		String temp_Group_Type			= groupInfo.getGroupType();
		String temp_Group_OrderBy		= groupInfo.getGroupOrderBy();
		String temp_Group_Phone			= groupInfo.getGroupPhone();
		String temp_Group_Fax			= groupInfo.getGroupFax();
		String temp_Group_Status		= groupInfo.getGroupStatus();
		String temp_Group_CompanyID		= groupInfo.getGroupCompanyid();
		String temp_Group_CompanyType	= groupInfo.getGroupCompanytype();
		String temp_Group_Desc			= groupInfo.getGroupDesc();
		int temp_GroupIntId				= groupInfo.getGroupIntId();
		String temp_Group_DnId			= groupInfo.getGroupDnId();		
			
		ArInfo Group_Name        = setObject("630000018",temp_Group_Name,"1");
		ArInfo Group_FullName    = setObject("630000019",temp_Group_FullName,"1");
		ArInfo Group_ParentID    = setObject("630000020",temp_Group_ParentID,"1");
		ArInfo Group_Type        = setObject("630000021",temp_Group_Type,"1");
		ArInfo Group_OrderBy	 = setObject("630000022",temp_Group_OrderBy,"1");
		ArInfo Group_Phone       = setObject("630000023",temp_Group_Phone,"1");
		ArInfo Group_Fax         = setObject("630000024",temp_Group_Fax,"1");
		ArInfo Group_Status      = setObject("630000025",temp_Group_Status,"1");
		ArInfo Group_CompanyID   = setObject("630000026",temp_Group_CompanyID,"1");
		ArInfo Group_CompanyType = setObject("630000027",temp_Group_CompanyType,"1");
		ArInfo Group_Desc        = setObject("630000028",temp_Group_Desc,"1");
		ArInfo Group_IntId		 = setObject("630000030",String.valueOf(temp_GroupIntId),"1");
		ArInfo Group_DnId		 = setObject("630000037",temp_Group_DnId,"1");
		
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(Group_Name);
			backlist.add(Group_FullName);
			backlist.add(Group_ParentID);
			backlist.add(Group_Type);
			backlist.add(Group_OrderBy);
			backlist.add(Group_Phone);
			backlist.add(Group_Fax);
			backlist.add(Group_Status);
			backlist.add(Group_CompanyID);
			backlist.add(Group_CompanyType);
			backlist.add(Group_Desc);
			backlist.add(Group_IntId);
			backlist.add(Group_DnId);
			return backlist;
		}
		catch(Exception e)
		{
			logger.error("[405]GroupAssociate.associateInsert() 组信息模块字段信息转换成ArrayList对象失败"+e.getMessage());
    		return null;
		}
	}    		 
}
