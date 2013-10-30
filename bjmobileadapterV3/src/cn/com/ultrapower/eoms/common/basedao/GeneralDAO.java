/*
 * Created on 2006-10-11
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package cn.com.ultrapower.eoms.common.basedao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import cn.com.ultrapower.eoms.page.div.Page;
import cn.com.ultrapower.eoms.util.Log;
import cn.com.ultrapower.eoms.util.PageInfo;
import cn.com.ultrapower.eoms.util.StringUtil;
/**
 * @author ultrapower
 *
 * To change the t
emplate for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GeneralDAO
{
	 public static org.hibernate.SessionFactory sessionFactory;
	 public static ThreadLocal threadSession = new ThreadLocal();
    

	 /**
	  * 
	 * 使用Spring注入sessionFactory
	 */
	public void setHibernateSessionFactory(SessionFactory _sessionFactory) {
		System.out.println("应用组Hibernate SessionFactory 加载成功");
		sessionFactory = _sessionFactory;
	}
	
	public static Session currentSession() throws HibernateException {
		Session s = (Session) threadSession.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			threadSession.set(s);
		}
		return s;
	}
	
	public static void closeSession() {
		try {
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null){
				s.close();
			}
		} catch (HibernateException e) {
			Log.logger.error(e);
		}
	}
	
	/**
	 * 保存数组中的对象
	 * 
	 * @param objs POJO对象
	 * @throws GeneralException
	 */
	public static void addObjects(Object[] objs) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();
			
			for (int i = 0; i < objs.length; i++)
			{
				session.save(objs[i]);	
			}
			
			tx.commit();

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("add object error, object:" + objs.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}

	/**
	 * 保存Vector中的对象
	 * @param objs
	 * @throws GeneralException
	 */
	public static void addObjects(Vector objs) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();
			
			for (int i = 0; i < objs.size(); i++)
			{
				Object obj = objs.get(i);
			
				session.save(obj);	
			}
			
			tx.commit();
			session.flush();
		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("add object error, object:" + objs.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	
	public static void addObject(Object obj) throws GeneralException
	{
		Object[] objs = new Object[1];
		objs[0] = obj;
		addObjects(objs);
	}

	/**
	 * 新增更新方法(传入一个vector进行整体更新)
	 * @param objs
	 * @throws GeneralException
	 */

	public static void updateObjects(Vector objs) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();
			
			for (int i = 0; i < objs.size(); i++)
			{
				Object obj = objs.get(i);
			
				session.update(obj);	
			}
			
			tx.commit();

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}

			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	
	
	
	
	
	public static void updateObjects(Object[] objs) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();
		
			for (int i = 0; i < objs.length; i++)
			{
				session.update(objs[i]);	
			}
		
			tx.commit();

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("update object error, object:" + objs.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	/**
	 * @description 把传入的Vector中的每个元素进行更新
	 * @param objs
	 * @throws GeneralException
	 * @exceptio
	 */
	public static void updateAndInsertObjects(Vector insertObjs,Vector updateObjs) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

			for(Iterator ite = updateObjs.iterator();ite.hasNext();){
				session.update(ite.next());
			}
			
			for(Iterator ite = insertObjs.iterator();ite.hasNext();){
				session.save(ite.next());
			}
		

			tx.commit();

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("update object error, object:" + insertObjs.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}

	public static void updateObject(Object obj) throws GeneralException
	{
		Object[] objs = new Object[1];
		objs[0] = obj;
		updateObjects(objs);
	}
	
	/**
	 * yyg
	 * @param obj
	 * @throws GeneralException
	 */
	public static void deleteObject(Object obj) throws GeneralException
	{
		if (obj == null) return;
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

			session.delete(obj);	
			
			tx.commit();

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("delete object error, object:" + obj.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
		
	}
	
	public static Object loadObject(Class objectClass, String objectId) throws GeneralException
	{
		Transaction tx = null;
		
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

			Object o = session.load(objectClass,new Long(objectId));

			tx.commit();
	
			return o;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("load object error, class:" + objectClass.getName() + " , id:" + objectId, e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	/**
	 * @description  类的类型和类的long id  来获得对象
	 * @param objectClass
	 * @param objectId
	 * @return
	 * @throws GeneralException
	 * @author LiangYang
	 */
	public static Object loadObject(Class objectClass, long objectId) throws GeneralException
	{
		Transaction tx = null;
		
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

			Object o = session.load(objectClass,new Long(objectId));

			tx.commit();
	
			return o;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
//			logger.error("load object error, class:" + objectClass.getName() + " , id:" + objectId, e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	
	
	/**
	 * 
	 * @param query
	 * @param values
	 * @param types
	 * @return
	 * @throws GeneralException
	 */
	public static Vector loadObjects(String query, Object[] values, Type[] types) throws GeneralException
	{
		Transaction tx = null;
	
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();
			Query newquery = session.createQuery(query);
			List list = newquery.list();
			Vector result = new Vector(list);

			tx.commit();
		
			return result;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("query object error, query:" + query, e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	/**yyg:??loadObjects????
	 * @describe:????Session?е?find()
	 * @deprecated
	 * ??????
	 * @param   :???query
	 * @return  :????Vector 
	 * @throws  :GeneralException
	 */
/*	
	public static Vector loadObjects(String query) throws GeneralException
	{
		Transaction tx = null;
		
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

			Vector result = loadObjects(session, query);
            
			tx.commit();
			
			return result;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("query object error, query:" + query, e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
			}
	}
*/	
	/**??loadObjects????
	 * @describe:????Session?е?find()???????????
	 * @param   :???query
	 * @return  :????Vector 
	 * @throws  :GeneralException
	 */
	public static Vector loadObjects(String query) throws GeneralException
	{
		Transaction tx = null;
		
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

//			List list = session.find(query);
			Query newquery = session.createQuery(query);
			List list = newquery.list();
			Vector result = new Vector(list);

			tx.commit();
		
			return result;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("query object error, query:" + query, e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	
	/**??loadObjects????
	 * @describe:????Session?е?find()???????????
	 * @param   :???query
	 * @return  :????Vector 
	 * @throws  :GeneralException
	 */
	public static Vector loadObjects1(String query) throws GeneralException
	{
		Transaction tx = null;
		
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();

//			List list = session.find(query);
			Query newquery = session.createQuery(query);
			List list = newquery.list();
			Vector result = new Vector(list);

			tx.commit();
		
			return result;

		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("query object error, query:" + query, e);
			throw new GeneralException(e);
		}finally{
			closeSession();
		}
	}
	/**
	 * @describe 获得查询条件记录的条数
	 * @param query
	 * @return
	 * @throws GeneralException
	 * @author LY 
	 * @date 2006-12-14
	 */
	public static String getRecordSize(String query) throws GeneralException {
		try{
			Session session = currentSession();
			if(session != null){
				
				if(query != null && !query.equals("")){
					Transaction tx = (Transaction)session.beginTransaction();
					Query que  = session.createQuery(query);
					String recordSize = que.uniqueResult().toString();
					tx.commit();
					return recordSize;
				}
			}
		}catch(Exception e){
			Log.logger.error("GernalDAO中获得记录的条数出现异常");
			throw new GeneralException(e);
		}finally{
			closeSession();
		}
		return null;
	}
	/**
	 * 以Page页对象返回查询结果
	 * @param query
	 * @param currentPage
	 * @return
	 * @throws GeneralException
	 */
	public static Page loadObjects(String query, int currentPage)
			throws GeneralException {
		
		Transaction tx = null;
		Page p = new Page();
		try {
			Session session = currentSession();
			int index = StringUtil.isHasSubString(query, "from");
			int pageSize = PageInfo.PAGE_SIZE;
			if (index != -1) {
				String queMaxSize = "select count(*) " + query.substring(index);
				if (StringUtil.isHasSubString(query, "distinct") != -1) {
					queMaxSize = "select distinct count(*) "
							+ query.substring(index);
				}
				tx = (Transaction) session.beginTransaction();

				Query que = session.createQuery(queMaxSize);
				int maxSize = Integer.parseInt(que.uniqueResult().toString());
				p.setRowCount(maxSize) ;
				int maxPage = maxSize / pageSize;
				if (maxSize % pageSize != 0) {
					maxPage++;
				}
				p.setMaxPage(maxPage);
				currentPage = Math.min(currentPage, maxPage);
				if (maxSize == 0) {
					p.setCurrentPage(0);
				} else {
					p.setCurrentPage(currentPage);
				}

				que = session.createQuery(query);

				if (currentPage>=0) {
					
					currentPage=currentPage==0?1:currentPage;
					que.setFirstResult((currentPage - 1) * pageSize);
					que.setMaxResults(pageSize);
				
				}
				
				List l = que.list();
				p.setList(l);
				tx.commit();
			}
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
	
	public static Page loadObjects(String query,String qur,int currentPage) throws GeneralException
	{
		Transaction tx = null;
		Page p = new Page();
		try
		{
			Session session = currentSession();
			

			int index = StringUtil.isHasSubString(query,"from");
			int pageSize = PageInfo.PAGE_SIZE;
			if(index != -1){
				String queMaxSize = "select count(*) " + query.substring(index);
				if(StringUtil.isHasSubString(query,"distinct") != -1){
					queMaxSize = "select distinct count(distinct "+qur+") " + query.substring(index);
				}
				tx = (Transaction)session.beginTransaction();
				Query que  = session.createQuery(query);
				
				
				if(currentPage>=0) //为负数的时候取所有的结果集
				 {
					currentPage=currentPage==0?1:currentPage;
					
					que.setFirstResult((currentPage-1)*pageSize);
				    que.setMaxResults(pageSize);
				
				 }
				//que.setFirstResult((currentPage - 1) * pageSize);
				//que.setMaxResults(pageSize);
				List l = que.list();
				p.setList(l);
				que = session.createQuery(queMaxSize);
				int maxSize = Integer.parseInt(que.uniqueResult().toString());
				int maxPage = maxSize / pageSize;
				if(maxSize % pageSize != 0){
					maxPage ++ ;
				}
				p.setMaxPage(maxPage);
				if(maxSize == 0){
					p.setCurrentPage(0);
				}else{
					p.setCurrentPage(Math.min(currentPage, maxPage));
				}
				
				tx.commit();
		}
			}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
			Log.logger.error("query object error, query:" + query, e);
			p = null;
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	return p;
	}

	
	/**
	 * yyg返回查询的多条记录
	 * @deprecated

	 * @param session
	 * @param query
	 * @return
	 * @throws GeneralException
	 */
	/*
	private static Vector loadObjects(Session session, String query) throws GeneralException
	{
		Vector retVec = new Vector();
		try
		{
			List objs = session.find(query);
			retVec = new Vector(objs);
			
		}
		catch (HibernateException e)
		{
			throw new GeneralException(e);
		}
		
		return retVec;
	}
	*/
/**
 * @deprecated
 * @param session
 * @param objs
 * @throws GeneralException
 */
	public static void addObjects(Session session, Vector objs)  throws GeneralException
	{
		if (objs == null) return;
		
		try
		{
			int iSize = objs.size();
			for (int i = 0; i < iSize; i++)
			{   
				
				session.save(objs.elementAt(i));
				
			}
			
		}
		catch (HibernateException e)
		{
			throw new GeneralException(e);
		}
	}
	/**
	 * @deprecated
	 * @description 通过调用public static void addObjects(Session session, Vector objs)
	 * 方法 ,来实现只是传一个对象和session过来 ,进行数据库的添加 
	 * @param session
	 * @param obj
	 * @throws GeneralException
	 * @author LiangYang
	 * @establish 2006-10-25
	 */
	public static void addObject(Session session, Object obj)  throws GeneralException{
		if (obj == null) return;
		
		try
		{
				session.save(obj);			
		}
		catch (HibernateException e)
		{
			throw new GeneralException(e);
		}
	}
	
	/**
	 * @deprecated
	 * yyg add ?????????
	 * @param session
	 * @param objs
	 * @throws GeneralException
	 */
	
	public static void updateMulObjects(Session session, Vector objs)  throws GeneralException
	{
		if (objs == null) return;
		
		try
		{
			int iSize = objs.size();
			for (int i = 0; i < iSize; i++)
			{
				session.update(objs.elementAt(i));
			}
			
		}
		catch (HibernateException e)
		{
			throw new GeneralException(e);
		}
	}
	
	/**
	 * @deprecated
	 * @param session
	 * @param objs
	 * @throws GeneralException
	 */
	
	public static void deleteMulObjects(Session session, Vector objs)  throws GeneralException
	{
		if (objs == null) return;
		
		try
		{
			int iSize = objs.size();
			for (int i = 0; i < iSize; i++)
			{
				session.delete(objs.elementAt(i));
			}
			
		}
		catch (HibernateException e)
		{
			throw new GeneralException(e);
		}
	}
	
	public static  void log(String str)
	{
		//System.out.println(str);
                Log.logger.debug(str);
	}
	
	/**
	 * 返回当前存储的对象
	 * @param objs
	 * @throws GeneralException
	 */
	public static Serializable addObjectReturn(Object obj) throws GeneralException
	{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();					
			Serializable ob = session.save(obj);						
			tx.commit();
			
			System.out.println("**********************");
			System.out.println(ob);
			System.out.println("**********************");
			
			return ob;
		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
					return null;
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
					return null;
				}
			Log.logger.error("add object error, object:" + obj.toString(), e);
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}
	}
	/**
	 * 批量执行更新的sql语句
	 * @param sql　SQL语句
	 * @throws GeneralException
	 */
	public static void batchUpdate(String sql) throws GeneralException{
		Transaction tx = null;
		try
		{
			Session session = currentSession();
			tx = (Transaction)session.beginTransaction();					
			Connection conn = session.connection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			tx.commit();
		}
		catch (Exception e)
		{
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
					Log.logger.error("Transaction rollback error!");
					e1.printStackTrace();
				}
			Log.logger.error("batch update error!");
			throw new GeneralException(e);
		}
		finally
		{
			closeSession();
		}		
	}
}
