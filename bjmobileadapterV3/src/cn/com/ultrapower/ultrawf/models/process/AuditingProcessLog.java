package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class AuditingProcessLog {
	
	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	public AuditingProcessLog(){}
	
	/**
	 * 根据关键字ProcessLogID查询审批环节日志信息，并返回一个审批环节日志信息(AuditingProcessLogModel)对象
	 * @param str_LinkID
	 */
	public AuditingProcessLogModel GetOneForKey(String str_ProcessLogID,int p_IsArchive)
	{
		if(str_ProcessLogID==null ||str_ProcessLogID.trim().equals(""))
			return null;
		AuditingProcessLogModel m_AuditingProcessLogModel=new AuditingProcessLogModel();
		StringBuffer sqlString = new StringBuffer();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		ParAuditingProcessLogModel m_ParAuditingProcessLogModel=new ParAuditingProcessLogModel();
		m_ParAuditingProcessLogModel.setProcessLogID(str_ProcessLogID);
		m_ParAuditingProcessLogModel.setIsArchive(p_IsArchive);
		
		sqlString.append(getSelectSql(m_ParAuditingProcessLogModel));
		Statement stm=null;
		ResultSet objRs =null;
		try
		{   
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if (objRs!=null && objRs.next()) 
			{
				m_AuditingProcessLogModel.setProcessLogID(objRs.getString("ProcessLogID"));
				m_AuditingProcessLogModel.setProcessID(objRs.getString("ProcessID"));
				m_AuditingProcessLogModel.setAct(objRs.getString("Act"));
				m_AuditingProcessLogModel.setlogUser(objRs.getString("logUser"));
				m_AuditingProcessLogModel.setlogUserID(objRs.getString("logUserID"));
				m_AuditingProcessLogModel.setStDate(objRs.getLong("StDate"));
				m_AuditingProcessLogModel.setResult(objRs.getString("Result"));
				m_AuditingProcessLogModel.setProcessLogBaseID(objRs.getString("ProcessLogBaseID"));
				m_AuditingProcessLogModel.setProcessLogBaseSchema(objRs.getString("ProcessLogBaseSchema"));				
			}
		}catch(Exception ex)
		{
			System.err.println("AuditingProcessLog.GetOneForKey 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		finally
		{
			try{
				if(objRs!=null)
					objRs.close();
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
		return m_AuditingProcessLogModel;
	}		
	
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	public  String getSelectSql(ParAuditingProcessLogModel p_ParAuditingProcessLogModel)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParAuditingProcessLogModel==null)
			p_ParAuditingProcessLogModel=new ParAuditingProcessLogModel();
		if(p_ParAuditingProcessLogModel.getIsArchive()==0||p_ParAuditingProcessLogModel.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLogModel));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_ParAuditingProcessLogModel.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLogModel));
			sqlString.append(" union ");
//			生成当前历史信息的sql
			p_ParAuditingProcessLogModel.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLogModel));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return(sqlString.toString());
	}	
	
	/**
	 * 返回查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParAuditingProcessLogModel p_ParAuditingProcessLogModel)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblname="";
		if(p_ParAuditingProcessLogModel.getIsArchive()==1)
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog_H);
		else
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcessLog);
		
		strReSql.append("SELECT ");
		strReSql.append(" C1 as ProcessLogID,C700020401 as ProcessID,C700020402 as Act,C700020403 as logUser");
		strReSql.append(",C700020404 as logUserID,C700020405 as StDate,C700020406 as Result ");
		strReSql.append(",C700020407 as ProcessLogBaseID,C700020408 as ProcessLogBaseSchema ");
		strReSql.append(" from "+strTblname+" ");	
		strReSql.append(" where 1=1 ");
		if(p_ParAuditingProcessLogModel!=null)
			strReSql.append(p_ParAuditingProcessLogModel.GetWhereSql());		
		return(strReSql.toString());
	}	
	
	/**
	 * 根据记录集返回列表	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList(ResultSet p_AuditingProcessLogRs) 
	{
		if(p_AuditingProcessLogRs==null) return null;
		List list = new ArrayList();
		try{
			while (p_AuditingProcessLogRs.next())
			{
				AuditingProcessLogModel m_AuditingProcessLog=new AuditingProcessLogModel();
				m_AuditingProcessLog.setProcessLogID(p_AuditingProcessLogRs.getString("ProcessLogID"));
				m_AuditingProcessLog.setProcessID(p_AuditingProcessLogRs.getString("ProcessID"));
				m_AuditingProcessLog.setAct(p_AuditingProcessLogRs.getString("Act"));
				m_AuditingProcessLog.setlogUser(p_AuditingProcessLogRs.getString("logUser"));
				m_AuditingProcessLog.setlogUserID(p_AuditingProcessLogRs.getString("logUserID"));
				m_AuditingProcessLog.setStDate(p_AuditingProcessLogRs.getLong("StDate"));
				m_AuditingProcessLog.setResult(p_AuditingProcessLogRs.getString("Result"));			 
				list.add(m_AuditingProcessLog);
			}
			p_AuditingProcessLogRs.close();
		}catch(Exception ex)
		{
			System.err.println("AuditingProcessLog.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}		
	/**
	 * 静态的得到某类工单某个处理环节的日志列表方法，返回一个List
	 * @param str_ProcessID
	 * @return
	 * @throws Exception
	 */
	public  List GetList(String str_ProcessID,int p_PageNumber, int p_StepRow,int p_IsArchive) 
	{
		
		List m_list ;
		ParAuditingProcessLogModel p_AuditingProcessLog=new ParAuditingProcessLogModel();
		//700020401	ProcessID 指向主工单处理过程记录的指针
		p_AuditingProcessLog.setProcessID(str_ProcessID);
		p_AuditingProcessLog.setIsArchive(p_IsArchive);
		m_list=GetList(p_AuditingProcessLog,p_PageNumber,p_StepRow);
		return m_list;		
		
	}
		
	/**
	 * 根据条件查询审批日志信息，并返回审批日志信息(AuditingProcessLogModel)实例对象列表
	 * @param p_AuditingProcessLog
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParAuditingProcessLogModel p_AuditingProcessLog,int p_PageNumber, int p_StepRow)
	{
		List m_List=new ArrayList();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(getSelectSql(p_AuditingProcessLog));
		String strSql=sqlString.toString();
		
		GetQueryResultRows(strSql);
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		Statement stm=null;
		ResultSet objRs =null;
		try {
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strSql);
			m_List= ConvertRsToList(objRs);
		} catch (Exception ex) {
			System.err.println("AuditingProcessLog.GetList 方法:"+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (objRs != null)
					objRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_List;
		
	}
	
	/**
	 * 
	 * @param p_Baschema
	 * @param p_BaseID
	 * @return
	 */
	public List GetList(String p_Baschema,String p_BaseID,int p_isArchive)
	{
		
		List m_List=null;
		StringBuffer strSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		
		ParAuditingProcessLogModel m_ParAuditingProcessLogModel=new ParAuditingProcessLogModel();
		m_ParAuditingProcessLogModel.setIsArchive(p_isArchive);
		
		String tblDealProcess="";
		if(m_ParAuditingProcessLogModel.getIsArchive()==0)
		{
			
			strSql.append(getSelectSql(m_ParAuditingProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");			
		}
		else if(m_ParAuditingProcessLogModel.getIsArchive()==1)
		{
			strSql.append(getSelectSql(m_ParAuditingProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");					
			
		}
		else
		{
			strSql.append(" select * from (");
			//生成当前活动信息的sql
			m_ParAuditingProcessLogModel.setIsArchive(0);			
			strSql.append(getSelectSql(m_ParAuditingProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");	
			
			strSql.append(" union ");
			
//			生成历史信息查询的sql
			m_ParAuditingProcessLogModel.setIsArchive(1);
			strSql.append(getSelectSql(m_ParAuditingProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingProcess_H);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");
			
			strSql.append(") ResultTbl ");
		}

		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet objRs =null;
		String strNewSql=strSql.toString();
		try {
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strNewSql);
			m_List= ConvertRsToList(objRs);
		} catch (Exception ex) {
			System.err.println("DealProcessLog.GetList 方法:"+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (objRs != null)
					objRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_List;		
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
		Statement stm=null;
		ResultSet m_BaseRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
			{
				
				System.err.println("Base.GetQueryResultRows 方法："+ex.getMessage());
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
	    	p_strFormName = Constants.TblAuditingProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcessLog_H;	  	    	
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
	    	p_strFormName = Constants.TblAuditingProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcessLog_H;	  	    	
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
	    	p_strFormName = Constants.TblAuditingProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingProcessLog_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
	
	/**
	 * 批量删除审批日志
	 * @param IsArchive
	 * @param p_AuditingProcessLog
	 * @return
	 */
	public boolean delete(int IsArchive,ParAuditingProcessLogModel p_AuditingProcessLog)
	{
		List m_list=this.GetList(p_AuditingProcessLog,0,0);
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
	    if(IsArchive==0){
	    	strFormName = Constants.TblAuditingProcessLog;	    	
	    }else
	    {
	    	strFormName = Constants.TblAuditingProcessLog_H;	  	    	
	    }			
		try{
			AuditingProcessLogModel m_AuditingProcessLogModel;
			for(int row=0;row<rowCount;row++)
			{
				m_AuditingProcessLogModel=(AuditingProcessLogModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_AuditingProcessLogModel.getProcessLogID());
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
