package cn.com.ultrapower.eoms.util.remedy;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.com.ultrapower.eoms.util.Log;
import cn.com.ultrapower.eoms.util.XMLUtil;

public class ConstantsManager {
	private File file;

	private Document doc;
	public ConstantsManager(){}
	public ConstantsManager(String _filePath) {
		file = new File(_filePath + File.separator + "arform.xml");
		try {
			doc = (Document)XMLUtil.getDocument(file);
		} catch (Exception e) {
			Log.logger.error("XML解析初始化失败");
			e.printStackTrace();
		}
	}

	public void getConfigContant(String _tagName) {
		
		Node root = null;
		NodeList nodeList = null;
		NamedNodeMap attributeList = null;
		
		try {
			root = doc.getFirstChild();
			if (root != null && root.hasChildNodes()) {
				nodeList = root.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node form = (Node) nodeList.item(i);
					if (form.getNodeName() != null && form.getNodeName().equalsIgnoreCase(_tagName)) {
							attributeList = form.getAttributes();
							
							RemedyForm remedyForm = new RemedyForm();
							remedyForm.setFormName(attributeList.getNamedItem("FormName").getNodeValue());
							remedyForm.setFormSchema(attributeList.getNamedItem("FormSchema").getNodeValue());
							remedyForm.setProcessSchema(attributeList.getNamedItem("processSchema").getNodeValue());
							remedyForm.setOwnerFiledValue(attributeList.getNamedItem("OwnerFiledValue").getNodeValue());
							
							Constants.hashmap.put(remedyForm.getFormName(),remedyForm);
					}
				}
			}
		} catch (Exception e) {
			Log.logger.error("读取arform.xml时，发现异常！");
			e.printStackTrace();
		}
	}

	public void getConstantInstance(){
		getConfigContant("form");
    }
	
}
