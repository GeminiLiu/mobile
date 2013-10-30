package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;

public class BaseCategory {

	public BaseCategory(){}
	
	/**
	 * @param p_objRs
	 */
	private BaseCategoryModel setBaseCategoryModelInfo(ResultSet p_objRs)
	{
		BaseCategoryModel  m_BaseCategoryModel=null;
		try
		{
			if (p_objRs.next()) 
			{
				m_BaseCategoryModel=new BaseCategoryModel();
				m_BaseCategoryModel.setBaseCategoryIsFlow(p_objRs.getInt("BaseCategoryIsFlow"));
				m_BaseCategoryModel.setBaseCategoryState(p_objRs.getInt("BaseCategoryState"));
				m_BaseCategoryModel.setBaseCategoryDayLastNo(p_objRs.getInt("BaseCategoryDayLastNo"));
//				m_BaseCategoryModel.setBaseCategoryClassCode(p_objRs.getInt("BaseCategoryClassCode"));
//				m_BaseCategoryModel.setBaseCategoryIsDefaultFix(p_objRs.getInt("BaseCategoryIsDefaultFix"));
				m_BaseCategoryModel.setBaseCategoryName(p_objRs.getString("BaseCategoryName"));
				m_BaseCategoryModel.setBaseCategorySchama(p_objRs.getString("BaseCategorySchama"));
				m_BaseCategoryModel.setBaseCategoryCode(p_objRs.getString("BaseCategoryCode"));
				m_BaseCategoryModel.setBaseCategoryDesc(p_objRs.getString("BaseCategoryDesc"));
//				m_BaseCategoryModel.setBaseCategoryBtnAllIDS(p_objRs.getString("BaseCategoryBtnAllIDS"));
//				m_BaseCategoryModel.setBaseCategoryClassName(p_objRs.getString("BaseCategoryClassName"));
//				m_BaseCategoryModel.setBaseCategoryDefaultFixTplBase(p_objRs.getString("BaseCategoryDefaultFixTplBase"));
//				m_BaseCategoryModel.setBaseCategoryPageIDS(p_objRs.getString("BaseCategoryPageIDS"));
//				m_BaseCategoryModel.setBaseCategoryBtnFreeIDS(p_objRs.getString("BaseCategoryBtnFreeIDS"));
//				m_BaseCategoryModel.setBaseCategoryBtnFixIDS(p_objRs.getString("BaseCategoryBtnFixIDS"));
			}
			p_objRs.close();
		}catch(Exception ex)
		{
			System.err.println("BaseCategory.SetBaseCategoryModelInfo："+ex.getMessage());
			ex.printStackTrace();				
		}
		return m_BaseCategoryModel;
		
	}
	
	/**
	 * 根据关键字查询，并返回工单类别一个(BaseCategoryModel)实例对象
	 * @param p_BaseCategorySchama
	 * @return
	 */
	public BaseCategoryModel getOneModel(String p_BaseCategorySchama)
	{
		BaseCategoryModel m_BaseCategoryModel=null;
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(this.getSelectSql());
		strSelect.append(" where 1=1 ");
		strSelect.append(" and C650000002='"+p_BaseCategorySchama+"'");
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			m_BaseCategoryModel=setBaseCategoryModelInfo(objRs);
		}catch(Exception ex)
		{
			System.err.println("BaseCategory.GetOneModel 方法："+ex.getMessage());
			ex.printStackTrace();				
		}	
		finally
		{
			try{
				if(objRs!=null)
					objRs.close();
			}catch(Exception ex)
			{
				System.err.print(ex.getMessage());
			}
			
			try{
				if(stm!=null)
					stm.close();
			}catch(Exception ex)
			{
				System.err.print(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return m_BaseCategoryModel;
	}
	
	
	/**
	 * 返回查询语句
	 * @return
	 */
	public  String getSelectSql()
	{
		StringBuffer strSelect = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseCategory);		
		strSelect.append("SELECT ");
		strSelect.append("C1 as BaseCategoryID,C650000001 as BaseCategoryName,C650000002 as BaseCategorySchama");
		strSelect.append(",C650000003 as BaseCategoryCode,C650000004 as BaseCategoryIsFlow,C650000005 as BaseCategoryState");
		strSelect.append(",C650000006 as BaseCategoryDayLastNo,C650000007 as BaseCategoryDesc");
		strSelect.append(" FROM "+strTblName);
		return strSelect.toString();
	}
	
	/**
	 * 构造函数之一，根据参数实例化一个工单类别类对象，不查询数据库，直接将类中的属性负值
	 * @param p_BaseCategoryRs
	 * @return
	 * @throws Exception
	 */
	public List<BaseCategoryModel> getRsConvertList(ResultSet p_objRs) throws Exception
	{
		List<BaseCategoryModel> m_list = new ArrayList<BaseCategoryModel>();
		try{
			while (p_objRs.next())
			{
				BaseCategoryModel m_BaseCategoryModel=new BaseCategoryModel();
				m_BaseCategoryModel.setBaseCategoryIsFlow(p_objRs.getInt("BaseCategoryIsFlow"));
				m_BaseCategoryModel.setBaseCategoryState(p_objRs.getInt("BaseCategoryState"));
				m_BaseCategoryModel.setBaseCategoryDayLastNo(p_objRs.getInt("BaseCategoryDayLastNo"));
				m_BaseCategoryModel.setBaseCategoryClassCode(p_objRs.getInt("BaseCategoryClassCode"));
				m_BaseCategoryModel.setBaseCategoryIsDefaultFix(p_objRs.getInt("BaseCategoryIsDefaultFix"));
				m_BaseCategoryModel.setBaseCategoryName(p_objRs.getString("BaseCategoryName"));
				m_BaseCategoryModel.setBaseCategorySchama(p_objRs.getString("BaseCategorySchama"));
				m_BaseCategoryModel.setBaseCategoryCode(p_objRs.getString("BaseCategoryCode"));
				m_BaseCategoryModel.setBaseCategoryDesc(p_objRs.getString("BaseCategoryDesc"));
				m_BaseCategoryModel.setBaseCategoryBtnAllIDS(p_objRs.getString("BaseCategoryBtnAllIDS"));
				m_BaseCategoryModel.setBaseCategoryClassName(p_objRs.getString("BaseCategoryClassName"));
				m_BaseCategoryModel.setBaseCategoryDefaultFixTplBase(p_objRs.getString("BaseCategoryDefaultFixTplBase"));
				m_BaseCategoryModel.setBaseCategoryPageIDS(p_objRs.getString("BaseCategoryPageIDS"));
				m_BaseCategoryModel.setBaseCategoryBtnFreeIDS(p_objRs.getString("BaseCategoryBtnFreeIDS"));
				m_BaseCategoryModel.setBaseCategoryBtnFixIDS(p_objRs.getString("BaseCategoryBtnFixIDS"));				
				m_list.add(m_BaseCategoryModel);
			}
			p_objRs.close();
		}
		catch(Exception ex)
		{
			System.err.println("BaseCategory.GetSelectSql方法："+ex.getMessage());
			ex.printStackTrace();				
		}
		
		return m_list;
	}


	
}
