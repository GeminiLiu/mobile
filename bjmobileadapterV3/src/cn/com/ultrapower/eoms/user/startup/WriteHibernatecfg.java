package cn.com.ultrapower.eoms.user.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.comm.function.ConfigContents;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.comm.function.GetConfigLastModify;

public class WriteHibernatecfg {

	static final Logger logger = (Logger) Logger.getLogger(Server.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//WriteHibernatecfg writecfg=new WriteHibernatecfg();
		//writecfg.readconfig();
		//writecfg.writeXml11();
	}
	
	public void readconfig()
	{
		try
		{
			Document doc 			= null;
		    SAXReader xmlReader 	= new SAXReader();
		    Function.initPram();
		    String cfgpath			= ConfigContents.CONF+"BasicInfoConfig.xml";
		    doc 					= xmlReader.read(new FileInputStream(cfgpath));
		    Element root			= doc.getRootElement();
		    
		    List paramlist 			= new ArrayList();
		    Element elementDS;
			Iterator iterDS;
			Iterator iterParam;
			
			iterDS = root.elementIterator("tablesource");
			if(iterDS.hasNext()) 
			{
				Element elementParam;
				elementDS	= (Element)iterDS.next();
				iterParam	= elementDS.elementIterator("param");
		    	while (iterParam.hasNext()) 
		    	{
		    		TableBean tableBean = new TableBean();
		    		elementParam		=(Element)iterParam.next();
		    		tableBean.setTablename(elementParam.attributeValue("Tablename"));
		    		tableBean.setpath(elementParam.attributeValue("path"));
		    		paramlist.add(tableBean);
		    	}
			}
			
			writecfg(paramlist);
			doc=null;
		}
		catch(Exception e)
		{
			logger.error("读配置文件失败");
			e.getCause();
		}
	}
	
	
	
	public void writecfg(List paramlist)
	{
		IDataBase dataBase	= null;
		Statement stm		= null;
		ResultSet rs		= null;

		try
    	{
			//实例化一个类型为接口IDataBase类型的工厂类
			dataBase	= DataBaseFactory.createDataBaseClassFromProp();
			stm		= dataBase.GetStatement();
			if(paramlist!=null)
			{
				Iterator it 	= paramlist.iterator();
				String path		= "";
				String Ttable	= "";
				String sql		= "select SCHEMAID from arschema where 1=1";
				String tsql		= "";
				
				while(it.hasNext())
				{
					TableBean tableBean = (TableBean)it.next();					
					path				= tableBean.getpath();
					Ttable				= "";
					tsql=sql+" and name='"+ tableBean.getTablename() +"'";
					rs=dataBase.executeResultSet(stm,tsql);
					//查找remedy中的form对应的T表
					if(rs.next())
					{
						Ttable="T"+rs.getString("SCHEMAID");
					}
					rs.close();
					if(Ttable!=null&&path!=null&&!String.valueOf(Ttable).equals("")&&!String.valueOf(path).equals(""))
					{
						//修改po映射文件
						if(writecfgXml(path,Ttable))
						{
							continue;
						}
						else
						{
							continue;
						}
					}

				}
			}
			stm.close();
			dataBase.closeConn();
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	finally
    	{
    		Function.closeDataBaseSource(rs,stm,dataBase);
    	}
	}
	
	public boolean writecfgXml(String path,String Ttable)
	{
		 Document doc = null;
		 String[] pathsp;
		 String newpath="";
		 pathsp			= path.split("/");
		 if(pathsp.length>0)
		 {
			 for(int i=0;i<pathsp.length;i++)
			 {
				 if(newpath.equals(""))
				 {
					 newpath		= pathsp[i];
				 }
				 else
				 {
					 newpath		= newpath+File.separator+pathsp[i];
				 }
			 }
		 }
		 
		 String strpath=ConfigContents.ROOT+newpath;
		 
		 logger.info("Ttable="+Ttable+"||"+"path="+strpath);
	     SAXReader xmlReader = new SAXReader(false);
	     xmlReader.setValidation(false);
	     xmlReader.setEntityResolver(new org.hibernate.util.DTDEntityResolver());
	     try
	     {
	    	 doc = xmlReader.read(new FileInputStream(strpath));
	    	 Element root	= doc.getRootElement();
			 Iterator iterDS;
			 iterDS = root.elementIterator("class");
			 if(iterDS.hasNext()) 
			 {
			 	Element element=(Element)iterDS.next();
			 	Attribute attribute=element.attribute("table");
			 	attribute.setValue(Ttable);
			 	logger.info("attribute value"+Ttable);
			 }
			 Writer out = new OutputStreamWriter(new FileOutputStream(strpath),"UTF-8");
			 OutputFormat format = OutputFormat.createPrettyPrint();		
			 XMLWriter writer = new XMLWriter(out,format);
			 writer.write(doc);
			 out.close();
			 logger.info("更新配置文件成功！！"+strpath);
			 return true;
	     }
	     catch(Exception e)
	     {
	    	 logger.error("更新配置文件失败！！"+strpath);
	    	 System.out.println("Exception!");
	    	 return false;
	     }	     
	}
	
}
