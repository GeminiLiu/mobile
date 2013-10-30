package cn.com.ultrapower.interfaces.server.thread;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.ultrapower.eoms.common.util.CalendarUtils;

import cn.com.ultrapower.eoms.processSheet.Contents;
import cn.com.ultrapower.eoms.processSheet.CustomProcessInterface;
import cn.com.ultrapower.eoms.processSheet.InterfaceCfg;
import cn.com.ultrapower.eoms.util.CalendarUtil;
import cn.com.ultrapower.interfaces.client.CrmProcessSheetPortType;
import cn.com.ultrapower.interfaces.util.AttachUtil;
import cn.com.ultrapower.interfaces.util.IDatabaseDAO;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.interfaces.util.*;
public class BulletinProcess {
	private CustomProcessInterface customProcessInterface = new CustomProcessInterface();
	public static void callprocess(){
		System.out.println("公告扫描开始");
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName("WF:EL_UVS_BULT_To_Interface");
//		String sqlCycleP = "SELECT t.c802043006 as title,t.c802043010 as urgentDegree,t.c802043012 as availTime , c802043011 as noticeDesc, t.c1 as baseId  FROM "+ strTblName + " t WHERE t.c802043009 = 0 " ;
		String sqlCycleP = "SELECT t.c802043006,t.c802043010,t.c802043012, c802043011, t.c1 FROM "+ strTblName + " t WHERE t.c802043009 = 0 " ;
		String sqlCycle = PageControl.GetSqlStringForPagination(sqlCycleP,"ORACLE",1, 10);
		System.out.println(sqlCycle);
		// 轮询
		try {
			IDatabaseDAO db=new IDatabaseDAO();
			List list=db.executeQuery(sqlCycle);
			Iterator iter=list.iterator();
			while(iter.hasNext()){
				HashMap hm=(HashMap)iter.next();
				String title = (String)hm.get("C802043006");
				String urgentDegree = (String)hm.get("C802043010");
				String availTime="";
				if(hm.get("C802043012")!=null){
					availTime = hm.get("C802043012").toString();					
				}
				String noticeDesc = (String)hm.get("C802043011");
				String baseId = (String)hm.get("C1");
				
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

//				String result="";//调试增加;

				if("".equals(result)){//调用成功， 更新数据
	            	String updateSql = "update " + strTblName  + " t set t.c802043009 = 1 where t.c1 = '" + baseId + "'";
	            	int a = db.executeUpdate(updateSql);
					if(a>0){
						System.out.println("公告派发success");
					}
	            }
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	

}
