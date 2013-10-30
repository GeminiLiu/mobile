package cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.Userpo;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class UserFind {
	
	static final Logger logger = (Logger) Logger.getLogger(UserFind.class);

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
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			-----------shigang modify--------------
			String sql="from Userpo a where a.c101 ='"+userLoginName+"'";
			List list= HibernateDAO.queryObject(sql);

			if(list.size()>0)
			{
				for(Iterator it	= list.iterator();it.hasNext();)
				{
					Userpo user	= (Userpo)it.next();
					return user.getC1();
				}
				return null;
			}
			else
			{
				logger.info("[449]UserFind.findModify() 系统中不存在该用户");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[449]UserFind.findModify() 从Remedy系统User表中根据用户登录名查找C1失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:从Remedy系统User表中根据用户登录名查找密码<p>
	 * @author wangwenzhuo
	 * @creattime 2006-02-04
	 * @param userLoginName
	 * @return String
	 */
	public String getUserPassword(String userLoginName)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from Userpo a where a.c101 ='"+userLoginName+"'");
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			-----------shigang modify--------------
			String sql="from Userpo a where a.c101 ='"+userLoginName+"'";
			List list= HibernateDAO.queryObject(sql);
			if(list.size()>0)
			{
				for(Iterator it	= list.iterator();it.hasNext();)
				{
					Userpo user	= (Userpo)it.next();
					return user.getC102();
				}
				return null;
			}
			else
			{
				logger.info("[547]UserFind.getUserPassword() 系统中不存在该用户");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("[547]UserFind.getUserPassword() 从Remedy系统User表中根据用户登录名查找密码失败"+e.getMessage());
			return null;
		}
	}

}
