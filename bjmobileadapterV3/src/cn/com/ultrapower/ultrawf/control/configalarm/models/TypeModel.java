package cn.com.ultrapower.ultrawf.control.configalarm.models;

public class TypeModel {
	private String TName;
	private String TValue;
	
	public TypeModel(){};
	
	public TypeModel(String strName,String strValue){
		this.TName=strName;
		this.TValue=strValue;
	}
	public String getTName() {
		return TName;
	}
	public void setTName(String name) {
		TName = name;
	}
	public String getTValue() {
		return TValue;
	}
	public void setTValue(String value) {
		TValue = value;
	}
}
