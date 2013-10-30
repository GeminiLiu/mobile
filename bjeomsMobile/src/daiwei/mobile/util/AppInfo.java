package daiwei.mobile.util;

import daiwei.mobile.activity.BaseApplication;
import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.animal.User;
import daiwei.mobile.common.HTTPConnection;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

/**
 * 获取客户端信息。包括版本信息，用户手机信息等
 * @author changxiaofei
 * @time 2013-4-1 上午11:35:44
 */
public class AppInfo {
	private static final String TAG = "AppInfo";
	
	/**
	 * 从AndroidManifest读本地版本号versionCode。
	 * @param context
	 * @return
	 */
	public static int getAppVersionCodeFromManifest(Context context) {
		int versionCode = 0;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getAppVersionCodeFromManifest " + e.toString());
		}
		return versionCode;
	}
	
	/**
	 * 从AndroidManifest读本地版本号versionName。
	 * @param context
	 * @return
	 */
	public static String getAppVersionNameFromManifest(Context context) {
		String versionName = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			versionName = "";
			Log.e(TAG, "getAppVersionNameFromManifest " + e.toString());
		}
		return versionName;
	}
	
	/**
	 * 从SharedPreferences里取缓存的版本号name
	 * @param context
	 * @return
	 */
	public static int getAppVersionCodeFromSharedPreferences(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		return sp.getInt(AppConstants.SP_VERSION_CODE, 0);
	}
	
	/**
	 * 从SharedPreferences里取缓存的版本号code。
	 * @param context
	 * @return
	 */
	public static String getAppVersionNameFromSharedPreferences(Context context) {
		SharedPreferences sp = context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		return sp.getString(AppConstants.SP_VERSION_NAME, "");
	}
	
	/**
	 * SDCard可用，则返回/sdcard/daiwei/。否则返回data/data/daiwei.mobile/daiwei
	 * @param context
	 * @return String
	 */
	public static String getMainPath(Context context) {
		String cacheFilePath = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 如果手机装有SDCard，并且可以进行读写
			cacheFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getPath()).append(AppConstants.PATH_MAIN)
					.toString();// 获取SDCard目录
		}else{
			cacheFilePath = new StringBuilder(context.getFilesDir().getPath()).append(AppConstants.PATH_MAIN).toString();
		}	
		return cacheFilePath;
	}
	
	/**
	 * 从SharedPreferences里取缓存的用户名密码。
	 * @param context
	 * @return User
	 */
	public static User getUserFromSharedPreferences(Context context) {
		User user = new User();
		SharedPreferences sp = context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		user.setUsername(sp.getString(AppConstants.SP_USER_NAME, ""));
		user.setPasswordEncode(sp.getString(AppConstants.SP_USER_PSW_ENCODE, ""));
		return user;
	}
	
	/**
	 * 从Application里取缓存的用户名密码。
	 * @param context
	 * @return User
	 */
	public static User getUserFromApplication(Context context) {
		User user = new User();
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		user.setUsername(application.getName());
		user.setPasswordEncode(application.getPsw());
		return user;
	}
	
	/**
	 * 在Application里缓存用户名密码。
	 * @param context
	 * @return User
	 */
	public static void saveUserToApplication(Context context, User user) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		application.setName(user.getUsername());
		application.setPsw(user.getPasswordEncode());
	}
	
	public static LoginModel getLoginModelFromApplication(Context context) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		LoginModel loginModel = application.getLoginModel();
		return loginModel;
	}
	
	/**
	 * 在Application里缓存LoginModel。
	 * @param context
	 * @return LoginModel
	 */
	public static void saveLoginModelToApplication(Context context, LoginModel loginModel) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		application.setLoginModel(loginModel);
	}

	/**
	 * 是否已登录(BaseApplication存了用户名密码，且已获取cookies)。
	 * @param context
	 * @return
	 */
	public static boolean isLoggedIn(Context context) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		if(StringUtil.isNotEmpty(application.getName())&&StringUtil.isNotEmpty(application.getPsw())){
			if(StringUtil.isNotEmpty(HTTPConnection.cookies)){
				return true;
			}
		}
		return false;
	}
}
