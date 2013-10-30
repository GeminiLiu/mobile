package cn.com.ultrapower.ultrawf.models.process;

import java.sql.*;
import java.util.*;

import cn.com.ultrapower.system.database.*;
import cn.com.ultrapower.system.remedyop.*;
import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class BaseModelInfo {
	
public static String MODELSCHEMA = "WF:App_Base_Model_Info";
public static String MODELFIELDSCHEMA = "WF:App_Base_Model_Field";
public static String MODELATTACHSCHEMA = "WF:App_Base_Model_Attachment";	
	public List getBaseModelInfoList()
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		tablename = rdbo.GetRemedyTableName(MODELSCHEMA);
		String sqlString = "select C1 as modelID"+
				", C650000001 as modelname"+
				", C650000002 as modelversion"+
				", C650000003 as modelbasename"+
				", C650000004 as modelbaseschema"+
				", C650000005 as modeltplname"+
				", C650000006 as modeltplID"+
				", C650000007 as modelsummary"+
				", C650000008 as modelactive"+
				", C650000009 as modelauthor"+
				", C650000010 as modelauthorID"+
				", C650000011 as modelmodifier"+
				", C650000012 as modelmodifierID"+
				", C650000013 as modelcreatedate"+
				", C650000014 as modelmodifydate"+
				", C650000015 as modelstartdate"+
				", C650000016 as modelenddate"+
				", C650000017 as modeldesc"+
				" from " + tablename + " order by modelID asc";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List BaseModellList = new ArrayList();
		try
		{
			while(rs.next())
			{
				BaseModelInfoModel m_ModelInfo = new BaseModelInfoModel();
				m_ModelInfo.setModelactive(rs.getInt("modelactive"));
				m_ModelInfo.setModelauthor(rs.getString("modelauthor"));
				m_ModelInfo.setModelauthorID(rs.getString("modelauthorID"));
				m_ModelInfo.setModelbasename(rs.getString("modelbasename"));
				m_ModelInfo.setModelbaseschema(rs.getString("modelbaseschema"));
				m_ModelInfo.setModelcreatedate(rs.getLong("modelcreatedate"));
				m_ModelInfo.setModeldesc(rs.getString("modeldesc"));
				m_ModelInfo.setModelenddate(rs.getLong("modelenddate"));
				m_ModelInfo.setModelID(rs.getString("modelID"));
				m_ModelInfo.setModelmodifier(rs.getString("modelmodifier"));
				m_ModelInfo.setModelmodifierID(rs.getString("modelmodifierID"));
				m_ModelInfo.setModelmodifydate(rs.getLong("modelmodifydate"));
				m_ModelInfo.setModelname(rs.getString("modelname"));
				m_ModelInfo.setModelstartdate(rs.getLong("modelstartdate"));
				m_ModelInfo.setModelsummary(rs.getString("modelsummary"));
				m_ModelInfo.setModeltplID(rs.getString("modeltplID"));
				m_ModelInfo.setModeltplname(rs.getString("modeltplname"));
				m_ModelInfo.setModelversion(rs.getString("modelversion"));
				BaseModellList.add(m_ModelInfo);
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
		return BaseModellList;
	}
	
	public List<String> getBaseFieldList(String modelID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		tablename = rdbo.GetRemedyTableName(MODELFIELDSCHEMA);
		String sqlString = "select C1 as modelFieldID"+
				" from " + tablename  + " where C650000000 = '" + modelID+"'";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List<String> modelFieldlList = new ArrayList<String>();
		try
		{
			while(rs.next())
			{
				modelFieldlList.add(rs.getString("modelFieldID"));
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
		return modelFieldlList;
	}
	
	public List<String> getBaseAttachList(String modelID)
	{
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		tablename = rdbo.GetRemedyTableName(MODELATTACHSCHEMA);
		String sqlString = "select C1 as modelAttachID"+
				" from " + tablename  + " where C650000000 = '" + modelID+"'";
		IDataBase idb = GetDataBase.createDataBase();;
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		List<String> modelAttachlList = new ArrayList<String>();
		try
		{
			while(rs.next())
			{
				modelAttachlList.add(rs.getString("modelAttachID"));
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
		return modelAttachlList;
	}
	
	public BaseModelInfoModel getOneBaseModelInfo(String modelID){
		BaseModelInfoModel m_ModelInfo = new BaseModelInfoModel();
		RemedyDBOp rdbo = new RemedyDBOp();
		String tablename = "";
		tablename = rdbo.GetRemedyTableName(MODELSCHEMA);
		String sqlString = "select C1 as modelID"+
				", C650000001 as modelname"+
				", C650000002 as modelversion"+
				", C650000003 as modelbasename"+
				", C650000004 as modelbaseschema"+
				", C650000005 as modeltplname"+
				", C650000006 as modeltplID"+
				", C650000007 as modelsummary"+
				", C650000008 as modelactive"+
				", C650000009 as modelauthor"+
				", C650000010 as modelauthorID"+
				", C650000011 as modelmodifier"+
				", C650000012 as modelmodifierID"+
				", C650000013 as modelcreatedate"+
				", C650000014 as modelmodifydate"+
				", C650000015 as modelstartdate"+
				", C650000016 as modelenddate"+
				", C650000017 as modeldesc"+
				" from " + tablename + " where C1 = '" + modelID+"'";
		IDataBase idb = GetDataBase.createDataBase();
		Statement stat = idb.GetStatement();
		ResultSet rs = idb.executeResultSet(stat, sqlString);
		if(rs==null)
			return null;
		try
		{
			while(rs.next())
			{
				m_ModelInfo.setModelactive(rs.getInt("modelactive"));
				m_ModelInfo.setModelauthor(rs.getString("modelauthor"));
				m_ModelInfo.setModelauthorID(rs.getString("modelauthorID"));
				m_ModelInfo.setModelbasename(rs.getString("modelbasename"));
				m_ModelInfo.setModelbaseschema(rs.getString("modelbaseschema"));
				m_ModelInfo.setModelcreatedate(rs.getLong("modelcreatedate"));
				m_ModelInfo.setModeldesc(rs.getString("modeldesc"));
				m_ModelInfo.setModelenddate(rs.getLong("modelenddate"));
				m_ModelInfo.setModelID(rs.getString("modelID"));
				m_ModelInfo.setModelmodifier(rs.getString("modelmodifier"));
				m_ModelInfo.setModelmodifierID(rs.getString("modelmodifierID"));
				m_ModelInfo.setModelmodifydate(rs.getLong("modelmodifydate"));
				m_ModelInfo.setModelname(rs.getString("modelname"));
				m_ModelInfo.setModelstartdate(rs.getLong("modelstartdate"));
				m_ModelInfo.setModelsummary(rs.getString("modelsummary"));
				m_ModelInfo.setModeltplID(rs.getString("modeltplID"));
				m_ModelInfo.setModeltplname(rs.getString("modeltplname"));
				m_ModelInfo.setModelversion(rs.getString("modelversion"));
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
		return m_ModelInfo;	
	}
	
	public void deleteOneBaseModelInfo( String modelID){
		List<String> m_modelFieldList=this.getBaseFieldList(modelID);
		List<String> m_modelAttachList=this.getBaseAttachList(modelID);
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		RemedyOp.FormDataDelete(MODELSCHEMA, modelID);	
		for(int i=0;i<m_modelFieldList.size();i++){
			RemedyOp.FormDataDelete(MODELFIELDSCHEMA, m_modelFieldList.get(i));	
		}
		for(int i=0;i<m_modelAttachList.size();i++){
			RemedyOp.FormDataDelete(MODELATTACHSCHEMA, m_modelAttachList.get(i));	
		}
		RemedyOp.RemedyLogout();	
	}	

}
