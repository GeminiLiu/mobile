package com.ultrapower.eoms.mobile.interfaces.formcommit.model;

public class FormCommitOutputModel
{
	private int success;
	private String taskId;
	private String errorMessage = "";
	
	/**
	 * 将对象属性拼装为XML
	 * @return 拼装的XML
	 */
	public String buildXml()
	{
		StringBuilder xml = new StringBuilder();
		xml.append("<opDetail>");
			xml.append("<baseInfo>");
				xml.append("<isLegal>1</isLegal>");
				if(taskId!=null && !"".equals(taskId))
					xml.append("<taskId>" + taskId + "</taskId>");
				xml.append("<success>" + success + "</success>");
				xml.append("<errorMessage>" + errorMessage + "</errorMessage>");
			xml.append("</baseInfo>");
		xml.append("</opDetail>");
		return xml.toString();
	}
	
	/**
	 * 异常时返回的XML
	 * @return 异常XML
	 */
	public static String buildExceptionXml(String exString)
	{
		return "<opDetail><baseInfo><isLegal>0</isLegal><success>0</success><errorMessage>" +exString +"</errorMessage></baseInfo></opDetail>";
	}
	
	public int getSuccess()
	{
		return success;
	}
	public void setSuccess(int success)
	{
		this.success = success;
	}
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
