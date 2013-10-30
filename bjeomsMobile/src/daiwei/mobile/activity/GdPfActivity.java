package daiwei.mobile.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import eoms.mobile.R;

public class GdPfActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gdpf);
		ImageView iv_friends = (ImageView) findViewById(R.id.iv_friends);

		iv_friends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 关闭activity
				finish();
			}
		});
		Spinner spinner = (Spinner) findViewById(R.id.spinner_reason);
		spinnerList(spinner, new String[] { "徐发球", "黄维", "徐哲" });
	
	}

	@Override
	public void getTvtf() {
		

	}

	@Override
	public void afterOnCreate() {
		
	}

}