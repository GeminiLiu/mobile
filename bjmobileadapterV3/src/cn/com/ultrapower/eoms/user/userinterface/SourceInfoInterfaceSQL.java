package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceElementInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceInfoPram;

public class SourceInfoInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(SourceInfoInterfaceSQL.class);
	
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
	private String roleskillmanagetable		= "";
	private String remedyserver				= "";
	private String rolemanagetable			= "";
	/**
	 * 取得配制信息，读取配制文件
	 */
	public SourceInfoInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager			= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage			= getTableProperty.GetFormName("systemmanage");
			sourceconfig			= getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition			= getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger		= getTableProperty.GetFormName("orgnazitionarranger");
			usertablename			= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename			= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename		= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill				= getTableProperty.GetFormName("RemedyTrole");
			skillaction				= getTableProperty.GetFormName("managergrandaction");
			usergrouprel			= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			sendscopetable			= getTableProperty.GetFormName("RemedyTsendscope");
			grandactiontable		= getTableProperty.GetFormName("RemedyTgrandaction");
			usersendscope			= getTableProperty.GetFormName("usersendscope");
			usersendscopenamemag 	= getTableProperty.GetFormName("usersendscopenamemag");
			roleskillmanagetable	= getTableProperty.GetFormName("RemedyTrolesskillmanage");
			remedyserver			= getTableProperty.GetFormName("driverurl");
			rolemanagetable		 	= getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	
	/**
	 * 根据参数查询出顶级资源信息。
	 * 日期 2007-2-27
	 * @author wangyanguang
	 */
	public List getRootElementList(SourceInfoPram sourcepram)
	{
		List returnList 		= new ArrayList();
		String userLoginName 	= "";
		String grandValue 		= "";
		String moduleName		= "";
		String strwhere			= "";
		String strwhere1		= "";
		String rolemanagestr	= "";
		String rolemanagestr2	= "";
		userLoginName 			= Function.nullString(sourcepram.getUserLoginName());
		grandValue 				= Function.nullString(sourcepram.getGrandValue());
		moduleName				= Function.nullString(sourcepram.getMoudleName());
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				strwhere = strwhere + " and (sysskill.c610000010='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				strwhere = strwhere + " or sysskill.c610000010='"+grandstr[i]+"'";
			}
			strwhere = strwhere + ")";
		}
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				strwhere1 = strwhere1 + " and (sysskill2.c610000010='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				
				strwhere1 = strwhere1 + " or sysskill2.c610000010='"+grandstr[i]+"'";
			}
			strwhere1 = strwhere1 + ")";
		}
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				rolemanagestr = rolemanagestr + " and (roleskillmanage.c660000009='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				rolemanagestr = rolemanagestr + " or roleskillmanage.c660000009='"+grandstr[i]+"'";
			}
			rolemanagestr = rolemanagestr + ")";
		}
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				rolemanagestr2 = rolemanagestr2 + " and (roleskillmanage2.c660000009='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				
				rolemanagestr2 = rolemanagestr2 + " or roleskillmanage2.c660000009='"+grandstr[i]+"'";
			}
			rolemanagestr2 = rolemanagestr2 + ")";
		}
		StringBuffer sql 		= new StringBuffer();

		sql.append(" select distinct source_id,source_parentid,source_cnname,source_url,source_orderby,source_module,c1");	
		sql.append(" from (select  sourceconfig1.source_id,sourceconfig1.source_parentid,sourceconfig1.source_cnname,");
		sql.append(" sourceconfig1.source_url,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1");  
		sql.append(" from sourceconfig sourceconfig1,");
		
		sql.append(" sourceconfig sourceconfig2,");
		
		sql.append(  usertablename+" usertable,");
		sql.append(  sysskill+" sysskill");
		sql.append(" where sourceconfig1.source_id = sysskill.c610000008  and sysskill.C610000007 = usertable.c1");
		sql.append(" and usertable.c630000001 = '"+userLoginName+"' and sysskill.C610000018 = '0'");
		
		sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
		
		sql.append(  strwhere);
		sql.append(" and sourceconfig1.source_type like '%0;%' and sourceconfig1.source_url is not null"); 
		sql.append(" and sourceconfig1.source_isleft = '0'");
		sql.append(" and sourceconfig2.source_isleft = '0'");
		sql.append(" union ");           
		sql.append(" select  sourceconfig1.source_id,sourceconfig1.source_parentid,sourceconfig1.source_cnname,");         
		sql.append(" sourceconfig1.source_url,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1 ");        
		sql.append(" from sourceconfig          sourceconfig1,"); 
		
		sql.append(" sourceconfig sourceconfig2,");
		
        sql.append(  usertablename+" usertable,");
        sql.append(  roleskillmanagetable +" roleskillmanage,");
        sql.append(  usergrouprel+" rolegroupuserrel,");
        sql.append(  rolemanagetable + " rolemanagetable,");

        sql.append(  groupusertablename+" groupuser");      
		sql.append(" where sourceconfig1.source_id = roleskillmanage.c660000007");                         
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");  
		
		sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
		sql.append(" and sourceconfig1.source_parentid=sourceconfig2.source_id");
		
		sql.append(  rolemanagestr);
		sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
		//新加的权限控制
		sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
//        sql.append(" and rolegroupuserrel.c660000027=groupuser.c620000027");
        sql.append(" and groupuser.c620000028=usertable.c1");
        sql.append(" and sourceconfig1.source_type like '%0;%'");
        sql.append(" and sourceconfig1.source_isleft = '0'");
        sql.append(" and sourceconfig2.source_isleft = '0'");
        sql.append(" and sourceconfig1.source_url is not null) uu ");         
		sql.append("  where 1 = 1 and not exists (");           
//		sql.append(" select distinct source_id,source_parentid,source_cnname,sourceattvalue_value,source_orderby,source_module,c1");	
		sql.append(" select  sourceconfig1.source_id ");
//		sql.append(" attvalue.sourceattvalue_value,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1");  
		sql.append(" from sourceconfig          sourceconfig1,"); 
		
		sql.append(" sourceconfig sourceconfig2,");
		
		sql.append(  usertablename+" usertable,");                     
		sql.append(  sysskill+" sysskill");                   
		sql.append(" where sourceconfig1.source_id = sysskill.c610000008  and sysskill.C610000007 = usertable.c1");                        
		sql.append(" and usertable.c630000001 = '"+userLoginName+"' and sysskill.C610000018 = '0'");  
		
		sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
		
		sql.append(strwhere);
		sql.append(" and sourceconfig1.source_type like '%0;%' and sourceconfig1.source_url is not null");
		sql.append(" and sourceconfig1.source_isleft = '0'");
		sql.append(" and sourceconfig2.source_isleft = '0'");
		sql.append("  and uu.source_parentid = sourceconfig1.source_id)");
		sql.append("  and not exists(");           
		sql.append(" select  sourceconfig1.source_id");         
//		sql.append(" attvalue.sourceattvalue_value,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1 ");        
		sql.append(" from sourceconfig          sourceconfig1,"); 
		
		sql.append(" sourceconfig sourceconfig2,");
		
        sql.append(  usertablename+" usertable,");
        sql.append(  roleskillmanagetable +" roleskillmanage,");
        sql.append(  usergrouprel+" rolegroupuserrel,");
        sql.append(  rolemanagetable + " rolemanagetable,");

        sql.append(  groupusertablename+" groupuser");        
		sql.append(" where sourceconfig1.source_id = roleskillmanage.c660000007");                         
		sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028"); 
		
		
		sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
		
		sql.append(rolemanagestr);
		sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
		//新加的权限控制
		sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
//        sql.append(" and rolegroupuserrel.c660000027=groupuser.c620000027");
        sql.append(" and groupuser.c620000028=usertable.c1");
        sql.append(" and sourceconfig1.source_type like '%0;%'");
        sql.append(" and sourceconfig1.source_isleft = '0'");
        sql.append(" and sourceconfig2.source_isleft = '0'");
        sql.append(" and sourceconfig1.source_url is not null and uu.source_parentid = sourceconfig1.source_id) ");
		sql.append(" order by   source_orderby ");
		
		logger.info("根据参数Bean信息查询顶级节点的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String sourceid     = Function.nullString(rs.getString("source_id"));
	 	    	String sourcename	= Function.nullString(rs.getString("source_cnname"));
	 	    	String urlvalue		= Function.nullString(rs.getString("source_url"));
	 	    	urlvalue			= urlvalue.replaceAll("remedyserver",remedyserver);
	 	    	String orderbyvalue = Function.nullString(rs.getString("source_orderby"));
	 	    	String moduleid		= Function.nullString(rs.getString("source_module"));
	 	    	String userid		= Function.nullString(rs.getString("c1"));
	 	    	logger.info("资源ID："+sourceid+",资源名称："+sourcename+",ulr:"+urlvalue+",排序值："+orderbyvalue+",模块ID："+moduleid+",用户ID："+userid+",用户登陆名："+userLoginName);
 	    	
	 	    	if(!sourceid.equals("")&&!sourcename.equals(""))
	 	    	{
	 	    		SourceElementInfo sourceinfo = new SourceElementInfo();
	 	    		sourceinfo.setSourceid(sourceid);
	 	    		sourceinfo.setSourcename(sourcename);
	 	    		sourceinfo.setUrlvalue(urlvalue);
	 	    		sourceinfo.setModuleid(moduleid);
	 	    		sourceinfo.setUserid(userid);
	 	    		sourceinfo.setUserLoginName(userLoginName);
	 	    		returnList.add(sourceinfo);
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
		
		return returnList;
		
	}

	/**
	 * 根据父ID查询出下一级节点的资源信息.
	 * 日期 2007-2-27
	 * @author wangyanguang
	 */
	public List getChildElementList(SourceInfoPram sourcepram)
	{
		List returnList 		= new ArrayList();
		String userLoginName 	= "";
		String grandValue 		= "";
//		String moduleName		= "";
		String strwhere			= "";
		String rolemanagestr	= "";
		String sourceparentid   = "";
		userLoginName 			= Function.nullString(sourcepram.getUserLoginName());
		grandValue 				= Function.nullString(sourcepram.getGrandValue());
		sourceparentid			= Function.nullString(sourcepram.getSourceParentid());
//		moduleName				= Function.nullString(sourcepram.getMoudleName());
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				strwhere = strwhere + " and (sysskill.c610000010='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				strwhere = strwhere + " or sysskill.c610000010='"+grandstr[i]+"'";
			}
			strwhere = strwhere + ")";
		}
		if(!grandValue.equals(""))
		{
			String[] grandstr = grandValue.split(";");
			if(grandstr.length>0)
			{
				rolemanagestr = rolemanagestr + " and (roleskillmanage.c660000009='"+grandstr[0]+"'";
			}
			for(int i=1;i<grandstr.length;i++)
			{
				rolemanagestr = rolemanagestr + " or roleskillmanage.c660000009='"+grandstr[i]+"'";
			}
			rolemanagestr = rolemanagestr + ")";
		}
		StringBuffer sql 		= new StringBuffer();
		

		sql.append("select distinct sourceconfig1.source_id,sourceconfig1.source_cnname,sourceconfig1.source_url,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1");
	    sql.append(" from sourceconfig sourceconfig1,");
	    
//	    sql.append(" sourceconfig sourceconfig2,");
	    
	    sql.append(usertablename+" usertable,");
	    sql.append(sysskill+" sysskill");
	    sql.append(" where sourceconfig1.source_id = sysskill.c610000008");
	    sql.append(" and sourceconfig1.source_parentid = '"+sourceparentid+"'");
	    sql.append(" and sysskill.C610000007 = usertable.c1");
	    sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
	    sql.append(" and sysskill.C610000018 = '0'");
	    
//	    sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
//		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
	    
	    sql.append(strwhere);
	    sql.append(" and sourceconfig1.source_type like '%0;%'");
	    sql.append(" and sourceconfig1.source_url is not null");
	    sql.append(" and sourceconfig1.source_isleft = '0'");
	    sql.append(" union");
	    sql.append(" select distinct sourceconfig1.source_id,sourceconfig1.source_cnname,sourceconfig1.source_url,sourceconfig1.source_orderby,sourceconfig1.source_module,usertable.c1");
	    sql.append(" from sourceconfig sourceconfig1,");  
	    
//	    sql.append(" sourceconfig sourceconfig2,");
	    
        sql.append(  usertablename+" usertable,");
        sql.append(  roleskillmanagetable +" roleskillmanage,");
        sql.append(  usergrouprel+" rolegroupuserrel,");
        sql.append(  rolemanagetable + " rolemanagetable,");
        sql.append(  groupusertablename+" groupuser");   
        sql.append(" where sourceconfig1.source_id = roleskillmanage.c660000007");
        sql.append(" and sourceconfig1.source_parentid = '"+sourceparentid+"'");
        sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");
        
//      sql.append(" and sourceconfig2.source_name='"+moduleName+"'");
//		sql.append(" and sourceconfig1.source_module=sourceconfig2.source_id");
        
        sql.append(rolemanagestr);
        sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
		//新加的权限控制
		sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");		       
		sql.append(" (rolegroupuserrel.c660000026 is null and rolegroupuserrel.c660000027 = groupuser.c620000027))");
		sql.append(" and rolemanagetable.c1=rolegroupuserrel.c660000028");
//        sql.append(" and rolegroupuserrel.c660000027=groupuser.c620000027");
	    sql.append(" and groupuser.c620000028=usertable.c1");
	    sql.append(" and sourceconfig1.source_type like '%0;%'");
	    sql.append("and sourceconfig1.source_url is not null");
	    sql.append(" and sourceconfig1.source_isleft = '0'");
	    
	    sql.append(" order by source_orderby");
		logger.info("根据参数Bean信息查询顶级节点的SQL："+sql.toString());
		
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String sourceid     = Function.nullString(rs.getString("source_id"));
	 	    	String sourcename	= Function.nullString(rs.getString("source_cnname"));
	 	    	String urlvalue		= Function.nullString(rs.getString("source_url"));
	 	    	String orderbyvalue = Function.nullString(rs.getString("source_orderby"));
	 	    	String moduleid		= Function.nullString(rs.getString("source_module"));
	 	    	String userid		= Function.nullString(rs.getString("c1"));
	 	    	urlvalue			= urlvalue.replaceAll("remedyserver",remedyserver);
	 	    	logger.info("资源ID："+sourceid+",资源名称："+sourcename+",ulr:"+urlvalue+",排序值："+orderbyvalue+",模块ID："+moduleid+",用户ID："+userid+",用户登陆名："+userLoginName);
	 	    	
	 	    	if(!sourceid.equals("")&&!sourcename.equals(""))
	 	    	{
	 	    		SourceElementInfo sourceinfo = new SourceElementInfo();
	 	    		sourceinfo.setSourceid(sourceid);
	 	    		sourceinfo.setSourcename(sourcename);
	 	    		sourceinfo.setUrlvalue(urlvalue);
	 	    		sourceinfo.setModuleid(moduleid);
	 	    		sourceinfo.setUserid(userid);
	 	    		sourceinfo.setUserLoginName(userLoginName);
	 	    		returnList.add(sourceinfo);
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
		return returnList;
		
	}
	
}
