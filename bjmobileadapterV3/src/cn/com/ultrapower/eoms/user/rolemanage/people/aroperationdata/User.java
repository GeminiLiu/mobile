package cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.UserFind;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class User {
	
	static final Logger logger = (Logger) Logger.getLogger(User.class);
	
	GetFormTableName tablename = new GetFormTableName();
	String driverurl           = tablename.GetFormName("driverurl");
	String user     		   = tablename.GetFormName("user");
	String password			   = tablename.GetFormName("password");
	int serverport			   = Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName			   = tablename.GetFormName("Remedyuser");
	
	/**
	 * <p>Description:对Remedy系统用户信息进行数据插入<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-11-6
	 * @param peopleInfo
	 * @return boolean
	 */
	public boolean insertUser(PeopleInfo peopleInfo)
	{
		try
		{
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				ArrayList userInfoValue	= UserAssociate.associateInsert(peopleInfo);
				ArEdit ar				= new ArEdit(user, password, driverurl, serverport);
				return ar.ArInster(TBLName,userInfoValue);
			}
		}
		catch(Exception e)
		{
			logger.error("[439]User.insertUser() 对Remedy系统用户信息数据添加失败"+e.getMessage());
			return false;
		}		
	}
	
	/**
	 * <p>Description:对Remedy系统用户信息进行数据修改<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-11-6
	 * @param peopleInfo
	 * @return boolean
	 */
	public boolean modifyUser(PeopleInfo peopleInfo)
	{
		try
		{
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				ArrayList userInfoValue	= UserAssociate.associateModify(peopleInfo);
				UserFind userFind		= new UserFind();		
				String c1 = userFind.findModify(peopleInfo.getUserLoginname());
				ArEdit ar				= new ArEdit(user, password, driverurl, serverport);
				return ar.ArModify(TBLName,c1,userInfoValue);
			}
		}
		catch(Exception e)
		{
			logger.error("[440]User.modifyUser() 对Remedy系统用户信息数据修改失败"+e.getMessage());
			return false;
		}
	}
	
}
