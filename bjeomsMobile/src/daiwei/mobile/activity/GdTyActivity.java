package daiwei.mobile.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import eoms.mobile.R;

public class GdTyActivity extends BaseActivity {

	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
	private ImageButton imageButton5;
//	private ImageView point;
	@Override
	public void getTvtf() {
		//标题title
		this.tl = new String[] {"流转内容",
				"任务信息", "任务类型","网络分类","任务描述",
				"基本信息", "建单人", "建单人单位", "联系方式", "建单时间", 
				"流程记录", "流程记录" };
		//内容value
		this.vl = new String[] {"黄维派发给徐发球;\n处理时限：2012-12-24 00:01:01;\n受理时限：2012-12-27 10:10:10",
				"","业务测试","无线，其他，其他","1、请各地市分公司按照之前的要求进行TD/2G扫频测试工作。注意事项：1)工单截止日期前完成扫频报告并随工单上报;2)扫频测试涉及城区所有网络并且与ATU通车一致;",
				"","黄维","中国移动通信集团辽宁分公司","13888888888","2012-12-12 12:12:12",
				"","> 2012-12-12 01:09:47  朝晖派发给黄维。\n> 2012-12-12 03:27:52  黄维签收。\n> 2012-12-12 07:20:15  黄维派发给发球。 \n> 2012-12-12 09:35:23  徐发球签收。\n> 2012-12-12 13:57:41  徐发球完成。",};// 内容
		//标题类型 0：常规信息 1：主题 2:spinner模板 3:editText模板 4：开关 5:无主题文本		
		this.tp = new int[] { 5,  0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 5 };// 3
		// frame 99直接挂在主模版上
		this.fr = new int[] { 99, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 2 };
	
		this.titleText = "代维任务工单";
		this.gdType = "请对相关基站内部空调设备进行厂家统计";
		this.gdId = "ID-001-1000001-0001 ( 一级处理中 )";

	}

	@Override
	public void afterOnCreate() {
	
		init();
		setlistener();
		

	}
	private void setlistener() {
		imageButton1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				changeColor();
				imageButton1.setBackgroundResource(R.drawable.bottom_receiveon);
				
//				Intent intent = new Intent(getApplicationContext(),
//						GdFoot1Activity.class);
//				startActivity(intent);
//				createDialog();
//				showDialog("您确定签收吗？");
				AlertDialog.Builder builder=new AlertDialog.Builder(GdTyActivity.this);
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
			/*	Intent intent=getIntent();
				String position=intent.getStringExtra("position");
				if(position.equals("0")){//通用
*/					Intent intent = new Intent(getApplicationContext(),
							GdReplytyActivity.class);
					startActivity(intent);
//				}
				
				
			
				
			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
//		point = (ImageView) findViewById(R.id.iv_point);//资源信息
		/*point.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),XjMessageActivity.class);
				startActivity(intent);
				
			}
		});*/
	}

	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.bottom_receive);
		imageButton2.setBackgroundResource(R.drawable.bottom_send);
		imageButton3.setBackgroundResource(R.drawable.bottom_back);
		imageButton5.setBackgroundResource(R.drawable.bottom_response);
	}
	

	/*private  void createDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(GdTyActivity.this);	
		  builder.setIcon(R.drawable.ic_launcher);
		  builder.setTitle("签收");
		  builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                  //这里添加点击确定后的逻辑
                  showDialog("你选择了确定");
              }
          });
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                  //这里添加点击确定后的逻辑
                  showDialog("你选择了取消");
              }
          });
	}*/
	/*  private void showDialog(String str) {
			 new AlertDialog.Builder(GdTyActivity.this)
		         .setMessage(str)
		         .show();
		    }*/
	  
	  
}
