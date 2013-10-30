package daiwei.mobile.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;

public class SubmitServiceImp implements SubmitService {
	private Context context;

	public XMLUtil submitData(Context context, String baseId, String category,
			String taskId, String actionCode, String actionText,String hasPic,
			String hasRec,Map<String, String> parmas) {
		this.context = context;
		TestXmlCreat tc = new TestXmlCreat(context);
		tc.addElement(tc.recordInfo, "baseId", baseId);
		tc.addElement(tc.recordInfo, "category", category);
		tc.addElement(tc.recordInfo, "taskId", taskId);
		tc.addElement(tc.recordInfo, "actionCode", actionCode);
		tc.addElement(tc.recordInfo, "actionText", actionText);

		Set<Entry<String, String>> set = parmas.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", entry.getKey());
			tc.addElement(tc.recordInfo, "field", entry.getValue(), map);
		}

		Map<String, String> parmasInout = new HashMap<String, String>();
		parmasInout.put("serviceCode", "G004");
		parmasInout.put("inputXml", tc.doc.asXML());
		parmasInout.put("hasPic", hasPic);
		parmasInout.put("hasRec", hasRec);
		XMLUtil xml = null;
		if(getNet()){
			HTTPConnection hc = new HTTPConnection(context);
			System.out.println("隐患上传地址============"+tc.doc.asXML());
			//============================xxj====================================================
			String x = hc.Sent(parmasInout);
//			String x = "";
			//================================================================================
			System.out.println("隐患返回数据x========="+x);
			xml = new XMLUtil(x.toString());
		}else{
			DBHelper helper = new DBHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			if(db.isOpen()){
				db.execSQL("update tb_gd_cache set actionCode = ? , actionText = ? , serviceCode = 'G004' , inputXml = ? ,hasPic = ? , hasRec = ? , IsWait = 0, IsUpload = 0 where TaskID = ?",
						new String[]{actionCode,actionText,tc.doc.asXML(),hasPic,hasRec,taskId});
				db.close();
			}
		}
		return xml;
	}

	private boolean getNet(){
		return NetWork.checkWork(context)==1;
	}
	
	
	@Override
	public XMLUtil submitData(Context context, String baseId, String category,
			String taskId, String actionCode, String actionText,
			Map<String, String> parmas, Map<String, File> files) {
		this.context = context;
		TestXmlCreat tc = new TestXmlCreat(context);
		HTTPConnection hc = new HTTPConnection(context);

		tc.addElement(tc.recordInfo, "baseId", baseId);
		tc.addElement(tc.recordInfo, "category", category);
		tc.addElement(tc.recordInfo, "taskId", taskId);
		tc.addElement(tc.recordInfo, "actionCode", actionCode);
		tc.addElement(tc.recordInfo, "actionText", actionText);

		Set<Entry<String, String>> set = parmas.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("code", entry.getKey());
			tc.addElement(tc.recordInfo, "field", entry.getValue(), map);
		}

		Map<String, String> parmasInout = new HashMap<String, String>();
		parmasInout.put("serviceCode", "G004");
		parmasInout.put("inputXml", tc.doc.asXML());
		String x = "";
		try {
			x = hc.Sent(parmasInout, files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XMLUtil xml = null;
		if (x==null) {
		
		}
		else{
			xml = new XMLUtil(x.toString());
		}
		return xml;
	}

}
