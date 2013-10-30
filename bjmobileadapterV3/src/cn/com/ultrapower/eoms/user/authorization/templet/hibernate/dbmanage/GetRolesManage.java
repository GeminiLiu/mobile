package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesManagepo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;

public class GetRolesManage 
{

	static final Logger logger = (Logger) Logger.getLogger(GetRolesManage.class);
	
	/**
	 * 根据资源管理表中ID值查询出RolesManagepo信息。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
	public RolesManagepo getRolesManage(String id)
	{
		try
		{
//			Session session				= HibernateSessionFactory.currentSession();
//			Transaction tx				= session.beginTransaction();
//			RolesManagepo rolesmanagepo	= (RolesManagepo)session.load(RolesManagepo.class,id);
			RolesManagepo rolesmanagepo	= (RolesManagepo)HibernateDAO.loadStringValue(RolesManagepo.class,id);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
			return rolesmanagepo;
		}
		catch(Exception e)
		{
			logger.error("710 GetRolesManage类中getRolesManage(String id)根据ID查询技能管理信息时出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 查询资源管理表中所有信息
	 * 日期 2006-12-28
	 * @author wangyanguang
	 */
	public List getRolesManageList()
	{
		String sql = "from RolesManagepo";
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();
//			
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject(sql);
			
		} catch (Exception e) 
		{
			logger.info("740 GetRolesManage 类中 " +
					"getRolesManageList 方法执行查询时出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据公司ID查询角色信息。
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public List getRolesManageInfo(String companyid,String usertype)
	{
		String sql = "";
		if(String.valueOf(usertype).equals("admin"))
		{
			sql = " from RolesManagepo";
		}
		else
		{
			sql = "from RolesManagepo rolesmanagepo where rolesmanagepo.c660000004='"+companyid+"'";
		}
		
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();
//			
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject(sql);
		}
		catch(Exception e)
		{
			logger.info("GetRolesManage 类中 " +
			"getRolesManageInfo 方法执行查询时出现异常！");
			return null;
		}
	}
}
