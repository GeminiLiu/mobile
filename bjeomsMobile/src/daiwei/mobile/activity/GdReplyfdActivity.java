package daiwei.mobile.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import eoms.mobile.R;
import daiwei.mobile.util.DateTimePickDialogUtil;

public class GdReplyfdActivity extends BaseActivity {
	private ImageButton submit;
	private ImageButton takephoto;
	private EditText startDateTime;
	private EditText startDateTime1;
	
	
	private String initStartDateTime ="2013年1月1日 14:44";
	private String initEndDateTime ="2013年2月8日 17:44";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gdfoot5fd);
		ImageView iv_friends = (ImageView) findViewById(R.id.iv_friends);
		 submit = (ImageButton) findViewById(R.id.submit);
			takephoto = (ImageButton) findViewById(R.id.takephoto);
			startDateTime=(EditText) findViewById(R.id.et_editText1);//发电开始时间
			startDateTime1=(EditText) findViewById(R.id.et_editText2);//市电恢复时间
			iv_friends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 关闭activity
				finish();
			}
		});
		//设置默认时间
		startDateTime.setText(initStartDateTime);
		startDateTime1.setText(initStartDateTime);
		
		startDateTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePickDialogUtil=new DateTimePickDialogUtil(GdReplyfdActivity.this, initEndDateTime);
				dateTimePickDialogUtil.dateTimePicKDialog(startDateTime);
			}
		});
		startDateTime1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePickDialogUtil=new DateTimePickDialogUtil(GdReplyfdActivity.this, initEndDateTime);
				dateTimePickDialogUtil.dateTimePicKDialog(startDateTime1);
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeColor();
				submit.setBackgroundResource(R.drawable.xj_detail_threeon);
			}
		});
		takephoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeColor();
				takephoto.setBackgroundResource(R.drawable.xj_detail_cameraon);
				
				Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intentFromCapture, 1);
				
			}
		});
	}
	

	@Override
	public void getTvtf() {
		// this.tl = new String[]{
		// "发点开始时间","市电恢复时间"};
		// this.vl = new String[]{
		// "2012-01-05 00-00-00"};//内容
		// this.tp = new int[]{
		// 0,0};//标题类型 0：常规信息 1：主题 2:spinner模板
		// this.fr = new int[]{
		// 0,0};//frame

	}

	@Override
	public void afterOnCreate() {

	}
	public void changeColor() {
		submit.setBackgroundResource(R.drawable.xj_detail_three);
		takephoto.setBackgroundResource(R.drawable.xj_detail_camera);
		
	}
	/**
	 * 隐藏弹出输入键盘
	 * @param edt
	 */
	private void hideIM(View edt) {
		try {
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null) {
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		} catch (Exception e) {
			Log.e("HideInputMethod", "failed:" + e.getMessage());
		}
	}
}
