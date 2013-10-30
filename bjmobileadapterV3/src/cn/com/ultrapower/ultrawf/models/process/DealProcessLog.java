package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;
import java.sql.*;

import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.ultrawf.share.FormatString;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.*;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.ultrawf.share.queryanalyse.ParseParmeter;

public class DealProcessLog {
	
public static String BASESCHEMA = "WF:App_DealProcessLog";
	
	public List getDealProcessLogList(String processID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		String sqlString = "select C1 as ProcessLogID" +
				", C700020407 as ProcessLogBaseID" +
				", C700020408 as ProcessLogBaseSchema" +
				", C700020401 as ProcessID" +
				", C700020402 as LogAct" +
				", C700020403 as logUser" +
				", C700020404 as logUserID" +
				", C700020405 as StDate" +
				", C700020409 as FlagStart" +
				", C700020406 as LogResult" +
				" from " + tablename + " where C700020401 = '" + processID + "' order by C1 asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List tdplList = new ArrayList();
		try
		{
			while(rs.next())
			{
				DealProcessLogModel dplModel = new DealProcessLogModel();
				dplModel.setProcessLogID(rs.getString("ProcessLogID"));
				dplModel.setProcessLogBaseID(rs.getString("ProcessLogBaseID"));
				dplModel.setProcessLogBaseSchema(rs.getString("ProcessLogBaseSchema"));
				dplModel.setProcessID(rs.getString("ProcessID"));
				dplModel.setAct(rs.getString("LogAct"));
				dplModel.setLogUser(rs.getString("logUser"));
				dplModel.setLogUserID(rs.getString("logUserID"));
				dplModel.setStDate(rs.getLong("StDate"));
				dplModel.setFlagStart(rs.getInt("StDate"));
				dplModel.setResult(rs.getString("LogResult"));
				tdplList.add(dplModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
		return tdplList;
	}
 
	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	public DealProcessLog(){}

	/**
	 * 根据关键字ProcessLogID查询处理环节日志信息，并返回一个处理环节日志信息(DealProcessLogModel)对象
	 * @param str_LinkID
	 */
	public DealProcessLogModel GetOneForKey(String str_ProcessLogID,int p_IsArchive)
	{
		if(str_ProcessLogID==null||str_ProcessLogID.trim().equals(""))
			return null;
		StringBuffer sqlString = new StringBuffer();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		DealProcessLogModel m_DealProcessLogModel=new DealProcessLogModel();
		ParDealProcessLogModel m_ParDealProcessLogModel=new ParDealProcessLogModel();
		m_ParDealProcessLogModel.setProcessLogID(str_ProcessLogID);
		m_ParDealProcessLogModel.setIsArchive(p_IsArchive);
		
		sqlString.append(getSelectSql(m_ParDealProcessLogModel));
		Statement stm=null;
		ResultSet objRs =null;
		try
		{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if (objRs.next()) 
			{
				m_DealProcessLogModel.setProcessLogID(objRs.getString("ProcessLogID"));
				m_DealProcessLogModel.setProcessID(objRs.getString("ProcessID"));
				m_DealProcessLogModel.setAct(objRs.getString("Act"));
				m_DealProcessLogModel.setLogUser(objRs.getString("logUser"));
				m_DealProcessLogModel.setLogUserID(objRs.getString("logUserID"));
				m_DealProcessLogModel.setStDate(objRs.getLong("StDate"));
				m_DealProcessLogModel.setResult(objRs.getString("Result"));	
				m_DealProcessLogModel.setProcessLogBaseID(objRs.getString("ProcessLogBaseID"));
				m_DealProcessLogModel.setProcessLogBaseSchema(objRs.getString("ProcessLogBaseSchema"));
			}
		}catch(Exception ex)
		{
			System.err.println("DealProcessLog.SetDealProcessLogInfo方法："+ex.getMessage());
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
		return m_DealProcessLogModel;
	}		
	
	
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	public  String getSelectSql(ParDealProcessLogModel p_DealProcessLog)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_DealProcessLog==null)
			p_DealProcessLog=new ParDealProcessLogModel();
		if(p_DealProcessLog.getIsArchive()==0||p_DealProcessLog.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_DealProcessLog));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_DealProcessLog.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_DealProcessLog));
			sqlString.append(" union all ");
//			生成当前历史信息的sql
			p_DealProcessLog.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_DealProcessLog));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return(sqlString.toString());
	}		
	
	/**
	 * 返回查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParDealProcessLogModel p_DealProcessLog)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblname="";
		if(p_DealProcessLog.getIsArchive()==1)
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
		else
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
		
		strReSql.append("SELECT ");
		strReSql.append(" C1 as ProcessLogID,C700020401 as ProcessID,C700020402 as Act,C700020403 as logUser");
		strReSql.append(",C700020404 as logUserID,C700020405 as StDate,C700020406 as Result ");
		strReSql.append(",C700020407 as ProcessLogBaseID,C700020408 as ProcessLogBaseSchema ");
		strReSql.append(" from "+strTblname+" ");	
		strReSql.append(" where 1=1 ");
		if(p_DealProcessLog!=null)
			strReSql.append(p_DealProcessLog.GetWhereSql());
		return(strReSql.toString());
	}	
	
	/**
	 * 根据记录集返回列表
	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList(ResultSet p_DealProcessLogRs) 
	{
		if(p_DealProcessLogRs==null) return null;
		List list = new ArrayList();
		try{
			while (p_DealProcessLogRs.next())
			{
				DealProcessLogModel m_DealProcessLog=new DealProcessLogModel();
				m_DealProcessLog.setProcessLogID(p_DealProcessLogRs.getString("ProcessLogID"));
				m_DealProcessLog.setProcessID(p_DealProcessLogRs.getString("ProcessID"));
				m_DealProcessLog.setAct(p_DealProcessLogRs.getString("Act"));
				m_DealProcessLog.setLogUser(p_DealProcessLogRs.getString("logUser"));
				m_DealProcessLog.setLogUserID(p_DealProcessLogRs.getString("logUserID"));
				m_DealProcessLog.setStDate(p_DealProcessLogRs.getLong("StDate"));
				m_DealProcessLog.setResult(p_DealProcessLogRs.getString("Result"));			 
				list.add(m_DealProcessLog);
			}
			p_DealProcessLogRs.close();
		}catch(Exception ex)
		{
			System.err.println("DealProcessLog.ConvertRsToList"+ex.getMessage());
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
	public  List GetList(String str_ProcessID,int p_PageNumber, int p_StepRow,int p_IsArchive) throws Exception
	{
		List m_list ;
		ParDealProcessLogModel p_DealProcessLog=new ParDealProcessLogModel();
		//700020401	ProcessID 指向主工单处理过程记录的指针
		p_DealProcessLog.setProcessID(str_ProcessID);
		p_DealProcessLog.setIsArchive(p_IsArchive);
		
		m_list=GetList(p_DealProcessLog,p_PageNumber,p_StepRow);
		return m_list;				
		
	}
	
	/**
	 * 
	 * @param p_Baschema
	 * @param p_BaseID
	 * @param p_isArchive
	 * @return
	 */
	public List GetList(String p_Baschema,String p_BaseID,int p_isArchive)
	{
		List m_List=null;
		StringBuffer strSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		
		ParDealProcessLogModel m_ParDealProcessLogModel=new ParDealProcessLogModel();
		m_ParDealProcessLogModel.setIsArchive(p_isArchive);
		
		String tblDealProcess="";
		if(m_ParDealProcessLogModel.getIsArchive()==0)
		{
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			strSql.append(getSelectSql(m_ParDealProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(") ");	

		}
		else if(m_ParDealProcessLogModel.getIsArchive()==1)
		{
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
			strSql.append(getSelectSql(m_ParDealProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
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
			m_ParDealProcessLogModel.setIsArchive(0);			
			
			strSql.append(getSelectSql(m_ParDealProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");	
			
			strSql.append(" union all ");
			
//			生成历史信息查询的sql
			
			m_ParDealProcessLogModel.setIsArchive(1);
			strSql.append(getSelectSql(m_ParDealProcessLogModel));
			//700020401	ProcessID
			strSql.append(" and C700020401 in ");
			tblDealProcess=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
			strSql.append("(select C1 from "+tblDealProcess);
			//700020001	ProcessBaseID
			//700020002	ProcessBaseSchema
			strSql.append(" where C700020001='"+p_BaseID+"'");
			strSql.append(" and C700020002='"+p_Baschema+"'");
			strSql.append(")");
			
			strSql.append(") ResultTbl ");
		}
		String strOrder=m_ParDealProcessLogModel.getOrderbyFiledNameString();
		if(strOrder.trim().equals(""))
			strOrder=" order by ProcessLogID";
		//排序
		strSql.append(strOrder);
		
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
	 * 根据条件查询处理日志信息，并返回处理日志信息(DealProcessLogModel)实例对象列表
	 * @param p_DealProcessLog
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParDealProcessLogModel p_DealProcessLog,int p_PageNumber, int p_StepRow)
	{
		List m_List=new ArrayList();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(getSelectSql(p_DealProcessLog));
		
		String strSql=sqlString.toString();
		GetQueryResultRows(strSql);
		
		String strOrder=p_DealProcessLog.getOrderbyFiledNameString();
		if(strOrder.trim().equals(""))
			strOrder=" order by ProcessLogID";
		//排序
		strSql+=strOrder;
		
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
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet objRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(objRs.next())
			{
				intQueryResultRows=objRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			System.err.println("DealProcessLog.GetQueryResultRows 方法："+ex.getMessage());
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
	    	p_strFormName = Constants.TblDealProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcessLog_H;	  	    	
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
	    	p_strFormName = Constants.TblDealProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcessLog_H;	  	    	
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
	    	p_strFormName = Constants.TblDealProcessLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcessLog_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
	
	
	public boolean delete(int IsArchive,ParDealProcessLogModel p_ParDealProcessLog)
	{
		if(p_ParDealProcessLog==null)
			return false;
		List m_list=this.GetList(p_ParDealProcessLog,0,0);
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
	    	strFormName = Constants.TblDealProcessLog;	    	
	    }else
	    {
	    	strFormName = Constants.TblDealProcessLog_H;	  	    	
	    }		
		try{
			

			DealProcessLogModel m_DealProcessLogModel;
			for(int row=0;row<rowCount;row++)
			{
				m_DealProcessLogModel=(DealProcessLogModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_DealProcessLogModel.getProcessLogID());
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
	
	
	/*变量绑定*/
	private  List ConvertRsToListForBind( ResultSet p_objRs) 
	{	
		if(p_objRs==null) return null;
		List list = new ArrayList();
		try{
			String colName;
			ResultSetMetaData metaData=p_objRs.getMetaData();
			int cols=metaData.getColumnCount();			
			while (p_objRs.next())
			{
				DealProcessLogModel m_DealProcessLog=new DealProcessLogModel();
				
				 for(int col=1;col<=cols;col++)
				 {		
					 colName=metaData.getColumnName(col);
					 if(FormatString.compareEqualsString(colName,"ProcessLogID"))
						 m_DealProcessLog.setProcessLogID(p_objRs.getString("ProcessLogID"));
					 if(FormatString.compareEqualsString(colName,"ProcessID"))
						 m_DealProcessLog.setProcessID(p_objRs.getString("ProcessID"));
					 if(FormatString.compareEqualsString(colName,"Act"))
						 m_DealProcessLog.setAct(p_objRs.getString("Act"));
					 if(FormatString.compareEqualsString(colName,"logUser"))
						m_DealProcessLog.setLogUser(p_objRs.getString("logUser"));
					 if(FormatString.compareEqualsString(colName,"logUserID"))
						 m_DealProcessLog.setLogUserID(p_objRs.getString("logUserID"));
					 if(FormatString.compareEqualsString(colName,"StDate"))
						 m_DealProcessLog.setStDate(p_objRs.getLong("StDate"));
					 if(FormatString.compareEqualsString(colName,"Result"))
						 m_DealProcessLog.setResult(p_objRs.getString("Result"));
					 if(FormatString.compareEqualsString(colName,"ProcessLogBaseID"))
						 m_DealProcessLog.setProcessLogBaseID(p_objRs.getString("ProcessLogBaseID"));
					 if(FormatString.compareEqualsString(colName,"ProcessLogBaseSchema"))
						 m_DealProcessLog.setProcessLogBaseSchema(p_objRs.getString("ProcessLogBaseSchema"));				

				 }//for(int col=1;col<=cols;col++)
				 list.add(m_DealProcessLog);
			}//while (p_DealRs.next())
		}catch(Exception ex)
		{
			System.err.println("DealProcess.ConvertRsToListForBind:"+ex.getMessage());
			ex.printStackTrace();			
		}
		
		return list;
	}	
		
	
	
	private String getSelectFiled(String tblAlias)
	{
		tblAlias=FormatString.CheckNullString(tblAlias);
		if(!tblAlias.equals(""))
			tblAlias+=".";
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(tblAlias+"C1 as ProcessLogID,");
		stringBuffer.append(tblAlias+"C700020401 as ProcessID,");
		stringBuffer.append(tblAlias+"C700020402 as Act,");
		stringBuffer.append(tblAlias+"C700020403 as logUser,");
		stringBuffer.append(tblAlias+"C700020404 as logUserID,");
		stringBuffer.append(tblAlias+"C700020405 as StDate,");
		stringBuffer.append(tblAlias+"C700020406 as Result,");
		stringBuffer.append(tblAlias+"C700020407 as ProcessLogBaseID,");
		stringBuffer.append(tblAlias+"C700020408 as ProcessLogBaseSchema ");
		return stringBuffer.toString();
		
	}
	
	
	
	public List getListForBind(ParDealProcessLogModel p_DealProcessLog,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;

		
		List m_FiledinfoList=null;
		
		if(p_DealProcessLog!=null)
		{
			m_FiledinfoList=p_DealProcessLog.getContionFiledInfoList();
		}
		else
			p_DealProcessLog=new ParDealProcessLogModel();
		ParseParmeter parseParmeter=new ParseParmeter(m_FiledinfoList);
		String basewhereSql=parseParmeter.getWhereSql();
		basewhereSql=FormatString.CheckNullString(basewhereSql);
		String dealFiled=parseParmeter.getSelectFiled();
		dealFiled=FormatString.CheckNullString(dealFiled);
		
		String whereSql=basewhereSql;
		String selectFiled=dealFiled;		
		
		if(selectFiled.equals(""))
		{
			selectFiled=this.getSelectFiled("");
		}		
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strDeallogTblName="";
		String strDeallogTblName_h="";
		int isArchive=p_DealProcessLog.getIsArchive();
		if(isArchive==0 || isArchive==1)
		{
			if(isArchive==1)
				strDeallogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
			else
				strDeallogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
		}
		else
		{
			strDeallogTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog);
			strDeallogTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcessLog_H);
		}
		
		StringBuffer sqlString = new StringBuffer();
		StringBuffer sqlRowCount = new StringBuffer();
		if(isArchive==0 || isArchive==1)
		{
			
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			if(isArchive==1)
				sqlString.append(" from "+strDeallogTblName_h+" deallog " );
			else
				sqlString.append(" from "+strDeallogTblName+" deallog " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			//排序
			String strOrder=p_DealProcessLog.getOrderbyFiledNameString();
			sqlString.append(strOrder);
			
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			if(isArchive==1)
				sqlRowCount.append(" from "+strDeallogTblName_h+" deallog " );
			else
				sqlRowCount.append(" from "+strDeallogTblName+" deallog " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);			
			
		}
		else
		{
			
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			sqlString.append(" from "+strDeallogTblName+" deallog " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			sqlString.append(" union all ");
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			sqlString.append(" from "+strDeallogTblName_h+" deallog " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			//排序
			String strOrder=p_DealProcessLog.getOrderbyFiledNameString();
			sqlString.append(strOrder);			
			//数量查询sql
			sqlRowCount.append(" select sum(rownums) rownums from (");
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			sqlRowCount.append(" from "+strDeallogTblName+" deallog " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);
			sqlRowCount.append(" union all ");
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			sqlRowCount.append(" from "+strDeallogTblName_h+" deallog " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);
			sqlRowCount.append(" ) deallog ");
			
		}
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=sqlString.toString();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//System.out.println("DealProcessLog-GetListForBind(ParDealProcess) strSql:"+strSql);
		//绑定查询条数变量
		int intStartRow=0;	
		int intRowCount=0;
		if(p_PageNumber>0)
		{
			if(p_PageNumber==1)
				intStartRow=1;
			else if(p_PageNumber>1)
				intStartRow=(p_PageNumber-1)*p_StepRow+1;
			else
				intStartRow=0;
			intRowCount=p_PageNumber*p_StepRow;
		}		
		PreparedStatement prestm=null;
		ResultSet m_ObjRs =null;		
		try{
			
			String[] dealValueList=parseParmeter.getParmeterValue();
			int deallen=0;
			if(dealValueList!=null)
				deallen=dealValueList.length;
			int bindIndex=0;
			
			prestm=m_dbConsole.getConn().prepareStatement(sqlRowCount.toString());
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
			}
			if(isArchive!=0 && isArchive!=1)
			{
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
				}				
			}			
			m_ObjRs=prestm.executeQuery();
			if(m_ObjRs!=null)
			{
				if(m_ObjRs.next())
				{
					intQueryResultRows=m_ObjRs.getInt("rownums");
				}			
			}
			
			if(intQueryResultRows<=0)
				return null;
			if(p_PageNumber<0||p_StepRow<0)
				return null;			
			
			m_ObjRs.close();
			prestm.close();			
			
			prestm=m_dbConsole.getConn().prepareStatement(strSql);
			bindIndex=0;		
			for(int i=0;i<deallen;i++)
			{	bindIndex++;
				prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
			}
			if(isArchive!=0 && isArchive!=1)
			{
				for(int i=0;i<deallen;i++)
				{	bindIndex++;
					prestm.setString(bindIndex, dealValueList[i] ); //为绑定变量赋值
				}				
			}
			if(p_PageNumber>0)
			{
				//查询条数限制的变量绑定
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intRowCount));
				bindIndex++;
				prestm.setString(bindIndex,String.valueOf(intStartRow));
			}			
			m_ObjRs=prestm.executeQuery();
			
			if(dealFiled.equals(""))
				m_Relist=ConvertRsToList(m_ObjRs);
			else
				m_Relist=ConvertRsToListForBind(m_ObjRs);
		}catch(Exception ex)
		{
			System.err.println("DealProcessLog.GetListForBind 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try {
				if (m_ObjRs != null)
					m_ObjRs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (prestm != null)
					prestm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return m_Relist;
	}	
		
	
}
