package cn.com.ultrapower.eoms.user.authorization.templet.aroperationdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.authorization.bean.GroupUserBean;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class RolesModuleRelSQL
{

	/**
	 * 根据角色ID查询出与此角色ID相关联的用户与组信息集合。
	 * 日期 2007-2-2
	 * @author wangyanguang
	 * @param roleid
	 */
	public List getUserGroupInfo(String roleid)
	{
		List returnList = new ArrayList();
		List list = getUserGroupSQL(roleid);
		if(list!=null)
		{
			for(Iterator it = list.iterator();it.hasNext();)
			{
				Object[] obj = (Object[])it.next();
				String userid = (String)obj[0];
				String groupid = (String)obj[1];
				if(!userid.equals("")&&!groupid.equals(""))
				{
					GroupUserBean groupuserinfo = new GroupUserBean();
					groupuserinfo.setGroupid(groupid);
					groupuserinfo.setUserid(userid);
					returnList.add(groupuserinfo);
				}
			}
		}
		if(returnList!=null)
		{
			return returnList;
		}
		else
		{
			return null;
		}
	}
	//根据角色ID查询用户与组信息。
	public List getUserGroupSQL(String roleid)
	{
		StringBuffer sql = new StringBuffer();
		
		sql.append("select distinct usertable.c1,groupusertable.c620000027");
		sql.append(" from SysPeoplepo usertable,SysGroupUserpo groupusertable,RolesUserGroupRelpo grouprolerel");
		sql.append(" where grouprolerel.c660000027=groupusertable.c620000027 ");
		sql.append(" and groupusertable.c620000028=usertable.c1");
		sql.append(" and grouprolerel.c660000028='"+roleid+"'");
		sql.append(" and ((grouprolerel.c660000027=groupusertable.c620000027  and grouprolerel.c660000026=groupusertable.c620000028)");
		sql.append(" or(grouprolerel.c660000027=groupusertable.c620000027 and grouprolerel.c660000026 is null))");

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
			return null;
		}
		
	}

	public static void main(String args[])
	{
		RolesModuleRelSQL rolesql = new RolesModuleRelSQL();
		List list = rolesql.getUserGroupInfo("000000000000014");
		for(Iterator it = list.iterator();it.hasNext();)
		{
			GroupUserBean groupuserinfo = (GroupUserBean)it.next();
			System.out.println("组ID："+groupuserinfo.getGroupid()+",用户ID："+groupuserinfo.getUserid());
			
		}
	}
}
