package cn.com.ultrapower.ultrawf.control.config;

import java.util.Map;

import cn.com.ultrapower.ultrawf.models.config.BaseQueryXmlConfigHandler;
import cn.com.ultrapower.system.util.FormatString;

public class BaseQueryXmlConfigManage
{
	private static Map<String, Map<String, String>> queryXmlMap;
	
	public void init()
	{
		BaseQueryXmlConfigHandler handler = new BaseQueryXmlConfigHandler();
		try
		{
			queryXmlMap = handler.getBaseQueryXmlConfigMap();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public String getBaseQueryName(String queryName, String schema)
	{
		String xmlKey = FormatString.CheckNullString(schema).equals("") ? "*" : schema;
		Map<String, String> xmlMap = queryXmlMap.get(FormatString.CheckNullString(queryName));
		if(xmlMap != null)
		{
			String xmlName = xmlMap.get(schema);
			return FormatString.CheckNullString(xmlName).equals("") ? queryName : xmlName;
		}
		else
		{
			return queryName;
		}
	}
}
