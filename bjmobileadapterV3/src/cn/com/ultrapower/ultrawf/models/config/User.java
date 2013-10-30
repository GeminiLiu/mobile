package cn.com.ultrapower.ultrawf.models.config;
 
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.util.FormatString;
import cn.com.ultrapower.system.util.PageControl;

public class User {
	
	/**
	 * 判断用户是否存在
	 * @param str_LoginName
	 * @return
	 */
	public UserModel UserIsExist(String str_LoginName) 
	{
		UserModel UserModelObj = null;
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try
		{
			StringBuffer strSelect = new StringBuffer();
			RemedyDBOp RemedyDBOpObj = new RemedyDBOp();
			strSelect.append("SELECT ");
			strSelect.append("C1 as UserID,C101 as LoginName,C102 as Pwd,C8 as FullName,C102 as PassWord,C104 as GroupList,C109 as LicenseType, C640000000 as RoleList");
			strSelect.append(" from " + RemedyDBOpObj.GetRemedyTableName("User"));
			strSelect.append(" where C101 ='"+str_LoginName+"'");
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());	
			if (objRs.next()) 
			{
				//ConvertRsToList(objRs);
				UserModelObj = new UserModel(FormatString.CheckNullString(objRs.getString("LoginName")));
				UserModelObj.SetFullName(FormatString.CheckNullString(objRs.getString("FullName")));
				UserModelObj.SetPassWord(FormatString.CheckNullString(objRs.getString("Pwd")));
				UserModelObj.SetGroupList(FormatString.CheckNullString(objRs.getString("GroupList")));
				UserModelObj.setRoleList(FormatString.CheckNullString(DataBaseOtherDeal.GetFieldClobValue(objRs, "RoleList")));
			}
		}
		catch(Exception ex)
		{
			System.err.println("初始化人员失败："+ex.getMessage());
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
		return UserModelObj;
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
	
				 UserModel m_UserModel=new UserModel();
				 m_UserModel.SetEmailAddress(p_ObjRs.getString("EmailAddress"));
				 m_UserModel.SetFullName(p_ObjRs.getString("FullName"));
				 m_UserModel.SetGroupList(p_ObjRs.getString("GroupList"));
				 m_UserModel.setRoleList(FormatString.CheckNullString(DataBaseOtherDeal.GetFieldClobValue(p_ObjRs, "RoleList")));
				 m_UserModel.SetLicenseType(p_ObjRs.getLong("LicenseType"));
				 m_UserModel.SetLoginName(p_ObjRs.getString("LoginName"));
				 m_UserModel.SetPassWord(p_ObjRs.getString("PassWord"));
				 m_UserModel.SetUserID(p_ObjRs.getString("RequestID"));
				 list.add(m_UserModel);
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
	private String GetSelectSql(ParUserModel p_ParUserModel)
	{	
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblUser);			
		stringBuffer.append(" select ");
		stringBuffer.append("C1 as RequestID,C8 as FullName,C101 as LoginName,C102 as Password");
		stringBuffer.append(",C103 as EmailAddress,C104 as GroupList,C109 as LicenseType, C640000000 as RoleList ");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ParUserModel!=null)
			stringBuffer.append(p_ParUserModel.GetWhereSql());
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
			System.err.println("User.GetList 方法"+ex.getMessage());
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
	
	public List GetList(ParUserModel p_ParUserModel,int p_PageNumber,int p_StepRow)
	{
		List m_list=new ArrayList();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		String strSql=GetSelectSql(p_ParUserModel);
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
			System.err.println("User.GetList 方法"+ex.getMessage());
			ex.printStackTrace();				
		}
		finally
		{
			try {
				if (m_UserRs != null)
					m_UserRs.close();
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
		return m_list;
	}
	
	
}
