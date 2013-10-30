package cn.com.ultrapower.eoms.user.config.menu.aroperationdata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.GetFormTableName;
import cn.com.ultrapower.eoms.user.comm.remedyapi.ArEdit;
import cn.com.ultrapower.eoms.user.config.menu.bean.GrandActionConfigBean;

public class GrandActionConfig {

	static final Logger logger 	= (Logger) Logger.getLogger(GrandActionConfig.class);
	ArEdit ae 					= null;
   
    GetFormTableName gftn 		= new GetFormTableName();
    String tableName 			= gftn.GetFormName("grandactionconfig");
    String user      			= gftn.GetFormName("user");
    String password				= gftn.GetFormName("password");
    String server				= gftn.GetFormName("driverurl");
    int port					= Integer.parseInt(gftn.GetFormName("serverport"));
    
    public GrandActionConfig()
    {  
    	try
    	{
    		ae = new ArEdit(user,password, server, port);
    		
    	}catch(Exception e)
    	{
    		logger.info("[200] FormField �޷�l��AR��");
    	}
    }
    
    public boolean grandActionConfigInsert( GrandActionConfigBean formFieldInfo) 
    {
        ArrayList formFieldValue 	= new ArrayList();
        try
        {
        	formFieldValue 			= GrandActionConfigAssociate.associateCondition(formFieldInfo);
        }catch(Exception e)
        {
        	logger.info("[201] FormFieldInsert ���� FormFieldAssociate��������ʱ��?");
        }
        try
        {
        	return ae.ArInster(tableName, formFieldValue);
        }catch(Exception e)
        {
        	logger.info("[202] FormFieldInsert ����AR���в���ʱ��?");
        	return false;
        }
    }
    

    public boolean formFieldModify(String recordID,
    		GrandActionConfigBean formFieldInfo) 
    {
        ArrayList formFieldValue = new ArrayList();
        try
        {
        	formFieldValue = GrandActionConfigAssociate.associateCondition(formFieldInfo);
        	
        }catch(Exception e)
        {
        	logger.info("?");
        }
        try
        {
        	return ae.ArModify(tableName, recordID, formFieldValue);
        	
        }catch(Exception e)
        {
        	logger.info("޸�ʱ��?");
        	return false;
        }
    }
    

    public boolean formFieldDelete(String formFieldID) 
    {
    	try
    	{
    		return ae.ArDelete(tableName, formFieldID);
    		
    	}catch(Exception e)
    	{
    		logger.info("");
    		return false;
    	}
    }
    
}
