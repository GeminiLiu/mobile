package cn.com.ultrapower.ultrawf.models.process;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;
import cn.com.ultrapower.system.util.FormatString;

public class BaseOwnFieldInfo {
	
	public static String BASESCHEMA = "WF:Config_BaseOwnFieldInfo_Mobile";
	
	public String Insert(RemedyFormOp RemedyOp, BaseOwnFieldInfoModel ownFieldModel)
	{
		List p_FieldInfoList = getfieldInfoList(ownFieldModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	public void InsertList(RemedyFormOp RemedyOp, List<BaseOwnFieldInfoModel> ownFieldModelList)
	{
		for(int i=0;i<ownFieldModelList.size();i++){
			this.Insert(RemedyOp,ownFieldModelList.get(i));
		}
	}
	
	public void Delete(RemedyFormOp RemedyOp, String ownFieldID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, ownFieldID);
	}
	
	public List<BaseOwnFieldInfoModel> GetOwnFieldList(String schema)
	{
		RemedyDBOp m_RemedyDBOp = new RemedyDBOp();
		String ownTabName ="T" +m_RemedyDBOp.GetRemedyTableID(BASESCHEMA);
		String sqlString = "select * from "+ownTabName+"  t where t.c650000002='"+schema+"'";
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List ownfieldList = new ArrayList();
		try
		{
			while(rs.next())
			{			
				BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = new BaseOwnFieldInfoModel();
				m_BaseOwnFieldInfoModel.setBase_field_ID(rs.getString("c650000003"));
				m_BaseOwnFieldInfoModel.setBase_field_DBName(rs.getString("c650000004"));
				m_BaseOwnFieldInfoModel.setBase_field_ShowName(rs.getString("c650000005"));
				m_BaseOwnFieldInfoModel.setBase_field_Type(rs.getString("c650000006"));
				ownfieldList.add(m_BaseOwnFieldInfoModel);
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
		return ownfieldList;
	}
	
	private List getfieldInfoList(BaseOwnFieldInfoModel ownFieldModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("650000004", ownFieldModel.getBase_field_DBName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000003", ownFieldModel.getBase_field_ID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000018", ownFieldModel.getBase_field_Purpose_FlowControl(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000017", ownFieldModel.getBase_field_Purpose_Print(), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000005", ownFieldModel.getBase_field_ShowName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000006", ownFieldModel.getBase_field_Type(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000007", ownFieldModel.getBase_field_TypeValue(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000001", ownFieldModel.getBaseCategoryName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000002", ownFieldModel.getBaseCategorySchama(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000011", ownFieldModel.getBaseFix_field_EditPhase(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000010", ownFieldModel.getBaseFix_field_RequiredPhase(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000009", ownFieldModel.getBaseFree_field_EditStep(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000008", ownFieldModel.getBaseFree_field_ShowStep(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000100", ownFieldModel.getBaseOwnFieldInfoDesc(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000019", "0", 6));
		remedyFieldList.add(new RemedyFieldInfo("650000014", "1", 6));
		remedyFieldList.add(new RemedyFieldInfo("650000016", "1", 6));
		remedyFieldList.add(new RemedyFieldInfo("650000012", String.valueOf(ownFieldModel.getPrintOneLine()), 6));
		remedyFieldList.add(new RemedyFieldInfo("650000013", String.valueOf(ownFieldModel.getPrintOrder()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000015", "0", 6));
		return remedyFieldList;
	}

}
