package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

	import java.util.Iterator;
	import java.util.List;

import org.apache.log4j.Logger;

	import org.hibernate.Query;
	import org.hibernate.Session;
	import org.hibernate.Transaction;
	import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
	import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

	public class GetRoleUserTreeCheckBox 
	{

		static final Logger logger = (Logger) Logger.getLogger(GetRoleUserTreeCheckBox.class);
		
		public static String findGroupUser(String groupid) 
		{
			String returnStr = "";
			try {
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysGroupUserpo where C620000027="+ groupid);
//				System.out.println(query);
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				String sql="from SysGroupUserpo where C620000027="+ groupid;
				List list =HibernateDAO.queryObject(sql);
			
				if (list!=null) 
				{
					for (Iterator it = list.iterator(); it.hasNext();) 
					{
						SysGroupUserpo sysgroupuser = (SysGroupUserpo) it.next();
						String userid = sysgroupuser.getC620000028();
						returnStr = returnStr + findUser(userid,groupid);
					}

				}
				return returnStr;
			} 
			catch (Exception e) 
			{
				logger.info("240 GetRoleUserTreeCheckBox 类中 findGroupUser(String groupid) 方法执行查询时出现异常！");
				return null;
			}
		}

		public static String findUser(String userid,String groupid) 
		{
			String userStr = "";
			try 
			{
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysPeoplepo where C1="+ userid);
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				String sql="from SysPeoplepo where C1="+ userid;
				List list =HibernateDAO.queryObject(sql);
			
				if (list!=null) 
				{
					for (Iterator it = list.iterator(); it.hasNext();) 
					{
						SysPeoplepo sysuser = (SysPeoplepo) it.next();
						String tmp_userid = sysuser.getC1();
						String tmp_groupid = sysuser.getC630000015();
						String tmp_username = sysuser.getC630000003();
						userStr =userStr+ "d.add("+tmp_userid+","+groupid+",\""+tmp_username+"<input type='checkbox' name='Group_Name' value='"+tmp_username+"'>\",'','','main','','','false');";
					}
				}
				return userStr;
			}
			catch (Exception e) 
			{
				logger.info("241  GetRoleUserTreeCheckBox 类中 findUser(String userid,String groupid) 方法执行查询时出现异常！");
				return null;
			}
		}
		public static String findGroupUserid(String groupid) 
		{
			String returnStr = "";
			try 
			{
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysGroupUserpo where C620000027="+ groupid);
//				System.out.println(query);
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				String sql="from SysGroupUserpo where C620000027="+ groupid;
				List list =HibernateDAO.queryObject(sql);
			
				if (list!=null) {
					for (Iterator it = list.iterator(); it.hasNext();) 
					{
						SysGroupUserpo sysgroupuser = (SysGroupUserpo) it.next();
						String userid = sysgroupuser.getC620000028();
						returnStr = returnStr + findUserid(userid,groupid);
					}

				}
				return returnStr;
			} 
			catch (Exception e) 
			{
				logger.info("242  GetRoleUserTreeCheckBox 类中 findGroupUserid(String groupid) 方法执行查询时出现异常！");
				return null;
			}
		}

		public static String findUserid(String userid,String groupid) 
		{
			String userStr = "";
			try 
			{
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysPeoplepo where C1="+ userid);
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				
				String sql="from SysPeoplepo where C1="+ userid;
				List list =HibernateDAO.queryObject(sql);
			
				if (list!=null) 
				{
					for (Iterator it = list.iterator(); it.hasNext();) 
					{
						SysPeoplepo sysuser = (SysPeoplepo) it.next();
						String tmp_userid = sysuser.getC1();
						String tmp_groupid = sysuser.getC630000015();
						String tmp_username = sysuser.getC630000003();
						userStr =userStr+ "d.add("+tmp_userid+","+groupid+",\""+tmp_username+"<input type='checkbox' name='Group_Name' value='"+tmp_userid+"||"+tmp_username+"'>\",'','','main','','','false');";
						
					}

				}
				return userStr;
				
			} 
			catch (Exception e) 
			{
				logger.info("243  GetRoleUserTreeCheckBox 类中 findUserid(String userid,String groupid) 方法执行查询时出现异常！");
				return null;
			}
		}
	}
