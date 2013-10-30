package daiwei.mobile.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import eoms.mobile.R;
import daiwei.mobile.Tools.GPS;
import daiwei.mobile.animal.Rotate3D;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.AutoUploadManager;
import daiwei.mobile.service.XJArriveImp;
import daiwei.mobile.service.XJDataService;
import daiwei.mobile.service.XJDataServiceImp;
import daiwei.mobile.service.XJUpServiceImp;
import daiwei.mobile.util.AMapUtil;
import daiwei.mobile.util.DateTimePickDialogUtil;
import daiwei.mobile.util.FileUtil;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.XJBaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.XJFieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.Content;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.RecordInfo;
import daiwei.mobile.util.TempletUtil.TMPModelXJ.XjItem;

/**
 * 巡检页面
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public class XjTmpActivity extends Activity implements OnClickListener,LocationSource,AMapLocationListener {
	public static final int WHAT_UP_DATA_FALSE = 0;// 完成上传失败
	public static final int WHAT_UP_DATA_TRUE = 1;// 完成上传成功
	public static final int XJ_ARRIVE_TRUE = 4;// 到达上传经纬度成功
	public static final int XJ_ARRIVE_FALSE = 5;// 到达上传经纬度失败
	public static final int XJ_ARRIVE = 6;// 到达
	public static final int XJ_LEAVE = 7;// 离开
	public static final int XJ_COMPLETE = 8;// 完成
	public static final int XJ_HIDDEN = 9;
	public int detailId = R.layout.gd_detail_bg;// 模板主体
	private LayoutInflater inflater;
	private ImageView img_back;// 返回按钮
	private LinearLayout lyt;
	private Intent xjtent = null;// 获取传递来的数据
	private Handler XJHandler = null;
	private XJThread xjThread = null;// 巡检下载线程
	private String taskId = null;// 巡检工单传递过来的数据
	private String taskNote = "";
	private List<XJBaseField> xjFields;
	private String bojectId;
	private String taskName;
	private String titleName;//标题名
	private String specialtyId;//巡检类型
	private WaitDialog wd = null;
	private ProgressDialog dialog = null;
	private TextView tvTitle;
	private TextView deal_tv1;
	private ImageButton arrive_img;
	private ImageButton leave_img;
	private ImageButton complete_img;
	private ImageButton recode_img;
	private ImageButton photo_img;
	private ImageButton hidden_img;
	ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();
	@SuppressWarnings("rawtypes")
	ArrayList titleList = new ArrayList();
	@SuppressWarnings("rawtypes")
	ArrayList spinnerList = new ArrayList();
	Map<String, String> map = new HashMap<String, String>();
	int mSingleChoiceID = -1;;
	private TextView deal_content;
	private ArrayAdapter<String> adapter;
	private boolean bottomFlag = true;
	private RelativeLayout bottomLayout;
	private Bitmap photo = null;
	private File sd, file, fileChild, temporaryFile = null;
	private String imgName, picPath, picPathChild;
	private FileOutputStream baos;
	private boolean state = true;
	private LocationClient mLocClient;
	private String longitude, latitude;
	private MyLocationListenner myListener = null;
	private static final int BUFFER = 1024 * 1024;// 缓存大小
	private boolean radioFlag = true;
	private MediaRecorder mRecorder;
	private Map<String, FieldView> sf = new HashMap<String, FieldView>();
	private XjItem xjItem = null;
	private String isW;
	private String status = "1";
	private View view;
	private Handler stepTimeHandler;// 录音定时器handler
	private Runnable mTicker;// 录音定时器定时发送消息的服务
	private long startTime = 0;
	private String eDistance = "1000";// 实际经纬度与服务器经纬度之间的距离

	private Gallery gallery;// 图片Galley适配器
	private ArrayList<String> picList;// 图片列表
	private ImageAdapter imageAdapter;// 图片适配器

	private Gallery voiceGallery;// 声音Galley适配器
	private ArrayList<String> voiceList;// 声音列表
	private VoiceAdapter voiceAdapter;// 声音适配器

	private ImageButton imgCamera;// 拍照按钮
	private ImageButton imgMic;// 录音按钮
	private boolean ifStart = false;// 判断是否可以点击此页面
	private boolean ifArrive = false;// 判断是否到达
	//地图图标
	private ImageView mapicon_img;
	private boolean ifOnReStart = true;//判断onStart 是否是回调页跳转过来的
	private LinearLayout ll_map;
	private LinearLayout ll_list;
	private float degree = (float) 0.0;
	private int mCenterX;
	private int mCenterY;
	boolean flag = true;//代表展示列表
	private boolean ifMap = true;//判断onStart 是否是从地图页跳转过来的
	
	private MapView aMapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private Marker defaultMarker;
	private boolean automaticFlag = false;// 是否获取经纬度的标记
	private Double jzlatitude1;
	private Double jzlongtitude1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(detailId);
		
		MyData.activityHashMap.put("BaseTmpActivity", this);// 加入集合
		MyData.add(XjTmpActivity.this);
		state = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在;
		if (state) {
			sd = Environment.getExternalStorageDirectory();
			picPath = sd + "/daiwei";
			file = new File(picPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		init();
		aMapView.onCreate(savedInstanceState);
		getData();
		initImage();
		initRecord();

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
	@Override
	protected void onPause() {
		super.onPause();
		aMapView.onPause();
		deactivate();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(!flag){
		aMapView.onDestroy();
		}
		deactivate();
	}

	private void initMap() {
		System.out.println("初始化地图............=");
		if (aMap == null) {
			aMap = aMapView.getMap();
			if (AMapUtil.checkReady(XjTmpActivity.this, aMap)) {// 如果有地图
				setUpMap();
			}
		}

	}
	private void setUpMap() {
		// 定位
		mAMapLocationManager = LocationManagerProxy
				.getInstance(XjTmpActivity.this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);
	}
	//初始化控件
	private void init() {
		img_back = (ImageView) findViewById(R.id.iv_friends);// 返回按钮
		img_back.setOnClickListener(this);
		// 下拉加载数据 listview
		lyt = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		deal_tv1 = (TextView) findViewById(R.id.deal_tv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		deal_content = (TextView) findViewById(R.id.deal_content);
		deal_content.setVisibility(View.GONE);

		bottomLayout = (RelativeLayout) findViewById(R.id.gd_bottom);// 底部动态加载条位置
		arrive_img = (ImageButton) findViewById(R.id.arrive_img);
		leave_img = (ImageButton) findViewById(R.id.leave_img);
		complete_img = (ImageButton) findViewById(R.id.complete_img);
		recode_img = (ImageButton) findViewById(R.id.recode_img);
		photo_img = (ImageButton) findViewById(R.id.photo_img);
		hidden_img = (ImageButton) findViewById(R.id.hidden_img);
		mapicon_img = (ImageView) findViewById(R.id.imgview_map_icon);//又上角地图图标
		ll_list = (LinearLayout) findViewById(R.id.ll_sv);
		ll_map = (LinearLayout) findViewById(R.id.ll_map);// 地图的布局
		aMapView = (MapView) findViewById(R.id.map);// 地图
		
		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		mCenterX = dm.widthPixels / 2;
		mCenterY = dm.heightPixels / 2;
		
		arrive_img.setOnClickListener(this);
		leave_img.setOnClickListener(this);
		complete_img.setOnClickListener(this);
		recode_img.setOnClickListener(this);
		photo_img.setOnClickListener(this);
		hidden_img.setOnClickListener(this);
		mapicon_img.setOnClickListener(this);
		changeColor();
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
		isW = xjtent.getStringExtra("isW");
		picPathChild = picPath + "/" + taskId;
		if (bottomFlag) {
			bottomLayout.setVisibility(View.VISIBLE);
		} else {
			bottomLayout.setVisibility(View.GONE);
		}
		tvTitle.setText(titleName);// 设置工单主题
		deal_tv1.setText(taskName);// 设置工单类型 (故障处理工单)
		xjThread = new XJThread(taskId);
		xjThread.start();
	}

	private void initImage() {
		picPathChild = picPath + "/" + taskId;// 巡检号对应下的文件
		picList = getList();
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setSelection(picList.size() / 2);
		imageAdapter = new ImageAdapter(XjTmpActivity.this, picList);
		gallery.setAdapter(imageAdapter);
		gallery.setOnItemClickListener(listener);
		imgCamera = (ImageButton) findViewById(R.id.camera);
		imgMic = (ImageButton) findViewById(R.id.mic);
		if (isW.equals("1")) {
			imgCamera.setOnClickListener(this);
			imgMic.setOnClickListener(this);
		} else {
			imgCamera.setVisibility(View.GONE);
			imgMic.setVisibility(View.GONE);
		}

	}

	private void initRecord() {
		picPathChild = picPath + "/" + taskId;// 工单号对应下的文件
		voiceList = getVoiceList();
		voiceGallery = (Gallery) findViewById(R.id.voicegallery);
		voiceAdapter = new VoiceAdapter(XjTmpActivity.this, voiceList);
		voiceGallery.setAdapter(voiceAdapter);
		voiceGallery.setOnItemClickListener(voiceListener);

	}

	/**
	 * 获取daiwei/taskId 下的所有文件列表
	 * 
	 * @return SD卡下对应ID的图片文件的列表
	 */
	private ArrayList<String> getList() {
		ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<String> list = getSubFiles(picPathChild);// 列出daiwei/taskId下的所有文件
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).endsWith(".JPG")) {
				imageList.add(list.get(i));
			}
		}
		return imageList;
	}

	/**
	 * 获取daiwei/taskId 下声音文件列表
	 * 
	 * @return SD卡下对应声音文件的列表
	 */
	private ArrayList<String> getVoiceList() {
		ArrayList<String> voiceList = new ArrayList<String>();
		ArrayList<String> list = getSubFiles(picPathChild);// 列出daiwei/taskId下的所有文件
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).endsWith(".amr")) {
				voiceList.add(list.get(i));
			}
		}
		return voiceList;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            baseDir 指定的目录
	 * @return 包含java.io.File的List
	 */
	private ArrayList<String> getSubFiles(String baseDir) {
		ArrayList<String> list = new ArrayList<String>();
		File file = new File(baseDir);
		if (file.exists()) {
			File[] tmp = file.listFiles();
			if (tmp == null || tmp.length < 1) {
				return list;
			}
			for (int i = 0; i < tmp.length; i++) {
				list.add(tmp[i].getName());
			}
		}
		return list;
	}

	/**
	 * gallery条目点击事件
	 */
	private OnItemClickListener listener = new OnItemClickListener() {

		private PopupWindow pw;

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			// Toast.makeText(getApplicationContext(), "点击了" + position,
			// 0).show();
			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popView = inflater.inflate(R.layout.popupwindow, null, true);// 初始化popWIn
			View layout1 = inflater.inflate(R.layout.gd_modle, null);// 父view
			Button btn_preview = (Button) popView
					.findViewById(R.id.btn_preview);// 预览图片
			Button btn_del = (Button) popView.findViewById(R.id.btn_del);// 清除图片
			Button btn_dismiss = (Button) popView
					.findViewById(R.id.btn_dismiss);// 取消

			pw = new PopupWindow(XjTmpActivity.this);
			pw.setContentView(popView);// 设置的View
			pw.setWidth(LayoutParams.FILL_PARENT);
			pw.setHeight(LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(true); // 设置弹出窗体可点击
			pw.setOutsideTouchable(true);// 设置允许在外点击消失
			// pw.setAnimationStyle(R.drawable.alpha_in);
			pw.showAtLocation(layout1, Gravity.BOTTOM, 0, 0);

			final String name = (String) imageAdapter.getItem(position);// 点击的图片文件名
			String path = picPathChild + "/" + name;// 工单目录+文件名
			final File newfile = new File(path);
			/**
			 * 预览图片
			 */
			btn_preview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent("android.intent.action.VIEW");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Uri uri = Uri.fromFile(newfile);
					if (name.endsWith(".JPG")) {
						intent.setDataAndType(uri, "image/*");
						startActivity(intent);
						pw.dismiss();
					}

				}
			});
			/**
			 * 清除图片
			 */
			btn_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						newfile.delete();
					} catch (Exception e) {
						Log.e("XjTmpActivity，删除文件操作", e.toString());
					} finally {
						imageAdapter.list.remove(position);// 移除文件名
						imageAdapter.notifyDataSetChanged();// 移除文件名
						Toast.makeText(getApplicationContext(), "删除成功",
								Toast.LENGTH_SHORT).show();
						pw.dismiss();
					}

				}
			});
			/**
			 * 取消操作
			 */
			btn_dismiss.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					pw.dismiss();
				}
			});

		}
	};

	/**
	 * voiceGallery条目点击事件
	 */
	private OnItemClickListener voiceListener = new OnItemClickListener() {

		private PopupWindow pw;

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				final int position, long id) {
			LayoutInflater inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popView = inflater.inflate(R.layout.popupwindow, null, true);// 初始化popWIn
			View layout1 = inflater.inflate(R.layout.gd_modle, null);// 父view
			Button btn_preview = (Button) popView
					.findViewById(R.id.btn_preview);// 播放录音
			btn_preview.setText("播放录音");
			Button btn_del = (Button) popView.findViewById(R.id.btn_del);// 清除录音
			btn_del.setText("清除录音");
			Button btn_dismiss = (Button) popView
					.findViewById(R.id.btn_dismiss);// 取消

			pw = new PopupWindow(XjTmpActivity.this);
			pw.setContentView(popView);// 设置的View
			pw.setWidth(LayoutParams.FILL_PARENT);
			pw.setHeight(LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(true); // 设置弹出窗体可点击
			pw.setOutsideTouchable(true);// 设置允许在外点击消失
			// pw.setAnimationStyle(R.drawable.alpha_in);
			pw.showAtLocation(layout1, Gravity.BOTTOM, 0, 0);

			final String name = (String) voiceAdapter.getItem(position);// 点击的录音文件名
			String path = picPathChild + "/" + name;// 工单目录+文件名
			final File newfile = new File(path);
			/**
			 * 打开录音
			 */
			btn_preview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(android.content.Intent.ACTION_VIEW);
					Uri uri = Uri.fromFile(newfile);

					if (name.endsWith(".amr")) {
						intent.setDataAndType(uri, "audio/*");
						startActivity(intent);
						pw.dismiss();
					}

				}
			});
			/**
			 * 清除录音
			 */
			btn_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						newfile.delete();
					} catch (Exception e) {
						Log.e("XjTmpActivity，删除录音文件操作", e.toString());
					} finally {
						voiceAdapter.list.remove(position);// 移除文件名
						voiceAdapter.notifyDataSetChanged();// 移除文件名
						Toast.makeText(getApplicationContext(), "删除成功",
								Toast.LENGTH_SHORT).show();
						pw.dismiss();
					}

				}
			});
			/**
			 * 取消操作
			 */
			btn_dismiss.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pw.dismiss();
				}
			});

		}
	};

	/**
	 * 处理逻辑 动态加载textview
	 */
	private void logic() {
		// 处理文本体 一级
		for (int i = 0; i < xjFields.size(); i++) {// 循环拿到每一个BaseField
			bojectId = xjFields.get(i).getObjectId();
			System.out.println("bojectId============" + bojectId);
			List<XJFieldGroup> xjFieldGroups = xjFields.get(i)
					.getXjFieldGroup();// 获取巡检列表
			System.out.println("模版.........=="
					+ TempletModel.TempletMapXJ.get(specialtyId)+",,,specialtyId="+specialtyId);
			if (TempletModel.TempletMapXJ.get(specialtyId) != null) {
				RecordInfo info = TempletModel.TempletMapXJ.get(specialtyId)
						.get(bojectId);
				if (info != null) {
					eDistance = info.getDistance();
					addfieldGroup(lyt, info, xjFieldGroups);
				}
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
				if (!ifStart && isW.endsWith("1")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.arrive_sure), Toast.LENGTH_SHORT)
							.show();
					return;
				}
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
													XjTmpActivity.this, "");
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
													XjTmpActivity.this, "");
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
					}
					else if (allItems[i].equals("")) {
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

	/**handler接收消息*/
	private Handler handler = new Handler() {
		AlertDialog.Builder builder = null;
		@SuppressLint("HandlerLeak")
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String date = null;
		double distance;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case XJ_ARRIVE_TRUE:
				dialog.dismiss();
				Toast.makeText(XjTmpActivity.this, getString(R.string.upload_tude_success),
						Toast.LENGTH_SHORT).show();
				ifStart = true;
				break;
			case XJ_ARRIVE_FALSE:
				dialog.dismiss();
				Toast.makeText(XjTmpActivity.this, getString(R.string.upload_tude_failed),
						Toast.LENGTH_SHORT).show();
				break;
			case WHAT_UP_DATA_TRUE:
				dialog.dismiss();
				Toast.makeText(XjTmpActivity.this, getString(R.string.upload_success), Toast.LENGTH_SHORT)
						.show();
				Intent xjIntent = new Intent();
				xjIntent.setAction("refresh");
				sendBroadcast(xjIntent);
				finish();
				break;
			case WHAT_UP_DATA_FALSE:
				dialog.dismiss();
				Toast.makeText(XjTmpActivity.this, getString(R.string.upload_failed), Toast.LENGTH_SHORT)
						.show();
				break;
			case XJ_ARRIVE:
				String templateLatitude = xjItem.getLatitude();
				String templateLongitude = xjItem.getLongitude();
				if(templateLatitude.equals("null")){
					templateLatitude="0";
				}
				if(templateLongitude.equals("null")){
					templateLongitude="0";
				}
				date = sdf.format(new java.util.Date());
				System.out.println("时间date================" + date);
				dialog.dismiss();
				distance = GetDistance(Double.parseDouble(latitude),
						Double.parseDouble(longitude),
						Double.parseDouble(templateLatitude),
						Double.parseDouble(templateLongitude));
				if (distance < Long.parseLong(eDistance)) {
					builder = new AlertDialog.Builder(XjTmpActivity.this);
					builder.setMessage("您所在位置与基站经纬度在误差范围内，可以正常巡检！"
							+ "\n-------------------------------" + "\n当前时间:"
							+ date + "\n当前基站:" + taskName + "\n该基站经纬度:"
							+ templateLongitude + ";"
							+ templateLatitude + "\n当前经纬度:" + longitude
							+ ";" + latitude
							+ "\n-------------------------------" + "\n可以巡检！");
					builder.setPositiveButton(getString(R.string.sure),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dia,
										int which) {
									wd = new WaitDialog(XjTmpActivity.this,
											getString(R.string.dialog_upload_tude_name));
									dialog = wd.getDialog();
									ArriveThread arriveThread = new ArriveThread(
											longitude, latitude);
									arriveThread.start();
									// Toast.makeText(getApplicationContext(),
									// "已到达",0).show();
									leave_img.setEnabled(true);
									complete_img.setEnabled(true);
									recode_img.setEnabled(true);
									photo_img.setEnabled(true);
									hidden_img.setEnabled(true);
								}
							})
							.setNegativeButton(getString(R.string.cancle),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dia, int which) {
											dia.dismiss();
										}
									}).show();
				} else {
					builder = new AlertDialog.Builder(XjTmpActivity.this);
					if (longitude.equals("4.9E-324")
							&& latitude.equals("4.9E-324")) {
						builder.setMessage("您所在位置与基站经纬度偏差较大，请重新定位或离靠近基站重新定位！"
								+ "\n-------------------------------"
								+ "\n当前时间:" + date + "\n当前基站:" + taskName
								+ "\n该基站经纬度: " + templateLongitude + ";"
								+ templateLatitude + "\n当前经纬度:"
								+ "获取经纬度失败!"
								+ "\n-------------------------------"
								+ "\n不能巡检！");
					} else {
						builder.setMessage("您所在位置与基站经纬度偏差较大，请重新定位或离靠近基站重新定位！"
								+ "\n-------------------------------"
								+ "\n当前时间:" + date + "\n当前基站:" + taskName
								+ "\n该基站经纬度:" + templateLongitude + ";"
								+ templateLatitude + "\n当前经纬度:" + longitude
								+ ";" + latitude
								+ "\n-------------------------------"
								+ "\n不能巡检！");
					}
					builder.setNegativeButton(getString(R.string.cancle),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dia,
										int which) {
									dia.dismiss();
								}
							}).show();
				}
				break;
			case XJ_LEAVE:
				String templateLeaveLatitude = xjItem.getLatitude();
				String templateLeaveLongitude = xjItem.getLongitude();				
				if(templateLeaveLatitude.equals("null")){
					templateLeaveLatitude="0";
				}
				if(templateLeaveLongitude.equals("null")){
					templateLeaveLongitude="0";
				}
				date = sdf.format(new java.util.Date());
				dialog.dismiss();
				distance = 0;
				distance = GetDistance(Double.parseDouble(latitude),
						Double.parseDouble(longitude),
						Double.parseDouble(templateLeaveLatitude),
						Double.parseDouble(templateLeaveLongitude));
				if (distance < Long.parseLong(eDistance)) {
					builder = new AlertDialog.Builder(XjTmpActivity.this);
					builder.setMessage("您所在位置与基站经纬度在误差范围内，可以正常巡检！"
							+ "\n-------------------------------" + "\n当前时间:"
							+ date + "\n当前基站:" + taskName + "\n该基站经纬度:"
							+ templateLeaveLongitude + ";"
							+ templateLeaveLatitude + "\n当前经纬度:" + longitude
							+ ";" + latitude
							+ "\n-------------------------------" + ""
							+ "\n可以巡检！");
					builder.setPositiveButton(getString(R.string.sure),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dia,
										int which) {
									wd = new WaitDialog(XjTmpActivity.this,
											getString(R.string.dialog_upload_name));
									dialog = wd.getDialog();
									status = "2";
									UpLoadThread upThread = new UpLoadThread(
											"", status, longitude, latitude);
									upThread.start();
								}
							})
							.setNegativeButton(getString(R.string.cancle),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dia, int which) {
											dia.dismiss();
										}
									}).show();
				} else {
					builder = new AlertDialog.Builder(XjTmpActivity.this);
					if (longitude.equals("4.9E-324")
							&& latitude.equals("4.9E-324")) {
						builder.setMessage("您所在位置与基站经纬度偏差较大，请重新定位或离靠近基站重新定位！"
								+ "\n-------------------------------"
								+ "\n当前时间: " + date + "\n当前基站:" + taskName
								+ "\n该基站经纬度:" + templateLeaveLongitude + ";"
								+ templateLeaveLatitude + "\n当前经纬度:"
								+ "获取经纬度失败!"
								+ "\n-------------------------------"
								+ "\n不能巡检！");
					} else {
						builder.setMessage("您所在位置与基站经纬度偏差较大，请重新定位或离靠近基站重新定位！"
								+ "\n-------------------------------"
								+ "\n当前时间:" + date + "\n当前基站:" + taskName
								+ "\n该基站经纬度:" + templateLeaveLongitude + ";"
								+ templateLeaveLatitude + "\n当前经纬度:" + longitude
								+ ";" + latitude
								+ "\n-------------------------------"
								+ "\n不能巡检！");
					}
					builder.setNegativeButton(getString(R.string.cancle),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dia,
										int which) {
									dia.dismiss();
								}
							}).show();

				}
				break;
			case XJ_HIDDEN:
				dialog.dismiss();
				Intent intent = new Intent(XjTmpActivity.this,
						YhHandleActivity.class);
				intent.putExtra("titleName", taskName + titleName +"上报隐患");
				intent.putExtra("longitude",longitude);
				intent.putExtra("latitude",latitude);
				intent.putExtra("site",taskName.substring(0,taskName.length()-9));
				intent.putExtra("professional","基站一体化.铁塔及天馈系统");
				startActivity(intent);
				ifOnReStart=false;
				break;
			case XJ_COMPLETE:
				dialog.dismiss();
				final View dialogview = inflater.inflate(R.layout.finishdialog,
						null);
				builder = new AlertDialog.Builder(XjTmpActivity.this);
				builder.setView(dialogview);
				builder.setPositiveButton(getString(R.string.sure),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dia, int which) {
								EditText et_finish = (EditText) dialogview
										.findViewById(R.id.et_finish);
								taskNote = et_finish.getText().toString();
								wd = new WaitDialog(XjTmpActivity.this,
										getString(R.string.dialog_upload_name));
								dialog = wd.getDialog();
								status = "3";
								UpLoadThread upThread = new UpLoadThread(
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
			default:
				break;
			}
		}
	};

	/**
	 * 计算两点间距离
	 * 
	 * @param lat1
	 *            纬度
	 * @param lng1
	 *            经度
	 * @param lat2
	 *            纬度
	 * @param lng2
	 *            经度
	 * @return 米
	 */
	private double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		System.out.println("lat1========" + lat1 + "......=" + lng1
				+ ",,,,,=lat2=" + lat2 + ",,,,,===lng2=" + lng2);
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378.137 * 1000;
		return s;
	}

	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// 到达、离开经纬度上传
	class ArriveThread extends Thread {
		private String longitude = null;
		private String latitude = null;

		private ArriveThread(String jingdu, String weidu) {
			this.longitude = jingdu;
			this.latitude = weidu;
		}

		public void run() {
			boolean falg = false;
			XJArriveImp xjArrive = new XJArriveImp();
			XMLUtil xmlArrive = xjArrive.getArrive(getApplicationContext(),
					taskId, longitude, latitude);
			falg = xmlArrive.getSuccess();
			if (xmlArrive.getIsLegal()) {
				if (falg) {
					handler.sendEmptyMessage(XJ_ARRIVE_TRUE);
				} else {
					handler.sendEmptyMessage(XJ_ARRIVE_FALSE);
				}
			} else {
				handler.sendEmptyMessage(XJ_ARRIVE_FALSE);
			}
		}
	}

	// 完成线程上传
	class UpLoadThread extends Thread {
		private String content;
		private String status;
		private String jingdu, weidu;

		private UpLoadThread(String editContent, String status, String jingdu,
				String weidu) {
			this.content = editContent;
			this.status = status;
			this.jingdu = jingdu;
			this.weidu = weidu;
		}

		@SuppressLint("SimpleDateFormat")
		public void run() {
			String zipFilePath = AutoUploadManager.getInstance()
					.getSDCardAttachmentPath(getApplicationContext());
			if (StringUtil.isEmpty(zipFilePath)) {
				zipFilePath = AutoUploadManager.getInstance()
						.getmemoryAttachmentPath(getApplicationContext());
			}
			File file = new File(zipFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String time = sDateFormat.format(new java.util.Date());
			File tempFile = null;
			try {
				tempFile = new File(picPathChild);
			} catch (Exception e1) {
			}
			int intPic = 0;
			int intRec = 0;
			if (tempFile != null && tempFile.exists() && tempFile.isDirectory()) {
				File[] tempFiles = tempFile.listFiles();
				for (int i = 0; i < tempFiles.length; i++) {
					File f = (File) tempFiles[i];
					String tempname = f.getName().toLowerCase();
					if (tempname != null
							&& tempname.length() > 4
							&& ".jpg".equals(tempname.substring(tempname
									.length() - 4))) {
						intPic++;
					} else if (tempname != null
							&& tempname.length() > 4
							&& ".amr".equals(tempname.substring(tempname
									.length() - 4))) {
						intRec++;
					}
				}
			}
			boolean falg = false;
			System.out.println("JINGDU===========" + jingdu + "WEIDU========"
					+ weidu);
			Set<Entry<String, FieldView>> set = sf.entrySet();
			for (Iterator<Map.Entry<String, FieldView>> it = set.iterator(); it
					.hasNext();) {
				Map.Entry<String, FieldView> entry = (Map.Entry<String, FieldView>) it
						.next();
				map.put(entry.getKey(), entry.getValue().getValue());
				// if (entry.getValue().getValue().trim().equals("")) {
				// Toast.makeText(getApplicationContext(), "不能为空，请重新填写！",
				// 0).show();
				// return;
				// }
			}
			XJUpServiceImp xjUp = new XJUpServiceImp();
			XMLUtil xml = xjUp.getXJUpLoad(getApplicationContext(), taskId,
					status, jingdu, weidu, "", content, map,"PT003");
			falg = xml.getSuccess();
			if (falg) {
				try {
					if (intPic > 0) {
						zipFile(picPathChild, zipFilePath + "/XJ_" + taskId
								+ "_PIC" + "_basestation_" + time + ".zip",
								".JPG");
					}
					if (intRec > 0) {
						zipFile(picPathChild, zipFilePath + "/XJ_" + taskId
								+ "_REC" + "_basestation_" + time + ".zip",
								".amr");
					}
					FileUtil.deleteFolder(picPathChild);// 删除源文件
					System.out.println("zip文件打包完毕  " + zipFilePath + "/XJ_"
							+ taskId + "_PIC_" + time + ".zip");
					System.out.println("zip文件打包完毕  " + zipFilePath + "/XJ_"
							+ taskId + "_REC_" + time + ".zip");
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(WHAT_UP_DATA_TRUE);

			} else {
				handler.sendEmptyMessage(WHAT_UP_DATA_FALSE);
			}
		}
	}

	/**巡检内容下载线程*/
	class XJThread extends Thread {
		private XJDataService xds = null;
		private XMLUtil ele;
		private String taskId;

		private XJThread(String taskId) {
			XJHandler = new Handler();
			this.taskId = taskId;
		}

		@Override
		public void run() {
			xds = new XJDataServiceImp();
			ele = xds.getXJData(getApplicationContext(), taskId,"PT002");
			xjFields = ele.getBaseFileds();
			System.out.println("xjFields================" + xjFields);
			xjItem = ele.getTude();
			System.out.println("纬度==============xjItem=" + xjItem + "///////"
					+ xjItem.getTaskResult() + ",,,," + xjItem.getLatitude()
					+ "纬度===" + xjItem.getLongitude());
			XJHandler.post(doneRunble);
		}

	}

	// 构建Runnable对象，在runnable中更新界面
	Runnable doneRunble = new Runnable() {
		@Override
		public void run() {
			// 更新界面
			dialog.dismiss();
			logic();
		}

	};
/**
 * 基站巡检中onclick事件
 */
	@Override
	public void onClick(View view) {
//		if (view.getId() != R.id.arrive_img && !ifStart && isW.equals("1")) {
//			Toast.makeText(getApplicationContext(), R.string.arrive_sure,
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
		switch (view.getId()) {
		case R.id.arrive_img:
			Boolean flagGps = GPS.isOPen(getApplicationContext());
			System.out.println("判断是否开启GPRS========" + flagGps);
			if (flagGps) {
				wd = new WaitDialog(this, getString(R.string.dialog_tude));
				dialog = wd.getDialog();
				myListener = new MyLocationListenner(getString(R.string.arrive));
				mLocClient = new LocationClient(getApplicationContext());
				mLocClient.registerLocationListener(myListener);
				mLocClient.requestLocation();
				mLocClient.start();
			} else {
				ifArrive = true;
				GPS.openGPS(XjTmpActivity.this);
			}
			changeColor();
			arrive_img.setBackgroundResource(R.drawable.xj_detail_oneon);
			break;
		case R.id.leave_img:
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				System.out.println("离开经度................");
				wd = new WaitDialog(this, getString(R.string.dialog_tude));
				dialog = wd.getDialog();
				myListener = new MyLocationListenner(getString(R.string.leave));
				mLocClient = new LocationClient(getApplicationContext());
				mLocClient.registerLocationListener(myListener);
				mLocClient.requestLocation();
				mLocClient.start();
				// TODO Auto-generated method stub
				changeColor();
				leave_img.setBackgroundResource(R.drawable.xj_detail_twoon);
			}
			break;
		case R.id.complete_img:
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				wd = new WaitDialog(this, getString(R.string.dialog_tude));
				dialog = wd.getDialog();
				myListener = new MyLocationListenner(
						getString(R.string.complete));
				mLocClient = new LocationClient(getApplicationContext());
				mLocClient.registerLocationListener(myListener);
				mLocClient.requestLocation();
				mLocClient.start();
				changeColor();
				complete_img
						.setBackgroundResource(R.drawable.xj_detail_threeon);
			}
			break;
		case R.id.photo_img:
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				// TODO Auto-generated method stub
				changeColor();
				photo_img.setBackgroundResource(R.drawable.xj_detail_cameraon);
				CreatDialog(0);
			}
			break;
		case R.id.hidden_img:
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				
				wd = new WaitDialog(this, getString(R.string.dialog_tude));
				dialog = wd.getDialog();
				myListener = new MyLocationListenner("隐患");
				mLocClient = new LocationClient(getApplicationContext());
				mLocClient.registerLocationListener(myListener);
				mLocClient.requestLocation();
				mLocClient.start();
				changeColor();
				hidden_img.setBackgroundResource(R.drawable.xj_detail_yhon);				
			}
			break;
		case R.id.recode_img:
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				changeColor();
				recode_img.setBackgroundResource(R.drawable.xj_detail_recordon);
				CreatDialog(2);
			}
			break;
		case R.id.iv_friends:
			finish();
			break;
		case R.id.camera:// 拍照
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			temporaryFile = new File(file, "ceshi.jpg");
			Uri u = Uri.fromFile(temporaryFile);
			intentFromCapture.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, u);
			startActivityForResult(intentFromCapture, 1);
			ifOnReStart=false;
			}
			break;
		case R.id.mic:// 录音
			if (!ifStart) {
				Toast.makeText(getApplicationContext(), R.string.arrive_sure,
						Toast.LENGTH_SHORT).show();
				return;
			} else {
			Intent intentFromMic = new Intent(this, MicActivity.class);
			intentFromMic.putExtra("baseId", taskId);
			intentFromMic.putExtra("baseSN", taskId);
			startActivityForResult(intentFromMic, 3);
			ifOnReStart=false;
			}
			break;
		case R.id.imgview_map_icon://进入地图查看当前位置与基站位置的线路
//			Intent intentFromMap=new Intent(this,MapViewActivity.class);
//			//传入地图从服务器中取出的基站的位置
//			String templateLatitude = xjItem.getLatitude();
//			String templateLongitude = xjItem.getLongitude();
//			intentFromMap.putExtra("latitude", templateLatitude);
//			intentFromMap.putExtra("longitude", templateLongitude);
//			startActivity(intentFromMap);
			if (flag) {//代表展示地图
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
				mapicon_img.setImageResource(R.drawable.icon_list);
				flag = false;
			} else {//代表展示列表
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
				mapicon_img.setImageResource(R.drawable.icon_map);
				flag = true;
			}
			
			ifOnReStart=false;
			break;
		default:
			break;

		// Intent intentFromCapture = new
		// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//
		// startActivityForResult(intentFromCapture, 1);

		}
	}

	@Override
	protected void onRestart() {
		Boolean flagGps = GPS.isOPen(getApplicationContext());
		if (flagGps && ifArrive&&ifOnReStart) {
			wd = new WaitDialog(this, getString(R.string.dialog_tude));
			dialog = wd.getDialog();
			myListener = new MyLocationListenner(getString(R.string.arrive));
			mLocClient = new LocationClient(getApplicationContext());
			mLocClient.registerLocationListener(myListener);
			mLocClient.requestLocation();
			mLocClient.start();
		} else if (!flagGps && ifArrive&&ifOnReStart) {
			Toast.makeText(XjTmpActivity.this, R.string.gps_ope_failed,
					Toast.LENGTH_SHORT).show();
		}
		super.onRestart();
	}

	/** 底部按钮颜色改变 */
	public void changeColor() {
		arrive_img.setBackgroundResource(R.drawable.xj_detail_one);
		leave_img.setBackgroundResource(R.drawable.xj_detail_two);
		complete_img.setBackgroundResource(R.drawable.xj_detail_three);
		recode_img.setBackgroundResource(R.drawable.xj_detail_record);
		photo_img.setBackgroundResource(R.drawable.xj_detail_camera);
		hidden_img.setBackgroundResource(R.drawable.xj_detail_yh);
	}

	public void CreatDialog(int id) {
		final String[] mItems = new String[titleList.size()];
		for (int i = 0; i < titleList.size(); i++) {
			mItems[i] = (String) titleList.get(i);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(
				XjTmpActivity.this);
		switch (id) {
		case 0:// 单选
			mSingleChoiceID = -1;
			builder.setIcon(R.drawable.icon);
			builder.setTitle(getString(R.string.photo_category));

			builder.setSingleChoiceItems(mItems, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							mSingleChoiceID = whichButton;
							// showDialog("你选择的id为" + whichButton + " , "
							// + mItems[whichButton]);
						}
					});
			builder.setPositiveButton(getString(R.string.sure),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							if (mItems.length > 0) {
								Intent intentFromCapture = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);
								temporaryFile = new File(file, "ceshi.JPG");
								Uri u = Uri.fromFile(temporaryFile);
								intentFromCapture.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT, u);
								startActivityForResult(intentFromCapture, 1);
							}
						}
					});
			builder.setNegativeButton(getString(R.string.cancle),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.create().show();
			break;

		case 1:
			MultiChoiceID.clear();
			builder.setIcon(R.drawable.icon);
			builder.setTitle(getString(R.string.information_proofread));
			builder.setMultiChoiceItems(mItems, new boolean[] { false, false,
					false, false, false, false },
					new DialogInterface.OnMultiChoiceClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton, boolean isChecked) {
							if (isChecked) {
								MultiChoiceID.add(whichButton);
								// showDialog("你选择的id为" + whichButton + " , "
								// + mItems[whichButton]);
							} else {
								MultiChoiceID.remove(whichButton);
							}

						}
					});
			builder.setPositiveButton(getString(R.string.sure),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
//							String str = "";
//							int size = MultiChoiceID.size();
//							for (int i = 0; i < size; i++) {
//								str += mItems[MultiChoiceID.get(i)] + ", ";
//							}
//							 showDialog("你选择的是" + str);
						}
					});
			builder.setNegativeButton(getString(R.string.cancle),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.create().show();
			break;
		case 2:

			if (state) {
				LayoutInflater inflater = LayoutInflater
						.from(getApplicationContext());
				view = inflater.inflate(R.layout.recode, null);
				builder.setTitle(getString(R.string.record));
				builder.setView(view);
				builder.setIcon(R.drawable.xj_detail_record);
				builder.setPositiveButton(getString(R.string.recode_start), onClickList);
				builder.setNegativeButton(getString(R.string.recode_end), onClickList);
				builder.create();
				builder.show();
			} else {
				Toast.makeText(XjTmpActivity.this, getString(R.string.SD_isNO), Toast.LENGTH_LONG)
						.show();
			}

			break;
		default:
			break;
		}

	}

	// 录音alterdialog
	DialogInterface.OnClickListener onClickList = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dia, int which) {
			if (which == -1 && radioFlag) {
				try {
					Field field = dia.getClass().getSuperclass()
							.getDeclaredField("mShowing");
					field.setAccessible(true);
					// 将mShowing变量设为false，表示对话框已关闭
					field.set(dia, false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stepTimeHandler = new Handler();
				startTime = System.currentTimeMillis();
				final TextView stepTimeTV = (TextView) view
						.findViewById(R.id.dia_time);
				mTicker = new Runnable() {
					public void run() {
						String content = showTimeCount(System
								.currentTimeMillis() - startTime);
						stepTimeTV.setText(content);
						long now = SystemClock.uptimeMillis();
						long next = now + (1000 - now % 1000);
						stepTimeHandler.postAtTime(mTicker, next);
					}
				};
				// 启动计时线程，定时更新
				mTicker.run();
				startRecording();
			}
			if (which == -2) {
				if (!radioFlag) {
					stopRecording();
					stepTimeHandler.removeCallbacks(mTicker);

					// 刷新图库
					ArrayList<String> newVoiceList = getVoiceList();
					voiceAdapter.setData(newVoiceList);
					voiceAdapter.notifyDataSetChanged();
				}
				try {
					Field field = dia.getClass().getSuperclass()
							.getDeclaredField("mShowing");
					field.setAccessible(true);
					// 将mShowing变量设为true，表示对话框未关闭
					field.set(dia, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	};
	

	// 计时器格式
	// 时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
	public String showTimeCount(long time) {
		if (time >= 360000000) {
			return "00:00:00";
		}
		String timeCount = "";
		long hourc = time / 3600000;
		String hour = "0" + hourc;
		hour = hour.substring(hour.length() - 2, hour.length());

		long minuec = (time - hourc * 3600000) / (60000);
		String minue = "0" + minuec;
		minue = minue.substring(minue.length() - 2, minue.length());

		long secc = (time - hourc * 3600000 - minuec * 60000) / 1000;
		String sec = "0" + secc;
		sec = sec.substring(sec.length() - 2, sec.length());
		timeCount = hour + ":" + minue + ":" + sec;
		return timeCount;
	}

	// 开始录音
	private void startRecording() {
		mRecorder = new MediaRecorder();
		// 设置音源为Micphone
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 设置封装格式
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String picDate = taskId + "_"
				+ sDateFormat.format(new java.util.Date());
		imgName = picPathChild + "/" + picDate + ".amr";// 文件地址名
		fileChild = new File(picPathChild);
		if (!fileChild.exists()) {
			fileChild.mkdirs();
		}
		mRecorder.setOutputFile(imgName);
		// 设置编码格式
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
		}
		mRecorder.start();
		radioFlag = false;
	}

	// 结束录音
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
		radioFlag = true;
	}

	/**
	 * 照相时需要的回调函数 彩华使用此回调函数
	 * 
	 * @param 照相传递过来的参数
	 * @param 拍照是否OK
	 * @param 拍照传递过来的intent
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {//底部按钮拍照
			if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), getString(R.string.photo_nostart),
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), getString(R.string.photo_sucess), Toast.LENGTH_SHORT)
					.show();
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String picDate = taskId + "_"
					+ sDateFormat.format(new java.util.Date());
			 // 减小内存
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// 获取资源图片
			try {
				String path = file + "/ceshi.jpg";
				InputStream is = new BufferedInputStream(new FileInputStream(
						path));
				photo = BitmapFactory.decodeStream(is, null, opt);// 减小内存
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			/** 对图片做压缩处理 */
			int bmpWidth = photo.getWidth();
			int bmpHeight = photo.getHeight();
			float scaleWidth = 320;
			float scaleHeight = 480;
			if (bmpHeight >= bmpWidth) {
				// 缩放图片的尺寸
				scaleWidth = (float) 320 / bmpWidth; // 目前分辨率大小写死 以后按需求再改
				scaleHeight = (float) 480 / bmpHeight;
			} else {
				// 缩放图片的尺寸
				scaleWidth = (float) 480 / bmpWidth; // 目前分辨率大小写死 以后按需求再改
				scaleHeight = (float) 320 / bmpHeight;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			// 产生缩放后的Bitmap对象
			Bitmap resizeBitmap = Bitmap.createBitmap(photo, 0, 0, bmpWidth,
					bmpHeight, matrix, false);
			photo.recycle();
			// picPathChild = picPath + "/" + taskId;
			imgName = picPathChild + "/" + picDate + ".JPG";
			fileChild = new File(picPathChild);
            if (!fileChild.exists()) {
				fileChild.mkdirs();
			}
			try {
				baos = new FileOutputStream(imgName);
				resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 把数据写入文件
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					baos.flush();
					baos.close();
					temporaryFile.delete();
					resizeBitmap.recycle();
					System.gc();
					// 刷新图库
					ArrayList<String> newList = getList();
					imageAdapter.setData(newList);
					imageAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// finally
		} else if (requestCode == 3) {//内容拍照按钮
			if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), getString(R.string.recode_nostart),
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), getString(R.string.recode_sucess), Toast.LENGTH_SHORT)
					.show();
			// 刷新图库
			ArrayList<String> newVoiceList = getVoiceList();
			voiceAdapter.setData(newVoiceList);
			voiceAdapter.notifyDataSetChanged();
		}
		ifArrive = false;
	}

	/**
	 * zip压缩功能. 压缩baseDir(文件夹目录)下所有文件，包括子目录
	 * 
	 * @throws Exception
	 */
	public static void zipFile(String baseDir, String fileName,
			String fileSuffix) throws Exception {
		List fileList = getSubFiles(new File(baseDir));
		System.out.println("fileList  大小===========" + fileList.size());
		ZipOutputStream zos = new ZipOutputStream(
				new FileOutputStream(fileName));
		// zos.setEncoding("GBK");
		ZipEntry ze = null;
		byte[] buf = new byte[BUFFER];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			String tempname = f.getName().toLowerCase();
			fileSuffix = fileSuffix.toLowerCase();
      	if (tempname != null && tempname.indexOf(fileSuffix) > 0
					&& tempname.indexOf(fileSuffix) == tempname.length() - 4) {
				ze = new ZipEntry(getAbsFileName(baseDir, f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				zos.putNextEntry(ze);
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, BUFFER)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
			}
		}
		zos.close();
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private static ArrayList getSubFiles(File baseDir) {
		ArrayList ret = new ArrayList();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		String bottomType;

		public MyLocationListenner(String type) {
			this.bottomType = type;
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				Toast.makeText(XjTmpActivity.this, getString(R.string.gettude_failed),
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				System.out.println("查看获取当前的地理类型============"
						+ location.getLocType());
				StringBuffer sb = new StringBuffer(256);
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				longitude = Double.toString(location.getLongitude());
				latitude = Double.toString(location.getLatitude());
				if (bottomType.equals(getString(R.string.arrive))) {
					handler.sendEmptyMessage(XJ_ARRIVE);
				}
				if (bottomType.equals(getString(R.string.leave))) {
					handler.sendEmptyMessage(XJ_LEAVE);
				}
				if (bottomType.equals(getString(R.string.complete))) {
					handler.sendEmptyMessage(XJ_COMPLETE);
				}
				if(bottomType.equals(getString(R.string.hidden))){
					handler.sendEmptyMessage(XJ_HIDDEN);
				}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				Toast.makeText(XjTmpActivity.this,
						getString(R.string.gettude_failed), Toast.LENGTH_SHORT)
						.show();
				return;
			} else {
				System.out.println("查看获取当前的地理类型============" + poiLocation);
				StringBuffer sb = new StringBuffer(256);
				sb.append("\nlatitude : ");
				sb.append(poiLocation.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(poiLocation.getLongitude());
				longitude = Double.toString(poiLocation.getLatitude());
				latitude = Double.toString(poiLocation.getLongitude());
				if (bottomType.equals(R.string.arrive)) {
					handler.sendEmptyMessage(XJ_ARRIVE);
				}
				if (bottomType.equals(R.string.leave)) {
					handler.sendEmptyMessage(XJ_LEAVE);
				}
			}
		}
	}

	/**
	 * gallery数据适配器
	 */
	public class ImageAdapter extends BaseAdapter {
		private Context context;
		public ArrayList<String> list;

		public ImageAdapter(Context c, ArrayList<String> list) {
			this.context = c;
			this.list = list;
		}

		private void setData(ArrayList<String> list) {
			this.list = list;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView img = null;
			Bitmap bitmap = null;
			if (convertView == null) {
				img = new ImageView(context);
				convertView = img;
				convertView.setTag(img);
			} else {
				img = (ImageView) convertView.getTag();
			}
			// 减小内存
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			// 获取资源图片
			try {
				String path = picPathChild + "/" + list.get(position);
				System.out.println("path==========" + path);
				InputStream is = new BufferedInputStream(new FileInputStream(
						path));
				bitmap = BitmapFactory.decodeStream(is, null, opt);// 减小内存
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Bitmap bitmap = BitmapFactory.decodeFile(path);最好不用
			// img.setImageResource(mImages[position]);
			img.setImageBitmap(bitmap);
			img.setLayoutParams(new Gallery.LayoutParams(192, 192));
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			return convertView;
		}

	}

	/**
	 * 录音适配器
	 * 
	 * @author qch
	 * 
	 */
	class VoiceAdapter extends BaseAdapter {
		public ArrayList<String> list;

		public VoiceAdapter(Context cotext, ArrayList<String> list) {
			this.list = list;
		}

		public void setData(ArrayList<String> list) {
			this.list = list;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("null")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.recordbutton, null);
				holder = new ViewHolder();
				holder.imgBackgroud = (ImageView) view
						.findViewById(R.id.imgview_recordbutton_bg);
				holder.tvRecordName = (TextView) view
						.findViewById(R.id.textview_recordbutton_recordname);
				view.setTag(holder);
			} else {
				holder.tvRecordName = (TextView) view.getTag();
			}
			// 获取音频文件
			holder.tvRecordName.setText(getString(R.string.record) + (position + 1));
			return view;
		}

	}

	// 录音的控件
	class ViewHolder {
		TextView tvRecordName;
		ImageView imgBackgroud;
	}

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
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
			double longitude = aLocation.getLongitude();
			double latitude = aLocation.getLatitude();
//			System.out.println("当前经度"+longitude+"::纬度"+latitude);
			LatLng position = new LatLng(latitude,longitude);// 当前位置，定位的经纬度
		    System.out.println("xjItem======================="+xjItem);
		    String jzlatitude=null;
		    String jzlongtitude = null;
            if(xjItem!=null){
			jzlatitude = xjItem.getLatitude();
			jzlongtitude = xjItem.getLongitude();
            
			if(jzlatitude!=null&&jzlongtitude!=null){
			jzlatitude1=Double.parseDouble(jzlatitude);
			jzlongtitude1=Double.parseDouble(jzlongtitude);
			}
			LatLng jzposition = new LatLng(jzlatitude1,jzlongtitude1);//基站的经纬度
		
			List mapPointList=new ArrayList();
			mapPointList.add(position);
			mapPointList.add(jzposition);
			aMap.addPolyline((new PolylineOptions())
					.addAll(mapPointList).color(Color.RED)
					.width(5));
			//清除点的缓存
			for(int i=0;i<mapPointList.size();i++){
				mapPointList.remove(i);
			}
			// 对地图添加一个marker
			defaultMarker = aMap.addMarker(new MarkerOptions()
			.position(position).title("0").snippet("00")
			.icon(BitmapDescriptorFactory.defaultMarker()));
			aMap.addMarker(new MarkerOptions()
			.position(jzposition).title("1").snippet("11")
			.icon(BitmapDescriptorFactory.defaultMarker()));
			aMap.getUiSettings().setZoomControlsEnabled(true);// 设置系统默认缩放按钮可见
            }
//			aMap.setOnMarkerClickListener(this);// 对marker添加点击监听器
			
		}
	}


}
