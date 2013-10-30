package daiwei.mobile.activity;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;

import eoms.mobile.R;
import daiwei.mobile.util.AMapUtil;
/**
 * 基站巡检的地图
 * 计算当前位置和基站的距离并画线
 * @author qch
 * @2013/5/28
 */
public class MapViewActivity extends Activity implements 
LocationSource,AMapLocationListener,OnMarkerClickListener{
	private MapView aMapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Double jzlatitude1;
	private Double jzlongtitude1;
	private Marker defaultMarker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
		aMapView = (MapView) findViewById(R.id.map);
		aMapView.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		aMapView.onResume();
		init();
	}

	private void init() {
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(MapViewActivity.this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
				//定位
				mAMapLocationManager = LocationManagerProxy
						.getInstance(MapViewActivity.this);
				aMap.setLocationSource(this);
				aMap.setMyLocationEnabled(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		aMapView.onPause();
		deactivate();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		aMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		aMapView.onLowMemory();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		aMapView.onSaveInstanceState(outState);
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		// Location API
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	/**
	 *取消定位ֹ
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
			double longitude = aLocation.getLongitude();
			double latitude = aLocation.getLatitude();
//			System.out.println("当前经度"+longitude+"::纬度"+latitude);
			LatLng position = new LatLng(latitude,longitude);// 当前位置，定位的经纬度
			String jzlatitude=getIntent().getStringExtra("latitude");
			String jzlongtitude=getIntent().getStringExtra("longitude");
//			System.out.println("基站经度:"+jzlongtitude+"基站纬度："+jzlatitude);
			if(jzlatitude!=null&&jzlongtitude!=null){
				jzlatitude1 = Double.parseDouble(jzlatitude);
				jzlongtitude1 = Double.parseDouble(jzlongtitude);
//				System.out.println("转化后的经度："+jzlongtitude1+"::纬度:"+jzlatitude1);
			}
		
			LatLng jzposition = new LatLng(jzlatitude1,jzlongtitude1);//基站的经纬度
			aMap.addPolyline((new PolylineOptions())
					.add(position,jzposition).color(Color.RED)
					.width(5));
			// 对地图添加一个marker
			defaultMarker = aMap.addMarker(new MarkerOptions()
			.position(position).title("0").snippet("00")
			.icon(BitmapDescriptorFactory.defaultMarker()));
			aMap.addMarker(new MarkerOptions()
			.position(jzposition).title("1").snippet("11")
			.icon(BitmapDescriptorFactory.defaultMarker()));
			aMap.getUiSettings().setZoomControlsEnabled(true);// 设置系统默认缩放按钮可见
			aMap.setOnMarkerClickListener(this);// 对marker添加点击监听器
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
