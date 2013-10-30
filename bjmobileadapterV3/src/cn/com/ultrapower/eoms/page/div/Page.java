package cn.com.ultrapower.eoms.page.div;

import java.util.List;
/**
 * 处理一些列表页面的数据.
 * @author LY
 *	2006-10-20
 */
public class Page {
	private int currentPage;
	private int maxPage;
	private List list;
	private int rowCount ;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
}
