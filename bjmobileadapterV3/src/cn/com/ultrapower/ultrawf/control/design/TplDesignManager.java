package cn.com.ultrapower.ultrawf.control.design;

import java.util.*; 

import cn.com.ultrapower.ultrawf.models.design.GroupUserInterfaceTmp;
import cn.com.ultrapower.ultrawf.models.design.RoleUserHandler;
import cn.com.ultrapower.ultrawf.models.design.UserModel;
import cn.com.ultrapower.ultrawf.models.process.*;
import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.*;
import cn.com.ultrapower.system.util.FormatTime;

public class TplDesignManager
{
	private TplBaseObj tbObj = null;
	private String userLoginName = "";
	private String userFullName = "";
	
	public void save(String type, TplBaseObj tplBaseObj, Sessions sess)
	{
		this.tbObj = tplBaseObj;
		getWay();
		//userLoginName = sess.getSession_UserLoginName();
		//userFullName = sess.getSession_UserFullName();
		

		userLoginName = "Demo";
		userFullName = "Demo";
		
		type = type == null ? "new" : type;
		if(type.toLowerCase().equals("new"))
		{
			newsave();
		}
		else if(type.toLowerCase().equals("edit"))
		{
			editsave();
		}
	}
	
	private void newsave()
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
			Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
			Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		//TplBase
		TplBaseModel tbModel = tbObj.getTbModel();
		tbModel.setBaseTplCreateDate(System.currentTimeMillis() / 1000);
		tbModel.setBaseTplAuthor(userFullName);
		tbModel.setBaseTplAuthorID(userLoginName);
		TplBase tb = new TplBase();
		String tplBaseID = tb.Insert(RemedyOp, tbModel);
		String schema = tbModel.getBaseTplSchema();
		String schemaName = tbModel.getBaseTplSchemaName();
		insertContent(RemedyOp, tplBaseID, schema, schemaName);
		RemedyOp.RemedyLogout();
	}
	
	private void editsave()
	{
		//TplBase
		TplBaseModel tbModel = tbObj.getTbModel();
		tbModel.setBaseTplModifyDate(System.currentTimeMillis() / 1000);
		tbModel.setBaseTplModifier(userFullName);
		tbModel.setBaseTplModifierID(userLoginName);
		TplBase tb = new TplBase();
		tb.Update(tbModel);
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
			Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
			Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String tplBaseID = tbModel.getBaseTplID();
		String schema = tbModel.getBaseTplSchema();
		String schemaName = tbModel.getBaseTplSchemaName();
		deletecontent(RemedyOp, tplBaseID);
		insertContent(RemedyOp, tplBaseID, schema, schemaName);
		RemedyOp.RemedyLogout();
	}
	
	public void delete(String tplBaseID)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
			Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
			Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		deletecontent(RemedyOp, tplBaseID);
		TplBase tb = new TplBase();
		tb.Delete(RemedyOp, tplBaseID);
		RemedyOp.RemedyLogout();
	}
	
	private void insertContent(RemedyFormOp RemedyOp, String tplBaseID, String schema, String schemaName)
	{
		//TplBaseFixState
		TplBaseFixState tbfs = new TplBaseFixState();
		
		List stateList = tbObj.getStateList();
		
		for(Iterator it = stateList.iterator(); it.hasNext();)
		{
			TplStateObj tsObj = (TplStateObj)it.next();
			TplBaseFixStateModel tbfsModel = tsObj.tbfsModel;
			tbfsModel.setBaseID(tplBaseID);
			tbfsModel.setBaseCategorySchama(schema);
			tbfsModel.setBaseCategoryName(schemaName);
			tbfs.Insert(RemedyOp, tbfsModel);
		}
		
		//TplDealProcess
		TplDealProcess tdp = new TplDealProcess();
		TplDealAssistantProcess tdap = new TplDealAssistantProcess();
		TplDealVerdict tdv = new TplDealVerdict();
		TplDealProcessRoleDefine tdpr = new TplDealProcessRoleDefine();
		TplCreateBaseDefine tcbd = new TplCreateBaseDefine();
		
		List processList = tbObj.getProcessList();
		
		for(Iterator it = processList.iterator(); it.hasNext();)
		{
			try
			{
				TplProcessObj tpObj = (TplProcessObj)it.next();
				
				StringBuffer sbWay = new StringBuffer();
				for(Iterator itway = tpObj.getWayList().iterator(); itway.hasNext();)
				{
					sbWay.append((String)itway.next());
					sbWay.append("#");
				}
				if(sbWay.length() <= 0)
				{
					sbWay.append("#");
				}
				
				if(tpObj.getProcessType().equals("BEGIN") || tpObj.getProcessType().equals("END"))
				{
					TplDealAssistantProcessModel tdapModel = tpObj.tdapModel;
					tdapModel.setAssistantProcessBaseID(tplBaseID);
					tdapModel.setAssistantProcessBaseSchema(schema);
					tdapModel.setBaseTplStateName(tbObj.getStateModel(tdapModel.getBaseTplStateCode()).tbfsModel.getBaseStateName());
					tdapModel.setAssistantProcessGoLine(sbWay.substring(0, sbWay.length() - 1));
					tdap.Insert(RemedyOp, tdapModel);
				}
				else if(tpObj.getProcessType().equals("STEP"))
				{
					TplDealProcessModel tdpModel = tpObj.tdpModel;
					tdpModel.setProcessBaseID(tplBaseID);
					tdpModel.setProcessBaseSchema(schema);
					tdpModel.setBaseStateName(tbObj.getStateModel(tdpModel.getBaseStateCode()).tbfsModel.getBaseStateName());
					tdpModel.setProcessGoLine(sbWay.substring(0, sbWay.length() - 1));
					tdp.Insert(RemedyOp, tdpModel);
					
					TplDealProcessRoleDefineModel tdprdModel = tpObj.tdprdModel;
					tdprdModel.setRoleBaseID(tplBaseID);
					tdprdModel.setRoleBaseSchema(schema);
					tdpr.Insert(RemedyOp, tdprdModel);
					
					List tcbdList = tpObj.tcbdList;
					for(Iterator ittcbd = tcbdList.iterator(); ittcbd.hasNext();)
					{
						TplCreateBaseDefineModel tcbdModel = (TplCreateBaseDefineModel)ittcbd.next();
						tcbdModel.setCBDBaseID(tplBaseID);
						tcbdModel.setCBDBaseSchema(schema);
						tcbdModel.setCBDBaseName(schemaName);
						
						tcbd.Insert(RemedyOp, tcbdModel);
					}
				}
				else if(tpObj.getProcessType().equals("CONDITION"))
				{
					TplDealVerdictModel tdvModel = tpObj.tdvModel;
					tdvModel.setVerdictBaseID(tplBaseID);
					tdvModel.setVerdictBaseSchema(schema);
					tdvModel.setBaseTplStateName(tbObj.getStateModel(tdvModel.getBaseTplStateCode()).tbfsModel.getBaseStateName());
					tdvModel.setVerdictGoLine(sbWay.substring(0, sbWay.length() - 1));
					tdv.Insert(RemedyOp, tdvModel);
				}
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		//TplDealLink
		TplDealLink tdl = new TplDealLink();
		
		List linkList = tbObj.getLinkList();
		
		for(Iterator it = linkList.iterator(); it.hasNext();)
		{
			try
			{
				TplLinkObj tlObj = (TplLinkObj)it.next();
				StringBuffer sbWay = new StringBuffer();
				for(Iterator itway = tlObj.getWayList().iterator(); itway.hasNext();)
				{
					sbWay.append((String)itway.next());
					sbWay.append("#");
				}
				if(sbWay.length() <= 0)
				{
					sbWay.append("#");
				}
				
				TplDealLinkModel tdlModel = tlObj.tdlModel;
				tdlModel.setLinkBaseID(tplBaseID);
				tdlModel.setLinkBaseSchema(schema);
				tdlModel.setLinkGoLine(sbWay.substring(0, sbWay.length() - 1));
				tdl.Insert(RemedyOp, tdlModel);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void deletecontent(RemedyFormOp RemedyOp, String tplBaseID)
	{
		//TplBaseFixState
		TplBaseFixState tbfs = new TplBaseFixState();
		
		List stateList = tbfs.getTplBaseFixStateList(tplBaseID, "", "");
		for(Iterator it = stateList.iterator(); it.hasNext();)
		{
			TplBaseFixStateModel tsObj = (TplBaseFixStateModel)it.next();
			tbfs.Delete(RemedyOp, tsObj.getBaseTplStateID());
		}
		
		//TplCreateBaseDefine
		TplCreateBaseDefine tcbd = new TplCreateBaseDefine();
		
		List tcbdList = tcbd.getTplCreateBaseDefineList(tplBaseID, "", "");
		for(Iterator it = tcbdList.iterator(); it.hasNext();)
		{
			TplCreateBaseDefineModel tcbdObj = (TplCreateBaseDefineModel)it.next();
			tcbd.Delete(RemedyOp, tcbdObj.getCreateBaseDefineID());
		}
		
		//TplDealProcessRoleDefine
		TplDealProcessRoleDefine tdprd = new TplDealProcessRoleDefine();
		
		List tdprdList = tdprd.getTplDealProcessRoleDefineList(tplBaseID, "", "");
		for(Iterator it = tdprdList.iterator(); it.hasNext();)
		{
			TplDealProcessRoleDefineModel tdprdObj = (TplDealProcessRoleDefineModel)it.next();
			tdprd.Delete(RemedyOp, tdprdObj.getRoleDefineID());
		}
		
		//TplDealProcess
		TplDealProcess tdp = new TplDealProcess();
		TplDealAssistantProcess tdap = new TplDealAssistantProcess();
		TplDealVerdict tdv = new TplDealVerdict();
		
		List processList = tdp.getTplDealProcessList(tplBaseID, "", "");
		for(Iterator it = processList.iterator(); it.hasNext();)
		{
			TplDealProcessModel tpObj = (TplDealProcessModel)it.next();
			tdp.Delete(RemedyOp, tpObj.getProcessID());
		}
		
		List assistantProcessList = tdap.getTplDealAssistantProcessList(tplBaseID, "", "");
		for(Iterator it = assistantProcessList.iterator(); it.hasNext();)
		{
			TplDealAssistantProcessModel tpObj = (TplDealAssistantProcessModel)it.next();
			tdap.Delete(RemedyOp, tpObj.getAssistantProcessID());
		}
		
		List verdictProcessList = tdv.getTplDealVerdictList(tplBaseID, "", "");
		for(Iterator it = verdictProcessList.iterator(); it.hasNext();)
		{
			TplDealVerdictModel tpObj = (TplDealVerdictModel)it.next();
			tdv.Delete(RemedyOp, tpObj.getVerdictID());
		}
		
		//TplDealLink
		TplDealLink tdl = new TplDealLink();
		
		List linkList = tdl.getTplDealLinkList(tplBaseID, "", "");
		
		for(Iterator it = linkList.iterator(); it.hasNext();)
		{
			TplDealLinkModel tlObj = (TplDealLinkModel)it.next();
			tdl.Delete(RemedyOp, tlObj.getLinkID());
		}
	}
	
	private void getWay()
	{
		List tpList = tbObj.getProcessList();
		
		TplProcessObj tpoBegin = null;
		
		for(Iterator it = tpList.iterator(); it.hasNext();)
		{
			TplProcessObj tpObj = (TplProcessObj)it.next();
			if(tpObj.getProcessType().equals("BEGIN"))
			{
				List beginWayList = new ArrayList();
				beginWayList.add(tpObj.getProcessID() + ";");
				tpObj.setWayList(beginWayList);
				tpoBegin = tpObj;
			}
		}
		
		if(tpoBegin != null)
		{
			findWay(tpoBegin, tpoBegin.getWayList().get(0).toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void findWay(TplProcessObj tpObj, String path)
	{
		//List nextProcessList = new ArrayList();
		//获取当前环节的NextLink列表
		List nextLinkList = tpObj.getBeginLinkList();
		//循环当前环节的NextLink列表
		for(Iterator itnlList = nextLinkList.iterator(); itnlList.hasNext();)
		{
			String linkID = (String)itnlList.next();
			List tmpWayList = new ArrayList();
			//获取Link对象
			TplLinkObj tlObj = tbObj.getLinkModel(linkID);
			//增加Link上的一条路径
			for(Iterator itpwList = tpObj.getWayList().iterator(); itpwList.hasNext();)
			{
				String pw = (String)itpwList.next();
				tmpWayList.add(pw + linkID + ";");
			}
			tlObj.setWayList(tmpWayList);

			//获取Link所指向的环节
			String nextProcess = tlObj.getEndProcess();
			TplProcessObj nextProcessObj = tbObj.getProcessModel(nextProcess);
			boolean hasPID = true;
			if((";" + path).indexOf(";" + nextProcess + ";") < 0)
			{
				for(Iterator itlwList = tlObj.getWayList().iterator(); itlwList.hasNext();)
				{
					String lw = (String)itlwList.next();
					boolean hasway = false;
					for(Iterator itwl = nextProcessObj.getWayList().iterator(); itwl.hasNext();)
					{
						if(((String)itwl.next()).equals(lw + nextProcess + ";"))
						{
							hasway = true;
						}
					}
					if(!hasway)
					{
						nextProcessObj.getWayList().add(lw + nextProcess + ";");
					}
					hasPID = false;
				}
			}
			if(!hasPID)
			{
				System.out.print("-->" + nextProcessObj.getProcessID());
				findWay(nextProcessObj, path + nextProcess + ";");
				System.out.println();
				System.out.print(tpObj.getProcessID());
			}
		}
	}
	
	public List<TplBaseModel> getTplBaseList()
	{
		TplBase tb = new TplBase();
		return tb.getTplBaseList();
	}
	
	public List<TplBaseModel> getTplBaseListBySchema(String baseSchema)
	{
		TplBase tb = new TplBase();
		return tb.getTplBaseListBySchema(baseSchema);
	}
	
	public List<TplBaseModel> getTplBaseListByCreateUserID(String userID)
	{
		TplBase tb = new TplBase();
		return tb.getTplBaseListByCreateUserID(userID);
	}
	
	public List<TplBaseModel> getTplBaseListByModifyUserID(String userID)
	{
		TplBase tb = new TplBase();
		return tb.getTplBaseListByModifyUserID(userID);
	}
	
	public TplBaseModel getTplBaseModel(String baseID)
	{
		TplBase tb = new TplBase();
		return tb.getTplBaseModel(baseID);
	}
	
	public List getTplStateList(String baseID, String schema, String type)
	{
		TplBaseFixState tbfs = new TplBaseFixState();
		return tbfs.getTplBaseFixStateList(baseID, schema, type);
	}
	
	public List getStateList(String baseID, String schema, String flowID)
	{
		TplBaseFixState tbfs = new TplBaseFixState();
		return tbfs.getBaseFixStateList(baseID, schema, flowID);
	}
	
	public List getTplDealAssistantProcessList(String baseID, String schema, String type)
	{
		TplDealAssistantProcess tdap = new TplDealAssistantProcess();
		return tdap.getTplDealAssistantProcessList(baseID, schema, type);
	}
	
	public List getDealAssistantProcessList(String baseID, String schema, String flowID)
	{
		TplDealAssistantProcess tdap = new TplDealAssistantProcess();
		return tdap.getDealAssistantProcessList(baseID, schema, flowID);
	}
	
	public List getTplProcessList(String baseID, String schema, String type)
	{
		TplDealProcess tdp = new TplDealProcess();
		return tdp.getTplDealProcessList(baseID, schema, type);
	}
	
	public List getProcessList(String baseID, String schema, String flowID)
	{
		TplDealProcess tdp = new TplDealProcess();
		List tdpList = tdp.getDealProcessList(baseID, schema, flowID);
		for(Iterator it = tdpList.iterator(); it.hasNext();)
		{
			TplDealProcessModel tdpModel = (TplDealProcessModel)it.next();
			StringBuffer processInfo = new StringBuffer();
			processInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			processInfo.append("<Process>");
			for(Iterator itIn = tdpModel.getDuplicatedList().iterator(); itIn.hasNext();)
			{
				processInfo.append(drawProcessInfo((TplDealProcessModel) itIn.next()));
			}
			processInfo.append("<SubLogs>");
			
			if(tdpModel.getStartInsideFlows() != null && !tdpModel.getStartInsideFlows().equals(""))
			{
				String[] insideFlows = tdpModel.getStartInsideFlows().substring(0, tdpModel.getStartInsideFlows().length() - 1).split(";");
				for(int i = 0; i < insideFlows.length; i++)
				{
					processInfo.append("<SubLog>");
					List subList = tdp.getDealProcessList(baseID, schema, insideFlows[i]);
					for(Iterator itSubFlow = subList.iterator(); itSubFlow.hasNext();)
					{
						TplDealProcessModel subModel = (TplDealProcessModel)itSubFlow.next();
						for(Iterator itSubProcess = subModel.getDuplicatedList().iterator(); itSubProcess.hasNext();)
						{
							processInfo.append(drawProcessInfo((TplDealProcessModel) itSubProcess.next()));
						}
					}
					processInfo.append("</SubLog>");
				}
			}
			
			processInfo.append("</SubLogs>");
			processInfo.append("</Process>");
			tdpModel.setLogXml(processInfo.toString());
		}
		return tdpList;
	}
	
	private StringBuffer drawProcessInfo(TplDealProcessModel pInfo)
	{
		StringBuffer templite = new StringBuffer();

		// 环节信息
		templite.append("<ProcessInfo>");

		templite.append("<Info>");
		
		String status = pInfo.getProcessStatus() == null ? "" : pInfo.getProcessStatus();
		templite.append("<Status>" + status + "</Status>");

		// 内容
		if (pInfo.getDesc() != null)
		{
			templite.append("<Desc>" + pInfo.getDesc() + "</Desc>");
		}
		else
		{
			templite.append("<Desc> </Desc>");
		}

		// 执行人

		if (pInfo.getDealer() != null)
		{
			templite.append("<Dealer>" + pInfo.getDealer() + "</Dealer>");
			templite.append("<PreDealer></PreDealer>");
		}
		else
		{
			if(pInfo.getAssignee() != null)
			{
				templite.append("<Dealer>" + pInfo.getAssignee() + "</Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
			else if(pInfo.getGroup() != null)
			{
				List<UserModel> userList = new ArrayList<UserModel>();
				if(pInfo.getGroupID().length() > 6)
				{
					RoleUserHandler ruHandler = new RoleUserHandler();
					userList = ruHandler.getUsers(pInfo.getGroupID());
				}
				else
				{
					GroupUserInterfaceTmp guit = new GroupUserInterfaceTmp();
					userList = guit.getUserList(pInfo.getGroupID());
				}
				
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer>");
				for(Iterator<UserModel> it = userList.iterator(); it.hasNext();)
				{
					templite.append(it.next().getUserName() + ";");
				}

				templite.append("</PreDealer>");
			}
			else
			{
				templite.append("<Dealer> </Dealer>");
				templite.append("<PreDealer></PreDealer>");
			}
		}

		// 开始时间

		try
		{
			templite.append("<StDate>" + FormatTime.formatIntToDateString(pInfo.getStDate()) + "</StDate>");
		}
		catch (Exception e)
		{
			templite.append("<StDate> </StDate>");
		}

		// 处理时间
		try
		{
			templite.append("<DealDate>" + FormatTime.formatIntToDateString(pInfo.getBgDate()) + "</DealDate>");
		}
		catch (Exception e)
		{
			templite.append("<DealDate> </DealDate>");
		}

		// 完成时间
		try
		{
			templite.append("<FinishDate>" + FormatTime.formatIntToDateString(pInfo.getEdDate()) + "</FinishDate>");
		}
		catch (Exception e)
		{
			templite.append("<FinishDate> </FinishDate>");
		}
		templite.append("</Info>");

		// 日志信息
		templite.append("<Log>");

		// 遍历日志信息
		List pLogList = pInfo.getLogList();
		
		for (Iterator it = pLogList.iterator(); it.hasNext();)
		{
			DealProcessLogModel pLogInfo = (DealProcessLogModel) it.next();
			templite.append("<LogInfo>");

			// Act
			templite.append("<Act>");
			templite.append(pLogInfo.getAct());
			templite.append("</Act>");

			// LogUser
			templite.append("<LogUser>");
			templite.append(pLogInfo.getLogUser());
			templite.append("</LogUser>");

			// StDate
			templite.append("<StDate>");
			templite.append(FormatTime.formatIntToDateString(pLogInfo.getStDate()));
			templite.append("</StDate>");

			// Result
			templite.append("<Result>");
			templite.append(pLogInfo.getResult().replace('\n', ' ').replace('\r', ' '));
			templite.append("</Result>");
			templite.append("</LogInfo>");
		}
		templite.append("</Log>");
		templite.append("</ProcessInfo>");
		return templite;
	}
	
	public List getTplVerdictList(String baseID, String schema, String type)
	{
		TplDealVerdict tdv = new TplDealVerdict();
		return tdv.getTplDealVerdictList(baseID, schema, type);
	}
	
	public List getVerdictList(String baseID, String schema, String flowID)
	{
		TplDealVerdict tdv = new TplDealVerdict();
		return tdv.getDealVerdictList(baseID, schema, flowID);
	}
	
	public List getTplLinkList(String baseID, String schema, String type)
	{
		TplDealLink tdl = new TplDealLink();
		return tdl.getTplDealLinkList(baseID, schema, type);
	}
	
	public List getLinkList(String baseID, String schema, String flowID)
	{
		TplDealLink tdl = new TplDealLink();
		return tdl.getDealLinkList(baseID, schema, flowID);
	}
	
	public List getTplDealProcessRoleDefineList(String baseID, String schema, String type)
	{
		TplDealProcessRoleDefine tdprd = new TplDealProcessRoleDefine();
		return tdprd.getTplDealProcessRoleDefineList(baseID, schema, type);
	}
	
	public List getDealProcessRoleDefineList(String baseID, String schema, String flowID)
	{
		TplDealProcessRoleDefine tdprd = new TplDealProcessRoleDefine();
		return tdprd.getDealProcessRoleDefineList(baseID, schema, flowID);
	}
	
	public List getTplCreateBaseDefineList(String baseID, String schema, String type)
	{
		TplCreateBaseDefine tcbd = new TplCreateBaseDefine();
		return tcbd.getTplCreateBaseDefineList(baseID, schema, type);
	}
}






