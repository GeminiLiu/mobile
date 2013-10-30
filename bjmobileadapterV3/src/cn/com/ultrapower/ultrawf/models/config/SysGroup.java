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

public class SysGroup {

	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}	
	
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
	
				 SysGroupModel m_SysGroupModel=new SysGroupModel();
				 m_SysGroupModel.setGroup_ID(p_ObjRs.getString("Group_ID"));
				 m_SysGroupModel.setGroup_Name(p_ObjRs.getString("Group_Name"));
				 m_SysGroupModel.setGroup_FullName(p_ObjRs.getString("Group_FullName"));
				 m_SysGroupModel.setGroup_ParentID(p_ObjRs.getString("Group_ParentID"));
				 m_SysGroupModel.setGroup_Type(p_ObjRs.getString("Group_Type"));
				 m_SysGroupModel.setGroup_CreateUser(p_ObjRs.getString("Group_CreateUser"));
				 m_SysGroupModel.setGroup_Phone(p_ObjRs.getString("Group_Phone"));
				 m_SysGroupModel.setGroup_Fax(p_ObjRs.getString("Group_Fax"));
				 m_SysGroupModel.setGroup_Status(p_ObjRs.getString("Group_Status"));
				 m_SysGroupModel.setGroup_CompanyID(p_ObjRs.getString("Group_CompanyID"));
				 m_SysGroupModel.setGroup_CompanyType(p_ObjRs.getString("Group_CompanyType"));
				 m_SysGroupModel.setGroup_Desc(p_ObjRs.getString("Group_Desc"));
				 m_SysGroupModel.setGroup_IntID(p_ObjRs.getString("Group_IntID"));
				 m_SysGroupModel.setGroup_DNID(p_ObjRs.getString("Group_DNID"));

				 list.add(m_SysGroupModel);
			}
			p_ObjRs.close();
		}catch(Exception ex)
		{
			System.err.println("SysGroup.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}
		
	/**
	 * 获取查询的Sql
	 * @param p_ParUserModel
	 * @return
	 */
	private String GetSelectSql(ParSysGroupModel p_ParSysGroupModel)
	{	
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysGroup);			
		stringBuffer.append(" select ");
		stringBuffer.append(" C1 as Group_ID,C630000018 as Group_Name,C630000019 as Group_FullName");
		stringBuffer.append(",C630000020 as Group_ParentID,C630000021 as Group_Type,C630000022 as Group_CreateUser");
		stringBuffer.append(",C630000023 as Group_Phone,C630000024 as Group_Fax,C630000025 as Group_Status");
		stringBuffer.append(",C630000026 as Group_CompanyID,C630000027 as Group_CompanyType,C630000028 as Group_Desc");
		stringBuffer.append(",C630000030 as Group_IntID,C630000037 as Group_DNID ");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ParSysGroupModel!=null)
			stringBuffer.append(p_ParSysGroupModel.GetWhereSql());
		return stringBuffer.toString();
	}
	
	public List GetList(int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(null);
		//System.out.println("User.GetList SQL语句："+strSql);
		GetQueryResultRows(strSql);
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
			System.err.println("SysGroup.GetList 方法"+ex.getMessage());
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
				System.err.println("SysGroup.GetList 方法:"+ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
		return m_list;
	}
	
	public List GetList(ParSysGroupModel p_ParGroupModel,int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(p_ParGroupModel);
		//System.out.println("User.GetList SQL语句："+strSql);
		GetQueryResultRows(strSql);
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
			System.err.println("SysGroup.GetList 方法"+ex.getMessage());
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
	
	/**
	 *返回查询的记录总数量(用于计算分页时的页数)
	 * @param p_strSql
	 */
	private void GetQueryResultRows(String p_strSql)
	{
		StringBuffer sqlString = new StringBuffer();
		sqlString.append(" select count(*) rownums from (");
		sqlString.append(p_strSql);
		//System.out.println("Base.GetQueryResultRows SQL语句："+p_strSql);
		sqlString.append(" )");
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet m_objRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_objRs = m_dbConsole.executeResultSet(stm, sqlString.toString());
			if(m_objRs.next())
			{
				intQueryResultRows=m_objRs.getInt("rownums");
			}
		}catch(Exception ex)
		{
			
			System.err.println("Base.GetQueryResultRows 方法："+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			try {
				if (m_objRs != null)
					m_objRs.close();
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
