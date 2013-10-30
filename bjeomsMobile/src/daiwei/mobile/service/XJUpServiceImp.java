package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.Element;

import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import android.content.Context;
/**
 * 巡检完成上传服务类
 * @author du 4/1
 *
 */
public class XJUpServiceImp  implements XJUpService{

	public XMLUtil getXJUpLoad(Context context, String taskId,String status,String longitude,String latitude,
			String taskResult, String taskNote, Map<String,String> ContentMap,String serviceCode) {
		//XJUpModel xjUp=new XJUpModel();
		TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
		HTTPConnection hc = new HTTPConnection(context.getApplicationContext());
		tc.addElement(tc.recordInfo, "taskId",  String.valueOf(taskId));
		tc.addElement(tc.recordInfo, "status",  String.valueOf(status));
		tc.addElement(tc.recordInfo, "longitude", String.valueOf(longitude));
		tc.addElement(tc.recordInfo, "latitude", String.valueOf(latitude));
		tc.addElement(tc.recordInfo, "taskResult", String.valueOf(taskResult));
		tc.addElement(tc.recordInfo, "taskNote", String.valueOf(taskNote));
		Element contents = tc.CreatNode(tc.recordInfo, "contents");
		
		System.out.println("String.valueOf(taskId)=========="+String.valueOf(taskId));
		Set<Entry<String, String>> set = ContentMap.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			Element content = tc.CreatNode(contents, "content");
			tc.addElement(content, "taskContentId", entry.getKey());
			tc.addElement(content, "dataValue", entry.getValue());
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("code", entry.getKey());
//			tc.addElement(tc.recordInfo, "field", entry.getValue(), map);
		}
		
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", serviceCode);
		parmas.put("inputXml", tc.doc.asXML());
		System.out.println("线路巡检上传XML=========="+tc.doc.asXML());
		String xjStr = hc.Sent(parmas);		
		System.out.println("服务器返回xml==========="+xjStr);
        XMLUtil xjXml = new XMLUtil(xjStr.toString());
        //xjUp.setSuccess(xjXml.getSuccess());
		return xjXml;
	}

}
