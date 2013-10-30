package daiwei.mobile.activity;

import daiwei.mobile.animal.LoginModel;
import daiwei.mobile.util.AppConstants;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 存可改变的全局用的变量
 * @author changxiaofei
 * @time 2013-3-26 上午10:18:08
 */
public class BaseApplication extends Application {

	/** 用户名 */
	private String name;
	/** base64后的密码 */
	private String psw;
	/** 当前设置的ip名称 */
	private String customIpName;
	/** 当前设置的ip地址 */
	private String customIpAddress;
	/** 存LoginModel对象(只存工单和巡检个数)，小部件跳转到app会用到 */
	private LoginModel loginModel;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public String getName() {
		if(name==null){
			SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			name = sp.getString(AppConstants.SP_USER_NAME, "");
			sp = null;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		if(psw==null){
			SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			psw = sp.getString(AppConstants.SP_USER_PSW_ENCODE, "");
			sp = null;
		}
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getCustomIpName() {
		return customIpName;
	}

	public void setCustomIpName(String customIpName) {
		this.customIpName = customIpName;
	}

	public String getCustomIpAddress() {
		if(customIpAddress==null){
			SharedPreferences sp = getApplicationContext().getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
			customIpAddress = sp.getString(AppConstants.SP_IP_ADDRESS, "");
			sp = null;
		}
		return customIpAddress;
	}

	public void setCustomIpAddress(String customIpAddress) {
		this.customIpAddress = customIpAddress;
	}

	public LoginModel getLoginModel() {
		return loginModel;
	}

	public void setLoginModel(LoginModel loginModel) {
		this.loginModel = loginModel;
	}

}
