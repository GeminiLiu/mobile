package daiwei.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import eoms.mobile.R;
import daiwei.mobile.util.DateTimePickDialogUtil;


public class GdReplytsActivity extends BaseActivity {
	private ImageButton submit;
	private ImageButton takephoto;
	
	private EditText tstime;//投诉恢复时间
	private String initStartDateTime ="2013年1月1日 14:44";
	private String initEndDateTime ="2013年2月8日 17:44";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gdfoot5ts);
		Spinner spinner = (Spinner) findViewById(R.id.spinner_reason);
		spinnerList(spinner, new String[] { "信号", "基站", "手机个人原因" });
		ImageView iv_friends = (ImageView) findViewById(R.id.iv_friends);
		 submit = (ImageButton) findViewById(R.id.submit);
		 takephoto = (ImageButton) findViewById(R.id.takephoto);
		 tstime=(EditText)findViewById(R.id.et_editText1);
		 tstime.setText(initStartDateTime);
		 
		iv_friends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 关闭activity
				finish();
			}
		});
		tstime.setText(initStartDateTime);//设置默认时间
		tstime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil dateTimePickDialogUtil=new DateTimePickDialogUtil(GdReplytsActivity.this, initEndDateTime);
				dateTimePickDialogUtil.dateTimePicKDialog(tstime);
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
	public void changeColor() {
		submit.setBackgroundResource(R.drawable.xj_detail_three);
		takephoto.setBackgroundResource(R.drawable.xj_detail_camera);
		
	}

	@Override
	public void afterOnCreate() {

	}

}
