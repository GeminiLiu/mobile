package daiwei.mobile.service;

import java.util.Timer;
import java.util.TimerTask;

import eoms.mobile.R;
import daiwei.mobile.Tools.GPS;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

/**
 * 后台服务 每个多少秒(目前为1分钟)获取一次GPS是否开启
 * @author 都 5/13
 * 
 */
public class GetGPSService extends Service {
	/** 判断GPS是否开启 初始赋值为未开启 */
	private boolean GPSflag = false;
	/** 发送消息的handler每个60秒发送一次 */
	private Handler gpsHandler;
	/** handler发送的what赋值 根据此what获取handler发送过来的message */
	private final int EVENT_LOCK_WINDOW = 0x100;
	/** 自定义时间控制器 */
	private GPSTimerTask mTimerTask;
	/** 获取GPS的时间间隔 */
	private int timeInterval = 60 * 1000;
	/**第一次执行需要的时间*/
    private int timeFirst = 1000;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate() {
		gpsHandler = new Handler() {
			public void handleMessage(Message message) {
				if (message.what == EVENT_LOCK_WINDOW) {
					GPSflag = GPS.isOPen(getApplicationContext());
					System.out.println("GPSflag 是否开启================"+GPSflag);
					if (!GPSflag) {
						Toast.makeText(getApplicationContext(),
								R.string.gps_message, Toast.LENGTH_SHORT)
								.show();
					}
					else{
						//textMethod();
					}
				}

			}
		};

		mTimerTask = new GPSTimerTask(); // 新建一个任务
		Timer mTimer = new Timer(true);
		mTimer.schedule(mTimerTask, timeFirst, timeInterval);// 第一个参数为任务命
														// 第二个为第一次执行需要的时间
														// 第三个为第一次执行后每个多长时间执行一次任务
		super.onCreate();
	}

	/**
	 * 时间定时器 每个多少秒（目前是一分钟）发送一次消息
	 * @author admin
	 * 
	 */
	class GPSTimerTask extends TimerTask {

		@Override
		public void run() {
			Message msg = gpsHandler.obtainMessage(EVENT_LOCK_WINDOW);
			msg.sendToTarget();
		}

	}
}
