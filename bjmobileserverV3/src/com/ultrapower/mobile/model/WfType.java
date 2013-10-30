package com.ultrapower.mobile.model;

import java.io.Serializable;

public class WfType implements Serializable {
	
	private String baseSchema;
	private String baseName;
	
	public WfType() {
	}
	
	public WfType(String baseSchema, String baseName) {
		this.baseSchema = baseSchema;
		this.baseName = baseName;
	}

	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
}
