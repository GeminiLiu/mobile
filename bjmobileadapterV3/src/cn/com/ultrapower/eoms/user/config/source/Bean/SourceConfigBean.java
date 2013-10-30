package cn.com.ultrapower.eoms.user.config.source.Bean;

public class SourceConfigBean {

	/**
	 * date 2006-10-27
	 * author shigang
	 * @param args
	 * @return void
	 */
	private Long source_id;
	private Long source_parentid;
	private String source_cnname;
	private String source_name;
	private String source_module;
	private Long source_orderby;
	private String source_desc;
	private String source_type;
	private String source_fieldtype;
    private String source_url;
    private Long source_isleft;
	
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getSource_fieldtype() {
		return source_fieldtype;
	}
	public void setSource_fieldtype(String source_fieldtype) {
		this.source_fieldtype = source_fieldtype;
	}
	
	public String getSource_type() {
		return source_type;
	}
	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}
	
	public String getSource_cnname() {
		return source_cnname;
	}
	public void setSource_cnname(String source_cnname) {
		this.source_cnname = source_cnname;
	}
	public String getSource_desc() {
		return source_desc;
	}
	public void setSource_desc(String source_desc) {
		this.source_desc = source_desc;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
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
	public Long getSource_orderby() {
		return source_orderby;
	}
	public void setSource_orderby(Long source_orderby) {
		this.source_orderby = source_orderby;
	}
	public Long getSource_parentid() {
		return source_parentid;
	}
	public void setSource_parentid(Long source_parentid) {
		this.source_parentid = source_parentid;
	}
	public Long getSource_isleft() {
		return source_isleft;
	}
	public void setSource_isleft(Long source_isleft) {
		this.source_isleft = source_isleft;
	}
}
