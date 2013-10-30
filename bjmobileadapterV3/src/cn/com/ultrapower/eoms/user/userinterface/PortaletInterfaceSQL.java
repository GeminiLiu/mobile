package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.PortaletInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.PortaletSourcePram;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAttributeInfo;

public class PortaletInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(PortaletInterfaceSQL.class);
	
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
	public PortaletInterfaceSQL()
	{
		GetFormTableName getTableProperty	= new GetFormTableName();
		try
		{
			sourcemanager		= getTableProperty.GetFormName("RemedyTsourceManager");
			systemmanage		= getTableProperty.GetFormName("systemmanage");
			sourceconfig		= getTableProperty.GetFormName("sourceconfig");
			dutyorgnazition		= getTableProperty.GetFormName("dutyorgnazition");
			orgnazitionarranger	= getTableProperty.GetFormName("orgnazitionarranger");
			usertablename		= getTableProperty.GetFormName("RemedyTpeople");
			grouptablename		= getTableProperty.GetFormName("RemedyTgroup");
			groupusertablename	= getTableProperty.GetFormName("RemedyTgroupuser");
			sysskill			= getTableProperty.GetFormName("RemedyTrole");
			skillaction			= getTableProperty.GetFormName("managergrandaction");
			usergrouprel		= getTableProperty.GetFormName("RemedyTrolesusergrouprel");
			sendscopetable		= getTableProperty.GetFormName("RemedyTsendscope");
			grandactiontable	= getTableProperty.GetFormName("RemedyTgrandaction");
			usersendscope		= getTableProperty.GetFormName("usersendscope");
			usersendscopenamemag = getTableProperty.GetFormName("usersendscopenamemag");
			roleskillmanagetable = getTableProperty.GetFormName("RemedyTrolesskillmanage");
			remedyserver	 	 =getTableProperty.GetFormName("driverurl");
			rolemanagetable		 = getTableProperty.GetFormName("RemedyTrolesmanage");
		}
		catch(Exception e)
		{
			logger.error("从配置表中读取数据表名时出现异常！");
		}
	}
	public List getSourceAttributeInfo(PortaletSourcePram sourcepram,String userLoginName)
	{
		List returnList 	= new ArrayList();
		String sourceid 	= "";
		String sourcename 	= "";
		if(sourcepram==null)
		{
			return null;
		}
		else
		{
			sourceid 	= Function.nullString(sourcepram.getSourceid());
			sourcename 	= Function.nullString(sourcepram.getSourcename());
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct sourcetable.source_id,");
		sql.append(" atttable.sourceatt_cnname,");
		sql.append(" sourcetable.source_url");
		sql.append(" from sourceconfig sourcetable,");
		sql.append(" sourceconfigattribute atttable,");
		sql.append(  sysskill+"  skilltable," );
		sql.append(  usertablename+"  usertable,");
		if(!sourcename.equals("")&&sourceid.equals(""))
		{
			sql.append(" sourceconfig sourcetable2,");
		}
		sql.append(" portalet_subscibe porttable");
		sql.append(" where sourcetable.source_id = atttable.sourceatt_sourceid");
	    sql.append(" and sourcetable.source_id=porttable.portalet_contentsourceid");
	    sql.append(" and usertable.c630000001='"+userLoginName+"'");
	    sql.append(" and usertable.c1=skilltable.c610000007");
	    sql.append(" and skilltable.c610000008=sourcetable.source_id");
		sql.append(" and sourcetable.source_url is not null");
		sql.append(" and sourcetable.source_isleft = '0'");
		
		if(!sourcename.equals("")&&sourceid.equals(""))
		{
			sql.append(" and porttable.portalet_portalsourceid=sourcetable2.source_id");
			sql.append(" and sourcetable2.source_name='"+sourcename+"'");
		}
		else
		{
			sql.append(" and porttable.portalet_portalsourceid='"+sourceid+"'");
		}
	    
	    logger.info("根据资源ID查询资源属性及属性值SQL："+sql.toString());
	    
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String source_id    = Function.nullString(rs.getString("source_id"));
	 	    	String attname		= Function.nullString(rs.getString("sourceatt_cnname"));
	 	    	String attvalue		= Function.nullString(rs.getString("source_url"));
	 	    	attvalue			= attvalue.replaceAll("remedyserver",remedyserver);
	 	    	logger.info("资源ID："+source_id+",资源名称："+attname+",url:"+attvalue);
 	    	
	 	    	if(!source_id.equals("")&&!attname.equals(""))
	 	    	{
	 	    		SourceAttributeInfo sourceinfo = new SourceAttributeInfo();
	 	    		sourceinfo.setAttvalue(attvalue);
	 	    		sourceinfo.setSourceattname(attname);
	 	    		sourceinfo.setSourceid(source_id);
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
	
	public LinkedHashMap getPortaletSQl(String userLoginName)
	{
	LinkedHashMap hm = new LinkedHashMap();
	StringBuffer sql = new StringBuffer();
	sql.append(" select distinct"); 
	sql.append(" sc1_source_name,");
	sql.append(" sc1_source_cnname,");
	sql.append(" sc1_source_orderby,");
	sql.append(" sc2_source_id,");
	sql.append(" sc2_source_cnname,");
	sql.append(" sc2_source_name,");
	sql.append(" sc2_source_url,"); 
	sql.append(" sc2_source_module,"); 
	sql.append(" note");
	    sql.append(" from (select");//1.用户与资源权限直接关联 
	sql.append(" sc1_source_name,");
	sql.append(" sc1_source_cnname,");
	sql.append(" sc1_source_orderby,");
	sql.append(" sc2_source_id,");
	sql.append(" sc2_source_cnname,");
	sql.append(" sc2_source_name,");
	sql.append(" sc2_source_url,"); 
	sql.append(" sc2_source_module,"); 
	sql.append(" note");
	    sql.append(" from (select distinct");//portalet_portalsourceid字段判断
	sql.append(" sourcetable.source_name     sc1_source_name,");
	sql.append(" sourcetable.source_cnname   sc1_source_cnname,");
	sql.append(" sourcetable.source_orderby  sc1_source_orderby,"); 
	sql.append(" ps.note                     note,");
	sql.append(" ps.portalet_id");
	sql.append(" from portalet_subscibe ps,");
	sql.append(" sourceconfig sourcetable,");
	sql.append(" "+sysskill+" skilltable,");
	sql.append(" "+usertablename+" usertable");
	sql.append(" where ps.portalet_portalsourceid = sourcetable.source_id");
	sql.append(" and sourcetable.source_isleft = '0'");
	sql.append(" and sourcetable.source_type like '%10;%'");
	sql.append(" and sourcetable.source_id = skilltable.c610000008");
	sql.append(" and skilltable.c610000007 = usertable.c1");
	sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
	sql.append(" ) uu1 inner join");
	sql.append(" (select distinct");//portalet_contentsourceid字段判断
	sql.append(" sourcetable2.source_id      sc2_source_id,");
	sql.append(" sourcetable2.source_cnname  sc2_source_cnname,");
	sql.append(" sourcetable2.source_name    sc2_source_name,");
	sql.append(" sourcetable2.source_url     sc2_source_url,");
	sql.append(" sourcetable2.source_module  sc2_source_module,");
	sql.append(" ps.portalet_id");
	sql.append(" from portalet_subscibe ps,");
	sql.append(" sourceconfig sourcetable2,");
	sql.append(" "+sysskill+" skilltable,");
	sql.append(" "+usertablename+" usertable");
	sql.append(" where ps.portalet_contentsourceid = sourcetable2.source_id");
	sql.append(" and sourcetable2.source_isleft = '0'");
	sql.append(" and sourcetable2.source_id = skilltable.c610000008");
	sql.append(" and skilltable.c610000007 = usertable.c1");
	sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
	sql.append(" ) uu2 on uu1.portalet_id = uu2.portalet_id");
	sql.append(" union all");
	sql.append(" select");//2.用户与组，角色权限关联 
	sql.append(" sc1_source_name,");
	sql.append(" sc1_source_cnname,");
	sql.append(" sc1_source_orderby,");
	sql.append(" sc2_source_id,");
	sql.append(" sc2_source_cnname,");
	sql.append(" sc2_source_name,");
	sql.append(" sc2_source_url,"); 
	sql.append(" sc2_source_module,"); 
	sql.append(" note");
	sql.append(" from (select distinct");//portalet_portalsourceid字段判断
	sql.append(" sourcetable.source_name     sc1_source_name,");
	sql.append(" sourcetable.source_cnname   sc1_source_cnname,");
	sql.append(" sourcetable.source_orderby  sc1_source_orderby,"); 
	sql.append(" ps.note                     note,");
	sql.append(" ps.portalet_id");
	sql.append(" from portalet_subscibe ps,");
	sql.append(" sourceconfig sourcetable,");
	sql.append(" "+usertablename + " usertable,");
	sql.append(" "+roleskillmanagetable +" roleskillmanage,");
	sql.append(" "+usergrouprel+" rolegroupuserrel,");
	sql.append(" "+groupusertablename+" groupuser");
	sql.append(" where ps.portalet_portalsourceid = sourcetable.source_id");
	sql.append(" and sourcetable.source_isleft = '0'");
	sql.append(" and sourcetable.source_type like '%10;%'"); 
	sql.append(" and sourcetable.source_id = roleskillmanage.c660000007");
	sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");
	sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");
	sql.append(" (rolegroupuserrel.c660000026 is null and");
	sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027))");
	sql.append(" and groupuser.c620000028 = usertable.c1"); 
	sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
	sql.append(" ) uu1 inner join");
	sql.append(" (select distinct");//portalet_contentsourceid字段判断
	sql.append(" sourcetable2.source_id      sc2_source_id,");
	sql.append(" sourcetable2.source_cnname  sc2_source_cnname,");
	sql.append(" sourcetable2.source_name    sc2_source_name,");
	sql.append(" sourcetable2.source_url     sc2_source_url,");
	sql.append(" sourcetable2.source_module  sc2_source_module,");
	sql.append(" ps.portalet_id");
	sql.append(" from portalet_subscibe ps,");
	sql.append(" sourceconfig sourcetable2,");
	sql.append(" "+usertablename + " usertable,");
	sql.append(" "+roleskillmanagetable +" roleskillmanage,");
	sql.append(" "+usergrouprel+" rolegroupuserrel,");
	sql.append(" "+groupusertablename+" groupuser");
	sql.append(" where ps.portalet_contentsourceid = sourcetable2.source_id");
	sql.append(" and sourcetable2.source_isleft = '0'"); 
	sql.append(" and sourcetable2.source_id = roleskillmanage.c660000007");
	sql.append(" and roleskillmanage.c660000006 = rolegroupuserrel.c660000028");
	sql.append(" and (rolegroupuserrel.c660000026 = groupuser.c620000028 or");
	sql.append(" (rolegroupuserrel.c660000026 is null and");
	sql.append(" rolegroupuserrel.c660000027 = groupuser.c620000027))");
	sql.append(" and groupuser.c620000028 = usertable.c1"); 
	sql.append(" and usertable.c630000001 = '"+userLoginName+"'");
	sql.append(" ) uu2 on uu1.portalet_id = uu2.portalet_id");
	sql.append(" ) ");
	sql.append(" order by sc1_source_orderby");
	    logger.info("根据用户登陆名查询portalet的SQL："+sql.toString());
	    
	IDataBase dataBase = null;
	dataBase = DataBaseFactory.createDataBaseClassFromProp();
	Statement stm = dataBase.GetStatement();
	ResultSet rs = dataBase.executeResultSet(stm,sql.toString());
	try
	{
	while(rs.next())
	{
	       String source1name    = Function.nullString(rs.getString("sc1_source_name"));
	       String source1cnname    = Function.nullString(rs.getString("sc1_source_cnname"));
	       String source2id = Function.nullString(rs.getString("sc2_source_id"));
	       String source2cnname = Function.nullString(rs.getString("sc2_source_cnname"));
	       String source2name    = Function.nullString(rs.getString("sc2_source_name"));
	       String source2value = Function.nullString(rs.getString("sc2_source_url"));
	       source2value = source2value.replaceAll("remedyserver",remedyserver);
	       String source2module = Function.nullString(rs.getString("sc2_source_module"));
	       String note = Function.nullString(rs.getString("note"));
	       logger.info("Key值："+source1name+",Value的英文名称："+source2name+",Valueurl:"+source2value);
	       System.out.println("Key值："+source1name+",Value的英文名称："+source2name+",Valueurl:"+source2value);
	       logger.info(",Value的ID："+source2id+",Value中文名："+source2cnname);
	       System.out.println(",Value的ID："+source2id+",Value中文名："+source2cnname);
	       logger.info("");
	       PortaletInfo portaletbean = new PortaletInfo();
	       portaletbean.setSourcecnname(source2cnname);
	       portaletbean.setSourceid(source2id);
	       portaletbean.setSourcename(source2name);
	       portaletbean.setUrlvalue(source2value);
	       portaletbean.setModuleid(source2module);
	       portaletbean.setNote(note);
	       portaletbean.setSource1cnname(source1cnname);
	       
	       List hmkey = (List)hm.get(source1name);
	       if(hmkey==null)
	       {
	       hmkey = new ArrayList();
	       hmkey.add(portaletbean);
	       hm.put(source1name,hmkey);
	       
	       }else
	       {
	       hmkey.add(portaletbean);
	       hm.put(source1name,hmkey);
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
	return hm;
	}


	
	
}
