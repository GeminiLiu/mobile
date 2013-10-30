package daiwei.mobile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 常晓飞
 * @author changxiaofei
 * @time 2013-4-9 上午10:12:05
 */
public class WidgetService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
