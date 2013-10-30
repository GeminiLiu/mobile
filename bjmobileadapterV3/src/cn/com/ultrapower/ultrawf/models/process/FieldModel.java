package cn.com.ultrapower.ultrawf.models.process;

public class FieldModel {
	
	private String 		fieldID 				= "";
	private String 		fieldschemaID 			= "";
	private String 		fieldDateType 			= "";
	private String 		fieldShowName 			= "";
	private String 		fieldDbName 			= "";	
	
	public String getFieldID() {
		return fieldID;
	}
	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}
	public String getFieldschemaID() {
		return fieldschemaID;
	}
	public void setFieldschemaID(String fieldschemaID) {
		this.fieldschemaID = fieldschemaID;
	}
	public String getFieldDateType() {
		return fieldDateType;
	}
	public void setFieldDateType(String fieldDateType) {
		this.fieldDateType = fieldDateType;
	}
	public String getFieldShowName() {
		return fieldShowName;
	}
	public void setFieldShowName(String fieldShowName) {
		this.fieldShowName = fieldShowName;
	}
	public String getFieldDbName() {
		return fieldDbName;
	}
	public void setFieldDbName(String fieldDbName) {
		this.fieldDbName = fieldDbName;
	}	
}
