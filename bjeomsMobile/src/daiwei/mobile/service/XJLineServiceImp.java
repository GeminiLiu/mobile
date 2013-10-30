package daiwei.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;

import android.R.integer;
import android.content.Context;
import daiwei.mobile.animal.Site;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;

public class XJLineServiceImp implements XJLineService {
	private Context mContext;
	private String mTaskId;

	public XJLineServiceImp(Context context,String taskId) {
     this.mContext = context;
     this.mTaskId = taskId;
	}

	@Override
	public HashMap<Integer, ArrayList<Site>> getSiteMap(String taskId) {
		HashMap<Integer, ArrayList<Site>> hashMap = new HashMap<Integer, ArrayList<Site>>();
		
		TestXmlCreat tc = new TestXmlCreat(mContext);
		tc.addElement(tc.recordInfo, "taskId", mTaskId);
//		tc.addElement(tc.recordInfo, "lineNo", "1");
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "PTL007");
		parmas.put("inputXml", tc.doc.asXML());
		HTTPConnection hc = new HTTPConnection(mContext);
		String x = hc.Sent(parmas);
		System.out.println("############x==========="+x);
		XMLUtil xml = new XMLUtil(x.toString());
		System.out.println("xml.getSucess"+xml.getSuccess());
		if(xml.getSuccess()){
		Iterator<Element> groupIts = xml.root.element("recordInfo").elements("group").iterator();
		int i = 0;
		while (groupIts.hasNext()) {
			
			ArrayList<Site> list = new ArrayList<Site>();
			
			Element group = groupIts.next();
			Iterator<Element> siteIts = group.elements("Site").iterator();
			
			while(siteIts.hasNext()){
				Element siteIt = siteIts.next();
				Site site = new Site();
				site.setLatitude(Double.valueOf(siteIt.attributeValue("Latitude")));
				site.setLongitude(Double.valueOf(siteIt.attributeValue("Longitude")));
				list.add(site);
			}
			hashMap.put(i, list);
			i++;
			//mapNum.put(rowInfo.attributeValue("type"), rowInfo.getText());
		}
		}
		
		
//		double lat = 41.50;
//		HashMap<Integer, ArrayList<Site>> hashMap = new HashMap<Integer, ArrayList<Site>>();
//		for(int i=0;i<4;i++){
//			lat = lat - 0.05;
//			double lng = 123.28;
//			ArrayList<Site> list = new ArrayList<Site>();
//			for (int ii = 0; ii < 10; ii++) {
//				lng = lng - 0.05;
//				if(ii%2==0){
//					lat=lat-0.05;
//				}
//				Site site = new Site();
//				site.setLatitude(lat);
//				site.setLongitude(lng);
//				list.add(site);
//			}
//			hashMap.put(i, list);
//		}
		return hashMap;
	}

	@Override
	public ArrayList<Site> getSiteList(String taskId, integer lineNo) {
		return null;
	}

}