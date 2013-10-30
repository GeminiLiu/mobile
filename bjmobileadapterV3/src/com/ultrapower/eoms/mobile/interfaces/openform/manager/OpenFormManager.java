package com.ultrapower.eoms.mobile.interfaces.openform.manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.process.AuditingProcessManage;
import cn.com.ultrapower.ultrawf.control.process.BaseManage;
import cn.com.ultrapower.ultrawf.control.process.BaseOwnFieldInfoManage;
import cn.com.ultrapower.ultrawf.control.process.DealProcessManage;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.BaseFieldBean;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.share.FormatInt;
import cn.com.ultrapower.ultrawf.share.FormatLong;
import cn.com.ultrapower.ultrawf.share.FormatString;

import com.ultrapower.eoms.common.dao.impl.HibernateDaoImpl;
import com.ultrapower.eoms.mobile.interfaces.openform.model.OpenFormInputModel;
import com.ultrapower.eoms.mobile.interfaces.openform.model.OpenFormOutputModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;

public class OpenFormManager implements InterfaceService
{
	private HibernateDaoImpl hibernateDao;

	public String call(String xml, String fileList)
	{
		OpenFormInputModel input = new OpenFormInputModel();
		String outputXml;

		try
		{
			input.buildModel(xml);
			OpenFormOutputModel output = handle(input);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = OpenFormOutputModel.buildExceptionXml();
			e.printStackTrace();
		}
		return outputXml;
	}

	private OpenFormOutputModel handle(OpenFormInputModel inputModel)
	{
		OpenFormOutputModel outputModel = new OpenFormOutputModel();

		List<String[]> actions = new ArrayList<String[]>();
		Map<String, String> fields = new LinkedHashMap<String, String>();
		
		DealProcessModel dpModel = null;
		AuditingProcessModel apModel = null;
		Long flagPredefined = 0L;
		Long flagActive = 0L;
		int flagAssign 	= 0;
		int flagCopy 	= 	0;
		int flagAssist 	= 	0;
		int flagTransfer = 	0;
		int flagTurnDown = 0;
		int flagTurnUp 	= 	0;
		int flagCancel 	= 	0;
		int flagClose 	= 	0;
		int flagToAuditing 		= 0;
		int flagToAssistAuditing = 0;
		String prePhaseNo 	= "";
		String flagAssignType 		= "";
		String processType = "";
		Long bgDate = 0L;
		RDParameter p_ProRDParameter = new RDParameter();
		p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		p_ProRDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);	
		p_ProRDParameter.addIndirectPar("processid",inputModel.getTaskID(),4);	
		dpModel = (new DealProcessManage()).getOneModel(p_ProRDParameter);
		if(dpModel==null)
		{
			apModel = (new AuditingProcessManage()).getOneModel(p_ProRDParameter);
		}
		if (dpModel != null)
		{
			//处理固定动作
			String customActions = dpModel.getCustomActions();
			if (customActions != null && !customActions.equals(""))
			{
				String[] actionArr = customActions.split(";");
				if (!ArrayUtils.isEmpty(actionArr))
				{
					for (int i = 0; i < actionArr.length; i++)
					{
						String actionStr = actionArr[i];
						if (actionStr != null && !actionStr.equals(""))
						{
							String[] actionAry = actionStr.split("=");
							actions.add(new String[] { "NEXT", actionAry[1], actionAry[0] });
						}
					}
				}
			}
			//处理自由动作
			flagPredefined = dpModel.getFlagPredefined();
			flagActive = dpModel.getFlagActive();
		    prePhaseNo 	= 	dpModel.getPrevPhaseNo();//.getPrePhaseNo();
			flagAssign 	= FormatInt.FormatStringToInt(dpModel.getFlag01Assign())	;//.getFlagAssign();
			flagCopy 	= 	FormatInt.FormatStringToInt(dpModel.getFlag02Copy());//.getFlagCopy();
			flagAssist 	= 	FormatInt.FormatStringToInt(dpModel.getFlag03Assist());//.getFlagAssist();
			flagTransfer = 	FormatInt.FormatStringToInt(dpModel.getFlag04Transfer());//.getFlagTransfer();
			flagTurnDown = FormatInt.FormatStringToInt(	dpModel.getFlag05TurnDown());//.getFlagTurnDown();
			flagTurnUp 	= 	FormatInt.FormatStringToInt(dpModel.getFlag06TurnUp());//.getFlagTurnUp();
			flagCancel 	= 	FormatInt.FormatStringToInt(dpModel.getFlag08Cancel());//.getFlagCancel();
			flagClose 	= 	FormatInt.FormatStringToInt(dpModel.getFlag09Close());//.getFlagClose();
			flagToAuditing 		= FormatInt.FormatStringToInt(dpModel.getFlag15ToAuditing());//.getFlagToAuditing();
			flagToAssistAuditing = FormatInt.FormatStringToInt(dpModel.getFlag16ToAssistAuditing());//.getFlagToAssistAuditing();
			flagAssignType 		= dpModel.getFlagType();
			flagAssignType = flagAssignType.equals("0")?"主办":
								flagAssignType.equals("1")?"协办":
										flagAssignType.equals("2")?"抄送":
											flagAssignType.equals("3")?"审批":
												flagAssignType.equals("4")?"质检":
													flagAssignType.equals("5")?"会审":"";
			//.getFlagAssignGroupOrUser();//.getFlagAssignType();
			processType = dpModel.getProcessType();
			bgDate = dpModel.getBgDate();
		}else if(apModel!=null)
		{
			//处理自由动作
			flagPredefined = apModel.getFlagPredefined();
			flagActive = apModel.getFlagActive();
		    prePhaseNo 	= 	apModel.getPrevPhaseNo();
			flagAssign 	= FormatInt.FormatStringToInt(apModel.getFlag01Assign())	;//.getFlagAssign();
			flagCopy 	= 	FormatInt.FormatStringToInt(apModel.getFlag02Copy());//.getFlagCopy();
			flagAssist 	= 	FormatInt.FormatStringToInt(apModel.getFlag03Assist());//.getFlagAssist();
			flagTransfer = 	FormatInt.FormatStringToInt(apModel.getFlag04Transfer());//.getFlagTransfer();
//			flagTurnDown = FormatInt.FormatStringToInt(	apModel.getFlag05TurnDown());//.getFlagTurnDown();
//			flagTurnUp 	= 	FormatInt.FormatStringToInt(apModel.getFlag06TurnUp());//.getFlagTurnUp();
			flagCancel 	= 	FormatInt.FormatStringToInt(apModel.getFlag08Cancel());//.getFlagCancel();
//			flagClose 	= 	FormatInt.FormatStringToInt(apModel.getFlag09Close());//.getFlagClose();
			flagToAuditing 		= FormatInt.FormatStringToInt(apModel.getFlag15ToAuditing());//.getFlagToAuditing();
			flagToAssistAuditing = FormatInt.FormatStringToInt(apModel.getFlag16ToAssistAuditing());//.getFlagToAssistAuditing();
			flagAssignType 		= apModel.getFlagType();
			flagAssignType = flagAssignType.equals("0")?"主办":
								flagAssignType.equals("1")?"协办":
										flagAssignType.equals("2")?"抄送":
											flagAssignType.equals("3")?"审批":
												flagAssignType.equals("4")?"质检":
													flagAssignType.equals("5")?"会审":"";
			//.getFlagAssignGroupOrUser();//.getFlagAssignType();
			processType = apModel.getProcessType();
			bgDate = apModel.getBgDate();
		}
		if(dpModel!=null||apModel!=null)
		{
			if (1 == flagAssign && !"审批".equals(flagAssignType))
			{
				actions.add(new String[] { "ASSIGN", "ASSIGN", "派发" });
			}
			if (1 == flagTransfer && 0 == flagPredefined)
			{
				actions.add(new String[] { "REASSIGN", "REASSIGN", "移交" });
			}
			if (1 == flagCopy && 0 == flagPredefined)
			{
				actions.add(new String[] { "MAKECOPY", "MAKECOPY", "抄送" });
			}
			if (1 == flagAssist && 0 == flagPredefined)
			{
				actions.add(new String[] { "ASSIST", "ASSIST", "协办" });
			}
			if (1 == flagToAuditing/* && 0 == flagPredefined */&& !"审批".equals(flagAssignType))
			{
				actions.add(new String[] { "AUDIT", "AUDIT", "提交审批" });
			}
			if (1 == flagToAssistAuditing && 0 == flagPredefined)
			{
				actions.add(new String[] { "ORGANIZEAUDIT", "ORGANIZEAUDIT", "组织会审" });
			}
			if (("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) && "DEAL".equals(processType) && (!"BEGIN".equals(prePhaseNo)) && 0 == bgDate)
			{
				actions.add(new String[] { "ACCEPT", "ACCEPT", "受理" });
			}
			if (("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) && "DEAL".equals(processType) && (!"BEGIN".equals(prePhaseNo)))
			{
				actions.add(new String[] { "NOTICE", "NOTICE", "阶段处理" });
			}
			if (("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) && "DEAL".equals(processType) && (!"BEGIN".equals(prePhaseNo)) && 0 == flagPredefined)
			{
				actions.add(new String[] { "FINISH", "FINISH", "完成" });
			}
			if ("审批".equals(flagAssignType) && "AUDITING".equals(processType) && 0 == flagPredefined)
			{
				actions.add(new String[] { "AUDITINGPASS", "AUDITINGPASS", "审批通过" });
				actions.add(new String[] { "AUDITINGNOPASS", "AUDITINGNOPASS", "审批不通过" });
				actions.add(new String[] { "REAUDIT", "REAUDIT", "审批通过并转审" });
			}
			if ("会审".equals(flagAssignType) && "AUDITING".equals(processType) && 0 == flagPredefined)
			{
				actions.add(new String[] { "ORGANIZEAUDITINGPASS", "ORGANIZEAUDITINGPASS", "会审通过" });
				actions.add(new String[] { "ORGANIZEAUDITINGNOPASS", "ORGANIZEAUDITINGNOPASS", "会审不通过" });
			}
			if ("抄送".equals(flagAssignType) && "DEAL".equals(processType) && 0 == flagPredefined)
			{
				actions.add(new String[] { "AFFIRM", "AFFIRM", "确认" });
			}
			if (1 == flagClose && "DEAL".equals(processType))
			{
				actions.add(new String[] { "CLOSE", "CLOSE", "归档" });
			}
			if (1 == flagCancel)
			{
				actions.add(new String[] { "CANCEL", "CANCEL", "作废" });
			}
			if (!"BEGIN".equals(prePhaseNo) && ("主办".equals(flagAssignType) || "协办".equals(flagAssignType) || "抄送".equals(flagAssignType)) && "DEAL".equals(processType) && 1 == flagTurnUp)
			{
				actions.add(new String[] { "REJECT", "REJECT", "处理驳回" });
			}
			if ("审批".equals(flagAssignType) && "AUDITING".equals(processType) && 1 == flagTurnUp)
			{
				actions.add(new String[] { "REJECT", "REJECT", "审批驳回" });
			}
			if ("会审".equals(flagAssignType) && "AUDITING".equals(processType) && 1 == flagTurnUp)
			{
				actions.add(new String[] { "REJECT",  "REJECT", "会审驳回" });
			}
			if (0 == flagPredefined && 1 == flagTurnDown && "DEAL".equals(processType) && ("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) && (1 == flagActive || 2 == flagActive))
			{
				actions.add(new String[] { "SENDBACK", "SENDBACK", "处理退回" });
			}
		}
		p_ProRDParameter = new RDParameter();
		p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		p_ProRDParameter.addIndirectPar("selectcondition"," and C650000017 = 1 ",4);
		p_ProRDParameter.addIndirectPar("orderby"," C650000013",4);
		
		//表单数据
		List<BaseOwnFieldInfoModel> baseOwnFieldList = (new BaseOwnFieldInfoManage()).getList(p_ProRDParameter);
		long m_BFCount 	= baseOwnFieldList.size();
		String m_str_Select_BaseOwnField = "";
		for (int i=0;i<m_BFCount;i++)
		{
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)baseOwnFieldList.get(i);
			String m_Base_field_Type		= m_BaseOwnFieldInfoModel.getBase_field_Type();
			String m_Base_field_TypeValue	= m_BaseOwnFieldInfoModel.getBase_field_TypeValue();
			String m_Base_field_DBName		= m_BaseOwnFieldInfoModel.getBase_field_DBName();
			String m_Base_field_ID			= m_BaseOwnFieldInfoModel.getBase_field_ID();
			Long m_EntryMode =  m_BaseOwnFieldInfoModel.getEntryMode();
			// 字段类型 0：display only 1：Optional 2：required
			if(m_EntryMode != 0 )
			{
				m_str_Select_BaseOwnField 		= m_str_Select_BaseOwnField + " Base.C" + m_Base_field_ID + " as " + m_Base_field_DBName + ",";
			}
		}
		p_ProRDParameter = new RDParameter();
		p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		p_ProRDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);
		p_ProRDParameter.addIndirectPar("formname",inputModel.getCategory(),4);
		p_ProRDParameter.addIndirectPar("selectrow",m_str_Select_BaseOwnField,4);
		BaseModel baseModel = (new BaseManage()).getOneModel(p_ProRDParameter);
		if (baseModel != null)
		{
			Hashtable<String, BaseFieldBean> M_BaseFieldHashtable = baseModel.getM_BaseFieldHashtable();
			if(M_BaseFieldHashtable!=null)
			{
				BaseFieldBean tempBaseFieldBean =  M_BaseFieldHashtable.get("BASECREATORFULLNAME");
				baseModel.setBaseCreatorFullName(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean =  M_BaseFieldHashtable.get("BASECREATORCONNECTWAY");
				baseModel.setBaseCreatorConnectWay(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASECREATORCORP");
				baseModel.setBaseCreatorCorp(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASECREATORDEP");
				baseModel.setBaseCreatorDep(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASECREATORDN");
				baseModel.setBaseCreatorDN(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASECREATEDATE");
				baseModel.setBaseCreateDate( FormatLong.FormatStringToLong(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASECLOSEDATE");
				baseModel.setBaseCloseDate( FormatLong.FormatStringToLong(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASEACCEPTOUTTIME");
				baseModel.setBaseAcceptOutTime( FormatLong.FormatStringToLong(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("BASEDEALOUTTIME");
				baseModel.setBaseDealOutTime( FormatLong.FormatStringToLong(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
				tempBaseFieldBean = M_BaseFieldHashtable.get("DEALPROCESSDESC");
				baseModel.setBaseDescrption(FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
//				baseModel.setDealProcessDesc( FormatString.CheckNullString(tempBaseFieldBean!=null?tempBaseFieldBean.getM_BaseFieldValue():""));
			}
			//赋值工单通用字段
			fields.put("BaseCreatorFullName",FormatString.CheckNullString(baseModel.getBaseCreatorFullName()));
			fields.put("BaseCreatorConnectWay", FormatString.CheckNullString(baseModel.getBaseCreatorConnectWay()));
			fields.put("BaseCreatorCorp", FormatString.CheckNullString(baseModel.getBaseCreatorCorp()));
			fields.put("BaseCreatorDep", FormatString.CheckNullString(baseModel.getBaseCreatorDep()));
			fields.put("BaseCreatorDN", FormatString.CheckNullString(baseModel.getBaseCreatorDN()));
			fields.put("BaseCreateDate", String.valueOf(baseModel.getBaseCreateDate()));
			fields.put("BaseCloseDate", String.valueOf(baseModel.getBaseCloseDate()));
			fields.put("BaseAcceptOutTime", String.valueOf(baseModel.getBaseAcceptOutTime()));
			fields.put("BaseDealOutTime", String.valueOf(baseModel.getBaseDealOutTime()));
			fields.put("DealProcessDesc", String.valueOf(dpModel != null ? dpModel.getDesc() : ""));
			
			for (BaseOwnFieldInfoModel field : baseOwnFieldList)
			{
				if(field.getBase_field_Type().equals("1000"))
				{
					//continue;
				}
				String code = field.getBase_field_DBName();
				String value = "";
				if(baseModel.getOneBaseFieldBean(code) != null)
				{
					value = baseModel.getOneBaseFieldBean(code).getM_BaseFieldValue();
				}
				fields.put(code, value);
			}
		}
		outputModel.setActions(actions);
		outputModel.setFields(fields);

		return outputModel;
	}
}
