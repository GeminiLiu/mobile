package cn.com.ultrapower.ultrawf.control.config;

import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.models.config.BaseCategoryClassModel;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class BaseCategoryClassManage {
	public List<BaseCategoryClassModel> getBaseCategoryClassList()
	{
      	RDParameter FlowType_rDParameter=new RDParameter();
      	FlowType_rDParameter.addIndirectPar("orderby","BaseCategoryClassCode asc",4);
      	ReBuildSQL FlowType_reBuildSQL	= new ReBuildSQL("configquery.BaseCategoryClass.SelectAll",FlowType_rDParameter);
      	//获得查询SQL
      	String FlowType_sql				= FlowType_reBuildSQL.getReBuildSQL();
      	System.out.println(FlowType_sql);
      	//查询数据
      	Table 	FlowType_table			= new Table(GetDataBase.createDataBase(),"");
      	RowSet 	FlowType_rowSet			= FlowType_table.executeQuery(FlowType_sql,null,0,0,0);
      	Row 	FlowType_Rs				= null;
      	List<BaseCategoryClassModel>	m_List_FlowType	= new ArrayList();
      	for (int i = 0;i<FlowType_rowSet.length();i++) 
		{
      		FlowType_Rs = FlowType_rowSet.get(i);
      		BaseCategoryClassModel m_BaseCategoryClassModel 	= new BaseCategoryClassModel(); 
      		m_BaseCategoryClassModel.setBaseCategoryClassID(FlowType_Rs.getString("BaseCategoryClassID"));
      		m_BaseCategoryClassModel.setBaseCategoryClassName(FlowType_Rs.getString("BaseCategoryClassName"));
      		m_BaseCategoryClassModel.setBaseCategoryClassCode(FlowType_Rs.getInt("BaseCategoryClassCode"));
      		m_BaseCategoryClassModel.setBaseCategoryClassDesc(FlowType_Rs.getString("BaseCategoryClassDesc"));
      		m_List_FlowType.add(m_BaseCategoryClassModel);
		}    
		return m_List_FlowType;
	}
}
