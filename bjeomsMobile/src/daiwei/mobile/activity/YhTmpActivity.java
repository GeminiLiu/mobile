package daiwei.mobile.activity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import eoms.mobile.R;
import daiwei.mobile.common.MyData;
import daiwei.mobile.common.XMLUtil;
import daiwei.mobile.service.GDDataService;
import daiwei.mobile.service.GDDataServiceImp;
import daiwei.mobile.util.TempletUtil.TMPModel.ActionButton;
import daiwei.mobile.util.TempletUtil.TMPModel.BaseField;
import daiwei.mobile.util.TempletUtil.TMPModel.Field;
import daiwei.mobile.util.TempletUtil.TMPModel.FieldGroup;
import daiwei.mobile.util.TempletUtil.TMPModel.Templet;
import daiwei.mobile.util.TempletUtil.TMPModel.TempletModel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class YhTmpActivity extends Activity implements OnClickListener{
	//模板主体
		public int detailId = R.layout.gd_detail_bg1;
		private TextView tv_title;//头标题
		private TextView tv_optiontitle;//标题
		private TextView tv_ID;//ID
		private ImageView img_back;//返回按钮
		private Intent intent;		
		private String baseSchema;
		private String baseSummary;
		private String baseSN;
		private String baseId;
		private String taskId;
		private LinearLayout ll_content;
		private LayoutInflater inflater;
		private Templet tmp;
		private XMLUtil xmlUtil;
		private Handler handler;
		HashMap<String,ViewType> viewTypeMap=new HashMap<String,ViewType>();
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_detail_bg1);
//		MyData.activityHashMap.put("YhTmpActivity", this);//加入集合
		MyData.add(YhTmpActivity.this);
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				List<Map<String,String>> list=(List<Map<String, String>>) msg.obj;
				if(list.size()!=0){
					Map<String,String> map = list.get(0);//获取map集合
					Set<Entry<String, String>> set = map.entrySet();//变成set集合
					Iterator<Entry<String, String>> it=set.iterator();//拿到迭代器
					while(it.hasNext()){
						Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
						ViewType vt = viewTypeMap.get(entry.getKey());//不直接返回值  返回ViewType类型
						if(vt!=null){
							if(vt.getType().equals("LABEL")){
								TextView tvtl = (TextView) vt.getView().findViewById(R.id.tv_vl);//gd_detail_textview
								tvtl.setText(tvtl.getText()+":"+entry.getValue());
							}else if(vt.getType().equals("STRING")){
								TextView tvvl = (TextView) vt.getView().findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());
							}else if(vt.getType().equals("TIME")){
								TextView tvvl=(TextView) vt.getView().findViewById(R.id.tv_vl);
								String el=entry.getValue();
								if(!el.equals(""))
									el=StringToLongToString(el);//转换成日期时间
								tvvl.setText(el);
							}else if(vt.getType().equals("SELECT")){
								TextView tvvl=(TextView) vt.getView().findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());
							}else if(vt.getType().equals("TEXTAREA")){
								TextView tvvl=(TextView)vt.getView().findViewById(R.id.tv_vl);
								tvvl.setText(entry.getValue());
							}
						}
					}
				}
				super.handleMessage(msg);
			}
			
		};
		findView();
		getIntentData();
		logic();
		loadData();
		
	}
    /**查找view控件*/
	private void findView() {
		//预留处理文本头
		tv_title = (TextView) findViewById(R.id.tv_title);//头标题
		tv_optiontitle = (TextView) findViewById(R.id.deal_tv1);//故障处理工单
		tv_ID = (TextView) findViewById(R.id.deal_content);	//一级处理中	
		img_back=(ImageView) findViewById(R.id.iv_friends);//返回按钮
		img_back.setOnClickListener(this);		
		ll_content = (LinearLayout) findViewById(R.id.gd_frame_lv1);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		intent = getIntent();			
	}	
	/*** 获取上一页传过来的数据*/	 
	private void getIntentData() {
		baseSchema = intent.getStringExtra("BaseSchema");
		baseSummary = intent.getStringExtra("BaseSummary");
		baseSN = intent.getStringExtra("BaseSN");
		baseId = intent.getStringExtra("BaseID");
		taskId = intent.getStringExtra("TaskID");		
		//处理文本头
		tv_title.setText("隐患详情");	
		tv_optiontitle.setText(baseSummary);
		tv_ID.setText(baseSN);
		
		tmp = TempletModel.TempletMap.get(baseSchema);
		System.out.println("隐患模板："+tmp);
	}
	/**
	 * 处理逻辑——界面数据
	 */
	private void logic() {
		//处理文本体 
		List<BaseField> list=tmp.getBaseFields();
		int size=list.size();
		for(int i=0;i<size;i++){
			BaseField baseField=list.get(i);
			if(baseField.getType().equals("field")){
				addField(ll_content, baseField.getField());
			}else{
				addfieldGroup(ll_content, baseField.getFieldGroup());
			}
			
		}
		//处理底部按钮
		List<ActionButton> listButton=tmp.getActionButtons();
		
	}
	private void addField(LinearLayout ll,Field field){
		if(field.getType().equals("LABEL"))
			addLabel(ll,field);
	}
	private void addLabel(LinearLayout ll,Field field){
		View view = inflater.inflate(R.layout.gd_detail_textview,
				null, true);
		TextView tv = (TextView) view.findViewById(R.id.tv_vl);
		tv.setText(field.getText());
		ll.addView(view);
		putViewType(view, field);
		
	}
	
	private void addfieldGroup(LinearLayout ll,FieldGroup fieldGroup){
		View view = inflater.inflate(R.layout.gd_detail_item_lv, null,
				true);
		ll.addView(view);
		TextView tv_title = (TextView) view.findViewById(R.id.detail_tv2);//找到标题文字
		final ImageView add = (ImageView) view.findViewById(R.id.add1);//找到加号
		final LinearLayout MessageN = (LinearLayout) view.findViewById(R.id.ll_normalMessage);//找到下面的内容的位置
		
		tv_title.setText(fieldGroup.getText());//设置fieldGroup标题名
		add.setImageResource(R.drawable.add);
		MessageN.setVisibility(View.GONE);

		//整个一条点击事件
		RelativeLayout normalMessage = (RelativeLayout) view.findViewById(R.id.ll_normalMessage1);	//标题
		normalMessage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (MessageN.getVisibility() == View.GONE) {//下面内容隐藏
					MessageN.setVisibility(View.VISIBLE);
					add.setImageResource(R.drawable.decrease);
				} else {
					MessageN.setVisibility(View.GONE);
					add.setImageResource(R.drawable.add);
				}
			}
		});
		//放内容
//		LinearLayout ll_fieldGroup = (LinearLayout) view.findViewById(R.id.ll_normalMessage);
		List<Field> fieldList= fieldGroup.getGroup();
		for(int i=0;i<fieldList.size();i++){//循环list集合
			addString(MessageN,fieldList.get(i));
		}
		
	}	
	private void addString(LinearLayout ll,Field field){
		View view = inflater.inflate(R.layout.gd_detail_item_text,
				null, true);
		TextView tvtl = (TextView) view.findViewById(R.id.tv_tl);
		tvtl.setText(field.getText());//标题
		TextView tvvl = (TextView) view.findViewById(R.id.tv_vl);
		tvvl.setText("");//内容
		ll.addView(view);
		putViewType(view, field);
	}
	
	/**适配动态底部按钮*/
	private void addActionButton(LinearLayout ll, final ActionButton ab,final String str){
		final View view= inflater.inflate(R.layout.footitem, null, true);//填充布局
		view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1)); 
		TextView tv = (TextView)view.findViewById(R.id.bottom_tv);
		tv.setText(str);//设置按钮名字
		
		ll.addView(view);
		/*lnyt.setOnTouchListener(new OnTouchListener() {//拦截事件
			@Override
			public boolean onTouch(View v, MotionEvent event) {
					return true;
			}
		});*/
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				view.findViewById(R.id.imageButton).setBackgroundResource(R.drawable.bottom_sendup1);
//				if(str.equals("受理")){
//					Toast.makeText(getApplicationContext(), "受理成功",0).show();
//					finish();
//				}else{
//				Intent intent = new Intent(getApplicationContext(),BaseHandleActivity.class);
//				Bundle mBundle = new Bundle();  
//				mBundle.putSerializable("field", ab); //ActionButton对象
//				mBundle.putSerializable("tmp",tmp);  //Templet对象				
//				mBundle.putString("title",str);				
//				mBundle.putString("baseId",baseId);
//				mBundle.putString("category",baseSchema);
//				mBundle.putString("taskId", taskId);
//				mBundle.putString("actionId", ab.getId());
//				mBundle.putString("actionCode", ab.getCode());
//				intent.putExtras(mBundle);  
//				startActivity(intent);			
//				}
			}
		});
	}
	
	private void loadData() {
		new Thread(){
			@Override
			public void run() {
				GDDataService ds = new GDDataServiceImp();//调用工单接口
				xmlUtil = ds.getData(getApplicationContext(),baseId, taskId,baseSchema);
				List<Map<String,String>> list = xmlUtil.getList();//获取数据
				System.out.println("隐患界面数据："+list);
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);
				super.run();
			}
			
		}.start();

	}
	/**返回键*/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_friends:
			finish();
			break;
		}
	}	
	/**
	 * 日期转换
	 * @param searcherInput
	 * @return
	 */
	private String StringToLongToString(String searcherInput) {
		long lgTime = 0;
		Boolean judge = false;
		if(searcherInput.equals("0"))
			return "";
		
		try {
			Integer.parseInt(searcherInput);
			searcherInput +="000";
			judge = true;
		} catch (NumberFormatException e) {
			judge = false;
		}
		if (judge == true) {
			lgTime = Long.parseLong(searcherInput);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			searcherInput = sdf.format(lgTime);
		}
		
		
		return searcherInput;
		
	}
	private void putViewType(View v,Field fl){
		ViewType viewType = new ViewType();
		viewType.setType(fl.getType());
		viewType.setView(v);
		viewTypeMap.put(fl.getId(), viewType);
	}
	class ViewType{
		private String type;
		private View view;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public View getView() {
			return view;
		}
		public void setView(View view) {
			this.view = view;
		}
	}
}

