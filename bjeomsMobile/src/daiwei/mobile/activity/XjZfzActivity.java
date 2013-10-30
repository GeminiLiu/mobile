package daiwei.mobile.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import eoms.mobile.R;
import daiwei.mobile.animal.XJListModel;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.XJDataService;
import daiwei.mobile.service.XJDataServiceImp;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.XJBaseField;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.Content;


/**
 * 巡检工单每个item
 * @author 都   3/26
 *
 */
public class XjZfzActivity extends BaseActivity {

//	private ImageView point;
	private ImageButton imageButton1;
	private ImageButton imageButton2;
	private ImageButton imageButton3;
//	private ImageButton imageButton4;
	private ImageButton imageButton5;
	private LayoutInflater inflater;	
	ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();	
	private AlertDialog.Builder builder;
	int mSingleChoiceID = -1;
	private String taskId=null;//巡检工单传递过来的数据
	private Intent xjtent=null;//利用intent获取数据	
	private List<XJBaseField> xjFields;
	final String[] mItems = { "设备标签", "工程工艺", "主设备", "运行环境", "供电系统", "天馈线系统" };
	private Handler XJHandler=null;
	private XJThread  xjThread=null;
	public void getTvtf() {
		xjtent=this.getIntent();
		taskId=xjtent.getStringExtra("taskId");
		xjThread=new XJThread(taskId);
		xjThread.start();
		
		
		this.tl =  new String[]{"设备标签","设备标签","固定资产条形码",
				"工程工艺","线缆布放",
				"主设备（含主机和干放）","对设备进行清洁、除尘","设备运行是否正常","检查主机及附属设施固定情况","尾纤和电缆","接地是否正常","直放站监控状态","检查并核对直放站基础信息（信源小区信息）",
				"运行环境","设备工作环境是否良好",
				"供电系统","外部供电","UPS及UPS风扇，（无安装UPS的站点取消UPS相关内容）","UPS电池管理参数","电池外观；电池连接电源、连接条","测量UPS电池电压","接地",
				"天馈线系统","检查锦缎和远端的天馈线系统","天线","有否接地","天线前方是否有障碍物阻挡","街头沐风、无渗漏、无破损、过度弯曲和开裂等现象"};
		this.vl = new String[]{"(全正常)","标签正确齐全，格式统一，位置合理","资产条形码正确齐全",
				"(有异常)","交直流、传输线隔离布放，整齐不凌乱，固定",
				"(有异常)","干净、整洁","无告警","各种支撑件牢固，不生锈","连接良好","地阻值小于5欧姆","正常连机","模拟部分监控告警，联机检查上报操作，确保监控功能有效",
				"(有异常)","室分系统的温度、湿度是否正常；是否有安全隐患。",
				"(全正常)","正常可靠","UPS工作正常，UPS风扇运行正常，无故障告警，UPS旁路功能正常","电池管理参数满足要求","无漏液，无变化现象；连接电缆、连接条牢固，无腐蚀","正常：220V，误差5%","接地线否固定，无生锈，接地电阻小于5欧姆",
				"(全正常)","桅杆、指甲、爬梯、拉线和紧固件牢固，无松动，松弛咸阳；无腐蚀现象","无松动、变动现象，方向角和倾角符合要求","接地线固定无生锈","如发现应及时通知主管部门","如发现应及时处理并通知主管部门"};
		this.tp = new int[]{0,4,4,
				0,4,
				0,4,4,4,4,4,4,4,
				0,4,
				0,4,4,4,4,4,4,
				0,4,4,4,4,4
				};
		this.fr = new int[]{0,0,0,
				0,1,
				0,2,2,2,2,2,2,2,
				0,3,
				0,4,4,4,4,4,4,
				0,5,5,5,5,5};
		
		
		this.titleText="基站巡检";
		this.gdType="铁岭.昌图.检察院基站";
		this.gdId="";
		this.detailId=R.layout.xj_detail_bg;
	}

	@Override
	public void afterOnCreate() {
//		point = (ImageView) findViewById(R.id.iv_point);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
//		imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
		imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
	    inflater = LayoutInflater.from(this);
		imageButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeColor();
				imageButton1.setBackgroundResource(R.drawable.xj_detail_oneon);
				AlertDialog.Builder builder=new AlertDialog.Builder(XjZfzActivity.this);
//				builder.setIcon(R.drawable.bottom_receiveon);
//				builder.setTitle("签收");
				builder.setMessage("当前时间: 12:42\n当前经纬度: 89.909 ; 109.890\n当前基站:铁岭.昌图.检察院基站\n该基站经纬度: 89.909 ; 109.890\n确定到达吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "已到达",0).show();
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
				// TODO Auto-generated method stub
				changeColor();
				imageButton2.setBackgroundResource(R.drawable.xj_detail_twoon);
				AlertDialog.Builder builder=new AlertDialog.Builder(XjZfzActivity.this);
//				builder.setIcon(R.drawable.bottom_receiveon);
//				builder.setTitle("签收");
				builder.setMessage("当前时间: 13:59\n当前经纬度: 89.909 ; 109.890\n当前地点:铁岭.昌图.检察院基站\n确定离开？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "已离开",0).show();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
			}
		});
/*		imageButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeColor();
				imageButton3.setBackgroundResource(R.drawable.xj_detail_threeon);
				AlertDialog.Builder builder=new AlertDialog.Builder(XjZfzActivity.this);
//				builder.setIcon(R.drawable.bottom_receiveon);
//				builder.setTitle("签收");
				builder.setMessage("巡检记事");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
				
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
			}
		});*/
		imageButton3.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeColor();
				
				imageButton3.setBackgroundResource(R.drawable.xj_detail_threeon);
				final View dialogview=inflater.inflate(R.layout.finishdialog, null);
				
				AlertDialog.Builder builder=new AlertDialog.Builder(XjZfzActivity.this);
				builder.setView(dialogview);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EditText et_finish=(EditText) dialogview.findViewById(R.id.et_finish);
						String content=et_finish.getText().toString();
						Toast.makeText(getApplicationContext(), content,0).show();
						dialog.dismiss();
				
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
			}
		});
	/*	imageButton4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeColor();
				imageButton4.setBackgroundResource(R.drawable.xj_detail_recordon);
			}
		});*/
		imageButton5.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		changeColor();
		imageButton5.setBackgroundResource(R.drawable.xj_detail_cameraon);
		CreatDialog(0);
		
	
//		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		
//		startActivityForResult(intentFromCapture, 1);
	}
});
		/*point.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),XjMessageActivity.class);
				startActivity(intent);
				
			}
		});*/
/*		point.setOnClickListener(new OnClickListener() {
			
			private PopupWindow popupWindow;

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				View popView = View.inflate(XjZfzActivity.this, R.layout.popup_xj,
						null);
				popupWindow = new PopupWindow(popView,
						150, LayoutParams.WRAP_CONTENT);
				popupWindow.setBackgroundDrawable(new ColorDrawable(
						Color.TRANSPARENT));
				popupWindow.setOutsideTouchable(true);
				popupWindow
						.setAnimationStyle(android.R.style.Animation_Dialog);
				popupWindow.update();
				popupWindow.setTouchable(true);
				popupWindow.setFocusable(true);
				if (!popupWindow.isShowing()) {
					popupWindow.showAsDropDown(popView, 400,115);
					// popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
			}
		});*/
	}
	public void changeColor() {
		imageButton1.setBackgroundResource(R.drawable.xj_detail_one);
		imageButton2.setBackgroundResource(R.drawable.xj_detail_two);
		imageButton3.setBackgroundResource(R.drawable.xj_detail_three);
//		imageButton4.setBackgroundResource(R.drawable.xj_detail_record);
		imageButton5.setBackgroundResource(R.drawable.xj_detail_camera);
	}
	
	private void showDialog(String str) {
		new AlertDialog.Builder(XjZfzActivity.this).setMessage(str).show();
	}

	public void CreatDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				XjZfzActivity.this);
		switch (id) {
			case 0:// 单选
				mSingleChoiceID = -1;
				builder.setIcon(R.drawable.icon);
				builder.setTitle("拍照类别");
				builder.setSingleChoiceItems(mItems, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								mSingleChoiceID = whichButton;
//								showDialog("你选择的id为" + whichButton + " , "
//										+ mItems[whichButton]);
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								if (mSingleChoiceID > 0) {
//									showDialog("你选择的是" + mSingleChoiceID);
									
									Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
									startActivityForResult(intentFromCapture, 1);
								}
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
	
							}
						});
	
				break;

		case 1:
			MultiChoiceID.clear();
			builder.setIcon(R.drawable.icon);
			builder.setTitle("资源信息校对");
			builder.setMultiChoiceItems(mItems, new boolean[] { false, false,
					false, false, false, false },
					new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton, boolean isChecked) {
							if (isChecked) {
								MultiChoiceID.add(whichButton);
								showDialog("你选择的id为" + whichButton + " , "
										+ mItems[whichButton]);
							} else {
								MultiChoiceID.remove(whichButton);
							}

						}
					});
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							String str = "";
							int size = MultiChoiceID.size();
							for (int i = 0; i < size; i++) {
								str += mItems[MultiChoiceID.get(i)] + ", ";
							}
							showDialog("你选择的是" + str);
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			break;
		}
		builder.create().show();
	}
	/**
	 * 巡检内容的异步下载
	 * @author admin
	 *
	 */
	class XJThread extends Thread{
		private XMLUtil xjUtil=null;
		private XJDataService  xds=null;
		private XJListModel xjList=null;
		private XMLUtil ele;
		private List<String> mItems;
		private String taskId;
		private String bojectId;
		private String contentId;
		private HashMap<String,Content> contents;
		private XJThread(String taskId) {
			XJHandler=new Handler();
			this.taskId=taskId;
		}
		@Override
		public void run() {
			contents = new HashMap<String,Content>();
			mItems=new ArrayList<String>();
			xds=new XJDataServiceImp();
			ele = xds.getXJData(getApplicationContext(), taskId,"PTL002");
			xjFields=ele.getBaseFileds();
			for(int j=0;j<xjFields.get(0).getXjFieldGroup().size();j++){
			}
			for(int i=0;i<xjFields.size();i++){
				bojectId=xjFields.get(i).getObjectId();
				mItems.add(bojectId);	
				contents=TempletModel.TempletMapXJ.get("basestation").get(bojectId).getContents();
			}
			XJHandler.post(doneRunble);
		}

	}
	// 构建Runnable对象，在runnable中更新界面  
	Runnable doneRunble=new  Runnable(){  
	        @Override  
	       public void run() {  
	           //更新界面  	        								
	       }  
	          
	    };
}