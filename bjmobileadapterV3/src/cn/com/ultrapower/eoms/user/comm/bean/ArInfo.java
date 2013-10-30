package cn.com.ultrapower.eoms.user.comm.bean;

/**
 * @author wangwenzhuo
 * @CreatTime 2006-10-16
 * @将Arfield封装在用户信息数据
 */
public class ArInfo {
	
	private String fieldID;
	private String value;
	private String flag;
	
	public String getFieldID() {
		return fieldID;
	}

	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
