package daiwei.mobile.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;

import daiwei.mobile.animal.Rotate3D;
import daiwei.mobile.animal.Site;
import eoms.mobile.R;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.db.DBHelper;
import daiwei.mobile.service.XJDataService;
import daiwei.mobile.service.XJDataServiceImp;
import daiwei.mobile.service.XJLineService;
import daiwei.mobile.service.XJLineServiceImp;
import daiwei.mobile.service.XJUpServiceImp;
import daiwei.mobile.util.AMapUtil;
import daiwei.mobile.util.DateTimePickDialogUtil;
import daiwei.mobile.util.ToastUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.XJBaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.XJFieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.XJLineField;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.Content;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.RecordInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 线路巡检页面
 * 
 * @author 都
 * @time 2013年5月22日
 */
public class XjLineActivity extends Activity implements OnClickListener,
		LocationSource, AMapLocationListener, OnMarkerClickListener {
	private static final int WHAT_UP_DATA_FALSE = 0;// 完成上传失败
	private static final int WHAT_UP_DATA_TRUE = 1;// 完成上传成功
	private LayoutInflater inflater;
	/** 返回按钮 */
	private ImageView imgBack;
	/** 开始 暂停 继续巡检 */
	private LinearLayout startLyt;// 开始巡检点击事件
	private ImageView startXj;// 开始巡检图片切换==等待图片
	private TextView startTv;// 开始巡检，暂停巡检，结束巡检 文字切换
	/** 完成巡检 */
	private LinearLayout completeLyt;// 完成巡检点击事件
	private ImageButton completeImg;// 完成巡检图片切换
	/** 隐患上报 */
	private LinearLayout hiddenLyt;// 隐患上报点击事件
	private ImageButton hiddenImg;// 隐患上报图片切换
	private LinearLayout lyt;
	/** 获取传递来的数据 */
	private Intent xjtent = null;
	/** 巡检工单传递过来的数据 */
	private String taskId = null;
	private List<XJBaseField> xjFields;
	/** 线路分段内容 */
	private List<XJLineField> XJLineField;
	private String bojectId;
	private String taskName;
	/** 标题名 */
	private String titleName;
	/** 巡检类型 */
	private String specialtyId;
	private WaitDialog wd = null;
	private TextView tvTitle;
	private TextView deal_tv1;
	@SuppressWarnings("rawtypes")
	ArrayList titleList = new ArrayList();
	@SuppressWarnings("rawtypes")
	ArrayList spinnerList = new ArrayList();
	Map<String, String> map = new HashMap<String, String>();
	private TextView deal_content;
	private ArrayAdapter<String> adapter;
	private Map<String, FieldView> sf = new HashMap<String, FieldView>();
	private ProgressDialog dialog = null;
	private String isW;
	/** 获取32为随机数 */
	private String randomNumber;
	/** 打点经度 */
	private String longitude;
	/** 打点纬度 */
	private String latitude;
	/** 线路巡检内容下载线程 */
	private XjLineThread xjLineThread = null;
	/** 线路巡检内容线程handler */
	private Handler xjLineHandler = null;
	/** 底部layout是否显示 */
	private boolean bottomFlag = true;
	/** 底部控件 */
	private RelativeLayout bottomLayout;
	/** 地图 */
	private ImageView point;
	private LinearLayout ll_map;
	private LinearLayout ll_list;
	private float degree = (float) 0.0;
	private int mCenterX;
	private int mCenterY;
	boolean flag = true;

	private MapView aMapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker defaultMarker;
	private boolean automaticFlag = false;// 是否获取经纬度的标记
	private String errorMessage = null;	

	// private HashMap<String ,String > tudeMap=new HashMap<String,String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xj_line);
		init();
		aMapView.onCreate(savedInstanceState);
		MyData.activityHashMap.put("BaseTmpActivity", this);// 加入集合
		MyData.add(XjLineActivity.this);
		getData();

	}

	@Override
	protected void onResume() {
		System.out.println("onResume...........");
		super.onResume();
		aMapView.onResume();
		initMap();
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

	private void initMap() {
		System.out.println("初始化地图............=");
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(XjLineActivity.this, aMap)) {// 如果有地图
				setUpMap();
			}
		}

	}

	/**
	 * 定位的方法
	 */
	private void setUpMap() {
		// // 对地图添加一个marker示例
		// defaultMarker = aMap.addMarker(new MarkerOptions()
		// .position(Constants.SHENYANG).title("沈阳市").snippet("沈阳SR国际新城")
		// .icon(BitmapDescriptorFactory.defaultMarker()));
		// aMap.setOnMarkerClickListener(this);// 对marker添加点击监听器
		// aMap.addMarker(new MarkerOptions().position(Constants.SHENYANG));
		// 绘制一条折线
		// aMap.addPolyline((new PolylineOptions())
		// .add(new LatLng(41.48, 123.58), Constants.SHENYANG,
		// new LatLng(41.5688, 123.90)).color(Color.RED)
		// .width(5));
		new Thread() {
			public void run() {

				XJLineService xjs = new XJLineServiceImp(
						getApplicationContext(), taskId);
				HashMap<Integer, ArrayList<Site>> hashMap = xjs.getSiteMap("");// 取出线
				for (int ii = 0; ii < hashMap.size(); ii++) {
					ArrayList<Site> siteList = hashMap.get(ii);// 取出每条线点的集合
					List<LatLng> pointList = new ArrayList<LatLng>();// 存放点的集合
					for (int i = 0; i < siteList.size(); i++) {
						// System.out.println("iiiiiiiiiiiiiiii"+ii);

						// 如果当前点位等于下一点位,不进行打点

						Site site = siteList.get(i);// 取出每个点
						double y = site.getLatitude() + new Double(ii) / 2000;
						double x = site.getLongitude() + new Double(i) / 2000;
						LatLng latlong = new LatLng(y, x);
						pointList.add(latlong);

						aMap.addMarker(new MarkerOptions()
								.position(latlong)
								.title("沈阳")
								.snippet("第一个点")
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.point_green)));
					}
					if (siteList.size() > 1) {
						System.out.println("hhhhh" + siteList.size());
						aMap.addPolyline((new PolylineOptions())
								.addAll(pointList).color(Color.RED).width(5));
					}
					// 取出对应的线，加入点
					// ArrayList<Site> siteList1 = hashMap.get(0);//取出每条线点的集合

				}

			};

		}.start();

		// 定位
		mAMapLocationManager = LocationManagerProxy
				.getInstance(XjLineActivity.this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);
	}

	// 初始化控件
	private void init() {
		imgBack = (ImageView) findViewById(R.id.back_img);// 返回按钮
		startXj = (ImageView) findViewById(R.id.start_img);
		startTv = (TextView) findViewById(R.id.start_tv);
		startLyt = (LinearLayout) findViewById(R.id.start_lyt);
		completeLyt = (LinearLayout) findViewById(R.id.complete_lyt);
		completeImg = (ImageButton) findViewById(R.id.complete_img);
		hiddenLyt = (LinearLayout) findViewById(R.id.hidden_lyt);
		hiddenImg = (ImageButton) findViewById(R.id.hidden_img);
		bottomLayout = (RelativeLayout) findViewById(R.id.gd_bottom);// 底部动态加载条位置
		point = (ImageView) findViewById(R.id.iv_point);
		ll_list = (LinearLayout) findViewById(R.id.ll_sv);
		ll_map = (LinearLayout) findViewById(R.id.ll_map);// 地图的布局
		aMapView = (MapView) findViewById(R.id.map);// 地图

		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;

		// 下拉加载数据 listview
		lyt = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		deal_tv1 = (TextView) findViewById(R.id.deal_tv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deal_content = (TextView) findViewById(R.id.deal_content);
		imgBack.setOnClickListener(this);
		startLyt.setOnClickListener(this);
		completeLyt.setOnClickListener(this);
		hiddenLyt.setOnClickListener(this);
		point.setOnClickListener(this);
		// changeColor();
	}

	// 获取上一个类传递过来的数值
	private void getData() {
		wd = new WaitDialog(this, getString(R.string.dialog_name));
		dialog = wd.getDialog();
		xjtent = this.getIntent();
		taskId = xjtent.getStringExtra("taskId");
		titleName = xjtent.getStringExtra("titleName");
		taskName = xjtent.getStringExtra("taskName");
		specialtyId = xjtent.getStringExtra("specialtyId");
		bottomFlag = xjtent.getBooleanExtra("bottomFlag", true);
		if (bottomFlag) {
			bottomLayout.setVisibility(View.VISIBLE);
		} else {
			bottomLayout.setVisibility(View.GONE);
		}
		isW = xjtent.getStringExtra("isW");
		tvTitle.setText(titleName);// 设置工单主题
		deal_tv1.setText(taskName);// 设置工单类型 (故障处理工单)
		xjLineThread = new XjLineThread(taskId);
		xjLineThread.start();

	}

	/**
	 * 处理逻辑 动态加载textview
	 */
	private void logic() {
		if (isW.equals("1")) {
			
			addLineField(lyt);
		}
		if (xjFields != null) {
			// 处理文本体 一级
			for (int i = 0; i < xjFields.size(); i++) {// 循环拿到每一个BaseField
				bojectId = xjFields.get(i).getObjectId();
				System.out.println("bojectId============" + bojectId);
				List<XJFieldGroup> xjFieldGroups = xjFields.get(i)
						.getXjFieldGroup();// 获取巡检列表
				System.out.println("模版.........=="
						+ TempletModel.TempletMapXJ.get(specialtyId)
						+ ",,,specialtyId=" + specialtyId);
				if (TempletModel.TempletMapXJ.get(specialtyId) != null) {
					RecordInfo info = TempletModel.TempletMapXJ
							.get(specialtyId).get(bojectId);
					if (info != null) {
						addfieldGroup(lyt, info, xjFieldGroups);
					}
				}
			}
		}
	}

	private void addLineField(LinearLayout lyt) {
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		lyt.addView(view);
		TextView tx = (TextView) view.findViewById(R.id.detail_tv2);
		tx.setText("线路分段");// 设置标题名

		ImageView tv3 = (ImageView) view.findViewById(R.id.detail_tv3);
		final ImageView add = (ImageView) view.findViewById(R.id.add1);
		final LinearLayout MessageN = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);// 内容
		MessageN.setVisibility(View.GONE);
		add.setImageResource(R.drawable.add);
		RelativeLayout normalMessage = (RelativeLayout) view
				.findViewById(R.id.ll_normalMessage1); // 标题加号
		normalMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
				} else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}
		});
		if (XJLineField != null) {
			for (int i = 0; i < XJLineField.size(); i++) {
				addLineString(MessageN, XJLineField.get(i).getResName(),
						XJLineField.get(i).getMinLongitude(), XJLineField
								.get(i).getMaxLongitude(),XJLineField.get(i).getMinLatitude(),XJLineField.get(i).getMaxLatitude());
			}
		}
	}

	/**
	 * 展开页面下的控件
	 * 
	 * @param lyt
	 * @param info
	 * @param xjFieldGroups
	 */
	@SuppressWarnings("unchecked")
	private void addfieldGroup(LinearLayout lyt, RecordInfo info,
			List<XJFieldGroup> xjFieldGroups) {
		String contentId;
		String titleName;
		titleName = info.getPatrolObject();
		XJFieldGroup xjField;
		titleList.add(titleName);
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		lyt.addView(view);
		TextView tx = (TextView) view.findViewById(R.id.detail_tv2);
		tx.setText(titleName);// 设置标题名

		ImageView tv3 = (ImageView) view.findViewById(R.id.detail_tv3);
		tv3.setVisibility(View.GONE);
		// tv3.setBackgroundResource(R.drawable.normal_all);//设置对号的图片
		// tv3.setBackgroundResource(R.drawable.enormal);
		final ImageView add = (ImageView) view.findViewById(R.id.add1);
		final LinearLayout MessageN = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);// 内容
		MessageN.setVisibility(View.GONE);
		add.setImageResource(R.drawable.add);
		RelativeLayout normalMessage = (RelativeLayout) view
				.findViewById(R.id.ll_normalMessage1); // 标题加号
		normalMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
				} else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}
		});
		for (int i = 0; i < xjFieldGroups.size(); i++) {

			contentId = xjFieldGroups.get(i).getContentId();
			xjField = xjFieldGroups.get(i);
			Content content = info.getContents().get(contentId);
			if (content != null) {
				addString(MessageN, content, xjField);
			}
		}

	}

	/**
	 * 线路分段动态添加展开内容
	 * 
	 * @param lyt
	 * @param name
	 * @param minLongitude
	 * @param maxLongitude
	 */
	private void addLineString(LinearLayout lyt, String name,
			String minLongitude, String maxLongitude,String minLatitude,String maxLatitude) {
		View view1 = inflater.inflate(R.layout.switchon, null, true);
		TextView button = (TextView) view1.findViewById(R.id.xj_button_on);
		EditText edit_content = (EditText) view1
				.findViewById(R.id.editext_content);
		Spinner spinner_content = (Spinner) view1
				.findViewById(R.id.spinner_content);
		button.setVisibility(View.GONE);
		edit_content.setVisibility(View.GONE);
		spinner_content.setVisibility(View.GONE);
		TextView tvtl = (TextView) view1.findViewById(R.id.tv_switch);
		TextView tvvl = (TextView) view1.findViewById(R.id.tv_switch1);
		tvtl.setText(name);
		tvvl.setText("最小经度:" + minLongitude + ",最大经度:" + maxLongitude+"\n"+"最小纬度:"+minLatitude+",最大纬度:"+maxLatitude);
		lyt.addView(view1);
	}

	private void addString(LinearLayout lyt, final Content content,
			final XJFieldGroup xjField) {
		// taskContentId=contentId;
		String writeWayid = null;
		writeWayid = content.getWriteWayid();
		System.out.println("子节点id==============" + writeWayid);
		View view1 = inflater.inflate(R.layout.switchon, null, true);

		TextView tvtl = (TextView) view1.findViewById(R.id.tv_switch);
		tvtl.setText(content.getMaintainContent());
		TextView tvvl = (TextView) view1.findViewById(R.id.tv_switch1);
		tvvl.setText(content.getQualityStandard());
		lyt.addView(view1);
		/** 正常与异常button按钮点击事件 */
		LinearLayout edit_layout = (LinearLayout) view1
				.findViewById(R.id.edit_layout);
		final TextView button = (TextView) view1
				.findViewById(R.id.xj_button_on);
		final EditText edit_content = (EditText) view1
				.findViewById(R.id.editext_content);
		final Spinner spinner_content = (Spinner) view1
				.findViewById(R.id.spinner_content);
		// button.setTextColor(000000);
		// edittext文本框
		if (writeWayid != null) {
			if (writeWayid.equals("1")) {
				String dataType = content.getDataType();
				System.out.println("!!!!!!!!!dataType=========" + dataType);
				button.setVisibility(View.GONE);
				spinner_content.setVisibility(View.GONE);
				edit_content.setText(xjField.getDataValue());
				if (isW.equals("0")) {
					edit_content.setFocusable(false);
				}
				if (dataType != null) {
					if (dataType.equals("STRING")) {
						edit_content
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
										25) });
					} else if (dataType.equals("INT")) {
						edit_content
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
										50) });
						edit_content.setInputType(InputType.TYPE_CLASS_NUMBER);
					} else if (dataType.equals("FLOAT")) {
						edit_content
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
										50) });
						edit_content.setInputType(EditorInfo.TYPE_CLASS_PHONE);
					} else if (dataType.equals("TIME")) {
						/** 日期焦点改变的监听 //获取到焦点时隐藏软键盘 */
						edit_content
								.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
										25) });
						edit_content
								.setOnFocusChangeListener(new OnFocusChangeListener() {// 焦点改变你的监听事件
									@Override
									public void onFocusChange(View v,
											boolean hasFocus) {
										System.out.println("hasFocus=========="
												+ hasFocus);
										if (hasFocus) {// 获取到焦点时隐藏软键盘
											InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
											imm.hideSoftInputFromWindow(
													edit_content
															.getWindowToken(),
													0);
											edit_content
													.setInputType(InputType.TYPE_NULL);// 不可以手动录入时间
											DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(
													XjLineActivity.this, "");
											dateTimePickDialogUtil
													.dateTimePicKDialog(edit_content);
										}
									}
								});
						if (isW.equals("1")) {
							edit_content
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(
													XjLineActivity.this, "");
											dateTimePickDialogUtil
													.dateTimePicKDialog(edit_content);
										}
									});
						}
					}
				}
				FieldView fieldView = new FieldView();
				fieldView.setType(writeWayid);
				fieldView.setView(view1);
				sf.put(xjField.getTaskContentId(), fieldView);
			}
			// 下拉框
			else if (writeWayid.equals("2")) {
				String spinnerText = content.getDataItem();
				String allItems[] = null;
				if (!spinnerText.equals("")) {
					allItems = spinnerText.split(";");
				}
				button.setVisibility(View.GONE);
				edit_content.setVisibility(View.GONE);
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, allItems);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_content.setAdapter(adapter);
				for (int i = 0; i < allItems.length; i++) {
					if (allItems[i].equals(xjField.getDataValue())) {
						spinner_content.setSelection(i);
					} else if (allItems[i].equals("")) {
						spinner_content.setSelection(0);
					}
				}
				if (isW.equals("0")) {
					spinner_content.setEnabled(false);
				}
				spinner_content
						.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
						});
				FieldView fieldView = new FieldView();
				fieldView.setType(writeWayid);
				fieldView.setView(view1);
				sf.put(xjField.getTaskContentId(), fieldView);
			}
			// 开关键
			else if (writeWayid.equals("3")) {
				String switchText = content.getDataItem();
				System.out.println("zhengchangyuyichang========="
						+ xjField.getDataValue());
				System.out.println("switchText=============" + switchText);
				String switchItems[] = null;
				if (!switchText.equals("")) {
					switchItems = switchText.split(";");
				}
				edit_layout.setVisibility(View.GONE);
				if (xjField.getDataValue().equals(switchItems[0])) {
					final String switchNormal = switchItems[0];
					final String switchStrError = switchItems[1];
					button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
					button.setBackgroundResource(R.drawable.switch_normal);
					button.setText(switchNormal);
					button.setOnClickListener(new OnClickListener() {
						Boolean switchFlag = true;

						@Override
						public void onClick(View v) {
							if (isW.equals("1")) {
								if (switchFlag) {
									button.setGravity(Gravity.RIGHT
											| Gravity.CENTER_VERTICAL);
									button.setText(switchStrError);
									// map.put(xjField.getTaskContentId(),"异常");
									button.setBackgroundResource(R.drawable.switch_enormal);
									switchFlag = false;
								} else if (!switchFlag) {
									button.setGravity(Gravity.LEFT
											| Gravity.CENTER_VERTICAL);
									button.setText(switchNormal);
									button.setBackgroundResource(R.drawable.switch_normal);
									switchFlag = true;
								}
							}
						}
					});
				} else if (xjField.getDataValue().equals(switchItems[1])) {
					final String switchNoraml = switchItems[0];
					final String switchError = switchItems[1];
					button.setBackgroundResource(R.drawable.switch_enormal);
					button.setText(switchError);
					button.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
					button.setOnClickListener(new OnClickListener() {
						Boolean switchFlag = false;

						@Override
						public void onClick(View v) {
							if (isW.equals("1")) {
								if (!switchFlag) {
									button.setText(switchNoraml);
									button.setGravity(Gravity.LEFT
											| Gravity.CENTER_VERTICAL);
									// map.put(xjField.getTaskContentId(),"异常");
									button.setBackgroundResource(R.drawable.switch_normal);
									switchFlag = true;
								} else if (switchFlag) {
									button.setText(switchError);
									button.setGravity(Gravity.RIGHT
											| Gravity.CENTER_VERTICAL);
									button.setBackgroundResource(R.drawable.switch_enormal);
									switchFlag = false;
								}
							}
						}
					});
				} else if (!xjField.getDataValue().equals(switchItems[0])
						&& !xjField.getDataValue().equals(switchItems[1])
						|| xjField.getDataValue().equals("")) {
					final String switchNormal = switchItems[0];
					final String switchStrError = switchItems[1];
					button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
					button.setBackgroundResource(R.drawable.switch_normal);
					button.setText(switchNormal);
					button.setOnClickListener(new OnClickListener() {
						Boolean switchFlag = true;

						@Override
						public void onClick(View v) {
							if (isW.equals("1")) {
								if (switchFlag) {
									button.setGravity(Gravity.RIGHT
											| Gravity.CENTER_VERTICAL);
									button.setText(switchStrError);
									// map.put(xjField.getTaskContentId(),"异常");
									button.setBackgroundResource(R.drawable.switch_enormal);
									switchFlag = false;
								} else if (!switchFlag) {
									button.setGravity(Gravity.LEFT
											| Gravity.CENTER_VERTICAL);
									button.setText(switchNormal);
									button.setBackgroundResource(R.drawable.switch_normal);
									switchFlag = true;
								}
							}
						}
					});
				}
				FieldView fieldView = new FieldView();
				fieldView.setType(writeWayid);
				fieldView.setView(view1);
				sf.put(xjField.getTaskContentId(), fieldView);
			}
			map.put(xjField.getTaskContentId(), content.getDefaultValue());
		}
	}

	/** 底部按钮颜色改变 */
	public void changeColor() {
		// arrive_img.setBackgroundResource(R.drawable.xj_detail_one);
		// leave_img.setBackgroundResource(R.drawable.xj_detail_two);
		// complete_img.setBackgroundResource(R.drawable.xj_detail_three);
		// hidden_img.setBackgroundResource(R.drawable.xj_detail_yh);
	}

	/** 巡检线路内容下载线程 */
	class XjLineThread extends Thread {
		private XJDataService xds = null;
		private XMLUtil ele;
		private String taskId;

		private XjLineThread(String taskId) {
			xjLineHandler = new Handler();
			this.taskId = taskId;
		}

		@Override
		public void run() {
			xds = new XJDataServiceImp();
			ele = xds.getXJData(getApplicationContext(), taskId, "PTL002");
			if (ele != null && !ele.equals("")) {
				if (ele.getSuccess()) {
					xjFields = ele.getBaseFileds();
					if (isW.equals("1")) {
						XJLineField = ele.getXJLineField();
					}

				} else {
					errorMessage = ele.getErrorMessage();
				}
				xjLineHandler.post(doneRunble);
			}
		}

	}

	// 构建Runnable对象，在runnable中更新界面
	Runnable doneRunble = new Runnable() {
		@Override
		public void run() {
			// 更新界面
			dialog.dismiss();
			if (xjFields == null && errorMessage != null) {
				Toast.makeText(XjLineActivity.this, errorMessage,
						Toast.LENGTH_SHORT).show();
			}
			logic();
		}

	};

	/** 巡检类里循环上传服务器 */
	class FieldView {
		private String type;
		private String actionCode;
		private View view;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getActionCode() {
			return actionCode;
		}

		public void setActionCode(String actionCode) {
			this.actionCode = actionCode;
		}

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}

		public String getValue() {
			String str = null;
			if (type.equals("1")) {
				EditText edit_content = (EditText) getView().findViewById(
						R.id.editext_content);
				str = edit_content.getText().toString();
			} else if (type.equals("2")) {
				Spinner spinner_content = (Spinner) getView().findViewById(
						R.id.spinner_content);
				str = spinner_content.getSelectedItem().toString();
			} else if (type.equals("3")) {
				TextView button = (TextView) getView().findViewById(
						R.id.xj_button_on);
				str = button.getText().toString();
			}
			return str;
		}
	}

	/**
	 * 线路巡检页面的点击事件
	 * 
	 * @param back_img
	 *            返回键 直接关掉此activity
	 * @param start_lyt
	 *            开始 暂停 继续打点事件
	 * @param complete_lyt
	 *            完成巡检
	 * @param hidden_lyt
	 *            隐患上报 跳转到新建隐患页面
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back_img:
			finish();
			break;
		case R.id.start_lyt:
			if (startTv.getText().equals("开始巡检")) {
				automaticFlag = true;
				Toast.makeText(XjLineActivity.this, "开始打点...",
						Toast.LENGTH_SHORT).show();
				randomNumber = getLineNo();
				startTv.setText("暂停巡检");
				startXj.setImageResource(R.drawable.xj_pause);
			} else if (startTv.getText().equals("暂停巡检")) {
				automaticFlag = false;
				randomNumber = "0";
				startTv.setText("继续巡检");
				startXj.setImageResource(R.drawable.xj_start);
			} else if (startTv.getText().equals("继续巡检")) {
				automaticFlag = true;
				randomNumber = getLineNo();
				startTv.setText("暂停巡检");
				startXj.setImageResource(R.drawable.xj_pause);
			}
			break;
		case R.id.complete_lyt:
			final View dialogview = inflater.inflate(R.layout.finishdialog,
					null);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					XjLineActivity.this);
			builder.setView(dialogview);
			builder.setPositiveButton(getString(R.string.sure),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dia, int which) {
							EditText et_finish = (EditText) dialogview
									.findViewById(R.id.et_finish);
							String taskNote = et_finish.getText().toString();
							wd = new WaitDialog(XjLineActivity.this,
									getString(R.string.dialog_upload_name));
							dialog = wd.getDialog();
							String status = "3";
							UpLoadLineThread upThread = new UpLoadLineThread(
									taskNote, status, longitude, latitude);
							upThread.start();

						}
					})
					.setNegativeButton(getString(R.string.cancle),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dia,
										int which) {
									dia.dismiss();
								}
							}).show();
			break;
		case R.id.hidden_lyt:
			Intent intent = new Intent(XjLineActivity.this,
					YhHandleActivity.class);
			intent.putExtra("titleName", taskName + titleName + "上报隐患");
			intent.putExtra("longitude", longitude);
			intent.putExtra("latitude", latitude);
			intent.putExtra("site", "死数据 等待服务器数据");
			intent.putExtra("professional", "基站一体化.铁塔及天馈系统");
			startActivity(intent);
			break;
		case R.id.iv_point:
			if (flag) {// 代表展示地图
				Rotate3D rotate3d = new Rotate3D(degree, -90, 0, mCenterX,
						mCenterY);
				Rotate3D rotate3d3 = new Rotate3D(90 + degree, 0, 0, mCenterX,
						mCenterY);

				rotate3d.setDuration(300);
				rotate3d3.setDuration(300);

				ll_list.startAnimation(rotate3d);
				ll_map.startAnimation(rotate3d3);

				ll_map.setVisibility(View.VISIBLE);// 展示地图
				ll_list.setVisibility(View.GONE);
				point.setImageResource(R.drawable.icon_list);

				/**根据服务器获取最大，最小经纬度画点*/
				if(XJLineField!=null){
				for(int i=0;i<XJLineField.size();i++){
					List<LatLng> serverList = new ArrayList<LatLng>();
					LatLng minPoint = new LatLng(Double.parseDouble(XJLineField.get(i).getMinLatitude()),Double.parseDouble(XJLineField.get(i).getMinLongitude()));// 定位最小的经纬度
					LatLng maxPoint = new LatLng(Double.parseDouble(XJLineField.get(i).getMaxLatitude()),Double.parseDouble(XJLineField.get(i).getMaxLongitude()));// 定位最大的经纬度
					
					aMap.addMarker(new MarkerOptions()
					.position(minPoint)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.point_green)));
					aMap.addMarker(new MarkerOptions()
					.position(maxPoint)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.point_green)));
					serverList.add(minPoint);
					serverList.add(maxPoint);
					aMap.addPolyline((new PolylineOptions()).addAll(serverList)
							.color(Color.BLUE).width(5));

				}
				}
				flag = false;
			} else {// 代表展示列表
				Rotate3D rotate3d = new Rotate3D(degree, 90, 0, mCenterX,
						mCenterY);
				Rotate3D rotate3d3 = new Rotate3D(-90 + degree, 0, 0, mCenterX,
						mCenterY);

				rotate3d.setDuration(300);
				rotate3d3.setDuration(300);

				ll_list.startAnimation(rotate3d3);
				ll_map.startAnimation(rotate3d);

				ll_map.setVisibility(View.GONE);// 隐藏地图
				ll_list.setVisibility(View.VISIBLE);
				point.setImageResource(R.drawable.icon_map);

				flag = true;
			}
			if (startTv.getText().equals("暂停巡检")) {
				automaticFlag = true;// 定位
			} else {
				automaticFlag = false;
			}
			break;
		default:
			break;
		}
	}

	// 完成线程上传
	class UpLoadLineThread extends Thread {
		private String content;
		private String status;
		private String jingdu, weidu;

		private UpLoadLineThread(String editContent, String status,
				String jingdu, String weidu) {
			this.content = editContent;
			this.status = status;
			this.jingdu = jingdu;
			this.weidu = weidu;
		}

		@SuppressLint("SimpleDateFormat")
		public void run() {

			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String time = sDateFormat.format(new java.util.Date());
			boolean falg = false;
			Set<Entry<String, FieldView>> set = sf.entrySet();
			for (Iterator<Map.Entry<String, FieldView>> it = set.iterator(); it
					.hasNext();) {
				Map.Entry<String, FieldView> entry = (Map.Entry<String, FieldView>) it
						.next();
				map.put(entry.getKey(), entry.getValue().getValue());
			}
			XJUpServiceImp xjUp = new XJUpServiceImp();
			XMLUtil xml = xjUp.getXJUpLoad(getApplicationContext(), taskId,
					status, jingdu, weidu, "", content, map, "PTL003");
			falg = xml.getSuccess();
			if (falg) {
				lineHandler.sendEmptyMessage(WHAT_UP_DATA_TRUE);

			} else {
				lineHandler.sendEmptyMessage(WHAT_UP_DATA_FALSE);
			}
		}
	}

	Handler lineHandler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case WHAT_UP_DATA_TRUE:
				dialog.dismiss();
				Toast.makeText(XjLineActivity.this,
						getString(R.string.upload_success), Toast.LENGTH_SHORT)
						.show();
				Intent xjIntent = new Intent();
				xjIntent.setAction("refresh");
				sendBroadcast(xjIntent);
				finish();
				break;
			case WHAT_UP_DATA_FALSE:
				dialog.dismiss();
				Toast.makeText(XjLineActivity.this,
						getString(R.string.upload_failed), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;

			}

		}

	};

	/**
	 * 五秒往数据库插入一条数据
	 * 
	 * @param taskID
	 *            线路巡检每个的任务ID
	 * @param Longitude五秒钟获取一次经度
	 * @param Latitude五秒钟获取一次纬度
	 * @param LineNo随机产生的32位数
	 * @return
	 */
	public boolean insertDB(String taskID, String Longitude, String Latitude,
			String LineNo) {
		Toast.makeText(XjLineActivity.this,
				"五秒钟运行一次经纬度" + Longitude + "," + Latitude, Toast.LENGTH_SHORT)
				.show();
		boolean b = true;
		try {
			DBHelper helper = new DBHelper(XjLineActivity.this);
			SQLiteDatabase db = helper.getWritableDatabase();
			String LocalTime = getLocalTime();
			if (db.isOpen()) {
				db.execSQL(
						"insert into tb_line_position (TaskID,Longitude,Latitude,LineNo,LocalTime,IsUpLoad) values (?,?,?,?,?,0)",
						new Object[] { taskID, Longitude, Latitude, LineNo,
								LocalTime });
				db.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}

	/**
	 * @Title: getLocalTime
	 * @Description: 返回手机当前时间
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private String getLocalTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = sDateFormat.format(new java.util.Date());
		return time;
	}

	/**
	 * @Title: getLineNo
	 * @Description: 生成32位随机巡检段号
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private String getLineNo() {

		randomNumber = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return randomNumber;
	}

	@Override
	protected void onPause() {
		System.out.println("onPause==========" + aMap);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (!flag) {
			aMapView.onDestroy();
		}
		deactivate();
		super.onDestroy();
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

	// 模拟打点画线的变量
	double caihua = 0.0;
	List<LatLng> poiList = new ArrayList<LatLng>();

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		System.out.println("定位回调...............");
		longitude = String.valueOf(aLocation.getLongitude());
		latitude = String.valueOf(aLocation.getLatitude());
		System.out.println("longitude======" + longitude + ",,,latitude======"
				+ latitude);		
		if (automaticFlag) {
			if (mListener != null) {
				mListener.onLocationChanged(aLocation);
				// 模拟打点-------------------------------------------------------------
				// 获取经纬度，打点
				double x = aLocation.getLatitude() + caihua;// 模拟打点
				caihua += 0.05;
				LatLng point = new LatLng(x, aLocation.getLongitude());// 定位的经纬度
				// -------------------------------------------------------------------
				// LatLng point = new
				// LatLng(aLocation.getLatitude(),aLocation.getLongitude());
				poiList.add(point);
				if (poiList.size() >= 2) { // 画线
					aMap.addPolyline((new PolylineOptions()).addAll(poiList)
							.color(Color.RED).width(5));
				}
				// 对地图添加一个marker
				defaultMarker = aMap.addMarker(new MarkerOptions()
						.position(point)
						.title("沈阳")
						.snippet("第一个点")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.point_green)));
				insertDB(taskId, longitude, latitude, randomNumber);
			}
		} else if (!flag) {// 点击顶部右上角按钮 第一次进入地图
			System.out.println("点击顶部右上角按钮 第一次进入地图");
			if (mListener != null) {
				mListener.onLocationChanged(aLocation);
				initMap();
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		System.out.println("激活定位............");
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		System.out.println("停止定位...............=");
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (marker.equals(defaultMarker)) {
			ToastUtil.show(XjLineActivity.this, marker.getSnippet());
		}
		return false;
	}
}
