package daiwei.mobile.service;

import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import daiwei.mobile.activity.BaseApplication;
import daiwei.mobile.animal.CommonResult;
import daiwei.mobile.common.HTTPConnection;
import daiwei.mobile.db.DBHelper;
import daiwei.mobile.util.AppConfigs;
import daiwei.mobile.util.AppConstants;
import daiwei.mobile.util.AppInfo;
import daiwei.mobile.util.StringUtil;

/**
 * 启动业务类
 * @author changxiaofei
 * @time 2013-4-1 上午10:15:57
 */
public class LaunchService {
	private static final String TAG = "LaunchService";
	private static LaunchService launchService;

	private LaunchService() {
	}

	/**
	 * @return
	 */
	public synchronized static LaunchService getInstance() {
		if (launchService == null) {
			launchService = new LaunchService();
		}
		return launchService;
	}
	
	/**
	 * 初次安装后启动。需释放附带的文件、图片、附带的数据库等，初始化数据库。
	 * 注：首次启动初始化失败，再次启动的情况，也被认为是首次安装且首次启动。
	 * @param context
	 * @return true初始化成功，会更新SharedPreferences里的版本号标记；false初始化失败
	 */
	public boolean firstInstall(Context context){
		boolean flag = false;
		try {
			//初始化db
			initDb(context);
			//更新SharedPreferences里的应用配置信息。
			SharedPreferences sp = context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			//存当前版本号
			editor.putInt(AppConstants.SP_VERSION_CODE, AppInfo.getAppVersionCodeFromManifest(context));
			editor.putString(AppConstants.SP_VERSION_NAME, AppInfo.getAppVersionNameFromManifest(context));
			//在SharedPreferences设置缺省ip地址
			String customIpName;
			String customIpAddress;
			if(AppConfigs.URL_HOST_NAME_DEFAULTS.length>0 && AppConfigs.URL_HOST_DEFAULTS.length>0){
				customIpName = AppConfigs.URL_HOST_NAME_DEFAULTS[0];
				customIpAddress = AppConfigs.URL_HOST_DEFAULTS[0];
			}else{
				customIpName = "";
				customIpAddress = "";
			}
			editor.putString(AppConstants.SP_IP_NAME, customIpName);
			editor.putString(AppConstants.SP_IP_ADDRESS, customIpAddress);
			//在SharedPreferences设置可供选ip(即历史记录)
			StringBuilder builderIpName = new StringBuilder();
			StringBuilder builderIpAddress = new StringBuilder();
			for (int i = 0; i < AppConfigs.URL_HOST_NAME_DEFAULTS.length; i++) {
				builderIpName.append(AppConfigs.URL_HOST_NAME_DEFAULTS[i]).append(";");
				builderIpAddress.append(AppConfigs.URL_HOST_DEFAULTS[i]).append(";");
			}
			if(builderIpName.length()>0 && builderIpAddress.length()>0){
				builderIpName = builderIpName.deleteCharAt(builderIpName.length()-1);
				builderIpAddress = builderIpAddress.deleteCharAt(builderIpAddress.length()-1);
			}
			editor.putString(AppConstants.SP_IP_NAME_HIS, builderIpName.toString());
			editor.putString(AppConstants.SP_IP_ADDRESS_HIS, builderIpAddress.toString());
			//在application存缺省ip地址，因为每次联网都用，为了读取性能放到application内存里
			BaseApplication application = (BaseApplication) context;
			application.setCustomIpName(customIpName);
			application.setCustomIpAddress(customIpAddress);
			editor.commit();
			flag = true;
		} catch (Exception e) {
			Log.e(TAG, "firstInstall " + e.toString());
			e.printStackTrace();
		}
		Log.i(TAG, "firstInstall " + flag);
		return flag;
	}
	
	/**
	 * 升级安装后首次启动。需重新释放附带的文件、图片、附带的数据库等，升级数据库。
	 * 注：升级替换安装后首次启动初始化失败，再次启动的情况，也被认为是升级替换安装后首次启动。
	 * @param context
	 * @return true初始化成功，会更新SharedPreferences里的版本号标记；false初始化失败
	 */
	public boolean upgradeInstall(Context context){
		boolean flag = false;
		try {
			//附带文件的释放，暂无。
			//初始化db
			initDb(context);
			//更新SharedPreferences里的应用配置信息。
			SharedPreferences sp = context.getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			//缓存新版本号	
			editor.putInt(AppConstants.SP_VERSION_CODE, AppInfo.getAppVersionCodeFromManifest(context));
			editor.putString(AppConstants.SP_VERSION_NAME, AppInfo.getAppVersionNameFromManifest(context));
			//如果需要，更新SharedPreferences里的缺省ip地址，根据需求不变、替换为最新。
			String customIpName;
			String customIpAddress;
			if(AppConfigs.URL_HOST_NAME_DEFAULTS.length>0 && AppConfigs.URL_HOST_DEFAULTS.length>0){
				String name = sp.getString(AppConstants.SP_IP_NAME_HIS, "");
				String address = sp.getString(AppConstants.SP_IP_ADDRESS_HIS, "");
				if(name!=""&&address!=""){
					customIpName=name;
					customIpAddress=address;
				}else{
				customIpName = AppConfigs.URL_HOST_NAME_DEFAULTS[1];
				customIpAddress = AppConfigs.URL_HOST_DEFAULTS[1];
				}
			}else{
				customIpName = "";
				customIpAddress = "";
			}
//			editor.putString(AppConstants.SP_IP_NAME, customIpName);
//			editor.putString(AppConstants.SP_IP_ADDRESS, customIpAddress);
			//如果需要，更新SharedPreferences里的可供选ip(即历史记录)，根据需求，不变、追加、替换为最新。
			StringBuilder builderIpName = new StringBuilder();
			StringBuilder builderIpAddress = new StringBuilder();
			for (int i = 0; i < AppConfigs.URL_HOST_NAME_DEFAULTS.length; i++) {
				builderIpName.append(AppConfigs.URL_HOST_NAME_DEFAULTS[i]).append(";");
				builderIpAddress.append(AppConfigs.URL_HOST_DEFAULTS[i]).append(";");
			}
			if(builderIpName.length()>0 && builderIpAddress.length()>0){
				builderIpName = builderIpName.deleteCharAt(builderIpName.length()-1);
				builderIpAddress = builderIpAddress.deleteCharAt(builderIpAddress.length()-1);
			}
//			editor.putString(AppConstants.SP_IP_NAME_HIS, builderIpName.toString());
//			editor.putString(AppConstants.SP_IP_ADDRESS_HIS, builderIpAddress.toString());
			//在application存缺省ip地址，因为每次联网都用，为了读取性能放到application内存里
			BaseApplication application = (BaseApplication) context;
			application.setCustomIpName(customIpName);
			application.setCustomIpAddress(customIpAddress);
			editor.commit();
			flag = true;
		} catch (Exception e) {
			Log.e(TAG, "firstInstall " + e.toString());
			e.printStackTrace();
		}
		Log.i(TAG, "replaceInstall " + flag);
		return flag;
	}
	
	/**
	 * 自动登录???暂时用的旧登录逻辑，以后再改。
	 * @param context
	 * @return
	 */
	public CommonResult autoLogin(Context context){
		CommonResult commonResult = new CommonResult();
		return commonResult;
	}
	
	/**
	 * 获取SDCard里工单附件缓存文件夹路径。
	 * @param context
	 * @return
	 */
	public CommonResult checkUpgrade(Context context) {
		String versionOnServer = null;
		CommonResult commonResult = new CommonResult();
		HTTPConnection hc = new HTTPConnection(context);
		Map<String, String> parmas = new HashMap<String, String>();
		parmas.put("serviceCode", "V");
		String strResult = null;	
		try {
			strResult = hc.Sent(parmas);
			if (StringUtil.isNotEmpty(strResult)) {
				try {
					Document doc = DocumentHelper.parseText(strResult.toString());
					Element root = doc.getRootElement();
					versionOnServer = root.getText();
					
				} catch (DocumentException e) {
					Log.e(TAG, "checkUpgrade parseText " + e.toString());
					e.printStackTrace();
				}
				long[] longVersionOnServer = stringVersionToLong(versionOnServer);
				long[] longVersionOnClient = stringVersionToLong(AppInfo.getAppVersionNameFromSharedPreferences(context));
				if (longVersionOnServer[0] > 0 && longVersionOnClient[0] > 0) {// 粗略检测version值合法性
					commonResult.setOk(true);// 操作过程无异常
					boolean neadUpgrade = false;
					if (longVersionOnServer[0] > longVersionOnClient[0]) {
						neadUpgrade = true;
					} else if (longVersionOnServer[0] == longVersionOnClient[0] && longVersionOnServer[1] > longVersionOnClient[1]) {
						neadUpgrade = true;
					} else if (longVersionOnServer[0] == longVersionOnClient[0] && longVersionOnServer[1] == longVersionOnClient[1]
							&& longVersionOnServer[2] > longVersionOnClient[2]) {
						neadUpgrade = true;
					}
					if (neadUpgrade) {
						commonResult.setObj1(true);// 结果是需要升级。
						commonResult.setStr1(versionOnServer);
					}else{
						commonResult.setObj1(false);// 结果是不需要升级。必须设置，否则会空指针
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "checkUpgrade hc.Sent error " + e.toString());
			e.printStackTrace();
		}
		return commonResult;
	}
	
	/**
	 * 根据版本号返回new long[3]。用于比较版本新旧。
	 * @param version
	 * @return
	 */
	public long[] stringVersionToLong(String version) {
		long[] longVersion = new long[3];
		try {			
			if (StringUtil.isNotEmpty(version)) {
				String[] temp = version.split("\\.");
				if (temp != null && temp.length == 3) {
					longVersion[0] = Long.parseLong(temp[0]);
					longVersion[1] = Long.parseLong(temp[1]);
					longVersion[2] = Long.parseLong(temp[2]);
				}
			}
		} catch (NumberFormatException e) {
			Log.e(TAG, "readLocalVersion " + e.toString());
			e.printStackTrace();
		}
		return longVersion;
	}

	/**
	 * 数据库初始化
	 * @param context
	 * @return
	 */
	private boolean initDb(Context context){
		boolean flag = false;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			DBHelper dbHelper = new DBHelper(context);
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT date('now') as date", null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				flag = true;
			}
		} catch (Exception e) {
			Log.e(TAG, "initDb error" + e.toString());
			e.printStackTrace();
		} finally {
			try {
				cursor.close();
				db.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}
	
}
