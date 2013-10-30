package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
public class BaseInfor {
	
	private  List ConvertRsToList(ResultSet p_ObjRs)
	{
		if(p_ObjRs==null) return null;
		List list = new ArrayList();
		try
		{
			while(p_ObjRs.next())
			{
				BaseInforModel m_BaseInforModel=new BaseInforModel();
				m_BaseInforModel.setRequestID(p_ObjRs.getString("RequestID"));
				m_BaseInforModel.setBaseID(p_ObjRs.getString("BaseID"));
				m_BaseInforModel.setBaseSchema(p_ObjRs.getString("BaseSchema"));
				m_BaseInforModel.setBaseName(p_ObjRs.getString("BaseName"));
				m_BaseInforModel.setBaseSN(p_ObjRs.getString("BaseSN"));
				 m_BaseInforModel.setBaseCreatorFullName(p_ObjRs.getString("BaseCreatorFullName"));
				 m_BaseInforModel.setBaseCreatorLoginName(p_ObjRs.getString("BaseCreatorLoginName"));
				 m_BaseInforModel.setBaseCreateDate(p_ObjRs.getLong("BaseCreateDate"));
				 m_BaseInforModel.setBaseFinishDate(p_ObjRs.getLong("BaseFinishDate"));
				 m_BaseInforModel.setBaseCloseDate(p_ObjRs.getLong("BaseCloseDate"));
				 m_BaseInforModel.setBaseStatus(p_ObjRs.getString("BaseStatus"));

				list.add(m_BaseInforModel);
			}
		}
		catch (Exception ex)
		{
			System.err.println("BaseInfor.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
	
	public List getList(ParBaseInforModel parBaseInforModel,int p_PageNumber,int p_StepRow )
	{
		List m_list=null;
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(this.getSqlString());
		stringBuffer.append(parBaseInforModel.getWhereSql());
		String strSql=stringBuffer.toString();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		Statement stm=null;
		ResultSet m_BaseRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_BaseRs = m_dbConsole.executeResultSet(stm, strSql);
			m_list=ConvertRsToList(m_BaseRs);	
		}catch(Exception ex)
		{
			System.err.println("BaseInfor.BaseInfor 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
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
		return m_list;
	}
	public String getSqlString()
	{
		StringBuffer stringBuffer=new StringBuffer();
		
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
	    String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseInfor);
		
		stringBuffer.append(" select ");
		
		stringBuffer.append("C1 as RequestID,C700000000 as BaseID,C700000001 as BaseSchema,C700000002 as BaseName,C700000003 as BaseSN");
		stringBuffer.append(",C700000004 as BaseCreatorFullName,C700000005 as BaseCreatorLoginName,C700000006 as BaseCreateDate");
		stringBuffer.append(",C700000007 as BaseSendDate,C700000008 as BaseFinishDate,C700000009 as BaseCloseDate");
		stringBuffer.append(",C700000010 as BaseStatus");
		
		stringBuffer.append(" from "+strTblName);
		stringBuffer.append("  where 1=1 ");
		return stringBuffer.toString();
	}
	
	public boolean delete(ParBaseInforModel parBaseInforModel)
	{
		if(parBaseInforModel==null)
			return false;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strFormName=Constants.TblBaseInfor;
		try{
			
			List m_list=this.getList(parBaseInforModel,0,0);
			int rowCount=0;
			if(m_list!=null)
				rowCount=m_list.size();
			BaseInforModel m_BaseInforModel;
			for(int row=0;row<rowCount;row++)
			{
				m_BaseInforModel=(BaseInforModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_BaseInforModel.getRequestID());
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
