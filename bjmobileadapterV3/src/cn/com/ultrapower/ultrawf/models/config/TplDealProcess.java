package cn.com.ultrapower.ultrawf.models.config;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

import cn.com.ultrapower.ultrawf.share.constants.Constants;


public class TplDealProcess {

	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}	

	public String getWhereSql(ParTplDealProcessModel parTplDealProcessModel)
	{
		StringBuffer sqlWhere= new StringBuffer();
		
		//private  String ProcessID;//
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C1",parTplDealProcessModel.getProcessID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020001",parTplDealProcessModel.getBaseTplID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020002",parTplDealProcessModel.getBaseTplSchema()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020003",parTplDealProcessModel.getPhaseNo()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020004",parTplDealProcessModel.getPrevPhaseNo()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020005",parTplDealProcessModel.getAssginee()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020006",parTplDealProcessModel.getAssgineeID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020007",parTplDealProcessModel.getGroup()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020008",parTplDealProcessModel.getGroupID()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020011",parTplDealProcessModel.getProcessStatus()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020012",parTplDealProcessModel.getAssignOverTimeDate()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020013",parTplDealProcessModel.getAcceptOverTimeDate()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020014",parTplDealProcessModel.getDealOverTimeDate()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020018",parTplDealProcessModel.getDesc()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020019",parTplDealProcessModel.getFlagType()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020020",parTplDealProcessModel.getFlagActive()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020021",parTplDealProcessModel.getFlagPredefined()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020022",parTplDealProcessModel.getFlagDuplicated()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020023",parTplDealProcessModel.getFlag01Assign()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020024",parTplDealProcessModel.getFlag02Copy()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020025",parTplDealProcessModel.getFlag03Assist()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020026",parTplDealProcessModel.getFlag04Transfer()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020027",parTplDealProcessModel.getFlag05TurnDown()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020028",parTplDealProcessModel.getFlag06TurnUp()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020029",parTplDealProcessModel.getFlag07Recall()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020030",parTplDealProcessModel.getFlag08Cancel()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020031",parTplDealProcessModel.getFlag09Close()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020032",parTplDealProcessModel.getFlag15ToAuditing()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020033",parTplDealProcessModel.getFlag20SideBySide()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020035",parTplDealProcessModel.getFlag31IsTransfer()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020036",parTplDealProcessModel.getTransferPhaseNo()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020043",parTplDealProcessModel.getProcessType()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020511",parTplDealProcessModel.getPosX()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C700020512",parTplDealProcessModel.getPosY()));
	
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C650042008",parTplDealProcessModel.getAcceptOverTimeDate_tmp()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C650042009",parTplDealProcessModel.getDealOverTimeDate_tmp()));
		sqlWhere.append(UnionConditionForSql.getStringFiledSql("","C650042010",parTplDealProcessModel.getAssignOverTimeDate_tmp()));
		
		return sqlWhere.toString();
	}
	
	public String getSelectSql(ParTplDealProcessModel parTplDealProcessModel)
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String tblProcessName=Constants.TblTplDealProcess;
		String strTblname=m_RemedyDBOp.GetRemedyTableName(tblProcessName);
		
		StringBuffer sqlBuffer= new StringBuffer();
		sqlBuffer.append("select C1 as ProcessID,C700020001 as BaseTplID,C700020002 as BaseTplSchema");
		sqlBuffer.append(",C700020003 as PhaseNo,C700020004 as PrevPhaseNo,C700020005 as Assginee");
		sqlBuffer.append(",C700020006 as AssgineeID,C700020007 as GroupName,C700020008 as GroupID");
		sqlBuffer.append(",C700020011 as ProcessStatus,C700020012 as AssignOverTimeDate");
		sqlBuffer.append(",C700020013 as AcceptOverTimeDate,C700020014 as DealOverTimeDate");
		sqlBuffer.append(",C700020018 as DisDesc,C700020019 as FlagType,C700020020 as FlagActive");
		sqlBuffer.append(",C700020021 as FlagPredefined,C700020022 as FlagDuplicated,C700020023 as Flag01Assign");
		sqlBuffer.append(",C700020024 as Flag02Copy,C700020025 as Flag03Assist,C700020026 as Flag04Transfer");
		sqlBuffer.append(",C700020027 as Flag05TurnDown,C700020028 as Flag06TurnUp,C700020029 as Flag07Recall");
		sqlBuffer.append(",C700020030 as Flag08Cancel,C700020031 as Flag09Close,C700020032 as Flag15ToAuditing");
		sqlBuffer.append(",C700020033 as Flag20SideBySide,C700020035 as Flag31IsTransfer,C700020036 as TransferPhaseNo");
		sqlBuffer.append(",C700020043 as ProcessType,C700020511 as PosX,C700020512 as PosY ");
		
		sqlBuffer.append(",C650042008 as AcceptOverTimeDate_tmp,C650042009 as DealOverTimeDate_tmp,C650042010 as AssignOverTimeDate_tmp ");
		
		sqlBuffer.append(" from "+strTblname);
		sqlBuffer.append(" where 1=1 ");
		if(parTplDealProcessModel!=null)
			sqlBuffer.append(getWhereSql(parTplDealProcessModel));
		return sqlBuffer.toString();
		
	}
	
	private  List ConvertRsToList( ResultSet objRs) 
	{
		if(objRs==null)
			return null;
		List list=new ArrayList();
		try{
			while (objRs.next())
			{
				TplDealProcessModel tplDealProcess=new TplDealProcessModel();
			
				tplDealProcess .setProcessID(objRs.getString("ProcessID"));
				tplDealProcess.setBaseTplID(objRs.getString("BaseTplID"));
				tplDealProcess.setBaseTplSchema(objRs.getString("BaseTplSchema"));
				tplDealProcess.setPhaseNo(objRs.getString("PhaseNo"));
				tplDealProcess.setPrevPhaseNo(objRs.getString("PrevPhaseNo"));
				tplDealProcess.setAssginee(objRs.getString("Assginee"));
				tplDealProcess.setAssgineeID(objRs.getString("AssgineeID"));
				tplDealProcess.setGroup(objRs.getString("GroupName"));
				tplDealProcess.setGroupID(objRs.getString("GroupID"));
				tplDealProcess.setProcessStatus(objRs.getString("ProcessStatus"));
				tplDealProcess.setAssignOverTimeDate(objRs.getLong("AssignOverTimeDate"));
				tplDealProcess.setAcceptOverTimeDate(objRs.getLong("AcceptOverTimeDate"));
				tplDealProcess.setDealOverTimeDate(objRs.getLong("DealOverTimeDate"));
				tplDealProcess.setDesc(objRs.getString("DisDesc"));
				tplDealProcess.setFlagType(objRs.getInt("FlagType"));
				tplDealProcess.setFlagActive(objRs.getInt("FlagActive"));
				tplDealProcess.setFlagPredefined(objRs.getInt("FlagPredefined"));
				tplDealProcess.setFlagDuplicated(objRs.getInt("FlagDuplicated"));
				tplDealProcess.setFlag01Assign(objRs.getInt("Flag01Assign"));
				tplDealProcess.setFlag02Copy(objRs.getInt("Flag02Copy"));
				tplDealProcess.setFlag03Assist(objRs.getInt("Flag03Assist"));
				tplDealProcess.setFlag04Transfer(objRs.getInt("Flag04Transfer"));
				tplDealProcess.setFlag05TurnDown(objRs.getInt("Flag05TurnDown"));
				tplDealProcess.setFlag06TurnUp(objRs.getInt("Flag06TurnUp"));
				tplDealProcess.setFlag07Recall(objRs.getInt("Flag07Recall"));
				tplDealProcess.setFlag08Cancel(objRs.getInt("Flag08Cancel"));
				tplDealProcess.setFlag09Close(objRs.getInt("Flag09Close"));
				tplDealProcess.setFlag15ToAuditing(objRs.getInt("Flag15ToAuditing"));
				tplDealProcess.setFlag20SideBySide(objRs.getInt("Flag20SideBySide"));
				tplDealProcess.setFlag31IsTransfer(objRs.getInt("Flag31IsTransfer"));
				tplDealProcess.setTransferPhaseNo(objRs.getString("TransferPhaseNo"));
				tplDealProcess.setProcessType(objRs.getString("ProcessType"));
				tplDealProcess.setPosX(objRs.getInt("PosX"));
				tplDealProcess.setPosY(objRs.getInt("PosY"));
				
				tplDealProcess.setAcceptOverTimeDate_tmp(objRs.getLong("AcceptOverTimeDate_tmp"));
				tplDealProcess.setDealOverTimeDate_tmp(objRs.getLong("DealOverTimeDate_tmp"));
				tplDealProcess.setAssignOverTimeDate_tmp(objRs.getLong("AssignOverTimeDate_tmp"));
				
				list.add(tplDealProcess);
			}//while (objRs.next())
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	public TplDealProcessModel getOneForKey(String p_BaseTplID,String p_TplSchema)
	{
		ParTplDealProcessModel parTplDealProcessModel=new ParTplDealProcessModel();
		parTplDealProcessModel.setBaseTplID(p_BaseTplID);
		parTplDealProcessModel.setBaseTplSchema(p_TplSchema);
		List list=getList(parTplDealProcessModel,1,1);
		
		int rowCount=list.size();
		if(rowCount>0)
		{
			return (TplDealProcessModel)list.get(0);
		}
		else
			return null;
		
		
	}
	public List getList(ParTplDealProcessModel parTplDealProcessModel,int p_PageNumber,int p_StepRow)
	{
		List m_Relist=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		
		String strSql;
		strSql=this.getSelectSql(parTplDealProcessModel);
		GetQueryResultRows(strSql);
		String strOrder="";
		if(parTplDealProcessModel!=null)
		   strOrder=parTplDealProcessModel.getOrderbyFiledNameString();
		//if(strOrder.trim().equals(""))
		//	strOrder=" order by ProcessID";
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
			System.err.println("TplDealProcess.GetQueryResultRows 方法："+ex.getMessage());
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

}
