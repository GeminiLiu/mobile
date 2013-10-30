package cn.com.ultrapower.eoms.util.remedy;

public class RemedyForm {

	private String formName;
	
	private String formSchema;
	
	private String processSchema;
	
	private String ownerFiledValue;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormSchema() {
		return formSchema;
	}

	public void setFormSchema(String formSchema) {
		this.formSchema = formSchema;
	}

	public String getOwnerFiledValue() {
		return ownerFiledValue;
	}

	public void setOwnerFiledValue(String ownerFiledValue) {
		this.ownerFiledValue = ownerFiledValue;
	}

	public String getProcessSchema() {
		return processSchema;
	}

	public void setProcessSchema(String processSchema) {
		this.processSchema = processSchema;
	}
}
