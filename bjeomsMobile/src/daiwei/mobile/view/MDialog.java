package daiwei.mobile.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 公共对话框
 * @author changxiaofei
 * @time 2013-3-28 上午9:17:41
 */
public class MDialog {
	private static final String TAG = "MDialog";
	
	/**
	 * 简单警告对话框。
	 * @param Activity Activity.this
	 * @param title 标题。null则无标题
	 * @param msg
	 * @param positiveButtonText 确认按钮文案,null则无此按钮。
	 * @param neutralButtonText 中立按钮文案,null则无此按钮。
	 * @param cancelButtonText 取消按钮文案,null则无此按钮。
	 * @param onClickListener 回调函数
	 */
	public static AlertDialog showDialog(Context context, String title, String msg, String positiveButtonText, String neutralButtonText,
			String negativeButtonText, final DialogOnClickListener onClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (null != title && title.trim().length() > 0) {
			builder.setTitle(title);
		}
		builder.setMessage(msg);
		if (null != positiveButtonText && positiveButtonText.trim().length() > 0) {
			builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (null != onClickListener) {
						onClickListener.onDialogClick(dialog, which);
					}
				}
			});
		}
		if (null != neutralButtonText && neutralButtonText.trim().length() > 0) {
			builder.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (null != onClickListener) {
						onClickListener.onDialogClick(dialog, which);
					}
				}
			});
		}
		if (null != negativeButtonText && negativeButtonText.trim().length() > 0) {
			builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (null != onClickListener) {
						onClickListener.onDialogClick(dialog, which);
					}
				}
			});
		}
		AlertDialog alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		// 屏蔽back键
		alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					return true;
				}
				return false;
			}
		});
		try {
			alertDialog.show();
		} catch (Exception e) {
			Log.e(TAG, "showDialog " + e.toString());
		}
		return alertDialog;
	}
	
	/**
	 * 接口，用于声明按钮的点击处理回调函数。
	 * @author changxiaofei
	 * @time 2013-3-28 上午9:18:27
	 */
	public interface DialogOnClickListener {
		/**
		 * 按钮的点击处理回调函数。
		 * @param dialog
		 * @param which
		 */
		void onDialogClick(DialogInterface dialog, int which);
	}
}
