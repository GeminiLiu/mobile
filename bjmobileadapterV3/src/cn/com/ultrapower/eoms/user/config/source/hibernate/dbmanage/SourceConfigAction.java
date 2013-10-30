package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.io.IOException;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;
import cn.com.ultrapower.eoms.user.config.menu.aroperationdata.GrandActionInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.GetNewId;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigAttributeBean;
import cn.com.ultrapower.eoms.user.config.source.Bean.SourceConfigBean;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceactionconfig;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributeconfig;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.comm.function.Function;
public class SourceConfigAction extends HttpServlet {

	static final Logger logger = (Logger) Logger.getLogger(SourceConfigAction.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String 		source_parentid  = request.getParameter("source_parentid");
		String  	source_cnname	 = request.getParameter("source_cnname");
		String 		source_name		 = request.getParameter("source_name");
		String  	source_module	 = request.getParameter("source_module");
		long  		source_orderby 	 = Function.nuLong(request.getParameter("source_orderby"));
		String 		source_desc		 = Function.nullString(request.getParameter("source_desc"));
		//String 	source_type		 = request.getParameter("source_type");
		long		parentid		 = Function.nuLong(request.getParameter("hid"));
		String 		flagType		 = request.getParameter("flagType");
		String 		source_fieldtype = request.getParameter("source_fieldtype");
		String 		source_url       = request.getParameter("source_url");
		//String 		source_isleft    = request.getParameter("source_isleft");
		long		source_isleft		 = Function.nuLong(request.getParameter("source_isleft"));
		String[] 	source_type	     = request.getParameterValues("source_type");
		 
		try{
			SourceConfigBean sourceCNF   = new SourceConfigBean();
			SourceConfigOp   Scon        = new SourceConfigOp();
			StringBuffer	 sourcevalue = new StringBuffer();
//			sourceCNF.setSource_id(new java.lang.Long(source_id));
//			sourceCNF.setSource_parentid(new java.lang.Long(source_parentid));
//			for (int i=0;i<source_type.length;i++){
//				if(i==source_type.length-1)
//				{
//					sourcevalue.append(source_type[i]);
//				}
//				else
//				{
//					if(i==0)
//					{
//						sourcevalue.append(source_type[i]);
//					}
//					else
//					{
//						sourcevalue.append(source_type[i]+",");
//					}
//				}
//			}
			
			for (int i=0;i<source_type.length;i++){
				sourcevalue.append(source_type[i]+";");
			}
			sourceCNF.setSource_parentid(new java.lang.Long(parentid));
			sourceCNF.setSource_cnname(source_cnname);
			sourceCNF.setSource_name(source_name);
			sourceCNF.setSource_module(source_module);
			sourceCNF.setSource_orderby(new java.lang.Long(source_orderby));
			sourceCNF.setSource_desc(source_desc);
			sourceCNF.setSource_type(sourcevalue.toString());
			sourceCNF.setSource_fieldtype(source_fieldtype);
			sourceCNF.setSource_url(source_url);
			sourceCNF.setSource_isleft(Long.valueOf(source_isleft));
			long source_id=0;
				if (flagType.equals("1"))
				{	
					String t1  = "from Sourceconfig t1 where t1.sourceName='"+source_name+"'";
					 List 	   			 list			= HibernateDAO.queryObject(t1);
					if (list.size()<=0){//判断没有当前记录时在
						GetNewId getNewId=new GetNewId();
						source_id=Long.parseLong(getNewId.getnewid("sourceconfig","source_id"));
						sourceCNF.setSource_id(new java.lang.Long(source_id));
		//				System.out.println("source_id::::::::::::::"+source_id);
						Scon.sourceCnfInsert(sourceCNF);
						//getT2Bean(new java.lang.Long(source_id));
						if (getT4Value(source_type,source_id)){
							logger.info("t4插入成功 369:");
						}else{
							logger.info("t4插入失败 370:");
						}
					}else{
						logger.info(list.size()+"英文名不能重复！");
					}
					String Path = request.getContextPath();
					response.sendRedirect(Path+"/roles/sourceconfig.jsp?targetflag=yes");
				}else if (flagType.equals("2"))
				{	
					source_id  = Long.parseLong(request.getParameter("source_id"));
					sourceCNF.setSource_id(new java.lang.Long(source_id));
					Scon.sourceCnfModify(sourceCNF);
					logger.info("修改成功！");
					String Path = request.getContextPath();
					response.sendRedirect(Path+"/roles/sourceconfig.jsp?targetflag=yes2&flagType=2&id="+source_id);
				}
				
				if (flagType.equals("1"))
				{	
					for (int i=0;i<source_type.length;i++){
						if (getT5Value(String.valueOf(source_id),source_type[i])){
							logger.info("t5表插入成功！371:");
						}else{
							logger.info("t5表插入失败！372:");
						}
					}
				}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private boolean getT5Value(String source_id,String flag) throws HibernateException{
		try{
			GrandActionInterface grandinterface = new GrandActionInterface();
			List list = new ArrayList();
			GrandActionConfigBean grandinfo = new GrandActionConfigBean();
			//通过当前类型查出所有t5表中相关数据
			SourceActionConfigInfo SourceActionConfigInfo =new SourceActionConfigInfo();
			List liSourceAction=SourceActionConfigInfo.GetSourceActionQry(flag);
			
			grandinfo.setDropDownConf_FieldID(source_id);  //资源ID
			for(int i=0;i<liSourceAction.size();i++)
			{
				Sourceactionconfig Sac=(Sourceactionconfig)liSourceAction.get(i);
				
				grandinfo.setDropDownConf_FieldValue(Sac.getSourceactionFieldvalue());  //动作中文名称
				grandinfo.setDropDownConf_NumValue(Sac.getSourceactionNumvalue());     //动作中文名对应的值
				grandinfo.setDropDownConf_OrderBy(String.valueOf(Sac.getSourceactionOrderby()));	//排序值
				list.add(grandinfo);
				boolean bl = grandinterface.insertGrandActionInterface(list);
				if(bl)
				{
					System.out.println("OK");
				}
				else
				{
					System.out.println("Sorry");
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	private boolean getT4Value(String[] sourcetype,long T1id){
		try{
			SourceConfigAttributeBean sourceatt	= new SourceConfigAttributeBean();
			GetNewId 			      getid		= new GetNewId();
			//把资源属性类型当做条件进行t4查询
			for(int i=0;i<sourcetype.length;i++){
				String sql="from Sourceattributeconfig  c where c.sourceattcfgType='"+sourcetype[i]+"'";
		   	    List 	   			 list			= HibernateDAO.queryObject(sql);
				Iterator it=list.iterator();
				while(it.hasNext()){
					Sourceattributeconfig sourceattributeconfig=(Sourceattributeconfig)it.next();
					
					String SourceattcfgId			= String.valueOf(sourceattributeconfig.getSourceattcfgId().toString());
					String SourceattcfgTypeflag		= String.valueOf(sourceattributeconfig.getSourceattcfgTypeflag());
					String SourceattcfgCnname		= String.valueOf(sourceattributeconfig.getSourceattcfgCnname());
					String SourceattcfgEnname		= String.valueOf(sourceattributeconfig.getSourceattcfgEnname());
					String SourceattcfgType		    = String.valueOf(sourceattributeconfig.getSourceattcfgType());
					String SourceattcfgOrderby		= String.valueOf(sourceattributeconfig.getSourceattcfgOrderby().toString());
					String SourceattcfgDesc			= String.valueOf(sourceattributeconfig.getSourceattcfgDesc());
					
					String sql1="from Sourceconfigattribute v where 1=1 and v.sourceattCnname = '"+SourceattcfgCnname+
								"' and  v.sourceattEnname='"+SourceattcfgEnname+"' and v.sourceattOrderby = " +SourceattcfgOrderby+
								" and v.sourceattType = '"+SourceattcfgType+"' and v.sourceattSourceid='"+T1id+"'";
							
			   	    List 	   			 list1			= HibernateDAO.queryObject(sql1);
			   	    //把相关的属性类型选择出来,如果t2表中没有相关记录进行插入
			   	    
			   	    if(list1.size()<=0){
			   	    
				    	long  		source_id 	        = Long.parseLong(getid.getnewid("sourceconfigattribute","sourceatt_id"));
				    	sourceatt.setSourceatt_id(new Long(source_id));
						
						sourceatt.setSourceatt_sourceid(new Long(T1id));
						sourceatt.setSourceatt_cnname(SourceattcfgCnname);
						sourceatt.setSourceatt_enname(SourceattcfgEnname);
						sourceatt.setSourceatt_type(SourceattcfgType);
						
						sourceatt.setSourceatt_orderby(Function.javaLong(SourceattcfgOrderby));
						sourceatt.setSourceatt_desc(SourceattcfgDesc);
						
						SourceAttDataBaseOp Scon=new SourceAttDataBaseOp();
						
						if (Scon.sourceCnfInsert(sourceatt)){
							logger.info("t2插入成功 373:");
						}else{
							logger.info("t2失败	374:");
						}
			   	    }
				}
			}
			return true;
		}catch(Exception e){
			logger.info("-------t4保存失败 375 "+e);
			e.printStackTrace();
			return false;
		}
	} 
	public static void main(String arg[]){
		
	}
	/**
	 * 此方法实现如下功能：
	 * 1.根据传入参数构造一个T1的Bean;
	 * 2.当属性T1插入一条记录时，同时向属性T2表中插入一条记录。



	 * 
	 * 日期 2006-11-6
	 * 
	 * @author wangyanguang/王彦广 
	 * @param id
	 * @return SourceConfigAttributeBean
	 */
	private void getT2Bean(Long id)
	{
		GetNewId 	getid					= new GetNewId();
    	long  		source_id 	        	= Long.parseLong(getid.getnewid("sourceconfigattribute","sourceatt_id"));
		SourceConfigAttributeBean sourceatt	= new SourceConfigAttributeBean();
		sourceatt.setSourceatt_id(new java.lang.Long(source_id));
		sourceatt.setSourceatt_sourceid(id);
		sourceatt.setSourceatt_cnname("资源类别");
		sourceatt.setSourceatt_enname("source_type");
		sourceatt.setSourceatt_orderby(new java.lang.Long(0));
		sourceatt.setSourceatt_desc("区分资源类别");
		sourceatt.setSourceatt_type("2");
		SourceAttDataBaseOp Scon=new SourceAttDataBaseOp();
		Scon.sourceCnfInsert(sourceatt);
	}

}
