package cn.com.ultrapower.eoms.user.userinterface.cm;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAllBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceValueBean;
import cn.com.ultrapower.eoms.user.comm.function.PrivateFieldInfo;
public class UserFolderSourceNew {

	/**
	 * date 2007-3-2
	 * author shigang
	 * @param args
	 * @return void
	 */
	GetFormTableName getFormTableName	= new GetFormTableName();
	
	private String roleskill				= getFormTableName.GetFormName("RemedyTrole");

	static final Logger logger 	= (Logger) Logger.getLogger(UserFolderSourceNew.class);
	
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");
	private HashMap	filedInfo   			= getFormTableName.GetDataparam("sourceforder");
	
	public List getsourcenew(String tuserId,SourceConfigBean sourceConfigBean,List valuelist)
	{
		//		作为返回参数的List变量
		List 		sourceall		= new ArrayList();
		//传过来的t1表的查询条件
		String 	scnfwhere		= t1where(sourceConfigBean);
		String 	sforderwhere	= convertquerywhere(valuelist);
		StringBuffer sql 	= new StringBuffer();
		sql.append(" select * from(");
		sql.append("select scnf.*,sforder.* " +
				   " from sourceconfig scnf,sourceforder sforder,"+roleskill+" sysskill" +
				   " where sysskill.C610000007 = '"+tuserId+"'" +
				   " and sysskill.c610000008 = scnf.source_id" +
		           " and sysskill.C610000018 = '0'" +
		           " and scnf.source_id=sforder.forder_Sourceid" +
		           " and scnf.source_type like '%7;%'" + scnfwhere + sforderwhere +
		           " union " +
		           " select scnf.*,sforder.* " +
		           " from sourceconfig scnf,sourceforder sforder" +
		           " ," + RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser" +
		           " where scnf.source_id = RemedyTrolesskillmanage.c660000007" +
		           " and scnf.source_id=sforder.forder_Sourceid" +
		           " and RemedyTrolesskillmanage.C660000006 =  RemedyTrolesusergrouprel.c660000028" +
		           " and RemedyTrolesusergrouprel.C660000027 = RemedyTgroupuser.C620000027" +
		           " and (RemedyTrolesusergrouprel.C660000026 = '"+tuserId+"' or RemedyTrolesusergrouprel.C660000026 is null)" +
		           " and RemedyTgroupuser.C620000028 = '"+tuserId+"'" +
		           " and scnf.source_type like '%7;%' " + scnfwhere + sforderwhere);
		sql.append(" ) datatable where 1=1 order by source_id");
		IDataBase dataBase	= null;
		Statement stm		= null;
		ResultSet rs		= null;
		try
		{
			String source_cnname			= "";
			String source_id				= "";
			
			String source_parentid			= "";
			String source_name				= "";
			String source_module			= "";
			String source_type				= "";
			
			logger.info(sql.toString());
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			rs		= dataBase.executeResultSet(stm,sql.toString());
			while(rs.next())
			{
				SourceValueBean sourceValueBean=new SourceValueBean();
				source_id				= rs.getString("source_id");
				source_cnname			= rs.getString("source_cnname");
				source_parentid			= rs.getString("source_parentid");
				source_name				= rs.getString("source_name");
				source_module			= rs.getString("source_module");
				source_type				= rs.getString("source_type");
				
				sourceValueBean.setSource_id(source_id);
				sourceValueBean.setSource_cnname(source_cnname);
				sourceValueBean.setSource_parentid(source_parentid);
				sourceValueBean.setSource_name(source_name);
				sourceValueBean.setSource_module(source_module);
				sourceValueBean.setSource_type(source_type);
				
				HashMap valuemap		= new HashMap();
				
				Set keyset 	= filedInfo.keySet();
				
				for(Iterator i = keyset.iterator();i.hasNext();)
				{
					String strkey	 						= (String)i.next();
					PrivateFieldInfo	privateFieldInfo 	= (PrivateFieldInfo)filedInfo.get(strkey);
					if(!String.valueOf(privateFieldInfo).equals("")&&!String.valueOf(privateFieldInfo).equals("null"))
					{
						int attvaluetype						= privateFieldInfo.getIntFieldType();
						if(String.valueOf(attvaluetype).equals("2"))
						{
							String fieldvalue = rs.getString(privateFieldInfo.getStrFieldID());
							if(!String.valueOf(fieldvalue).equals("")&&!String.valueOf(fieldvalue).equals("null"))
							{
								valuemap.put(strkey,Function.Unixto_datetime(fieldvalue+"000"));
							}
						}
						else
						{
							valuemap.put(strkey,rs.getString(privateFieldInfo.getStrFieldID()));
						}
					}
				}
				sourceValueBean.setExts(valuemap);
				sourceall.add(sourceValueBean);
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			return null;
		}
		finally
		{
			
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return sourceall;
	}

	public String t1where(SourceConfigBean sourceConfigBean){
		StringBuffer cnfwhere=new StringBuffer();
		if(sourceConfigBean.getSource_cnname()!=null){
			cnfwhere.append(" and scnf.source_cnname="+sourceConfigBean.getSource_cnname());
		}
		if(sourceConfigBean.getSource_desc()!=null){
			cnfwhere.append(" and scnf.Source_desc="+sourceConfigBean.getSource_desc());
		}
		if(sourceConfigBean.getSource_fieldtype()!=null){
			cnfwhere.append(" and scnf.Source_fieldtype="+sourceConfigBean.getSource_fieldtype());
		}
		if(sourceConfigBean.getSource_id()!=null){
			cnfwhere.append(" and scnf.Source_id="+sourceConfigBean.getSource_id());
		}
		if(sourceConfigBean.getSource_module()!=null){
			cnfwhere.append(" and scnf.Source_module="+sourceConfigBean.getSource_module());
		}
		if(sourceConfigBean.getSource_name()!=null){
			cnfwhere.append(" and scnf.Source_name="+sourceConfigBean.getSource_name());
		}
		if(sourceConfigBean.getSource_orderby()!=null){
			cnfwhere.append(" and scnf.Source_orderby="+sourceConfigBean.getSource_orderby());
		}
		if(sourceConfigBean.getSource_parentid()!=null){
			cnfwhere.append(" and scnf.Source_parentid="+sourceConfigBean.getSource_parentid());
		}
		if(sourceConfigBean.getSource_type()!=null){
			cnfwhere.append(" and scnf.Source_type="+sourceConfigBean.getSource_type());
		}
		return cnfwhere.toString();
	}
	
	public String convertquerywhere(List valuelist){
		StringBuffer valuequerywhere=new StringBuffer();
		if (valuelist!=null&&valuelist.size()>0)
		{
			for(int i=1;i<valuelist.size();i++)
			{
				SourceAttQueryBean sourceAttQueryBean=(SourceAttQueryBean)valuelist.get(i);
				if(!String.valueOf(sourceAttQueryBean).equals("")&&!String.valueOf(sourceAttQueryBean).equals("null"))
				{
					PrivateFieldInfo privateFieldInfo			= (PrivateFieldInfo)filedInfo.get(sourceAttQueryBean.getsource_attname());
					String attoperator		= sourceAttQueryBean.getsource_attqueryop();
					String attvalue			= sourceAttQueryBean.getsource_attnamevalue();
					String attname			= privateFieldInfo.getStrFieldID();
					if(!String.valueOf(attname).equals("")&&!String.valueOf(attname).equals("null")
							&&!String.valueOf(attoperator).equals("")&&!String.valueOf(attoperator).equals("null")
					)
					{
						valuequerywhere.append(" and " + String.valueOf(attname));
						valuequerywhere.append(" " + String.valueOf(attoperator));
						valuequerywhere.append(" " + String.valueOf(attvalue));
					}
				}
				
			}
		}
		return valuequerywhere.toString();
	}
	public static void main(String[] args)
	{
//		ArrayList bb=new ArrayList();
//		bb.add("url");
//		SourceConfigBean sourceConfigBean=new SourceConfigBean();
//		sourceConfigBean.setSource_id(new Long(75));
//		RoleGrandInterface aa=new RoleGrandInterface(); 
//		aa.GetFolderSource("000000000000100",sourceConfigBean,bb);
		String aa="bbbbccc";
		
		System.out.print(aa.indexOf("2"));
	}

}
