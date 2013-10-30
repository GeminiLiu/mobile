package daiwei.mobile.Tools;

import java.util.HashMap;
import daiwei.mobile.animal.HttpResultData;
import daiwei.mobile.common.HTTPConnection;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 判断wife 
 * 判断GPRS 
 * 判断所有网络
 * 判断是否能上网
 * @author 都 5/2
 * 
 */
public class NetWork {
	/**
	 * 判断wifi是否开启
	 * @param context
	 * @return
	 */
	public  static boolean checkWife(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conManager != null) {
			State wifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();
			if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断GPRS是否开启
	 * @param context
	 * @return
	 */
	public static boolean checkGPRS(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conManager != null) {
			NetworkInfo[] infos = conManager.getAllNetworkInfo();
			if (infos != null) {
				State mobile = conManager.getNetworkInfo(
						ConnectivityManager.TYPE_MOBILE).getState();
				if (mobile == State.CONNECTED || mobile == State.CONNECTING)
					return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否开网
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null&&networkInfo.isAvailable()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否能上网
	 * @param context
	 * @return
	 */
	public static boolean checkInternet(Context context) {

		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null &&  networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断网络类型 
	 * 1：能连网 
	 * 2：没有打开网络连接 
	 * 3：能打开网络连接，但不能连网
	 * @param context
	 * @return
	 */
	public static int checkWork(Context context) {
		
		if (!checkNetWork(context)) {
			return 2;
		}else if(checkInternet(context)){
			return 1;
		}else{
			return 3;
		}
	}

}
