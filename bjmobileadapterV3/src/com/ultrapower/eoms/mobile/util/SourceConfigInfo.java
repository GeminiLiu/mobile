package com.ultrapower.eoms.mobile.util;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

//import cn.com.ultrapower.eoms.user.config.duty.hibernate.dbmanage.DutyOrgDatabaseInfo;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceconfig;
public class SourceConfigInfo {

	/**
	 * date 2006-10-27
	 * author shigang
	 * @param args
	 * @return void
	 */
	static final Logger logger = (Logger) Logger.getLogger(SourceConfigInfo.class);
	//閺屻儴顕楅惄绋垮彠閹碉拷閺堝鏆熼幑锟�
	public List GetsourceCFGlist() throws HibernateException
	{
		try{
			 String 		sql	 	= "from Sourceconfig";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("386  GetsourceCFGlist error:"+e.toString());
				return null;
		 }
	}
	
	public List GetsourceCFGlistByType() throws HibernateException
	{
		try{
			 String 		sql	 	= "from Sourceconfig t1 where t1.sourceType like '%1;%' and t1.sourceType like '%3;%'  ";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("387 GetsourceCFGlistByType  error:"+e.toString());
				return null;
		 }
	}
	
	public List GetsourceCFGlist(java.lang.Long source_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig where source_id='"+source_id+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("388  GetsourceCFGlist error:"+e.toString());
				return null;
		 }
	}

	//閹稿瀚抽弬鍥ф倳閺岋拷
	public List GetsourceCFGlist(String source_enname) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig c where c.sourceName='"+source_enname+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("389 GetsourceCFGlist error:"+e.toString());
				return null;
		 }
	}
	public List Getlist(String sql) throws HibernateException
	{
		try{
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("390 Getlist  error:"+e.toString());
				return null;
		 }
	}
	
	/**
	 * 
	 * 鏃ユ湡 2006-12-4
	 * 
	 * @author wangyanguang/鐜嬪溅骞� 
	 * @param source_parentid
	 * @return
	 * @throws HibernateException List
	 *
	 */
	public List GetsourceCFGInfo(java.lang.Long source_parentid) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig where source_parentid='"+source_parentid+"'" ;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	/**
	 * 鏍规嵁璧勬簮ID杩斿洖璧勬簮Bean淇℃伅銆�
	 * 鏃ユ湡 2006-12-4
	 * 
	 * @author wangyanguang/鐜嬪溅骞� 
	 * @param source_id
	 * @return
	 * @throws HibernateException List
	 *
	 */
	public static Sourceconfig GetsourceCFGlistInfo(java.lang.Long source_id) 
	{
		try{
			 String 		sql	 	 = "from Sourceconfig where source_id='"+source_id+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		    if(l1!=null)
		    {
		    	Sourceconfig sourceinfo = null;
		    	Iterator it = l1.iterator();
		    	while(it.hasNext())
		    	{
		    		sourceinfo = (Sourceconfig)it.next();
		    	}
		    	return sourceinfo;
		    }else
		    {
		    	return null;
		    }
		    
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	/**
	 * 鏍规嵁璧勬簮璧勬簮鑻辨枃鍚嶈繑鍥炶祫婧怋ean淇℃伅銆�
	 * 鏃ユ湡 2007-1-18
	 * @author wangyanguang/鐜嬪溅骞� 
	 * @param source_id
	 * @return
	 * @throws HibernateException List
	 *
	 */
	public static Sourceconfig GetsourceCFGlistInfo(String sourcename) 
	{
		try{
			 String 		sql	 	 = "from Sourceconfig t where t.sourceName='"+sourcename+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		    if(l1!=null)
		    {
		    	Sourceconfig sourceinfo = null;
		    	Iterator it = l1.iterator();
		    	while(it.hasNext())
		    	{
		    		sourceinfo = (Sourceconfig)it.next();
		    	}
		    	return sourceinfo;
		    }else
		    {
		    	return null;
		    }
		    
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	/**
	 * 鏃ユ湡 2006-12-2
	 * @author xuquanxing 
	 * @param source_parentid
	 * @param type
	 * @return
	 * @throws HibernateException List
	 */
	public List GetsourceCFGInfos(java.lang.Long source_parentid,String type) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig t where t.sourceType like '%"+type+"%' and t.sourceParentid='"+source_parentid+"'" ;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	
	/**
	 * 閺冦儲婀� 2006-11-17
	 * @author xuquanxing 
	 * @param source_enname
	 * @param sourcetype
	 * @return
	 * @throws HibernateException List
	 */
	public List GetsourceCFGlist(String source_enname,String sourcetype) throws HibernateException
	{
		try{
               String 		sql	 	 = "from Sourceconfig t1,Sourceconfig t2 where t1.sourceParentid=t2.sourceId and ";
               String      sql1     = sql+"t1.sourceName='"+source_enname+"' and t1.sourceType like '%"+sourcetype+"%'";
 			   HibernateDAO   session = new HibernateDAO(); 
			   List 			l1	    = session.queryObject(sql1);
		       return l1;
		  }catch(Exception e)
		  {
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		  }
	}
	/**
	 * 鏃ユ湡 2006-11-24
	 * @author xuquanxing 
	 * @param source_enname
	 * @param sourcetype
	 * @return
	 * @throws HibernateException List
	 */
	public List GetsourceCFGInfo(String source_enname,String sourcetype,String formtype,String formtype1) throws HibernateException
	{
		String str = "";
		try{
			   if(formtype.equals("")&&(!formtype1.equals("")))
			   {
				   str  = "from Sourceconfig t1 where t1.sourceType like '%"+sourcetype+"%' and t1.sourceParentid =(select t2.sourceId from Sourceconfig t2 where t2.sourceType like '%"+formtype1+"%' and t2.sourceName='"+source_enname+"') ";
			   }
			   else if(!formtype.equals("")&&(formtype1.equals("")))
			   {
				   str  = "from Sourceconfig t1 where t1.sourceType like '%"+sourcetype+"%' and t1.sourceParentid =(select t2.sourceId from Sourceconfig t2 where t2.sourceType like '%"+formtype+"%' and t2.sourceName='"+source_enname+"') ";
			   }
			   else if(formtype.equals("")&&(formtype1.equals("")))
			   {
				   return null;
			   }
			   else if(!formtype.equals("")&&(!formtype1.equals("")))
			   {
				  str = "from Sourceconfig t1 where t1.sourceType like '%"+sourcetype+"%' and t1.sourceParentid =(select t2.sourceId from Sourceconfig t2 where t2.sourceType like '%"+formtype+"%' and t2.sourceType like '%"+formtype1+"%' and t2.sourceName='"+source_enname+"') ";
			   }
               System.out.println(str);
 			   HibernateDAO   session    = new HibernateDAO(); 
			   List 			l1	    = session.queryObject(str);
		       return l1;
		  }catch(Exception e)
		  {
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		  }
	}
	/**
	 * 鏃ユ湡 2006-11-24
	 * @author xuquanxing 
	 * @param source_enname
	 * @param sourcetype
	 * @return
	 * @throws HibernateException List
	 * 璇ユ柟娉曟牴鎹紶鍏ョ殑sql璇彞,杩斿洖鏌愬瓧娈电殑鍊�
	 */
	public List GetsourceFieldInfomation(String  nameen) throws HibernateException
	{
		try{
              System.out.println("name="+nameen);
			 String sql = " select t1.sourceId, t2.sourceattEnname,t3.sourceattvalueValue ,t22.sourceattEnname , t32.sourceattvalueValue  "+
			                     " from Sourceconfig t1,Sourceconfigattribute t2,Sourceattributevalue t3,"+
				                 " Sourceconfigattribute t22,Sourceattributevalue t32 "+
				                 " where t1.sourceName='"+nameen+"' and t1.sourceId=t2.sourceattSourceid and"+
				                 " t1.sourceId=t22.sourceattSourceid and t22.sourceattEnname='fieldid' "+ 
				                 " and t2.sourceattEnname='fieldtype' and t22.sourceattId=t32.sourceattvalueAttid "+
				                 " and t2.sourceattId=t3.sourceattvalueAttid ";
			 System.out.println("sql"+sql);
			 HibernateDAO   session    = new HibernateDAO(); 
			   List 			l1	   = session.queryObject(sql);
			   System.out.println("result="+l1.size());
		       return l1;
		  }catch(Exception e)
		  {
				logger.error("SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		  }
	}
	
	
	
	public static Sourceconfig GetsourceInfo(java.lang.Long source_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceconfig t1 where t1.sourceId='"+source_id+"'";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
			 if(l1.size()>0)
			 {
				 Iterator it = l1.iterator();
				 while(it.hasNext())
				 {
					 Sourceconfig sourceconfig = (Sourceconfig)it.next();
					 return sourceconfig;
				 }
			 }
		     return null;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	
	/**
	 * <p>Description:鏍规嵁璧勬簮鑻辨枃鍚嶆煡鎵捐祫婧怚D<p>
	 * @author wangwenzhuo
	 * @creattime 2007-3-10
	 * @param sourcename
	 * @return String
	 */
	public String GetsourceId(String sourcename) 
	{
		try
		{
			String sql	= "select t.sourceId from Sourceconfig t where t.sourceName='"+sourcename+"'";
			List list	= HibernateDAO.queryObject(sql); 
			if(list.size()>0)
		    {
		    	for(Iterator it = list.iterator();it.hasNext();)
		    	{
		    		String sourceId = String.valueOf((Long)it.next());
		    		return sourceId;
		    	}
		    	logger.info("绯荤粺涓笉瀛樺湪璇ヨ祫婧�");
		    	return null;
		    }
			else
		    {
				logger.info("绯荤粺涓笉瀛樺湪璇ヨ祫婧�");
		    	return null;
		    }
		 }
		catch(Exception e)
		{
			logger.error("SourceConfigInfo GetsourceId error:"+e.getMessage());
			return null;
		 }
	}
	
		/*public static void main(String[] args) throws HibernateException {
			

			SourceConfigInfo aa= new SourceConfigInfo();
		//	Sourceconfig sys = SourceConfigInfo.GetsourceInfo(new Long(187));
		//	System.out.println(sys.getSourceCnname());
//			//aa.GetsourceCFGlist(new java.lang.Long(1));
			List list = aa.GetsourceFieldInfomation("accepttime");
			System.out.println("listooo="+list.size());
    		for(Iterator it =list.iterator();it.hasNext();)
            {
               Object[] arrayvalue = (Object[])it.next();
               String fieldid             = (String)arrayvalue[2];
               String fieldtype           = (String)arrayvalue[4];
               System.out.println("fieldid="+fieldid);
            }
	}*/

}
