package daiwei.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;
import android.content.Context;
import android.widget.Toast;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;

public class GDDataServiceImp implements GDDataService {
	private Context context;

	public XMLUtil getData(Context context, String baseId, String taskId,
			String category) {
		this.context = context;

		String x = "";
		if (getNet()) {
			TestXmlCreat tc = new TestXmlCreat(context);
			HTTPConnection hc = new HTTPConnection(context);

			tc.addElement(tc.recordInfo, "baseId", baseId);
			tc.addElement(tc.recordInfo, "category", category);
			tc.addElement(tc.recordInfo, "taskId", taskId);

			Map<String, String> parmas = new HashMap<String, String>();
			parmas.put("serviceCode", "G002");// 工单数据界面
			parmas.put("inputXml", tc.doc.asXML());

			x = hc.Sent(parmas);
		}else{
			Toast.makeText(context, "网络不给力，数据获取失败!!", 0).show();
		}
		
//		else {
//			System.out.println("走的是这里.............");
//			x = FileUtil.readFile(Environment.getExternalStorageDirectory()
//					.getPath()
//					+ AppConstants.FILE_CACHE
//					+ "/"
//					+ taskId
//					+ "/data.xml");
//			System.out.println("x==============="+Environment.getExternalStorageDirectory()
//					.getPath()
//					+ AppConstants.FILE_CACHE
//					+ "/"
//					+ taskId
//					+ "/data.xml");
//		}

		XMLUtil xml = new XMLUtil(x.toString());

		return xml;
	}

	public boolean getNet(){
		return NetWork.checkWork(context)==1;
	}
	@Override
	public List<String> getProcessInfo(Context context,String baseId, String category) {
		ArrayList<String> ProcessInfoList = new ArrayList<String>();
		this.context=context;
		String x = "";
		if (getNet()) {
			TestXmlCreat tc = new TestXmlCreat(context);
			HTTPConnection hc = new HTTPConnection(context);
			tc.addElement(tc.recordInfo, "baseId", baseId);
			tc.addElement(tc.recordInfo, "category", category);
			Map<String, String> parmas = new HashMap<String, String>();
			parmas.put("serviceCode", "G005");// 流程记录
			parmas.put("inputXml", tc.doc.asXML());

			x = hc.Sent(parmas);
			System.out.println("齐彩华上传G005======="+tc.doc.asXML());
			System.out.println("齐彩华获取数据 xxxxxxx=="+x);
			
			XMLUtil xml = new XMLUtil(x);
//			Iterator<Element> ProcessInfos = xml.root.element("recordInfo").element("ProcessInfos").elements("ProcessInfo").iterator();
			Iterator<Element> ProcessInfos = xml.root.elements("ProcessInfo").iterator();
				String space = "   ";
				while (ProcessInfos.hasNext()) {
					Iterator<Element> InfoViews = ProcessInfos.next().element("InfoViews").elements("InfoView").iterator();
					while (InfoViews.hasNext()){
						Element InfoView = InfoViews.next();
						StringBuffer sb = new StringBuffer();
						sb.append(">").append(space);
						sb.append(InfoView.element("ProcessinfoFinishDate").getText()).append(space);
						sb.append(InfoView.element("ProcessinfoDealer").getText());
						sb.append(InfoView.element("ProcessinfoStatus").getText()).append(",");
						sb.append(InfoView.element("ProcessinfoDesc").getText());
						
						ProcessInfoList.add(sb.toString());
					}
				}
		} 
		return ProcessInfoList;
	} 
	
	
}