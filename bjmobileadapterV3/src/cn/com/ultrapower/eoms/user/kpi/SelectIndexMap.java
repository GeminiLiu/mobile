package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class SelectIndexMap
{
 private static final long interday = 7*24*60*60*1000; //查询一周内的指标情况

	/**
	 * 日期 2007-4-23
	 * @author xuquanxing 
	 * @param charttype            kpichart type
	 * @param kpi_danweiname       kpi      unit
	 * @param kpi_tablename        kpi      tablen name
	 * @param lidu                 kpi      timelidu
	 * @return Map
	 */
	public Map getIndexMap(String charttype,String kpi_danweiname, String kpi_tablename,String lidu,String tablename,String belongnename)
	{
	    System.out.println("选择数据开始。。。。");
		 IDataBase     dataBase	= DataBaseFactory.createDataBaseClassFromProp();
	     Statement     stm		= null;
		 ResultSet     rs		= null;
		 ResultSet     res		= null;
		 List capvalues         = new ArrayList();
		 List list              = new ArrayList();
		 List cnnamelist        = new ArrayList();
		 Map lastindexinfo      = new HashMap();//store the key= indexname value=map(indextime,indexvalue)
		 long          currenttime = System.currentTimeMillis();
		 //从配置表中查要显示的指标的sql
		 StringBuffer  barsql      = new StringBuffer();
		 String        indexname   = "";
		 barsql.append(" select * from kpiconfig k")
		       .append(" where  k.KPI_TABLENAME = '"+kpi_tablename+"'")
		       .append(" and k.KPI_CHARTTYPE    = '"+charttype+"'")
		       .append(" and k.KPI_DANWEINAME   = '"+kpi_danweiname+"'");
		 System.out.println("lastsql="+barsql.toString());
		 stm	   = dataBase.GetStatement();
		 rs        = dataBase.executeResultSet(stm,barsql.toString());
		 try
		 {
		 	 while(rs.next())
		    {
			     list.add(rs.getString("KPI_ENNAME"));//the index enname
			     cnnamelist.add(rs.getString("KPI_CNNAME"));//the index cnname
			     if(indexname.equals(""))
			     {
			         indexname = rs.getString("KPI_ENNAME");
			     }else
			     {
			         indexname =indexname+","+rs.getString("KPI_ENNAME");
			     }
		     }
		     rs.close();
		 }catch(SQLException e)
		 {
		    e.printStackTrace();
		 }
		 //根据时间粒度和belongnename查询应该在图中显示的字段,indexname,是从kpiconfig中查出的 属于某类图的指标
		  try
		 {
		    StringBuffer buff= new StringBuffer();
		    if(!belongnename.equals(""))
		    {
			    buff.append("select "+ indexname +" ,time from  "+tablename+" kpi where ("+currenttime+" -to_number(kpi.TIME))>=")
		            .append(interday)
		            .append(" and kpi.kpilidu='"+lidu+"' and kpi.belongnename='"+belongnename+"'"); 
		    }else
		    {
			    buff.append("select "+ indexname +" ,time from  "+tablename+" kpi where ("+currenttime+" -to_number(kpi.TIME))>=")
		            .append(interday)
		            .append(" and kpi.KPILIDU='"+lidu+"'"); 
		    }
		    buff.append(" order by time desc");
		  System.out.println("finalsql="+buff.toString());
		  res                  	= dataBase.executeResultSet(stm,buff.toString());
		  while(res.next())
		  {
			 List values                = new ArrayList();//保存每条记录
			 ResultSetMetaData metadata = res.getMetaData();
			 int columnnum              = metadata.getColumnCount();
			 String  time               = res.getString("time");
			 for(int k=1;k<=columnnum ;k++)
			 {
			     String  indexvalue = res.getString(k);
			     values.add(indexvalue);
			 }
			 capvalues.add(values);
		 
		 }
		 }catch(Exception e)
		 {
	         e.printStackTrace();
		 }finally
		 {
			 Function.closeDataBaseSource(rs,stm,dataBase);
		 }
		 //将相应字段和其对应的值存入map
		 for(int t=0; t<cnnamelist.size();t++)
		 {
		    String indexnam     = (String)cnnamelist.get(t);//get the kpiindex name
		    Map    indexinfoma  = new LinkedHashMap();
		     for(int n=0;n<capvalues.size();n++)//the first record
		     {
		        ArrayList arlist = (ArrayList)capvalues.get(n);
		        String invalue   = (String)arlist.get(t);//get the kpiindex value
			    System.out.println("invalue＝"+invalue);
		        String indextime = DateOperation.secondTo_Day((String)arlist.get(arlist.size()-1));//get the kpiindex time
			    System.out.println("indextime＝"+indextime);
		        indexinfoma.put(indextime,invalue);
		     }
		     lastindexinfo.put(indexnam,indexinfoma);//the last parameter
		 }
		    System.out.println("选择数据结束 。。。。");
		 return lastindexinfo;
	}
}
