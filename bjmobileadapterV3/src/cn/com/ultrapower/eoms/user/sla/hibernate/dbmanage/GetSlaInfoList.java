package cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.sla.hibernate.po.SysSlapo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * ���� 2006-10-17
 * @author xuquanxing/徐全星
 * 该类用于封装对数据库的查询操作
 * */
public class GetSlaInfoList {
    static final Logger logger = (Logger) Logger.getLogger(GetSlaInfoList.class);
	    
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @return
	 * @throws HibernateException List
	 * 返回所有的sla的列表
	 */
	public List getResults() throws HibernateException{
		Transaction tr   = null;
		Session session  = null;
		List  resultlist = null;
		Query query      = null;
		try{
//			 session    = HibernateSessionFactory.currentSession();
//			 tr         = session.beginTransaction();
//			 query      = session.createQuery("from SysSlapo syspo order by syspo.c1 desc");
//		     resultlist = query.list();
//			 tr.commit();
//			 
//				-----------shigang modify--------------
			 String sql="from SysSlapo syspo order by syspo.c1 desc";
			  resultlist= HibernateDAO.queryObject(sql);

			 return resultlist;
		}catch(Exception ex){
//			if(tr!=null)
//				try{
//					tr.rollback();
//				}catch(HibernateException e){
//					logger.info("[105]:getResults"+ex.getMessage());
//				}
//				return null;
//		}finally{
//			HibernateSessionFactory.closeSession();
//		}
			return null;}
	}
	
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @param id
	 * @return
	 * @throws HibernateException List
     * 返回该id所对应的记录,并取得其信息
	 */
	public List getOnlyResult(String id) throws HibernateException{
		Transaction tran = null;
		Session session  = null;
		List  resultlist = null;
		Query query      = null;
		System.out.println("ok");
		System.out.println(id);
		try{
//			 session    = HibernateSessionFactory.currentSession();
//			 tran       = session.beginTransaction();
//			 query      = session.createQuery("from SysSlapo where c1='"+id+"'");
//				-----------shigang modify--------------
			 String sql="from SysSlapo where c1='"+id+"'";
			  resultlist= HibernateDAO.queryObject(sql);

			 //query.setString(0,id);
//		     resultlist = query.list();
//			 tran.commit();
//			 session.flush();
			 return resultlist;			 
		}catch(Exception ex){
				return null;
		}
	}
	
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @param id
	 * @return
	 * @throws HibernateException List
     * 返回属于给定sla的所有短信动作信息列表
	 */
	public List getRelativeSMSResult(String id) throws HibernateException{
		Transaction tran = null;
		Session session  = null;
		List  resultlist = null;
		Query query      = null;
		try{
//			 session    = HibernateSessionFactory.currentSession();
//			 tran       = session.beginTransaction();
//			 query      = session.createQuery("from SysSlaSmspo s where s.c600000012=?");
//			 query.setString(0,id);
//		     resultlist = query.list();
//				-----------shigang modify--------------
			 String sql="from SysSlaSmspo s where s.c600000012='"+id+"'";
			  resultlist= HibernateDAO.queryObject(sql);
//		     tran.commit();
		}catch(Exception ex){
			return null;
//			if(tran!=null)
//				try{
//					tran.rollback();
//				}catch(HibernateException e){
//					logger.info("[107]����:getRelativeSMSResult"+ex.getMessage());
//				}
//		}finally{
//			HibernateSessionFactory.closeSession();
		}
		return resultlist;//���ز�ѯ�Ľ�� 
	}
	
	/**
	 * ���� 2006-10-17
	 * @author xuquanxing 
	 * @param id
	 * @return
	 * @throws HibernateException List
     * 返回属于给定sla的所有邮件动作信息列表
	 */
	public List getRelativeMailResult(String id) throws HibernateException{
		Transaction tran = null;
		Session session  = null;
		List  resultlist = null;
		Query query      = null;
		try{
//			 session    = HibernateSessionFactory.currentSession();
//			 tran       = session.beginTransaction();
//			 query      = session.createQuery("from SysSlaMailpo s where s.c600000021=?");
//			 query.setString(0,id);
//		     resultlist = query.list();
//			 tran.commit();
//			-----------shigang modify--------------
			 String sql="from SysSlaMailpo s where s.c600000021='"+id+"'";
			  resultlist= HibernateDAO.queryObject(sql);
		}catch(Exception ex){
			return resultlist;
			
		}
		return resultlist;
	}
	
	
	/**
	 * 日期 2006-10-25
	 * @author xuquanxing 
	 * @param id
	 * @return
	 * @throws HibernateException List
     * 返回属于给定sla的所有升级动作信息列表
	 */
	public List getRelativeUpdateResult(String id) throws HibernateException{
		Transaction tran = null;
		List  resultlist = null;
		Query query      = null;
		try{
			 String sql	= "from SysSlaWorkFlowManagepo s where s.c600000031='"+id+"'";
		     resultlist = HibernateDAO.queryObject(sql);
			 tran.commit();
		}catch(Exception ex){
			if(tran!=null)
				try{
					tran.rollback();
				}catch(HibernateException e){
					logger.info("[109]:getRelativeUpdateResult"+ex.getMessage());
				}
		}finally{

		}
		return resultlist;
	}
	
	public static void main(String[] args) throws HibernateException{
		GetSlaInfoList getslainfolist=new GetSlaInfoList();
		List list=getslainfolist.getResults();
		for(Iterator it=list.iterator(); it.hasNext();){
			SysSlapo t=(SysSlapo)it.next();
			System.out.println(t.getC600000001());
		}
	}
}
