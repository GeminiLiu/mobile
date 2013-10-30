package com.ultrapower.eoms.mobile.interfaces.openform.model;

import java.util.List;
import java.util.Map;

public class OpenFormOutputModel
{
	private List<String[]> actions;
	
	private Map<String, String> fields;
	
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
				xml.append("<actionOps>");
					for(String[] act : actions)
					{
						xml.append("<actionop id=\"" + act[1] + "\" code=\"" + act[0] + "\"><![CDATA[" + act[2] + "]]></actionop>");
					}
				xml.append("</actionOps>");
			xml.append("</baseInfo>");
			xml.append("<recordInfo>");
			for(String field : fields.keySet())
			{
				xml.append("<field code=\"" + field + "\"><![CDATA[" + fields.get(field) + "]]></field>");
			}
			xml.append("</recordInfo>");
		xml.append("</opDetail>");
		System.out.println("OpenFormOutputModel>> "+xml.toString());
		return xml.toString();
	}
	
	/**
	 * 异常时返回的XML
	 * @return 异常XML
	 */
	public static String buildExceptionXml()
	{
		return "<opDetail><baseInfo><isLegal>0</isLegal></baseInfo></opDetail>";
	}

	public List<String[]> getActions()
	{
		return actions;
	}

	public void setActions(List<String[]> actions)
	{
		this.actions = actions;
	}

	public Map<String, String> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, String> fields)
	{
		this.fields = fields;
	}
}
