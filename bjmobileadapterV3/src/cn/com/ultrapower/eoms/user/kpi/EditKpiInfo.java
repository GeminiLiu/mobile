package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class EditKpiInfo
{
    public Map editKpi(String tablename ,String id)
    {
		if(tablename==null||id==null)
		{
			return null;
		}
    	IDataBase      dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement      stm		   = null;
		ResultSet      res          = null;
	    FindTableTitle op          = new FindTableTitle();
	    String        dealflag      = "2";
		String        sql          = "select * from "+tablename+" t where t.id='"+id+"'";
		stm	                       = dataBase.GetStatement();
		List          accordvalue  = new ArrayList();
		String[]      column       = null;
	    try
		{
			StringBuffer buff = op.tableTitleDeal(sql,tablename,dealflag);
			column            = buff.toString().split(",");//取得列中文名
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}	
		res  = dataBase.executeResultSet(stm,sql);
		try
		{
			ResultSetMetaData metadata = res.getMetaData();//取得元数据对象
			int columnnum              = metadata.getColumnCount();//取得列数
			if(res.next())
			{
				for(int i=1; i<=columnnum;i++)
				  {
					  String columnname  = metadata.getColumnName(i);
					  String columnvalue = Function.nullString(res.getString(i));
					//  if(columnname.equalsIgnoreCase("nename")||columnname.equalsIgnoreCase("condir")||columnname.equalsIgnoreCase("KPILIDU")||columnname.equalsIgnoreCase("BELONGNENAME")||columnname.equalsIgnoreCase("memo1"))
					  if(columnname.equalsIgnoreCase("memo1"))
					  {
							continue;
					  }else if(columnname.equalsIgnoreCase("ID"))
					  {
						  id = columnvalue;
						  continue;
					  }
					  /*else if(columnname.equalsIgnoreCase("time"))
					  {
						  String timelidu    = "";
						  System.out.println("columnvalue="+columnvalue);
						  timelidu = DateOperation.secondTo_Month(columnvalue);
						  accordvalue.add(timelidu);
					  }*/else
					  {
						  accordvalue.add(columnvalue);
					  }
				  }
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		//将字段名称和值以key/vaule的形式保存 并返回 
		Map  map = new HashMap();
		for(int j=0; j<column.length; j++)
		{
			if(column[j]!=null&&!column[j].equals(""))
			{
				//if(accordvalue.get(j)!=null&&!accordvalue.get(j).equals(""))
				//{
					map.put(column[j],accordvalue.get(j));
				//}
			}
		}
		return map;
    }
    
    
    
    /**
     * 日期 2007-5-17
     * @author xuquanxing 
     * @param  tablename
     * @return List          根据传入的表名查询该表,返回各自段对应的中文名
     */
    public List createKpi(String tablename)
    {
		if(tablename==null)
		{
			return null;
		}
	    FindTableTitle op           = new FindTableTitle();
	    String         dealflag     = "2";
		String         sql          = "select * from "+tablename+" t ";
		List           accordvalue  = new ArrayList();
		String[]       column       = null;
	    try
		{
			StringBuffer buff = op.tableTitleDeal(sql,tablename,dealflag);
			column            = buff.toString().split(",");//取得列中文名
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}	
		List  list = new ArrayList();
		for(int j=0; j<column.length; j++)
		{
			if(column[j]!=null&&!column[j].equals(""))
			{
				list.add(column[j]);
			}
		}
		return list;
    }

}
