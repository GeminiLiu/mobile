package daiwei.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import eoms.mobile.R;

public class GdReplytyActivity extends BaseActivity {
	private Spinner spinner;
	private EditText editText1;
	private ImageButton submit;
	private ImageButton takephoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gdfoot5);
		editText1 = (EditText)findViewById(R.id.et_editText1);
		ImageView iv_friends = (ImageView) findViewById(R.id.iv_friends);
		 submit = (ImageButton) findViewById(R.id.submit);
		takephoto = (ImageButton) findViewById(R.id.takephoto);
		
		spinner = (Spinner)findViewById(R.id.spinner);
		spinnerList(spinner,new String[]{"正常","不正常"});
		iv_friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//关闭activity
				finish();
			}
		});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Spinner spinner1=(Spinner)parent;
				String position1=(String) spinner1.getItemAtPosition(position);
//				Toast.makeText(getApplicationContext(), position1, 0).show();
				editText1.setText(position1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
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
	public void changeColor() {
		submit.setBackgroundResource(R.drawable.xj_detail_three);
		takephoto.setBackgroundResource(R.drawable.xj_detail_camera);
		
	}
	@Override
	public void getTvtf() {
//		this.tl =  new String[]{
//				"发点开始时间","市电恢复时间"};
//		this.vl = new String[]{
//				"2012-01-05 00-00-00"};//内容
//		this.tp = new int[]{
//				0,0};//标题类型  0：常规信息      1：主题    2:spinner模板
//		this.fr = new int[]{
//				0,0};//frame
		
		
	}

	@Override
	public void afterOnCreate() {
	
	}
}
