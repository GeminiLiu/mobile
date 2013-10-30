package cn.com.ultrapower.eoms.user.config.menu.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class MenuFindInfoList {
	static final Logger logger = (Logger) Logger.getLogger(MenuFindInfoList.class);
	/**
	 * ���� 2006-10-21
	 * ���� shigang
	 * @return void
	 */
	static Session session=null;
//	public MenuFindInfoList(){
//		try{
//				session = HibernateSessionFactory.currentSession();
//				
//			}catch(Exception e){
//				logger.error("330 MenuFindInfoList session error:"+e.toString());
//			}
//	}

	public List getMenu() throws HibernateException{
		try{
			 String 		sql	    = "from SysDropDownConfigpo";
			 List 			l1	    = HibernateDAO.queryObject(sql);
			return l1;
		}catch(Exception e){
			logger.error("331 MenuRequestAction.getMenu error:"+e.toString());
			return null;
		}
	}
	//���id����������
	public List getMenuId(String getId) throws HibernateException{
		try{
			 String 		sql	 	 = "from SysDropDownConfigpo t where t.c1='"+getId+"'";
			 List 			l1	    = HibernateDAO.queryObject(sql);
			
			 return l1;
		}catch(Exception e){
			logger.error("332 MenuRequestAction.getMenuId error:"+e.toString());
			return null;
		}
	}

	
	public List getMenuListInfo(String FieldName,String TableName) throws HibernateException
	{
		String 		sql	 	 = "from SysDropDownConfigpo where c620000015='"+ FieldName +"' and c620000019='"+ TableName +"'";
	
		try
		{
			 List 			l1	    = HibernateDAO.queryObject(sql);
			 
			logger.info("fieldname1:"+FieldName+"tablename1:"+TableName);
			return l1;
		}
		catch(Exception e)
		{
			logger.error("333 MenuRequestAction.getMenuList error:"+e.toString());
			return null;
		}
	}
	
	public static void main(String[] args) throws HibernateException {

	}
}
