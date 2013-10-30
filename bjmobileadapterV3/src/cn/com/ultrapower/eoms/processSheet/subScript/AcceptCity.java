package cn.com.ultrapower.eoms.processSheet.subScript;

public class AcceptCity implements InterStr {

	public String getStrByNo(String No) {
		return ChangeTypeUtil.getStrByNo("WF:Config_EL_00_AcceptCity", "c650000006", "c650000005", No);
	}
}
