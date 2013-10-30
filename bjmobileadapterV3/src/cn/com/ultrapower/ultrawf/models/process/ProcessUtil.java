package cn.com.ultrapower.ultrawf.models.process;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

/**
 * 返流程处理的表名
 * @author xufaqiu
 *
 */
public class ProcessUtil {
	
	/**
	 * 根据工单是否关闭而查询Process表名，如果工单已经关闭了则返回存储历史Process的表
	 * 名，否则返回当前处理的Process表名
	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getDealProcessTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getDealProcessTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应Process处理表名，如果已存入历史则返回Process历史存档表名，	 * 否则返回当前活动的Process表名。	 * @param p_IsArchived 是否已历史存档	 * @return
	 */
	public String getDealProcessTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的Process表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
		}
		else
		{
			//取活动的Process表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);			
		}
		return (strProcessName);
	}
	
	
	/**
	 * 根据工单是否归档而查询处理日志表名，如果工单已经关闭了则返回存储历史处理日志的表
	 * 名，否则返回当前处理的处理日志表名	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getDealProcessLogTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getDealProcessLogTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应处理日志处理表名，如果已存入历史则返回处理日志历史存档表名，	 * 否则返回当前活动的处理日志表名。	 * @param p_IsArchived 是否已历史存档	 * @return
	 */
	public String getDealProcessLogTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的DealProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
		}
		else
		{
			//取活动的DealProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);			
		}
		return (strProcessName);
	}	
	
	
	/**
	 * 根据工单是否归档而查询处理日志表名，如果工单已经关闭了则返回存储历史处理日志的表
	 * 名，否则返回当前处理的处理日志表名
	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getDealLinkTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getDealLinkTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应处理日志处理表名，如果已存入历史则返回处理日志历史存档表名，
	 * 否则返回当前活动的处理日志表名。
	 * @param p_IsArchived 是否已历史存档
	 * @return
	 */
	public String getDealLinkTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的DealProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink_H);
		}
		else
		{
			//取活动的DealProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink);			
		}
		return (strProcessName);
	}		
	
	
	/**
	 * 根据工单是否关闭而查询审批Process表名，如果工单已经关闭了则返回存储历史审批Process的表
	 * 名，否则返回当前审批的Process表名
	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getAuditingProcessTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getAuditingProcessTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应审批Process表名，如果已存入历史则返回审批Process历史存档表名，
	 * 否则返回当前活动的审批Process表名。
	 * @param p_IsArchived 是否已历史存档
	 * @return
	 */
	public String getAuditingProcessTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的AuditingProcess表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
		}
		else
		{
			//取活动的AuditingProcess表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);			
		}
		return (strProcessName);
	}
	
	
	/**
	 * 根据工单是否归档而查询处理日志表名，如果工单已经关闭了则返回存储历史审批日志的表
	 * 名，否则返回当前审批的处理日志表名
	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getAuditingProcessLogTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getAuditingProcessLogTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应处理日志处理表名，如果已存入历史则返回审批日志历史存档表名，
	 * 否则返回当前活动的审批日志表名。
	 * @param p_IsArchived 是否已历史存档
	 * @return
	 */
	public String getAuditingProcessLogTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的AuditingProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog_H);
		}
		else
		{
			//取活动的AuditingProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);			
		}
		return (strProcessName);
	}	
	
	
	/**
	 * 根据工单是否归档而查询处理日志表名，如果工单已经关闭了则返回存储历史审批日志的表
	 * 名，否则返回当前处理的审批日志表名
	 * @param p_BaseID
	 * @param p_BaseSchema
	 * @return
	 */
	public String getAuditingLinkTblName(String p_BaseSchema,String p_BaseID)
	{
		String strProcessName="";
		Base m_Base=new Base();
		BaseModel m_BaseModel=m_Base.GetOneForKey(p_BaseSchema,p_BaseID);
		if(m_BaseModel!=null)
		{
			strProcessName=getAuditingLinkTblName(m_BaseModel.getIsArchive());
		}
		return (strProcessName);
	}
	
	/**
	 * 根据工单是否已存入历史信取相应处理日志处理表名，如果已存入历史则返回审批日志历史存档表名，
	 * 否则返回当前活动的审批日志表名。
	 * @param p_IsArchived 是否已历史存档
	 * @return
	 */
	public String getAuditingLinkTblName(int p_IsArchived)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strProcessName="";
		//如果是从Process历史(存档)表中读取
		if(p_IsArchived==1)
		{
			//取归档的AuditingProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink_H);
		}
		else
		{
			//取活动的AuditingProcessLog表
			strProcessName=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink);			
		}
		return (strProcessName);
	}		
	
	/**
	 * 
	 * @param p_hashTable 新增字段hash表
	 * @param p_strFormName 插入表名
	 * @return
	 */
	public String Insert(Map p_hashTable,String p_strFormName)
	{
		List m_List=new ArrayList();
		String   key;
		PublicFieldInfo publicFieldInfo = null;
		
		for(Iterator it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			publicFieldInfo = (PublicFieldInfo)p_hashTable.get(key);

	        if(publicFieldInfo!=null){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(publicFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(publicFieldInfo.getStrFieldID());
	        	if (publicFieldInfo.getStrFieldValue() != null && publicFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }
	    }		
		String strReturnID=Insert(p_strFormName,m_List);
		return strReturnID;
	}
	
	/**
	 * 
	 * @param p_strFormName
	 * @param p_FieldInfoList
	 * @return
	 */
	public String Insert(String p_strFormName,List p_FieldInfoList)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strReturnID=RemedyOp.FormDataInsertReturnID(p_strFormName,p_FieldInfoList);
		RemedyOp.RemedyLogout();
		return strReturnID;
	}
	
	/**
	 * 
	 * @param strModifyEntryId 修改信息主键
	 * @param p_hashTable 修改字段信息hash表
	 * @param p_strFormName 修改信息表名
	 * @return
	 */
	public boolean Update(String strModifyEntryId,Map p_hashTable,String p_strFormName)
	{
		boolean blnRe=true;
		List m_List=new ArrayList();
		String   key;
        PublicFieldInfo publicFieldInfo = null;
		
		for(Iterator it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			publicFieldInfo = (PublicFieldInfo)p_hashTable.get(key);
	        if(publicFieldInfo!=null && publicFieldInfo.getStrFieldID()!="1"){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(publicFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(publicFieldInfo.getStrFieldID());
	          	if (publicFieldInfo.getStrFieldValue() != null && publicFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	  
	        }
	    }
	  if(m_List.size()>0)
		  blnRe=Update(p_strFormName,strModifyEntryId,m_List);
		return blnRe;
	}	
	
	/**
	 * 保存工单信息
	 * @param p_BaseSchema
	 * @param strModifyEntryId
	 * @param p_FieldInfoList
	 * @return
	 */
	public boolean Update(String p_strFormName,String strModifyEntryId,List p_FieldInfoList)
	{
		boolean blnRe=true;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		blnRe=RemedyOp.FormDataModify(p_strFormName,strModifyEntryId,p_FieldInfoList);
		RemedyOp.RemedyLogout();
		return blnRe;
	}	
	
	
	/**
	 * 删除工单信息
	 * @param p_strFormName
	 * @param strModifyEntryId
	 * @return
	 */
	public boolean Delete(String p_strFormName,String strDeleteEntryId)
	{
		boolean blnRe=true;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		blnRe=RemedyOp.FormDataDelete(p_strFormName,strDeleteEntryId);
		RemedyOp.RemedyLogout();
		return blnRe;
	}	
	
	public String getClobString(Clob p_clob)
	{
		String strValue="";
	
		if (p_clob!=null) 
		{
			try{
			if(p_clob.length()>0)
			{
				strValue=p_clob.getSubString((long)1,(int)p_clob.length());
			}
			}catch(Exception ex)
			{
				strValue="";
			}
		}
		return strValue;
	}
	
}
