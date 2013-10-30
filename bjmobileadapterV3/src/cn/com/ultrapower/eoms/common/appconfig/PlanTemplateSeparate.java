package cn.com.ultrapower.eoms.common.appconfig;

public class PlanTemplateSeparate {
	private String rowseparate;
	private String cellseparate;
	
	public PlanTemplateSeparate() {
	}
	public PlanTemplateSeparate(String rowseparate, String cellseparate) {
		super();
		this.rowseparate = rowseparate;
		this.cellseparate = cellseparate;
	}
	public String getCellseparate() {
		return cellseparate;
	}
	public void setCellseparate(String cellseparate) {
		this.cellseparate = cellseparate;
	}
	public String getRowseparate() {
		return rowseparate;
	}
	public void setRowseparate(String rowseparate) {
		this.rowseparate = rowseparate;
	}
	
}
