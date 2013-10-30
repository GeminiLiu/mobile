package com.ultrapower.mobile.model;

import java.io.Serializable;

public class WorkSheet implements Serializable {
	
	private String baseSn;
	private String baseType;
	private String baseStatus;
	private String title;
	private String creator;
	private String assigner;
	private long createTime;//建单时间
	private long dealTime;//处理时限
	private long acceptTime;//受理时限
	private long assigneTime;//分配时间
	
	private String baseSchema;
	private String baseId;
	private String tplId;
	private String taskId;
	private String taskName;
	
	public WorkSheet() {
		
	}
	
	public String getBaseSn() {
		return baseSn;
	}

	public void setBaseSn(String baseSn) {
		this.baseSn = baseSn;
	}

	public String getBaseType() {
		return baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAssigner() {
		return assigner;
	}

	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDealTime() {
		return dealTime;
	}

	public void setDealTime(long dealTime) {
		this.dealTime = dealTime;
	}

	public long getAssigneTime() {
		return assigneTime;
	}

	public void setAssigneTime(long assigneTime) {
		this.assigneTime = assigneTime;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public long getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(long acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
