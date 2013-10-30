package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Templet  implements Serializable {
	private Map<String,Dic> dicDefine;
	private List<BaseField> baseFields;
	private List<ActionButton> actionButtons;
	private HashMap<String,HashMap<String,TemplateRT>> templatestore;
	public Map<String, Dic> getDicDefine() {
		return dicDefine;
	}

	public void setDicDefine(Map<String, Dic> dicDefine) {
		this.dicDefine = dicDefine;
	}

	public List<BaseField> getBaseFields() {
		return baseFields;
	}

	public void setBaseFields(List<BaseField> baseFields) {
		this.baseFields = baseFields;
	}

	public List<ActionButton> getActionButtons() {
		return actionButtons;
	}

	public void setActionButtons(List<ActionButton> actionButtons) {
		this.actionButtons = actionButtons;
	}

	public Map<String, HashMap<String, TemplateRT>> getTemplatestore() {
		return templatestore;
	}

	public void setTemplatestore(HashMap<String, HashMap<String, TemplateRT>> templatestore) {
		this.templatestore = templatestore;
	}

	


}