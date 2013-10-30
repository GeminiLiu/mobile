package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceattributeconfig;

public class SourceAttributeConfigInfo {

	/**
	 * date 2006-11-10
	 * author shigang
	 * @param args
	 * @return void
	 */	
	//查询相关所有数据

	static final Logger logger = (Logger) Logger.getLogger(SourceAttributeConfigInfo.class);

	public List GetAttributeConfiglist() throws HibernateException
	{
		try{
			 String 		sql	 	= "from Sourceconfig";
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 GetAttributeConfiglist error:"+e.toString());
				return null;
		 }
	}
	//把id传过来当条件
	public List GetSourceAttributeConfiglist(java.lang.Long source_id) throws HibernateException
	{
		try{
			 String 		sql	 	 = "from Sourceattributeconfig where sourceattcfgId="+source_id;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	//拼成的sql做为条件
	public List GetSourceAttributeConfiglist(String sql) throws HibernateException
	{
		try{
			// String 		sql	 	 = "from Sourceattributeconfig where sourceattcfgId="+source_id;
			 HibernateDAO   session = new HibernateDAO(); 
			 List 			l1	    = session.queryObject(sql);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	public Object GetSourceAttributeConfigload(Long source_id) throws HibernateException
	{
		try{
			// String 		sql	 	 = "from Sourceattributeconfig where sourceattcfgId="+source_id;
			 HibernateDAO   session  = new HibernateDAO(); 
			 Object 			l1	 = session.load(Sourceattributeconfig.class,source_id);
		     return l1;
		 }catch(Exception e){
				logger.error("356 SourceConfigInfo GetFieldList error:"+e.toString());
				return null;
		 }
	}
	public static void main(String[] args) throws HibernateException {
		// TODO Auto-generated method stub
		
		SourceAttributeConfigInfo sourceAttributeConfigInfo=new SourceAttributeConfigInfo();
		Sourceattributeconfig sourceattributlist=(Sourceattributeconfig)sourceAttributeConfigInfo.GetSourceAttributeConfigload(new Long(1));
		System.out.println(sourceattributlist.getSourceattcfgCnname());
}
}