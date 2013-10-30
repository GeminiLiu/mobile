package daiwei.mobile.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import eoms.mobile.R;
import daiwei.mobile.Tools.SIM;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.GDDataService;
import daiwei.mobile.service.GDDataServiceImp;
import daiwei.mobile.service.GDDownLoadAttach;
import daiwei.mobile.service.GDDownLoadAttachImp;
import daiwei.mobile.service.LoginService;
import daiwei.mobile.service.LoginServiceImp;
import daiwei.mobile.service.SubmitService;
import daiwei.mobile.service.SubmitServiceImp;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.BaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.FieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import daiwei.mobile.util.TempletUtil.TMPModel.Type;

/**
 * 已办工单和待办工单每天Item跳转到此类
 * 
 * @author admin
 * 
 */
public class BaseTmpActivity extends Activity implements OnClickListener {
	// 模板主体
	public int detailId = R.layout.gd_detail_bg1;
	private LayoutInflater inflater;
	HashMap<String, ViewType> vt = new HashMap<String, ViewType>();
	private TextView tv_title;// 头标题
	private TextView tv_optiontitle;// 标题
	private TextView tv_ID;// ID
	private Templet tmp;
	private String baseSchema;// 版本类型
	private String baseSummary;// 任务标题
	private String baseSN;
	private String baseId;
	private String taskId;
	private int isWait;
	private ImageView img_back;// 返回按钮
	private Intent intent = null;// 获取传递来的数据
	private LinearLayout lyt, lnyt;
	private List<BaseField> listBaseField;
	private int listsize = 0;
	private RelativeLayout rlt;
	private Handler handler;
	private XMLUtil ele;
	public static final int SUBMIT_ERROR = 10;
	public static final int SUBMIT_SUCCESS = 11;
	public static final int DOWNLOAD_SUCCESS = 12;
	public static final int DOWNLOAD_ERROR = 13;
	public static final int DOWNLOADPROCESS_SUCCESS = 14;//下载流程信息
	public static final int DOWNLOADPROCESS_ERROR = 15;
	private ProgressDialog dialog = null;
	private WaitDialog wd = null;
	private int mSingleChoiceID = 0;

	
	// gallery
	private Gallery gallery;
	private ArrayList<String> picList;// 图片列表
	private ImageAdapter adapter;
	private String baseIdPath;//attach/baseId
	private Gallery voiceGallery;
	private ArrayList<String> voiceList;// 声音列表
	private VoiceAdapter voiceAdapter;
	
	private TextView tv_processInfo;//流程记录
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_detail_bg1);//R.layout.gd_detail_bg1
		MyData.activityHashMap.put("BaseTmpActivity", this);// 加入集合
		MyData.add(BaseTmpActivity.this);
		findViewById();
		handler = new Handler() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void handleMessage(Message msg) {
				List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
				if (list.size() != 0) {
					Map<String, String> map = list.get(0);// 获取map集合
					Set<Entry<String, String>> set = map.entrySet();
					Iterator<Map.Entry<String, String>> it = set.iterator();
					while (it.hasNext()) {
						Map.Entry<String, String> entry = (Map.Entry<String, String>) it
								.next();
						ViewType ve = vt.get(entry.getKey());// 不直接返回值
																// 返回ViewType类型
						if (ve != null) {
							if (ve.getType().equals("LABEL")) {
								TextView tvtl = (TextView) ve.getView()
										.findViewById(R.id.tv_vl);// gd_detail_textview
								// 如果是处理时限和受理时限 就转成时间
								if (entry.getKey().equals("BaseDealOutTime")
										|| entry.getKey().equals(
												"BaseAcceptOutTime"))
									tvtl.setText(tvtl.getText()
											+ ":"
											+ StringToLongToString(entry
													.getValue()));
								else
									tvtl.setText(tvtl.getText() + ":"
											+ entry.getValue());

								if (entry.getKey().equals("DealProcessDesc")
										|| entry.getKey().equals(
												"BaseAcceptOutTime")) {
									View v = (View) ve.getView().findViewById(
											R.id.line);
									v.setVisibility(View.GONE);
								}

							} else if (ve.getType().equals("STRING")) {
								final TextView tvvl = (TextView) ve.getView()
										.findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());

								/** 判断简单人联系方式 */
								if (entry.getKey().equals(
										"BaseCreatorConnectWay")) {
									tvvl.setTextColor(Color.RED);
									TextPaint tp = tvvl.getPaint(); 
									tp.setFakeBoldText(true);
									tvvl.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											String telephone[] = ((String) tvvl
													.getText()).split(",");
											CreatDialog(telephone);
										}
									});

								}
								/*
								 * if(entry.getKey().equals("INC_HappenTime")){//
								 * 故障发生时间
								 * tvvl.setText(StringToLongToString(entry
								 * .getValue())); }else{
								 * tvvl.setText(entry.getValue()); }
								 */
							} else if (ve.getType().equals("TIME")) {
								TextView tvvl = (TextView) ve.getView()
										.findViewById(R.id.tv_vl);
								String el = entry.getValue();
								if (!el.equals("")) {
									el = StringToLongToString(el);
								}
								tvvl.setText(el);
							} else if (ve.getType().equals("SELECT")) {//数据字典
								TextView tvvl = (TextView) ve.getView()
										.findViewById(R.id.tv_vl);
								Map<String, String> dicMap = tmp.getDicDefine()
										.get(ve.getDic()).getOptions();
								tvvl.setText(dicMap.get(entry.getValue()));
							}
						}//end if (ve != null)
					}

					// 处理按钮
					if (tmp != null) {
						List<ActionButton> ab = tmp.getActionButtons();
						int abs = ab.size();
						for (int i = 0; i < abs; i++) {
							ActionButton abn = ab.get(i);
							abn.setCityID(map.get("BaseDWBelongCityID"));
							abn.setSpecialtyID(map.get("BaseDwItemID"));
							Map<String,String> actionOps = ele.getActionOps();
							String actionName = actionOps.get(abn.getId());
							if (actionName!= null) {
								addActionButton(lnyt, abn, actionName);// 动态加载传数据方法
							}

						}
					}
					// 已办 或者 底部没有按钮时 去除底部
					if (isWait == 0 || lnyt.getChildCount() == 0) {
						lnyt.setVisibility(View.GONE);
					}
//					lnyt.setVisibility(View.GONE);
				}
				dialog.dismiss();
				super.handleMessage(msg);
			}

		};
		LoginService loginService = new LoginServiceImp();
		if (loginService.isTempletMapAvailable(getApplicationContext())) {// 成功加载模板
			getData();//获取从上个activity传来的值以及工单记录查看信息字段信息
			setText();//根据工单名称获取信息页面显示标题
			logic();//加载工单属性
			loadData();
		} else {
			// TODO 提示什么
		}
	}

	/** 点击电话号码弹出打电话提示 */
	private void CreatDialog(final String[] telephoneItems) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				BaseTmpActivity.this);
		builder.setIcon(R.drawable.sym_action_call);
		builder.setTitle("建单人联系方式");
		builder.setSingleChoiceItems(telephoneItems, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						mSingleChoiceID = whichButton;
					}
				});
		builder.setPositiveButton(getString(R.string.sure),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						if (telephoneItems.length != 0) {
							SIM checkSim=new SIM(BaseTmpActivity.this);
						if(checkSim.isCanUseSim()){								
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ telephoneItems[mSingleChoiceID]));
						startActivity(intent);
							}else{
								 Toast.makeText(BaseTmpActivity.this, R.string.sim_no, Toast.LENGTH_SHORT).show();
							}
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
	}

	/** 加载工单数据数据 */
	private void loadData() {
		wd = new WaitDialog(BaseTmpActivity.this, "数据加载中...");
		dialog = wd.getDialog();
		new Thread() {
			@Override
			public void run() {
				GDDataService ds = new GDDataServiceImp();
				ele = ds.getData(getApplicationContext(), baseId, taskId,
						baseSchema);
				System.out.println("baseId========"+baseId+",,,taskId="+taskId+",baseSchema"+baseSchema);
				List<Map<String, String>> list = ele.getList();// 获取数据
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);

				super.run();
			}

		}.start();

	}

	/**
	 * 处理逻辑 动态加载textview
	 */
	private void logic() {
		rlt.addView(lnyt);// 加底部背景
		// 处理文本体
		for (int i = 0; i < listsize; i++) {// 循环拿到每一个BaseField
			BaseField baseField = listBaseField.get(i);
			if (baseField.getType().equals("field")) {
				addField(lyt, baseField.getField());
			} else {
				addfieldGroup(lyt, baseField.getFieldGroup());
			}
		}
		addProcessRecord(lyt);//增加流程记录
		//======附件功能暂时去掉  xiaxj==============
		addfieldGroupAttach(lyt);// 增加附件信息
		//===========================
	}

	/**
	 * 设置方法
	 */
	private void setText() {
		List<Type>  tmpList= LoginServiceImp.xml.getType();
		String title = "";
		for(Type type:tmpList){
			if(baseSchema.equalsIgnoreCase(type.getType())){
				title = type.getText()+title;
			}
		}
		tv_title.setText(title);
		tv_optiontitle.setText(baseSummary);
		tv_ID.setText(baseSN);
	}
	/**
	 * 设置方法
	 */
	private void setTextOld() {
		if (baseSchema.equals("WF4:EL_AM_PS")) {
			tv_title.setText("发电保障工单");
		} else if (baseSchema.equals("WF4:EL_AM_TTH")) {
			tv_title.setText("故障处理工单");
		} else if (baseSchema.equals("WF4:EL_AM_AT")) {
			tv_title.setText("任务工单");
		}
		tv_optiontitle.setText(baseSummary);
		tv_ID.setText(baseSN);
	}

	/**
	 * 获取此类需要的数据的方法
	 */
	private void getData() {
		baseSchema = intent.getStringExtra("BaseSchema");
		baseSummary = intent.getStringExtra("BaseSummary");
		baseSN = intent.getStringExtra("BaseSN");
		baseId = intent.getStringExtra("BaseID");
		taskId = intent.getStringExtra("TaskID");
		isWait = intent.getIntExtra("IsWait", 0);
		String notice = intent.getStringExtra("Notice");
		/*
		 * //------------ if(notice != null){ SharedPreferences sp =
		 * getSharedPreferences(AppConstants.SP_CONFIG, Context.MODE_PRIVATE);
		 * String username = sp.getString("username", ""); String password =
		 * sp.getString("password", "");
		 * 
		 * LoginService lg = new LoginServiceImp(); TestXmlCreat tc = new
		 * TestXmlCreat(); tc.addElement(tc.baseInfo,"username", username);
		 * tc.addElement(tc.baseInfo,"password", password); Map<String,Object>
		 * loginMap = lg.login(tc.doc,getApplicationContext()); boolean b =
		 * (Boolean) loginMap.get("IsLegal"); if(b){
		 * 
		 * } } //---------------
		 */
		/** 获取工单模版 */
		tmp = TempletModel.TempletMap.get(baseSchema);
        System.out.println("tmp==========="+tmp);
		if (tmp != null) {
			listBaseField = tmp.getBaseFields(); // 从模板中获取 List<BaseField>
			listsize = listBaseField.size();
		}
	}

	/**
	 * 获取上一个类传递过来的数值
	 */
	private void findViewById() {
		// 预留处理文本头
		tv_title = (TextView) findViewById(R.id.tv_title);// 头标题
		tv_optiontitle = (TextView) findViewById(R.id.deal_tv1);// 故障处理工单
		tv_ID = (TextView) findViewById(R.id.deal_content); // 一级处理中

		img_back = (ImageView) findViewById(R.id.iv_friends);// 返回按钮
		img_back.setOnClickListener(this);

		// 下拉加载数据 listview
		lyt = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rlt = (RelativeLayout) findViewById(R.id.gd_bottom);// 底部动态加载条位置
		lnyt = (LinearLayout) inflater.inflate(R.layout.footbg, null, true);// 底部横条
		intent = getIntent();

	}

	/**
	 * 日期转换
	 * 
	 * @param searcherInput
	 * @return
	 */
	private String StringToLongToString(String searcherInput) {
		long lgTime = 0;
		Boolean judge = false;
		if (searcherInput.equals("0"))
			return "";

		try {
			Integer.parseInt(searcherInput);
			searcherInput += "000";
			judge = true;
		} catch (NumberFormatException e) {
			judge = false;
		}
		if (judge == true) {
			lgTime = Long.parseLong(searcherInput);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			searcherInput = sdf.format(lgTime);
		}

		return searcherInput;

	}

	private void addField(LinearLayout lyt, Field field) {
		if (field.getType().equals("LABEL"))
			addLabel(lyt, field);
	}

	/**
	 * 主题 受理时限 处理时限 <baseField> <field code='BaseSummary'
	 * type='LABEL'>主题</field> <field code='BaseDealOutTime'
	 * type='LABEL'>处理时限</field>
	 * 
	 * @param lyt
	 * @param field
	 */
	private void addLabel(LinearLayout lyt, Field field) {
		View view = inflater.inflate(R.layout.gd_detail_textview, null, true);
		TextView tv = (TextView) view.findViewById(R.id.tv_vl);
		tv.setText(field.getText());
		lyt.addView(view);
		putViewType(view, field);
	}

	private void addString(LinearLayout lyt, Field field) {
		View view = inflater.inflate(R.layout.gd_detail_item_text, null, true);
		TextView tvtl = (TextView) view.findViewById(R.id.tv_tl);//属性名
		tvtl.setText(field.getText());
		TextView tvvl = (TextView) view.findViewById(R.id.tv_vl);//属性值
		// tvvl.setText(bd.getText());
		tvvl.setText("");
		lyt.addView(view);
		putViewType(view, field);
	}
	/**
	 *	流程记录
	 * @param lyt
	 * @param fieldGroup
	 */
	private void addProcessRecord(LinearLayout lyt) {
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		lyt.addView(view);
		TextView tx = (TextView) view.findViewById(R.id.detail_tv2);
		tx.setText("流程记录");// 设置标题名
		final ImageView add = (ImageView) view.findViewById(R.id.add1);
		final LinearLayout MessageN = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);// 内容
		MessageN.setVisibility(View.GONE);
		add.setImageResource(R.drawable.add);
		
		View processInfoView = inflater.inflate(R.layout.processinfo, null, true);
		MessageN.addView(processInfoView);
		tv_processInfo = (TextView) processInfoView.findViewById(R.id.processinfo);
		
		RelativeLayout normalMessage = (RelativeLayout) view
				.findViewById(R.id.ll_normalMessage1); // 标题加号
		normalMessage.setOnClickListener(new OnClickListener() {
			boolean b = false;// 保证只下载一次
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
					if (!b) {
					showDialog(1);
					progressdialog.setContentView(R.layout.customprogress);
					//调用G005接口 下载流程记录
					new Thread(){
						public void run() {
							GDDataService getProcessInfoImp=new GDDataServiceImp();
							List<String> list=getProcessInfoImp.getProcessInfo(getApplicationContext(), baseId, baseSchema);
							System.out.println("======================"+list);
							for(int i=0;i<list.size();i++){
								System.out.println("===========111"+list.get(i));
							}
							if (list.size()!=0&&list!=null) {
								Message msg = Message.obtain();
								msg.what = DOWNLOADPROCESS_SUCCESS;
								msg.obj=list;
								handlerDownLoadAttach.sendMessage(msg);
							} else {
								Message msg = Message.obtain();
								msg.what =DOWNLOADPROCESS_ERROR;
								handlerDownLoadAttach.sendMessage(msg);
							}
						};
					}.start();
					b=true;
					}
					
				}else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}
		});
	}
	/**
	 * 附件信息的布局
	 * 下载附件功能
	 * 解压文件功能
	 * 
	 * @param lyt
	 * @param fieldGroup
	 */
	private void addfieldGroupAttach(LinearLayout lyt) {
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		lyt.addView(view);
		TextView tx = (TextView) view.findViewById(R.id.detail_tv2);
		tx.setText("附件信息");// 设置标题名

		final ImageView add = (ImageView) view.findViewById(R.id.add1);
		final LinearLayout MessageN = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);// 内容
		MessageN.setVisibility(View.GONE);
		add.setImageResource(R.drawable.add);

		View attachView = inflater.inflate(R.layout.attach, null, true);// 填充附件布局
		MessageN.addView(attachView);

		RelativeLayout normalMessage = (RelativeLayout) view
				.findViewById(R.id.ll_normalMessage1); // 标题加号
		normalMessage.setOnClickListener(new OnClickListener() {
			
			boolean b = false;//保证只下载一次附件
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
					//点击附件信息  调用G008接口 下载附件
					if(!b){
//						wd = new WaitDialog(BaseTmpActivity.this, "附件下载中...");
//						dialog=wd.getDialog();
						showDialog(1);
						progressdialog.setContentView(R.layout.customprogress);
						new Thread(){
							public void run() {
								GDDownLoadAttach loadAttach=new GDDownLoadAttachImp();
								String isOk=loadAttach.downLoadAttach(getApplicationContext(), baseId, baseSchema);
								System.out.println("下载压缩文件是否成功？"+isOk);
									if(isOk.equals("OK")){
										Message msg=Message.obtain();
										msg.what=DOWNLOAD_SUCCESS;
										handlerDownLoadAttach.sendMessage(msg);
										System.out.println("解压文件成功");
									}else{
										Message msg=Message.obtain();
										msg.what=DOWNLOAD_ERROR;
										handlerDownLoadAttach.sendMessage(msg);
									}
								
							};
						}.start();
					b = true;
					}

				} else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}

			
		});
	}

	
	/**
	 * gallery操作-----------------------------------------------------------------------------
	 */
	private void initImage() {
		baseIdPath = Environment.getExternalStorageDirectory()+"/attach/"+baseId;
		picList = getList();
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setSelection(picList.size() / 2);
		adapter = new ImageAdapter(BaseTmpActivity.this, picList);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(listener);
//		imgCamera = (ImageButton) findViewById(R.id.camera);
//		imgMic = (ImageButton) findViewById(R.id.mic);
//		ll_camera = (LinearLayout) findViewById(R.id.ll_camera);
//		ll_mic = (LinearLayout) findViewById(R.id.ll_mic);
//
//		imgCamera.setOnClickListener(this);
//		imgMic.setOnClickListener(this);
	}
	private void initRecord() {
		baseIdPath = Environment.getExternalStorageDirectory()+"/attach/"+baseId;
		voiceList = getVoiceList();
		voiceGallery = (Gallery) findViewById(R.id.voicegallery);
		voiceAdapter = new VoiceAdapter(BaseTmpActivity.this, voiceList);
		voiceGallery.setAdapter(voiceAdapter);
		voiceGallery.setOnItemClickListener(voiceListener);
	}
	/**
	 * 获取attach/baseId 下的图片文件列表
	 * 
	 * @return
	 */
	private ArrayList<String> getList() {
		ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<String> list = getSubFiles(baseIdPath);// 列出attach/baseId下的所有文件
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).endsWith(".JPG")) {
				imageList.add(list.get(i));
			}
		}
		return imageList;
	}
	/**
	 * 获取attach/baseId 下声音文件列表
	 * @return
	 */
	private ArrayList<String> getVoiceList() {
		ArrayList<String> voiceList = new ArrayList<String>();
		ArrayList<String> list = getSubFiles(baseIdPath);// 列出daiwei/baseId下的所有文件
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
				String path1 = baseIdPath + "/" + list.get(position);
				System.out.println("dddddddpath=======" + path1);
				InputStream is = new BufferedInputStream(new FileInputStream(
						path1));
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
	class ViewHolder {
		TextView tvRecordName;
		ImageView imgBackgroud;
		TextView alterTv = null;
		RadioButton rbtn;

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

			pw = new PopupWindow(BaseTmpActivity.this);
			pw.setContentView(popView);// 设置的View
			pw.setWidth(LayoutParams.FILL_PARENT);
			pw.setHeight(LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(true); // 设置弹出窗体可点击
			pw.setOutsideTouchable(true);// 设置允许在外点击消失
			// pw.setAnimationStyle(R.drawable.alpha_in);
			pw.showAtLocation(layout1, Gravity.BOTTOM, 0, 0);

			final String name = (String) adapter.getItem(position);// 点击的图片文件名
			String path = baseIdPath + "/" + name;// 工单目录+文件名
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

			pw = new PopupWindow(BaseTmpActivity.this);
			pw.setContentView(popView);// 设置的View
			pw.setWidth(LayoutParams.FILL_PARENT);
			pw.setHeight(LayoutParams.WRAP_CONTENT);
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(true); // 设置弹出窗体可点击
			pw.setOutsideTouchable(true);// 设置允许在外点击消失
			// pw.setAnimationStyle(R.drawable.alpha_in);
			pw.showAtLocation(layout1, Gravity.BOTTOM, 0, 0);

			final String name = (String) voiceAdapter.getItem(position);// 点击的录音文件名
			String path = baseIdPath + "/" + name;// 工单目录+文件名
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
	 * addfieldGroup
	 * 
	 * @param lyt
	 * @param fieldGroup
	 */
	private void addfieldGroup(LinearLayout lyt, FieldGroup fieldGroup) {
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		lyt.addView(view);
		TextView tx = (TextView) view.findViewById(R.id.detail_tv2);
		if(StringUtil.isNotEmpty(fieldGroup.getText())){
			tx.setText(fieldGroup.getText());// 设置标题名
		}

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

		LinearLayout lt = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);
		List<Field> fieldList = fieldGroup.getGroup();
		for (int i = 0; i < fieldList.size(); i++) {
			System.out.println("fieldList.get(i).getType()============="
					+ fieldList.get(i).getType());
			if (!fieldList.get(i).getType().equals("HIDDEN"))
				addString(lt, fieldList.get(i));
		}
	}

	
	/**适配动态底部按钮
	 * 提交数据
	 */
	private void addActionButton(LinearLayout lnyt, final ActionButton ab,final String str){
		final View view= inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1)); 
		//底部显示工单待办数据的view隐藏掉
		TextView wfNumView = (TextView)view.findViewById(R.id.msg_num_tv);
		wfNumView.setVisibility(View.GONE);
		
		TextView bottomTv = (TextView)view.findViewById(R.id.bottom_tv);
		bottomTv.setText(str);

		lnyt.addView(view);
		//设置工单动作处理监听
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				view.findViewById(R.id.imageButton).setBackgroundResource(
						R.drawable.bottom_sendup1);
				if (ab.getField().isEmpty()) {// 代表受理 不进入下一页 并提交数据
					wd = new WaitDialog(BaseTmpActivity.this, "附件信息加载中...");
					dialog = wd.getDialog();
					new Thread() {// 开启子线程提交数据
						public void run() {
							SubmitService ss = new SubmitServiceImp();
							XMLUtil xml = ss.submitData(
									getApplicationContext(), baseId,
									baseSchema, taskId, ab.getCode(),
									ab.getName(), "0", "0",
									new HashMap<String, String>());
							if (xml != null) {
								if (xml.getSuccess()) {// 如果返回值为1，提交成功退到列表提示成功
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
					}.start();
				} else {
					Intent intent = new Intent(getApplicationContext(),
							BaseHandleActivity.class);
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("field", ab); // ActionButton对象
					mBundle.putSerializable("tmp", tmp); // Templet对象
					mBundle.putString("title", str);
					mBundle.putString("baseId", baseId);
					mBundle.putString("category", baseSchema);
					mBundle.putString("taskId", taskId);
					mBundle.putString("actionId", ab.getId());
					mBundle.putString("actionCode", ab.getCode());
					mBundle.putString("baseSN", baseSN);
					intent.putExtras(mBundle);
					startActivity(intent);
				}
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
				dialog.dismiss();
				// 发送广播
				Intent in = new Intent();
				in.setAction("android.intent.action.Friends");
				sendBroadcast(in);
				Toast.makeText(getApplicationContext(), "提交成功",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case SUBMIT_ERROR:// 提交失败
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "对不起，提交失败！", 0).show();
				break;

			}
		}
	};
	/**
	 * 下载附件成功失败的处理
	 */
	private Handler handlerDownLoadAttach = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DOWNLOAD_SUCCESS://下载并解压成功
//				dialog.dismiss();
				removeDialog(1);
//				Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
				initImage();
				initRecord();
				if((picList==null||picList.size()==0)&&(voiceList==null||voiceList.size()==0)){
					Toast.makeText(getApplicationContext(), "尚无附件", 0).show();
					return;
				}
				break;
			case DOWNLOAD_ERROR://下载并解压失败
				removeDialog(1);
				Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
				break;
			case DOWNLOADPROCESS_SUCCESS://下载流程记录成功 设置数据
				removeDialog(1);
				StringBuffer sb=new StringBuffer();
				List<String> list=(List<String>) msg.obj;
				for(int i=0;i<list.size();i++){
					sb.append(list.get(i)+"\n");
				}
				tv_processInfo.setText(sb.toString());
				break;
			case DOWNLOADPROCESS_ERROR:
				removeDialog(1);
				Toast.makeText(getApplicationContext(), "下载流程信息失败",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	//-------------------------------------------
	ProgressDialog progressdialog ;
	//创建dialog
	@Override
	protected Dialog onCreateDialog(int id) {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	switch (id) {
	case 1: {
	progressdialog = new ProgressDialog(this);
//	progressdialog.setMessage(message);
	progressdialog.setIndeterminate(true);
//	progressdialog.setCancelable(cancel);
	return progressdialog;
	}
	}
	return null;
	}
	
	
	
	
	private void putViewType(View v,Field bd){
		ViewType viewType = new ViewType();
		viewType.setType(bd.getType());
		viewType.setView(v);
		viewType.setDic(bd.getDic());
		vt.put(bd.getId(), viewType);
	}

	/**
	 * 点击顶部按钮 关闭 并activity
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_friends:
			finish();
			break;
		}

	}

	@Override
	protected void onRestart() {
		for (int i = 0; i < lnyt.getChildCount(); i++) {
			lnyt.getChildAt(i).findViewById(R.id.imageButton)
					.setBackgroundResource(R.drawable.bottom_sendup);
		}

		super.onRestart();
	}
}

class ViewType {
	private String type;
	private String dic;
	private View view;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDic() {
		return dic;
	}

	public void setDic(String dic) {
		this.dic = dic;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
}