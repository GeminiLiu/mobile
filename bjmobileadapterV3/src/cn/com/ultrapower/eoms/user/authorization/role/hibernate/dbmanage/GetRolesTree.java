package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

	import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.templet.hibernate.po.RolesManagepo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

	public class GetRolesTree {

		static final Logger logger = (Logger) Logger.getLogger(GetRolesTree.class);

		private StringBuffer tmpstrjstree = new StringBuffer();
		
		

		public StringBuffer showTree(String companyid)
		{
			
			StringBuffer strjs = new  StringBuffer();
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
	    	strjs.append(intfind(companyid));
	    	strjs.append("}catch(e){}");
	 	    strjs.append("</script>");
	    	strjs.append("<script>document.write(tree);</script>");
	    	return strjs;
		}
		
		public String intfind(String companyid)
		{
			
			try
			{
//				Session session = HibernateSessionFactory.currentSession();
//				Transaction tx = session.beginTransaction();
//				Query query = session.createQuery("from SysGrouppo");
//				List list = query.list();
//				tx.commit();
//				HibernateSessionFactory.closeSession();
				String sql="from RolesManagepo rolesmanagepo";
				if(companyid != ""){
					sql += " where rolesmanagepo.c660000004='"+companyid+"'";
				}
				
				List list=HibernateDAO.queryObject(sql);
				if(list!=null)
				{
				    for(Iterator it=list.iterator(); it.hasNext();)
			 	    {
				    	RolesManagepo roles  = (RolesManagepo)it.next();
			 	    	String rolesName     = roles.getC660000001();
			 	    	String rolesID       = roles.getC1();
			 	    	//						��ǰID       ��ID             ��ʾ��              		   URL
			 	    	tmpstrjstree.append("tree.add(new WebFXLoadTreeItem(\""+rolesName+"\",\"#\",\"CatagorygrandShow?id="+rolesID+"\",\"\",\"\",\"\",\"mainFrame\"));");
			 	    }
				}
				logger.info(tmpstrjstree);
				return tmpstrjstree.toString();
			}
			catch(Exception e)
			{
				logger.info("235 GetRoleTree 类中 intfind() 方法执行查询时出现异常！");
				e.printStackTrace(System.err);
				return null;
			}		
		}
	}

