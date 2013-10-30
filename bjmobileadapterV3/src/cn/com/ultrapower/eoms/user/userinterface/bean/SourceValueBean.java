package cn.com.ultrapower.eoms.user.userinterface.bean;

import java.util.Map;

public class SourceValueBean {

	/**
	 * date 2007-2-27 author shigang
	 * 
	 * @param args
	 * @return void
	 */
	private String source_id;//资源类别id

	private String source_parentid;//资源类别父id

	private String source_cnname;//资源中文名称

	private String source_name;//资源英文名称

	private String source_module;//资源所属模块

	private String source_type;//资源类别类型

	private Map exts;

	public Map getExts() {
		return exts;
	}

	public void setExts(Map exts) {
		this.exts = exts;
	}

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

}
