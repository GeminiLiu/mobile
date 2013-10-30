package com.ultrapower.eoms.mobile.util;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributevalue;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfigattribute;
import cn.com.ultrapower.eoms.user.comm.function.Function;

public class GetAttInfoList {

	static final Logger logger = (Logger) Logger.getLogger(SourceConfigInfo.class);
	//查询相关所有数据
	public List GetsourceCFGlist() throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfigattribute";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	//根据资源类别属性ID取得资源配制信息。
	public List GetsourceCFGlist(java.lang.Long sourceatt_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfigattribute where SOURCEATT_ID="+sourceatt_id;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	//根据资源类别ID取得资源配制信息。
	public static  List GetsourceCFGinfo(java.lang.Long sourceatt_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig t1 where t1.sourceParentid="+sourceatt_id+" and t1.sourceType like '%5;%'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}	
	
	//根据资源类别ID与资源属性类型：1.目录属性 取得资源配制信息。
	public static  List Getsource(java.lang.Long sourceatt_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfigattribute where sourceatt_sourceid="+sourceatt_id+" and sourceatt_type='1'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}	
	//根据资源ID，资源类别，英文名查看数据库中是否有相同记录。
	public static List getSameRecord(long sourceid,String attType)
	{
		try{
			 String 		sql	 	 = "from Sourceconfigattribute t where t.sourceattSourceid="+sourceid
			 							+" and t.sourceattType='"+attType+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     if(l1!=null)
		     {
		    	 return l1;
		     }
		     else
		     {
		    	 return null;
		     }
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	public static String getname(java.lang.Long  id) {
		
		String sourcename ="";
		try {
			String sql="from Sourceconfigattribute t1,Sourceconfig t2 where t1.sourceattSourceid=t2.sourceId and t1.sourceattId="+id;
			HibernateDAO   session = new HibernateDAO(); 
			List 			l1	    = session.queryObject(sql);
			
			Iterator it = l1.iterator();
			 while(it.hasNext()){
		         Object[] results=(Object[])it.next();
		         Sourceconfigattribute      t11 = (Sourceconfigattribute) results[0];
		         Sourceconfig      t12 = (Sourceconfig) results[1];
		         sourcename    = t12.getSourceCnname();
			 }
			 
			return sourcename;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//根据sourceconfig(T1)表的资源ID，从sourceconfigattribute(T2)表中取得此资源ID的所有属性ID，然后根据属性ID
	//与sourceattributevalue(T3)表进行关联取得属性值。
	
	public List getAttValue(java.lang.Long id,String type)
	{
		List list = null;
		try {
			String sql="from  Sourceconfig t1,Sourceconfigattribute t2,Sourceattributevalue t3 where t1.sourceId="+id+" and t1.sourceId=t2.sourceattSourceid and t2.sourceattId=t3.sourceattvalueAttid";
			String tmpsql = Function.nullString(type);
			if(!tmpsql.equals("")&& tmpsql!=null)
			{
				sql = sql + " and t1.sourceType like '%"+type+";"+"%'";
			}
			HibernateDAO   session = new HibernateDAO(); 
			list    = session.queryObject(sql);
		} catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		
		if(list.size()>0)
		{
			return list;
		}else
		{
			return null;
		}
	}
	
	
//	public List getAttValue(java.lang.Long id)
//	{
//		List list = null;
//		try {
//			String sql="from Sourceconfigattribute t2,Sourceattributevalue t3 where t2.sourceattSourceid="+id+" and t2.sourceattId=t3.sourceattvalueAttid and t2.sourceattType='1'";
//			HibernateDAO   session = new HibernateDAO(); 
//			list    = session.queryObject(sql);
//		} catch (Exception e) 
//		{
//			e.printStackTrace();
//			return null;
//		}
//		
//		if(list.size()>0)
//		{
//			return list;
//		}else
//		{
//			return null;
//		}
//	}
	
	//根据sourceconfig(T1)表的资源ID，从sourceconfigattribute(T2)表中取得此资源ID的所有属性ID，并且sourceconfigattribute(T2)
	public boolean getAttFlag(java.lang.Long id,String sourceType)
	{
		List list = null;
		try {
			String sql="from Sourceconfigattribute t2,Sourceattributevalue t3 where t2.sourceattSourceid="+id+" and t2.sourceattId=t3.sourceattvalueAttid and t2.sourceattEnname='source_type' and t3.sourceattvalueValue='"+sourceType+"'";
			HibernateDAO   session = new HibernateDAO(); 
			list    = session.queryObject(sql);
		} catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
		if(list.size()>0)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static void main(String[] args) 
	{
		GetAttInfoList getatt = new GetAttInfoList();
//		boolean bl = getatt.getAttFlag(new Long(12),"444");
//		System.out.println(bl);
		try
		{
			
		
	
		//System.out.println(getSameEnName(3,"1"));
			
			
			
//		List list = GetsourceCFGinfo(new java.lang.Long(12));
//		List fieldlist = GetsourceCFGinfo(new Long(12));
//	    //List fieldlist = getformfield.getFieldList(formid);
//		 if (!fieldlist.isEmpty()) 
//		 {
//		     for (int j = 0; j < fieldlist.size(); j++) 
//		     {
//			     Sourceconfigattribute fieldpo = (Sourceconfigattribute) fieldlist.get(j);
//				System.out.println( fieldpo.getSourceattCnname());//取得字段的中文名
//				 System.out.println( fieldpo.getSourceattEnname());//取得字段的英文名
//			 }
//		  }
//		
		
//		Iterator it = list.iterator();
//		 while(it.hasNext()){
//	         
//	         Sourceconfigattribute      t11 = (Sourceconfigattribute)it.next();
//	         System.out.println("中文名："+t11.getSourceattCnname()+" 英文名："+t11.getSourceattEnname());
//		 }
		}catch(Exception e)
		{
			System.out.println("exception!");
		}
	}

}
