package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;

/**
 * 登陆成功后 上传字段到服务器 并获取模版
 * 
 * @author admin
 * 
 */
public class LoginDownLoad {
	public String getWaitList(Context context, String type) {
		String str = null;
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		tc.addElement(tc.recordInfo, "category", "workflow");
		tc.addElement(tc.recordInfo, "version", type);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "L002");
		parmas.put("inputXml", tc.doc.asXML());
		str = hc.Sent(parmas);
		return str;
	}
}