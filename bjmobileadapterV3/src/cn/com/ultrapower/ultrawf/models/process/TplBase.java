package cn.com.ultrapower.ultrawf.models.process;

import java.util.*;
import java.sql.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;

public class TplBase
{
	public static String BASESCHEMA = "WF:App_TplBase";
	
	/**
	 * TplBase的插入操作
	 * @param p_FieldInfoList 插入的字段
	 * @return 插入数据的C1
	 */
	public String Insert(RemedyFormOp RemedyOp, TplBaseModel tbModel)
	{
		List p_FieldInfoList = getfieldInfoList(tbModel);
		String strReturnID=RemedyOp.FormDataInsertReturnID(BASESCHEMA,p_FieldInfoList);
		return strReturnID;
	}
	
	private List getfieldInfoList(TplBaseModel tbModel)
	{
		List remedyFieldList = new ArrayList();	
		remedyFieldList.add(new RemedyFieldInfo("650000001", tbModel.getBaseTplName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000002", tbModel.getBaseTplVersion(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000003", tbModel.getBaseTplSchema(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000015", tbModel.getBaseTplSchemaName(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000004", String.valueOf(tbModel.getBaseTplIsActive()), 6));
		remedyFieldList.add(new RemedyFieldInfo("650000005", String.valueOf(tbModel.getBaseTplStartDate()), 7));
		remedyFieldList.add(new RemedyFieldInfo("650000016", String.valueOf(tbModel.getBaseTplFlagStart()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000006", String.valueOf(tbModel.getBaseTplEndDate()), 7));
		remedyFieldList.add(new RemedyFieldInfo("650000017", String.valueOf(tbModel.getBaseTplFlagEnd()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000007", tbModel.getBaseTplAuthor(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000008", tbModel.getBaseTplAuthorID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000009", String.valueOf(tbModel.getBaseTplCreateDate()), 7));
		remedyFieldList.add(new RemedyFieldInfo("650000018", String.valueOf(tbModel.getBaseTplFlagCreate()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000010", tbModel.getBaseTplModifier(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000011", tbModel.getBaseTplModifierID(), 4));
		remedyFieldList.add(new RemedyFieldInfo("650000012", String.valueOf(tbModel.getBaseTplModifyDate()), 7));
		remedyFieldList.add(new RemedyFieldInfo("650000019", String.valueOf(tbModel.getBaseTplFlagModify()), 2));
		remedyFieldList.add(new RemedyFieldInfo("650000013", tbModel.getBaseTplDesc(), 4));
		return remedyFieldList;
	}
	
	public void Update(TplBaseModel tbModel)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "update " + tablename + " set C650000001 = '" + tbModel.getBaseTplName() + "'" +
				", C650000002 = '" + tbModel.getBaseTplVersion() + "'" +
				", C650000003 = '" + tbModel.getBaseTplSchema() + "'" +
				", C650000015 = '" + tbModel.getBaseTplSchemaName() + "'" +
				", C650000004 = " + tbModel.getBaseTplIsActive() + 
				", C650000005 = " + tbModel.getBaseTplStartDate() +
				", C650000006 = " + tbModel.getBaseTplEndDate() + 
				", C650000010 = '" + tbModel.getBaseTplModifier() + "'" + 
				", C650000011 = '" + tbModel.getBaseTplModifierID() + "'" + 
				", C650000012 = " + tbModel.getBaseTplModifyDate() + 
				", C650000013 = '" + tbModel.getBaseTplDesc() + "'" + 
				" where C1 = '" + tbModel.getBaseTplID() + "'";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		idb.executeNonQuery(stat, sqlString);
		try {
			if (stat != null)
				stat.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}				
		idb.closeConn();
	}
	
	public void Delete(RemedyFormOp RemedyOp, String tplBaseID)
	{
		RemedyOp.FormDataDelete(BASESCHEMA, tplBaseID);
	}
	
	public TplBaseModel getTplBaseModel(String baseID)
	{
		TplBaseModel tbModel = new TplBaseModel();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "select C1 as BaseTplID"+
				", C650000001 as BaseTplName" +
				", C650000002 as BaseTplVersion" +
				", C650000003 as BaseTplSchema" +
				", C650000015 as BaseTplSchemaName" +
				", C650000004 as BaseTplIsActive" +
				", C650000005 as BaseTplStartDate" +
				", C650000006 as BaseTplEndDate" +
				", C650000007 as BaseTplAuthor" +
				", C650000008 as BaseTplAuthorID" +
				", C650000009 as BaseTplCreateDate" +
				", C650000010 as BaseTplModifier" +
				", C650000011 as BaseTplModifierID" +
				", C650000012 as BaseTplModifyDate" +
				", C650000013 as BaseTplDesc" +
				" from " + tablename + " where C1 = '" + baseID + "'";
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			if(rs.next())
			{
				tbModel.setBaseTplID(rs.getString("BASETPLID"));
				tbModel.setBaseTplName(rs.getString("BaseTplName"));
				tbModel.setBaseTplVersion(rs.getString("BaseTplVersion"));
				tbModel.setBaseTplSchema(rs.getString("BaseTplSchema"));
				tbModel.setBaseTplSchemaName(rs.getString("BaseTplSchemaName"));
				tbModel.setBaseTplIsActive(rs.getInt("BaseTplIsActive"));
				tbModel.setBaseTplStartDate(rs.getLong("BaseTplStartDate"));
				tbModel.setBaseTplEndDate(rs.getLong("BaseTplEndDate"));
				tbModel.setBaseTplAuthor(rs.getString("BaseTplAuthor"));
				tbModel.setBaseTplAuthorID(rs.getString("BaseTplAuthorID"));
				tbModel.setBaseTplCreateDate(rs.getInt("BaseTplCreateDate"));
				tbModel.setBaseTplModifier(rs.getString("BaseTplModifier"));
				tbModel.setBaseTplModifierID(rs.getString("BaseTplModifierID"));
				tbModel.setBaseTplModifyDate(rs.getLong("BaseTplModifyDate"));
				tbModel.setBaseTplDesc(rs.getString("BaseTplDesc"));			
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
		return tbModel;
	}
	public List<TplBaseModel> getTplBaseList()
	{
		List<TplBaseModel> tbList = new ArrayList<TplBaseModel>();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "select C1 as BaseTplID"+
				", C650000001 as BaseTplName" +
				", C650000002 as BaseTplVersion" +
				", C650000003 as BaseTplSchema" +
				", C650000015 as BaseTplSchemaName" +
				", C650000004 as BaseTplIsActive" +
				", C650000005 as BaseTplStartDate" +
				", C650000006 as BaseTplEndDate" +
				", C650000007 as BaseTplAuthor" +
				", C650000008 as BaseTplAuthorID" +
				", C650000009 as BaseTplCreateDate" +
				", C650000010 as BaseTplModifier" +
				", C650000011 as BaseTplModifierID" +
				", C650000012 as BaseTplModifyDate" +
				", C650000013 as BaseTplDesc" +
				" from " + tablename;
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			while(rs.next())
			{
				TplBaseModel tbModel = new TplBaseModel();
				tbModel.setBaseTplID(rs.getString("BASETPLID"));
				tbModel.setBaseTplName(rs.getString("BaseTplName"));
				tbModel.setBaseTplVersion(rs.getString("BaseTplVersion"));
				tbModel.setBaseTplSchema(rs.getString("BaseTplSchema"));
				tbModel.setBaseTplSchemaName(rs.getString("BaseTplSchemaName"));
				tbModel.setBaseTplIsActive(rs.getInt("BaseTplIsActive"));
				tbModel.setBaseTplStartDate(rs.getLong("BaseTplStartDate"));
				tbModel.setBaseTplEndDate(rs.getLong("BaseTplEndDate"));
				tbModel.setBaseTplAuthor(rs.getString("BaseTplAuthor"));
				tbModel.setBaseTplAuthorID(rs.getString("BaseTplAuthorID"));
				tbModel.setBaseTplCreateDate(rs.getInt("BaseTplCreateDate"));
				tbModel.setBaseTplModifier(rs.getString("BaseTplModifier"));
				tbModel.setBaseTplModifierID(rs.getString("BaseTplModifierID"));
				tbModel.setBaseTplModifyDate(rs.getLong("BaseTplModifyDate"));
				tbModel.setBaseTplDesc(rs.getString("BaseTplDesc"));
				tbList.add(tbModel);
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
		return tbList;
	}
	
	public List<TplBaseModel> getTplBaseListBySchema(String baseSchema)
	{
		List<TplBaseModel> tbList = new ArrayList<TplBaseModel>();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "select C1 as BaseTplID"+
				", C650000001 as BaseTplName" +
				", C650000002 as BaseTplVersion" +
				", C650000003 as BaseTplSchema" +
				", C650000015 as BaseTplSchemaName" +
				", C650000004 as BaseTplIsActive" +
				", C650000005 as BaseTplStartDate" +
				", C650000006 as BaseTplEndDate" +
				", C650000007 as BaseTplAuthor" +
				", C650000008 as BaseTplAuthorID" +
				", C650000009 as BaseTplCreateDate" +
				", C650000010 as BaseTplModifier" +
				", C650000011 as BaseTplModifierID" +
				", C650000012 as BaseTplModifyDate" +
				", C650000013 as BaseTplDesc" +
				" from " + tablename + " where C650000003 = '" + baseSchema + "' order by C650000009 desc";
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			while(rs.next())
			{
				TplBaseModel tbModel = new TplBaseModel();
				tbModel.setBaseTplID(rs.getString("BASETPLID"));
				tbModel.setBaseTplName(rs.getString("BaseTplName"));
				tbModel.setBaseTplVersion(rs.getString("BaseTplVersion"));
				tbModel.setBaseTplSchema(rs.getString("BaseTplSchema"));
				tbModel.setBaseTplSchemaName(rs.getString("BaseTplSchemaName"));
				tbModel.setBaseTplIsActive(rs.getInt("BaseTplIsActive"));
				tbModel.setBaseTplStartDate(rs.getLong("BaseTplStartDate"));
				tbModel.setBaseTplEndDate(rs.getLong("BaseTplEndDate"));
				tbModel.setBaseTplAuthor(rs.getString("BaseTplAuthor"));
				tbModel.setBaseTplAuthorID(rs.getString("BaseTplAuthorID"));
				tbModel.setBaseTplCreateDate(rs.getInt("BaseTplCreateDate"));
				tbModel.setBaseTplModifier(rs.getString("BaseTplModifier"));
				tbModel.setBaseTplModifierID(rs.getString("BaseTplModifierID"));
				tbModel.setBaseTplModifyDate(rs.getLong("BaseTplModifyDate"));
				tbModel.setBaseTplDesc(rs.getString("BaseTplDesc"));
				tbList.add(tbModel);
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
		return tbList;
	}
	
	public List<TplBaseModel> getTplBaseListByCreateUserID(String userID)
	{
		List<TplBaseModel> tbList = new ArrayList<TplBaseModel>();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "select C1 as BaseTplID"+
				", C650000001 as BaseTplName" +
				", C650000002 as BaseTplVersion" +
				", C650000003 as BaseTplSchema" +
				", C650000015 as BaseTplSchemaName" +
				", C650000004 as BaseTplIsActive" +
				", C650000005 as BaseTplStartDate" +
				", C650000006 as BaseTplEndDate" +
				", C650000007 as BaseTplAuthor" +
				", C650000008 as BaseTplAuthorID" +
				", C650000009 as BaseTplCreateDate" +
				", C650000010 as BaseTplModifier" +
				", C650000011 as BaseTplModifierID" +
				", C650000012 as BaseTplModifyDate" +
				", C650000013 as BaseTplDesc" +
				" from " + tablename + " where C650000008 = '" + userID + "' order by C650000009 desc";
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			while(rs.next())
			{
				TplBaseModel tbModel = new TplBaseModel();
				tbModel.setBaseTplID(rs.getString("BASETPLID"));
				tbModel.setBaseTplName(rs.getString("BaseTplName"));
				tbModel.setBaseTplVersion(rs.getString("BaseTplVersion"));
				tbModel.setBaseTplSchema(rs.getString("BaseTplSchema"));
				tbModel.setBaseTplSchemaName(rs.getString("BaseTplSchemaName"));
				tbModel.setBaseTplIsActive(rs.getInt("BaseTplIsActive"));
				tbModel.setBaseTplStartDate(rs.getLong("BaseTplStartDate"));
				tbModel.setBaseTplEndDate(rs.getLong("BaseTplEndDate"));
				tbModel.setBaseTplAuthor(rs.getString("BaseTplAuthor"));
				tbModel.setBaseTplAuthorID(rs.getString("BaseTplAuthorID"));
				tbModel.setBaseTplCreateDate(rs.getInt("BaseTplCreateDate"));
				tbModel.setBaseTplModifier(rs.getString("BaseTplModifier"));
				tbModel.setBaseTplModifierID(rs.getString("BaseTplModifierID"));
				tbModel.setBaseTplModifyDate(rs.getLong("BaseTplModifyDate"));
				tbModel.setBaseTplDesc(rs.getString("BaseTplDesc"));
				tbList.add(tbModel);
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
		return tbList;
	}
	
	public List<TplBaseModel> getTplBaseListByModifyUserID(String userID)
	{
		List<TplBaseModel> tbList = new ArrayList<TplBaseModel>();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = rdbo.GetRemedyTableName(BASESCHEMA);
		
		String sqlString = "select C1 as BaseTplID"+
				", C650000001 as BaseTplName" +
				", C650000002 as BaseTplVersion" +
				", C650000003 as BaseTplSchema" +
				", C650000015 as BaseTplSchemaName" +
				", C650000004 as BaseTplIsActive" +
				", C650000005 as BaseTplStartDate" +
				", C650000006 as BaseTplEndDate" +
				", C650000007 as BaseTplAuthor" +
				", C650000008 as BaseTplAuthorID" +
				", C650000009 as BaseTplCreateDate" +
				", C650000010 as BaseTplModifier" +
				", C650000011 as BaseTplModifierID" +
				", C650000012 as BaseTplModifyDate" +
				", C650000013 as BaseTplDesc" +
				" from " + tablename + " where C650000011 = '" + userID + "' order by C650000009 desc";
		
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			while(rs.next())
			{
				TplBaseModel tbModel = new TplBaseModel();
				tbModel.setBaseTplID(rs.getString("BASETPLID"));
				tbModel.setBaseTplName(rs.getString("BaseTplName"));
				tbModel.setBaseTplVersion(rs.getString("BaseTplVersion"));
				tbModel.setBaseTplSchema(rs.getString("BaseTplSchema"));
				tbModel.setBaseTplSchemaName(rs.getString("BaseTplSchemaName"));
				tbModel.setBaseTplIsActive(rs.getInt("BaseTplIsActive"));
				tbModel.setBaseTplStartDate(rs.getLong("BaseTplStartDate"));
				tbModel.setBaseTplEndDate(rs.getLong("BaseTplEndDate"));
				tbModel.setBaseTplAuthor(rs.getString("BaseTplAuthor"));
				tbModel.setBaseTplAuthorID(rs.getString("BaseTplAuthorID"));
				tbModel.setBaseTplCreateDate(rs.getInt("BaseTplCreateDate"));
				tbModel.setBaseTplModifier(rs.getString("BaseTplModifier"));
				tbModel.setBaseTplModifierID(rs.getString("BaseTplModifierID"));
				tbModel.setBaseTplModifyDate(rs.getLong("BaseTplModifyDate"));
				tbModel.setBaseTplDesc(rs.getString("BaseTplDesc"));
				tbList.add(tbModel);
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
		return tbList;
	}
}
