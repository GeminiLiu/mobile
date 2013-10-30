package cn.com.ultrapower.eoms.processSheet.subScript;

public class PrivenceChange implements InterStr {

	public String getStrByNo(String No) {
		if(No != null && "NX".equalsIgnoreCase(No)){
			return "宁夏";
		}
		return "";
	}
	
}
