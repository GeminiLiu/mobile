package cn.com.ultrapower.ultrawf.models.process;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class Field {
	
	public List<FieldModel> getFieldList(String schema)
	{
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
		String schemaID = m_RemedyDBOp.GetRemedyTableID(schema);
		String ownTabName ="T" +m_RemedyDBOp.GetRemedyTableID("WF:Config_BaseOwnFieldInfo_Mobile");
		String sqlString = "select t1.SCHEMAID as SCHEMAID, t1.FIELDID as FIELDID, FIELDNAME , DATATYPE, PROPLONG" +
		" from field t1,field_dispprop t2 where t1.fieldid = t2.fieldid and t1.schemaid = t2.schemaid and t1.schemaid = '" + schemaID + 
		"' and (t1.fieldid>800000000 or (t1.fieldid>700000010 and t1.fieldid<700000020) ) and t1.fieldid<800046000" +
		" and FOPTION<3 and DATATYPE<8 and "+
		"not exists(select C650000003 from "+ownTabName+" o where C650000002 = '"+schema+"' and o.C650000003 = t1.fieldid) "+
		"order by FIELDID asc";
		System.out.println(sqlString);
		IDataBase idb = GetDataBase.createDataBase();
		
//		Table table=new Table(idb,"");
//		RowSet rowSet=table.executeQuery(sqlString, null, 0, 0, 0);
//		int rowCount=0;
//		if(rowSet!=null)
//		{
//			rowCount=rowSet.length();
//			
//		}
//		for(int row=0;row<rowCount;row++)
//		{
//			Row objRow=rowSet.get(row);
//			FieldModel m_FieldModel = new FieldModel();
//			m_FieldModel.setFieldschemaID(objRow.getString("SCHEMAID"));
//			
//			
//		}
		
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List fieldList = new ArrayList();
		try
		{
			while(rs.next())
			{
				String tmp_ShowName=getFieldShowName(rs.getClob("PROPLONG"));				
				FieldModel m_FieldModel = new FieldModel();
				m_FieldModel.setFieldschemaID(rs.getString("SCHEMAID"));
				m_FieldModel.setFieldID(rs.getString("FIELDID"));
				m_FieldModel.setFieldDbName(rs.getString("FIELDNAME"));
				m_FieldModel.setFieldShowName(tmp_ShowName);
				m_FieldModel.setFieldDateType(rs.getString("DATATYPE"));
				fieldList.add(m_FieldModel);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stat != null)
					stat.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			idb.closeConn();
		}
		return fieldList;
	}
	
	

	private String getFieldShowName(Clob p_clob)
	{
		String strValue=getClobString(p_clob);
		String strReturn="";
		while(strValue.length()>0){
			int j=strValue.indexOf("\\");
			strReturn=strValue.substring(0, j);
			if(this.isNumeric(strReturn)==true)
			{
				strValue=strValue.substring(j+1);
				continue;
			}else
			{
				if(strReturn.lastIndexOf("：")>0)
				{
					strReturn=strReturn.substring(0, strReturn.length()-1);
				}
				break;
			}			
					
		}
		return strReturn;
	}
	
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() )
		{
			return false;
		}
		return true;
		}
	
	private String getClobString(Clob p_clob)
	{
		String strValue="";
	
		if (p_clob!=null) 
		{
			try{
			if(p_clob.length()>0)
			{
				strValue=p_clob.getSubString((long)1,(int)p_clob.length());
			}
			}catch(Exception ex)
			{
				strValue="";
			}
		}
		return strValue;
	}

	public static void main(String[] args) {
		Field m_Field = new Field();		
	}
}
