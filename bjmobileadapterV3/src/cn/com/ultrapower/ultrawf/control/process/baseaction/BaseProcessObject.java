package cn.com.ultrapower.ultrawf.control.process.baseaction;

import java.io.File;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import cn.com.ultrapower.ultrawf.control.process.AuditingProcessManage;
import cn.com.ultrapower.ultrawf.control.process.DealProcessManage;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
import cn.com.ultrapower.ultrawf.control.process.bean.BaseAttachmentInfo;
import cn.com.ultrapower.ultrawf.models.process.AuditingProcessModel;
import cn.com.ultrapower.ultrawf.models.process.DealProcessModel;
import cn.com.ultrapower.ultrawf.share.Guid;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.FormatTime;

public class BaseProcessObject {
	//所有工单字段
	protected Hashtable<String, BaseFieldInfo> Hashtable_BaseAllFields = null;
	
	/**
	 * 描述：操作工单的结束
	 * @return 是否成功
	 */
	protected void baseClose() {
		this.Hashtable_BaseAllFields.clear();
		this.Hashtable_BaseAllFields = null;
	}

	public void printBaseAllFields(Hashtable<String, BaseFieldInfo> Hashtable_BaseAllFields)
	{
		String   key;	
		for(Iterator<String> it=Hashtable_BaseAllFields.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)   it.next();
	        BaseFieldInfo m_BaseFieldInfo = (BaseFieldInfo)Hashtable_BaseAllFields.get(key);
	        System.out.println(key + " : "+m_BaseFieldInfo.getStrFieldID() + " 	= " + m_BaseFieldInfo.getStrFieldValue());
		}
	}
	
	protected String getGUID(String p_Guid_Top,long p_intSeed) {
		Thread Thread_currentThread = Thread.currentThread();
		Guid m_obj_Guid = new Guid(p_intSeed);
		Integer m_Intege_random 		= new Integer(m_obj_Guid.random(100000));
		Long 	m_Intege_currentTime 	= new Long(System.currentTimeMillis());
		String m_str_Guid	= p_Guid_Top + "-" + m_Intege_currentTime.toString() + "-" + Thread_currentThread.getName() + "-" + m_Intege_random.toString();
		return m_str_Guid;
	}
	
	protected String getFieldValue(String p_FieldName) {
		try
		{
			return ((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName)).getStrFieldValue();
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("设置工单<"+p_FieldName+">字段错误",ex);	
			return null;
		}		
	}
	
	protected void setFieldValue(BaseFieldInfo p_FieldInfo) {
		if (Hashtable_BaseAllFields.containsKey(p_FieldInfo.getStrFieldName()))
		{
			((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldInfo.getStrFieldName())).setStrFieldValue(p_FieldInfo.getStrFieldValue());
		}
		else
		{
      		Hashtable_BaseAllFields.put(p_FieldInfo.getStrFieldName(), p_FieldInfo);
		}
	}
	
	protected void setFieldValue(String p_FieldName,String p_FieldValue) {
		try
		{
			((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName)).setStrFieldValue(p_FieldValue);
		}
		catch(Exception ex)
		{ 
			BaseWrite_Log.writeExceptionLog("设置工单<"+p_FieldName+">字段错误",ex);		
		}
	}
	
	protected void setFieldValueFromOtherField(String p_FieldName_to,String p_FieldName_from) {
		try
		{
			((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName_to)).setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName_from)).getStrFieldValue());
		}
		catch(Exception ex)
		{ 
			BaseWrite_Log.writeExceptionLog("设置工单<"+p_FieldName_to+">字段错误",ex);		
		}
	}

	protected void setFieldValueFromOtherField(BaseFieldInfo p_FieldInfo_to,String p_FieldName_from) {
		if (Hashtable_BaseAllFields.containsKey(p_FieldInfo_to.getStrFieldName()))
		{
			((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldInfo_to.getStrFieldName())).setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName_from)).getStrFieldValue());
		}
		else
		{
      		Hashtable_BaseAllFields.put(p_FieldInfo_to.getStrFieldName(), p_FieldInfo_to);
			((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldInfo_to.getStrFieldName())).setStrFieldValue(((BaseFieldInfo)Hashtable_BaseAllFields.get(p_FieldName_from)).getStrFieldValue());
		}
	}	
	
	/**
	 * 描述：操作工单的PUTH
	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_strUserLoginName	操作工单动作的用户登陆名
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 返回工单ID
	 */
	protected String basePush(String p_BaseSchema,String p_BaseID,String p_strUserLoginName,int p_Operation) {
		try
		{
			Hashtable_BaseAllFields.remove("BaseID");
			this.baseUpdataC2(p_strUserLoginName, p_BaseSchema, p_BaseID, p_Operation);
			
			List<RemedyFieldInfo> m_List	= new ArrayList<RemedyFieldInfo>();
			BaseFieldInfo baseFieldInfo = null;
			for(Iterator<String> it=Hashtable_BaseAllFields.keySet().iterator();it.hasNext();)   
			{   
				String   key   =   (String)it.next();		
				baseFieldInfo = (BaseFieldInfo)Hashtable_BaseAllFields.get(key);

		        if(baseFieldInfo!=null){
		        	RemedyFieldInfo m_RemedyFieldInfo =new RemedyFieldInfo();
		        	m_RemedyFieldInfo.setIntFieldType(baseFieldInfo.getIntFieldType());
		          	m_RemedyFieldInfo.setStrFieldID(baseFieldInfo.getStrFieldID());
		        	if (baseFieldInfo.getStrFieldValue() != null && baseFieldInfo.getStrFieldValue().equals("null")==false)
		        	{
			          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	  
		        	}
		        	else
		        	{
		        		m_RemedyFieldInfo.setStrFieldValue(null);	  
		        	}
		          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	      	  
		          	m_List.add(m_RemedyFieldInfo);	        	
		        }
		    }		
			if (p_Operation == 0)
			{	
				return remedyEntryInsert(p_BaseSchema,m_List);
			}
			else
			{
				if (remedyEntryUpdate(p_BaseSchema,p_BaseID,m_List)==true)
				{
					return p_BaseID;
				}
			}
		}
		catch(Exception ex)
		{ 
			BaseWrite_Log.writeExceptionLog("PUTH工单",ex);		
			return null;
		}
		return null;
	}
	
	public String remedyEntryInsert(String p_strFormName,Hashtable<String,PublicFieldInfo> p_hashTable)
	{
		String   key;
		List<RemedyFieldInfo> m_List	= new ArrayList<RemedyFieldInfo>();
		PublicFieldInfo baseFieldInfo = null;
		
		for(Iterator<String> it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();		
			baseFieldInfo = (PublicFieldInfo)p_hashTable.get(key);

	        if(baseFieldInfo!=null){
	        	RemedyFieldInfo m_RemedyFieldInfo =new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(baseFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(baseFieldInfo.getStrFieldID());
	        	if (baseFieldInfo.getStrFieldValue() != null && baseFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }
	    }
		String strReturnID=remedyEntryInsert(p_strFormName,m_List);
		return strReturnID;
	}

	
	public String remedyEntryInsert(String p_strFormName,List<RemedyFieldInfo> p_FieldInfoList)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strReturnID=RemedyOp.FormDataInsertReturnID(p_strFormName,p_FieldInfoList);
		RemedyOp.RemedyLogout();
		return strReturnID;
	}

	public boolean remedyEntryUpdate(String p_BaseSchema,String strModifyEntryId,Hashtable<String,PublicFieldInfo> p_hashTable)
	{
		boolean blnRe=true;
		String   key;
		List<RemedyFieldInfo> m_List	= new ArrayList<RemedyFieldInfo>();
		PublicFieldInfo baseFieldInfo = null;
		
		for(Iterator<String> it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			baseFieldInfo = (PublicFieldInfo)p_hashTable.get(key);
	        if(baseFieldInfo!=null  && baseFieldInfo.getStrFieldID()!="1"){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(baseFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(baseFieldInfo.getStrFieldID());
	          	if (baseFieldInfo.getStrFieldValue() != null && baseFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(baseFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }
	    }
	  if(m_List.size()>0)
		  blnRe=remedyEntryUpdate(p_BaseSchema,strModifyEntryId,m_List);
		return blnRe;
	}	
	
	/**
	 * 保存信息
	 * @param p_BaseSchema
	 * @param strModifyEntryId
	 * @param p_FieldInfoList
	 * @return
	 */
	public boolean remedyEntryUpdate(String p_BaseSchema,String strModifyEntryId,List<RemedyFieldInfo> p_FieldInfoList)
	{
		boolean blnRe=true;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		blnRe=RemedyOp.FormDataModify(p_BaseSchema,strModifyEntryId,p_FieldInfoList);

		RemedyOp.RemedyLogout();
		return blnRe;
	}
	
	protected boolean baseAttachmentPush(List<BaseAttachmentInfo> p_BaseAttachmentList){

		try{
			int listCount=0;
			if(p_BaseAttachmentList!=null)
				listCount=p_BaseAttachmentList.size();
			for (int i=0;i<listCount;i++)
			{
				String str_Attachment_Path = ((BaseAttachmentInfo)p_BaseAttachmentList.get(i)).getStrAttachmentPath();
				
				Hashtable<String,PublicFieldInfo> Hashtable_AddBaseAttachAllFields = new Hashtable<String,PublicFieldInfo>();
				
				int intCharIndexOf = str_Attachment_Path.lastIndexOf(File.separator);
				if(intCharIndexOf>0)
					intCharIndexOf++;
				
				String str_Attachment_Name = str_Attachment_Path.substring(intCharIndexOf);
				
				Hashtable_AddBaseAttachAllFields.put("BaseID", new PublicFieldInfo("BaseID","650000001",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseID")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("BaseSchema", new PublicFieldInfo("BaseSchema","650000002",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_BaseSchema")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("ProcessID", new PublicFieldInfo("ProcessID","650000011",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessID")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("ProcessType", new PublicFieldInfo("ProcessType","650000012",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessType")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("ProcessLogID", new PublicFieldInfo("ProcessLogID","650000013",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_Pro_ProcessLogID")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("FlagActive", new PublicFieldInfo("FlagActive","650000004","1",2));
				Hashtable_AddBaseAttachAllFields.put("upLoadUser", new PublicFieldInfo("upLoadUser","650000005",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserFullName")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("upLoadUserID", new PublicFieldInfo("upLoadUserID","650000006",((BaseFieldInfo)Hashtable_BaseAllFields.get("tmp_UserLoginName")).getStrFieldValue(),4));
				Hashtable_AddBaseAttachAllFields.put("upLoadTimeDate", new PublicFieldInfo("upLoadTimeDate","650000007",(new Long(FormatTime.FormatDateToInt(FormatTime.getCurrentDate())).toString()),7));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileName", new PublicFieldInfo("upLoadFileName","650000008",str_Attachment_Name,4));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileDesc", new PublicFieldInfo("upLoadFileDesc","650000009","接口来的！",4));
				Hashtable_AddBaseAttachAllFields.put("upLoadFileContent", new PublicFieldInfo("upLoadFileContent","650000010",str_Attachment_Path,11));
				Hashtable_AddBaseAttachAllFields.put("ViewAttAchID", new PublicFieldInfo("ViewAttAchID","650000014","",4));

				this.remedyEntryInsert(Constants.TblBaseAttachment, Hashtable_AddBaseAttachAllFields);
	
				Hashtable_AddBaseAttachAllFields.clear();
				Hashtable_AddBaseAttachAllFields = null;
			}
			return true;
		}
		catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("加工单附件",ex);			
			return false;
		}
	}
	
	/**
	 * 描述：修改与操作有关的信息表的C2字段C2字段
	 * @param p_strUserLoginName	登陆名 
	 * @param p_BaseSchema			工单类型
	 * @param p_BaseID				工单ID
	 * @param p_Operation			操作类型：0：为新建；1：为修改
	 * @return 是否成功
	 */
	public boolean baseUpdataC2(String p_strUserLoginName,String p_BaseSchema, String p_BaseID,int p_Operation) {
		//需要修改的Form有: 
		boolean blnRe=true;
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblName=m_RemedyDBOp.GetRemedyTableName(p_BaseSchema);
		String strSql;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		try{
			stm=m_dbConsole.GetStatement();
			if(p_Operation==0)
			{
				//更新DealProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseCategory);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				//650000002	BaseCategorySchama
				strSql+=" where C650000002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);
				m_dbConsole.closeConn();
			}
			else
			{
				//更新工单C2信息
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'"+" where C1='"+p_BaseID+"'";
				m_dbConsole.executeNonQuery(stm,strSql);
				
				//更新DealProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020001='"+p_BaseID+"' and C700020002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新DealLink的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020501='"+p_BaseID+"' and C700020502='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新AuditingProcess的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020001='"+p_BaseID+"' and C700020002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);	
				
				//更新AuditingLink的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020501='"+p_BaseID+"' and C700020502='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);			
				
				//更新BaseAttachment（附件表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseAttachment);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C650000001='"+p_BaseID+"' and C650000002='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);			
				
				//更新BaseInfor（工单信息表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppBaseInfor);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700000000='"+p_BaseID+"' and C700000001='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新DealVerdict（工单判断表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppDealVerdict);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020601='"+p_BaseID+"' and C700020602='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
				//更新DealAssistantProcess（工单辅助环节表）的C2字段信息
				tblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAppDealAssistantProcess);
				strSql=" update "+tblName+" set C2='"+Constants.REMEDY_DEMONAME+"'";
				strSql+=" where C700020801='"+p_BaseID+"' and C700020802='"+p_BaseSchema+"'";
				m_dbConsole.executeNonQuery(stm,strSql);		
				
			}
		}catch(Exception ex)
		{
			BaseWrite_Log.writeExceptionLog("修改工单关联C失败",ex);			
		}
		finally
		{
			try{
				if(stm!=null)
					stm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return blnRe;
	}
	
	public String selectOneProcess(RDParameter p_ProRDParameter,String p_ProcessType) {
		p_ProRDParameter.addIndirectPar("baseschema",getFieldValue("BaseSchema"),4);
		p_ProRDParameter.addIndirectPar("basesid",getFieldValue("BaseID"),4);		
		if (p_ProcessType.equals("AUDITING"))
		{
			AuditingProcessModel 		m_ProcessModel		= (new AuditingProcessManage()).getOneModel(p_ProRDParameter);
			if (m_ProcessModel != null)
			{
				return m_ProcessModel.getProcessID();
			}
		}
		else
		{
			DealProcessModel 		m_ProcessModel			= (new DealProcessManage()).getOneModel(p_ProRDParameter);
			if (m_ProcessModel != null)
			{
				return m_ProcessModel.getProcessID();
			}
		}
		return null;
	}

	public String selectWaitProcess() {
		String m_baseschema 						= getFieldValue("BaseSchema");
		String m_baseid 							= getFieldValue("BaseID");
		String m_tmp_UserLoginName					= getFieldValue("tmp_UserLoginName");
		String m_tmp_UserCloseBaseSamenessGroupID	= getFieldValue("tmp_UserCloseBaseSamenessGroupID");
		
		String m_sql = "SELECT SelectWaitingProcess('"+m_baseid+"','"+m_baseschema+"','"+m_tmp_UserLoginName+"','"+m_tmp_UserCloseBaseSamenessGroupID+"') as ProcessString,NEXTSELECTTEMP FROM BASENEXTSELECTTEMP";
      	Table 	m_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	m_rowSet		= m_table.executeQuery(m_sql,null,0,0,0);
      	String m_ProcessString	= "";
      	if (m_rowSet.length()>0)
      	{
      		Row 	m_Rs			= null;
	  		m_Rs = m_rowSet.get(0);
	  		m_ProcessString 	= FormatString.CheckNullString(m_Rs.getString("ProcessString"));
      	}      	
		return m_ProcessString;
	}
	
	public List<Object> selectMoreProcess(RDParameter p_ProRDParameter,String p_ProcessType) {
		p_ProRDParameter.addIndirectPar("baseschema",getFieldValue("BaseSchema"),4);
		p_ProRDParameter.addIndirectPar("basesid",getFieldValue("BaseID"),4);	
		List<Object> 		m_ProcessModelList = new ArrayList<Object>();
		if (p_ProcessType.equals("AUDITING"))
		{
			List<AuditingProcessModel> m_ProcessModelList_Auditing = (new AuditingProcessManage()).getList(p_ProRDParameter);
			m_ProcessModelList.addAll(m_ProcessModelList_Auditing);
		}
		else
		{
			List<DealProcessModel> m_ProcessModelList_Deal 	= (new DealProcessManage()).getList(p_ProRDParameter);
			m_ProcessModelList.addAll(m_ProcessModelList_Deal);
		}
		return m_ProcessModelList;
	}

}
