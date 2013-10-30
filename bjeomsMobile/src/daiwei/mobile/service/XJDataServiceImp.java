package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
/**
 * 获取巡检列表
 * @author admin
 * @params taskId：巡检每个item对应的 id
 */
public class XJDataServiceImp implements XJDataService{
	@Override
	public XMLUtil getXJData(Context context, String taskId,String serviceCode) {
		TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
		HTTPConnection hc = new HTTPConnection(context.getApplicationContext());		
		tc.addElement(tc.recordInfo, "taskId", taskId);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", serviceCode);//巡检数据界面
		parmas.put("inputXml", tc.doc.asXML());
		String x = hc.Sent(parmas);
	    
		System.out.println("tc.doc.asXML()===="+tc.doc.asXML()+serviceCode);
		System.out.println("WLAN巡检=========="+x+serviceCode);
		XMLUtil xml = new XMLUtil(x.toString());
		return xml;
	}
	

}
