package cn.com.ultrapower.ultrawf.models.design;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.sqlquery.ReBuildSQL;
import cn.com.ultrapower.system.sqlquery.parameter.RDParameter;
import cn.com.ultrapower.system.table.Row;
import cn.com.ultrapower.system.table.RowSet;
import cn.com.ultrapower.system.table.Table;

public class DimensionHandler
{
	private static final String tplDimensionForm = "WF:App_WorkFlowDimensions";
	
	public void Delete(RemedyFormOp RemedyOp, String requestID)
	{
		RemedyOp.FormDataDelete(tplDimensionForm, requestID);
	}
	
	public DimensionModel getDimension(String fieldID, String schema)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("fieldid", fieldID, 4);
		rdp.addIndirectPar("schema", schema, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("query.design.Dimension",rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql,null,0,0,0);
		DimensionModel dModel = new DimensionModel();
		if(rowSet.length() > 0)
		{
			Row rs = rowSet.get(0);
			dModel.setRequestID(rs.getString("RequestID"));
			dModel.setDFieldText(rs.getString("DFieldText"));
			dModel.setDFieldName(rs.getString("DFieldName"));
			dModel.setDFieldID(rs.getString("DFieldID"));
			dModel.setFieldBaseType(rs.getString("FieldBaseType"));
			dModel.setFieldBaseTypeName(rs.getString("FieldBaseTypeName"));
			dModel.setFieldFormText(rs.getString("FieldFormText"));
			dModel.setFieldFormName(rs.getString("FieldFormName"));
			dModel.setFieldText(rs.getString("FieldText"));
			dModel.setFieldName(rs.getString("FieldName"));
			dModel.setFieldID(rs.getString("FieldID"));
		}
		return dModel;
	}
	
	public List<DimensionModel> getDimensionList(String schema)
	{
		RDParameter rdp = new RDParameter();
		rdp.addIndirectPar("schema", schema, 4);
		ReBuildSQL reBuildSQL = new ReBuildSQL("query.design.Dimension",rdp, "");
		String sql = reBuildSQL.getReBuildSQL();
		Table table = new Table(GetDataBase.createDataBase(),"");
		RowSet rowSet = table.executeQuery(sql,null,0,0,0);
		List<DimensionModel> dList = new ArrayList<DimensionModel>();
		for(int i = 0; i < rowSet.length(); i++)
		{
			Row rs = rowSet.get(i);
			DimensionModel dModel = new DimensionModel();
			dModel.setRequestID(rs.getString("RequestID"));
			dModel.setDFieldText(rs.getString("DFieldText"));
			dModel.setDFieldName(rs.getString("DFieldName"));
			dModel.setDFieldID(rs.getString("DFieldID"));
			dModel.setFieldBaseType(rs.getString("FieldBaseType"));
			dModel.setFieldBaseTypeName(rs.getString("FieldBaseTypeName"));
			dModel.setFieldFormText(rs.getString("FieldFormText"));
			dModel.setFieldFormName(rs.getString("FieldFormName"));
			dModel.setFieldText(rs.getString("FieldText"));
			dModel.setFieldName(rs.getString("FieldName"));
			dModel.setFieldID(rs.getString("FieldID"));
			dList.add(dModel);
		}
		return dList;
	}
}
