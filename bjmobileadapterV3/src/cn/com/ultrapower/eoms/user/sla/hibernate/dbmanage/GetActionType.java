package cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SysActionpo;

public class GetActionType 
{
	static final Logger logger = (Logger) Logger.getLogger(GetActionType.class);
	
	/**
	 * 日期 2006-10-20
	 * @author xuquanxing 
	 * @param actionid
	 * @return
	 * @throws HibernateException List
	 * 该方法用于根据动作类型的id，返回动作对应的对象名，判断应该对那个表进行操作
	 */
	public List getActionTypeResult(String actionid) throws HibernateException
	{
		Transaction tran = null;
		Session session  = null;
		List        list = null;
		Query query      = null;
		System.out.println("type:"+actionid);
    	try{ 
//    		session = HibernateSessionFactory.currentSession();
//    		tran    = session.beginTransaction();
//    		System.out.print("type1:"+actionid);
//    		query   = session.createQuery("from SysActionpo s where s.c1="+actionid);
//			-----------shigang modify--------------
    		String sql="from SysActionpo s where s.c1="+actionid;
    		 list= HibernateDAO.queryObject(sql);

    	    //query.setString(0,actionid);
//       		list    = query.list();
//       		tran.commit();
       		return list;
           	}catch(Exception ex)
    	     {
//    		     if(tran!=null)
//				     try{
//					     tran.rollback();
//				        }catch(HibernateException e)
//				         {
//					        logger.info("[103]getActionTypeResult:"+ex.getMessage());
//				         }
		     return null;
		}
	}
	
	public List getActionAllTypeResult() throws HibernateException
	{
		Transaction tran = null;
		Session session  = null;
		List        list = null;
		Query query      = null;
    	try{ 
//    		session = HibernateSessionFactory.currentSession();
//    		tran    = session.beginTransaction();
//    		query   = session.createQuery("from SysActionpo ");
//    	    //query.setString(0,actionid);
       		
    		String sql="from SysActionpo ";
   		 	list= HibernateDAO.queryObject(sql);

       		//tran.commit();
       		return list;
           	}catch(Exception ex)
    	     {
           		return null;
    	     }
	}
	
	public static void main(String[] args){
		GetActionType op =new GetActionType();
		try{
		List list=	op.getActionAllTypeResult();
		for(Iterator it=list.iterator(); it.hasNext();){
			SysActionpo t=(SysActionpo)it.next();
			System.out.print("safasdf"+t.getC600000010());
		}
		}catch(Exception e){
			
		}
	}
}
