package cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;
import cn.com.ultrapower.eoms.user.config.menu.hibernate.po.GrandActionConfigpo;

public class GrandActionConfigList {
	static final Logger logger = (Logger) Logger.getLogger(GrandActionConfigList.class);
	public List getMenuId(String sourceid) throws HibernateException{
		try{
			 String 		sql	 	 = "from GrandActionConfigpo where C620000032="+sourceid;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			
			 return l1;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	
	public List getGrandActionList() throws HibernateException{
		try{
			 String 		sql	 	 = "from GrandActionConfigpo";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			
			 return l1;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	//根据授权字段ID查询出授权字段名。
	public static String getMenuName(String getId) throws HibernateException{
		try{
			 String returnname = "";
			 String 		sql	 	 = "from GrandActionConfigpo where C1="+getId;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 Iterator it = l1.iterator();
			 while(it.hasNext())
			 {
				 GrandActionConfigpo grandpo = (GrandActionConfigpo)it.next();
				 returnname = grandpo.getC620000033();
			 }
			 return returnname;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	//根据资源ID与授权字段值查询出授权字段名。
	public String getActionMenuName(String getId,String moduleid) throws HibernateException{
		try{
			 String returnname = "";
			 String 		sql	 	 = "from GrandActionConfigpo t where t.c620000034='"+getId+"' and t.c620000032='"+moduleid+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 Iterator it = l1.iterator();
			 while(it.hasNext())
			 {
				 GrandActionConfigpo grandpo = (GrandActionConfigpo)it.next();
				 returnname = grandpo.getC620000033();
				 break;
			 }
			 return returnname;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	
	public  List getGrandActionConfigInfo(String getId) throws HibernateException{
		try{
			 String returnname = "";
			 String 		sql	 	 = "from GrandActionConfigpo where C1="+getId;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 return l1;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	//根据资源类型与字段对应值查出是否有重复记录。
	public static List getSameRecord(String sourcetype,String value)
	{
		try{
			 String returnname = "";
			 String 		sql	 	 = "from GrandActionConfigpo t where t.c620000032='"+sourcetype+"' and t.c620000034='"+value+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 if(l1!=null)
			 {
				 return l1;
			 }else
			 {
				 return null;
			 }
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	//根据资源类型查询数据库看是否有重复记录。
	public static List getDupRecord(String sourcetype)
	{
		try{
			 String returnname = "";
			 String 		sql	 	 = "from GrandActionConfigpo t where t.c620000032='"+sourcetype+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 if(l1!=null)
			 {
				 return l1;
			 }else
			 {
				 return null;
			 }
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}
	

	
	public static void main(String[] args)
	{
		try
		{
			GrandActionConfigList grandconfig = new GrandActionConfigList();
			System.out.println(grandconfig.getActionMenuName("231","12"));
		}
		catch(Exception e)
		{
			System.out.println("Exception");
		}
//		try
//		{
//		List list = grandconfig.getActionMenuName("231","12");
//		Iterator it = list.iterator();
//		while(it.hasNext())
//		{
//			GrandActionConfigpo grandconfigpo = (GrandActionConfigpo)it.next();
//			System.out.println(grandconfigpo.getC1());
//		}
//		}catch(Exception e)
//		{
//			System.out.println("exception");
//			
//		}
		
		
	}
}
