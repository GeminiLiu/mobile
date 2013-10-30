package cn.com.ultrapower.ultrawf.models.config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.ultrawf.share.PageControl;
import cn.com.ultrapower.system.database.GetDataBase;
import cn.com.ultrapower.system.database.IDataBase;

public class ConfigQueryDetail {
	
	private  List ConvertRsToList( ResultSet p_Rs)  throws Exception
	{	
		if(p_Rs==null) return null;
		List list = new ArrayList();
		try{
			while (p_Rs.next())
			{
				 ConfigQueryDetailModel m_ConfigQueryDetailModel=new ConfigQueryDetailModel();
				 m_ConfigQueryDetailModel.setCode(p_Rs.getString("code"));
				 m_ConfigQueryDetailModel.setFieldid(p_Rs.getString("fieldid"));
					 m_ConfigQueryDetailModel.setFieldname(p_Rs.getString("fieldname"));
					// Fielddisplayname 用于在界面显示的名称
					 m_ConfigQueryDetailModel.setFielddisplayname(p_Rs.getString("fielddisplayname"));
					// 查询字段的数据类型(字符、数字s等)
					 m_ConfigQueryDetailModel.setFieldtype(p_Rs.getString("fieldtype"));
					//Fielddisplaytype 用于在界面显示的方式。如：text、radio、checkbox、select等	1：文本输入框
					 m_ConfigQueryDetailModel.setFielddisplaytype(p_Rs.getString("fielddisplaytype"));
					 m_ConfigQueryDetailModel.setColspan(p_Rs.getString("colspan"));
					 m_ConfigQueryDetailModel.setRowspan(p_Rs.getString("rowspan"));
					//Logicexp用于查询时的表达式。如：左like、右like、like、not、or、in、=、<>等
					 m_ConfigQueryDetailModel.setLogicexp(p_Rs.getString("logicexp"));
					 m_ConfigQueryDetailModel.setSortid(p_Rs.getString("sortid"));
					//Defaultvalue1 当显示方式为select、radio、checkbox时的显示值.格式为”值+冒号+显示值+分号”如：0:是;1:否;
					 m_ConfigQueryDetailModel.setDefaultvalue1(p_Rs.getString("defaultvalue1"));
					//Defaultvalue2 当显示方式为select、radio、checkbox时的显示值，从配置表中读取据。
					//本字段用于保存读取配置信息的SQL.规则是第一个字段做为value值，第二个字段做为显示值。
					 m_ConfigQueryDetailModel.setDefaultvalue2(p_Rs.getString("defaultvalue2"));
					// Parclassname 用于标识将该参数赋值给哪个参数类
					 m_ConfigQueryDetailModel.setParclassname(p_Rs.getString("parclassname"));
					//Defaultvalue 默认的缺省值
					 m_ConfigQueryDetailModel.setDefaultvalue(p_Rs.getString("defaultvalue"));
					 
					 m_ConfigQueryDetailModel.setIsNewLine(p_Rs.getString("isnewline"));
					list.add(m_ConfigQueryDetailModel);
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
		
	
	public List getList(ParConfigQueryDetailModel p_ParConfigQueryDetailModel,int p_PageNumber,int p_StepRow)
	{
		IDataBase m_dbConsole = GetDataBase.createDataBase();
		StringBuffer strSqlBuffer=new StringBuffer();
		List m_List=null;
		Statement stm=null;
		ResultSet m_ObjRs =null;
		strSqlBuffer.append(this.getSelectSql());
		strSqlBuffer.append(" where 1=1 ");
		if(p_ParConfigQueryDetailModel!=null)
			strSqlBuffer.append(p_ParConfigQueryDetailModel.getWhereSql(""));
		String strSql="";
		//分页
		if(p_PageNumber>0)
			strSql=PageControl.GetSqlStringForPagination(strSqlBuffer.toString(),m_dbConsole.getDatabaseType(),p_PageNumber,p_StepRow);
		else
			strSql=strSqlBuffer.toString();
			
		String strOrder="";
		if(p_ParConfigQueryDetailModel!=null)
			p_ParConfigQueryDetailModel.getOrderbyFiledNameString();
		
		strOrder=p_ParConfigQueryDetailModel.getOrderbyFiledNameString();
		if(strOrder.trim().equals(""))
			strOrder=" order by sortid ";
		//排序
		strSql+=strOrder;
		
		try{
			stm=m_dbConsole.GetStatement();
			m_ObjRs = m_dbConsole.executeResultSet(stm, strSql);
			m_List=ConvertRsToList(m_ObjRs);	
		}catch(Exception ex)
		{
			System.err.println("ConfigQueryDetail.GetList 方法"+ex.getMessage());
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
    public boolean insert(ParConfigQueryDetailModel p_ParConfigQueryDetailModel)
    {
        IDataBase m_dbConsole = GetDataBase.createDataBase();
        StringBuffer strSqlBuffer=new StringBuffer();
        int insertcount=0;
        Statement stm=null;
        if(p_ParConfigQueryDetailModel!=null)
        {
            strSqlBuffer.append("insert into CONFIGQUERYDETAIL(CODE,FIELDID,FIELDNAME,FIELDDISPLAYNAME,FIELDTYPE," +
                    "FIELDDISPLAYTYPE,COLSPAN,ROWSPAN,LOGICEXP,SORTID,DEFAULTVALUE1,DEFAULTVALUE2,PARCLASSNAME," +
                    "DEFAULTVALUE,ISNEWLINE) values('");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getCode());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getFieldid());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getFieldname());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getFielddisplayname());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getFieldtype());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getFielddisplaytype());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getColspan());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getRowspan());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getLogicexp());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getSortid());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getDefaultvalue1());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getDefaultvalue2());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getParclassname());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getDefaultvalue());
            strSqlBuffer.append("','");
            strSqlBuffer.append(p_ParConfigQueryDetailModel.getIsNewLine());
            strSqlBuffer.append("')");
        }
        else
            return false;
        try{
            stm=m_dbConsole.GetStatement();
            insertcount=m_dbConsole.executeNonQuery(stm,strSqlBuffer.toString());
        }catch(Exception ex)
        {
            System.err.println("ConfigQueryDetail.insert方法"+ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try{
                if(stm!=null)
                {
                    stm.close();
                }
            }catch(Exception ex)
            {
                System.err.println(ex.getMessage());
            }
            m_dbConsole.closeConn();
        }
        if(insertcount>0)
            return true;
        else
            return false;
    }
    public int update(ParConfigQueryDetailModel p_ParConfigQueryDetailModel)
    {
        IDataBase m_dbConsole = GetDataBase.createDataBase();
        StringBuffer strSqlBuffer=new StringBuffer();
        Statement stm=null;
        int updatecount=0;
        if(p_ParConfigQueryDetailModel!=null)
        {
            strSqlBuffer.append("update CONFIGQUERYDETAIL set FIELDID='"+p_ParConfigQueryDetailModel.getFieldid()+"'");       
            strSqlBuffer.append(",FIELDNAME='"+p_ParConfigQueryDetailModel.getFieldname()+"'");
            strSqlBuffer.append(",FIELDDISPLAYNAME='"+p_ParConfigQueryDetailModel.getFielddisplayname()+"'");
            strSqlBuffer.append(",FIELDTYPE='"+p_ParConfigQueryDetailModel.getFieldtype()+"'");
            strSqlBuffer.append(",FIELDDISPLAYTYPE='"+p_ParConfigQueryDetailModel.getFielddisplaytype()+"'");
            strSqlBuffer.append(",COLSPAN='"+p_ParConfigQueryDetailModel.getColspan()+"'");
            strSqlBuffer.append(",ROWSPAN='"+p_ParConfigQueryDetailModel.getRowspan()+"'");
            strSqlBuffer.append(",LOGICEXP='"+p_ParConfigQueryDetailModel.getLogicexp()+"'");
            strSqlBuffer.append(",SORTID='"+p_ParConfigQueryDetailModel.getSortid()+"'");
            strSqlBuffer.append(",DEFAULTVALUE1='"+p_ParConfigQueryDetailModel.getDefaultvalue1()+"'");
            strSqlBuffer.append(",DEFAULTVALUE2='"+p_ParConfigQueryDetailModel.getDefaultvalue2()+"'");
            strSqlBuffer.append(",PARCLASSNAME='"+p_ParConfigQueryDetailModel.getParclassname()+"'");
            strSqlBuffer.append(",DEFAULTVALUE='"+p_ParConfigQueryDetailModel.getDefaultvalue()+"'");
            
            strSqlBuffer.append(",ISNEWLINE='"+p_ParConfigQueryDetailModel.getIsNewLine()+"'");
            
            strSqlBuffer.append(" where CODE='"+p_ParConfigQueryDetailModel.getCode()+"'");
            strSqlBuffer.append(" and FIELDID='"+p_ParConfigQueryDetailModel.getFieldid()+"'");
            strSqlBuffer.append(" and FIELDNAME='"+p_ParConfigQueryDetailModel.getFieldname()+"'");
            
 
        }
        else
            return updatecount;
        System.out.println(strSqlBuffer);
        try{
            stm=m_dbConsole.GetStatement();
            updatecount=m_dbConsole.executeNonQuery(stm,strSqlBuffer.toString());
        }catch(Exception ex)
        {
            System.err.println("ConfigQueryDetail.update方法"+ex.getMessage());
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
        return updatecount;
    }
    
    public int Delete(List p_ParConfigQueryDetailModelList)
    { 
    	int deletecount=0;
    	IDataBase m_dbConsole = GetDataBase.createDataBase();
    	String strSql;
        Statement stm=null;
        try{
        	
            stm=m_dbConsole.GetStatement();
            int rowCount=0;
            if(p_ParConfigQueryDetailModelList!=null)
            	rowCount=p_ParConfigQueryDetailModelList.size();
            
            for(int j=0;j<rowCount;j++)
            {
            	strSql="delete from CONFIGQUERYDETAIL where 1=1 ";
            	ParConfigQueryDetailModel m_ParConfigQueryDetailModel=(ParConfigQueryDetailModel)p_ParConfigQueryDetailModelList.get(j);
            	strSql+=m_ParConfigQueryDetailModel.getWhereSql("");
            	deletecount+=m_dbConsole.executeNonQuery(stm,strSql.toString());
            }
        }catch(Exception ex)
        {
            System.err.println("ConfigQueryDetail.update方法"+ex.getMessage());
            ex.printStackTrace();
        }
        finally{
            try{
                if(stm!=null)
                    stm.close();
            }catch(Exception ex)
            {
                System.err.println(ex.getMessage());
            }
            m_dbConsole.closeConn();
        }    	
    	return deletecount;
    }
    
    public boolean delete(String code)
    {
        IDataBase m_dbConsole = GetDataBase.createDataBase();
        StringBuffer strSqlBuffer=new StringBuffer();
        Statement stm=null;
        int deletecount=0;
        if(code!=null)
        {
            strSqlBuffer.append("delete from CONFIGQUERYDETAIL where CODE='");
            strSqlBuffer.append(code);
            strSqlBuffer.append("'");
        }
        else
            return false;
        try{
            stm=m_dbConsole.GetStatement();
            deletecount=m_dbConsole.executeNonQuery(stm,strSqlBuffer.toString());
        }catch(Exception ex)
        {
            System.err.println("ConfigQueryDetail.update方法"+ex.getMessage());
            ex.printStackTrace();
        }
        finally{
            try{
                if(stm!=null)
                    stm.close();
            }catch(Exception ex)
            {
                System.err.println(ex.getMessage());
            }
            m_dbConsole.closeConn();
        }
        if(deletecount>0)
            return true;
        else
            return false;
    }
	public String getSelectSql()
	{
		StringBuffer strSql=new StringBuffer();
		strSql.append(" select code, fieldid, fieldname, fielddisplayname, fieldtype");
		strSql.append(", fielddisplaytype, colspan, rowspan, logicexp, sortid, defaultvalue1");
		strSql.append(", defaultvalue2, parclassname, defaultvalue,isnewline");
		strSql.append(" from configquerydetail ");
		return strSql.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ParConfigQueryDetailModel m_ParConfigQueryDetailModel=new ParConfigQueryDetailModel();
        ConfigQueryDetail m_ConfigQueryDetail=new ConfigQueryDetail();
        m_ParConfigQueryDetailModel.setCode("qqqqqq");
        m_ConfigQueryDetail.update(m_ParConfigQueryDetailModel);
	}

}
