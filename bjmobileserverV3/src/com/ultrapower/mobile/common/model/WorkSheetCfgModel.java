package com.ultrapower.mobile.common.model;

import java.io.Serializable;


public class WorkSheetCfgModel implements Serializable {
	private String code;
	private String name;
	private String waitDeal;
	private String dealed;

	public WorkSheetCfgModel() {
	}

	public WorkSheetCfgModel(String code, String name, String waitDeal,
			String dealed) {
		this.code = code;
		this.name = name;
		this.waitDeal = waitDeal;
		this.dealed = dealed;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWaitDeal() {
		return waitDeal;
	}

	public void setWaitDeal(String waitDeal) {
		this.waitDeal = waitDeal;
	}

	public String getDealed() {
		return dealed;
	}

	public void setDealed(String dealed) {
		this.dealed = dealed;
	}
}
