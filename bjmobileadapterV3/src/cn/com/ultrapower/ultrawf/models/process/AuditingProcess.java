package cn.com.ultrapower.ultrawf.models.process;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
public class AuditingProcess {
	
	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	public AuditingProcess(){}
	/**
	 * 根据关键字查询并返回一条审批处理记录

	 * @param p_strProcessID
	 * @return
	 */
	public AuditingProcessModel GetOneForKey(String p_strProcessID,int p_IsArchive)
	{
		AuditingProcessModel m_AuditingProcessModel=new AuditingProcessModel();
		
		
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		m_ParDealProcess.setProcessID(p_strProcessID);
		m_ParDealProcess.setIsArchive(p_IsArchive);
		
		String strSql=this.getSelectSql(m_ParDealProcess);
		Statement stm=null;
		ResultSet p_DealRs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			p_DealRs = m_dbConsole.executeResultSet(stm, strSql);
			if (p_DealRs!=null && p_DealRs.next())
			{
				 m_AuditingProcessModel.setProcessID(p_DealRs.getString("ProcessID"));
				 m_AuditingProcessModel.setProcessBaseID(p_DealRs.getString("ProcessBaseID"));
				 m_AuditingProcessModel.setProcessBaseSchema(p_DealRs.getString("ProcessBaseSchema"));
				 m_AuditingProcessModel.setPhaseNo(p_DealRs.getString("PhaseNo"));
				 m_AuditingProcessModel.setPrevPhaseNo(p_DealRs.getString("PrevPhaseNo"));
				 m_AuditingProcessModel.setCreateByUserID(p_DealRs.getString("CreateByUserID"));
				 m_AuditingProcessModel.setAssginee(p_DealRs.getString("Assginee"));
				 m_AuditingProcessModel.setAssgineeID(p_DealRs.getString("AssgineeID"));
				 m_AuditingProcessModel.setGroup(p_DealRs.getString("GroupName"));
				 m_AuditingProcessModel.setGroupID(p_DealRs.getString("GroupID"));
				 m_AuditingProcessModel.setDealer(p_DealRs.getString("Dealer"));
				 m_AuditingProcessModel.setDealerID(p_DealRs.getString("DealerID"));
				 m_AuditingProcessModel.setProcessStatus(p_DealRs.getString("ProcessStatus"));
				 m_AuditingProcessModel.setAuditingOverTimeDate(p_DealRs.getLong("AuditingOverTimeDate"));
				 m_AuditingProcessModel.setStDate(p_DealRs.getLong("StDate"));
				 m_AuditingProcessModel.setBgDate(p_DealRs.getLong("BgDate"));
				 m_AuditingProcessModel.setEdDate(p_DealRs.getLong("EdDate"));
				 m_AuditingProcessModel.setDesc(p_DealRs.getString("DescInfo"));
				 m_AuditingProcessModel.setFlagType(p_DealRs.getString("FlagType"));
				 m_AuditingProcessModel.setFlagActive(p_DealRs.getInt("FlagActive"));
				 m_AuditingProcessModel.setFlagPredefined(p_DealRs.getInt("FlagPredefined"));
				 m_AuditingProcessModel.setFlagDuplicated(p_DealRs.getInt("FlagDuplicated"));
				 m_AuditingProcessModel.setFlag01Assign(p_DealRs.getString("Flag01Assign"));
				 m_AuditingProcessModel.setFlag02Copy(p_DealRs.getString("Flag02Copy"));
				 m_AuditingProcessModel.setFlag03Assist(p_DealRs.getString("Flag03Assist"));
				 m_AuditingProcessModel.setFlag04Transfer(p_DealRs.getString("Flag04Transfer"));
				 m_AuditingProcessModel.setFlag08Cancel(p_DealRs.getString("Flag08Cancel"));
				 m_AuditingProcessModel.setFlag15ToAuditing(p_DealRs.getString("Flag15ToAuditing"));
				 m_AuditingProcessModel.setFlag16TurnUpAuditing(p_DealRs.getString("Flag16TurnUpAuditing"));
				 m_AuditingProcessModel.setFlag17RecallAuditing(p_DealRs.getString("Flag17RecallAuditing"));
				 m_AuditingProcessModel.setFlag20SideBySide(p_DealRs.getString("Flag20SideBySide"));
				 m_AuditingProcessModel.setFlag22IsSelect(p_DealRs.getString("Flag22IsSelect"));
				 m_AuditingProcessModel.setAuditingWayPhaseNo(p_DealRs.getString("AuditingWayPhaseNo"));
				 m_AuditingProcessModel.setAuditingWayIsActive(p_DealRs.getString("AuditingWayIsActive"));
				 m_AuditingProcessModel.setAuditingWayNo(p_DealRs.getString("AuditingWayNo"));
				 m_AuditingProcessModel.setProcessType(p_DealRs.getString("ProcessType"));
				 
				 m_AuditingProcessModel.setCommissioner(p_DealRs.getString("Commissioner"));
				 m_AuditingProcessModel.setCommissionerID(p_DealRs.getString("CommissionerID"));
				 m_AuditingProcessModel.setCloseBaseSamenessGroup(p_DealRs.getString("CloseBaseSamenessGroup"));
				 m_AuditingProcessModel.setCloseBaseSamenessGroupID(p_DealRs.getString("CloseBaseSamenessGroupID"));
				 m_AuditingProcessModel.setIsGroupSnatch(p_DealRs.getString("IsGroupSnatch"));
				 //m_DealProcessModel.setFlag32IsToTransfer(p_DealRs.getString("Flag32IsToTransfer"));
				 m_AuditingProcessModel.setFlag33IsEndPhase(p_DealRs.getString("Flag33IsEndPhase"));				 
				
			}
		}catch(Exception ex)
		{
			System.err.println("AuditingProcess.GetOneForKey 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		finally
		{
			try{
				if(p_DealRs!=null)
					p_DealRs.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
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
		return m_AuditingProcessModel;
	}
	
	/**
	 * 生成查询的Sql语句
	 * @param p_ParBaseModel
	 * @param p_ParDealProcess
	 * @return
	 */
	private String getSelectSql(ParDealProcess p_ParDealProcess)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParDealProcess==null)
			p_ParDealProcess=new ParDealProcess();
		
		if(p_ParDealProcess.getIsArchive()==0||p_ParDealProcess.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_ParDealProcess));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_ParDealProcess.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_ParDealProcess));
			sqlString.append(" union all ");
			//生成当前历史信息的sql
			p_ParDealProcess.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_ParDealProcess));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return sqlString.toString();
		
	}	
	/**
	 * 审批环节的查询语句
	 * @return
	 */
	private  String getSelectSqlExt(ParDealProcess p_ParDealProcess)
	{
		StringBuffer strReSql = new StringBuffer();
		String strTblname="";
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		
		if(p_ParDealProcess.getIsArchive()==1)
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
		else
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
		strReSql.append("select ");
		strReSql.append("C1 as ProcessID,C700020001 as ProcessBaseID,C700020002 as ProcessBaseSchema");
		strReSql.append(",C700020003 as PhaseNo,C700020004 as PrevPhaseNo,C700020045 as CreateByUserID");
		strReSql.append(",C700020005 as Assginee,C700020006 as AssgineeID,C700020007 as GroupName");
		strReSql.append(",C700020008 as GroupID,C700020009 as Dealer,C700020010 as DealerID,C700020011 as ProcessStatus");
		strReSql.append(",C700020037 as AuditingOverTimeDate,C700020015 as StDate,C700020016 as BgDate,C700020017 as EdDate");
		strReSql.append(",C700020018 as DescInfo,C700020019 as FlagType,C700020020 as FlagActive,C700020021 as FlagPredefined");
		strReSql.append(",C700020022 as FlagDuplicated,C700020023 as Flag01Assign,C700020024 as Flag02Copy,C700020025 as Flag03Assist");
		strReSql.append(",C700020026 as Flag04Transfer,C700020030 as Flag08Cancel,C700020032 as Flag15ToAuditing");
		strReSql.append(",C700020038 as Flag16TurnUpAuditing,C700020039 as Flag17RecallAuditing,C700020033 as Flag20SideBySide");
		strReSql.append(",C700020046 as Flag22IsSelect,C700020040 as AuditingWayPhaseNo,C700020041 as AuditingWayIsActive");
		strReSql.append(",C700020042 as AuditingWayNo,C700020043 as ProcessType ");
		
		strReSql.append(" ,C700020047 as Commissioner,C700020048 as CommissionerID");
		strReSql.append(" ,C700020050 as CloseBaseSamenessGroup,C700020051 as CloseBaseSamenessGroupID");
		strReSql.append(" ,C700020049 as IsGroupSnatch");
		strReSql.append(" ,C700020053 as Flag33IsEndPhase ");
		
		
		strReSql.append(" from "+strTblname+" AuditingProcess" );	
		strReSql.append(" where 1=1 ");
		strReSql.append(p_ParDealProcess.GetWhereSql(""));
		return(strReSql.toString());
		
	}
	
	/**
	 * 根据记录集返回Base列表
	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private List ConvertRsToList(ResultSet p_DealRs)  throws Exception
	{
		if(p_DealRs==null ) return null;
		List list = new ArrayList();
		try{
			while (p_DealRs.next())
			{
				AuditingProcessModel m_AuditingProcessModel=new AuditingProcessModel();
				 m_AuditingProcessModel.setProcessID(p_DealRs.getString("ProcessID"));
				 m_AuditingProcessModel.setProcessBaseID(p_DealRs.getString("ProcessBaseID"));
				 m_AuditingProcessModel.setProcessBaseSchema(p_DealRs.getString("ProcessBaseSchema"));
				 m_AuditingProcessModel.setPhaseNo(p_DealRs.getString("PhaseNo"));
				 m_AuditingProcessModel.setPrevPhaseNo(p_DealRs.getString("PrevPhaseNo"));
				 m_AuditingProcessModel.setCreateByUserID(p_DealRs.getString("CreateByUserID"));
				 m_AuditingProcessModel.setAssginee(p_DealRs.getString("Assginee"));
				 m_AuditingProcessModel.setAssgineeID(p_DealRs.getString("AssgineeID"));
				 m_AuditingProcessModel.setGroup(p_DealRs.getString("GroupName"));
				 m_AuditingProcessModel.setGroupID(p_DealRs.getString("GroupID"));
				 m_AuditingProcessModel.setDealer(p_DealRs.getString("Dealer"));
				 m_AuditingProcessModel.setDealerID(p_DealRs.getString("DealerID"));
				 m_AuditingProcessModel.setProcessStatus(p_DealRs.getString("ProcessStatus"));
				 m_AuditingProcessModel.setAuditingOverTimeDate(p_DealRs.getLong("AuditingOverTimeDate"));
				 m_AuditingProcessModel.setStDate(p_DealRs.getLong("StDate"));
				 m_AuditingProcessModel.setBgDate(p_DealRs.getLong("BgDate"));
				 m_AuditingProcessModel.setEdDate(p_DealRs.getLong("EdDate"));
				 m_AuditingProcessModel.setDesc(p_DealRs.getString("DescInfo"));
				 m_AuditingProcessModel.setFlagType(p_DealRs.getString("FlagType"));
				 m_AuditingProcessModel.setFlagActive(p_DealRs.getInt("FlagActive"));
				 m_AuditingProcessModel.setFlagPredefined(p_DealRs.getInt("FlagPredefined"));
				 m_AuditingProcessModel.setFlagDuplicated(p_DealRs.getInt("FlagDuplicated"));
				 m_AuditingProcessModel.setFlag01Assign(p_DealRs.getString("Flag01Assign"));
				 m_AuditingProcessModel.setFlag02Copy(p_DealRs.getString("Flag02Copy"));
				 m_AuditingProcessModel.setFlag03Assist(p_DealRs.getString("Flag03Assist"));
				 m_AuditingProcessModel.setFlag04Transfer(p_DealRs.getString("Flag04Transfer"));
				 m_AuditingProcessModel.setFlag08Cancel(p_DealRs.getString("Flag08Cancel"));
				 m_AuditingProcessModel.setFlag15ToAuditing(p_DealRs.getString("Flag15ToAuditing"));
				 m_AuditingProcessModel.setFlag16TurnUpAuditing(p_DealRs.getString("Flag16TurnUpAuditing"));
				 m_AuditingProcessModel.setFlag17RecallAuditing(p_DealRs.getString("Flag17RecallAuditing"));
				 m_AuditingProcessModel.setFlag20SideBySide(p_DealRs.getString("Flag20SideBySide"));
				 m_AuditingProcessModel.setFlag22IsSelect(p_DealRs.getString("Flag22IsSelect"));
				 m_AuditingProcessModel.setAuditingWayPhaseNo(p_DealRs.getString("AuditingWayPhaseNo"));
				 m_AuditingProcessModel.setAuditingWayIsActive(p_DealRs.getString("AuditingWayIsActive"));
				 m_AuditingProcessModel.setAuditingWayNo(p_DealRs.getString("AuditingWayNo"));
				 m_AuditingProcessModel.setProcessType(p_DealRs.getString("ProcessType"));

				 m_AuditingProcessModel.setCommissioner(p_DealRs.getString("Commissioner"));
				 m_AuditingProcessModel.setCommissionerID(p_DealRs.getString("CommissionerID"));
				 m_AuditingProcessModel.setCloseBaseSamenessGroup(p_DealRs.getString("CloseBaseSamenessGroup"));
				 m_AuditingProcessModel.setCloseBaseSamenessGroupID(p_DealRs.getString("CloseBaseSamenessGroupID"));
				 m_AuditingProcessModel.setIsGroupSnatch(p_DealRs.getString("IsGroupSnatch"));
				 //m_DealProcessModel.setFlag32IsToTransfer(p_DealRs.getString("Flag32IsToTransfer"));
				 m_AuditingProcessModel.setFlag33IsEndPhase(p_DealRs.getString("Flag33IsEndPhase"));				 
				 
				 
				 list.add(m_AuditingProcessModel);
			}
			p_DealRs.close();
		}catch(Exception ex)
		{
			System.err.println("AuditingProcess.ConvertRsToList 方法："+ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		return list;
	}		

	/**
	 * 描述：静态的得到某类工单审批环节列表方法，返回一个List（AuditingProcess对象的数组）。
	 * @param str_ProcessBaseID
	 * @param str_ProcessBaseSchema
	 * @return
	 * @throws Exception
	 */
	public  List GetList(String str_ProcessBaseID , String str_ProcessBaseSchema,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseID(str_ProcessBaseID);
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		//m_ParDealProcess.setPhaseNo(str_PhaseNo);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到某类工单某个审批环节所有记录列表方法，返回一个List（AuditingProcess对象的数组）
	 * @param str_ProcessBaseID
	 * @param str_ProcessBaseSchema
	 * @param str_PhaseNo
	 * @return
	 * @throws Exception
	 */
	public  List GetList(String str_ProcessBaseID , String str_ProcessBaseSchema , String str_PhaseNo,int p_PageNumber,int p_StepRow)throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseID(str_ProcessBaseID);
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setPhaseNo(str_PhaseNo);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;	
	}
	/**
	 * 描述：静态的得到所有工单所有待审批环节列表方法，返回一个List（AuditingProcess对象的数组）。
	 * @param str_UserLoginName
	 * @return
	 * @throws Exception
	 */
	public List GetListWaitAuditing(String str_UserLoginName,int p_PageNumber,int p_StepRow)
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到某类工单所有待办环节列表方法，返回一个List（AuditingProcess对象的数组）。
	 * @param str_ProcessBaseSchema
	 * @param str_UserLoginName
	 * @return
	 * @throws Exception
	 */
	public List GetListWaitAuditing(String str_ProcessBaseSchema,
			String str_UserLoginName, int p_PageNumber, int p_StepRow)
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitAuditing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	
	/**
	 * 根据条件查询审批信息，并返回审批信息(AuditingProcessModel)实例对象
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//StringBuffer sqlString = new StringBuffer();
		String strSql;
		strSql=this.getSelectSql(p_ParDealProcess);
		//System.err.println("AuditingProcess.GetList 方法sql: "+strSql);
		GetQueryResultRows(strSql);
		Statement stm=null;
		ResultSet m_ObjRs=null;
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		try{
			stm=m_dbConsole.GetStatement();
			m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_ObjRs);
			m_ObjRs.close();
			stm.close();
		}catch(Exception ex)
		{
			System.err.println("AuditingProcess.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		finally
		{
			
			try{
				if(m_ObjRs!=null)
					m_ObjRs.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
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
		return m_Relist;
	}
	
	/**
	 * 返回查询的记录总数量(用于计算分页时的页数)
	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		sqlString.append(" )");
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_BaseRs=null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
			{
				System.err.println("AuditingProcess.GetQueryResultRows 方法："+ex.getMessage());
				ex.printStackTrace();
			}
		finally
		{
			try{
				if(m_BaseRs!=null)
					m_BaseRs.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
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
			
	}
	

	/**
	 * 
	 * @param p_hashTable
	 * @param IsArchive
	 * @return
	 */
	public String Insert(Hashtable p_hashTable,int IsArchive)
	{  
		ProcessUtil processUtil = new ProcessUtil();
	    String strReturnID = null;
	    String p_strFormName = "";
	    if(IsArchive==0){
	    	p_strFormName = Constants.TblAuditingProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcess_H;	  	    	
	    }
	    strReturnID = processUtil.Insert(p_hashTable,p_strFormName);
		return strReturnID;
	}
	
	
	/**
	 * 
	 * @param strModifyEntryId
	 * @param p_hashTable
	 * @param IsArchive
	 * @return
	 */
	public boolean Update(String strModifyEntryId,Hashtable p_hashTable,int IsArchive)
	{  
		ProcessUtil processUtil = new ProcessUtil();
	    boolean reault = false;
	    String p_strFormName = "";
	    if(IsArchive==0){
	    	p_strFormName = Constants.TblAuditingProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcess_H;	  	    	
	    }
	    reault = processUtil.Update(strModifyEntryId,p_hashTable,p_strFormName);
		return reault;
	}
	
/**
 * 删除
 * @param p_strFormName
 * @param strDeleteEntryId
 * @return
 */
	public boolean Delete(int IsArchive,String strDeleteEntryId)
	{  
		ProcessUtil processUtil = new ProcessUtil();
	    boolean reault = false;
	    String p_strFormName = "";
	    if(IsArchive==0){
	    	p_strFormName = Constants.TblAuditingProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcess_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
	
	public boolean delete(int isArchive,ParDealProcess p_ParDealProcess)
	{
		List m_list=this.GetList(p_ParDealProcess,0,0);
		int rowCount=0;
		if(m_list!=null)
			rowCount=m_list.size();
		if(rowCount<=0)
			return false;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strFormName;
	    if(isArchive==0){
	    	strFormName = Constants.TblAuditingProcess;	    	
	    }else
	    {
	    	strFormName = Constants.TblAuditingProcess_H;	  	    	
	    }		
		try{
			

			AuditingProcessModel m_AuditingProcessModel;
			for(int row=0;row<rowCount;row++)
			{
				m_AuditingProcessModel=(AuditingProcessModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_AuditingProcessModel.getProcessID());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		finally{
			
			RemedyOp.RemedyLogout();
		}
		return true;
	}	
	
}
