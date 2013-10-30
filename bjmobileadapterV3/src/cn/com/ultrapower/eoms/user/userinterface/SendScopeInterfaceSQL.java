package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.ElementInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SendScopePram;

public class SendScopeInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(SendScopeInterfaceSQL.class);
	
	private String sourcemanager			= "";
	private String systemmanage				= "";
	private String sourceconfig				= "";
	private String dutyorgnazition			= "";
	private String orgnazitionarranger		= "";
	private String usertablename			= "";
	private String grouptablename			= "";
	private String groupusertablename		= "";
	private String sysskill					= "";
	private String uid						= "";
	private String skillaction				= "";
	private String usergrouprel				= "";
	private String sendscopetable			= "";
	private String grandactiontable			= "";
	private String usersendscope			= "";
	private String usersendscopenamemag		= "";
	private String sendscopemanagetable		= "";
	private String roleskillmanagetable		= "";
	private String rolemanagetable			= "";
	/**
	 * 取得配制信息，读取配制文件
	 */
	public SendScopeInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager		 = getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage		 = getTableProperty.GetFormName("systemmanage");
			sourceconfig		 = getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition		 = getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger	 = getTableProperty.GetFormName("orgnazitionarranger");
			usertablename		 = getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		 = getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	 = getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill			 = getTableProperty.GetFormName("RemedyTrole");
			skillaction			 = getTableProperty.GetFormName("managergrandaction");
			usergrouprel		 = getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			sendscopetable		 = getTableProperty.GetFormName("RemedyTsendscope");
			grandactiontable	 = getTableProperty.GetFormName("RemedyTgrandaction");
			usersendscope		 = getTableProperty.GetFormName("usersendscope");
			usersendscopenamemag = getTableProperty.GetFormName("usersendscopenamemag");
			sendscopemanagetable = getTableProperty.GetFormName("RemedyTrolessendmanage");
			roleskillmanagetable = getTableProperty.GetFormName("RemedyTrolesskillmanage");
			rolemanagetable		 = getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}

	/**
	 * 根据用户登陆名与资源名查询出此用户所能派发的组（组的顶级节点）
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param username
	 * @param sourcename
	 */
	public List getParentElement(SendScopePram sendscopepram)
	{
		String username 	= sendscopepram.getUserLoginName();
		String sourcename	= sendscopepram.getSourceName();
		String sendscopetype = sendscopepram.getSendscopetype();
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		//////////////
		sql.append(" select distinct c1,c630000018,c630000020,c630000030,c630000022,c630000019 from (");
		sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022");
		sql.append(" from "+ sendscopetable +"         sendscopetable,");
		sql.append( grouptablename+" grouptable,");		
		sql.append( usertablename + " usertable,");		                
		sql.append( " sourceconfig sourcetable ");		                
		sql.append(" where usertable.c630000012='0' and sendscopetable.c610000020 = usertable.c1");		                
		sql.append(" and usertable.c630000001='"+username+"'");	  
		sql.append(" and sendscopetable.c610000025=sourcetable.source_id");
		sql.append(" and sourcetable.source_name='"+sourcename+"'");
		sql.append(" and sendscopetable.c610000026='"+sendscopetype+"'");
		sql.append(" and sendscopetable.c610000023 = grouptable.c1");
		sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
		sql.append(" union ");		 
		sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022 ");		
		sql.append(" from "+sendscopemanagetable+"  sendscopemanagetable,");		
		sql.append( grouptablename+" grouptable,");				                
		sql.append(groupusertablename+"  groupusertable,");		                
		sql.append( usertablename + " usertable,");		                
		sql.append(usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(" sourceconfig sourcetable");		      
		sql.append(" where usertable.c630000012='0' and usertable.c630000001='"+username+"'");	
		sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
		sql.append("  and groupusertable.c620000028 = usertable.c1");		      
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and");		           
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027) or");		       
		sql.append(" (usergrouprel.c660000026 is null and usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append("  and sendscopemanagetable.c660000014 = usergrouprel.c660000028");		  
		sql.append(" and sendscopemanagetable.c660000015 = sourcetable.source_id");		   
		sql.append(" and sourcetable.source_name='"+sourcename+"'");       
		sql.append(" and sendscopemanagetable.c660000019 ='"+sendscopetype+"'");		       
		sql.append(" and sendscopemanagetable.c660000016 = grouptable.c1) uu");		       
		sql.append("  where 1=1  and not exists(");		  
//		sql.append(" select distinct c1,c630000018,c630000020,c630000030,c630000022 from (");
		sql.append(" select  grouptable.c1 ");
		sql.append(" from "+ sendscopetable +"         sendscopetable,");
		sql.append( grouptablename+" grouptable,");		
		sql.append( usertablename + " usertable,");	
		sql.append( " sourceconfig sourcetable ");		                
		sql.append(" where usertable.c630000012='0' and sendscopetable.c610000020 = usertable.c1");		                
		sql.append(" and usertable.c630000001='"+username+"'");	  
		sql.append(" and sendscopetable.c610000025=sourcetable.source_id");
		sql.append(" and sourcetable.source_name='"+sourcename+"'");
		sql.append(" and sendscopetable.c610000026='"+sendscopetype+"'");
		sql.append(" and sendscopetable.c610000023 = grouptable.c1");
		sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
		sql.append(" and  grouptable.c1 = uu.c630000020)");
		sql.append(" and not exists( ");		 
		sql.append(" select  grouptable.c1 ");		
		sql.append(" from "+sendscopemanagetable+"  sendscopemanagetable,");		
		sql.append( grouptablename+" grouptable,");				                
		sql.append(groupusertablename+"  groupusertable,");		                
		sql.append( usertablename + " usertable,");
		sql.append(usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");			
		sql.append(" sourceconfig sourcetable");		      
		sql.append(" where usertable.c630000012='0' and usertable.c630000001='"+username+"'");	
		sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
		sql.append("  and groupusertable.c620000028 = usertable.c1");		      
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and");		           
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027) or");		       
		sql.append(" (usergrouprel.c660000026 is null and usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append("  and sendscopemanagetable.c660000014 = usergrouprel.c660000028");		  
		sql.append(" and sendscopemanagetable.c660000015 = sourcetable.source_id");		   
		sql.append(" and sourcetable.source_name='"+sourcename+"'");       
		sql.append(" and sendscopemanagetable.c660000019 ='"+sendscopetype+"'");		       
		sql.append(" and sendscopemanagetable.c660000016 = grouptable.c1 ");		   
		sql.append(" and  grouptable.c1 = uu.c630000020)");		   
		sql.append("  order by c630000022 ");
				   
		logger.info("根据用户与资源查询用户能派发的组的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	//String groupid     	= Function.nullString(rs.getString("c1"));
	 	    	String groupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	//String recordid		= Function.nullString(rs.getString("sendscopeid"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		//elementinfo.setRecordid(recordid);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 根据父组ID查询资源配置表、组表、派发表查询出子组信息。
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param parentid       	父组ID
	 * @param username			用户名
	 * @param sourcename		资源名
	 */
	public List getChildElement(String parentid,String username,String sourcename,String sendscopetype)
	{
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
//		sql.append("select distinct grouptable.c1, grouptable.c630000018,grouptable.c630000030,sendscopetable.c1 sendscopeid from ");
//		sql.append(sendscopetable+" sendscopetable,"+grouptablename+" grouptable, ");
//		sql.append(usertablename+" usertable,"+sourceconfig+" sourcetable");
//		sql.append(" where grouptable.c630000020='"+parentid+"'");
//		sql.append(" and  grouptable.c1= sendscopetable.c610000023 "); 
//		sql.append(" and  sendscopetable.c610000020 = usertable.c1");
//		sql.append(" and  sendscopetable.c610000026='"+sendscopetype+"'");
//		sql.append(" and  usertable.c630000001='"+username+"'");
//		sql.append(" and  sendscopetable.c610000025=sourcetable.source_id");
//		sql.append(" and  sourcetable.source_name='"+sourcename+"'");
		
		sql.append(" select distinct tb.c1,tb.c630000018,tb.c630000020,tb.c630000030,tb.c630000019,tb.c630000022,groupusertable2.c7 from (");
		sql.append(" select distinct grouptable.c1, grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022 ");
		sql.append(" from ");
		sql.append( sendscopetable+" sendscopetable,");
		sql.append( grouptablename+" grouptable,");
        sql.append( usertablename+" usertable,");
        sql.append(" sourceconfig sourcetable");
        sql.append(" where usertable.c630000012='0' and grouptable.c630000020  = '"+parentid+"'"); 
        sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
        sql.append(" and grouptable.c1 = sendscopetable.c610000023 and sendscopetable.c610000020 = usertable.c1");
        sql.append(" and  sendscopetable.c610000026='"+sendscopetype+"'");
        sql.append(" and  usertable.c630000001='"+username+"'");
        sql.append(" and  sendscopetable.c610000025=sourcetable.source_id");
        sql.append(" and  sourcetable.source_name='"+sourcename+"'");
        sql.append(" union ");
        sql.append(" select distinct grouptable.c1, grouptable.c630000018, grouptable.c630000020, grouptable.c630000030,grouptable.c630000019,grouptable.c630000022 ");
        sql.append(" from "+sendscopemanagetable+"  sendscopemanagetable,");		
		sql.append( grouptablename+" grouptable,");				                
		sql.append( groupusertablename+"  groupusertable,");		                
		sql.append( usertablename + " usertable,");		                
		sql.append( usergrouprel+" usergrouprel,");
		sql.append( rolemanagetable + " rolemanagetable,");
		sql.append(" sourceconfig sourcetable");     
		sql.append(" where usertable.c630000012='0' and grouptable.c630000020 = '"+parentid+"'");
		sql.append(" and grouptable.c630000025='0' and grouptable.c630000021!='4' ");
		sql.append(" and  usertable.c630000001='"+username+"'");
		sql.append(" and groupusertable.c620000028 = usertable.c1");
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and usergrouprel.c660000027 = groupusertable.c620000027) or ");
		sql.append(" (usergrouprel.c660000026 is null and usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append(" and sendscopemanagetable.c660000014 = usergrouprel.c660000028");
		sql.append(" and sendscopemanagetable.c660000015 = sourcetable.source_id");
		sql.append(" and  sourcetable.source_name='"+sourcename+"'");
		sql.append(" and sendscopemanagetable.c660000019 = '"+sendscopetype+"'");
		sql.append(" and sendscopemanagetable.c660000016 = grouptable.c1");
		sql.append(") tb left join ");
		sql.append( groupusertablename+" groupusertable2 on groupusertable2.c620000027=tb.c1 ");
		sql.append(" order by tb.c630000022 ");
		
		logger.info("根据组查询子组的查询语句："+sql.toString());
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
				String groupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	//String groupid     	= Function.nullString(rs.getString("c1"));
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	//String recordid     = Function.nullString(rs.getString("sendscopeid"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	String hasuser = Function.nullString(rs.getString("c7"));
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		elementinfo.setHasuser(hasuser);
	 	    		//elementinfo.setRecordid(recordid);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 根据组ID与资源名，查询出对此资源有权限的组成员List
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param parentid		组ID
	 * @param sourcename	资源名称
	 */
	public List getUserElement(String parentid,String sourcename,String workflowtype)
	{
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		if(workflowtype.equals(""))
		{
			workflowtype = "0";
		}
		
		sql.append("select distinct c1,c630000001,c630000003,c630000017,c630000008,c630000019 from (");
		sql.append("select distinct usertable.c1,usertable.c630000001,usertable.c630000003,usertable.c630000017,usertable.c630000008,grouptable.c630000019 ");
		sql.append(" from "+sysskill+"  skilltable,");
		sql.append(groupusertablename+" groupusertable,");
		sql.append(grandactiontable +" grandactiontable,");
		sql.append(usertablename + " usertable,");
		sql.append(sourceconfig +" sourcetable,");
		sql.append(grouptablename + " grouptable ");
		
		sql.append(" where groupusertable.c620000027 ='"+parentid+"'");
		sql.append(" and usertable.c630000012='0'");
		sql.append(" and grouptable.c1='"+parentid+"'");
		sql.append(" and   skilltable.c610000007     = groupusertable.c620000028 ");
		sql.append(" and   groupusertable.c620000028 = usertable.c1");
		sql.append(" and   skilltable.c610000010 = grandactiontable.c620000034");
		sql.append(" and   skilltable.c610000008 = grandactiontable.c620000032");
		sql.append(" and   skilltable.c610000008 = sourcetable.source_id");
		sql.append(" and   sourcetable.source_name='"+sourcename+"'");
		sql.append(" and   skilltable.c610000018 = '0'");
		sql.append(" and   skilltable.c610000019 = '"+workflowtype+"'");
		sql.append(" and   (skilltable.c610000015 > "+(System.currentTimeMillis()/1000)+" or skilltable.c610000014 is null or skilltable.c610000015=0 or skilltable.c610000015 is null )");
		
		sql.append(" union ");
		sql.append(" select distinct usertable.c1, usertable.c630000001, usertable.c630000003,usertable.c630000017,usertable.c630000008,grouptable.c630000019 from ");
		sql.append(  roleskillmanagetable+" roleskillmanage,");
		sql.append(  usergrouprel+"  usergrouprel,");
		sql.append(  rolemanagetable + " rolemanagetable,");
		sql.append(  groupusertablename+" groupusertable,");
		sql.append(  grandactiontable +" grandactiontable,");
		sql.append(  usertablename + " usertable,");
		sql.append(" sourceconfig  sourcetable,");
		sql.append(  grouptablename +" grouptable ");
		sql.append(" where groupusertable.c620000027 = '"+parentid+"'");
		sql.append(" and grouptable.c1='"+parentid+"'");
		sql.append(" and usertable.c630000012='0'");
		sql.append(" and groupusertable.c620000028 = usertable.c1");
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and");
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027) or (usergrouprel.c660000026 is null and");        
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");
		sql.append(" and roleskillmanage.c660000006=usergrouprel.c660000028 and roleskillmanage.c660000007=grandactiontable.c620000032");       
		sql.append(" and roleskillmanage.c660000009=grandactiontable.c620000034  and roleskillmanage.c660000007=sourcetable.source_id");
		sql.append(" and sourcetable.source_name = '"+sourcename+"'");
		sql.append(" and roleskillmanage.c660000013='"+workflowtype+"'");      
		sql.append(")");
		sql.append(" order by c630000017");
		
		logger.info("根据组查询用户的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String userid     	= Function.nullString(rs.getString("c630000001"));
	 	    	String username1	= Function.nullString(rs.getString("c630000003"));
	 	    	String mobile		= Function.nullString(rs.getString("c630000008"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	System.out.println("用户ID："+userid+",用户名称："+username1);
	 	    	
	 	    	if(!userid.equals("")&&!username1.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("1");
	 	    		elementinfo.setElementid(userid);
	 	    		elementinfo.setElementname(username1);
	 	    		elementinfo.setMobile(mobile);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		returnList.add(elementinfo);
	 	    	}
	 	    	
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 根据组ID与资源名，技能动作值，查询出对此资源有权限的组成员List
	 * 日期 2007-1-13
	 * @author wangyanguang
	 * @param parentid		组ID
	 * @param sourcename	资源名称
	 */
	public List getUserElement(String parentid,String sourcename,String actionvalue,String workflowtype)
	{
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		if(workflowtype.equals(""))
		{
			workflowtype = "0";
		}
		
//		sql.append("select distinct c1,c630000001,c630000003,c630000017 from (");
		sql.append("select distinct usertable.c1,usertable.c630000001,usertable.c630000003,usertable.c630000017,usertable.c630000008,grouptable.c630000019");
		sql.append(" from "+sysskill+"  skilltable,");
		sql.append(groupusertablename+" groupusertable,");
		sql.append(grandactiontable +" grandactiontable,");
		sql.append(usertablename + " usertable,");
		sql.append(sourceconfig +" sourcetable,");
		sql.append(grouptablename +" grouptable");
		sql.append(" where groupusertable.c620000027 ='"+parentid+"'");
		sql.append(" and   grouptable.c1='"+parentid+"'");
		sql.append(" and   usertable.c630000012='0'");
		sql.append(" and   skilltable.c610000007     = groupusertable.c620000028 ");
		sql.append(" and   groupusertable.c620000028 = usertable.c1");
		sql.append(" and   skilltable.c610000010 = grandactiontable.c620000034");
		sql.append(" and   skilltable.c610000008 = grandactiontable.c620000032");
		sql.append(" and   skilltable.c610000008 = sourcetable.source_id");
		sql.append(" and   sourcetable.source_name='"+sourcename+"'");
		sql.append(" and   skilltable.c610000018 = '0'");
		sql.append(" and   skilltable.c610000019 = '"+workflowtype+"'");
		sql.append(" and   (skilltable.c610000015 > "+(System.currentTimeMillis()/1000)+" or skilltable.c610000014 is null)");
		
		if(!actionvalue.equals(""))
		{
			String tmpsql[] = actionvalue.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "skilltable.c610000010='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or skilltable.c610000010='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			
			sql.append(sql1);
		}
		
		sql.append(" union ");
		sql.append(" select distinct usertable.c1, usertable.c630000001, usertable.c630000003,usertable.c630000017,usertable.c630000008,grouptable.c630000019 from ");
		sql.append(  roleskillmanagetable+" roleskillmanage,");  
		sql.append(  usergrouprel+"  usergrouprel,");
		sql.append(  rolemanagetable + " rolemanagetable,");
		sql.append(  groupusertablename+" groupusertable,");
		sql.append(  grandactiontable +" grandactiontable,");
		sql.append(  usertablename + " usertable,");
		sql.append(" sourceconfig  sourcetable,");
		sql.append(  grouptablename + " grouptable");
		sql.append(" where groupusertable.c620000027 = '"+parentid+"'");
		sql.append(" and grouptable.c1='"+parentid+"'");
		sql.append(" and usertable.c630000012='0'");
		sql.append(" and groupusertable.c620000028 = usertable.c1");
		sql.append(" and ((usergrouprel.c660000026 = groupusertable.c620000028 and");
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027) or (usergrouprel.c660000026 is null and");        
		sql.append(" usergrouprel.c660000027 = groupusertable.c620000027))");
		sql.append(" and rolemanagetable.c1=usergrouprel.c660000028");	
		sql.append(" and roleskillmanage.c660000006=usergrouprel.c660000028 and roleskillmanage.c660000007=grandactiontable.c620000032");       
		sql.append(" and roleskillmanage.c660000009=grandactiontable.c620000034  and roleskillmanage.c660000007=sourcetable.source_id");
		sql.append(" and sourcetable.source_name = '"+sourcename+"'");
		sql.append(" and roleskillmanage.c660000013='"+workflowtype+"'"); 
//		sql.append(")");
		
		
		
		if(!actionvalue.equals(""))
		{
			String tmpsql[] = actionvalue.split(",");
			String sql1 = " and (";
			sql1 = sql1 + "roleskillmanage.c660000009='"+(tmpsql[0])+"'";
			for(int i=1;i<tmpsql.length;i++)
			{
				sql1 = sql1 + " or roleskillmanage.c660000009='"+(tmpsql[i])+"'";
			}
			sql1 = sql1 + ")";
			
			sql.append(sql1);
		}
		
		sql.append(" order by c630000017");
		
		logger.info("根据组查询用户的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String userid     	= Function.nullString(rs.getString("c630000001"));
	 	    	String username1	= Function.nullString(rs.getString("c630000003"));
	 	    	String mobile		= Function.nullString(rs.getString("c630000008"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	logger.info("用户ID："+userid+",用户名称："+username1);
	 	    	
	 	    	if(!userid.equals("")&&!username1.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("1");
	 	    		elementinfo.setElementid(userid);
	 	    		elementinfo.setElementname(username1);
	 	    		elementinfo.setMobile(mobile);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 根据参数查询自定义派发表，得到自定义派发信息。
	 * 日期 2007-1-19
	 * @author wangyanguang
	 * @param sendscopepram
	 */
	public List getCustomerSendScopeInfo(SendScopePram sendscopepram)
	{
		String username 	= sendscopepram.getUserLoginName();
		String sourcename	= sendscopepram.getSourceName();
		String sendscopetype = sendscopepram.getSendscopetype();
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		sql.append("select distinct usersendscopetable.groupnameid, namemagtable.groupname from  ");
		sql.append(usersendscope+" usersendscopetable,"+usersendscopenamemag+" namemagtable, ");
		sql.append(usertablename+" usertable,"+sourceconfig+" sourcetable");
		sql.append(" where usertable.c630000012='0' and usersendscopetable.userid = usertable.c1");
		sql.append(" and usertable.c630000001='"+username+"'");
		sql.append(" and usersendscopetable.sourceid=sourcetable.source_id");
		sql.append(" and sourcetable.source_name='"+sourcename+"'");
		sql.append(" and usersendscopetable.sendscopetype='"+sendscopetype+"'");
		sql.append(" and usersendscopetable.groupnameid=namemagtable.id");
		
		
		logger.info("根据用户与资源查询用户能派发的组的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String groupid     	= Function.nullString(rs.getString("groupnameid"));
	 	    	String groupname	= Function.nullString(rs.getString("groupname"));
	 	    	
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 根据自定义派发组名称ID查询此ID对应的派发组信息。
	 * 日期 2007-1-19
	 * @author wangyanguang
	 * @param sendscopepram
	 */
	public List getCustomerGroupInfo(SendScopePram sendscopepram)
	{
		String username 	= sendscopepram.getUserLoginName();
		String sourcename	= sendscopepram.getSourceName();
		String sendscopetype = sendscopepram.getSendscopetype();
		String groupnameid = sendscopepram.getNodeParentid();
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		sql.append("select distinct grouptable.c1, grouptable.c630000018,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022 from  ");
		sql.append(usersendscope+" usersendscopetable,"+grouptablename+" grouptable, ");
		sql.append(usertablename+" usertable,"+sourceconfig+" sourcetable");
		sql.append(" where usertable.c630000012='0' and usersendscopetable.userid = usertable.c1");
		sql.append(" and usertable.c630000001='"+username+"'");
		sql.append(" and usersendscopetable.sourceid=sourcetable.source_id");
		sql.append(" and sourcetable.source_name='"+sourcename+"'");
		sql.append(" and usersendscopetable.sendscopetype='"+sendscopetype+"'");
		sql.append(" and usersendscopetable.groupnameid='"+groupnameid+"'");
		sql.append(" and usersendscopetable.groupid=grouptable.c1");
		sql.append(" and grouptable.c630000025='0'");
		sql.append(" order by grouptable.c630000022 ");
		logger.info("根据用户与资源查询用户能派发的组的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	String groupid     	= Function.nullString(rs.getString("C630000030"));
	 	    	//String groupid     	= Function.nullString(rs.getString("C1"));
	 	    	String groupname	= Function.nullString(rs.getString("C630000018"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	/**
	 * 根据用户登陆名与资源名查询出此用户所能派发的组（组的顶级节点）
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param username
	 * @param sourcename
	 */
	public List getNoGrandParentElement(SendScopePram sendscopepram)
	{
		String username 	= sendscopepram.getUserLoginName();
		String sourcename	= sendscopepram.getSourceName();
		String sendscopetype = sendscopepram.getSendscopetype();
		List returnList 	= new ArrayList();
		StringBuffer sql 	= new StringBuffer();
		
		//////////////
		sql.append(" select  grouptable.c1,grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022" +
				" from "+ grouptablename +" grouptable" +
						" where 1=1" +
						" and grouptable.c630000020='0'" +
						" and grouptable.c630000025='0'" +
						" order by c630000022");
				   
		logger.info("根据用户与资源查询用户能派发的组的查询语句："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			while(rs.next())
			{
	 	    	//String groupid     	= Function.nullString(rs.getString("c1"));
	 	    	String groupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	//String recordid		= Function.nullString(rs.getString("sendscopeid"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!groupid.equals("")&&!groupname.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(groupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		//elementinfo.setRecordid(recordid);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	
	
	/**
	 * 根据父组ID查询资源配置表、组表、派发表查询出子组信息。
	 * 日期 2007-1-12
	 * @author wangyanguang
	 * @param parentid       	父组ID
	 * @param username			用户名
	 * @param sourcename		资源名
	 */
	public List getNoGrandChildElement(SendScopePram sendscopepram)
	{
		List returnList 		= new ArrayList();
		StringBuffer sql 		= new StringBuffer();
		StringBuffer usersql 	= new StringBuffer();
		
		String groupid 	  	= Function.nullString(sendscopepram.getNodeParentid());
		groupid=Function.getStrZero(new Long(groupid));
		
//		sql.append(" select distinct grouptable.c1, grouptable.c630000018,grouptable.c630000020,grouptable.c630000030,grouptable.c630000019,grouptable.c630000022 " +
//				   " from "+grouptablename+" grouptable" +
//				   " where 1=1" +
//				   " and grouptable.c630000025='0'" +
//				   " and grouptable.c630000020='"+groupid+"'");
//		
//		sql.append("  order by c630000022 ");
		//PGC 通过关联groupuser确定组下是否有用户存在，有则setHasuser，保证派发树有复选框
		sql.append(" select distinct grouptable.c1," +
				   " min(grouptable.c630000018) c630000018," +
			       " min(grouptable.c630000020) c630000020," +
			       " min(grouptable.c630000030) c630000030," +
			       " min(grouptable.c630000019) c630000019," +
			       " min(grouptable.c630000022) c630000022," +
			       " nvl(min(groupuser.c620000028),0) c620000028 " +
				   "	from "+grouptablename+" grouptable , "+groupusertablename+" groupuser" +
				   "	where 1 = 1" +
				   "	and grouptable.c630000025 = '0'" +
				   "	and grouptable.c630000020 = '"+groupid+"'" +
				   "	and grouptable.c1 = groupuser.c620000027(+)" +
				   "	group by grouptable.c1" +
				   "	order by min(grouptable.c630000022)") ;
		
		
		logger.info("根据组查询子组的查询语句："+sql.toString());
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		try
		{
			String tmpgroupid		= "";
			while(rs.next())
			{
				tmpgroupid     	= Function.nullString(rs.getString("c630000030"));
	 	    	//String groupid     	= Function.nullString(rs.getString("c1"));
	 	    	String groupname	= Function.nullString(rs.getString("c630000018"));
	 	    	//String recordid     = Function.nullString(rs.getString("sendscopeid"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	logger.info("组ID："+groupid+",组名称："+groupname);
	 	    	
	 	    	if(!String.valueOf(tmpgroupid).equals("")&&!String.valueOf(tmpgroupid).equals("null")&&!String.valueOf(groupname).equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("0");
	 	    		elementinfo.setElementid(tmpgroupid);
	 	    		elementinfo.setElementname(groupname);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		if(!"0".equals(Function.nullString(rs.getString("c620000028")))){
	 	    			elementinfo.setHasuser("true");
	 	    		}
	 	    		//elementinfo.setRecordid(recordid);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			usersql.append("select distinct usertable.c1,usertable.c630000001,usertable.c630000003,usertable.c630000017,usertable.c630000008,grouptable.c630000019,usertable.c1 as userId " +
						   " from "+usertablename+" usertable" +
						   ","+groupusertablename+" groupusertable" +
						   ","+grouptablename+" grouptable" +
						   " where 1=1" +
						   " and usertable.c630000012='0' and groupusertable.c620000027='"+groupid+"'" +
						   " and grouptable.c1=groupusertable.c620000027" +
						   " and groupusertable.c620000028=usertable.c1" +
						   " order by usertable.c630000017");
			logger.info("根据组查询用户的查询语句："+usersql.toString());
			rs		= dataBase.executeResultSet(stm,usersql.toString());
			while(rs.next())
			{
	 	    	String userid     	= Function.nullString(rs.getString("c630000001"));
	 	    	String username1	= Function.nullString(rs.getString("c630000003"));
	 	    	String mobile		= Function.nullString(rs.getString("c630000008"));
	 	    	String groupFullName = Function.nullString(rs.getString("c630000019"));
	 	    	String userID = Function.nullString(rs.getString("userId"));
	 	    	logger.info("用户ID："+userid+",用户名称："+username1);
	 	    	
	 	    	if(!userid.equals("")&&!username1.equals(""))
	 	    	{
	 	    		ElementInfoBean elementinfo = new ElementInfoBean();
	 	    		elementinfo.setElementflag("1");
	 	    		elementinfo.setElementid(userid);
	 	    		elementinfo.setElementname(username1);
	 	    		elementinfo.setMobile(mobile);
	 	    		elementinfo.setGroupFullName(groupFullName);
	 	    		elementinfo.setUserid(userID);
	 	    		returnList.add(elementinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}
	}
	public static void main(String args[])
	{
		
		//"UltraProcess:App_Base"
		SendScopeInterfaceSQL sendsql = new SendScopeInterfaceSQL();
		//sendsql.getChildElement("000000000600001","Demo","UltraProcess:App_Base","0");
		sendsql.getUserElement("000000000600001","UltraProcess:App_Base","1000,1","0");
	}
}
