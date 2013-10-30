package daiwei.mobile.service;

import android.content.Context;
import android.os.Environment;
import daiwei.mobile.util.AppConstants;

/**
 * widget业务类
 * @author changxiaofei
 * @time 2013-3-27 下午4:02:39
 */
public class WidgetManager {
	private static final String TAG = "WidgetManager";
	private static WidgetManager widgetManager;
	
	/**
	 * @return
	 */
	public synchronized static WidgetManager getInstance() {
		if (widgetManager == null) {
			widgetManager = new WidgetManager();
		}
		return widgetManager;
	}
	
	/**
	 * 获取SDCard里工单附件缓存文件夹路径。
	 * @param context
	 * @return
	 */
	public String getSDCardAttachmentPath(Context context) {
		String cacheFilePath = "";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果手机装有SDCard，并且可以进行读写
			cacheFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_ATTACHMENT).toString();// 获取SDCard目录
		}
		return cacheFilePath;
	}


}
