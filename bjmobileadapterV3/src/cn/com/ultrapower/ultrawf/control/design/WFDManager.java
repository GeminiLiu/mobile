package cn.com.ultrapower.ultrawf.control.design;
 
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.ultrapower.ultrawf.models.design.WFDHandler;
import cn.com.ultrapower.ultrawf.models.process.TplCreateBaseDefineModel;
import cn.com.ultrapower.system.util.FormatTime;

public class WFDManager
{
	public void save(String wfxml, String type)
	{
		Element rootElement = null;
		try
		{
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build((Reader)new StringReader(wfxml));
		rootElement = doc.getRootElement();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		
		TplBaseObj tbObj = new TplBaseObj();
		
		Element node_base = rootElement.getChild("base");
		Element node_statuses = rootElement.getChild("statuses");
		Element node_assistants = rootElement.getChild("assistants");
		Element node_processes = rootElement.getChild("processes");
		Element node_verdicts = rootElement.getChild("verdicts");
		Element node_links = rootElement.getChild("links");
		
		setBase(tbObj, node_base);
		setStatus(tbObj, node_statuses);
		setAssistants(tbObj, node_assistants);
		setProcesses(tbObj, node_processes);
		setVerdicts(tbObj, node_verdicts);
		setLinks(tbObj, node_links);
		
		WFDHandler wh = new WFDHandler();
		wh.saveDesignXML(tbObj.getTbModel(), wfxml);
		
		TplDesignManager tdm = new TplDesignManager();
		tdm.save(type, tbObj, null);
	}
	
	private void setBase(TplBaseObj tbObj, Element node_base)
	{
		tbObj.getTbModel().setBaseTplID(getFieldValue(node_base, "BaseTplID"));
		tbObj.getTbModel().setBaseTplName(getFieldValue(node_base, "BaseTplName"));
		tbObj.getTbModel().setBaseTplSchema(getFieldValue(node_base, "BaseTplSchema"));
		tbObj.getTbModel().setBaseTplSchemaName(getFieldValue(node_base, "BaseTplSchemaName"));
		tbObj.getTbModel().setBaseTplVersion(getFieldValue(node_base, "BaseTplVersion"));
		if(!getFieldValue(node_base, "BaseTplStartDate").equals("")) tbObj.getTbModel().setBaseTplStartDate(FormatTime.FormatDateStringToInt(getFieldValue(node_base, "BaseTplStartDate")));
		if(!getFieldValue(node_base, "BaseTplStartDate").equals("")) tbObj.getTbModel().setBaseTplFlagStart(FormatTime.FormatDateStringToInt(getFieldValue(node_base, "BaseTplStartDate")));
		if(!getFieldValue(node_base, "BaseTplEndDate").equals("")) tbObj.getTbModel().setBaseTplEndDate(FormatTime.FormatDateStringToInt(getFieldValue(node_base, "BaseTplEndDate")));
		if(!getFieldValue(node_base, "BaseTplEndDate").equals("")) tbObj.getTbModel().setBaseTplFlagEnd(FormatTime.FormatDateStringToInt(getFieldValue(node_base, "BaseTplEndDate")));
		tbObj.getTbModel().setBaseTplIsActive(Integer.parseInt(getFieldValue(node_base, "BaseTplIsActive")));
		tbObj.getTbModel().setBaseTplDesc(getFieldValue(node_base, "BaseTplDesc"));
	}
	
	private void setStatus(TplBaseObj tbObj, Element node_statuses)
	{
		for(Iterator it = node_statuses.getChildren("status").iterator(); it.hasNext();)
		{
			Element node_status = (Element)it.next();
			TplStateObj tsObj = new TplStateObj();
			tsObj.tbfsModel.setBaseStateCode(getFieldValue(node_status, "BaseStateCode"));
			tsObj.tbfsModel.setBaseStateIndex(Integer.parseInt(getFieldValue(node_status, "BaseStateIndex")));
			tsObj.tbfsModel.setBaseStateName(getFieldValue(node_status, "BaseStateName"));
			tsObj.tbfsModel.setBaseStateColor(getFieldValue(node_status, "#CCCCCC"));
			tsObj.tbfsModel.setBaseStateLength(Integer.parseInt(getFieldValue(node_status, "BaseStateLength")));
			tbObj.addStateModel(tsObj);
		}
	}
	
	private void setAssistants(TplBaseObj tbObj, Element node_assistants)
	{
		for(Iterator it = node_assistants.getChildren("assistant").iterator(); it.hasNext();)
		{
			Element node_assistant = (Element)it.next();
			TplProcessObj tpObj = new TplProcessObj();
			tpObj.setProcessID(getFieldValue(node_assistant, "AssistantProcessPhaseNo"));
			tpObj.setProcessType(getFieldValue(node_assistant, "AssistantProcessPurpose").equals("0") ? "BEGIN" : "END");
			tpObj.tdapModel.setAssistantProcessPhaseNo(getFieldValue(node_assistant, "AssistantProcessPhaseNo"));
			tpObj.tdapModel.setBaseTplStateCode(getFieldValue(node_assistant, "BaseStateCode"));
			tpObj.tdapModel.setBaseTplStateName(getFieldValue(node_assistant, "BaseStateName"));
			tpObj.tdapModel.setAssistantProcessPosX(Integer.parseInt(getFieldValue(node_assistant, "AssistantProcessPosX")));
			tpObj.tdapModel.setAssistantProcessPosY(Integer.parseInt(getFieldValue(node_assistant, "AssistantProcessPosY")));
			tpObj.tdapModel.setAssistantProcessPurpose(Integer.parseInt(getFieldValue(node_assistant, "AssistantProcessPurpose")));
			tbObj.addProcessModel(tpObj);
		}
	}
	
	private void setProcesses(TplBaseObj tbObj, Element node_processes)
	{
		for(Iterator it = node_processes.getChildren("process").iterator(); it.hasNext();)
		{
			Element node_process = (Element)it.next();
			TplProcessObj tpObj = new TplProcessObj();
			tpObj.setProcessID(getFieldValue(node_process, "PhaseNo"));
			tpObj.setProcessType("STEP");
			tpObj.tdpModel.setPhaseNo(getFieldValue(node_process, "PhaseNo"));
			tpObj.tdpModel.setBaseStateCode(getFieldValue(node_process, "BaseStateCode"));
			tpObj.tdpModel.setBaseStateName(getFieldValue(node_process, "BaseStateName"));
			tpObj.tdpModel.setRoleOnlyID(getFieldValue(node_process, "RoleOnlyID"));
			tpObj.tdpModel.setRoleName(getFieldValue(node_process, "RoleName"));
			tpObj.tdpModel.setPosX(Integer.parseInt(getFieldValue(node_process, "PosX")));
			tpObj.tdpModel.setPosY(Integer.parseInt(getFieldValue(node_process, "PosY")));
			tpObj.tdpModel.setActionName(getFieldValue(node_process, "ActionName"));
			tpObj.tdpModel.setActionPageID(getFieldValue(node_process, "ActionPageID"));
			tpObj.tdpModel.setActionPageName(getFieldValue(node_process, "ActionPageName"));
			tpObj.tdpModel.setDesc(getFieldValue(node_process, "Desc"));
			tpObj.tdpModel.setAssignOverTimeDate_tmp(Integer.parseInt(getFieldValue(node_process, "AssignOverTimeDate_tmp").equals("") ? "0" : getFieldValue(node_process, "AssignOverTimeDate_tmp")));
			tpObj.tdpModel.setAcceptOverTimeDate_tmp(Integer.parseInt(getFieldValue(node_process, "AcceptOverTimeDate_tmp").equals("") ? "0" : getFieldValue(node_process, "AcceptOverTimeDate_tmp")));
			tpObj.tdpModel.setDealOverTimeDate_tmp(Integer.parseInt(getFieldValue(node_process, "DealOverTimeDate_tmp").equals("") ? "0" : getFieldValue(node_process, "DealOverTimeDate_tmp")));
			tpObj.tdpModel.setFlagType(Integer.parseInt(getFieldValue(node_process, "FlagType")));
			tpObj.tdpModel.setFlag01Assign(Integer.parseInt(getFieldValue(node_process, "Flag01Assign")));
			tpObj.tdpModel.setFlag02Copy(Integer.parseInt(getFieldValue(node_process, "Flag02Copy")));
			tpObj.tdpModel.setFlag03Assist(Integer.parseInt(getFieldValue(node_process, "Flag03Assist")));
			tpObj.tdpModel.setFlag04Transfer(Integer.parseInt(getFieldValue(node_process, "Flag04Transfer")));
			tpObj.tdpModel.setFlag05TurnDown(Integer.parseInt(getFieldValue(node_process, "Flag05TurnDown")));
			tpObj.tdpModel.setFlag06TurnUp(Integer.parseInt(getFieldValue(node_process, "Flag06TurnUp")));
			tpObj.tdpModel.setFlag07Recall(Integer.parseInt(getFieldValue(node_process, "Flag07Recall")));
			tpObj.tdpModel.setFlag08Cancel(Integer.parseInt(getFieldValue(node_process, "Flag08Cancel")));
			tpObj.tdpModel.setFlag09Close(Integer.parseInt(getFieldValue(node_process, "Flag09Close")));
			tpObj.tdpModel.setFlag15ToAuditing(Integer.parseInt(getFieldValue(node_process, "Flag15ToAuditing")));
			tpObj.tdpModel.setFlag20SideBySide(Integer.parseInt(getFieldValue(node_process, "Flag20SideBySide")));
			tpObj.tdpModel.setFlag35IsCanCreateBase(Integer.parseInt(getFieldValue(node_process, "Flag35IsCanCreateBase")));
			tpObj.tdpModel.setFlag16ToAssistAuditing(Integer.parseInt(getFieldValue(node_process, "Flag16ToAssistAuditing")));
			try
			{
			tpObj.tdpModel.setFlag37IsNeedStartInsideFlow(Integer.parseInt(getFieldValue(node_process, "Flag37IsNeedStartInsideFlow")));
			tpObj.tdpModel.setStartInsideFlowID(getFieldValue(node_process, "StartInsideFlowID"));
			tpObj.tdpModel.setStartInsideFlowName(getFieldValue(node_process, "StartInsideFlowName"));
			}
			catch(Exception e)
			{}
			tpObj.tdpModel.setCustomActions(getFieldValue(node_process, "CustomActions"));
			
			Element node_role = node_process.getChild("role");
			tpObj.tdprdModel.setRoleOnlyID(getFieldValue(node_role, "RoleOnlyID"));
			tpObj.tdprdModel.setRoleName(getFieldValue(node_role, "RoleName"));
			tpObj.tdprdModel.setRoleProcessRoleType(Integer.parseInt(getFieldValue(node_role, "RoleProcessRoleType")));
			tpObj.tdprdModel.setRoleDesc(getFieldValue(node_role, "RoleDesc"));
			tpObj.tdprdModel.setRoleProcessPhaseNo(getFieldValue(node_role, "RoleProcessPhaseNo"));
			tpObj.tdprdModel.setAssignee(getFieldValue(node_role, "Assignee"));
			tpObj.tdprdModel.setAssigneeID(getFieldValue(node_role, "AssigneeID"));
			tpObj.tdprdModel.setGroup(getFieldValue(node_role, "Group"));
			tpObj.tdprdModel.setGroupID(getFieldValue(node_role, "GroupID"));
			tpObj.tdprdModel.setRoleKey(getFieldValue(node_role, "RoleKey"));
			tpObj.tdprdModel.setContextAssignee_FieldID(getFieldValue(node_role, "ContextAssignee_FieldID"));
			tpObj.tdprdModel.setContextAssigneeID_FieldID(getFieldValue(node_role, "ContextAssigneeID_FieldID"));
			tpObj.tdprdModel.setContextGroup_FieldID(getFieldValue(node_role, "ContextGroup_FieldID"));
			tpObj.tdprdModel.setContextGroupID_FieldID(getFieldValue(node_role, "ContextGroupID_FieldID"));
			tpObj.tdprdModel.setTopRoleMatchConditionsDesc(getFieldValue(node_role, "TopRoleMatchConditionsDesc"));
			
			List node_creates = node_process.getChild("creates").getChildren("create");
			for(Iterator it_c = node_creates.iterator(); it_c.hasNext();)
			{
				Element node_create = (Element)it_c.next();
				TplCreateBaseDefineModel tcbdModel = new TplCreateBaseDefineModel();
				tcbdModel.setCBDProcessPhaseNo(getFieldValue(node_create, "CBDProcessPhaseNo"));
				tcbdModel.setCreateBaseAftermathType(Integer.parseInt(getFieldValue(node_create, "CreateBaseAftermathType")));
				tcbdModel.setCreateBaseSchema(getFieldValue(node_create, "CreateBaseSchema"));
				tcbdModel.setCreateBaseName(getFieldValue(node_create, "CreateBaseName"));
				tpObj.tcbdList.add(tcbdModel);
			}
			
			tbObj.addProcessModel(tpObj);
		}
	}
	
	private void setVerdicts(TplBaseObj tbObj, Element node_verdicts)
	{
		for(Iterator it = node_verdicts.getChildren("verdict").iterator(); it.hasNext();)
		{
			Element node_verdict = (Element)it.next();
			TplProcessObj tpObj = new TplProcessObj();
			tpObj.setProcessID(getFieldValue(node_verdict, "VerdictPhaseNo"));
			tpObj.setProcessType("CONDITION");
			tpObj.tdvModel.setVerdictPhaseNo(getFieldValue(node_verdict, "VerdictPhaseNo"));
			tpObj.tdvModel.setBaseTplStateCode(getFieldValue(node_verdict, "BaseStateCode"));
			tpObj.tdvModel.setBaseTplStateName(getFieldValue(node_verdict, "BaseStateName"));
			tpObj.tdvModel.setVerdictPosX(Integer.parseInt(getFieldValue(node_verdict, "VerdictPosX")));
			tpObj.tdvModel.setVerdictPosY(Integer.parseInt(getFieldValue(node_verdict, "VerdictPosY")));
			tpObj.tdvModel.setVerdictDesc(getFieldValue(node_verdict, "VerdictDesc"));
			tpObj.tdvModel.setVerdictCondition(getFieldValue(node_verdict, "VerdictCondition"));
			tbObj.addProcessModel(tpObj);
		}
	}
	
	private void setLinks(TplBaseObj tbObj, Element node_links)
	{
		for(Iterator it = node_links.getChildren("link").iterator(); it.hasNext();)
		{
			Element node_link = (Element)it.next();
			TplLinkObj tlObj = new TplLinkObj();
			String lid = getFieldValue(node_link, "LinkPhaseNo");
			String bp = getFieldValue(node_link, "StartPhaseNo");
			String ep = getFieldValue(node_link, "EndPhase");
			tlObj.setLinkID(lid);
			tlObj.setBeginProcess(bp);
			tlObj.setEndProcess(ep);
			tlObj.tdlModel.setLinkPhaseNo(lid);
			tlObj.tdlModel.setStartPhaseNo(bp);
			tlObj.tdlModel.setEndPhaseNo(ep);
			String strstartport = getFieldValue(node_link, "StartPort");
			int startport = 0;
			if(strstartport.equals("BEGIN")) startport = 0;
			else if(strstartport.equals("END")) startport = 1;
			else if(strstartport.equals("STEP")) startport = 2;
			else if(strstartport.equals("CONDITION")) startport = 3;
			String strendport = getFieldValue(node_link, "EndPort");
			int endport = 0;
			if(strendport.equals("BEGIN")) endport = 0;
			else if(strendport.equals("END")) endport = 1;
			else if(strendport.equals("STEP")) endport = 2;
			else if(strendport.equals("CONDITION")) endport = 3;
			tlObj.tdlModel.setStartPort(startport);
			tlObj.tdlModel.setEndPort(endport);
			tlObj.tdlModel.setStartPoint(Integer.parseInt(getFieldValue(node_link, "StartPoint")));
			tlObj.tdlModel.setEndPoint(Integer.parseInt(getFieldValue(node_link, "EndPoint")));
			String type = getFieldValue(node_link, "LinkType");
			
			if(type.equals("YES"))
			{
				tlObj.tdlModel.setLinkVerdictResult(1);
			}
			else if(type.equals("NO"))
			{
				tlObj.tdlModel.setLinkVerdictResult(0);
			}
			else
			{
				tlObj.tdlModel.setLinkVerdictResult(2);
			}
			
			tlObj.tdlModel.setLinkNum(Integer.parseInt(getFieldValue(node_link, "LinkNum")));
			tbObj.addLinkModel(tlObj);
			
			tbObj.getProcessModel(bp).addBeginLink(lid);
			tbObj.getProcessModel(ep).addEndLink(lid);
		}
	}
	
	private String getFieldValue(Element node, String fieldname)
	{
		String value = "";
		List cnode = node.getChildren("field");
		for(Iterator it = cnode.iterator(); it.hasNext();)
		{
			Element field = (Element)it.next();
			if(field.getAttributeValue("fieldname").equals(fieldname))
			{
				value = field.getText();
				break;
			}
		}
		return value;
	}
}
