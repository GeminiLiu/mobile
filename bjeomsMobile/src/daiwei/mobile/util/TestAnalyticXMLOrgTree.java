package daiwei.mobile.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import daiwei.mobile.animal.OrgItem;

/**
 * 解析派发树
 * 
 * @author Administrator
 * 
 */
public class TestAnalyticXMLOrgTree {

	public static List<OrgItem> domParser(String in) {
		SAXReader reader = new SAXReader();
		try {
			// Document doc = reader.read(in);//读流
			Document doc = DocumentHelper.parseText(in);
			Element root = doc.getRootElement();
			return Analytic(root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static List<OrgItem> Analytic(Element element) {
		Iterator<Element> its = element.elements("item").iterator();
		List<OrgItem> itemList = new ArrayList<OrgItem>();
		while (its.hasNext()) {
			Element rowInfo = its.next();
			String checkBox = rowInfo.attributeValue("nocheckbox");
			OrgItem it = new OrgItem();
			boolean b = checkBox.equals("1") ? true : false;
			it.setNocheckbox(b);
			String child = rowInfo.attributeValue("child");
			it.setChild(child.equals("true"));
			Iterator<Element> rowIts = rowInfo.elements("userdata").iterator();

			while (rowIts.hasNext()) {
				Element rowit = rowIts.next();
				String name = rowit.attributeValue("name");
				if (name.equals("id"))
					it.setId(rowit.getText());
				else if (name.equals("type"))
					it.setType(rowit.getText());
				else if (name.equals("text"))
					it.setText(rowit.getText());
				else if (name.equals("loginname"))
					it.setLoginname(rowit.getText());
			}
//			System.out.println(it.getId() + ":" + it.getType() + ":"
//					+ it.getText() + ":" + it.getLoginname());
			itemList.add(it);
		}
		return itemList;
	}

}
