package cn.com.ultrapower.ultrawf.models.config;

import cn.com.ultrapower.ultrawf.share.UnionConditionForSql;

public class ParConfigQueryDetailModel {
	
	private String Code="";
	private String Fieldid="";
	private String Fieldname="";
	// Fielddisplayname 用于在界面显示的名称
	private String Fielddisplayname="";
	// 查询字段的数据类型(字符、数字s等)
	private String Fieldtype="";
	//Fielddisplaytype 用于在界面显示的方式。如：text、radio、checkbox、select等	1：文本输入框
	private String Fielddisplaytype="";
	private String Colspan="";
	private String Rowspan="";
	//Logicexp用于查询时的表达式。如：左like、右like、like、not、or、in、=、<>等
	private String Logicexp="";
	private String Sortid="";
	//Defaultvalue1 当显示方式为select、radio、checkbox时的显示值.格式为”值+冒号+显示值+分号”如：0:是;1:否;
	private String Defaultvalue1="";
	//Defaultvalue2 当显示方式为select、radio、checkbox时的显示值，从配置表中读取据。
	//本字段用于保存读取配置信息的SQL.规则是第一个字段做为value值，第二个字段做为显示值。
	private String Defaultvalue2="";
	// Parclassname 用于标识将该参数赋值给哪个参数类
	private String Parclassname="";
	//Defaultvalue 默认的缺省值
	private String Defaultvalue="";
    public String IsNewLine="";

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getColspan() {
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
		return Fielddisplayname;
	}
	
	public void setFielddisplayname(String fielddisplayname) {
		Fielddisplayname = fielddisplayname;
	}

	public String getFielddisplaytype() {
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
    public void setIsNewLine(String IsNewLine)
    {
        this.IsNewLine=IsNewLine;
    }
    public String getIsNewLine()
    {
        return this.IsNewLine;
    }
	
	
	//返回排序字符串

	public String getOrderbyFiledNameString() 
	{
		String strRe="";
		if(!OrderbyFiledNameString.trim().equals(""))
		{
			//如果升序
			if(OrderByType==0)
				strRe=" order by "+OrderbyFiledNameString;
			else
			{
				String[] strAry =OrderbyFiledNameString.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+= strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return (strRe);
	}
	
	
	//用于排序字段的名称
	private String OrderbyFiledNameString="";
	//排序类型 0升序　否则为降序
	private int OrderByType=0;
	/**
	 * 设置排序字段
	 * @param p_strOrderByFiledNameString
	 * @param p_intOrderByType 0 升序 否则为降序
	 */
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString,int p_intOrderByType) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=p_intOrderByType;
	}	
	public void setOrderbyFiledNameString(String p_strOrderByFiledNameString) {
		OrderbyFiledNameString = p_strOrderByFiledNameString;
		OrderByType=0;
	}		
	

	public String getWhereSql(String p_TblAliasName)
	{
		String strTblPrefix=p_TblAliasName;
		if(!strTblPrefix.equals(""))
			strTblPrefix=strTblPrefix+".";		
			
		StringBuffer sqlString = new StringBuffer();
		
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Code",getCode()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Fieldid",getFieldid()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Fieldname",getFieldname()));
		// Fielddisplayname 用于在界面显示的名称
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Fielddisplayname",getFielddisplayname()));
		// 查询字段的数据类型(字符、数字s等)
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Fieldtype",getFieldtype()));
		//Fielddisplaytype 用于在界面显示的方式。如：text、radio、checkbox、select等	1：文本输入框
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Fielddisplaytype",getFielddisplaytype()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Colspan",getColspan()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Rowspan",getRowspan()));
		//Logicexp用于查询时的表达式。如：左like、右like、like、not、or、in、=、<>等
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Logicexp",getLogicexp()));
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Sortid",getSortid()));
		//Defaultvalue1 当显示方式为select、radio、checkbox时的显示值.格式为”值+冒号+显示值+分号”如：0:是;1:否;
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Defaultvalue1",getDefaultvalue1()));
		//Defaultvalue2 当显示方式为select、radio、checkbox时的显示值，从配置表中读取据。
		//本字段用于保存读取配置信息的SQL.规则是第一个字段做为value值，第二个字段做为显示值。
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Defaultvalue2",getDefaultvalue2()));
		// Parclassname 用于标识将该参数赋值给哪个参数类
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Parclassname",getParclassname()));
		//Defaultvalue 默认的缺省值
		sqlString.append(UnionConditionForSql.getStringFiledSql(p_TblAliasName,"Defaultvalue",getDefaultvalue()));
		
		
		return sqlString.toString();

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
