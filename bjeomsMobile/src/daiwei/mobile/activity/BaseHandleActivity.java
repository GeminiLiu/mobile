package daiwei.mobile.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import eoms.mobile.R;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.animal.Item;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.AutoUploadManager;
import daiwei.mobile.service.GetActionService;
import daiwei.mobile.service.GetActionServiceImp;
import daiwei.mobile.service.GetTreeService;
import daiwei.mobile.service.GetTreeServiceImp;
import daiwei.mobile.service.SubmitService;
import daiwei.mobile.service.SubmitServiceImp;
import daiwei.mobile.util.DateTimePickDialogUtil;
import daiwei.mobile.util.FileUtil;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.TestAnalyticXML;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.Dic;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.TemplateRT;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;


/**
 * 提交工单页面：拍照，处理，上传等
 * 
 * @author 都 3/25
 */
@SuppressLint("HandlerLeak")
public class BaseHandleActivity extends Activity implements OnClickListener,
		OnTouchListener {
	public int detailId = R.layout.gd_modle;
	private LayoutInflater inflater;
	private LinearLayout linearLayout;
	private Map<String, Dic> dicMap;
	private String baseId;
	private String category;
	private String taskId;
	private String actionId;
	private String actionCode;
	private String actionText;
	private String baseSN;
	private Map<String, FieldView> sf = new HashMap<String, FieldView>();
	private Map<String, String> map;
	private XMLUtil.FieldVO filedVO;
	private Map<String,Long> requiredFieldMap;//字段是否必填
	private ProgressDialog progressDialog = null;
	public static final int SUBMIT_ERROR = 10;
	public static final int SUBMIT_SUCCESS = 11;
	public static final int OFF_LINE_SUCCESS = 12;
	private Button button;
	// private String value = null;
	private Bitmap photo = null;
	private boolean state = true;//sd卡是否存在
	private Item item = new Item();
	private Item parentItem = null;//父节点
	private LinearLayout ll_title = null;
	private View view_title = null;
	private ImageView iv_fiends = null;
	private TextView tv_title = null;
	private ActionButton actionButton = null;
	private Templet templet = null;
	private Intent intent = null;
	private GetActionService getActionService = null;
	private XMLUtil xmlUtil = null;
	private String cityID = "";
	private String specialtyID = null;
	private List<Field> listField = null;
	private LinearLayout layout_bottom = null;
	private LinearLayout layout_foot = null;
	private File sd, file, imgFile = null;
	private String imgName, picPath, picPathChild;
	private FileOutputStream baos;
	private static final int BUFFER = 1024 * 1024;// 缓存大小

	private LinearLayout ll_friends;// 整个布局
	private LinearLayout ll_sv;// 整个布局
	private boolean radioFlag = true;
	private MediaRecorder mRecorder;
	private ProgressDialog dialog = null;
	private WaitDialog wd = null;
	private Handler handler;
	private View dia_view;
	private Handler stepTimeHandler;
	private Runnable mTicker;
	private long startTime = 0;
	private int site=0;

	// gallery
	private Gallery gallery;
	private ArrayList<String> picList;// 图片列表
	private ImageAdapter adapter;

	private Gallery voiceGallery;
	private ArrayList<String> voiceList;// 声音列表
	private VoiceAdapter voiceAdapter;

	private ImageButton imgCamera;
	private ImageButton imgMic;
	private LinearLayout ll_camera;
	private LinearLayout ll_mic;
	int mSingleChoiceID = -1;
	
	private Handler fieldHandler;

	// private ImageButton imagePhoto;//右上角文件夹
	// private ImageButton imageRecord;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_modle);//R.layout.gd_modle
		MyData.activityHashMap.put("BaseHandleActivity", this);// 加入集合
		MyData.add(BaseHandleActivity.this);
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
		/**
		 * 消息处理
		 */
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
				if (list.size() != 0) {
					map = list.get(0);// 获取map集合
					init();
				}

				dialog.dismiss();
				super.handleMessage(msg);
			}

		};

		findViewById();
		getIntentData();//获取前一个activity传递的参数，以及加载底部动作按钮
		getData();// 获取动作处理表单字段默认值数据
		initImage();//加载拍照列表
		initRecord();//加载录音列表

	}

	private void initRecord() {
		picPathChild = picPath + "/" + baseSN;// 工单号对应下的文件
		//picPathName = picPath + "/" + baseSN;
		voiceList = getVoiceList();
		voiceGallery = (Gallery) findViewById(R.id.voicegallery);
		voiceAdapter = new VoiceAdapter(BaseHandleActivity.this, voiceList);
		voiceGallery.setAdapter(voiceAdapter);
		voiceGallery.setOnItemClickListener(voiceListener);

	}

	private void initImage() {
		picPathChild = picPath + "/" + baseSN;// 工单号对应下的文件
		//picPathName = picPath + "/" + baseSN;
		picList = getList();
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setSelection(picList.size() / 2);
		adapter = new ImageAdapter(BaseHandleActivity.this, picList);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(listener);
		imgCamera = (ImageButton) findViewById(R.id.camera);
		imgMic = (ImageButton) findViewById(R.id.mic);
		ll_camera = (LinearLayout) findViewById(R.id.ll_camera);
		ll_mic = (LinearLayout) findViewById(R.id.ll_mic);

		imgCamera.setOnClickListener(this);
		imgMic.setOnClickListener(this);

	}

	public void findViewById() {
		linearLayout = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ll_title = (LinearLayout) findViewById(R.id.gd_layout1);
		view_title = inflater.inflate(R.layout.gd_layout1, null, true);
		ll_title.addView(view_title);
		iv_fiends = (ImageView) view_title.findViewById(R.id.iv_friends);
		tv_title = (TextView) view_title.findViewById(R.id.tv_title);
		// imagePhoto = (ImageButton)
		// view_title.findViewById(R.id.photo);//右上角文件夹
		// imageRecord = (ImageButton) view_title.findViewById(R.id.record);
		// imagePhoto.setOnClickListener(this);
		// imagePhoto.setVisibility(View.GONE);
		// imageRecord.setOnClickListener(this);

		layout_bottom = (LinearLayout) findViewById(R.id.gd_layout3);
		layout_foot = (LinearLayout) inflater.inflate(R.layout.footbg, null,
				true);
		layout_bottom.addView(layout_foot);
		iv_fiends.setOnClickListener(this);
		ll_friends = (LinearLayout) findViewById(R.id.ll_friends);
		ll_sv = (LinearLayout) findViewById(R.id.ll_sv);

		linearLayout.setOnTouchListener(this);
		ll_friends.setOnTouchListener(this);
		ll_sv.setOnTouchListener(this);
	}

	public void getIntentData() {
		intent = this.getIntent();
		tv_title.setText(intent.getStringExtra("title"));
		actionButton = (ActionButton) intent.getSerializableExtra("field");
		cityID = actionButton.getCityID();// 地市
		actionText = actionButton.getName();// 获取按钮名字
		specialtyID = actionButton.getSpecialtyID();// 专业
		listField = actionButton.getField();//下一步操作要显示的表单字段信息
		templet = (Templet) intent.getSerializableExtra("tmp");//用户登录时存储的模板信息
		dicMap = templet.getDicDefine();// 获取字典
		baseId = intent.getStringExtra("baseId");
		category = intent.getStringExtra("category");
		taskId = intent.getStringExtra("taskId");
		actionId = intent.getStringExtra("actionId");
		actionCode = intent.getStringExtra("actionCode");
		baseSN=intent.getStringExtra("baseSN");
		System.out.println("baseSN============"+baseSN);
		addActionButton(layout_foot, intent.getStringExtra("title"));
	}

	/**
	 * 开启线程获取数据
	 */
	public void getData() {
		wd = new WaitDialog(BaseHandleActivity.this, "数据加载中...");
		dialog = wd.getDialog();
		new Thread() {
			@Override
			public void run() {
				getActionService = new GetActionServiceImp();// 加载动作界面接口
				//获取字段的默认值
				xmlUtil = getActionService.getData(getApplicationContext(),
						baseId, category, taskId, actionId, actionCode);
				List<Map<String, String>> list = xmlUtil.getList();// 获取数据
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);
				// map = xmlUtil.getList().get(0); //
				// 获取field字段list集合中的第一条：Map<String,String>
				super.run();
			}

		}.start();
	}
	/**
	 * 开启线程获取数据
	 */
	public void getFieldData() {
		wd = new WaitDialog(BaseHandleActivity.this, "数据加载中...");
		dialog = wd.getDialog();
		new Thread() {
			@Override
			public void run() {
				getActionService = new GetActionServiceImp();// 加载动作界面接口
				xmlUtil = getActionService.getData(getApplicationContext(),
						baseId, category, taskId, actionId, actionCode);
				List<XMLUtil.FieldVO> fieldInfoList = xmlUtil.getFieldInfoList();

				Message msg = new Message();
				msg.obj = fieldInfoList;
				handler.sendMessage(msg);
				// map = xmlUtil.getList().get(0); //
				// 获取field字段list集合中的第一条：Map<String,String>
				super.run();
			}

		}.start();
	}
	public void init() {
		/** 添加动作按钮 */
		if (actionButton.getPhoto() != null) {
			if (actionButton.getPhoto().equals("1")) {
				addPhotoButton(layout_foot);// 添加拍照按钮
				ll_camera.setVisibility(View.VISIBLE);
			}
			if (actionButton.getRadio().equals("1")) {
				addRadioButton(layout_foot);// 添加录音按钮
				ll_mic.setVisibility(View.VISIBLE);
			}
		}
		for (int i = 0; i < listField.size(); i++) {
			Field field = listField.get(i);// 获取每一个Field对象
			if (field.getType().equals("STRING")) {
				addEditText(field, false, 0);
			} else if (field.getType().equals("INT")) {
				addEditText(field, false, 1);
			} else if (field.getType().equals("TEXTAREA")) {
				addEditText(field, true, 2);
			} else if (field.getType().equals("FLOAT")) {
				addEditTextFloat(field);
			} else if (field.getType().equals("TREE")) {// 代表文件夹 转派——代维小组
				cityID = "";
				addTree(field, cityID, specialtyID);
			}else if(field.getType().equalsIgnoreCase("ORGTREE")){
				cityID = "";
				addTree(field, cityID, specialtyID);
			} else if (field.getType().equals("SELECT")) {
				addSpiner(field);
			} else if (field.getType().equals("TIME")) {
				addTime(field);
			}
		}
	}
	
	public void initField() {
		/** 添加动作按钮 */
		if (actionButton.getPhoto() != null) {
			if (actionButton.getPhoto().equals("1")) {
				addPhotoButton(layout_foot);// 添加拍照按钮
				ll_camera.setVisibility(View.VISIBLE);
			}
			if (actionButton.getRadio().equals("1")) {
				addRadioButton(layout_foot);// 添加录音按钮
				ll_mic.setVisibility(View.VISIBLE);
			}
		}
		for (int i = 0; i < listField.size(); i++) {
			Field field = listField.get(i);// 获取每一个Field对象
			if (field.getType().equals("STRING")) {
				addEditText(field, false, 0);
			} else if (field.getType().equals("INT")) {
				addEditText(field, false, 1);
			} else if (field.getType().equals("TEXTAREA")) {
				addEditText(field, true, 2);
			} else if (field.getType().equals("FLOAT")) {
				addEditTextFloat(field);
			} else if (field.getType().equals("TREE")) {// 代表文件夹 转派——代维小组
				addTree(field, cityID, specialtyID);
			} else if (field.getType().equals("SELECT")) {
				addSpiner(field);
			} else if (field.getType().equals("TIME")) {
				addTime(field);
			}
		}
	}

	/**
	 * 增加录音功能
	 * @param radioLayout
	 */
	private void addRadioButton(LinearLayout radioLayout) {

		View view = inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
		TextView tv = (TextView) view.findViewById(R.id.bottom_tv);
		tv.setText("录音");
		// imageRecord.setVisibility(view.VISIBLE);
		//隐藏掉显示工单数目的图标
		TextView tvNum = (TextView)view.findViewById(R.id.msg_num_tv);
		tvNum.setVisibility(View.GONE);
		radioLayout.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state) {
					if (radioFlag) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BaseHandleActivity.this);
						LayoutInflater inflater = LayoutInflater
								.from(getApplicationContext());
						dia_view = inflater.inflate(R.layout.recode, null);
						builder.setTitle("录音");
						builder.setView(dia_view);
						builder.setIcon(R.drawable.xj_detail_record);
						builder.setPositiveButton("开始录音", onClickList);
						builder.setNegativeButton("结束录音", onClickList);
						builder.create();
						builder.show();
					}
				} else {
					Toast.makeText(BaseHandleActivity.this, "请插入SD卡",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	// 录音alterdialog
	DialogInterface.OnClickListener onClickList = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == -1 && radioFlag) {
				try {
					java.lang.reflect.Field field = dialog.getClass()
							.getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					// 将mShowing变量设为false，表示对话框已关闭
					field.set(dialog, false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stepTimeHandler = new Handler();
				startTime = System.currentTimeMillis();
				final TextView stepTimeTV = (TextView) dia_view
						.findViewById(R.id.dia_time);
				mTicker = new Runnable() {
					public void run() {
						String content = showTimeCount(System
								.currentTimeMillis() - startTime);
						System.out.println("content==========" + content);
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
					java.lang.reflect.Field field = dialog.getClass()
							.getSuperclass().getDeclaredField("mShowing");
					field.setAccessible(true);
					// 将mShowing变量设为true，表示对话框未关闭
					field.set(dialog, true);
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
	@SuppressLint("SimpleDateFormat")
	private void startRecording() {
		mRecorder = new MediaRecorder();
		// 设置音源为Micphone
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		// 设置封装格式
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String picDate = baseSN + "_"
				+ sDateFormat.format(new java.util.Date());
		imgName = picPathChild + "/" + picDate + ".amr";// 文件地址名
		File fileChild = new File(picPathChild);
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
	 * 增加拍照按钮
	 * 
	 * @param lnyt
	 */
	private void addPhotoButton(LinearLayout ll) {
		View view = inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
		TextView tv = (TextView) view.findViewById(R.id.bottom_tv);
		tv.setText("拍照");
		// imagePhoto.setVisibility(View.VISIBLE);
		//隐藏掉显示工单数目的图标
		TextView tvNum = (TextView)view.findViewById(R.id.msg_num_tv);
		tvNum.setVisibility(View.GONE);
		
		ll.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state) {
					try {
						Intent intentFromCapture = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						imgFile = new File(file, "ceshi.JPG");
						Uri u = Uri.fromFile(imgFile);
						intentFromCapture.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, u);
						startActivityForResult(intentFromCapture, 1);
					} catch (Exception e) {
						Toast.makeText(BaseHandleActivity.this, "没有找到储存目录",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(BaseHandleActivity.this, "请插入SD卡",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 增加时间的控件
	 * 
	 * @param field
	 */
	private void addTime(Field field) {
		View view = inflater.inflate(R.layout.edittext, null, true);
		TextView tv_editText = (TextView) view.findViewById(R.id.tv_editText);
		tv_editText.setText(field.getText());
		linearLayout.addView(view);
		final EditText startDateTime = (EditText) view
				.findViewById(R.id.et_editText);
		final String value = map.get(field.getId());// 获取map中的值
		System.out.println("value========" + value);
		if (value != null) {
			// value = StringToLongToString(value);
			startDateTime.setText(StringToLongToString(value));
		}
		/**
		 * 日期焦点改变的监听 //获取到焦点时隐藏软键盘
		 */
		startDateTime.setOnFocusChangeListener(new OnFocusChangeListener() {// 焦点改变你的监听事件
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {// 获取到焦点时隐藏软键盘
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									startDateTime.getWindowToken(), 0);
							startDateTime.setInputType(InputType.TYPE_NULL);// 不可以手动录入时间
							DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(
									BaseHandleActivity.this,
									StringToLongToString(value));
							dateTimePickDialogUtil
									.dateTimePicKDialog(startDateTime);
						}
					}
				});
		startDateTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(
						BaseHandleActivity.this, value);
				System.out.println("value===========" + value);
				dateTimePickDialogUtil.dateTimePicKDialog(startDateTime);
			}
		});
		FieldView fieldView = new FieldView();
		fieldView.setType("TIME");
		fieldView.setView(startDateTime);
		sf.put(field.getId(), fieldView);
	}

	/**
	 * 转化日期的工具类
	 * 
	 * @param searcherInput
	 * @return
	 */
	private String StringToLongToString(String searcherInput) {
		long lgTime = 0;
		Boolean judge = false;// 代表不能转成long
		try {
			Integer.parseInt(searcherInput);
			searcherInput += "000";
			judge = true;
		} catch (NumberFormatException e) {
			judge = false;
		}
		if (judge == true) {
			lgTime = Long.parseLong(searcherInput);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
			searcherInput = sdf.format(lgTime);
		}
		return searcherInput;
	}

	/**
	 * 增加editText 浮点型
	 */
	private void addEditTextFloat(Field field) {
		View view = inflater.inflate(R.layout.edittext_float, null);
		TextView tv_editText = (TextView) view.findViewById(R.id.tv_editText);
		tv_editText.setText(field.getText());
		EditText et = (EditText) view.findViewById(R.id.et_editText);
		String value = map.get(field.getId());
		if (value != null) {
			et.setText(value);
		}
		linearLayout.addView(view);
		FieldView fieldView = new FieldView();
		fieldView.setType("FLOAT");
		fieldView.setView(et);
		sf.put(field.getId(), fieldView);
	}

	/**
	 * 增加文本
	 * 
	 * @param field
	 *            字段
	 * @param b
	 *            布尔值 是否多行
	 * @param inputtype
	 *            输入类型
	 */
	private void addEditText(final Field field, Boolean b, int inputtype) {
		View view = inflater.inflate(R.layout.edittext, null, true);
		TextView tv_editText = (TextView) view.findViewById(R.id.tv_editText);
		System.out.println("field.getText()==========" + field.getText());
		tv_editText.setText(field.getText());
		final EditText et = (EditText) view.findViewById(R.id.et_editText);
		if (b) {
			int row = Integer.valueOf(field.getRow());
			et.setLines(row);
			et.setGravity(Gravity.TOP | Gravity.LEFT);

		}
		if (inputtype == 1) {
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else if (inputtype == 2) {
			// et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		} else if (inputtype == 3) {
		}
		String value = map.get(field.getId());//获取字段默认值
		if (value != null)
			et.setText(value);
		Button templateBtn = (Button) view.findViewById(R.id.template_btn);
		if (field.getTemplate() != null) {//工单有模板时，引入模板中的内容
			templateBtn.setVisibility(View.VISIBLE);
			final HashMap<String, TemplateRT> mapRt = this.templet
					.getTemplatestore().get(field.getTemplate());
			templateBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					final ArrayList<String> titleList = new ArrayList<String>();
					Iterator<String> it = mapRt.keySet().iterator();
					while (it.hasNext()) {
						String option = it.next();
						String[] param = option.split("=");
						String str = sf.get(param[0]) == null ? map
								.get(param[0]) : sf.get(param[0]).getValue();
						if (str == null && param[0].equals("BaseDwItemID"))
							str = specialtyID;
						if (str.equals(param[1])) {
							ArrayList<String> contents = mapRt.get(option)
									.getContent();
							for (int i = 0; i < contents.size(); i++) {
								String content = contents.get(i);
								int startIndex = content.indexOf("$");
								if (startIndex == -1){
									titleList.add(content);
									continue;
								}
									

								int endIndex = content
										.substring(startIndex + 1).indexOf("$");
								String substr = content.substring(
										startIndex + 1, startIndex + 1
												+ endIndex);
								content = content.replace("$" + substr + "$",
										sf.get(substr).getValue());
								titleList.add(content);
							}
						}
					}
					View layout = LayoutInflater.from(getApplicationContext())
							.inflate(R.layout.alert_listview, null);
					ListView alterList = (ListView) layout
							.findViewById(R.id.alter_list);
					System.out.println("@@@@@@@@@@@titleList============="
							+ titleList.size());
					final AlterAdapter alterAdapter = new AlterAdapter(titleList,
							getApplicationContext(),site);
					alterList.setAdapter(alterAdapter);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							BaseHandleActivity.this);
					mSingleChoiceID = -1;
					builder.setIcon(R.drawable.icon_small);
					builder.setTitle(field.getText());
					builder.setView(layout);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									if (titleList.size() > 0) {
										whichButton = alterAdapter.getAlterPosition();
										et.setText(titleList.get(whichButton));
									}
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							});
					builder.create().show();

				}
			});
		}

		linearLayout.addView(view);
		FieldView fieldView = new FieldView();
		fieldView.setType("STRING");
		fieldView.setView(et);
		sf.put(field.getId(), fieldView);
	}
	/**
	 * 获取派发树 并调用接口T002
	 * 
	 * @param field
	 *           showTeam 是否显示小组
	 *            showPerson 是否显示人员 multi 是否允许多选 selectObjs 可选择的项
	 */
	private void addOrgTree(Field field) {
		View view = inflater.inflate(R.layout.spinnerbutton, null, true);
		TextView tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
		tv_spinner.setText(field.getText());
		button = (Button) view.findViewById(R.id.button_reason);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 传递对象orgItemList到DialogActivity
				Intent intent = new Intent(getApplicationContext(),
						OrgTreeDialogActivity.class);
				startActivityForResult(intent, 6);
			}
		});
		linearLayout.addView(view);
		FieldView fv = new FieldView();
		fv.setType("ORGTREE");
		fv.setActionCode(actionCode);
		fv.setView(button);
		sf.put(field.getId(), fv);
	}
	private void addTreeOld(Field field, String cityID, String specialtyID) {
		String showCorp = field.getCorp();
		String showCenter = field.getCenter();
		String showStation = field.getStation();
		String showTeam = field.getTeam();
		String showPerson = field.getPerson();
		String multi = field.getMulti();
		String selectObjs = field.getSelect();
		GetTreeService tree = new GetTreeServiceImp();// 调用T001接口 获取派发树
		String strTree = tree.getTree(
				BaseHandleActivity.this.getApplicationContext(), showCorp,
				showCenter, showStation, showTeam, showPerson, multi,
				selectObjs, cityID, specialtyID);
		System.out.println("返回派发树：" + strTree);
		item = TestAnalyticXML.domParser(strTree);

		View view = inflater.inflate(R.layout.spinnerbutton, null, true);
		TextView tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
		tv_spinner.setText(field.getText());
		button = (Button) view.findViewById(R.id.button_reason);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(getApplicationContext(), "弹出对话框",0).show();
				// 传递对象Item到DialogActivity
				Intent intent = new Intent(getApplicationContext(),
						DialogActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("item", item);
				intent.putExtras(mBundle);
				startActivityForResult(intent, 2);
			}
		});
		linearLayout.addView(view);
		FieldView fv = new FieldView();
		fv.setType("TREE");
		fv.setActionCode(actionCode);
		fv.setView(button);
		sf.put(field.getId(), fv);
	}
	/**
	 * 获取派发树 并调用接口
	 * 
	 * @param field
	 *            showCorp 是否显示公司 showCenter 是否显示中心 showStation 是否显示驻点 showTeam
	 *            是否显示小组 showPerson 是否显示人员 multi 是否允许多选 selectObjs 可选择的项
	 */
	private void addTree(Field field, String cityID, String specialtyID) {
		String showCorp = field.getCorp();
		String showCenter = field.getCenter();
		String showStation = field.getStation();
		String showTeam = field.getTeam();
		String showPerson = field.getPerson();
		String multi = field.getMulti();
		String selectObjs = field.getSelect();
		GetTreeService tree = new GetTreeServiceImp();// 调用T001接口 获取派发树
//		String strTree = "";
		String strTree = tree.getTree(
				BaseHandleActivity.this.getApplicationContext(), showCorp,
				showCenter, showStation, showTeam, showPerson, multi,
				selectObjs, cityID, specialtyID);
		System.out.println("返回派发树："+strTree);
//		item = TestAnalyticXML.domParser(strTree);
		/********************用服务端返回的 xml文件 进行测试 *****************************/
//		String fileName = "aa.xml";
//		strTree = getTestTree(fileName);
		
       /*********************************上面注释回头删掉*******************/
		 System.out.println("strTree================="+strTree);
		item.setType(selectObjs);
		TestAnalyticXML.getAllItem(item,parentItem,strTree);
		
		View view = inflater.inflate(R.layout.spinnerbutton, null, true);
		TextView tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
		tv_spinner.setText(field.getText());
		button = (Button) view.findViewById(R.id.button_reason);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(getApplicationContext(), "弹出对话框",0).show();
				// 传递对象Item到DialogActivity
				Intent intent = new Intent(getApplicationContext(),
						DialogActivity.class);
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("item", item);
				intent.putExtras(mBundle);
				startActivityForResult(intent, 2);
			}
		});
		linearLayout.addView(view);
		FieldView fv = new FieldView();
		fv.setType("TREE");
		fv.setActionCode(actionCode);
		fv.setView(button);
		sf.put(field.getId(), fv);
	}
   public String getTestTree(String fileName){
//		String fileName = "aa.xml"; //文件名字
	   String strTree = "";
		try{ 

		   InputStream in = getResources().getAssets().open(fileName);

		   // \Test\assets\yan.txt这里有这样的文件存在

		   int length = in.available();         

		byte [] buffer = new byte[length];        

		in.read(buffer);            

		strTree = EncodingUtils.getString(buffer, "UTF-8");     
		}catch(Exception e){ 
		      e.printStackTrace();         
		}
		return strTree;
   }
	private void addSpiner(Field field) {
		Dic dic = dicMap.get(field.getDic());// 获取字典
		Map<String, String> options = dic.getOptions();
		ArrayList<CItem> list = new ArrayList<CItem>();
		Iterator<String> it = options.keySet().iterator();
		int i = 0;
		int r = 0;
		while (it.hasNext()) {
			String option = it.next();
			list.add(new CItem(option, options.get(option)));
			if(map != null && map.get(field.getId()) != null){
				if(map.get(field.getId()).equals(option))
					r = i;
			}
			i++;
			
		}
		View view = inflater.inflate(R.layout.spinner, null, true);
		TextView tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
		tv_spinner.setText(field.getText());
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner_reason);
		spinnerList(spinner, list);
		linearLayout.addView(view);
		spinner.setSelection(r);
		FieldView fv = new FieldView();
		fv.setType("SELECT");
		fv.setView(spinner);
		sf.put(field.getId(), fv);
	}

	/**
	 * 照相、派发树、录音需要的回调函数
	 * 
	 * @param 照相传递过来的参数
	 * @param 拍照是否OK
	 * @param 拍照传递过来的intent
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && requestCode == 2) {
			Item item = (Item) data.getSerializableExtra("name");// 获取对象
			button.setText(item.getText());
			button.setTag(item);
		} else if (requestCode == 1) {
			// 这里永远都不是null
			// if (uri == null) {
			// Toast.makeText(getApplicationContext(), "拍照失败",
			// Toast.LENGTH_SHORT).show();
			// return;
			// }			
			if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "还未拍照",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), "拍照成功", Toast.LENGTH_SHORT)
					.show();
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String picDate = baseSN + "_"
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
			/** 对图片做处理 */
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
			imgName = picPathChild + "/" + picDate + ".JPG";
			File fileChild = new File(picPathChild);
			if (!fileChild.exists()) {
				fileChild.mkdirs();
			}
			try {
				baos = new FileOutputStream(imgName);
				resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 把数据写入文件
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					baos.flush();
					baos.close();
					//File ceshiImg=new File();
					resizeBitmap.recycle();
					imgFile.delete();					
					System.gc();
					// 刷新图库
					ArrayList<String> newList = getList();
					adapter.setData(newList);
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}// finally

		} else if (requestCode == 3) {
			if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "还未录音",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), "录音成功", Toast.LENGTH_SHORT)
					.show();
			// 刷新图库
			ArrayList<String> newVoiceList = getVoiceList();
			voiceAdapter.setData(newVoiceList);
			voiceAdapter.notifyDataSetChanged();
		}
	}

	private void spinnerList(Spinner spinner, List<CItem> cm) {
		ArrayAdapter<CItem> choiceAdapter = new ArrayAdapter<CItem>(
				getApplicationContext(), R.layout.myspinner, cm);
		choiceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setPrompt("请选择");
		spinner.setAdapter(choiceAdapter);
		final Map<String, String> map = new HashMap<String, String>();
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				String ids = ((CItem) spinner.getSelectedItem()).getID();
				String value = ((CItem) spinner.getSelectedItem()).getValue();
				map.put(ids, value);
//				 list.add(0, map);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	/**
	 * zip压缩功能. 压缩baseDir(文件夹目录)下所有文件，包括子目录
	 * 
	 * @throws Exception
	 */
	public static void zipFile(String baseDir, String fileName,
			String fileSuffix) throws Exception {
		List fileList = getSubFiles(new File(baseDir));
		ZipOutputStream zos = new ZipOutputStream(
				new FileOutputStream(fileName));
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
	private static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
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
	 * 动作按钮 提交验证
	 * 
	 * @param ll
	 *            按钮所在布局
	 * @param str按钮文字
	 */
	private void addActionButton(LinearLayout ll, final String str) {
		View view = inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
		TextView tv = (TextView) view.findViewById(R.id.bottom_tv);
		TextView tvNum = (TextView)view.findViewById(R.id.msg_num_tv);
		tvNum.setVisibility(View.GONE);//将显示工单数目的view隐藏
		tv.setText(str);
		ll.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Set<Entry<String, FieldView>> set = sf.entrySet();
				for (Iterator<Map.Entry<String, FieldView>> it = set.iterator(); it
						.hasNext();) {
					Map.Entry<String, FieldView> entry = (Map.Entry<String, FieldView>) it
							.next();
					map.put(entry.getKey(), entry.getValue().getValue());
					//判断哪些为必填项，需要根据字段设置的是否必填进行判断       有待完善==xiaxj========================
					if (entry.getValue().getValue().trim().equals("")) {
						Toast.makeText(getApplicationContext(), "不能为空，请重新填写！",
								0).show();
						return;
					}
					//=====================
				}
				progressDialog = ProgressDialog.show(BaseHandleActivity.this,
						"请稍等...", "提交数据中...", true);
				new Thread() {// 开启子线程提交数据
					public void run() {
						String zipFilePath = AutoUploadManager.getInstance()
								.getSDCardAttachmentPath(
										getApplicationContext());
						if (StringUtil.isEmpty(zipFilePath)) {
							zipFilePath = AutoUploadManager.getInstance()
									.getmemoryAttachmentPath(
											getApplicationContext());
						}
						File file = new File(zipFilePath);
						if (!file.exists()) {
							file.mkdirs();
						}
						SimpleDateFormat sDateFormat = new SimpleDateFormat(
								"yyyyMMdd HHmmss");
						String time = sDateFormat.format(new java.util.Date());
						SubmitService ss = new SubmitServiceImp();
						File tempFile = null;
						try {
							tempFile = new File(picPathChild);
						} catch (Exception e1) {
						}
						int intPic = 0;
						int intRec = 0;
						if (tempFile != null && tempFile.exists()
								&& tempFile.isDirectory()) {
							File[] tempFiles = tempFile.listFiles();
							for (int i = 0; i < tempFiles.length; i++) {
								File f = (File) tempFiles[i];
								String tempname = f.getName().toLowerCase();
								if (tempname != null
										&& tempname.length() > 4
										&& ".jpg".equals(tempname
												.substring(tempname.length() - 4))) {
									intPic++;
								} else if (tempname != null
										&& tempname.length() > 4
										&& ".amr".equals(tempname
												.substring(tempname.length() - 4))) {
									intRec++;
								}
							}
						}
						XMLUtil xml = ss.submitData(getApplicationContext(),
								baseId, category, taskId, actionCode,
								actionText, String.valueOf(intPic),
								String.valueOf(intRec), map);
						//如果有网络
						if(NetWork.checkWork(getApplicationContext())==1){						
						if (xml != null) {
							System.out.println("xml.getSuccess()========"+xml.getSuccess());
							if (xml.getSuccess()) {// 如果返回值为1，提交成功退到列表提示成功
								// 工单文本信息提交成功后，将附件打成zip包，打包成功后，删除图片录音等工单附件原件。
								try {
									if (intPic > 0) {
										zipFile(picPathChild, zipFilePath
												+ "/GD_" + taskId + "_PIC_"
												+ time + ".zip", ".JPG");
									}
									if (intRec > 0) {
										zipFile(picPathChild, zipFilePath
												+ "/GD_" + taskId + "_REC_"
												+ time + ".zip", ".amr");
									}
									FileUtil.deleteFolder(picPathChild);// 删除源文件
								} catch (Exception e) {
									e.printStackTrace();
								}
								Message msg = Message.obtain();
								msg.what = SUBMIT_SUCCESS;
								handlerSubmit.sendMessage(msg);
							} else {
								Message msg = Message.obtain();
								msg.what = SUBMIT_ERROR;
								handlerSubmit.sendMessage(msg);
							}
						} else {
							Message msg = Message.obtain();
							msg.what = SUBMIT_ERROR;
							handlerSubmit.sendMessage(msg);
						}
						}
						//如果没有网络
						else{
							progressDialog.dismiss();
							// 迭代ActivityMap中的数据，退出所有activity
							Iterator<String> it = MyData.activityHashMap.keySet()
									.iterator();
							while (it.hasNext()) {
								String st = it.next();
								MyData.activityHashMap.get(st).finish();
							}
							
//							try {
//								String offLinePath=Environment.getExternalStorageDirectory().getPath()+AppConstants.FILE_CACHE+"/"+baseSN;
//								File offLineFile= new File(offLinePath);
//								if (!offLineFile.exists()) {
//									offLineFile.mkdirs();
//								}
//								System.out.println("dizhi============="+offLinePath+",,,"+zipFilePath);
//								if (intPic > 0) {
//									zipFile(picPathChild, offLinePath
//											+ "/GD_" + taskId + "_PIC_"
//											+ time + ".zip", ".JPG");
//								}
//								if (intRec > 0) {
//									zipFile(picPathChild, offLinePath
//											+ "/GD_" + taskId + "_REC_"
//											+ time + ".zip", ".amr");
//								}
//								FileUtil.deleteFolder(picPathChild);// 删除源文件
//							} catch (Exception e) {
//								e.printStackTrace();
//							}	
//							Message msg = Message.obtain();
//							msg.what = OFF_LINE_SUCCESS;
//							handlerSubmit.sendMessage(msg);
						}
					}
				}.start();
			}
		});
	}

	/**
	 * 提交数据 成功失败的处理
	 */
	private Handler handlerSubmit = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SUBMIT_SUCCESS:// 提交成功 关闭dialog 跳转界面
				progressDialog.dismiss();
				// 发送广播
				Intent in = new Intent();
				in.setAction("android.intent.action.Friends");
				sendBroadcast(in);
				Toast.makeText(getApplicationContext(), "提交成功",
						Toast.LENGTH_SHORT).show();
				// 迭代ActivityMap中的数据，退出所有activity
				Iterator<String> it = MyData.activityHashMap.keySet()
						.iterator();
				while (it.hasNext()) {
					String st = it.next();
					MyData.activityHashMap.get(st).finish();
				}
				break;
			case SUBMIT_ERROR:// 提交失败
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(), "对不起，提交失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case OFF_LINE_SUCCESS:// 离线提交成功
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(), "离线提交成功！",
						Toast.LENGTH_SHORT).show();
				// 迭代ActivityMap中的数据，退出所有activity
				Iterator<String> offLineIt = MyData.activityHashMap.keySet()
						.iterator();
				while (offLineIt.hasNext()) {
					String st = offLineIt.next();
					MyData.activityHashMap.get(st).finish();
				}
				break;
				
			}
		}
	};

	// -------------------------------------------onClick---------------------------------------------
	/**
	 * onClick点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_friends:
			finish();
			break;
		case R.id.photo:// 右上角文件夹查看拍照和录音
			String path = new StringBuilder(Environment
					.getExternalStorageDirectory().getPath())
					.append("/daiwei/").toString();// 获取SDCard目录

			Intent intent = new Intent(this, Rim.class);
			intent.putExtra("baseId", baseSN);
			startActivity(intent);
			break;
		case R.id.camera:// 拍照
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			imgFile = new File(file, "ceshi.jpg");
			Uri u = Uri.fromFile(imgFile);
			intentFromCapture.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, u);
			startActivityForResult(intentFromCapture, 1);
			break;
		case R.id.mic:// 录音
			Intent intentFromMic = new Intent(this, MicActivity.class);
			intentFromMic.putExtra("baseId", baseSN);
			intentFromMic.putExtra("baseSN", baseSN);
			startActivityForResult(intentFromMic, 3);
			break;
		default:
			break;
		}
	}

	// 横竖屏切换设置
	public void onConfigurationChanged(Configuration config) {

		super.onConfigurationChanged(config);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.gd_layout3:
			return true;
		case R.id.ll_friends:
			// Toast.makeText(this, "Touch Touch", Toast.LENGTH_SHORT).show();
			// 隐藏系统软键盘 把TestActivity传进来
			((InputMethodManager) getApplicationContext().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
					BaseHandleActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		case R.id.gd_frame_lv1:
			((InputMethodManager) getApplicationContext().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
					BaseHandleActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			break;
		case R.id.ll_sv:
			// Toast.makeText(this, "Touch", Toast.LENGTH_SHORT).show();
			break;
		}

		return false;
	}

	/**
	 * 获取daiwei/baseId 下的图片文件列表
	 * 
	 * @return
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
	 * 获取daiwei/baseId 下声音文件列表
	 * 
	 * @return
	 */
	private ArrayList<String> getVoiceList() {
		ArrayList<String> voiceList = new ArrayList<String>();
		ArrayList<String> list = getSubFiles(picPathChild);// 列出daiwei/baseId下的所有文件
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).endsWith(".amr")) {
				voiceList.add(list.get(i));
			}
		}
		return voiceList;
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

			pw = new PopupWindow(BaseHandleActivity.this);
			pw.setContentView(popView);// 设置的View
			pw.setWidth(LayoutParams.FILL_PARENT);
			pw.setHeight(LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(true); // 设置弹出窗体可点击
			pw.setOutsideTouchable(true);// 设置允许在外点击消失
			// pw.setAnimationStyle(R.drawable.alpha_in);
			pw.showAtLocation(layout1, Gravity.BOTTOM, 0, 0);

			final String name = (String) adapter.getItem(position);// 点击的图片文件名
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
						Log.e("BaseHandleActivity，删除文件操作", e.toString());
					} finally {
						adapter.list.remove(position);// 移除文件名
						adapter.notifyDataSetChanged();// 移除文件名
						Toast.makeText(getApplicationContext(), "删除成功", 0)
								.show();
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

			pw = new PopupWindow(BaseHandleActivity.this);
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
						Log.e("BaseHandleActivity，删除录音文件操作", e.toString());
					} finally {
						voiceAdapter.list.remove(position);// 移除文件名
						voiceAdapter.notifyDataSetChanged();// 移除文件名
						Toast.makeText(getApplicationContext(), "删除成功", 0)
								.show();
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
	public class VoiceAdapter extends BaseAdapter {
		private Context context;
		public ArrayList<String> list;

		public VoiceAdapter(Context c, ArrayList<String> list) {
			this.context = c;
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
			holder.tvRecordName.setText("录音" + (position + 1));
			return view;
		}

	}

}

class ViewHolder {
	TextView tvRecordName;
	ImageView imgBackgroud;
	TextView alterTv = null;
	RadioButton rbtn;

}

/**
 * spinner里用到
 * 
 * @author qch 实体类
 */
class CItem {
	private String ID = "";
	private String Value = "";

	public CItem() {
		ID = "";
		Value = "";
	}

	public CItem(String _ID, String _Value) {
		ID = _ID;
		Value = _Value;
	}

	@Override
	public String toString() {
		return Value;
	}

	public String getID() {
		return ID;
	}

	public String getValue() {
		return Value;
	}
}

class FieldView {
	private String type;
	private String actionCode;
	private Object view;

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

	public Object getView() {
		return view;
	}

	public void setView(Object view) {
		this.view = view;
	}

	public String getValue() {
		String str = null;
		if (type.equals("STRING") || type.equals("INT")) {
			EditText et = (EditText) getView();
			str = et.getText().toString();
		} else if (type.equals("FLOAT")) {
			EditText et = (EditText) getView();
			str = et.getText().toString();
		} else if (type.equals("SELECT")) {
			Spinner sp = (Spinner) getView();
			str = ((CItem) sp.getSelectedItem()).getID();
		} else if (type.equals("TIME")) {
			EditText et = (EditText) getView();
			str = et.getText().toString();
			str = str.replace("年", "-");
			str = str.replace("月", "-");
			str = str.replace("日", "");
			System.out.println(str);
		} else if (type.equals("TREE")) {
			Button button = (Button) getView();
			// str=button.getText().toString();
			Item item = (Item) button.getTag();
			if (item == null) {
				return "";
			}
			String mh = ":";
			String douhao = ",";
//			StringBuffer sb = new StringBuffer(actionCode);
//			sb.append(mh);
			StringBuffer sb = new StringBuffer();
			sb.append(item.getType().equals("person") ? "U" : "G");
			sb.append(mh);
			sb.append(item.getType().equals("person") ? item.getLoginname() : item.getId());
			sb.append(douhao);
			sb.append(item.getText());
			return sb.toString();
		}
		return str;
	}
}

class AlterAdapter extends BaseAdapter {
	private ArrayList<String> items = null;
	private Context mContext = null;
	private int alterPosition = 0;
	private int site;
	
	public AlterAdapter(ArrayList<String> item, Context context,int position) {
		this.items = item;
		this.mContext = context;
		this.site=position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.items == null ? 0 : this.items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.alter_item, null);
			holder = new ViewHolder();
			holder.alterTv = (TextView) convertView
					.findViewById(R.id.alter_text);
			holder.rbtn = (RadioButton) convertView
					.findViewById(R.id.alter_radio);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.alterTv.setText(items.get(position));
		holder.rbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				site=position;
				System.out.println("site========"+site);
				notifyDataSetChanged();
				setAlterPosition(position);
			}
		});
		if(position==site){
			holder.rbtn.setChecked(true);
		}
		else{
			holder.rbtn.setChecked(false);
		}
		return convertView;
	}

	public int getAlterPosition() {
		return alterPosition;
	}

	public void setAlterPosition(int alterPosition) {
		this.alterPosition = alterPosition;
	}
}