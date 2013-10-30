package daiwei.mobile.activity;

import eoms.mobile.R;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 关于
 * @author changxiaofei
 * @time 2013-4-1 下午7:26:06
 */
public class AboutActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "AboutActivity";
	private ImageView ibBarBack;
	private TextView tvAppVersion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		findViews();
		setListeners();
		
	}

	public void findViews() {
		ibBarBack = (ImageView) findViewById(R.id.ib_bar_back);
		tvAppVersion = (TextView) findViewById(R.id.tv_app_version);
		String versionName = "";
		try {
			PackageManager pm = getPackageManager();
			PackageInfo pinfo = pm.getPackageInfo(this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			int versionCode = pinfo.versionCode;
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
		}
		tvAppVersion.setText("V" + versionName);
	}

	public void setListeners() {
		ibBarBack.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_bar_back:
			this.finish();
			break;
		default:
			break;
		}
	}

}