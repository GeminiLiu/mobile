package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

	import java.util.Iterator;
	import java.util.List;

import org.apache.log4j.Logger;

	import org.hibernate.Query;
	import org.hibernate.Session;
	import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
	import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateSessionFactory;
	import cn.com.ultrapower.eoms.user.config.groupuser.hibernate.po.SysGroupUserpo;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

	public class GetRoleGroupUserTree 
	{
		static final Logger logger = (Logger) Logger.getLogger(GetRoleGroupUserTree.class);
		public static String findGroupUser(String groupid) 
		{
			String returnStr = "";
			try {
				String sql="from SysGroupUserpo where C620000027='"+groupid+"'";
				List list = HibernateDAO.queryObject(sql);
				if (list!=null) 
				{
					for (Iterator it = list.iterator(); it.hasNext();) 
					{
						SysGroupUserpo sysgroupuser = (SysGroupUserpo) it.next();
						String userid = sysgroupuser.getC620000028();
						returnStr = returnStr + findUser(userid,groupid);
					}
				}
				return returnStr;
			} 
			catch (Exception e) 
			{
				logger.info("232 GetRoleGroupUserTree 类中 findGroupUser(String groupid) 方法查询时出现异常!");
				return null;
			}
		}

		public static String findUser(String userid,String groupid) 
		{
			String userStr = "";
			try 
			{
				String sql	= "from SysPeoplepo where C1="+ userid;
				List list = HibernateDAO.queryObject(sql);
				if (list!=null) 
				{
					for (Iterator it = list.iterator(); it.hasNext();) {
						SysPeoplepo sysuser = (SysPeoplepo) it.next();
						String tmp_userid = sysuser.getC1();
						String tmp_username = sysuser.getC630000003();
						userStr =userStr+ "d.add("+tmp_userid+","+groupid+",\"<font onclick=back_time('"+tmp_username+"','"+tmp_userid+"','"+groupid+"');>"+tmp_username+"</font>\",'','','main','','','false');";
					}

				}
				return userStr;
				
			} 
			catch (Exception e) 
			{
				logger.info("233 GetRoleGroupUserTree 类中findUser(String userid,String groupid) 方法查询时出现异常!");
				return null;
			}
		}

	}