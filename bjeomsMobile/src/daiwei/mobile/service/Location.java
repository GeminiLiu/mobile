package daiwei.mobile.service;

import com.baidu.location.*;
import android.app.Application;

public class Location extends Application {

	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	public String str;
	
	@Override
	public void onCreate() {
		mLocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );		
		super.onCreate(); 
	}
		
	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return ;
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			str=sb.toString();
		}
		
		public void onReceivePoi(BDLocation poiLocation) {}
	}
}