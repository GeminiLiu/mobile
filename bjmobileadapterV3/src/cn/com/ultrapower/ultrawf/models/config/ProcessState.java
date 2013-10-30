package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
public class ProcessState {
    
	public ProcessState(){}
	
	public ProcessState(String str_ProcessStateName)
	{
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(GetSelectSql());
		strSelect.append(" where 1=1 ");
		//650000001	ProcessStateName			流程状态名称
		strSelect.append(" and C650000001='"+str_ProcessStateName+"'");
		SetProcessStateInfo(strSelect.toString());	
	}
	
	private void SetProcessStateInfo(String strSql)
	{
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		if(strSql.equals(""))
			return;
		Statement stm=null;
		ResultSet objRs =null;
		try
		{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSql);
			if (objRs.next()) 
			{
				ProcessStateModel m_ProcessState=new ProcessStateModel();
				m_ProcessState.SetProcessStateID(objRs.getString("ProcessStateID"));
				m_ProcessState.SetSubmitter(objRs.getString("Submitter"));
				m_ProcessState.SetCreateDate(objRs.getLong("CreateDate"));
				m_ProcessState.SetAssignedTo(objRs.getString("AssignedTo"));
				m_ProcessState.SetLastModifiedBy(objRs.getString("LastModifiedBy"));
				m_ProcessState.SetModifiedDate(objRs.getLong("ModifiedDate"));
				m_ProcessState.SetStatus(objRs.getInt("Status"));
				m_ProcessState.SetShortDescription(objRs.getString("ShortDescription"));
				m_ProcessState.SetProcessStateName(objRs.getString("ProcessStateName"));
				m_ProcessState.SetProcessStateDesc(objRs.getString("ProcessStateDesc"));				
			}
		}catch(Exception ex)
		{
			System.err.println("ProcessState.GetRsConvertList 方法："+ex.getMessage());
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
	}		
	
	/**
	 * 返回查询语句
	 * @return
	 */
	private  String GetSelectSql()
	{
		StringBuffer strSelect = new StringBuffer();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblProcessState);		
		
		strSelect.append("SELECT ");
		strSelect.append("C1 as ProcessStateID,C2 as Submitter,C3 as CreateDate,C4 as AssignedTo");
		strSelect.append(",C5 as LastModifiedBy,C6 as ModifiedDate,C7 as Status,C8 as ShortDescription");
		strSelect.append(",C650000001 as ProcessStateName,C650000002 as ProcessStateDesc ");
		strSelect.append(" from "+strTblName+" ProcessState ");
		return strSelect.toString();
	}	
	
	/**
	 * 
	 * @param p_BaseStateRs
	 * @return
	 * @throws Exception
	 */
	private  List GetRsConvertList(ResultSet p_ProcessStateRs) throws Exception
	{
		List m_list = new ArrayList();
		try{
			while (p_ProcessStateRs.next())
			{
				ProcessStateModel m_ProcessState=new ProcessStateModel();
				m_ProcessState.SetProcessStateID(p_ProcessStateRs.getString("ProcessStateID"));
				m_ProcessState.SetSubmitter(p_ProcessStateRs.getString("Submitter"));
				m_ProcessState.SetCreateDate(p_ProcessStateRs.getLong("CreateDate"));
				m_ProcessState.SetAssignedTo(p_ProcessStateRs.getString("AssignedTo"));
				m_ProcessState.SetLastModifiedBy(p_ProcessStateRs.getString("LastModifiedBy"));
				m_ProcessState.SetModifiedDate(p_ProcessStateRs.getLong("ModifiedDate"));
				m_ProcessState.SetStatus(p_ProcessStateRs.getInt("Status"));
				m_ProcessState.SetShortDescription(p_ProcessStateRs.getString("ShortDescription"));
				m_ProcessState.SetProcessStateName(p_ProcessStateRs.getString("ProcessStateName"));
				m_ProcessState.SetProcessStateDesc(p_ProcessStateRs.getString("ProcessStateDesc"));				
				m_list.add(m_ProcessState);
			}
		}
		catch(Exception ex)
		{
			System.err.println("ProcessState.GetRsConvertList 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		
		return m_list;
	}	
	
	/**
	 * 静态的得到工单状态列表方法，返回一个List（BaseState对象的数组）
	 * @return
	 */
	public  List GetList()
	{
		List m_list = new ArrayList();
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(GetSelectSql());
		strSelect.append(" where 1=1 ");
		Statement stm=null;
		ResultSet objRs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			m_list=GetRsConvertList(objRs);
		}catch(Exception ex)
		{
			System.err.println("ProcessState.GetList 方法："+ex.getMessage());
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
		return m_list;
	}	
	
}
