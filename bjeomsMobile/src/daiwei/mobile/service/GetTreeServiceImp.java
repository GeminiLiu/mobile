package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;

public class GetTreeServiceImp implements GetTreeService{

	@Override
	public String getTree(Context context, String showCorp, String showCenter,
			String showStation, String showTeam, String showPerson,
			String multi, String selectObjs, String cityID, String specialtyID) {
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);
		
		tc.addElement(tc.recordInfo, "showCorp", showCorp);
		tc.addElement(tc.recordInfo, "showCenter", showCenter);
		tc.addElement(tc.recordInfo, "showStation", showStation);
		tc.addElement(tc.recordInfo, "showTeam", showTeam);
		tc.addElement(tc.recordInfo, "showPerson", showPerson);
		tc.addElement(tc.recordInfo, "multi", multi);
		tc.addElement(tc.recordInfo, "selectObjs", selectObjs);
		tc.addElement(tc.recordInfo, "cityID", cityID);
		tc.addElement(tc.recordInfo, "specialtyID", specialtyID);
		
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "T001");
		parmas.put("inputXml", tc.doc.asXML());
		
		String x = hc.Sent(parmas);
		return x;
	}
	//获取eoms派发树
		@Override
		public String getOrgTrr(Context context, String showTeam,
				String showPerson, String multi, String selectObjs, String parentID) {
			TestXmlCreat tc = new TestXmlCreat(context);
			HTTPConnection hc = new HTTPConnection(context);

			tc.addElement(tc.recordInfo, "showTeam", showTeam);
			tc.addElement(tc.recordInfo, "showPerson", showPerson);
			tc.addElement(tc.recordInfo, "multi", multi);
			tc.addElement(tc.recordInfo, "selectObjs", selectObjs);
			tc.addElement(tc.recordInfo, "parentID", parentID);
			
			Map<String, String> parmas = new HashMap<String, String>();
			parmas.put("serviceCode", "T002");
			parmas.put("inputXml", tc.doc.asXML());

			String x = hc.Sent(parmas);
			Log.i("GetTreeServiceImp-----eoms派发树", x);
			return x;
		}
	
	
}