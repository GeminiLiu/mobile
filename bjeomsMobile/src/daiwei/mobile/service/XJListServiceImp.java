package daiwei.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import daiwei.mobile.animal.XJListModel;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
/**
 * 获取巡检列表
 * @author 都3/25
 *
 */
public class XJListServiceImp implements XJListService{

	@Override
	public XJListModel getXJWaitList(Context context, int isWait, int pageNum,
			int pageSize,String specialtyId,String serviceCode) {
		XJListModel xjm=new XJListModel();
		TestXmlCreat tc = new TestXmlCreat(context.getApplicationContext());
		HTTPConnection hc = new HTTPConnection(context.getApplicationContext());
		tc.addElement(tc.recordInfo, "isWait",  String.valueOf(isWait));
		tc.addElement(tc.recordInfo, "currentPage", String.valueOf(pageNum));
		tc.addElement(tc.recordInfo, "pageSize", String.valueOf(pageSize));
		tc.addElement(tc.recordInfo, "specialtyId", specialtyId);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", serviceCode);
		parmas.put("inputXml", tc.doc.asXML());
		String xjStr = hc.Sent(parmas);
		System.out.println("tc.doc.asXML()===="+tc.doc.asXML());
        XMLUtil xjXml = new XMLUtil(xjStr.toString());
        if(serviceCode.equals("PTL001")){
        	xjm.setXJListInfo(xjXml.getXJLineList());
        }
        else{
        xjm.setXJListInfo(xjXml.getXJList());
        }
		return xjm;
	}

	@Override
	public List<Map<String, String>> getFinishList(int pageNum) {
		return null;
	}

}
