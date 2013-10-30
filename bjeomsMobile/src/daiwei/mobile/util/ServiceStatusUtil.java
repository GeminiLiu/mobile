package daiwei.mobile.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStatusUtil {
	/**
	 * 判断服务是否处于运行状态.
	 * @param context
	 * @param classname
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String classname) {
		ActivityManager am; // 相当于电脑上的进程管理器. 可以获取手机上运行的状态信息.
		am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(100);
		for (RunningServiceInfo info : infos) {
			String servicename = info.service.getClassName();
			if(classname.equals(servicename)){
				return true;
			}
		}

		return false;
	}
}
