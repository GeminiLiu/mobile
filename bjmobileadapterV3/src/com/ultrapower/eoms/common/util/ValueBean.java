package com.ultrapower.eoms.common.util;

/**
 * 字段条件的拼接
 * @author calvin
 */
public class ValueBean {
	
	String fieldname	= "";
	String fieldopcode	= "";
	Object fieldvalue	= null;
	public ValueBean(){
		
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getFieldopcode() {
		return fieldopcode;
	}
	public void setFieldopcode(String fieldopcode) {
		this.fieldopcode = fieldopcode;
	}
	public Object getFieldvalue() {
		return fieldvalue;
	}
	public void setFieldvalue(Object fieldvalue) {
		this.fieldvalue = fieldvalue;
	}
}