package com.ultrapower.eoms.mobile.interfaces.dealdesc.manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.flowmap.DealProcessManager;
import cn.com.ultrapower.ultrawf.control.flowmap.ProcessManager;
import cn.com.ultrapower.ultrawf.control.process.BaseAttachmentManage;
import cn.com.ultrapower.ultrawf.control.process.BaseFieldModifyLogManage;
import cn.com.ultrapower.ultrawf.control.process.BaseManage;
import cn.com.ultrapower.ultrawf.control.process.BaseOwnFieldInfoManage;
import cn.com.ultrapower.ultrawf.control.process.ProcessLogManage;
import cn.com.ultrapower.ultrawf.control.process.ProcessManage;
import cn.com.ultrapower.ultrawf.models.process.BaseAttachmentModel;
import cn.com.ultrapower.ultrawf.models.process.BaseFieldModifyLogModel;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.process.ProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.ProcessModel;
import cn.com.ultrapower.ultrawf.share.FormatInt;
import cn.com.ultrapower.ultrawf.share.FormatLong;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.FormatTime;

import com.ultrapower.eoms.mobile.interfaces.dealdesc.model.DealDescInputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class DealDescManager implements InterfaceService
{

	public String call(String xml, String fileList)
	{
		DealDescInputModel input = new DealDescInputModel();
		String outputXml;
		
		try
		{
			input.buildModel(xml);
			outputXml = handle(input);
		}
		catch (Exception e)
		{
			outputXml = "<opDetail><baseInfo><isLegal>0</isLegal><success>0</success><errorMessage>" +e.getMessage() +"</errorMessage></baseInfo></opDetail>";
			e.printStackTrace();
		}
		return outputXml;
	}
	
	private String handle(DealDescInputModel inputModel) 
	{
		String m_BaseSchema = inputModel.getCategory();
		String m_BaseID = inputModel.getBaseID();
		
		RDParameter Process_rDParameter=new RDParameter();
		Process_rDParameter.addIndirectPar("is_notnull","1",2);
      	Process_rDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
     	Process_rDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);
     	Process_rDParameter.addIndirectPar("orderby","StDate",4);
     	List<ProcessModel> m_ProcessModelList= (new ProcessManage()).getList(Process_rDParameter);
     	List<ProcessModel> m_ProcessModelShowList = new ArrayList<ProcessModel>();
     	for (int Process_row = 0; Process_row < m_ProcessModelList.size(); Process_row++) {
     		ProcessModel m_ProcessModel = m_ProcessModelList.get(Process_row);
			String 	m_str_processid 	= m_ProcessModel.getProcessID();
			String 	m_str_processtype 	= m_ProcessModel.getProcessType();
			String 	m_str_ProcessStatus	= m_ProcessModel.getProcessStatus();
			long 	m_long_FlagActive 	= m_ProcessModel.getFlagActive();
			if (m_long_FlagActive==4 || m_str_ProcessStatus.equals("已跳过"))
			{
				continue;
			}
			m_ProcessModelShowList.add(m_ProcessModel);
			//处理记录
			RDParameter ProcessLog_rDParameter=new RDParameter();	
			ProcessLog_rDParameter.addIndirectPar("baseschema",m_BaseSchema,4);
			ProcessLog_rDParameter.addIndirectPar("baseid",m_BaseID,4);
			ProcessLog_rDParameter.addIndirectPar("processid",m_str_processid,4);
			ReBuildSQL ProcessLog_reBuildSQL = null;
			ProcessLogManage m_ProcessLogManage = null;
			if (m_str_processtype.equals("DEAL"))
			{
				m_ProcessLogManage	= new ProcessLogManage("DEAL");
			}
			else if (m_str_processtype.equals("AUDITING"))
			{
				m_ProcessLogManage	= new ProcessLogManage("AUDITING");
			}
			List<ProcessLogModel> m_List_ProcessLogModel = m_ProcessLogManage.getList(ProcessLog_rDParameter);
			if(m_List_ProcessLogModel.size()>0){
				m_ProcessModel.setLogList(m_List_ProcessLogModel);
			}else{
				m_ProcessModel.setLogList(new ArrayList());
			}
//			if(m_List_ProcessLogModel.size()==0)
//			{
////				out.print("&nbsp;");
//			}
//			else
//			{
//				for (int ProcessLog_row = 0; ProcessLog_row < m_List_ProcessLogModel.size(); ProcessLog_row++) {
//					ProcessLogModel m_ProcessLogModel = m_List_ProcessLogModel.get(ProcessLog_row);
//					String str_LogUserFullName = m_ProcessLogModel.getLogUser();
//					String str_logUserDep 	= FormatString.CheckNullString(m_ProcessLogModel.getLogUserDep());
//					String str_logUserCorp 	= FormatString.CheckNullString(m_ProcessLogModel.getLogUserCorp());
//					String str_logResult 	= FormatString.CheckNullString(m_ProcessLogModel.getResult());
//					String str_logStDate 	= FormatTime.formatIntToDateString(m_ProcessLogModel.getStDate());
//					
//					if (!(str_logUserDep.equals("null")))
//					{
//						str_LogUserFullName =  str_logUserDep + "." + str_LogUserFullName;
//					}
//					if (!(str_logUserCorp.equals("null")))
//					{
//						str_LogUserFullName =  str_logUserCorp + "." + str_LogUserFullName;
//					}				
//		  			if (ProcessLog_row<m_List_ProcessLogModel.size()-1)
//		  			{
////		  				out.print("<BR>");
//		  			}
//				}
//			}
     	}
     	DealProcessManager dpm = new DealProcessManager();
		String output = "";
		try
		{
			DealProcessManager dpManager = new DealProcessManager();
			output = dpm.buildProcessXML(m_ProcessModelShowList);    
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}
}
