package com.ultrapower.eoms.mobile.util;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

//import cn.com.ultrapower.eoms.resource.service.ResourceAttribute;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceAttQueryBean;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
//import cn.com.ultrapower.eoms.user.userinterface.RoleGrandInterface;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAllBean;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceValueBean;

public class UserFolderSource {

	/**
	 * date 2007-3-2
	 * author shigang
	 * @param args
	 * @return void
	 */
	GetFormTableName getFormTableName	= new GetFormTableName();
	
	private String roleskill				= getFormTableName.GetFormName("RemedyTrole");

	static final Logger logger 	= (Logger) Logger.getLogger(RoleGrandInterface.class);
	
	private String RemedyTrolesusergrouprel	= getFormTableName.GetFormName("RemedyTrolesusergrouprel");
	private String RemedyTrolesskillmanage	= getFormTableName.GetFormName("RemedyTrolesskillmanage");
	private String RemedyTgroupuser			= getFormTableName.GetFormName("RemedyTgroupuser");
	
	public List GetFolderSource(String username,SourceConfigBean sourceConfigBean,List valuelist){
		List returnsource		=	new ArrayList();
		List FolderSourceList	=	new ArrayList();
		//把条件放入下面的类进行查询
		returnsource=getsource(username,sourceConfigBean,valuelist);
		
		
		for(int i=0;i<returnsource.size();i++){
			Map exts=new HashMap();
//			SourceAllBean SourceAllBean=new SourceAllBean();
			SourceAllBean sourceAllBean =(SourceAllBean)returnsource.get(i);
//			if(String.valueOf(sourceAllBean.getSource_cnname()).equals("马瑞京文件夹")){
//				System.out.print("ddd");
//			}
			//当第一次存数据时
			if (i==0){
				SourceValueBean SourceValueBean=new SourceValueBean();
				SourceValueBean.setSource_id(String.valueOf(sourceAllBean.getSource_id()));
				SourceValueBean.setSource_cnname(String.valueOf(sourceAllBean.getSource_cnname()));
				
				SourceValueBean.setSource_parentid(sourceAllBean.getSource_parentid());
				SourceValueBean.setSource_name(sourceAllBean.getSource_name());
				SourceValueBean.setSource_module(sourceAllBean.getSource_module());
				SourceValueBean.setSource_type(sourceAllBean.getSource_type());
				
//				exts.put(String.valueOf(sourceAllBean.getSource_name()),String.valueOf(sourceAllBean.getSourceattvalue_value()));
				exts.put(String.valueOf(sourceAllBean.getSourceatt_enname()),String.valueOf(sourceAllBean.getSourceattvalue_value()));

				SourceValueBean.setExts(exts);
				FolderSourceList.add(SourceValueBean);
			}else{
				SourceAllBean SourceAllBean2=(SourceAllBean)returnsource.get(i-1);
				//判断是否和上一条数据的id 相等，如果相等，把他存入上一条记录中，不相等存入下一条记录中
				if(String.valueOf(SourceAllBean2.getSource_id()).equals(sourceAllBean.getSource_id())){
					SourceValueBean SourceValuelast=(SourceValueBean)FolderSourceList.get(FolderSourceList.size()-1);
					exts=SourceValuelast.getExts();
					exts.put(String.valueOf(sourceAllBean.getSourceatt_enname()),String.valueOf(sourceAllBean.getSourceattvalue_value()));
				}else{
					SourceValueBean SourceValueBean=new SourceValueBean();
					SourceValueBean.setSource_id(String.valueOf(sourceAllBean.getSource_id()));
					SourceValueBean.setSource_cnname(String.valueOf(sourceAllBean.getSource_cnname()));
//					exts.put(String.valueOf(sourceAllBean.getSource_name()),String.valueOf(sourceAllBean.getSourceattvalue_value()));
//					shigang-
					exts.put(String.valueOf(sourceAllBean.getSourceatt_enname()),String.valueOf(sourceAllBean.getSourceattvalue_value()));
					
					SourceValueBean.setExts(exts);
					
					SourceValueBean.setSource_parentid(sourceAllBean.getSource_parentid());
					SourceValueBean.setSource_name(sourceAllBean.getSource_name());
					SourceValueBean.setSource_module(sourceAllBean.getSource_module());
					SourceValueBean.setSource_type(sourceAllBean.getSource_type());
					
					FolderSourceList.add(SourceValueBean);
				}
			}
		}
	
		return FolderSourceList;
	}
	
	public List getsource(String tuserId,SourceConfigBean sourceConfigBean,List valuelist)
	{
		try
		{	//传过来的t1表的查询条件
			String t1where=t1where(sourceConfigBean);

			StringBuffer sql = new StringBuffer();
			StringBuffer t2sql = new StringBuffer();
			t2sql.append("select source_id from(select count(sab.sourceatt_sourceid) test,t1.source_id from sourceconfig t1,sourceconfigattribute sab,sourceattributevalue t3");
			t2sql.append(" where t1.source_id=sab.sourceatt_sourceid and sab.sourceatt_id=t3.sourceattvalue_attid");
			int n=0;
			
			if (valuelist!=null&&valuelist.size()>0){
				SourceAttQueryBean sourceAttQueryBean0=(SourceAttQueryBean)valuelist.get(0);
				//传过来的类型
				String strflag=sourceAttQueryBean0.getsource_attqueryop();
				
				t2sql.append(" and ((sab.sourceatt_enname='"+sourceAttQueryBean0.getsource_attname()+"'");
				if (strflag.equals("like")){
					t2sql.append(" and t3.sourceattvalue_value "+strflag+" '%"+sourceAttQueryBean0.getsource_attnamevalue().trim()+"%')");
				}else{
					t2sql.append(" and t3.sourceattvalue_value "+strflag+"'"+sourceAttQueryBean0.getsource_attnamevalue().trim()+"')");
				}
				n=n+1;
				for(int i=1;i<valuelist.size();i++){
					SourceAttQueryBean sourceAttQueryBean1=(SourceAttQueryBean)valuelist.get(i);
//					传过来的类型
					strflag=sourceAttQueryBean1.getsource_attqueryop();
					if (t2sql.toString().indexOf(sourceAttQueryBean1.getsource_attname())==-1){
						//如果不重复的
						n=n+1;
						t2sql.append(" or (sab.sourceatt_enname='"+sourceAttQueryBean1.getsource_attname()+"'");
						if (strflag.equals("like")){
							t2sql.append(" and t3.sourceattvalue_value "+strflag+" '%"+sourceAttQueryBean1.getsource_attnamevalue().trim()+"%')");
						}else{
							t2sql.append(" and t3.sourceattvalue_value "+strflag+"'"+sourceAttQueryBean1.getsource_attnamevalue().trim()+"')");
						}
					}
					else
					{//重的日期
						t2sql.append(" and (sab.sourceatt_enname='"+sourceAttQueryBean1.getsource_attname()+"'");
						if (strflag.equals("like")){
							t2sql.append(" and t3.sourceattvalue_value "+strflag+" '%"+sourceAttQueryBean1.getsource_attnamevalue().trim()+"%')");
						}else{
							t2sql.append(" and t3.sourceattvalue_value "+strflag+"'"+sourceAttQueryBean1.getsource_attnamevalue().trim()+"')");
						}
					}
					
				}
				
				t2sql.append(")");
			}
				t2sql.append(" group by t1.source_id) cou where cou.test >= "+n +" and cou.source_id=datatable.source_id");
				
				System.out.println(t2sql.toString());
				
			//根据配置文件中的表名和传入的参数确定sql语句
			sql.append(" select t1.*,t2.*,t3.* from sourceconfig t1,sourceconfigattribute t2,sourceattributevalue t3");
			sql.append(" where t2.sourceatt_sourceid=t1.source_id and t3.sourceattvalue_attid=t2.sourceatt_id");
			sql.append(" and t1.source_id in(");
			sql.append(" select * from(");
			sql.append(" select distinct t11.source_id ");
			sql.append(" from sourceconfig t11,sourceconfigattribute t22,sourceattributevalue t33,"+roleskill+"  sysskill");
			sql.append(" where t22.sourceatt_sourceid=t11.source_id and t33.sourceattvalue_attid=t22.sourceatt_id");
			sql.append(" and sysskill.C610000007 = '"+tuserId+"'");
			sql.append(" and sysskill.c610000008 = t11.source_id");
			sql.append(" and sysskill.C610000018 = '0'");
			sql.append(" and t11.source_type like '%7;%'");
			sql.append(" union ");
			sql.append(" select distinct t111.source_id ");
			sql.append(" from sourceconfig t111,sourceconfigattribute t222,sourceattributevalue t333,");
			sql.append(RemedyTrolesusergrouprel+" RemedyTrolesusergrouprel,"+RemedyTrolesskillmanage+" RemedyTrolesskillmanage,"+RemedyTgroupuser+" RemedyTgroupuser");
			sql.append(" where t222.sourceatt_sourceid=t111.source_id and t333.sourceattvalue_attid=t222.sourceatt_id");
			sql.append(" and t111.source_id = RemedyTrolesskillmanage.c660000007");
			sql.append(" and RemedyTrolesskillmanage.C660000006 =  RemedyTrolesusergrouprel.c660000028");
			sql.append(" and RemedyTrolesusergrouprel.C660000027 = RemedyTgroupuser.C620000027");
			sql.append(" and (RemedyTrolesusergrouprel.C660000026 = '"+tuserId+"' or RemedyTrolesusergrouprel.C660000026 is null)");
			sql.append(" and RemedyTgroupuser.C620000028 = '"+tuserId+"'");
			sql.append(" and t111.source_type like '%7;%' ");
			sql.append(" ) datatable where 1=1 and exists(");
			sql.append(t2sql.toString());
			sql.append(" )) "+ t1where +" order by source_id");
			System.out.println("------"+sql.toString());
			logger.info(sql.toString());
			//实例化一个类型为接口IDataBase类型的工厂类
			IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			Statement stm		= dataBase.GetStatement();
			//获得数据库查询结果集
			ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
			
			String source_cnname			= "";
			String source_id				= "";
			String sourceattvalue_value		= "";
			String sourceatt_enname			= "";
			
			String source_parentid			= "";
			String source_name				= "";
			String source_module			= "";
			String source_type				= "";
			
			//作为返回参数的List变量
			List 		sourceall		= new ArrayList();
			//把数据提取出来并压到库里
			while(rs.next())
			{
				SourceAllBean SourceAllBean=new SourceAllBean();
				
				source_id				= rs.getString("source_id");
				source_cnname			= rs.getString("source_cnname");
				
				source_parentid			= rs.getString("source_parentid");
				source_name				= rs.getString("source_name");
				source_module			= rs.getString("source_module");
				source_type				= rs.getString("source_type");
				
			

				sourceatt_enname		= rs.getString("sourceatt_enname");//t2名
				sourceattvalue_value	= rs.getString("sourceattvalue_value");//t3值
				
				SourceAllBean.setSource_id(source_id);
				SourceAllBean.setSource_cnname(source_cnname);
				SourceAllBean.setSourceatt_enname(sourceatt_enname);
				SourceAllBean.setSourceattvalue_value(sourceattvalue_value);
				
				SourceAllBean.setSource_parentid(source_parentid);
				SourceAllBean.setSource_name(source_name);
				SourceAllBean.setSource_module(source_module);
				SourceAllBean.setSource_type(source_type);
				
				sourceall.add(SourceAllBean);
			}
		
			//关闭连接
			rs.close();
			stm.close();
			dataBase.closeConn();

			return sourceall;	
		}
		catch(Exception e)
		{
			logger.error("getsource 获得资源id失败"+e.getMessage());
			return null;
		}
	}
//	t1的条件
	public String t1where(SourceConfigBean sourceConfigBean){
		StringBuffer t1where=new StringBuffer();
		if(sourceConfigBean.getSource_cnname()!=null){
			t1where.append(" and t1.source_cnname="+sourceConfigBean.getSource_cnname());
		}
		if(sourceConfigBean.getSource_desc()!=null){
			t1where.append(" and t1.Source_desc="+sourceConfigBean.getSource_desc());
		}
		if(sourceConfigBean.getSource_fieldtype()!=null){
			t1where.append(" and t1.Source_fieldtype="+sourceConfigBean.getSource_fieldtype());
		}
		if(sourceConfigBean.getSource_id()!=null){
			t1where.append(" and t1.Source_id="+sourceConfigBean.getSource_id());
		}
		if(sourceConfigBean.getSource_module()!=null){
			t1where.append(" and t1.Source_module="+sourceConfigBean.getSource_module());
		}
		if(sourceConfigBean.getSource_name()!=null){
			t1where.append(" and t1.Source_name="+sourceConfigBean.getSource_name());
		}
		if(sourceConfigBean.getSource_orderby()!=null){
			t1where.append(" and t1.Source_orderby="+sourceConfigBean.getSource_orderby());
		}
		if(sourceConfigBean.getSource_parentid()!=null){
			t1where.append(" and t1.Source_parentid="+sourceConfigBean.getSource_parentid());
		}
		if(sourceConfigBean.getSource_type()!=null){
			t1where.append(" and t1.Source_type="+sourceConfigBean.getSource_type());
		}
		return t1where.toString();
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
