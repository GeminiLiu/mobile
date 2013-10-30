package com.ultrapower.mobile.model.xml;

import java.util.ArrayList;
import java.util.List;

public class DictInfo {
	private String id="";
	private String key="";
	private String value="";
	private List<DictInfo> dicts = new ArrayList<DictInfo>();
	
	public DictInfo() {
		
	}
	
	public DictInfo(String id, String key, String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public DictInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<DictInfo> getDicts() {
		return dicts;
	}
	public void setDicts(List<DictInfo> dicts) {
		this.dicts = dicts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
