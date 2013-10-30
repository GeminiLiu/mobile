package cn.com.ultrapower.eoms.processSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
  
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.ultrapower.ultrawf.share.constants.Constants;

public class InterfaceCfg {
	
	public static final String PATH = Constants.sysPath;
	//public static final String PATH = "E:\\xqldev\\eoms\\WebRoot\\WEB-INF";
	private static String _fileName="";
	SAXReader reader = null;
 	private Document rootDoc = null;
	private Element rootElem = null;
	static{
		_fileName = Contents.CFGFILE;
	}
	
	public InterfaceCfg()
	{
		rootDoc = getResource();
		rootElem = getRootElement();
	}
	public InterfaceCfg(String fileName) 
	{
		_fileName = fileName;
		rootDoc = getResource();
		rootElem = getRootElement();
	} 
	
	/**
	 * 
	 * @param fileName 输入的配置文件路径
	 * @return
	 */
	public Document getResource() {

		reader = new SAXReader();
		try {
			Document doc = reader.read(PATH + File.separator + "cfg" +File.separator +_fileName);
			//Reader inputReader = new StringReader(str);
			//Document doc = reader.read(inputReader);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Element getRootElement() {
		rootDoc = getResource();
		if (rootDoc != null) {
			return rootDoc.getRootElement();
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param name 工单名称
	 * @return 工单名称所对应的属性数据
	 */
	public Map getProcessMap(String name)
	{
		Map procMap = new HashMap();
		Element procElem = rootElem.element(name);
		
		List attList = procElem.attributes();
		for(int i=0;i<attList.size();i++)
		{
			org.dom4j.Attribute attr = (Attribute) attList.get(i);
			procMap.put(attr.getName(), attr.getStringValue());	
		}
		return procMap;
	}
	
	/**
	 * 
	 * @param name 工单名称
	 * @param methodName 接口方法名称
	 * @return 工单名称所对应的属性数据
	 */
	public Map<String,String> getProcessMap(String name,String methodName)
	{
		Map<String,String> procMap = new HashMap<String,String>();
		Element methodElem = rootElem.element(name).element(methodName);
		
		List attList = methodElem.attributes();
		for(int i=0;i<attList.size();i++)
		{
			org.dom4j.Attribute attr = (Attribute) attList.get(i);
			procMap.put(attr.getName(), attr.getStringValue());	
		}
		return procMap;
	}
	
	
	/**
	 * 
	 * @param name 工单类型
	 * @param dealType 处理类型
	 * @return 元素列表 元素存放在map中.
	 */
	public List getElements(String name,String dealType) {
		List revList = new ArrayList();
		Element elem = rootElem.element(name);
		Element deal = elem.element(dealType);
		revList = this.nextElement(deal);
		return revList;
	}
	
	public List getBaseFieldInfo(String name,String dealType)
	{
		List revList = new ArrayList();

		Element elem = rootElem.element(name);
		Element deal = elem.element(dealType);
		List childList = deal.elements();
		for (int i = 0; i < childList.size(); i++) {
			Map map = new HashMap();
			Element row = (Element) childList.get(i);
			
			if(Contents.BASEFIELDINFO.equals(row.getName()))
			{
				List attList = row.attributes();
				for(int j=0;j<attList.size();j++)
				{
					org.dom4j.Attribute attr = (Attribute) attList.get(j);
					map.put(attr.getName(), attr.getStringValue());	
				}
				revList.add(map);
			}
		}
		return revList;
	}
	
	/**
	 * 
	 * @param element 根元素
	 * @return 元素列表 元素存放在map中.
	 */
	private List nextElement(Element element) {
		List revList = new ArrayList();
		List childList = element.elements();
		if (childList.size() == 0)
			return revList;

		for (int i = 0; i < childList.size(); i++) {
			Map map = new HashMap();
			Element row = (Element) childList.get(i);
			
			if(!Contents.FIELD.equals(row.getName()))
				continue;
			
			List elmList = row.elements();
			
			List<Attribute> attList = row.attributes();
			for(int j=0;j<attList.size();j++)
			{
				org.dom4j.Attribute attr = (Attribute) attList.get(j);
				map.put(attr.getName(), attr.getStringValue());	
			}
//			if(row.attributeValue(Contents.CLASSNAME)!=null)
//				map.put(Contents.CLASSNAME,row.attributeValue(Contents.CLASSNAME));
//			if(row.attributeValue(Contents.FIELDTYPE)!=null)
//				map.put(Contents.FIELDTYPE, row.attributeValue(Contents.FIELDTYPE));
//			if(row.attributeValue(Contents.DATEFORMAT)!=null)
//				map.put(Contents.DATEFORMAT, row.attributeValue(Contents.DATEFORMAT));
			
			for (int j = 0; j < elmList.size(); j++) {
				Element elm = (Element) elmList.get(j);
				map.put(elm.getName(), elm.getText());
				if(Contents.CONNECT.equals(row.attributeValue(Contents.FIELDTYPE)) && elm.getName().equals(Contents.FIELDBMC)&& row.attributeValue(Contents.CONNECTBMC)!=null)
				{
					map.put(Contents.CONNECTBMC, row.attributeValue(Contents.CONNECTBMC));
				}
				if(Contents.CONNECT.equals(row.attributeValue(Contents.FIELDTYPE)) && elm.getName().equals(Contents.FIELDCOLUMN) && row.attributeValue(Contents.CONNECTCOLUMN)!=null)
				{
					map.put(Contents.CONNECTCOLUMN, row.attributeValue(Contents.CONNECTCOLUMN));
				}
			}
			revList.add(map);
		}
		return revList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "<opDetail><recordInfo>" +
				"<fieldInfo><fieldChName>标题</fieldChName><fieldEnName>title</fieldEnName><fieldContent>this is 标题 1</fieldContent></fieldInfo>" +
				"<fieldInfo><fieldChName>受理时限</fieldChName><fieldEnName>dealTime1</fieldEnName><fieldContent>this 按时 受理 时限 1</fieldContent></fieldInfo></recordInfo>" +
			"<recordInfo><fieldInfo><fieldChName>标题</fieldChName><fieldEnName>title</fieldEnName><fieldContent>this is 标题 1</fieldContent></fieldInfo>" +
			"<fieldInfo><fieldChName>受理时限</fieldChName><fieldEnName>dealTime1</fieldEnName><fieldContent>this 按时 受理 时限 1</fieldContent></fieldInfo></recordInfo>" +
			"</opDetail>";

		/*XmlSplit incfg = new XmlSplit();
		List list = incfg.getDatatoList(str,"person", "newWorksheet");
		System.out.println(list.size());
		System.out.println();*/
		
		InterfaceCfg inte = new InterfaceCfg();
		inte.getBaseFieldInfo("person","newWorksheet");
		if("aaa".equals(null))
			System.out.println(true);
		else
			System.out.println(false);
		
	}

}
