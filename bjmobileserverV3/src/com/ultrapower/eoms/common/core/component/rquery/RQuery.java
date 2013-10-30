package com.ultrapower.eoms.common.core.component.rquery;

import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlQuery;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;

public class RQuery {

	SqlResult sqlResult=null;
	private DataTable dt=null;
	private int pageSize=0;
	private int page=0;
	private int isCount=0;
	private int pageCount=0;
	private int rowCount=0;
	SqlQuery sqlQuery=null;
	
	public int getQueryResultSetCount()
	{
		return this.rowCount;
	}
	
	public RQuery(final String sqlName,final HashMap p_indirectValues)
	{
		//XmlParser xmlParse=null;
		HashMap indirectValues=p_indirectValues;
		if(indirectValues==null)
			indirectValues=new HashMap();
		Object obj=null;
		if(StartUp.sqlmapElement!=null)
			obj=StartUp.sqlmapElement.get(sqlName);
		if(obj!=null)
		{
			Element sqlqueryElement=(Element)obj;
			sqlQuery=new SqlQuery(sqlqueryElement,indirectValues,true);
		}
	}
	public DataTable getDataTable()
	{
		if(dt==null)
		{
			if(sqlQuery!=null)
			{
				sqlResult=sqlQuery.getSql();
				if(sqlResult!=null)
				{
					Object[] values=null;
					
					List list=sqlResult.getValues();
					int size=0;
					if(list !=null)
					{
						size=list.size();
					}
					if(size>0)
					{
						values = (Object[])list.toArray(new String[size]);
					}
					QueryAdapter queryAdapter=new QueryAdapter();
					dt=queryAdapter.executeQuery(sqlResult.getSql(), values, this.getPage(), this.getPageSize(), this.getIsCount());
					this.setPageCount(queryAdapter.getPageCount());
					this.rowCount=queryAdapter.getQueryResultSetCount();
				}
			}
			
		}
		return dt;
	}
	public int getPageSize() 
	{
		return pageSize;
	}

	public void setPageSize(int pageSize) 
	{
		this.pageSize = pageSize;
	}

	public int getPage() 
	{
		return page;
	}

	public void setPage(int page) 
	{
		this.page = page;
	}

	public int getIsCount() 
	{
		return isCount;
	}

	public void setIsCount(int isCount)
	{
		this.isCount = isCount;
	}

	public int getPageCount() 
	{
		return pageCount;
	}

	public void setPageCount(int pageCount) 
	{
		this.pageCount = pageCount;
	}
	/**
	 * 返回查询的sql对象
	 * @return
	 */
	public SqlResult getSqlResult()
	{
		return sqlResult;
	}
	
}

