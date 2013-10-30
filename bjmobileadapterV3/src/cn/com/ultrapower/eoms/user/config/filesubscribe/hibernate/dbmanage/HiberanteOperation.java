package cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.dbmanage;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.filesubscribe.hibernate.po.FileSubscribe;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
public class HiberanteOperation
{
	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @param filesubscribe
	 * @return boolean 新建记录
	 */
	public boolean createRecord(FileSubscribe filesubscribe)
	{
		Transaction tran = null;
		Session session = null;
		List list = null;
		Query query = null;
		boolean issucc = false;
		try
		{
//			session = HibernateSessionFactory.currentSession();
//			tran = session.beginTransaction();
//			session.save(filesubscribe);
//			tran.commit();
			HibernateDAO.insert(filesubscribe);
			issucc = true;
		} catch (Exception ex)
		{
//			if (tran != null)
//				try
//				{
//					tran.rollback();
//				} catch (HibernateException e)
//				{
//					// logger.info("[103]getActionTypeResult:" +
//					// ex.getMessage());
//				}
			issucc = false;
		} finally
		{
//			try
//			{
////				HibernateSessionFactory.closeSession();
//			} catch (HibernateException e)
//			{
//				e.printStackTrace();
//			}
		}
		return issucc;
	}

	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @param filesubscribe
	 * @return boolean 删除记录
	 */
	public boolean delRecord(FileSubscribe filesubscribe)
	{
		Transaction tran = null;
		Session session = null;
		List list = null;
		Query query = null;
		boolean issucc = false;
		try
		{
			//-----shigang---用hibernate公用方法
			HibernateDAO.deleteObject(filesubscribe);
//			session = HibernateSessionFactory.currentSession();
//			tran = session.beginTransaction();
//			// session.save(filesubscribe);
//			session.delete(filesubscribe);
//			tran.commit();
			issucc = true;
		} catch (Exception ex)
		{
//			if (tran != null)
//				try
//				{
//					tran.rollback();
//				} catch (HibernateException e)
//				{
//					// logger.info("[103]getActionTypeResult:" +
//					// ex.getMessage());
//				}
			issucc = false;
		} finally
		{
//			try
//			{
//				HibernateSessionFactory.closeSession();
//			} catch (HibernateException e)
//			{
//				e.printStackTrace();
//			}
		}
		return issucc;
	}

	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @param filesubscribe
	 * @return boolean 修改记录
	 */
	public boolean updateRecord(FileSubscribe filesubscribe)
	{
		Transaction tran = null;
		Session session = null;
		List list = null;
		Query query = null;
		boolean issucc = false;
		try
		{
//			session = HibernateSessionFactory.currentSession();
//			tran = session.beginTransaction();
//			// session.save(filesubscribe);
//			session.update(filesubscribe);
//			tran.commit();
//			--------------shigang modify--------------
			HibernateDAO.modify(filesubscribe);
			issucc = true;
		} catch (Exception ex)
		{
			if (tran != null)
				try
				{
					tran.rollback();
				} catch (HibernateException e)
				{
					// logger.info("[103]getActionTypeResult:" +
					// ex.getMessage());
				}
			issucc = false;
		} finally
		{
//			try
//			{
//				HibernateSessionFactory.closeSession();
//			} catch (HibernateException e)
//			{
//				e.printStackTrace();
//			}
		}
		return issucc;
	}

	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @return List 取得所有记录
	 */
	public List getAllRecord()
	{
		Transaction tran = null;
		Session session = null;
		List list = null;
		Query query = null;
		String sql = "from FileSubscribe a, Sourceconfig b where a.filesmsSourceid=b.sourceId";
		try
		{
//			session = HibernateSessionFactory.currentSession();
//			tran = session.beginTransaction();
//			// session.save(filesubscribe);
//			query = session.createQuery(sql);
//			//list = session.find(sql);
//			list= query.list();
//			tran.commit();
//			--------------shigang modify--------------
			list= HibernateDAO.queryObject(sql);
		} catch (Exception ex)
		{
//			if (tran != null)
//				try
//				{
//					tran.rollback();
//				} catch (HibernateException e)
//				{
//					// logger.info("[103]getActionTypeResult:" +
//					// ex.getMessage());
//				}
		} finally
		{
//			try
//			{
//				HibernateSessionFactory.closeSession();
//			} catch (HibernateException e)
//			{
//				e.printStackTrace();
//			}
		}
		return list;
	}

	/**
	 * 日期 2007-1-18
	 * 
	 * @author xuquanxing
	 * @param id
	 * @return List 取得某条记录信息
	 */
	public List getOnlyRecord(Long id)
	{
		Transaction tran = null;
		Session session = null;
		List list = null;
		Query query = null;
		String sql = "from FileSubscribe a,Sourceconfig b  where a.filesmsSourceid=b.sourceId and a.filesmsId=" + id;
		try
		{
//			session = HibernateSessionFactory.currentSession();
//			tran = session.beginTransaction();
//			// session.save(filesubscribe);
//			list = session.find(sql);
//			tran.commit();
//			-----------shigang modify--------------
			list= HibernateDAO.queryObject(sql);
		} catch (Exception ex)
		{
//			if (tran != null)
//				try
//				{
//					tran.rollback();
//				} catch (HibernateException e)
//				{
//					// logger.info("[103]getActionTypeResult:" +
//					// ex.getMessage());
//				}
//		} finally
//		{
////			try
////			{
////				HibernateSessionFactory.closeSession();
////			} catch (HibernateException e)
////			{
////				e.printStackTrace();
////			}
		}
		return list;
	}

}
