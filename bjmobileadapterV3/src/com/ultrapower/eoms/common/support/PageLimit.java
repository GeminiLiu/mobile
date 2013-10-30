/**
 * 
 */
package com.ultrapower.eoms.common.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;

public class PageLimit {
	private Limit limit;
	private Integer CURRENT_ROWS_SIZE = 20;
	private Integer totalRows;
	private boolean export;
	private Integer pageSize;
	private Map<String, String> sortValueMap;
	private String sortColumn;
	private String sortDesc;
	private Object entity;
	private Map<String, String> aliasMap = new HashMap<String, String>();
	private Map<String, String> operMap = new HashMap<String, String>();
	private Map<String, String> actualMap = new HashMap<String, String>();

	private static final PageLimit m_instance = new PageLimit();

	private PageLimit() {
	}

	/**
	 * 构建 ec limit 对象
	 */
	public static PageLimit getInstance() {

		HttpServletRequest request = ServletActionContext.getRequest();
		m_instance.limit = RequestUtils.getLimit(request);
		if (RequestUtils.getCurrentRowsDisplayed(request) != 0)
			m_instance.CURRENT_ROWS_SIZE = RequestUtils
					.getCurrentRowsDisplayed(request);// 当前默认显示的行数
		m_instance.totalRows = RequestUtils.getTotalRowsFromRequest(request);
		m_instance.export = RequestUtils.isExported(request);
		if (m_instance.export) {
			int[] rows = RequestUtils.getRowStartEnd(request,
					m_instance.totalRows, m_instance.CURRENT_ROWS_SIZE);
			m_instance.CURRENT_ROWS_SIZE = rows[1] - rows[0];
		}
		m_instance.pageSize = m_instance.limit.getPage();

		Sort sort = m_instance.limit.getSort();
		m_instance.sortValueMap = (HashMap) sort.getSortValueMap();
		Iterator sortIterator = m_instance.sortValueMap.keySet().iterator();

		while (sortIterator.hasNext()) {
			m_instance.sortColumn = (String) sortIterator.next();
			m_instance.sortDesc = (String) m_instance.sortValueMap
					.get(m_instance.sortColumn);
		}
		return m_instance;

	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	public int getCURRENT_ROWS_SIZE() {
		return CURRENT_ROWS_SIZE;
	}

	public void setCURRENT_ROWS_SIZE(int current_rows_size) {
		CURRENT_ROWS_SIZE = current_rows_size;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setCURRENT_ROWS_SIZE(Integer current_rows_size) {
		CURRENT_ROWS_SIZE = current_rows_size;
	}

	public String getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}

	public Map<String, String> getAliasMap() {
		return aliasMap;
	}

	public void setAliasColumn(String column) {
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.aliasMap.put(columns[1], columns[0]);
		if (columns.length == 1)
			this.aliasMap.put(columns[0], "");
	}

	public void setAliasColumn(String column, String operation) {
		setAliasColumn(column);
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.operMap.put(columns[1], operation);
		if (columns.length == 1)
			this.aliasMap.put(columns[0], operation);
	}

	public void setAliasColumn(String column, String operation,
			String actualColumn) {
		setAliasColumn(column, operation);
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.actualMap.put(columns[1], actualColumn);
		if (columns.length == 1)
			this.actualMap.put(columns[0], actualColumn);
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Map<String, String> getOperMap() {
		return operMap;
	}

	public void setOperMap(Map<String, String> operMap) {
		this.operMap = operMap;
	}

	public Map<String, String> getActualMap() {
		return actualMap;
	}

	public void setActualMap(Map<String, String> actualMap) {
		this.actualMap = actualMap;
	}

}
