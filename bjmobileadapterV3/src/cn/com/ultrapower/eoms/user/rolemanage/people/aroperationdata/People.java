package cn.com.ultrapower.eoms.user.rolemanage.people.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.rolemanage.password.hibernate.dbmanage.PasswordOp;
import cn.com.ultrapower.eoms.user.rolemanage.people.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.UserFind;

/**
 * <p>Description:封装调用（ArEdit）Remedy java api实现对数据库表单的增删改<p>
 * @author wangwenzhuo
 * @creatTime 2006-10-13Ͷ��û��Ľ���
 */
public class People {
	
	static final Logger logger	= (Logger) Logger.getLogger(People.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	String driverurl			= tablename.GetFormName("driverurl");
	String user					= tablename.GetFormName("user");
	String password				= tablename.GetFormName("password");
	int serverport				= Integer.parseInt(tablename.GetFormName("serverport"));
	String people				= tablename.GetFormName("people");
	String TBLName			   = tablename.GetFormName("Remedyuser");
	User userInfo				= new User();
	
	/**
	 * <p>Description:对用户信息进行数据添加<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-10-13
	 * @param peopleInfo
	 * @return boolean
	 */
	public boolean insertPeople(PeopleInfo peopleInfo)
	{
		try
		{
			
			ArrayList peopleInfoValue	= PeopleAssociate.associateInsert(peopleInfo);
			ArEdit ar					= new ArEdit(user,password,driverurl,serverport);
			String userID				= ar.ArInsterR(people,peopleInfoValue);
			
			if(!String.valueOf(userID).equals("null")&&!String.valueOf(userID).equals(""))
			{
				peopleInfo.setUserIntId(Integer.parseInt(userID));
				modifyPeople(peopleInfo,userID,"managerModify");
			}
			else
			{
				logger.info("[432]People.insertPeople() 对表UltraProcess:SysUser进行数据添加失败,没有获得到纪录的流水号！");
			}
			//更新系统用户表
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				//同步用户密码信息表
				PasswordOp passwordOp = new PasswordOp();
				if(passwordOp.insertPasswordInfo(peopleInfo))
				{
					return userInfo.insertUser(peopleInfo);
				}
				else
				{
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[432]People.insertPeople() 对用户信息进行数据添加失败"+e.getMessage());
			return false;
		}		
	}
	
	/**
	 * <p>Description:对用户信息进行数据修改<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-10-13
	 * @param peopleInfo
	 * @param id
	 * @param flag			personalModify个人信息修改,managerModify管理员修改
	 * @return boolean
	 */
	public boolean modifyPeople(PeopleInfo peopleInfo,String id,String flag)
	{
		try
		{
			ArrayList peopleInfoValue = PeopleAssociate.associateInsert(peopleInfo);
			ArEdit ar 				  = new ArEdit(user,password,driverurl,serverport);
			ar.ArModify(people,id,peopleInfoValue);
			//更新系统用户表
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				//根据不同修改方式,同步用户密码信息表
				//如果为管理员修改密码,包括修改其自身
				if(flag.equals("managerModify"))
				{
					//获得新旧密码
					String userLoginName	= peopleInfo.getUserLoginname(); 			
					UserFind userFind		= new UserFind();
					String oldPassword				= Function.nullString(userFind.getUserPassword(userLoginName));
					String newPassword				= Function.nullString(peopleInfo.getUserPassword());
					//如果用户密码没有改变
					if(newPassword.equals(oldPassword))
					{
						return userInfo.modifyUser(peopleInfo);
					}
					//如果用户密码改变
					else
					{
						PasswordOp passwordOp = new PasswordOp();
						if(passwordOp.modifyPasswordInfo(peopleInfo))
						{
							return userInfo.modifyUser(peopleInfo);
						}
						else
						{
							logger.error("[433]People.modifyPeople() 对用户密码信息表同步,修改用户信息失败");
							return false;
						}
					}
				}
				//如果为用户修改个人密码
				else
				{
					return userInfo.modifyUser(peopleInfo);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("[433]People.modifyPeople() 对用户信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * <p>Description:对用户信息进行数据修改<p>
	 * @author wangwumei
	 * @CreatTime 2010-12-30
	 * @param peopleInfo
	 * @param id
	 * @param flag			personalModify个人信息修改,managerModify管理员修改
	 * @return boolean
	 */
	public boolean modifyPeoplePassword(PeopleInfo peopleInfo,String id,String flag)
	{
		try
		{
			ArrayList peopleInfoValue = PeopleAssociate.associateInsert(peopleInfo);
			ArEdit ar 				  = new ArEdit(user,password,driverurl,serverport);
			ar.ArModify(people,id,peopleInfoValue);
			//更新系统用户表
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				//根据不同修改方式,同步用户密码信息表
				//如果为管理员修改密码,包括修改其自身
				if(flag.equals("managerModify"))
				{
					//获得新旧密码
					String userLoginName	= peopleInfo.getUserLoginname(); 			
					UserFind userFind		= new UserFind();
					String oldPassword				= Function.nullString(userFind.getUserPassword(userLoginName));
					String newPassword				= Function.nullString(peopleInfo.getUserPassword());
					//如果用户密码没有改变
					if(newPassword.equals(oldPassword))
					{
						return modifyUser(peopleInfo);
					}
					//如果用户密码改变
					else
					{
						PasswordOp passwordOp = new PasswordOp();
						if(passwordOp.modifyPasswordInfo(peopleInfo))
						{
							return modifyUser(peopleInfo);
						}
						else
						{
							logger.error("[433]People.modifyPeople() 对用户密码信息表同步,修改用户信息失败");
							return false;
						}
					}
				}
				//如果为用户修改个人密码
				else
				{
					return modifyUser(peopleInfo);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("[433]People.modifyPeople() 对用户信息进行数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * <p>Description:对Remedy系统用户信息进行数据修改<p>
	 * @author wangwumei
	 * @CreatTime 2010-12-30
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
				ArrayList userInfoValue	= associateModify(peopleInfo);
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
	
	 /**
     * <p>Description:Remedy系统用户信息把字段信息封装到一个bean对象内<p>
     * @author wangwumei
	 * @CreatTime 2010-12-30
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
    		logger.error("[441]UserAssociate.setObject() Remedy系统用户信息把字段信息封装bean对象内失败"+e.getMessage());
    		return null;
    	}
	}
	
	
	 /**
     * <p>Description:Remedy系统用户信息修改时字段信息转换ArrayList对象<p>
     * @author wangwumei
	 * @CreatTime 2010-12-30
	 * @param peopleInfo
	 * @return backlist
     */
    public static ArrayList associateModify(PeopleInfo peopleInfo)
    {
		String temp_User_FullName		= peopleInfo.getUserFullname();
		String temp_User_Password		= peopleInfo.getUserPassword();
		String temp_User_LicenseType	= peopleInfo.getUserLicensetype();
		

		ArInfo User_Password	= setObject("102",temp_User_Password,"1");
		ArInfo User_FullName	= setObject("8",temp_User_FullName,"1");
		ArInfo User_LicenseType	= setObject("109",temp_User_LicenseType,"1");
		
											
		try
		{
			ArrayList backlist = new ArrayList();
			backlist.add(User_Password);
			return backlist;
			
			
		}
		catch(Exception e)
		{
			logger.error("[443]UserAssociate.associateModify() Remedy系统用户信息修改时字段信息转换ArrayList对象失败"+e.getMessage());
			return null;
		}
	}
	
	
	public static void main(String[] args)
	{
		PeopleInfo peopleInfo = new PeopleInfo();

		peopleInfo.setUserFullname("王文琢");				
		peopleInfo.setUserCpid("000000000600001");
		peopleInfo.setUserCreateuser("Demo");
		peopleInfo.setUserDpid("000000000600271");
		peopleInfo.setUserFax("");
		peopleInfo.setUserLicensetype("0");
		peopleInfo.setUserLoginname("wangwenzhuo");
		peopleInfo.setUserMail("1@1.com");
		peopleInfo.setUserMobie("13910213484");
		peopleInfo.setUserOrderby("1");
		peopleInfo.setUserPassword("2");
		peopleInfo.setUserPhone("010-12321312");
		peopleInfo.setUserPosition("0");
		peopleInfo.setUserStatus("0");
		peopleInfo.setUserType("4");
		peopleInfo.setUserCptype("");
		peopleInfo.setUserBelongGroupId("000000000600001;");
		peopleInfo.setUserIsmanager("");
		

		People people = new People();
		people.modifyPeople(peopleInfo, "000000000001944", "managerModify");
	}
	
}