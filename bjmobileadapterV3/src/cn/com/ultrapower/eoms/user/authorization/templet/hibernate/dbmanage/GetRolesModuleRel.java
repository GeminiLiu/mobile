package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesModuleRelpo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;

public class GetRolesModuleRel
{
	static final Logger logger = (Logger) Logger.getLogger(GetRolesModuleRel.class);
	/**
	 * 查询技能管理或派发管理表中所有信息
	 * 日期 2006-12-28
	 * @author wangyanguang
	 */
	public List getRolesManageList(String sql)
	{
		
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
			logger.info("750 GetRolesModuleRel 类中 " +
					"getRolesManageList 方法执行查询时出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据角色关联表中ID值查询出RolesModuleRelpo信息。
	 * 日期 2006-12-26
	 * @author wangyanguang
	 */
	public RolesModuleRelpo getRolesModuleRel(String id)
	{
		try
		{
//			Session session						= HibernateSessionFactory.currentSession();
//			Transaction tx						= session.beginTransaction();
//			RolesModuleRelpo rolesmanagepo		= (RolesModuleRelpo)session.load(RolesModuleRelpo.class,id);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return rolesmanagepo;
			RolesModuleRelpo rolesmanagepo=(RolesModuleRelpo)HibernateDAO.loadStringValue(RolesModuleRelpo.class,id);
			return rolesmanagepo;
		}
		catch(Exception e)
		{
			logger.error("751 GetRolesManage类中getRolesManage(String id)" +
							"根据ID查询技能管理信息时出现异常！"+e.getMessage());
			return null;
		}
	}

	/**
	 * 根据角色ID查询出所有资源授权管理表中的记录
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getSkillInfo(String roleid)
	{
		StringBuffer sql =  new StringBuffer();
		sql.append(" from RolesModuleRelpo rolesmodulerel,RolesSkillManagepo skillmanage ");
		sql.append(" where rolesmodulerel.c660000020="+roleid+"");
		sql.append(" and rolesmodulerel.c660000022='0'");
		sql.append(" and rolesmodulerel.c660000021=skillmanage.c1");
		System.out.println(sql.toString());
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql.toString());
//			List list 		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject(sql.toString());
		} 
		catch (Exception e) 
		{
			logger.info("750 GetRolesModuleRel 类中 " +
					"getSkillInfo(String roleid) 方法执行查询时出现异常！");
			return null;
		}
	}
	
	/**
	 * 根据角色ID查询出所有派发授权管理一中的记录。
	 * 日期 2007-1-9
	 * @author wangyanguang
	 */
	public List getSendInfo(String roleid)
	{
		StringBuffer sql =  new StringBuffer();
		
		sql.append(" from RolesModuleRelpo rolesmodulerel,RolesSendManagepo sendmanage ");
		sql.append(" where rolesmodulerel.c660000020="+roleid+"");
		sql.append(" and rolesmodulerel.c660000022='1'");
		sql.append(" and rolesmodulerel.c660000021=sendmanage.c1");
		System.out.println("查询派发管理中的ＳＱＬ："+sql.toString());
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql.toString());
//			List list 		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			return list;
			return HibernateDAO.queryObject(sql.toString());
		} 
		catch (Exception e) 
		{
			logger.info("750 GetRolesModuleRel 类中 " +
					"getSendInfo(String roleid) 方法执行查询时出现异常！");
			return null;
		}
	
	}
	
	/**
	 * 根据角色ID、技能ID与类型ID判断记录在数据库中是否存在。
	 * 日期 2007-2-2
	 * @author wangyanguang
	 * @param roleid    角色ID
	 * @param skillid	技能ID
	 * @param typeid	类型ID
	 */
	public static boolean getBoolValue(String roleid,String skillid,String typeid)
	{
		StringBuffer sql =  new StringBuffer();
		
		sql.append(" from RolesModuleRelpo rolesmodulerel ");
		sql.append(" where rolesmodulerel.c660000020="+roleid+"");
		sql.append(" and rolesmodulerel.c660000022='"+typeid+"'");
		sql.append(" and rolesmodulerel.c660000021='"+skillid+"'");
		System.out.println("查询派发管理中的ＳＱＬ："+sql.toString());
		try 
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx 	= session.beginTransaction();
//			Query query 	= session.createQuery(sql.toString());
//			List list 		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
			List list = HibernateDAO.queryObject(sql.toString());
			if(list!=null)
			{
				if(list.size()>0)
				{
					return false;
				}
				else
				{
					return true;
				}
			}else
			{
				return true;
			}
		} 
		catch (Exception e) 
		{
			logger.info(" GetRolesModuleRel 类中 " +
					"getBoolValue(String roleid,String skillid,String typeid)方法执行查询时出现异常！");
			return false;
		}
		
	}
	
	public static void main(String[] args)
	{
		GetRolesModuleRel getrel = new GetRolesModuleRel();
		System.out.println(getrel.getSkillInfo("000000000000008"));
	}
}
