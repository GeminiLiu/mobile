package cn.com.ultrapower.eoms.user.config.groupuser.aroperationdata;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.po.BaseCatagorygrandpo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.config.groupuser.bean.GroupUserInfo;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage.GroupUserFind;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GetGroupInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata.People;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.*;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-16
 */
public class GroupUser {
	
	static final Logger logger = (Logger) Logger.getLogger(GroupUser.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	String driverurl			= tablename.GetFormName("driverurl");
	String user					= tablename.GetFormName("user");
	String password				= tablename.GetFormName("password");
	int serverport				= Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName				= tablename.GetFormName("groupuser");
	String TBLName2				= tablename.GetFormName("userclosebasegroup");
	
	IDataBase dataBase	= null;
	Statement stmt		= null;
	
	/**
	 * <p>Description:对组成员信息进行数据添加<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-16
	 * @param groupUserInfo
	 * @return boolean
	 */
	public boolean insertGroupUser(GroupUserInfo groupUserInfo)
	{
		try
		{
			ArrayList groupUserInfoValue	= GroupUserAssociate.associateInsert(groupUserInfo);
			ArEdit ar						= new ArEdit(user, password, driverurl, serverport);
			//同步用户信息表
			insertGroupId(groupUserInfo);	
			return ar.ArInster(TBLName, groupUserInfoValue);
		}
		catch(Exception e)
		{
			logger.error("[456]GroupUser.insertGroupUser() 对组成员信息进行数据添加失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据组成员组ID进行数据删除<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-16
	 * @param id
	 * @return boolean
	 */
	public boolean deleteGroupUserByGroup(String id,String groupId)
	{
		try
		{
			ArEdit ar = new ArEdit(user, password, driverurl, serverport);
			//同步用户信息表
			modifyByGroupId(groupId);
	    	return ar.ArDelete(TBLName, id);
		}
		catch(Exception e)
		{
			logger.error("[457]GroupUser.deleteGroupUserByGroup() 根据组成员组ID进行数据删除失败"+e.getMessage());
			return false;
		}
    }
	
	/**
	 * <p>Description:根据组成员ID进行数据删除<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-11
	 * @param id
	 * @param userId
	 * @return boolean
	 */
	public boolean deleteGroupUserById(String id)
	{
		try
		{
			ArEdit ar = new ArEdit(user, password, driverurl, serverport);
			//同步用户信息表
			modifyById(id);
	    	return ar.ArDelete(TBLName, id);
		}
		catch(Exception e)
		{
			logger.error("[515]GroupUser.deleteGroupUserByUser() 根据组成员ID进行数据删除失败"+e.getMessage());
			return false;
		}
    }
	
	/**
	 * <p>Description:将组成员表中的MGroup_UserID作为User_BelongGroupID插入用户信息表<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-31
	 * @param groupUserInfo
	 * @return boolean
	 */
	public boolean insertGroupId(GroupUserInfo groupUserInfo)
	{
		//先根据传入参数得到相应用户ID和组ID
		String userId					= groupUserInfo.getMgroupUserId();
		String groupId					= groupUserInfo.getMgroupGroupId();
		//根据用户ID得到相应用户信息
		GetUserInfoList userInfoList	= new GetUserInfoList();
		SysPeoplepo peoplepo			= userInfoList.getUserInfoID(userId);
		//将所得用户信息set入PeopleInfo
		PeopleInfo peopleInfo			= new PeopleInfo();
		
		peopleInfo.setUserLoginname(Function.nullString(peoplepo.getC630000001()));
		peopleInfo.setUserPassword(Function.nullString(peoplepo.getC630000002()));
		peopleInfo.setUserFullname(Function.nullString(peoplepo.getC630000003()));
		peopleInfo.setUserCreateuser(Function.nullString(peoplepo.getC630000004()));
		peopleInfo.setUserPosition(Function.nullString(peoplepo.getC630000005()));
		peopleInfo.setUserIsmanager(Function.nullString(peoplepo.getC630000006()));
		peopleInfo.setUserType(Function.nullString(peoplepo.getC630000007()));
		peopleInfo.setUserMobie(Function.nullString(peoplepo.getC630000008()));
		peopleInfo.setUserPhone(Function.nullString(peoplepo.getC630000009()));
		peopleInfo.setUserFax(Function.nullString(peoplepo.getC630000010()));
		peopleInfo.setUserMail(Function.nullString(peoplepo.getC630000011()));
		peopleInfo.setUserStatus(Function.nullString(peoplepo.getC630000012()));
		peopleInfo.setUserCpid(Function.nullString(peoplepo.getC630000013()));
		peopleInfo.setUserCptype(Function.nullString(peoplepo.getC630000014()));
		peopleInfo.setUserDpid(Function.nullString(peoplepo.getC630000015()));
		peopleInfo.setUserLicensetype(Function.nullString(peoplepo.getC630000016()));
		peopleInfo.setUserOrderby(Function.nullString(peoplepo.getC630000017()));
		peopleInfo.setUserIntId(peoplepo.getC630000029().intValue());
		
		//新字段
		peopleInfo.setTime_out(peoplepo.getC639900003());
		peopleInfo.setUserSmsOrder(peoplepo.getC639900004());
		peopleInfo.setUserDutyShow(peoplepo.getC639900005());
		
		//从根据用户ID得到相应用户信息中查到用户所属组ID
		//该方法主要是将该字段同步到用户信息表中
		String userBelongGroupId = peoplepo.getC630000036();
		try
		{
			if(userBelongGroupId == null||userBelongGroupId.equals(""))
			{
				//若当前用户所属组ID不存在或为空，则将从组成员操作中所得组ID作为该用户的所属组ID
				peopleInfo.setUserBelongGroupId(groupId+";");
			}
			else
			{
				//若当前用户拥有组列表,则将从组成员操作中所得组ID加入组列表,即该用户属于多个组
				peopleInfo.setUserBelongGroupId(userBelongGroupId+groupId+";");
			}
			//更新用户信息表
			People people	= new People();
			return people.modifyPeople(peopleInfo,userId,"managerModify");
		}
		catch(Exception e)
		{
			logger.error("[458]GroupUser.insertGroupId()  将User_BelongGroupID插入用户信息表失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:根据组成员表中的组ID修改用户表中的所属ID字段并同步Remedy系统用户表中的组列表字段<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-17
	 * @param groupId
	 */
	public void modifyByGroupId(String groupId)
	{
		//根据组ID从组成员表中得到相应用户信息的List
		GroupUserFind groupuser = new GroupUserFind();
		List list = groupuser.find(groupId);
		try
		{
			for(Iterator it = list.iterator();it.hasNext();)
			{
				Object[] obj			= (Object[])it.next();
				SysPeoplepo peoplepo	= (SysPeoplepo)obj[0];
				PeopleInfo peopleInfo	= new PeopleInfo();
				
				peopleInfo.setUserLoginname(Function.nullString(peoplepo.getC630000001()));
				peopleInfo.setUserPassword(Function.nullString(peoplepo.getC630000002()));
				peopleInfo.setUserFullname(Function.nullString(peoplepo.getC630000003()));
				peopleInfo.setUserCreateuser(Function.nullString(peoplepo.getC630000004()));
				peopleInfo.setUserPosition(Function.nullString(peoplepo.getC630000005()));
				peopleInfo.setUserIsmanager(Function.nullString(peoplepo.getC630000006()));
				peopleInfo.setUserType(Function.nullString(peoplepo.getC630000007()));
				peopleInfo.setUserMobie(Function.nullString(peoplepo.getC630000008()));
				peopleInfo.setUserPhone(Function.nullString(peoplepo.getC630000009()));
				peopleInfo.setUserFax(Function.nullString(peoplepo.getC630000010()));
				peopleInfo.setUserMail(Function.nullString(peoplepo.getC630000011()));
				peopleInfo.setUserStatus(Function.nullString(peoplepo.getC630000012()));
				peopleInfo.setUserCpid(Function.nullString(peoplepo.getC630000013()));
				peopleInfo.setUserCptype(Function.nullString(peoplepo.getC630000014()));
				peopleInfo.setUserDpid(Function.nullString(peoplepo.getC630000015()));
				peopleInfo.setUserLicensetype(Function.nullString(peoplepo.getC630000016()));
				peopleInfo.setUserOrderby(Function.nullString(peoplepo.getC630000017()));
				peopleInfo.setUserIntId(peoplepo.getC630000029().intValue());
				
				//新字段
				peopleInfo.setTime_out(peoplepo.getC639900003());
				peopleInfo.setUserSmsOrder(peoplepo.getC639900004());
				peopleInfo.setUserDutyShow(peoplepo.getC639900005());
				
				//从根据用户ID得到相应用户信息中查到用户所属组ID
				//该方法主要是将该字段同步到用户信息表中
				String userBelongGroupId		= peoplepo.getC630000036();
				//得到该用户所属组列表的各个组
				String temp_userBelongGroupId[] = userBelongGroupId.split(";");
				String finalBelongGroupId = "";
				for(int i = 0;i<temp_userBelongGroupId.length;i++)
				{
					//如果为从组成员操作中所得组ID，则不存入组列表
					if(temp_userBelongGroupId[i].equals(groupId))
					{
						continue;
					}
					//将其他组ID作为当前用户所属组列表
					else if(!finalBelongGroupId.equals(""))
					{
						finalBelongGroupId = finalBelongGroupId + ";";
					}
					finalBelongGroupId = finalBelongGroupId + temp_userBelongGroupId[i];
				}
				//若最终拼得的组列表为空
				if(finalBelongGroupId.equals(""))
				{
					peopleInfo.setUserBelongGroupId("");
				}
				//否则
				else
				{
					peopleInfo.setUserBelongGroupId(finalBelongGroupId+";");
				}
				//同步用户信息表
				People people	= new People();
				people.modifyPeople(peopleInfo,peoplepo.getC1(),"managerModify");
			}
		}
		catch(Exception e)
		{
			logger.error("[459]GroupUser.modifyGroupId()  根据组成员表中的组ID修改用户表中的所属ID字段并同步Remedy系统用户表中的组列表字段失败"+e.getMessage());
		}
	}
	
	/**
	 * <p>Description:根据组成员ID修改用户表中的所属ID字段并同步Remedy系统用户表中的组列表字段<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-11
	 * @param id
	 */
	public void modifyById(String id)
	{
		//根据组ID从组成员表中得到相应用户ID和组ID
		GroupUserFind groupuser			= new GroupUserFind();
		SysGroupUserpo sysGroupUserpo	= groupuser.findModify(id);
		String userId					= sysGroupUserpo.getC620000028();
		String groupId					= sysGroupUserpo.getC620000027();
		
		try
		{
			//根据用户ID获取用户相应信息
			GetUserInfoList getUserInfoList	= new GetUserInfoList();
			SysPeoplepo peoplepo			= getUserInfoList.getUserInfoID(userId);
			
			PeopleInfo peopleInfo			= new PeopleInfo();
			
			peopleInfo.setUserLoginname(Function.nullString(peoplepo.getC630000001()));
			peopleInfo.setUserPassword(Function.nullString(peoplepo.getC630000002()));
			peopleInfo.setUserFullname(Function.nullString(peoplepo.getC630000003()));
			peopleInfo.setUserCreateuser(Function.nullString(peoplepo.getC630000004()));
			peopleInfo.setUserPosition(Function.nullString(peoplepo.getC630000005()));
			peopleInfo.setUserIsmanager(Function.nullString(peoplepo.getC630000006()));
			peopleInfo.setUserType(Function.nullString(peoplepo.getC630000007()));
			peopleInfo.setUserMobie(Function.nullString(peoplepo.getC630000008()));
			peopleInfo.setUserPhone(Function.nullString(peoplepo.getC630000009()));
			peopleInfo.setUserFax(Function.nullString(peoplepo.getC630000010()));
			peopleInfo.setUserMail(Function.nullString(peoplepo.getC630000011()));
			peopleInfo.setUserStatus(Function.nullString(peoplepo.getC630000012()));
			peopleInfo.setUserCpid(Function.nullString(peoplepo.getC630000013()));
			peopleInfo.setUserCptype(Function.nullString(peoplepo.getC630000014()));
			peopleInfo.setUserDpid(Function.nullString(peoplepo.getC630000015()));
			peopleInfo.setUserLicensetype(Function.nullString(peoplepo.getC630000016()));
			peopleInfo.setUserOrderby(Function.nullString(peoplepo.getC630000017()));
			peopleInfo.setUserIntId(peoplepo.getC630000029().intValue());
			
			//新字段
			peopleInfo.setTime_out(peoplepo.getC639900003());
			peopleInfo.setUserSmsOrder(peoplepo.getC639900004());
			peopleInfo.setUserDutyShow(peoplepo.getC639900005());
			
			//从根据用户ID得到相应用户信息中查到用户所属组ID
			//该方法主要是将该字段同步到用户信息表中
			String userBelongGroupId		= peoplepo.getC630000036();
			//得到该用户所属组列表的各个组
			String temp_userBelongGroupId[] = userBelongGroupId.split(";");
			String finalBelongGroupId = "";
			for(int i = 0;i<temp_userBelongGroupId.length;i++)
			{
				//如果为从组成员操作中所得组ID，则不存入组列表
				if(temp_userBelongGroupId[i].equals(groupId))
				{
					continue;
				}
				//将其他组ID作为当前用户所属组列表
				else if(!finalBelongGroupId.equals(""))
				{
					finalBelongGroupId = finalBelongGroupId + ";";
				}
				finalBelongGroupId = finalBelongGroupId + temp_userBelongGroupId[i];
			}
			//若最终拼得的组列表为空
			if(finalBelongGroupId.equals(""))
			{
				peopleInfo.setUserBelongGroupId("");
			}
			//否则
			else
			{
				peopleInfo.setUserBelongGroupId(finalBelongGroupId+";");
			}
			//同步用户信息表
			People people	= new People();
			people.modifyPeople(peopleInfo,peoplepo.getC1(),"managerModify");
		}
		catch(Exception e)
		{
			logger.error("[519]GroupUser.modifyById()  根据组成员ID修改用户表中的所属ID字段并同步Remedy系统用户表中的组列表字段失败"+e.getMessage());
		}
	}
	
	/**
	 * 获取工单类型集合
	 * author fangqun
	 * @return List
	 */
	public List getBaseCategory()
	{
		
		try
		{
			
			StringBuffer sql = new StringBuffer();
			
			sql.append(" from BaseCatagorygrandpo basecatagorygrandpo ");
			
			List list=HibernateDAO.queryObject(sql.toString());

			return list;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>Description:对人员工单处理同组归档配置添加<p>
	 * @author fangqun
	 * @creattime 2008-5-4
	 * @param groupUserInfo
	 * @return boolean
	 */
	public boolean insertUserCloseBaseGroup(GroupUserInfo groupUserInfo)
	{
		try
		{
			boolean flag = false;
			List list = null;
			list = getBaseCategory();
			
			GetUserInfoList userinfo = new GetUserInfoList();
			SysPeoplepo peoplepo = userinfo.getUserInfoID(groupUserInfo.getMgroupUserId());
			
			GetGroupInfoList groupinfo = new GetGroupInfoList();
			SysGrouppo grouppo = groupinfo.getGroupInfo(groupUserInfo.getMgroupGroupId());
			
			if(list!=null)
			{
			    for(Iterator it=list.iterator(); it.hasNext();)
		 	    {
			    	BaseCatagorygrandpo baseCatagorygrandpo = (BaseCatagorygrandpo)it.next();
			    	ArrayList groupUserInfoValue	= GroupUserAssociate.associateInsert_UserCloseBaseGroup(peoplepo,grouppo,baseCatagorygrandpo);
					ArEdit ar						= new ArEdit(user, password, driverurl, serverport);
					flag = ar.ArInster(TBLName2, groupUserInfoValue);
		 	    }
			}
			
			return flag;
		}
		catch(Exception e)
		{
			logger.error("对人员工单处理同组归档配置添加失败"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取人员工单处理同组归档配置id
	 * author fangqun
	 * @return List
	 */
	public ResultSet getUserCloseBaseGroupId(String groupuserid)
	{
		
		try
		{
			GroupUserFind groupuser			= new GroupUserFind();
			SysGroupUserpo sysGroupUserpo	= groupuser.findModify(groupuserid);
			
			GetUserInfoList userinfo = new GetUserInfoList();
			SysPeoplepo peoplepo = userinfo.getUserInfoID(sysGroupUserpo.getC620000028());
			
			RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
			String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUserCloseBaseGroup);
			StringBuffer sql = new StringBuffer();
			
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stmt		= dataBase.GetStatement();
			
			sql.append(" select base.c1 from "+strTblName+" base where base.c650000004='"+peoplepo.getC630000001()
					+"' and base.c650000006="+sysGroupUserpo.getC620000027());
			System.out.println(sql.toString());
			ResultSet result = null;
			result = stmt.executeQuery(sql.toString());

			return result;
	    }
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>Description:根据组成员ID进行数据删除<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-11
	 * @param id
	 * @param userId
	 * @return boolean
	 */
	public boolean deleteUserCloseBaseGroupById(String groupuserid)
	{
		try
		{
			boolean flag = false;
			ResultSet result = null;
			result = getUserCloseBaseGroupId(groupuserid);
			
			if(result!=null)
			{
			    while(result.next())
		 	    {
			    	ArEdit ar = new ArEdit(user, password, driverurl, serverport);
			    	flag = ar.ArDelete(TBLName2, (String)result.getString(1));
		 	    }
			}
			if(result != null && stmt != null && dataBase != null){
				result.close();
				Function.closeDataBaseSource(null, stmt, dataBase);
				result = null;
				stmt = null;
				dataBase = null;
			}
			
	    	return flag;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
    }
}
