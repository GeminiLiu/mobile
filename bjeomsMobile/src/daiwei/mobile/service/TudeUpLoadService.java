package daiwei.mobile.service;

import java.util.Timer;
import java.util.TimerTask;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.util.AppInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * 经纬度上传服务
 * 
 * @author 都 5/14
 * 
 */
public class TudeUpLoadService extends Service {
	private static final int TUDEWHAT = 1;
	private static final int TIMEERWHAT = 2;
	/** 定义全局经纬度变量 */
	private String longitude, latitude;
	/** 定义全局handler 发送和接受消息 */
	private Handler tudeHandler;
	/** 自定义时间控制器 */
	private TuedTimerTask mTimerTask;
	/** 获取经纬度的时间间隔 */
	private int timeInterval = 5 * 60 * 1000;
	/** 第一次执行需要的时间 */
	private int timeFirst = 1000;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		tudeHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case TUDEWHAT:// handler接受到1时 开启线程上传经纬度到服务器
					TudeThread tudeThread = new TudeThread(longitude, latitude);
					tudeThread.start();
					break;
				case TIMEERWHAT:// handler接受到2时 获取当前位置的经纬度
					if (NetWork.checkNetWork(getApplicationContext())) {
						MyLocationListenner myListener = new MyLocationListenner();
						LocationClient mLocClient = new LocationClient(
								getApplicationContext());
						mLocClient.registerLocationListener(myListener);
						mLocClient.requestLocation();
						mLocClient.start();
					}
					break;

				}
			}

		};
		mTimerTask = new TuedTimerTask(); // 新建一个任务
		Timer mTimer = new Timer(true);
		mTimer.schedule(mTimerTask, timeFirst, timeInterval);// 第一个参数为任务命
		// 第二个为第一次执行需要的时间
		// 第三个为第一次执行后每个多长时间执行一次任务
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			longitude = Double.toString(location.getLongitude());
			latitude = Double.toString(location.getLatitude());
			tudeHandler.sendEmptyMessage(TUDEWHAT);
		}
		public void onReceivePoi(BDLocation poiLocation) {
			System.out.println("查看获取当前的地理类型============" + poiLocation);
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			longitude = Double.toString(poiLocation.getLatitude());
			latitude = Double.toString(poiLocation.getLongitude());
			tudeHandler.sendEmptyMessage(TUDEWHAT);
		}
	}

	/** 上传经纬度的线程 */
	class TudeThread extends Thread {
		String longitude, latitude;

		private TudeThread(String longitude, String latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}

		@Override
		public void run() {
			String userName=AppInfo.getUserFromSharedPreferences(getApplicationContext()).getUsername();
			if(userName!=null||userName!=""){
			TudeUpLoadImp tudeUpLoad = new TudeUpLoadImp();
			XMLUtil ele = tudeUpLoad.getTudeData(getApplicationContext(), "0",
					LoginServiceImp.userName, userName,longitude, latitude);
			}
			super.run();
		}

	}

	/**
	 * 时间定时器 每个多少秒（目前是一分钟）发送一次消息
	 * @author admin
	 * 
	 */
	class TuedTimerTask extends TimerTask {

		@Override
		public void run() {
			Message msg = tudeHandler.obtainMessage(TIMEERWHAT);
			msg.sendToTarget();
		}

	}
}
