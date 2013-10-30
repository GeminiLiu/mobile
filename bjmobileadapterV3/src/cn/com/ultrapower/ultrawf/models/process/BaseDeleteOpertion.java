package cn.com.ultrapower.ultrawf.models.process;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class BaseDeleteOpertion {

	/**
	 * 根据工单信息删除和工单相关联的数据
	 * @param parBaseModel
	 * @return
	 */
	public boolean deleteBaseRelate(ParBaseModel parBaseModel)
	{
		
		
		
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		
		RemedyOp.FormDataDelete("","");
		
		RemedyOp.RemedyLogout();
		return true;
	}
	
	public boolean deleteBaseRealte(String baseIds,String baseSchema)
	{
		baseIds=FormatString.CheckNullString(baseIds);
		baseSchema=FormatString.CheckNullString(baseSchema);
		if(baseIds.equals("")|| baseSchema.equals(""))
			return false;
		//删除处理日志表
		DealProcessLog m_DealProcessLog=new DealProcessLog();		
		ParDealProcessLogModel m_ParDealProcessLog=new ParDealProcessLogModel();
		m_ParDealProcessLog.setBaseID("OR:"+baseIds);
		m_ParDealProcessLog.setBaseSchema(baseSchema);
		m_ParDealProcessLog.setIsArchive(0);
		m_DealProcessLog.delete(0,m_ParDealProcessLog);
		
		//删除审批日志
		AuditingProcessLog m_AuditingProcessLog=new AuditingProcessLog();
		ParAuditingProcessLogModel m_ParAuditingProcessLogModel=new ParAuditingProcessLogModel();
		m_ParAuditingProcessLogModel.setAct("OR:"+baseIds);
		m_ParAuditingProcessLogModel.setBaseSchema(baseSchema);
		m_ParAuditingProcessLogModel.setIsArchive(0);
		m_AuditingProcessLog.delete(0,m_ParAuditingProcessLogModel);
				
		//删除Process信息
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessBaseID("OR:"+baseIds);
		m_ParDealProcess.setProcessBaseSchema(baseSchema);
		m_ParDealProcess.setIsArchive(0);
		
		DealProcess m_DealProcess=new DealProcess();
		m_DealProcess.delete(0,m_ParDealProcess);
		
		//删除Auditing信息
		AuditingProcess m_AuditingProcess=new AuditingProcess();
		m_AuditingProcess.delete(0,m_ParDealProcess);
		
		//删除DealLink信息		
		DealProcessLink m_DealProcessLink=new DealProcessLink();
		ParDealProcessLinkModel m_ParDealProcessLinkModel=new ParDealProcessLinkModel();
		m_ParDealProcessLinkModel.setLinkBaseID("OR:"+baseIds);
		m_ParDealProcessLinkModel.setLinkBaseSchema(baseSchema);
		m_ParDealProcessLinkModel.setIsArchive(0);
		m_DealProcessLink.delete(0,m_ParDealProcessLinkModel);
		
		//删除AuditingLink信息
		AuditingProcessLink m_AuditingProcessLink=new AuditingProcessLink();
		ParAuditingProcessLinkModel m_ParAuditingProcessLinkModel=new ParAuditingProcessLinkModel();
		m_ParAuditingProcessLinkModel.setBaseID("OR:"+baseIds);
		m_ParAuditingProcessLinkModel.setBaseSchema(baseSchema);
		m_ParAuditingProcessLinkModel.setIsArchive(0);
		m_AuditingProcessLink.delete(0,m_ParAuditingProcessLinkModel);
		
		//删除附件
		BaseAttachment m_BaseAttachment=new BaseAttachment();
		ParBaseAttachmentModel m_ParBaseAttachmentModel=new ParBaseAttachmentModel();
		m_ParBaseAttachmentModel.setBaseID("OR:"+baseIds);
		m_ParBaseAttachmentModel.setBaseSchema(baseSchema);		
		m_BaseAttachment.delete(m_ParBaseAttachmentModel);
		
		//删除工单信息
		Base m_Base=new Base();
		ParBaseModel m_ParBaseModel=new ParBaseModel();
		m_ParBaseModel.setBaseID("OR:"+baseIds);
		m_ParBaseModel.setBaseSchema(baseSchema);
		//m_ParBaseModel.setbase
		m_Base.delete(m_ParBaseModel);
		
		//删除BaseInfor信息
		BaseInfor m_BaseInfor=new BaseInfor();
		ParBaseInforModel m_ParBaseInforModel=new ParBaseInforModel();
		m_ParBaseInforModel.setBaseID("OR:"+baseIds);
		m_ParBaseInforModel.setBaseSchema(baseSchema);
		m_BaseInfor.delete(m_ParBaseInforModel);
		
		
		return true;
	}
	
	
}
