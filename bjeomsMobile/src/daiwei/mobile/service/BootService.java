package daiwei.mobile.service;

import java.text.SimpleDateFormat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import eoms.mobile.R;
import daiwei.mobile.activity.DialogVersonActivity;
import daiwei.mobile.activity.SplashActivity;
import daiwei.mobile.util.AppConstants;
/**
 * 重启服务 读系统短信；先拦截，如果短信被拦截，就不会进入系统库就不读系统的短信
 * 得到短信，震动并弹出notificaition的消息提示
 * @author qch
 *
 */
public class BootService extends Service {
	public static final String TAG = "BootService";
	private static Context mContext;
	private ContentObserver mObserver;//内容观察者
	public static NotificationManager mNotificationManager;
	public final static int MY_NOTIFY_ID = 123456;
	private static int count=0;
	private static SharedPreferences sp;
	private static AudioManager mAudioManager;//声音管理者

	@Override
	public void onCreate() {
		Log.i(TAG, "BootService onCreate()执行！");
		super.onCreate();
		mContext = getApplicationContext();
		mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		sp = getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		notice = sp.getBoolean(AppConstants.SP_IS_NOTIFICATION_NOTICE, true);
		addSMSObserver();
		
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//得到短信
			MessageItem item = (MessageItem) msg.obj;
//			myVibrate();//震动
			if(notice)
			sendNotify(MY_NOTIFY_ID, item.getBody(), item.getBody());//弹出notification
		}
	};
	private boolean notice;
/**
 * 短信监听的方法
 */
	public void addSMSObserver() {
		Log.i(TAG, "addSMSObserver. ");
		ContentResolver resolver = getContentResolver();//获取内容观察者
		mObserver = new SMSObserver(resolver, handler);//创建短信内容观察者
		resolver.registerContentObserver(SMS.CONTENT_URI, true, mObserver);

	}
/**
 * 消息个数重置为0
 */
	public static void resetCount(){
		count = 0;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy().");
		this.getContentResolver().unregisterContentObserver(mObserver);
		super.onDestroy();
		System.exit(0);
	}
	/**
	 * 震动的方法
	 */
	/*private void myVibrate(){
		SharedPreferences sp = mContext.getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		Boolean b = sp.getBoolean(AppConstants.SP_IS_AUTO_NOTICE, true);
		if(b){
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(1000);
		}
	}*/
	/**
	 * 弹出Notification
	 * @param id
	 * @param tickerText 通知弹出的文字
	 * @param text2   显示的文本
	 */
	public static void sendNotify(int id, String tickerText, String text2){
		/**
		 * 如果拦截的内容是"PS更新版本"
		 * 更新版本的dialog
		 */
		if(tickerText.split(":")[1].equals(" PS更新版本")){
			System.out.println("新版本更新提示..........");
			Intent intent=new Intent(mContext,DialogVersonActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
		count++;
		long time = System.currentTimeMillis();//获得系统时间
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		String when=sdf.format(time);
		int icon = R.drawable.icon;
		Notification mNotification = new Notification(icon, tickerText, time);
		// 放置在"正在运行"栏目中
//		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
//		mNotification.defaults = Notification.DEFAULT_ALL;
		mNotification.flags |= Notification.FLAG_NO_CLEAR;//滑动的时候，不清楚notify
		mNotification.flags |= Notification.FLAG_AUTO_CANCEL;//通知被点击后，自动消失
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.notify_view);
		contentView.setTextViewText(R.id.notify_name, "EOMS管理"+"("+count+"条新消息)");
		contentView.setTextViewText(R.id.notify_msg, text2 != null ? text2
				: "来自服务的内容");
		contentView.setTextViewText(R.id.notify_time, when);
		mNotification.contentView = contentView;// 指定个性化视图
		
//		String msg = "21739872193#dw#2132123#dw#123123#dw#123123#dw#123123#dw#23234";
//		String[] str = msg.split("#dw#");
		//String msgXML = "<BaseSchema>21739872193<BaseSchema/><BaseSummary>21739872193<BaseSummary/>";
		
		//text2:____________WF4:EL_AM_AT;测试工单0321;ID-651-20130321-00001;000000000000062;;
		String[] str=text2.split(";");
		
//		Intent intent = new Intent(mContext,BaseTmpActivity.class);
		Intent intent = new Intent(mContext,SplashActivity.class);
		/*intent.putExtra("BaseSchema", str[0]);
		intent.putExtra("BaseSummary", str[1]);
		intent.putExtra("BaseSN", str[2]);
		intent.putExtra("BaseID", str[3]);
		intent.putExtra("TaskID", str[4]);
		intent.putExtra("IsWait", 1);
		intent.putExtra("notice", 1);*/
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.contentIntent = contentIntent; // 指定内容意图
		initNotification(mNotification);
		mNotificationManager.notify(MY_NOTIFY_ID, mNotification);
	}
	/**
	 * 震动声音的设置
	 * @param notification
	 */
	private static void initNotification(Notification notification){
		
		boolean b1 = sp.getBoolean(AppConstants.SP_IS_VOICE_NOTICE, true);//声音
		boolean b = sp.getBoolean(AppConstants.SP_IS_AUTO_NOTICE, true);//震动
		System.out.println("声音："+b1);
		System.out.println("震动："+b);
		if(b1){
			if(mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
				//开启声音，但是系统是静音模式,也不能有声音提示
				setRingerMode(true, true);
			}else{
				setRingerMode(false, true);
			}
			notification.defaults |= Notification.DEFAULT_SOUND;
		}
		if(b){
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		
	}
	
	private static void setRingerMode(boolean silent, boolean vibrate) {
		if (silent) {
		 mAudioManager.setRingerMode(vibrate ? AudioManager.RINGER_MODE_VIBRATE : AudioManager.RINGER_MODE_SILENT);
		} else {
		  mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		  mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, vibrate ? AudioManager.VIBRATE_SETTING_ON : AudioManager.VIBRATE_SETTING_OFF);
		}
	}
}
