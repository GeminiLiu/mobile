package cn.com.ultrapower.eoms.processSheet.subScript;

public class NetType implements InterStr {

	public String getStrByNo(String No) {
		// TODO Auto-generated method stub
		
		return ChangeTypeUtil.getStrByNo("WF:Config_EL_00_NetType", "c650000006", "c650000005", No);
	}

}
