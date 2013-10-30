package cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRoleUserTree;
import cn.com.ultrapower.eoms.user.authorization.role.hibernate.dbmanage.GetRoleUserTreeCheckBox;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.rolemanage.group.hibernate.po.SysGrouppo;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.function.Function;

	public class GetRoleGroupCheckedTree 
	{
			static final Logger logger = (Logger) Logger.getLogger(GetRoleGroupCheckedTree.class);

			static Session session = null;
			private String strjstree 		= "";
			private String tmpstrjstree		= "";
			String            Group_Type1	= "";
			Function str=new Function();
		    GetFormTableName  tablename = new GetFormTableName();
		    			
		    public GetRoleGroupCheckedTree()
		    {
		    	try
		    	{
		    		Group_Type1 = tablename.GetFormName("Group_Type");
		    	}
		    	catch(Exception e)
		    	{
		    		logger.info("231 GetRoleGroupCheckedTree 类中 构造方法 调用GetFormTableName 时出现异常！");
		    	}
		    }
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
				String groupParentID  = "";
	 	    	String Group_Type	  = "";
	 	    	String Group_Name     = "";
	 	    	String groupID        = "";
				try
				{
				     String query = "from SysGrouppo";
					
					 HibernateDAO  session = new HibernateDAO(); 
					 List l1  = session.queryObject(query);
					 Iterator it=l1.iterator();
					 
					    while(it.hasNext())
				 	    {
					    	SysGrouppo sysgroup = (SysGrouppo)it.next();
					    	 groupParentID  = str.nullString(sysgroup.getC630000020());
				 	    	 Group_Name     = str.nullString(sysgroup.getC630000018().trim());
				 	    	 groupID        = str.nullString(sysgroup.getC1().toString());

					 	    	tmpstrjstree="d.add("+groupID+","+groupParentID+",\""+Group_Name+
					 	    					"</font>\",\"\",\"\",\"main\",\"\",\"\",\"false\");";
					 	    	
					 	    	strjstree=strjstree+tmpstrjstree+GetRoleUserCheckedTree.findGroupUser(groupID);
				 	    	
				 	    }	
					logger.info("strjstree:"+strjstree);
					return strjstree.toString();
				}
				catch(Exception e)
				{
					logger.info("----请查看c21是否为空----"+e);
					e.printStackTrace(System.err);
					return null;
				}		
			}
		}


