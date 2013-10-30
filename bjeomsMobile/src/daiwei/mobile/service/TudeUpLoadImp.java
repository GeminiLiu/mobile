package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
/**
 * 经纬度上传服务
 * @author 都  5/14
 * @params trackObjectName：用户名  longitude：经度   latitude：纬度
 */
public class TudeUpLoadImp implements TudeService{
	@Override
	public XMLUtil getTudeData(Context context, String trackType,String userName,
			String trackObjectName, String longitude, String latitude) {
		TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
		HTTPConnection hc = new HTTPConnection(context.getApplicationContext());		
		tc.addElement(tc.recordInfo, "trackType", trackType);
		tc.addElement(tc.recordInfo, "trackObjectId", userName);
		tc.addElement(tc.recordInfo, "trackObjectName", trackObjectName);
		tc.addElement(tc.recordInfo, "longitude", longitude);
		tc.addElement(tc.recordInfo, "latitude", latitude);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "L003");
		parmas.put("inputXml", tc.doc.asXML());
		String x = hc.Sent(parmas);
		XMLUtil xml = new XMLUtil(x.toString());
		return xml;
	}

}
