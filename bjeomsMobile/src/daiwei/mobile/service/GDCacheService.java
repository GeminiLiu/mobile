package daiwei.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import daiwei.mobile.activity.BaseApplication;
import daiwei.mobile.animal.ListModel;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.db.DBHelper;

/**
 * 工单缓存业务类
 * @author changxiaofei
 * @time 2013-3-27 下午4:02:39
 */
public class GDCacheService {
	private static final String TAG = "gdCacheService";
	private static GDCacheService gdCacheService;
	
	/**
	 * @return
	 */
	public synchronized static GDCacheService getInstance() {
		if (gdCacheService == null) {
			gdCacheService = new GDCacheService();
		}
		return gdCacheService;
	}
	
	/**
	 * 联网获取工单数据缓存数据，并存到数据库里 。
	 * @param context
	 * @param list
	 * @return
	 */
/*	public boolean loadGDCache(Context context, ArrayList<ContentValues> list) {
		boolean result = false;
		if (list == null || list.size() == 0) {
			return result;
		}
		// 生成请求参数xml
		Document doc = DocumentHelper.createDocument();
		//doc.setXMLEncoding("utf-8");
		Element opDetail = doc.addElement("opDetail");
		Element baseInfo = opDetail.addElement("baseInfo");
		String userName = ((BaseApplication)context).getName();
		baseInfo.addElement("userName").addText(userName);
		Element recordInfo = opDetail.addElement("recordInfo");
		for (ContentValues cv : list) {
			recordInfo.addElement("item").addAttribute("taskid", cv.getAsString("taskid"))
				.addText(new StringBuilder(cv.getAsString("baseId")).append(",").append(cv.getAsString("category")).toString());
		}
		Log.d(TAG, "loadGDCache xml= " + doc.asXML());
		//http请求
		HashMap<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "G007");
		parmas.put("inputXml", doc.asXML());
		HTTPConnection.post(context, parmas);
		return result;
	}*/
	
	/**
	 * 从db获取缓存的工单列表。
	 * @param context
	 * @param type 类型
	 * @return
	 */
	public ListModel getGdInfo(Context context, String type) {
		ListModel listModel = new ListModel();
		List<Map<String, String>> list= new ArrayList<Map<String, String>>();
		Map<String, String> mapBaseCount = new HashMap<String, String>();
		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase db = null;
		db = dbHelper.getReadableDatabase();
		Cursor cursor = null;
		Cursor cursorCount = null;
		try {
			if("ALL".equals(type)){
				cursor = db.rawQuery("select type, xml_info from tb_gd_cache", null);
			}else{
				cursor = db.rawQuery("select type, xml_info from tb_gd_cache where type glob ?", new String[]{type});
			}
			if(cursor!=null && cursor.getCount()>0){
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					String tempType = cursor.getString(0);
					String tempXml = cursor.getString(1);
					//???解析xml
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
				}
			}
			int EL_AM_TTH = 0;
			int EL_AM_PS = 0;
			int EL_AM_AT = 0;
			cursorCount = db.rawQuery("select count(_id) from tb_gd_cache group by type where type glob 'WF4:EL_AM_TTH'", null);
			if(cursorCount!=null && cursorCount.getCount()>0){
				cursorCount.moveToNext();
				EL_AM_TTH = cursorCount.getInt(0);
			}
			cursorCount.close();
			cursorCount = db.rawQuery("select type, xml_info from tb_gd_cache group by type where type glob 'WF4:EL_AM_PS')", null);
			if(cursorCount!=null && cursorCount.getCount()>0){
				cursorCount.moveToNext();
				EL_AM_PS = cursorCount.getInt(0);
			}
			cursorCount.close();
			cursorCount = db.rawQuery("select type, xml_info from tb_gd_cache group by type where type glob 'WF4:EL_AM_AT')", null);
			if(cursorCount!=null && cursorCount.getCount()>0){
				cursorCount.moveToNext();
				EL_AM_AT = cursorCount.getInt(0);
			}
			cursorCount.close();
			mapBaseCount.put("WF4:EL_AM_TTH", String.valueOf(EL_AM_TTH));
			mapBaseCount.put("WF4:EL_AM_PS", String.valueOf(EL_AM_PS));
			mapBaseCount.put("WF4:EL_AM_AT", String.valueOf(EL_AM_AT));
			mapBaseCount.put("ALL", String.valueOf(EL_AM_TTH+EL_AM_PS+EL_AM_AT));
		} catch (Exception e) {
			Log.e(TAG, "getGdInfo " + e.toString());
			e.printStackTrace();
		}finally{
			try {
				if(cursor!=null){
					cursor.close();
				}
				if(cursorCount!=null && !cursorCount.isClosed()){
					cursorCount.close();
				}
				db.close();
			} catch (Exception e2) {
			}
		}
		listModel.setBaseCount(mapBaseCount);
		listModel.setListInfo(list);
		return listModel;
	}

}
