
package cn.com.ultrapower.eoms.user.comm.remedyapi;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.bean.ArInfo;

import com.remedy.arsys.api.ARException;
import com.remedy.arsys.api.ARServerUser;
import com.remedy.arsys.api.DateInfo;
import com.remedy.arsys.api.Entry;
import com.remedy.arsys.api.EntryFactory;
import com.remedy.arsys.api.EntryID;
import com.remedy.arsys.api.EntryItem;
import com.remedy.arsys.api.FieldID;
import com.remedy.arsys.api.NameID;
import com.remedy.arsys.api.Timestamp;
import com.remedy.arsys.api.Value;

/**
 * @author 
 * @CreatTime 2006-10-16
 * @封装对Remedy Form的增加删除修改方法。
 */

public class ArEdit {
	
	private ARServerUser arserveruser;
    static final Logger logger = (Logger) Logger.getLogger(ArEdit.class);
	public boolean Flag;
	
	/**
	 * @author 
	 * @CreatTime 2006-10-16
	 * @登陆到Remedy服务器
	 */
	
	public ArEdit(String StrName,String StrPassword,String StrServer,int serverPort)
	{
		System.out.println("登陆REMEDY的用户名="+StrName);
		System.out.println("登陆REMEDY密码="+StrPassword);
		System.out.println("登陆REMEDY服务器名"+StrServer);
		System.out.println("登陆REMEDY服务器端口号:"+serverPort);
		try
		{
			arserveruser = RemedyArServer.getArServerUser(StrName, StrPassword, StrServer, serverPort);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			logger.error("登陆REMEDY的用户名="+StrName);
			logger.error("登陆REMEDY密码="+StrPassword);
			logger.error("登陆REMEDY服务器名"+StrServer);
			logger.error("登陆REMEDY服务器端口号:"+serverPort);
			logger.error(e.getMessage());
		}
	}
	/**
	 * @author 
	 * @CreatTime 2006-10-16
	 * @封装对Remedy Form进行数据删除操作。
	 */
	public boolean ArDelete(String CformName,String DelId){
		try
		{
			logger.info("执行数据删除操作");
			logger.info("Form名称="+CformName);
			logger.info("删除记录ID号="+DelId);
			
			EntryItem    aentryitem[] 		= new EntryItem[1];
	        EntryFactory entryfactory 		= EntryFactory.getFactory();
	        Entry        entry 				= (Entry)entryfactory.newInstance();
	        EntryID      obj 				= new EntryID(DelId);
	        
            //	      建立与服务器的关联
	        entry.setContext(arserveruser);
	        //建立记录于Form的关联。
	        entry.setSchemaID(new NameID(CformName));
	        //定位到当前记录
            entry.setEntryID(obj);
	        //对记录进行删除操作
	        entry.remove();
	        logger.info("删除记录成功！"+"ID号："+DelId+"表名称："+CformName);
			return true;
		}
		catch(ARException e)
		{	
			logger.error("删除记录失败！"+"ID号："+DelId+"表名称："+CformName+e.getMessage());
			return false;
		}
	}
	/**
	 * @author 
	 * @CreatTime 2006-10-16
	 * @封装对Remedy Form进行数据增加操作。
	 */
	public boolean ArInster(String CormName,ArrayList CntryItem){
		try
		{
			logger.info("执行数据增加操作");
			logger.info("Form名称="+CormName);
			
			int          Strlen			= CntryItem.size();
			EntryItem    aentryitem[]	= new EntryItem[Strlen];
	        EntryFactory entryfactory 	= EntryFactory.getFactory();
	        Entry        entry 			= (Entry)entryfactory.newInstance();
	        ArInfo       entryitem		= null;
	        
	        String       entryType		= "";
	        long         id  			= 0;
	        String		 fieldvalue		= "";
	        
	        //建立与服务器的关联
	        entry.setContext(arserveruser);
            //建立记录于Form的关联。
	        entry.setSchemaID(new NameID(CormName));
	        
	        for(int i=0;i<CntryItem.size();i++)
            {
        	    entryitem 		= (ArInfo)CntryItem.get(i);
        	    
        	    logger.info("字段类型："+String.valueOf(entryitem.getFlag()));
	            logger.info("字段ID："+entryitem.getFieldID().toString());
	            logger.info("字段值："+String.valueOf(entryitem.getValue()));
	            
        	    entryType 		= String.valueOf(entryitem.getFlag());
	            id				= Long.parseLong(entryitem.getFieldID().toString());
	            fieldvalue 		= String.valueOf(entryitem.getValue());
	            
	            //entryType字段类型：1：字符串2：日期型（数字型）
	            if (entryType=="1"||entryType.equals("1"))
	            {
	                aentryitem[i]=new EntryItem(new FieldID(id),new Value(fieldvalue));
	            }
	            else if(entryType=="2"||entryType.equals("2"))
	            {
	                Timestamp StrTime=new Timestamp(Long.parseLong(fieldvalue));
	                if (!String.valueOf(entryitem.getValue()).equals("null")&&!String.valueOf(entryitem.getValue()).equals(""))
	                {
	                	aentryitem[i]=new EntryItem(new FieldID(id),new Value(StrTime));
            		}
	            }
	            else if(entryType=="13"||entryType.equals("13"))
	            {
	            	DateInfo dateinfo=new DateInfo(Integer.parseInt(fieldvalue));
	                if (!String.valueOf(entryitem.getValue()).equals("null")&&!String.valueOf(entryitem.getValue()).equals(""))
	                {
	                	aentryitem[i]=new EntryItem(new FieldID(id),new Value(dateinfo));
            		}
	            }
	            else
	            {
	            	logger.error("字段类型参数错误");
	            }
            }
	        
	        entryitem = null;
	        
	        entry.setEntryItems(aentryitem);
	        entry.create();
	        logger.info("执行数据插入操作成功：获得记录ID号为："+String.valueOf(entry.getEntryID()));
	        System.out.println();
	        //释放对象entry（记录）
	        System.out.println(String.valueOf(entry.getEntryID()));
			entryfactory.releaseInstance(entry);
			
			return true;
			
		}
		catch(ARException e)
		{
			System.out.println(e.getMessage());
			logger.error("执行数据插入失败"+e.getMessage());
			return false;
		}
	}
	/**
	 * @author 
	 * @CreatTime 2006-10-16
	 * @封装对Remedy Form进行数据增加操作，带有返回值，返回值是记录的Request ID。
	 */
	public String ArInsterR(String CormName,ArrayList CntryItem){
		try
		{
			logger.info("执行数据增加操作");
			logger.info("Form名称="+CormName);
			
			int            Strlen			= CntryItem.size();
			EntryItem      aentryitem[]		= new EntryItem[Strlen];
	        EntryFactory   entryfactory 	= EntryFactory.getFactory();
	        Entry          entry 			= (Entry)entryfactory.newInstance();
	        String         entryType		= "";
	        long           id				= 0;
	        String		   fieldvalue		= "";
	        
	        entry.setContext(arserveruser);
	        entry.setSchemaID(new NameID(CormName));

	        ArInfo entryitem=null;
	        for(int i=0;i<CntryItem.size();i++)
            {
        	    entryitem 	= (ArInfo)CntryItem.get(i);
        	    
        	    logger.info("字段类型："+String.valueOf(entryitem.getFlag()));
	            logger.info("字段ID："+entryitem.getFieldID().toString());
	            logger.info("字段值："+String.valueOf(entryitem.getValue()));
	            
        	    entryType 	= String.valueOf(entryitem.getFlag());
	            id			= Long.parseLong(entryitem.getFieldID().toString());
	            fieldvalue 	= String.valueOf(entryitem.getValue());
	            
                //entryType字段类型：1：字符串2：日期型（数字型）
	            if (entryType=="1"||entryType.equals("1"))
	            {
	                aentryitem[i]=new EntryItem(new FieldID(id),new Value(fieldvalue));
	            }
	            else if(entryType=="2"||entryType.equals("2"))
	            {
	                Timestamp StrTime=new Timestamp(Long.parseLong(fieldvalue));
	                if (!String.valueOf(entryitem.getValue()).equals("null")&&!String.valueOf(entryitem.getValue()).equals(""))
	                {
	                    aentryitem[i]=new EntryItem(new FieldID(id),new Value(StrTime));
            		}
	            }
	            
            }
	        entryitem=null;
	        entry.setEntryItems(aentryitem);
	        entry.create();
	        logger.info("执行数据插入操作成功：获得记录ID号为："+String.valueOf(entry.getEntryID()));
	        //释放对象entry（记录）
			entryfactory.releaseInstance(entry);
			
			return String.valueOf(entry.getEntryID());
			
		}catch(ARException e){
			logger.error("插入失败"+e.getMessage());
			return null;
		}
	}
	/**
	 * @author 
	 * @CreatTime 2006-10-16
	 * @封装对Remedy Form进行数据修改操作，带有返回值，返回值是记录的Request ID。
	 */
	public boolean ArModify(String CformName,String CmodEntryId,ArrayList CntryItem){
		try
		{
			logger.info("执行数据修改操作");
			logger.info("Form名称="+CformName);
			logger.info("记录id号="+CmodEntryId);
			
			int Strlen=CntryItem.size();
			EntryItem         aentryitem[] 	= new EntryItem[Strlen];
	        EntryFactory      entryfactory 	= EntryFactory.getFactory();
	        Entry             entry 		= (Entry)entryfactory.newInstance();
	        EntryID           obj 			= new EntryID(CmodEntryId);
	        
	        String         entryType		= "";
	        long           ids				= 0;
	        String		   fieldvalue		= "";
	        
	        entry.setContext(arserveruser);
	        entry.setSchemaID(new NameID(CformName));

	        
            entry.setEntryID(obj);
            ArInfo entryitem=null;
            
            for(int i=0;i<CntryItem.size();i++)
            {
            	entryitem=(ArInfo)CntryItem.get(i);
            	
            	logger.info("字段类型："+String.valueOf(entryitem.getFlag()));
	            logger.info("字段ID："+entryitem.getFieldID().toString());
	            logger.info("字段值："+String.valueOf(entryitem.getValue()));
	            
            	entryType  	= String.valueOf(entryitem.getFlag());
            	ids    		= Long.parseLong(entryitem.getFieldID().toString());
            	fieldvalue	= String.valueOf(entryitem.getValue());
            	
            	if (entryType=="1"||entryType.equals("1"))
            	{
            		aentryitem[i]=new EntryItem(new FieldID(ids),new Value(fieldvalue));
            	}
            	else if(entryType=="2"||entryType.equals("2"))
            	{          		
            		if(fieldvalue != null && !"".equals(fieldvalue))
            		{
            			Timestamp StrTime = null;
            			StrTime = new Timestamp(Long.parseLong(fieldvalue));
            			
                		aentryitem[i]=new EntryItem(new FieldID(ids),new Value(StrTime));

            		} 
            		
            	}
            	logger.info("ids="+ids+"value="+String.valueOf(entryitem.getValue())+"entryType="+entryType);
            }
            
	        entry.setEntryItems(aentryitem);
	        entry.store();
	        logger.info("执行数据修改操作成功");
	        return true;
		}
		catch(ARException e)
		{
			logger.error("执行数据修改操作失败"+e.getMessage());
			return false;
		}
	}

}
