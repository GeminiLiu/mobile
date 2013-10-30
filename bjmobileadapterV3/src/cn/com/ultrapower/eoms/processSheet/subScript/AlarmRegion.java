package cn.com.ultrapower.eoms.processSheet.subScript;

public class AlarmRegion implements InterStr {

	public String getStrByNo(String No) {
		if (null != No && !No.equals("")) {
			if ("银川市".equals(No)) {
				return "银川";
			} else if ("固原市".equals(No)) {
				return "固原";
			} else if ("中卫市".equals(No)) {
				return "中卫";
			} else if ("石嘴山市".equals(No)) {
				return "石嘴山";
			} else if ("吴忠市".equals(No)) {
				return "吴忠";
			} else if ("吴忠地区".equals(No)) {
				return "吴忠";
			}else if ("固原地区".equals(No)) {
				return "固原";
			}
		}
		return No;
	}
}
