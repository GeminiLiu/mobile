package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class AuditingProcessLink {

	private int intQueryResultRows=0;
	
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	public AuditingProcessLink(){}
	
	/**
	 *根据关键字LinkId查询审批环节流转信息，并返回一个审批环节流转信息(AuditingProcessLinkModel)对象 
	 * @param str_LinkID
	 */
	public AuditingProcessLinkModel GetOneForKey(String str_LinkID,int p_IsArchive)
	{
		StringBuffer sqlString = new StringBuffer();
		AuditingProcessLinkModel m_AuditingProcessLink=new AuditingProcessLinkModel();
		ParAuditingProcessLinkModel p_ParAuditingProcessLinkModel=new ParAuditingProcessLinkModel();
		p_ParAuditingProcessLinkModel.setIsArchive(p_IsArchive);
		p_ParAuditingProcessLinkModel.setLinkID(str_LinkID);
		sqlString.append(getSelectSql(p_ParAuditingProcessLinkModel));
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try
		{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if (objRs!=null && objRs.next()) 
			{
				m_AuditingProcessLink.setLinkID(objRs.getString("LinkID"));
				m_AuditingProcessLink.setLinkBaseID(objRs.getString("LinkBaseID"));
				m_AuditingProcessLink.setLinkBaseSchema(objRs.getString("LinkBaseSchema"));
				m_AuditingProcessLink.setStartPhase(objRs.getString("StartPhase"));
				m_AuditingProcessLink.setEndPhase(objRs.getString("EndPhase"));
				m_AuditingProcessLink.setDesc(objRs.getString("Desc"));
				m_AuditingProcessLink.setFlag00IsAvail(objRs.getInt("Flag00IsAvail"));
				m_AuditingProcessLink.setFlag21Required(objRs.getInt("Flag21Required"));
				m_AuditingProcessLink.setAuditingWayIsActive(objRs.getInt("AuditingWayIsActive"));
				m_AuditingProcessLink.setAuditingWayNo(objRs.getString("AuditingWayNo"));
				m_AuditingProcessLink.setLinkType(objRs.getString("LinkType"));				
			}
		}catch(Exception ex)
		{
			System.err.println("AuditingProcessLink.SetAuditingProcessInfo方法："+ex.getMessage());
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
		return m_AuditingProcessLink;
	}	
	
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	private  String getSelectSql(ParAuditingProcessLinkModel p_ParAuditingProcessLinkModel)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParAuditingProcessLinkModel==null)
			p_ParAuditingProcessLinkModel=new ParAuditingProcessLinkModel();
		if(p_ParAuditingProcessLinkModel.getIsArchive()==0||p_ParAuditingProcessLinkModel.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLinkModel));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_ParAuditingProcessLinkModel.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLinkModel));
			sqlString.append(" union ");
//			生成当前历史信息的sql
			p_ParAuditingProcessLinkModel.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_ParAuditingProcessLinkModel));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return(sqlString.toString());
	}		
	/**
	 * 返回查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParAuditingProcessLinkModel p_ParAuditingProcessLinkModel)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblname="";
		if(p_ParAuditingProcessLinkModel.getIsArchive()==1)
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink_H);
		else
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblAuditingLink);
		strReSql.append("SELECT ");
		strReSql.append(" C1 as LinkID,C700020501 as LinkBaseID,C700020502 as LinkBaseSchema,C700020503 as StartPhase");
		strReSql.append(",C700020504 as EndPhase,C700020505 as DescInfo,C700020506 as Flag00IsAvail,C700020507 as Flag21Required");
		strReSql.append(",C700020508 as AuditingWayIsActive,C700020509 as AuditingWayNo,C700020044 as LinkType");
		strReSql.append(" from "+strTblname+" AuditingProcessLink");
		strReSql.append(" where 1=1 ");
		strReSql.append(p_ParAuditingProcessLinkModel.GetWhereSql());		
		return(strReSql.toString());
	}
	
	/**
	 * 根据记录集返回列表	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private List ConvertRsToList(ResultSet p_AuditingLinkRs)  
	{
		List list = new ArrayList();
		try{
			while (p_AuditingLinkRs!=null && p_AuditingLinkRs.next())
			{
				 AuditingProcessLinkModel m_AuditingProcessLink=new AuditingProcessLinkModel();
				 m_AuditingProcessLink.setLinkID(p_AuditingLinkRs.getString("LinkID"));
				 m_AuditingProcessLink.setLinkBaseID(p_AuditingLinkRs.getString("LinkBaseID"));
				 m_AuditingProcessLink.setLinkBaseSchema(p_AuditingLinkRs.getString("LinkBaseSchema"));
				 m_AuditingProcessLink.setStartPhase(p_AuditingLinkRs.getString("StartPhase"));
				 m_AuditingProcessLink.setEndPhase(p_AuditingLinkRs.getString("EndPhase"));
				 m_AuditingProcessLink.setDesc(p_AuditingLinkRs.getString("DescInfo"));
				 m_AuditingProcessLink.setFlag00IsAvail(p_AuditingLinkRs.getInt("Flag00IsAvail"));
				 m_AuditingProcessLink.setFlag21Required(p_AuditingLinkRs.getInt("Flag21Required"));
				 m_AuditingProcessLink.setAuditingWayIsActive(p_AuditingLinkRs.getInt("AuditingWayIsActive"));
				 m_AuditingProcessLink.setAuditingWayNo(p_AuditingLinkRs.getString("AuditingWayNo"));
				 m_AuditingProcessLink.setLinkType(p_AuditingLinkRs.getString("LinkType"));			 
				 list.add(m_AuditingProcessLink);
			}
			p_AuditingLinkRs.close();
		}catch(Exception ex)
		{
			System.err.println(ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}		
	
	/**
	 * 描述：静态的得到某类工单已某个处理环节为头处理环节流转列表方法，返回一个List（DealProcessLink对象的数组）
	 * @param str_LinkBaseID
	 * @param str_LinkBaseSchema
	 * @param str_StartPhase
	 * @return
	 */
	public  List GetListForStartPhase(String str_LinkBaseID,
			String str_LinkBaseSchema, String str_StartPhase, int p_PageNumber,
			int p_StepRow)
	{
		List m_list ;
		ParAuditingProcessLinkModel p_AuditingProcessLink=new ParAuditingProcessLinkModel();
		p_AuditingProcessLink.setLinkBaseID(str_LinkBaseID);
		p_AuditingProcessLink.setLinkBaseSchema(str_LinkBaseSchema);
		p_AuditingProcessLink.setStartPhase(str_StartPhase);
		m_list=GetList(p_AuditingProcessLink,p_PageNumber,p_StepRow);
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
			int p_StepRow)
	{
		List m_list ;
		ParAuditingProcessLinkModel p_AuditingProcessLink=new ParAuditingProcessLinkModel();
		p_AuditingProcessLink.setLinkBaseID(str_LinkBaseID);
		p_AuditingProcessLink.setLinkBaseSchema(str_LinkBaseSchema);
		p_AuditingProcessLink.setEndPhase(str_EndPhase);
		m_list=GetList(p_AuditingProcessLink,p_PageNumber,p_StepRow);
		return m_list;
	}	

	/**
	 * 根据查询条件查询审批环节流转日志，返回审批环节流转对象(AuditingProcessLinkModel)列表
	 * @param p_AuditingProcessLink
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List GetList(ParAuditingProcessLinkModel p_AuditingProcessLink,int p_PageNumber, int p_StepRow)
	{
		List m_List=new ArrayList();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(getSelectSql(p_AuditingProcessLink));

		String strSql=sqlString.toString();
		GetQueryResultRows(strSql);
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		try {
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strSql);
			m_List= ConvertRsToList(objRs);
		} catch (Exception ex) {
			System.err.println("AuditingProcessLink.GetList 方法:"+ex.getMessage());
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
		ResultSet m_BaseRs = null;
		Statement stm=null;
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
				System.err.println("AuditingProcessLink.GetQueryResultRows 方法："+ex.getMessage());
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
	    	p_strFormName = Constants.TblAuditingLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingLink_H;	  	    	
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
	    	p_strFormName = Constants.TblAuditingLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingLink_H;	  	    	
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
	    	p_strFormName = Constants.TblAuditingLink;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblAuditingLink_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
	
	public boolean delete(int isArchive,ParAuditingProcessLinkModel p_AuditingProcessLink)
	{
		List m_list=this.GetList(p_AuditingProcessLink,0,0);
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
	    	strFormName = Constants.TblAuditingLink;	    	
	    }else
	    {
	    	strFormName = Constants.TblAuditingLink_H;	  	    	
	    }		
		try{
			AuditingProcessLinkModel m_AuditingProcessLinkModel;
			for(int row=0;row<rowCount;row++)
			{
				m_AuditingProcessLinkModel=(AuditingProcessLinkModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_AuditingProcessLinkModel.getLinkID());
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
