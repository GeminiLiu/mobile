package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
import cn.com.ultrapower.eoms.user.userinterface.SourceConfigInfoBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.PeopleInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SkillSourceInfo;

public class JDBCSourceGrandInterface 
{
	
	static final Logger logger 	= (Logger) Logger.getLogger(JDBCSourceGrandInterface.class);
	
	/**
	 * 根据用户登陆名查询出用户信息Bean
	 * 
	 * 日期 2006-12-12
	 * @author wangyanguang/王彦广 
	 * @param userloginname			用户登陆名
	 * @return SysPeoplepo
	 */
	public PeopleInfo getUserInfo(String userloginname)
	{
		String usertablename 				= "";
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			usertablename 					= getTableProperty.GetFormName("RemedyTpeople");
		}
		catch(Exception e)
		{
			logger.info("JDBCSourceGrandInterface 类中 getUserInfo(String userloginname)方法调用");
		}
		String sql = "";
		sql = sql + " select usertable.C1 userid" +
			",usertable.C630000029 userintid" +
			",usertable.C630000001 userloginname" +
			",usertable.C630000002 userpassword" +
			",usertable.C630000003 userfullname" +
			",usertable.C630000004 usercreateuser" +
			",usertable.C630000005 userposition" +
			",usertable.C630000006 userismanager" +
			",usertable.C630000007 usertype" +
			",usertable.C630000008 usermobie" +
			",usertable.C630000009 userphone" +
			",usertable.C630000010 userfax" +
			",usertable.C630000011 usermail" +
			",usertable.C630000012 userstatus" +
			",usertable.C630000013 usercpid" +
			",usertable.C630000014 usercptype" +
			",usertable.C630000015 userdpid" +
			",usertable.C630000016 userlicensetype" +
			",usertable.C630000036 userbelonggroupid" +
			",usertable.C630000017 userorderby" +
			" from "+usertablename+" usertable" +" where usertable.C630000001='"+userloginname+"'";
		System.out.println("sql:"+sql);
		try
		{
			ResultSet rs = null;
			try
			{
				rs = JDBCSingleton.getConn(sql);
			}
			catch(Exception e)
			{
				logger.info("JDBCSourceGrandInterface 中查询数据出现异常！");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			if(rs!=null)
			{
				JDBCSQLAssociate sqlassociate = new JDBCSQLAssociate();
				PeopleInfo people = sqlassociate.getUserInfoAsso(rs);
				if(people!=null)
				{
					return people;
				}
			}
			rs.close();
			rs = null;
			return null;
		}
		catch(Exception e)
		{
			logger.info("JDBCSourceGrandInterface类中 getUserInfo(String userloginname)" +
					"方法执行查询时出现异常！"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 根据用户ID，资源BEAN信息和条件LIST查询出满足条件的LIST，LIST值为SkillSourceInfo BEAN信息。
	 * 日期 2006-12-12
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userid				用户ID
	 * @param sourceConfigBean		资源Bean信息
	 * @param beanList				条件List
	 * @return List					返回值，List中的内容是**Bean信息
	 */
	public List getSkillAndSourceInfo(String userid,SourceConfigInfoBean sourceConfigBean, List beanList,String commissionType)
	{
		List returnList 					= null;
		String roletablename 				= "";
		String sourcetablename				= "";
		String sourceatttablename 			= "";
		String sourceattvaluetablename 		= "";
		String sql 							= "";
		String sourceid 					= "";
		String parentid 					= "";
		String enname 						= "";
		String cnname 						= "";
		String desc 						= "";
		String type 						= "";
		String orderby 						= "";
		String module 						= "";
		int size 							= 0; 
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			roletablename					= getTableProperty.GetFormName("RemedyTrole");
			sourcetablename					= getTableProperty.GetFormName("sourceconfig");
			sourceatttablename				= getTableProperty.GetFormName("sourceconfigattribute");
			sourceattvaluetablename			= getTableProperty.GetFormName("sourceattributevalue");
		}
		catch(Exception e)
		{
			logger.info("JDBCSourceGrandInterface 类中 " +
					"getSkillAndSourceInfo(String userid,SourceConfigInfoBean sourceConfigBean, List beanList)" +
					"调用GetTableProperty读取配置表信息时出现异常！"+e.getMessage());
		}
		if(beanList!=null)
		{
			size = beanList.size();
		}
		
		if(size==0)
		{	
			sql = "select " +
					" skilltable.C1 skillid" +
					",skilltable.C610000006 skilltype" +
					",skilltable.C610000011 skillgroupid" +
					",skilltable.C610000007 skilluserid" +
					",skilltable.C610000008 skillmodule" +
					",skilltable.C610000009 skillcategoryqueryid" +
					",skilltable.C610000010 skillaction" +
					",skilltable.C610000012 skillcommissiongid" +
					",skilltable.C610000014 skillcommissionuid" +
					",skilltable.C610000015 skillcommissionclosetime" +
					",skilltable.C610000018 skillstatus" +
					",skilltable.C610000019 skillworkflowtype" +
					",sourcetable.SOURCE_ID sourceid " +
					",sourcetable.SOURCE_PARENTID sourceparentid" +
					",sourcetable.SOURCE_CNNAME sourcecnname" +
					",sourcetable.SOURCE_NAME sourcename" +
					",sourcetable.SOURCE_MODULE sourcemodule" +
					",sourcetable.SOURCE_ORDERBY sourceorderby" +
					",sourcetable.SOURCE_DESC sourcedesc" +
					",sourcetable.SOURCE_TYPE sourcetype" +
					",sourceatttable.SOURCEATT_ID sourceattid" +
					",sourceatttable.SOURCEATT_SOURCEID sourceattsourceid" +
					",sourceatttable.SOURCEATT_CNNAME sourceattcnname" +
					",sourceatttable.SOURCEATT_ENNAME sourceattenname" +
					",sourceatttable.SOURCEATT_ORDERBY sourceattordeby" +
					",sourceatttable.SOURCEATT_DESC sourceattdesc" +
					",sourceatttable.SOURCEATT_TYPE sourceatttype" +
					",sourceattvaluetable.SOURCEATTVALUE_ID sourceattvalueid" +
					",sourceattvaluetable.SOURCEATTVALUE_ATTID sourceattvalueattid" +
					",sourceattvaluetable.SOURCEATTVALUE_BELONGROW sourceattvaluebelongrow" +
					",sourceattvaluetable.SOURCEATTVALUE_VALUE sourceattvaluevalue" +
					",sourceattvaluetable.SOURCEATT_TYPE sourceattvaluetype" +
					" from "+ roletablename + " skilltable,"+ sourcetablename +" sourcetable " +
					"," + sourceatttablename + " sourceatttable"+
					"," + sourceattvaluetablename + " sourceattvaluetable"+
					" where 1=1 and skilltable.C610000008=sourcetable.SOURCE_ID" +
					" and sourceatttable.sourceatt_sourceid = sourcetable.SOURCE_ID " +
					" and sourceatttable.sourceatt_id = sourceattvaluetable.SOURCEATTVALUE_ATTID";
					//判断是否查代办人信息。
					if(commissionType.equals("0"))
					{
						sql = sql + " and skilltable.C610000007="+userid;
					}
					else if(commissionType.equals("1"))
					{
						sql = sql + "and ( skilltable.C610000007="+userid+" or skilltable.C610000014="+userid+")";
					}
					else
					{
						sql = sql + " and skilltable.C610000007="+userid;
					}
					//技能表中状态等于启用。
					sql = sql + " and skilltable.C610000018='0'";
			if(sourceConfigBean!=null)
			{
				 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
				 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
				 enname 	= Function.nullString(sourceConfigBean.getSource_name());
				 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
				 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
				 type 		= Function.nullString(sourceConfigBean.getSource_type());
				 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
				 module 	= Function.nullString(sourceConfigBean.getSource_module());
			}
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 	= " and (";
				sql1 			= sql1 + " sourcetable.SOURCE_ID="+tmpsql[0];
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or sourcetable.SOURCE_ID="+tmpsql[i];
				}
				sql1 	= sql1 + ")";
				sql 	= sql + sql1;
			}
			
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and sourcetable.SOURCE_PARENTID="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_NAME='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_CNNAME='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and sourcetable.SOURCE_DESC='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and sourcetable.SOURCE_TYPE like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and sourcetable.SOURCE_ORDERBY="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and sourcetable.SOURCE_MODULE='"+module+"'";
			}
		}else
		{
			sql = "select " +
			" skilltable.C1 skillid" +
			",skilltable.C610000006 skilltype" +
			",skilltable.C610000011 skillgroupid" +
			",skilltable.C610000007 skilluserid" +
			",skilltable.C610000008 skillmodule" +
			",skilltable.C610000009 skillcategoryqueryid" +
			",skilltable.C610000010 skillaction" +
			",skilltable.C610000012 skillcommissiongid" +
			",skilltable.C610000014 skillcommissionuid" +
			",skilltable.C610000015 skillcommissionclosetime" +
			",skilltable.C610000018 skillstatus" +
			",skilltable.C610000019 skillworkflowtype" +
			",sourcetable.SOURCE_ID sourceid " +
			",sourcetable.SOURCE_PARENTID sourceparentid" +
			",sourcetable.SOURCE_CNNAME sourcecnname" +
			",sourcetable.SOURCE_NAME sourcename" +
			",sourcetable.SOURCE_MODULE sourcemodule" +
			",sourcetable.SOURCE_ORDERBY sourceorderby" +
			",sourcetable.SOURCE_DESC sourcedesc" +
			",sourcetable.SOURCE_TYPE sourcetype" +
			",sourceatttable.SOURCEATT_ID sourceattid" +
			",sourceatttable.SOURCEATT_SOURCEID sourceattsourceid" +
			",sourceatttable.SOURCEATT_CNNAME sourceattcnname" +
			",sourceatttable.SOURCEATT_ENNAME sourceattenname" +
			",sourceatttable.SOURCEATT_ORDERBY sourceattordeby" +
			",sourceatttable.SOURCEATT_DESC sourceattdesc" +
			",sourceatttable.SOURCEATT_TYPE sourceatttype" +
			",sourceattvaluetable.SOURCEATTVALUE_ID sourceattvalueid" +
			",sourceattvaluetable.SOURCEATTVALUE_ATTID sourceattvalueattid" +
			",sourceattvaluetable.SOURCEATTVALUE_BELONGROW sourceattvaluebelongrow" +
			",sourceattvaluetable.SOURCEATTVALUE_VALUE sourceattvaluevalue" +
			",sourceattvaluetable.SOURCEATT_TYPE sourceattvaluetype" +
			"  from  "+ roletablename + " skilltable,"+ sourcetablename +" sourcetable";
			
			for(int i=0;i<size;i++)
			{
				sql = sql +" ,"+ sourceatttablename +" sourceatttable"+i;
			}
			
			for(int j =0;j<size;j++)
			{
				sql = sql + " ,"+sourceattvaluetablename+" sourceattvaluetable"+j;
			}
			sql = sql +" where 1=1  and skilltable.C610000008=sourcetable.SOURCE_ID" +
					" and sourceatttable.sourceatt_sourceid = sourcetable.SOURCE_ID " +
					" and sourceatttable.sourceatt_id = sourceattvaluetable.SOURCEATTVALUE_ATTID";
			//判断是否查代办人信息。
			if(commissionType.equals("0"))
			{
				sql = sql + " and skilltable.C610000007="+userid;
			}
			else if(commissionType.equals("1"))
			{
				sql = sql + "and ( skilltable.C610000007="+userid+" or skilltable.C610000014="+userid+")";
			}
			else
			{
				sql = sql + " and skilltable.C610000007="+userid;
			}
			//技能表中状态等于启用。
			sql = sql + " and skilltable.C610000018='0'";
			for(int m=0;m<size;m++)
			{
				sql = sql + " and sourcetable.SOURCE_ID = sourceatttable"+m+".SOURCEATT_SOURCEID ";
			}
			
			for(int n=0;n<size;n++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(n);
				String attname = Function.nullString(sourceAttBean.getsource_attname());
				System.out.println(attname);
				if(!attname.equals(""))
				{
					sql = sql + " and sourceatttable"+n+".SOURCEATT_ENNAME='"+attname+"'";
				}
				
			}
			for(int o=0;o<size;o++)
			{
				sql = sql + " and sourceatttable"+o+".SOURCEATT_ID = sourceattvaluetable"+o+".SOURCEATTVALUE_ATTID";
			}
			for(int p=0;p<size;p++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(p);
				String attvalue = Function.nullString(sourceAttBean.getsource_attnamevalue());
				String operation = Function.nullString(sourceAttBean.getsource_attqueryop());
				if(!operation.equals("")&&!attvalue.equals(""))
				{
					sql = sql + " and sourceattvaluetable"+p+".SOURCEATTVALUE_VALUE "+operation+" '"+attvalue+"'";
				}
			}
			
			 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			 enname 	= Function.nullString(sourceConfigBean.getSource_name());
			 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
			 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
			 type 		= Function.nullString(sourceConfigBean.getSource_type());
			 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			 module 	= Function.nullString(sourceConfigBean.getSource_module());
			
			
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 = " and (";
				sql1 = sql1 + "sourcetable.SOURCE_ID="+(tmpsql[0]);
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or sourcetable.SOURCE_ID="+(tmpsql[i]);
				}
				sql1 = sql1 + ")";
				System.out.println(sql1);
				sql = sql + sql1;
			}
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and sourcetable.SOURCE_PARENTID="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_NAME='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_CNNAME='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and sourcetable.SOURCE_DESC='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and sourcetable.SOURCE_TYPE like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and sourcetable.SOURCE_ORDERBY="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and sourcetable.SOURCE_MODULE='"+module+"'";
			}
			
		}
		System.out.println("sql:"+sql);
		try 
		{
			ResultSet rs = null;
			try
			{
				rs = JDBCSingleton.getConn(sql);
			}
			catch(Exception e)
			{
				logger.info("JDBCSourceGrandInterface 中查询数据出现异常！");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			if(rs!=null)
			{
				JDBCSQLAssociate sqlassociate = new JDBCSQLAssociate();
				returnList = sqlassociate.getSkillAndPropertyList(rs);
				if(returnList!=null)
				{
					return returnList;
				}
			}
			
			rs.close();
			rs = null;
			return null;
		}
		catch (Exception e) 
		{
			logger.info("");
			return null;
		}
	
	}
	
	/**
	 * 根据资源BEAN信息和条件LIST查询出满足条件的LIST，LIST值为SkillSourceInfo BEAN信息。
	 * 日期 2006-12-13
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userid
	 * @param sourceConfigBean
	 * @param beanList
	 * @return List
	 *
	 */
	public List getDirectorySourceInfo(SourceConfigInfoBean sourceConfigBean, List beanList)
	{

		List returnList 					= null;
		String roletablename 				= "";
		String sourcetablename				= "";
		String sourceatttablename 			= "";
		String sourceattvaluetablename 		= "";
		String sql 							= "";
		String sourceid 					= "";
		String parentid 					= "";
		String enname 						= "";
		String cnname 						= "";
		String desc 						= "";
		String type 						= "";
		String orderby 						= "";
		String module 						= "";
		int size 							= 0; 
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			roletablename					= getTableProperty.GetFormName("RemedyTrole");
			sourcetablename					= getTableProperty.GetFormName("sourceconfig");
			sourceatttablename				= getTableProperty.GetFormName("sourceconfigattribute");
			sourceattvaluetablename			= getTableProperty.GetFormName("sourceattributevalue");
		}
		catch(Exception e)
		{
			logger.info("JDBCSourceGrandInterface 类中 " +
					"getSkillAndSourceInfo(String userid,SourceConfigInfoBean sourceConfigBean, List beanList)" +
					"调用GetTableProperty读取配置表信息时出现异常！"+e.getMessage());
		}
		if(beanList!=null)
		{
			size = beanList.size();
		}
		
		if(size==0)
		{	
			sql = "select " +
					",sourcetable.SOURCE_ID sourceid " +
					",sourcetable.SOURCE_PARENTID sourceparentid" +
					",sourcetable.SOURCE_CNNAME sourcecnname" +
					",sourcetable.SOURCE_NAME sourcename" +
					",sourcetable.SOURCE_MODULE sourcemodule" +
					",sourcetable.SOURCE_ORDERBY sourceorderby" +
					",sourcetable.SOURCE_DESC sourcedesc" +
					",sourcetable.SOURCE_TYPE sourcetype" +
					",sourceatttable.SOURCEATT_ID sourceattid" +
					",sourceatttable.SOURCEATT_SOURCEID sourceattsourceid" +
					",sourceatttable.SOURCEATT_CNNAME sourceattcnname" +
					",sourceatttable.SOURCEATT_ENNAME sourceattenname" +
					",sourceatttable.SOURCEATT_ORDERBY sourceattordeby" +
					",sourceatttable.SOURCEATT_DESC sourceattdesc" +
					",sourceatttable.SOURCEATT_TYPE sourceatttype" +
					",sourceattvaluetable.SOURCEATTVALUE_ID sourceattvalueid" +
					",sourceattvaluetable.SOURCEATTVALUE_ATTID sourceattvalueattid" +
					",sourceattvaluetable.SOURCEATTVALUE_BELONGROW sourceattvaluebelongrow" +
					",sourceattvaluetable.SOURCEATTVALUE_VALUE sourceattvaluevalue" +
					",sourceattvaluetable.SOURCEATT_TYPE sourceattvaluetype" +
					" from "+ sourcetablename +" sourcetable " +
					"," + sourceatttablename + " sourceatttable"+
					"," + sourceattvaluetablename + " sourceattvaluetable"+
					" where 1=1 " +
					" and sourceatttable.sourceatt_sourceid = sourcetable.SOURCE_ID " +
					" and sourceatttable.sourceatt_id = sourceattvaluetable.SOURCEATTVALUE_ATTID";
			if(sourceConfigBean!=null)
			{
				 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
				 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
				 enname 	= Function.nullString(sourceConfigBean.getSource_name());
				 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
				 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
				 type 		= Function.nullString(sourceConfigBean.getSource_type());
				 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
				 module 	= Function.nullString(sourceConfigBean.getSource_module());
			}
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 	= " and (";
				sql1 			= sql1 + " sourcetable.SOURCE_ID="+tmpsql[0];
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or sourcetable.SOURCE_ID="+tmpsql[i];
				}
				sql1 	= sql1 + ")";
				sql 	= sql + sql1;
			}
			
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and sourcetable.SOURCE_PARENTID="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_NAME='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_CNNAME='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and sourcetable.SOURCE_DESC='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and sourcetable.SOURCE_TYPE like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and sourcetable.SOURCE_ORDERBY="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and sourcetable.SOURCE_MODULE='"+module+"'";
			}
		}else
		{
			sql = "select " +
			",sourcetable.SOURCE_ID sourceid " +
			",sourcetable.SOURCE_PARENTID sourceparentid" +
			",sourcetable.SOURCE_CNNAME sourcecnname" +
			",sourcetable.SOURCE_NAME sourcename" +
			",sourcetable.SOURCE_MODULE sourcemodule" +
			",sourcetable.SOURCE_ORDERBY sourceorderby" +
			",sourcetable.SOURCE_DESC sourcedesc" +
			",sourcetable.SOURCE_TYPE sourcetype" +
			",sourceatttable.SOURCEATT_ID sourceattid" +
			",sourceatttable.SOURCEATT_SOURCEID sourceattsourceid" +
			",sourceatttable.SOURCEATT_CNNAME sourceattcnname" +
			",sourceatttable.SOURCEATT_ENNAME sourceattenname" +
			",sourceatttable.SOURCEATT_ORDERBY sourceattordeby" +
			",sourceatttable.SOURCEATT_DESC sourceattdesc" +
			",sourceatttable.SOURCEATT_TYPE sourceatttype" +
			",sourceattvaluetable.SOURCEATTVALUE_ID sourceattvalueid" +
			",sourceattvaluetable.SOURCEATTVALUE_ATTID sourceattvalueattid" +
			",sourceattvaluetable.SOURCEATTVALUE_BELONGROW sourceattvaluebelongrow" +
			",sourceattvaluetable.SOURCEATTVALUE_VALUE sourceattvaluevalue" +
			",sourceattvaluetable.SOURCEATT_TYPE sourceattvaluetype" +
			"  from  "+ sourcetablename +" sourcetable";
			
			for(int i=0;i<size;i++)
			{
				sql = sql +" ,"+ sourceatttablename +" sourceatttable"+i;
			}
			
			for(int j =0;j<size;j++)
			{
				sql = sql + " ,"+sourceattvaluetablename+" sourceattvaluetable"+j;
			}
			sql = sql +" where 1=1 " +
					" and sourceatttable.sourceatt_sourceid = sourcetable.SOURCE_ID " +
					" and sourceatttable.sourceatt_id = sourceattvaluetable.SOURCEATTVALUE_ATTID";
			for(int m=0;m<size;m++)
			{
				sql = sql + " and sourcetable.SOURCE_ID = sourceatttable"+m+".SOURCEATT_SOURCEID ";
			}
			
			for(int n=0;n<size;n++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(n);
				String attname = Function.nullString(sourceAttBean.getsource_attname());
				System.out.println(attname);
				if(!attname.equals(""))
				{
					sql = sql + " and sourceatttable"+n+".SOURCEATT_ENNAME='"+attname+"'";
				}
				
			}
			for(int o=0;o<size;o++)
			{
				sql = sql + " and sourceatttable"+o+".SOURCEATT_ID = sourceattvaluetable"+o+".SOURCEATTVALUE_ATTID";
			}
			for(int p=0;p<size;p++)
			{
				SourceAttQueryBean sourceAttBean = (SourceAttQueryBean)beanList.get(p);
				String attvalue = Function.nullString(sourceAttBean.getsource_attnamevalue());
				String operation = Function.nullString(sourceAttBean.getsource_attqueryop());
				if(!operation.equals("")&&!attvalue.equals(""))
				{
					sql = sql + " and sourceattvaluetable"+p+".SOURCEATTVALUE_VALUE "+operation+" '"+attvalue+"'";
				}
			}
			
			 sourceid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_id()));
			 parentid 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_parentid()));
			 enname 	= Function.nullString(sourceConfigBean.getSource_name());
			 cnname 	= Function.nullString(sourceConfigBean.getSource_cnname());
			 desc 		= Function.nullString(sourceConfigBean.getSource_desc());
			 type 		= Function.nullString(sourceConfigBean.getSource_type());
			 orderby 	= Function.nullString(String.valueOf(sourceConfigBean.getSource_orderby()));
			 module 	= Function.nullString(sourceConfigBean.getSource_module());
			
			
			if(!sourceid.equals("")&&sourceid!=null)
			{
				String tmpsql[] = sourceid.split(",");
				String sql1 = " and (";
				sql1 = sql1 + "sourcetable.SOURCE_ID="+(tmpsql[0]);
				for(int i=1;i<tmpsql.length;i++)
				{
					sql1 = sql1 + " or sourcetable.SOURCE_ID="+(tmpsql[i]);
				}
				sql1 = sql1 + ")";
				System.out.println(sql1);
				sql = sql + sql1;
			}
			if(!parentid.equals("")&&parentid!=null)
			{
				sql = sql + " and sourcetable.SOURCE_PARENTID="+parentid;
			}
			if(!enname.equals("")&&enname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_NAME='"+enname+"'";
			}
			if(!cnname.equals("")&&cnname!=null)
			{
				sql = sql + " and sourcetable.SOURCE_CNNAME='"+cnname+"'";
			}
			if(!desc.equals("")&&desc!=null)
			{
				sql = sql + " and sourcetable.SOURCE_DESC='"+desc+"'";
			}
			if(!type.equals("")&&type!=null)
			{
				sql = sql + " and sourcetable.SOURCE_TYPE like '%"+type+"%'";
			}
			if(!orderby.equals("")&&orderby!=null)
			{
				sql = sql + " and sourcetable.SOURCE_ORDERBY="+orderby;
			}
			if(!module.equals("")&&module!=null)
			{
				sql = sql + " and sourcetable.SOURCE_MODULE='"+module+"'";
			}
			
		}
		System.out.println("sql:"+sql);
		try 
		{
			ResultSet rs = null;
			try
			{
				rs = JDBCSingleton.getConn(sql);
			}
			catch(Exception e)
			{
				logger.info("JDBCSourceGrandInterface 中查询数据出现异常！");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			if(rs!=null)
			{
				//
				JDBCSQLAssociate sqlassociate = new JDBCSQLAssociate();
				returnList = sqlassociate.getSourceAndPropertyList(rs);
				if(returnList!=null)
				{
					return returnList;
				}
			}
			
			rs.close();
			rs = null;
			return null;
		}
		catch (Exception e) 
		{
			logger.info("");
			return null;
		}
	
	
	}
	
	
	
	//查询资源表，根据参数向下查询，或者是向上查询。
	
	public List getSourceInfo(String tmpsourceid,String findType)
	{
		List returnList 					= new ArrayList();
		String sourcetablename				= "";
		String sql 							= "";
		GetFormTableName getTableProperty 	= new GetFormTableName();
		try
		{
			sourcetablename					= getTableProperty.GetFormName("sourceconfig");
		}
		catch(Exception e)
		{
			logger.info("JDBCSourceGrandInterface 类中 " +
					"getSourceInfo(String tmpsourceid,String findType)" +
					"调用GetTableProperty读取配置表信息时出现异常！"+e.getMessage());
		}
		sql = "select " +
					"sourcetable.SOURCE_ID sourceid " +
					",sourcetable.SOURCE_PARENTID sourceparentid" +
					",sourcetable.SOURCE_CNNAME sourcecnname" +
					",sourcetable.SOURCE_NAME sourcename" +
					",sourcetable.SOURCE_MODULE sourcemodule" +
					",sourcetable.SOURCE_ORDERBY sourceorderby" +
					",sourcetable.SOURCE_DESC sourcedesc" +
					",sourcetable.SOURCE_TYPE sourcetype" +
					" from "+ sourcetablename +" sourcetable " +
					" where 1=1";
		if(findType.equals("up"))
		{
			sql = sql + " and sourcetable.SOURCE_ID="+tmpsourceid;
		}
		else if(findType.equals("down"))
		{
			sql = sql + " and sourcetable.SOURCE_PARENTID="+tmpsourceid;
		}
		System.out.println("sql:"+sql);
		
		try 
		{
			ResultSet rs = null;
			try
			{
				rs = JDBCSingleton.getConn(sql);
			}
			catch(Exception e)
			{
				logger.info("JDBCSourceGrandInterface 中查询数据出现异常！");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			//获得数据库查询结果集
					
			if(rs!=null)
			{
				JDBCSQLAssociate sqlassociate = new JDBCSQLAssociate();
				returnList = sqlassociate.getSourceInfo(rs);
				if(returnList!=null)
				{
					return returnList;
				}
			}
			rs.close();
			rs = null;
			return null;
		}
		catch (Exception e) 
		{
			logger.info("");
			return null;
		}
		
		
	}

	
	/**
	 * 根据用户ID，资源ID，资源类型查询出动作权限和资源属性值。
	 * 日期 2006-12-12
	 * 
	 * @author wangyanguang/王彦广 
	 * @param userid
	 * @param sourceid
	 * @param sourcetype
	 * @return List
	 *
	 */
	public List getActionAndProperty(String userid,String sourceid,String sourcetype)
	{
		String sql = "";
		return null;
	}
	
	
	
	public static void main(String args[])
	{
		JDBCSourceGrandInterface jdbcinterface = new JDBCSourceGrandInterface();
		long timeshow = System.currentTimeMillis();
		SourceConfigInfoBean sourcebean = new SourceConfigInfoBean();
		sourcebean.setSource_id("18");
		List list = jdbcinterface.getSkillAndSourceInfo("000000000000001",null,null,"");
		if(list!=null)
		{
			if(list.size()>0)
			{
				Iterator it = list.iterator();
				while(it.hasNext())
				{
					SkillSourceInfo skillinfo = (SkillSourceInfo)it.next();
					System.out.println(skillinfo.getSource_cnname());
					System.out.println(skillinfo.getSourceatt_cnname());
					System.out.println(skillinfo.getSourceattvalue_value());
				}
			}
		}
		System.out.println(System.currentTimeMillis()-timeshow);
		
		
	}

}
