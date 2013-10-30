package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class GetRolesSkillManage 
{

	static final Logger logger = (Logger) Logger.getLogger(GetRolesSkillManage.class);
	
	/**
	 * 根据资源权限管理表中ID值查询出RolesSkillManagepo信息。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
	public RolesSkillManagepo getRolesSkillManage(String id)
	{
		RolesSkillManagepo roleskillpo = null;
		String sql = "from RolesSkillManagepo rolesskillmanage where rolesskillmanage.c1 ='"+id+"'";
		try
		{
//			Session session	= HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();

						List list =HibernateDAO.queryObject(sql);
					
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				for(Iterator it = list.iterator();it.hasNext();)
				{
					 roleskillpo = (RolesSkillManagepo)it.next();
				}
			}
			if(roleskillpo!=null)
			{
				return roleskillpo;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			try
			{
//				HibernateSessionFactory.closeSession();
			}catch(Exception ex)
			{
				
			}
			logger.error("710 GetRolesManage类中getRolesManage(String id)" +
							"根据ID查询技能管理信息时出现异常！"+e.getMessage());
			return null;
		}
	}

	/**
	 * 根据技能名称、资源ID查询资源权限管理表，返回资源权限管理Bean信息的List.
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
	public List getRolesSkillManageList(String skillname,String skillsourceid,String cpid)
	{
		String sql = "from RolesSkillManagepo rolesskillmanage where rolesskillmanage.c660000006 ='"
					+ skillname+"' and rolesskillmanage.c660000007='"+skillsourceid+"' and rolesskillmanage.c660000012='"+cpid+"'";
		System.out.println(sql);
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();

			List list =HibernateDAO.queryObject(sql);
					
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				return list;
			}
			return null;
		} catch (Exception e) 
		{
			logger.info("720 GetRolesSkillManage 类中 " +
					"getRolesSkillManageList(String skillname,String skillsourceid) 方法执行查询时出现异常！");
			return null;
		}
	}

}
