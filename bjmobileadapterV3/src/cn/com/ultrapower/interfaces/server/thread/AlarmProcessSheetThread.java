package cn.com.ultrapower.interfaces.server.thread;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.NMSAlarmInterface;
import cn.com.ultrapower.eoms.processSheet.InterfaceCfg;
import cn.com.ultrapower.eoms.util.CalendarUtil;

import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class AlarmProcessSheetThread implements Job {

	private NMSAlarmInterface nMSalarmInterface = new NMSAlarmInterface();
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		nMSalarmInterface = new NMSAlarmInterface();
		System.out.println("网管告警工单状态同步扫描开始");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(nMSalarmInterface.getPrefix()+ Contents.BMCSPLIT + nMSalarmInterface.getQuatzTable());
		String sqlCycle = nMSalarmInterface.getQuatzSelect().replace(Contents.QUATZTABLE,strTblName);
		
		// 轮询
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet resultSet =null;
		Statement upStm=null;
		ResultSet upResultSet =null;
		ResultSet rs =null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			stm = m_dbConsole.GetStatement();
			resultSet = m_dbConsole.executeResultSet(stm,sqlCycle);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while(resultSet.next()) {
				StringBuffer sb =  new StringBuffer("<opDetail>");
				String c1 = FormatString.CheckNullString(resultSet.getString(1)).trim();
				String alarmId = FormatString.CheckNullString(resultSet.getString(2)).trim();
				String sheetNo = FormatString.CheckNullString(resultSet.getString(3)).trim();
				String sheetStatus = FormatString.CheckNullString(resultSet.getString(4)).trim();
				long statusTime =resultSet.getLong(5);
				java.util.Calendar cl = java.util.Calendar.getInstance();
				cl.setTimeInMillis(statusTime*1000l);
				
				sb.append("<recordInfo>");
				sb.append("<fieldInfo>");
					sb.append("<fieldChName>");
					sb.append("网管告警ID");
					sb.append("</fieldChName>");
					
					sb.append("<fieldEnName>");
					sb.append("alarmId");
					sb.append("</fieldEnName>");
					
					sb.append("<fieldContent>");
					sb.append(alarmId);
					sb.append("</fieldContent>");
				sb.append("</fieldInfo>");
				
				sb.append("<fieldInfo>");
					sb.append("<fieldChName>");
					sb.append("EOMS工单ID");
					sb.append("</fieldChName>");
					
					sb.append("<fieldEnName>");
					sb.append("sheetNo");
					sb.append("</fieldEnName>");
					
					sb.append("<fieldContent>");
					sb.append(sheetNo);
					sb.append("</fieldContent>");
				sb.append("</fieldInfo>");
				
				sb.append("<fieldInfo>");
					sb.append("<fieldChName>");
					sb.append("工单状态");
					sb.append("</fieldChName>");
					
					sb.append("<fieldEnName>");
					sb.append("sheetStatus");
					sb.append("</fieldEnName>");
					
					sb.append("<fieldContent>");
					sb.append(sheetStatus);
					sb.append("</fieldContent>");
				sb.append("</fieldInfo>");
				
				sb.append("<fieldInfo>");
					sb.append("<fieldChName>");
					sb.append("状态时间");
					sb.append("</fieldChName>");
					
					sb.append("<fieldEnName>");
					sb.append("statusTime");
					sb.append("</fieldEnName>");
					
					sb.append("<fieldContent>");
					sb.append(sf.format(cl.getTime()));
					sb.append("</fieldContent>");
					sb.append("</fieldInfo>");
				sb.append("</recordInfo></opDetail>");
				
				String sheetno="";
				if("网管系统".equals(resultSet.getString(6))){
					// 调用服务
					String url = nMSalarmInterface.getUrls();
					Map<String,String> methodmap = nMSalarmInterface.getMethodMap(Contents.SynsyncSheetState);
					String nameSpace="http://service.eoms.chinamobile.com/SheetStateSync";
					
		            String p_CallOperationName = Contents.SynsyncSheetState;
		            Service   service   =   new   Service();   
		    		Call call = null;
		    		call = (Call)service.createCall();
		    		call.setTargetEndpointAddress(new java.net.URL(url)); 
		    		call.setOperationName(new QName(nameSpace, Contents.SynsyncSheetState));
	    			call.setUseSOAPAction(true);
		    		call.setSOAPActionURI(nameSpace+"//"+p_CallOperationName);

					org.apache.axis.description.OperationDesc oper = new org.apache.axis.description.OperationDesc();
					oper.addParameter(new javax.xml.namespace.QName(nameSpace, "serSupplier"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
					oper.addParameter(new javax.xml.namespace.QName(nameSpace, "serCaller"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
					oper.addParameter(new javax.xml.namespace.QName(nameSpace, "callerPwd"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
					oper.addParameter(new javax.xml.namespace.QName(nameSpace, "callTime"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
					oper.addParameter(new javax.xml.namespace.QName(nameSpace, "opDetail"), new javax.xml.namespace.QName(nameSpace, "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
					oper.setReturnType(XMLType.XSD_STRING);
					
					call.setOperation(oper);
					String result = (String) call.invoke(new Object[]{methodmap.get(Contents.SUPPLIER),
		            		methodmap.get(Contents.CALLER),
		            		methodmap.get(Contents.CALLERPWD),
		            		sf.format(new Date()),
		            		sb.toString()
		            		});
					
					
					sheetno = result.substring(result.indexOf("alarmID")+"alarmID=".length(), result.indexOf("errList")-1);
				}else if("数据网管".equals(resultSet.getString(6))){//数据网管接口
					// 调用服务
					System.out.println("数据网管接口调用");
		            Service   service   =   new   Service();   
		    		Call call = null;
		    		call = (Call)service.createCall();
		    		call.setTargetEndpointAddress("http://10.223.84.5:9000/SheetStateSync");
		            call.setOperationName("syncSheetState");//方法名   
		            call.setReturnType(XMLType.XSD_STRING);
		            call.addParameter("serSupplier",XMLType.XSD_STRING, ParameterMode.IN); 
		            call.addParameter("serCaller",XMLType.XSD_STRING, ParameterMode.IN); 
		            call.addParameter("callerPwd",XMLType.XSD_STRING, ParameterMode.IN); 
		            call.addParameter("callTime",XMLType.XSD_STRING, ParameterMode.IN); 
		            call.addParameter("opDetail",XMLType.XSD_STRING, ParameterMode.IN); 
		    		String result = (String) call.invoke(new Object[]{"NX_CRM",
		            		"NX_EOMS",
		            		"password",
		            		sf.format(new Date()),
		            		sb.toString()
		            		});
					
					
					sheetno = result.substring(result.indexOf("alarmID")+"alarmID=".length(), result.indexOf("errList")-1);
					
					
				}else if("传输网管".equals(resultSet.getString(6))){//传输网管接口
					System.out.println("传输网管接口调用");
					
					
					
					
				}else if("动力环境".equals(resultSet.getString(6))){//动力环境接口
					System.out.println("动力环境接口调用");
					
					
					
					
				}
				
	            if(sheetno.length()>0){//调用成功， 更新数据
	            	String updateSql = generateUpdateSql(nMSalarmInterface.getPrefix()+ Contents.BMCSPLIT + nMSalarmInterface.getQuatzTable() ,c1);
	            	upStm = m_dbConsole.GetStatement();
					int a = m_dbConsole.executeNonQuery(upStm,updateSql);
					
	            }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.commit();
			m_dbConsole.closeConn();
		}
	}
		

	/**
	 * 通过配置文件，生成更新的sql
	 * @param sheetName
	 * @param dealType
	 * @param serialNum
	 * @return
	 */
	private String generateUpdateSql(String sheetName,String id){
		String sql = nMSalarmInterface.getQuatzUpdate();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(sheetName);
		return sql.replace(Contents.QUATZTABLE,strTblName).replace("#id#",id);
	}

}
