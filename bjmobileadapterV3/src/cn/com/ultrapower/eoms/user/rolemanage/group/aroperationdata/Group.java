package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata.GroupAssociate;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-16Ͷ��û��Ľ���
 */
public class Group {
	
	static final Logger logger	= (Logger) Logger.getLogger(Group.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	String driverurl			= tablename.GetFormName("driverurl");
	String user					= tablename.GetFormName("user");
	String password				= tablename.GetFormName("password");
	int serverport				= Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName				= tablename.GetFormName("group");
	ReGroup regroup				= new ReGroup();
	
	/**
	 * <p>Description:对组信息进行数据插入<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-16
	 * @param groupInfo
	 * @return boolean
	 */
	public boolean insertGroup(GroupInfo groupInfo)
	{
		try
		{
			
			ArrayList groupInfoValue 	= GroupAssociate.associateInsert(groupInfo);
			ArEdit ar 	                = new ArEdit(user, password, driverurl, serverport);
			String groupIntID			= ar.ArInsterR(TBLName,groupInfoValue);
			groupInfo.setGroupIntId(Integer.parseInt(groupIntID));
			groupInfo.setGroupDnId(groupInfo.getGroupDnId()+groupIntID+";");
			if(groupInfo.getGroupName().toLowerCase().equals("administrator"))
			{
				return true;
			}
			else
			{
				modifyRGroup(groupInfo,groupIntID);
				return regroup.insertReGroup(groupInfo);
			}
		}
		catch(Exception e)
		{
			logger.error("[401]Group.insertGroup() 对组信息进行数据插入失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:对组信息进行数据修改<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-6
	 * @param groupInfo
	 * @param id
	 * @return boolean
	 */
	public boolean modifyGroup(GroupInfo groupInfo,String id)
	{
		try
		{
		
			ArrayList groupInfoValue = GroupAssociate.associateInsert(groupInfo);
			ArEdit ar 	             = new ArEdit(user, password, driverurl, serverport);
			ar.ArModify(TBLName,id,groupInfoValue);
			if(groupInfo.getGroupName().toLowerCase().equals("administrator"))
			{
				return true;
			}
			else
			{
				return regroup.modifyReGroup(groupInfo);
			}
		}
		catch(Exception e)
		{
			logger.error("[402]Group.modifyGroup() 对组信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:协助insertGroup进行数据修改<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-16
	 * @param groupInfo
	 * @param id
	 * @return boolean
	 */
	public boolean modifyRGroup(GroupInfo groupInfo,String id)
	{
		try
		{
			ArrayList groupInfoValue = GroupAssociate.associateInsert(groupInfo);
			ArEdit ar 	             = new ArEdit(user, password, driverurl, serverport);	
			return ar.ArModify(TBLName,id,groupInfoValue);
		}
		catch(Exception e)
		{
			logger.error("[403]Group.modifyRGroup() 协助insertGroup进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
}
