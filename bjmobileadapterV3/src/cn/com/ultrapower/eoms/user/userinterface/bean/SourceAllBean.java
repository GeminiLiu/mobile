package cn.com.ultrapower.eoms.user.userinterface.bean;

public class SourceAllBean {

	/**
	 * date 2007-2-28
	 * author shigang
	 * @param args
	 * @return void
	 */
	private String source_id;

	private String source_parentid;

	private String source_cnname;

	private String source_name;

	private String source_module;

	private String source_type;
	
	private String sourceatt_enname;
	
	private String sourceattvalue_value;

	public String getSource_cnname() {
		return source_cnname;
	}

	public void setSource_cnname(String source_cnname) {
		this.source_cnname = source_cnname;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public String getSource_module() {
		return source_module;
	}

	public void setSource_module(String source_module) {
		this.source_module = source_module;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getSource_parentid() {
		return source_parentid;
	}

	public void setSource_parentid(String source_parentid) {
		this.source_parentid = source_parentid;
	}

	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}

	public String getSourceatt_enname() {
		return sourceatt_enname;
	}

	public void setSourceatt_enname(String sourceatt_enname) {
		this.sourceatt_enname = sourceatt_enname;
	}

	public String getSourceattvalue_value() {
		return sourceattvalue_value;
	}

	public void setSourceattvalue_value(String sourceattvalue_value) {
		this.sourceattvalue_value = sourceattvalue_value;
	}

}
