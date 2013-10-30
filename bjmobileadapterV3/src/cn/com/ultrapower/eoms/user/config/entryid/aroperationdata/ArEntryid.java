package cn.com.ultrapower.eoms.user.config.entryid.aroperationdata;
import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.comm.function.Log;
import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
/**
�ϳ�ARapi  ArEntryid

�Ѵ���4��ֵ����entryidInster����ݷ���associatelnster�д��?�ڷ���
 */
public class ArEntryid 
{
   private String GetID_ID;
   private String GetID_TableName;
   private String GetID_IDName;
   private String GetID_IDNameValue;
   private String GetID_Desc;
   
   private String strId;
   private String strValue;
   private String strFlag;

   GetFormTableName tablename 	= new GetFormTableName();
   String driverurl 			= tablename.GetFormName("driverurl");
   String user 					= tablename.GetFormName("user");
   String password 				= tablename.GetFormName("password");
   int serverport 				= Integer.parseInt(tablename.GetFormName("serverport"));
   String TBLName 				= tablename.GetFormName("sysgetid");
   
   static final Logger logger = (Logger) Logger.getLogger(ArEntryid.class);
   
//   ServerProperties servervalue=new ServerProperties();
//   String url=servervalue.url;
//   String user=servervalue.user;
//   String password=servervalue.password;
//   int  serverport=servervalue.serverport;
 
//   String url="192.168.10.80";
//   String user="Demo";
//   String password="";
//   int  serverport=2722;
   ArEdit aredit=new ArEdit(user,password,driverurl,serverport);
   
   /**
   ���Array
   @return boolean
   @roseuid 452B429702CE
    */
   public ArrayList entryidInster(ArrayList value) 
   {
	   try{
		   ArrayList returnList = new ArrayList();
	    	ArInfo Info = new ArInfo();
	    	Info.setFieldID("620000002");
	    	Info.setValue(value.get(0).toString());
	    	Info.setFlag("1");
	    	returnList.add(Info);
	    	
	    	ArInfo Info1 = new ArInfo();
	    	Info1.setFieldID("620000003");
	    	Info1.setValue(value.get(1).toString());
	    	Info1.setFlag("1");
	    	returnList.add(Info1);
	    	
	    	ArInfo Info2 = new ArInfo();
	    	Info2.setFieldID("620000004");
	    	Info2.setValue(value.get(2).toString());
	    	Info2.setFlag("1");
	    	returnList.add(Info2);
	    	return returnList;
	    	
	   }catch(Exception e){
		   logger.error("301 arentryid.entryidInste error:"+e.toString());
		   return null;
	   }
   }
   public ArrayList entryidModify(ArrayList value) 
   {
	   try{
		   ArrayList returnList = new ArrayList();
	    	ArInfo Info = new ArInfo();
	    	Info.setFieldID("620000002");
	    	Info.setValue(value.get(1).toString());
	    	Info.setFlag("1");
	    	returnList.add(Info);
	    	
	    	ArInfo Info1 = new ArInfo();
	    	Info1.setFieldID("620000003");
	    	Info1.setValue(value.get(2).toString());
	    	Info1.setFlag("1");
	    	returnList.add(Info1);
	    	
	    	ArInfo Info2 = new ArInfo();
	    	Info2.setFieldID("620000004");
	    	Info2.setValue(value.get(3).toString());
	    	Info2.setFlag("1");
	    	returnList.add(Info2);
	    	return returnList;
	    	
	   }catch(Exception e){
		   logger.error("302 arentryid.entryidModify error:"+e.toString());
		   return null;
	   }
   }
     
   /**
   @roseuid 452C9D13009C
    */
   public synchronized void associateInster(ArrayList value) 
   {  
	   try{
			ArrayList array=entryidInster(value);
			if (aredit.ArInster(TBLName,array)){
				logger.info("303 arentryid.associate info: InsterITEOMS_SYSGETID");
			}else{
				logger.info("304 arentryid.associate info: InsterITEOMS_SYSGETID");
			}
		}catch(Exception e){
			logger.error("305 arentryid.associateInster"+e.toString());
		}
		
   }
   
   /**
   @roseuid //ֻ��ֵ��������������//�����������1��ʼ
    */
   
   public synchronized  void associateModify(ArrayList value) 
   {	
	   //��split����ݽ��зָ�
	   try
	   {
		   	ArrayList array=entryidModify(value);
			if (aredit.ArModify(TBLName,value.get(0).toString(),array)){
				logger.info("306 arentryid.associate info: InsterITEOMS_SYSGETID ");
				System.out.println("306 arentryid.associate info: InsterITEOMS_SYSGETID ");
			}
			else
			{
				logger.info("307 arentryid.associate info: InsterITEOMS_SYSGETID");
				System.out.println("307 arentryid.associate info: InsterITEOMS_SYSGETID ");
			}
	   	}
	   catch(Exception e)
	   {
			logger.error("308 arentryid.associateModify "+e.toString());
			System.out.println("308 arentryid.associateModify "+e.toString());
		}
   }
   
}
