package com.ultrapower.eoms.mobile.interfaces.formcommit.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import cn.com.ultrapower.eoms.user.rolemanage.people.hibernate.po.SysPeoplepo;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.ultrawf.control.process.BaseAttachmentManage;
import cn.com.ultrapower.ultrawf.control.process.BaseOwnFieldInfoManage;
import cn.com.ultrapower.ultrawf.control.process.ProcessManage;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachment;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseDealObject;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseToDealObject;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Auditing;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Cancel;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Close;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Confirm;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_DealTurnDown;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_DealTurnUp;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Finish;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_NextBaseCustom;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_PhaseNotice;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_Start;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToAssistAuditing;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToAuditing;
import cn.com.ultrapower.ultrawf.control.process.solidifyaction.Action_ToDeal;
import cn.com.ultrapower.ultrawf.models.process.BaseOwnFieldInfoModel;
import cn.com.ultrapower.ultrawf.models.process.ProcessModel;
import cn.com.ultrapower.ultrawf.share.FileOperUtil;
import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.FormatString;

import com.ultrapower.eoms.common.cfg.ConfigKeys;
import com.ultrapower.eoms.common.dao.impl.HibernateDaoImpl;
import com.ultrapower.eoms.mobile.dao.UserLoginDAO;
import com.ultrapower.eoms.mobile.interfaces.formcommit.model.FormCommitInputModel;
import com.ultrapower.eoms.mobile.interfaces.formcommit.model.FormCommitOutputModel;
import com.ultrapower.eoms.mobile.interfaces.syncattach.model.BaseAttachmentInfo;
import com.ultrapower.eoms.mobile.interfaces.syncattach.model.SysAttachModel;
import com.ultrapower.eoms.mobile.service.InterfaceService;
import com.ultrapower.eoms.mobile.util.CryptUtils;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

public class ForCommitManager implements InterfaceService
{
	protected Map<String, BaseFieldInfo> baseAllFields = new Hashtable<String, BaseFieldInfo>();
	private HibernateDaoImpl hibernateDao;
	
	public String call(String xml, String fileList)
	{
		FormCommitInputModel input = new FormCommitInputModel();
		String outputXml = "";

		try
		{
			input.buildModel(xml);
			FormCommitOutputModel output = handle(input, fileList);
			outputXml = output.buildXml();
		}
		catch (Exception e)
		{
			outputXml = FormCommitOutputModel.buildExceptionXml(e.getMessage());
			e.printStackTrace();
		}
		return outputXml;
	}

	private FormCommitOutputModel handle(FormCommitInputModel inputModel, String fileList)
	{
		boolean flowSuccess = false;
		FormCommitOutputModel outputModel = new FormCommitOutputModel();

		String baseId = inputModel.getBaseID();
		String baseSchema = inputModel.getCategory();
		String userName = inputModel.getUserName();
		String actionType = inputModel.getActionCode();
		String actionText = inputModel.getActionText();
		String taskId = inputModel.getTaskID();
		String dealActorStr = "";
		List ToUser = new ArrayList();
		List ToUserID = new ArrayList();
		List ToGroup = new ArrayList();
		List ToGroupID = new ArrayList();
		List p_BaseToDealObject = new ArrayList();

		outputModel.setTaskId(taskId);
		//转换字段
		RDParameter p_ProRDParameter = new RDParameter();
		p_ProRDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
		List<BaseOwnFieldInfoModel> baseOwnFieldList = (new BaseOwnFieldInfoManage()).getList(p_ProRDParameter);
		List<BaseFieldInfo> bizFields = new ArrayList<BaseFieldInfo>();
		for (BaseOwnFieldInfoModel field : baseOwnFieldList)
		{
			if (inputModel.getFields().containsKey(field.getBase_field_DBName()))
			{
				if (field.getBase_field_Type().equals("2")
						|| field.getBase_field_Type().equals("4")
						|| field.getBase_field_Type().equals("6")
						|| field.getBase_field_Type().equals("7")
						|| field.getBase_field_Type().equals("10"))
				{
					String value = "";
					inputModel.getFields().get(field.getBase_field_DBName());
					if (field.getBase_field_Type().equals("7"))
					{
						value = String.valueOf(FormatTime.FormatDateStringToInt(inputModel.getFields().get(field.getBase_field_DBName())));
					}
					else
					{
						value = inputModel.getFields().get(field.getBase_field_DBName());
					}
					BaseFieldInfo fieldInfo = new BaseFieldInfo(field.getBase_field_DBName(),field.getBase_field_ID(),value,Integer.parseInt(field.getBase_field_Type()));
					// 字段类型 0：display only 1：Optional 2：required
//					if(field.getEntryMode() !=0 )
					if(!"1000".equals(field.getBase_field_Type()))
						bizFields.add(fieldInfo);
				}
				if (field.getBase_field_Type().equals("1000"))
				{
					dealActorStr = inputModel.getFields().get(field.getBase_field_DBName());
					if(dealActorStr.length()>0){
						String[] dealActorArr = dealActorStr.split(";");
						if(dealActorArr.length>0){
							for(String dealActor:dealActorArr){
								if(dealActor.length()>0){
									String[] dealArray = dealActor.split(":");
									String[] dealer = dealArray[1].split(",");
									if("U".equals(dealArray[0])){
										ToUser.add(dealer[1]);
										ToUserID.add(dealer[0]);
									}
									if("G".equals(dealArray[0])){
										ToGroup.add(dealer[1]);
										ToGroupID.add(dealer[0]);
									}
								}
							}
						}
						String tmp_AssignIDStr = inputModel.getFields().get("tmp_AssignIDStr");
						if(tmp_AssignIDStr!=null&&tmp_AssignIDStr.length()>0)
						{
							//fanying 动作为 下一步 时，将处理人作为字段传给接口 ，其他自由动作需要封装处理人对象传给接口 
							if("NEXT".equals(actionType)||"FINISH".equals(actionType))
							{
								String[] tmp_INC_X = tmp_AssignIDStr.split(";");
								String[] ToUserArray = tmp_INC_X[0].split(",");
								String[] ToUserIDArray = tmp_INC_X[1].split(",");
								String[] ToGroupArray = tmp_INC_X[2].split(",");
								String[] ToGroupIDArray = tmp_INC_X[3].split(",");
								bizFields.add(new BaseFieldInfo(ToUserArray[1],ToUserArray[0],ToUser.size()==1?(String)ToUser.get(0):"",4));
								bizFields.add(new BaseFieldInfo(ToUserIDArray[1],ToUserIDArray[0],ToUserID.size()==1?(String)ToUserID.get(0):"",4));
								bizFields.add(new BaseFieldInfo(ToGroupArray[1],ToGroupArray[0],ToGroup.size()==1?(String)ToGroup.get(0):"",4));
								bizFields.add(new BaseFieldInfo(ToGroupIDArray[1],ToGroupIDArray[0],ToGroupID.size()==1?(String)ToGroupID.get(0):"",4));
							}
						}
					}
				}
			}
		}

		if (bizFields!=null && bizFields.size()>0) {
			for (int i = 0; i < bizFields.size(); i++) {
				BaseFieldInfo bizField = bizFields.get(i);
				String fieldName = bizField.getStrFieldName();
				baseAllFields.put(fieldName, bizField);
			}
		}
		//根据工单号和processid获取dealprocess信息
		RDParameter Process_rDParameter=new RDParameter();
		Process_rDParameter.addIndirectPar("is_notnull","1",2);
      	Process_rDParameter.addIndirectPar("baseschema",inputModel.getCategory(),4);
     	Process_rDParameter.addIndirectPar("baseid",inputModel.getBaseID(),4);
     	Process_rDParameter.addIndirectPar("processid",inputModel.getTaskID(),4);
		ProcessManage processManage = new ProcessManage();
		ProcessModel processModel = processManage.getOneModel(Process_rDParameter);
		List<BaseAttachmentInfo> attachList = null;
		if(fileList != null && !fileList.equals(""))
		{
			attachList = new ArrayList<BaseAttachmentInfo>();
			String[] attArr = fileList.split("\\|");
			for(String att : attArr)
			{
				String[] attinfo = att.split("\\*");
				BaseAttachmentInfo attModel = new BaseAttachmentInfo(attinfo[2], attinfo[0]);
				attModel.setName(attinfo[0]);
				attModel.setDbname(attinfo[1]);
				attModel.setUploaddate(attinfo[3]);
				
				attachList.add(attModel);
			}
			setAttachment(attachList,processModel,userName);
		}
		
		

		//流转
		if ("ACCEPT".equals(actionType))
		{//受理
			Action_Start action = new Action_Start();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), "DEAL", null);
		}
		else if ("NOTICE".equals(actionType))
		{//阶段处理
			Action_PhaseNotice action = new Action_PhaseNotice();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, bizFields, null);
		}
		else if ("FINISH".equals(actionType))
		{//完成
			Action_Finish action = new Action_Finish();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, bizFields, null);
		}
		else if ("AFFIRM".equals(actionType))
		{//确认
			Action_Confirm action = new Action_Confirm();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, bizFields, null);
		}
		else if ("CLOSE".equals(actionType))
		{//归档
			Action_Close action = new Action_Close();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(),null, bizFields, null);
		}
		else if ("CANCEL".equals(actionType))
		{//作废
			Action_Cancel action = new Action_Cancel();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, bizFields, null);
		}
		else if ("MAKECOPY".equals(actionType))
		{//抄送
			//封装派发人对象
			p_BaseToDealObject = buildToDealObject(2,0,dealActorStr);
			Action_ToDeal action = new Action_ToDeal();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("REASSIGN".equals(actionType))
		{//转派
			p_BaseToDealObject = buildToDealObject(0,1,dealActorStr);
			Action_ToDeal action = new Action_ToDeal();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("ASSIGN".equals(actionType))
		{//派发
			p_BaseToDealObject = buildToDealObject(0,0,dealActorStr);
			Action_ToDeal action = new Action_ToDeal();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("AUDIT".equals(actionType))
		{//提交审批
			p_BaseToDealObject = buildToDealObject(3,0,dealActorStr);
			Action_ToAuditing action = new Action_ToAuditing();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("REAUDIT".equals(actionType))
		{//通过并转审
			p_BaseToDealObject = buildToDealObject(3,0,dealActorStr);
			Action_Auditing action = new Action_Auditing();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, 2, p_BaseToDealObject, bizFields, null);
		}
		else if ("ORGANIZEAUDIT".equals(actionType))
		{//组织会审
			p_BaseToDealObject = buildToDealObject(3,0,dealActorStr);
			Action_ToAssistAuditing action = new Action_ToAssistAuditing();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("AUDITINGPASS".equals(actionType))
		{//审批通过
			p_BaseToDealObject = buildToDealObject(3,0,dealActorStr);
			Action_Auditing action = new Action_Auditing();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, 1, p_BaseToDealObject, bizFields, null);
		}
		else if ("AUDITINGNOPASS".equals(actionType))
		{//审批不通过
			p_BaseToDealObject = null;// buildToDealObject(3,0,dealActorStr);
			Action_Auditing action = new Action_Auditing();
			flowSuccess = action.do_Action(userName, baseSchema, baseId,  inputModel.getTaskID(), null, 0, p_BaseToDealObject, bizFields, null);
		}
		else if ("ORGANIZEAUDITINGPASS".equals(actionType))
		{//会审通过
			
//			BaseAction action = new AssistAuditPassAction();
//			flowSuccess = action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObjs, bizFields, attachList);
		}
		else if ("ORGANIZEAUDITINGNOPASS".equals(actionType))
		{//会审不通过
//			BaseAction action = new AssistAuditNoPassAction();
//			flowSuccess = action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObjs, bizFields, attachList);
		}
		else if ("NEXT".equals(actionType))
		{//下一步
			//TODO
			Action_NextBaseCustom action_Next_Custom = new Action_NextBaseCustom();
			flowSuccess = action_Next_Custom.do_Action(userName, baseSchema,baseId,inputModel.getTaskID(),null,actionText,bizFields,null);
		}
		else if ("REJECT".equals(actionType))
		{//驳回
			p_BaseToDealObject = buildDealObject(dealActorStr);
			Action_DealTurnUp action = new Action_DealTurnUp();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(), null, p_BaseToDealObject, bizFields, null);
		}
		else if ("SENDBACK".equals(actionType))
		{//退回
			p_BaseToDealObject = buildToDealObject(0,0,dealActorStr);
			Action_DealTurnDown action = new Action_DealTurnDown();
			flowSuccess = action.do_Action(userName, baseSchema, baseId, inputModel.getTaskID(),null, p_BaseToDealObject, bizFields, null);
		}
		if(flowSuccess)
		{
			outputModel.setSuccess(1);
		}
		else
		{
			outputModel.setSuccess(0);
		}
		return outputModel;
	}
	protected List buildDealObject(String dealActorStr){
		List p_BaseToDealObject = new ArrayList();
		String[] dealActorArr = dealActorStr.split(";");
		if(dealActorArr.length>0){
			for(String dealActor:dealActorArr){
				if(dealActor.length()>0){
					BaseDealObject baseDealObject = new BaseDealObject();
					String[] dealArray = dealActor.split(":");
					String[] dealer = dealActorArr[1].split(",");
					if("U".equals(dealArray[0])){
						baseDealObject.setAssginee(dealer[1]);
						baseDealObject.setAssgineeID(dealer[0]);
					}
					if("G".equals(dealArray[0])){
						baseDealObject.setGroup(dealer[1]);
						baseDealObject.setGroupID(dealer[0]);
					}
					p_BaseToDealObject.add(baseDealObject);
					
				}
			}
		}
		return p_BaseToDealObject;
	}
	protected List buildToDealObject(int flagType,int isTransfer,String dealActorStr){
		List p_BaseToDealObject = new ArrayList();
		String[] dealActorArr = dealActorStr.split(";");
		if(dealActorArr.length>0){
			for(String dealActor:dealActorArr){
				if(dealActor.length()>0){
					BaseToDealObject baseToDealObject = new BaseToDealObject();
					String[] dealArray = dealActor.split(":");
					String[] dealer = dealArray[1].split(",");
					if("U".equals(dealArray[0])){
						baseToDealObject.setAssginee(dealer[1]);
						baseToDealObject.setAssgineeID(dealer[0]);
					}
					if("G".equals(dealArray[0])){
						baseToDealObject.setGroup(dealer[1]);
						baseToDealObject.setGroupID(dealer[0]);
					}
					
					baseToDealObject.setFlagType(flagType);//设置处理类型 (主办：0) (协办：1) (抄送：2) (审批：3) (质检：4) (会审：5)
					baseToDealObject.setFlag31IsTransfer(isTransfer);//是否转派过来的工单：0否；1是；默认为0，默认为：0
//					baseToDealObject.setAssignOverTimeDate(0);
//					baseToDealObject.setDealOverTimeDate(0);
//					baseToDealObject.setAcceptOverTimeDate(0);
					baseToDealObject.setFlag01Assign(1);
					baseToDealObject.setFlag02Copy(1);
					baseToDealObject.setFlag03Assist(1);
					baseToDealObject.setFlag04Transfer(1);
					baseToDealObject.setFlag05TurnDown(1);
					baseToDealObject.setFlag06TurnUp(1);
					baseToDealObject.setFlag07Recall(1); 
					baseToDealObject.setFlag08Cancel(1);
					baseToDealObject.setFlag09Close(1);
					baseToDealObject.setFlag15ToAuditing(1);
					p_BaseToDealObject.add(baseToDealObject);
					
				}
			}
		}
		return p_BaseToDealObject;
	}
	/**
	 * 附件提交
	 * @param attachs
	 * @return
	 */
	protected boolean setAttachment(List<BaseAttachmentInfo> attachs,ProcessModel processModel,String userName) {
		
		cn.com.ultrapower.ultrawf.models.process.BaseAttachment m_BaseAttachment = new cn.com.ultrapower.ultrawf.models.process.BaseAttachment();
		boolean rtn = true;
		if (attachs!=null&&attachs.size()>0) {
			String p_BaseID = processModel.getProcessBaseID();
			String p_BaseSchema = processModel.getProcessBaseSchema();
			String p_ProcessID = processModel.getProcessID();
			String p_ProcessType = processModel.getProcessType();
			String p_PhaseNo = processModel.getPhaseNo();
			String p_ProcessLogID = "" ;
			String p_upLoadUser = userName;
			String p_upLoadUserID = userName;
			
			//根据用户登录名获取fullname
			UserLoginDAO  uld = new UserLoginDAO();
			Vector vec = uld.findLoginUser(userName);
			if(vec.size() > 0)
			{
				SysPeoplepo  sp = null;
				sp = (SysPeoplepo)vec.get(0);
				p_upLoadUser = sp.getC630000003();
			}
			
			for (int i = 0; i < attachs.size(); i++) {
				BaseAttachmentInfo atta = attachs.get(i);
				String fileName = atta.getPath();
				Random15 random = new Random15();
				String guid = random.getRandom(System.currentTimeMillis());
				BaseAttachmentManage att = new BaseAttachmentManage();
			 	String attachID = att.baseAttachmentPush(p_BaseID,p_BaseSchema,p_ProcessID,
			 							p_ProcessType,p_PhaseNo,p_ProcessLogID,"1",p_upLoadUser,p_upLoadUserID,
			 							Init_Get_TIMESTAMP().toString(),atta.getName(),
			 							p_upLoadUser+"在"+FormatTime.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"上传！",
			 							fileName,guid);
			 	List<SysAttachModel> sysAttachModelList = hibernateDao.find("from SysAttachModel where dbname=?", new String[]{atta.getDbname()});
			 	if(sysAttachModelList!=null&&sysAttachModelList.size()==1){
			 		SysAttachModel sysAttachModel = sysAttachModelList.get(0);
			 		String oldId = sysAttachModel.getAttachID();
			 		sysAttachModel.setAttachID(attachID);
			 		hibernateDao.save(sysAttachModel);
			 		hibernateDao.removeById(SysAttachModel.class, oldId);
			 	}
			}
		}
		return rtn;
	}
	public String getFieldValue(String fieldName) {
		BaseFieldInfo baseField = baseAllFields.get(fieldName);
		if (baseField == null) {
			return "";
		}
		return baseField.getStrFieldValue();
	}
	private Long Init_Get_TIMESTAMP() { 
		Long m_obj_Long_TIMESTAMP = new Long(System.currentTimeMillis()/1000);
		return m_obj_Long_TIMESTAMP;
	}

	public HibernateDaoImpl getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(HibernateDaoImpl hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}
	
}
