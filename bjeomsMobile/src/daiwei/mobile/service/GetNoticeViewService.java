package daiwei.mobile.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import eoms.mobile.R;
import daiwei.mobile.activity.SplashActivity;
import daiwei.mobile.activity.TestActivity;
import daiwei.mobile.receiver.BootCompleteReceiver;



/**
 * @author Administrator
 * 
 */
public class GetNoticeViewService extends Service {
	private static Context mContext;
	private static NotificationManager mNotificationManager;
	private static int count=0;
	public static final int MY_NOTIFY_ID = 123456;
	public static boolean isStart;

	@Override
	public void onCreate() {

		mContext = getApplicationContext();
		mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
//		setNotification(null);
		sendNotify(MY_NOTIFY_ID, "您好，欢迎进入代维管理系统", null);
		isStart = true;
		super.onCreate();
		
		IntentFilter filter = new IntentFilter();
		filter.setPriority(Integer.MAX_VALUE);//设置最高优先级
		filter.addAction("daiwei.sms");
		registerReceiver(new BroadcastReceiver() {//注册广播接受者

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				Log.e("接收到广播", "receive");
				count++;
				setNotification(intent);
			}
		}, filter);

	}
	
	public static void resetCount(){
		count = 0;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 创建通知
	 */
	private void  setNotification(Intent i) {
		String sender = null;
		String body = null;
		if (i != null) {
					body = i.getStringExtra("sms");
		}
		sendNotify(MY_NOTIFY_ID, body, body);

	}
	
	public static void sendNotify(int id, String tickerText, String text2){
		long when = System.currentTimeMillis();
		int icon = R.drawable.icon;
		Notification mNotification = new Notification(icon, tickerText, when);
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.notify_view);
		
		contentView.setTextViewText(R.id.notify_name, "代维管理"+"("+count+"条新消息"+")");
		contentView.setTextViewText(R.id.notify_msg, text2 != null ? text2
				: "来自服务的内容");
		contentView.setTextViewText(R.id.notify_time, "17:50");
		mNotification.contentView = contentView;// 指定个性化视图
		
//		String msg = "21739872193#dw#2132123#dw#123123#dw#123123#dw#123123#dw#23234";
//		String[] str = msg.split("#dw#");
		//String msgXML = "<BaseSchema>21739872193<BaseSchema/><BaseSummary>21739872193<BaseSummary/>";
		Intent intent = new Intent(mContext,SplashActivity.class);
	/*	intent.putExtra("BaseSchema", str[0]);
		intent.putExtra("BaseSummary", str[1]);
		intent.putExtra("BaseSN", str[2]);
		intent.putExtra("BaseID", str[3]);
		intent.putExtra("TaskID", str[4]);*/
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.contentIntent = contentIntent; // 指定内容意图

		mNotificationManager.notify(MY_NOTIFY_ID, mNotification);
	}

}
