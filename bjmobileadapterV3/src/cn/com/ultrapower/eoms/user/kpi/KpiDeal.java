package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class KpiDeal
{

	/**
	 * 日期 2007-2-9
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		//KpiDeal  kpi =  new KpiDeal();
		//kpi.getGsmCurrentIndex();
	}
	
	public String findKpiData()
	{
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		= null;
		ResultSet     rs		= null;
		StringBuffer  buff      = new StringBuffer();
		String        sql       = "select distinct * from kpigsm order by TIME desc";
		stm	                    = dataBase.GetStatement();
		//获得数据库查询结果集
		rs	            = dataBase.executeResultSet(stm,sql);
		List cnnamelist = new ArrayList();
		try
		{
			if(rs.next())
			{
				buff.append("<tr class='tabletitle'>");
				ResultSetMetaData metadata = rs.getMetaData();
				int columnnum = metadata.getColumnCount();
				for(int i=1; i<columnnum; i++)
				{
					String columnname = metadata.getColumnName(i);//取得列名称
					if((!columnname.equals("ID"))&&(!columnname.equals("TIME"))&&(!columnname.equals("BELONGNENAME")))
					{
						String cnname     = this.getColumnCnName(columnname,"kpigsm");//取得该列对应的中文名
						System.out.println("====cnname="+columnname);
						cnnamelist.add(cnname);
						System.out.println("cnname="+cnname);
						buff.append("<td>");
						buff.append(cnname);
						buff.append("</td>");
					}else
					{
						continue;
					}
				}
				buff.append("<td>时间粒度</td>");
				buff.append("</tr>");
			}else
			{
				return null;
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		try
		{ if(rs.next())
		    {
				do
				{
					String tottraffic      = rs.getString("TOTTRAFFIC");        //忙时话音信道总话务量
					String droptchnbr      = rs.getString("DROPTCHNBR");        //忙时话音信道掉话总次数（含切换
					String trafficdroprate = rs.getString("TRAFFICDROPRATE");   //话务掉话比
					String attsdcchnbr     = rs.getString("ATTSDCCHNBR");       //SDCCH试呼总次数
					String ovrsdcchnbr     = rs.getString("OVRSDCCHNBR");       //SDCCH溢出总次数
					String ovrtchnhonbr    = rs.getString("OVRTCHNHONBR");      //话音溢出总次数（不含切换）
					String atttchnhonbr    = rs.getString("ATTTCHNHONBR");      //话音信道试呼总次数（不含切换）
					String radiosucrate    = rs.getString("RADIOSUCRATE");      //无线接通率
					String kpilidu         = rs.getString("kpilidu");           //kpi的时间粒度0：日上报 1：月上报 2;时上报
					String time            = rs.getString("time");
					String timelidu        = "";
					if(kpilidu.equals("0"))
					{
						timelidu = DateOperation.secondTo_Day(time);
					}else if(kpilidu.equals("1"))
					{
						timelidu = DateOperation.secondTo_Month(time);
					}else if(kpilidu.equals("2"))
					{
						timelidu = DateOperation.secondTo_Hour(time);
					}
					buff.append("<tr class='tablecontent'>");
					buff.append("<td>"+tottraffic+"</td>");
					buff.append("<td>"+droptchnbr+"</td>");
					buff.append("<td>"+trafficdroprate+"</td>");
					buff.append("<td>"+attsdcchnbr+"</td>");
					buff.append("<td>"+ovrsdcchnbr+"</td>");
					buff.append("<td>"+ovrtchnhonbr+"</td>");
					buff.append("<td>"+atttchnhonbr+"</td>");
					buff.append("<td>"+radiosucrate+"</td>");
					buff.append("<td>"+timelidu+"</td>");
					buff.append("</tr>");
				}while(rs.next());  
		    }else
		    {
		    	// do nothing
		    }
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return buff.toString();
	}
	
	public String getColumnCnName(String columnenname, String tablename)
	{
//		实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		String fieldcnname  = null;
		String fielaenname  = null;
		stm	= dataBase.GetStatement();
		String sql = "select kpi.kpi_cnname from kpiconfig kpi where lower(kpi.kpi_enname)=lower('"+columnenname+"') and  lower(kpi.KPI_TABLENAME)=lower('"+tablename+"')";
		//获得数据库查询结果集
		System.out.println("mysql="+sql);
		rs	= dataBase.executeResultSet(stm,sql);
		try
		{
			while(rs.next())
			{
				fieldcnname = rs.getString("KPI_CNNAME");//中文名
				//fielaenname = rs.getString("kpi_enname");//英文名
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		return fieldcnname;
	}
	
	private Map getGsmCurrentIndex(List cnlist,String ennames, String tablename)
	{
		if(cnlist==null)
		{
			return null;
		}
		if(ennames==null)
		{
			return null;
		}
		if(tablename==null)
		{
			return null;
		}
        String currentday   = "";
        String beforday      = "";
        String tempday      = "";
		System.out.println("ennames"+ennames);
		System.out.println("tablename"+tablename);
		String        sql     = "";
		String[] tablenandkey = tablename.split("#");
		//实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		= null;
		ResultSet     rs		= null;
		Map           mapinfo   = new HashMap();
		if(tablenandkey.length==2)
		{
            currentday   = DateOperation.yearMonthDayToMillsecond(DateOperation.millsecondToYearMonthDay());
            beforday      = DateOperation.yearMonthDayToMillsecond(DateOperation.getInternalDate(-500));//取得下一天的日期 00:00:00
			System.out.println("tablenamesfsdfsdafs"+tablenandkey[1]);
			sql       = "select distinct "+ennames+", time from "+tablenandkey[0]+"  k where k. belongnename='"+tablenandkey[1]+"' and k.kpilidu='0' and k.time>='"+beforday+"' and  k.time<'"+currentday+"'";
		}else
		{
			//按照时间降序排列
            currentday   = DateOperation.yearMonthDayToMillsecond(DateOperation.millsecondToYearMonthDay());
            beforday      = DateOperation.yearMonthDayToMillsecond(DateOperation.getInternalDate(-500));//取得下一天的日期 00:00:00
			sql     = "select distinct "+ennames+" ,time from "+tablenandkey[0]+"  k  where  k.kpilidu='0' and k.time>='"+beforday+"' and  k.time<'"+currentday+"'";
		}
		System.out.println(" sql="+ sql);
		stm	            = dataBase.GetStatement();
		//获得数据库查询结果集
		rs	            = dataBase.executeResultSet(stm,sql);
		List cnnamelist = new ArrayList();
		List conindex   = new ArrayList();
		try
		{
			if(rs!=null&&rs.next())//只取一条
			{
				ResultSetMetaData metadata = rs.getMetaData();
				int columnnum = metadata.getColumnCount();//取得总列数
				for(int i=1; i<columnnum; i++)
				{
					String columnname = metadata.getColumnName(i);//取得列名称
					if(columnname.equalsIgnoreCase("neName")||columnname.equalsIgnoreCase("conDir")||columnname.equals("ID")||columnname.equalsIgnoreCase("time")||columnname.equalsIgnoreCase("kpilidu")||columnname.equalsIgnoreCase("belongnename"))
					//	if((!columnname.equalsIgnoreCase("NENAME"))&&(!columnname.equals("ID"))&&(!columnname.equals("TIME"))&&(!columnname.equals("BELONGNENAME")))
					{
						continue;
					}else
					{
						conindex.add(rs.getString(i));
					}
				}
			}else
			{
				return null;
			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		if(conindex.size()>0&&cnlist.size()>0)
		{
			for(int j=0;j<conindex.size(); j++)
			{
				mapinfo.put(cnlist.get(j),conindex.get(j));
			}
		}
		return mapinfo;
	}
	
	public Map getColumnInfo(String tablename)
	{
        if(tablename==null||tablename.equals(""))
        {
        	return null;
        }
		//		实例化一个类型为接口IDataBase类型的工厂类
		IDataBase dataBase	= DataBaseFactory.createDataBaseClassFromProp();
		Statement stm		= null;
		ResultSet rs		= null;
		String fieldcnname  = null;
		String fielaenname  = null;
		String indexenname  = "";//指标英文名
		String indexcnname  = "";//指标中文名
		String tempnamed    = "";
		List   cnnamelist   = new ArrayList();//存储 所有指标中文名字段
		Map    indexinfo    = new HashMap();//存储中文名和其对应值 
		String[] tablenandkey = tablename.split("#");
		stm	                  = dataBase.GetStatement();
		String sql = "select kpi.KPI_CNNAME,kpi.KPI_ENNAME from kpiconfig kpi where lower( kpi.KPI_TABLENAME)= lower('"+tablenandkey[0]+"') order by kpi_id";
	    System.out.println("testsql="+sql);
		rs	= dataBase.executeResultSet(stm,sql);
		try
		{
			while(rs.next())
			{
				tempnamed = rs.getString("KPI_ENNAME");
				if(tempnamed.equalsIgnoreCase("neName")||tempnamed.equalsIgnoreCase("conDir")||tempnamed.equalsIgnoreCase("time")||tempnamed.equalsIgnoreCase("kpilidu")||tempnamed.equalsIgnoreCase("belongnename"))
				{
					continue;
				}else if(indexenname.equals(""))
				{
					indexenname = tempnamed;
				}else
				{
					indexenname = indexenname + ","+ rs.getString("KPI_ENNAME");
				}
				indexcnname = rs.getString("KPI_CNNAME");
			//	if(!indexcnname.equals("名称"))
			//	{
					cnnamelist.add(indexcnname);
			//	}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		indexinfo = getGsmCurrentIndex(cnnamelist,indexenname,tablename);
		System.out.println("system info="+indexinfo);
		return indexinfo;
	}
}
