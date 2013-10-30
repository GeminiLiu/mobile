package cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.sendscope.hibernate.po.AbstractT97;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class GetSendScope 
{
	static final Logger logger = (Logger) Logger.getLogger(GetSendScope.class);

	static Session session = null;

	/**
	 *  初始化HibernateSessionFactory.
	 *  @author wangyanguang
	 */
	public GetSendScope() {
		try 
		{
//			session = HibernateSessionFactory.currentSession();
//			logger.info("[30000]Hibernate sucess!");
		} 
		catch (Exception ex) 
		{
			logger.info("271 GetSendScope 类中 构造方法初始化 HibernateSessionFactory时出现异常！"+ ex.getMessage());
		}
	}

	/**
	 * 查询派发表中所有记录。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @return List
	 */
	public List getSendScope() 
	{

		try 
		{
//			Transaction tx = session.beginTransaction();
//			String sql = "from ManageGroupUserpo";
//			logger.info(sql);
//			List l_result = session.find(sql);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return l_result;
			return HibernateDAO.queryObject("from ManageGroupUserpo");
		}
		catch (Exception e) 
		{
			logger.info("272 GetSendScope 类中 调用 getSendScope()执行查询时出现异常！"+ e.getMessage());
			return null;
		}
	}

	/**
	 * 根据派发表中的ID查询出此ID的Bean信息。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param id
	 * @return AbstractT97
	 */
	public AbstractT97 getSendScopeBean(String id) 
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session
//					.createQuery("from ManageGroupUserpo where c1 =" + id);
//			List list = query.list();

			String sql="from ManageGroupUserpo where c1 ='" + id + "'";
						List list =HibernateDAO.queryObject(sql);
					
			if(list!=null)
			{
				for (Iterator it = list.iterator(); it.hasNext();) 
				{
					AbstractT97 t97 = (AbstractT97) it.next();
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return t97;
				}
			}
			return null;
		}
		catch (Exception e) 
		{
			logger.info("273 GetSendScope 类中 调用 getSendScopeBean(String id)执行查询时出现异常！"+ e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据角色ID查询派发表中满足条件记录集合。
	 * 日期 2006-12-11
	 * 
	 * @author wangyanguang/王彦广 
	 * @param roleid
	 * @return List
	 */
	public static List getManagerGroupUserInfo(String roleid)
	{
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery("from ManageGroupUserpo where c610000020="+roleid);
//			List list = query.list();
//			
			String sql="from ManageGroupUserpo where c610000020='"+roleid+"'";
			List list =HibernateDAO.queryObject(sql);
			
			if(list!=null)
			{
				if(list.size()>0)
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return list;
				}else
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return null;
				}
			}else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.info("274 GetSendScope 类中 调用getManagerGroupUserInfo(String roleid)执行查询时出现异常！"+e.getMessage());
			return null;
		}
		
	}
	
	//根据用户ID、组ID、派发组ID、资源ID查询，看当前记录是否存在。
	public static boolean isExists(String userid,String groupid,String sendscopegroupid,String sourceid,String type)
	{

		try 
		{
			String sql = "from ManageGroupUserpo t where t.c610000020='"+userid+"' and t.c610000021='"+groupid
							+"' and t.c610000023='"+sendscopegroupid+"' and t.c610000025='"+sourceid+"' and t.c610000026='"+type+"'";
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
			List list =HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size() >0)
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return true;
				}else
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return false;
				}
			}else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.info("275 GetSendScope 类中 调用 isExists(String userid,String groupid,String sendscopegroupid,String sourceid)执行查询时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	
	//根据用户ID、组ID、派发组ID、资源ID查询，看当前记录是否存在。如果存在返回List.
	public static List getSendScopeValue(String userid,String groupid,String sendscopegroupid,String sourceid)
	{

		try 
		{
			String sql = "from ManageGroupUserpo t where t.c610000020='"+userid+"' and t.c610000021='"+groupid
							+"' and t.c610000023='"+sendscopegroupid+"' and t.c610000025='"+sourceid+"'";
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(sql);
//			List list = query.list();
			List list =HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size() >0)
				{
					
					return list;
				}else
				{
					
					return null;
				}
			}else
			{
				
				return null;
			}
		} 
		catch (Exception e) 
		{
			logger.info("275 GetSendScope 类中 调用 getSendscopevalue(String userid,String groupid,String sendscopegroupid,String sourceid)执行查询时出现异常！"+e.getMessage());
			return null;
		}

		
	}
	
	public boolean isExists(String userid,String groupid)
	{
		try 
		{
			String sql 		= "from ManageGroupUserpo t where t.c610000020='"+userid+"' and t.c610000021="+groupid+"'";
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list		= query.list();
			List list =HibernateDAO.queryObject(sql);
			if(list!=null)
			{
				if(list.size() >0)
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return true;
				}else
				{
//					tx.commit();
//					HibernateSessionFactory.closeSession();
					return false;
				}
			}else
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.info("275 GetSendScope 类中 调用 isExists(String userid,String groupid执行查询时出现异常！"+e.getMessage());
			return false;
		}
	}
	
	public static void main(String args[])
	{
		boolean bl = isExists("000000000000060","000000000600296","000000000600004","17","0");
		System.out.println(bl);
		
	}
	}
