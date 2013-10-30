package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class Group {
	
	/**
	 * 将查询的Rs记录转换成实体类的List
	 * @param p_ObjRs
	 * @return
	 * @throws Exception
	 */
	private List ConvertRsToList(ResultSet p_ObjRs) 
	{
		List list = new ArrayList();
		try{
			while(p_ObjRs.next())
			{
	
				 GroupModel m_GroupModel=new GroupModel();
				 m_GroupModel.SetGroupID(p_ObjRs.getLong("GroupID"));
				 m_GroupModel.SetGroupName(p_ObjRs.getString("GroupName"));
				 m_GroupModel.SetGroupType(p_ObjRs.getLong("GroupType"));
				 m_GroupModel.SetLongGroupName(p_ObjRs.getString("LongGroupName"));
				 m_GroupModel.SetRequestID(p_ObjRs.getString("RequestID"));
				 list.add(m_GroupModel);
			}
			p_ObjRs.close();
		}catch(Exception ex)
		{
			System.err.println("User.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}
		
	/**
	 * 获取查询的Sql
	 * @param p_ParUserModel
	 * @return
	 */
	private String GetSelectSql(ParGroupModel p_ParGroupModel)
	{	
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblGroup);			
		stringBuffer.append(" select ");
		stringBuffer.append("C1 as RequestID,C8 as LongGroupName,C105 as GroupName");
		stringBuffer.append(",C106 as GroupID,C107 as GroupType ");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ParGroupModel!=null)
			stringBuffer.append(p_ParGroupModel.GetWhereSql());
		return stringBuffer.toString();
	}
	
	public List GetList(int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(null);
		//System.out.println("User.GetList SQL语句："+strSql);
		//GetQueryResultRows(strSql);
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		Statement stm=null;
		ResultSet m_UserRs =null;
		try
		{
			stm=m_dbConsole.GetStatement();
			m_UserRs = m_dbConsole.executeResultSet(stm, strSql);
			m_list=ConvertRsToList(m_UserRs);
		}catch(Exception ex)
		{
			System.err.println("Group.GetList 方法"+ex.getMessage());
			ex.printStackTrace();				
		}
		finally
		{
			try{
				if(m_UserRs!=null)
					m_UserRs.close();
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
	
	public List GetList(ParGroupModel p_ParGroupModel,int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(p_ParGroupModel);
		//System.out.println("User.GetList SQL语句："+strSql);
		//GetQueryResultRows(strSql);
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		Statement stm=null;
		ResultSet m_UserRs =null;		
		try
		{
			stm=m_dbConsole.GetStatement();
			m_UserRs = m_dbConsole.executeResultSet(stm, strSql);
			m_list=ConvertRsToList(m_UserRs);
		}catch(Exception ex)
		{
			System.err.println("Group.GetList 方法"+ex.getMessage());
			ex.printStackTrace();				
		}
		finally
		{
			try{
				if(m_UserRs!=null)
					m_UserRs.close();
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
