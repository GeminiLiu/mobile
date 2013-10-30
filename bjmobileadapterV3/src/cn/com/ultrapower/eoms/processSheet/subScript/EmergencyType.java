package cn.com.ultrapower.eoms.processSheet.subScript;


public class EmergencyType implements InterStr {

	public String getStrByNo(String No) {
		if("001".equals(No)){
			return "紧急";
		}else if("002".equals(No)){
			return "重要";
		}else if("003".equals(No)){
			return "次要";
		}else{
			return "一般";
		}
	}
}
