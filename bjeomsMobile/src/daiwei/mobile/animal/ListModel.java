package daiwei.mobile.animal;

import java.util.List;
import java.util.Map;

public class ListModel{
	private Map<String,String> baseCount;
	private List<Map<String, String>> listInfo;
	
	public Map<String, String> getBaseCount() {
		return baseCount;
	}
	public void setBaseCount(Map<String, String> baseCount) {
		this.baseCount = baseCount;
	}
	public List<Map<String, String>> getListInfo() {
		return listInfo;
	}
	public void setListInfo(List<Map<String, String>> listInfo) {
		this.listInfo = listInfo;
	}
}