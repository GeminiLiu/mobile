package com.ultrapower.eoms.mobile.interfaces.querydeallist.model;

import java.util.List;
import java.util.Map;

public class QueryDealListOutputModel
{
	/**
	 * 各工单的待办列表
	 */
	private Map<String, Integer> baseCount;
	
	/**
	 * 查询的数据列表
	 */
	private List<Map<String, String>> baseList;

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
				xml.append("<baseCount>");
					if(baseCount!=null)
					{
						for(String key : baseCount.keySet())
						{
							xml.append("<category type=\"" + key + "\">" + baseCount.get(key) + "</category>");
						}
					}
				xml.append("</baseCount>");
			xml.append("</baseInfo>");
			for(Map<String, String> fields : baseList)
			{
				xml.append("<recordInfo>");
				for(String key : fields.keySet())
				{
					xml.append("<field code=\"" + key + "\"><![CDATA[" + fields.get(key) + "]]></field>");
				}
				xml.append("</recordInfo>");
			}
		xml.append("</opDetail>");
		
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
	
	public Map<String, Integer> getBaseCount()
	{
		return baseCount;
	}

	public void setBaseCount(Map<String, Integer> baseCount)
	{
		this.baseCount = baseCount;
	}

	public List<Map<String, String>> getBaseList()
	{
		return baseList;
	}

	public void setBaseList(List<Map<String, String>> baseList)
	{
		this.baseList = baseList;
	}
}
