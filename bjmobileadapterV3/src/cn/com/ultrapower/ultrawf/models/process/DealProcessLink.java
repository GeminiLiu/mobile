package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

//import cn.com.ultrapower.ultrawf.share.FormatTime;
//import cn.com.ultrapower.ultrawf.share.FormatTime;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class DealProcessLink {

	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	public DealProcessLink(){}
	
	/**
	 * 根据关键字LinkId查询处理环节流转信息，并返回一个处理环节流转信息(DealProcessLinkModel)对象
	 * @param str_LinkID
	 */
	public DealProcessLinkModel GetOneForKey(String str_LinkID,int p_IsArchive)
	{
		String sqlString = "";
		if(str_LinkID==null || str_LinkID.trim().equals(""))
			return null;
		DealProcessLinkModel m_DealProcessLinkModel=new DealProcessLinkModel();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		ParDealProcessLinkModel m_DealProcessLink=new ParDealProcessLinkModel();
		m_DealProcessLink.setLinkID(str_LinkID);
		m_DealProcessLink.setIsArchive(p_IsArchive);
		
		sqlString=getSelectSql(m_DealProcessLink);
		Statement stm=null;
		ResultSet objRs =null;
		try
		{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString);
			if (objRs.next()) 
			{
				m_DealProcessLinkModel.setLinkID(objRs.getString("LinkID"));
				m_DealProcessLinkModel.setLinkBaseID(objRs.getString("LinkBaseID"));
				m_DealProcessLinkModel.setLinkBaseSchema(objRs.getString("LinkBaseSchema"));
				m_DealProcessLinkModel.setStartPhase(objRs.getString("StartPhase"));
				m_DealProcessLinkModel.setEndPhase(objRs.getString("EndPhase"));
				m_DealProcessLinkModel.setDesc(objRs.getString("DescInfo"));
				m_DealProcessLinkModel.setFlag00IsAvail(objRs.getInt("Flag00IsAvail"));
				m_DealProcessLinkModel.setFlag21Required(objRs.getInt("Flag21Required"));
				m_DealProcessLinkModel.setLinkType(objRs.getString("LinkType"));				
			}
		}catch(Exception ex)
		{
			//m_dbConsole.closeConn();
			System.err.println("DealProcessLink.SetDealProcessInfo 方法："+ex.getMessage());
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
		return m_DealProcessLinkModel;
	}
	
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	private  String getSelectSql(ParDealProcessLinkModel p_DealProcessLink)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_DealProcessLink==null)
			p_DealProcessLink=new ParDealProcessLinkModel();
		if(p_DealProcessLink.getIsArchive()==0||p_DealProcessLink.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_DealProcessLink));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_DealProcessLink.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_DealProcessLink));
			sqlString.append(" union ");
//			生成当前历史信息的sql
			p_DealProcessLink.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_DealProcessLink));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return(sqlString.toString());
	}		
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParDealProcessLinkModel p_DealProcessLink)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblname="";
		if(p_DealProcessLink.getIsArchive()==1)
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink_H);
		else
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblDealLink);
		
		strReSql.append("SELECT ");
		strReSql.append(" C1 as LinkID,C700020501 as LinkBaseID,C700020502 as LinkBaseSchema,C700020503 as StartPhase");
		strReSql.append(" ,C700020504 as EndPhase,C700020505 as DescInfo,C700020506 as Flag00IsAvail");
		strReSql.append(" ,C700020507 as Flag21Required ,C700020044 as LinkType");	
		strReSql.append(" from "+strTblname+" DealLink ");
		strReSql.append(" where 1=1 ");
		if(p_DealProcessLink!=null)
		strReSql.append(p_DealProcessLink.GetWhereSql());
		return(strReSql.toString());
	}	
	
	/**
	 * 根据记录集返回列表	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList(ResultSet p_DealLinkRs)  
	{
		if(p_DealLinkRs==null) return null;
		List list = new ArrayList();
		try{
			while (p_DealLinkRs.next())
			{
				 DealProcessLinkModel m_DealProcessLink=new DealProcessLinkModel();
				 m_DealProcessLink.setLinkID(p_DealLinkRs.getString("LinkID"));
				 m_DealProcessLink.setLinkBaseID(p_DealLinkRs.getString("LinkBaseID"));
				 m_DealProcessLink.setLinkBaseSchema(p_DealLinkRs.getString("LinkBaseSchema"));
				 m_DealProcessLink.setStartPhase(p_DealLinkRs.getString("StartPhase"));
				 m_DealProcessLink.setEndPhase(p_DealLinkRs.getString("EndPhase"));
				 m_DealProcessLink.setDesc(p_DealLinkRs.getString("Desc"));
				 m_DealProcessLink.setFlag00IsAvail(p_DealLinkRs.getInt("Flag00IsAvail"));
				 m_DealProcessLink.setFlag21Required(p_DealLinkRs.getInt("Flag21Required"));
				 m_DealProcessLink.setLinkType(p_DealLinkRs.getString("LinkType"));				 
				 list.add(m_DealProcessLink);
			}
			p_DealLinkRs.close();
		}catch(Exception ex)
		{
			System.err.println("DealProcessLink.ConvertRsToList 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}	
	/**
	 * 描述：静态的得到某类工单处理环节流转列表方法，返回一个List（DealProcessLink对象的数组）
	 * @param str_LinkBaseID
	 * @param str_LinkBaseSchema
	 * @return
	 * @throws Exception
	 */
	public List GetList(String str_LinkBaseID, String str_LinkBaseSchema,
			int p_PageNumber, int p_StepRow)
	{
		List m_list ;
		ParDealProcessLinkModel p_DealgProcessLink=new ParDealProcessLinkModel();
		p_DealgProcessLink.setLinkBaseID(str_LinkBaseID);
		p_DealgProcessLink.setLinkBaseSchema(str_LinkBaseSchema);
		m_list=GetList(p_DealgProcessLink,p_PageNumber,p_StepRow);
		return m_list;
	}
	/**
	 * 描述：静态的得到某类工单已某个处理环节为头处理环节流转列表方法，返回一个List（DealProcessLink对象的数组）
	 * @param str_LinkBaseID
	 * @param str_LinkBaseSchema
	 * @param str_StartPhase
	 * @return
	 */
	public List GetListForStartPhase(String str_LinkBaseID,
			String str_LinkBaseSchema, String str_StartPhase, int p_PageNumber,
			int p_StepRow)
	{
		List m_list ;
		ParDealProcessLinkModel p_DealgProcessLink=new ParDealProcessLinkModel();
		p_DealgProcessLink.setLinkBaseID(str_LinkBaseID);
		p_DealgProcessLink.setLinkBaseSchema(str_LinkBaseSchema);
		p_DealgProcessLink.setStartPhase(str_StartPhase);
		m_list=GetList(p_DealgProcessLink,p_PageNumber,p_StepRow);
		return m_list;
		
	}
	/**
	 * 描述：静态的得到某类工单已某个处理环节为尾处理环节流转列表方法，返回一个List（DealProcessLink对象的数组）
	 * @param str_LinkBaseID
	 * @param str_LinkBaseSchema
	 * @param str_EndPhase
	 * @return
	 */
	public List GetListForEndPhase(String str_LinkBaseID,
			String str_LinkBaseSchema, String str_EndPhase, int p_PageNumber,
			int p_StepRow) throws Exception
	{
		List m_list ;
		ParDealProcessLinkModel p_DealgProcessLink=new ParDealProcessLinkModel();
		p_DealgProcessLink.setLinkBaseID(str_LinkBaseID);
		p_DealgProcessLink.setLinkBaseSchema(str_LinkBaseSchema);
		p_DealgProcessLink.setEndPhase(str_EndPhase);
		m_list=GetList(p_DealgProcessLink,p_PageNumber,p_StepRow);
		return m_list;
		
	}
	
	
	public List GetList(ParDealProcessLinkModel p_DealProcessLink,int p_PageNumber, int p_StepRow)
	{
		List m_List=new ArrayList();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(getSelectSql(p_DealProcessLink));
		//sqlString.append(" where 1=1 ");
		//sqlString.append(p_DealProcessLink.GetWhereSql());
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
			//m_dbConsole.closeConn();
		} catch (Exception ex) {
			//m_dbConsole.closeConn();
			System.err.println("AuditingProcessLink.GetList 方法:"+ex.getMessage());
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
	 * 返回本次查询的总行数，用于计算分页时的总页数
	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		sqlString.append(" )");
		Statement stm=null;
		ResultSet objRs =null;			
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(objRs.next())
			{
				intQueryResultRows=objRs.getInt("rownums");
			}
			//m_dbConsole.closeConn();
		}catch(Exception ex)
		{
			//m_dbConsole.closeConn();
			System.err.println("Base.GetQueryResultRows 方法："+ex.getMessage());
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
	    	p_strFormName = Constants.TblDealLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealLink_H;	  	    	
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
	    	p_strFormName = Constants.TblDealLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealLink_H;	  	    	
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
	    	p_strFormName = Constants.TblDealLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblDealLink_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
	
	
	public boolean delete(int isArchive,ParDealProcessLinkModel p_DealProcessLink)
	{
		List m_list=this.GetList(p_DealProcessLink,0,0);
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
	    	strFormName = Constants.TblDealLink;	    	
	    }else
	    {
	    	strFormName = Constants.TblDealLink_H;	  	    	
	    }		
		try{
			DealProcessLinkModel m_DealProcessLinkModel;
			for(int row=0;row<rowCount;row++)
			{
				m_DealProcessLinkModel=(DealProcessLinkModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_DealProcessLinkModel.getLinkID());
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
