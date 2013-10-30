package com.ultrapower.mobile.common.enums;

public enum UserOnlineEnum {
	ONLINE(1),OFFLINE(0);
	
	private int val;
	private String desc;
	
	private UserOnlineEnum(int val) {
		this.val = val;
		if (val == 0) {
			this.desc = "离线";
		} else if (val == 1) {
			this.desc = "在线";
		}
	}
	
	public int getVal() {
		return val;
	}
	
	public String getDesc() {
		return desc;
	}
	
	@Override
	public String toString() {
		return val + "";
	}
}
