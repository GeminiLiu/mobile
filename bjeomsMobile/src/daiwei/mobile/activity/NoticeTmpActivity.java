package daiwei.mobile.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import eoms.mobile.R;
import daiwei.mobile.Tools.NetWork;
import daiwei.mobile.Tools.SIM;
import daiwei.mobile.activity.YhTmpActivity.ViewType;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.GDDataService;
import daiwei.mobile.service.GDDataServiceImp;
import daiwei.mobile.service.SubmitService;
import daiwei.mobile.service.SubmitServiceImp;
import daiwei.mobile.util.StringUtil;
import daiwei.mobile.util.WaitDialog;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.BaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.Dic;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.FieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 公告详情页面
 * @author qch
 * 2013/6/23
 */
public class NoticeTmpActivity extends Activity implements OnClickListener {
	public int detailId = R.layout.gd_detail_bg1;
	private TextView tv_title;// 头标题
	private TextView tv_optiontitle;// 标题
	private TextView tv_ID;// ID
	private ImageView img_back;// 返回按钮
	private Intent intent;
	private String baseSchema;
	private String baseSummary;
	private String baseSN;
	private String baseId;
	private String taskId;
	// private int isWait;
	private LinearLayout ll_content;
	private LayoutInflater inflater;
	private Templet tmp;// 公告模板
	private XMLUtil xmlUtil;
	private Handler handler;
	HashMap<String, ViewType> viewTypeMap = new HashMap<String, ViewType>();
	private int mSingleChoiceID = 0;//建单人联系方式选择的条目
	private RelativeLayout rlt;//底部动态加载条的位置
	private LinearLayout lnyt;//底部横条
	// 地图图标
	private ImageView mapicon_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.gd_detail_bg1);
		MyData.add(NoticeTmpActivity.this);
		findView();
		getIntentData();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				List<Map<String,String>> list=(List<Map<String, String>>) msg.obj;
				if(list.size()!=0){//请求的界面数据有内容
					Map<String,String> map = list.get(0);//获取map集合
					Set<Entry<String, String>> set = map.entrySet();//变成set集合
					Iterator<Entry<String, String>> it=set.iterator();//拿到迭代器
					while(it.hasNext()){
						Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
						System.out.println("公告数据"+entry.getKey()+":::"+entry.getValue());
						ViewType vt = viewTypeMap.get(entry.getKey());//不直接返回值  返回ViewType类型
						if(vt!=null){
							if(vt.getType().equals("LABEL")){
								TextView tvtl = (TextView) vt.getView().findViewById(R.id.tv_vl);//gd_detail_textview
								if(entry.getKey().equals("End_Time")){//有效期
									String el=entry.getValue();
									if(!el.equals(""))
										el=StringUtil.StringToLongToString(el);//转换成日期时间
									tvtl.setText(tvtl.getText()+":"+el);
								}else{
									tvtl.setText(tvtl.getText()+":"+entry.getValue());
								}
								System.out.println("LABEL:"+tvtl.getText()+":"+entry.getValue());
							}else if(vt.getType().equals("STRING")){
								final TextView tvvl = (TextView) vt.getView().findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());
								System.out.println("STRING:entry.getKey()==========="+entry.getKey());
								System.out.println("STRING:entry.getValue()========="+entry.getValue());
								/** 判断建单人联系方式 */
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
							}else if(vt.getType().equals("TIME")){
								TextView tvvl=(TextView) vt.getView().findViewById(R.id.tv_vl);
								String el=entry.getValue();
								if(!el.equals(""))
									el=StringUtil.StringToLongToString(el);//转换成日期时间
								tvvl.setText(el);
							}else if(vt.getType().equals("SELECT")){
								TextView tvvl = (TextView) vt.getView()
										.findViewById(R.id.tv_vl);
								Map<String, String> dicMap = tmp.getDicDefine()
										.get(vt.getDic()).getOptions();
								tvvl.setText(dicMap.get(entry.getValue()));
								
							}else if(vt.getType().equals("TREE")){
								TextView tvvl=(TextView)vt.getView().findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());
							}
//							else if(vt.getType().equals("TEXTAREA")){
//								TextView tvvl=(TextView)vt.getView().findViewById(R.id.tv_vl);
//								tvvl.setText(entry.getValue());
//							}
						}
					}
				}
				super.handleMessage(msg);
			}
			
		};
		super.onCreate(savedInstanceState);
		
	
		logic();
		loadData();
	}
	
	  /**查找view控件*/
	private void findView() {
		// 预留处理文本头
		tv_title = (TextView) findViewById(R.id.tv_title);// 头标题
		tv_optiontitle = (TextView) findViewById(R.id.deal_tv1);// 故障处理工单
		tv_ID = (TextView) findViewById(R.id.deal_content); // 一级处理中
		img_back = (ImageView) findViewById(R.id.iv_friends);// 返回按钮
		img_back.setOnClickListener(this);
		ll_content = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		intent = getIntent();
		rlt = (RelativeLayout) findViewById(R.id.gd_bottom);// 底部动态加载条位置
		lnyt = (LinearLayout) inflater.inflate(R.layout.footbg, null, true);// 底部横条
//		mapicon_img = (ImageView) findViewById(R.id.imgview_map_icon);// 又上角地图图标
//		mapicon_img.setVisibility(View.GONE);//公告不要地图
	}

	/**
	 * 获取上一页传过来的数据
	 */
	private void getIntentData() {
		baseSchema = intent.getStringExtra("BaseSchema");
		baseSummary = intent.getStringExtra("BaseSummary");
		baseSN = intent.getStringExtra("BaseSN");
		baseId = intent.getStringExtra("BaseID");
		taskId = intent.getStringExtra("TaskID");
		// 处理文本头
		tv_title.setText("公告详情");
		tv_optiontitle.setText(baseSummary);
		tv_ID.setText(baseSN);
		// 获取模板
		tmp = TempletModel.TempletMap.get(baseSchema);
		System.out.println("公告tmp:" + tmp);
		if (tmp == null) {
			Toast.makeText(getApplicationContext(), "对不起，公告模板为空", 0).show();
		}
	}

	/**
	 * 处理逻辑——界面数据
	 */
	private void logic() {
//		rlt.addView(lnyt);// 加底部背景
		// 处理文本体
		if(tmp!=null){
		List<BaseField> list = tmp.getBaseFields();
		for (int i = 0; i < list.size(); i++) {
			BaseField baseField = list.get(i);
			if (baseField.getType().equals("field")) {
				addField(ll_content, baseField.getField());
			} else {
				addfieldGroup(ll_content, baseField.getFieldGroup());
			}
		}
		//处理底部按钮
//		List<ActionButton> ab=tmp.getActionButtons();
//		for(int i=0;i<ab.size();i++){
//			ActionButton abn=ab.get(i);
//			System.out.println("按钮名字"+abn.getId()+":"+abn.getName());
//			if(abn.getId()!=null){
//				addActionButton(lnyt, abn, abn.getName());// 动态加载传数据方法
//			}else{
//				Toast.makeText(getApplicationContext(), "底部按钮为空", 0).show();
//			}
//		}
		
		
		}
	}

	private void addField(LinearLayout ll, Field field) {
		if (field.getType().equals("LABEL"))
			if(!field.getId().equals("DealProcessDesc")){//不显示流程信息
				addLabel(ll, field);
				}
	}

	private void addLabel(LinearLayout ll, Field field) {
		View view = inflater.inflate(R.layout.gd_detail_textview, null, true);
		TextView tv = (TextView) view.findViewById(R.id.tv_vl);
		tv.setText(field.getText());
		ll.addView(view);
		 putViewType(view, field);

	}

	private void addfieldGroup(LinearLayout ll, FieldGroup fieldGroup) {
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null, true);
		ll.addView(view);
		TextView tv_title = (TextView) view.findViewById(R.id.detail_tv2);// 找到标题文字
		final ImageView add = (ImageView) view.findViewById(R.id.add1);// 找到加号
		final LinearLayout MessageN = (LinearLayout) view
				.findViewById(R.id.ll_normalMessage);// 找到下面的内容的位置

		tv_title.setText(fieldGroup.getText());// 设置fieldGroup标题名
		add.setImageResource(R.drawable.add);
		MessageN.setVisibility(View.GONE);

		// 整个一条点击事件
		RelativeLayout normalMessage = (RelativeLayout) view
				.findViewById(R.id.ll_normalMessage1); // 标题
		normalMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {// 下面内容隐藏
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
				} else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}
		});
		// 放内容
		// LinearLayout ll_fieldGroup = (LinearLayout)
		// view.findViewById(R.id.ll_normalMessage);
		List<Field> fieldList = fieldGroup.getGroup();
		for (int i = 0; i < fieldList.size(); i++) {// 循环list集合
			addString(MessageN, fieldList.get(i));
		}

	}
	//增加内容
	private void addString(LinearLayout ll, Field field) {
		View view = inflater.inflate(R.layout.gd_detail_item_text, null, true);
		TextView tvtl = (TextView) view.findViewById(R.id.tv_tl);
		tvtl.setText(field.getText());// 标题
		TextView tvvl = (TextView) view.findViewById(R.id.tv_vl);
		tvvl.setText("");// 内容
		ll.addView(view);
		 putViewType(view, field);
	}
	/**
	 * 开启线程
	 * 获取公告界面数据
	 */
	private void loadData() {
		new Thread() {
			@Override
			public void run() {
				GDDataService ds = new GDDataServiceImp();// 调用工单接口
				xmlUtil = ds.getData(getApplicationContext(), baseId, taskId,
						baseSchema);
				List<Map<String, String>> list = xmlUtil.getList();// 获取数据
				System.out.println("打印公告界面数据：" + list);
				 Message msg = new Message();
				 msg.obj = list;
				 handler.sendMessage(msg);
				super.run();
			}

		}.start();
	}
	/**
	 * 适配动态底部按钮 提交数据
	 * @param LinearLayout lnyt 父布局
	 * @param ActionButton ab 按钮
	 * @param String str 按钮文字
	 */
	private void addActionButton(LinearLayout lnyt, final ActionButton ab,
			final String str) {
		final View view = inflater.inflate(R.layout.footitem, null, true);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));

		TextView bottomTv = (TextView) view.findViewById(R.id.bottom_tv);
		bottomTv.setText(str);
		lnyt.addView(view);

		
	}
	/** 点击电话号码弹出打电话提示 */
	private void CreatDialog(final String[] telephoneItems) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				NoticeTmpActivity.this);
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
							SIM checkSim=new SIM(NoticeTmpActivity.this);
						if(checkSim.isCanUseSim()){								
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ telephoneItems[mSingleChoiceID]));
						startActivity(intent);
							}else{
								 Toast.makeText(NoticeTmpActivity.this, R.string.sim_no, Toast.LENGTH_SHORT).show();
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
	private void putViewType(View v,Field fl){
		ViewType viewType = new ViewType();
		viewType.setType(fl.getType());
		viewType.setView(v);
		viewType.setDic(fl.getDic());
		viewTypeMap.put(fl.getId(), viewType);
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
	/** 返回键 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_friends:
			finish();
			break;
		}
	}
}
