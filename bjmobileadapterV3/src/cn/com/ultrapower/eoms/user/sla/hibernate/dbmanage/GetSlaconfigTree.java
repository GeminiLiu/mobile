package cn.com.ultrapower.eoms.user.sla.hibernate.dbmanage;

	import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.dbmanage.GetUserInfoList;

	public class GetSlaconfigTree {

		static final Logger logger = (Logger) Logger.getLogger(GetSlaconfigTree.class);

		private StringBuffer tmpstrjstree = new StringBuffer();
		
		

		public StringBuffer showTree(String userloginname)
		{
			
			StringBuffer strjs = new  StringBuffer();
			strjs.append("<script language=\"JavaScript\">var tree = new WebFXTree(\"Root\");");
			strjs.append("try{");
	    	strjs.append(intfind(userloginname));
	    	strjs.append("}catch(e){}");
	 	    strjs.append("</script>");
	    	strjs.append("<script>document.write(tree);</script>");
	    	return strjs;
		}
		
		public String intfind(String userloginname)
		{
			
			try
			{
				String                companyid = "";
				GetUserInfoList getUserInfoList	= new GetUserInfoList();
			  	companyid						= getUserInfoList.getUserInfoName(userloginname).getC630000013();
				
				StringBuffer sql = new StringBuffer();
				
				sql.append(" select slaconfigpo.id,grouptable.c630000018,basecatagorygrandpo.C650000001,sysdropdownconfigpo.c620000016,slaconfigpo.Slasupertime,slaconfigpo.SlaSchema,sysdropdownconfigpo2.c620000016");				
				sql.append(" from SlaConfigPo slaconfigpo,SysGrouppo grouptable,");
				sql.append(" BaseCatagorygrandpo basecatagorygrandpo,SysDropDownConfigpo sysdropdownconfigpo,SysDropDownConfigpo sysdropdownconfigpo2");

				sql.append(" where slaconfigpo.SlaType=sysdropdownconfigpo.c620000017 and sysdropdownconfigpo.c620000019='slaconfig' and sysdropdownconfigpo.c620000015='SlaType'");
				sql.append(" and slaconfigpo.Sendobj=sysdropdownconfigpo2.c620000017 and sysdropdownconfigpo2.c620000019='slaconfig' and sysdropdownconfigpo2.c620000015='Sendobj'");
				sql.append(" and slaconfigpo.SlaSchema=basecatagorygrandpo.C1");
				sql.append(" and slaconfigpo.Slacompany=grouptable.c1");
				
				if(!userloginname.equals("Demo"))
				{
				    sql.append(" and slaconfigpo.Slacompany='"+companyid+"'");
				}
				
				List list=HibernateDAO.queryObject(sql.toString());
				if(list!=null)
				{
				    for(Iterator it=list.iterator(); it.hasNext();)
			 	    {
				    	Object[] obj = (Object[]) it.next();
						String id = (String) obj[0];
						String Slacompany = String.valueOf(obj[1]);
						String SlaSchema = (String) obj[2];
						String SlaType = (String) obj[3];
						String Slasupertime = (String) obj[4];
						String sendobj = (String) obj[6];
			 	    	//						��ǰID       ��ID             ��ʾ��              		   URL
			 	    	tmpstrjstree.append("tree.add(new WebFXLoadTreeItem(\""+SlaSchema+"-"+SlaType+"-"+sendobj+"-"+Slasupertime+"分\",\"#\",\"UserSlaConfigShow?id="+id+"&Slacompany="+Slacompany+"\",\"\",\"\",\"\",\"mainFrame\"));");
			 	    }
				}
				logger.info(tmpstrjstree);
				return tmpstrjstree.toString();
			}
			catch(Exception e)
			{
				logger.info("235 GetSlaconfigTree 类中 intfind() 方法执行查询时出现异常！");
				e.printStackTrace(System.err);
				return null;
			}		
		}
	}

