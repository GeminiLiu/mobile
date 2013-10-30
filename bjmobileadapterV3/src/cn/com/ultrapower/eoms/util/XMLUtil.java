package cn.com.ultrapower.eoms.util;


/**
 * 在此处插入类型说明。
 * 创建日期：(2000-12-14 9:35:13)
 * @author：Administrator
 */

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLUtil {

	public static Element[] ZERO_LENGTH_ELEMENT = new Element[0];

	public static final DummyErrorHandler DUMMY_ERROR_HANDLER =
		new DummyErrorHandler();

	public static class DummyErrorHandler implements ErrorHandler {
		public void fatalError(SAXParseException err) throws SAXException {
			throw err;
		}
		public void error(SAXParseException err) throws SAXException {
			throw err;
		}
		public void warning(SAXParseException err) throws SAXException {
			throw err;
		}
	}
	/**
	 * 此处插入方法描述。
	 * 创建日期：(2002-5-31 10:48:14)
	 * @return org.w3c.dom.Attr
	 * @param name java.lang.String
	 * @param value java.lang.String
	 */
	public static Attr createAttribute(
		Document doc,
		String name,
		String value) {
		Attr attr = doc.createAttribute(name);
		attr.setNodeValue(String.valueOf(value));
		return attr;
	}
	/**
	 * <br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 15:55:35)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 * @return org.w3c.dom.Document
	 */
	public static Document createDocument() {
		Document doc = null;
		try {
			DocumentBuilder builder =
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.newDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * 功能说明：创建一个Element，拥有给定的子Node。添加Node的顺序按照数组顺序。<br>
	 * 输入参数：doc Document：创建Element的Document，若为null,返回null<br>
	 *			childs Node[]：此Element的子Node。若为null,返回没有子node的Element<br>
	 *			nodeName:此Element的名字<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 16:54:23)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 * @return org.w3c.dom.Element
	 * @param doc org.w3c.dom.Document
	 * @param childs org.w3c.dom.Node[]
	 * @param nodeName java.lang.String
	 */
	public static Element createElementWithChilds(
		Document doc,
		Node[] childs,
		String nodeName) {
		return createElementWithChilds(doc, childs, nodeName, null);
	}
	/**
	 * 功能说明：创建一个Element，拥有给定的子Node。添加Node的顺序按照数组顺序。<br>
	 * 输入参数：doc Document：创建Element的Document，若为null,返回null<br>
	 *			childs Node[]：此Element的子Node。若为null,返回没有子node的Element<br>
	 *			nodeName:此Element的名字，如果originalElement不为null， 则此参数无效<br>
	 *			originalElement Element:初始Element，如果不为null，则将childs append到此element上，然后返回此element<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 16:54:23)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 * @return org.w3c.dom.Element
	 * @param doc org.w3c.dom.Document
	 * @param childs org.w3c.dom.Node[]
	 * @param nodeName java.lang.String
	 */
	public static Element createElementWithChilds(
		Document doc,
		Node[] childs,
		String nodeName,
		Element originalElement) {
		if (doc == null) {
			return null;
		}
		Element result;
		if (originalElement == null) {
			result = doc.createElement(nodeName);
		} else {
			result = originalElement;
		}
		if (childs == null) {
			return result;
		}
		for (int i = 0; i < childs.length; i++) {
			result.appendChild(childs[i]);
		}
		return result;
	}
	/**
	 * 功能说明：创建带有指定文本的Element。<br>
	 * 输入参数：doc Document：创建Element的Document对象<br>
	 *			elementName String:此Element的名字<br>
	 *			elementText：此Elemenent的文本。<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 16:27:44)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 * @return org.w3c.dom.Element
	 * @param doc org.w3c.dom.Document
	 * @param elementName java.lang.String
	 * @param elementText java.lang.String
	 */
	public static Element createElementWithTextNode(
		Document doc,
		String elementName,
		String elementText) {
		elementName = (elementName == null ? "" : elementName);
		elementText = (elementText == null ? "" : elementText);
		Element result = doc.createElement(elementName);
		result.appendChild(doc.createTextNode(elementText));
		return result;
	}
	/**
	 * 得到XML文件中某个Node下的第一个CDATA子节点的内容。
	 * 创建日期：(2001-9-18 12:52:45)
	 * @return java.lang.String
	 * @param node org.w3c.dom.Node
	 */
	public static String getCDATASection(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		Node child = node.getFirstChild();
		while (child != null
			&& child.getNodeType() != Node.CDATA_SECTION_NODE) {
			child = child.getNextSibling();
		}
		if (child == null) {
			return null;
		}
		return ((CDATASection) child).getData();
	}
	/**
	 * 功能说明：根据文件生成Document对象
	 * 输入参数：
	 * 返回值：
	 * 异常：
	 * 创建者：高开
	 * 创建时间：(2002-2-27 13:45:59)
	 * 版本：版本号
	 * 
	 * @return org.w3c.dom.Document
	 * @param f java.io.File
	 */
	public static Document getDocument(File f) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory =
				DocumentBuilderFactory.newInstance();
			// Use validating parser.
			docBuilderFactory.setValidating(false);
			//modified by linyu. unvalidation.
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(DUMMY_ERROR_HANDLER);
			doc = docBuilder.parse(f);
			// Normalize text representation
			doc.getDocumentElement().normalize();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return doc;
	}
	public static Document getDocument(InputStream is) {
		return getDocument(new InputSource(is));
	}
	public static Document getDocument(Reader r) {
		return getDocument(new InputSource(r));
	}
	public static Document getDocument(String data) {
		return getDocument(new StringReader(data));
	}
	public static Document getDocument(InputSource is) {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory =
				DocumentBuilderFactory.newInstance();
			// Use validating parser.
			docBuilderFactory.setValidating(false);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(DUMMY_ERROR_HANDLER);
			doc = docBuilder.parse(is);
			// Normalize text representation
			doc.getDocumentElement().normalize();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return doc;
	}
	/**
	 * 得到XML文件中某个Element的第一个子Element。
	 * 创建日期：(2000-12-8 11:52:17)
	 * @return org.w3c.dom.Node
	 * @param node org.w3c.dom.Node
	 */
	public static Element getNextChildElement(Node node) {
		Node n = node.getFirstChild();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}
	/**
	 * 得到XML文件中某个Element的下一个兄弟Element。
	 * 创建日期：(2000-12-8 11:55:45)
	 * @param node org.w3c.dom.Node
	 * @return org.w3c.dom.Element
	 */
	public static Element getNextSiblingElement(Node node) {
		Node n = node.getNextSibling();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (01-2-22 15:17:42)
	 * @return java.lang.String
	 * @param element org.w3c.dom.Node
	 * @param attributeName java.lang.String
	 */
	public static String getNodeAttributeValue(
		Node element,
		String attributeName) {
		Node tmpNode = element.getAttributes().getNamedItem(attributeName);
		String tmp = null;
		if (tmpNode != null)
			tmp = tmpNode.getNodeValue();
		return tmp;
	}
	/**
	 * 得到XML文件中某个Node下的第一个Text子节点的内容。
	 * 创建日期：(2000-12-14 9:46:03)
	 * @return java.lang.String
	 * @param node org.w3c.dom.Node
	 */
	public static String getTextData(Node node) {
		if (!node.hasChildNodes()) {
			return null;
		}
		Node child = node.getFirstChild();
		while (child != null && child.getNodeType() != Node.TEXT_NODE) {
			child = child.getNextSibling();
		}
		if (child == null) {
			return null;
		}
		return ((Text) child).getData();
	}
	/**
	 * 此处插入方法描述。
	 * 创建日期：(2002-5-31 14:22:41)
	 * @param node org.w3c.dom.Node
	 * @param container java.util.Map
	 */
	public static Map readParamNode(
		Node node,
		String name,
		String value,
		Map container) {
		if (container == null) {
			container = new HashMap();
		}
		while (node != null) {
			container.put(
				getNodeAttributeValue(node, name),
				getNodeAttributeValue(node, value));
			node = getNextSiblingElement(node);
		}
		return container;
	}
	/**
	 * 此处插入方法描述。
	 * 创建日期：(2002-5-31 14:22:41)
	 * @param node org.w3c.dom.Node
	 * @param container java.util.Map
	 */
	public static Map readParamNode(Node node, Map container) {
		return readParamNode(node, "name", "value", container);
	}
	/**
	 * 此处插入方法描述。
	 * 创建日期：(2002-5-31 10:59:00)
	 * @param elem org.w3c.dom.Element
	 * @param name java.lang.String
	 * @param value java.lang.String
	 */
	public static void setAttribute(Element elem, String name, String value) {
		setAttribute(
			elem,
			createAttribute(elem.getOwnerDocument(), name, value));
	}
	/**
	 * 此处插入方法描述。
	 * 创建日期：(2002-5-31 11:00:11)
	 * @param elem org.w3c.dom.Element
	 * @param attribute org.w3c.dom.Attr
	 */
	public static void setAttribute(Element elem, Attr attribute) {
		elem.setAttributeNode(attribute);
	}
	/**
	 * 功能说明：根据Map产生一系列Element，来表示一系列的键-值关系。每个Element代表一个<br>
	 *					键-值。此方法等同于调用writeParamNode(doc, map, nodeName, "name", "value");<br>
	 * 输入参数：doc Document:产生Element的Document<br>
	 *			map Map:根据此Map来产生键-值。如果此Map为null,则返回零长数组<br>
	 *			elementName：这些Element的名字<br>
	 * 返回值：Element[]：生成的Element数组<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 16:32:31)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 */
	public static Element[] writeParamNode(
		Document doc,
		Map map,
		String nodeName) {
		return writeParamNode(doc, map, nodeName, "name", "value");
	}
	/**
	 * 功能说明：根据Map产生一系列Element，来表示一系列的键-值关系。每个Element代表一个<br>
	 *					键-值<br>
	 * 例子：Map中存在如下关系，key1对应value1，key2对应value2<br>
	 *			这样调用此方法：writeParamNode(document, map, "Param", "name", "value");<br>
	 *			将会产生如下XML对应的Element<br>
	 *			&lt;Param name="key1" value="value1"/&rt;<br>
	 *			&lt;Param name="key2" value="value2"/&rt;<br>
	 * 输入参数：doc Document:产生Element的Document<br>
	 *			map Map:根据此Map来产生键-值。如果此Map为null,则返回零长数组<br>
	 *			elementName：这些Element的名字<br>
	 *			name:Element中表示键的属性名<br>
	 *			value:Element中表示值的属性名<br>
	 * 返回值：Element[]：生成的Element数组<br>
	 * 异常：<br>
	 * 创建者：<br>
	 * 创建日期：(2002-7-8 16:32:31)<br>
	 * 版本：3.5.0.1<br>
	 * 
	 */
	public static Element[] writeParamNode(
		Document doc,
		Map map,
		String elementName,
		String name,
		String value) {
		if (doc == null || map == null) {
			return ZERO_LENGTH_ELEMENT;
		}

		Iterator iter = map.keySet().iterator();
		String k, v;
		ArrayList result = new ArrayList();
		Element element;
		//Attr attr;
		while (iter.hasNext()) {
			k = iter.next().toString();
			v = map.get(k).toString();
			element = doc.createElement(elementName);
			element.setAttribute(name, k);
			element.setAttribute(value, v);
			result.add(element);
		}
		return (Element[]) result.toArray(ZERO_LENGTH_ELEMENT);
	}
}
