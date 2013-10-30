package cn.com.ultrapower.eoms.processSheet.subScript;

public class ComplainSource implements InterStr {

	public String getStrByNo(String No) {
		return ChangeTypeUtil.getStrByNo("WF:Config_EL_TTM_CCH_ComplainSource", "c650000001", "c650000002", No);
	}
}
