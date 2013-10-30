package cn.com.ultrapower.ultrawf.models.process;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;
import cn.com.ultrapower.system.remedyop.PublicFieldInfo;
import cn.com.ultrapower.ultrawf.share.AttachInfoBean;
import cn.com.ultrapower.ultrawf.share.constants.Constants;
import cn.com.ultrapower.ultrawf.share.constants.ConstantsManager;
import cn.com.ultrapower.ultrawf.share.Guid;
import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.remedyop.RemedyDBOp;
import cn.com.ultrapower.system.remedyop.RemedyFieldInfo;
import cn.com.ultrapower.system.remedyop.RemedyFormOp;

public class BaseAttachment {
	
	public String Insert(Map p_hashTable)
	{
		List m_List=new ArrayList();
		String   key;
		PublicFieldInfo publicFieldInfo = null;
		
		for(Iterator it=p_hashTable.keySet().iterator();it.hasNext();)   
		{   
			key   =   (String)it.next();
			RemedyFieldInfo m_RemedyFieldInfo = null;			
			publicFieldInfo = (PublicFieldInfo)p_hashTable.get(key);

	        if(publicFieldInfo!=null){
	        	m_RemedyFieldInfo=new RemedyFieldInfo();
	        	m_RemedyFieldInfo.setIntFieldType(publicFieldInfo.getIntFieldType());
	          	m_RemedyFieldInfo.setStrFieldID(publicFieldInfo.getStrFieldID());
	        	if (publicFieldInfo.getStrFieldValue() != null && publicFieldInfo.getStrFieldValue().equals("null")==false)
	        	{
		          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	  
	        	}
	        	else
	        	{
	        		m_RemedyFieldInfo.setStrFieldValue(null);	  
	        	}
	          	m_RemedyFieldInfo.setStrFieldValue(publicFieldInfo.getStrFieldValue());	      	  
	          	m_List.add(m_RemedyFieldInfo);	        	
	        }
	    }
		String strReturnID=Insert(Constants.TblBaseAttachment,m_List);
		return strReturnID;
	}
	
	public String Insert(String p_strFormName,List p_FieldInfoList)
	{
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strReturnID=RemedyOp.FormDataInsertReturnID(p_strFormName,p_FieldInfoList);
		RemedyOp.RemedyLogout();
		return strReturnID;
	}
	
	public List GetAttachmentList(List p_EntryID_List)
	{
		List m_StrFilePath_List = new ArrayList();
		for (int i=0;i<p_EntryID_List.size();i++)
		{
			String m_strEntryID = (String)p_EntryID_List.get(i);
			RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
					Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
					Constants.REMEDY_DEMOPASSWORD);			
			RemedyOp.RemedyLogin();
			String strReturnFilePath = RemedyOp.GetEntryAttachment(Constants.TblBaseAttachment,"650000010",m_strEntryID,GetFileName(m_strEntryID));
			RemedyOp.RemedyLogout();
			//System.out.println(strReturnFilePath);
			m_StrFilePath_List.add(strReturnFilePath);
		}
		return m_StrFilePath_List;		
	}
	
	public List GetAttachmentList(String[] p_EntryID_List)
	{
		List m_StrFilePath_List = new ArrayList();
		for (int i=0;i<p_EntryID_List.length;i++)
		{
			String m_strEntryID = p_EntryID_List[i];
			if (m_strEntryID==null || m_strEntryID.equals("null") || m_strEntryID.equals(""))
			{
				continue;
			}
			RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
					Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
					Constants.REMEDY_DEMOPASSWORD);			
			RemedyOp.RemedyLogin();
			AttachInfoBean m_AttachInfoBean_tmp = new AttachInfoBean();
			m_AttachInfoBean_tmp.setAttachName(GetFileName(m_strEntryID));
			Long 	m_Intege_currentTime 	= new Long(System.currentTimeMillis());
			String strReturnFilePath = RemedyOp.GetEntryAttachment(Constants.TblBaseAttachment,"650000010",m_strEntryID,Guid.Get_GUID("tmp",m_Intege_currentTime.intValue()));
			m_AttachInfoBean_tmp.setAttachFilePath(strReturnFilePath);
			RemedyOp.RemedyLogout();
			//System.out.println(strReturnFilePath);
			m_StrFilePath_List.add(m_AttachInfoBean_tmp);
		}
		return m_StrFilePath_List;		
	}	
	
	public String GetFileName(String p_strEntryID)
	{
		String strSql		= GetSelectSql(p_strEntryID);
		String strFileName	= "";
		Statement stm=null;
		ResultSet p_Rs =null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			p_Rs = m_dbConsole.executeResultSet(stm, strSql);
			if (p_Rs!=null && p_Rs.next())
			{
				strFileName = p_Rs.getString("upLoadFileName");
				//System.out.println(strFileName);
				int int_1 = strFileName.lastIndexOf(File.separator);
				if (int_1>0)
				{
					strFileName = strFileName.substring(int_1 + 1,strFileName.length());
				}
				//System.out.println(strFileName);
			}
			else
			{
				throw new Exception("没有找到该附件");
			}
		}
		catch(Exception ex)
		{
			System.err.println("找到该附件失败："+ex.getMessage());
			ex.printStackTrace();			
		}			
		finally
		{
			try {
				if (p_Rs != null)
					p_Rs.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
			try {
				if (stm != null)
					stm.close();
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}				
			m_dbConsole.closeConn();
		}
		return strFileName;		
	}
	
	
	
	private  String GetSelectSql(String p_strEntryID)
	{
		StringBuffer strSelect = new StringBuffer();
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
		String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseAttachment);
		strSelect.append("SELECT ");
		strSelect.append("C1 as AttachmentID,C650000008 as upLoadFileName ");
		strSelect.append(" from "+strTblName+" BaseAttachment ");
		strSelect.append(" where C1 = '" + p_strEntryID + "' ");
		return strSelect.toString();
	}
	
	
	private  List ConvertRsToList(ResultSet p_ObjRs)
	{
		if(p_ObjRs==null) return null;
		List list = new ArrayList();
		try
		{
			while(p_ObjRs.next())
			{
				BaseAttachmentModel m_BaseAttachmentModel=new BaseAttachmentModel();
				m_BaseAttachmentModel.setRequestID(p_ObjRs.getString("RequestID"));
				
				m_BaseAttachmentModel.setBaseID(p_ObjRs.getString("BaseID"));
				m_BaseAttachmentModel.setBaseSchema(p_ObjRs.getString("BaseSchema"));
				m_BaseAttachmentModel.setUpLoadFileName(p_ObjRs.getString("UpLoadFileName"));
				m_BaseAttachmentModel.setUpLoadTimeDate(p_ObjRs.getLong("UpLoadTimeDate"));
				m_BaseAttachmentModel.setUpLoadUser(p_ObjRs.getString("UpLoadUser"));
				m_BaseAttachmentModel.setUpLoadUserID(p_ObjRs.getString("UpLoadUserID"));
				m_BaseAttachmentModel.setPhaseNo(p_ObjRs.getString("PhaseNo"));

				list.add(m_BaseAttachmentModel);
			}
		}
		catch (Exception ex)
		{
			System.err.println("BaseAttachment.ConvertRsToList 方法"+ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}	
	
	private String getSqlString()
	{
		RemedyDBOp m_RemedyDBOp=new RemedyDBOp();
	    String strTblName=m_RemedyDBOp.GetRemedyTableName(Constants.TblBaseAttachment);
	    
		StringBuffer bufferString=new StringBuffer();
		bufferString.append(" select ");
		bufferString.append(" C1 as RequestID,C650000001 AS BaseID,C650000002 AS BaseSchema");
		bufferString.append(" ,C650000008 as UpLoadFileName,C650000007 AS UpLoadTimeDate,C650000005 AS UpLoadUser");
		bufferString.append(" ,C650000006 as UpLoadUserID,C650000003 AS PhaseNo");
		bufferString.append(" from "+strTblName);
		bufferString.append( " where 1=1 ");
		return bufferString.toString();
	}
	
	public List getList(ParBaseAttachmentModel parBaseAttachmentModel,int p_PageNumber,int p_StepRow)
	{
		List m_list=null;
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(this.getSqlString());
		stringBuffer.append(parBaseAttachmentModel.getWhereSql());
		String strSql=stringBuffer.toString();
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSql,m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		
		Statement stm=null;
		ResultSet m_objRs =null;
		try{
			stm=m_dbConsole.GetStatement();
			m_objRs = m_dbConsole.executeResultSet(stm, strSql);
			m_list=ConvertRsToList(m_objRs);	
		}catch(Exception ex)
		{
			System.err.println("BaseAttachment.getList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try{
				if(m_objRs!=null)
					m_objRs.close();
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
	
	public boolean delete(ParBaseAttachmentModel parBaseAttachmentModel)
	{
		if(parBaseAttachmentModel==null)
			return false;
		
		List m_list=this.getList(parBaseAttachmentModel,0,0);
		int rowCount=0;
		if(m_list!=null)
			rowCount=m_list.size();
		if(rowCount<=0)
			return false;
		RemedyFormOp RemedyOp = new RemedyFormOp(Constants.REMEDY_SERVERNAME,
				Constants.REMEDY_SERVERPORT, Constants.REMEDY_DEMONAME,
				Constants.REMEDY_DEMOPASSWORD);
		RemedyOp.RemedyLogin();
		String strFormName=Constants.TblBaseAttachment;
		try{
			
			BaseAttachmentModel m_BaseAttachmentModel;
			for(int row=0;row<rowCount;row++)
			{
				m_BaseAttachmentModel=(BaseAttachmentModel)m_list.get(row);
				RemedyOp.FormDataDelete(strFormName,m_BaseAttachmentModel.getRequestID());
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConstantsManager m_ConstantsManager;
		String str_path = "Y:\\UltraProcess\\WEB-INF";
		if (!(str_path.toString().equals("null") || str_path.toString().equals("") || str_path.toString().equals("选择路径！"))) 
		{
			m_ConstantsManager = new ConstantsManager(str_path);
			m_ConstantsManager.getConstantInstance();
		}
		
		List m_StrFilePath_List = new ArrayList();
		m_StrFilePath_List.add("000000000000002");
		BaseAttachment m_BaseAttachment = new BaseAttachment();
		m_BaseAttachment.GetAttachmentList(m_StrFilePath_List);
		System.out.println("成功");
	}
	
}
