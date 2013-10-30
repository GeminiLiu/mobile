package cn.com.ultrapower.eoms.user.authorization.templet.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesManagepo;
import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesUserGroupRelpo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;

public class GetRolesUserGroupRel
{
	/**
	 * 根据公司ID生成下拉列表值。
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public String getRoleInfo(String companyid,String usertype)
	{
		GetRolesManage getroleinfo  = new GetRolesManage();
		StringBuffer optionvalue   = new StringBuffer();
		try
		{
			List list = getroleinfo.getRolesManageInfo(companyid,usertype);
			Iterator it=list.iterator();
			optionvalue.append("<option value=''>请选择</option>");
			
			while(it.hasNext())
			{	
				RolesManagepo rolesmanageinfo = (RolesManagepo)it.next();
				optionvalue.append("<option value='"+String.valueOf(rolesmanageinfo.getC1())+"'>"+String.valueOf(rolesmanageinfo.getC660000001())+"</option>");
			}
			
			return optionvalue.toString();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * 根据ID查询用户、组与权限关联信息Bean.
	 * 日期 2007-1-8
	 * @author wangyanguang
	 */
	public RolesUserGroupRelpo getRolesUserGroup(String id)
	{
		try
		{
//			Session session						= HibernateSessionFactory.currentSession();
//			Transaction tx						= session.beginTransaction();
//			RolesUserGroupRelpo rolesmanagepo	= (RolesUserGroupRelpo)session.load(RolesUserGroupRelpo.class,id);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
			RolesUserGroupRelpo rolesmanagepo	= (RolesUserGroupRelpo)HibernateDAO.loadStringValue(RolesUserGroupRelpo.class,id);
			return rolesmanagepo;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	

}
