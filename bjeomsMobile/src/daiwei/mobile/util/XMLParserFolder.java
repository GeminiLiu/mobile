package daiwei.mobile.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;




public class XMLParserFolder{
	private List<Map<String,String>> rsList = new ArrayList<Map<String,String>>();
	
	private StringBuffer idata = new StringBuffer();
	private Document doc;
	private Element root;
	public  XMLParserFolder(String idata){
		try {
			doc = DocumentHelper.parseText(idata.toString());
			root = doc.getRootElement();
			Iterator it=root.elements().iterator();
			while(it.hasNext()){
				Element info=(Element) it.next();
				
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	
}