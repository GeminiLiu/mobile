package daiwei.mobile.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class IsNetwork {
	/**
	 * 判断网络
	 * @param context
	 * @return
	 */
	public static boolean isAccessNetWork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null
				&& connManager.getActiveNetworkInfo().isAvailable()) {
			return true;
		}
		return false;
	}
	public static void showToast(Context cnt, String text){
		Toast.makeText(cnt, text, Toast.LENGTH_SHORT).show();
	}
}
