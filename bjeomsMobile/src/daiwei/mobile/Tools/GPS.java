package daiwei.mobile.Tools;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
/**
 * 判断是否开启GPS
 * 是否开启GPS
 * @author 都 5/14
 *
 */
public class GPS {
	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager 
		                         = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		//boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps) {
			return true;
		}
		return false;
	}
	/**
	 * 强制帮用户打开GPS
	 * @param context
	 */
	public static final void openGPS(final Context context) {
		Builder b = new AlertDialog.Builder(context).setTitle("没有开启GPS").setMessage("是否开启GPS"); 
		b.setPositiveButton("是", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				Intent intent=null;
				if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
				context.startActivity(intent); 		 
			} 
		}).setNeutralButton("否", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int whichButton) { 
				dialog.cancel(); 
			} 
		}).create(); 
		b.show(); 	 	
	}
}
