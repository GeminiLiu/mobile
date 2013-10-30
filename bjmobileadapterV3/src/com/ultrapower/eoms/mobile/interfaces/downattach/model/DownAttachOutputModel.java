package com.ultrapower.eoms.mobile.interfaces.downattach.model;

import java.util.Map;

public class DownAttachOutputModel
{
	private int success;
	private Map<String, String> attachPathMap;
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
				xml.append("<attachs>");
				if(attachPathMap != null)
				{
					for(String key : attachPathMap.keySet())
					{
						xml.append("<attach name='" + key + "'>" + attachPathMap.get(key) + "</attach>");
					}
				}
				xml.append("</attachs>");
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

	public Map<String, String> getAttachPathMap()
	{
		return attachPathMap;
	}

	public void setAttachPathMap(Map<String, String> attachPathMap)
	{
		this.attachPathMap = attachPathMap;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
}
