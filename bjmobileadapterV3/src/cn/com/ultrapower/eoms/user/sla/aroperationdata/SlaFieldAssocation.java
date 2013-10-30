package cn.com.ultrapower.eoms.user.sla.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.sla.bean.SlaDefineBean;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
/**
 * @author xuquanxing/徐全星
 *@version1.0
 *该类用于拼接sla定义的条件,以插入到remedy数据库中
 */

public class SlaFieldAssocation {
	  
    static final Logger logger = (Logger) Logger.getLogger(SlaFieldAssocation.class);
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * 
	 */
	   private  ArInfo InitSla_name( SlaDefineBean sladefinebean)
	   {
		   try
		   {
			   ArInfo arinfoslaname = new ArInfo();
			   arinfoslaname.setFieldID("600000001");
			   arinfoslaname.setValue(sladefinebean.getSla_name());
			   arinfoslaname.setFlag("1");
			   return arinfoslaname;			   
		   }catch(Exception e)
		   {
			  logger.error("[124]SlaFieldAssocation:InitSla_name初始化sla名称时发生异常"+e.getMessage()) ;
			  return null;
		   }
	   }
	   
	 
	/**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * 
	 */
	private ArInfo InitSla_startdatetime( SlaDefineBean sladefinebean)
	{
		 try
		 {
			 ArInfo startdatetime = new ArInfo();
			 startdatetime.setFieldID("600000002");
			 startdatetime.setValue(sladefinebean.getSla_startdatetime());
			 startdatetime.setFlag("2");
			 logger.info("startdatetime::"+sladefinebean.getSla_startdatetime());
			 logger.info("startdatetime1::"+startdatetime.getValue());
			 return startdatetime;
		 }catch(Exception e)
		 {
			  logger.error("[125]SlaFieldAssocation:InitSla_startdatetime初始化sla开始时间时发生异常"+e.getMessage()) ;
		      return null;
		 }
	   }
       
	
	 /**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * 	 */
	
	private ArInfo InitSla_endtdatetime( SlaDefineBean sladefinebean)
	{
		 try
		 {
			 ArInfo enddatetime = new ArInfo();
			 enddatetime.setFieldID("600000003");
			 enddatetime.setValue(sladefinebean.getSla_enddatetime());
			 enddatetime.setFlag("2");
			 logger.info("endtdatetime::"+sladefinebean.getSla_enddatetime());
			 return enddatetime;
		 }catch(Exception e)
		 {
			  logger.error("[126]SlaFieldAssocation:InitSla_endtdatetime初始化sla结束时间时发生异常"+e.getMessage()) ;
		      return null;
		 }
	   }
	   
	   /**
	 * ���� 2006-10-13
	 * @author xuquanxing
	 * @param sladefinebean
	 * @return ArrayList
	 * 
	 * 	 */
	private ArInfo InitSla_state( SlaDefineBean sladefinebean)
	{
		 try
		 {
			 ArInfo state = new ArInfo();
			 state.setFieldID("600000004");
			 state.setValue(sladefinebean.getSla_status());
			 state.setFlag("1");
			 return state;
		 }catch(Exception e)
		 {
			  logger.error("[127]SlaFieldAssocation:InitSla_state初始化sla状态时发生异常"+e.getMessage()) ;
		      return null;
		 }
	   }
	   
	
	   /**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * 
	 */
	   private ArInfo InitSla_apptable( SlaDefineBean sladefinebean)
	   {
		   try
		   {
			   ArInfo apptable = new ArInfo();
			   apptable.setFieldID("600000005");
			   apptable.setValue(sladefinebean.getSla_apptable());
			   apptable.setFlag("1");
			   return apptable;
		   }catch(Exception e)
		   {
				  logger.error("[128]SlaFieldAssocation:InitSla_apptable初始化sla应用表单时发生异常"+e.getMessage()) ;
			      return null;
		   }		  
	   }
	   
	  
	   /**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 *
	 */
	private ArInfo InitSla_appcommstartquery( SlaDefineBean sladefinebean)
	{
		 try
		 {
			 ArInfo appcommstartquery = new ArInfo();
			 appcommstartquery.setFieldID("600000006");
			 appcommstartquery.setValue(sladefinebean.getSla_appcommstartquery());
			 appcommstartquery.setFlag("1");
			 return appcommstartquery;
		 }catch(Exception e)
		 {
			  logger.error("[129]SlaFieldAssocation:InitSla_appcommstartquery初始化sla开始条件时发生异常"+e.getMessage()) ;
		      return null;
		 }
	   }
	  
	  
	   /**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * 
	 */
	private ArInfo InitSla_appcommendquery( SlaDefineBean sladefinebean)
	{
		 try
		 {
			 ArInfo appcommendquery = new ArInfo();
			 appcommendquery.setFieldID("600000007");
			 appcommendquery.setValue(sladefinebean.getSla_appcommendquery());
			 appcommendquery.setFlag("1");
			 return appcommendquery;
		 }catch(Exception e)
		 {
			  logger.error("[130]SlaFieldAssocation：InitSla_appcommendquery初始化sla结束条件时发生异常"+e.getMessage()) ;
		      return null;
		 }
	   }
	   
	   
	   /**
	 * ���� 2006-10-13
	 * @author xuquanxing/��ȫ�� 
	 * @param sladefinebean
	 * @return ArrayList
	 * �������
	 */
	   private ArInfo InitSla_usercommquery( SlaDefineBean sladefinebean)
	   {
			 try
			 {
					ArInfo usercommquery = new ArInfo();
					usercommquery.setFieldID("600000008");
					usercommquery.setValue(sladefinebean.getSla_usercommquery());
					usercommquery.setFlag("1");
					return usercommquery;		   
			 }catch(Exception e)
			 {
				  logger.error("[131]SlaFieldAssocation：InitSla_usercommquery初始化sla用户条件时发生异常"+e.getMessage()) ;
			      return null;
			 }
	   }
	   
	  
	   
	   /**
		 * ���� 2006-10-13
		 * @author xuquanxing/��ȫ�� 
		 * @param sladefinebean
		 * @return ArrayList
		 * ս��
		 */
	   public ArrayList assocationResult( SlaDefineBean sladefinebean)
	   {
		  try
		   {
			   ArInfo sla_name               = InitSla_name( sladefinebean);
			   ArInfo sla_startdatetime      = InitSla_startdatetime(sladefinebean);
			   ArInfo sla_enddatetime        = InitSla_endtdatetime(sladefinebean);
			   logger.info("endtdatetime1::"+sla_enddatetime.getValue());
			   ArInfo sla_status             = InitSla_state(sladefinebean);
			   ArInfo sla_apptable           = InitSla_apptable(sladefinebean);
			   ArInfo sla_appcommstartquery  = InitSla_appcommstartquery(sladefinebean);
			   ArInfo sla_appcommendquery    = InitSla_appcommendquery(sladefinebean);
			   ArInfo sla_usercommquery      = InitSla_usercommquery(sladefinebean);
	           ArrayList result              = new ArrayList();
	           result.add(sla_name);
	           result.add(sla_startdatetime);
	           result.add(sla_enddatetime);
	           result.add(sla_status);
	           result.add(sla_apptable);
	           result.add(sla_appcommstartquery);
	           result.add(sla_appcommendquery);
	           result.add(sla_usercommquery);
	           return result;
		   }catch(Exception e)
		   {
				  logger.error("[132]SlaFieldAssocation：assocationResult封装sla信息时发生异常"+e.getMessage()) ;
			      return null;
		   }
	   }
	   
}
