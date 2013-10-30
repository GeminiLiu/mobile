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

public class BaseFieldModifyLog {

	private int intQueryResultRows=0;
	//返回查询的总行数行数

	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	public BaseFieldModifyLog(){}

	/**
	 * 根据关键字ProcessLogID查询处理环节日志信息，并返回一个处理环节日志信息(DealProcessLogModel)对象
	 * @param str_LinkID
	 */
	public BaseFieldModifyLogModel getOneForKey(String strField_ModifyLogID ,int p_IsArchive)
	{
		if(strField_ModifyLogID==null||strField_ModifyLogID.trim().equals(""))
			return null;
		StringBuffer sqlString = new StringBuffer();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		
		BaseFieldModifyLogModel m_BaseFieldModifyLogModel=null;
		
		ParBaseFieldModifyLogModel m_ParBaseFieldModifyLogModel=new ParBaseFieldModifyLogModel();
		m_ParBaseFieldModifyLogModel.setBase_Field_ModifyLog_ID(strField_ModifyLogID);
		m_ParBaseFieldModifyLogModel.setIsArchive(p_IsArchive);
		
		sqlString.append(getSelectSql(m_ParBaseFieldModifyLogModel));
		Statement stm=null;
		ResultSet objRs =null;
		try
		{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if (objRs.next()) 
			{	
				m_BaseFieldModifyLogModel=new BaseFieldModifyLogModel();
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ID(objRs.getString("Field_ModifyLog_ID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_BaseID(objRs.getString("Field_ModifyLog_BaseID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_BaseSchema(objRs.getString("Field_ModifyLog_BaseSchema"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessID(objRs.getString("Field_ModifyLog_ProcessID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessType(objRs.getString("Field_ModifyLog_ProcessType"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_field_ID(objRs.getString("Field_ModifyLog_field_ID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_FieldDBName(objRs.getString("Field_ModifyLog_FieldDBName"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Base_field_ShowName(objRs.getString("Field_ModifyLog_field_ShowName"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Date(objRs.getLong("Field_ModifyLog_Date"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Dealer(objRs.getString("Field_ModifyLog_Dealer"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_DealerID(objRs.getString("Field_ModifyLog_DealerID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_OldValue(objRs.getString("Field_ModifyLog_OldValue"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_NewValue(objRs.getString("Field_ModifyLog_NewValue"));
			}
		}catch(Exception ex)
		{
			System.err.println("BaseFieldModifyLog.SetDealProcessLogInfo方法："+ex.getMessage());
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
		return m_BaseFieldModifyLogModel;
	}		
	
	
	/**
	 * 返回处理环节查询的SQL
	 * @return
	 */
	private  String getSelectSql(ParBaseFieldModifyLogModel p_ParBaseFieldModifyLogModel)
	{
		StringBuffer sqlString = new StringBuffer();
		if(p_ParBaseFieldModifyLogModel==null)
			p_ParBaseFieldModifyLogModel=new ParBaseFieldModifyLogModel();
		if(p_ParBaseFieldModifyLogModel.getIsArchive()==0||p_ParBaseFieldModifyLogModel.getIsArchive()==1)
		{
			sqlString.append(getSelectSqlExt(p_ParBaseFieldModifyLogModel));
		}
		else 
		{
			sqlString.append(" select * from (");
			//生成当前活动信息的sql
			p_ParBaseFieldModifyLogModel.setIsArchive(0);			
			sqlString.append(getSelectSqlExt(p_ParBaseFieldModifyLogModel));
			sqlString.append(" union ");
//			生成当前历史信息的sql
			p_ParBaseFieldModifyLogModel.setIsArchive(1);
			sqlString.append(getSelectSqlExt(p_ParBaseFieldModifyLogModel));
			sqlString.append(") ResultTbl where 1=1 ");
		}
		return(sqlString.toString());
	}		
	
	/**
	 * 返回查询的SQL
	 * @return
	 */
	private  String getSelectSqlExt(ParBaseFieldModifyLogModel p_ParBaseFieldModifyLogModel)
	{
		StringBuffer strReSql = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblname="";
		if(p_ParBaseFieldModifyLogModel.getIsArchive()==1)
		{
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseFieldModifyLog_H);
			//strTblname="t217";
		}
		else
		{
			strTblname=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseFieldModifyLog);
			//strTblname="t216";
		}
		
		strReSql.append("SELECT ");
		strReSql.append(" C1 as Field_ModifyLog_ID,C700021001 as Field_ModifyLog_BaseID");
		strReSql.append(",C700021002 as Field_ModifyLog_BaseSchema,C700021013 as Field_ModifyLog_ProcessID");
		strReSql.append(",C700021011 as Field_ModifyLog_ProcessType,C700021004 as Field_ModifyLog_field_ID");
		strReSql.append(",C700021006 as Field_ModifyLog_FieldDBName,C700021012 as Field_ModifyLog_field_ShowName ");
		strReSql.append(",C700021005 as Field_ModifyLog_Date,C700021008 as Field_ModifyLog_Dealer");
		strReSql.append(",C700021007 as Field_ModifyLog_DealerID,C700021009 as Field_ModifyLog_OldValue");
		strReSql.append(",C700021010 as Field_ModifyLog_NewValue ");
		strReSql.append(" from "+strTblname+" ");	
		strReSql.append(" where 1=1 ");
		if(p_ParBaseFieldModifyLogModel!=null)
			strReSql.append(p_ParBaseFieldModifyLogModel.getWhereSql());
		return(strReSql.toString());
	}	
	
	/**
	 * 根据记录集返回列表
	 * @param p_BaseRs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList(ResultSet p_ObjRs) 
	{
		if(p_ObjRs==null) return null;
		BaseFieldModifyLogModel m_BaseFieldModifyLogModel;
		List list = new ArrayList();
		try{
			while (p_ObjRs.next())
			{
				m_BaseFieldModifyLogModel=new BaseFieldModifyLogModel();
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ID(p_ObjRs.getString("Field_ModifyLog_ID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_BaseID(p_ObjRs.getString("Field_ModifyLog_BaseID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_BaseSchema(p_ObjRs.getString("Field_ModifyLog_BaseSchema"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessID(p_ObjRs.getString("Field_ModifyLog_ProcessID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_ProcessType(p_ObjRs.getString("Field_ModifyLog_ProcessType"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_field_ID(p_ObjRs.getString("Field_ModifyLog_field_ID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_FieldDBName(p_ObjRs.getString("Field_ModifyLog_FieldDBName"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Base_field_ShowName(p_ObjRs.getString("Field_ModifyLog_field_ShowName"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Date(p_ObjRs.getLong("Field_ModifyLog_Date"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_Dealer(p_ObjRs.getString("Field_ModifyLog_Dealer"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_DealerID(p_ObjRs.getString("Field_ModifyLog_DealerID"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_OldValue(p_ObjRs.getString("Field_ModifyLog_OldValue"));
				m_BaseFieldModifyLogModel.setBase_Field_ModifyLog_NewValue(p_ObjRs.getString("Field_ModifyLog_NewValue"));
				list.add(m_BaseFieldModifyLogModel);
			}
			p_ObjRs.close();
		}catch(Exception ex)
		{
			System.err.println("BaseFieldModifyLog.ConvertRsToList"+ex.getMessage());
			ex.printStackTrace();			
		}
		
		return list;
	}		
	
	/**
	 * 根据条件查询处理日志信息，并返回处理日志信息(DealProcessLogModel)实例对象列表
	 * @param p_DealProcessLog
	 * @param p_PageNumber
	 * @param p_StepRow
	 * @return
	 */
	public List getList(ParBaseFieldModifyLogModel p_ParBaseFieldModifyLogModel,int p_PageNumber, int p_StepRow)
	{
		List m_List=new ArrayList();
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(getSelectSql(p_ParBaseFieldModifyLogModel));
		
		String strSql=sqlString.toString();
		GetQueryResultRows(strSql);
		
		String strOrder=p_ParBaseFieldModifyLogModel.getOrderbyFiledNameString();
		if(!strOrder.trim().equals(""))
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
			System.err.println("BaseFieldModifyLog.GetList 方法:"+ex.getMessage());
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
			System.err.println("BaseFieldModifyLog.GetQueryResultRows 方法："+ex.getMessage());
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
	    	p_strFormName = Constants.TblBaseFieldModifyLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblBaseFieldModifyLog_H;	  	    	
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
	    	p_strFormName = Constants.TblBaseFieldModifyLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblBaseFieldModifyLog_H;	  	    	
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
	    	p_strFormName = Constants.TblBaseFieldModifyLog;	    	
	    }else
	    {
	    	p_strFormName = Constants.TblBaseFieldModifyLog_H;	  	    	
	    }
	    reault = processUtil.Delete(p_strFormName,strDeleteEntryId);
		return reault;
	}
}
