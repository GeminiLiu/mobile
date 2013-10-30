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

public class ConfigGroupIsSnatch {

	
	public ConfigGroupIsSnatchModel GetOneForKey(String p_Configid) 
	{
		ConfigGroupIsSnatchModel m_ConfigGroupIsSnatchModel=null;
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		Statement stm=null;
		ResultSet objRs =null;
		ParConfigGroupIsSnatchModel p_ParConfigGroupIsSnatchModel=new ParConfigGroupIsSnatchModel();
		try {
			
			p_ParConfigGroupIsSnatchModel.setConfigID(p_Configid);
			String strSql=this.getSelectSql(p_ParConfigGroupIsSnatchModel);
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm, strSql);
			if (objRs!=null && objRs.next()) {
				m_ConfigGroupIsSnatchModel=new ConfigGroupIsSnatchModel();
				m_ConfigGroupIsSnatchModel.setConfigID(objRs.getString("ConfigID"));
				m_ConfigGroupIsSnatchModel.setBaseCategoryName(objRs.getString("BaseCategoryName"));
				m_ConfigGroupIsSnatchModel.setBaseCategorySchama(objRs.getString("BaseCategorySchama"));
				m_ConfigGroupIsSnatchModel.setGroup(objRs.getString("Group"));
				m_ConfigGroupIsSnatchModel.setGroupID(objRs.getString("GroupID"));
				m_ConfigGroupIsSnatchModel.setGroupIsSnatch(objRs.getLong("GroupIsSnatch"));	
			
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
		
		return m_ConfigGroupIsSnatchModel;
		
	}
	
	private  List ConvertRsToList(ResultSet p_ObjRs)  throws Exception
	{
		if(p_ObjRs==null) return null;
		
		List list = new ArrayList();
		try{
			while(p_ObjRs.next())
			{
				ConfigGroupIsSnatchModel m_ConfigGroupIsSnatchModel=new ConfigGroupIsSnatchModel();
				m_ConfigGroupIsSnatchModel.setConfigID(p_ObjRs.getString("ConfigID"));
				m_ConfigGroupIsSnatchModel.setBaseCategoryName(p_ObjRs.getString("BaseCategoryName"));
				m_ConfigGroupIsSnatchModel.setBaseCategorySchama(p_ObjRs.getString("BaseCategorySchama"));
				m_ConfigGroupIsSnatchModel.setGroup(p_ObjRs.getString("Group"));
				m_ConfigGroupIsSnatchModel.setGroupID(p_ObjRs.getString("GroupID"));
				m_ConfigGroupIsSnatchModel.setGroupIsSnatch(p_ObjRs.getLong("GroupIsSnatch"));				
				list.add(m_ConfigGroupIsSnatchModel);
			}
		}catch(Exception ex)
		{
			System.err.println("ConfigGroupIsSnatch.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		finally
		{
			p_ObjRs.close();
		}
		return list;
	}
	
	/**
	 * 生成sql语句
	 * @param p_ParConfigGroupIsSnatchModel
	 * @return
	 */
	private String getSelectSql(ParConfigGroupIsSnatchModel p_ParConfigGroupIsSnatchModel)
	{
		StringBuffer stringBuffer=new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblGroupIsSnatch);			
		stringBuffer.append(" select ");
		stringBuffer.append("C1 as ConfigID,C650000001 as BaseCategoryName,C650000002 as BaseCategorySchama");
		stringBuffer.append(",C650000003 as Group,C650000004 as GroupID,C650000005 as GroupIsSnatch");
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append(" where 1=1 ");
		if(p_ParConfigGroupIsSnatchModel!=null)
			stringBuffer.append(p_ParConfigGroupIsSnatchModel.getWhereSql(""));
		return stringBuffer.toString();
	}
	
	
	public List getList(ParConfigGroupIsSnatchModel p_ParConfigGroupIsSnatchModel,int p_PageNumber,int p_StepRow )
	{
		
		List m_Relist=null;
		
		//StringBuffer sqlString = new StringBuffer();
		String strSql;
		strSql=this.getSelectSql(p_ParConfigGroupIsSnatchModel);
		
		if(strSql.trim().equals(""))
		{	
			return m_Relist;
		}
		PageControl m_PageControl=new PageControl();
		intQueryResultRows=m_PageControl.GetQueryResultRows(strSql);
		
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		//strSql+=p_ParBaseModel.getOrderbyFiledNameString();
		Statement stm=null;
		ResultSet m_Resultset =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_Resultset = m_dbConsole.executeResultSet(stm, strSql);
			m_Relist=ConvertRsToList(m_Resultset);	
		}catch(Exception ex)
		{
			System.err.println("ConfigGroupIsSnatch.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try{
				if(m_Resultset!=null)
					m_Resultset.close();
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
		return m_Relist;
		
	}		
	//返回查询的总行数行数

	private int intQueryResultRows=0;
	public int getQueryResultRows()
	{
		return intQueryResultRows;
	}	

}
