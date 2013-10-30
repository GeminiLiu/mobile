package cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SysSlaMailpo;

/**
 * ���� 2006-10-17
 * @author xuquanxing/徐全星
 * 获得邮件动作的信息
 *
 */
public class GetSlaMailInfoList {
	static final Logger logger = (Logger) Logger.getLogger(GetSlaMailInfoList.class);
	
	
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @param slaid
	 * @param actionid
	 * @return
	 * @throws HibernateException List
	 */
	public List getResult(String objectname ,String actionid) throws HibernateException{
		Transaction tran = null;
		Session session  = null;
		List        list = null;
		Query query      = null;
		System.out.print(objectname);
		System.out.print(actionid);
		//System.out.print("FROM " + objectname+ " where c1=?");
    	try{ 
//    		session = HibernateSessionFactory.currentSession();
//    		tran    = session.beginTransaction();
//    		query   = session.createQuery("FROM "+ objectname+ " where c1="+actionid);
//    	    //query.setString(0,actionid);
//       		list    = query.list();
//       		tran.commit();
//       		System.out.print(list.size());
//       		//			-----------shigang modify--------------
    		String sql="FROM "+ objectname+ " where c1="+actionid;
    		 list= HibernateDAO.queryObject(sql);
       		
    	}catch(Exception ex){
    		
		}
    	return list;
	}
	public static void main(String[] args){
		GetSlaMailInfoList op=new GetSlaMailInfoList();
		
		try{
			List list=op.getResult("SysSlaMailpo","000000000000001");
			//System.out.print("sdfsd");
			//System.out.print(list.size());
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				SysSlaMailpo ti=(SysSlaMailpo)it.next();
				System.out.println(ti.getC600000021());
			}
		}catch(Exception e){}
	}
}
