package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;
import java.sql.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

import cn.com.ultrapower.ultrawf.share.queryanalyse.ParseParmeter;
import cn.com.ultrapower.ultrawf.share.FormatString;

public class DealProcess {
	
	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	public DealProcess(){}
	/**
	 * 根据关键字查询并返回一个处理信息(DealProcessModel对象)
	 * @param p_strProcessID
	 * @return
	 */
	public DealProcessModel GetOneForKey(String p_strProcessID,int p_IsArchive)
	{
		DealProcessModel m_DealProcessModel=null;
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
				m_DealProcessModel=new DealProcessModel();
				 m_DealProcessModel.setProcessID(p_DealRs.getString("ProcessID"));
				 m_DealProcessModel.setProcessBaseID(p_DealRs.getString("ProcessBaseID"));
				 m_DealProcessModel.setProcessBaseSchema(p_DealRs.getString("ProcessBaseSchema"));
				 m_DealProcessModel.setPhaseNo(p_DealRs.getString("PhaseNo"));
				 m_DealProcessModel.setPrevPhaseNo(p_DealRs.getString("PrevPhaseNo"));
				 m_DealProcessModel.setCreateByUserID(p_DealRs.getString("CreateByUserID"));
				 m_DealProcessModel.setAssginee(p_DealRs.getString("Assginee"));
				 m_DealProcessModel.setAssgineeID(p_DealRs.getString("AssgineeID"));
				 m_DealProcessModel.setGroup(p_DealRs.getString("GroupName"));
				 m_DealProcessModel.setGroupID(p_DealRs.getString("GroupID"));
				 m_DealProcessModel.setDealer(p_DealRs.getString("Dealer"));
				 m_DealProcessModel.setDealerID(p_DealRs.getString("DealerID"));
				 m_DealProcessModel.setProcessStatus(p_DealRs.getString("ProcessStatus"));
				 m_DealProcessModel.setAssignOverTimeDate(p_DealRs.getLong("AssignOverTimeDate"));
				 m_DealProcessModel.setAcceptOverTimeDate(p_DealRs.getLong("AcceptOverTimeDate"));
				 m_DealProcessModel.setDealOverTimeDate(p_DealRs.getLong("DealOverTimeDate"));
				 m_DealProcessModel.setStDate(p_DealRs.getLong("StDate"));
				 m_DealProcessModel.setBgDate(p_DealRs.getLong("BgDate"));
				 m_DealProcessModel.setEdDate(p_DealRs.getLong("EdDate"));
				 m_DealProcessModel.setDesc(p_DealRs.getString("DescInfo"));
				 m_DealProcessModel.setFlagType(p_DealRs.getString("FlagType"));
				 m_DealProcessModel.setFlagActive(p_DealRs.getInt("FlagActive"));
				 m_DealProcessModel.setFlagPredefined(p_DealRs.getInt("FlagPredefined"));
				 m_DealProcessModel.setFlagDuplicated(p_DealRs.getInt("FlagDuplicated"));
				 m_DealProcessModel.setFlag01Assign(p_DealRs.getString("Flag01Assign"));
				 m_DealProcessModel.setFlag02Copy(p_DealRs.getString("Flag02Copy"));
				 m_DealProcessModel.setFlag03Assist(p_DealRs.getString("Flag03Assist"));
				 m_DealProcessModel.setFlag04Transfer(p_DealRs.getString("Flag04Transfer"));
				 m_DealProcessModel.setFlag05TurnDown(p_DealRs.getString("Flag05TurnDown"));
				 m_DealProcessModel.setFlag06TurnUp(p_DealRs.getString("Flag06TurnUp"));
				 m_DealProcessModel.setFlag07Recall(p_DealRs.getString("Flag07Recall"));
				 m_DealProcessModel.setFlag08Cancel(p_DealRs.getString("Flag08Cancel"));
				 m_DealProcessModel.setFlag09Close(p_DealRs.getString("Flag09Close"));
				 m_DealProcessModel.setFlag15ToAuditing(p_DealRs.getString("Flag15ToAuditing"));
				 m_DealProcessModel.setFlag20SideBySide(p_DealRs.getString("Flag20SideBySide"));
				 m_DealProcessModel.setFlag22IsSelect(p_DealRs.getString("Flag22IsSelect"));
				 m_DealProcessModel.setFlag30AuditingResult(p_DealRs.getString("Flag30AuditingResult"));
				 m_DealProcessModel.setFlag31IsTransfer(p_DealRs.getString("Flag31IsTransfer"));
				 m_DealProcessModel.setTransferPhaseNo(p_DealRs.getString("TransferPhaseNo"));
				 m_DealProcessModel.setProcessType(p_DealRs.getString("ProcessType"));
				 
				 m_DealProcessModel.setCommissioner(p_DealRs.getString("Commissioner"));
				 m_DealProcessModel.setCommissionerID(p_DealRs.getString("CommissionerID"));
				 m_DealProcessModel.setCloseBaseSamenessGroup(p_DealRs.getString("CloseBaseSamenessGroup"));
				 m_DealProcessModel.setCloseBaseSamenessGroupID(p_DealRs.getString("CloseBaseSamenessGroupID"));
				 //m_DealProcessModel.setIsGroupSnatch(p_DealRs.getString("IsGroupSnatch"));
				 m_DealProcessModel.setFlag32IsToTransfer(p_DealRs.getString("Flag32IsToTransfer"));
				 m_DealProcessModel.setFlag33IsEndPhase(p_DealRs.getString("Flag33IsEndPhase"));				 
				
			}//if (p_DealRs.next())
		}catch(Exception ex)
		{
			System.err.println("DealProcess.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}			
		finally
		{
			try {
				if (p_DealRs != null)
					p_DealRs.close();
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
		return m_DealProcessModel;
	}
	
	/**
	 * 根据记录集返回列表	 * @param p_DealRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList( ResultSet p_DealRs)  throws Exception
	{	
		if(p_DealRs==null) return null;
		List list = new ArrayList();
		try{
			while (p_DealRs!=null&&p_DealRs.next())
			{
				 DealProcessModel m_DealProcessModel=new DealProcessModel();
				 m_DealProcessModel.setProcessID(p_DealRs.getString("ProcessID"));
				 m_DealProcessModel.setProcessBaseID(p_DealRs.getString("ProcessBaseID"));
				 m_DealProcessModel.setProcessBaseSchema(p_DealRs.getString("ProcessBaseSchema"));
				 m_DealProcessModel.setPhaseNo(p_DealRs.getString("PhaseNo"));
				 m_DealProcessModel.setPrevPhaseNo(p_DealRs.getString("PrevPhaseNo"));
				 m_DealProcessModel.setCreateByUserID(p_DealRs.getString("CreateByUserID"));
				 m_DealProcessModel.setAssginee(p_DealRs.getString("Assginee"));
				 m_DealProcessModel.setAssgineeID(p_DealRs.getString("AssgineeID"));
				 m_DealProcessModel.setGroup(p_DealRs.getString("GroupName"));
				 m_DealProcessModel.setGroupID(p_DealRs.getString("GroupID"));
				 m_DealProcessModel.setDealer(p_DealRs.getString("Dealer"));
				 m_DealProcessModel.setDealerID(p_DealRs.getString("DealerID"));
				 m_DealProcessModel.setProcessStatus(p_DealRs.getString("ProcessStatus"));
				 m_DealProcessModel.setAssignOverTimeDate(p_DealRs.getLong("AssignOverTimeDate"));
				 m_DealProcessModel.setAcceptOverTimeDate(p_DealRs.getLong("AcceptOverTimeDate"));
				 m_DealProcessModel.setDealOverTimeDate(p_DealRs.getLong("DealOverTimeDate"));
				 m_DealProcessModel.setStDate(p_DealRs.getLong("StDate"));
				 m_DealProcessModel.setBgDate(p_DealRs.getLong("BgDate"));
				 m_DealProcessModel.setEdDate(p_DealRs.getLong("EdDate"));
				 m_DealProcessModel.setDesc(p_DealRs.getString("DescInfo"));
				 m_DealProcessModel.setFlagType(p_DealRs.getString("FlagType"));
				 m_DealProcessModel.setFlagActive(p_DealRs.getInt("FlagActive"));
				 m_DealProcessModel.setFlagPredefined(p_DealRs.getInt("FlagPredefined"));
				 m_DealProcessModel.setFlagDuplicated(p_DealRs.getInt("FlagDuplicated"));
				 m_DealProcessModel.setFlag01Assign(p_DealRs.getString("Flag01Assign"));
				 m_DealProcessModel.setFlag02Copy(p_DealRs.getString("Flag02Copy"));
				 m_DealProcessModel.setFlag03Assist(p_DealRs.getString("Flag03Assist"));
				 m_DealProcessModel.setFlag04Transfer(p_DealRs.getString("Flag04Transfer"));
				 m_DealProcessModel.setFlag05TurnDown(p_DealRs.getString("Flag05TurnDown"));
				 m_DealProcessModel.setFlag06TurnUp(p_DealRs.getString("Flag06TurnUp"));
				 m_DealProcessModel.setFlag07Recall(p_DealRs.getString("Flag07Recall"));
				 m_DealProcessModel.setFlag08Cancel(p_DealRs.getString("Flag08Cancel"));
				 m_DealProcessModel.setFlag09Close(p_DealRs.getString("Flag09Close"));
				 m_DealProcessModel.setFlag15ToAuditing(p_DealRs.getString("Flag15ToAuditing"));
				 m_DealProcessModel.setFlag20SideBySide(p_DealRs.getString("Flag20SideBySide"));
				 m_DealProcessModel.setFlag22IsSelect(p_DealRs.getString("Flag22IsSelect"));
				 m_DealProcessModel.setFlag30AuditingResult(p_DealRs.getString("Flag30AuditingResult"));
				 m_DealProcessModel.setFlag31IsTransfer(p_DealRs.getString("Flag31IsTransfer"));
				 m_DealProcessModel.setTransferPhaseNo(p_DealRs.getString("TransferPhaseNo"));
				 m_DealProcessModel.setProcessType(p_DealRs.getString("ProcessType"));
			 
				 m_DealProcessModel.setCommissioner(p_DealRs.getString("Commissioner"));
				 m_DealProcessModel.setCommissionerID(p_DealRs.getString("CommissionerID"));
				 m_DealProcessModel.setCloseBaseSamenessGroup(p_DealRs.getString("CloseBaseSamenessGroup"));
				 m_DealProcessModel.setCloseBaseSamenessGroupID(p_DealRs.getString("CloseBaseSamenessGroupID"));
				 //m_DealProcessModel.setIsGroupSnatch(p_DealRs.getString("IsGroupSnatch"));
				 m_DealProcessModel.setFlag32IsToTransfer(p_DealRs.getString("Flag32IsToTransfer"));
				 m_DealProcessModel.setFlag33IsEndPhase(p_DealRs.getString("Flag33IsEndPhase"));
				 
				 list.add(m_DealProcessModel);
			}//while (p_DealRs.next())
			p_DealRs.close();
		}catch(Exception ex)
		{
			System.err.println(ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		
		return list;
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
	 * 返回处理环节查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParDealProcess p_ParDealProcess)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblProcessName="";
		if(p_ParDealProcess.getIsArchive()==1)
			tblProcessName=Constants.TblDealProcess_H;
		else
			tblProcessName=Constants.TblDealProcess;
		String strTblname=m_RemedyDBOp.GetRemedyTableName(tblProcessName);
		
		strReSql.append("SELECT ");
		strReSql.append("  DealProcess.C1 as ProcessID,DealProcess.C700020001 as ProcessBaseID,DealProcess.C700020002 as ProcessBaseSchema,DealProcess.C700020003 as PhaseNo");
		strReSql.append(" ,DealProcess.C700020004 as PrevPhaseNo,DealProcess.C700020045 as CreateByUserID,DealProcess.C700020005 as Assginee,DealProcess.C700020006 as AssgineeID,DealProcess.C700020007 as GroupName");
		strReSql.append(" ,DealProcess.C700020008 as GroupID,DealProcess.C700020009 as Dealer,DealProcess.C700020010 as DealerID,DealProcess.C700020011 as ProcessStatus,DealProcess.C700020012 as AssignOverTimeDate");
		strReSql.append(" ,DealProcess.C700020013 as AcceptOverTimeDate,DealProcess.C700020014 as DealOverTimeDate,DealProcess.C700020015 as StDate,DealProcess.C700020016 as BgDate");
		strReSql.append(" ,DealProcess.C700020017 as EdDate,DealProcess.C700020018 as DescInfo,DealProcess.C700020019 as FlagType,DealProcess.C700020020 as FlagActive,DealProcess.C700020021 as FlagPredefined");
		strReSql.append(" ,DealProcess.C700020022 as FlagDuplicated,DealProcess.C700020023 as Flag01Assign,DealProcess.C700020024 as Flag02Copy,DealProcess.C700020025 as Flag03Assist");
		strReSql.append(" ,DealProcess.C700020026 as Flag04Transfer,DealProcess.C700020027 as Flag05TurnDown,DealProcess.C700020028 as Flag06TurnUp,DealProcess.C700020029 as Flag07Recall");
		strReSql.append(" ,DealProcess.C700020030 as Flag08Cancel,DealProcess.C700020031 as Flag09Close,DealProcess.C700020032 as Flag15ToAuditing,DealProcess.C700020033 as Flag20SideBySide");
		strReSql.append(" ,DealProcess.C700020046 as Flag22IsSelect,DealProcess.C700020034 as Flag30AuditingResult,DealProcess.C700020035 as Flag31IsTransfer,DealProcess.C700020036 as TransferPhaseNo,DealProcess.C700020043 as ProcessType ");
		
		strReSql.append(" ,DealProcess.C700020047 as Commissioner,DealProcess.C700020048 as CommissionerID");
		strReSql.append(" ,DealProcess.C700020050 as CloseBaseSamenessGroup,DealProcess.C700020051 as CloseBaseSamenessGroupID");
		strReSql.append(" ,DealProcess.C700020049 as IsGroupSnatch");
		strReSql.append(" ,DealProcess.C700020052 as Flag32IsToTransfer,DealProcess.C700020053 as Flag33IsEndPhase ");
		
		strReSql.append(" from "+strTblname+" DealProcess ");		
		strReSql.append(" where 1=1 ");
		strReSql.append(p_ParDealProcess.GetWhereSql(""));
		return(strReSql.toString());
	}	
	
	/**
	 * 静态的得到某类工单处理环节列表方法，返回一个List（DealProcess对象的数组）
	 * @param str_ProcessBaseID
	 * @param str_ProcessBaseSchema
	 * @return
	 */
	public  List GetList(String str_ProcessBaseID,String str_ProcessBaseSchema,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseID(str_ProcessBaseID);
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;	
	}
	
	/**
	 * 静态的得到某类工单某个处理环节所有记录列表方法，返回一个List（DealProcess对象的数组）
	 * @param str_ProcessBaseID
	 * @param str_ProcessBaseSchema
	 * @param str_PhaseNo
	 * @return
	 */
	public  List GetList(String str_ProcessBaseID , String str_ProcessBaseSchema , String str_PhaseNo,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseID(str_ProcessBaseID);
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setPhaseNo(str_PhaseNo);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到所有工单所有待阅环节列表方法，返回一个List（DealProcess对象的数组）。	 */
	public  List GetListWaitConfirm(String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		//m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到某类工单所有待阅环节列表方法，返回一个List（DealProcess对象的数组）。	 */
	public  List GetListWaitConfirm(String str_ProcessBaseSchema , String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitConfirm);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 *描述：静态的得到所有工单所有待办环节列表方法，返回一个List（DealProcess对象的数组）。	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitDeal(String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		//m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitDeal);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到某类工单所有待办环节列表方法，返回一个List（DealProcess对象的数组）。	 * @param str_ProcessBaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListWaitDeal(String str_ProcessBaseSchema , String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessWaitDeal);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到所有工单所有处理中环节列表方法，返回一个List（DealProcess对象的数组）
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListDealing(String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}	
	/**
	 * 描述：静态的得到某类工单所有处理中环节列表方法，返回一个List（DealProcess对象的数组）。	 * @param str_ProcessBaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListDealing(String str_ProcessBaseSchema , String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessDealing);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}	
	/**
	 * 描述：静态的得到所有工单所有我建立的环节（不包含复制品）列表方法，返回一个List（DealProcess对象的数组）。	 * 查询由我操作产生的建单环节	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListMyCreate(String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		//m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyCreate);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	/**
	 * 描述：静态的得到某类工单所有我建立的环节（不包含复制品）列表方法，返回一个List（DealProcess对象的数组）。	 * 查询由我操作产生的建单环节	 * @param str_ProcessBaseSchema
	 * @param str_UserLoginName
	 * @return
	 */
	public  List GetListMyCreate(String str_ProcessBaseSchema , String str_UserLoginName,int p_PageNumber,int p_StepRow) throws Exception
	{
		List m_Relist;
		ParDealProcess m_ParDealProcess=new ParDealProcess();
		
		m_ParDealProcess.setProcessBaseSchema(str_ProcessBaseSchema);
		m_ParDealProcess.setTasekPersonID(str_UserLoginName);
		m_ParDealProcess.setProcessOptionalType(Constants.ProcessMyCreate);
		m_Relist=GetList(m_ParDealProcess,p_PageNumber,p_StepRow);
		return m_Relist;
	}
	
	/**
	 * 根据条件查询处理信息，并返回处理信息(DealProcess)对象的List
	 * @param p_ParDealProcess
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		
		String strSql;
		strSql=this.getSelectSql(p_ParDealProcess);
		GetQueryResultRows(strSql);
		
		String strOrder=p_ParDealProcess.getOrderbyFiledNameString();
		if(strOrder.trim().equals(""))
			strOrder=" order by ProcessID";
		//排序
		strSql+=strOrder;		
		
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		try{
			stm=m_dbConsole.GetStatement();
			m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_ObjRs);	
		}catch(Exception ex)
		{
			System.err.println("DealProcess.GetList 方法"+ex.getMessage());
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
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return m_Relist;
	}
	
	/**
	 * 返回本次查询的总行数，用于计算分页时的总页数
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
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_BaseRs.next())
			{
				intQueryResultRows=m_BaseRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			System.err.println("DealProcess.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (m_BaseRs != null)
					m_BaseRs.close();
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
	public String Insert(Map p_hashTable,int IsArchive)
	{  
		ProcessUtil processUtil = new ProcessUtil();
	    String strReturnID = null;
	    String p_strFormName = "";
	    if(IsArchive==0){
	    	p_strFormName = Constants.TblDealProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcess_H;	  	    	
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
	public boolean Update(String strModifyEntryId,Map p_hashTable,int IsArchive)
	{  
		ProcessUtil processUtil = new ProcessUtil();
	    boolean reault = false;
	    String p_strFormName = "";
	    if(IsArchive==0){
	    	p_strFormName = Constants.TblDealProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcess_H;	  	    	
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
	    	p_strFormName = Constants.TblDealProcess;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealProcess_H;	  	    	
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
	    	strFormName = Constants.TblDealProcess;	    	
	    }else
	    {
	    	strFormName = Constants.TblDealProcess_H;	  	    	
	    }		
		try{
			DealProcessModel m_DealProcessModel;
			for(int row=0;row<rowCount;row++)
			{
				m_DealProcessModel=(DealProcessModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_DealProcessModel.getProcessID());
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
	
	
	/*绑定变量部分*/
	
	
	private  List ConvertRsToListForBind( ResultSet p_DealRs) 
	{	
		if(p_DealRs==null) return null;
		List list = new ArrayList();
		try{
			String colName;
			ResultSetMetaData metaData=p_DealRs.getMetaData();
			int cols=metaData.getColumnCount();			
			while (p_DealRs!=null&&p_DealRs.next())
			{
				 DealProcessModel m_DealProcessModel=new DealProcessModel();
				 for(int col=1;col<=cols;col++)
				 {
					 colName=metaData.getColumnName(col);
					 if(FormatString.compareEqualsString(colName,"ProcessID"))
						 m_DealProcessModel.setProcessID(p_DealRs.getString("ProcessID"));
					 if(FormatString.compareEqualsString(colName,"ProcessBaseID"))
						 m_DealProcessModel.setProcessBaseID(p_DealRs.getString("ProcessBaseID"));
					 if(FormatString.compareEqualsString(colName,"ProcessBaseSchema"))
						 m_DealProcessModel.setProcessBaseSchema(p_DealRs.getString("ProcessBaseSchema"));
					 if(FormatString.compareEqualsString(colName,"PhaseNo"))
						 m_DealProcessModel.setPhaseNo(p_DealRs.getString("PhaseNo"));
					 if(FormatString.compareEqualsString(colName,"PrevPhaseNo"))
						 m_DealProcessModel.setPrevPhaseNo(p_DealRs.getString("PrevPhaseNo"));
					 if(FormatString.compareEqualsString(colName,"CreateByUserID"))
						 m_DealProcessModel.setCreateByUserID(p_DealRs.getString("CreateByUserID"));
					 if(FormatString.compareEqualsString(colName,"Assginee"))
						 m_DealProcessModel.setAssginee(p_DealRs.getString("Assginee"));
					 if(FormatString.compareEqualsString(colName,"AssgineeID"))
						 m_DealProcessModel.setAssgineeID(p_DealRs.getString("AssgineeID"));
					 if(FormatString.compareEqualsString(colName,"GroupName"))
						 m_DealProcessModel.setGroup(p_DealRs.getString("GroupName"));
					 if(FormatString.compareEqualsString(colName,"GroupID"))
						 m_DealProcessModel.setGroupID(p_DealRs.getString("GroupID"));
					 if(FormatString.compareEqualsString(colName,"Dealer"))
						 m_DealProcessModel.setDealer(p_DealRs.getString("Dealer"));
					 if(FormatString.compareEqualsString(colName,"DealerID"))
						 m_DealProcessModel.setDealerID(p_DealRs.getString("DealerID"));
					 if(FormatString.compareEqualsString(colName,"ProcessStatus"))
						 m_DealProcessModel.setProcessStatus(p_DealRs.getString("ProcessStatus"));
					 if(FormatString.compareEqualsString(colName,"AssignOverTimeDate"))
						 m_DealProcessModel.setAssignOverTimeDate(p_DealRs.getLong("AssignOverTimeDate"));
					 if(FormatString.compareEqualsString(colName,"AcceptOverTimeDate"))
						 m_DealProcessModel.setAcceptOverTimeDate(p_DealRs.getLong("AcceptOverTimeDate"));
					 if(FormatString.compareEqualsString(colName,"DealOverTimeDate"))
						 m_DealProcessModel.setDealOverTimeDate(p_DealRs.getLong("DealOverTimeDate"));
					 if(FormatString.compareEqualsString(colName,"StDate"))
						 m_DealProcessModel.setStDate(p_DealRs.getLong("StDate"));
					 if(FormatString.compareEqualsString(colName,"BgDate"))
						 m_DealProcessModel.setBgDate(p_DealRs.getLong("BgDate"));
					 if(FormatString.compareEqualsString(colName,"EdDate"))
						 m_DealProcessModel.setEdDate(p_DealRs.getLong("EdDate"));
					 if(FormatString.compareEqualsString(colName,"DescInfo"))
						 m_DealProcessModel.setDesc(p_DealRs.getString("DescInfo"));
					 if(FormatString.compareEqualsString(colName,"FlagType"))
						 m_DealProcessModel.setFlagType(p_DealRs.getString("FlagType"));
					 if(FormatString.compareEqualsString(colName,"FlagActive"))
						 m_DealProcessModel.setFlagActive(p_DealRs.getInt("FlagActive"));
					 if(FormatString.compareEqualsString(colName,"FlagPredefined"))
						 m_DealProcessModel.setFlagPredefined(p_DealRs.getInt("FlagPredefined"));
					 if(FormatString.compareEqualsString(colName,"FlagDuplicated"))
						 m_DealProcessModel.setFlagDuplicated(p_DealRs.getInt("FlagDuplicated"));
					 if(FormatString.compareEqualsString(colName,"Flag01Assign"))
						 m_DealProcessModel.setFlag01Assign(p_DealRs.getString("Flag01Assign"));
					 if(FormatString.compareEqualsString(colName,"Flag02Copy"))
						 m_DealProcessModel.setFlag02Copy(p_DealRs.getString("Flag02Copy"));
					 if(FormatString.compareEqualsString(colName,"Flag03Assist"))
						 m_DealProcessModel.setFlag03Assist(p_DealRs.getString("Flag03Assist"));
					 if(FormatString.compareEqualsString(colName,"Flag04Transfer"))
						 m_DealProcessModel.setFlag04Transfer(p_DealRs.getString("Flag04Transfer"));
					 if(FormatString.compareEqualsString(colName,"Flag05TurnDown"))
						 m_DealProcessModel.setFlag05TurnDown(p_DealRs.getString("Flag05TurnDown"));
					 if(FormatString.compareEqualsString(colName,"Flag06TurnUp"))
						 m_DealProcessModel.setFlag06TurnUp(p_DealRs.getString("Flag06TurnUp"));
					 if(FormatString.compareEqualsString(colName,"Flag07Recall"))
						 m_DealProcessModel.setFlag07Recall(p_DealRs.getString("Flag07Recall"));
					 if(FormatString.compareEqualsString(colName,"Flag08Cancel"))
						 m_DealProcessModel.setFlag08Cancel(p_DealRs.getString("Flag08Cancel"));
					 if(FormatString.compareEqualsString(colName,"Flag09Close"))
						 m_DealProcessModel.setFlag09Close(p_DealRs.getString("Flag09Close"));
					 if(FormatString.compareEqualsString(colName,"Flag15ToAuditing"))
						 m_DealProcessModel.setFlag15ToAuditing(p_DealRs.getString("Flag15ToAuditing"));
					 if(FormatString.compareEqualsString(colName,"Flag20SideBySide"))
						 m_DealProcessModel.setFlag20SideBySide(p_DealRs.getString("Flag20SideBySide"));
					 if(FormatString.compareEqualsString(colName,"Flag22IsSelect"))
						 m_DealProcessModel.setFlag22IsSelect(p_DealRs.getString("Flag22IsSelect"));
					 if(FormatString.compareEqualsString(colName,"Flag30AuditingResult"))
						 m_DealProcessModel.setFlag30AuditingResult(p_DealRs.getString("Flag30AuditingResult"));
					 if(FormatString.compareEqualsString(colName,"Flag31IsTransfer"))
						 m_DealProcessModel.setFlag31IsTransfer(p_DealRs.getString("Flag31IsTransfer"));
					 if(FormatString.compareEqualsString(colName,"TransferPhaseNo"))
						 m_DealProcessModel.setTransferPhaseNo(p_DealRs.getString("TransferPhaseNo"));
					 if(FormatString.compareEqualsString(colName,"ProcessType"))
						 m_DealProcessModel.setProcessType(p_DealRs.getString("ProcessType"));
					 if(FormatString.compareEqualsString(colName,"Commissioner"))
						 m_DealProcessModel.setCommissioner(p_DealRs.getString("Commissioner"));
					 if(FormatString.compareEqualsString(colName,"CommissionerID"))
						 m_DealProcessModel.setCommissionerID(p_DealRs.getString("CommissionerID"));
					 if(FormatString.compareEqualsString(colName,"CloseBaseSamenessGroup"))
						 m_DealProcessModel.setCloseBaseSamenessGroup(p_DealRs.getString("CloseBaseSamenessGroup"));
					 if(FormatString.compareEqualsString(colName,"CloseBaseSamenessGroupID"))
						 m_DealProcessModel.setCloseBaseSamenessGroupID(p_DealRs.getString("CloseBaseSamenessGroupID"));
					 //if(FormatString.compareEqualsString(colName,"IsGroupSnatch"))
						 //m_DealProcessModel.setIsGroupSnatch(p_DealRs.getString("IsGroupSnatch"));
					 if(FormatString.compareEqualsString(colName,"Flag32IsToTransfer"))
						 m_DealProcessModel.setFlag32IsToTransfer(p_DealRs.getString("Flag32IsToTransfer"));
					 if(FormatString.compareEqualsString(colName,"Flag33IsEndPhase"))
						 m_DealProcessModel.setFlag33IsEndPhase(p_DealRs.getString("Flag33IsEndPhase"));
				 }//for(int col=1;col<=cols;col++)
				 list.add(m_DealProcessModel);
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
		stringBuffer.append(tblAlias+"C1 as ProcessID,");
		stringBuffer.append(tblAlias+"C700020001 as ProcessBaseID,");
		stringBuffer.append(tblAlias+"C700020002 as ProcessBaseSchema," );
		stringBuffer.append(tblAlias+"C700020003 as PhaseNo,");
		stringBuffer.append(tblAlias+"C700020004 as PrevPhaseNo,");
		stringBuffer.append(tblAlias+"C700020045 as CreateByUserID,");
		stringBuffer.append(tblAlias+"C700020005 as Assginee,");
		stringBuffer.append(tblAlias+"C700020006 as AssgineeID,");
		stringBuffer.append(tblAlias+"C700020007 as GroupName,");
		stringBuffer.append(tblAlias+"C700020008 as GroupID,");
		stringBuffer.append(tblAlias+"C700020009 as Dealer,");
		stringBuffer.append(tblAlias+"C700020010 as DealerID,");
		stringBuffer.append(tblAlias+"C700020011 as ProcessStatus,");
		stringBuffer.append(tblAlias+"C700020012 as AssignOverTimeDate,");
		stringBuffer.append(tblAlias+"C700020013 as AcceptOverTimeDate,");
		stringBuffer.append(tblAlias+"C700020014 as DealOverTimeDate,");
		stringBuffer.append(tblAlias+"C700020015 as StDate,");
		stringBuffer.append(tblAlias+"C700020016 as BgDate,");
		stringBuffer.append(tblAlias+"C700020017 as EdDate,");
		stringBuffer.append(tblAlias+"C700020018 as DescInfo,");
		stringBuffer.append(tblAlias+"C700020019 as FlagType,");
		stringBuffer.append(tblAlias+"C700020020 as FlagActive,");
		stringBuffer.append(tblAlias+"C700020021 as FlagPredefined,");
		stringBuffer.append(tblAlias+"C700020022 as FlagDuplicated,");
		stringBuffer.append(tblAlias+"C700020023 as Flag01Assign,");
		stringBuffer.append(tblAlias+"C700020024 as Flag02Copy,");
		stringBuffer.append(tblAlias+"C700020025 as Flag03Assist,");
		stringBuffer.append(tblAlias+"C700020026 as Flag04Transfer,");
		stringBuffer.append(tblAlias+"C700020027 as Flag05TurnDown,");
		stringBuffer.append(tblAlias+"C700020028 as Flag06TurnUp,");
		stringBuffer.append(tblAlias+"C700020029 as Flag07Recall,");
		stringBuffer.append(tblAlias+"C700020030 as Flag08Cancel,");
		stringBuffer.append(tblAlias+"C700020031 as Flag09Close,");
		stringBuffer.append(tblAlias+"C700020032 as Flag15ToAuditing,");
		stringBuffer.append(tblAlias+"C700020033 as Flag20SideBySide,");
		stringBuffer.append(tblAlias+"C700020046 as Flag22IsSelect,");
		stringBuffer.append(tblAlias+"C700020034 as Flag30AuditingResult,");
		stringBuffer.append(tblAlias+"C700020035 as Flag31IsTransfer,");
		stringBuffer.append(tblAlias+"C700020036 as TransferPhaseNo,");
		stringBuffer.append(tblAlias+"C700020043 as ProcessType,");
		
		stringBuffer.append(tblAlias+"C700020047 as Commissioner,");
		stringBuffer.append(tblAlias+"C700020048 as CommissionerID,");
		stringBuffer.append(tblAlias+"C700020050 as CloseBaseSamenessGroup,");
		stringBuffer.append(tblAlias+"C700020051 as CloseBaseSamenessGroupID,");
		stringBuffer.append(tblAlias+"C700020049 as IsGroupSnatch,");
		stringBuffer.append(tblAlias+"C700020052 as Flag32IsToTransfer,");
		stringBuffer.append(tblAlias+"C700020053 as Flag33IsEndPhase ");
		return stringBuffer.toString();
		
	}
	
	
	public List getListForBind(ParDealProcess p_ParDealProcess,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;

		
		List m_FiledinfoList=null;
		
		if(p_ParDealProcess!=null)
		{
			m_FiledinfoList=p_ParDealProcess.getContionFiledInfoList();
		}
		else
			p_ParDealProcess=new ParDealProcess();
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
		String strDealTblName="";
		String strDealTblName_h="";
		int isArchive=p_ParDealProcess.getIsArchive();
		if(isArchive==0 || isArchive==1)
		{
			if(isArchive==1)
				strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
			else
				strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
		}
		else
		{
			strDealTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess);
			strDealTblName_h=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealProcess_H);
		}
		
		StringBuffer sqlString = new StringBuffer();
		StringBuffer sqlRowCount = new StringBuffer();
		if(isArchive==0 || isArchive==1)
		{
			
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			if(isArchive==1)
				sqlString.append(" from "+strDealTblName_h+" deal " );
			else
				sqlString.append(" from "+strDealTblName+" deal " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			//排序
			String strOrder=p_ParDealProcess.getOrderbyFiledNameString();
			sqlString.append(strOrder);
			
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			if(isArchive==1)
				sqlRowCount.append(" from "+strDealTblName_h+" deal " );
			else
				sqlRowCount.append(" from "+strDealTblName+" deal " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);			
			
		}
		else
		{
			
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			sqlString.append(" from "+strDealTblName+" deal " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			sqlString.append(" union all ");
			sqlString.append(" select ");
			sqlString.append(selectFiled);
			sqlString.append(" from "+strDealTblName_h+" deal " );
			sqlString.append(" where 1=1 ");
			sqlString.append(whereSql);
			//排序
			String strOrder=p_ParDealProcess.getOrderbyFiledNameString();
			sqlString.append(strOrder);			
			//数量查询sql
			sqlRowCount.append(" select sum(rownums) rownums from (");
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			sqlRowCount.append(" from "+strDealTblName+" deal " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);
			sqlRowCount.append(" union all ");
			sqlRowCount.append(" select ");
			sqlRowCount.append(" count(*) rownums ");
			sqlRowCount.append(" from "+strDealTblName_h+" deal " );
			sqlRowCount.append(" where 1=1 ");
			sqlRowCount.append(whereSql);
			sqlRowCount.append(" )deal ");
			
		}
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=sqlString.toString();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//System.out.println("DealProcess-GetListForBind(ParDealProcess) strSql:"+strSql);
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
			//System.out.println("intQueryResultRows:"+intQueryResultRows);
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
			System.err.println("DealProcess.GetListForBind 方法"+ex.getMessage());
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
