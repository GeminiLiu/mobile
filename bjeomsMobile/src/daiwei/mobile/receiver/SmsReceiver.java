package daiwei.mobile.receiver;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import daiwei.mobile.service.BootService;
import daiwei.mobile.util.AppConstants;
/**
 * 短信拦截的广播
 * @author qch
 *
 */
public class SmsReceiver extends BroadcastReceiver {
	private String TAG = "abortBroadcast";
	private Context mContext;
	public static NotificationManager mNotificationManager;
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	public final int MY_NOTIFY_ID = 123456;
	private static int count = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		SharedPreferences sp=context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		boolean notice= sp.getBoolean(AppConstants.SP_IS_NOTIFICATION_NOTICE, true);//消息提示
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String action = intent.getAction();
		//if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
		if (SMS_RECEIVED_ACTION.equals(action)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				for (Object pdu : pdus) {
					SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
					String sender = message.getOriginatingAddress();
					if (sender.contains("13269237133")) {
						if(notice){//消息提示 弹出notify
							BootService.sendNotify(MY_NOTIFY_ID, message.getMessageBody(),
									message.getMessageBody());
						}
						this.abortBroadcast();//终止广播
						Log.v(TAG, "AbortBroadcast sucessfully!");
					}
				}
			}
		}
	}

}
