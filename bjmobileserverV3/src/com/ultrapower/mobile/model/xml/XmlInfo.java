package com.ultrapower.mobile.model.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class XmlInfo {
	private List<FieldInfo> fields = new ArrayList<FieldInfo>();
	private List<ActionModel> actions = new ArrayList<ActionModel>();
	private List<List<FieldInfo>> listRecords = new ArrayList<List<FieldInfo>>();
	private List<DictInfo> dicts = new ArrayList<DictInfo>();
	
	public List<FieldInfo> getFields() {
		return fields;
	}
	public void setFields(List<FieldInfo> fields) {
		this.fields = fields;
	}
	public List<List<FieldInfo>> getListRecords() {
		return listRecords;
	}
	public void setListRecords(List<List<FieldInfo>> listRecords) {
		this.listRecords = listRecords;
	}
	
	public String getContent(String key) {
		String val = null;
		if (CollectionUtils.isNotEmpty(fields) && StringUtils.isNotBlank(key)) {
			for (int i = 0; i < fields.size(); i++) {
				FieldInfo fieldInfo = fields.get(i);
				String fieldEnName = fieldInfo.getEname();
				String fieldContent = fieldInfo.getContent();
				if (key.equalsIgnoreCase(fieldEnName)) {
					val = fieldContent;
					break;
				}
			}
		}
		return val;
	}
	
	public String getCnName(String key) {
		String val = null;
		if (CollectionUtils.isNotEmpty(fields) && StringUtils.isNotBlank(key)) {
			for (int i = 0; i < fields.size(); i++) {
				FieldInfo fieldInfo = fields.get(i);
				String fieldEnName = fieldInfo.getEname();
				String fieldChName = fieldInfo.getCname();
				if (key.equalsIgnoreCase(fieldEnName)) {
					val = fieldChName;
					break;
				}
			}
		}
		return val;
	}
	public List<ActionModel> getActions() {
		return actions;
	}
	public void setActions(List<ActionModel> actions) {
		this.actions = actions;
	}
	public List<DictInfo> getDicts() {
		return dicts;
	}
	public void setDicts(List<DictInfo> dicts) {
		this.dicts = dicts;
	}
}
