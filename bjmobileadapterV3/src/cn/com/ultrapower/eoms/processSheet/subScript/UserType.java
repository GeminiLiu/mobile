package cn.com.ultrapower.eoms.processSheet.subScript;

public class UserType implements InterStr {

	public String getStrByNo(String No) {
		return ChangeTypeUtil.getStrByNo("WF:Config_EL_TTM_CCH_UserType", "c650000001", "c650000002", No);
	}
}
