package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.os.Environment;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.FileUtil;

/**
 * 工单加载动作界面接口
 * 
 * @author Administrator
 * 
 */
public class GetActionServiceImp implements GetActionService {
	private Context context;

	public XMLUtil getData(Context context, String baseId, String category,
			String taskId, String actionId, String actionCode) {
		this.context = context;
		String x = "";
		if (getNet()) {
			TestXmlCreat tc = new TestXmlCreat(context);
			HTTPConnection hc = new HTTPConnection(context);

			tc.addElement(tc.recordInfo, "baseId", baseId);
			tc.addElement(tc.recordInfo, "category", category);
			tc.addElement(tc.recordInfo, "taskId", taskId);
			tc.addElement(tc.recordInfo, "actionId", actionId);
			tc.addElement(tc.recordInfo, "actionCode", actionCode);

			Map<String, String> parmas = new HashMap<String, String>();
			parmas.put("serviceCode", "G003");//获取初始值
			parmas.put("inputXml", tc.doc.asXML());

			x = hc.Sent(parmas);
		} else {
			x = FileUtil.readFile(Environment.getExternalStorageDirectory()
					.getPath()
					+ AppConstants.FILE_CACHE
					+ "/"
					+ taskId
					+ "/"
					+ actionCode + "-" + actionId + ".xml");
		}
		XMLUtil xml = new XMLUtil(x.toString());

		return xml;
	}

	public boolean getNet() {
		return NetWork.checkWork(context)==1;
	}
}
