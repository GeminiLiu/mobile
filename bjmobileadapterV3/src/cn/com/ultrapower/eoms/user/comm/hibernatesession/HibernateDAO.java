package cn.com.ultrapower.eoms.user.comm.hibernatesession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.hql.classic.QueryTranslatorImpl;

import org.apache.log4j.Logger;


import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.comm.function.*;

public class HibernateDAO {

	/**
	 * date 2006-10-25
	 * author shigang
	 * @param args
	 * @return void
	 */
	static final Logger logger = (Logger) Logger.getLogger(HibernateDAO.class);
	static int allpage=1;
	//插入
	public static void insert(Object[] objs) throws HibernateException{
		try{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			Transaction tx = session.beginTransaction();
			for (int i = 0; i < objs.length; i++)
			{
				session.save(objs[i]);	
			}
			tx.commit();
			logger.info("361 HibernateDAO insert succeed:");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("361 HibernateDAO insert error:"+e.toString());
			
		}finally{
			 HibernateSessionFactory.closeSession();
		}		
	}

	public static boolean insert(Object obj) throws HibernateException
	{
		try{
			Object[] objs = new Object[1];
			objs[0] = obj;
			insert(objs);
			System.out.println("添加成功");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//修改
	public static void modify(Object[] objs) throws HibernateException{
		try{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			Transaction tx = session.beginTransaction();
			for (int i = 0; i < objs.length; i++)
			{
				session.update(objs[i]);	
			}
			tx.commit();	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("362 HibernateDAO modify error:"+e.toString());
		}finally{
			 HibernateSessionFactory.closeSession();
		}		
	}

	public static boolean modify(Object obj) throws HibernateException
	{
		try{
			Object[] objs = new Object[1];
			objs[0] = obj;
			modify(objs);
			System.out.println("修改成功");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//删除
	public static boolean deleteObject(Object obj) throws HibernateException
	{
		if (obj == null) return false;
		Transaction tx = null;
		try
		{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			tx = (Transaction)session.beginTransaction();
			session.delete(obj);	
			tx.commit();
			return true;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("363 HibernateDAO delete error:"+e.toString());
			return false;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
		
	}
	public static Object load(Class obj,Long id) throws HibernateException
	{
		if (obj == null) return null;
		
		Object obj1 = null;
		Transaction tx = null;
		try
		{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			tx = (Transaction)session.beginTransaction();
			obj1 = (Object)session.get(obj,id);	
			tx.commit();
			return obj1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("364 HibernateDAO delete error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	public static Object loadStringValue(Class obj,String id) throws HibernateException
	{
		if (obj == null) return null;
		
		Object obj1 = null;
		Transaction tx = null;
		try
		{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			tx = (Transaction)session.beginTransaction();
			obj1 = (Object)session.get(obj,id);	
			tx.commit();
			return obj1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("364 HibernateDAO delete error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	public static Object loadvalue(Class obj,String id) throws HibernateException
	{
		if (obj == null) return null;
		
		Object obj1 = null;
		Transaction tx = null;
		try
		{
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			tx = (Transaction)session.beginTransaction();
			obj1 = (Object)session.load(obj,new Long(id));	
			tx.commit();
			return obj1;
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("364 HibernateDAO delete error:"+e.toString());
			if (tx != null)
				try
				{
					tx.rollback();
				}
				catch (Exception e1)
				{
				}
				return null;
			
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	
	public static List queryObject(String sql) throws HibernateException{
		try
		{	
			 Session session=null;
			 session = HibernateSessionFactory.currentSession();
			 Transaction	tx 	 	  = session.beginTransaction();
			 Query 			query 	  = session.createQuery(sql);
			 List 			queryList = query.list();
			 tx.commit();
			 return queryList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("365 HibernateDAO queryObject error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	
	public static List queryClearObject(String sql) throws HibernateException{
		try
		{	
			 Session session = null;
			 session = HibernateSessionFactory.currentSession();
			 session.clear();
			 Transaction	tx 	 	  = session.beginTransaction();
			 Query 			query 	  = session.createQuery(sql);
			 List 			queryList = query.list();
			 tx.commit();
			 return queryList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[] HibernateDAO queryObject error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	
	//sql为查询，fistrow从多少号开始
	public static List queryObjectRow(String sql,int fistrow) throws HibernateException{
		try
		{	
			 Session session=null;
			 session = HibernateSessionFactory.currentSession();
			 Transaction	tx 	 	  = session.beginTransaction();
			 Query 			query 	  = session.createQuery(sql);
//				当为第一行时1*row 第二行时2*row...
			 query.setFetchSize(fistrow);
			 query.setMaxResults(20);
			 List 			queryList = query.list(); 
			 tx.commit();
			 return queryList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("365 HibernateDAO queryObject error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	//当出现非主键也有不为空的项的时候用此方法 传过来的是 列如 "from Sourceconfig where source_id=1"

	public static boolean deleteMulObjects1(String sql) throws HibernateException
	{
		if (sql == null) return false;
		Session session=null;
		session = HibernateSessionFactory.currentSession();
		try{
				String tmpsql	= String.valueOf(sql.toLowerCase()).trim();
				if(tmpsql.startsWith("from"))
				{
					tmpsql	= "delete "+sql;
				}
				else
				{
					tmpsql	= sql;
				}
				System.out.println("sql"+tmpsql);
				Transaction	tx= session.beginTransaction();
				//session.delete(tmpsql);
				Query query = session.createQuery(tmpsql);
				query.executeUpdate();
				tx.commit();
				return true;
		}catch (HibernateException e){
			System.out.print(e);
			return false;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
		
	}
	public static List queryObjectPageold(String sql,int pagenum,int pagesize) throws HibernateException
	{
		Session session=null;
		try
		{	
			 session = HibernateSessionFactory.currentSession();
			 Query 			query 	  = session.createQuery(sql);
			 List 			queryList = query.list(); 
			 
			 List newlist   =new ArrayList();
			 
			int absoluteLocation;
			absoluteLocation = pagesize * (pagenum - 1);
			logger.info("absoluteLocation"+absoluteLocation);
			logger.info("queryList.size()"+queryList.size());
			if(queryList!=null)
			{
				if(queryList.size()>0)
				{
					allpage=queryList.size();
					
					if(pagesize!=0)
					{
						allpage=(allpage-1)/pagesize+1;
					}
					logger.info("queryList.size()1"+queryList.size());
					if(pagenum>allpage)
					{
						absoluteLocation=0;
					}
					for (int i = absoluteLocation, j = 0; i < queryList.size()&& i < absoluteLocation + pagesize; i++, j++) 
					{
						newlist.add(j,(Object)queryList.get(i));
					}
					
				}
				else
				{
					allpage=1;
				}
			}
			else
			{
				allpage=1;
			}
			 return newlist;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("990 HibernateDAO queryObject error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}

	public static List queryObjectPage(String sql,int pagenum,int pageSize) throws HibernateException
	{
		Session session=null;
		try
		{	
			 session 			= HibernateSessionFactory.currentSession();
			 List 	queryList	= null;
			 List	maxquerylist= null;
			 //重新组合sql语句


			int index = sql.toLowerCase().indexOf("from");
			if(index != -1)
			{
				//String queMaxSize = "select count(*) " + sql.substring(index);
				String queMaxSize 	= sql;//"select count(*) " + sql.substring(index);
				int maxSize			= 0;
				Query 			query 	  = session.createQuery(sql);
				if(String.valueOf(pagenum).equals("null")||pagenum<1)
				{
					pagenum	= 1;
				}
				if(String.valueOf(pageSize).equals("null")||pageSize<1)
				{
					pageSize	= 1;
				}
				query.setFirstResult((pagenum-1)*pageSize);
				query.setMaxResults(pageSize);
				queryList = query.list();
				
				System.out.println(query.getQueryString()+"wuwenlong1");
				
				if(sql.toLowerCase().indexOf("distinct") != -1)
				{
					queMaxSize 		= sql;
					org.hibernate.SessionFactory sessionFactory=HibernateSessionFactory.sessionFactory;
					QueryTranslatorImpl translator = new QueryTranslatorImpl(queMaxSize,Collections.EMPTY_MAP,(org.hibernate.engine.SessionFactoryImplementor)sessionFactory);
					translator.compile(Collections.EMPTY_MAP, false);
					queMaxSize	= "select count(*) from (" + translator.getSQLString() + ")";
					int tmpmaxSize = Function.getpagesize(queMaxSize);
					if(!String.valueOf(tmpmaxSize).equals("")&&!String.valueOf(tmpmaxSize).equals("null"))
					{
						maxSize=tmpmaxSize;
					}
					else
					{
						maxSize=0;
					}
				}
				else
				{
					queMaxSize		= "select count(*) " + sql.substring(index);
					query 			= session.createQuery(queMaxSize);
					maxSize 		= Integer.parseInt(query.uniqueResult().toString());
				}
				
				int maxPage = maxSize / pageSize;
				if(maxSize % pageSize != 0){
					maxPage ++ ;
				}
				if(String.valueOf(maxPage).equals("0"))
				{
					maxPage	= 1;
				}
				allpage		=	maxPage;
				return queryList;
			}
			else
			{
				return queryList;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("990 HibernateDAO queryObject error:"+e.toString());
			return null;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
	}
	public static int getallpage()
	{
		return allpage;
	}
	
	
	//有条件的删除插入
	public static boolean delInsRollObject(Object obj,String delSql) throws HibernateException
	{
		if (delSql == null) return false;
		Transaction tx = null;
		try
		{
			logger.error("363 HibernateDAO delInsRollObject error-----------1:");
			Session session=null;
			session = HibernateSessionFactory.currentSession();
			tx = (Transaction)session.beginTransaction();
			//session.delete(delSql);
			String tmpsql	= String.valueOf(delSql.toLowerCase()).trim();
			if(tmpsql.startsWith("from"))
			{
				tmpsql	= "delete "+delSql;
			}
			else
			{
				tmpsql	= delSql;
			}
			
			//session.delete(tmpsql);
			Query query = session.createQuery(tmpsql);
			query.executeUpdate();
			
			Object[] objs = new Object[1];
			objs[0] = obj;

			for (int i = 0; i < objs.length; i++)
			{
				session.save(objs[i]);
			}
			
			tx.commit();
			logger.error("363 HibernateDAO delInsRollObject error-------------2:");
			return true;

		}
		catch (Exception e)
		{	
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			logger.error("363 HibernateDAO delInsRollObject error:"+e.toString());
			return false;
		}
		finally
		{
			 HibernateSessionFactory.closeSession();
		}
		
	}
	
	public static void main(String[] args)
	{
		Session session=null;
		String 	sql="";
		try
		{
			 session 			= HibernateSessionFactory.currentSession();
//			 用于hibernate 2.x HibernateSessionFactory
			//org.hibernate.SessionFactory sessionFactory=HibernateSessionFactory.sessionFactory;
//			QueryTranslator translator = new QueryTranslator("select syskillpo.c610000011,syskillpo.c610000007,syskillpo.c610000008,syskillpo.c610000009,sourceconfig.sourceCnname,sysPeoplepo.c630000003,sysPeoplepo.c1 from SysSkillpo syskillpo,Sourceconfig sourceconfig,SysPeoplepo sysPeoplepo where syskillpo.c610000008=sourceconfig.sourceId and syskillpo.c610000007=sysPeoplepo.c1");
//			translator.compile((org.hibernate.engine.SessionFactoryImplementor)HibernateSessionFactory.sessionFactory,Collections.EMPTY_MAP, false);
//			sql="select count(*) from (" + translator.getSQLString() + ")";
			//return "select count(*) from (" + translator.getSQLString() + ") tmp_count_t"; 
			
			 //String test= "select count(count(syskillpo.c610000011)),count(count(syskillpo.c610000007)),count(count(syskillpo.c610000008)),count(count(syskillpo.c610000009)),count(count(sourceconfig.sourceCnname)),count(count(sysPeoplepo.c630000003)),count(count(sysPeoplepo.c1)) from SysSkillpo syskillpo,Sourceconfig sourceconfig,SysPeoplepo sysPeoplepo";
			 //test	= test+" where 1=1 group by syskillpo.c610000011,syskillpo.c610000007,syskillpo.c610000008,syskillpo.c610000009,sourceconfig.sourceCnname,sysPeoplepo.c630000003,sysPeoplepo.c1";
			 
			 //String test= "select count(*) from (select syskillpo.c610000011,syskillpo.c610000007,syskillpo.c610000008,syskillpo.c610000009,sourceconfig.sourceCnname,sysPeoplepo.c630000003,sysPeoplepo.c1 from SysSkillpo syskillpo,Sourceconfig sourceconfig,SysPeoplepo sysPeoplepo)";
			 //test	= test+" where 1=1 group by syskillpo.c610000011,syskillpo.c610000007,syskillpo.c610000008,syskillpo.c610000009,sourceconfig.sourceCnname,sysPeoplepo.c630000003,sysPeoplepo.c1";

			 //System.out.println(queryList.size());
			System.out.println(sql);
			int tttttt=Function.getpagesize(sql);
			
			System.out.println(tttttt+"==============================");
		}
		catch(HibernateException e)
		{	
			//
			System.out.println("error");
			e.getMessage();
			e.getStackTrace();
		}
		
	}
}
