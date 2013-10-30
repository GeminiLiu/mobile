package daiwei.mobile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import daiwei.mobile.util.AppConstants;

/**
 * 工单附件自动上传服务
 * @author changxiaofei
 * @time 2013-3-27 下午3:28:24
 */
public class AutoUploadService extends Service {
	private static final String TAG = "AutoUploadService";
	private volatile boolean flag = true;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "AutoUploadService onCreate()");
		// 定时扫描工单附件缓存文件夹，有附件则自动上传，扫描完毕继续定时扫描。
		new UploadThread().start();
		new UploadCacheThread().start();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.i(TAG, "AutoUploadService onStart()");
	}

	class UploadThread extends Thread {
		@Override
		public void run() {
			while(flag){
				AutoUploadManager.getInstance().uploadAttachment(getApplicationContext());
				try {
					Thread.sleep(AppConstants.AUTO_SCAN_ATTACHMENT_FREQUENCY);
//					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	class UploadCacheThread extends Thread {
		@Override
		public void run() {
			while(flag){
				AutoUploadManager.getInstance().uploadCache(getApplicationContext());
				try {
					Thread.sleep(AppConstants.AUTO_SCAN_ATTACHMENT_FREQUENCY);
//					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "AutoUploadService onDestroy()");
		flag = false;
		super.onDestroy();
	}
}
