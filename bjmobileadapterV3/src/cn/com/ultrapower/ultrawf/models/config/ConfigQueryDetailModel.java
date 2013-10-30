package cn.com.ultrapower.ultrawf.models.config;

public class ConfigQueryDetailModel {

	private String Code;
	private String Fieldid="";
	private String Fieldname;
	// Fielddisplayname 用于在界面显示的名称
	private String Fielddisplayname;
	// 查询字段的数据类型(字符、数字s等)
	private String Fieldtype;
	//Fielddisplaytype 用于在界面显示的方式。如：text、radio、checkbox、select等	1：文本输入框
	private String Fielddisplaytype;
	private String Colspan;
	private String Rowspan;
	//Logicexp用于查询时的表达式。如：左like、右like、like、not、or、in、=、<>等
	private String Logicexp;
	private String Sortid;
	//Defaultvalue1 当显示方式为select、radio、checkbox时的显示值.格式为”值+冒号+显示值+分号”如：0:是;1:否;
	private String Defaultvalue1;
	//Defaultvalue2 当显示方式为select、radio、checkbox时的显示值，从配置表中读取据。
	//本字段用于保存读取配置信息的SQL.规则是第一个字段做为value值，第二个字段做为显示值。
	private String Defaultvalue2;
	// Parclassname 用于标识将该参数赋值给哪个参数类
	private String Parclassname;
	//Defaultvalue 默认的缺省值
	private String Defaultvalue;
	private String IsNewLine;
	
	//字段的值,用于存放字段当前的值(如:值可能是有界面上通过request的geparameter获得的)
	private String Evaluate;
	
	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getColspan() {
		if(Colspan==null)
			return "";
		else
			return Colspan;
	}

	public void setColspan(String colspan) {
		Colspan = colspan;
	}

	public String getDefaultvalue() {
		return Defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		Defaultvalue = defaultvalue;
	}

	public String getDefaultvalue1() {
		if(Defaultvalue1==null)
			return "";
		return Defaultvalue1;
	}

	public void setDefaultvalue1(String defaultvalue1) {
		Defaultvalue1 = defaultvalue1;
	}

	public String getDefaultvalue2() {
		return Defaultvalue2;
	}

	public void setDefaultvalue2(String defaultvalue2) {
		Defaultvalue2 = defaultvalue2;
	}

	
	public String getFielddisplayname() {
		if(Fielddisplayname==null)
			return "";
		else
			return Fielddisplayname;
	}
	
	public void setFielddisplayname(String fielddisplayname) {
		Fielddisplayname = fielddisplayname;
	}

	public String getFielddisplaytype() {
		if(Fielddisplaytype==null)
			return "";
		return Fielddisplaytype;
	}

	public void setFielddisplaytype(String fielddisplaytype) {
		Fielddisplaytype = fielddisplaytype;
	}

	public String getFieldid() {
		return Fieldid;
	}

	public void setFieldid(String fieldid) {
		Fieldid = fieldid;
	}

	public String getFieldname() {
		if(Fieldname==null)
			return "";
		return Fieldname;
	}

	public void setFieldname(String fieldname) {
		Fieldname = fieldname;
	}

	public String getFieldtype() {
		return Fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		Fieldtype = fieldtype;
	}

	public String getLogicexp() {
		if(Logicexp==null)
			return "";
		return Logicexp;
	}

	public void setLogicexp(String logicexp) {
		Logicexp = logicexp;
	}

	public String getParclassname() {
		return Parclassname;
	}

	public void setParclassname(String parclassname) {
		Parclassname = parclassname;
	}

	public String getRowspan() {
		return Rowspan;
	}

	public void setRowspan(String rowspan) {
		Rowspan = rowspan;
	}

	public String getSortid() {
		return Sortid;
	}

	public void setSortid(String sortid) {
		Sortid = sortid;
	}

	public String getIsNewLine() {
		if(IsNewLine==null)
			return "";
		else
			return IsNewLine;
	}

	public void setIsNewLine(String isNewLine) {
		IsNewLine = isNewLine;
	}

	public String getEvaluate() {
		if(Evaluate==null)
			return "";
		else
			return Evaluate;
	}

	public void setEvaluate(String evaluate) {
		Evaluate = evaluate;
	}

}
