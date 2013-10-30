package cn.com.ultrapower.ultrawf.control.config;

import java.util.*;

import cn.com.ultrapower.ultrawf.models.config.*;
import cn.com.ultrapower.ultrawf.share.OpDB;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class BaseCategoryManage {

	public BaseCategoryManage()
	{
		
	}

	public List<BaseCategoryModel> getFixList()
	{
		RDParameter FlowType_rDParameter=new RDParameter();
      	FlowType_rDParameter.addIndirectPar("isflow","1",2);
      	FlowType_rDParameter.addIndirectPar("orderby","BaseCategoryCode",4);
      	ReBuildSQL FlowType_reBuildSQL	= new ReBuildSQL("configquery.BaseCatgory.SelectAll",FlowType_rDParameter);
      	//获得查询SQL
      	String FlowType_sql				= FlowType_reBuildSQL.getReBuildSQL();
      	System.out.println(FlowType_sql);
      	//查询数据
      	Table 	FlowType_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	FlowType_rowSet			= FlowType_table.executeQuery(FlowType_sql,null,0,0,0);
      	Row 	FlowType_Rs				= null;
      	List<BaseCategoryModel>	m_List_FlowType	= new ArrayList();
      	for (int i = 0;i<FlowType_rowSet.length();i++) 
		{
      		FlowType_Rs = FlowType_rowSet.get(i);
      		BaseCategoryModel m_BaseCategoryModel 	= new BaseCategoryModel(); 
      		m_BaseCategoryModel.setBaseCategoryName(FlowType_Rs.getString("BaseCategoryName"));
      		m_BaseCategoryModel.setBaseCategorySchama(FlowType_Rs.getString("BaseCategorySchama"));
      		m_BaseCategoryModel.setBaseCategoryCode(FlowType_Rs.getString("BaseCategoryCode"));
      		m_BaseCategoryModel.setBaseCategoryClassCode(FlowType_Rs.getInt("BaseCategoryClassCode"));
      		m_BaseCategoryModel.setBaseCategoryIsFlow(FlowType_Rs.getInt("BaseCategoryIsFlow"));
      		m_BaseCategoryModel.setBaseCategoryState(FlowType_Rs.getInt("BaseCategoryState"));
      		m_BaseCategoryModel.setBaseCategoryDayLastNo(FlowType_Rs.getInt("BaseCategoryDayLastNo"));
      		m_BaseCategoryModel.setBaseCategoryDesc(FlowType_Rs.getString("BaseCategoryDesc"));
      		m_List_FlowType.add(m_BaseCategoryModel);
		}    
		return m_List_FlowType;

	}

	public List<BaseCategoryModel> GetFreeList()
	{
		RDParameter FlowType_rDParameter=new RDParameter();
      	FlowType_rDParameter.addIndirectPar("isflow","0",2);
      	FlowType_rDParameter.addIndirectPar("orderby","BaseCategoryCode",4);
      	ReBuildSQL FlowType_reBuildSQL	= new ReBuildSQL("configquery.BaseCatgory.SelectAll",FlowType_rDParameter);
      	//获得查询SQL
      	String FlowType_sql				= FlowType_reBuildSQL.getReBuildSQL();
      	System.out.println(FlowType_sql);
      	//查询数据
      	Table 	FlowType_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	FlowType_rowSet			= FlowType_table.executeQuery(FlowType_sql,null,0,0,0);
      	Row 	FlowType_Rs				= null;
      	List<BaseCategoryModel>	m_List_FlowType	= new ArrayList();
      	for (int i = 0;i<FlowType_rowSet.length();i++) 
		{
      		FlowType_Rs = FlowType_rowSet.get(i);
      		BaseCategoryModel m_BaseCategoryModel 	= new BaseCategoryModel(); 
      		m_BaseCategoryModel.setBaseCategoryBtnAllIDS(FlowType_Rs.getString("BaseCategoryBtnAllIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFixIDS(FlowType_Rs.getString("BaseCategoryBtnFixIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFreeIDS(FlowType_Rs.getString("BaseCategoryBtnFreeIDS"));
			m_BaseCategoryModel.setBaseCategoryClassCode(FlowType_Rs.getInt("BaseCategoryClassCode"));
			m_BaseCategoryModel.setBaseCategoryClassName(FlowType_Rs.getString("BaseCategoryClassName"));
			m_BaseCategoryModel.setBaseCategoryCode(FlowType_Rs.getString("BaseCategoryCode"));
			m_BaseCategoryModel.setBaseCategoryDayLastNo(FlowType_Rs.getInt("BaseCategoryDayLastNo"));
			m_BaseCategoryModel.setBaseCategoryDefaultFixTplBase(FlowType_Rs.getString("BaseCategoryDefaultFixTplBase"));
			m_BaseCategoryModel.setBaseCategoryDesc(FlowType_Rs.getString("BaseCategoryDesc"));
			m_BaseCategoryModel.setBaseCategoryIsDefaultFix(FlowType_Rs.getInt("BaseCategoryIsDefaultFix"));
			m_BaseCategoryModel.setBaseCategoryIsFlow(FlowType_Rs.getInt("BaseCategoryIsFlow"));
			m_BaseCategoryModel.setBaseCategoryName(FlowType_Rs.getString("BaseCategoryName"));
			m_BaseCategoryModel.setBaseCategoryPageHIDS(FlowType_Rs.getString("BaseCategoryPageHIDS"));
			m_BaseCategoryModel.setBaseCategoryPageIDS(FlowType_Rs.getString("BaseCategoryPageIDS"));
			m_BaseCategoryModel.setBaseCategorySchama(FlowType_Rs.getString("BaseCategorySchama"));
			m_BaseCategoryModel.setBaseCategoryState(FlowType_Rs.getInt("BaseCategoryState"));
      		m_List_FlowType.add(m_BaseCategoryModel);
		}    
		return m_List_FlowType;
	}
	
	public BaseCategoryModel getOneModel(String p_BaseCategorySchama)
	{
		RDParameter m_rDParameter=new RDParameter();
		m_rDParameter.addIndirectPar("baseschama",p_BaseCategorySchama,4);
		RowSet 	m_rowSet	= OpDB.getDataSetFromXML("configquery.BaseCatgory.BaseOn_BaseSchamaSelectOne", m_rDParameter);
  		BaseCategoryModel m_BaseCategoryModel 	= null; 		
		if (m_rowSet.length()>0)
		{
	      	Row 	m_Rs		= m_rowSet.get(0);
			m_BaseCategoryModel = new BaseCategoryModel();
			m_BaseCategoryModel.setBaseCategoryBtnAllIDS(m_Rs.getString("BaseCategoryBtnAllIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFixIDS(m_Rs.getString("BaseCategoryBtnFixIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFreeIDS(m_Rs.getString("BaseCategoryBtnFreeIDS"));
			m_BaseCategoryModel.setBaseCategoryClassCode(m_Rs.getInt("BaseCategoryClassCode"));
			m_BaseCategoryModel.setBaseCategoryClassName(m_Rs.getString("BaseCategoryClassName"));
			m_BaseCategoryModel.setBaseCategoryCode(m_Rs.getString("BaseCategoryCode"));
			m_BaseCategoryModel.setBaseCategoryDayLastNo(m_Rs.getInt("BaseCategoryDayLastNo"));
			m_BaseCategoryModel.setBaseCategoryDefaultFixTplBase(m_Rs.getString("BaseCategoryDefaultFixTplBase"));
			m_BaseCategoryModel.setBaseCategoryDesc(m_Rs.getString("BaseCategoryDesc"));
			m_BaseCategoryModel.setBaseCategoryIsDefaultFix(m_Rs.getInt("BaseCategoryIsDefaultFix"));
			m_BaseCategoryModel.setBaseCategoryIsFlow(m_Rs.getInt("BaseCategoryIsFlow"));
			m_BaseCategoryModel.setBaseCategoryName(m_Rs.getString("BaseCategoryName"));
			m_BaseCategoryModel.setBaseCategoryPageHIDS(m_Rs.getString("BaseCategoryPageHIDS"));
			m_BaseCategoryModel.setBaseCategoryPageIDS(m_Rs.getString("BaseCategoryPageIDS"));
			m_BaseCategoryModel.setBaseCategorySchama(m_Rs.getString("BaseCategorySchama"));
			m_BaseCategoryModel.setBaseCategoryState(m_Rs.getInt("BaseCategoryState"));
		}
		return m_BaseCategoryModel;		
	}	
	
	public List<BaseCategoryModel> getAllList()
	{
		RDParameter FlowType_rDParameter=new RDParameter();
      	FlowType_rDParameter.addIndirectPar("orderby","BaseCategoryClassCode",4);
      	ReBuildSQL FlowType_reBuildSQL	= new ReBuildSQL("configquery.BaseCatgory.SelectAll",FlowType_rDParameter);
      	//获得查询SQL
      	String FlowType_sql				= FlowType_reBuildSQL.getReBuildSQL();
      	System.out.println(FlowType_sql);
      	//查询数据
      	Table 	FlowType_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	FlowType_rowSet			= FlowType_table.executeQuery(FlowType_sql,null,0,0,0);
      	Row 	FlowType_Rs				= null;
      	List<BaseCategoryModel>	m_List_FlowType	= new ArrayList();
      	for (int i = 0;i<FlowType_rowSet.length();i++) 
		{
      		FlowType_Rs = FlowType_rowSet.get(i);
      		BaseCategoryModel m_BaseCategoryModel 	= new BaseCategoryModel(); 
      		m_BaseCategoryModel.setBaseCategoryBtnAllIDS(FlowType_Rs.getString("BaseCategoryBtnAllIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFixIDS(FlowType_Rs.getString("BaseCategoryBtnFixIDS"));
			m_BaseCategoryModel.setBaseCategoryBtnFreeIDS(FlowType_Rs.getString("BaseCategoryBtnFreeIDS"));
			m_BaseCategoryModel.setBaseCategoryClassCode(FlowType_Rs.getInt("BaseCategoryClassCode"));
			m_BaseCategoryModel.setBaseCategoryClassName(FlowType_Rs.getString("BaseCategoryClassName"));
			m_BaseCategoryModel.setBaseCategoryCode(FlowType_Rs.getString("BaseCategoryCode"));
			m_BaseCategoryModel.setBaseCategoryDayLastNo(FlowType_Rs.getInt("BaseCategoryDayLastNo"));
			m_BaseCategoryModel.setBaseCategoryDefaultFixTplBase(FlowType_Rs.getString("BaseCategoryDefaultFixTplBase"));
			m_BaseCategoryModel.setBaseCategoryDesc(FlowType_Rs.getString("BaseCategoryDesc"));
			m_BaseCategoryModel.setBaseCategoryIsDefaultFix(FlowType_Rs.getInt("BaseCategoryIsDefaultFix"));
			m_BaseCategoryModel.setBaseCategoryIsFlow(FlowType_Rs.getInt("BaseCategoryIsFlow"));
			m_BaseCategoryModel.setBaseCategoryName(FlowType_Rs.getString("BaseCategoryName"));
			m_BaseCategoryModel.setBaseCategoryPageHIDS(FlowType_Rs.getString("BaseCategoryPageHIDS"));
			m_BaseCategoryModel.setBaseCategoryPageIDS(FlowType_Rs.getString("BaseCategoryPageIDS"));
			m_BaseCategoryModel.setBaseCategorySchama(FlowType_Rs.getString("BaseCategorySchama"));
			m_BaseCategoryModel.setBaseCategoryState(FlowType_Rs.getInt("BaseCategoryState"));
      		m_List_FlowType.add(m_BaseCategoryModel);
		}    
		return m_List_FlowType;
	}
	

}
