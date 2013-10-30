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

public class FindTableTitle
{

	/**
	 * 日期 2007-5-15
	 * @author xuquanxing 
	 * @param args void
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 日期 2007-5-16
	 * @author xuquanxing 
	 * @param sql
	 * @param tablename                  表名
	 * @param dealflag                   根据此表识判断是编辑还是显示，1，显示，2 编辑
	 * @return
	 * @throws SQLException StringBuffer
	 */
	public StringBuffer  tableTitleDeal(String sql,String tablename,String dealflag) throws SQLException
	{
		System.out.println("godsql="+sql);
		if(sql==null||sql.equals("")||tablename==null||tablename.equals(""))
		{
			return null;
		}
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		String        sqlstr       = "";
		StringBuffer  buff          = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		ResultSet re               = null;
		re	                       = dataBase.executeResultSet(stm,sql);
		//*****元数据操作
		
		ResultSetMetaData metadata = re.getMetaData();//取得元数据对象
		int columnnum              = metadata.getColumnCount();//取得列数
		String[] columnname        = new String[columnnum];
		String   column            = "";
		for(int i =0; i<columnnum; i++)
		{
			column = metadata.getColumnName(i+1);
		//	if(column.equalsIgnoreCase("nename")||column.equalsIgnoreCase("condir")||column.equalsIgnoreCase("ID")||column.equalsIgnoreCase("KPILIDU")||column.equalsIgnoreCase("BELONGNENAME")||column.equalsIgnoreCase("memo1"))
			if(column.equalsIgnoreCase("ID")||column.equalsIgnoreCase("memo1"))
			{
				continue;
			}
			columnname[i]=column;
			System.out.println("column="+columnname[i]);//将列名存入数组
		}try
		{
			re.close();
		}catch(Exception e)
		{
			
		}
		try
		{
			stm.close();
		}catch(Exception e)
		{
			
		}

		Function.closeDataBaseSource(re,stm,dataBase);
		
		//******表头数据******
		List  list = this.getCnname(columnname,tablename);//取得中文名
		int size   = list.size();
		if(dealflag.equals("1"))
		{
    		buff.append("<tr class='tabletitle'>");
			for (int j = 0; j < size; j++)
			{
				String namecn = (String)list.get(j);
				if(namecn.equals("唯一标识"))
				{
					buff.append("<td style='display:none'>");
					buff.append(list.get(j));
					buff.append("</td>");
					continue;
				}
				buff.append("<td>");
				buff.append(list.get(j));
				buff.append("</td>");
			}
			buff.append("<td>");
			buff.append("操作");
			buff.append("</td>");
			buff.append("</tr>");
		}else if(dealflag.equals("2"))//如果为2 返回的是包括字段中文名和英文名的字符串
		{
			String enname[] = getEnName(tablename).split(",");
			for (int j = 0; j < size; j++)
			{
				buff.append(list.get(j)+":"+enname[j]);
				buff.append(",");
			}
		}
		
		System.out.println("mybuff="+buff.toString());
		//System.out.println("hahahh22222"+lastbuff.toString());
		return buff ;
	}
	
	
	
	/**
	 * 日期 2007-5-17
	 * @author xuquanxing 
	 * @param args
	 * @param tablename
	 * @return List
	 */
	public List  getCnname(String[] args,String tablename)
	{
		System.out.println("args="+args.length);
		System.out.println("tablename="+tablename);
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		ResultSet     rs		   = null;
		String        sql          = "";
		stm	                       = dataBase.GetStatement();
		List          list         = new ArrayList();
		try
		{
			for(int i=0; i<args.length; i++)
			{
				if(args[i]!=null)
				{
					sql = "select kpi.kpi_cnname from  kpiconfig kpi where lower(kpi.kpi_tablename)=lower('"+tablename+"') and lower(kpi.kpi_enname)=lower('"+args[i]+"')";
					System.out.println("sql="+sql);
					String cnname   = "";
					rs  = dataBase.executeResultSet(stm,sql);
					try
					{
						if(rs.next())
						{
							cnname          = rs.getString("kpi_cnname");
							System.out.println("cnname="+cnname);
						}
						list.add(cnname);
						rs.close();
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e)
		{
			
		}finally
		{
			Function.closeDataBaseSource(rs,stm,dataBase);
		}
		System.out.println("listsize="+list.size());
		
		return list;
	}
	
	
	
	
	/**
	 * 日期 2007-5-16
	 * @author xuquanxing 
	 * @param strsql
	 * @return String[]
	 * 根据传入的sql取得英文名
	 */
	public String getEnName(String tablename)
	{
		IDataBase     dataBase	   = DataBaseFactory.createDataBaseClassFromProp();
		Statement     stm		   = null;
		String        sqlstr       = "select * from "+tablename;
		StringBuffer  buff         = new StringBuffer();
		stm	                       = dataBase.GetStatement();
		ResultSet re               = null;
		String   columnname        = "";
		re	                       = dataBase.executeResultSet(stm,sqlstr);
		//*****元数据操作
		
		ResultSetMetaData metadata;
		try
		{
			metadata = re.getMetaData();
//			取得元数据对象
			int columnnum              = metadata.getColumnCount();//取得列数
			String   column            = "";
			for(int i =0; i<columnnum; i++)
			{
				column = metadata.getColumnName(i+1);
			//	if(column.equalsIgnoreCase("nename")||column.equalsIgnoreCase("condir")||column.equalsIgnoreCase("ID")||column.equalsIgnoreCase("KPILIDU")||column.equalsIgnoreCase("BELONGNENAME")||column.equalsIgnoreCase("memo1"))
				if(column.equalsIgnoreCase("ID")||column.equalsIgnoreCase("memo1"))
				{
					continue;
				}
				if(columnname.equals(""))
				{
					columnname = column;
				}else
				{
					columnname =columnname+ ","+column;
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			Function.closeDataBaseSource(re,stm,dataBase);
		}
		return columnname;
	}
}
