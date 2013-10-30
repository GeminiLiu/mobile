package daiwei.mobile.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import eoms.mobile.R;
import daiwei.mobile.Tools.IsNetwork;
import daiwei.mobile.animal.CommonResult;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.common.MyData;
import daiwei.mobile.service.AutoUploadManager;
import daiwei.mobile.service.AutoUploadService;
import daiwei.mobile.service.LaunchService;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.view.MDialog;
import daiwei.mobile.view.MToast;

/**
 * 系统设置 activity
 * @author Administrator
 *
 */
public class SettingActivity extends Activity implements View.OnClickListener {
	/** 删除 */
	public static final int WHAT_DEL = 1;
	/** 检测新版本 */
	public static final int WHAT_CHECK_UPGRADE = 2;
	/** 下载进度更新 */
	public static final int WHAT_DOWN_UPDATE = 3;
	/** 下载完成 */
	public static final int WHAT_DOWN_OVER = 4;
//	private Context context;
	public View set;
	private ImageButton ibAutoUpload;
	private LinearLayout llLogout;
	private LinearLayout llOfflinemap;//离线地图下载
	private LinearLayout llClearCache;
	private LinearLayout llCheckUpgrade;
	private LinearLayout llAbout;
	private boolean isOpen;
	private boolean isShock;//震动
	private boolean isVoiceOpen;//声音
	private boolean isNotificationNotice;//Notification提示
	private AlertDialog dialog;
	private LinearLayout llShock;
	private ImageView imageShock;
	private ProgressDialog progressDialog;
	private ProgressBar mProgressBar;
	private Dialog downloadDialog;
	private AlertDialog mDialog;
	private Thread downLoadThread;
	private boolean interceptFlag = false;
	private String apkUrl;
	private int progress;
	/** 下载包安装路径 */
	private String savePath;
	private String saveFileName;
	private ImageView image_voice_notify;//声音  开启按钮
	private ImageView image_notification_notice;//消息提示 开启按钮
	
	private ImageView imageBack;//返回按钮
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terminal);
		MyData.add(SettingActivity.this);
		findViewById();
		setListeners();
	}
	
	private void findViewById() {
//		set = LayoutInflater.from(context).inflate(R.layout.terminal, null);
		ibAutoUpload = (ImageButton) findViewById(R.id.ib_auto_upload);
		imageShock = (ImageView) findViewById(R.id.image_shock);
//		llLogout = (LinearLayout) findViewById(R.id.ll_logout);//注销
		llOfflinemap=(LinearLayout) findViewById(R.id.ll_offlinemap);//离线地图下载
		llShock = (LinearLayout) findViewById(R.id.ll_shock);
		image_voice_notify = (ImageView)findViewById(R.id.image_voice_notify);
		image_notification_notice=(ImageView)findViewById(R.id.image_notification_notice);//消息提示
		llClearCache = (LinearLayout) findViewById(R.id.ll_clear_cache);
		llCheckUpgrade = (LinearLayout) findViewById(R.id.ll_check_upgrade);
		llAbout = (LinearLayout) findViewById(R.id.ll_about);
		TextView tvVersion = (TextView) findViewById(R.id.tv_version);
		//返回按钮
		imageBack = (ImageView)this.findViewById(R.id.iv_friends);
		
		// 初始化上传服务开关按钮状态
		SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		isOpen = sp.getBoolean(AppConstants.SP_IS_AUTO_UPLOAD, true);// 默认传输服务是开启的
		isShock = sp.getBoolean(AppConstants.SP_IS_AUTO_NOTICE, true);// 默认震动开启
		isVoiceOpen = sp.getBoolean(AppConstants.SP_IS_VOICE_NOTICE, true);//默认声音开启
		isNotificationNotice=sp.getBoolean(AppConstants.SP_IS_NOTIFICATION_NOTICE, true);//默认消息提示
		//初始化开启按钮 设置图片
		if (isOpen) {
			ibAutoUpload.setImageResource(R.drawable.setting_checkbox_on);
		} else {
			ibAutoUpload.setImageResource(R.drawable.setting_check_off);
		}
		if (isShock) {//如果震动是开启的
			imageShock.setImageResource(R.drawable.setting_checkbox_on);
		} else {
			imageShock.setImageResource(R.drawable.setting_check_off);
		}
		if (isVoiceOpen) {
			image_voice_notify.setImageResource(R.drawable.setting_checkbox_on);
		} else {
			image_voice_notify.setImageResource(R.drawable.setting_check_off);
		}
		if(isNotificationNotice){//如果消息提示
			image_notification_notice.setImageResource(R.drawable.setting_checkbox_on);
		}else{
			image_notification_notice.setImageResource(R.drawable.setting_check_off);
		}
//		set.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (MyViewGroup.showright) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
	}
	
	private void setListeners() {
//		setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (MyViewGroup.showright) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
		ibAutoUpload.setOnClickListener(this);
		imageShock.setOnClickListener(this);//震动
		image_voice_notify.setOnClickListener(this);//声音
		image_notification_notice.setOnClickListener(this);//消息提示
//		llLogout.setOnClickListener(this);
		llOfflinemap.setOnClickListener(this);//离线地图
		llClearCache.setOnClickListener(this);
		llCheckUpgrade.setOnClickListener(this);
		llAbout.setOnClickListener(this);
		imageBack.setOnClickListener(this);
	
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_auto_upload:
			if (!isOpen) {
				isOpen = true;
				ibAutoUpload.setImageResource(R.drawable.setting_checkbox_on);
				startService(new Intent(this, AutoUploadService.class));// 启动工单附件自动上传服务
			} else {
				isOpen = false;
				ibAutoUpload.setImageResource(R.drawable.setting_check_off);
				stopService(new Intent(this, AutoUploadService.class));// 结束工单附件自动上传服务
			}
			SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			sp.edit().putBoolean(AppConstants.SP_IS_AUTO_UPLOAD, isOpen).commit();
			break;
//		case R.id.ll_logout:
//			MDialog.showDialog(this, null, "确认要注销？", "确定", null, " 取消", new MDialog.DialogOnClickListener() {
//				@Override
//				public void onDialogClick(DialogInterface dialog, int which) {
//					switch (which) {
//					case DialogInterface.BUTTON_POSITIVE:// 注销
//						SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
//						Editor editor = sp.edit();
//						editor.putString(AppConstants.SP_USER_NAME, "");
//						editor.putString(AppConstants.SP_USER_PSW_ENCODE, "");
//						editor.commit();
//						HTTPConnection.cookies = null;
//						startActivity(new Intent(getApplicationContext().getApplicationContext(), LoginActivity.class));
//						MyData.remove((Activity) SettingActivity.this);
//						SettingActivity.this.finish();
//						break;
//					case DialogInterface.BUTTON_NEGATIVE:
//						break;
//					default:
//						break;
//					}
//					dialog.cancel();
//				}
//			});
//			break;
//		case R.id.ll_offlinemap:
////			Toast.makeText(context, "离线地图下载列表", Toast.LENGTH_SHORT).show();
//			Intent intent=new Intent(context,OfflineCityListActivity.class);
//			context.startActivity(intent);
//			break;
		case R.id.image_shock:
			if (!isShock) {
				isShock = true;
				imageShock.setImageResource(R.drawable.setting_checkbox_on);
				// 启动震动服务
			} else {
				isShock = false;
				imageShock.setImageResource(R.drawable.setting_check_off);
				// 结束服务
			}
			SharedPreferences sp1 = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			System.out.println("震动的状态："+isShock);
			sp1.edit().putBoolean(AppConstants.SP_IS_AUTO_NOTICE, isShock).commit();
			break;
		case R.id.image_voice_notify://声音
			if (!isVoiceOpen) {
				isVoiceOpen = true;
				image_voice_notify.setImageResource(R.drawable.setting_checkbox_on);
			} else {
				isVoiceOpen = false;
				image_voice_notify.setImageResource(R.drawable.setting_check_off);
			}
			SharedPreferences sp2 = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			System.out.println("声音的状态："+isVoiceOpen);
			sp2.edit().putBoolean(AppConstants.SP_IS_VOICE_NOTICE, isVoiceOpen).commit();
			break;
		case R.id.image_notification_notice://消息提示
			if(!isNotificationNotice){
				isNotificationNotice=true;
				image_notification_notice.setImageResource(R.drawable.setting_checkbox_on);
			}else{
				isNotificationNotice=false;
				image_notification_notice.setImageResource(R.drawable.setting_check_off);
			}
			SharedPreferences sp3 = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			System.out.println("消息提示的状态："+isNotificationNotice);
			sp3.edit().putBoolean(AppConstants.SP_IS_NOTIFICATION_NOTICE, isNotificationNotice).commit();
			break;
		case R.id.ll_clear_cache:
			MDialog.showDialog(SettingActivity.this, null, "确认要清空缓存？", "确定", null, " 取消", new MDialog.DialogOnClickListener() {
				@Override
				public void onDialogClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						new Thread(new Runnable() {
							@Override
							public void run() {
								SharedPreferences sharedPrefLogin=getApplicationContext().getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
								SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("AUTOLOGOIN",Context.MODE_PRIVATE);
								sharedPrefLogin.edit().clear().commit();
								sharedPref.edit().clear().commit();
								boolean result = AutoUploadManager.getInstance().delOldAttachment(getApplicationContext());
								Message msg = handler.obtainMessage();
								msg.obj = result;
								msg.what = WHAT_DEL;
								handler.sendMessage(msg);
							}
						}).start();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					default:
						break;
					}
					dialog.cancel();
				}
			});
			break;
		case R.id.ll_check_upgrade:
			if (!IsNetwork.isAccessNetWork(getApplicationContext())) {
				MToast.showWhenNetworkUnavailable(getApplicationContext());
				return;
			}
			progressDialog = ProgressDialog.show(SettingActivity.this, null, "正在检测新版本...");
			new Thread(new Runnable() {
				@Override
				public void run() {
					CommonResult commonResult = LaunchService.getInstance().checkUpgrade(getApplicationContext());
					Message msg = handler.obtainMessage();
					msg.obj = commonResult;
					msg.what = WHAT_CHECK_UPGRADE;
					handler.sendMessage(msg);
				}
			}).start();
			break;
		case R.id.ll_about:
			startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			break;
		case R.id.iv_friends:
			SettingActivity.this.finish();
			break;
		default:
			break;
		}
	}
	/**
	 * handler
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DEL:
				boolean result = (Boolean) msg.obj;
				if (result) {
					MToast.show(getApplicationContext(), "清理完毕。");
				} else {
					MToast.show(getApplicationContext(), "清理失败。");
				}
				break;
			case WHAT_CHECK_UPGRADE:
				progressDialog.dismiss();
				CommonResult commonResult = (CommonResult) msg.obj;
				if (commonResult.isOk()) {// 操作过程无异常
					if ((Boolean) commonResult.getObj1()) {// 需要升级
						String versionOnServer = commonResult.getStr1();
						apkUrl = new StringBuilder("http://").append(((BaseApplication) getApplicationContext()).getCustomIpAddress())
								.append(AppConfigs.URL_NEW_APP.replace("newVersionName", versionOnServer)).toString();
						mDialog = MDialog.showDialog(SettingActivity.this, null, new StringBuilder("检测到新版本v").append(versionOnServer).append("，请下载。").toString(),
								"下载", null, "以后再说", new MDialog.DialogOnClickListener() {
									@Override
									public void onDialogClick(DialogInterface dialog, int which) {
										switch (which) {
										case DialogInterface.BUTTON_POSITIVE:
											showDownloadDialog();
											break;
										case DialogInterface.BUTTON_NEGATIVE:
											break;
										default:
											break;
										}
										mDialog.dismiss();
									}
								});
					} else {
						MToast.show(getApplicationContext(), "当前已是最新版本。");
					}
				} else {
					MToast.show(getApplicationContext(), "检测新版失败，请稍后重试。");
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
	 * 下载进度框
	 */
	private void showDownloadDialog() {
		savePath = AppInfo.getMainPath(getApplicationContext());
		saveFileName = new StringBuilder(savePath).append("/").append(AppConstants.PATH_UPGRADE_APK_FILENAME).toString();
		AlertDialog.Builder builder = new Builder(SettingActivity.this);
		builder.setTitle("正在下载新版本");
		final LayoutInflater inflater = LayoutInflater.from(getBaseContext());
		View v = inflater.inflate(R.layout.progressbar_download, null);
		mProgressBar = (ProgressBar) v.findViewById(R.id.pb_dowanload);
		builder.setView(v);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
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
					handler.sendEmptyMessage(WHAT_DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						handler.sendEmptyMessage(WHAT_DOWN_OVER);
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
}
