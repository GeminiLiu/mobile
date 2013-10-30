package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
import java.util.ArrayList;

public class TemplateRT  implements Serializable{
	private String paramId;
	private String paramCode;
	private ArrayList<String> content;
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public ArrayList<String> getContent() {
		return content;
	}
	public void setContent(ArrayList<String> content) {
		this.content = content;
	}
}