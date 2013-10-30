package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.constants.*;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class BaseState {

//	属性设置区域--End--
	
	public BaseState()
	{}

	public BaseStateModel GetOneModel(String BaseStateID)
	{
		BaseStateModel m_BaseStateModel = null;
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(GetSelectSql());
		strSelect.append(" where 1=1 ");
		strSelect.append(" and c1='"+BaseStateID+"'");
		Statement stm=null;
		ResultSet objRs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			m_BaseStateModel=SetBaseStateInfo(objRs);
		}catch(Exception ex)
		{
			System.err.println("BaseState.GetList 方法："+ex.getMessage());
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
		return m_BaseStateModel;
	}
	
	private BaseStateModel SetBaseStateInfo(ResultSet p_objRs)
	{
		BaseStateModel m_BaseState=new BaseStateModel();
		try
		{
			if (p_objRs.next()) 
			{
			m_BaseState.SetBaseStateID(p_objRs.getString("BaseStateID"));
			m_BaseState.SetSubmitter(p_objRs.getString("Submitter"));
			m_BaseState.SetCreateDate(p_objRs.getLong("CreateDate"));
			m_BaseState.SetAssignedTo(p_objRs.getString("AssignedTo"));
			m_BaseState.SetLastModifiedBy(p_objRs.getString("LastModifiedBy"));
			m_BaseState.SetModifiedDate(p_objRs.getLong("ModifiedDate"));
			m_BaseState.SetStatus(p_objRs.getInt("Status"));
			m_BaseState.SetShortDescription(p_objRs.getString("ShortDescription"));
			m_BaseState.SetBaseStateName(p_objRs.getString("BaseStateName"));
			m_BaseState.SetBaseStateDesc(p_objRs.getString("BaseStateDesc"));			
			}
			p_objRs.close();
		}catch(Exception ex)
		{
			System.err.println("BaseState.GetRsConvertList 方法："+ex.getMessage());
			ex.printStackTrace();			
		}
		return m_BaseState;
	}	
	
	/**
	 * 返回查询语句
	 * @return
	 */
	private  String GetSelectSql()
	{
		StringBuffer strSelect = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseState);
		strSelect.append("SELECT ");
		strSelect.append("C1 as BaseStateID,C2 as Submitter,C3 as CreateDate,C4 as AssignedTo");
		strSelect.append(",C5 as LastModifiedBy,C6 as ModifiedDate,C7 as Status,C8 as ShortDescription");
		strSelect.append(",C650000001 as BaseStateName,C650000002 as BaseStateDesc ");
		strSelect.append(" from "+strTblName+" BaseState ");
		return strSelect.toString();
	}
	
	/**
	 * @param p_BaseStateRs
	 * @return
	 * @throws Exception
	 */
	public  List GetRsConvertList(ResultSet p_BaseStateRs) throws Exception
	{
		List m_list = new ArrayList();
		try{
			while (p_BaseStateRs.next())
			{
				 BaseStateModel m_BaseModel=new BaseStateModel();
				 m_BaseModel.SetBaseStateID(p_BaseStateRs.getString("BaseStateID"));
				 //System.out.println("baseid::;"+m_BaseModel.GetBaseStateID());
				 m_BaseModel.SetSubmitter(p_BaseStateRs.getString("Submitter"));
				 m_BaseModel.SetCreateDate(p_BaseStateRs.getLong("CreateDate"));
				 m_BaseModel.SetAssignedTo(p_BaseStateRs.getString("AssignedTo"));
				 m_BaseModel.SetLastModifiedBy(p_BaseStateRs.getString("LastModifiedBy"));
				 m_BaseModel.SetModifiedDate(p_BaseStateRs.getLong("ModifiedDate"));
				 m_BaseModel.SetStatus(p_BaseStateRs.getInt("Status"));
				 m_BaseModel.SetShortDescription(p_BaseStateRs.getString("ShortDescription"));
				 m_BaseModel.SetBaseStateName(p_BaseStateRs.getString("BaseStateName"));
				 m_BaseModel.SetBaseStateDesc(p_BaseStateRs.getString("BaseStateDesc"));				
				 m_list.add(m_BaseModel);
			}//while (p_BaseStateRs.next())
			p_BaseStateRs.close();
		}
		catch(Exception ex)
		{
			System.err.println("BaseState.GetRsConvertList 方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		
		return m_list;
	}
	
	/**
	 * 描述：静态的得到工单状态列表方法，返回一个List（BaseState对象的数组）。
	 */
	public  List GetList()
	{
		List m_list = new ArrayList();
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(GetSelectSql());
		strSelect.append(" where 1=1 ");
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			m_list=GetRsConvertList(objRs);
		}catch(Exception ex)
		{
			System.err.println("BaseState.GetList 方法："+ex.getMessage());
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

