package cn.com.ultrapower.eoms.user.userinterface;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAttributeInfo;
import cn.com.ultrapower.eoms.user.userinterface.bean.SourceAttributePram;

public class SourceAttributeInterfaceSQL
{
	static final Logger logger = (Logger) Logger.getLogger(SourceAttributeInterfaceSQL.class);
	/**
	 * 根据资源属性信息Bean 查询资源ID，资源属性及资源属性值。
	 * 日期 2007-1-25
	 * @author wangyanguang
	 */
	public List getSourceAttributeInfo(SourceAttributePram sourcepram)
	{
		List returnList = new ArrayList();
		String sourceid = "";
		if(sourcepram==null)
		{
			return null;
		}
		else
		{
			sourceid = Function.nullString(sourcepram.getSourceid());
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" select sourcetable.source_id,");
		sql.append(" atttable.sourceatt_cnname,");
		sql.append(" attvaluetable.sourceattvalue_value");
		sql.append(" from sourceconfig sourcetable,");
		sql.append(" sourceconfigattribute atttable,");
		sql.append(" sourceattributevalue  attvaluetable");
		sql.append(" where sourcetable.source_id = atttable.sourceatt_sourceid");
	    sql.append(" and atttable.sourceatt_id = attvaluetable.sourceattvalue_attid");  
	    if(!sourceid.equals(""))
	    {
	    	sql.append(" and sourcetable.source_id='"+sourceid+"'");
	    }
	    
	    logger.info("根据资源ID查询资源属性及属性值SQL："+sql.toString());
	    
		IDataBase dataBase	= null;
		dataBase			= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= dataBase.GetStatement();
		ResultSet rs		= dataBase.executeResultSet(stm,sql.toString());
		
		try
		{
			while(rs.next())
			{
	 	    	String source_id    = Function.nullString(rs.getString("source_id"));
	 	    	String attname		= Function.nullString(rs.getString("sourceatt_cnname"));
	 	    	String attvalue		= Function.nullString(rs.getString("sourceattvalue_value"));
	 	    	logger.info("资源ID："+source_id+",资源名称："+attname+",ulr:"+attvalue);
 	    	
	 	    	if(!source_id.equals("")&&!attname.equals(""))
	 	    	{
	 	    		SourceAttributeInfo sourceinfo = new SourceAttributeInfo();
	 	    		sourceinfo.setAttvalue(attvalue);
	 	    		sourceinfo.setSourceattname(attname);
	 	    		sourceinfo.setSourceid(source_id);
	 	    		returnList.add(sourceinfo);
	 	    	}
			}
			rs.close();
			stm.close();
			dataBase.closeConn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(returnList!=null)
		{
			return returnList;
		}else
		{
			return null;
		}

	}

}
