package daiwei.mobile.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inspur.zsyw.platform.Platform;
import com.inspur.zsyw.platform.Platform.PlatformCallback;

import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.animal.CommonResult;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.animal.User;
import daiwei.mobile.service.BootService;
import daiwei.mobile.service.LaunchService;
import daiwei.mobile.service.LoginService;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.view.MDialog;
import eoms.mobile.R;

/**
 * 启动页面
 * @author changxiaofei
 * @time 2013-4-1 上午10:16:53
 */
public class SplashActivity extends Activity {
	
	private static final String TAG = "SplashActivity";
	private final static int WHAT_FIRST_INSTALL = 1;//首次安装
	private final static int WHAT_REPLACE_INSTALL = 2;//替换安装
	private final static int WHAT_AUTO_LOGIN = 3;//自动登录
	private final static int WHAT_CHECK_UPGRADE = 4;//检查更新
	private final static int WHAT_OFF_LINE_LOGIN = 7;//离线登录
	/** 下载进度更新 */
	private final static  int WHAT_DOWN_UPDATE = 5;
	/** 下载完成 */
	private final static  int WHAT_DOWN_OVER = 6;
	private ProgressBar pbLaunch;
	private AlertDialog dialog;
	private int newVersioncode;
	private int cacheVersioncode;
	private User user;
	/* 检测并在线升级 */
	private ProgressBar mProgressBar;
	private Dialog downloadDialog;
//	private ProgressDialog prodialog;//取消这句话,不在login中传入
	private Thread downLoadThread;
	private boolean interceptFlag = false;
	private String apkUrl;
	private int progress;
	/** 下载包安装路径 */
	private String savePath;
	private String saveFileName;
	public static boolean loginFlag=false;
	
	private Platform platform;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		/**
		 * 点击notification取消
		 */
//		NotificationManager nmag = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		nmag.cancel(123456);
		BootService.resetCount();//消息个数重置0
		// MyData.add(SplashActivity.this);//这个页面只打开一次，跳走后就finish，所以不用记录
		//以前判断网络的方法 IsNetwork.isAccessNetWork(getApplicationContext())
		findViews();//查找控件
	    if(NetWork.checkWork(getApplicationContext())==1){
	    	//		setListeners();//设置控件事件
			launchType();//启动检测版本
			//startGpsService();//开始获取GPS服务
		   // startTudeUpLoadService();//开启上传经纬度服务
		   // startSiteUpLoadService();//开启位置上传服务
	    }else{
	    	networkStateCheck();
	    }
	    
	}
	
	private void addShortcutToDesktop() {

	      Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	      // 不允许重建
	      shortcut.putExtra("duplicate", false);
	      // 设置名字
	      shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,this.getString(R.string.app_name));
	      // 设置图标
	      shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(this,R.drawable.icon));
	      // 设置意图和快捷方式关联程序
	      shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,new Intent(this, this.getClass()).setAction(Intent.ACTION_MAIN));
	      // 发送广播
	      sendBroadcast(shortcut);

	  }
	 /**程序安装后开始GPS服务*/
//     private void startGpsService(){
//    	  Intent gpsService=new Intent(SplashActivity.this,GetGPSService.class);
//    	  startService(gpsService);
//      }
     /**程序安装后开始上传经纬度服务*/
//     private void startTudeUpLoadService() {
//    	
//    	 Intent tudeService=new Intent(SplashActivity.this,TudeUpLoadService.class);
//   	     startService(tudeService);		
// 	}
     /**程序安装开启位置上传服务*/
//     private void startSiteUpLoadService(){
//    	 System.out.println("开启上传经纬度服务..................");
//    	 Intent siteService = new Intent(SplashActivity.this,SiteUploadService.class);
//    	 startService(siteService);		
//     }
	public void findViews() {
		pbLaunch = (ProgressBar) findViewById(R.id.pb_launch);
		user = AppInfo.getUserFromSharedPreferences(getBaseContext());
		newVersioncode = AppInfo.getAppVersionCodeFromManifest(getApplicationContext());
		cacheVersioncode = AppInfo.getAppVersionCodeFromSharedPreferences(getApplicationContext());
	}
	
	@Override
	protected void onRestart() {
		findViews();
		setListeners();
		launchType();
		super.onRestart();
	}

	public void setListeners() {
	}
	
	/**
	 * handler
	 */
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_FIRST_INSTALL:
				addShortcutToDesktop();//快捷方式
				Intent mIntent = new Intent();
//				mIntent.setClass(SplashActivity.this, GuideActivity.class);
				mIntent.setClass(SplashActivity.this, LoginActivity.class);
				startActivity(mIntent);
				finish();
				loginFlag=true;
				break;
			case WHAT_REPLACE_INSTALL:
				loginFlag=false;
				autoLogin();
				break;
			case WHAT_OFF_LINE_LOGIN:
				//Intent offLoginIntent = new Intent(SplashActivity.this, TestActivity.class);// 跳转主界面
				Intent offLoginIntent = new Intent(SplashActivity.this, LoginActivity.class);// 跳转主界面
				startActivity(offLoginIntent);
				finish();
				break;
			case WHAT_AUTO_LOGIN://自动登录
				Map<String, Object> loginMap = (Map<String, Object>) msg.obj;
				System.out.println("--loginMap->>"+loginMap.toString());
				boolean ok = (Boolean) loginMap.get("IsLegal");// 从集合中获取IsLegal字段，是否合法
				if (ok) {
					Log.i(TAG, "autoLogin success");
					LoginModel lm = (LoginModel) loginMap.get("Ccount");// 获取个数对象
					Intent intent = new Intent(SplashActivity.this, GdListActivity.class);// 跳转主界面
					Bundle mBundle = new Bundle();// 传LoginModel对象
					mBundle.putSerializable("LoginModel", lm);
					intent.putExtras(mBundle);
					startActivity(intent);
					finish();
				} else {
//					// 自动登录失败
//					Log.i(TAG, "autoLogin false");
//					if (dialog != null && dialog.isShowing()) {
//						dialog.dismiss();
//					}
//					dialog = MDialog.showDialog(SplashActivity.this, null, "自动登录失败", "手动登录", null, "退出", new MDialog.DialogOnClickListener() {
//						@Override
//						public void onDialogClick(DialogInterface dialog, int which) {
//							switch (which) {
//							case DialogInterface.BUTTON_POSITIVE:
//								gotoLoginActivity();
//								break;
//							case DialogInterface.BUTTON_NEGATIVE:// 退出
//								break;
//							default:
//								break;
//							}
//							dialog.dismiss();
//							SplashActivity.this.finish();
//						}
//					});
					Toast.makeText(SplashActivity.this, "登录失败", Toast.LENGTH_LONG).show();
					finish();
				}
				break;
			case WHAT_CHECK_UPGRADE:
				CommonResult commonResult = (CommonResult) msg.obj;
				if (commonResult.isOk()) {// 操作过程无异常
					if ((Boolean) commonResult.getObj1()) {// 需要升级
						pbLaunch.setVisibility(View.INVISIBLE);
						String versionOnServer = commonResult.getStr1();
						apkUrl = new StringBuilder("http://").append(((BaseApplication) getApplicationContext()).getCustomIpAddress())
								.append(AppConfigs.URL_NEW_APP.replace("newVersionName", versionOnServer)).toString();
						dialog = MDialog.showDialog(SplashActivity.this, null, new StringBuilder("检测到新版本v").append(versionOnServer).append("，请下载。")
								.toString(), "下载", null, "以后再说", new MDialog.DialogOnClickListener() {
							@Override
							public void onDialogClick(DialogInterface dialog, int which) {
								switch (which) {
								case DialogInterface.BUTTON_POSITIVE:
									showDownloadDialog();
									break;
								case DialogInterface.BUTTON_NEGATIVE:
									pbLaunch.setVisibility(View.VISIBLE);
									autoLogin();
									break;
								default:
									break;
								}
								dialog.dismiss();
							}
						});
					} else {
						Log.i(TAG, "checkUpgrade result is newest");
						autoLogin();
					}
				} else {
					Log.i(TAG, "checkUpgrade result appear exception");
					autoLogin();
				}
				break;
			case WHAT_DOWN_UPDATE:
				mProgressBar.setProgress(progress);
				break;
			case WHAT_DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 启动检测： <li>首次安装且首次启动。要释放附带文件、初始化数据库</li> <li>替换安装，版本升级等。要释放附带文件、升级数据库</li> <li>其它启动，什么都不做，延迟n秒进入主页。</li>
	 */
	private void launchType() {
		System.out.println("cacheVersioncode=="+cacheVersioncode+",,,newVersioncode="+newVersioncode);
		if (cacheVersioncode == 0) {// 初次安装后启动。需释放附带的文件、图片、附带的数据库等，初始化数据库。
			Log.i(TAG, "launchType first install");
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_FIRST_INSTALL;
					msg.obj = LaunchService.getInstance().firstInstall(getApplicationContext());
					mHandler.sendMessageDelayed(msg, 3000);
				}
			}).start();
		} else if (newVersioncode > cacheVersioncode) {// 升级安装。升级替换安装后首次启动。需重新释放附带的文件、图片、附带的数据库等，升级数据库。
			Log.i(TAG, "launchType replace install");
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_REPLACE_INSTALL;
					msg.obj = LaunchService.getInstance().upgradeInstall(getApplicationContext());
					mHandler.sendMessage(msg);
				}
			}).start();
		} else if (newVersioncode < cacheVersioncode) {// 替换安装，用低版本替换高版本。弹出对话框，提示初始化失败，请清除缓存后重新启动应用，点击确认退出。
			Log.w(TAG, "launchType replace install newVersioncode < cacheVersioncode");
			pbLaunch.setVisibility(View.INVISIBLE);
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			dialog = MDialog.showDialog(SplashActivity.this, null, "初始化失败，请清除缓存后重新启动应用。", "退出", null, null, new MDialog.DialogOnClickListener() {
				@Override
				public void onDialogClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					SplashActivity.this.finish();
				}
			});
		} else {// 其它情况：除首次安装或替换安装后第一次启动外的正常启动；相同版本的apk替换安装
			Log.i(TAG, "launchType common checkUpgrade()");
			checkUpgrade();
		}
	}
	
	/**
	 * 根据缓存的登录信息自动登录
	 */
	private void autoLogin() {
		if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPasswordEncode())) {// 无缓存的用户登录信息，直接进登录页。
			Log.i(TAG, "autoLogin user empty");
			gotoLoginActivity();
		} else {
			Log.i(TAG, "autoLogin do autoLogin");
			if (IsNetwork.isAccessNetWork(getApplicationContext())) {// 自动登录
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = mHandler.obtainMessage();
						msg.what = WHAT_AUTO_LOGIN;
//						CommonResult commonResult = LaunchService.getInstance().autoLogin(getApplicationContext());
						// 这里暂用旧的登录模块，里面很多异常没处理。???
						LoginService lg = new LoginServiceImp();
						Map<String, Object> loginMap = lg.login(getApplicationContext(), user.getUsername(), user.getPasswordEncode());// 返回Map<String,Object>集合
						msg.obj = loginMap;
						mHandler.sendMessage(msg);
					}
				}).start();
			} else {
				pbLaunch.setVisibility(View.INVISIBLE);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				dialog = MDialog.showDialog(SplashActivity.this, null, "无法连接网络，请设置网络连接。", "确定", null, null, new MDialog.DialogOnClickListener() {
					@Override
					public void onDialogClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						SplashActivity.this.finish();
					}
				});
			}
		}
	}
	
	/**
	 * 跳转到登录页
	 */
	private void gotoLoginActivity() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent mIntent = new Intent();
				mIntent.setClass(SplashActivity.this, LoginActivity.class);
				startActivity(mIntent);
				SplashActivity.this.finish();
			}
		}, 3000);
	}
	
	/**
	 * 联网检测是否需要升级apk
	 */
	private void checkUpgrade() {
		if (IsNetwork.isAccessNetWork(getApplicationContext())) {// 自动登录
			new Thread(new Runnable() {
				@Override
				public void run() {
					CommonResult commonResult = LaunchService.getInstance().checkUpgrade(getApplicationContext());
					Message msg = mHandler.obtainMessage();
					msg.obj = commonResult;
					msg.what = WHAT_CHECK_UPGRADE;
					mHandler.sendMessage(msg);
				}
			}).start();
		}
	}
	
	/**
	 * 下载进度框
	 */
	private void showDownloadDialog() {
//		savePath = AppInfo.getMainPath(getApplicationContext());
//		saveFileName = new StringBuilder(savePath).append("/").append(AppConstants.PATH_UPGRADE_APK_FILENAME).toString();
		savePath = AppInfo.getMainPath(getApplicationContext());
		saveFileName = new StringBuilder(savePath).append("/").append(AppConstants.PATH_UPGRADE_APK_FILENAME).toString();
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		builder.setTitle("正在下载新版本");
		final LayoutInflater inflater = LayoutInflater.from(SplashActivity.this);
		View v = inflater.inflate(R.layout.progressbar_download, null);
		mProgressBar = (ProgressBar) v.findViewById(R.id.pb_dowanload);
		builder.setView(v);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
				pbLaunch.setVisibility(View.VISIBLE);
				autoLogin();
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);
				int count = 0;
				byte buf[] = new byte[1024];
				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(WHAT_DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(WHAT_DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	/**
	 * 安装apk
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		if (null != platform){
			platform.unbindService();// 注销服务
		}

		super.onDestroy();
	}
	/**
	 * 设置网络
	 */
	private void networkStateCheck() {
		Builder b = new AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage("请开启GPS或WIFI网络连接"); 
		b.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				Intent intent=null;
				if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(Settings.ACTION_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
				startActivity(intent); 		 
			} 
		}).setNeutralButton("取消", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				System.out.println("!!!!!!!!!!!!!!!!");
				System.out.println("缓存的用户名和密码=========="+StringUtil.isEmpty(user.getUsername()));
				/**如果没有缓存的用户名和密码*/
				if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPasswordEncode())) {// 无缓存的用户登录信息，直接进登录页。
					Log.i(TAG, "autoLogin user empty");
					System.out.println("第一次登录........");
					dialog.dismiss();
					gotoLoginActivity();
				}
				else{
					Message msg = mHandler.obtainMessage();
					msg.what = WHAT_OFF_LINE_LOGIN;	
					LoginService lg = new LoginServiceImp();
					Map<String, Object> loginMap = lg.login(getApplicationContext(), user.getUsername(), user.getPasswordEncode());// 返回Map<String,Object>集合
					msg.obj = loginMap;
					mHandler.sendMessage(msg);
				}
				
			} 
		}).create(); 
		b.show(); 	 	
	}
}
