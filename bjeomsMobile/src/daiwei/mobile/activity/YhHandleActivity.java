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
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.amap.api.maps.MapView;

import eoms.mobile.R;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.AutoUploadManager;
import daiwei.mobile.service.SubmitService;
import daiwei.mobile.service.SubmitServiceImp;
import daiwei.mobile.util.FileUtil;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.BaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.Dic;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.FieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 新建隐患页面
 * 
 * @author qch
 * 
 */
public class YhHandleActivity extends Activity implements OnClickListener {
	public int detailId = R.layout.gd_modle;
	private Templet tmp;
	private LinearLayout ll_content;
	private LinearLayout ll_title;
	private LinearLayout ll_bottom;
	LayoutInflater inflater;
	private Map<String, FieldView> sf = new HashMap<String, FieldView>();
	private LinearLayout ll_foot;
	private ProgressDialog progressDialog = null;
	public static final int SUBMIT_ERROR = 10;
	public static final int SUBMIT_SUCCESS = 11;
	private Map<String, String> map;
	private String titleName, site, longitude, latitude, professional;
	private Map<String, Dic> dicMap;// Templet类中的 public Map<String, Dic>
									// getDicDefine() {

	private Boolean state = true;
	private File sd, file, imageFile = null;
	private String imgName, picPath, picPathChild;

	// gallery
	private Gallery gallery;
	private ArrayList<String> picList;// 图片列表
	private ImageAdapter adapter;

	private Gallery voiceGallery;
	private ArrayList<String> voiceList;// 声音列表
	private VoiceAdapter voiceAdapter;

	private ImageButton imgCamera;// 拍照按钮
	private ImageButton imgMic;// 录音按钮
	private static final int BUFFER = 1024 * 1024;// 缓存大小

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(detailId);
		MyData.activityHashMap.put("YhHandleActivity", this);// 加入集合
		MyData.add(YhHandleActivity.this);
		state = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (state) {
			sd = Environment.getExternalStorageDirectory();
			picPath = sd + "/daiwei";

			file = new File(picPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		findView();
		getTmp();// 获取模板
		logic();
		initImage();
		initRecord();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 找控件
	 */
	private void findView() {
		Intent intent = getIntent();
		titleName = intent.getStringExtra("titleName");
		site = intent.getStringExtra("site");
		longitude = intent.getStringExtra("longitude");
		latitude = intent.getStringExtra("latitude");
		professional = intent.getStringExtra("professional");
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ll_title = (LinearLayout) findViewById(R.id.gd_layout1);
		ll_content = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		ll_bottom = (LinearLayout) findViewById(R.id.gd_layout3);
		View view_title = inflater.inflate(R.layout.gd_layout1, null);// 填充头布局
		ll_foot = (LinearLayout) inflater.inflate(R.layout.footbg, null);
		ll_title.addView(view_title);// 往第一部分里加头
		ll_bottom.addView(ll_foot);// 往第三部部分里加脚
		ImageView iv_friends = (ImageView) findViewById(R.id.iv_friends);
		TextView tv_title = (TextView) view_title.findViewById(R.id.tv_title);
//		imageMap = (ImageView) findViewById(R.id.imgview_map);
		tv_title.setText("新建隐患");
		iv_friends.setOnClickListener(this);
	}

	private void initRecord() {
		picPathChild = picPath + "/YH";
		voiceList = getVoiceList();
		voiceGallery = (Gallery) findViewById(R.id.voicegallery);
		voiceAdapter = new VoiceAdapter(YhHandleActivity.this, voiceList);
		voiceGallery.setAdapter(voiceAdapter);
		voiceGallery.setOnItemClickListener(voiceListener);

	}

	private void initImage() {
		picPathChild = picPath + "/YH";
		picList = getList();
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setSelection(picList.size() / 2);
		adapter = new ImageAdapter(YhHandleActivity.this, picList);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(listener);
		imgCamera = (ImageButton) findViewById(R.id.camera);
		imgMic = (ImageButton) findViewById(R.id.mic);

		imgCamera.setOnClickListener(this);
		imgMic.setOnClickListener(this);

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
				System.out.println("dddddddpath=======" + path);
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

			pw = new PopupWindow(YhHandleActivity.this);
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
						Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT)
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

			pw = new PopupWindow(YhHandleActivity.this);
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
						Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT)
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
	 * 获取隐患模板 显示YhTmp中的内容
	 */
	private void getTmp() {
		tmp = TempletModel.TempletMap.get(("WF4:EL_AM_ER"));// 获取隐患模板
		if (tmp == null) {
			Toast.makeText(getApplicationContext(), "模板为空", Toast.LENGTH_SHORT).show();
			return;
		}
		dicMap = tmp.getDicDefine();// 获取字典 Templet类中的 public Map<String, Dic>
									// getDicDefine() {

	}

	private void logic() {
		// 处理文本体
		List<BaseField> list = tmp.getBaseFields();
		for (int i = 0; i < list.size(); i++) {
			BaseField baseField = list.get(i);
			if (baseField.getType().equals("fieldGroup")) {
				FieldGroup fieldGroup = baseField.getFieldGroup();
				if (!fieldGroup.getText().equals("基本信息")) {
					List<Field> fieldList = fieldGroup.getGroup();
					for (int ii = 0; ii < fieldList.size(); ii++) {
						Field field = fieldList.get(ii);
						if (field.getType().equals("STRING")) {
							addEditText(field, false, 0);
						} else if (field.getType().equals("SELECT")) {
							addSpiner(field);
						} else if (field.getType().equals("TEXTAREA")) {
							addEditText(field, true, 2);
						}

					}
				}
			} else if (baseField.getType().equals("field")) {
				Field field = baseField.getField();
				if (field.getType().equals("LABEL")) {
					addEditText(field, false, 0);
				}
			}
		}

		// ----------
		List<ActionButton> abList = tmp.getActionButtons();
		for (int i = 0; i < abList.size(); i++) {
			ActionButton ab = abList.get(i);
			if (ab.getCreateaction() != null)
				addActionButton(ll_foot, ab);// 动态加载传数据方法
		}

	}

	/**
	 * 增加文本  地图
	 * 
	 * @param field
	 *            字段
	 * @param b
	 *            布尔值 是否多行
	 * @param inputtype
	 *            输入类型
	 */
	private void addEditText(Field field, Boolean b, int inputtype) {

		View view = inflater.inflate(R.layout.edittext, null, true);
		TextView tv_editText = (TextView) view.findViewById(R.id.tv_editText);
		Button mapBtn = (Button) view.findViewById(R.id.template_btn);
		tv_editText.setText(field.getText());
		EditText et = (EditText) view.findViewById(R.id.et_editText);
		System.out.println("titleName==========" + titleName);
		System.out.println("site==========" + site);
		System.out.println("longitude==========" + longitude);
		System.out.println("latitude==========" + latitude);
		if (field.getText().equals("主题")) {
			et.setText(titleName);
		} else if (field.getText().equals("网元名称")) {
			et.setText(site);			
		} 
		else if(field.getText().equals("隐患发现地点")){
			et.setText(site);
			mapBtn.setVisibility(View.VISIBLE);
			mapBtn.setText("选择");
			mapBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent intent = new Intent(YhHandleActivity.this,YhMapViewActivity.class);
			    startActivityForResult(intent,4);
				}
			});
		}else if (field.getText().equals("隐患地点经度")) {
			et.setText(longitude);
		} else if (field.getText().equals("隐患地点纬度")) {
			et.setText(latitude);
		} else {
			et.setText("");
		}
		if (b) {
			et.setLines(Integer.valueOf(field.getRow()));
		}

		if (inputtype == 1) {
			et.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else if (inputtype == 2) {
			et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		}

		ll_content.addView(view);

		FieldView fieldView = new FieldView();
		fieldView.setType("STRING");
		fieldView.setView(et);
		sf.put(field.getId(), fieldView);
	}

	/** 适配动态底部按钮 */
	private void addActionButton(LinearLayout lnyt, final ActionButton ab) {
		final View view = inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));

		TextView tv = (TextView) view.findViewById(R.id.bottom_tv);
		tv.setText(ab.getName());

		lnyt.addView(view);

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				view.findViewById(R.id.imageButton).setBackgroundResource(
						R.drawable.bottom_sendup1);
				if (ab.getField().isEmpty()) {
					map = new HashMap<String, String>();
					Set<Entry<String, FieldView>> set = sf.entrySet();
					for (Iterator<Map.Entry<String, FieldView>> it = set
							.iterator(); it.hasNext();) {
						Map.Entry<String, FieldView> entry = (Map.Entry<String, FieldView>) it
								.next();
						map.put(entry.getKey(), entry.getValue().getValue());
						if (entry.getValue().getValue().trim().equals("")) {
							Toast.makeText(getApplicationContext(), "不能为空！", Toast.LENGTH_SHORT)
									.show();
							return;
						}
					}

					progressDialog = ProgressDialog.show(YhHandleActivity.this,
							"请稍等...", "提交数据中...", true);
					new Thread() {// 开启子线程提交数据
						public void run() {
							String zipFilePath = AutoUploadManager
									.getInstance().getSDCardAttachmentPath(
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
									"yyyyMMddHHmmss");
							String time = sDateFormat
									.format(new java.util.Date());
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
													.substring(tempname
															.length() - 4))) {
										intPic++;
									} else if (tempname != null
											&& tempname.length() > 4
											&& ".amr".equals(tempname
													.substring(tempname
															.length() - 4))) {
										intRec++;
									}
								}
							}

							SubmitService ss = new SubmitServiceImp();
							XMLUtil xml = ss.submitData(
									getApplicationContext(), "",
									"WF4:EL_AM_ER", "", ab.getCode(),
									ab.getName(), String.valueOf(intPic),
									String.valueOf(intRec), map);
							System.out.println("隐患返回的taskId========"
									+ xml.getTaskId());
							String taskId = null;
							if (xml != null) {
								if (xml.getSuccess()) {// 如果返回值为1，提交成功退到列表提示成功
									Message msg = Message.obtain();
									msg.what = SUBMIT_SUCCESS;
									handlerSubmit.sendMessage(msg);
									taskId = xml.getTaskId();
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
				}
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

	private void addSpiner(Field field) {

		Dic dic = dicMap.get(field.getDic());// 获取字典
		Map<String, String> options = dic.getOptions();
		ArrayList<CItem> list = new ArrayList<CItem>();
		Iterator<String> it = options.keySet().iterator();// 迭代字典
		while (it.hasNext()) {
			String option = it.next();
			list.add(new CItem(option, options.get(option)));// 把字典重新加入到list集合中
																// id,value
		}
		View view = inflater.inflate(R.layout.spinner, null, true);
		TextView tv_spinner = (TextView) view.findViewById(R.id.tv_spinner);
		System.out.println("professional=========="+professional);
		tv_spinner.setText(field.getText());
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner_reason);
		spinnerList(spinner, list);
		ll_content.addView(view);

		FieldView fv = new FieldView();
		fv.setType("SELECT");
		fv.setView(spinner);
		sf.put(field.getId(), fv);
	}

	private void spinnerList(Spinner spinner, List<CItem> cm) {
		ArrayAdapter<CItem> choiceAdapter = new ArrayAdapter<CItem>(
				getApplicationContext(), R.layout.myspinner, cm);
		choiceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setPrompt("请选择");
		spinner.setAdapter(choiceAdapter);
		final Map<String, String> map = new HashMap<String, String>();
		if(professional!=null){
		for(int i=0;i<cm.size();i++){
			if(professional.equals(cm.get(i))){
				spinner.setSelection(i);
			}
			else{
				spinner.setSelection(0);
			}
		}
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				String ids = ((CItem) spinner.getSelectedItem()).getID();
				String value = ((CItem) spinner.getSelectedItem()).getValue();
				map.put(ids, value);
				// list.add(0, map);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

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
				Intent intent = new Intent();
				intent.setAction("android.intent.action.YhMain");
				sendBroadcast(intent);
				Toast.makeText(getApplicationContext(), "提交成功",
						Toast.LENGTH_SHORT).show();
				finish();
				// 刷新列表

				break;
			case SUBMIT_ERROR:// 提交失败
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(), "对不起，提交失败！", Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};
	private ImageView imageMap;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_friends:
			finish();
			break;
		case R.id.camera:// 拍照
			Intent intentFromCapture = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			imageFile = new File(file, "ceshi.jpg");
			Uri u = Uri.fromFile(imageFile);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, u);
			startActivityForResult(intentFromCapture, 1);
			break;
		case R.id.mic:// 录音
			Intent intentFromMic = new Intent(this, MicActivity.class);
			intentFromMic.putExtra("baseId", "YH");
			intentFromMic.putExtra("baseSN", "YH");
			startActivityForResult(intentFromMic, 3);
			break;
		default:
			break;
		}
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
		Bitmap photo = null;
		FileOutputStream baos = null;
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "还未拍照",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), "拍照成功", Toast.LENGTH_SHORT)
					.show();
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String picDate = "YH_" + sDateFormat.format(new java.util.Date());
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
					resizeBitmap.recycle();
					imageFile.delete();					
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
			// 刷新gallery
			ArrayList<String> newVoiceList = getVoiceList();
			voiceAdapter.setData(newVoiceList);
			voiceAdapter.notifyDataSetChanged();
			//长按地图返回的经纬度（隐患选择经纬度）
		}else if(requestCode == 4){
			if(data==null){
				return;
			}
			EditText address = (EditText)sf.get("ProblemsPlace").getView();
			address.setText(data.getStringExtra("address"));
			
			EditText etlat = (EditText)sf.get("DangerLatitude").getView();
			etlat.setText(data.getStringExtra("lat"));
			
			EditText etlon = (EditText)sf.get("Coordinate").getView();
			etlon.setText(data.getStringExtra("log"));
			
			System.out.println("返回的经纬度："+latitude+":::"+longitude);
		}
	}

	/**
	 * 界面销毁时，删除 picPathChild = picPath + "/YH"文件夹 保证下次进入新建隐患没有附件
	 */
	@Override
	protected void onDestroy() {
		FileUtil.deleteFolder(picPathChild);// 删除源文件
		super.onDestroy();
	}
	
}
