package daiwei.mobile.util.TempletUtil.TMPModel;

import java.io.Serializable;
/**
 * 工单模版解析实体类
 * @author 都 3/31
 *
 */
public class BaseField  implements Serializable  {
	private String id;
	private String type;//field或fieldGroup
	private Field field;
	private FieldGroup fieldGroup;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public FieldGroup getFieldGroup() {
		return fieldGroup;
	}
	public void setFieldGroup(FieldGroup fieldGroup) {
		this.fieldGroup = fieldGroup;
	}
}