package cn.com.ultrapower.ultrawf.control.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class BaseQueryXmlConfigHandler
{
	private File file = new File(Constants.sysPath + File.separator + "conf" + File.separator + "BaseQueryXMLConfig.xml");
	
	public Map<String, Map<String, String>> getBaseQueryXmlConfigMap() throws Exception
	{
		SAXBuilder bu = new SAXBuilder();
		Document doc = bu.build(file);
		Element rootElement = doc.getRootElement();

		List<Element> queryXmlList = rootElement.getChildren("sqls");
		Map<String, Map<String, String>> xmlMap = new HashMap<String, Map<String, String>>();
		for(Element ele_query : queryXmlList)
		{
			List<Element> sqlXmlList = ele_query.getChildren("sql");
			Map<String, String> sqlMap = new HashMap<String, String>();
			for(Element ele_sql : sqlXmlList)
			{
				sqlMap.put(ele_sql.getAttributeValue("schema"), ele_sql.getText());
			}
			xmlMap.put(ele_query.getAttributeValue("name"), sqlMap);
		}
		return xmlMap;
	}
}
