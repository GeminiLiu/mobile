package daiwei.mobile.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import android.content.Context;
import daiwei.mobile.activity.BaseApplication;

public class TestXmlCreat {
	public Document doc;
	public Element root;
	public Element baseInfo;
	public Element recordInfo;
	
	/**
	 * 非登录http请求用，请求所需的用户名和密码参数由本方法从Application缓存里获取<br>
	 * <b>注： context用getApplicationContext()<b>
	 * @param context 用getApplicationContext()
	 */
	public TestXmlCreat(Context context) {
		this.doc = DocumentHelper.createDocument();
		this.root = this.doc.addElement("opDetail");
		this.baseInfo = CreatNode(root, "baseInfo");
		this.recordInfo = CreatNode(root, "recordInfo");
		// 暂时没有验证发送基础信息
		addElement(this.baseInfo, "userName", ((BaseApplication) context).getName());
		addElement(this.baseInfo, "password", ((BaseApplication) context).getPsw());
		addElement(this.baseInfo, "machineCode", "phone");
		addElement(this.baseInfo, "simNum", "12345");
	}
	
	/**
	 * 登录http请求用，要带用户名和密码参数<br>
	 * <b>注： context用getApplicationContext()<b>
	 * @param context 用getApplicationContext()
	 * @param name
	 * @param psw
	 */
	public TestXmlCreat(Context context, String name, String psw) {
		this.doc = DocumentHelper.createDocument();
		this.root = this.doc.addElement("opDetail");
		this.baseInfo = CreatNode(root, "baseInfo");
		this.recordInfo = CreatNode(root, "recordInfo");
		// 暂时没有验证发送基础信息
		addElement(this.baseInfo, "userName", name);
		addElement(this.baseInfo, "password", psw);
		addElement(this.baseInfo, "machineCode", "phone");
		addElement(this.baseInfo, "simNum", "12345");
	}
	
	public Element CreatNode(Element elt, String Node) {
		Element elm = elt.addElement(Node);
		return elm;
	}
	
	public void addElement(String Node, String Text, String... Attributes) {
		Element elm = root.addElement(Node);
		elm.setText(Text);
		for (int i = 0; i < Attributes.length; i++) {
			elm.addAttribute(Attributes[i], Attributes[i++]);
		}
		
	}
	public void addElement(Element elt, String Node, String Text) {
		if (Text == null) {
			Text = "";
		}
		Element elm = elt.addElement(Node);
		elm.setText(Text);
	}
	
	public void addElement(Element elt, String Node, String Text, Map<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		Element elm = elt.addElement(Node);
		if (Text == null) {
			Text = "";
		}
		elm.setText(Text);
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			elm.addAttribute(entry.getKey(), entry.getValue());
		}
	}
	// public static void main(String[] args){
	// TestXmlCreat();
	// addElement("fieldName","fieldContext");
	// }
}