package daiwei.mobile.activity;

import eoms.mobile.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class YhActivity extends Activity {
	private ImageView iv_friends;
	private ImageButton imageButton1;
	private ImageButton imageButton3;
	private ImageButton imageButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yh);
		iv_friends = (ImageView) findViewById(R.id.iv_friends);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

		findViewById(R.id.imageButton2);
		iv_friends.setImageResource(R.drawable.back);
		iv_friends.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeColor();
				imageButton1.setBackgroundResource(R.drawable.history_searchon);
				Intent intent = new Intent(getApplicationContext(),
						YhHisResultActivity.class);
				startActivity(intent);
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeColor();
				imageButton2
						.setBackgroundResource(R.drawable.xj_detail_threeon);
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {// 拍照

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						changeColor();
						imageButton3
								.setBackgroundResource(R.drawable.xj_detail_cameraon);
						Intent intentFromCapture = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(intentFromCapture, 1);
					}
				});
	}

	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.history_search);
		imageButton2.setBackgroundResource(R.drawable.xj_detail_three);
		imageButton3.setBackgroundResource(R.drawable.xj_detail_camera);

	}
}
