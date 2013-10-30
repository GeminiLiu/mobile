package cn.com.ultrapower.ultrawf.control.process;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.process.BaseFieldBean;
import cn.com.ultrapower.ultrawf.models.process.BaseModel;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;

public class BaseManage {
	public List<BaseModel> getList(RDParameter p_rDParameter)
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		IDataBase m_dbConsole = null;
		List<BaseModel>	m_BaseModelList	= new ArrayList<BaseModel>();
		try
		{
			p_rDParameter.addIndirectPar("is_notnull","1",2);
	      	ReBuildSQL m_reBuildSQL	= new ReBuildSQL("Base.BaseOn_BaseIDSelectBase",p_rDParameter);
	      	//获得查询SQL
	      	String m_sql			= m_reBuildSQL.getReBuildSQL();
			System.out.println(m_sql);
			m_dbConsole = GetDataBase.createDataBase();
			pstmt = m_dbConsole.getConn().prepareStatement(m_sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				m_BaseModelList.add(setModelValue(rs));
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception ex) {
			//throw new DBAccessException(InforGeter.getErrorInfor(this, "delRow", ex,"删除表" + name + "中的数据时出错！"));
		} finally {
			try
			{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
			m_dbConsole.closeConn();
		}
		return m_BaseModelList;
	}

	public BaseModel getOneModel(RDParameter p_rDParameter)
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		IDataBase m_dbConsole = null;
		BaseModel	m_BaseModel	= new BaseModel();
		try
		{
			p_rDParameter.addIndirectPar("is_notnull","1",2);
	      	ReBuildSQL m_reBuildSQL	= new ReBuildSQL("Base.BaseOn_BaseIDSelectBase",p_rDParameter);
	      	//获得查询SQL
	      	String m_sql			= m_reBuildSQL.getReBuildSQL();
			System.out.println(m_sql);
			m_dbConsole = GetDataBase.createDataBase();
			pstmt = m_dbConsole.getConn().prepareStatement(m_sql);
			rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				m_BaseModel = setModelValue(rs);
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception ex) {
			
		} finally {
			try
			{
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex)
			{}
			m_dbConsole.closeConn();
		}
		return m_BaseModel;
	}
	
	public BaseModel getOneModel(String p_BaseSchema,String p_BaseID)
	{
      	RDParameter m_rDParameter = new RDParameter();
      	m_rDParameter.addIndirectPar("formname",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("baseschema",p_BaseSchema,4);
      	m_rDParameter.addIndirectPar("baseid",p_BaseID,4);
      	return getOneModel(m_rDParameter);      	
	}
	
	private BaseModel setModelValue(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		String name = "";
		int cols = rsmd.getColumnCount();
		Hashtable<String,BaseFieldBean>	m_BaseFieldHashtable	= new Hashtable<String,BaseFieldBean>();
		for (int i = 1; i <= cols; i++) {
			
			name = rsmd.getColumnName(i);
			int	ColType = rsmd.getColumnType(i);
			String value;
	 		if(rs.getObject(i)==null)
				value="";
			else	
			{
				 if(ColType==java.sql.Types.CLOB)
				 {
					 Clob m_clob=rs.getClob(i);
					 value=OpDB.getClobString(m_clob);
				 }
				 else
				 value=rs.getString(i);
			}
			BaseFieldBean m_BaseFieldBean = new BaseFieldBean();
			m_BaseFieldBean.setM_BaseFieldDBName(name);
			m_BaseFieldBean.setM_BaseFieldValue(value);
			m_BaseFieldBean.setM_BaseFieldType(ColType);
			m_BaseFieldHashtable.put(name,m_BaseFieldBean);
		}
		BaseModel m_BaseModel = new BaseModel();
		m_BaseModel.setM_BaseFieldHashtable(m_BaseFieldHashtable);
		return m_BaseModel;
	}
}
