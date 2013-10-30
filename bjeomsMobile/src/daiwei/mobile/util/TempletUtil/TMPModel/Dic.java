package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
import java.util.Map;


public class Dic  implements Serializable {
	private Map<String,String> options;

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
}
