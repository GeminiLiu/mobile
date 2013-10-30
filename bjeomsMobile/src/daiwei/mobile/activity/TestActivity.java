package daiwei.mobile.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import eoms.mobile.R;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.common.MyData;
import daiwei.mobile.service.AutoUploadService;
import daiwei.mobile.service.BootService;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.view.Desktop;
import daiwei.mobile.view.Friends;
import daiwei.mobile.view.MyViewGroup;
/**
 * 登录成功后进入的主页面 包括两个view friends&desktop
 * @author 都 3/25
 *
 */
public class TestActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "TestActivity";
	/** intent参数key，跳转来源 */
	public static final String KEY_FROM = "key_from";
	/** intent参数key，工单巡检个数 */
	public static final String KEY_LOGINMODEL = "LoginModel";
	/** 常量，跳转来自启动 */
	public static final int CONSTANT_FROM_LAUNCHER = 1;
	/** 常量，跳转来自小部件 */
	public static final int CONSTANT_FROM_WIDGET = 2;
	private int intFrom = 1;
	public static int screenW;
	private MyViewGroup group;
	private Desktop desktop;
	public Friends friends;
	public static String versionName;
	SharedPreferences isShowIconRef;
	private int logic_jump=1;//登录页面进入首页的跳转逻辑 是否加载数据 
	private  String gdNum;
	private String xjNum;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intFrom = getIntent().getIntExtra(KEY_FROM, 1);
//		intFrom = 2;
		MyViewGroup.showright = false;
		group = new MyViewGroup(this,TestActivity.this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		
		//获取工单、巡检个数
		LoginModel lm=(LoginModel) getIntent().getSerializableExtra("LoginModel");
		if(lm!=null){
		gdNum = lm.getGdNum();
		xjNum = lm.getXjNum();
		}
		desktop = new Desktop(TestActivity.this, group,gdNum,xjNum,intFrom);
		friends = new Friends(TestActivity.this, group,logic_jump);
		group.addView(desktop.mDesktop, params);
		group.addView(friends.mFriends, params);
		setContentView(group);
		MyData.add(TestActivity.this); // 启动时存入HashMap中
//		System.out.println("能否能上网==="+NetWork.checkInternet(getApplicationContext())+
//				"判断GPRS==="+NetWork.checkGPRS(getApplicationContext())+",,,判断是否开网"+NetWork.checkNetWork(getApplicationContext())
//				+"判断wifi==="+NetWork.checkWife(getApplicationContext())+"判断网络类型========"+	NetWork.checkWork(getApplicationContext()));
		if(intFrom == 2){//如果是桌面小部件进来的
			desktop.showSetting();//显示设置界面
		}
		Intent intent=getIntent();
		if(intent.hasExtra("type")){
		String type = intent.getStringExtra("type");
		
		if(type.equals("gd")){
		
			group.showRight();
		}
		}
		//短信拦截
		startService(new Intent(this,
				BootService.class));
		
		//启动附件自动上传
		new Handler().postDelayed(runnable, 5*60000);//避免系统繁忙，进入5分钟后启动服务
	}
	
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			boolean isOpen = sp.getBoolean(AppConstants.SP_IS_AUTO_UPLOAD, true);// 默认是开启的
			if (isOpen) {//用户设置为开启
				getApplicationContext().startService(new Intent(getApplicationContext(), AutoUploadService.class));//启动工单附件自动上传服务
			}
		}
	};
	

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		if (intent.hasExtra("type")) {
			String type = intent.getStringExtra("type");

			if (type.equals("gd")) {

				 group.showRight();
			}
		}
		super.onNewIntent(intent);
	}


	/**
	 * 快捷方式
	 */
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if (group.getChildAt(1).getScrollX() == 0) {
				group.showLeft();
				return true;
			}

			new AlertDialog.Builder(this)
					.setView(View.inflate(this, R.layout.exit_dialog, null))
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									NotificationManager nm = (NotificationManager) getApplicationContext()
											.getSystemService(
													Context.NOTIFICATION_SERVICE);
									nm.cancelAll();
									
                                 MyData.finishProgram();
								}
							}).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

   /**程序注销会走这步   临时注销掉 还是有错 但是APK不会黑*/
	@Override
	protected void onDestroy() {
//		int num=Friends.list.size();
//		int xjNum=XjMain.xjlist.size();
//		if(num==0&&Friends.reciver!=null){
//			unregisterReceiver(Friends.reciver);
//			Friends.reciver=null;
//		}
//		if(xjNum==0&&XjMain.re!=null){
//			unregisterReceiver(XjMain.re);
//			XjMain.re=null;
//		}
//		for(int i=0;i<num;i++){
//			unregisterReceiver(Friends.list.get(i));
//			Friends.list.remove(i);
//		}
//		for(int j=0;j<xjNum;j++){
//			unregisterReceiver(XjMain.xjlist.get(j));
//			XjMain.xjlist.remove(j);
//		}
		super.onDestroy();	
	}
}