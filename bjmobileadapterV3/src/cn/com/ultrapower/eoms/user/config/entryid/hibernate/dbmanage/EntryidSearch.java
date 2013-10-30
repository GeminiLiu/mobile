/**
 * shigang 
 * ��صĲ�ѯ
 */
package cn.com.ultrapower.eoms.user.config.entryid.hibernate.dbmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import cn.com.ultrapower.eoms.user.config.entryid.aroperationdata.ArEntryid;
import cn.com.ultrapower.eoms.user.config.entryid.hibernate.po.Sysgetidpo;
import cn.com.ultrapower.eoms.user.comm.hibernatesession.HibernateDAO;

public class EntryidSearch 
{
	private String strWhere="from Sysgetidpo t where t.c620000002=";
   /**
   @return List
   @roseuid 452B47CC006D
   ͨ��hql��ѯ����LIST���͵����
   strwhereΪ�����Ϊ����4�����ֵ
    */
   static final Logger logger = (Logger) Logger.getLogger(EntryidSearch.class);
   public ArrayList entryidFind(String TableName,String IDName) 
   {
	 String tempwhere = strWhere+"'"+TableName+"'" +"and t.c620000003='"+IDName+"'";
	  // String tempwhere="from Sysgetidpo";
	 System.out.println(tempwhere);
	   try
	   {
		   List 		    	l1 = HibernateDAO.queryObject(tempwhere);
			
			if (!l1.isEmpty()){//�շ���list ��֮���ؿ�
				System.out.print("-----------------------");
				Iterator strlist=l1.iterator();
				ArrayList listreturn = new ArrayList();
				while(strlist.hasNext()){
					Sysgetidpo iteomssysgetid=(Sysgetidpo)strlist.next();
					listreturn.add(iteomssysgetid.getC1());
					listreturn.add(iteomssysgetid.getC620000004());					
				}
				
				return listreturn;
				
			}else
			{
				logger.info("309"+tempwhere+"IteomsSysgetid Ϊ��");
				return null;
			}
	   }
	   catch(Exception e)
	   {
		   logger.error("309"+tempwhere+"IteomsSysgetid error:"+e.getMessage());
	   }
	   
    return null;
   }
}
