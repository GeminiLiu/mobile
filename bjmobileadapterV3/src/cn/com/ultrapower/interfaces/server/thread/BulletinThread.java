package cn.com.ultrapower.interfaces.server.thread;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.ultrapower.eoms.processSheet.CustomProcessInterface;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

import com.ultrapower.eoms.common.util.CalendarUtils;

public class BulletinThread  implements Job {
	
	private CustomProcessInterface customProcessInterface = new CustomProcessInterface();
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		System.out.println("公告扫描开始");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp(); 
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:EL_UVS_BULT_To_Interface_Old");
		String sqlCycle = "SELECT t.c802043006 as title,t.c802043010 as urgentDegree,t.c802043012 as availTime , c802043011 as noticeDesc, t.c1 as baseId  FROM "+ strTblName + " t WHERE t.c802043009 = 0 " ;
		System.out.println(sqlCycle);
		// 轮询
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		Statement upStm=null;
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sqlCycle);
			while(resultSet.next()) {
				String title = FormatString.CheckNullString(resultSet.getString(1)).trim();
				String urgentDegree = FormatString.CheckNullString(resultSet.getString(2)).trim();
				String availTime = FormatString.CheckNullString(resultSet.getString(3)).trim();
				String noticeDesc = FormatString.CheckNullString(resultSet.getString(4)).trim();
				String baseId = FormatString.CheckNullString(resultSet.getString(5)).trim();
				
				// 调用服务
				
				
				
				
				
				
				String endpoint = "http://10.224.10.20/E-OMS_1860/Bulletin.asmx";    
				String nameSpace="http://service.eoms.chinamobile.com/Bulletin";
				
	            String p_CallOperationName = "newBulletin";
	            Service   service   =   new   Service();   
	    		Call call = null;
	    		call = (Call)service.createCall();
	    		call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
	    		call.setOperationName(new QName(nameSpace, "newBulletin"));
    			call.setUseSOAPAction(true);
	    		call.setSOAPActionURI(nameSpace+"/"+p_CallOperationName);

				org.apache.axis.description.OperationDesc oper = new org.apache.axis.description.OperationDesc();
				oper.addParameter(new javax.xml.namespace.QName(nameSpace, "title"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
				oper.addParameter(new javax.xml.namespace.QName(nameSpace, "urgentDegree"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
				oper.addParameter(new javax.xml.namespace.QName(nameSpace, "availTime"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
				oper.addParameter(new javax.xml.namespace.QName(nameSpace, "noticeDesc"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
				oper.setReturnType(XMLType.XSD_STRING);
				
				call.setOperation(oper);
				String result = (String) call.invoke(new Object[]{title,urgentDegree,CalendarUtils.unixto_datetime(availTime),noticeDesc});
				
				
				
				System.out.println("title:"+title+";urgentDegree:"+urgentDegree +"availTime:"+CalendarUtils.unixto_datetime(availTime) + ";noticeDesc:"  + noticeDesc + "返回结果:" + result);
				
				
				
				
				
				
		        	
	            if("".equals(result)){//调用成功， 更新数据
	            	String updateSql = "update " + strTblName  + " t set t.c802043009 = 1 where t.c1 = '" + baseId + "'";
	            	upStm = m_dbConsole.GetStatement();
					int a = m_dbConsole.executeNonQuery(upStm,updateSql);
					m_dbConsole.commit();
					if(a>0){
						System.out.println("公告派发success");
					}
	            }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
	}	
	}
