package cn.com.ultrapower.eoms.user.config.source.Bean;

public class SourceConfigAttributeBean {

	private Long 	sourceatt_id;
	private Long 	sourceatt_sourceid 	;
	private String 	sourceatt_cnname 	= "";
	private String 	sourceatt_enname	= "";
	private Long 	sourceatt_orderby 	;
	private String 	sourceatt_desc		= "";
	private String  sourceatt_type      = "";
	
	public String getSourceatt_type() {
		return sourceatt_type;
	}
	public void setSourceatt_type(String sourceatt_type) {
		this.sourceatt_type = sourceatt_type;
	}
	public String getSourceatt_cnname() {
		return sourceatt_cnname;
	}
	public void setSourceatt_cnname(String sourceatt_cnname) {
		this.sourceatt_cnname = sourceatt_cnname;
	}
	public String getSourceatt_desc() {
		return sourceatt_desc;
	}
	public void setSourceatt_desc(String sourceatt_desc) {
		this.sourceatt_desc = sourceatt_desc;
	}
	public String getSourceatt_enname() {
		return sourceatt_enname;
	}
	public void setSourceatt_enname(String sourceatt_enname) {
		this.sourceatt_enname = sourceatt_enname;
	}
	public Long getSourceatt_orderby() {
		return sourceatt_orderby;
	}
	public void setSourceatt_orderby(Long sourceatt_orderby) {
		this.sourceatt_orderby = sourceatt_orderby;
	}
	public Long getSourceatt_sourceid() {
		return sourceatt_sourceid;
	}
	public void setSourceatt_sourceid(Long sourceatt_sourceid) {
		this.sourceatt_sourceid = sourceatt_sourceid;
	}
	public Long getSourceatt_id() {
		return sourceatt_id;
	}
	public void setSourceatt_id(Long sourceatt_id) {
		this.sourceatt_id = sourceatt_id;
	}

}