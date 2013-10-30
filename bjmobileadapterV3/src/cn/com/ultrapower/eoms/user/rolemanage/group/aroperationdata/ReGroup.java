package cn.com.ultrapower.eoms.user.rolemanage.group.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.rolemanage.group.bean.GroupInfo;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.ReGroupFind;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-18
 */
public class ReGroup {

	static final Logger logger = (Logger) Logger.getLogger(ReGroup.class);
	
	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName			   = tablename.GetFormName("Remedygroup");
	
	/**
	 * <p>Description:对Remedy系统组信息进行数据添加<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param groupInfo
	 * @return boolean
	 */
	public boolean insertReGroup(GroupInfo groupInfo)
	{
		try
		{
			if(groupInfo.getGroupName().toLowerCase().equals("administrator"))
			{
				return true;
			}
			else
			{
				ArrayList groupInfoValue	= ReGroupAssociate.associateInsert(groupInfo);
				ArEdit ar					= new ArEdit(user, password, driverurl, serverport);
				return ar.ArInster(TBLName,groupInfoValue);
			}	
		}
		catch(Exception e)
		{
			logger.error("[406]ReGroup.insertReGroup() 对Remedy系统组信息数据添加失败"+e.getMessage());
			return false;
		}		
	}
	
	/**
	 * <p>Description:对Remedy系统组信息进行数据修改<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-18
	 * @param groupInfo
	 * @return boolean
	 */
	public boolean modifyReGroup(GroupInfo groupInfo)
	{
		try
		{
			if(groupInfo.getGroupName().toLowerCase().equals("administrator"))
			{
				return true;
			}
			else
			{
				ArrayList groupInfoValue	= ReGroupAssociate.associateModify(groupInfo);
				//根据传入参数查找系统组信息ID
				ReGroupFind	groupFind		= new ReGroupFind();	
				String c1					= groupFind.findModify(String.valueOf(groupInfo.getGroupIntId()));
				if(c1==null)
				{
					logger.info("[407]ReGroup.modifyReGroup() 系统组表中没有相应记录");
					return false;
				}
				else
				{
					ArEdit ar					= new ArEdit(user, password, driverurl, serverport);
					return ar.ArModify(TBLName,c1,groupInfoValue);
				}
			}
		}
		catch(Exception e)
		{
			
			logger.error("[407]ReGroup.modifyReGroup() 对Remedy系统组信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}

}
