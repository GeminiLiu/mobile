package cn.com.ultrapower.ultrawf.control.process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Hashtable;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.FormatTime;
//import cn.com.ultrapower.ultrawf.control.process.bean.PublicFieldInfo;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.ultrawf.control.config.BaseCategoryManage;
import cn.com.ultrapower.ultrawf.models.config.BaseCategory;
import cn.com.ultrapower.ultrawf.models.config.BaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.config.ParBaseCategoryModel;
import cn.com.ultrapower.ultrawf.models.process.Base;
import cn.com.ultrapower.ultrawf.models.process.DealProcess;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLog;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLink;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcess;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLog;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLink;

import cn.com.ultrapower.ultrawf.models.process.BaseFieldModifyLog;
import cn.com.ultrapower.ultrawf.models.process.BaseFieldModifyLogModel;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseFieldModifyLogModel;
import cn.com.ultrapower.ultrawf.models.process.ParBaseModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcess;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParDealProcessLogModel;
import cn.com.ultrapower.ultrawf.models.process.ParAuditingProcessLinkModel;
import cn.com.ultrapower.ultrawf.models.process.ParAuditingProcessLogModel;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.system.remedyop.*;

public class DataArchive {
	
	/**
	 * 根据工单类别和时间查找在该时间之前关闭或作废(_baseStatus)的工单集合 
	 * @param _baseSchema
	 * @param _date
	 * @return
	 */
	private List getBase(String _baseSchema,Date _date){
		List baseModels = null;
		Base base = new Base();
		ParBaseModel parBaseModel = new ParBaseModel();
		parBaseModel.setBaseSchema(_baseSchema);
		//parBaseModel.setBaseStatus(_baseStatus);
		parBaseModel.setBaseIsTrueClose(1);
		parBaseModel.setIsArchive(0);
		parBaseModel.setBaseCloseDateEnd(FormatTime.FormatDateToInt(_date));
		baseModels = base.GetList(parBaseModel,0,0);
		System.out.println("查询时间为：" + _date + "；记录数" + baseModels.size());
		return baseModels;		
	}
	
	/**
	 * 得到处理环节列表
	 * @param _baseSchema
	 * @param _BaseId
	 * @return
	 */
	private List getDealProcess(String _baseSchema,String _BaseId){
		List dealProcessModels = null;
		DealProcess dealProcess = new DealProcess();
		ParDealProcess parDealProcess = new ParDealProcess();
		parDealProcess.setProcessBaseSchema(_baseSchema);
		parDealProcess.setProcessBaseID(_BaseId);
		parDealProcess.setIsArchive(0);
		dealProcessModels = dealProcess.GetList(parDealProcess,0,0);
		return dealProcessModels;
	}
	
	/**
	 * 得到处理环节流转列表
	 * @param _baseSchema
	 * @param _BaseId
	 * @return
	 */
	private List getDealLink(String _baseSchema,String _BaseId){
		List dealProcessLinkModels = null;
		DealProcessLink dealProcessLink = new DealProcessLink();
		ParDealProcessLinkModel parDealProcessLinkModel = new ParDealProcessLinkModel();
		parDealProcessLinkModel.setLinkBaseID(_BaseId);
		parDealProcessLinkModel.setLinkBaseSchema(_baseSchema);
		parDealProcessLinkModel.setIsArchive(0);
		dealProcessLinkModels = dealProcessLink.GetList(parDealProcessLinkModel,0,0);
		return dealProcessLinkModels;
	}
	
	/**
	 * 得到处理环节日志列表
	 * @param _dealProcessId
	 * @return
	 */
	
	private List getDealProcessLog(String _dealProcessId){
		List dealProcessLogModels = null;
		DealProcessLog dealProcessLog = new DealProcessLog();
		ParDealProcessLogModel parDealProcessLogModel = new ParDealProcessLogModel();
		parDealProcessLogModel.setProcessID(_dealProcessId);	
		parDealProcessLogModel.setIsArchive(0);
		dealProcessLogModels = dealProcessLog.GetList(parDealProcessLogModel,0,0);
		return dealProcessLogModels;
	}
	
	
	/**
	 * 得到审批环节信息列表
	 * @param _baseSchema
	 * @param _BaseId
	 * @return
	 */
	private List getAuditingProcess(String _baseSchema,String _BaseId){
		List AuditingProcessModels = null;
		AuditingProcess auditingProcess = new AuditingProcess();
		ParDealProcess parDealProcess = new ParDealProcess();
		parDealProcess.setProcessBaseID(_BaseId);
		parDealProcess.setProcessBaseSchema(_baseSchema);
		parDealProcess.setIsArchive(0);
		AuditingProcessModels = auditingProcess.GetList(parDealProcess,0,0);
		return AuditingProcessModels;
	}
	
	/**
	 * 得到审批环节流转列表
	 * @param _baseSchema
	 * @param _BaseId
	 * @return
	 */
	private List getAuditingLink(String _baseSchema,String _BaseId){
		List auditingProcessLinkModels = null;
		AuditingProcessLink auditingProcessLink = new AuditingProcessLink();
		ParAuditingProcessLinkModel parAuditingProcessLinkModel = new ParAuditingProcessLinkModel();
		parAuditingProcessLinkModel.setLinkBaseID(_BaseId);
		parAuditingProcessLinkModel.setLinkBaseSchema(_baseSchema);
		parAuditingProcessLinkModel.setIsArchive(0);
		auditingProcessLinkModels = auditingProcessLink.GetList(parAuditingProcessLinkModel,0,0);
		return auditingProcessLinkModels;
	}
	
	/**
	 * 得到审批环节日志列表
	 * @param _dealProcessId
	 * @return
	 */
	
	private List getAuditingProcessLog(String _auditingProcessId){
		List auditingProcessLogModel = null;
		AuditingProcessLog auditingProcessLog = new AuditingProcessLog();
		ParAuditingProcessLogModel parAuditingProcessLogModel = new ParAuditingProcessLogModel();
		parAuditingProcessLogModel.setProcessID(_auditingProcessId);
		parAuditingProcessLogModel.setIsArchive(0);
		auditingProcessLogModel = auditingProcessLog.GetList(parAuditingProcessLogModel,0,0);
		return auditingProcessLogModel;
	}
	
	/**
	 * 得到工单审批或处理的环节对字段修改的日志信息记录列表
	 * @param _dealProcessId
	 * @return
	 */
	
	private List getBaseFieldModifyLog(String p_ProcessId,String p_Processtype){
		List m_BaseFieldModifyLogModelList = null;
		ParBaseFieldModifyLogModel 	m_ParBaseFieldModifyLogModel = new ParBaseFieldModifyLogModel();
		BaseFieldModifyLogManage	m_BaseFieldModifyLogManage = new BaseFieldModifyLogManage();
		m_ParBaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessID(p_ProcessId);
		m_ParBaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessType(p_Processtype);
		m_ParBaseFieldModifyLogModel.setIsArchive(0);
		m_BaseFieldModifyLogModelList=m_BaseFieldModifyLogManage.getList(m_ParBaseFieldModifyLogModel,0,0);
		return m_BaseFieldModifyLogModelList;
	}	
	
	/**
	 * 将数据备份到archive表并删除
	 * @param _baseSchema
	 * @param _BaseId
	 */
	private void operateOneBaseArchive(String _baseSchema,String _BaseId){		
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        
		DealProcess dealProcess = new DealProcess();
		DealProcessLog dealProcessLog = new DealProcessLog();
		DealProcessLink dealProcessLink = new DealProcessLink();
		AuditingProcess auditingProcess = new AuditingProcess();
		AuditingProcessLog auditingProcessLog = new AuditingProcessLog();
		AuditingProcessLink auditingProcessLink = new AuditingProcessLink();
		BaseFieldModifyLog m_BaseFieldModifyLog = new BaseFieldModifyLog();
		
		DealProcessModel dealProcessModel = null;
		DealProcessLogModel dealProcessLogModel = null;
		DealProcessLinkModel dealProcessLinkModel = null;
		AuditingProcessModel auditingProcessModel = null;
		AuditingProcessLogModel auditingProcessLogModel = null;
		AuditingProcessLinkModel auditingProcessLinkModel = null;
		BaseFieldModifyLogModel m_BaseFieldModifyLogModel = null;
		
		List dealProcessModels = null;
		List dealProcessLogModels = null;
		List dealProcessLinkModels = null;
		List auditingProcessModels = null;
		List auditingProcessLogModels = null;
		List auditingProcessLinkModels = null;
		List m_BaseFieldModifyLogModelList = null;
		
		String processId = null;
		
		//处理环节信息和日志信息备份
		Hashtable dealprocessHashtable = this.DealProcessInit();
		Hashtable dealprocesslogHashtable = this.DealProcessLogInit();
		Hashtable m_BaseFieldModifyLogHashtable = this.BaseFieldModifyLogInit();
		dealProcessModels = this.getDealProcess(_baseSchema,_BaseId);
		for(int process=0;process<dealProcessModels.size();process++){
			
			dealProcessModel = (DealProcessModel)dealProcessModels.get(process);
			dealProcessLogModels = this.getDealProcessLog(dealProcessModel.getProcessID());
			m_BaseFieldModifyLogModelList = this.getBaseFieldModifyLog(dealProcessModel.getProcessID(),dealProcessModel.getProcessType());
			dealprocessHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealProcess,dealProcessModel.getProcessID());
			processId = dealProcess.Insert(dealprocessHashtable,1);
			dealProcess.Delete(0,dealProcessModel.getProcessID());
			
			for(int log=0;log<dealProcessLogModels.size();log++){
				dealProcessLogModel = (DealProcessLogModel)dealProcessLogModels.get(log);			
				dealprocesslogHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealProcessLog,dealProcessLogModel.getProcessLogID());
				((PublicFieldInfo)dealprocesslogHashtable.get("ProcessID")).setStrFieldValue(processId); 
				dealProcessLog.Insert(dealprocesslogHashtable,1);
				dealProcessLog.Delete(0,dealProcessLogModel.getProcessLogID());
				dealprocesslogHashtable.clear();
			}
			
			for(int fieldModifyLog=0;fieldModifyLog<m_BaseFieldModifyLogModelList.size();fieldModifyLog++){
				m_BaseFieldModifyLogModel = (BaseFieldModifyLogModel)m_BaseFieldModifyLogModelList.get(fieldModifyLog);			
				m_BaseFieldModifyLogHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblBaseFieldModifyLog,m_BaseFieldModifyLogModel.getBase_Field_ModifyLog_ID());
				((PublicFieldInfo)m_BaseFieldModifyLogHashtable.get("Base_Field_ModifyLog_ProcessID")).setStrFieldValue(processId); 
				m_BaseFieldModifyLog.Insert(m_BaseFieldModifyLogHashtable,1);
				m_BaseFieldModifyLog.Delete(0,m_BaseFieldModifyLogModel.getBase_Field_ModifyLog_ID());
				m_BaseFieldModifyLogHashtable.clear();
			}			
			
			dealprocessHashtable.clear();
		}
		
		//处理环节流转信息备份
		Hashtable deallinkHashtable = this.DealLinkInit();
		dealProcessLinkModels = this.getDealLink(_baseSchema,_BaseId);
		for(int link=0;link<dealProcessLinkModels.size();link++){
			dealProcessLinkModel = (DealProcessLinkModel)dealProcessLinkModels.get(link); 
			deallinkHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealLink,dealProcessLinkModel.getLinkID());
			dealProcessLink.Insert(deallinkHashtable,1);
			dealProcessLink.Delete(0,dealProcessLinkModel.getLinkID());
			deallinkHashtable.clear();
		}
		
		
        //审批环节信息和日志信息备份
		Hashtable auditingprocessHashtable = this.AuditingProcessInit();
		Hashtable auditingprocesslogHashtable = this.AuditingProcessLogInit();
		auditingProcessModels = this.getAuditingProcess(_baseSchema,_BaseId);
		for(int process=0;process<auditingProcessModels.size();process++){
			
			auditingProcessModel = (AuditingProcessModel)auditingProcessModels.get(process);
			auditingProcessLogModels = this.getAuditingProcessLog(auditingProcessModel.getProcessID());
			auditingprocessHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingProcess,auditingProcessModel.getProcessID());
			processId = auditingProcess.Insert(auditingprocessHashtable,1);
			auditingProcess.Delete(0,auditingProcessModel.getProcessID());
			
			for(int log=0;log<auditingProcessLogModels.size();log++){
				auditingProcessLogModel = (AuditingProcessLogModel)auditingProcessLogModels.get(log);
				auditingprocesslogHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingProcessLog,auditingProcessLogModel.getProcessLogID());
				((PublicFieldInfo)auditingprocesslogHashtable.get("ProcessID")).setStrFieldValue(processId);
				auditingProcessLog.Insert(auditingprocesslogHashtable,1);
				auditingProcessLog.Delete(0,auditingProcessLogModel.getProcessLogID());
				auditingprocesslogHashtable.clear();
			}
			
			for(int fieldModifyLog=0;fieldModifyLog<m_BaseFieldModifyLogModelList.size();fieldModifyLog++){
				m_BaseFieldModifyLogModel = (BaseFieldModifyLogModel)m_BaseFieldModifyLogModelList.get(fieldModifyLog);
				m_BaseFieldModifyLogHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblBaseFieldModifyLog,m_BaseFieldModifyLogModel.getBase_Field_ModifyLog_ID());
				((PublicFieldInfo)m_BaseFieldModifyLogHashtable.get("Base_Field_ModifyLog_ProcessID")).setStrFieldValue(processId); 
				m_BaseFieldModifyLog.Insert(m_BaseFieldModifyLogHashtable,1);
				m_BaseFieldModifyLog.Delete(0,m_BaseFieldModifyLogModel.getBase_Field_ModifyLog_ID());
				m_BaseFieldModifyLogHashtable.clear();
			}
			
			auditingprocessHashtable.clear();
		}
		
		//审批环节流转信息备份
		Hashtable auditinglinkHashtable = this.AuditingLinkInit();
		auditingProcessLinkModels = this.getAuditingLink(_baseSchema,_BaseId);
		for(int link=0;link<auditingProcessLinkModels.size();link++){
			auditingProcessLinkModel = (AuditingProcessLinkModel)auditingProcessLinkModels.get(link); 
			auditinglinkHashtable = m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingLink,auditingProcessLinkModel.getLinkID());					
		    auditingProcessLink.Insert(auditinglinkHashtable,1);
			auditingProcessLink.Delete(0,auditingProcessLinkModel.getLinkID());
			auditinglinkHashtable.clear();
		}

		IDataBase m_dbConsole	= GetDataBase.createDataBase();
		Statement stmUpdate1	= m_dbConsole.GetStatement();
		Statement stmUpdate2 	= m_dbConsole.GetStatement();
		try {
			String strRemedyTableName 	= m_RemedyDBOp.GetRemedyTableName(_baseSchema);
			String SqlUpdate1 = "update " + strRemedyTableName + " set " + 
									" C700000023 =  " + "1" +  "" + "," +
									" C700000024 = '" + Constants.TblAuditingLink_H + "'" + "," +
									" C700000025 = '" + Constants.TblAuditingProcess_H + "'" + "," +
									" C700000026 = '" + Constants.TblAuditingProcessLog_H + "'" + "," +
									" C700000027 = '" + Constants.TblDealLink_H + "'" + "," +
									" C700000028 = '" + Constants.TblDealProcessLog_H + "'" + "," +
									" C700000029 = '" + Constants.TblDealProcessLog_H + "'" +
								" where C1 = '" + _BaseId + "' and C700000001 = '" + _baseSchema + "'";
			//System.out.println(SqlUpdate);
			m_dbConsole.executeNonQuery(stmUpdate1,SqlUpdate1);	
			stmUpdate1.close();
			
			String strRemedyBaseInfoTableName 	= m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
			String SqlUpdate2 = "update " + strRemedyBaseInfoTableName + " set " + 
									" C700000023 =  " + "1" +  "" + "," +
									" C700000024 = '" + Constants.TblAuditingLink_H + "'" + "," +
									" C700000025 = '" + Constants.TblAuditingProcess_H + "'" + "," +
									" C700000026 = '" + Constants.TblAuditingProcessLog_H + "'" + "," +
									" C700000027 = '" + Constants.TblDealLink_H + "'" + "," +
									" C700000028 = '" + Constants.TblDealProcessLog_H + "'" + "," +
									" C700000029 = '" + Constants.TblDealProcessLog_H + "'" +
								" where C700000000 = '" + _BaseId + "' and C700000001 = '" + _baseSchema + "'";
			//System.out.println(SqlUpdate);
			m_dbConsole.executeNonQuery(stmUpdate2,SqlUpdate2);	
			stmUpdate2.close();
			
			System.out.println("修改的工单为：" + _baseSchema + "；工单ID为" + "：" + _BaseId);
			WriteLog(_baseSchema,_BaseId);
		} 
		catch (SQLException e) {
			ErrorFunction(e);
		}
		finally
		{
			try {
				if(stmUpdate1!=null)
					stmUpdate1.close();
				if(stmUpdate2!=null)
					stmUpdate2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			m_dbConsole.closeConn();
		}		
	}
	
	
	/**
	 * 初始化处理环节表字段
	 * @return
	 */
	private Hashtable DealProcessInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealProcess);
	}
	
	/**
	 * 初始化处理环节日志表字段
	 * @return
	 */
	private Hashtable DealProcessLogInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealProcessLog);
	}
	
	/**
	 * 初始化处理环节流转表字段
	 * @return
	 */
	private Hashtable DealLinkInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblDealLink);
	}
	
	/**
	 * 初始化审批环节表字段
	 * @return
	 */
	private Hashtable AuditingProcessInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingProcess);
	}
	
	/**
	 * 初始化审批环节日志表字段
	 * @return
	 */
	private Hashtable AuditingProcessLogInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingProcessLog);
	}
	
	/**
	 * 初始化审批环节流转表字段
	 * @return
	 */
	private Hashtable AuditingLinkInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblAuditingLink);
	}
	
	/**
	 * 初始化工单审批或处理的环节对字段修改的日志信息记录表字段
	 * @return
	 */
	private Hashtable BaseFieldModifyLogInit(){
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
        return m_RemedyDBOp.GetRemedyTableStorageField_ToHashtable(Constants.TblBaseFieldModifyLog);
	}

	public void operateArchive(Date _date){
		BaseCategoryManage 		m_BaseCategory 			= new BaseCategoryManage();
		ParBaseCategoryModel 	m_ParBaseCategoryModel	= new ParBaseCategoryModel();
		BaseCategoryModel 		m_BaseCategoryModel 	= null;
		List 					baseModels 				= null;
		BaseModel 				baseModel 				= null;
		List 					arr_BaseCategory 	= m_BaseCategory.getAllList();
		for (int Category_i = 0;Category_i < arr_BaseCategory.size();Category_i++)
		{
			m_BaseCategoryModel = (BaseCategoryModel)(arr_BaseCategory.get(Category_i));
			//String str_BaseCategoryName 	= m_BaseCategoryModel.GetBaseCategoryName();
			String str_BaseCategorySchama 	= m_BaseCategoryModel.GetBaseCategorySchama();
			
			baseModels 	= getBase(str_BaseCategorySchama,_date);
			baseModel 	= null;
			for(int i=0;i<baseModels.size();i++){
				baseModel = (BaseModel)baseModels.get(i);
				operateOneBaseArchive(str_BaseCategorySchama,baseModel.getBaseID());
				System.out.println("查询的工单为：" + str_BaseCategorySchama + "；工单ID为" + "：" +baseModel.getBaseID());
				baseModel  = null;
			}
			baseModels.clear();
		}			
	}
	
	public void ErrorFunction(Exception e) {
		String strError = e.toString();
		ErrorFunction(strError);
	}

	public void ErrorFunction(String strError) {
		String strTmp = "";
		Timestamp TmpDataTime = new Timestamp(System.currentTimeMillis());
		strTmp = strTmp + "工单表级分离线程：" + "；";	
		strTmp = strTmp + "发生错误的时间为：" + TmpDataTime.toString() + "\r\n";
		strTmp = strTmp + "　错误的描述为：" + strError + "\r\n";
		strTmp = strTmp + "------------------------------------\r\n";
		if (WriteToFile(strTmp, "BaseArchiveErr.log") == false) {
			strTmp = strTmp + "	***********-写文件错误-***************\r\n";
			strTmp = strTmp + "------------------------------------\r\n";
		}		
	}

	public void WriteLog(String strBaseName, String strBaseID) {
		Timestamp TmpDataTime = new Timestamp(System.currentTimeMillis());
		String strTmp = "";

		strTmp = strTmp + "工单表级分离线程：" + "；";	
		strTmp = strTmp + "操作的时间为：" + TmpDataTime.toString() + "\r\n";
		strTmp = strTmp + "　操作工单为：" + strBaseName + "\r\n";
		strTmp = strTmp + "　操作工单号为：" + strBaseID + "\r\n";
		strTmp = strTmp + "　操作为：" + "分离成功" + "\r\n";
		strTmp = strTmp + "------------------------------------\r\n";
		if (WriteToFile(strTmp, "BaseArchive.log") == false) {
			strTmp = strTmp + "	***********-写文件错误-***************\r\n";
			strTmp = strTmp + "------------------------------------\r\n";
		}

	}	
	public boolean WriteToFile(String strTmp, String strFile) {
		try {
			File TmpFile = new File(System.getProperty("user.dir") + File.separator 
					+ strFile);
			if (TmpFile.exists() == false) {
				TmpFile.createNewFile();
			}
			FileWriter TmpFileWriter = new FileWriter(TmpFile, true);
			TmpFileWriter.write(strTmp);
			TmpFileWriter.close();
		} catch (IOException e) {
			System.out.println("	写文件异常！");
			return false;
		}
		return true;

	}
	
	public static void main(String args[]){
		ConstantsManager m_ConstantsManager = new ConstantsManager(System.getProperty("user.dir")
				+ File.separator
				+ "WEB-INF");
		m_ConstantsManager.getConstantInstance();	
		DataArchive m_dataArchive = new DataArchive();
		Calendar calendar = GregorianCalendar.getInstance();
		Date date = (Date) calendar.getTime();		
		m_dataArchive.operateArchive(date);
	}
}
