package cn.com.ultrapower.eoms.user.kpi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.ultrapower.eoms.user.comm.function.Function;
import cn.com.ultrapower.eoms.user.database.DataBaseFactory;
import cn.com.ultrapower.eoms.user.database.IDataBase;

public class FindKpiInfo
{
    StringBuffer lastbuff = new StringBuffer();
	/**
	 * 日期 2007-5-14
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
    }
	
	/**
	 * 日期 2007-5-23
	 * @author xuquanxing 
	 * @param sql
	 * @param strbuffer
	 * @param flag
	 * @param tablename
	 * @return
	 * @throws SQLException StringBuffer
	 * 查询并返回最终显示结果
	 */
	public StringBuffer getInterconInfo(String sql,StringBuffer strbuffer,String flag,String tablename,String lidu,String notifysql) throws SQLException
	{
		//实例化一个类型为接口IDataBase类型的工厂类
		System.out.println("FindKpiInfo+sql="+sql);
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		ResultSet     res		   = null;
		ResultSet     re		   = null;
		String        sqlstr       = "";
		StringBuffer  buf          = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		//************
		sqlstr                     = "select t.kpinationsmsname,t.kpinationsmsbelongnename,t.ownerflag,t.memo1,t.tablename from kpi_info_config t where t.ownerflag='"+flag+"'";
		res                        = dataBase.executeResultSet(stm,sqlstr);
		int n    =0;
		int column=0;
		try
		{
/*			while(res.next())
			{
				lastbuff.append("<tr>");
				lastbuff.append("<td  bgcolor='#FF0000' align='center' colspan=='10' nowrap>");
				lastbuff.append(res.getString("kpinationsmsname"));
				lastbuff.append("</td>");
				lastbuff.append("</tr>");
				lastbuff.append(strbuffer.toString());
				buf = tableContentDeal(sql,res.getString("kpinationsmsbelongnename"),tablename);//取得唯一标识 ;
				lastbuff.append(buf.toString());
			}*/
			while(res.next())
			{
			    column    = res.getInt("memo1");//取得所占列数
				System.out.println("column="+column);
				lastbuff.append("<tr class='tabletitle'  border='0' cellpadding='0' cellspacing='1'>");
				lastbuff.append("<td  align='center' colspan='"+column+"'>");
				lastbuff.append(DeleteNull.dealNull(res.getString("kpinationsmsname")));
				lastbuff.append("</td>");
				lastbuff.append("</tr>");
				lastbuff.append(strbuffer.toString());
				buf = tableContentDeal(sql,res.getString("kpinationsmsbelongnename"),tablename,lidu);//取得唯一标识 ;
				lastbuff.append(buf.toString());
			}
			String notifystr = getNotifyInfo(flag,lidu,notifysql);
			if(notifystr.equals(""))
			{
				lastbuff.append("<tr backcolor='red'>");
				lastbuff.append("<td  align='center' colspan='"+column+"'>");
				lastbuff.append(" (备注)0-日上报,1-月上报,2-时上报");
				lastbuff.append("</td>");
				lastbuff.append("</tr>");
			}else
			{
				lastbuff.append("<tr backcolor='red'>");
				lastbuff.append("<td  align='center' colspan='"+column+"'>");
				lastbuff.append(" (备注)1缺失指标集信息:"+notifystr+" 2注:0-日上报,1-月上报,2-时上报");
				lastbuff.append("</td>");
				lastbuff.append("</tr>");
			}
		}catch(Exception e)
		{
			
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return lastbuff;
}

	/**
	 * 日期 2007-5-15
	 * @author xuquanxing 
	 * @param sql
	 * @param dataBase
	 * @param stm
	 * @param buff
	 * @param benename void
	 * 表中显示的内容
	 */
	private StringBuffer tableContentDeal(String sql,String enname,String tablename,String lidu)
	{
		IDataBase dataBase    = DataBaseFactory.createDataBaseClassFromProp();
		Statement stm         = null;
		stm                   = dataBase.GetStatement();
		StringBuffer buff     = new StringBuffer();
		String tempsql        = "";
		tempsql               = sql + " and belongnename='" + enname + "'";
		ResultSet rs          = dataBase.executeResultSet(stm, tempsql);
		try
		{
			ResultSetMetaData metadata = rs.getMetaData();// 取得元数据对象
			int columnnum = metadata.getColumnCount();// 取得列数
			while (rs.next())
			{
				String id = "";
				// condir = rs.getString("condir");
				buff.append("<tr class='tablecontent'>");
				for (int i = 1; i <= columnnum; i++)
				{
					String columnname = metadata.getColumnName(i);
					System.out.println("columnname=" + columnname);
					String columnvalue =DeleteNull.dealNull(rs.getString(i));
					// if(columnname.equalsIgnoreCase("nename")||columnname.equalsIgnoreCase("condir")||columnname.equalsIgnoreCase("KPILIDU")||columnname.equalsIgnoreCase("BELONGNENAME")||columnname.equalsIgnoreCase("memo1"))
					if (columnname.equalsIgnoreCase("memo1"))
					{
						continue;
					} else if (columnname.equalsIgnoreCase("ID"))
					{
						id = columnvalue;
						continue;
					} else if (columnname.equalsIgnoreCase("time"))
					{
						String timelidu = "";
						if (lidu.equals("0"))
						{
							timelidu = DateOperation.secondTo_Day(columnvalue);
						} else if (lidu.equals("1"))
						{
							timelidu = DateOperation.secondTo_Month(columnvalue);
						} else if (lidu.equals("2"))
						{
							timelidu = DateOperation.secondTo_Hour(columnvalue);
						} else
						{
							timelidu = DateOperation.DateToUinx_datetime(columnvalue);
						}

						buff.append("<td >" + timelidu + "</td>");
					} else if (columnname.equalsIgnoreCase("BELONGNENAME"))
					{
						System.out.println("=====godgod=" + columnname);
						buff.append("<td style='display:none'>" + columnvalue + "</td>");
					} else
					{
						buff.append("<td >" + columnvalue + "</td>");
					}
				}
				buff.append("<td><a href='kpiedit.jsp?tablename=" + tablename + "&editid=" + id
						+ "' class='tablelink'>编辑</a>");
				// buff.append("|<a
				// href='kpidelete.jsp?tablename="+tablename+"&deleteid="+id+"'
				// class='tablelink'>删除</a></td>");
				buff.append("</tr>");
			}
			//rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally 
		{ 
			Function.closeDataBaseSource(rs,stm,dataBase); 
		}

		return buff;
	}
	public String getNotifyInfo(String numflag,String lidu,String sqlstr) throws SQLException
	{
		// 实例化一个类型为接口IDataBase类型的工厂类
		System.out.println("sqlstr="+sqlstr);
		if(sqlstr.equals(""))
		{
			return "";
		}
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		ResultSet     res		   = null;
		ResultSet     re		   = null;
		String        laststr      = "";
		StringBuffer  buf          = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		//************
		res                        = dataBase.executeResultSet(stm,sqlstr);
		int n    =0;
		try
		{
			while(res.next())
			{
				if(laststr.equals(""))
				{
					laststr = DeleteNull.dealNull(res.getString(1));	
				}else
				{
					laststr =laststr+","+ DeleteNull.dealNull(res.getString(1));
				}

			}
		}catch(Exception e)
		{
			
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return laststr;
}
	/**
	 * 日期 2007-5-24
	 * @author xuquanxing 
	 * @param key
	 * @return
	 * @throws SQLException String
	 * 取得唯一标识
	 */
	public String getNenameOrCondir(String sqlstr) throws SQLException
	{
		// 实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		ResultSet     res		   = null;
		ResultSet     re		   = null;
		String        laststr      = "";
		StringBuffer  buf          = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		//************
		res                        = dataBase.executeResultSet(stm,sqlstr);
		int n    =0;
		try
		{
			while(res.next())
			{
				laststr = DeleteNull.dealNull(res.getString("kpinationsmsbelongnename"));
			}
		}catch(Exception e)
		{
			
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return laststr;
}
	
	
	public List getSpecialName(String numflag)
	{		// 实例化一个类型为接口IDataBase类型的工厂类
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		ResultSet     res		   = null;
		ResultSet     re		   = null;
		List          list         = new ArrayList();
		String        laststr      = "";
		stm	                       = dataBase.GetStatement();
		String        sqlstr       = "SELECT KPINATIONSMSNAME FROM KPI_INFO_CONFIG T WHERE T.OWNERFLAG='"+numflag+"'";
		//************
		res                        = dataBase.executeResultSet(stm,sqlstr);
		try
		{
			while(res.next())
			{
				laststr = DeleteNull.dealNull(res.getString("KPINATIONSMSNAME"));
				list.add(laststr);
			}
		}catch(Exception e)
		{
			
		}finally
		{
			Function.closeDataBaseSource(res,stm,dataBase);
		}
		return list;
	}
 }
