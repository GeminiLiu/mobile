package daiwei.mobile.Tools;

import java.util.HashMap;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

/**
 * 获取高德经纬度
 * 
 * @author 都
 * @time 2013.5.23
 * 
 */
public class GetTude implements AMapLocationListener {
	/** 存放经纬的Map */
	private HashMap<String, String> tudeMap = new HashMap<String, String>();
	/** 构造方法里的activity */
	private Context context;
    public GetTude(Context context){
    	this.context = context;
    }
    public HashMap<String, String> GetTude() {
		return tudeMap;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			System.out.println("geoLat========"+geoLat);
			System.out.println("geoLng========"+geoLng);
			tudeMap.put("longitude", String.valueOf(geoLng));// 存放经度
			tudeMap.put("latitude", String.valueOf(geoLat));// 存放纬度
		}
	}

}
