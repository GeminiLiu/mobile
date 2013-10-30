package daiwei.mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import daiwei.mobile.service.AutoUploadService;
import daiwei.mobile.service.BootService;
import daiwei.mobile.util.AppConstants;

public class BootCompleteReceiver extends BroadcastReceiver {
	private static final String TAG = "BootCompleteReceiver";
	
	/**
	 * 当手机重启的时候 注册广播接受 开启服务
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent service = new Intent(context, BootService.class);
			context.startService(service);
			SharedPreferences sp = context.getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			boolean isOpen = sp.getBoolean(AppConstants.SP_IS_AUTO_UPLOAD, true);// 默认是开启的
			if (isOpen) {//用户设置为开启
				context.startService(new Intent(context, AutoUploadService.class));//启动工单附件自动上传服务
			}
			Log.i(TAG, "手机启动了，开启服务");
		}
	}
}
