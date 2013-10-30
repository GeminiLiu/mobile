package daiwei.mobile.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.geocoder.Geocoder;

import eoms.mobile.R;
import daiwei.mobile.util.AMapUtil;
import daiwei.mobile.util.Constants;

public class YhMapViewActivity extends Activity implements 
LocationSource,AMapLocationListener,OnMapLongClickListener{
	private MapView aMapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Geocoder coder;
	
	private ProgressDialog progDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
		aMapView = (MapView) findViewById(R.id.map);
		aMapView.onCreate(savedInstanceState);
		progDialog = new ProgressDialog(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		aMapView.onResume();
		init();
		coder = new Geocoder(this);
	}

	private void init() {
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(YhMapViewActivity.this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
				mAMapLocationManager = LocationManagerProxy
						.getInstance(YhMapViewActivity.this);
				aMap.setLocationSource(this);
				aMap.setMyLocationEnabled(true);
				
				aMap.setOnMapLongClickListener(this);
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
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	/**
	 * 停止定位
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
			System.out.println("当前经度"+longitude+"::纬度"+latitude);
		
		}
	}
	// 逆地理编码
		public void getAddress(final double mlat, final double mLon) {
			Thread t = new Thread(new Runnable() {
				public void run() {

					try {
						List<List<Address>> lists = coder.getFromLocation(mlat,
								mLon, 3, 3, 3, 500);
						List<Address> address = lists.get(0);// 0代表poi搜索结果，1代表rode搜索结果，2代表cross搜索结果
						if (address != null && address.size() > 0) {
							Address addres = address.get(0);
							String addressName = addres.getAdminArea()
									+ addres.getSubLocality()
									+ addres.getFeatureName() + "附近";
							
							AddressPoint ap = new AddressPoint();
							ap.setLatitude(mlat);
							ap.setLongtitude(mLon);
							ap.setAddressName(addressName);
							Message msg = Message.obtain(handler,
									Constants.REOCODER_RESULT);
							msg.obj = ap;
							
							handler.sendMessage(msg);
							
						}
					} catch (AMapException e) {
						handler.sendMessage(Message
								.obtain(handler, Constants.ERROR));
					}

				}
			});

			progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDialog.setIndeterminate(false);
			progDialog.setCancelable(true);
			progDialog.setMessage("正在获取地址");
			progDialog.show();
			t.start();
//			return addressName;
		}
		private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == Constants.REOCODER_RESULT) {
					progDialog.dismiss();
//					Toast.makeText(getApplicationContext(), addressName,
//							Toast.LENGTH_SHORT).show();
					Builder builder = new AlertDialog.Builder(YhMapViewActivity.this);
					builder.setTitle("设置隐患地点").setIcon(R.drawable.icon_small);
					final AddressPoint ap = (AddressPoint)msg.obj;
					
					String n = "\n";
					StringBuffer sb = new StringBuffer(ap.getAddressName());
					sb.append(n);
					sb.append("纬度:");
					sb.append(ap.getLatitude());
					sb.append(n);
					sb.append("经度:");
					sb.append(ap.getLongtitude());
				
					builder.setMessage(sb);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dia,
										int which) {
									
									double lat=ap.getLatitude();
									double log=ap.getLongtitude();
									Intent intent=new Intent();
									intent.putExtra("address", ap.getAddressName());
									intent.putExtra("lat", String.valueOf(lat));
									intent.putExtra("log", String.valueOf(log));
									setResult(4, intent);
									finish();
									
								}
							})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dia, int which) {
											dia.dismiss();
										}
									}).show();
				} else if (msg.what == Constants.ERROR) {
					progDialog.dismiss();
					Toast.makeText(getApplicationContext(), "请检查网络连接是否正确?",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	/**
	 * 地图长按事件
	 */
	@Override
	public void onMapLongClick(LatLng point) {
//		String poi=point.toString();
		double lat=point.latitude;
		double lon=point.longitude;
		
//		String latitude=String.valueOf(lat);
//		String longtitude=String.valueOf(lon);
//		Toast.makeText(getApplicationContext(),latitude+":::"+longtitude, 0).show();
		System.out.println(lat+":"+lon);
		getAddress(lat, lon);
		
//		System.out.println(addressName);
		
	}

	class AddressPoint{
		double latitude;
		double longtitude;
		String addressName;
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
		public double getLongtitude() {
			return longtitude;
		}
		public void setLongtitude(double longtitude) {
			this.longtitude = longtitude;
		}
		public String getAddressName() {
			return addressName;
		}
		public void setAddressName(String addressName) {
			this.addressName = addressName;
		}
	
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		return super.onKeyDown(keyCode, event);
	}
}
