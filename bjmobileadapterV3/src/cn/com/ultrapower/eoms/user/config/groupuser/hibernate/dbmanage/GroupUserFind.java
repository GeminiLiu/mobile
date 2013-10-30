package cn.com.ultrapower.eoms.user.config.groupuser.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

/**
 * <p>Description:使用hibernate从数据库中查找字段 <p>
 * @author wangwenzhuo
 * @CreatTime 2006-10-20
 */
public class GroupUserFind {
	
	static final Logger logger = (Logger) Logger.getLogger(GroupUserFind.class);
	
	/**
	 * <p>Description:从UltraProcess:SysGroupUser表中返回一个List<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-20
	 * @return List
	 */
	public List find()
	{
		try 
		{
//		    Session session	= HibernateSessionFactory.currentSession();
//		    session.clear();
//			Transaction tx	= session.beginTransaction();
//			Query query		= session.createQuery("from SysGroupUserpo");
//			List list		= query.list();
//			tx.commit();
//			HibernateSessionFactory.closeSession();
			String sql="from SysGroupUserpo";
			List list= HibernateDAO.queryObject(sql);
			return list;
		} 
		catch (Exception e) 
		{
			logger.error("[464]GroupUserFind.find() 从UltraProcess:SysGroupUser表中返回List失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:根据组成员ID查找相应信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-20
	 * @param id
	 * @return SysGrouppo 
	 */
	public SysGroupUserpo findModify(String id)
	{
		try
		{
//			Session session = HibernateSessionFactory.currentSession();
//			Transaction tx	= session.beginTransaction();
//			SysGroupUserpo sysGroupUserpo = (SysGroupUserpo)session.load(SysGroupUserpo.class,id);
//			tx.commit();
//			HibernateSessionFactory.closeSession();
//			-----------shigang modify--------------
			SysGroupUserpo sysGroupUserpo = (SysGroupUserpo)HibernateDAO.loadStringValue(SysGroupUserpo.class,id);
			
			return sysGroupUserpo;
		}
		catch(Exception e)
		{
			logger.error("[465]GroupUserFind.findModify() 根据组成员ID查找相应信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:按照权限ID查找相应用户信息<p>
	 * @author wangwenzhuo
	 * @creattime 2006-10-23
	 * @param groupId
	 * @return List
	 */
	public List find(String groupId)
	{
		try
		{
			String sql="select a,b.c1 from SysPeoplepo a,SysGroupUserpo b where a.c1=b.c620000028 and b.c620000027 ='"+groupId+"' and a.c630000012='0' order by a.c630000017,a.c630000001";
			List list= HibernateDAO.queryObject(sql);
			return list;
		}
		catch(Exception e)
		{
			logger.error("[466]GroupUserFind.find() 按照权限ID查找相应用户信息失败"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * <p>Description:将虚拟组成员下的人员拼为一个字符串<p>
	 * @author wangwenzhuo
	 * @creattime 2007-1-9
	 * @param groupId
	 * @return String
	 */
	public String findselectedOfVirtual(String groupId)
	{
		String temp_str = "";
		List list		= find(groupId);
		try
		{
			if(list!=null)
			{
				for(Iterator it = list.iterator();it.hasNext();)
				{
					Object[] obj			= (Object[])it.next();
					SysPeoplepo syspeople	= (SysPeoplepo)obj[0];
					String userId				= syspeople.getC1();
					if(!temp_str.equals(""))
					{
						temp_str = temp_str + ",";
					}
					temp_str = temp_str + userId;
				}
			}
			else
			{
				temp_str = "";
			}
			return temp_str;
		}
		catch(Exception e)
		{
			logger.error("[528]GroupUserFind.findselectedOfVirtual() 将虚拟组成员下的人员拼为一个字符串失败"+e.getMessage());
			return null;
		}
	}

}
