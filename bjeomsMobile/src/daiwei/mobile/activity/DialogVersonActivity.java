package daiwei.mobile.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eoms.mobile.R;
import daiwei.mobile.animal.CommonResult;
import daiwei.mobile.service.LaunchService;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.view.MDialog;
import daiwei.mobile.view.MToast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
/**
 * 获取消息推送更新版本的提示弹出的dialogAcitivity
 * @author 都5/3
 *
 */
public class DialogVersonActivity extends Activity{
	/** 删除 */
	public static final int WHAT_DEL = 1;
	/** 检测新版本 */
	public static final int WHAT_CHECK_UPGRADE = 2;
	/** 下载进度更新 */
	public static final int WHAT_DOWN_UPDATE = 3;
	/** 下载完成 */
	public static final int WHAT_DOWN_OVER = 4;
	public View set;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.verson_dialog);	
		progressDialog = ProgressDialog.show(DialogVersonActivity.this, null, "正在检测新版本...");
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
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_CHECK_UPGRADE:
				progressDialog.dismiss();
				CommonResult commonResult = (CommonResult) msg.obj;
				if (commonResult.isOk()) {// 操作过程无异常
					if ((Boolean) commonResult.getObj1()) {// 需要升级
						String versionOnServer = commonResult.getStr1();
						apkUrl = new StringBuilder("http://").append(((BaseApplication) getApplicationContext()).getCustomIpAddress())
								.append(AppConfigs.URL_NEW_APP.replace("newVersionName", versionOnServer)).toString();
						mDialog = MDialog.showDialog(DialogVersonActivity.this, null, new StringBuilder("检测到新版本v").append(versionOnServer).append("，请下载。").toString(),
								"下载", null, "以后再说", new MDialog.DialogOnClickListener() {
									@Override
									public void onDialogClick(DialogInterface dialog, int which) {
										switch (which) {
										case DialogInterface.BUTTON_POSITIVE:
											showDownloadDialog();
											break;
										case DialogInterface.BUTTON_NEGATIVE:
											finish();
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
			case WHAT_DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			case WHAT_DOWN_UPDATE:
				mProgressBar.setProgress(progress);
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
			AlertDialog.Builder builder = new Builder(DialogVersonActivity.this);
			builder.setTitle("正在下载新版本");
			final LayoutInflater inflater = LayoutInflater.from(DialogVersonActivity.this);
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
			finish();
		}
}
