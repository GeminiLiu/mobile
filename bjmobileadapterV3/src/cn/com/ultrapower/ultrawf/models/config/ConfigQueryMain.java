package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class ConfigQueryMain {
	
	/**
	 * 返回ConfigQueryMainModel类的List
	 * @param p_Rs
	 * @return
	 * @throws Exception
	 */
	private  List ConvertRsToList( ResultSet p_Rs)  throws Exception
	{	
		if(p_Rs==null) return null;
		List list = new ArrayList();
		try{
			while (p_Rs.next())
			{
				ConfigQueryMainModel m_ConfigQueryMainModel=new ConfigQueryMainModel();
				m_ConfigQueryMainModel=new ConfigQueryMainModel();
				m_ConfigQueryMainModel.setCode(p_Rs.getString("code"));
				m_ConfigQueryMainModel.setConfdesc(p_Rs.getString("confdesc"));
				m_ConfigQueryMainModel.setColcount(p_Rs.getString("colcount"));
				m_ConfigQueryMainModel.setLablepercent(p_Rs.getString("lablepercent"));
				m_ConfigQueryMainModel.setInputpercent(p_Rs.getString("inputpercent"));				
				list.add(m_ConfigQueryMainModel);
			}//while (p_DealRs.next())
			p_Rs.close();
		}catch(Exception ex)
		{
			System.err.println(ex.getMessage());
			ex.printStackTrace();			
			throw ex;
		}
		
		return list;
	}	


	
	public ConfigQueryMainModel getOneModel(String p_Code)
	{
		ConfigQueryMainModel m_ConfigQueryMainModel=null;
		StringBuffer strSelect = new StringBuffer();
		strSelect.append(getSelectSql());
		strSelect.append(" where 1=1 ");
		strSelect.append(" and code='"+p_Code+"'");
		Statement stm=null;
		ResultSet objRs=null;
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		try{
			stm=m_dbConsole.GetStatement();
			objRs = m_dbConsole.executeResultSet(stm,strSelect.toString());
			if (objRs.next()) 
			{
				m_ConfigQueryMainModel=new ConfigQueryMainModel();
				m_ConfigQueryMainModel.setCode(objRs.getString("code"));
				m_ConfigQueryMainModel.setConfdesc(objRs.getString("confdesc"));
				m_ConfigQueryMainModel.setColcount(objRs.getString("colcount"));
				m_ConfigQueryMainModel.setLablepercent(objRs.getString("lablepercent"));
				m_ConfigQueryMainModel.setInputpercent(objRs.getString("inputpercent"));
			}
		}catch(Exception ex)
		{
			System.err.println("ConfigQueryMain.GetOneModel 方法："+ex.getMessage());
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
		return m_ConfigQueryMainModel;
	}	
	
	
	public List getList(ParConfigQueryMainModel p_ParConfigQueryMainModel,int p_PageNumber,int p_StepRow)
	{
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		StringBuffer strSqlBuffer=new StringBuffer();
		List m_List=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		strSqlBuffer.append(this.getSelectSql());
		strSqlBuffer.append(" where 1=1 ");
		if(p_ParConfigQueryMainModel!=null)
			strSqlBuffer.append(p_ParConfigQueryMainModel.getWhereSql(""));
		
		String strOrder="";
		if(p_ParConfigQueryMainModel!=null)
			p_ParConfigQueryMainModel.getOrderbyFiledNameString();
		if(strOrder.trim().equals(""))
			strOrder=" order by code ";
		//排序
		strSqlBuffer.append(strOrder);		
		
		String strSql="";
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSqlBuffer.toString(),m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		else
			strSql=strSqlBuffer.toString();
			

		
		try{
			stm=m_dbConsole.GetStatement();
			m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
			m_List=ConvertRsToList(m_ObjRs);	
		}catch(Exception ex)
		{
			System.err.println("ConfigQueryMain.GetList 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try {
				if (m_ObjRs != null)
					m_ObjRs.close();
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
		
		return m_List;
	}
	public boolean insert(ConfigQueryMainModel p_ConfigQueryMainModel)
	{
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		StringBuffer strSqlInsert=new StringBuffer();
		int insertCount=0;
		Statement stm=null;
		if(p_ConfigQueryMainModel!=null)
		{
			strSqlInsert.append("insert into CONFIGQUERYMAIN(CODE,CONFDESC,COLCOUNT,LABLEPERCENT,INPUTPERCENT) values('");
			strSqlInsert.append(p_ConfigQueryMainModel.getCode());
			strSqlInsert.append("','");
			strSqlInsert.append(p_ConfigQueryMainModel.getConfdesc());
			strSqlInsert.append("',");
			strSqlInsert.append(p_ConfigQueryMainModel.getColcount());
			strSqlInsert.append(",");
			strSqlInsert.append(p_ConfigQueryMainModel.getLablepercent());
			strSqlInsert.append(",");
			strSqlInsert.append(p_ConfigQueryMainModel.getInputpercent());
			strSqlInsert.append(")");
		}
        else
            return false;
		try{
			stm=m_dbConsole.GetStatement();
			insertCount=m_dbConsole.executeNonQuery(stm,strSqlInsert.toString());	
		}catch(Exception ex)
		{
			System.err.println("ConfigQueryMain.insert 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try
			{
				if(stm!=null)
					stm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		if(insertCount>0)
			return true;
		else
			return false;
	}
    public boolean delete(String fldCode)
    {
        IDataBase m_dbConsole = GetDataBase.createDataBase();
        StringBuffer strSqlDelete=new StringBuffer();
        Statement stm=null;
        int deleteCount=0;
        if(fldCode!=null&&(!fldCode.equals("")))
        {
            strSqlDelete.append("delete from CONFIGQUERYMAIN where CODE='");
            strSqlDelete.append(fldCode);
            strSqlDelete.append("'");
        }
        else
            return false;
        try{
            stm=m_dbConsole.GetStatement();
            deleteCount=m_dbConsole.executeNonQuery(stm,strSqlDelete.toString());
        }catch(Exception ex)
        {
            System.err.println("ConfigQueryMain.delete 方法"+ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
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
        if(deleteCount>0)
            return true;
        else
            return false;
    }
	public int update(ConfigQueryMainModel p_ConfigQueryMainModel)
	{
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		StringBuffer strSqlInsert=new StringBuffer();
		int insertCount=0;
		Statement stm=null;
		if(p_ConfigQueryMainModel!=null)
		{
			strSqlInsert.append("update configquerymain set CONFDESC='");
            
			strSqlInsert.append(p_ConfigQueryMainModel.getConfdesc());
			strSqlInsert.append("',COLCOUNT=");
			strSqlInsert.append(p_ConfigQueryMainModel.getColcount());
			strSqlInsert.append(",LABLEPERCENT=");
			strSqlInsert.append(p_ConfigQueryMainModel.getLablepercent());
			strSqlInsert.append(",INPUTPERCENT=");
			strSqlInsert.append(p_ConfigQueryMainModel.getInputpercent());
			strSqlInsert.append("  where CODE='");
			strSqlInsert.append(p_ConfigQueryMainModel.getCode());
            strSqlInsert.append("'");
			
		}
        else
            return insertCount;
		try{
			stm=m_dbConsole.GetStatement();
			insertCount=m_dbConsole.executeNonQuery(stm,strSqlInsert.toString());	
		}catch(Exception ex)
		{
			System.err.println("ConfigQueryMain.insert 方法"+ex.getMessage());
			ex.printStackTrace();			
			//throw ex;			
		}
		finally
		{
			try
			{
				if(stm!=null)
					stm.close();
			}
			catch(Exception ex)
			{
				System.err.println(ex.getMessage());
			}
			m_dbConsole.closeConn();
		}
		return insertCount;
	}
	public String getSelectSql()
	{
		StringBuffer strSql=new StringBuffer();
		strSql.append(" select code, confdesc, colcount, lablepercent, inputpercent ");
		strSql.append(" from configquerymain ");
		return strSql.toString();
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("dddddddddd");
	}

}
