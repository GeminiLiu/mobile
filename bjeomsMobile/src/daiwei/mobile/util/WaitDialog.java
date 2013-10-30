package daiwei.mobile.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class WaitDialog implements OnKeyListener{
	private ProgressDialog dialog;
	public static boolean flag=true;

	public WaitDialog(Context context, String dt) {
        flag=true;
		dialog = ProgressDialog.show(context, "", dt);
		dialog.setOnKeyListener(this);
	}

	public ProgressDialog getDialog() {
		return dialog;
	}
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.dismiss();
			flag=false;
			return false;
		}
		return false;
	}

}
