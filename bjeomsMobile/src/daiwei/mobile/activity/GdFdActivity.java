package daiwei.mobile.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import eoms.mobile.R;

public class GdFdActivity extends BaseActivity {
	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
	private ImageButton imageButton5;
//	private ImageView point;
	@Override
	public void getTvtf() {
		this.tl = new String[] {"流转内容",
				"任务信息", "站点名称","站点类型","服务区域","紧急程度","停电发生时间","停电事件描述",
				"基本信息", "建单人", "建单人单位", "联系方式", "建单时间", 
				"流程记录", "流程记录" };
		this.vl = new String[] {"黄维派发给徐发球;\n处理时限：2012-12-24 00:01:01;\n受理时限：2012-12-27 10:10:10",
				"","铁岭XXX基站","XXX类型","XXX区域","紧急","2012-01-01 00-00-00","不明原因停电",
				"","黄维","中国移动通信集团辽宁分公司","13888888888","2012-12-12 12:12:12",
				"","> 2012-12-12 01:09:47  朝晖派发给黄维。\n> 2012-12-12 03:27:52  黄维签收。\n> 2012-12-12 07:20:15  黄维派发给发球。 \n> 2012-12-12 09:35:23  徐发球签收。\n> 2012-12-12 13:57:41  徐发球完成。"};// 内容
		this.tp = new int[] { 5,  0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 5 };// 3
		this.fr = new int[] { 99, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 2 };
//		this.titleText = "XXXXX基站紧急发电";
//		this.gdType = "发点保障工单";
		this.titleText = "发电保障工单";
		this.gdType = "铁岭市.昌图.检察院基站发电保障";
		this.gdId = "ID-2137-9834-3928";
	}

	@Override
	public void afterOnCreate() {
		ImageView back = (ImageView) findViewById(R.id.iv_friends);
//		point = (ImageView) findViewById(R.id.iv_point);
		/*point.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),XjMessageActivity.class);
				startActivity(intent);
				
			}
		});*/
		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// Toast.makeText(getApplicationContext(), "back",0).show();
				return false;
			}
		});
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
		imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton1.setBackgroundResource(R.drawable.bottom_receiveon);
			/*	Intent intent = new Intent(getApplicationContext(),
						GdFoot1Activity.class);
				startActivity(intent);*/
				AlertDialog.Builder builder=new AlertDialog.Builder(GdFdActivity.this);
//				builder.setIcon(R.drawable.bottom_receiveon);
//				builder.setTitle("签收");
				builder.setMessage("确定要签收吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "已签收",0).show();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton2.setBackgroundResource(R.drawable.bottom_sendon);
				Intent intent = new Intent(getApplicationContext(),
						GdPfActivity.class);
				startActivity(intent);

			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton3.setBackgroundResource(R.drawable.bottom_backon);
				Intent intent = new Intent(getApplicationContext(),
						GdBackActivity.class);
				startActivity(intent);

			}
		});

		imageButton5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton5.setBackgroundResource(R.drawable.bottom_responseon);
				Intent intent = new Intent(getApplicationContext(),
						GdReplyfdActivity.class);
				startActivity(intent);

			}
		});

	}
	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.bottom_receive);
		imageButton2.setBackgroundResource(R.drawable.bottom_send);
		imageButton3.setBackgroundResource(R.drawable.bottom_back);
		imageButton5.setBackgroundResource(R.drawable.bottom_response);
	}
}