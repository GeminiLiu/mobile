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
//import cn.com.ultrapower.ultrawf.share.PageControl;

public class SysUser {
 
	private int intQueryResultRows=0;
	//返回查询的总行数行数
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}
	
	public SysUserModel getOneForKey(String p_LoginName) 
	{
		if(p_LoginName==null)
			p_LoginName="";
		if(p_LoginName.trim().equals(""))
			return null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		SysUserModel m_SysUserModel=null;
		Statement stm=null;
		ResultSet objRs =null;
		try {
			ParSysUserModel p_ParSysUserModel=new ParSysUserModel();
			p_ParSysUserModel.setUser_LoginName(p_LoginName);
			String strSql=this.GetSelectSql(p_ParSysUserModel);
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strSql);
			if (objRs!=null && objRs.next()) {
				m_SysUserModel=new SysUserModel();
				 m_SysUserModel.setUser_ID(objRs.getString("User_ID"));
				 m_SysUserModel.setUser_LoginName(objRs.getString("User_LoginName"));
				 m_SysUserModel.setUser_PassWord(objRs.getString("User_PassWord"));
				 m_SysUserModel.setUser_FullName(objRs.getString("User_FullName"));
				 m_SysUserModel.setUser_CreateUser(objRs.getString("User_CreateUser"));
				 m_SysUserModel.setUser_Position(objRs.getString("User_Position"));
				 m_SysUserModel.setUser_IsManager(objRs.getString("User_IsManager"));
				 m_SysUserModel.setUser_Type(objRs.getString("User_Type"));
				 m_SysUserModel.setUser_Mobie(objRs.getString("User_Mobie"));
				 m_SysUserModel.setUser_Phone(objRs.getString("User_Phone"));
				 m_SysUserModel.setUser_Fax(objRs.getString("User_Fax"));
				 m_SysUserModel.setUser_Mail(objRs.getString("User_Mail"));
				 m_SysUserModel.setUser_Status(objRs.getString("User_Status"));
				 m_SysUserModel.setUser_CPID(objRs.getString("User_CPID"));
				 m_SysUserModel.setUser_CPType(objRs.getString("User_CPType"));
				 m_SysUserModel.setUser_DPID(objRs.getString("User_DPID"));
				 m_SysUserModel.setUser_LicenseType(objRs.getString("User_LicenseType"));
				 m_SysUserModel.setUser_OrderBy(objRs.getString("User_OrderBy"));
				 m_SysUserModel.setUser_IntID(objRs.getString("User_IntID"));
				 m_SysUserModel.setUser_BelongGroupID(objRs.getString("User_BelongGroupID"));
			}//if(objRs.next())
			//return getBaseDealOutTime().toString();
			
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		finally {
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
		return m_SysUserModel;
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
	
				 SysUserModel p_SysUserModel=new SysUserModel();
				 p_SysUserModel.setUser_ID(p_ObjRs.getString("User_ID"));
				 p_SysUserModel.setUser_LoginName(p_ObjRs.getString("User_LoginName"));
				 p_SysUserModel.setUser_PassWord(p_ObjRs.getString("User_PassWord"));
				 p_SysUserModel.setUser_FullName(p_ObjRs.getString("User_FullName"));
				 p_SysUserModel.setUser_CreateUser(p_ObjRs.getString("User_CreateUser"));
				 p_SysUserModel.setUser_Position(p_ObjRs.getString("User_Position"));
				 p_SysUserModel.setUser_IsManager(p_ObjRs.getString("User_IsManager"));
				 p_SysUserModel.setUser_Type(p_ObjRs.getString("User_Type"));
				 p_SysUserModel.setUser_Mobie(p_ObjRs.getString("User_Mobie"));
				 p_SysUserModel.setUser_Phone(p_ObjRs.getString("User_Phone"));
				 p_SysUserModel.setUser_Fax(p_ObjRs.getString("User_Fax"));
				 p_SysUserModel.setUser_Mail(p_ObjRs.getString("User_Mail"));
				 p_SysUserModel.setUser_Status(p_ObjRs.getString("User_Status"));
				 p_SysUserModel.setUser_CPID(p_ObjRs.getString("User_CPID"));
				 p_SysUserModel.setUser_CPType(p_ObjRs.getString("User_CPType"));
				 p_SysUserModel.setUser_DPID(p_ObjRs.getString("User_DPID"));
				 p_SysUserModel.setUser_LicenseType(p_ObjRs.getString("User_LicenseType"));
				 p_SysUserModel.setUser_OrderBy(p_ObjRs.getString("User_OrderBy"));
				 p_SysUserModel.setUser_IntID(p_ObjRs.getString("User_IntID"));
				 p_SysUserModel.setUser_BelongGroupID(p_ObjRs.getString("User_BelongGroupID"));

				 list.add(p_SysUserModel);
			}
			p_ObjRs.close();
		}catch(Exception ex)
		{
			System.err.println("SysUser.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
		}
		return list;
	}
		
	/**
	 * 获取查询的Sql
	 * @param p_ParUserModel
	 * @return
	 */
	private String GetSelectSql(ParSysUserModel p_ParSysUserModel)
	{	
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblSysUser);			
		stringBuffer.append(" select C1 as User_ID,C630000001 as User_LoginName");
		stringBuffer.append(",C630000002 as User_PassWord,C630000003 as User_FullName");
		stringBuffer.append(",C630000004 as User_CreateUser,C630000005 as User_Position");
		stringBuffer.append(",C630000006 as User_IsManager,C630000007 as User_Type,C630000008 as User_Mobie");
		stringBuffer.append(",C630000009 as User_Phone,C630000010 as User_Fax,C630000011 as User_Mail");
		stringBuffer.append(",C630000012 as User_Status,C630000013 as User_CPID,C630000014 as User_CPType");
		stringBuffer.append(",C630000015 as User_DPID,C630000016 as User_LicenseType,C630000017 as User_OrderBy");
		stringBuffer.append(",C630000029 as User_IntID,C630000036 as User_BelongGroupID ");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ParSysUserModel!=null)
			stringBuffer.append(p_ParSysUserModel.GetWhereSql());
		return stringBuffer.toString();
	}
	
	public List getList(int p_PageNumber,int p_StepRow)
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
			System.err.println("SysUser.GetList 方法"+ex.getMessage());
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
				System.err.println("SysUser.GetList 方法:"+ex.getMessage());
			}			
			m_dbConsole.closeConn();
		}
		return m_list;
	}
	
	public List getList(ParSysUserModel p_ParSysUserModel,int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(p_ParSysUserModel);
		//System.out.println("User.GetList SQL语句："+strSql);
		GetQueryResultRows(strSql);
		//加排序条件
		strSql+=p_ParSysUserModel.getOrderbyFiledNameString(); 
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
			System.err.println("SysUser.GetList 方法"+ex.getMessage());
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
