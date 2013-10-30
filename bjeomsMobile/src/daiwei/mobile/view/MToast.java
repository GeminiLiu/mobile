package daiwei.mobile.view;

import eoms.mobile.R;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast封装
 * @author changxiaofei
 * @time 2013-3-29 上午9:12:32
 */
public class MToast {
	private static final String TAG = "MyToast";
	private static final int duration = 2;
	private static Toast toast = null;
	
	/**
	 * 通用，吐司提示。显示时长见变量duration
	 * @param context
	 * @param textId
	 */
	public static void show(Context context, int textId) {
		String text = context.getResources().getString(textId);
		if (toast != null) {
			toast.setText(text);
			toast.setDuration(duration);
		} else {
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}
		toast.show();
	}
	
	/**
	 * 通用，吐司提示。显示时长见变量duration
	 * @param context
	 * @param text
	 */
	public static void show(Context context, String text) {
		if (toast != null) {
			toast.setText(text);
			toast.setDuration(duration);
		} else {
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}
		toast.show();
	}
	
	/**
	 * 通用，吐司提示。显示时长自定义
	 * @param context
	 * @param textId
	 * @param second 秒
	 */
	public static void showWithCustomTime(Context context, int textId, int second) {
		String text = context.getResources().getString(textId);
		if (toast != null) {
			toast.setText(text);
			toast.setDuration(second);
		} else {
			toast = Toast.makeText(context, text, second);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}
		toast.show();
	}
	
	/**
	 * 网络不可用，吐司提示。显示时长见变量duration
	 * @param context
	 */
	public static void showWhenNetworkUnavailable(Context context) {
		if (toast != null) {
			toast.setText(R.string.msg_error_network_unavailable);
			toast.setDuration(duration);
		} else {
			toast = Toast.makeText(context, R.string.msg_error_network_unavailable, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}
		toast.show();
	}
	
	/**
	 * 网络超时,吐司提示。显示时长见变量duration
	 * @param context
	 */
	public static void showWhenHttpTimeout(Context context) {
		if (toast != null) {
			toast.setText(R.string.msg_error_http_timeout);
			toast.setDuration(duration);
		} else {
			toast = Toast.makeText(context, R.string.msg_error_http_timeout, duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		}
		toast.show();
	}
	
	
}
