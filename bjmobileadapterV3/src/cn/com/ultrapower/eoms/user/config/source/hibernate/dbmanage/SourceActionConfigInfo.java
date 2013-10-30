package cn.com.ultrapower.eoms.user.config.source.hibernate.dbmanage;

import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;
import cn.com.ultrapower.eoms.user.config.source.hibernate.po.Sourceactionconfig;
public class SourceActionConfigInfo {

	/**
	 * date 2006-11-10
	 * author shigang
	 * @param args
	 * @return void
	 */	
	//查询相关所有数据

	static final Logger logger = (Logger) Logger.getLogger(SourceActionConfigInfo.class);


	public Object GetSourceActionload(Long source_id) throws HibernateException
	{
		try{
			 HibernateDAO   session  = new HibernateDAO(); 
			 Object 			l1	 = session.load(Sourceactionconfig.class,source_id);
		     return l1;
		 }catch(Exception e){
				logger.error("384 GetSourceActionload GetFieldList error:"+e.toString());
				return null;
		 }
	}
	public List GetSourceActionQry(String sourceaction_typeflag) throws HibernateException
	{
		try{
			
			 HibernateDAO   session  = new HibernateDAO(); 
			 String Sql="from Sourceactionconfig t where t.sourceactionTypeflag="+sourceaction_typeflag;
			 List SA=session.queryObject(Sql);
		     return SA;
		 }catch(Exception e){
				logger.error("383 GetSourceActionQry GetFieldList error:"+e.toString());
				return null;
		 }
	}
	public static void main(String[] args) throws HibernateException {
		// TODO Auto-generated method stub
	}
}