package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

	import java.util.Iterator;
	import java.util.List;
	import java.util.Set;
	import java.util.Vector;

	import org.hibernate.Query;
	import org.hibernate.Session;
	import org.hibernate.Transaction;

	import org.apache.log4j.Logger;

	import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
	import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.dbmanage.GroupFind;
	import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
	import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;

	public class GetRoleTree {

		static final Logger logger = (Logger) Logger.getLogger(GetRoleTree.class);

		private String strjstree 		= "";
		private String tmpstrjstree		= "";
		
		

		public StringBuffer showTree()
		{
			
			StringBuffer strjs = new  StringBuffer();
			strjs.append("<script language='JavaScript'>d = new dTree('d');d.add(0,-1,'',null,'','main');</script>");
	    	strjs.append("<script language='JavaScript'>");
	    	strjs.append(intfind());
	 	    strjs.append("</script>");
	    	strjs.append("<script>document.write(d);</script>");
	    	return strjs;
		}
		
		public String intfind()
		{
			tmpstrjstree="";
			
			try
			{
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysGrouppo");
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				String sql="from SysGrouppo";
				List list=HibernateDAO.queryObject(sql);
				if(list!=null)
				{
				    for(Iterator it=list.iterator(); it.hasNext();)
			 	    {
			 	    	SysGrouppo sysgroup  = (SysGrouppo)it.next();
			 	    	String groupParentID = sysgroup.getC630000020();
			 	    	String groupName     = sysgroup.getC630000018();
			 	    	String groupID       = sysgroup.getC1();
			 	    	//						��ǰID       ��ID             ��ʾ��              		   URL
			 	    	tmpstrjstree="d.add("+groupID+","+groupParentID+",\""+groupName+"\",\"\",\"\",\"main\",\"\",\"\",\"false\");";
			 	    	
			 	    	
			 	    	strjstree=strjstree+tmpstrjstree+GetRoleUserTree.findGroupUser(groupID);
			 	    }
				}
				logger.info(strjstree);
				return strjstree.toString();
			}
			catch(Exception e)
			{
				logger.info("235 GetRoleTree 类中 intfind() 方法执行查询时出现异常！");
				e.printStackTrace(System.err);
				return null;
			}		
		}
	}

