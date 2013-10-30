package cn.com.ultrapower.eoms.processSheet;

import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cn.com.ultrapower.eoms.processSheet.subScript.InterStr;
import cn.com.ultrapower.ultrawf.control.process.baseaction.bean.BaseFieldInfo;
public class XmlSplit {

	SAXReader reader = null;

	Document rootDoc = null;

	public Document getResource(String str) {

		reader = new SAXReader();
		try {
			// Document doc =
			// reader.read(this.getClass().getResourceAsStream("/test.xml"));
			Reader inputReader = new StringReader(str);
			Document doc = reader.read(inputReader);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Element getRootElement(String s) {
		rootDoc = getResource(s);
		if (rootDoc != null) {
			return rootDoc.getRootElement();
		} else {
			return null;
		}
	}

	private List xml2(String str) {
		List revList = new ArrayList();
		Element root = getRootElement(str);
		revList = root.elements(Contents.RECORDINFO);
		return revList;
	}

	public List xml2(String str, String keys) {
		List revList = new ArrayList();
		Element root = getRootElement(str);
		Element elmKey = root.element(keys);
		revList = nextElement(elmKey);
		return revList;
	}

	private List nextElement(Element element) {
		List revList = new ArrayList();
		List childList = element.elements();
		if (childList.size() == 0)
			return revList;

		for (int i = 0; i < childList.size(); i++) {
			Map map = new HashMap();
			Element row = (Element) childList.get(i);
			List elmList = row.elements();
			for (int j = 0; j < elmList.size(); j++) {
				Element elm = (Element) elmList.get(j);
				map.put(elm.getName(), elm.getText());
			}
			revList.add(map);
		}
		return revList;
	}

	
	public List getDatatoList(String str, String sheetName, String dealType) {
		List list = new ArrayList();
		InterfaceCfg incfg = new InterfaceCfg();
		List cfgList = incfg.getElements(sheetName, dealType);
		List dataList = this.xml2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ str);
		for (int k = 0; k < dataList.size(); k++) {
			List changeList = this.nextElement((Element) dataList.get(k));
			List listBean = changeMap(cfgList, changeList);
			list.add(listBean);
		}
		return list;
	}

	private List changeMap(List cfgList, List dataList) {
		List<BaseFieldInfo> list = new ArrayList<BaseFieldInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			Map dataMap = (Map) dataList.get(i);
			for (int j = 0; j < cfgList.size(); j++) {
				Map<String,String> cfgMap = (Map) cfgList.get(j);
				if (cfgMap.get(Contents.FIELDENNAME)!=null && cfgMap.get(Contents.FIELDENNAME).toString().equals(dataMap.get(Contents.FIELDENNAME))) {
					// map.put(cfgMap.get("fieldColumn"),dataMap.get("fieldContent"));
					BaseFieldInfo baseFieldInfo = null;
					String classname = cfgMap.get(Contents.CLASSNAME);
					int datetype = Integer.parseInt((String)(cfgMap.get(Contents.FIELDDATATYPE)));
					if(classname!=null)
					{
						try {
							InterStr interStr = (InterStr)Class.forName(classname).newInstance();
							baseFieldInfo = new BaseFieldInfo(
									(String) cfgMap.get(Contents.FIELDBMC),
									(String) cfgMap.get(Contents.FIELDCOLUMN),
									interStr.getStrByNo((String) dataMap.get(Contents.FIELDCONTENT)),
									Integer.parseInt((String)(cfgMap.get(Contents.FIELDDATATYPE))));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else
					{
						String dateformat = cfgMap.get(Contents.DATEFORMAT);
						if(datetype==Contents.BMCDATATYPE_DATE && dateformat!=null){
							String strDate = (String) dataMap.get(Contents.FIELDCONTENT);
							SimpleDateFormat sf = new SimpleDateFormat(dateformat);
							try {
								strDate = String.valueOf(sf.parse(strDate).getTime()/1000);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							baseFieldInfo = new BaseFieldInfo((String) cfgMap.get(Contents.FIELDBMC),
																	(String) cfgMap.get(Contents.FIELDCOLUMN),
																	strDate,
																	Integer.parseInt((String)(cfgMap.get(Contents.FIELDDATATYPE))));
						}else
						{
							baseFieldInfo = new BaseFieldInfo((String) cfgMap.get(Contents.FIELDBMC),
									(String) cfgMap.get(Contents.FIELDCOLUMN),
									(String) dataMap.get(Contents.FIELDCONTENT),
									Integer.parseInt((String)(cfgMap.get(Contents.FIELDDATATYPE))));
						}
						
					}
					list.add(baseFieldInfo);
					
					if(cfgMap.get(Contents.FIELDTYPE)!=null && cfgMap.get(Contents.FIELDTYPE).toString().equals(Contents.CONNECT))
					{
						String[] conBMC = ((String) cfgMap.get(Contents.CONNECTBMC)).split(Contents.CONNECTSPLIT);
						String[] conCol = ((String) cfgMap.get(Contents.CONNECTCOLUMN)).split(Contents.CONNECTSPLIT);
						for(int m=0;m<conBMC.length&&m<conCol.length;m++)
						{
							BaseFieldInfo conInfo = new BaseFieldInfo(conBMC[m],conCol[m],(String) dataMap.get(Contents.FIELDCONTENT),
									Integer.parseInt((String)(cfgMap.get(Contents.FIELDDATATYPE))));
							list.add(conInfo);
						}
					}
					break;
				}
			}
		}
		return list;
	}
	
	public List getDatatoListM(String str,String sheetName,String dealType)
	{
		List list = new ArrayList();
		InterfaceCfg incfg = new InterfaceCfg();
		List cfgList = incfg.getElements(sheetName, dealType);
		List dataList = this.xml2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+str);
		for(int k=0;k<dataList.size();k++)
		{
			List changeList = this.nextElement((Element)dataList.get(k));
			Map map = changeMapM(cfgList,changeList);			
			list.add(map);
		}
		return list;
	}
	
	private Map changeMapM(List cfgList,List changeList)
	{
		Map map = new HashMap();
		for(int i=0;i<changeList.size();i++)
		{
			Map dataMap = (Map)changeList.get(i);
			for(int j=0;j<cfgList.size();j++)
			{
				Map cfgMap = (Map)cfgList.get(j);
				if(cfgMap.get(Contents.FIELDENNAME)!=null && cfgMap.get(Contents.FIELDENNAME).toString().equals(dataMap.get(Contents.FIELDENNAME)))
				{
					map.put(cfgMap.get(Contents.FIELDENNAME), dataMap.get(Contents.FIELDCONTENT));
					break;
				}
			}
			
		}
		return map;
	}
	
}
