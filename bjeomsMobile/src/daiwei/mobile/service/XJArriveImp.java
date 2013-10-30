package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
/**
 * 巡检经纬度上传并取得返回值
 * @author 都   4/18
 *
 */
public class XJArriveImp implements XJArriveInterface{

	@Override
	public XMLUtil getArrive(Context context, String taskId, String longitude,
			String latitude) {
		TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
		HTTPConnection hc = new HTTPConnection(context.getApplicationContext());
		tc.addElement(tc.recordInfo, "taskId",  String.valueOf(taskId));
		tc.addElement(tc.recordInfo, "longitude", String.valueOf(longitude));
		tc.addElement(tc.recordInfo, "latitude", String.valueOf(latitude));
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "PT005");
		parmas.put("inputXml", tc.doc.asXML());
		String xjStr = hc.Sent(parmas);
        XMLUtil xjXml = new XMLUtil(xjStr.toString());
		return xjXml;
	}

}
