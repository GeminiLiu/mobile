package cn.com.ultrapower.eoms.common.basedao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.page.div.Page;
import cn.com.ultrapower.eoms.util.Log;

public class GeneralDAOChild extends GeneralDAO{
    
	public static Session currentSession() throws HibernateException {
		Session s = (Session) threadSession.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			threadSession.set(s);
		}
		return s;
	}
	
	public static void closeSession()  
	{
		try
		{
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null)
				s.close();
		}
		catch (HibernateException e)
		{
	                Log.logger.error(e);
		}
	}
//	****************************************
	/**
	 * 以Page页对象返回查询结果
	 * @param query
	 * @return
	 * @throws GeneralException
	 */
    public static Page loadObjects2(String query)throws GeneralException {

        Transaction tx = null;
        Page p = new Page();
        try {
        	Session session = currentSession();
        	tx = (Transaction)session.beginTransaction();

        	Query newquery = session.createQuery(query);
			List list = newquery.list();
        	p.setList(list);

        	tx.commit();
	
        } catch (Exception e) {
        	if (tx != null)
        		try {
			tx.rollback();
        		} catch (Exception e1) {
        		}
        		Log.logger.error("query object error, query:" + query, e);
        		p = null;
        		throw new GeneralException(e);
        } finally {
        	closeSession();
        }
return p;
}
}
