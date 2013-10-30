package cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.Grouppo;

/**
 * <p>Description:使用hibernate从数据库中查找字段<p>
 * @author wangwenzhuo
 * @CreatTime 2006-11-6
 */
public class ReGroupFind {
	
	static final Logger logger 		= (Logger) Logger.getLogger(ReGroupFind.class);

	/**
	 * <p>Description:根据groupId查找Remedy表Grouppo的C1<p>
	 * @author wangwenzhuo
	 * @creattime 2006-11-6
	 * @param groupId
	 * @return String
	 */
	public String findModify(String groupId)
	{
		try
		{
//			Session session	= HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query = session.createQuery("from Grouppo a where a.c106="+groupId);
//			List list = query.list();
//			-----------shigang modify--------------
			String sql="from Grouppo a where a.c106="+groupId;
			List list= HibernateDAO.queryObject(sql);

			
			for(Iterator it=list.iterator(); it.hasNext();)
			{
				Grouppo group	=(Grouppo)it.next();
				String c1		= group.getC1();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return c1;
			}
			
			logger.info("[431]ReGroupFind.findModify() 系统组表中没有相应记录");
			return null;
		}
		catch(Exception e){
			logger.error("[431]ReGroupFind.findModify() 根据groupId查找Remedy表Grouppo的C1失败"+e.getMessage());
			return null;
		}
	}

}
