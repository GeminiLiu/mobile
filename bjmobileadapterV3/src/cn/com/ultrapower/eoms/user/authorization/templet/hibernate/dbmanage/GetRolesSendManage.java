package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSendManagepo;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesSkillManagepo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class GetRolesSendManage
{

	static final Logger logger = (Logger) Logger.getLogger(GetRolesSendManage.class);
	
	/**
	 * 根据派发权限管理表中ID值查询出RolesSendManagepo信息。
	 * 日期 2006-12-27
	 * @author wangyanguang
	 */
	public RolesSendManagepo getRolesSendManage(String id)
	{
		String sql = " from RolesSendManagepo rolespo where rolespo.c1='"+id+"'";
		RolesSendManagepo rolessendpo = null;
		try
		{
//			Session session	= HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			Query query 	= session.createQuery(sql);
//			List list 		= query.list();
			List list 		=	HibernateDAO.queryObject(sql);
			if(list!=null)
			{
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				for(Iterator it = list.iterator();it.hasNext();)
				{
					rolessendpo = (RolesSendManagepo)it.next();
				}
			}
			if(rolessendpo!=null)
			{
				return rolessendpo;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("720 GetRolesSendManage类中getRolesSendManage(String id)根据ID查询派发权限管理信息时出现异常！"+e.getMessage());
			return null;
		}
	}
	
	public boolean isDuplicate(String id, String RoleSend_Name,
			String RoleSend_SouceID, String RoleSend_Group,String RoleSend_type) throws HibernateException {
		try {
			String sql = "from RolesSendManagepo t where t.c660000014='"
					+ RoleSend_Name + "' and t.c660000015='"+RoleSend_SouceID
					+ "' and t.c660000016='" + RoleSend_Group + "' and t.c660000019='"+RoleSend_type+"'";

			List list = HibernateDAO.queryObject(sql);
			for (Iterator it = list.iterator(); it.hasNext();) {

				RolesSendManagepo po = (RolesSendManagepo) it.next();
				if (!id.equals(po.getC1())) {
					return true;
				} else {
					continue;
				}
			}
			return false;

		} catch (Exception e) {
			logger.error("332 MenuRequestAction.getMenuId error:"
					+ e.toString());
			e.printStackTrace();
			return true;
		}
	}

}
