package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;

import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.Userpo;

/**
 * <p>Description:用于修改用户信息时的接口<p>
 * @author wangwenzhuo
 * @CreatTime 2006-12-4
 */
public class PersonalOperation {

	static final Logger logger	= (Logger) Logger.getLogger(PersonalOperation.class);
	
	GetFormTableName tablename	= new GetFormTableName();
	String driverurl			= tablename.GetFormName("driverurl");
	String user					= tablename.GetFormName("user");
	String password				= tablename.GetFormName("password");
	int serverport				= Integer.parseInt(tablename.GetFormName("serverport"));
	String TBLName				= tablename.GetFormName("people");
	
	/**
	 * <p>Description:对用户信息进行数据修改<p>
	 * @author wangwenzhuo
	 * @CreatTime 2006-10-13
	 * @param peopleInfo
	 * @param id
	 * @return boolean
	 */
	public boolean modifyPeople(PeopleInfo peopleInfo,String id)
	{
		try
		{
			ArrayList peopleInfoValue = PeopleAssociate.associateInsert(peopleInfo);
			ArEdit ar 				  = new ArEdit(user, password, driverurl, serverport);
			ar.ArModify(TBLName,id,peopleInfoValue);
			if(peopleInfo.getUserLoginname().toLowerCase().equals("demo"))
			{
				return true;
			}
			else
			{
				return modifyUser(peopleInfo);
			}
		}
		catch(Exception e)
		{
			logger.error("[414]PersonalOperation.modifyPeople() 数据修改失败"+e.getMessage());
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
				String User_groupIDs = findUserGroupIds(peopleInfo.getUserLoginname());
				peopleInfo.setUserBelongGroupId(User_groupIDs);
				ArrayList userInfoValue	= UserAssociate.associateModify(peopleInfo);	
				String c1 = findModify(peopleInfo.getUserLoginname());
				ArEdit ar				= new ArEdit(user, password, driverurl, serverport);
				return ar.ArModify(TBLName,c1,userInfoValue);
			}
		}
		catch(Exception e)
		{
			logger.error("[451]PersonalOperation.modifyUser() 数据修改失败"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * <p>Description:从Remedy系统User表中根据用户登录名查找C1<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-6
	 * @param userLoginName
	 * @return String
	 */
	public String findModify(String userLoginName)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from Userpo a where a.c101 ='"+userLoginName+"'");
//			List list		= query.list();
			
//			-----------shigang modify--------------
			String sql="from Userpo a where a.c101 ='"+userLoginName+"'";
			List list= HibernateDAO.queryObject(sql);

			for(Iterator it	= list.iterator();it.hasNext();)
			{
				Userpo user	= (Userpo)it.next();
				String c1	= user.getC1();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return c1;
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[452]PersonalOperation.findModify() 从Remedy系统用户信息修改失败"+e.getMessage());
			return null;
		}
	}

	/**
	 * <p>Description:从Remedy系统User表中根据用户登录名查找用户组ID c104<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-6
	 * @param userLoginName
	 * @return String
	 */
	public String findUserGroupIds(String userLoginName)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from Userpo a where a.c101 ='"+userLoginName+"'");
//			List list		= query.list();
			
//			-----------shigang modify--------------
			String sql="from Userpo a where a.c101 ='"+userLoginName+"'";
			List list= HibernateDAO.queryObject(sql);

			for(Iterator it	= list.iterator();it.hasNext();)
			{
				Userpo user	= (Userpo)it.next();
				String c104	= user.getC104();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return c104;
			}
			return null;
		}
		catch(Exception e)
		{
			logger.error("[452]PersonalOperation.findModify() 从Remedy系统用户信息修改失败"+e.getMessage());
			return null;
		}
	}
}
