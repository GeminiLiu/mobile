package daiwei.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.TestXmlCreat;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class SiteUploadService extends Service {
	/** 发送消息的handler每个60秒发送一次 */
	private Handler siteHandler;
	/** handler发送的what赋值 根据此what获取handler发送过来的message */
	private final int EVENT_LOCK_WINDOW = 0x100;
	/** 自定义时间控制器 */
	private SiteTimerTask siteTimerTask;
	/** 上传位置的时间间隔 */
	private int timeInterval = 30*1000;
	/**第一次执行需要的时间*/
    private int timeFirst = 30*1000;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate() {
		siteHandler = new Handler() {
			public void handleMessage(Message message) {
				if (message.what == EVENT_LOCK_WINDOW) {	
					Context context = getApplicationContext(); 
					DBHelper helper = new DBHelper(context);
					System.out.println("!!!!!!!!!!!!!!!!");
					TestXmlCreat tc = new TestXmlCreat(context);
					
					List<Map<String,String>> mapList = helper.executeQuery("select * from tb_line_position where IsUpLoad = 0", null);
					System.out.println("mapList======"+mapList);
					for(Map<String,String> map : mapList){
						
						HashMap<String,String> hashmap = new HashMap<String,String>();
						
						hashmap.put("Latitude", map.get("Latitude"));
						hashmap.put("Longitude", map.get("Longitude"));		
						hashmap.put("LocalTime", map.get("LocalTime"));
						hashmap.put("lineUUID", map.get("LineNo"));
						hashmap.put("taskId", map.get("TaskID"));
						
						tc.addElement(tc.recordInfo, "Site", "",hashmap);
						Map<String, String> parmasInout = new HashMap<String, String>();
						parmasInout.put("serviceCode", "PTL005");
						parmasInout.put("inputXml", tc.doc.asXML());
						System.out.println("位置服务..........=="+tc.doc.asXML());
						HTTPConnection hc = new HTTPConnection(context);
						String x = hc.Sent(parmasInout);
						System.out.println("上传x========="+x);
						XMLUtil xml = new XMLUtil(x.toString());
						System.out.println("xml========="+xml.getSuccess());
						SQLiteDatabase db = helper.getWritableDatabase();
						if(xml.getSuccess()){
							if(db.isOpen()){
							db.execSQL("update tb_line_position set IsUpLoad = 1 where  TaskID = ?" , new String[]{map.get("TaskID")});
							db.close();
							}
						}
					}
				}

			}
		};

		siteTimerTask = new SiteTimerTask(); // 新建一个任务
		Timer mTimer = new Timer(true);
		mTimer.schedule(siteTimerTask, timeFirst, timeInterval);// 第一个参数为任务命
														// 第二个为第一次执行需要的时间
														// 第三个为第一次执行后每个多长时间执行一次任务
		super.onCreate();
	}

	/**
	 * 时间定时器 每个多少秒（目前是一分钟）发送一次消息
	 * @author admin
	 * 
	 */
	class SiteTimerTask extends TimerTask {

		@Override
		public void run() {
			Message msg = siteHandler.obtainMessage(EVENT_LOCK_WINDOW);
			msg.sendToTarget();
		}

	}
}