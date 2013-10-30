package cn.com.ultrapower.ultrawf.control.process;

import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;

public interface IProcessLog {
	public List GetDealtList(String p_ProcessID ,int p_IsArchive);
	public List GetDealtList(String p_Baschema,String p_BaseID,int p_IsArchive);
	public List GetAuditingtList(String p_ProcessID,int p_IsArchive);
	public List GetAuditingtList(String p_Baschema,String p_BaseID,int p_IsArchive);
	
	public List getListForBind(ParDealProcessLogModel p_DealProcessLog,int p_PageNumber,int p_StepRow);
}
