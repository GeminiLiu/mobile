package com.ultrapower.mobile.model.xml;

import java.io.Serializable;

public class ActionModel implements Serializable {

	private String actionId = "";
	private String actionName = "";
	private String actionType = "";
	private String actorDesc = "处理人";
	private int order;
	private boolean needActor;
	private boolean singleFlag;//true单个处理人，false多个处理人
	
	public ActionModel() {
	}
	
	public ActionModel(String actionId, String actionName, String actionType,
			int order, boolean needActor) {
		super();
		this.actionId = actionId;
		this.actionName = actionName;
		this.actionType = actionType;
		this.order = order;
		this.needActor = needActor;
	}
	
	public ActionModel(String actionId, String actionName, String actionType,
			int order, boolean needActor, boolean singleFlag) {
		super();
		this.actionId = actionId;
		this.actionName = actionName;
		this.actionType = actionType;
		this.order = order;
		this.needActor = needActor;
		this.singleFlag = singleFlag;
	}
	
	public ActionModel(String actionId, String actionName, String actionType,
			int order, boolean needActor, boolean singleFlag, String actorDesc) {
		super();
		this.actionId = actionId;
		this.actionName = actionName;
		this.actionType = actionType;
		this.order = order;
		this.needActor = needActor;
		this.singleFlag = singleFlag;
		this.actorDesc = actorDesc;
	}

	public ActionModel(String actionId, String actionName, String actionType) {
		this.actionId = actionId;
		this.actionName = actionName;
		this.actionType = actionType;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isNeedActor() {
		return needActor;
	}

	public void setNeedActor(boolean needActor) {
		this.needActor = needActor;
	}

	public boolean isSingleFlag() {
		return singleFlag;
	}

	public void setSingleFlag(boolean singleFlag) {
		this.singleFlag = singleFlag;
	}

	public String getActorDesc() {
		return actorDesc;
	}

	public void setActorDesc(String actorDesc) {
		this.actorDesc = actorDesc;
	}
}
