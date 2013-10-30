package cn.com.ultrapower.ultrawf.control.flowmap;

import java.util.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.com.ultrapower.ultrawf.models.flowmap.*;
import cn.com.ultrapower.ultrawf.models.flowmap.Process;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.models.process.ProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.ProcessModel;

/**
 * 对DealProcess操作的类
 * 
 * @author BigMouse
 */
public class DealProcessManager extends ProcessManager
{
	/**
	 * 查询出指定工单的所有环节
	 * 
	 * @param baseid：工单号
	 * @param baseschema：工单类别
	 * @param isArchive：是否为历史数据
	 * @param processtype：环节类型（Deal和Auditing）
	 * @return 封装指定工单的所有环节的List
	 * @throws Exception
	 */
	public List buildProcessList(String baseid, String baseschema, String baseTplID, String type, String beginProcess, int isArchive, String processtype) throws Exception
	{
		List pInfoList = getProcessList(baseid, baseschema, isArchive, processtype);
		List lInfoList = getLinkList(baseid, baseschema, baseTplID, isArchive, processtype);
		List pLogInfoList =getProcessLogList(baseid, baseschema, isArchive, processtype);

		// 创建ProcessList，并将所有的ProcessInfo都放进去
		ProcessList pList = new ProcessList();
		pList.setProcessLogInfoList(pLogInfoList);
		pList.setTplLinkList(lInfoList);
		String bp = "BEGIN";
		if(type.equals("free"))
		{
			for(Iterator it = pInfoList.iterator(); it.hasNext();)
			{
				ProcessInfo pInfo = (ProcessInfo) it.next();
				if(pInfo.getPhaseNo().equals(beginProcess))
				{
					for(Iterator it1 = lInfoList.iterator(); it1.hasNext();)
					{
						LinkInfo lInfo = (LinkInfo)it1.next();
						if(lInfo.getEndPhase().equals(pInfo.getPhaseNo()))
						{
							bp = lInfo.getStartPhase();
							break;
						}
					}
					break;
				}
			}
		}
		for (Iterator it = pInfoList.iterator(); it.hasNext();)
		{
			ProcessInfo pInfo = (ProcessInfo) it.next();
			if(type.equals("tpl"))
			{
				if(pInfo.getFlagPredefined() == 1)
				{
					pInfo.setFlowMapNo(beginProcess);
					pInfo.setBeginPhaseNo(bp);
					pList.addProcessInfo(pInfo);
				}
				else
				{
					if(pInfo.getFlagDuplicated() == 0 && pInfo.getPrevPhaseNo() != null && pInfo.getFlagPredefined() == 0 && (pInfo.getPrevPhaseNo().substring(1, 3).equals("p_") || pInfo.getPrevPhaseNo().substring(0, 2).equals("p_")))
					{
						pList.getFpList().add(pInfo.getPrevPhaseNo());
					}
				}
			}
			else if(type.equals("free"))
			{
				if((pInfo.getFlagPredefined() == 0 || pInfo.getPhaseNo().equals(beginProcess)))
				{
					if(pInfo.getFlagPredefined() == 0 && pInfo.getPrevPhaseNo().equals(bp))
					{}
					else
					{
						pInfo.setFlowMapNo(beginProcess);
						pInfo.setBeginPhaseNo(bp);
						pList.addProcessInfo(pInfo);
					}
				}
			}
			else
			{
				pInfo.setFlowMapNo(beginProcess);
				pList.addProcessInfo(pInfo);
			}
			
		}
		System.out.println("Read end.");

		// 封装到List
		List processList = new ArrayList();
		processList.add(pList);
		return processList;
	}
	
	public List getProcessList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		RemedyDBOp rdbop = new RemedyDBOp();
        String baseform;
        if(isArchive == 0)
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_DealProcess");
        }
        else
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_DealProcess_Archive");
        }
		
		// 查询出ProcessInfo的List
		List pInfoList = Process.getProcess(processtype).getProcessInfoList("SELECT * FROM " + baseform + " WHERE C700020001 = '" + baseid + "' AND C700020002 = '" + baseschema + "' AND C700020011 LIKE '%'");
		
		String beginPhaseNo = ((ProcessInfo)pInfoList.get(0)).getBeginPhaseNo();
		
		AuditingProcessManager apManager = new AuditingProcessManager();
		List pInfoList_audit = apManager.getProcessList(baseid, baseschema, isArchive, "Auditing");
		for(Iterator it = pInfoList_audit.iterator(); it.hasNext();)
		{
			ProcessInfo pInfo = (ProcessInfo)it.next();
			pInfo.setBeginPhaseNo(beginPhaseNo);
		}
		pInfoList.addAll(pInfoList_audit);
		
		return pInfoList;
	}
	
	public List getLinkList(String baseid, String baseschema, String baseTplID, int isArchive, String processtype) throws Exception
	{
		RemedyDBOp rdbop = new RemedyDBOp();
        String baseform;
		List lInfoList = new ArrayList();
		if(baseTplID != null && !baseTplID.equals(""))
		{
			baseform = rdbop.GetRemedyTableName("WF:App_DealLink");
			Link link = Link.getLink("DLink");
			lInfoList = link.getLinkListInfo("SELECT * FROM " + baseform + " WHERE C700020501 = '" + baseid + "' and C700020502 = '" + baseschema + "'");
		}
		return lInfoList;
	}
	
	public List getProcessLogList(String baseid, String baseschema, int isArchive, String processtype) throws Exception
	{
		RemedyDBOp rdbop = new RemedyDBOp();
        String baseform;
		//查询出所有的Log信息
		ProcessLog pLog = new ProcessLog();
		
		if(isArchive == 0)
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_DealProcessLog");
        }
        else
        {
        	baseform = rdbop.GetRemedyTableName("WF:App_DealProcessLog_Archive");
        }
		List pLogInfoList = pLog.getProcessLogInfoList("SELECT C1, C700020401, C700020402, C700020403, C700020404, C700020405, C700020406 FROM " + baseform + " WHERE C700020407 = '" + baseid + "' AND C700020408 = '" + baseschema + "'");
		
		AuditingProcessManager apManager = new AuditingProcessManager();
		List pLogInfoList_audit = apManager.getProcessLogList(baseid, baseschema, isArchive, "Auditing");
		pLogInfoList.addAll(pLogInfoList_audit);
		
		return pLogInfoList;
	}
	
	public String buildProcessXML(List processList){
		Element root = new Element("ProcessInfos"); 
 	    Document doc = new Document(root) ;
 	    for (int i = 0; i < processList.size(); i++) {
 	    	Element processinfoElt = new Element("ProcessInfo");
 	    	ProcessModel processInfo = (ProcessModel) processList.get(i);
 	    	processinfoElt.addContent(new Element("PhaseNo").setText(processInfo.getPhaseNo()));
 	    	processinfoElt.addContent(new Element("ProcessId").setText(processInfo.getProcessID()));
 	    	processinfoElt.addContent(new Element("HasFreeProcess").setText("false"));
 	    	String titledealer=(processInfo.getAssginee().length()>0?processInfo.getAssginee():processInfo.getAssigneeDep());
 			String dealer = processInfo.getDealer()!=null?processInfo.getDealer():"";
 	    	if(dealer.length()>0)titledealer = dealer;
 	    	processinfoElt.addContent(new Element("Title").setText(titledealer));
 	    	processinfoElt.addContent(new Element("Content").setText(""));
 	    	processinfoElt.addContent(new Element("Status").setText(processInfo.getProcessStatus()));
 	    	processinfoElt.addContent(new Element("PrephaseNo").setText(processInfo.getPrevPhaseNo()));
 	    	processinfoElt.addContent(new Element("BrerPhaseNo").setText(""));
 	    	processinfoElt.addContent(new Element("Flagduplicated").setText(processInfo.getFlagDuplicated()+""));
 	    	processinfoElt.addContent(new Element("Flagendduplicated").setText(processInfo.getFlag34IsEndDuplicated()));
 	    	processinfoElt.addContent(new Element("Flagpredefined").setText(""+processInfo.getFlagPredefined()));
 	    	processinfoElt.addContent(new Element("Stepid").setText(processInfo.getPhaseNo()));
 	    	processinfoElt.addContent(new Element("Forwardstepid").setText(processInfo.getPrevPhaseNo()));
 	    	processinfoElt.addContent(new Element("Forwardstepids").setText(""));
 	    	processinfoElt.addContent(new Element("Flowid").setText(""));
 	    	processinfoElt.addContent(new Element("Flowids").setText(""));
 	    	processinfoElt.addContent(new Element("Dealer").setText(processInfo.getDealer()));
 	    	processinfoElt.addContent(new Element("Desc").setText(processInfo.getDesc()));
 	    	processinfoElt.addContent(new Element("StDate").setText(FormatTime.formatIntToDateString(processInfo.getStDate())));
 	    	processinfoElt.addContent(new Element("BgDate").setText(FormatTime.formatIntToDateString(processInfo.getBgDate())));
 	    	processinfoElt.addContent(new Element("EdDate").setText(FormatTime.formatIntToDateString(processInfo.getEdDate())));
 	    	processinfoElt.addContent(new Element("Flagactive").setText(processInfo.getFlagActive()+""));
 	    	processinfoElt.addContent(new Element("ProcessType").setText(processInfo.getProcessType()));
 	    	processinfoElt.addContent(new Element("FinishDate").setText(FormatTime.formatIntToDateString(processInfo.getEdDate())));
 	    	processinfoElt.addContent(new Element("CreateDate").setText(FormatTime.formatIntToDateString(processInfo.getStDate())));
 	    	
 	   	
 	    	Element infoviewsElt = new Element("InfoViews");
 	    	Element infoviewElt = new Element("InfoView");
    		infoviewElt.addContent(new Element("ProcessinfoStatus").setText(processInfo.getProcessStatus()));
    		infoviewElt.addContent(new Element("ProcessinfoDesc").setText(processInfo.getDesc()));
    		infoviewElt.addContent(new Element("ProcessinfoDealer").setText(processInfo.getDealer()));
    		infoviewElt.addContent(new Element("ProcessinfoPreDealer").setText(""));
    		infoviewElt.addContent(new Element("ProcessinfoStDate").setText(FormatTime.formatIntToDateString(processInfo.getStDate())));
    		infoviewElt.addContent(new Element("ProcessinfoDealDate").setText(FormatTime.formatIntToDateString(processInfo.getBgDate())));
    		infoviewElt.addContent(new Element("ProcessinfoFinishDate").setText(FormatTime.formatIntToDateString(processInfo.getEdDate())));
    		Element processLogsElt = new Element("ProcessLogs");
    		
 	    	List processlogList = processInfo.getLogList();
 	    	if(processlogList!=null)
 	    	{
	 	    	for (int j = 0; j < processlogList.size(); j++) 
	 	    	{
	 	    		ProcessLogModel logModel = (ProcessLogModel)processlogList.get(j);
	    			Element processLogElt = new Element("ProcessLog");
	    			processLogElt.addContent(new Element("ActionName").setText(logModel.getAct()));
	    			processLogElt.addContent(new Element("LogTime").setText(FormatTime.formatIntToDateString(logModel.getStDate())));
	    			processLogElt.addContent(new Element("LogUser").setText(logModel.getLogUser()));
	    			processLogElt.addContent(new Element("Processid").setText(logModel.getProcessID()));
	    			processLogElt.addContent(new Element("Result").setText(logModel.getResult()));
	 	    		processLogsElt.addContent(processLogElt);
	 	    	    Element processfieldsElt = new Element("Fields");
	 	    	    infoviewElt.addContent(processfieldsElt);
				}
 	    	}
 	    	infoviewElt.addContent(processLogsElt);
 	    	infoviewsElt.addContent(infoviewElt);
 	    	processinfoElt.addContent(infoviewsElt); 
 	    	
 	    	root.addContent(processinfoElt);
		}
 	   Format format = Format.getPrettyFormat();// 
	    XMLOutputter outp = new XMLOutputter(format);   
      
	   return outp.outputString(doc);
	}
}
