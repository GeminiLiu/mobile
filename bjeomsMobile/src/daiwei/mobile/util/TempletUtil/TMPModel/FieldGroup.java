package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
import java.util.List;

public class FieldGroup  implements Serializable  {
	private String text;
	private List<Field> group;
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Field> getGroup() {
		return group;
	}

	public void setGroup(List<Field> group) {
		this.group = group;
	}
}